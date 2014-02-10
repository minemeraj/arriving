package com.editors.forniture.data;

import java.util.Vector;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.main.Renderer3D;

public class Forniture {



	double x_side=0;
	double y_side=0;
	double z_side=0;

	double nw_x=0;
	double nw_y=0;


	public Forniture(){}

	public Forniture( double nw_x, double nw_y,double x_side, double y_side,double z_side
			) {
		super();

		this.x_side = x_side;
		this.y_side = y_side;
		this.z_side = z_side;
		this.nw_x = nw_x;
		this.nw_y = nw_y;


	}

	public Object clone(){

		Forniture grid=new Forniture(nw_x,nw_y,x_side,y_side,z_side);
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

	public static Forniture buildPlan(String str) {

		String[] vals = str.split(",");

		double nw_x =Double.parseDouble(vals[0]);
		double nw_y = Double.parseDouble(vals[1]);
		double x_side =Double.parseDouble(vals[2]);
		double y_side = Double.parseDouble(vals[3]);
		double z_side = Double.parseDouble(vals[4]); 


		Forniture grid=new Forniture(nw_x,nw_y,x_side,y_side,z_side);

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
		n=buildLeg(0,0,0,points,polyData,n,leg_side,leg_lenght);
		
		//backRightLeg
		n=buildLeg(x_side-leg_side,0,0,points,polyData,n,leg_side,leg_lenght);
		
		
		//frontLeftLeg
		n=buildLeg(0,y_side-leg_side,0,points,polyData,n,leg_side,leg_lenght);
		
	
		//frontRightLeg
		n=buildLeg(x_side-leg_side,y_side-leg_side,0,points,polyData,n,leg_side,leg_lenght);
		
		
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;


	}

	private int buildLeg(double x , double y, double z, Vector points, Vector polyData,
			int n, double leg_side, double leg_lenght) {
		
		BPoint pLeg000=new BPoint(x,y,z,n++);
		BPoint pLeg100=new BPoint(x+leg_side,y,z,n++);
		BPoint pLeg110=new BPoint(x+leg_side,y+leg_side,z,n++);
		BPoint pLeg010=new BPoint(x,y+leg_side,z,n++);
		
		points.setElementAt(pLeg000,pLeg000.getIndex());
		points.setElementAt(pLeg100,pLeg100.getIndex());
		points.setElementAt(pLeg110,pLeg110.getIndex());
		points.setElementAt(pLeg010,pLeg010.getIndex());
		
		LineData Leg=buildLine(pLeg000,pLeg010,pLeg110,pLeg100,Renderer3D.CAR_BOTTOM);
		polyData.add(Leg);
		
		
		BPoint pLeg001=new BPoint(x,y,z+leg_lenght,n++);
		BPoint pLeg101=new BPoint(x+leg_side,y,leg_lenght,n++);
		BPoint pLeg111=new BPoint(x+leg_side,y+leg_side,z+leg_lenght,n++);
		BPoint pLeg011=new BPoint(x,y+leg_side,z+leg_lenght,n++);
		
		points.setElementAt(pLeg001,pLeg001.getIndex());
		points.setElementAt(pLeg101,pLeg101.getIndex());
		points.setElementAt(pLeg111,pLeg111.getIndex());
		points.setElementAt(pLeg011,pLeg011.getIndex());
		
		LineData LegS0=buildLine(pLeg000,pLeg001,pLeg011,pLeg010,Renderer3D.CAR_LEFT);
		polyData.add(LegS0);
		LineData LegS1=buildLine(pLeg010,pLeg011,pLeg111,pLeg110,Renderer3D.CAR_FRONT);
		polyData.add(LegS1);
		LineData LegS2=buildLine(pLeg110,pLeg111,pLeg101,pLeg100,Renderer3D.CAR_RIGHT);
		polyData.add(LegS2);
		LineData LegS3=buildLine(pLeg100,pLeg101,pLeg001,pLeg000,Renderer3D.CAR_BACK);
		polyData.add(LegS3);
		
		return n;
	}

	private void translatePoints(Vector points, double dx, double dy) { 

		for (int i = 0; i < points.size(); i++) {
			BPoint point = (BPoint) points.elementAt(i);
			if(point==null)
				continue;
			point.translate(dx,dy,0);
		}

	}

	private LineData buildLine(BPoint p0, BPoint p1, BPoint p2,
			BPoint p3, int face) {

		LineData ld=new LineData();

		ld.addIndex(p0.getIndex());
		ld.addIndex(p1.getIndex());					
		ld.addIndex(p2.getIndex());
		if(p3!=null)
			ld.addIndex(p3.getIndex());	
		ld.setData(""+face);

		return ld;
	}



	class BPoint extends Point3D{

		public BPoint(double x, double y, double z, int index) {
			super(x, y, z);
			this.index = index;
		}

		int index=-1;

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}


	}




}	