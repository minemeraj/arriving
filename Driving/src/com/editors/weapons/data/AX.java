package com.editors.weapons.data;

import java.util.Vector;

import com.CustomData;

public class AX extends CustomData  {
	
	
	double barrel_length=0;
	double barrel_radius=0;	
	int barrel_meridians=0;
	
	public AX(double barrel_length, double barrel_radius, int barrel_meridians) {
		
		this.barrel_length = barrel_length;
		this.barrel_radius = barrel_radius;
		this.barrel_meridians = barrel_meridians;
		
		initMesh();
	}

	private void initMesh() {
		
		points=new Vector();
		points.setSize(200);
		polyData=new Vector();

		n=0;
		
	}

}
