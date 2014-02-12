package com.editors.cars.data;

import java.util.Vector;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import com.BPoint;
import com.CustomData;
import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.main.Renderer3D;

public class Car extends CustomData {



	double x_side=0;
	double y_side=0;
	double z_side=0;



	public Car(){}

	public Car( double x_side, double y_side,double z_side
			) {
		super();

		this.x_side = x_side;
		this.y_side = y_side;
		this.z_side = z_side;
	


	}

	public Object clone(){

		Car grid=new Car(x_side,y_side,z_side);
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

		return "C="+x_side+","+y_side+","+z_side;
	}

	public static Car buildCar(String str) {

		String[] vals = str.split(",");


		double x_side =Double.parseDouble(vals[0]);
		double y_side = Double.parseDouble(vals[1]);
		double z_side = Double.parseDouble(vals[2]); 


		Car grid=new Car(x_side,y_side,z_side);

		return grid;
	}



	public double getZ_side() {
		return z_side;
	}

	public void setZ_side(double z_side) {
		this.z_side = z_side;
	}


	public int pos(int i, int j, int k){

		return (i+(1+1)*j)*2+k;
	}



	public PolygonMesh buildMesh(){




		Vector points=new Vector();
		points.setSize(50);

		Vector polyData=new Vector();

		//basic sides:
		
		int n=0;

		BPoint p000=new BPoint(0,0,0,n++);
		BPoint p100=new BPoint(x_side,0,0,n++);
		BPoint p010=new BPoint(0,y_side,0,n++);
		BPoint p001=new BPoint(0,0,z_side,n++);
		BPoint p110=new BPoint(x_side,y_side,0,n++);
		BPoint p011=new BPoint(0,y_side,z_side,n++);
		BPoint p101=new BPoint(x_side,0,z_side,n++);
		BPoint p111=new BPoint(x_side,y_side,z_side,n++);

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
		
		//roof:
		
		int roof_height=100;

		BPoint pr000=new BPoint(0,0,z_side,n++);
		BPoint pr100=new BPoint(x_side,0,z_side,n++);
		BPoint pr010=new BPoint(0,y_side,z_side,n++);
		BPoint pr001=new BPoint(0,0,z_side+roof_height,n++);
		BPoint pr110=new BPoint(x_side,y_side,z_side,n++);
		BPoint pr011=new BPoint(0,y_side,z_side+roof_height,n++);
		BPoint pr101=new BPoint(x_side,0,z_side+roof_height,n++);
		BPoint pr111=new BPoint(x_side,y_side,z_side+roof_height,n++);
		
		points.setElementAt(pr000,pr000.getIndex());
		points.setElementAt(pr100,pr100.getIndex());
		points.setElementAt(pr010,pr010.getIndex());
		points.setElementAt(pr001,pr001.getIndex());
		points.setElementAt(pr110,pr110.getIndex());
		points.setElementAt(pr011,pr011.getIndex());
		points.setElementAt(pr101,pr101.getIndex());
		points.setElementAt(pr111,pr111.getIndex());
		
		LineData topRLD=buildLine(pr001,pr101,pr111,pr011,Renderer3D.CAR_TOP);
		polyData.add(topRLD);


		//LineData bottomRLD=buildLine(pr000,pr010,pr110,pr100,Renderer3D.CAR_BOTTOM);
		//polyData.add(bottomRLD);

		LineData leftRLD=buildLine(pr000,pr001,pr011,pr010,Renderer3D.CAR_LEFT);
		polyData.add(leftRLD);


		LineData rightRLD=buildLine(pr100,pr110,pr111,pr101,Renderer3D.CAR_RIGHT);
		polyData.add(rightRLD);

		LineData backRLD=buildLine(pr000,pr100,pr101,pr001,Renderer3D.CAR_BACK);
		polyData.add(backRLD);

		LineData frontRLD=buildLine(pr010,pr011,pr111,pr110,Renderer3D.CAR_FRONT);
		polyData.add(frontRLD);
		

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;


	}



}	