import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.imageio.ImageIO;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;

public class Character {
	//position
	public double x;
	public double y;
	//size
	public double width;
	public double height;
	//stats
	public int hp;
	public int dmg;
	public String name;
	//Constructor
	public Character (String name, double x, double y, double width, double height, int hp, int dmg) {
		this.x = x;
		this.y = y;
		this.hp = hp;
		this.dmg = dmg;
		this.width = width;
		this.height = height;
		this.name = name;
		
	}
	
	public void move(double dx, double dy) {
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
