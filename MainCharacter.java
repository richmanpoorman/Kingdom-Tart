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
import java.util.HashMap;

public class MainCharacter extends Character{
	
	public static HashMap<String, Integer> inventory = new HashMap<String, Integer>();
	public static HashMap<String, Lambda> cookBook = new HashMap<String, Lambda>();
	public static HashMap<Lambda, String> cookBookName = new HashMap<Lambda, String>();
	public Lambda[] recipes = new Lambda[4];
	public int speed = 5;
	
	public MainCharacter(String name, int x, int y, int hp, int dmg, int width, int height, String colorSpecialty) {
		super(name, x, y, width, height, hp, dmg, colorSpecialty);
		isTeam = true;
	}
	
	public void add(Fruit fruit) {
		if(inventory.containsKey(fruit.name))
			inventory.put(fruit.name,inventory.get(fruit.name)+1);
		else
			inventory.put(fruit.name,1);
	}
	
	public boolean use(String fruit) {
		if (inventory.get(fruit) !=0) {
			inventory.put(fruit,inventory.get(fruit)-1);
			return true;
		}
		return false;
	}
	
	public void addRecipe(String name, Lambda l) {
		cookBook.put(name,l);
		cookBookName.put(l, name);
	}
	
	public void setRecipes(String a, String b, String c, String d) {
		recipes[0] = (cookBook.containsKey(a))?cookBook.get(a):(e)->{};
		recipes[1] = (cookBook.containsKey(b))?cookBook.get(b):(e)->{};
		recipes[2] = (cookBook.containsKey(c))?cookBook.get(c):(e)->{};
		recipes[3] = (cookBook.containsKey(d))?cookBook.get(d):(e)->{};
	}
	/*
	public void playerUpdate(Set<Integer> pressed) {
		if(pressed != null) {
			for(Integer i : pressed) {
				
				switch(i) {
				
					case KeyEvent.VK_W:
						
						y-=speed;
						
						break;
					
					case KeyEvent.VK_A:
						
						x -=speed;
						
						break;
						
					case KeyEvent.VK_S:
						
						y+=speed;
						
						break;
					case KeyEvent.VK_D:
						x+=speed;
						break;
					default:
						
						break;
				}	
			}
		}
	}*/
}
