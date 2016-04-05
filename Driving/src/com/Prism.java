package com;

public class Prism {
	
	private int size=0;
	
	public Point3D[] upperBase=null;
	public Point3D[] lowerBase=null;
	

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
