/*   // Using buttons : not correct gameplay
*					if(isBetween(mousePos[0], mousePos[1], 10, 100, 400, 100)) { // If in atk block 1
*						mousePos[0] = 10; // Locks mouse x movement
*						mousePos[1] = 400; // Locks mouse y movement
*						System.out.println("ATK 1");
*						
*						
*						if(!anim.isEmpty() && anim.containsKey(party[turn].name)) { // If there is an animation
							if(anim.get(party[turn].name).isPlayed()) { // if the animation has played, go to the next character
								
	
									if(party[turn].recipes[0] != null) // If they have a function
										party[turn].recipes[0].interact(); // Do it
								
								turn++; // next person
								// Mouse reset
								mousePos[0] = 0;
								mousePos[1] = 0;
	
								
							}
						}
						else { // if there is no animation, just go to the next character
							

								if(party[turn].recipes[1] != null) // If they have a function
									party[turn].recipes[1].interact(); // Do it
							
							turn++; // next person
							// Mouse reset
							mousePos[0] = 0;
							mousePos[1] = 0;
	
						}
						
				
					
					}
					else if(isBetween(mousePos[0], mousePos[1], 120, 100, 400, 100)) { // If in atk block 2
						mousePos[0] = 120;
						mousePos[1] = 400;
						System.out.println("ATK 2");
						if(!anim.isEmpty() && anim.containsKey(party[turn].name)) {
							

								if(party[turn].recipes[3] != null) // If they have a function
									party[turn].recipes[2].interact(); // Do it
							
							if(anim.get(party[turn].name).isPlayed()) {
								turn++; // next person
								// Mouse reset
								mousePos[0] = 0;
								mousePos[1] = 0;
							}
	
						}
						else {
							

								if(party[turn].recipes[3] != null) // If they have a function
									party[turn].recipes[3].interact(); // Do it
							
							turn++; // next person
							// Mouse reset
							mousePos[0] = 0;
							mousePos[1] = 0;
	
						}
				
					
					}
					else if(isBetween(mousePos[0], mousePos[1], 230, 100, 400, 100)) { // If in atk block 3
						mousePos[0] = 230;
						mousePos[1] = 400;
						System.out.println("ATK 3");
						
						
						if(!anim.isEmpty() && anim.containsKey(party[turn].name)) {
							if(anim.get(party[turn].name).isPlayed()) {
								
	
									if(party[turn].recipes[0] != null) // If they have a function
										party[turn].recipes[0].interact(); // Do it
								
								turn++; // next person
								// Mouse reset
								mousePos[0] = 0;
								mousePos[1] = 0;
	
							}
						}
						else {  // if there is no animation, just go to the next character
							

								if(party[turn].recipes[0] != null) // If they have a function
									party[turn].recipes[0].interact(); // Do it
							
							turn++; // next person
							// Mouse reset
							mousePos[0] = 0;
							mousePos[1] = 0;
							
						}
					
					
					}
					else if(isBetween(mousePos[0], mousePos[1], 340, 100, 400, 100)) { // If in atk block 4
						mousePos[0] = 340;
						mousePos[1] = 400;
						System.out.println("ATK 4");
						
						
						if(!anim.isEmpty() && anim.containsKey(party[turn].name)) {
							if(anim.get(party[turn].name).isPlayed()) {
								
	
									if(party[turn].recipes[0] != null) // If they have a function
										party[turn].recipes[0].interact(); // Do it
								
								turn++; // next person
								// Mouse reset
								mousePos[0] = 0;
								mousePos[1] = 0;
	
							}
						}
						else {// if there is no animation, just go to the next character
							

								if(party[turn].recipes[0] != null) // If they have a function
									party[turn].recipes[0].interact(); // Do it
							
							turn++; // next person
							// Mouse reset
							mousePos[0] = 0;
							mousePos[1] = 0;
							
							
	
						}
					} */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
//import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
//import java.awt.event.KeyListener;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import javafx.scene.image.Image;

public class Main extends JPanel implements Runnable{
	public MainCharacter player;
	public MainCharacter[] party = new MainCharacter[4];
	public ArrayList<MainCharacter> teammates = new ArrayList<MainCharacter>();
	
	public int turn = 0;
	public boolean isTurn = true;
	
	public static Set<Integer> pressed;
	
	
	public Fruit[] fruitSelect = new Fruit[4];
	
	public int selected = -1;
	
	// All the fruit set up
	public Fruit[] setUp =  {
		new Fruit("Strawberry","Red","Sweet",convert("Strawberry.png")),
		new Fruit("Pineapple","Yellow","Sweet",convert("Pineapple.png")),
		new Fruit("Apple","Green","Sweet",convert("Apple.png")),
		new Fruit("Banana","Yellow","Savory",convert("Banana.png")),
		new Fruit("Blueberry","Blue","Sweet",convert("Blueberry.png")),
		new Fruit("Carrot","Orange","Savory",convert("Carrot.png")),
		new Fruit("Grapes","Purple","Savory",convert("Grapes.png")),
		new Fruit("Kiwi","Green","Sweet",convert("Kiwi.png")),
		new Fruit("Lemon","Yellow","Sour",convert("Lemon.png")),
		new Fruit("Orange","Orange","Savory",convert("Orange.png")),
		new Fruit("Pumpkin","Orange","Savory",convert("Pumpkin.png"))
	};
	
	public Tileset map;
	public Enemy[] enemy = new Enemy[4];
	public int[] mousePos = new int[2];
	public ArrayList<Enemy> enemiesInField = new ArrayList<Enemy>();
	public boolean isBattle = true;
	
	public static HashMap<String,Animation> anim = new HashMap<String, Animation>();
	
	
	
	public Main() {	
		//adds all the components to look for
		addKeyListener(new KeyListener());
		addMouseListener(new MouseListener());
		enemy[0] = new Enemy ("John Adams", 0, 0, 100, 100, 100, 100);
		player = new MainCharacter("player",100,100,10,10,10,10); // Stock Character
		player.setRecipes("", "", "", "");
		party[0] = player;
		for (int i = 0; i < 10; i++) {
			party[0].add(new Fruit("Strawberry"));
			party[0].add(new Fruit("Pineapple"));
			party[0].add(new Fruit("Apple"));
			party[0].add(new Fruit("Banana"));
			party[0].add(new Fruit("Blueberry"));
			party[0].add(new Fruit("Carrot"));
			party[0].add(new Fruit("Grapes"));
			party[0].add(new Fruit("Kiwi"));
			party[0].add(new Fruit("Lemon"));
			party[0].add(new Fruit("Orange"));
			party[0].add(new Fruit("Pumpkin"));
		}
		
		
		System.out.println(party[0].inventory.keySet());
		System.out.println(getRandomFruit(4));
		fruitSelect = getRandomFruit(4); // Fruit in random

		pressed = new HashSet<Integer>();
		setFocusable(true); // Allows the program to focus on different types of inputs
		setDoubleBuffered(false);
		
		// Starts runnable
		Thread t = new Thread(this);
		t.start(); 
		
	}

	public void paint(Graphics g) {
		
		g.clearRect(0, 0, this.getWidth(), this.getHeight()); // Clears the screen
		// Frames of different gameplay
		if (isBattle) { // If in combat (combat animations)
			
			
			for(int i = 0; i < 4; i++) { // for every slot
				if(fruitSelect[i].sprite != null) // If the fruit exists
					g.drawImage(fruitSelect[i].sprite, 10 + 110 * i, 100, 100, 100,this); // draw it
				
				if(selected >= 0) { // If one has been selected
					if(fruitSelect[i].sprite != null) // If the image exists
						g.drawImage(fruitSelect[selected].sprite, 10 + 110 * selected, 100, 100, 100,Color.RED ,this); // Give it a red background
				}
				
				
			}
			
			
			for(int i = 0; i < 4; i ++ ) {
				g.fillRect(10 + 110 * i, 400, 100, 100);
			}
			
		}
		else { // If overworld (overworld animations)
			g.fillRect(player.x,player.y,10,10);
			
		}
		
		g.fillRect(mousePos[0], mousePos[1], 10, 10); // Test: Create box at the specified mouse click
		//g.dispose(); // Supposed to clear the screen, but doesn't work
	}
	
	public void run() {
		long beforeTime = System.currentTimeMillis();
		
		while (true) {
			
			long timeDiff = System.currentTimeMillis() - beforeTime;
			beforeTime = System.currentTimeMillis();
			long sleep = 30 - timeDiff;
			gameplay(); // Runs game (behind the scenes math)
			repaint(); // Paints the frame (images)
			if (sleep < 2) {

				sleep = 2;
			}
			try {
				Thread.sleep(sleep);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void gameplay() {
		
		if (isBattle) { // Checks if in battle
			if(isTurn) { // Your turn
				if(party[turn] != null) { // if there is a party member
										
					if(isBetween(mousePos[0],mousePos[1],10,100,100,100)) {
						
						selected = 0;
						
						
					}
					else if(isBetween(mousePos[0],mousePos[1],120,100,100,100)) {
						
						selected = 1;
						
						
					}
					else if(isBetween(mousePos[0],mousePos[1],230,100,100,100)) {
						
						selected = 2;
						
						
					}
					else if(isBetween(mousePos[0],mousePos[1],340,100,100,100)) {
						
						selected = 3;
						
						
					}
					
					
					
					
					
				}
				else { // if no member, skip
					turn ++;
				}
				
				
				//If gone through all the party members
				if(turn >= party.length) {
					isTurn = false;
					
					turn = 0;
				}
				
				
				
				
			}
			else { // Enemy Turn
				isTurn = true; // Testing: player turn;
				System.out.println("Enemy");
				
			}
			
			
			// Checks enemy hp
			if(enemy == null) // Checks if the enemy have people
				isBattle = false;
			else
				for(Enemy e : enemy)
					if(e != null)
						if(e.hp <= 0)
							isBattle = false;
			if(party == null) // Checks if the party has people
				isBattle = false;
			else
				for(MainCharacter m : party)
					if(m!= null)
						if(m.hp <= 0)
							isBattle = false;
		}
		else { // Not in combat
			
			player.playerUpdate(pressed); // Momentary: creates a square where the Main Character is
			
		}
		
	}
	public boolean isBetween(int posX, int posY, int x, int w, int y, int h) {
		return (posX >= x && posX <= x + w && posY >= y && posY <= y + h);
	}
	
	/* Returns an array of random fruit from the inventory */
	public Fruit[] getRandomFruit(int numOfFruit) {
		
		Fruit[] fruitArray = new Fruit[numOfFruit]; // Array to return
		Set<String> partyKey;
		
		if(!party[turn].inventory.isEmpty()) {
			partyKey = party[turn].inventory.keySet(); // Gets all the keys in the inventory
		}
		else 
			return null;
		
		int o = 0; // counter
		
		while(o < numOfFruit) { // while the thing is not full
			int i = 0; // counter on which in the array
			String a = ""; // fruit name
			int partyKeyNum = (int)(Math.random() * partyKey.size()); // random one in bag
			for(String s : partyKey) { // For every key in the dictionary
				
				if (i == partyKeyNum) { // if it is the random one
					if(party[turn].inventory.get(s)>0) { // if you have it
						a = s; // set string to the new thing
						fruitArray[o] = Fruit.dictionary.get(a); // add to the output
						o++; // next one
						
					}
				}
				
				i++; // next fruit
			}
	
		}
		
		return fruitArray; // return array of NAMES of the fruits
			
	}
	

	public BufferedImage convert (String f) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(f));
		}
		catch(IOException e)
		{}
		return img;
	}
	
	private class KeyListener extends KeyAdapter{
		@Override
		public synchronized void keyPressed(KeyEvent e) {
			// System.out.println("Press"); // Test the Key Function
			pressed.add(e.getKeyCode());
		}

		@Override
		public synchronized void keyReleased(KeyEvent e) {
			pressed.remove(e.getKeyCode());
		}
		
	}
	
	private class MouseListener extends MouseAdapter{
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			// System.out.println("Click"); // Test the Press Function
			mousePos[0] = e.getX();
			mousePos[1] = e.getY();
		}
	
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	/* Class to return images: stores images into "animations"  */
	public class Animation{
		public int frameCount = 0; // What frame it is on
		
		public ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>(); // All the frames
		
		//Constructor
		public Animation(ArrayList<BufferedImage> frames) {
			this.frames = frames;	
		}
		
		
		public void reset () {// Returns the animation back to frame one
			frameCount = 0;
		}
		
		public BufferedImage play(boolean isLoop) { // Returns an image at each frame ( is played in run ) 
			if(frames.size() < frameCount) { // Returns all the frames before the 
				frameCount++;
				return frames.get(frameCount-1);
				
			}
			else if (isLoop) { // Checks if the animation wants to loop
				frameCount = 0;
			}
			
			return frames.get(frames.size()-1);	// Play the last frame
			
				
		}
		
		public boolean isPlayed () { // Tests if animation is finished
			return frameCount == frames.size();
		}
	}

}
