import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.imageio.ImageIO;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Character {
	//position
	public int x;
	public int y;
	//size
	public int width;
	public int height;
	//stats
	public int hp;
	public int dmg;
	public String name;
	//Constructor
	public Character (String name, int x, int y, int width, int height, int hp, int dmg) {
		this.x = x;
		this.y = y;
		this.hp = hp;
		this.dmg = dmg;
		this.width = width;
		this.height = height;
		this.name = name;
		
	}
	
	public void move(int dx, int dy) {
		x += dx;
		y += dy;
	}
	
	public boolean isContact(Character character) {
		return(
				x+width >= character.x && 
				x<= character.x+character.width &&
				y+height >= character.x &&
				x<= character.y+character.height
			  );
	}

	public void interact(Character tag, Lambda l) {
		if (isContact(tag)) {
			l.interact();
		}
	}
	
	

	
}
