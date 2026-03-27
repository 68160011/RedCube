package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;
import entity.Boss;

public class GamePanel extends JPanel implements Runnable{
	
	//screen setting
	final int originalTileSize = 16; // 16x16 
	final int scale = 3;
	public final int tileSize = originalTileSize * scale; // 48x48
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	//World Setting
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	//Game State
	public int gameState;
	public final int playState = 1;
	public final int winState = 2;
	public final int gameOverState = 3;
	
	//FPS
	int FPS = 60;
	
	public TileManager tileM = new TileManager(this);
	
	KeyHandler keyH = new KeyHandler();
	public CollisionChecker cChecker = new CollisionChecker(this);
	Thread gameThread;
	public Player player = new Player(this,keyH);
	public Boss boss;

	
	public GamePanel () {
		
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		gameState = playState;
		boss = new Boss(this);
		boss = null;
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS; // 0.01666 seconds
		double nextDrawTime = System.nanoTime() + drawInterval;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
	        long currentTime = System.nanoTime();
				 
			//1 character position
			update();
			//2 screen
			repaint();
					
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				
				
				if(remainingTime < 0) {
					remainingTime = 0;
				}

				Thread.sleep((long) remainingTime);
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			timer += (System.nanoTime() - currentTime);
			drawCount++;

			if(timer >= 1000000000) {
			    System.out.println("FPS: " + drawCount);
			    drawCount = 0;
			    timer = 0;
			}
			
			

		}
		
		
	}
	public void update() {

	    if(gameState == playState) {
	        player.update();

	        if(boss != null) {
	            boss.update();
	        }
	    }
	    if(gameState == gameOverState && keyH.enterPressed) {

	        // Player reset
	        player.worldX = tileSize * 23;
	        player.worldY = tileSize * 21;
	        player.currentHP = player.maxHP;

	        // Reload Map
	        tileM.loadMap("/maps/world01.txt");

	        // Delete boss
	        boss = null;

	        gameState = playState;
	        keyH.enterPressed = false;
	    }
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		
		tileM.draw(g2);
		
		if(gameState == winState) {
	        g2.setColor(Color.yellow);
	        g2.setFont(g2.getFont().deriveFont(60F));

	        int x = screenWidth/2 - g2.getFontMetrics().stringWidth("YOU WIN")/2;
	        int y = screenHeight/2;

	        g2.drawString("YOU WIN", x, y);
	    }

	    if(gameState == gameOverState) {
	        g2.setColor(Color.red);
	        g2.setFont(g2.getFont().deriveFont(50F));

	        int x = screenWidth/2 - g2.getFontMetrics().stringWidth("TRY AGAIN")/2;
	        int y = screenHeight/2;

	        g2.drawString("TRY AGAIN", x, y);
	    }
		
		if(boss != null) {
	        boss.draw(g2);
	    }
		
		player.draw(g2);
		
		g2.dispose();
		
		
	}
	
	

}
