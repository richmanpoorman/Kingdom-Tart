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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
//import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class Main extends JPanel implements Runnable{
	public MainCharacter player;
	public MainCharacter[] party = new MainCharacter[4];
	public ArrayList<MainCharacter> teammates = new ArrayList<MainCharacter>();
	
	public int turn = 0;
	public boolean isTurn = true;
	
	public static Set<Integer> pressed;
	
	public Tileset map;
	public Enemy[] enemy = new Enemy[4];
	public int[] mousePos = new int[2];
	public ArrayList<Enemy> enemiesInField = new ArrayList<Enemy>();
	public boolean isBattle = true;
	
	public static HashMap<String,Animation> anim = new HashMap<String, Animation>();
	
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
	
	public Main() {	
		//adds all the components to look for
		addKeyListener(new KeyListener());
		addMouseListener(new MouseListener());
		enemy[0] = new Enemy ("John Adams", 0, 0, 100, 100, 100, 100);
		player = new MainCharacter("player",100,100,10,10,10,10); // Stock Character
		player.setRecipes("", "", "", "");
		
		party[0] = player;
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
		if (isBattle) {
			
			for(int i = 0; i < 4; i ++ ) {
				g.fillRect(10 + 110 * i, 400, 100, 100);
			}
			
		}
		else {
		//	System.out.println(player.x);
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
	public boolean isBetween(int posX, int posY, int x, int w, int y, int h) {
		return (posX >= x && posX <= x + w && posY >= y && posY <= y + h);
	}
	
	
	
	public void gameplay() {
		
		if (isBattle) { // Checks if in battle
			if(isTurn) { // Your turn
				if(party[turn] != null) { // if there is a party member
					if(isBetween(mousePos[0], mousePos[1], 10, 100, 400, 100)) { // If in atk block 1
						mousePos[0] = 10; // Locks mouse x movement
						mousePos[1] = 400; // Locks mouse y movement
						System.out.println("ATK 1");
						
						
						if(!anim.isEmpty() && anim.containsKey(party[turn].name)) { // If there is an animation
							if(anim.get(party[turn].name).isPlayed()) { // if the animation has played, go to the next character
								
								// // If the party member exists
									if(party[turn].recipes[0] != null) // If they have a function
										party[turn].recipes[0].interact(); // Do it
								
								turn++; // next person
								// Mouse reset
								mousePos[0] = 0;
								mousePos[1] = 0;
	
								
							}
						}
						else { // if there is no animation, just go to the next character
							
							// // If the party member exists
								if(party[turn].recipes[0] != null) // If they have a function
									party[turn].recipes[0].interact(); // Do it
							
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
							
							// // If the party member exists
								if(party[turn].recipes[0] != null) // If they have a function
									party[turn].recipes[0].interact(); // Do it
							
							if(anim.get(party[turn].name).isPlayed()) {
								turn++; // next person
								// Mouse reset
								mousePos[0] = 0;
								mousePos[1] = 0;
							}
	
						}
						else {
							
							// // If the party member exists
								if(party[turn].recipes[0] != null) // If they have a function
									party[turn].recipes[0].interact(); // Do it
							
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
								
								// // If the party member exists
									if(party[turn].recipes[0] != null) // If they have a function
										party[turn].recipes[0].interact(); // Do it
								
								turn++; // next person
								// Mouse reset
								mousePos[0] = 0;
								mousePos[1] = 0;
	
							}
						}
						else {  // if there is no animation, just go to the next character
							
							// // If the party member exists
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
								
								// // If the party member exists
									if(party[turn].recipes[0] != null) // If they have a function
										party[turn].recipes[0].interact(); // Do it
								
								turn++; // next person
								// Mouse reset
								mousePos[0] = 0;
								mousePos[1] = 0;
	
							}
						}
						else {// if there is no animation, just go to the next character
							
							// // If the party member exists
								if(party[turn].recipes[0] != null) // If they have a function
									party[turn].recipes[0].interact(); // Do it
							
							turn++; // next person
							// Mouse reset
							mousePos[0] = 0;
							mousePos[1] = 0;
							
							
	
						}
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

}
