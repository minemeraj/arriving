package com.editors.cars.data;

import java.util.Vector;

import com.CustomData;

public class Ship extends CustomData {
	
	public Ship() {
		

		
		initMesh();
	}

	private void initMesh() {
		
		points=new Vector();
		points.setSize(200);
		polyData=new Vector();

		n=0;
		
	}

}
