
package com.glorhalla.main;


import java.awt.Canvas;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
//import java.awt.Font;
import java.awt.Graphics;



import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
//import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



import javax.swing.JFrame;

import com.glorhala.world.TuboGenerator;
import com.glorhala.world.World;
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
	
	private BufferedImage image;
	
	
	public static List<Entity> entities;
	
	public static Spritesheet spritesheet;
	
	public static Player player;

	public static String state = "NORMAL";
	
	public static TuboGenerator tubogenerator;
	
	public UI ui;
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;
	public static double score = 0;
	public Game()  {

		
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT*SCALE));
		
		initFrame();

		image = new BufferedImage(WIDTH , HEIGHT, BufferedImage.TYPE_INT_RGB);
		entities =  new ArrayList<Entity>();
		spritesheet = new Spritesheet("/spritesheet.png");
		tubogenerator = new TuboGenerator();
		player = new Player(WIDTH/2-30, HEIGHT/2, 16, 16,2, spritesheet.getSprite(0, 0, 16, 16));
	
		
		ui = new UI();
		
	
		
		
		
		
		
		
		entities.add(player);
		
		
	}
	
	public void initFrame() {
			frame = new JFrame("Flappy Bird");
			frame.add(this);
			//frame.setUndecorated(true);
			frame.setResizable(false);
			frame.pack();
			
			///frame.setAlwaysOnTop(true);
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
		tubogenerator.tick();
		if(state == "NORMAL") {
		for(int i = 0; i< entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
			}
		
		}
		if(state == "PERDEU") {
			this.framesGameOver++;
			if(this.framesGameOver == 30) {
				this.framesGameOver = 0;
				if(this.showMessageGameOver) {
					this.showMessageGameOver = false;
				} else {
					this.showMessageGameOver = true;
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
		g.setColor(new Color(122, 102, 255));
		g.fillRect(0, 0, WIDTH , HEIGHT);
		
		
		//Graphics2D g2 = (Graphics2D) g;
		Collections.sort(entities, Entity.nodeSorter);
		
		for(int i = 0; i< entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
	
		

		g.dispose();
		g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, WIDTH*SCALE,HEIGHT*SCALE,null);
		ui.render(g);
		if(Game.state == "PERDEU") {
			g.setColor(Color.YELLOW);
			g.setFont(new Font("arial", Font.BOLD, 30));
			if(showMessageGameOver) {
			g.drawString(">pressione enter para reiniciar<", Game.WIDTH/2 - 80,Game.HEIGHT/2+150);
			}
			}
		
		
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
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.isPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(state == "PERDEU") {
				World.restartGame();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.isPressed = false;
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
	
	}

}
