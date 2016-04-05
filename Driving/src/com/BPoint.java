package com;


public class BPoint extends Point3D{
	
	private int index=-1;

	public BPoint(double x, double y, double z, int index) {
		super(x, y, z);
		this.index = index;
	}


	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}


}
