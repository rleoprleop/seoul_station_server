package com.capstone.capstone.service.Game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlockBox {
    private double x_right;
    private double x_left;
    private double y;
    private int width;// = 40;
    private int height;// = 70;

    public BlockBox(double x_right, double x_left, double y){
        this.x_right=x_right;
        this.x_left=x_left;
        this.y=y;
        width=40;
        height=70;
    }
}
