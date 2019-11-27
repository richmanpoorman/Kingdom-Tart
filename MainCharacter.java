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
	
	public static HashMap<Fruit, Integer> inventory = new HashMap<Fruit, Integer>();
	public static HashMap<String, Lambda> cookBook = new HashMap<String, Lambda>();
	public static HashMap<String, Integer> cookBookCountdowns = new HashMap<>();
	public static HashMap<String, Lambda3> cookBookFilters = new HashMap<>();
	public static HashMap<String, Integer> cookBookNum = new HashMap<>();
	public static HashMap<Lambda, String> cookBookName = new HashMap<Lambda, String>();

	public Lambda[] recipes = new Lambda[4];
	public int[] recipesCountDown = new int[4];
	public int speed = 5;
	
	public MainCharacter(String name, int x, int y, int hp, int dmg, int width, int height, String colorSpecialty) {
		super(name, x, y, width, height, hp, dmg, colorSpecialty);
		isTeam = true;
	}
	
	public void add(Fruit fruit) {
		if(inventory.containsKey(fruit))
			inventory.put(fruit,inventory.get(fruit.name)+1);
		else
			inventory.put(fruit,1);
	}
	
	public static Fruit use(Fruit fruit) {
		if (inventory.containsKey(fruit)&&inventory.get(fruit) !=0) {
			inventory.put(fruit,inventory.get(fruit)-1);
			return fruit;
		}
		return null;
	}
	
	public static Fruit getRandomFruit() {
		Set<Fruit> fruitsInInventory = inventory.keySet();
		Fruit returnFruit = null;
		while(returnFruit == null) {
			int a = (int)(Math.random()*inventory.size());
			int i = 0;
			for(Fruit fruit : fruitsInInventory) {
				if(!inventory.get(fruit).equals(0) && i == a) {
					returnFruit = fruit;
				}
				i++;
			}
		}
		return returnFruit;
	}
	
	public static void addRecipe(String name, Lambda l, int c) {
		cookBook.put(name,l);
		cookBookCountdowns.put(name,c);
		cookBookName.put(l, name);
	}
	
	public static void addRecipe(String name, Lambda l, int c, int num) {
		cookBook.put(name,l);
		cookBookCountdowns.put(name,c);
		cookBookName.put(l, name);
		cookBookNum.put(name,num);
	}
	
	public static void addRecipe(String name, Lambda l, int c, Lambda3 filter) {
		cookBook.put(name,l);
		cookBookCountdowns.put(name,c);
		cookBookName.put(l, name);
		cookBookFilters.put(name,filter);
	}
	
	public static void addRecipe(String name, Lambda l, int c, int num ,Lambda3 filter) {
		cookBook.put(name,l);
		cookBookCountdowns.put(name,c);
		cookBookName.put(l, name);
		cookBookFilters.put(name,filter);
		cookBookNum.put(name,num);
	}
	public void setRecipes(String a, String b,   String c,  String d) {
		recipes[0] = (cookBook.containsKey(a))?cookBook.get(a):(z)->{};
		recipesCountDown[0] = (cookBookCountdowns.containsKey(a))?cookBookCountdowns.get(a):0;
		
		recipes[1] = (cookBook.containsKey(b))?cookBook.get(b):(z)->{};
		recipesCountDown[1] = (cookBookCountdowns.containsKey(b))?cookBookCountdowns.get(b):0;
		
		recipes[2] = (cookBook.containsKey(c))?cookBook.get(c):(z)->{};
		recipesCountDown[2] = (cookBookCountdowns.containsKey(c))?cookBookCountdowns.get(c):0;
		
		recipes[3] = (cookBook.containsKey(d))?cookBook.get(d):(z)->{};
		recipesCountDown[3] = (cookBookCountdowns.containsKey(d))?cookBookCountdowns.get(d):0;
	}
	
	public String recipeName(int i) {
		if(i < 4 && i >= 0 && cookBookName.containsKey(recipes[i])) return cookBookName.get(recipes[i]);
		return "";
	}
}
