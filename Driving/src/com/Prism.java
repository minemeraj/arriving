package com;

public class Prism {
	
	int size=0;
	
	public Point3D[] upperBase=null;
	public Point3D[] lowerBase=null;
	

	public Prism(Point3D[] upperBase, Point3D[] lowerBase) {
		super();
		this.upperBase = upperBase;
		this.lowerBase = lowerBase;
	}
	
	public Prism() {
		super();
	}
	
	public Prism(int size) {
		
		this.size=size;
		upperBase=new Point3D[size];
		lowerBase=new Point3D[size];
	}

	public int getSize() {
		return size;
	}


}
