package com.editors.plants.data;

import java.util.Vector;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import com.BPoint;
import com.CustomData;
import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.main.Renderer3D;

public class Plant extends CustomData{



	double x_side=0;
	double y_side=0;
	double z_side=0;

	double nw_x=0;
	double nw_y=0;


	public Plant(){}

	public Plant( double nw_x, double nw_y,double x_side, double y_side,double z_side
			) {
		super();

		this.x_side = x_side;
		this.y_side = y_side;
		this.z_side = z_side;
		this.nw_x = nw_x;
		this.nw_y = nw_y;


	}

	public Object clone(){

		Plant grid=new Plant(nw_x,nw_y,x_side,y_side,z_side);
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

		return "F="+nw_x+","+nw_y+","+x_side+","+y_side+","+z_side;
	}

	public static Plant buildPlan(String str) {

		String[] vals = str.split(",");

		double nw_x =Double.parseDouble(vals[0]);
		double nw_y = Double.parseDouble(vals[1]);
		double x_side =Double.parseDouble(vals[2]);
		double y_side = Double.parseDouble(vals[3]);
		double z_side = Double.parseDouble(vals[4]); 


		Plant grid=new Plant(nw_x,nw_y,x_side,y_side,z_side);

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



		Vector points=new Vector();
		points.setSize(50);

		Vector polyData=new Vector();
		
		int nBase=12;
		
		int n=0;
		
		double leg_side=10;
		double leg_lenght=100;

		//basic sides:
		
		BPoint[] upoints=new BPoint[nBase];
		BPoint[] bpoints=new BPoint[nBase];
		
		double r=50;
		
		for (int i = 0; i < nBase; i++) {
			
			double x=r*Math.cos(2*Math.PI/nBase*i);
			double y=r*Math.sin(2*Math.PI/nBase*i);
			
			upoints[i]=new BPoint(x,y,200,n++);
			points.setElementAt(upoints[i],upoints[i].getIndex());
			
		}


		LineData topLD=new LineData();
		
		for (int i = 0; i < nBase; i++) {
			
			topLD.addIndex(upoints[i].getIndex());
			
		}
		
		polyData.add(topLD);
		
		for (int i = 0; i < nBase; i++) {
			
			double x=r*Math.cos(2*Math.PI/nBase*i);
			double y=r*Math.sin(2*Math.PI/nBase*i);
			
			bpoints[i]=new BPoint(x,y,0,n++);
			points.setElementAt(bpoints[i],bpoints[i].getIndex());
			
		}


		LineData bottomLD=new LineData();
		
		for (int i = nBase-1; i >=0; i--) {
			
			bottomLD.addIndex(bpoints[i].getIndex());
			
		}
		
		polyData.add(bottomLD);
		
		
		
		for (int i = 0; i < nBase; i++) {
			
			LineData sideLD=new LineData();
			
			sideLD.addIndex(bpoints[i].getIndex());
			sideLD.addIndex(bpoints[(i+1)%nBase].getIndex());
			sideLD.addIndex(upoints[(i+1)%nBase].getIndex());
			sideLD.addIndex(upoints[i].getIndex());			
			polyData.add(sideLD);
			
		}
		
		
	
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;


	}

	

	



}	