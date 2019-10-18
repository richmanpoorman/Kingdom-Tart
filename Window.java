import java.awt.Color;
import java.awt.Graphics;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

public class Window extends JFrame{
	public Window() {

		add(new Main());
		setTitle("Trailblazers");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(1400, 700);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(true);
		
	}

	public static void main(String[] args) {
		new Window();
	}
	
}
