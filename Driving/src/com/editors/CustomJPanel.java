package com.editors;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.CustomData;
import com.LineData;
import com.Point3D;
import com.Polygon3D;
import com.PolygonMesh;

public abstract class CustomJPanel extends JPanel{
	
	private final Color BACKGROUND = Color.BLACK;
	
	
	protected int x0=400;
	protected int y0=500;
	
	private double dy=1.0;
	private double dx=1.0; 
	
	private int deltax=10;
	private int deltay=10;
	
	private int WIDTH=0;
	private int HEIGHT=0; 
	
	private double alfa=Math.PI/3;
	private double cosAlfa=Math.cos(alfa);
	private double sinAlfa=Math.sin(alfa);
	
	public Graphics2D graph=null;
	public BufferedImage buf=null;
	public Graphics2D buffGraph;
	
	private String filter=null;
	
	private double teta=0;
	private double ct=Math.cos(teta);
	private double st=Math.sin(teta);
	

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
		buffGraph.setColor(Color.BLUE);
		buffGraph.drawLine((int)calcX(0,0,0),(int)calcY(0,0,0),(int)calcX(0,0,100),(int)calcY(0,0,100));
		
	}

	public void draw(PolygonMesh mesh) {
		
		if(buf==null)
			return;
		Graphics buffGraph = buf.getGraphics();
		
		ArrayList<LineData> poly = mesh.getPolygonData();
		Point3D[] points = mesh.points;
		
		for (int i = 0; i < poly.size(); i++) {
			LineData ld = (LineData) poly.get(i);
			Polygon3D pol=LineData.buildPolygon(ld,points);
			
			
			for (int j = 0; j < pol.npoints; j++) {				
					
				if(filter!=null && ! filter.equals(ld.getData()))
					continue;
				
				drawLine(buffGraph,pol.xpoints[j],pol.ypoints[j],pol.zpoints[j],pol.xpoints[(j+1)% pol.npoints],pol.ypoints[(j+1)% pol.npoints],pol.zpoints[(j+1)% pol.npoints]);
				
			}
		}
		
		
		
	}

	public abstract void draw(CustomData data);

	private void drawLine(Graphics buffGraph, double x0,double y0,double z0,double x1,double y1,double z1) {
		
		
		double xx0=ct*x0+st*y0;
		double yy0=-st*x0+ct*y0;
		
		double xx1=ct*x1+st*y1;
		double yy1=-st*x1+ct*y1;
		
		buffGraph.drawLine(calcX(xx0,yy0,z0),calcY(xx0,yy0,z0),calcX(xx1,yy1,z1),calcY(xx1,yy1,z1));
		
	}
	



	public String getFilter() {
		return filter;
	}


	public void setFilter(String filter) {
		this.filter = filter;
	}


	public double getTeta() {
		return teta;
	}


	public void setTeta(double teta) {
		this.teta = teta;
		ct=Math.cos(teta);
		st=Math.sin(teta);
	}
	
	public void rotate(double dTeta){
		
		teta+=dTeta;
		
		ct=Math.cos(teta);
		st=Math.sin(teta);
	}

}
