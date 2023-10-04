/*package com.capstone.capstone.redis;

import com.capstone.capstone.dto.CreateWaitRoomRequestDTO;
import com.capstone.capstone.dto.JoinWaitRoomRequestDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.time.LocalDateTime.now;

@Getter
@RedisHash("waitRoom")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WaitRoom implements Serializable {

    @Id
    private String id;

    private int limitMembers;//같이 할 플레이어의 수. 2 or 4
    private boolean waiting;//대기방인지, 시작한 방인지.

    private List<String> members = new CopyOnWriteArrayList<>();//userid로 구성.

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime modifiedAt;

    public WaitRoom(JoinWaitRoomRequestDTO roomDto) {
        id = UUID.randomUUID().toString();
        limitMembers = roomDto.getLimitMembers();
        createdAt = now();
        modifiedAt = now();
        members.add(roomDto.getUserId());
    }*/
    /**
     * 대기방 인원이 여유가 있고, 요청이 호스트가 아니라면 회원 추가
     */
    /*public boolean joinMembers(String userId) {
        members.add(userId);
        modifiedAt = now();
        return true;
    }
*/
    /**
     * 대기방에 있는 유저 나가기
     */
   /* public void leaveMembers(String userId) {
        members.remove(userId);
        modifiedAt = now();
    }
*/
    /**
     * 대기방 만석 파악
     */
  /*  private boolean isNotFullMembers() {
        return members.size() < limitMembers;
    }
}*/