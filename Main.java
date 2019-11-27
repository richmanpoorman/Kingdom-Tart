// // // // // HELLO

import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.HashSet;

import java.util.Set;
//import java.awt.event.KeyListener;

import javax.imageio.ImageIO;
//import javax.swing.JMenuItem;
//import javax.swing.JPanel;
import javax.swing.*;

@SuppressWarnings("serial")
public class Main extends JPanel implements Runnable{
	
	//Instance Variables
	ArrayList<Button> buttonsAll = new ArrayList<Button>(); // Stores all the buttons for every
	ArrayList<Button> buttonsMenu = new ArrayList<Button>(); // Stores all the buttons for the menu
		HashSet<Button> buttonsMenuExtra = new HashSet<Button>();
	ArrayList<Button> buttonsBattle = new ArrayList<Button>(); // Stores all the buttons for the battling
	ArrayList<Button> buttonsOverworld = new ArrayList<Button>(); // Stores all the buttons for the overworld
	Button[][] recipeButtons = new Button[4][4]; // All the recipes for all of the party members
	
	Fruit[] displayFruit = new Fruit[4]; // All of the selectable fruit
	int selected = -1;
	
	ArrayList<Enemy> enemiesInField = new ArrayList<Enemy>();
	HashSet<Enemy> enemiesInBattle = new HashSet<Enemy>(); 
	
	ArrayList<MainCharacter> team = new ArrayList<MainCharacter>(); // All of the collected teammates
	MainCharacter[] battleParty = new MainCharacter[4]; // The party used in battle
	
	Set<Integer> pressed = new HashSet<Integer>(); // Stores all information about key inputs
	
	
	// Set moves menu
	JPopupMenu[] setMove = new JPopupMenu[5];
	
	int mouseX = 0, mouseY = 0; // Stores mouse position
	int xPos = 0, yPos = 0; // Stores player location in the overworld
	int turn = 0; // Turn of attacking
	boolean isBattle = false, isMenu = false; // Determines whether or not fighting/in the menu
	boolean isTurn = true; // For battles : isTurn - Whether or not the player goes
	boolean isPressedOverall = false; // Whether pressed down
	
	// At the start
	public Main() {
		
		
		
		// Allows the checking of the player input
		addMouseListener(new MouseListener());
		addKeyListener(new KeyListener());
		setFocusable(true);
		setDoubleBuffered(false);
		
		// Add menu button
		Button a = new Button("Menu",10,10,100,20);
		a.setUsage(()->isMenu = !isMenu);
		buttonsAll.add(a);
		
		//Add four blank recipe buttons
		for(int o = 0; o < 4; o++) {
			for(int i = 0; i < 4; i++) {
				Button z = new Button(i +", "+o,10 + 210*i,400,200,100,10,10);
				z.setUsage(()->System.out.println(z.name + " PRESSED")); // MOMENTARY : TEST WHETHER BUTTON IS PRESSED IN RECIPES (IF NOT SET)
				recipeButtons[o][i] = z; // Adds four sets of four blank buttons for the recipes
			}
		}
		
		
		
		
		// MOMENTARY : Add Fruit to the player inventory
		Fruit[] setUp =  {
			new Fruit("Strawberry","Red","Sweet",convert("Strawberry.png")),
			new Fruit("Pineapple","Yellow","Sweet",convert("Pinapple.png")),
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
		for(Fruit f : setUp) {
			MainCharacter.inventory.put(f, 5);
		}
		
		// Add Popup Menu for the recipe set
		
		// Add End Turn button in battle
		Button b = new Button("End Turn", 860, 400, 200, 100);
		b.setUsage(()->endTurn());
		buttonsBattle.add(b);

		// Add first character to party
		battleParty[0] = new MainCharacter("",0,0,12,12,12,12,"");
		for(int i = 0; i < 4; i++) recipeButtons[0][i].setRecipe("");
		// MOMENTARY: Adding Button Functionality
				recipeButtons[0][0].name = "Test";
				MainCharacter.addRecipe("Test",(MainCharacter)->System.out.println("YES"),12,3,(f)->(f.color.equals("Blue")||f.color.equals("Red")));
		//		recipeButtons[0][0].setNewSize(3);
				recipeButtons[0][0].setRecipe("Test");
		//		recipeButtons[0][0].setFilter((f)->f.color.equals("Blue"));
				
		
		// MOMENTARY : Adds Test Buttons/Enemies
		Button q = new Button("Set Recipes",10,110,100,100);
		q.setUsage(()->setRecipesOpen());
		buttonsMenu.add(q);
		buttonsMenu.add(new Button ("  A " , 120, 110, 100, 100, 2, 2));
		enemiesInField.add(new Enemy("John Adams", 100, 100, 100, 100,100,100,""));
		
		
		// Sets all of the moves in the cookbook to the popup menu of setting moves
		for(int i = 0; i < 5; i++) setMove[i] = new JPopupMenu();
		for(String key : MainCharacter.cookBook.keySet()) {
			JMenuItem m = new JMenuItem(key);
			for(JPopupMenu j : setMove) {
				j.add(m);
			}
		}
		
		//Starts the program
		Thread t = new Thread(this);
		t.start();
	}
	
	// Images
	public void paint(Graphics g) {
		g.clearRect(0, 0, this.getWidth(),this.getHeight()); // Clears the screen every frame
		
		// g.drawRect(10, 10, 100, 20); // Menu button // MOMENTARY
		
		// Determines whether battling or not
		if(isMenu)menuGraphics(g);
		else if(isBattle)battleGraphics(g);
		else worldExplorationGraphics(g);
		
		
		
	}
	
	// Scenes
	public void worldExploration() { // For the overworld scene
		for(Button b : buttonsOverworld) {
			b.pressed();
			if(b.isPressed) b.useUsage();
		}
		for(Button b : buttonsAll) {
			b.pressed();
			if(b.isPressed) b.useUsage();
		}
		
		
		playerMove(5); // Move the player with WASD key input at a speed of 25 pixels / frame
		
		for (int i = 0; i < enemiesInField.size(); i++) { // For every enemy...
			enemiesInField.get(i).updateLocation(xPos, yPos, this);
			
			if(enemiesInField.get(i).isContact(xPos, yPos, 50, 100)) {  // Checks if in contact with enemy
				isBattle = true; // Enter battle
				enemiesInBattle.add(enemiesInField.get(i));
			}
		}
	}
	public void battle() { // for battling scene
		for(Button b : buttonsBattle) { // Check if the universal buttons is selected
			b.pressed();
			if(b.isPressed) b.useUsage();
		}
		for(Button b : buttonsAll) { // Check if the universal buttons is selected
			b.pressed();
			if(b.isPressed) b.useUsage();
		}
		
		// Selecting fruit
		for(int i = 0; i < 4; i++) {
			if(isBetween(10 + 110*i, 100, 100, 100)) {
				selected = i;
				mouseX = 0;
				mouseY = 0;
				//displayFruit[i] = null;
			}
		}
		
		
		
		if(isTurn) { // Your turn
			
			if(turn < 4) { // While still have party members to go
				if(battleParty[turn] != null) {
					for(Button b : recipeButtons[turn]) {
						b.pressed();
						// Checks if you can use the recipe
						if(b.isPressed) {
							if(!b.allFruitIn()) {
								if(selected > -1 && displayFruit[selected] != null && b.filter(displayFruit[selected])) {
									b.addFruit(displayFruit[selected]);
									displayFruit[selected] = null;
									selected = -1;
								}
								
							}
							else if(b.isFinishCountdown()) {
								b.useRecipe(battleParty[turn]);
								b.reset();
							}
						}
					}
				} else {turn ++;} // Go to next person if person DNE
			} else { isTurn = false; turn = 0;} // If gone through all people, go to enemy turn
		}
		
		else { // Their turn
			
			
			isTurn = true;
			
			
		}
	}
	public void menu() { // for menu scene
		for(Button b : buttonsMenu) {
			b.pressed();
			if(b.isPressed) b.useUsage();
		}
		for (Button b : buttonsMenuExtra) {
			b.pressed();
			if(b.isPressed) b.useUsage();
		}
		for(Button b : buttonsAll) {
			b.pressed();
			if(b.isPressed) b.useUsage();
		}
		
		
	}
	
	// Scene Graphics
	public void worldExplorationGraphics(Graphics g) {
		for(Button b : buttonsOverworld) {
			if(b.isPressed)g.setColor(Color.RED); else g.setColor(Color.BLUE);
			g.fillRect(b.x, b.y, b.w, b.h);
			g.setColor(Color.BLACK);
			g.drawString(b.name, b.x, b.y + b.h/2);
		}
		for(Button b : buttonsAll) {
			if(b.isPressed)g.setColor(Color.RED); else g.setColor(Color.ORANGE);
			g.fillRect(b.x, b.y, b.w, b.h);
			g.setColor(Color.BLACK);
			g.drawString(b.name, b.x, b.y + b.h/2);
		}
		
		g.drawImage(convert("Main Character.png"), getWidth() / 2 - 25, getHeight() / 2 - 50, 50 , 100,  this); // Draws the player at the center of the screen
		
		for (Enemy e : enemiesInField) {
			
			g.fillRect(e.x, e.y, e.width, e.height);
		}
	}
	public void battleGraphics(Graphics g) {
		for(Button b : buttonsBattle) {
			if(b.isPressed)g.setColor(Color.RED); else g.setColor(Color.GREEN);
			g.fillRect(b.x, b.y, b.w, b.h);
			g.setColor(Color.BLACK);
			g.drawString(b.name, b.x, b.y + b.h/2);
		}
		for(Button b : buttonsAll) {
			if(b.isPressed)g.setColor(Color.RED); else g.setColor(Color.ORANGE);
			g.fillRect(b.x, b.y, b.w, b.h);
			g.setColor(Color.BLACK);
			g.drawString(b.name, b.x, b.y + b.h/2);
		}
		
		// Displays selectable fruit
		for(int i = 0; i < 4; i++) 
			if(displayFruit[i] != null)
				if (i == selected)
					g.drawImage(displayFruit[i].sprite,10 + 110*i, 100, 100, 100, Color.RED, this);
				else
					g.drawImage(displayFruit[i].sprite,10 + 110*i, 100, 100, 100, this);
		
		if(turn < 4 && isTurn) { // If your turn ... 
			for(Button b : recipeButtons[turn]) {
				if(b!=null) {
					if(b.isPressed)g.setColor(Color.RED); else g.setColor(Color.BLUE);
					g.fillRect(b.x, b.y, b.w, b.h);
					g.setColor(Color.BLACK);
					g.drawString(b.name, b.x, b.y + b.h/2);
					if(b.allFruitIn())g.drawString((b.countDown) + "", b.x, b.y + b.h/2 - 20); else g.drawString((b.fruitLeft()) + "", b.x, b.y + b.h/2 - 20);
					// Draws all the fruit in fruit collected
					for(int i = 0; i < b.fruitCollected.length; i++) {
						if(b.fruitCollected[i] != null) {
							g.drawImage(b.fruitCollected[i].sprite,b.x + 21*i, b.y + b.h/2, 20,20,this);
						}
					}
				}
			}
		}
	}
	public void menuGraphics(Graphics g) {
		for(Button b : buttonsMenu) {
			if(b.isPressed)g.setColor(Color.RED); else g.setColor(Color.BLUE);
			g.fillRect(b.x, b.y, b.w, b.h);
			g.setColor(Color.BLACK);
			g.drawString(b.name, b.x, b.y + b.h/2);
		}
		for(Button b : buttonsMenuExtra) {
			if(b.isPressed)g.setColor(Color.RED); else g.setColor(Color.BLUE);
			g.fillRect(b.x, b.y, b.w, b.h);
			g.setColor(Color.BLACK);
			g.drawString(b.name, b.x, b.y + b.h/2);
		}
		for(Button b : buttonsAll) {
			if(b.isPressed)g.setColor(Color.RED); else g.setColor(Color.ORANGE);
			g.fillRect(b.x, b.y, b.w, b.h);
			g.setColor(Color.BLACK);
			g.drawString(b.name, b.x, b.y + b.h/2);
		}
		
		
	}
	
	// Update
	public void run() {
		long beforeTime = System.currentTimeMillis();
		
		while (true) {
			
			long timeDiff = System.currentTimeMillis() - beforeTime;
			beforeTime = System.currentTimeMillis();
			long sleep = 30 - timeDiff;
			
			// Determines whether battling or not
			if(isMenu)menu();
			else if(isBattle)battle();
			else worldExploration();
			
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
	
	// Methods
	public BufferedImage convert (String f) { // Convert file name to an image
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(f));
		}
		catch(IOException e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
		return img;
	}
	public void playerMove(int speed) { // Move the player based on WASD controls and at the speed specified (speed = pixels / frame)
	//	System.out.println(xPos + " , " + yPos); // TEST: Locations
		if(pressed != null) {
			for(Integer i : pressed) {
				switch(i) {
					case KeyEvent.VK_W:
						yPos-=speed;
						break;
					case KeyEvent.VK_A:
						xPos -=speed;
						break;
					case KeyEvent.VK_S:
						yPos+=speed;
						break;
					case KeyEvent.VK_D:
						xPos+=speed;
						break;
					default:
						break;
				}	
			}
		}
	}
	public boolean isBetween(int x, int y, int w, int h) {
		return mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h;
	}
	public Lambda2 setRecipe(String name, int partyMember, int slot) {
		return () -> {
			if(MainCharacter.cookBook.get(name) != null)recipeButtons[partyMember][slot].setRecipe(name);
			if(MainCharacter.cookBookCountdowns.get(name) != null)recipeButtons[partyMember][slot].setCountDown(MainCharacter.cookBookCountdowns.get(name));
			if(MainCharacter.cookBookFilters.get(name) != null)recipeButtons[partyMember][slot].setFilter(MainCharacter.cookBookFilters.get(name));
			if(MainCharacter.cookBookNum.get(name) != null)recipeButtons[partyMember][slot].setNewSize(MainCharacter.cookBookNum.get(name));
		};
	}
	public void setRecipesOpen() { // WILL FIX
		int i = 0;
		for(int o = 0; o < 4; o++) {
			for (String name : MainCharacter.cookBook.keySet()) {
				Button b = new Button(name, 400 + 110 * o, 210 + 21 * i, 100, 20);
				b.setUsage(setRecipe(name, 0 , o));
				if(!buttonsMenuExtra.contains(b)) {
					buttonsMenuExtra.add(b);
					i++;
				}
			}
			i = 0;
		}
		
	}
	public void setRecipesGraphic() { // WILL FIX
		
		
	}
	public void endTurn() {
		turn++;
		for (int i = 0; i < 4; i++) if(displayFruit[i] == null) displayFruit[i] = MainCharacter.getRandomFruit();
		for(Button[] r : recipeButtons) for(Button b : r) b.countDown();
			
		
	}
	
	
	//Subclasses
	/* Class to store information about an animation in one place */
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
	/* Class for clickable buttons for menu/recipe */
	/* Subclass of Button : used for anything that is clicked by the player */
	public class Button {
		
		// Instance Variables
		public int x, y, w, h; // Location relative to player and dimension variable
		private int tx,ty; // Stores absolute location
		public String name = ""; // Name of button
		public boolean isPressed; // Whether or not activated
		public Fruit[] fruitCollected; // Storage of the fruit used
		
		private int  countDown, countDownInitial; // Number of fruit inputed into the button
		private Lambda recipe; // Recipe for combat
		private Lambda2 usage;
		private Lambda3 filter;
		
		// Constructors
		public Button(String name, int x, int y, int w, int h) { // For Menu/Overworld
			this.tx = x;
			this.ty = y;
			this.w = w;
			this.h = h;
			this.x = tx - xPos;
			this.y = y - yPos;
			isPressed = false;
			this.name = name;
			fruitCollected = new Fruit[0];
			
		}		

		public Button(String name, int x, int y, int w, int h, int numOfFruit, int countDown) { // For combat selection
			this(name, x,y,w,h);
			fruitCollected = new Fruit[numOfFruit];
			this.countDown = countDown;
			countDownInitial = countDown;
			
		}
		
		// Methods
		public void updateLocation() {
			x = tx - xPos + getWidth()/2;
			y = ty - yPos + getHeight()/2;
		}
		public void pressed() { // Checks if the button is pressed
			isPressed = (mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h);
		}
		public void addFruit(Fruit fruit) { 
			for(int i = 0; i < fruitCollected.length; i++) {
				if(fruitCollected[i]==null) {
					fruitCollected[i] = fruit;
					break;
				}
			}
		}
		public void resetFruit() { // Sets every value in the fruitCollected array to null ; clears out the array
			for(int i = 0; i < fruitCollected.length;i++)fruitCollected[i] = null;
		}
		
		public void setNewSize(int i) {
			fruitCollected = new Fruit[i];
		}
		
		public void useUsage() {
			
			if(isPressedOverall&&usage!=null) {
				usage.interact();
				isPressedOverall = false;
			}
		}
		public void setUsage(Lambda2 l) { // Set non battle functions
			usage = l;
		}
		public void setUsage(Lambda2 l, String name) { // Set non battle functions and names
			this.name = name;
			usage = l;
		}
	
		public void useRecipe(Character e) { // Uses recipe set
			if(recipe!=null)recipe.interact(e);
		}
		public void setRecipe(String l) { // Changes the recipe
			recipe = (MainCharacter.cookBook.containsKey(l))?MainCharacter.cookBook.get(l): (MainCharacter)->{};
			name = l;
			countDownInitial = (MainCharacter.cookBookCountdowns.containsKey(l))?MainCharacter.cookBookCountdowns.get(l): 0;
			countDown = countDownInitial;
			filter = (MainCharacter.cookBookFilters.containsKey(l))?MainCharacter.cookBookFilters.get(l): null;
			setNewSize((MainCharacter.cookBookNum.containsKey(l))?MainCharacter.cookBookNum.get(l): fruitCollected.length);
		}
		
		public void setCountDown(int n) {
			countDownInitial = n;
			countDown = n;
		}
		public boolean isFinishCountdown() { // FIX LATER ::
			return countDown == 0;
		}
		public void countDown() {
			
			if(countDown > 0 && allFruitIn()) countDown--;
		}
		public boolean allFruitIn() {
			int i = 0;
			for(; i < fruitCollected.length; i++)if(fruitCollected[i] == null) break;
			return i == fruitCollected.length;
		}
		public int fruitLeft() {
			int i = 0;
			for(; i < fruitCollected.length; i++)if(fruitCollected[i] == null) break;
			return fruitCollected.length - i ;
		}
		public void reset() {
			for(int i = 0; i < fruitCollected.length; i++) fruitCollected[i] = null;
			
			countDown = countDownInitial;

			
		}
		public void setFilter(Lambda3 l) {
			if(l != null) filter = l;
		}
		public boolean filter(Fruit f) {
			if(filter != null) return filter.interact(f);
			else return true;
		}
		
	}
	/* Checks the keyboard inputs */
	private class KeyListener extends KeyAdapter{
		@Override
		public synchronized void keyPressed(KeyEvent e) {
		//	System.out.println("Press"); // Test the Key Function
			pressed.add(e.getKeyCode());
		}

		@Override
		public synchronized void keyReleased(KeyEvent e) {
			pressed.remove(e.getKeyCode());
		}
		
	}
	/* Checks the mouse inputs */
	private class MouseListener extends MouseAdapter{
		@Override
		public void mousePressed(MouseEvent e) { // When clicked, mouseX and mouseY are set
			mouseX = e.getX();
			mouseY = e.getY();
			isPressedOverall = true;
		}
	
		@Override
		public void mouseReleased(MouseEvent e) {
			mouseX = 0;
			mouseY = 0;
			isPressedOverall = false;
		}
	}
}
