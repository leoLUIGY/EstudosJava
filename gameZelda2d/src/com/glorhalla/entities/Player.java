package com.glorhalla.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.glorhala.world.Camera;
import com.glorhala.world.World;
import com.glorhalla.main.Game;

public class Player extends Entity{

	public boolean right , up, left, down;
	public int right_dir = 0, left_dir = 1;
	public int dir = right_dir;
	public double speed  = 1.4;
	
	private int frames  = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage playerDamage;
	
	private boolean arma = false;
	public int ammo = 0;
	
	private int damageFramas = 0;
	public boolean isDamage = false;
	
	public boolean shoot = false, mouseShoot = false;
	public  double life = 100, maxLife = 100;
	public int mx, my;
	
	public boolean jump = false;
	public boolean isJumping  = false;
	
	public int z = 0;
	
	public int jumpFrames = 50, jumpCur = 0;
	public boolean jumpUp = false, jumpDown = false;
	public int jumpSpd = 2;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		playerDamage  = Game.spritesheet.getSprite(0, 16,16,16);
		for(int i  = 0; i<4; i++) {
		rightPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 0, 16, 16);
		}
		for(int i  = 0; i<4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 16, 16, 16);
			}
	}
	
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void tick() {
		
		if(jump) {
			if(isJumping == false) {
				jump = false;
				isJumping = true;
				jumpUp = true;
			}
		}
		
		if (isJumping == true) {
			
				if(jumpUp ) {
				jumpCur+=2;
				} else if(jumpDown) {
					jumpCur -=2;
					if(jumpCur <=0) {
						isJumping = false;
						jumpDown = false;
						jumpUp = false;
					}
				}
				z = jumpCur;
				if(jumpCur >= jumpFrames) {
					jumpUp = false;
					jumpDown = true;
				}
			}
		
		
		moved = false;
		if(right && World.isFree((int)(x+speed), this.getY())) {
			moved = true;
			dir =  right_dir;
			x+=speed;
		}else if(left && World.isFree((int)(x-speed), this.getY())) {
			moved = true;
			dir =  left_dir;
			x-=speed;
		}
		
		if(up && World.isFree(this.getX(),(int)(y - speed))) {
			moved = true;
			y-=speed;
		}else if(down && World.isFree(this.getX(), (int)(y+speed))) {
			moved = true;
			y+=speed;
		}
		if(moved) {
			frames++;
			if(frames ==maxFrames) {
				frames = 0;
				index++;
				
				if(index > maxIndex) {
					index = 0;
				}
			}
		}
		
		this.checkCollisionLife();
		this.checkCollisionAmmo();
		this.checkCollisionGun();
		if(isDamage) {
			this.damageFramas++;
			if(this.damageFramas == 8) {
				this.damageFramas = 0;
				isDamage = false;
			}
		}
		
		if(shoot ) {
			
			shoot = false;
			if (arma && ammo >0) {
				ammo--;
				int dx = 0;
				int px = 0;
				int py = 6;
				if(dir == right_dir) {
					px = 12;
					dx = 1;
				} else {
					px = -6;
					dx = -1;
				}
				BulletShoot bullet = new BulletShoot(this.getX()+px, this.getY()+py, 3, 3, null, dx, 0);
				Game.bullets.add(bullet);
			}
		}
		
		if(mouseShoot)  {
			mouseShoot = false;
			if (arma && ammo >0) {
				ammo--;
				double angle = 0;
				
				int px = 8;
				int py = 8;
				if(dir == right_dir) {
					px = 12;
					angle = Math.toDegrees(Math.atan2(my - (this.getY() + py + 8 - Camera.y), mx - (this.getX()+px + 8-Camera.x)));
					
					
				} else {
					px = -6;
					angle = Math.toDegrees(Math.atan2(my - (this.getY() + py +  8 - Camera.y), mx - (this.getX()+ px+ 8-Camera.x)));
					
				
				}

				double dx =Math.cos(angle);
				double dy = Math.sin(angle);
				BulletShoot bullet = new BulletShoot(this.getX()+px, this.getY()+py, 3, 3, null, dx, dy);
				Game.bullets.add(bullet);
			}
		}
		if(life <= 0) {
			Game.gamestate = "GAME_OVER";
		}
		Camera.x =  Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);
		Camera.y =  Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*16 - Game.HEIGHT);
		
	}
	public void checkCollisionGun() {
		for(int i = 0; i< Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Weapon) {
				if(Entity.isColliding(this, atual)) {
					arma = true;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void checkCollisionAmmo() {
		for(int i = 0; i< Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Bullet) {
				if(Entity.isColliding(this, atual)) {
					ammo  += 20;
					
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void checkCollisionLife() {
		for(int i = 0; i< Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Lifepack) {
				if(Entity.isColliding(this, atual)) {
					life  += 10;
					if (life > 100) {
						life = 100;
					}
					Game.entities.remove(atual);
				}
			}
		}
	}
	public void render(Graphics g) {
		if(!isDamage) {
		if(dir == right_dir ) {
		g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
			if(arma) {
				g.drawImage(Entity.GUN_RIGHT, this.getX()+6 - Camera.x, this.getY() - Camera.y - z, null);
			}
		}
		else if(dir == left_dir) {
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
			if(arma) {
				g.drawImage(Entity.GUN_LEFT, this.getX()-6 - Camera.x, this.getY() - Camera.y - z, null);
				
			}
		}
		} else {
			g.drawImage(playerDamage, this.getX() - Camera.x,this.getY() - Camera.y - z, null);
		}
		if (isJumping) {
			g.setColor(Color.BLACK);
			g.fillOval( this.getX() - Camera.x+84, this.getY() - Camera.y+16,8, 8);
		}
	}
}
