package com.glorhala.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.glorhalla.entities.Bullet;
import com.glorhalla.entities.Enemy;
import com.glorhalla.entities.Entity;
import com.glorhalla.entities.Lifepack;
import com.glorhalla.entities.Particle;
import com.glorhalla.entities.Player;
import com.glorhalla.entities.Weapon;
import com.glorhalla.graficos.Spritesheet;
import com.glorhalla.main.Game;

public class World {
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE= 16;
	
	public World(String path) {
		/*
		Game.player.setX(0);
		Game.player.setY(0);
		WIDTH = 100;
		HEIGHT = 100;
		tiles = new Tile[WIDTH*HEIGHT];
		
		
		for(int xx = 0; xx < WIDTH; xx++) {
			for(int yy = 0; yy < HEIGHT; yy++) {
				tiles[xx+(yy*WIDTH)] = new WallTile(xx*16, yy*16,Tile.TILE_WALL);
			}
		}
		
		int dir =  0;
		int xx = 0, yy = 0;
		for(int i = 0; i < 200; i++) {
			tiles[xx+(yy*WIDTH)] = new FloorTile(xx*16, yy*16,Tile.TILE_FLOOR);
			if(dir == 0) {
				
				if(xx<WIDTH) {
					xx++;
				}
			}else if(dir ==1) {

				if(xx > 0) {
					xx--;
				}
			}else if(dir == 2) {
				if(yy<HEIGHT) {
					yy++;
				}
				
			} else if(dir == 3) {

				if(yy > 0) {
					yy--;
				}
			}
			
			if(Game.rand.nextInt(100) < 30) {
				dir = Game.rand.nextInt(4);
			}
			
		}*/
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(),pixels, 0, map.getWidth() );
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy< map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy*map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
					if(pixelAtual == 0xff000000) {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
					} else if (pixelAtual == 0xffffffff) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL);
					}else if (pixelAtual == 0xff0074fa) {
						//player
						Game.player.setX(xx*16);
						Game.player.setY(yy * 16);
					} else if(pixelAtual == 0xffff0000){
						//enemy
						Enemy en = new Enemy(xx*16, yy*16, 16, 16, Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
					} else if(pixelAtual == 0xff00ffff) {
						//arma
						Game.entities.add(new Weapon(xx*16, yy*16, 16, 16, Entity.WEAPON_EN));
					}else if(pixelAtual == 0xffffff00) {
						//life
						Lifepack pack = new Lifepack(xx*16, yy*16, 16, 16, Entity.LIFEPACK_EN);
						Game.entities.add(pack);
						pack.setMask(8, 8, 8, 8);
					}else if(pixelAtual == 0xff00ff00) {
						//bullet
						Game.entities.add(new Bullet(xx*16, yy*16, 16, 16, Entity.BULLET_EN));
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void generateParticle(int amount, int x, int y) {
		Game.entities.add(new Particle(x, y,1, 1, null));
	}
	
	public static boolean  isFreeDynamic(int xnext, int ynext, int width, int height) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext /TILE_SIZE;
		
		int x2 = (xnext + width - 1) / TILE_SIZE;
		int y2 = ynext/TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + height- 1)/TILE_SIZE;
		
		int x4 = (xnext  + width - 1 )/ TILE_SIZE;
		int y4 = (ynext + height - 1)/TILE_SIZE;
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof WallTile)||
				(tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));
	}
	
	public static boolean  isFree(int xnext, int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext /TILE_SIZE;
		
		int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = ynext/TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + TILE_SIZE - 1)/TILE_SIZE;
		
		int x4 = (xnext  + TILE_SIZE - 1 )/ TILE_SIZE;
		int y4 = (ynext + TILE_SIZE - 1)/TILE_SIZE;
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof WallTile)||
				(tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));
	}

	public static void restartGame(String level) {
		Game.entities =  new ArrayList<Entity>();
		Game.enemies =  new ArrayList<Enemy>();
		Game.spritesheet = new Spritesheet("/spriteSheet.png");
		Game.player = new Player(30, 40, 16, 16, Game.spritesheet.getSprite(32, 0, 16, 16));
		Game.entities.add(Game.player);
		Game.world  = new World("/" + level);
		return;
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x / 16;
		int ystart = Camera.y/16;
		
		int xfinal = xstart + (Game.WIDTH/16);
		int yfinal = ystart + (Game.WIDTH/16);
		for(int xx = xstart; xx <= xfinal; xx++){
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx<0 || yy<0||xx>=WIDTH||yy>=HEIGHT) {
					continue;
				}
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
	
	public static void renderMiniMap() {
		for(int i = 0; i < Game.minimapPixels.length; i++) {
			Game.minimapPixels[i] = 0;
		}
		for(int xx= 0; xx < WIDTH; xx++) {
			for(int yy = 0; yy < HEIGHT; yy++) {
				if(tiles[xx+(yy*WIDTH)] instanceof WallTile) {
					Game.minimapPixels[xx+(yy*WIDTH)] = 0x00ff00;
				}
			}
		}
		int xPlayer = Game.player.getX() / 16;
		int yPlayer = Game.player.getY()/16;
		
		Game.minimapPixels[xPlayer + (yPlayer * WIDTH)] = 0x0000ff;
		
	} 
}
