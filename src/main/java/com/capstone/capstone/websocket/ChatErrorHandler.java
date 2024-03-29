package com.capstone.capstone.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class ChatErrorHandler extends StompSubProtocolErrorHandler {

    public ChatErrorHandler() {
        super();
    }

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]>clientMessage, Throwable ex)
    {
        log.info("websocket error {}", clientMessage);
        ex.printStackTrace();
        Throwable exception = new MessageDeliveryException("error!!");
        if (exception instanceof MessageDeliveryException)
        {

            return handleUnauthorizedException(clientMessage, exception);
        }

        return super.handleClientMessageProcessingError(clientMessage, ex);
    }

    private Message<byte[]> handleUnauthorizedException(Message<byte[]> clientMessage, Throwable ex)
    {

        String message = ex.getMessage();

        return prepareErrorMessage(clientMessage, message/*, String.valueOf(ErrorCodeConstants.UNAUTHORIZED_STRING)*/);

    }

    private Message<byte[]> prepareErrorMessage(Message<byte[]> clientMessage, String message/*, String errorCode*/)
    {


        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);

        //accessor.setMessage(errorCode);
        accessor.setLeaveMutable(true);

        return MessageBuilder.createMessage(message.getBytes(StandardCharsets.UTF_8), accessor.getMessageHeaders());
    }
}