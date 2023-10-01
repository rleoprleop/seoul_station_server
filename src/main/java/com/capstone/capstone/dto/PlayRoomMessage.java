package com.capstone.capstone.dto;

import lombok.Data;

@Data
public class PlayRoomMessage {//플레이어 이름, 위치, 뱡향, 체력, 플레이가 끝나는지 확인.
    private int keyCode;
    private boolean keydown;
    private boolean active;
    /*private String userId;
    private String nickName;
    private int x;
    private boolean right;
    private int hp;
    private boolean clear;
    private String reward;*/
}
