package com.editors.cars.data;

import java.util.Vector;

import com.CustomData;

public class Tank extends CustomData {
	
	public Tank() {
		

		
		initMesh();
	}

	private void initMesh() {
		
		points=new Vector();
		points.setSize(200);
		polyData=new Vector();

		n=0;
		
	}

}
