import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.imageio.ImageIO;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Battle implements KeyListener{
	Graphics g;
	int select = 0;
	
	public Enemy enemy;
	public MainCharacter mainCharacter;
	public boolean attackDecision = true;
	
	public Battle (MainCharacter mainCharacter, Enemy enemy) {
		this.enemy = enemy;
		this.mainCharacter = mainCharacter;
	}
	
	public void battle() {
		
		
		
		g.fillRect(100, 600, 50, 20);
		g.fillRect(160, 600, 50, 20);
		g.fillRect(220, 600, 50, 20);
		g.fillRect(280, 600, 50, 20);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_A) {
			select = (select<=0)?3:select - 1;
		}
		else if (e.getKeyCode()==KeyEvent.VK_D) {
			select = (select>=3)?0:select + 1;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
