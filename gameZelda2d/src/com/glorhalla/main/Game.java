package com.glorhalla.main;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
//import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
//import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.glorhala.world.World;
import com.glorhalla.entities.BulletShoot;
import com.glorhalla.entities.Enemy;
import com.glorhalla.entities.Entity;
import com.glorhalla.entities.Player;
import com.glorhalla.graficos.Spritesheet;
import com.glorhalla.graficos.UI;



public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;
	private Thread thread;
	private int CUR_LEVEL =1, MAX_LEVEL = 2;
	private BufferedImage image;
	
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<BulletShoot> bullets;
	public static Spritesheet spritesheet;
	public static World world;
	public static Player player;
	
	public static Random rand;
	
	public UI ui;
	
	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("pixelart.ttf");
	public Font newfont;
	
	public static String gamestate = "MENU";
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;
	private boolean restartGame = false;
	
	public Menu menu;
	
	public boolean saveGame = false;
	
	public int mx, my;

	public Game() {
		Sound.musicBackground.loop();
		rand  = new Random();
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT*SCALE));
		initFrame();
		
		ui = new UI();
		image = new BufferedImage(WIDTH , HEIGHT, BufferedImage.TYPE_INT_RGB);
		entities =  new ArrayList<Entity>();
		enemies =  new ArrayList<Enemy>();
		bullets = new ArrayList<BulletShoot>();
		
		
		spritesheet = new Spritesheet("/spriteSheet.png");
		player = new Player(30, 40, 16, 16, spritesheet.getSprite(32, 0, 16, 16));
		world  = new World("/level1.png");
		entities.add(player);
		menu = new Menu();
		try {
			newfont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(16f);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initFrame() {
			frame = new JFrame("Game1");
			frame.add(this);
			frame.setResizable(false);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			}
	
	public synchronized void start() {
		thread  = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
		if(gamestate == "NORMAL") {
			if(this.saveGame) {
				this.saveGame = false;
				String[] opt1 = {"level"};
				int[] opt2 = {this.CUR_LEVEL};
				Menu.saveGame(opt1, opt2, 10);
			}
			this.restartGame = false;
		for(int i = 0; i< entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}
		
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).tick();
		}
		
		if(enemies.size() ==0) {
			CUR_LEVEL++;
			if(CUR_LEVEL > MAX_LEVEL) {
				CUR_LEVEL = 1;
			}
			String newWorld  = "level" + CUR_LEVEL + ".png";
			World.restartGame(newWorld);
		}
	}else if(gamestate == "GAME_OVER") {
		this.framesGameOver++;
		if(this.framesGameOver == 30) {
			this.framesGameOver = 0;
			if(this.showMessageGameOver) {
				this.showMessageGameOver = false;
			} else {
				this.showMessageGameOver = true;
			}
		} if(restartGame) {
			this.restartGame = false;
			Game.gamestate = "NORMAL";
			CUR_LEVEL = 1;
			String newWorld  = "level" + CUR_LEVEL + ".png";
			World.restartGame(newWorld);
		}
	} else if (gamestate == "MENU") {
	menu.tick();
	}
}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH , HEIGHT);
		
		world.render(g);
		//Graphics2D g2 = (Graphics2D) g;
		for(int i = 0; i< entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}
		
		ui.render(g);
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE,HEIGHT*SCALE,null);
		
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.setColor(Color.WHITE);
		g.drawString("Munição: " + player.ammo, 580, 20);
		g.setFont(newfont);
		if(gamestate == "GAME_OVER" ) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setFont(new Font("arial", Font.BOLD, 36));
			g.setColor(Color.WHITE);
			g.drawString("Game Over",(WIDTH*SCALE)/2 - 80, (HEIGHT*SCALE)/2 - 20);
			if(showMessageGameOver)
				g.drawString("Pressione enter para reiniciar",(WIDTH*SCALE)/2 - 300
						, (HEIGHT*SCALE)/2 + 20);
		} else if(gamestate == "MENU") {
			menu.render(g);
		}
		//Graphics2D g2 = (Graphics2D) g;
		//double angleMouse = Math.atan2(my - 200+25, mx -200 + 25);
		//g2.rotate(angleMouse, 200+25, 200+25);
		//g.setColor(Color.red);
		//g.fillRect(200,200,50,50);
		bs.show();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long lastTime = System.nanoTime();
		double amountOfTick = 60.0;
		double ns = 1000000000 / amountOfTick;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS" + frames);
				frames = 0;
				timer+=1000;
			}
		}
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_Z) {
					player.jump = true;
					
				}
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
			
		} else if(e.getKeyCode() ==  KeyEvent.VK_LEFT ||
			e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		
		// TODO Auto-generated method stub
			if (e.getKeyCode() == KeyEvent.VK_UP ||
					e.getKeyCode() == KeyEvent.VK_W) {
				player.up = true;
				if(gamestate == "MENU") {
					menu.up = true;
				}
			} else if(e.getKeyCode() ==  KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_S) {
				player.down = true;
				
				if(gamestate == "MENU") {
					menu.down = true;
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_X) {
				player.shoot = true;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				this.restartGame = true;
				if(gamestate ==  "MENU") {
					menu.enter = true;
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				gamestate = "MENU";
				menu.pause = true;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				if(gamestate == "NORMAL") {
					this.saveGame = true;
				}
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
			
		} else if(e.getKeyCode() ==  KeyEvent.VK_LEFT ||
			e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		
		// TODO Auto-generated method stub
			if (e.getKeyCode() == KeyEvent.VK_UP ||
					e.getKeyCode() == KeyEvent.VK_W) {
				player.up = false;
			} else if(e.getKeyCode() ==  KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_S) {
				player.down = false;
			}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		player.mouseShoot = true;
		player.mx = (e.getX()/3) ;
		player.my = (e.getY()/3) ;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		this.mx = e.getX();
		this.my = e.getY();
	}

}