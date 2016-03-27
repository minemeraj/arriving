package com;

import java.util.ArrayList;
import java.util.Vector;

public class SquareMesh extends PolygonMesh {
	


	int numx=0;
	int numy=0;	
	int dx=0;
	int dy=0;
	double x0=0;
	double y0=0;
	
	
	public SquareMesh(Vector points, ArrayList lines) {
		super(points, lines);
	}
	
	public SquareMesh(Vector points, ArrayList lines,int numx, int numy, int dx,int dy,double x0, double y0) {
		super(points, lines);
		this.numx = numx;
		this.numy = numy;
		this.dx = dx;
		this.dy = dy;
		this.x0 = x0;
		this.y0 = y0;
	}
	
	public SquareMesh(int numx, int numy, int dx,int dy, double x0, double y0) {
		super();
		this.numx = numx;
		this.numy = numy;
		this.dx = dx;
		this.dy = dy;
		this.x0 = x0;
		this.y0 = y0;
	}
	
	public SquareMesh() {
		// TODO Auto-generated constructor stub
	}
	public int getNumx() {
		return numx;
	}
	public void setNumx(int numx) {
		this.numx = numx;
	}
	public int getNumy() {
		return numy;
	}
	public void setNumy(int numy) {
		this.numy = numy;
	}
	public double getX0() {
		return x0;
	}
	public void setX0(double x0) {
		this.x0 = x0;
	}
	public double getY0() {
		return y0;
	}
	public void setY0(double y0) {
		this.y0 = y0;
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}
	
	@Override
	public SquareMesh clone() {
		
		SquareMesh pm=new SquareMesh();
		pm.points=new Point3D[this.points.length];
		
		for(int i=0;i<this.points.length;i++){
	
			pm.points[i]=points[i].clone();
			
		}
		
		for(int i=0;i<this.polygonData.size();i++){

			pm.addPolygonData(polygonData.get(i).clone());
		}
		for(int i=0;i<this.normals.size();i++){

			pm.normals.add(normals.elementAt(i).clone());
		}
		pm.setNumx(getNumx());
		pm.setNumy(getNumy());
		pm.setDx(getDx());
		pm.setDy(getDy());
		pm.setX0(getX0());
		pm.setY0(getY0());
		
		return pm;
	}

	

}
