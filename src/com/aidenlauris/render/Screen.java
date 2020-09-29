package com.aidenlauris.render;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.game.util.IOHandler;

/**
 * @author Aiden & Lauris
 * Jan 22, 2019
 * 
 * The physical screen where things are printed too
 */
public class Screen extends JFrame{
	
	IOHandler io = new IOHandler();
	
	private RenderHandler painter = new RenderHandler(io);

	
	public Screen() {
		
		
		//creates the window
		setTitle("ScreenOverlay");
		setSize(GameLogic.camx, GameLogic.camy);
		
		//close operation
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		getContentPane().setBackground(Color.BLUE);
		
		//add the render component
		add(getPainter());
		
		//add all listeners
		addKeyListener(io);
		addMouseListener(io);
		addMouseMotionListener(io);
		setResizable(false);
		
		
		setCursor( getToolkit().createCustomCursor(
                new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB ),
                new Point(),
                null ) );
		
		//show it to the screen
		setVisible(true);	
		
	}


	/**
	 * @return
	 */
	public RenderHandler getPainter() {
		return painter;
	}


	public void setPainter(RenderHandler painter) {
		this.painter = painter;
	}
}
