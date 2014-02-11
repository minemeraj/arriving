package com.editors;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JPanel;

import com.LineData;
import com.Point3D;
import com.Polygon3D;
import com.PolygonMesh;

public class CustomJPanel extends JPanel{
	
	public static final Color BACKGROUND = Color.BLACK;
	
	
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
	
	public Graphics2D graph=null;
	public BufferedImage buf=null;
	public Graphics2D buffGraph;
	

	public void initialize() {
		
		graph=(Graphics2D)getGraphics();
		WIDTH=getWidth();
		HEIGHT=getHeight();
		buf=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		buffGraph = (Graphics2D) buf.getGraphics();
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
	
	public void drawBasic(){
		
		if(buf==null)
			return;
		
		
		buffGraph.setColor(BACKGROUND);
		buffGraph.fillRect(0,0,getWidth(),getHeight());
		
	}
	
	public void drawAxes(){
		
		buffGraph.setColor(Color.GREEN);
		buffGraph.drawLine((int)calcX(0,0,0),(int)calcY(0,0,0),(int)calcX(100,0,0),(int)calcY(100,0,0));
		buffGraph.setColor(Color.YELLOW);
		buffGraph.drawLine((int)calcX(0,0,0),(int)calcY(0,0,0),(int)calcX(0,100,0),(int)calcY(0,100,0));

		
	}

	public void draw(PolygonMesh mesh) {
		
		if(buf==null)
			return;
		Graphics buffGraph = buf.getGraphics();
		
		Vector<LineData> poly = mesh.getPolygonData();
		Point3D[] points = mesh.points;
		
		for (int i = 0; i < poly.size(); i++) {
			LineData ld = (LineData) poly.elementAt(i);
			Polygon3D pol=LineData.buildPolygon(ld,points);
			
			
			for (int j = 0; j < pol.npoints; j++) {				
								
				drawLine(buffGraph,pol.xpoints[j],pol.ypoints[j],pol.zpoints[j],pol.xpoints[(j+1)% pol.npoints],pol.ypoints[(j+1)% pol.npoints],pol.zpoints[(j+1)% pol.npoints]);
				
			}
		}
		
		
		
	}


	private void drawLine(Graphics buffGraph, double x0,double y0,double z0,double x1,double y1,double z1) {
		
		buffGraph.drawLine(calcX(x0,y0,z0),calcY(x0,y0,z0),calcX(x1,y1,z1),calcY(x1,y1,z1));
		
	}
}
