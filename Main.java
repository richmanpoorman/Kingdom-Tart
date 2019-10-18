import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class Main extends JPanel implements Runnable, ComponentListener, MouseMotionListener, KeyListener{
	public MainCharacter player;
	public MainCharacter[] party = new MainCharacter[4];
	public ArrayList<MainCharacter> teammates = new ArrayList<MainCharacter>();
	
	
	
	public static Set<Integer> pressed;
	
	public Tileset map;
	public Enemy[] enemy;
	public int[] mousePos = new int[2];
	public ArrayList<Enemy> enemiesInField = new ArrayList<Enemy>();
	public boolean isBattle = false;
	
	
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
		
		player = new MainCharacter("player",100,100,10,10,10,10);
		
		pressed = new HashSet<Integer>();
		setFocusable(true);
		setDoubleBuffered(false);
		
		// Starts runnable
		Thread t = new Thread(this);
		t.start(); 
		
	}
	
	public void gameplay() {
		
		if (isBattle) {
			
			
			
		}
		else {
			
			player.playerUpdate(pressed);
			
		}
		
	}
	
	
	
	public void paint(Graphics g) {
		
		g.clearRect(0, 0, this.getWidth(), this.getHeight()); // Clears the screen
		// Frames of different gameplay
		if (isBattle) {
			
			
			
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
	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentResized(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private class KeyListener extends KeyAdapter{
		@Override
		public synchronized void keyPressed(KeyEvent e) {
			System.out.println("Press");
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
			System.out.println("Click");
			mousePos[0] = e.getX();
			mousePos[1] = e.getY();
		}
	
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
