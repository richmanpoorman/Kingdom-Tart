
import java.awt.image.BufferedImage;

import java.util.HashMap;

public class Fruit {
	public String name, flavor;
	public Colors color;
	public BufferedImage sprite;
	public static HashMap<String, Fruit> dictionary = new HashMap<String, Fruit>();
	
	public Fruit(String name, Colors color, String flavor, BufferedImage sprite) {
		this(name, color, flavor, sprite, true);
		if(!dictionary.containsKey(name)) dictionary.put(name, new Fruit(name,color,flavor,sprite,true));
	}
	public Fruit(String name, Colors color, String flavor, BufferedImage sprite, boolean a) {
		this.name = name;
		this.color = color;
		this.flavor = flavor; 
		this.sprite = sprite;
		
	}
	
	public Fruit(String name) {
		this.name = name;
		if (dictionary.containsKey(name)) {
			this.color = dictionary.get(name).color;
			this.flavor = dictionary.get(name).flavor;
			this.sprite = dictionary.get(name).sprite;
		}
		else {
			System.out.println(dictionary.keySet());
		}
	}
	
	
	
}
