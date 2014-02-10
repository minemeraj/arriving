package com;


public class BPoint extends Point3D{

	public BPoint(double x, double y, double z, int index) {
		super(x, y, z);
		this.index = index;
	}

	int index=-1;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}


}
