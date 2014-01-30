package com.editors.buildings;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class BuildingJPanel extends JPanel{

	private static final Color BACKGROUND = Color.BLACK;
	private Graphics2D graph;
	
	int x0=200;
	int y0=200;
	
	double dy=1.0;
	double dx=1.0; 
	
	int deltax=10;
	int deltay=10;
	
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
		
		graph.setColor(Color.WHITE);
		
		graph.drawLine((int)calcX(0,0),(int)calcY(0,0),(int)calcX(100,0),(int)calcY(100,0));
		graph.drawLine((int)calcX(0,0),(int)calcY(0,0),(int)calcX(0,100),(int)calcY(0,100));
		
	}
	
	double calcX(double x,double y){
		
		return x0+x/dx;
		
	}
	
	double calcY(double x,double y){
		
		return HEIGHT-y0-y/dy;
		
	}
	
	public void zoom(int i) {
		
		
		if(i>0)
			dx=dy=dx/2.0;
		else
			dx=dy=dx*2.0;
		
		draw();
	}
	
	public void translate(int i, int j) {
		
		
		x0+=i*deltax;
		y0+=j*deltay;
		
		draw();
	}

}
