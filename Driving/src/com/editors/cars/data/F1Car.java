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
	double front_width=0;
    double front_length=0;
    double front_height=0;
    double back_width=0;
    double back_length=0;
    double back_height=0;
    
    double roof_width=0;
    double roof_length=0;
    double roof_height=0;
    
    double wheel_radius=0;
    double wheel_width=0;
    
    double front_overhang=0;
    double wheel_base=0;    
    double rear_overhang=0;
	
	public F1Car(double x_side, double y_side, double z_side,
			double front_width, double front_length, double front_height,
			double back_width, double back_length, double back_height,
			double roof_width, double roof_length, double roof_height,
			double wheel_radius, double wheel_width, double front_overhang,
			double wheel_base, double rear_overhang) {

		this.x_side = x_side;
		this.y_side = y_side;
		this.z_side = z_side;
		this.front_width = front_width;
		this.front_length = front_length;
		this.front_height = front_height;
		this.back_width = back_width;
		this.back_length = back_length;
		this.back_height = back_height;
		this.roof_width = roof_width;
		this.roof_length = roof_length;
		this.roof_height = roof_height;
		this.wheel_radius = wheel_radius;
		this.wheel_width = wheel_width;
		this.front_overhang = front_overhang;
		this.wheel_base = wheel_base;
		this.rear_overhang = rear_overhang;
		
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
