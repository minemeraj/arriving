package com.editors.cars.data;

import java.util.Vector;

import com.BPoint;
import com.CustomData;
import com.Segments;
import com.main.Renderer3D;

public class F1Car extends CustomData {
	
	double x_side=0;
	double y_side=0;
	double z_side=0;
	
	public F1Car(double x_side, double y_side, double z_side) {
		super();
		this.x_side = x_side;
		this.y_side = y_side;
		this.z_side = z_side;
		
		initMesh();
	}

	private void initMesh() {
		
		points=new Vector();
		points.setSize(200);
		polyData=new Vector();

		n=0;
		
		Segments s0=new Segments(0,x_side*0.5,0,y_side,0,z_side);
		
		BPoint[][][] body=new BPoint[2][2][2];
		
		body[0][0][0]=addBPoint(-1.0,0.0,0,s0);
		body[1][0][0]=addBPoint(1.0,0.0,0,s0);
		body[0][1][0]=addBPoint(-1.0,1.0,0,s0);
		body[1][1][0]=addBPoint(1.0,1.0,0,s0);		
		
		body[0][0][1]=addBPoint(-1.0,0.0,1.0,s0);
		body[1][0][1]=addBPoint(1.0,0.0,1.0,s0);
		body[0][1][1]=addBPoint(-1.0,1.0,1.0,s0);
		body[1][1][1]=addBPoint(1.0,1.0,1.0,s0);
		
		addLine(body[0][0][1],body[1][0][1],body[1][1][1],body[0][1][1],Renderer3D.CAR_TOP);		

		addLine(body[0][0][0],body[0][0][1],body[0][1][1],body[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(body[1][0][0],body[1][1][0],body[1][1][1],body[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(body[0][1][0],body[0][1][1],body[1][1][1],body[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1],Renderer3D.CAR_BACK);
		
		addLine(body[0][0][0],body[0][1][0],body[1][1][0],body[1][0][0],Renderer3D.CAR_BOTTOM);
	}

}
