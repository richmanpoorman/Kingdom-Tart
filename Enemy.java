import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Enemy extends Character{
	public static HashMap<String, Enemy> dictionary = new HashMap<String, Enemy>();
	public static int index = 0;
	public static HashMap<String, Lambda> cookBook = new HashMap<String, Lambda>();
	public static HashMap<Lambda, String> cookBookName = new HashMap<Lambda, String>();
	public Lambda[] recipes = new Lambda[4];
	
	public static HashMap<String, Lambda> cookBookMainCharacter = new HashMap<String, Lambda>();
	
	
	
	
	//Constructors
	public Enemy(String name, int x, int y, int width, int height, int hp, int dmg, String colorSpecialty) {
		super(name, x, y, width, height, hp, dmg, colorSpecialty);
	//	this.attack = l;
		index++;
		isTeam = false;
	}
	public Enemy(String name, int x, int y) {
		super(name,x,y,dictionary.get(name).width,dictionary.get(name).height,dictionary.get(name).hp,dictionary.get(name).dmg);
		index++;
		isTeam = false;
	}
	//Prevent recursion
	public Enemy(String name, int x, int y, int hp, int dmg, int width, int height, String colorSpecialty,boolean a) {
		super(name, x, y, width, height, hp, dmg);
		isTeam = false;
		if(dictionary.containsKey(name) == false);
			dictionary.put(name, new Enemy(name, 0, 0, hp, dmg, width, height, colorSpecialty));
	}
	public void addRecipe(String name, Lambda l) {
		cookBook.put(name,l);
		cookBookName.put(l, name);
	}
	
	
	// Set recipes
	public void setRecipes(String a, String b, String c, String d) {
		recipes[0] = (cookBook.containsKey(a))?cookBook.get(a):(e)->{};
		recipes[1] = (cookBook.containsKey(b))?cookBook.get(b):(e)->{};
		recipes[2] = (cookBook.containsKey(c))?cookBook.get(c):(e)->{};
		recipes[3] = (cookBook.containsKey(d))?cookBook.get(d):(e)->{};
	}
	
	
	
	//Join Party
	public MainCharacter joinParty() {
		MainCharacter p = new MainCharacter(name,x,y,maxHp,dmg,width,height, colorSpecialty);
		this.isTeam = true;
		for(int i = 0; i < 4; i++) {
			if(recipes[i] != null) {
				p.addRecipe(cookBookName.get(recipes[i]), recipes[i]);
			//	p.recipes[i] = recipes[i];
			}
		}
		p.setRecipes((cookBookName.containsKey(recipes[0]))?cookBookName.get(recipes[0]):"", (cookBookName.containsKey(recipes[1]))?cookBookName.get(recipes[1]):"", (cookBookName.containsKey(recipes[2]))?cookBookName.get(recipes[2]):"", (cookBookName.containsKey(recipes[3]))?cookBookName.get(recipes[3]):"");
		return p;
	}
}