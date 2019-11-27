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
	
	

	
}
