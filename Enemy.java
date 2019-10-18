import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Enemy extends Character{
	public Lambda attack;
	public static HashMap<String, Enemy> dictionary = new HashMap<String, Enemy>();
	
	//Constructors
	public Enemy(String name, int x, int y, int hp, int dmg, int width, int height) {
		super(name, x, y, width, height, hp, dmg);
		if(!dictionary.containsKey(name))
			dictionary.put(name, new Enemy(name, 0, 0, hp, dmg, width, height));
	}
	public Enemy(String name, int x, int y) {
		super(name,x,y,dictionary.get(name).width,dictionary.get(name).height,dictionary.get(name).hp,dictionary.get(name).dmg);
	}
	
	
	//Attack Pattern
	public void battleAttack(Lambda l) {
		attack = l;
	}
	
	//Join Party
	public MainCharacter joinParty() {
		return new MainCharacter(name,x,y,hp,dmg,width,height);
	}
}