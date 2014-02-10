package com.editors;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class CustomJPanel extends JPanel{
	
	public static final Color BACKGROUND = Color.BLACK;
	public Graphics2D graph;
	
	public int x0=400;
	public int y0=500;
	
	public double dy=1.0;
	public double dx=1.0; 
	
	public int deltax=10;
	public int deltay=10;
	
	public int WIDTH=0;
	public int HEIGHT=0; 
	
	public double alfa=Math.PI/3;
	public double cosAlfa=Math.cos(alfa);
	public double sinAlfa=Math.sin(alfa);
	

	public void initialize() {
		
		graph=(Graphics2D)getGraphics();
		WIDTH=getWidth();
		HEIGHT=getHeight();
	}


	public int calcX(double sx,double sy,double sz){

		
		//return x0+(int) (deltax*(sy-sx*sinAlfa));//axonometric formula
		return x0+(int) ((sx*sinAlfa-sy*sinAlfa)/dx);
	}

	public int calcY(double sx,double sy,double sz){

		
		//return y0+(int) (deltay*(sz-sx*cosAlfa));
		return y0-(int) ((sz+sy*cosAlfa+sx*cosAlfa)/dy);
	}
	
	/*public double calcX(double x,double y){
		
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
		
	}*/
	
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
