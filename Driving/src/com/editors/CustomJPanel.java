package com.editors;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class CustomJPanel extends JPanel{
	
	public static final Color BACKGROUND = Color.BLACK;
	public Graphics2D graph;
	
	public int x0=200;
	public int y0=200;
	
	public double dy=1.0;
	public double dx=1.0; 
	
	public int deltax=10;
	public int deltay=10;
	
	public int WIDTH=0;
	public int HEIGHT=0; 

	public void initialize() {
		
		graph=(Graphics2D)getGraphics();
		WIDTH=getWidth();
		HEIGHT=getHeight();
	}


	public double calcX(double x,double y){
		
		return x0+x/dx;
		
	}
	
	public double calcY(double x,double y){
		
		return HEIGHT-y0-y/dy;
		
	}
	
	public double invertX(double x,double y){
		
		return (-x0+x)*dx;
		
	}
	
	public double invertY(double x,double y){
		
		return (-y+HEIGHT-y0)*dy;
		
	}
	
	public void zoom(int i) {
		
		
		if(i>0)
			dx=dy=dx/2.0;
		else
			dx=dy=dx*2.0;
		
		
	}
	
	public void translate(int i, int j) {
		
		
		x0+=i*deltax;
		y0+=j*deltay;
		
		
	}
}
