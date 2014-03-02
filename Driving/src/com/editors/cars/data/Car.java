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

	double front_width=0;
    double front_length=0;
    double front_height=0;
    double back_width=0;
    double back_length=0;
    double back_height=0;
    
    double roof_width=0;
    double roof_length=0;
    double roof_height=0;
    
    public static int CAR_TYPE_CAR=0;
    public static int CAR_TYPE_TRUCK=1;
    
    public int car_type=CAR_TYPE_CAR;

	public Car(){}

	public Car(int car_type, double x_side, double y_side,double z_side,
			double front_width,double front_length,double front_height,
			double back_width,double back_length,double back_height,
			 double roof_width,double roof_length,double roof_height
			) {
		super();

		this.car_type = car_type;
		
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

	}

	public Object clone(){

		Car grid=new Car(
				car_type,
				x_side,y_side,z_side,
				front_width,front_length,front_height,
				back_width,back_length,back_height,
				roof_width,roof_length,roof_height);
		return grid;

	}
	

	public String toString() {

		return "C="+car_type+","+x_side+","+y_side+","+z_side+","
		+front_width+","+front_length+","+front_height+","
		+back_width+","+back_length+","+back_height+
		","+roof_width+","+roof_length+","+roof_height;
	}

	public static Car buildCar(String str) {

		String[] vals = str.split(",");


		int car_type=Integer.parseInt(vals[0]);
		double x_side =Double.parseDouble(vals[1]);
		double y_side = Double.parseDouble(vals[2]);
		double z_side = Double.parseDouble(vals[3]); 
		double front_width= Double.parseDouble(vals[4]);
		double front_length= Double.parseDouble(vals[5]);		
		double front_height= Double.parseDouble(vals[6]);
		double back_width= Double.parseDouble(vals[7]);
		double back_length= Double.parseDouble(vals[8]);		 
		double back_height= Double.parseDouble(vals[9]); 		
		double roof_width= Double.parseDouble(vals[10]); 
		double roof_length= Double.parseDouble(vals[11]); 
		double roof_height= Double.parseDouble(vals[12]); 

		Car grid=new Car(car_type,
				x_side,y_side,z_side,
				front_width,front_length,front_height,
				back_width,back_length,back_height,
				roof_width,roof_length,roof_height);

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
	
	public double getFront_width() {
		return front_width;
	}

	public void setFront_width(double front_width) {
		this.front_width = front_width;
	}

	public double getBack_width() {
		return back_width;
	}

	public void setBack_width(double back_width) {
		this.back_width = back_width;
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

	public double getFront_height() {
		return front_height;
	}

	public void setFront_height(double front_height) {
		this.front_height = front_height;
	}

	public double getBack_height() {
		return back_height;
	}

	public void setBack_height(double back_height) {
		this.back_height = back_height;
	}

	public double getRoof_width() {
		return roof_width;
	}

	public void setRoof_width(double roof_width) {
		this.roof_width = roof_width;
	}

	public double getRoof_length() {
		return roof_length;
	}

	public void setRoof_length(double roof_length) {
		this.roof_length = roof_length;
	}

	public int getCar_type() {
		return car_type;
	}

	public void setCar_type(int car_type) {
		this.car_type = car_type;
	}

	
	public PolygonMesh buildMesh(){


		if(car_type==CAR_TYPE_CAR)
			return buildCarMesh();
		else if(car_type==CAR_TYPE_TRUCK)
			return buildTruckMesh();
		else
			return buildCarMesh();
		


	}


	private PolygonMesh buildCarMesh() {
		
		points=new Vector();
		points.setSize(50);

		polyData=new Vector();

		//basic sides:
		
		double xc=x_side*0.5;
		
		n=0;
		
		
		//main body:

		BPoint p000=addBPoint(xc-x_side*0.5,back_length,0);
		BPoint p100=addBPoint(xc+x_side*0.5,back_length,0);
		BPoint p110=addBPoint(xc+x_side*0.5,back_length+y_side,0);
		BPoint p010=addBPoint(xc-x_side*0.5,back_length+y_side,0);
		
		BPoint p001=addBPoint(xc-x_side*0.5,back_length,z_side);	
		BPoint p101=addBPoint(xc+x_side*0.5,back_length,z_side);
		BPoint p111=addBPoint(xc+x_side*0.5,back_length+y_side,z_side);
		BPoint p011=addBPoint(xc-x_side*0.5,back_length+y_side,z_side);		


		//LineData topLD=addLine(p001,p101,p111,p011,Renderer3D.CAR_TOP);
		//polyData.add(topLD);


		LineData bottomLD=addLine(p000,p010,p110,p100,Renderer3D.CAR_BOTTOM);

		LineData leftLD=addLine(p000,p001,p011,p010,Renderer3D.CAR_LEFT);

		LineData rightLD=addLine(p100,p110,p111,p101,Renderer3D.CAR_RIGHT);


		/*LineData backLD=addLine(p000,p100,p101,p001,Renderer3D.CAR_BACK);
		polyData.add(backLD);

		LineData frontLD=addLine(p010,p011,p111,p110,Renderer3D.CAR_FRONT);
		polyData.add(frontLD);*/
		
		
		//back part:
		BPoint pBack000=addBPoint(xc-back_width*0.5,0,0);
		BPoint pBack100=addBPoint(xc+back_width*0.5,0,0);
		BPoint pBack101=addBPoint(xc+back_width*0.5,0,back_height);
		BPoint pBack001=addBPoint(xc-back_width*0.5,0,back_height);
		
		LineData backMask=addLine(pBack000,pBack100,pBack101,pBack001,Renderer3D.CAR_RIGHT);
		
		LineData backTop=addLine(pBack001,pBack101,p101,p001,Renderer3D.CAR_TOP);
		
		LineData backBottom=addLine(pBack100,pBack000,p000,p100,Renderer3D.CAR_BOTTOM);
		
		LineData backLeft=addLine(pBack000,pBack001,p001,p000,Renderer3D.CAR_LEFT);
		
		LineData backRight=addLine(pBack101,pBack100,p100,p101,Renderer3D.CAR_RIGHT);

		//front part:		
		BPoint pFront000=addBPoint(xc-front_width*0.5,back_length+y_side+front_length,0);
		BPoint pFront100=addBPoint(xc+front_width*0.5,back_length+y_side+front_length,0);
		BPoint pFront101=addBPoint(xc+front_width*0.5,back_length+y_side+front_length,front_height);
		BPoint pFront001=addBPoint(xc-front_width*0.5,back_length+y_side+front_length,front_height);

		
		LineData frontMask=addLine(pFront000,pFront001,pFront101,pFront100,Renderer3D.CAR_RIGHT);
		
		LineData frontTop=addLine(p011,p111,pFront101,pFront001,Renderer3D.CAR_TOP);
		
		LineData frontBottom=addLine(p110,p010,pFront000,pFront100,Renderer3D.CAR_BOTTOM);

		LineData frontLeft=addLine(p010,p011,pFront001,pFront000,Renderer3D.CAR_LEFT);
		
		LineData frontRight=addLine(p111,p110,pFront100,pFront101,Renderer3D.CAR_RIGHT);
		
		//roof:		
		
		BPoint pr000=addBPoint(0,back_length,z_side);
		BPoint pr100=addBPoint(x_side,back_length,z_side);
		BPoint pr110=addBPoint(x_side,back_length+y_side,z_side);
		BPoint pr010=addBPoint(0,back_length+y_side,z_side);	
		
		double roofDY=(y_side-roof_length)/2.0;
		
		BPoint pr001=addBPoint(xc-roof_width*0.5,roofDY+back_length,z_side+roof_height);	
		BPoint pr101=addBPoint(xc+roof_width*0.5,roofDY+back_length,z_side+roof_height);
		BPoint pr111=addBPoint(xc+roof_width*0.5,y_side+back_length-roofDY,z_side+roof_height);
		BPoint pr011=addBPoint(xc-roof_width*0.5,y_side+back_length-roofDY,z_side+roof_height);	

		
		LineData topRLD=addLine(pr001,pr101,pr111,pr011,Renderer3D.CAR_TOP);

		//LineData bottomRLD=addLine(pr000,pr010,pr1addLineRenderer3D.CAR_BOTTOM);
		//polyData.add(bottomRLD);

		LineData leftRLD=addLine(pr000,pr001,pr011,pr010,Renderer3D.CAR_LEFT);

		LineData rightRLD=addLine(pr100,pr110,pr111,pr101,Renderer3D.CAR_RIGHT);

		LineData backRLD=addLine(pr000,pr100,pr101,pr001,Renderer3D.CAR_BACK);

		LineData frontRLD=addLine(pr010,pr011,pr111,pr110,Renderer3D.CAR_FRONT);

		

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}



	private PolygonMesh buildTruckMesh() {
		
		points=new Vector();
		points.setSize(50);

		polyData=new Vector();

		//basic sides:
		
		//double front_height=z_side;
		//double back_height=z_side;
		
		n=0;
		
		
		//front:
		
		double xc=x_side*0.5;
		
		BPoint pFront000=addBPoint(xc-front_width*0.5,back_length+y_side,0);
		BPoint pFront100=addBPoint(xc+front_width*0.5,back_length+y_side,0);
		BPoint pFront110=addBPoint(xc+front_width*0.5,back_length+y_side+front_length,0);
		BPoint pFront010=addBPoint(xc-front_width*0.5,back_length+y_side+front_length,0);
		
		BPoint pFront001=addBPoint(xc-front_width*0.5,back_length+y_side,front_height);	
		BPoint pFront101=addBPoint(xc+front_width*0.5,back_length+y_side,front_height);
		BPoint pFront111=addBPoint(xc+front_width*0.5,back_length+y_side+front_length,front_height);
		BPoint pFront011=addBPoint(xc-front_width*0.5,back_length+y_side+front_length,front_height);	



		LineData topFrontLD=addLine(pFront001,pFront101,pFront111,pFront011,Renderer3D.CAR_TOP);

		LineData bottomFrontLD=addLine(pFront000,pFront010,pFront110,pFront100,Renderer3D.CAR_BOTTOM);

		LineData leftFrontLD=addLine(pFront000,pFront001,pFront011,pFront010,Renderer3D.CAR_LEFT);

		LineData rightFrontLD=addLine(pFront100,pFront110,pFront111,pFront101,Renderer3D.CAR_RIGHT);

		LineData backFrontLD=addLine(pFront000,pFront100,pFront101,pFront001,Renderer3D.CAR_BACK);

		LineData frontFrontLD=addLine(pFront010,pFront011,pFront111,pFront110,Renderer3D.CAR_FRONT);

		
		
		//main body:

		BPoint p000=addBPoint(xc-x_side*0.5,back_length,0);
		BPoint p100=addBPoint(xc+x_side*0.5,back_length,0);
		BPoint p110=addBPoint(xc+x_side*0.5,back_length+y_side,0);
		BPoint p010=addBPoint(xc-x_side*0.5,back_length+y_side,0);
		
		BPoint p001=addBPoint(xc-x_side*0.5,back_length,z_side);	
		BPoint p101=addBPoint(xc+x_side*0.5,back_length,z_side);
		BPoint p111=addBPoint(xc+x_side*0.5,back_length+y_side,z_side);
		BPoint p011=addBPoint(xc-x_side*0.5,back_length+y_side,z_side);		


		LineData topLD=addLine(p001,p101,p111,p011,Renderer3D.CAR_TOP);

		LineData bottomLD=addLine(p000,p010,p110,p100,Renderer3D.CAR_BOTTOM);

		LineData leftLD=addLine(p000,p001,p011,p010,Renderer3D.CAR_LEFT);

		LineData rightLD=addLine(p100,p110,p111,p101,Renderer3D.CAR_RIGHT);

		LineData backLD=addLine(p000,p100,p101,p001,Renderer3D.CAR_BACK);

		LineData frontLD=addLine(p010,p011,p111,p110,Renderer3D.CAR_FRONT);		
		
		//roof:		
		
		BPoint pRoof000=addBPoint(xc-roof_width*0.5,back_length,z_side);
		BPoint pRoof100=addBPoint(xc+roof_width*0.5,back_length,z_side);
		BPoint pRoof110=addBPoint(xc+roof_width*0.5,back_length+roof_length,z_side);
		BPoint pRoof010=addBPoint(xc-roof_width*0.5,back_length+roof_length,z_side);
		
		BPoint pRoof001=addBPoint(xc-roof_width*0.5,back_length,z_side+roof_height);	
		BPoint pRoof101=addBPoint(xc+roof_width*0.5,back_length,z_side+roof_height);
		BPoint pRoof111=addBPoint(xc+roof_width*0.5,back_length+roof_length,z_side+roof_height);
		BPoint pRoof011=addBPoint(xc-roof_width*0.5,back_length+roof_length,z_side+roof_height);		


		LineData topRoofLD=addLine(pRoof001,pRoof101,pRoof111,pRoof011,Renderer3D.CAR_TOP);

		LineData bottomRoofLD=addLine(pRoof000,pRoof010,pRoof110,pRoof100,Renderer3D.CAR_BOTTOM);

		LineData leftRoofLD=addLine(pRoof000,pRoof001,pRoof011,pRoof010,Renderer3D.CAR_LEFT);

		LineData rightRoofLD=addLine(pRoof100,pRoof110,pRoof111,pRoof101,Renderer3D.CAR_RIGHT);

		LineData backRoofLD=addLine(pRoof000,pRoof100,pRoof101,pRoof001,Renderer3D.CAR_BACK);

		LineData frontRoofLD=addLine(pRoof010,pRoof011,pRoof111,pRoof110,Renderer3D.CAR_FRONT);		

		//back:

		BPoint pBack000=addBPoint(xc-back_width*0.5,0,0);
		BPoint pBack100=addBPoint(xc+back_width*0.5,0,0);
		BPoint pBack110=addBPoint(xc+back_width*0.5,back_length,0);
		BPoint pBack010=addBPoint(xc-back_width*0.5,back_length,0);

		BPoint pBack001=addBPoint(xc-back_width*0.5,0,back_height);	
		BPoint pBack101=addBPoint(xc+back_width*0.5,0,back_height);
		BPoint pBack111=addBPoint(xc+back_width*0.5,back_length,back_height);
		BPoint pBack011=addBPoint(xc-back_width*0.5,back_length,back_height);		


		LineData topBackLD=addLine(pBack001,pBack101,pBack111,pBack011,Renderer3D.CAR_TOP);

		LineData bottomBackLD=addLine(pBack000,pBack010,pBack110,pBack100,Renderer3D.CAR_BOTTOM);

		LineData leftBackLD=addLine(pBack000,pBack001,pBack011,pBack010,Renderer3D.CAR_LEFT);

		LineData rightBackLD=addLine(pBack100,pBack110,pBack111,pBack101,Renderer3D.CAR_RIGHT);

		LineData backBackLD=addLine(pBack000,pBack100,pBack101,pBack001,Renderer3D.CAR_BACK);

		LineData frontBackLD=addLine(pBack010,pBack011,pBack111,pBack110,Renderer3D.CAR_FRONT);

		/////////
		

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}



}	