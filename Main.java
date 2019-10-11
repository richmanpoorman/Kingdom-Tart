import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

public class Main extends JPanel implements Runnable, MouseMotionListener, ComponentListener{
	
	public MainCharacter[] player;
	public Tileset map;
	public Enemy[] enemy;
	
	public Main() {
	
		
		
		
	}
	public void paint(Graphics g) {
		
		
		g.dispose();
	}
	
	public void run() {
		long beforeTime = System.currentTimeMillis();
		while (true) {
			long timeDiff = System.currentTimeMillis() - beforeTime;
			beforeTime = System.currentTimeMillis();
			long sleep = 30 - timeDiff;
			repaint();
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
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
