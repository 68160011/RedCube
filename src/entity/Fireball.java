package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.GamePanel;

public class Fireball {

    GamePanel gp;

    public int worldX, worldY;
    int speed = 6;
    String direction;
    public boolean alive = true;

    int size = 24;

    public Fireball(GamePanel gp, int x, int y, String direction) {
        this.gp = gp;
        this.worldX = x;
        this.worldY = y;
        this.direction = direction;
    }

    public void update() {

        // movement
        switch(direction) {
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
        }

        // boss hit
        if(gp.boss != null) {

            Rectangle fireRect = new Rectangle(worldX, worldY, size, size);

            Rectangle bossRect = new Rectangle(
                gp.boss.worldX,
                gp.boss.worldY,
                gp.tileSize,
                gp.tileSize
            );

            if(fireRect.intersects(bossRect)) {
                gp.boss.takeDamage(15, "fire");
                alive = false;
                System.out.println("🔥 HIT!");
            }
        }

        
        if(worldX < 0 || worldY < 0 || 
           worldX > gp.worldWidth || worldY > gp.worldHeight) {
            alive = false;
        }
    }

    public void draw(Graphics2D g2) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // fireball
        g2.setColor(Color.orange);
        g2.fillOval(screenX, screenY, size, size);
    }
}
