package com.capstone.capstone.websocket;

import com.capstone.capstone.security.JwtTokenProvider;
import com.capstone.capstone.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebsocketSecurityInterceptor implements ChannelInterceptor, ApplicationContextAware {

    //private final WaitRoomFacadeService waitRoomFacadeService;
    //private final AntPathMatcher antPathMatcher;
    private WebSocketService webSocketService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        webSocketService =  applicationContext.getBean("webSocketService",WebSocketService.class);
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        System.out.println("preSend, full message: "+headerAccessor);
        log.info("{}",headerAccessor.getSessionId());
        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
            String bearer=headerAccessor.getFirstNativeHeader("Authorization");
            String token = JwtTokenProvider.getTokenFromHeader(bearer);
            System.out.println("token: "+token);
            log.info("header: {}, {}",headerAccessor.getFirstNativeHeader("WaitRoomId"),headerAccessor.getFirstNativeHeader("UserId"),headerAccessor.getSessionId());
            webSocketService.createGame(headerAccessor.getFirstNativeHeader("WaitRoomId"),headerAccessor.getFirstNativeHeader("UserId"),headerAccessor.getSessionId());
            if(!JwtTokenProvider.validateToken(token)){
                System.out.println("error!!");
                throw new MessageDeliveryException("error authorization");
            }
            /*UsernamePasswordAuthenticationToken authentication = UsernamePasswordAuthenticationToken()
            // accessor에 등록
            accessor.setUser(authentication);*/
        }
        if(StompCommand.DISCONNECT.equals(headerAccessor.getCommand())) {
            webSocketService.endGame(headerAccessor.getSessionId());
        }
        return message;
    }

    ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    /*private void isValidateWaitRoomIdAndJoinMember(StompHeaderAccessor headerAccessor) {
        String accessToken = headerAccessor.getFirstNativeHeader("Authorization");
        String refreshToken = headerAccessor.getFirstNativeHeader("RefreshToken");
        String waitRoomId = headerAccessor.getFirstNativeHeader("WaitRoomId");
        String userId = headerAccessor.getFirstNativeHeader("UserId");

        if (accessToken == null || refreshToken == null || waitRoomId == null || userId == null)
            throw new WebsocketSecurityException();

        log.info("validateUserAccessor >>");
        validateUserAccessor(validateTokenAccessor(accessToken, refreshToken), userId);

        String destination = headerAccessor.getDestination();
        log.info("destination = {}", destination);
        log.info("destination >>");
        if (destination == null) throw new WebsocketSecurityException();

        if (isApplyUri(destination)) {
            log.info("isJoinedMember >>");
            waitRoomFacadeService.isJoinedMember(waitRoomId, userId);
        }
    }

    private String validateTokenAccessor(String accessToken, String refreshToken) {
        try {
            String userIdFromAccess = tokenProviderPolicy.getUserIdFromToken(tokenProviderPolicy.removeType(accessToken));
            String userIdFromRefresh = tokenProviderPolicy.getUserIdFromToken(refreshToken);

            if (!userIdFromAccess.equals(userIdFromRefresh)) throw new WebsocketSecurityException();

            return userIdFromAccess;
        } catch (Exception e) {
            throw new WebsocketSecurityException();
        }
    }

    private boolean isApplyUri(String destination) {*/
        //return !antPathMatcher.match("/wait-service/waitroom/**/**/join", destination);
   /* }

    private void validateUserAccessor(String parseUserId, String headerUserId) {
        if (!parseUserId.equals(headerUserId)) throw new WebsocketSecurityException();
    }*/
}