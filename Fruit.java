import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.imageio.ImageIO;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;

public class Fruit {
	public String name;
	public String color;
	public String flavor;
	public BufferedImage sprite;
	public static HashMap<String, Fruit> dictionary = new HashMap<String, Fruit>();
	
	public Fruit(String name, String color, String flavor, BufferedImage sprite) {
		this(name, color, flavor, sprite, true);
		if(!dictionary.containsKey(name)) {
			dictionary.put(name, new Fruit(name,color,flavor,sprite,true));
			
		}
	}
	public Fruit(String name, String color, String flavor, BufferedImage sprite, boolean a) {
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
