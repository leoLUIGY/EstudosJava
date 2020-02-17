package com.glorhalla.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.glorhala.world.Camera;
import com.glorhalla.main.Game;

public class Particle extends Entity{
	public int lifeTime = 10;
	public int curLife = 0;
	
	public int spd = 2;
	public double dx = 0;
	public double dy = 0;
	public Particle(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		dx = new Random().nextGaussian();
		dy = new Random().nextGaussian();
	}
	
	public void tick() {
		x+=dx*spd;
		y+=dy*spd;
		curLife++;
		if(lifeTime == curLife) {
			Game.entities.remove(this);
		}
	
	}

	public void render(Graphics g) {
		 g.setColor(Color.red);
		 g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 2, 2);
	}
}
