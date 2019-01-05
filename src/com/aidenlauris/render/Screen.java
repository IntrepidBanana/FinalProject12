package com.aidenlauris.render;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.aidenlauris.game.IOHandler;
import com.aidenlauris.game.WorldMap;

public class Screen extends JFrame{
	
	IOHandler io = new IOHandler();
	
	private PainterLoop painter = new PainterLoop(io);

	
	public Screen() {
		setTitle("ScreenOverlay");
		setSize(WorldMap.camx, WorldMap.camy);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.BLUE);
		add(getPainter());
		addKeyListener(io);
		addMouseListener(io);
		addMouseMotionListener(io);
		addMouseWheelListener(io);
		setResizable(false);
		setCursor( getToolkit().createCustomCursor(
                new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB ),
                new Point(),
                null ) );
		setVisible(true);	
		
	}


	public PainterLoop getPainter() {
		return painter;
	}


	public void setPainter(PainterLoop painter) {
		this.painter = painter;
	}
}
