package com;

public class Rib {

	
	public Point4D[] points=null;
	public int index=0;
	
	public Rib(int size){
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

	
}
