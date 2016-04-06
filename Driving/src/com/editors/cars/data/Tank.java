package com.editors.cars.data;

import java.util.ArrayList;

import com.BPoint;
import com.CustomData;
import com.Segments;
import com.main.Renderer3D;

public class Tank extends CustomData {
	
	private double x_side=0;
	private double y_side=0;
	private double z_side=0;
	
	private double front_width=0;
	private double front_length=0;
	private double front_height=0;
    
	private double back_width=0;
	private double back_length=0;
	private double back_height=0;

	private double roof_width=0;
	private double roof_length=0;
	private double roof_height=0;
	private double front_overhang=0;
	private double rear_overhang=0;
	
	public Tank(double x_side, double y_side, double z_side,
			double front_width, double front_length, double front_height,
			double back_width, double back_length, double back_height,
			double roof_width, double roof_length, double roof_height,
			double front_overhang, double rear_overhang) {
		super();
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
		this.front_overhang = front_overhang;
		this.rear_overhang = rear_overhang;
		
		initMesh();
	}



	private void initMesh() {
		
		points=new ArrayList();
		polyData=new ArrayList();

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
		
		
		Segments s1=new Segments(0,roof_width*0.5,rear_overhang,roof_length,z_side,roof_height);
		
		BPoint[][][] turret=new BPoint[2][2][2];
		
		turret[0][0][0]=addBPoint(-1.0,0.0,0,s1);
		turret[1][0][0]=addBPoint(1.0,0.0,0,s1);
		turret[0][1][0]=addBPoint(-1.0,1.0,0,s1);
		turret[1][1][0]=addBPoint(1.0,1.0,0,s1);		
		
		turret[0][0][1]=addBPoint(-1.0,0.0,1.0,s1);
		turret[1][0][1]=addBPoint(1.0,0.0,1.0,s1);
		turret[0][1][1]=addBPoint(-1.0,1.0,1.0,s1);
		turret[1][1][1]=addBPoint(1.0,1.0,1.0,s1);
		
		addLine(turret[0][0][1],turret[1][0][1],turret[1][1][1],turret[0][1][1],Renderer3D.CAR_TOP);		

		addLine(turret[0][0][0],turret[0][0][1],turret[0][1][1],turret[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(turret[1][0][0],turret[1][1][0],turret[1][1][1],turret[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(turret[0][1][0],turret[0][1][1],turret[1][1][1],turret[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(turret[0][0][0],turret[1][0][0],turret[1][0][1],turret[0][0][1],Renderer3D.CAR_BACK);
		
		addLine(turret[0][0][0],turret[0][1][0],turret[1][1][0],turret[1][0][0],Renderer3D.CAR_BOTTOM);
		
		addYCylinder(0,rear_overhang+roof_length,z_side+roof_height*0.5,10,100,10);


		
	}

}
