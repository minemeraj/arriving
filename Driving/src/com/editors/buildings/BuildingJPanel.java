package com.editors.buildings;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class BuildingJPanel extends JPanel{

	private static final Color BACKGROUND = Color.BLACK;
	private Graphics2D graph;
	
	int x0=0;
	int y0=0;
	
	double deltay=1.0;
	double deltax=1.0;
	
    int WIDTH=0;
    int HEIGHT=0;

	public void initialize() {
		
		graph=(Graphics2D)getGraphics();
		WIDTH=getWidth();
		HEIGHT=getHeight();
		
	}
	

	public void draw() {
		
		if(graph==null)
			return;
		
		graph.setColor(BACKGROUND);
		graph.fillRect(0,0,WIDTH,HEIGHT);
		
	}
	
	double calcX(double x,double y){
		
		return x0+x/deltax;
		
	}
	
	double calcY(double x,double y){
		
		return HEIGHT+y0-y/deltay;
		
	}

}
