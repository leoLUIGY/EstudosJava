
package com.glorhalla.main;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
//import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
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
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.glorhala.world.World;
import com.glorhalla.entities.BulletShoot;
import com.glorhalla.entities.Enemy;
import com.glorhalla.entities.Entity;
import com.glorhalla.entities.Npc;
import com.glorhalla.entities.Player;
import com.glorhalla.graficos.Spritesheet;
import com.glorhalla.graficos.UI;
import java.awt.image.DataBufferInt;


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
	public static Npc npc;
	
	public static Random rand;
	
	public UI ui;
	
	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("pixelart.ttf");
	public Font newfont;
	
	public int xx, yy;
	public static String gamestate = "MENU";
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;
	private boolean restartGame = false;
	
	public Menu menu;
	
	public boolean saveGame = false;
	
	public int[] pixels;
	public BufferedImage lightmap;
	public int[] lightMapPixels;
	
	public int mx, my;
	public static BufferedImage minimap;
	public static int[] minimapPixels;

	public BufferedImage sprite1;
	public BufferedImage sprite2;
	
	public int[] pixels1;
	public int[] pixels2;
	
	public int x1 = 30, y1 = 90;
	public int x2 = 100, y2 = 100;
	
	
	public static int entrada =1;
	public static int comecar = 2;
	public static int jogando = 3;
	public static int estado_cena = entrada;
	public int timeCena = 0, maxTimeCena = 60*3;
	public Game()  {
	//	Sound.musicBackground.loop();
		rand  = new Random();
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		//setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT*SCALE));
		/*try {
			sprite1 = ImageIO.read(getClass().getResource("/a.png"));
			sprite2 = ImageIO.read(getClass().getResource("/b.png"));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		pixels1 = new int[sprite1.getWidth() * sprite1.getHeight()];
		sprite1.getRGB(0, 0, sprite1.getWidth(), sprite1.getHeight(), pixels1,0, sprite1.getWidth());
		
		pixels2 = new int[sprite2.getWidth() * sprite2.getHeight()];
		sprite2.getRGB(0, 0, sprite2.getWidth(), sprite2.getHeight(), pixels2,0, sprite2.getWidth());
		*/
		initFrame();
		
		ui = new UI();
		image = new BufferedImage(WIDTH , HEIGHT, BufferedImage.TYPE_INT_RGB);
		try {
			lightmap = ImageIO.read(getClass().getResource("/lightmap.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		lightMapPixels = new int[lightmap.getWidth() * lightmap.getHeight()];
		lightmap.getRGB(0,  0, lightmap.getWidth(), lightmap.getHeight(), lightMapPixels, 0, lightmap.getWidth());
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		entities =  new ArrayList<Entity>();
		enemies =  new ArrayList<Enemy>();
		bullets = new ArrayList<BulletShoot>();
		
		
		spritesheet = new Spritesheet("/spriteSheet.png");
		player = new Player(30, 20, 16, 16, spritesheet.getSprite(32, 0, 16, 16));
		world  = new World("/level1.png");
		
		npc = new Npc(100, 20, 16, 16, spritesheet.getSprite(32,32, 16,16));
		minimap = new BufferedImage(World.WIDTH, World.HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		entities.add(player);
		entities.add(npc);
		menu = new Menu();
		try {
			newfont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(16f);
			minimapPixels = ((DataBufferInt)minimap.getRaster().getDataBuffer()).getData();
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
			//frame.setUndecorated(true);
			frame.setResizable(false);
			frame.pack();
			Image imagem = null;
			try {
				imagem = ImageIO.read(getClass().getResource("/outra.png"));
			}catch(IOException e) {
				e.printStackTrace();
			}
			//Toolkit toolkit = Toolkit.getDefaultToolkit();
			//Image image = toolkit.getImage(getClass().getResource("/outra.png"));
			//Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "ing");
			
			//frame.setCursor(c);
			frame.setIconImage(imagem);
			frame.setAlwaysOnTop(true);
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
		
		//if(this.isColliding(x1, y1, x2, y2, pixels1, pixels2, sprite1, sprite2)) {
			
		//}
		if(gamestate == "NORMAL") {
		//	xx++;
			if(this.saveGame) {
				this.saveGame = false;
				String[] opt1 = {"level"};
				int[] opt2 = {this.CUR_LEVEL};
				Menu.saveGame(opt1, opt2, 10);
			}
			this.restartGame = false;
			
		if(Game.estado_cena== Game.jogando) {
		for(int i = 0; i< entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}
		
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).tick();
		}
		}else {
			if(Game.estado_cena== Game.entrada) {
				if(player.getX()< 100) {
					player.x++;
				} else {
					Game.estado_cena = Game.comecar;
				}
			} else if(Game.estado_cena == Game.comecar) {
				timeCena++;
				if(timeCena == maxTimeCena) {
					Game.estado_cena = Game.jogando;
				}
			}
			
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
		player.updateCamera();
	menu.tick();
	}
}
	
	public void drawRactangleExample(int xoff, int yoff) {
		for(int xx = 0; xx<32;xx++) {
			for(int yy = 0; yy< 32; yy++) {
				int xOff = xx + xoff;
				int yOff =  yy + yoff;
				if(xOff < 0 || yOff < 0 || xOff >= WIDTH || yOff >= HEIGHT)
					continue;
				pixels[xOff +(yOff * WIDTH)] = 0Xff0000;
			}
		}
	}
	
	public void applyLight() {
		for (int xx = 0; xx<Game.WIDTH;xx++) {
			for (int yy = 0; yy < Game.HEIGHT; yy++) {
				if(lightMapPixels[xx + (yy * Game.WIDTH)] == 0xffffffff) {
					pixels[xx+(yy*Game.WIDTH)] = 0;
				} 
			}
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
		Collections.sort(entities, Entity.nodeSorter);
		for(int i = 0; i< entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}
		
		
		applyLight();
		ui.render(g);
		
		//g.drawImage(sprite1, x1, y1, null);
		//g.drawImage(sprite2, x2, y2, null);
		
		
		g.dispose();
		g = bs.getDrawGraphics();
		//drawRactangleExample(xx,yy);
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
		//&& CUR_LEVEL == 1
		if(gamestate == "NORMAL"&& CUR_LEVEL == 1 ) {
		World.renderMiniMap();
		g.drawImage(minimap, 615,350, World.WIDTH*5, World.HEIGHT*5,null);
		}
		
		if(Game.estado_cena == Game.comecar) {
			g.setFont(new Font("arial", Font.BOLD,20));
			g.drawString("Vamos começar Bicho bruto",(WIDTH*SCALE)/2 - 80, (HEIGHT*SCALE)/2 - 20);
		}
		//Graphics2D g2 = (Graphics2D) g;
		//double angleMouse = Math.atan2(my - 200+25, mx -200 + 25);
		//g2.rotate(angleMouse, 200+25, 200+25);
		//g.setColor(Color.red);
		//g.fillRect(200,200,50,50);
		bs.show();
	}
	
	public boolean isColliding(int x1,int y1, int x2,int y2, int[] pixels1, int[] pixels2, BufferedImage sprite1, BufferedImage sprite2) {
		for(int xx1 = 0; xx1 < sprite1.getWidth(); xx1++) {
			for(int yy1 = 0; yy1 < sprite1.getHeight();yy1++) {
				for(int xx2 = 0; xx2 < sprite2.getWidth(); xx2++) {
					for(int yy2 = 0; yy2 < sprite2.getHeight();yy2++) {
						int pixelAtual1 = pixels1[xx1+yy1*sprite1.getWidth()];
						int pixelAtual2 = pixels2[xx2+yy2*sprite2.getWidth()];
						if(pixelAtual1 == 0x00FFFFFF || pixelAtual2 == 0x00FFFFFF) {
							continue;
						}
						if(xx1 + x1 == xx2+ x2 && yy1+ y1 == yy2+y2) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public void run() {
		requestFocus();
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
				
				if(npc.showMessage) {
					npc.showMessage = false;
					Npc.atacar = true;
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
