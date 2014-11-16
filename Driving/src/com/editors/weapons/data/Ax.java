package com.editors.weapons.data;

import java.util.Vector;

import com.BPoint;
import com.CustomData;
import com.Segments;
import com.main.Renderer3D;

public class Ax extends CustomData  {
	
	
	double barrel_length=0;
	double barrel_radius=0;	
	int barrel_meridians=0;
	
	double forearm_length=0;
	double forearm_width=0;
	public Ax(double barrel_length, double barrel_radius, int barrel_meridians,
			double forearm_length, double forearm_width, double forearm_height) {
		super();
		this.barrel_length = barrel_length;
		this.barrel_radius = barrel_radius;
		this.barrel_meridians = barrel_meridians;
		this.forearm_length = forearm_length;
		this.forearm_width = forearm_width;
		this.forearm_height = forearm_height;
		
		initMesh();
	}

	double forearm_height=0;
	

	private void initMesh() {
		
		points=new Vector();
		points.setSize(200);
		polyData=new Vector();

		n=0;
		
		
		addZCylinder(0,0,0,barrel_radius,barrel_length,barrel_meridians);
		
		Segments s0=new Segments(0,barrel_radius,0,forearm_length,barrel_length-forearm_height,forearm_height);
		Segments s1=new Segments(0,barrel_radius,0,forearm_length,barrel_length-(forearm_height+forearm_width)*0.5,forearm_width);
		
		BPoint[][][] blade=new BPoint[2][2][2];
		
		blade[0][0][0]=addBPoint(-1.0,0.0,0,s0);
		blade[1][0][0]=addBPoint(1.0,0.0,0,s0);
		blade[0][1][0]=addBPoint(0.0,1.0,0,s1);
		blade[1][1][0]=null;	
		
		blade[0][0][1]=addBPoint(-1.0,0.0,1.0,s0);
		blade[1][0][1]=addBPoint(1.0,0.0,1.0,s0);
		blade[0][1][1]=addBPoint(0.0,1.0,1.0,s1);
		blade[1][1][1]=null;
		
		addLine(blade[0][0][1],blade[1][0][1],blade[0][1][1],null,Renderer3D.CAR_TOP);		

		addLine(blade[0][0][0],blade[0][0][1],blade[0][1][1],blade[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(blade[1][0][0],blade[0][1][0],blade[0][1][1],blade[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(blade[0][0][0],blade[1][0][0],blade[1][0][1],blade[0][0][1],Renderer3D.CAR_BACK);
		
		addLine(blade[0][0][0],blade[0][1][0],blade[1][0][0],null,Renderer3D.CAR_BOTTOM);

		
	}

}
