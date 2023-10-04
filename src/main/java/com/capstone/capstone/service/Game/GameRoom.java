package com.capstone.capstone.service.Game;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameRoom {//전체 게임방 gameroom->room->Player1, Player2, ...
    private Map<String, Room> roomMap=new HashMap<>();//roomid가 키.

    public Room getRoom(String roomId){
        return roomMap.get(roomId);
    }

    public void addRoom(String roomId, Room room){
        roomMap.put(roomId,room);
    }

    public boolean hasRoom(String roomId){
        if(roomMap.get(roomId)==null){
            return false;
        }
        else{
            return true;
        }
    }
    public void removeRoom(String roomId){
        roomMap.remove(roomId);
    }
}
