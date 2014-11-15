package com.editors.cars.data;

import java.util.Vector;

import com.CustomData;

public class F1Car extends CustomData {
	
	public F1Car() {
		

		
		initMesh();
	}

	private void initMesh() {
		
		points=new Vector();
		points.setSize(200);
		polyData=new Vector();

		n=0;
		
	}

}
