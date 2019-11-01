import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Character {
	//position
	public int x, tx;
	public int y, ty;
	//size
	public int width, height;
	//stats
	public int hp;
	public int dmg;
	public String name;
	public int maxHp;
	
	public String colorSpecialty;
	
	public boolean isTeam;
	
	//Constructor
		// General
	public Character (String name, int x, int y, int width, int height, int hp, int dmg) {
		this.x = x;
		this.y = y;
		this.hp = hp;
		this.dmg = dmg;
		this.width = width;
		this.height = height;
		this.name = name;
		this.maxHp = hp;
		ty = y;
		tx = x;
	}
		// For enemy/player
	public Character (String name, int x, int y, int width, int height, int hp, int dmg, String colorSpecialty) {
		this(name, x, y, width, height, hp, dmg);
		this.colorSpecialty = colorSpecialty;
	}
	
	
	public void move(int dx, int dy) {
		tx += dx;
		ty += dy;
	}
	
	public void updateLocation(int posX, int posY, JPanel observer) {
		x = tx - posX + (observer.getWidth()/2);
		y = ty - posY + (observer.getHeight()/2);
	}
	
	public boolean isContact(Character character) {
		return(
				tx+width >= character.tx && 
				tx<= character.tx+character.width &&
				ty+height >= character.tx &&
				tx<= character.ty+character.height
			  );
	}
	
	public boolean isContact(int ax, int ay, int w, int h) {
		return (ax <= tx + width &&
				ax + w >= tx &&
				ay <= ty + height &&
				ay + h >= ty);
	}

	public void interact(Character tag, Lambda l) {
		if (isContact(tag)) {
			l.interact(tag);
		}
	}
	
	

	
}
