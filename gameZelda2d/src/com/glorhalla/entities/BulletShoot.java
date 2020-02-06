package com.glorhalla.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.glorhala.world.Camera;
import com.glorhalla.main.Game;

public class BulletShoot extends Entity {
	private double dx;
	private double dy;
	private double spd = 4;
	
	private int life =50, curLife = 0;
	
	public BulletShoot(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
		if(curLife == life) {
			Game.bullets.remove(this);
			return;
		}
		// TODO Auto-generated constructor stub
	}

	 
	 public void tick() {
		 x+=dx * spd;
		 y+=dy * spd;
	 }
	 public void render(Graphics g) {
		 g.setColor(Color.YELLOW);
		 g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 2, 2);
	 }
}
