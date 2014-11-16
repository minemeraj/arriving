package com.editors.weapons.data;

import java.util.Vector;

import com.BPoint;
import com.CustomData;
import com.Segments;
import com.main.Renderer3D;

public class Sword extends CustomData {

	double forearm_length=0;
	double forearm_width=0;
	double forearm_height=0;

	public Sword(double forearm_length, double forearm_width,
			double forearm_height) {
		super();
		this.forearm_length = forearm_length;
		this.forearm_width = forearm_width;
		this.forearm_height = forearm_height;

		initMesh();
	}



	private void initMesh() {

		points=new Vector();
		points.setSize(200);
		polyData=new Vector();

		n=0;

		Segments s0=new Segments(0,forearm_width*0.5,0,forearm_length,0,forearm_height);

		BPoint[][][] blade=new BPoint[2][2][2];

		blade[0][0][0]=addBPoint(-1.0,0.0,0,s0);
		blade[1][0][0]=addBPoint(1.0,0.0,0,s0);
		blade[0][1][0]=addBPoint(-1.0,1.0,0,s0);
		blade[1][1][0]=addBPoint(1.0,1.0,0,s0);		

		blade[0][0][1]=addBPoint(-1.0,0.0,1.0,s0);
		blade[1][0][1]=addBPoint(1.0,0.0,1.0,s0);
		blade[0][1][1]=addBPoint(-1.0,1.0,1.0,s0);
		blade[1][1][1]=addBPoint(1.0,1.0,1.0,s0);

		addLine(blade[0][0][1],blade[1][0][1],blade[1][1][1],blade[0][1][1],Renderer3D.CAR_TOP);		

		addLine(blade[0][0][0],blade[0][0][1],blade[0][1][1],blade[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(blade[1][0][0],blade[1][1][0],blade[1][1][1],blade[1][0][1],Renderer3D.CAR_RIGHT);

		addLine(blade[0][1][0],blade[0][1][1],blade[1][1][1],blade[1][1][0],Renderer3D.CAR_FRONT);

		addLine(blade[0][0][0],blade[1][0][0],blade[1][0][1],blade[0][0][1],Renderer3D.CAR_BACK);

		addLine(blade[0][0][0],blade[0][1][0],blade[1][1][0],blade[1][0][0],Renderer3D.CAR_BOTTOM);
	}

}
