// // // // // HELLO

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
import java.util.Iterator;
import java.util.Set;
//import java.awt.event.KeyListener;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import javafx.scene.image.Image;

/*
public class Main extends JPanel implements Runnable{
	//player
	public MainCharacter player;
	
	// party in battle
	public MainCharacter[] party = new MainCharacter[4];
	
	// team (in background)
	public ArrayList<MainCharacter> teammates = new ArrayList<MainCharacter>();
	
	// Whos turn in the party it is
	public int turn = 0;
	public int turnEnemy = 0;
	
	//Enemy turn counter
	public int enemyTurn = 0;
	
	// Checks turn
	public boolean isTurn = true;
	
	// Key inputs
	public static Set<Integer> pressed;
	
	// Turn counter
	public int[][] countDown = new int[4][4];
	public int[][] countDownEnemy = new int[4][4];
	
	//Fruits to use
	public Fruit[] fruitSelect = new Fruit[4];
	public Fruit[] fruitSelectEnemy = new Fruit[4];
	
	// Choice of Fruit
	public int selected = -1;
	
	// Choice of recipe
	public int recipePlace = -1;
	
	// Store fruit chosen
	//private Fruit fruitIn; // NEVER USED
	
	// All the fruit set up
	public Fruit[] setUp =  {
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
	// Map
	public Tileset map;
	
	// enemy attack party
	public Enemy[] enemy = new Enemy[4];
	
	// attacking which enemy
	public int target;
	
	// mouse pos when clicked
	public int[] mousePos = new int[2];
	
	//Enemies in the overworld
	public ArrayList<Enemy> enemiesInField = new ArrayList<Enemy>();
	
	// If in battle
	public boolean isBattle = false;
	
	// All the animations
	public static HashMap<String,Animation> anim = new HashMap<String, Animation>();
	
	// isEnemy but better
	public boolean isEnemy = true;
	
	// Thread
	Thread t = new Thread(this);
	
	public Main() {	
		//adds all the components to look for
		addKeyListener(new KeyListener());
		addMouseListener(new MouseListener());
		
		// Reset the counters
		for(int i = 0; i < 4; i++) {
			for (int o = 0; o < 4; o++) {
				countDown[o][i] = 0;
				countDownEnemy[o][i] = 0;
			}
		}	
		isEnemy = false;
		enemy[0] = new Enemy ("John Adams", 0, 0, 100, 1, 10, 10, true); // Stock enemy
		enemy[0].addRecipe("XYZ Affair", 
				attackSetUp(3, ()->
						setEnemyParty(isEnemy)[0].hp -= setEnemyParty(!isEnemy)[0].dmg
				, isEnemy)
		);
		
		enemy[0].addRecipe("Alien and Sedition Acts", 
				attackSetUp(5, ()->{setEnemyParty(isEnemy)[0].hp-=setEnemyParty(!isEnemy)[0].dmg * 2;}
				, isEnemy)
		);
		
		player = new MainCharacter("player",100,100,10,10,50,100); // Stock Character
		
		// Adds Test Recipe
		player.addRecipe
		("Test", 
					
				attackSetUp(3, () -> {
					enemy[target].hp -= party[turn].dmg;
				}
				, true)
			
		);
		
		// Adds Bake Recipe
		player.addRecipe
		("Bake",
				
				attackSetUp(10, () -> {
					enemy[target].hp -= party[turn].dmg * 3;
				}
				, true)
		);
		
		
		player.addRecipe("Midnight Snack", 
				attackSetUp(10 , 
						() -> {
							int heal = 10;
							if(party[turn].hp+ heal < party[turn].maxHp)
								party[turn].hp+= 10;
							else
								party[turn].hp = party[turn].maxHp;
							
						}
				, true)
			
				
				
		);
		
		player.setRecipes("Test", "Bake", "Midnight Snack", ""); // Recipe setter

		party[0] = player; // Set player to the party
		
		
		// Adds all the fruits 
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
		//Thread t = new Thread(this);
		t.start(); 
		
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
		if(party[0].hp > 0) {
			if (isBattle) { // Checks if in battle
				if(isTurn) { // Your turn
			 		if(party[turn] != null) { // if there is a party member
						//Party turn
			 			isEnemy = true;
			 			
			 			//Fruit selector
						if(isBetween(mousePos[0],mousePos[1],10,100,100,100)) { // Fruit in slot a
							selected = 0;	
						//	fruitIn = fruitSelect[selected];
						}
						else if(isBetween(mousePos[0],mousePos[1],120,100,100,100)) { // Fruit in slot b
							selected = 1;
						//	fruitIn = fruitSelect[selected];
						}
						else if(isBetween(mousePos[0],mousePos[1],230,100,100,100)) { // Fruit in slot c
							selected = 2;
						//	fruitIn = fruitSelect[selected];
						}
						else if(isBetween(mousePos[0],mousePos[1],340,100,100,100)) { // Fruit in slot d
							selected = 3;
						//	fruitIn = fruitSelect[selected];
						}
						
						
						if(selected >= 0) { // If something is selected
							if(isBetween(mousePos[0],mousePos[1],10,100,400,100)) { // Fruit in slot a
								if(fruitSelect[selected] != null) { // If fruit exists
									// Choice recipe
									recipePlace = 0;
									
									// Which recipe
									party[turn].recipes[0].interact();
	
									// MOMENTARY : Removes fruit select when used
									//fruitSelect[selected] = null;
	
									//Reset the selection of fruit and recipe spot
									selected = -1;
									recipePlace = -1;
								}
							}
							else if(isBetween(mousePos[0],mousePos[1],120,100,400,100)) { // Fruit in slot b
	
								if(fruitSelect[selected] != null) {
									recipePlace = 1;
									party[turn].recipes[1].interact();
								//	fruitSelect[selected] = null;
									selected = -1;
									recipePlace = -1;
								}
								
							}
							else if(isBetween(mousePos[0],mousePos[1],230,100,400,100)) { // Fruit in slot c
								
								if(fruitSelect[selected] != null) {
									
									
									recipePlace = 2;
									party[turn].recipes[2].interact();
								//	fruitSelect[selected] = null;
									selected = -1;
									recipePlace = -1;
								}
								
							}
							else if(isBetween(mousePos[0],mousePos[1],340,100,400,100)) { // Fruit in slot d
	
								if(fruitSelect[selected] != null) {
									recipePlace = 3;
									party[turn].recipes[3].interact();
								//	fruitSelect[selected] = null;
									selected = -1;
									recipePlace = -1;
								}
								
							}
							
							
	
							
						}
						
						if(isBetween(mousePos[0],mousePos[1],10,100,250,100)) { // Choose end turn
							mousePos[0]=0;
							mousePos[1]=1;
							if(turn < party.length && !party[turn].inventory.isEmpty())
								fruitSelect = getRandomFruit(4);
							turn++;
							
							// Countdown for each turn
							for (int i = 0; i < 4; i++)
								for (int o = 0; o < 4; o++)
									if(countDown[o][i]>0)
										countDown[o][i]--;
							
							for (int i = 0; i < 4; i++)
								for (int o = 0; o < 4; o++)
									if(countDownEnemy[o][i]>0)
										countDownEnemy[o][i]--;
						}
						
					}
					else { // if no member, skip
						turn ++;
					}
					
					
					//If gone through all the party members
					if(turn >= party.length) {
						isTurn = false;
						recipePlace = -1;
						selected = -1;
						turn = 0;
					}
					
					
					
				}
				else { // Enemy Turn
					// isEnemy false : enemy is person
					isEnemy = false;
					
					for(Enemy e : enemy) {
						if(e != null && e.recipes[0] != null)
							e.recipes[0].interact();
					}
	
					isTurn = true; // Testing: player turn;
				//	System.out.println("Enemy");
				//	System.out.println(enemy[0].hp);
					
				}
				
				
				// Checks enemy hp
				int i = 0;
				if(enemy == null) // Checks if the enemy have people
					isBattle = false;
				else
					for(Enemy e : enemy) { 
						if(e != null) { // If the enemy exists
							if(e.hp <= 0) {	// and it is dead
								i++;
							}
						}
						else // if the enemy does not exist
							i++; 
					}
				
				
				if(i == enemy.length) {
					isBattle = false;
					for(Enemy e : enemy) { // Chance to 
						int rand = (int)(Math.random()*2);
						
						// 50 % chance to join party
						if(rand == 1) {
							int p = 0;
							for(;p<party.length;p++) {
								if(party[p] == null) {
									isEnemy = true;
									if(e!= null)
										party[p] = e.joinParty();
									break;
								}

							}
							if(p == party.length)
								if(e!= null)
									teammates.add(e.joinParty());

						}
					}
				}
				
				int o = 0;
				if(party == null) // Checks if the party has people
					isBattle = false;
				else
					for(MainCharacter m : party) {
						if(m!= null) {
							if(m.hp <= 0) {
								o++;
							}
						}
						else
							o++;
					}
				if(o == party.length) 
					isBattle = false;
			
				
			}
			
			
			 * 
			 * NOT IN COMBAT
			 * 
			 * 
			
			else { // Not in combat
				
				player.playerUpdate(pressed); // Momentary: creates a square where the Main Character is
				/* TEST: Make enemies 
				int spawnChance = (int)(Math.random()*100);
				
				if(spawnChance <= 3) {
					Enemy e = new Enemy("John Adams", (int)(Math.random()*(this.getWidth()-10)),(int)(Math.random()*(this.getHeight()-10) ));
					e.setRecipes("XYZ Affair", "Alien and Sedition Acts", "", "");
					enemiesInField.add(e);
					
				}
				
				
				for(Enemy e : enemiesInField) { // Check every enemy
					// If contact
					if(isBetween(e.x,e.y,player.x,player.width,player.y,player.height)||isBetween(e.x+e.width,e.y+e.height,player.x,player.width,player.y,player.height)) {
						isBattle = true; // Enter battle
						enemy[0] = e; // Add it to the attack party
						enemiesInField.remove(e); // Get rid of the enemy
						fruitSelect = getRandomFruit(4); // Fruit in random
						
						// Reset the counters
						for(int i = 0; i < 4; i++) {
							for (int o = 0; o < 4; o++) {
								countDownEnemy[o][i] = 0;
								countDownEnemy[o][i] = 0;
							}
						}	
						
						break; // Stop looking
					}
				}
				
			}
		}
		else { // If dead
			if (isBetween(mousePos[0],mousePos[1],100,100,100,100)) {
				Thread a = new Thread(this);
				party[0].hp = party[0].maxHp;
				a.start();
				System.out.println("Oh no");
				mousePos[0] = 0;
				mousePos[1] = 1;
			}
		}
	}
	
	public void paint(Graphics g) {
		g.clearRect(0, 0, this.getWidth(), this.getHeight()); // Clears the screen
		if(party[0].hp > 0) {
			
			// Frames of different gameplay
			if (isBattle) { // If in combat (combat animations)
				//Player HP Bar
				g.fillRect(500, 300, 100, 20);
				g.setColor(Color.RED);
				if(turn < party.length)
					g.fillRect(500, 300, party[turn].hp*100/party[turn].maxHp, 20);
				g.setColor(Color.BLACK);
				
				//Enemy HP Bar
				g.fillRect(500, 350, 100, 20);
				g.setColor(Color.RED);
				if(enemyTurn < enemy.length) {
					g.drawString(enemy[enemyTurn].name,500, 340);
					g.fillRect(500, 350, enemy[enemyTurn].hp*100/enemy[enemyTurn].maxHp, 20);
				}
				g.setColor(Color.BLACK);
				
				//Party members
				for(int i = 0; i < 4; i++)
					if(party[i] != null)
						g.drawString(party[i].name, 450, 40 + i * 11);
					
				
				
				for(int i = 0; i < 4; i++) { // for every slot
					if(fruitSelect[i]!=null&&fruitSelect[i].sprite != null) // If the fruit exists
						g.drawImage(fruitSelect[i].sprite, 10 + 110 * i, 100, 100, 100,this); // draw it
					
					if(selected >= 0) { // If one has been selected
						if(fruitSelect[i]!=null&&fruitSelect[i].sprite != null) // If the image exists
							g.drawImage(fruitSelect[selected].sprite, 10 + 110 * selected, 100, 100, 100,Color.RED ,this); // Give it a red background
					}
					
					
				}
				
				
				for(int i = 0; i < 4; i ++ ) {
					// Recipes
					g.setColor(Color.GRAY);
					
					g.fillRect(10 + 110 * i, 400, 100, 100);
					g.setColor(Color.BLACK);
					if(turn < party.length) {
						// draws the name of it on the block, none if there is no recipe
						g.drawString((turn < party.length&&!party[turn].cookBookName.isEmpty()&&party[turn].cookBookName.containsKey(party[turn].recipes[i])&& party[turn].cookBookName.get(party[turn].recipes[i]) !=null)?party[turn].cookBookName.get(party[turn].recipes[i]):"NONE", 30 + 110 * i, 450);
						if (party[turn]!=null)
							g.drawString((countDown[turn][i])+"", 30 + 110 * i, 475);
					}
	
				}
				
				// End turn button
				g.setColor(Color.GREEN);
				g.fillRect (10,250, 100,100);
				g.setColor(Color.BLACK);
				
			}
			else { // If overworld (overworld animations)
				g.drawImage(convert("Main Character.png"),player.x,player.y,50,100,this);
				//g.fillRect(player.x,player.y,10,10);
				g.setColor(Color.RED);
				
				if(!enemiesInField.isEmpty()) {
					for(Enemy e : enemiesInField) {
						g.fillRect(e.x, e.y, 10, 10);
					}
				}
				
				g.setColor(Color.BLACK);
			}
			
			g.fillRect(mousePos[0], mousePos[1], 10, 10); // Test: Create box at the specified mouse click
			//g.dispose(); // Supposed to clear the screen, but doesn't work
	
		}
		else {
			
			g.drawString("GAME OVER", 100,500);
			
			g.drawString("Try Again?", 100,100);
			g.drawRect(100, 100, 100, 100);
		}
	}
	
	public boolean isBetween(int posX, int posY, int x, int w, int y, int h) {
		return (posX >= x && posX <= x + w && posY >= y && posY <= y + h);
	}
	
	/* Returns an array of random fruit from the inventory 
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
	
	public Character[] setEnemyParty (boolean isTeam) {
		if(isTeam) 
			return enemy;
		else
			return party;
	}
	
	public int setEnemy (boolean isTeam) {
		int target = 0;
		if(isTeam) {
			for(Enemy e : enemy) {
				if(e.hp>0) {
					break;
				}
				target++;
			}
		}
		else {
			for(MainCharacter m : party) {
				if(m.hp>0) {
					break;
				}
				target++;
			}
		}
		return target;
	}

	public Lambda attackSetUp(int turnA , Lambda l, boolean isTeam) {
		return (()->{
			
			target = setEnemy(isTeam); // Sets the target as the enemy
			
			if(isTeam) {
				if(recipePlace!=-1 && turn < 4 && countDown[turn][recipePlace] == 0) { // If recipe selected and is ready
					l.interact(); // Do designated recipe action
					countDown[turn][recipePlace] = turnA; // Set number of turns
					party[turn].use(fruitSelect[selected].name); // Use the fruit
					fruitSelect[selected] = null; // take fruit out
				}
			}
			else { // Enemy
				
				if(countDownEnemy[turnEnemy][0] == 0) { // If cooldown over 
					l.interact();
					countDownEnemy[turnEnemy][0] = turnA;
				}
			
			}
		});
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
	
	/* Class to return images: stores images into "animations"  
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
*/

public class Main extends JPanel implements Runnable{
	
	//Instance Variables
	ArrayList<Button> buttonsAll = new ArrayList<Button>(); // Stores all the buttons for every
	ArrayList<Button> buttonsMenu = new ArrayList<Button>(); // Stores all the buttons for the menu
	ArrayList<Button> buttonsBattle = new ArrayList<Button>(); // Stores all the buttons for the battling
	Button[][] recipeButtons = new Button[4][4];
	ArrayList<Button> buttonsOverworld = new ArrayList<Button>(); // Stores all the buttons for the overworld
	ArrayList<Enemy> enemiesInField = new ArrayList<Enemy>();
	HashSet<Enemy> enemiesInBattle = new HashSet<Enemy>(); 
	Set<Integer> pressed = new HashSet<Integer>(); // Stores all information about key inputs
	ArrayList<MainCharacter> team = new ArrayList<MainCharacter>(); // All of the collected teammates
	MainCharacter[] battleParty = new MainCharacter[4]; // The party used in battle
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
				Button z = new Button(i +", "+o,10 + 210*i,400,200,100);
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
		
		// Add Fruit Buttons 
		for(int i = 0; i < 4; i++) {
			Button a1 = new Button("", 10 + 110 * i, 100, 100 , 100, MainCharacter.getRandomFruit());
			a1.setUsage(()->MainCharacter.use(a1.displayFruit));
			buttonsBattle.add(a1);
		}
		
		// Add End Turn button in battle
		Button b = new Button("End Turn", 860, 400, 200, 100);
		b.setUsage(()->turn++);
		buttonsBattle.add(b);
		
		// Add first character to party
		battleParty[0] = new MainCharacter("",0,0,12,12,12,12,"");
		for(int i = 0; i < 4; i++) recipeButtons[0][i].setRecipe(battleParty[0].recipes[i],1);
		
		// MOMENTARY : Adds Test Buttons/Enemies
		buttonsMenu.add(new Button("",10,110,100,100));
		buttonsMenu.add(new Button ("  A " , 120, 110, 100, 100, 2, 2));
		enemiesInField.add(new Enemy("", 100, 100, 100, 100,100,100,""));
		
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
		
		
		if(isTurn) { // Your turn
			
			if(turn < 4) { // While still have party members to go
				if(battleParty[turn] != null) {
					for(Button b : recipeButtons[turn]) {
						b.pressed();
						b.useRecipe(battleParty[turn]);
					}
				}
				else {
					turn ++;
				}
			}
			else {
				isTurn = false;
				turn = 0;
			}
			
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
			if(b.isDisplayFruit) {
				g.drawImage(b.displayFruit.sprite, b.x, b.y, b.w, b.h, this);
			}
		}
		for(Button b : buttonsAll) {
			if(b.isPressed)g.setColor(Color.RED); else g.setColor(Color.ORANGE);
			g.fillRect(b.x, b.y, b.w, b.h);
			g.setColor(Color.BLACK);
			g.drawString(b.name, b.x, b.y + b.h/2);
		}
		
		if(turn < 4 && isTurn) {
			for(Button b : recipeButtons[turn]) {
				if(b.isPressed)g.setColor(Color.RED); else g.setColor(Color.BLUE);
				g.fillRect(b.x, b.y, b.w, b.h);
				g.setColor(Color.BLACK);
				g.drawString(b.name, b.x, b.y + b.h/2);
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
		public String name; // Name of button
		public boolean isPressed; // Whether or not activated
		public Fruit[] fruitCollected; // Storage of the fruit used
		public Fruit displayFruit;
		
		private boolean isDisplayFruit = false;
		private int numOfFruitCollected, countDown, countDownInitial; // Number of fruit inputed into the button
		private Lambda recipe; // Recipe for combat
		private Lambda2 usage;
		
		
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
			
		}		
		public Button(String name, int x, int y, int w, int h, Fruit f) { // Fruit selection button
			this(name, x, y, w, h);
			displayFruit = f;
			isDisplayFruit = true;
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
		public boolean addFruit(Fruit fruit) { // Adds fruit if combat selection : returns false if not full, returns true if array is full
			if(numOfFruitCollected < fruitCollected.length) {
				fruitCollected[numOfFruitCollected] = fruit;
				numOfFruitCollected++;
			}
			return fruitCollected.length == numOfFruitCollected; 
		}
		public void resetFruit() { // Sets every value in the fruitCollected array to null ; clears out the array
			for(int i = 0; i < fruitCollected.length;i++)fruitCollected[i] = null;
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
		public void setRecipe(Lambda l, int countDown) { // Changes the recipe
			recipe = l;
			name = (MainCharacter.cookBookName.containsKey(recipe))?MainCharacter.cookBookName.get(recipe):"";
			if(countDown>=0) {
				countDownInitial = countDown;
				this.countDown = countDown;
			}
		}
		public void setCountDown(int n) {
			countDownInitial = n;
		}
		public boolean isFinishCountdown() { // FIX LATER ::
			if(countDown == 0) {
				countDown = countDownInitial;
				return true;
			}
			else {
				countDown--;
				return false;
			}
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
