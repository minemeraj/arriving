package com.editors.cars.data;

import java.util.ArrayList;

import com.BPoint;
import com.CustomData;
import com.LineData;
import com.Point3D;
import com.Segments;
import com.main.Renderer3D;

public class F1Car extends CustomData {
	
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
    
	private double wheel_radius=0;
	private double wheel_width=0;
    
	private double front_overhang=0;
	private double wheel_base=0;    
	private double rear_overhang=0;
	
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
	
	public void initMesh() {
		
		points=new ArrayList<Point3D>();
		polyData=new ArrayList<LineData>();

		n=0;
		
		
		Segments rs=new Segments(0,x_side*0.5,0,50,back_height,30);
		
		BPoint[][][] backSpoiler=new BPoint[2][2][2];
		 
		backSpoiler[0][0][0]=addBPoint(-1.0,0.0,0,rs);
		backSpoiler[1][0][0]=addBPoint(1.0,0.0,0,rs);
		backSpoiler[0][1][0]=addBPoint(-1.0,1.0,0,rs);
		backSpoiler[1][1][0]=addBPoint(1.0,1.0,0,rs);		
		
		backSpoiler[0][0][1]=addBPoint(-1.0,0.0,1.0,rs);
		backSpoiler[1][0][1]=addBPoint(1.0,0.0,1.0,rs);
		backSpoiler[0][1][1]=addBPoint(-1.0,1.0,1.0,rs);
		backSpoiler[1][1][1]=addBPoint(1.0,1.0,1.0,rs);
		
		addLine(backSpoiler[0][0][1],backSpoiler[1][0][1],backSpoiler[1][1][1],backSpoiler[0][1][1],Renderer3D.CAR_TOP);		

		addLine(backSpoiler[0][0][0],backSpoiler[0][0][1],backSpoiler[0][1][1],backSpoiler[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(backSpoiler[1][0][0],backSpoiler[1][1][0],backSpoiler[1][1][1],backSpoiler[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(backSpoiler[0][1][0],backSpoiler[0][1][1],backSpoiler[1][1][1],backSpoiler[1][1][0],Renderer3D.CAR_BACK);
		
		addLine(backSpoiler[0][0][0],backSpoiler[1][0][0],backSpoiler[1][0][1],backSpoiler[0][0][1],Renderer3D.CAR_BACK);
		
		addLine(backSpoiler[0][0][0],backSpoiler[0][1][0],backSpoiler[1][1][0],backSpoiler[1][0][0],Renderer3D.CAR_BOTTOM);
		
		Segments s0=new Segments(0,back_width*0.5,0,back_length,0,back_height);
		
		BPoint[][][] back=new BPoint[2][2][2];
		
		back[0][0][0]=addBPoint(-1.0,0.0,0,s0);
		back[1][0][0]=addBPoint(1.0,0.0,0,s0);
		back[0][1][0]=addBPoint(-1.0,1.0,0,s0); 
		back[1][1][0]=addBPoint(1.0,1.0,0,s0);		
		
		back[0][0][1]=addBPoint(-1.0,0.0,1.0,s0);
		back[1][0][1]=addBPoint(1.0,0.0,1.0,s0);
		back[0][1][1]=addBPoint(-1.0,1.0,1.0,s0);
		back[1][1][1]=addBPoint(1.0,1.0,1.0,s0);
		
		addLine(back[0][0][1],back[1][0][1],back[1][1][1],back[0][1][1],Renderer3D.CAR_TOP);		

		addLine(back[0][0][0],back[0][0][1],back[0][1][1],back[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(back[1][0][0],back[1][1][0],back[1][1][1],back[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(back[0][1][0],back[0][1][1],back[1][1][1],back[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(back[0][0][0],back[1][0][0],back[1][0][1],back[0][0][1],Renderer3D.CAR_BACK);
		
		addLine(back[0][0][0],back[0][1][0],back[1][1][0],back[1][0][0],Renderer3D.CAR_BOTTOM);
		
		buildWheel(-x_side*0.5,rear_overhang,wheel_radius,wheel_radius,wheel_width);
		buildWheel(x_side*0.5-wheel_width,rear_overhang,wheel_radius,wheel_radius,wheel_width);
		
		Segments s1=new Segments(0,x_side*0.5,back_length,y_side,0,z_side);
		
		BPoint[][][] body=new BPoint[2][2][2];
		
		body[0][0][0]=addBPoint(-1.0,0.0,0,s1);
		body[1][0][0]=addBPoint(1.0,0.0,0,s1);
		body[0][1][0]=addBPoint(-1.0,1.0,0,s1); 
		body[1][1][0]=addBPoint(1.0,1.0,0,s1);		
		
		body[0][0][1]=addBPoint(-1.0,0.0,1.0,s1);
		body[1][0][1]=addBPoint(1.0,0.0,1.0,s1);
		body[0][1][1]=addBPoint(-1.0,1.0,1.0,s1);
		body[1][1][1]=addBPoint(1.0,1.0,1.0,s1);
		
		addLine(body[0][0][1],body[1][0][1],body[1][1][1],body[0][1][1],Renderer3D.CAR_TOP);		

		addLine(body[0][0][0],body[0][0][1],body[0][1][1],body[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(body[1][0][0],body[1][1][0],body[1][1][1],body[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(body[0][1][0],body[0][1][1],body[1][1][1],body[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1],Renderer3D.CAR_BACK);
		
		addLine(body[0][0][0],body[0][1][0],body[1][1][0],body[1][0][0],Renderer3D.CAR_BOTTOM);
		
		
		Segments s2=new Segments(0,front_width*0.5,back_length+y_side,front_length,0,front_height);
		
		BPoint[][][] front=new BPoint[2][2][2];
		
		front[0][0][0]=addBPoint(-1.0,0.0,0,s2);
		front[1][0][0]=addBPoint(1.0,0.0,0,s2);
		front[0][1][0]=addBPoint(-1.0,1.0,0,s2);
		front[1][1][0]=addBPoint(1.0,1.0,0,s2);		
		
		front[0][0][1]=addBPoint(-1.0,0.0,1.0,s2);
		front[1][0][1]=addBPoint(1.0,0.0,1.0,s2);
		front[0][1][1]=addBPoint(-1.0,1.0,1.0,s2);
		front[1][1][1]=addBPoint(1.0,1.0,1.0,s2);
		
		addLine(front[0][0][1],front[1][0][1],front[1][1][1],front[0][1][1],Renderer3D.CAR_TOP);		

		addLine(front[0][0][0],front[0][0][1],front[0][1][1],front[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(front[1][0][0],front[1][1][0],front[1][1][1],front[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(front[0][1][0],front[0][1][1],front[1][1][1],front[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(front[0][0][0],front[1][0][0],front[1][0][1],front[0][0][1],Renderer3D.CAR_BACK);
		
		addLine(front[0][0][0],front[0][1][0],front[1][1][0],front[1][0][0],Renderer3D.CAR_BOTTOM);
		
		
		Segments s3=new Segments(0,roof_width*0.5,back_length,roof_length,z_side,roof_height);
		
		BPoint[][][] roof=new BPoint[2][2][2];
		
		roof[0][0][0]=addBPoint(-1.0,0.0,0,s3);
		roof[1][0][0]=addBPoint(1.0,0.0,0,s3);
		roof[0][1][0]=addBPoint(-1.0,1.0,0,s3);
		roof[1][1][0]=addBPoint(1.0,1.0,0,s3);		
		
		roof[0][0][1]=addBPoint(-1.0,0.0,1.0,s3);
		roof[1][0][1]=addBPoint(1.0,0.0,1.0,s3);
		roof[0][1][1]=addBPoint(-1.0,1.0,1.0,s3);
		roof[1][1][1]=addBPoint(1.0,1.0,1.0,s3);
		
		addLine(roof[0][0][1],roof[1][0][1],roof[1][1][1],roof[0][1][1],Renderer3D.CAR_TOP);		

		addLine(roof[0][0][0],roof[0][0][1],roof[0][1][1],roof[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(roof[1][0][0],roof[1][1][0],roof[1][1][1],roof[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(roof[0][1][0],roof[0][1][1],roof[1][1][1],roof[1][1][0],Renderer3D.CAR_BACK);
		
		addLine(roof[0][0][0],roof[1][0][0],roof[1][0][1],roof[0][0][1],Renderer3D.CAR_BACK);
		
		addLine(roof[0][0][0],roof[0][1][0],roof[1][1][0],roof[1][0][0],Renderer3D.CAR_BOTTOM);
		
		buildWheel(-x_side*0.5,back_length+y_side+front_length-front_overhang,wheel_radius,wheel_radius,wheel_width);
		buildWheel(x_side*0.5-wheel_width,back_length+y_side+front_length-front_overhang,wheel_radius,wheel_radius,wheel_width);
		
		Segments fs=new Segments(0,x_side*0.5,back_length+y_side+front_length,50,0,30);
		
		BPoint[][][] frontSpoiler=new BPoint[2][2][2];
		 
		frontSpoiler[0][0][0]=addBPoint(-1.0,0.0,0,fs);
		frontSpoiler[1][0][0]=addBPoint(1.0,0.0,0,fs);
		frontSpoiler[0][1][0]=addBPoint(-1.0,1.0,0,fs);
		frontSpoiler[1][1][0]=addBPoint(1.0,1.0,0,fs);		
		
		frontSpoiler[0][0][1]=addBPoint(-1.0,0.0,1.0,fs);
		frontSpoiler[1][0][1]=addBPoint(1.0,0.0,1.0,fs);
		frontSpoiler[0][1][1]=addBPoint(-1.0,1.0,1.0,fs);
		frontSpoiler[1][1][1]=addBPoint(1.0,1.0,1.0,fs);
		
		addLine(frontSpoiler[0][0][1],frontSpoiler[1][0][1],frontSpoiler[1][1][1],frontSpoiler[0][1][1],Renderer3D.CAR_TOP);		

		addLine(frontSpoiler[0][0][0],frontSpoiler[0][0][1],frontSpoiler[0][1][1],frontSpoiler[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(frontSpoiler[1][0][0],frontSpoiler[1][1][0],frontSpoiler[1][1][1],frontSpoiler[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(frontSpoiler[0][1][0],frontSpoiler[0][1][1],frontSpoiler[1][1][1],frontSpoiler[1][1][0],Renderer3D.CAR_BACK);
		
		addLine(frontSpoiler[0][0][0],frontSpoiler[1][0][0],frontSpoiler[1][0][1],frontSpoiler[0][0][1],Renderer3D.CAR_BACK);
		
		addLine(frontSpoiler[0][0][0],frontSpoiler[0][1][0],frontSpoiler[1][1][0],frontSpoiler[1][0][0],Renderer3D.CAR_BOTTOM);
	}
	
	private void buildWheel(double rxc, double ryc, double rzc,double r, double wheel_width) {
		
		
		int raysNumber=10;
		
		//back wheel
		
				BPoint[] lRearWheel=new BPoint[raysNumber];
				BPoint[] rRearWheel=new BPoint[raysNumber];

				
				for(int i=0;i<raysNumber;i++){
					
					double teta=i*2*Math.PI/(raysNumber);
					
					double x=rxc;
					double y=ryc+r*Math.sin(teta);
					double z=rzc+r*Math.cos(teta);
					
					lRearWheel[i]=addBPoint(x,y,z);
					rRearWheel[i]=addBPoint(x+wheel_width,y,z);
				}
				
				LineData leftRearWheel=new LineData();
				LineData rightRearWheel=new LineData();
				
				for(int i=0;i<raysNumber;i++){
					
					leftRearWheel.addIndex(lRearWheel[i].getIndex());
					rightRearWheel.addIndex(rRearWheel[raysNumber-1-i].getIndex());
					
				}
				leftRearWheel.setData(""+Renderer3D.CAR_LEFT);
				polyData.add(leftRearWheel);
				rightRearWheel.setData(""+Renderer3D.CAR_RIGHT);
				polyData.add(rightRearWheel);
				
				
				for(int i=0;i<raysNumber;i++){
					
					LineData tyreRearWheel=new LineData();
					tyreRearWheel.addIndex(lRearWheel[i].getIndex());
					tyreRearWheel.addIndex(rRearWheel[i].getIndex());
					tyreRearWheel.addIndex(rRearWheel[(i+1)%raysNumber].getIndex());
					tyreRearWheel.addIndex(lRearWheel[(i+1)%raysNumber].getIndex());
					tyreRearWheel.setData(""+Renderer3D.CAR_BOTTOM);
					
					polyData.add(tyreRearWheel);
				}		
			
		
	}

}
