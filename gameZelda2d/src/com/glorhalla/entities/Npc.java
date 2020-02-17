package com.glorhalla.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.glorhalla.main.Game;

public class Npc extends Entity{

	public String[] frases = new String[2];
	public boolean showMessage = false;
	public boolean show = false;
	
	public int curIndexMsg = 0;
	public int fraseIndex = 0;
	public int time = 0;
	public int maxTime = 10;
	public static boolean atacar = false;
	public Npc(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		frases[0] ="corre carrocho!";
		frases[1] ="Destrua os inimigos";
		// TODO Auto-generated constructor stub
	}
	
	public void tick() {
		int xPlayer = Game.player.getX();
		int yPlayer = Game.player.getY();
		
		int xNpc = (int)x;
		int yNpc = (int)y;
		
		if(Math.abs(xPlayer - xNpc) < 30 &&
				Math.abs(yPlayer - yNpc)< 30) {
			
			if(show ==false) {
			showMessage = true;
			show = true;
			}
		} else {
			//showMessage = false;
		}
		
		if(showMessage) {
			this.time++;
			if(this.time >= this.maxTime) {
				this.maxTime = 0;
				if(curIndexMsg < frases[fraseIndex].length() - 1) {
				curIndexMsg++;
				} else {
					if(fraseIndex < frases.length - 1) {
						fraseIndex++;
						curIndexMsg = 0;
					}
				}
			}
		}
	}
	
	public void render(Graphics g) {
		super.render(g);
		if(showMessage) {
			g.setColor(Color.WHITE);
			g.fillRect(9, 9, Game.WIDTH - 18, Game.HEIGHT - 18);
			g.setColor(Color.blue);
			g.fillRect(10,10,Game.WIDTH-20, Game.HEIGHT - 20);
			
			g.setFont(new Font("Arial", Font.BOLD, 9));
			g.setColor(Color.white);
			g.drawString(frases[fraseIndex].substring(0, curIndexMsg), (int)x, (int)y);
			
			g.drawString(">Pressione Enter para Fechar<", (int)x+10, (int)y+13);
		}
	}
}
