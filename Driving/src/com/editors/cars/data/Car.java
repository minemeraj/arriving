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


    double front_length=0;
    double back_length=0;
    double roof_height=0;

	public Car(){}

	public Car( double x_side, double y_side,double z_side, double front_length,double back_length,double roof_height
			) {
		super();

		this.x_side = x_side;
		this.y_side = y_side;
		this.z_side = z_side;
		this.front_length = front_length;
		this.back_length = back_length;
		this.roof_height = roof_height;

	}

	public Object clone(){

		Car grid=new Car(x_side,y_side,z_side,front_length,back_length,roof_height);
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

		return "C="+x_side+","+y_side+","+z_side+","+back_length+","+front_length+","+roof_height;
	}

	public static Car buildCar(String str) {

		String[] vals = str.split(",");


		double x_side =Double.parseDouble(vals[0]);
		double y_side = Double.parseDouble(vals[1]);
		double z_side = Double.parseDouble(vals[2]); 
		double back_length= Double.parseDouble(vals[3]); 
		double front_length= Double.parseDouble(vals[4]);		
		double roof_height= Double.parseDouble(vals[5]); 

		Car grid=new Car(x_side,y_side,z_side,front_length,back_length,roof_height);

		return grid;
	}



	public double getZ_side() {
		return z_side;
	}

	public void setZ_side(double z_side) {
		this.z_side = z_side;
	}
	
	public double getFront_length() {
		return front_length;
	}

	public void setFront_length(double front_length) {
		this.front_length = front_length;
	}

	public double getBack_length() {
		return back_length;
	}

	public void setBack_length(double back_length) {
		this.back_length = back_length;
	}
	

	public double getRoof_height() {
		return roof_height;
	}

	public void setRoof_height(double roof_height) {
		this.roof_height = roof_height;
	}


	public int pos(int i, int j, int k){

		return (i+(1+1)*j)*2+k;
	}



	public PolygonMesh buildMesh(){



		Vector points=new Vector();
		points.setSize(50);

		Vector polyData=new Vector();

		//basic sides:
		
		//double front_height=z_side;
		//double back_height=z_side;
		
		int n=0;
		
		
		//main body:

		BPoint p000=new BPoint(0,back_length,0,n++);
		BPoint p100=new BPoint(x_side,back_length,0,n++);
		BPoint p110=new BPoint(x_side,back_length+y_side,0,n++);
		BPoint p010=new BPoint(0,back_length+y_side,0,n++);
		
		BPoint p001=new BPoint(0,back_length,z_side,n++);	
		BPoint p101=new BPoint(x_side,back_length,z_side,n++);
		BPoint p111=new BPoint(x_side,back_length+y_side,z_side,n++);
		BPoint p011=new BPoint(0,back_length+y_side,z_side,n++);		
		

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

		/*LineData backLD=buildLine(p000,p100,p101,p001,Renderer3D.CAR_BACK);
		polyData.add(backLD);

		LineData frontLD=buildLine(p010,p011,p111,p110,Renderer3D.CAR_FRONT);
		polyData.add(frontLD);*/
		
		
		//back part:
		BPoint pBack000=new BPoint(0,0,0,n++);
		BPoint pBack100=new BPoint(x_side,0,0,n++);
		BPoint pBack101=new BPoint(x_side,0,z_side,n++);
		BPoint pBack001=new BPoint(0,0,z_side,n++);
		
		points.setElementAt(pBack000,pBack000.getIndex());
		points.setElementAt(pBack100,pBack100.getIndex());
		points.setElementAt(pBack101,pBack101.getIndex());
		points.setElementAt(pBack001,pBack001.getIndex());
		
		LineData backMask=buildLine(pBack000,pBack100,pBack101,pBack001,Renderer3D.CAR_RIGHT);
		polyData.add(backMask);
		
		LineData backTop=buildLine(pBack001,pBack101,p101,p001,Renderer3D.CAR_TOP);
		polyData.add(backTop);
		
		LineData backBottom=buildLine(pBack100,pBack000,p000,p100,Renderer3D.CAR_BOTTOM);
		polyData.add(backBottom);
		
		LineData backLeft=buildLine(pBack000,pBack001,p001,p000,Renderer3D.CAR_LEFT);
		polyData.add(backLeft);
		
		LineData backRight=buildLine(pBack101,pBack100,p100,p101,Renderer3D.CAR_RIGHT);
		polyData.add(backRight);
		
	
		//front:		
		BPoint pFront000=new BPoint(0,back_length+y_side+front_length,0,n++);
		BPoint pFront100=new BPoint(x_side,back_length+y_side+front_length,0,n++);
		BPoint pFront101=new BPoint(x_side,back_length+y_side+front_length,z_side,n++);
		BPoint pFront001=new BPoint(0,back_length+y_side+front_length,z_side,n++);
		
		
		points.setElementAt(pFront000,pFront000.getIndex());
		points.setElementAt(pFront100,pFront100.getIndex());
		points.setElementAt(pFront101,pFront101.getIndex());
		points.setElementAt(pFront001,pFront001.getIndex());
		
		LineData frontMask=buildLine(pFront000,pFront001,pFront101,pFront100,Renderer3D.CAR_RIGHT);
		polyData.add(frontMask);
		
		LineData frontTop=buildLine(p011,p111,pFront101,pFront001,Renderer3D.CAR_TOP);
		polyData.add(frontTop);
		
		LineData frontBottom=buildLine(p110,p010,pFront000,pFront100,Renderer3D.CAR_BOTTOM);
		polyData.add(frontBottom);
		
		LineData frontLeft=buildLine(p010,p011,pFront001,pFront000,Renderer3D.CAR_LEFT);
		polyData.add(frontLeft);
		
		LineData frontRight=buildLine(p111,p110,pFront100,pFront101,Renderer3D.CAR_RIGHT);
		polyData.add(frontRight);
		
		//roof:
		
		
		
		BPoint pr000=new BPoint(0,back_length,z_side,n++);
		BPoint pr100=new BPoint(x_side,back_length,z_side,n++);
		BPoint pr110=new BPoint(x_side,back_length+y_side,z_side,n++);
		BPoint pr010=new BPoint(0,back_length+y_side,z_side,n++);	
		
		double yy0Up=back_length+10;
		double yy1Up=back_length+y_side-10;
		
		BPoint pr001=new BPoint(0,yy0Up,z_side+roof_height,n++);	
		BPoint pr101=new BPoint(x_side,yy0Up,z_side+roof_height,n++);
		BPoint pr111=new BPoint(x_side,yy1Up,z_side+roof_height,n++);
		BPoint pr011=new BPoint(0,yy1Up,z_side+roof_height,n++);	
		
		
		
		
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