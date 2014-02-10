package com.editors.animals;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.Polygon3D;
import com.editors.animals.data.Animal;

public class AnimalsJPanel extends JPanel{

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
	

	public void draw(Animal animal) {
		
		if(graph==null)
			return;
		
		graph.setColor(BACKGROUND);
		graph.fillRect(0,0,getWidth(),getHeight());
		
		if(animal!=null){
			
			
			graph.setColor(Color.WHITE);
			drawAnimalData(animal);
			
		}
		graph.setColor(Color.GREEN);
		graph.drawLine((int)calcX(0,0),(int)calcY(0,0),(int)calcX(100,0),(int)calcY(100,0));
		graph.setColor(Color.YELLOW);
		graph.drawLine((int)calcX(0,0),(int)calcY(0,0),(int)calcX(0,100),(int)calcY(0,100));
		
	}


	private void drawAnimalData(Animal animal) {


		double x0=animal.getNw_x();
		double y0=animal.getNw_y();		
		double xside=animal.getX_side();
		double yside=animal.getY_side();
		
		Polygon3D pol=new Polygon3D();
		pol.addPoint((int)calcX(x0,y0),(int)calcY(x0,y0),0);
		pol.addPoint((int)calcX(x0+xside,y0),(int)calcY(x0+xside,y0),0);
		pol.addPoint((int)calcX(x0+xside,y0+yside),(int)calcY(x0+xside,y0+yside),0);
		pol.addPoint((int)calcX(x0,y0+yside),(int)calcY(x0,y0+yside),0);
	
			
		graph.draw(pol);
	
		
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
