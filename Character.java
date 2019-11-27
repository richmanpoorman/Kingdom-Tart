import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JPanel;


public class Character {
	//position
	public int x, tx;
	public int y, ty;
	//size
	public int width, height;
	//stats
	public int hp, maxHp, dmg;
	public String name;
	
	public String colorSpecialty;
	
	public boolean isTeam;
	
	public static HashMap<Fruit, Integer> inventory = new HashMap<Fruit, Integer>();
	public static HashMap<String, Lambda> cookBook = new HashMap<String, Lambda>();
	public static HashMap<String, Integer> cookBookCountdowns = new HashMap<>();
	public static HashMap<String, Lambda3> cookBookFilters = new HashMap<>();
	public static HashMap<String, Integer> cookBookNum = new HashMap<>();
	public static HashMap<Lambda, String> cookBookName = new HashMap<Lambda, String>();

	public HashSet<String> ownRecipes = new HashSet<>();
	
	public Lambda[] recipes = new Lambda[4];
	public int[] recipesCountDown = new int[4];
	
	
	
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
				tx + width/2 >= character.tx && 
				tx - width/2<= character.tx+character.width &&
				ty + height/2 >= character.tx &&
				ty - height/2<= character.ty+character.height
			  );
	}
	
	public boolean isContact(int ax, int ay, int w, int h) {
		return (ax - w/2<= tx + width &&
				ax + w/2 >= tx &&
				ay - h/2<= ty + height &&
				ay + h/2 >= ty);
	}

	public void interact(Character tag, Lambda l) {
		if (isContact(tag)) {
			l.interact(tag);
		}
	}
	
	public void addOwnRecipe(String s) {
		ownRecipes.add(s);
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

	
}
