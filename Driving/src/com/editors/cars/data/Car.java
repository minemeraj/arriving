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


		//LineData topLD=buildLine(p001,p101,p111,p011,Renderer3D.CAR_TOP);
		//polyData.add(topLD);


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
		
		double backDX=(x_side-back_width)/2.0;
		
		
		//back part:
		BPoint pBack000=new BPoint(backDX,0,0,n++);
		BPoint pBack100=new BPoint(x_side-backDX,0,0,n++);
		BPoint pBack101=new BPoint(x_side-backDX,0,back_height,n++);
		BPoint pBack001=new BPoint(backDX,0,back_height,n++);
		
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
		
		

		double frontDX=(x_side-front_width)/2.0;
		
	
		//front part:		
		BPoint pFront000=new BPoint(frontDX,back_length+y_side+front_length,0,n++);
		BPoint pFront100=new BPoint(x_side-frontDX,back_length+y_side+front_length,0,n++);
		BPoint pFront101=new BPoint(x_side-frontDX,back_length+y_side+front_length,front_height,n++);
		BPoint pFront001=new BPoint(frontDX,back_length+y_side+front_length,front_height,n++);
		
		
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
		
		double roofDX=(x_side-roof_width)/2.0;
		double roofDY=(y_side-roof_length)/2.0;
		
		BPoint pr001=new BPoint(roofDX,roofDY+back_length,z_side+roof_height,n++);	
		BPoint pr101=new BPoint(x_side-roofDX,roofDY+back_length,z_side+roof_height,n++);
		BPoint pr111=new BPoint(x_side-roofDX,y_side+back_length-roofDY,z_side+roof_height,n++);
		BPoint pr011=new BPoint(roofDX,y_side+back_length-roofDY,z_side+roof_height,n++);	
		
		
		
		
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



	private PolygonMesh buildTruckMesh() {
		
		Vector points=new Vector();
		points.setSize(50);

		Vector polyData=new Vector();

		//basic sides:
		
		//double front_height=z_side;
		//double back_height=z_side;
		
		int n=0;
		
		
		//front:
		
		double FRONT_DX=(x_side-front_width)/2.0;
		
		BPoint pFront000=new BPoint(FRONT_DX,back_length,z_side,n++);
		BPoint pFront100=new BPoint(FRONT_DX+front_width,back_length,z_side,n++);
		BPoint pFront110=new BPoint(FRONT_DX+front_width,back_length+front_length,z_side,n++);
		BPoint pFront010=new BPoint(FRONT_DX,back_length+front_length,z_side,n++);
		
		BPoint pFront001=new BPoint(FRONT_DX,back_length,z_side+front_height,n++);	
		BPoint pFront101=new BPoint(FRONT_DX+front_width,back_length,z_side+front_height,n++);
		BPoint pFront111=new BPoint(FRONT_DX+front_width,back_length+front_length,z_side+front_height,n++);
		BPoint pFront011=new BPoint(FRONT_DX,back_length+front_length,z_side+front_height,n++);		
		

		points.setElementAt(pFront000,pFront000.getIndex());
		points.setElementAt(pFront100,pFront100.getIndex());
		points.setElementAt(pFront010,pFront010.getIndex());
		points.setElementAt(pFront001,pFront001.getIndex());
		points.setElementAt(pFront110,pFront110.getIndex());
		points.setElementAt(pFront011,pFront011.getIndex());
		points.setElementAt(pFront101,pFront101.getIndex());
		points.setElementAt(pFront111,pFront111.getIndex());


		LineData topFrontLD=buildLine(pFront001,pFront101,pFront111,pFront011,Renderer3D.CAR_TOP);
		polyData.add(topFrontLD);


		LineData bottomFrontLD=buildLine(pFront000,pFront010,pFront110,pFront100,Renderer3D.CAR_BOTTOM);
		polyData.add(bottomFrontLD);

		LineData leftFrontLD=buildLine(pFront000,pFront001,pFront011,pFront010,Renderer3D.CAR_LEFT);
		polyData.add(leftFrontLD);


		LineData rightFrontLD=buildLine(pFront100,pFront110,pFront111,pFront101,Renderer3D.CAR_RIGHT);
		polyData.add(rightFrontLD);

		LineData backFrontLD=buildLine(pFront000,pFront100,pFront101,pFront001,Renderer3D.CAR_BACK);
		polyData.add(backFrontLD);

		LineData frontFrontLD=buildLine(pFront010,pFront011,pFront111,pFront110,Renderer3D.CAR_FRONT);
		polyData.add(frontFrontLD);
		
		
		//main body:

		BPoint p000=new BPoint(0,0,0,n++);
		BPoint p100=new BPoint(x_side,0,0,n++);
		BPoint p110=new BPoint(x_side,y_side,0,n++);
		BPoint p010=new BPoint(0,y_side,0,n++);
		
		BPoint p001=new BPoint(0,0,z_side,n++);	
		BPoint p101=new BPoint(x_side,0,z_side,n++);
		BPoint p111=new BPoint(x_side,y_side,z_side,n++);
		BPoint p011=new BPoint(0,y_side,z_side,n++);		
		

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
		
		double ROOF_DX=(x_side-roof_width)/2.0; 
		
		BPoint pRoof000=new BPoint(ROOF_DX,0,z_side,n++);
		BPoint pRoof100=new BPoint(ROOF_DX+roof_width,0,z_side,n++);
		BPoint pRoof110=new BPoint(ROOF_DX+roof_width,roof_length,z_side,n++);
		BPoint pRoof010=new BPoint(ROOF_DX,roof_length,z_side,n++);
		
		BPoint pRoof001=new BPoint(ROOF_DX,0,z_side+roof_height,n++);	
		BPoint pRoof101=new BPoint(ROOF_DX+roof_width,0,z_side+roof_height,n++);
		BPoint pRoof111=new BPoint(ROOF_DX+roof_width,roof_length,z_side+roof_height,n++);
		BPoint pRoof011=new BPoint(ROOF_DX,roof_length,z_side+roof_height,n++);		
		

		points.setElementAt(pRoof000,pRoof000.getIndex());
		points.setElementAt(pRoof100,pRoof100.getIndex());
		points.setElementAt(pRoof010,pRoof010.getIndex());
		points.setElementAt(pRoof001,pRoof001.getIndex());
		points.setElementAt(pRoof110,pRoof110.getIndex());
		points.setElementAt(pRoof011,pRoof011.getIndex());
		points.setElementAt(pRoof101,pRoof101.getIndex());
		points.setElementAt(pRoof111,pRoof111.getIndex());


		LineData topRoofLD=buildLine(pRoof001,pRoof101,pRoof111,pRoof011,Renderer3D.CAR_TOP);
		polyData.add(topRoofLD);


		LineData bottomRoofLD=buildLine(pRoof000,pRoof010,pRoof110,pRoof100,Renderer3D.CAR_BOTTOM);
		polyData.add(bottomRoofLD);

		LineData leftRoofLD=buildLine(pRoof000,pRoof001,pRoof011,pRoof010,Renderer3D.CAR_LEFT);
		polyData.add(leftRoofLD);


		LineData rightRoofLD=buildLine(pRoof100,pRoof110,pRoof111,pRoof101,Renderer3D.CAR_RIGHT);
		polyData.add(rightRoofLD);

		LineData backRoofLD=buildLine(pRoof000,pRoof100,pRoof101,pRoof001,Renderer3D.CAR_BACK);
		polyData.add(backRoofLD);

		LineData frontRoofLD=buildLine(pRoof010,pRoof011,pRoof111,pRoof110,Renderer3D.CAR_FRONT);
		polyData.add(frontRoofLD);
		
		/////////
		

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}



}	