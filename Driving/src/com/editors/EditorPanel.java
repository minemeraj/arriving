package com.editors;

import com.Point3D;


public interface EditorPanel {
	

	
	public void draw() ;

	public void displayAll();
	
	public void resetLists();
	
	public void clean();
	
	public void selectAllPoints();
	
	public void selectPoint(Point3D p);
	
	public void deselectAllLines();
	
	public void deselectAllPoints();
	
	public void deselectAll();
	
	public void initialize();

	public void rotate(double df);
	
	public void zoom(int n);
	 
	public void translate(int i, int j);
	
	public void buildPolygon();
	
	public void delete();
	
	public void joinSelectedPoints();
	
	public void addPoint() ;
	
	public void addPoint(double x, double y ,double z);
	
	public void rescaleAllPoints();
	
	public void changeSelectedPoint();
	
	public void moveSelectedPointWithMouse(Point3D p3d, int type);
	
	public void moveSelectedPoints(int dx, int dy, int dz);
	
	public void selectPoint(int x, int y);
	
	
}
