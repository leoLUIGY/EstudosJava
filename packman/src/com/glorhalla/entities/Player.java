package com.glorhalla.entities;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.glorhala.world.Camera;
import com.glorhala.world.World;
import com.glorhalla.main.Game;


public class Player extends Entity{

	public boolean right , up, left, down;

	public BufferedImage sprite_left;
	public int lastDir = 1;

	public Player(int x, int y, int width, int height,int speed, BufferedImage sprite) {
		super(x, y, width, height,speed, sprite);
		// TODO Auto-generated constructor stub
		sprite_left = Game.spritesheet.getSprite(48, 0,16, 16);
	}
	
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	

	public void tick() {
		
		
		depth = 1;
		
		
		
	
		if(right && World.isFree((int)(x+speed), this.getY())) {
		
			x+=speed;
			lastDir = 1;
		}else if(left && World.isFree((int)(x-speed), this.getY())) {
			x-=speed;
			lastDir = -1;
		}
		
		if(up && World.isFree(this.getX(),(int)(y - speed))) {
		
			y-=speed;
		}else if(down && World.isFree(this.getX(), (int)(y+speed))) {
		
			y+=speed;
		}
	
		verificarPegaFruta();
		verificarColisEnemy();
		if(Game.frutas_contagem == Game.frutas_atual ) {
			World.restartGame();
			return;
		}
	}
	//&& Game.frutas_atual > 1
	public void verificarPegaFruta() {
		for(int i = 0; i< Game.entities.size();i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Fruta) {
				if(Entity.isColliding(this, current)) {
					Game.frutas_atual++;
					Game.entities.remove(i);
					return;
				}
			}
		}
	}
	
	public void verificarColisEnemy() {
		for(int i = 0; i< Game.entities.size();i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Enemy) {
				if(Entity.isColliding(this, current)) {
					Game.state = "PERDEU";
					return;
					
				}
			}
		}
	}
	public void render(Graphics g) {
		if(lastDir == 1) {
			super.render(g);
		} else {
			g.drawImage(sprite_left, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}
	
}
