package entity;

import java.awt.image.BufferedImage;

public class Entity {
	
	public int worldX, worldY;
	public int speed;
	
	
	public BufferedImage up1 ,up2 , down1 , down2 , left1 , left2 , right1 , right2;
	public String direction;
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public int maxHP;
	public int currentHP;
	public void takeDamage(int amount) {
	    currentHP -= amount;

	    if(currentHP < 0) {
	        currentHP = 0;
	    }
	}
	public void takeDamage(int amount, String type) {

	    if(type.equals("fire")) {
	        amount += 5;
	    }

	    currentHP -= amount;

	    if(currentHP < 0) {
	        currentHP = 0;
	    }
	}
	
	

}
