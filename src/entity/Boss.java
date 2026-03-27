package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;

public class Boss extends Entity {

    GamePanel gp;

    int attackCooldown = 0;
    int attackInterval = 60;

    public Boss(GamePanel gp) {
        this.gp = gp;
        
        worldX = gp.tileSize * 20;
        worldY = gp.tileSize * 20;

        speed = 1;

        maxHP = 200;
        currentHP = maxHP;
    }
    public void update() {

        
        if(gp.player.worldX < worldX) {
            worldX -= speed;
        }
        if(gp.player.worldX > worldX) {
            worldX += speed;
        }
        if(gp.player.worldY < worldY) {
            worldY -= speed;
        }
        if(gp.player.worldY > worldY) {
            worldY += speed;
        }

        
        int dx = Math.abs(gp.player.worldX - worldX);
        int dy = Math.abs(gp.player.worldY - worldY);

        if(attackCooldown > 0) {
            attackCooldown--;
        }

        if(dx < gp.tileSize && dy < gp.tileSize) {

            if(attackCooldown == 0) {
                gp.player.takeDamage(15); 

                attackCooldown = attackInterval; 
            }
        }
        if(currentHP <= 0) {
            gp.gameState = gp.winState;
        }
        }
 

    public void draw(Graphics2D g2) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.setColor(Color.red);
        g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);

        // HP bar
        g2.setColor(Color.white);
        g2.drawString("Boss HP: " + currentHP, screenX, screenY - 10);
    }
}
