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

		return nw_x+","+nw_y+","+x_side+","+y_side+","+z_side;
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

		int xnum=1;
		int ynum=1;


		Vector points=new Vector();
		points.setSize(50);

		Vector polyData=new Vector();
		
		int n=0;
		
		double leg_side=10;
		double leg_lenght=100;

		//basic sides:

		BPoint p000=new BPoint(0,0,leg_lenght,n++);
		BPoint p100=new BPoint(x_side,0,leg_lenght,n++);
		BPoint p010=new BPoint(0,y_side,leg_lenght,n++);
		BPoint p001=new BPoint(0,0,leg_lenght+z_side,n++);
		BPoint p110=new BPoint(x_side,y_side,leg_lenght,n++);
		BPoint p011=new BPoint(0,y_side,leg_lenght+z_side,n++);
		BPoint p101=new BPoint(x_side,0,leg_lenght+z_side,n++);
		BPoint p111=new BPoint(x_side,y_side,leg_lenght+z_side,n++);

		points.setElementAt(p000,p000.getIndex());
		points.setElementAt(p100,p100.getIndex());
		points.setElementAt(p010,p010.getIndex());
		points.setElementAt(p001,p001.getIndex());
		points.setElementAt(p110,p110.getIndex());
		points.setElementAt(p011,p011.getIndex());
		points.setElementAt(p101,p101.getIndex());
		points.setElementAt(p111,p111.getIndex());


		LineData topLD=buildLine(p001,p101,p111,p011,Renderer3D.CAR_TOP);
		polyData.add(topLD);
		
		LineData bottomLD=buildLine(p000,p010,p110,p100,Renderer3D.CAR_BOTTOM);
		polyData.add(bottomLD);

		LineData leftLD=buildLine(p000,p001,p011,p010,Renderer3D.CAR_LEFT);
		polyData.add(leftLD);


		LineData rightLD=buildLine(p100,p110,p111,p101,Renderer3D.CAR_RIGHT);
		polyData.add(rightLD);


		LineData backLD=buildLine(p000,p100,p101,p001,Renderer3D.CAR_BACK);
		polyData.add(backLD);

		LineData frontLD=buildLine(p010,p011,p111,p110,Renderer3D.CAR_FRONT);
		polyData.add(frontLD);
		
		
		//legs:	
		
		//backLeftLeg
		
		BPoint pBackLeftLeg000=new BPoint(0,0,0,n++);
		BPoint pBackLeftLeg100=new BPoint(leg_side,0,0,n++);
		BPoint pBackLeftLeg110=new BPoint(leg_side,leg_side,0,n++);
		BPoint pBackLeftLeg010=new BPoint(0,leg_side,0,n++);
		
		points.setElementAt(pBackLeftLeg000,pBackLeftLeg000.getIndex());
		points.setElementAt(pBackLeftLeg100,pBackLeftLeg100.getIndex());
		points.setElementAt(pBackLeftLeg110,pBackLeftLeg110.getIndex());
		points.setElementAt(pBackLeftLeg010,pBackLeftLeg010.getIndex());
		
		LineData backLeftLeg=buildLine(pBackLeftLeg000,pBackLeftLeg010,pBackLeftLeg110,pBackLeftLeg100,Renderer3D.CAR_FRONT);
		polyData.add(backLeftLeg);

		
		BPoint pBackLeftLeg001=new BPoint(0,0,leg_lenght,n++);
		BPoint pBackLeftLeg101=new BPoint(leg_side,0,leg_lenght,n++);
		BPoint pBackLeftLeg111=new BPoint(leg_side,leg_side,leg_lenght,n++);
		BPoint pBackLeftLeg011=new BPoint(0,leg_side,leg_lenght,n++);
		
		points.setElementAt(pBackLeftLeg001,pBackLeftLeg001.getIndex());
		points.setElementAt(pBackLeftLeg101,pBackLeftLeg101.getIndex());
		points.setElementAt(pBackLeftLeg111,pBackLeftLeg111.getIndex());
		points.setElementAt(pBackLeftLeg011,pBackLeftLeg011.getIndex());
		
		LineData backLeftLegS0=buildLine(pBackLeftLeg000,pBackLeftLeg001,pBackLeftLeg011,pBackLeftLeg010,Renderer3D.CAR_LEFT);
		polyData.add(backLeftLegS0);
		LineData backLeftLegS1=buildLine(pBackLeftLeg010,pBackLeftLeg011,pBackLeftLeg111,pBackLeftLeg110,Renderer3D.CAR_FRONT);
		polyData.add(backLeftLegS1);
		LineData backLeftLegS2=buildLine(pBackLeftLeg110,pBackLeftLeg111,pBackLeftLeg101,pBackLeftLeg100,Renderer3D.CAR_RIGHT);
		polyData.add(backLeftLegS2);
		LineData backLeftLegS3=buildLine(pBackLeftLeg100,pBackLeftLeg101,pBackLeftLeg001,pBackLeftLeg000,Renderer3D.CAR_BACK);
		polyData.add(backLeftLegS3);
		
		//backRightLeg
		
		BPoint pBackRightLeg000=new BPoint(x_side-leg_side,0,0,n++);
		BPoint pBackRightLeg100=new BPoint(x_side,0,0,n++);
		BPoint pBackRightLeg110=new BPoint(x_side,leg_side,0,n++);
		BPoint pBackRightLeg010=new BPoint(x_side-leg_side,leg_side,0,n++);
		
		points.setElementAt(pBackRightLeg000,pBackRightLeg000.getIndex());
		points.setElementAt(pBackRightLeg100,pBackRightLeg100.getIndex());
		points.setElementAt(pBackRightLeg110,pBackRightLeg110.getIndex());
		points.setElementAt(pBackRightLeg010,pBackRightLeg010.getIndex());
		
		LineData backRightLeg=buildLine(pBackRightLeg000,pBackRightLeg010,pBackRightLeg110,pBackRightLeg100,Renderer3D.CAR_FRONT);
		polyData.add(backRightLeg);

		
		BPoint pBackRightLeg001=new BPoint(x_side-leg_side,0,leg_lenght,n++);
		BPoint pBackRightLeg101=new BPoint(x_side,0,leg_lenght,n++);
		BPoint pBackRightLeg111=new BPoint(x_side,leg_side,leg_lenght,n++);
		BPoint pBackRightLeg011=new BPoint(x_side-leg_side,leg_side,leg_lenght,n++);
		
		points.setElementAt(pBackRightLeg001,pBackRightLeg001.getIndex());
		points.setElementAt(pBackRightLeg101,pBackRightLeg101.getIndex());
		points.setElementAt(pBackRightLeg111,pBackRightLeg111.getIndex());
		points.setElementAt(pBackRightLeg011,pBackRightLeg011.getIndex());
		
		LineData backRightLegS0=buildLine(pBackRightLeg000,pBackRightLeg001,pBackRightLeg011,pBackRightLeg010,Renderer3D.CAR_LEFT);
		polyData.add(backRightLegS0);
		LineData backRightLegS1=buildLine(pBackRightLeg010,pBackRightLeg011,pBackRightLeg111,pBackRightLeg110,Renderer3D.CAR_FRONT);
		polyData.add(backRightLegS1);
		LineData backRightLegS2=buildLine(pBackRightLeg110,pBackRightLeg111,pBackRightLeg101,pBackRightLeg100,Renderer3D.CAR_RIGHT);
		polyData.add(backRightLegS2);
		LineData backRightLegS3=buildLine(pBackRightLeg100,pBackRightLeg101,pBackRightLeg001,pBackRightLeg000,Renderer3D.CAR_BACK);
		polyData.add(backRightLegS3);
		
		//frontLeftLeg
		
		BPoint pFrontLeftLeg000=new BPoint(0,y_side-leg_side,0,n++);
		BPoint pFrontLeftLeg100=new BPoint(leg_side,y_side-leg_side,0,n++);
		BPoint pFrontLeftLeg110=new BPoint(leg_side,y_side,0,n++);
		BPoint pFrontLeftLeg010=new BPoint(0,y_side,0,n++);
		
		points.setElementAt(pFrontLeftLeg000,pFrontLeftLeg000.getIndex());
		points.setElementAt(pFrontLeftLeg100,pFrontLeftLeg100.getIndex());
		points.setElementAt(pFrontLeftLeg110,pFrontLeftLeg110.getIndex());
		points.setElementAt(pFrontLeftLeg010,pFrontLeftLeg010.getIndex());
		
		LineData FrontLeftLeg=buildLine(pFrontLeftLeg000,pFrontLeftLeg010,pFrontLeftLeg110,pFrontLeftLeg100,Renderer3D.CAR_FRONT);
		polyData.add(FrontLeftLeg);

		
		BPoint pFrontLeftLeg001=new BPoint(0,y_side-leg_side,leg_lenght,n++);
		BPoint pFrontLeftLeg101=new BPoint(leg_side,y_side-leg_side,leg_lenght,n++);
		BPoint pFrontLeftLeg111=new BPoint(leg_side,y_side,leg_lenght,n++);
		BPoint pFrontLeftLeg011=new BPoint(0,y_side,leg_lenght,n++);
		
		points.setElementAt(pFrontLeftLeg001,pFrontLeftLeg001.getIndex());
		points.setElementAt(pFrontLeftLeg101,pFrontLeftLeg101.getIndex());
		points.setElementAt(pFrontLeftLeg111,pFrontLeftLeg111.getIndex());
		points.setElementAt(pFrontLeftLeg011,pFrontLeftLeg011.getIndex());
		
		LineData FrontLeftLegS0=buildLine(pFrontLeftLeg000,pFrontLeftLeg001,pFrontLeftLeg011,pFrontLeftLeg010,Renderer3D.CAR_LEFT);
		polyData.add(FrontLeftLegS0);
		LineData FrontLeftLegS1=buildLine(pFrontLeftLeg010,pFrontLeftLeg011,pFrontLeftLeg111,pFrontLeftLeg110,Renderer3D.CAR_FRONT);
		polyData.add(FrontLeftLegS1);
		LineData FrontLeftLegS2=buildLine(pFrontLeftLeg110,pFrontLeftLeg111,pFrontLeftLeg101,pFrontLeftLeg100,Renderer3D.CAR_RIGHT);
		polyData.add(FrontLeftLegS2);
		LineData FrontLeftLegS3=buildLine(pFrontLeftLeg100,pFrontLeftLeg101,pFrontLeftLeg001,pFrontLeftLeg000,Renderer3D.CAR_BACK);
		polyData.add(FrontLeftLegS3);
		
		//frontRightLeg
		
		BPoint pFrontRightLeg000=new BPoint(x_side-leg_side,y_side-leg_side,0,n++);
		BPoint pFrontRightLeg100=new BPoint(x_side,y_side-leg_side,0,n++);
		BPoint pFrontRightLeg110=new BPoint(x_side,y_side,0,n++);
		BPoint pFrontRightLeg010=new BPoint(x_side-leg_side,y_side,0,n++);
		
		points.setElementAt(pFrontRightLeg000,pFrontRightLeg000.getIndex());
		points.setElementAt(pFrontRightLeg100,pFrontRightLeg100.getIndex());
		points.setElementAt(pFrontRightLeg110,pFrontRightLeg110.getIndex());
		points.setElementAt(pFrontRightLeg010,pFrontRightLeg010.getIndex());
		
		LineData FrontRightLeg=buildLine(pFrontRightLeg000,pFrontRightLeg010,pFrontRightLeg110,pFrontRightLeg100,Renderer3D.CAR_FRONT);
		polyData.add(FrontRightLeg);

		
		BPoint pFrontRightLeg001=new BPoint(x_side-leg_side,y_side-leg_side,leg_lenght,n++);
		BPoint pFrontRightLeg101=new BPoint(x_side,y_side-leg_side,leg_lenght,n++);
		BPoint pFrontRightLeg111=new BPoint(x_side,y_side,leg_lenght,n++);
		BPoint pFrontRightLeg011=new BPoint(x_side-leg_side,y_side,leg_lenght,n++);
		
		points.setElementAt(pFrontRightLeg001,pFrontRightLeg001.getIndex());
		points.setElementAt(pFrontRightLeg101,pFrontRightLeg101.getIndex());
		points.setElementAt(pFrontRightLeg111,pFrontRightLeg111.getIndex());
		points.setElementAt(pFrontRightLeg011,pFrontRightLeg011.getIndex());
		
		LineData frontRightLegS0=buildLine(pFrontRightLeg000,pFrontRightLeg001,pFrontRightLeg011,pFrontRightLeg010,Renderer3D.CAR_LEFT);
		polyData.add(frontRightLegS0);
		LineData frontRightLegS1=buildLine(pFrontRightLeg010,pFrontRightLeg011,pFrontRightLeg111,pFrontRightLeg110,Renderer3D.CAR_FRONT);
		polyData.add(frontRightLegS1);
		LineData frontRightLegS2=buildLine(pFrontRightLeg110,pFrontRightLeg111,pFrontRightLeg101,pFrontRightLeg100,Renderer3D.CAR_RIGHT);
		polyData.add(frontRightLegS2);
		LineData frontRightLegS3=buildLine(pFrontRightLeg100,pFrontRightLeg101,pFrontRightLeg001,pFrontRightLeg000,Renderer3D.CAR_BACK);
		polyData.add(frontRightLegS3);
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;


	}

	

	



}	