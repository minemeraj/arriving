package com;

import java.text.DecimalFormat;

public class PointListItem {


	private DecimalFormat df=new DecimalFormat("000");

	private Point3D p;
	private int index=-1;

	public PointListItem(Point3D p, int index) {	
		super();
		this.p = p;
		this.index=index;
	}
	
	@Override
	public String toString() {
		
		return df.format(p.x)+","+df.format(p.y)+","+df.format(p.z)+"("+index+")";
	}
	
}
