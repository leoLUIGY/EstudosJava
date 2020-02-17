package com.glorhalla.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.glorhalla.main.Game;

public class Tubo extends Entity{
	public Tubo(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		// TODO Auto-generated constructor stub
	}

	public void tick() {
		x--;
		if(x+width <=0) {
			Game.entities.remove(this);
		}
		if(x+width==Game.player.x-10) {
			Game.score+=0.5;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect((int)x,(int)y,width, height);
	}
}
