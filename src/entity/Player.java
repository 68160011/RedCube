package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	public boolean collisionOn = false;
	public java.awt.Rectangle solidArea = new java.awt.Rectangle(8, 16, 32, 32);
	public int maxHP;
	public int currentHP;
	public int damage = 10;
	
	
	public Player(GamePanel gp , KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
	
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY  = gp.screenHeight/2 - (gp.tileSize/2);
		
		
		setDefaultValues();
		getPlayerImage();
	}
	public void setDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
		maxHP = 100;
		currentHP = maxHP;
	}
	public void getPlayerImage () {
		
		
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/Up1.1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/Up1.2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/Down1.1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/Down1.2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/Left1.1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/Left1.2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/Right1.1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/Right1.2.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
			
	
	}
	public void takeDamage(int amount) {
		currentHP -= amount;

        if(currentHP < 0) {
            currentHP = 0;
        }

        System.out.println("HP: " + currentHP);

        if(currentHP == 0) {
            System.out.println("💀 Player Dead");
        }
    }

	public void update() {
		if(keyH.spacePressed) {

		    if(gp.boss != null) {
		        gp.boss.takeDamage(damage);
		        System.out.println("Boss HP: " + gp.boss.currentHP);
		    }

		    keyH.spacePressed = false; 
		}

	    if (keyH.upPressed || keyH.downPressed || 
	        keyH.leftPressed || keyH.rightPressed) {

	        if(keyH.upPressed) {
	            direction = "up";
	        }
	        else if(keyH.downPressed) {
	            direction = "down";
	        }
	        else if(keyH.leftPressed) {
	            direction = "left";
	        }
	        else if(keyH.rightPressed) {
	            direction = "right";
	        }

	        //  collision
	        collisionOn = false;
	        gp.cChecker.checkTile(this);

	        //  movement
	        if(collisionOn == false) {
	            switch(direction) {
	                case "up": worldY -= speed; break;
	                case "down": worldY += speed; break;
	                case "left": worldX -= speed; break;
	                case "right": worldX += speed; break;
	            }
	        }
	        

	        
	        int col = (worldX + gp.tileSize/2) / gp.tileSize;
	        int row = (worldY + gp.tileSize/2) / gp.tileSize;
	        
	       // System.out.println("col: " + col + " row: " + row);

	        if (col == 23 && row == 7) {
	        	System.out.println(" PORTAL HIT");
	            gp.tileM.loadMap("/maps/world02.txt");
	            
	            gp.boss = new Boss(gp);

	            worldX = gp.tileSize * 10;
	            worldY = gp.tileSize * 40;

	            return;
	        }

	        // animation
	        spriteCounter++;
	        if (spriteCounter > 10) {
	            spriteNum = (spriteNum == 1) ? 2 : 1;
	            spriteCounter = 0;
	        }
	        
	    }
	    if(currentHP <= 0) {
	        gp.gameState = gp.gameOverState;
	    }
	}
	public void draw(Graphics2D g2) {
		
		g2.setColor(Color.white);
		g2.drawString("HP: " + currentHP, 20, 20);
		
		BufferedImage image = null;
		switch(direction) {
		case "up":
			if(spriteNum == 1) {
				image = up1;
			}
			if (spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if(spriteNum == 1) {
				image = down1;
			}
			if(spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}
			if(spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if(spriteNum == 1) {
				image = right1;
			}
			if(spriteNum == 2) {
				image = right2;
			}
			break;
			
			
			
		}
		g2.drawImage(image, screenX , screenY ,gp.tileSize,gp.tileSize,null);
	
		
	}

}
