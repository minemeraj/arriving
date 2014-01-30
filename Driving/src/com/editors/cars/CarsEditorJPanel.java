package com.editors.cars;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class CarsEditorJPanel extends JPanel{

	private static final Color BACKGROUND = Color.BLACK;
	private Graphics2D graph;

	public void initialize() {
		
		graph=(Graphics2D)getGraphics();
		
	}
	

	public void draw() {
		
		if(graph==null)
			return;
		
		graph.setColor(BACKGROUND);
		graph.fillRect(0,0,getWidth(),getHeight());
		
	}

}
