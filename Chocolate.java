import java.awt.image.BufferedImage;

public class Chocolate extends Fruit{
	public String enhancement;
	
	public Chocolate(String name, Colors color, String flavor, BufferedImage sprite, String enhancement) {
		super(name, color, flavor, sprite);
		this.enhancement = enhancement;
		dictionary.remove(name);
		dictionary.put(name, new Chocolate(name,color,flavor,sprite, enhancement));
		
	}
	public Chocolate(String name, String enhancement) {
		super(name);
		this.enhancement = enhancement;
	}
}
