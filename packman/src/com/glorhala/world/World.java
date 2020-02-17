package com.glorhala.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;


import javax.imageio.ImageIO;

import com.glorhalla.entities.Enemy;
import com.glorhalla.entities.Entity;
import com.glorhalla.entities.Fruta;
import com.glorhalla.entities.Player;
import com.glorhalla.main.Game;

public class World {
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE= 16;
	
	public World(String path) {
	
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
					} else if (pixelAtual == 0xff00ff00) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL);
					}else if (pixelAtual == 0xff0000ff) {
						//player
						Game.player.setX(xx*16);
						Game.player.setY(yy * 16);
					} else if(pixelAtual == 0xffff0000){
						//Maça para o player
						Fruta fruta = new Fruta(xx*16, yy*16, 16, 16, 0, Entity.MACA_SPRITE);
						Game.entities.add(fruta);
						Game.frutas_contagem++;
					}else if(pixelAtual == 0xffffffff){
						Enemy enemy = new Enemy(xx*16, yy * 16, 16, 16,1, Entity.ENEMY1);
						Game.entities.add(enemy);
					}else if(pixelAtual == 0xffffff00){
						Enemy enemy = new Enemy(xx*16, yy * 16, 16, 16,1, Entity.ENEMY2);
						Game.entities.add(enemy);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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

	public static void restartGame() {
		Game.state = "NORMAL";
		Game.player = new Player(16, 16, 16, 16,2, Game.spritesheet.getSprite(32, 0, 16, 16));
		Game.entities.clear();
		Game.entities.add(Game.player);
		Game.frutas_atual = 0;
		Game.frutas_contagem = 0;
		Game.world = new World("/level.png");
		
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
	
	
}
