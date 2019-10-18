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
	
	public HashMap<String, Integer> inventory = new HashMap<String, Integer>();
	public HashMap<String, Lambda> cookBook = new HashMap<String, Lambda>();
	public Lambda[] recipes = new Lambda[4];
	public int speed = 10;
	
	public MainCharacter(String name, int x, int y, int hp, int dmg, int width, int height) {
		super(name, x, y, width, height, hp, dmg);
	}
	
	public void add(Fruit fruit) {
		inventory.put(fruit.name,inventory.get(fruit.name)+1);
	}
	
	public boolean use(Fruit fruit) {
		if (inventory.get(fruit.name) !=0) {
			inventory.put(fruit.name,inventory.get(fruit.name)-1);
			return true;
		}
		return false;
	}
	
	public void addRecipe(String name, Lambda l) {
		cookBook.put(name,l);
	}
	
	public void setRecipes(String a, String b, String c, String d) {
		recipes[0] = (cookBook.containsKey(a))?cookBook.get(a):()->{};
		recipes[1] = (cookBook.containsKey(b))?cookBook.get(b):()->{};
		recipes[2] = (cookBook.containsKey(c))?cookBook.get(c):()->{};
		recipes[3] = (cookBook.containsKey(d))?cookBook.get(d):()->{};
	}
	
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
	}
}
