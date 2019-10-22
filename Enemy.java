import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Enemy extends Character{
	public static HashMap<String, Enemy> dictionary = new HashMap<String, Enemy>();
	public static int index = 0;
	public static HashMap<String, Lambda> cookBook = new HashMap<String, Lambda>();
	public static HashMap<Lambda, String> cookBookName = new HashMap<Lambda, String>();
	public static Lambda[] recipes = new Lambda[4];
	
	//Constructors
	public Enemy(String name, int x, int y, int hp, int dmg, int width, int height) {
		super(name, x, y, width, height, hp, dmg);
	//	this.attack = l;
		index++;
	}
	public Enemy(String name, int x, int y) {
		super(name,x,y,dictionary.get(name).width,dictionary.get(name).height,dictionary.get(name).hp,dictionary.get(name).dmg);
		index++;
	}
	//Prevent recursion
	public Enemy(String name, int x, int y, int hp, int dmg, int width, int height,boolean a) {
		super(name, x, y, width, height, hp, dmg);
		if(dictionary.containsKey(name) == false);
			dictionary.put(name, new Enemy(name, 0, 0, hp, dmg, width, height));
	}
	
	// Set recipes
	public void setRecipes(String a, String b, String c, String d) {
		recipes[0] = (cookBook.containsKey(a))?cookBook.get(a):()->{};
		recipes[1] = (cookBook.containsKey(b))?cookBook.get(b):()->{};
		recipes[2] = (cookBook.containsKey(c))?cookBook.get(c):()->{};
		recipes[3] = (cookBook.containsKey(d))?cookBook.get(d):()->{};
	}
	
	//Join Party
	public MainCharacter joinParty() {
		MainCharacter p = new MainCharacter(name,x,y,maxHp,dmg,width,height);
		for(int i = 0; i < 4; i++)
			if(recipes[i]!=null)
				p.recipes[i] = recipes[i];
			else
				p.recipes[i]= ()->{};
		return p;
	}
}