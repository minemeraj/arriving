package com;

public class Rib {

	
	private Point4D[] points=null;
	private int index=0;
	/**length used when stretching splines **/
	private double length;
	
	Rib(int size){
		points=new Point4D[size];
	}

	public Point4D[] getPoints() {
		return points;
	}

	public void setPoints(Point4D[] points) {
		this.points = points;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	void translate(int dx, int dy, double dz) {


		for (int k = 0; k < points.length; k++) {
			points[k].translate(dx, dy, dz);
		}
		
	}

	public void setLength(double len) {
		this.length=len;
		
	}
	
	public double getLength(){
		return length;
	}

	
}
