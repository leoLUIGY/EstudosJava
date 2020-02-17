package com.glorhalla.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.glorhala.world.AStar;
import com.glorhala.world.Camera;
import com.glorhala.world.Vector2i;
import com.glorhala.world.World;
import com.glorhalla.main.Game;
import com.glorhalla.main.Sound;

public class Enemy extends Entity{
	
	private double speed = 0.6;
	
	

	private int frames  = 0, maxFrames = 20, index = 0, maxIndex = 1;
	private BufferedImage[] sprites;
	
	private int life = 3;
	
	private boolean isDamaged  = false;
	private int damageFrames = 10, damageCurrent = 0;
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		// TODO Auto-generated constructor stub
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(112, 16, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(128, 16, 16, 16);
	}
	public void tick() {
		depth = 0;
		maskx = 5;
		masky = 5;
		mwidth = 8;
		mheight = 8;
		/*
		if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 200) {
		if(this.isCollidingWithPlayer() == false) {
			if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY()) &&
					!isColidding((int)(x+speed), this.getY())) {
				x+= speed;
			}
			else if((int)x > Game.player.getX()&& World.isFree((int)(x-speed), this.getY()) &&
					!isColidding((int)(x-speed), this.getY())) {
				x-= speed;
			}
			if((int)y < Game.player.getY()&& World.isFree(this.getX(),(int)(y +speed)) &&
					!isColidding(this.getX(),(int)(y + speed))) {
				y+= speed;
			}
			else if((int)y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed)) && 
					!isColidding(this.getX(), (int)(y-speed))) {
				y-= speed;
			}
		} else {
			
			
		
		}
		}
		
		*/
		if(!isCollidingWithPlayer() && Npc.atacar == true) {
		if(path == null || path.size() ==0) {
			Vector2i start =new Vector2i((int)( x/ 16), (int)(y/16));
			Vector2i end =new Vector2i((int)( Game.player.x/ 16), (int)(Game.player.y/16));
			path = AStar.findPath(Game.world, start, end);
			}
		} else {
			if(Game.rand.nextInt(100) < 10  && Npc.atacar == true) {
			//	Sound.hurtEffect.play();
				Sound.music1.play();
				Game.player.isDamage = true;
				Game.player.life--;
				
			}
		}
		if(new Random().nextInt(100) < 90) {
			followPath(path);
		}
		if(new Random().nextInt(100) < 5) {
			Vector2i start =new Vector2i((int)( x/ 16), (int)(y/16));
			Vector2i end =new Vector2i((int)( Game.player.x/ 16), (int)(Game.player.y/16));
			path = AStar.findPath(Game.world, start, end);
		}
			frames++;
			if(frames ==maxFrames) {
				frames = 0;
				index++;
				
				if(index > maxIndex) {
					index = 0;
				
			}
		}
			collidingBullet();
			if(life <=0) {
				destroySelf();
				return;
			}
			
			if(isDamaged) {
				this.damageCurrent++;
				if(this.damageCurrent == this.damageFrames) {
					this.damageCurrent = 0;
					this.isDamaged = false;
				}
			}
	}
	
	public void destroySelf() {
		Game.enemies.remove(this);
		Game.entities.remove(this);
	}
	public void collidingBullet() {
		for(int i = 0; i< Game.bullets.size(); i++) {
			Entity e = Game.bullets.get(i);
			if(e instanceof BulletShoot) 
			{
				if(Entity.isColliding(this, e)) {
					
					isDamaged = true;
					life--;
					Game.bullets.remove(i);
					return;
				}
			}
		}
		
	}
	
	public boolean isCollidingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX()+ maskx,this.getY() + masky, mwidth, mheight);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(),16, 16 );
		
		return enemyCurrent.intersects(player);
	}
	
	public void render(Graphics g) {
		if(!isDamaged) {
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else {
			g.drawImage(Entity.ENEMY_FEEDBACK, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}
}
