package com.editors.animals.data;

import java.util.Vector;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import com.BPoint;
import com.CustomData;
import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.main.Renderer3D;

public class Animal extends CustomData{



	double x_side=0;
	double y_side=0;
	double z_side=0;

	double nw_x=0;
	double nw_y=0;


	public Animal(){}

	public Animal( double nw_x, double nw_y,double x_side, double y_side,double z_side
			) {
		super();

		this.x_side = x_side;
		this.y_side = y_side;
		this.z_side = z_side;
		this.nw_x = nw_x;
		this.nw_y = nw_y;


	}

	public Object clone(){

		Animal grid=new Animal(nw_x,nw_y,x_side,y_side,z_side);
		return grid;

	}


	public double getX_side() {
		return x_side;
	}
	public void setX_side(double x_side) {
		this.x_side = x_side;
	}
	public double getY_side() {
		return y_side;
	}
	public void setY_side(double y_side) {
		this.y_side = y_side;
	}


	public String toString() {

		return nw_x+","+nw_y+","+x_side+","+y_side+","+z_side;
	}

	public static Animal buildPlan(String str) {

		String[] vals = str.split(",");

		double nw_x =Double.parseDouble(vals[0]);
		double nw_y = Double.parseDouble(vals[1]);
		double x_side =Double.parseDouble(vals[2]);
		double y_side = Double.parseDouble(vals[3]);
		double z_side = Double.parseDouble(vals[4]); 


		Animal grid=new Animal(nw_x,nw_y,x_side,y_side,z_side);

		return grid;
	}



	public double getZ_side() {
		return z_side;
	}

	public void setZ_side(double z_side) {
		this.z_side = z_side;
	}

	public double getNw_x() {
		return nw_x;
	}

	public void setNw_x(double nw_x) {
		this.nw_x = nw_x;
	}

	public double getNw_y() {
		return nw_y;
	}

	public void setNw_y(double nw_y) {
		this.nw_y = nw_y;
	}

	public int pos(int i, int j, int k){

		return (i+(1+1)*j)*2+k;
	}



	

	public PolygonMesh buildMesh(){

		int xnum=1;
		int ynum=1;


		Vector points=new Vector();
		points.setSize(50);

		Vector polyData=new Vector();
		
		int n=0;
		
		double leg_side=10;
		double leg_lenght=100;

		//basic sides:

		n=buildBox(0,0,leg_lenght,points,polyData,n,x_side,y_side,z_side);
		
		
		//legs:	
		//backLeftLeg
		n=buildBox(0,0,0,points,polyData,n,leg_side,leg_side,leg_lenght);
		
		//backRightLeg
		n=buildBox(x_side-leg_side,0,0,points,polyData,n,leg_side,leg_side,leg_lenght);
		
		
		//frontLeftLeg
		n=buildBox(0,y_side-leg_side,0,points,polyData,n,leg_side,leg_side,leg_lenght);
		
	
		//frontRightLeg
		n=buildBox(x_side-leg_side,y_side-leg_side,0,points,polyData,n,leg_side,leg_side,leg_lenght);
		
		
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;


	}

	

	



}	