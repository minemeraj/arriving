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
		
		BPoint[][][] p=new BPoint[2][2][2];

		p[0][0][0]=addBPoint(xc-x_side*0.5,back_length,0);
		p[1][0][0]=addBPoint(xc+x_side*0.5,back_length,0);
		p[1][1][0]=addBPoint(xc+x_side*0.5,back_length+y_side,0);
		p[0][1][0]=addBPoint(xc-x_side*0.5,back_length+y_side,0);
		
		p[0][0][1]=addBPoint(xc-x_side*0.5,back_length,z_side);	
		p[1][0][1]=addBPoint(xc+x_side*0.5,back_length,z_side);
		p[1][1][1]=addBPoint(xc+x_side*0.5,back_length+y_side,z_side);
		p[0][1][1]=addBPoint(xc-x_side*0.5,back_length+y_side,z_side);		




		LineData bottomLD=addLine(p[0][0][0],p[0][1][0],p[1][1][0],p[1][0][0],Renderer3D.CAR_BOTTOM);

		LineData leftLD=addLine(p[0][0][0],p[0][0][1],p[0][1][1],p[0][1][0],Renderer3D.CAR_LEFT);

		LineData rightLD=addLine(p[1][0][0],p[1][1][0],p[1][1][1],p[1][0][1],Renderer3D.CAR_RIGHT);


		
		//back part:
		
		BPoint[][][] pBack=new BPoint[2][2][2];
		
		pBack[0][0][0]=addBPoint(xc-back_width*0.5,0,0);
		pBack[1][0][0]=addBPoint(xc+back_width*0.5,0,0);
		pBack[1][0][1]=addBPoint(xc+back_width*0.5,0,back_height);
		pBack[0][0][1]=addBPoint(xc-back_width*0.5,0,back_height);
		
		LineData backMask=addLine(pBack[0][0][0],pBack[1][0][0],pBack[1][0][1],pBack[0][0][1],Renderer3D.CAR_RIGHT);
		
		LineData backTop=addLine(pBack[0][0][1],pBack[1][0][1],p[1][0][1],p[0][0][1],Renderer3D.CAR_TOP);
		
		LineData backBottom=addLine(pBack[1][0][0],pBack[0][0][0],p[0][0][0],p[1][0][0],Renderer3D.CAR_BOTTOM);
		
		LineData backLeft=addLine(pBack[0][0][0],pBack[0][0][1],p[0][0][1],p[0][0][0],Renderer3D.CAR_LEFT);
		
		LineData backRight=addLine(pBack[1][0][1],pBack[1][0][0],p[1][0][0],p[1][0][1],Renderer3D.CAR_RIGHT);

		//front part:	
		
		BPoint[][][] pFront=new BPoint[2][2][2];
		
		pFront[0][0][0]=addBPoint(xc-front_width*0.5,back_length+y_side+front_length,0);
		pFront[1][0][0]=addBPoint(xc+front_width*0.5,back_length+y_side+front_length,0);
		pFront[1][0][1]=addBPoint(xc+front_width*0.5,back_length+y_side+front_length,front_height);
		pFront[0][0][1]=addBPoint(xc-front_width*0.5,back_length+y_side+front_length,front_height);

		
		LineData frontMask=addLine(pFront[0][0][0],pFront[0][0][1],pFront[1][0][1],pFront[1][0][0],Renderer3D.CAR_RIGHT);
		
		LineData frontTop=addLine(p[0][1][1],p[1][1][1],pFront[1][0][1],pFront[0][0][1],Renderer3D.CAR_TOP);
		
		LineData frontBottom=addLine(p[1][1][0],p[0][1][0],pFront[0][0][0],pFront[1][0][0],Renderer3D.CAR_BOTTOM);

		LineData frontLeft=addLine(p[0][1][0],p[0][1][1],pFront[0][0][1],pFront[0][0][0],Renderer3D.CAR_LEFT);
		
		LineData frontRight=addLine(p[1][1][1],p[1][1][0],pFront[1][0][0],pFront[1][0][1],Renderer3D.CAR_RIGHT);
		
		//roof:	
		
		BPoint[][][] pr=new BPoint[2][2][2];
		
		pr[0][0][0]=addBPoint(0,back_length,z_side);
		pr[1][0][0]=addBPoint(x_side,back_length,z_side);
		pr[1][1][0]=addBPoint(x_side,back_length+y_side,z_side);
		pr[0][1][0]=addBPoint(0,back_length+y_side,z_side);	
		
		double roofDY=(y_side-roof_length)/2.0;
		
		pr[0][0][1]=addBPoint(xc-roof_width*0.5,roofDY+back_length,z_side+roof_height);	
		pr[1][0][1]=addBPoint(xc+roof_width*0.5,roofDY+back_length,z_side+roof_height);
		pr[1][1][1]=addBPoint(xc+roof_width*0.5,y_side+back_length-roofDY,z_side+roof_height);
		pr[0][1][1]=addBPoint(xc-roof_width*0.5,y_side+back_length-roofDY,z_side+roof_height);	

		
		LineData topRLD=addLine(pr[0][0][1],pr[1][0][1],pr[1][1][1],pr[0][1][1],Renderer3D.CAR_TOP);



		LineData leftRLD=addLine(pr[0][0][0],pr[0][0][1],pr[0][1][1],pr[0][1][0],Renderer3D.CAR_LEFT);

		LineData rightRLD=addLine(pr[1][0][0],pr[1][1][0],pr[1][1][1],pr[1][0][1],Renderer3D.CAR_RIGHT);

		LineData backRLD=addLine(pr[0][0][0],pr[1][0][0],pr[1][0][1],pr[0][0][1],Renderer3D.CAR_BACK);

		LineData frontRLD=addLine(pr[0][1][0],pr[0][1][1],pr[1][1][1],pr[1][1][0],Renderer3D.CAR_FRONT);

		

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
		
		BPoint[][][] pFront=new BPoint[2][2][2];
		
		pFront[0][0][0]=addBPoint(xc-front_width*0.5,back_length+y_side,0);
		pFront[1][0][0]=addBPoint(xc+front_width*0.5,back_length+y_side,0);
		pFront[1][1][0]=addBPoint(xc+front_width*0.5,back_length+y_side+front_length,0);
		pFront[0][1][0]=addBPoint(xc-front_width*0.5,back_length+y_side+front_length,0);
		
		pFront[0][0][1]=addBPoint(xc-front_width*0.5,back_length+y_side,front_height);	
		pFront[1][0][1]=addBPoint(xc+front_width*0.5,back_length+y_side,front_height);
		pFront[1][1][1]=addBPoint(xc+front_width*0.5,back_length+y_side+front_length,front_height);
		pFront[0][1][1]=addBPoint(xc-front_width*0.5,back_length+y_side+front_length,front_height);	



		LineData topFrontLD=addLine(pFront[0][0][1],pFront[1][0][1],pFront[1][1][1],pFront[0][1][1],Renderer3D.CAR_TOP);

		LineData bottomFrontLD=addLine(pFront[0][0][0],pFront[0][1][0],pFront[1][1][0],pFront[1][0][0],Renderer3D.CAR_BOTTOM);

		LineData leftFrontLD=addLine(pFront[0][0][0],pFront[0][0][1],pFront[0][1][1],pFront[0][1][0],Renderer3D.CAR_LEFT);

		LineData rightFrontLD=addLine(pFront[1][0][0],pFront[1][1][0],pFront[1][1][1],pFront[1][0][1],Renderer3D.CAR_RIGHT);

		LineData backFrontLD=addLine(pFront[0][0][0],pFront[1][0][0],pFront[1][0][1],pFront[0][0][1],Renderer3D.CAR_BACK);

		LineData frontFrontLD=addLine(pFront[0][1][0],pFront[0][1][1],pFront[1][1][1],pFront[1][1][0],Renderer3D.CAR_FRONT);

		
		
		//main body:
		
		BPoint[][][] p=new BPoint[2][2][2];

		p[0][0][0]=addBPoint(xc-x_side*0.5,back_length,0);
		p[1][0][0]=addBPoint(xc+x_side*0.5,back_length,0);
		p[1][1][0]=addBPoint(xc+x_side*0.5,back_length+y_side,0);
		p[0][1][0]=addBPoint(xc-x_side*0.5,back_length+y_side,0);
		
		p[0][0][1]=addBPoint(xc-x_side*0.5,back_length,z_side);	
		p[1][0][1]=addBPoint(xc+x_side*0.5,back_length,z_side);
		p[1][1][1]=addBPoint(xc+x_side*0.5,back_length+y_side,z_side);
		p[0][1][1]=addBPoint(xc-x_side*0.5,back_length+y_side,z_side);		


		LineData topLD=addLine(p[0][0][1],p[1][0][1],p[1][1][1],p[0][1][1],Renderer3D.CAR_TOP);

		LineData bottomLD=addLine(p[0][0][0],p[0][1][0],p[1][1][0],p[1][0][0],Renderer3D.CAR_BOTTOM);

		LineData leftLD=addLine(p[0][0][0],p[0][0][1],p[0][1][1],p[0][1][0],Renderer3D.CAR_LEFT);

		LineData rightLD=addLine(p[1][0][0],p[1][1][0],p[1][1][1],p[1][0][1],Renderer3D.CAR_RIGHT);

		LineData backLD=addLine(p[0][0][0],p[1][0][0],p[1][0][1],p[0][0][1],Renderer3D.CAR_BACK);

		LineData frontLD=addLine(p[0][1][0],p[0][1][1],p[1][1][1],p[1][1][0],Renderer3D.CAR_FRONT);		
		
		//roof:		
		
		BPoint[][][] pRoof=new BPoint[2][2][2];
		
		pRoof[0][0][0]=addBPoint(xc-roof_width*0.5,back_length,z_side);
		pRoof[1][0][0]=addBPoint(xc+roof_width*0.5,back_length,z_side);
		pRoof[1][1][0]=addBPoint(xc+roof_width*0.5,back_length+roof_length,z_side);
		pRoof[0][1][0]=addBPoint(xc-roof_width*0.5,back_length+roof_length,z_side);
		
		pRoof[0][0][1]=addBPoint(xc-roof_width*0.5,back_length,z_side+roof_height);	
		pRoof[1][0][1]=addBPoint(xc+roof_width*0.5,back_length,z_side+roof_height);
		pRoof[1][1][1]=addBPoint(xc+roof_width*0.5,back_length+roof_length,z_side+roof_height);
		pRoof[0][1][1]=addBPoint(xc-roof_width*0.5,back_length+roof_length,z_side+roof_height);		


		LineData topRoofLD=addLine(pRoof[0][0][1],pRoof[1][0][1],pRoof[1][1][1],pRoof[0][1][1],Renderer3D.CAR_TOP);

		LineData bottomRoofLD=addLine(pRoof[0][0][0],pRoof[0][1][0],pRoof[1][1][0],pRoof[1][0][0],Renderer3D.CAR_BOTTOM);

		LineData leftRoofLD=addLine(pRoof[0][0][0],pRoof[0][0][1],pRoof[0][1][1],pRoof[0][1][0],Renderer3D.CAR_LEFT);

		LineData rightRoofLD=addLine(pRoof[1][0][0],pRoof[1][1][0],pRoof[1][1][1],pRoof[1][0][1],Renderer3D.CAR_RIGHT);

		LineData backRoofLD=addLine(pRoof[0][0][0],pRoof[1][0][0],pRoof[1][0][1],pRoof[0][0][1],Renderer3D.CAR_BACK);

		LineData frontRoofLD=addLine(pRoof[0][1][0],pRoof[0][1][1],pRoof[1][1][1],pRoof[1][1][0],Renderer3D.CAR_FRONT);		

		//back:
		
		BPoint[][][] pBack=new BPoint[2][2][2];

		pBack[0][0][0]=addBPoint(xc-back_width*0.5,0,0);
		pBack[1][0][0]=addBPoint(xc+back_width*0.5,0,0);
		pBack[1][1][0]=addBPoint(xc+back_width*0.5,back_length,0);
		pBack[0][1][0]=addBPoint(xc-back_width*0.5,back_length,0);

		pBack[0][0][1]=addBPoint(xc-back_width*0.5,0,back_height);	
		pBack[1][0][1]=addBPoint(xc+back_width*0.5,0,back_height);
		pBack[1][1][1]=addBPoint(xc+back_width*0.5,back_length,back_height);
		pBack[0][1][1]=addBPoint(xc-back_width*0.5,back_length,back_height);		


		LineData topBackLD=addLine(pBack[0][0][1],pBack[1][0][1],pBack[1][1][1],pBack[0][1][1],Renderer3D.CAR_TOP);

		LineData bottomBackLD=addLine(pBack[0][0][0],pBack[0][1][0],pBack[1][1][0],pBack[1][0][0],Renderer3D.CAR_BOTTOM);

		LineData leftBackLD=addLine(pBack[0][0][0],pBack[0][0][1],pBack[0][1][1],pBack[0][1][0],Renderer3D.CAR_LEFT);

		LineData rightBackLD=addLine(pBack[1][0][0],pBack[1][1][0],pBack[1][1][1],pBack[1][0][1],Renderer3D.CAR_RIGHT);

		LineData backBackLD=addLine(pBack[0][0][0],pBack[1][0][0],pBack[1][0][1],pBack[0][0][1],Renderer3D.CAR_BACK);

		LineData frontBackLD=addLine(pBack[0][1][0],pBack[0][1][1],pBack[1][1][1],pBack[1][1][0],Renderer3D.CAR_FRONT);

		/////////
		

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}



}	