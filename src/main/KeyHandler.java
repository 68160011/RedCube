package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	public boolean upPressed , downPressed , leftPressed , rightPressed;
	public boolean spacePressed;
	public boolean enterPressed;
	public boolean firePressed;

	@Override
	public void keyTyped(KeyEvent e) {

		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = true;
				
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
		    spacePressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
		    enterPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_F) {
		    firePressed = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;
				
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if(code == KeyEvent.VK_ENTER) {
	        enterPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_F) {
		    firePressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
		    spacePressed = false;
		}
		
		
	}

}
