package com.editors.cars.data;

import java.util.Vector;
import com.BPoint;
import com.CustomData;
import com.LineData;
import com.PolygonMesh;
import com.Prism;
import com.Segments;
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
    
    double wheel_radius=0;
    double wheel_width=0;
    
    double front_overhang=0;
    double wheel_base=0;    
    double rear_overhang=0;
    
    public static int CAR_TYPE_CAR=0;
    public static int CAR_TYPE_TRUCK=1;
    public static int CAR_TYPE_BYKE=2;
    public static int CAR_TYPE_TRACTOR=3;
    public static int CAR_TYPE_RAILROAD_CAR_0=4;
    public static int CAR_TYPE_RAILROAD_CAR_1=41;
    public static int CAR_TYPE_RAILROAD_CAR_2=42;
    public static int CAR_TYPE_RAILROAD_CAR_3=43;
    public static int CAR_TYPE_RAILROAD_CAR_4=44;
    public static int CAR_TYPE_AIRPLANE=5;
    public static int CAR_TYPE_STARSHIP=6;
    public static int CAR_TYPE_F1_CAR=7;
    public static int CAR_TYPE_SHIP=8;
    
    public int car_type=CAR_TYPE_CAR;

	public Car(){}

	public Car(int car_type, double x_side, double y_side,double z_side,
			double front_width,double front_length,double front_height,
			double back_width,double back_length,double back_height,
			 double roof_width,double roof_length,double roof_height,
			 double wheel_radius,double wheel_width,
			 double front_overhang,double wheel_base, double rear_overhang
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
		
		this.wheel_radius = wheel_radius;
		this.wheel_width = wheel_width;
		
		this.front_overhang = front_overhang;
		this.wheel_base = wheel_base;
		this.rear_overhang = rear_overhang;
	}

	public Object clone(){

		Car grid=new Car(
				car_type,
				x_side,y_side,z_side,
				front_width,front_length,front_height,
				back_width,back_length,back_height,
				roof_width,roof_length,roof_height,
				wheel_radius,wheel_width,
				front_overhang,wheel_base,rear_overhang
				
				);
		return grid;

	}
	

	public String toString() {

		return "C="+car_type+","+x_side+","+y_side+","+z_side+","
		+front_width+","+front_length+","+front_height+","
		+back_width+","+back_length+","+back_height+
		","+roof_width+","+roof_length+","+roof_height+"," +
		wheel_radius+","+wheel_width+","+
		front_overhang+","+wheel_base+","+rear_overhang;
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
		double wheel_radius= Double.parseDouble(vals[13]); 
		double wheel_width= Double.parseDouble(vals[13]);
		double front_overhang= Double.parseDouble(vals[13]);
		double wheel_base= Double.parseDouble(vals[13]);
		double rear_overhang= Double.parseDouble(vals[13]);
		
		Car grid=new Car(car_type,
				x_side,y_side,z_side,
				front_width,front_length,front_height,
				back_width,back_length,back_height,
				roof_width,roof_length,roof_height,
				wheel_radius,wheel_width,
				front_overhang,wheel_base,rear_overhang
				
				);

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

	public double getWheel_radius() {
		return wheel_radius;
	}

	public void setWheel_radius(double wheel_radius) {
		this.wheel_radius = wheel_radius;
	}


	public double getWheel_width() {
		return wheel_width;
	}

	public void setWheel_width(double wheel_width) {
		this.wheel_width = wheel_width;
	}

	public double getFront_overhang() {
		return front_overhang;
	}

	public void setFront_overhang(double front_overhang) {
		this.front_overhang = front_overhang;
	}

	public double getWheel_base() {
		return wheel_base;
	}

	public void setWheel_base(double wheel_base) {
		this.wheel_base = wheel_base;
	}

	public double getRear_overhang() {
		return rear_overhang;
	}

	public void setRear_overhang(double rear_overhang) {
		this.rear_overhang = rear_overhang;
	}
	
	public PolygonMesh buildMesh(){


		if(car_type==CAR_TYPE_CAR)
			return buildCarMesh();
		else if(car_type==CAR_TYPE_TRUCK)
			return buildTruckMesh();
		else if(car_type==CAR_TYPE_BYKE)
			return buildBykeMesh();		
		else if(car_type==CAR_TYPE_TRACTOR)
			return buildTractorMesh();
		else if(car_type==CAR_TYPE_RAILROAD_CAR_0 ||
				car_type==CAR_TYPE_RAILROAD_CAR_1 ||
				car_type==CAR_TYPE_RAILROAD_CAR_2 ||
				car_type==CAR_TYPE_RAILROAD_CAR_3 ||
				car_type==CAR_TYPE_RAILROAD_CAR_4
			)
			return buildRailroadCarMesh();
		else if(car_type==CAR_TYPE_STARSHIP)
			return buildStarshipMesh();
		else if(car_type==CAR_TYPE_F1_CAR)
			return buildF1CarMesh();
		else if(car_type==CAR_TYPE_SHIP)
			return buildShipMesh(); 
		else
			return buildAirplaneMesh();

	}







	private PolygonMesh buildCarMesh() {
		
		points=new Vector();
		points.setSize(400);

		polyData=new Vector();

		//basic sides:
		
		double xc=x_side*0.5;
		
		n=0;
		
		
		//main body:
		
		int pnx=5;
		int bny=2;
		int pny=9;
		int fny=5;
		int pnz=3;
		
		int numy=bny+pny+fny;
		
		
		BPoint[][][] p=new BPoint[pnx][bny+pny+fny][pnz];
		
		//back part:	
		
		
		double back_width1=back_width*(1-0.5)+x_side*0.5; 
		double back_height1=back_height*(1-0.75)+z_side*0.75; 
		
		Segments b0=new Segments(xc,back_width,0,back_length,0,back_height);
		Segments b1=new Segments(xc,back_width1,0,back_length,0,back_height1);
		
	
		
		p[0][0][0]=addBPoint(-0.5,0.1,0,b0);
		p[1][0][0]=addBPoint(-0.25,0,0,b0);
		p[2][0][0]=addBPoint(0.0,0,0,b0);
		p[3][0][0]=addBPoint(0.25,0,0,b0);
		p[4][0][0]=addBPoint(0.5,0.1,0,b0);
		p[0][0][1]=addBPoint(-0.5,0.1,0.5,b0);
		p[1][0][1]=addBPoint(-0.25,0,0.5,b0);
		p[2][0][1]=addBPoint(0.0,0,0.5,b0);
		p[3][0][1]=addBPoint(0.25,0,0.5,b0);
		p[4][0][1]=addBPoint(0.5,0.1,0.5,b0);
		p[0][0][2]=addBPoint(-0.5,0.1,0.9,b0);
		p[1][0][2]=addBPoint(-0.25,0,1.0,b0);
		p[2][0][2]=addBPoint(0.0,0,1.0,b0);
		p[3][0][2]=addBPoint(0.25,0,1.0,b0);
		p[4][0][2]=addBPoint(0.5,0.1,0.9,b0);
		
		p[0][1][0]=addBPoint(-0.5,0.5,0,b1);
		p[1][1][0]=addBPoint(-0.25,0.5,0,b1);
		p[2][1][0]=addBPoint(0.0,0.5,0,b1);	
		p[3][1][0]=addBPoint(0.25,0.5,0,b1);	
		p[4][1][0]=addBPoint(0.5,0.5,0,b1);	
		p[0][1][1]=addBPoint(-0.5,0.5,0.5,b1);
		p[1][1][1]=addBPoint(-0.25,0.5,0.5,b1);
		p[2][1][1]=addBPoint(0.0,0.5,0.5,b1);	
		p[3][1][1]=addBPoint(0.25,0.5,0.5,b1);	
		p[4][1][1]=addBPoint(0.5,0.5,0.5,b1);	
		p[0][1][2]=addBPoint(-0.5,0.5,0.9,b1);
		p[1][1][2]=addBPoint(-0.25,0.5,1.0,b1);
		p[2][1][2]=addBPoint(0.0,0.5,1.0,b1);
		p[3][1][2]=addBPoint(0.25,0.5,1.0,b1);
		p[4][1][2]=addBPoint(0.5,0.5,0.9,b1);
		
		
				
		//main body:	
		
		Segments p0=new Segments(xc,x_side,back_length,y_side,0,z_side);
		
		double lw=(wheel_radius*2.0/Math.sqrt(3))/y_side;
		double zw=wheel_radius/z_side;
		double yw=0.1875;

		p[0][bny][0]=addBPoint(-0.5,yw-lw,0,p0);
		p[1][bny][0]=addBPoint(-0.25,yw-lw,0,p0);
		p[2][bny][0]=addBPoint(0,yw-lw,0,p0);
		p[3][bny][0]=addBPoint(0.25,yw-lw,0,p0);
		p[4][bny][0]=addBPoint(0.5,yw-lw,0,p0);
		p[0][bny][1]=addBPoint(-0.5,0.05,0.5,p0);	
		p[1][bny][1]=addBPoint(-0.25,0.0,0.5,p0);	
		p[2][bny][1]=addBPoint(0,0.0,0.5,p0);	
		p[3][bny][1]=addBPoint(0.25,0.0,0.5,p0);	
		p[4][bny][1]=addBPoint(0.5,0.05,0.5,p0);	
		p[0][bny][2]=addBPoint(-0.5,0.05,1.0,p0);	
		p[1][bny][2]=addBPoint(-0.25,0.0,1.0,p0);	
		p[2][bny][2]=addBPoint(0,0.0,1.0,p0);	
		p[3][bny][2]=addBPoint(0.25,0.0,1.0,p0);	
		p[4][bny][2]=addBPoint(0.5,0.05,1.0,p0);	
		
		p[0][bny+1][0]=addBPoint(-0.5,yw-lw*0.5,zw,p0);	
		p[1][bny+1][0]=addBPoint(-0.25,yw-lw*0.5,zw,p0);
		p[2][bny+1][0]=addBPoint(0,yw-lw*0.5,zw,p0);
		p[3][bny+1][0]=addBPoint(0.25,yw-lw*0.5,zw,p0);
		p[4][bny+1][0]=addBPoint(0.5,yw-lw*0.5,zw,p0);		
		p[0][bny+1][1]=addBPoint(-0.5,0.125,0.5,p0);
		p[1][bny+1][1]=addBPoint(-0.25,0.125,0.5,p0);
		p[2][bny+1][1]=addBPoint(0,0.125,0.5,p0);
		p[3][bny+1][1]=addBPoint(0.25,0.125,0.5,p0);
		p[4][bny+1][1]=addBPoint(0.5,0.125,0.5,p0);
		p[0][bny+1][2]=addBPoint(-0.5,0.125,1.0,p0);
		p[1][bny+1][2]=addBPoint(-0.25,0.125,1.0,p0);
		p[2][bny+1][2]=addBPoint(0,0.125,1.0,p0);
		p[3][bny+1][2]=addBPoint(0.25,0.125,1.0,p0);
		p[4][bny+1][2]=addBPoint(0.5,0.125,1.0,p0);
		
		
		p[0][bny+2][0]=addBPoint(-0.5,yw+lw*0.5,zw,p0);	
		p[1][bny+2][0]=addBPoint(-0.25,yw+lw*0.5,zw,p0);
		p[2][bny+2][0]=addBPoint(0,yw+lw*0.5,zw,p0);
		p[3][bny+2][0]=addBPoint(0.25,yw+lw*0.5,zw,p0);
		p[4][bny+2][0]=addBPoint(0.5,yw+lw*0.5,zw,p0);		
		p[0][bny+2][1]=addBPoint(-0.5,0.25,0.5,p0);
		p[1][bny+2][1]=addBPoint(-0.25,0.25,0.5,p0);
		p[2][bny+2][1]=addBPoint(0,0.25,0.5,p0);
		p[3][bny+2][1]=addBPoint(0.25,0.25,0.5,p0);
		p[4][bny+2][1]=addBPoint(0.5,0.25,0.5,p0);
		p[0][bny+2][2]=addBPoint(-0.5,0.25,1.0,p0);
		p[1][bny+2][2]=addBPoint(-0.25,0.25,1.0,p0);
		p[2][bny+2][2]=addBPoint(0,0.25,1.0,p0);
		p[3][bny+2][2]=addBPoint(0.25,0.25,1.0,p0);
		p[4][bny+2][2]=addBPoint(0.5,0.25,1.0,p0);
		
		p[0][bny+3][0]=addBPoint(-0.5,yw+lw,0,p0);	
		p[1][bny+3][0]=addBPoint(-0.25,yw+lw,0,p0);
		p[2][bny+3][0]=addBPoint(0,yw+lw,0,p0);
		p[3][bny+3][0]=addBPoint(0.25,yw+lw,0,p0);
		p[4][bny+3][0]=addBPoint(0.5,yw+lw,0,p0);		
		p[0][bny+3][1]=addBPoint(-0.5,0.375,0.5,p0);
		p[1][bny+3][1]=addBPoint(-0.25,0.375,0.5,p0);
		p[2][bny+3][1]=addBPoint(0,0.5,0.375,p0);
		p[3][bny+3][1]=addBPoint(0.25,0.375,0.5,p0);
		p[4][bny+3][1]=addBPoint(0.5,0.375,0.5,p0);
		p[0][bny+3][2]=addBPoint(-0.5,0.375,1.0,p0);
		p[1][bny+3][2]=addBPoint(-0.25,0.375,1.0,p0);
		p[2][bny+3][2]=addBPoint(0,0.375,1.0,p0);
		p[3][bny+3][2]=addBPoint(0.25,0.375,1.0,p0);
		p[4][bny+3][2]=addBPoint(0.5,0.375,1.0,p0);
		
		
		p[0][bny+4][0]=addBPoint(-0.5,0.5,0,p0);	
		p[1][bny+4][0]=addBPoint(-0.25,0.5,0,p0);
		p[2][bny+4][0]=addBPoint(0,0.5,0,p0);
		p[3][bny+4][0]=addBPoint(0.25,0.5,0,p0);
		p[4][bny+4][0]=addBPoint(0.5,0.5,0,p0);		
		p[0][bny+4][1]=addBPoint(-0.5,0.5,0.5,p0);
		p[1][bny+4][1]=addBPoint(-0.25,0.5,0.5,p0);
		p[2][bny+4][1]=addBPoint(0,0.5,0.5,p0);
		p[3][bny+4][1]=addBPoint(0.25,0.5,0.5,p0);
		p[4][bny+4][1]=addBPoint(0.5,0.5,0.5,p0);
		p[0][bny+4][2]=addBPoint(-0.5,0.5,1.0,p0);
		p[1][bny+4][2]=addBPoint(-0.25,0.5,1.0,p0);
		p[2][bny+4][2]=addBPoint(0,0.5,1.0,p0);
		p[3][bny+4][2]=addBPoint(0.25,0.5,1.0,p0);
		p[4][bny+4][2]=addBPoint(0.5,0.5,1.0,p0);
		
		p[0][bny+5][0]=addBPoint(-0.5,0.625,0,p0);	
		p[1][bny+5][0]=addBPoint(-0.25,0.625,0,p0);
		p[2][bny+5][0]=addBPoint(0,0.625,0,p0);
		p[3][bny+5][0]=addBPoint(0.25,0.625,0,p0);
		p[4][bny+5][0]=addBPoint(0.5,0.625,0,p0);		
		p[0][bny+5][1]=addBPoint(-0.5,0.625,0.5,p0);
		p[1][bny+5][1]=addBPoint(-0.25,0.625,0.5,p0);
		p[2][bny+5][1]=addBPoint(0,0.625,0.5,p0);
		p[3][bny+5][1]=addBPoint(0.25,0.625,0.5,p0);
		p[4][bny+5][1]=addBPoint(0.5,0.625,0.5,p0);
		p[0][bny+5][2]=addBPoint(-0.5,0.625,1.0,p0);
		p[1][bny+5][2]=addBPoint(-0.25,0.625,1.0,p0);
		p[2][bny+5][2]=addBPoint(0,0.625,1.0,p0);
		p[3][bny+5][2]=addBPoint(0.25,0.625,1.0,p0);
		p[4][bny+5][2]=addBPoint(0.5,0.625,1.0,p0);
		
		p[0][bny+6][0]=addBPoint(-0.5,0.750,0,p0);	
		p[1][bny+6][0]=addBPoint(-0.25,0.750,0,p0);
		p[2][bny+6][0]=addBPoint(0,0.750,0,p0);
		p[3][bny+6][0]=addBPoint(0.25,0.750,0,p0);
		p[4][bny+6][0]=addBPoint(0.5,0.750,0,p0);		
		p[0][bny+6][1]=addBPoint(-0.5,0.750,0.5,p0);
		p[1][bny+6][1]=addBPoint(-0.25,0.750,0.5,p0);
		p[2][bny+6][1]=addBPoint(0,0.750,0.5,p0);
		p[3][bny+6][1]=addBPoint(0.25,0.750,0.5,p0);
		p[4][bny+6][1]=addBPoint(0.5,0.750,0.5,p0);
		p[0][bny+6][2]=addBPoint(-0.5,0.750,1.0,p0);
		p[1][bny+6][2]=addBPoint(-0.25,0.750,1.0,p0);
		p[2][bny+6][2]=addBPoint(0,1.0,0.750,p0);
		p[3][bny+6][2]=addBPoint(0.25,0.750,1.0,p0);
		p[4][bny+6][2]=addBPoint(0.5,0.750,1.0,p0);
		
		p[0][bny+7][0]=addBPoint(-0.5,0.875,0,p0);	
		p[1][bny+7][0]=addBPoint(-0.25,0.875,0,p0);
		p[2][bny+7][0]=addBPoint(0,0.875,0,p0);
		p[3][bny+7][0]=addBPoint(0.25,0.875,0,p0);
		p[4][bny+7][0]=addBPoint(0.5,0.875,0,p0);		
		p[0][bny+7][1]=addBPoint(-0.5,0.875,0.5,p0);
		p[1][bny+7][1]=addBPoint(-0.25,0.875,0.5,p0);
		p[2][bny+7][1]=addBPoint(0,1.0,0.875,p0);
		p[3][bny+7][1]=addBPoint(0.25,0.875,0.5,p0);
		p[4][bny+7][1]=addBPoint(0.5,0.875,0.5,p0);
		p[0][bny+7][2]=addBPoint(-0.5,0.875,1.0,p0);
		p[1][bny+7][2]=addBPoint(-0.25,0.875,1.0,p0);
		p[2][bny+7][2]=addBPoint(0,1.0,0.875,p0);
		p[3][bny+7][2]=addBPoint(0.25,0.875,1.0,p0);
		p[4][bny+7][2]=addBPoint(0.5,0.875,1.0,p0);
		
		p[0][bny+8][0]=addBPoint(-0.5,0.995,0,p0);	
		p[1][bny+8][0]=addBPoint(-0.25,1.0,0,p0);
		p[2][bny+8][0]=addBPoint(0,1.0,0,p0);
		p[3][bny+8][0]=addBPoint(0.25,1.0,0,p0);
		p[4][bny+8][0]=addBPoint(0.5,0.995,0,p0);		
		p[0][bny+8][1]=addBPoint(-0.5,0.995,0.5,p0);
		p[1][bny+8][1]=addBPoint(-0.25,1.0,0.5,p0);
		p[2][bny+8][1]=addBPoint(0,1.0,0.5,p0);
		p[3][bny+8][1]=addBPoint(0.25,1.0,0.5,p0);
		p[4][bny+8][1]=addBPoint(0.5,0.995,0.5,p0);
		p[0][bny+8][2]=addBPoint(-0.5,0.995,1.0,p0);
		p[1][bny+8][2]=addBPoint(-0.25,1.0,1.0,p0);
		p[2][bny+8][2]=addBPoint(0,1.0,1.0,p0);
		p[3][bny+8][2]=addBPoint(0.25,1.0,1.0,p0);
		p[4][bny+8][2]=addBPoint(0.5,0.995,1.0,p0);

		//front part:
		
		
		lw=(wheel_radius*2.0/Math.sqrt(3))/front_length;
		zw=wheel_radius/front_height;
		yw=0.5;
		
		double front_width0=front_width*(1-0.85)+x_side*0.85;  
		double front_width1=front_width*(1-0.75)+x_side*0.75; 
		double front_width2=front_width*(1-0.65)+x_side*0.65; 
		double front_width3=front_width*(1-0.5)+x_side*0.5; 
		
		double front_height0=front_height*(1-0.95)+z_side*0.95;
		double front_height1=front_height*(1-0.85)+z_side*0.85;
		double front_height2=front_height*(1-0.75)+z_side*0.75;
		double front_height3=front_height*(1-0.65)+z_side*0.65;
		
		Segments f0=new Segments(xc,front_width0,back_length+y_side,front_length,0,front_height0);
		Segments f1=new Segments(xc,front_width1,back_length+y_side,front_length,0,front_height1);
		Segments f2=new Segments(xc,front_width2,back_length+y_side,front_length,0,front_height2);
		Segments f3=new Segments(xc,front_width3,back_length+y_side,front_length,0,front_height3);
		Segments f4=new Segments(xc,front_width,back_length+y_side,front_length,0,front_height);
				
		
		p[0][bny+pny][0]=addBPoint(-0.5,yw-lw,0.0,f0);
		p[1][bny+pny][0]=addBPoint(-0.25,yw-lw,0.0,f0);
		p[2][bny+pny][0]=addBPoint(0,yw-lw,0.0,f0);
		p[3][bny+pny][0]=addBPoint(0.25,yw-lw,0.0,f0);
		p[4][bny+pny][0]=addBPoint(0.5,yw-lw,0.0,f0);	
		p[0][bny+pny][1]=addBPoint(-0.5,0.2,0.5,f0);
		p[1][bny+pny][1]=addBPoint(-0.25,0.2,0.5,f0);
		p[2][bny+pny][1]=addBPoint(0,0.2,0.5,f0);
		p[3][bny+pny][1]=addBPoint(0.25,0.2,0.5,f0);
		p[4][bny+pny][1]=addBPoint(0.5,0.2,0.5,f0);
		p[0][bny+pny][2]=addBPoint(-0.5,0.2,0.9,f0);
		p[1][bny+pny][2]=addBPoint(-0.25,0.2,1.0,f0);
		p[2][bny+pny][2]=addBPoint(0.0,0.2,1.0,f0);
		p[3][bny+pny][2]=addBPoint(0.25,0.2,1.0,f0);
		p[4][bny+pny][2]=addBPoint(0.5,0.2,0.9,f0);
		
		p[0][bny+pny+1][0]=addBPoint(-0.5,yw-lw*0.5,zw,f1);
		p[1][bny+pny+1][0]=addBPoint(-0.25,yw-lw*0.5,zw,f1);
		p[2][bny+pny+1][0]=addBPoint(0.0,yw-lw*0.5,zw,f1);
		p[3][bny+pny+1][0]=addBPoint(0.25,yw-lw*0.5,zw,f1);
		p[4][bny+pny+1][0]=addBPoint(0.5,yw-lw*0.5,zw,f1);	
		p[0][bny+pny+1][1]=addBPoint(-0.5,0.4,0.5,f1);
		p[1][bny+pny+1][1]=addBPoint(-0.25,0.4,0.5,f1);
		p[2][bny+pny+1][1]=addBPoint(0.0,0.4,0.5,f1);
		p[3][bny+pny+1][1]=addBPoint(0.25,0.4,0.5,f1);
		p[4][bny+pny+1][1]=addBPoint(0.5,0.4,0.5,f1);	
		p[0][bny+pny+1][2]=addBPoint(-0.5,0.4,0.9,f1);
		p[1][bny+pny+1][2]=addBPoint(-0.25,0.4,1.0,f1);
		p[2][bny+pny+1][2]=addBPoint(0.0,0.4,1.0,f1);
		p[3][bny+pny+1][2]=addBPoint(0.25,0.4,1.0,f1);
		p[4][bny+pny+1][2]=addBPoint(0.5,0.4,0.9,f1);
		
		p[0][bny+pny+2][0]=addBPoint(-0.5,yw+lw*0.5,zw,f2);
		p[1][bny+pny+2][0]=addBPoint(-0.25,yw+lw*0.5,zw,f2);
		p[2][bny+pny+2][0]=addBPoint(0.0,yw+lw*0.5,zw,f2);
		p[3][bny+pny+2][0]=addBPoint(0.25,yw+lw*0.5,zw,f2);
		p[4][bny+pny+2][0]=addBPoint(0.5,yw+lw*0.5,zw,f2);	
		p[0][bny+pny+2][1]=addBPoint(-0.5,0.6,0.5,f2);
		p[1][bny+pny+2][1]=addBPoint(-0.25,0.6,0.5,f2);
		p[2][bny+pny+2][1]=addBPoint(0.0,0.6,0.5,f2);
		p[3][bny+pny+2][1]=addBPoint(0.25,0.6,0.5,f2);
		p[4][bny+pny+2][1]=addBPoint(0.5,0.6,0.5,f2);	
		p[0][bny+pny+2][2]=addBPoint(-0.5,0.6,0.9,f2);
		p[1][bny+pny+2][2]=addBPoint(-0.25,0.6,1.0,f2);
		p[2][bny+pny+2][2]=addBPoint(0.0,0.6,1.0,f2);
		p[3][bny+pny+2][2]=addBPoint(0.25,0.6,1.0,f2);
		p[4][bny+pny+2][2]=addBPoint(0.5,0.6,0.9,f2);
		
		p[0][bny+pny+3][0]=addBPoint(-0.5,yw+lw,0.0,f3);
		p[1][bny+pny+3][0]=addBPoint(-0.25,yw+lw,0.0,f3);
		p[2][bny+pny+3][0]=addBPoint(0.0,yw+lw,0.0,f3);
		p[3][bny+pny+3][0]=addBPoint(0.25,yw+lw,0.0,f3);
		p[4][bny+pny+3][0]=addBPoint(0.5,yw+lw,0.0,f3);	
		p[0][bny+pny+3][1]=addBPoint(-0.5,0.8,0.5,f3);
		p[1][bny+pny+3][1]=addBPoint(-0.25,0.8,0.5,f3);
		p[2][bny+pny+3][1]=addBPoint(0.0,0.8,0.5,f3);
		p[3][bny+pny+3][1]=addBPoint(0.25,0.8,0.5,f3);
		p[4][bny+pny+3][1]=addBPoint(0.5,0.8,0.5,f3);	
		p[0][bny+pny+3][2]=addBPoint(-0.5,0.8,0.9,f3);
		p[1][bny+pny+3][2]=addBPoint(-0.25,0.8,1.0,f3);
		p[2][bny+pny+3][2]=addBPoint(0.0,0.8,1.0,f3);
		p[3][bny+pny+3][2]=addBPoint(0.25,0.8,1.0,f3);
		p[4][bny+pny+3][2]=addBPoint(0.5,0.8,0.9,f3);
		
		
		p[0][bny+pny+4][0]=addBPoint(-0.5,1.0,0.0,f4);
		p[1][bny+pny+4][0]=addBPoint(-0.25,1.0,0.0,f4);
		p[2][bny+pny+4][0]=addBPoint(0.0,1.0,0.0,f4);
		p[3][bny+pny+4][0]=addBPoint(0.25,1.0,0.0,f4);
		p[4][bny+pny+4][0]=addBPoint(0.5,1.0,0.0,f4);	
		p[0][bny+pny+4][1]=addBPoint(-0.5,0.9,0.5,f4);
		p[1][bny+pny+4][1]=addBPoint(-0.25,1.0,0.5,f4);
		p[2][bny+pny+4][1]=addBPoint(0.0,1.0,0.5,f4);
		p[3][bny+pny+4][1]=addBPoint(0.25,1.0,0.5,f4);
		p[4][bny+pny+4][1]=addBPoint(0.5,0.9,0.5,f4);	
		p[0][bny+pny+4][2]=addBPoint(-0.5,0.9,0.9,f4);
		p[1][bny+pny+4][2]=addBPoint(-0.25,1.0,1.0,f4);
		p[2][bny+pny+4][2]=addBPoint(0.0,1.0,1.0,f4);
		p[3][bny+pny+4][2]=addBPoint(0.25,1.0,1.0,f4);
		p[4][bny+pny+4][2]=addBPoint(0.5,0.9,0.9,f4);
		
		for(int i=0;i<pnx-1;i++){

			for(int j=0;j<numy-1;j++){ 

				for (int k = 0; k < pnz-1; k++) {


					if(i==0){

						LineData leftLD=addLine(p[i][j][k],p[i][j][k+1],p[i][j+1][k+1],p[i][j+1][k],Renderer3D.CAR_LEFT);

					}

				
					if(j==0){
						LineData backMask=addLine(p[i][j][k],p[i+1][j][k],p[i+1][j][k+1],p[i][j][k+1],Renderer3D.CAR_BACK);	
					}
					
					if(j<bny || j>= pny+bny-1){
						
						if(k+1==pnz-1){
							addLine(p[i][j][k+1],p[i+1][j][k+1],p[i+1][j+1][k+1],p[i][j+1][k+1],Renderer3D.CAR_TOP);
						}
					}
					
					if(j+1==numy-1){

						LineData frontMask=addLine(p[i][j+1][k],p[i][j+1][k+1],p[i+1][j+1][k+1],p[i+1][j+1][k],Renderer3D.CAR_FRONT);	
						
					}

					if(k==0){
						LineData bottomLD=addLine(p[i][j][k],p[i][j+1][k],p[i+1][j+1][k],p[i+1][j][k],Renderer3D.CAR_BOTTOM);
					}


					if(i+1==pnx-1){

						LineData rightLD=addLine(p[i+1][j][k],p[i+1][j+1][k],p[i+1][j+1][k+1],p[i+1][j][k+1],Renderer3D.CAR_RIGHT);
					}

				}
			}

		}

		
			
		

		//roof:	
		
		int rnz=3;
		
		BPoint[][][] pr=new BPoint[pnx][pny][rnz];
		
		double roofDY=(y_side-roof_length)/2.0;
		
		Segments r0=new Segments(xc,roof_width,roofDY+back_length,roof_length,z_side,roof_height);
		
		
		pr[0][0][0]=p[0][bny][pnz-1];	
		pr[1][0][0]=p[1][bny][pnz-1];	
		pr[2][0][0]=p[2][bny][pnz-1];	
		pr[3][0][0]=p[3][bny][pnz-1];		
		pr[4][0][0]=p[4][bny][pnz-1];			
		pr[0][1][0]=p[0][bny+1][pnz-1];	
		pr[4][1][0]=p[4][bny+1][pnz-1];		
		pr[0][2][0]=p[0][bny+2][pnz-1];		
		pr[4][2][0]=p[4][bny+2][pnz-1];		
		pr[0][3][0]=p[0][bny+3][pnz-1];			
		pr[4][3][0]=p[4][bny+3][pnz-1];			
		pr[0][4][0]=p[0][bny+4][pnz-1];			
		pr[4][4][0]=p[4][bny+4][pnz-1];	
		pr[0][5][0]=p[0][bny+5][pnz-1];	
		pr[4][5][0]=p[4][bny+5][pnz-1];	
		pr[0][6][0]=p[0][bny+6][pnz-1];		
		pr[4][6][0]=p[4][bny+6][pnz-1];	
		pr[0][7][0]=p[0][bny+7][pnz-1];		
		pr[4][7][0]=p[4][bny+7][pnz-1];	
		pr[0][8][0]=p[0][bny+8][pnz-1];	
		pr[1][8][0]=p[1][bny+8][pnz-1];	
		pr[2][8][0]=p[2][bny+8][pnz-1];	
		pr[3][8][0]=p[3][bny+8][pnz-1];	
		pr[4][8][0]=p[4][bny+8][pnz-1];	
		
		
		pr[0][0][1]=addBPoint(-0.5,0.0,0.75,r0);	
		pr[1][0][1]=addBPoint(-0.25,0.0,0.75,r0);	
		pr[2][0][1]=addBPoint(0.0,0.0,0.75,r0);	
		pr[3][0][1]=addBPoint(0.25,0.0,0.75,r0);	
		pr[4][0][1]=addBPoint(0.5,0.0,0.75,r0);		
		pr[0][1][1]=addBPoint(-0.5,0.125,0.75,r0);		
		pr[4][1][1]=addBPoint(0.5,0.125,0.75,r0);		
		pr[0][2][1]=addBPoint(-0.5,0.25,0.75,r0);			
		pr[4][2][1]=addBPoint(0.5,0.25,0.75,r0);		
		pr[0][3][1]=addBPoint(-0.5,0.375,0.75,r0);	
		pr[4][3][1]=addBPoint(0.5,0.375,0.75,r0);		
		pr[0][4][1]=addBPoint(-0.5,0.5,0.75,r0);		
		pr[4][4][1]=addBPoint(0.5,0.5,0.75,r0);
		pr[0][5][1]=addBPoint(-0.5,0.625,0.75,r0);		
		pr[4][5][1]=addBPoint(0.5,0.625,0.75,r0);
		pr[0][6][1]=addBPoint(-0.5,0.75,0.75,r0);	
		pr[4][6][1]=addBPoint(0.5,0.75,0.75,r0);
		pr[0][7][1]=addBPoint(-0.5,0.875,0.75,r0);	
		pr[4][7][1]=addBPoint(0.5,0.875,0.75,r0);
		pr[0][8][1]=addBPoint(-0.5,1.0,0.75,r0);	
		pr[1][8][1]=addBPoint(-0.25,1.0,0.75,r0);	
		pr[2][8][1]=addBPoint(0.0,1.0,0.75,r0);	
		pr[3][8][1]=addBPoint(0.25,1.0,0.75,r0);	
		pr[4][8][1]=addBPoint(0.5,1.0,0.75,r0);
		
		
		pr[0][0][2]=addBPoint(-0.5,0.0,0.9,r0);	
		pr[1][0][2]=addBPoint(-0.25,0.0,1.0,r0);	
		pr[2][0][2]=addBPoint(0.0,0.0,1.0,r0);	
		pr[3][0][2]=addBPoint(0.25,0.0,1.0,r0);	
		pr[4][0][2]=addBPoint(0.5,0.0,0.9,r0);		
		pr[0][1][2]=addBPoint(-0.5,0.125,0.9,r0);	
		pr[1][1][2]=addBPoint(-0.25,0.125,1.0,r0);	
		pr[2][1][2]=addBPoint(-0.0,0.125,1.0,r0);	
		pr[3][1][2]=addBPoint(0.25,0.125,1.0,r0);	
		pr[4][1][2]=addBPoint(0.5,0.125,0.9,r0);		
		pr[0][2][2]=addBPoint(-0.5,0.25,0.9,r0);	
		pr[1][2][2]=addBPoint(-0.25,0.25,1.0,r0);	
		pr[2][2][2]=addBPoint(0.0,0.25,1.0,r0);	
		pr[3][2][2]=addBPoint(0.25,0.25,1.0,r0);	
		pr[4][2][2]=addBPoint(0.5,0.25,0.9,r0);		
		pr[0][3][2]=addBPoint(-0.5,0.375,0.9,r0);	
		pr[1][3][2]=addBPoint(-0.25,0.375,1.0,r0);	
		pr[2][3][2]=addBPoint(0.0,0.375,1.0,r0);	
		pr[3][3][2]=addBPoint(0.25,0.375,1.0,r0);	
		pr[4][3][2]=addBPoint(0.5,0.375,0.9,r0);		
		pr[0][4][2]=addBPoint(-0.5,0.5,0.9,r0);	
		pr[1][4][2]=addBPoint(-0.25,0.5,1.0,r0);	
		pr[2][4][2]=addBPoint(0.0,0.5,1.0,r0);	
		pr[3][4][2]=addBPoint(0.25,0.5,1.0,r0);	
		pr[4][4][2]=addBPoint(0.5,0.5,0.9,r0);
		pr[0][5][2]=addBPoint(-0.5,0.625,0.9,r0);	
		pr[1][5][2]=addBPoint(-0.25,0.625,1.0,r0);	
		pr[2][5][2]=addBPoint(0.0,0.625,1.0,r0);	
		pr[3][5][2]=addBPoint(0.25,0.625,1.0,r0);	
		pr[4][5][2]=addBPoint(0.5,0.625,0.9,r0);
		pr[0][6][2]=addBPoint(-0.5,0.75,0.9,r0);	
		pr[1][6][2]=addBPoint(-0.25,0.75,1.0,r0);	
		pr[2][6][2]=addBPoint(0.0,0.75,1.0,r0);	
		pr[3][6][2]=addBPoint(0.25,0.75,1.0,r0);	
		pr[4][6][2]=addBPoint(0.5,0.75,0.9,r0);
		pr[0][7][2]=addBPoint(-0.5,0.875,0.9,r0);	
		pr[1][7][2]=addBPoint(-0.25,0.875,1.0,r0);	
		pr[2][7][2]=addBPoint(0.0,0.875,1.0,r0);	
		pr[3][7][2]=addBPoint(0.25,0.875,1.0,r0);	
		pr[4][7][2]=addBPoint(0.5,0.875,0.9,r0);
		pr[0][8][2]=addBPoint(-0.5,1.0,0.9,r0);	
		pr[1][8][2]=addBPoint(-0.25,1.0,1.0,r0);	
		pr[2][8][2]=addBPoint(0.0,1.0,1.0,r0);	
		pr[3][8][2]=addBPoint(0.25,1.0,1.0,r0);	
		pr[4][8][2]=addBPoint(0.5,1.0,0.9,r0);

		
		for (int i = 0; i < pnx-1; i++) {



			for (int j = 0; j < pny-1; j++) {
				
				for (int k = 0; k < rnz-1; k++) {
					


					if(i==0){

						 LineData leftRLD=addLine(pr[i][j+1][k],pr[i][j+1][k+1],pr[i][j][k+1],pr[i][j][k],Renderer3D.CAR_LEFT);

					}

						
					if(k+1==rnz-1){
					
						LineData topRLD=addLine(pr[i][j][k+1],pr[i+1][j][k+1],pr[i+1][j+1][k+1],pr[i][j+1][k+1],Renderer3D.CAR_TOP);

					}
					
					if(j==0){
						LineData backRLD=addLine(pr[i][j][k],pr[i+1][j][k],pr[i+1][j][k+1],pr[i][j][k+1],Renderer3D.CAR_BACK);
					}
					
						
					if(j+1==pny-1){

						LineData frontRLD=addLine(pr[i][j+1][k+1],pr[i][j+1][k],pr[i+1][j+1][k],pr[i+1][j+1][k+1],Renderer3D.CAR_FRONT);	
					}
					

					if(i+1==pnx-1){

						LineData rightRLD=addLine(pr[i+1][j][k+1],pr[i+1][j+1][k+1],pr[i+1][j+1][k],pr[i+1][j][k],Renderer3D.CAR_RIGHT);

					}
				}
				
			}

		}


		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}
	
	

	private PolygonMesh buildStarshipMesh() {
		
		points=new Vector();
		points.setSize(400);

		polyData=new Vector();
		
		
		n=0;
		
		double by=roof_length-y_side;
		
		int hnumx=5;
		int hnumy=5;
		int hnumz=2;
		
		Segments h0=new Segments(0,front_width,by+y_side+50,front_length,100,front_height);
		
		BPoint[][][] head=new BPoint[hnumx][hnumy][hnumz];
		
		head[0][0][0]=addBPoint(-0.5,0.0,0,h0);
		head[1][0][0]=addBPoint(-0.25,0.0,0,h0);
		head[2][0][0]=addBPoint(0.0,0.0,0,h0);	
		head[3][0][0]=addBPoint(0.25,0.0,0,h0);	
		head[4][0][0]=addBPoint(0.5,0.0,0,h0);	
		head[0][0][1]=addBPoint(-0.5,0.0,1.0,h0);
		head[1][0][1]=addBPoint(-0.25,0.0,1.0,h0);
		head[2][0][1]=addBPoint(0.0,0.0,1.0,h0);
		head[3][0][1]=addBPoint(0.25,0.0,1.0,h0);
		head[4][0][1]=addBPoint(0.5,0.0,1.0,h0);
		
		head[0][1][0]=addBPoint(-0.5,0.25,0,h0);
		head[1][1][0]=addBPoint(-0.25,0.25,0,h0);
		head[2][1][0]=addBPoint(0.0,0.25,0,h0);	
		head[3][1][0]=addBPoint(0.25,0.25,0,h0);	
		head[4][1][0]=addBPoint(0.5,0.25,0,h0);	
		head[0][1][1]=addBPoint(-0.5,0.25,1.0,h0);
		head[1][1][1]=addBPoint(-0.25,0.25,1.0,h0);
		head[2][1][1]=addBPoint(0.0,0.25,1.0,h0);
		head[3][1][1]=addBPoint(0.25,0.25,1.0,h0);
		head[4][1][1]=addBPoint(0.5,0.25,1.0,h0);
		
		
		head[0][2][0]=addBPoint(-0.5,0.5,0,h0);
		head[1][2][0]=addBPoint(-0.25,0.5,0,h0);
		head[2][2][0]=addBPoint(0.0,0.5,0,h0);	
		head[3][2][0]=addBPoint(0.25,0.5,0,h0);	
		head[4][2][0]=addBPoint(0.5,0.5,0,h0);	
		head[0][2][1]=addBPoint(-0.5,0.5,1.0,h0);
		head[1][2][1]=addBPoint(-0.25,0.5,1.0,h0);
		head[2][2][1]=addBPoint(0.0,0.5,1.0,h0);
		head[3][2][1]=addBPoint(0.25,0.5,1.0,h0);
		head[4][2][1]=addBPoint(0.5,0.5,1.0,h0);
		
		head[0][3][0]=addBPoint(-0.5,0.75,0,h0);
		head[1][3][0]=addBPoint(-0.25,0.75,0,h0);
		head[2][3][0]=addBPoint(0.0,0.75,0,h0);	
		head[3][3][0]=addBPoint(0.25,0.75,0,h0);	
		head[4][3][0]=addBPoint(0.5,0.75,0,h0);	
		head[0][3][1]=addBPoint(-0.5,0.75,1.0,h0);
		head[1][3][1]=addBPoint(-0.25,0.75,1.0,h0);
		head[2][3][1]=addBPoint(0.0,0.75,1.0,h0);
		head[3][3][1]=addBPoint(0.25,0.75,1.0,h0);
		head[4][3][1]=addBPoint(0.5,0.75,1.0,h0);
		
		head[0][4][0]=addBPoint(-0.5,1.0,0,h0);
		head[1][4][0]=addBPoint(-0.25,1.0,0,h0);
		head[2][4][0]=addBPoint(0.0,1.0,0,h0);	
		head[3][4][0]=addBPoint(0.25,1.0,0,h0);	
		head[4][4][0]=addBPoint(0.5,1.0,0,h0);	
		head[0][4][1]=addBPoint(-0.5,1.0,1.0,h0);
		head[1][4][1]=addBPoint(-0.25,1.0,1.0,h0);
		head[2][4][1]=addBPoint(0.0,1.0,1.0,h0);
		head[3][4][1]=addBPoint(0.25,1.0,1.0,h0);
		head[4][4][1]=addBPoint(0.5,1.0,1.0,h0);
		
		for(int i=0;i<hnumx-1;i++){

			for (int j = 0; j < hnumy-1; j++) {


				for(int k=0;k<hnumz-1;k++){		

					if(i==0){
						addLine(head[i][j][k],head[i][j][k+1],head[i][j+1][k+1],head[i][j+1][k],Renderer3D.CAR_LEFT);
					}

					if(k==0)
						addLine(head[i][j][k],head[i][j+1][k],head[i+1][j+1][k],head[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					if(i+1==hnumx-1){
						addLine(head[i+1][j][k],head[i+1][j+1][k],head[i+1][j+1][k+1],head[i+1][j][k+1],Renderer3D.CAR_RIGHT);
					}

					if(j==0)
						addLine(head[i][j][k],head[i+1][j][k],head[i+1][j][k+1],head[i][j][k+1],Renderer3D.CAR_BACK);		

					if(j+1==hnumy-1)
						addLine(head[i][j+1][k],head[i][j+1][k+1],head[i+1][j+1][k+1],head[i+1][j+1][k],Renderer3D.CAR_FRONT);

					if(k+1==hnumz-1)
						addLine(head[i][j][k+1],head[i+1][j][k+1],head[i+1][j+1][k+1],head[i][j+1][k+1],Renderer3D.CAR_TOP);

				}

			}

		}
		
				
		int bnumx=2;
		int bnumy=5;
		int bnumz=2;
		
		
		
		Segments b0=new Segments(0,x_side,by,y_side,0,z_side);
		
		BPoint[][][] body=new BPoint[bnumx][bnumy][bnumz];
		
		body[0][0][0]=addBPoint(-0.5,0.0,0,b0);
		body[1][0][0]=addBPoint(0.5,0.0,0,b0);	
		body[0][0][1]=addBPoint(-0.5,0.0,1.0,b0);
		body[1][0][1]=addBPoint(0.5,0.0,1.0,b0);
		
		body[0][1][0]=addBPoint(-0.5,0.25,0,b0);		
		body[1][1][0]=addBPoint(0.5,0.25,0,b0);	
		body[0][1][1]=addBPoint(-0.5,0.25,1.0,b0);		
		body[1][1][1]=addBPoint(0.5,0.25,1.0,b0);
		
		
		body[0][2][0]=addBPoint(-0.5,0.5,0,b0);		
		body[1][2][0]=addBPoint(0.5,0.5,0,b0);	
		body[0][2][1]=addBPoint(-0.5,0.5,1.0,b0);	
		body[1][2][1]=addBPoint(0.5,0.5,1.0,b0);
		
		body[0][3][0]=addBPoint(-0.5,0.75,0,b0);		
		body[1][3][0]=addBPoint(0.5,0.75,0,b0);	
		body[0][3][1]=addBPoint(-0.5,0.75,1.0,b0);		
		body[1][3][1]=addBPoint(0.5,0.75,1.0,b0);
		
		body[0][4][0]=addBPoint(-0.5,1.0,0,b0);		
		body[1][4][0]=addBPoint(0.5,1.0,0,b0);	
		body[0][4][1]=addBPoint(-0.5,1.0,1.0,b0);
		body[1][4][1]=addBPoint(0.5,1.0,1.0,b0);
		
		for(int i=0;i<bnumx-1;i++){

			for (int j = 0; j < bnumy-1; j++) {


				for(int k=0;k<bnumz-1;k++){		

					if(i==0){
						addLine(body[i][j][k],body[i][j][k+1],body[i][j+1][k+1],body[i][j+1][k],Renderer3D.CAR_LEFT);
					}

					if(k==0)
						addLine(body[i][j][k],body[i][j+1][k],body[i+1][j+1][k],body[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					if(i+1==bnumx-1){
						addLine(body[i+1][j][k],body[i+1][j+1][k],body[i+1][j+1][k+1],body[i+1][j][k+1],Renderer3D.CAR_RIGHT);
					}

					if(j==0)
						addLine(body[i][j][k],body[i+1][j][k],body[i+1][j][k+1],body[i][j][k+1],Renderer3D.CAR_BACK);		

					if(j+1==bnumy-1)
						addLine(body[i][j+1][k],body[i][j+1][k+1],body[i+1][j+1][k+1],body[i+1][j+1][k],Renderer3D.CAR_FRONT);

					if(k+1==bnumz-1)
						addLine(body[i][j][k+1],body[i+1][j][k+1],body[i+1][j+1][k+1],body[i][j+1][k+1],Renderer3D.CAR_TOP);

				}

			}

		}
		
		int nnumx=2;
		int nnumy=2;
		int nnumz=2;
		
		Segments n0=new Segments(0,50,0,150,0,50);
		
		BPoint[][][] neck=new BPoint[nnumx][nnumy][nnumz];
		
		neck[0][0][0]=body[0][4][0];
		neck[1][0][0]=body[1][4][0];
		neck[0][0][1]=body[0][4][1];
		neck[1][0][1]=body[1][4][1];
		
		neck[0][1][0]=head[1][0][0];
		neck[1][1][0]=head[3][0][0];
		neck[0][1][1]=head[1][0][1];
		neck[1][1][1]=head[3][0][1];
		
	
		
		for(int i=0;i<nnumx-1;i++){

			for (int j = 0; j < nnumy-1; j++) {


				for(int k=0;k<nnumz-1;k++){		

					if(i==0){
						addLine(neck[i][j][k],neck[i][j][k+1],neck[i][j+1][k+1],neck[i][j+1][k],Renderer3D.CAR_LEFT);
					}

					if(k==0)
						addLine(neck[i][j][k],neck[i][j+1][k],neck[i+1][j+1][k],neck[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					if(i+1==nnumx-1){
						addLine(neck[i+1][j][k],neck[i+1][j+1][k],neck[i+1][j+1][k+1],neck[i+1][j][k+1],Renderer3D.CAR_RIGHT);
					}

					if(j==0)
						addLine(neck[i][j][k],neck[i+1][j][k],neck[i+1][j][k+1],neck[i][j][k+1],Renderer3D.CAR_BACK);		

					if(j+1==nnumy-1)
						addLine(neck[i][j+1][k],neck[i][j+1][k+1],neck[i+1][j+1][k+1],neck[i+1][j+1][k],Renderer3D.CAR_FRONT);

					if(k+1==nnumz-1)
						addLine(neck[i][j][k+1],neck[i+1][j][k+1],neck[i+1][j+1][k+1],neck[i][j+1][k+1],Renderer3D.CAR_TOP);

				}

			}

		}
		
		
		int wnumx=5;
		int wnumy=5;
		int wnumz=2;
		
		Segments lw=new Segments(-x_side-roof_width,roof_width,0,roof_length,100,roof_height);
		
		BPoint[][][] leftWing=new BPoint[wnumx][wnumy][wnumz];
		
		leftWing[0][0][0]=addBPoint(-0.5,0.0,0,lw);
		leftWing[1][0][0]=addBPoint(-0.25,0.0,0,lw);
		leftWing[2][0][0]=addBPoint(0.0,0.0,0,lw);	
		leftWing[3][0][0]=addBPoint(0.25,0.0,0,lw);	
		leftWing[4][0][0]=addBPoint(0.5,0.0,0,lw);	
		leftWing[0][0][1]=addBPoint(-0.5,0.0,1.0,lw);
		leftWing[1][0][1]=addBPoint(-0.25,0.0,1.0,lw);
		leftWing[2][0][1]=addBPoint(0.0,0.0,1.0,lw);
		leftWing[3][0][1]=addBPoint(0.25,0.0,1.0,lw);
		leftWing[4][0][1]=addBPoint(0.5,0.0,1.0,lw);
		
		leftWing[0][1][0]=addBPoint(-0.5,0.25,0,lw);
		leftWing[1][1][0]=addBPoint(-0.25,0.25,0,lw);
		leftWing[2][1][0]=addBPoint(0.0,0.25,0,lw);	
		leftWing[3][1][0]=addBPoint(0.25,0.25,0,lw);	
		leftWing[4][1][0]=addBPoint(0.5,0.25,0,lw);	
		leftWing[0][1][1]=addBPoint(-0.5,0.25,1.0,lw);
		leftWing[1][1][1]=addBPoint(-0.25,0.25,1.0,lw);
		leftWing[2][1][1]=addBPoint(0.0,0.25,1.0,lw);
		leftWing[3][1][1]=addBPoint(0.25,0.25,1.0,lw);
		leftWing[4][1][1]=addBPoint(0.5,0.25,1.0,lw);
		
		
		leftWing[0][2][0]=addBPoint(-0.5,0.5,0,lw);
		leftWing[1][2][0]=addBPoint(-0.25,0.5,0,lw);
		leftWing[2][2][0]=addBPoint(0.0,0.5,0,lw);	
		leftWing[3][2][0]=addBPoint(0.25,0.5,0,lw);	
		leftWing[4][2][0]=addBPoint(0.5,0.5,0,lw);	
		leftWing[0][2][1]=addBPoint(-0.5,0.5,1.0,lw);
		leftWing[1][2][1]=addBPoint(-0.25,0.5,1.0,lw);
		leftWing[2][2][1]=addBPoint(0.0,0.5,1.0,lw);
		leftWing[3][2][1]=addBPoint(0.25,0.5,1.0,lw);
		leftWing[4][2][1]=addBPoint(0.5,0.5,1.0,lw);
		
		leftWing[0][3][0]=addBPoint(-0.5,0.75,0,lw);
		leftWing[1][3][0]=addBPoint(-0.25,0.75,0,lw);
		leftWing[2][3][0]=addBPoint(0.0,0.75,0,lw);	
		leftWing[3][3][0]=addBPoint(0.25,0.75,0,lw);	
		leftWing[4][3][0]=addBPoint(0.5,0.75,0,lw);	
		leftWing[0][3][1]=addBPoint(-0.5,0.75,1.0,lw);
		leftWing[1][3][1]=addBPoint(-0.25,0.75,1.0,lw);
		leftWing[2][3][1]=addBPoint(0.0,0.75,1.0,lw);
		leftWing[3][3][1]=addBPoint(0.25,0.75,1.0,lw);
		leftWing[4][3][1]=addBPoint(0.5,0.75,1.0,lw);
		
		leftWing[0][4][0]=addBPoint(-0.5,1.0,0,lw);
		leftWing[1][4][0]=addBPoint(-0.25,1.0,0,lw);
		leftWing[2][4][0]=addBPoint(0.0,1.0,0,lw);	
		leftWing[3][4][0]=addBPoint(0.25,1.0,0,lw);	
		leftWing[4][4][0]=addBPoint(0.5,1.0,0,lw);	
		leftWing[0][4][1]=addBPoint(-0.5,1.0,1.0,lw);
		leftWing[1][4][1]=addBPoint(-0.25,1.0,1.0,lw);
		leftWing[2][4][1]=addBPoint(0.0,1.0,1.0,lw);
		leftWing[3][4][1]=addBPoint(0.25,1.0,1.0,lw);
		leftWing[4][4][1]=addBPoint(0.5,1.0,1.0,lw);
		
		for(int i=0;i<wnumx-1;i++){

			for (int j = 0; j < wnumy-1; j++) {


				for(int k=0;k<wnumz-1;k++){		

					if(i==0){
						addLine(leftWing[i][j][k],leftWing[i][j][k+1],leftWing[i][j+1][k+1],leftWing[i][j+1][k],Renderer3D.CAR_LEFT);
					}

					if(k==0)
						addLine(leftWing[i][j][k],leftWing[i][j+1][k],leftWing[i+1][j+1][k],leftWing[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					if(i+1==wnumx-1){
						addLine(leftWing[i+1][j][k],leftWing[i+1][j+1][k],leftWing[i+1][j+1][k+1],leftWing[i+1][j][k+1],Renderer3D.CAR_RIGHT);
					}

					if(j==0)
						addLine(leftWing[i][j][k],leftWing[i+1][j][k],leftWing[i+1][j][k+1],leftWing[i][j][k+1],Renderer3D.CAR_BACK);		

					if(j+1==wnumy-1)
						addLine(leftWing[i][j+1][k],leftWing[i][j+1][k+1],leftWing[i+1][j+1][k+1],leftWing[i+1][j+1][k],Renderer3D.CAR_FRONT);

					if(k+1==wnumz-1)
						addLine(leftWing[i][j][k+1],leftWing[i+1][j][k+1],leftWing[i+1][j+1][k+1],leftWing[i][j+1][k+1],Renderer3D.CAR_TOP);

				}

			}

		}
		
		int unx=2;
		int uny=2;
		int unz=2;
		
		BPoint[][][] leftUpright=new BPoint[unx][uny][unz];
		
		leftUpright[0][0][0]=leftWing[4][2][0];		
		leftUpright[0][0][1]=leftWing[4][2][1];		
		leftUpright[0][1][0]=leftWing[4][3][0];		
		leftUpright[0][1][1]=leftWing[4][3][1];
		

		leftUpright[1][0][0]=body[0][2][0];
		leftUpright[1][0][1]=body[0][2][1];
		leftUpright[1][1][0]=body[0][3][0];
		leftUpright[1][1][1]=body[0][3][1];
		
		for(int i=0;i<unx-1;i++){

			for (int j = 0; j < uny-1; j++) {


				for(int k=0;k<unz-1;k++){		

					if(i==0){
						addLine(leftUpright[i][j][k],leftUpright[i][j][k+1],leftUpright[i][j+1][k+1],leftUpright[i][j+1][k],Renderer3D.CAR_LEFT);
					}

					if(k==0)
						addLine(leftUpright[i][j][k],leftUpright[i][j+1][k],leftUpright[i+1][j+1][k],leftUpright[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					if(i+1==unx-1){
						addLine(leftUpright[i+1][j][k],leftUpright[i+1][j+1][k],leftUpright[i+1][j+1][k+1],leftUpright[i+1][j][k+1],Renderer3D.CAR_RIGHT);
					}

					if(j==0)
						addLine(leftUpright[i][j][k],leftUpright[i+1][j][k],leftUpright[i+1][j][k+1],leftUpright[i][j][k+1],Renderer3D.CAR_BACK);		

					if(j+1==uny-1)
						addLine(leftUpright[i][j+1][k],leftUpright[i][j+1][k+1],leftUpright[i+1][j+1][k+1],leftUpright[i+1][j+1][k],Renderer3D.CAR_FRONT);

					if(k+1==unz-1)
						addLine(leftUpright[i][j][k+1],leftUpright[i+1][j][k+1],leftUpright[i+1][j+1][k+1],leftUpright[i][j+1][k+1],Renderer3D.CAR_TOP);

				}

			}

		}
		
		
		Segments rw=new Segments(x_side+roof_width,roof_width,0,roof_length,100,roof_height);
		
		BPoint[][][] rightWing=new BPoint[wnumx][wnumy][wnumz];
		
		rightWing[0][0][0]=addBPoint(-0.5,0.0,0,rw);
		rightWing[1][0][0]=addBPoint(-0.25,0.0,0,rw);
		rightWing[2][0][0]=addBPoint(0.0,0.0,0,rw);	
		rightWing[3][0][0]=addBPoint(0.25,0.0,0,rw);	
		rightWing[4][0][0]=addBPoint(0.5,0.0,0,rw);	
		rightWing[0][0][1]=addBPoint(-0.5,0.0,1.0,rw);
		rightWing[1][0][1]=addBPoint(-0.25,0.0,1.0,rw);
		rightWing[2][0][1]=addBPoint(0.0,0.0,1.0,rw);
		rightWing[3][0][1]=addBPoint(0.25,0.0,1.0,rw);
		rightWing[4][0][1]=addBPoint(0.5,0.0,1.0,rw);
		
		rightWing[0][1][0]=addBPoint(-0.5,0.25,0,rw);
		rightWing[1][1][0]=addBPoint(-0.25,0.25,0,rw);
		rightWing[2][1][0]=addBPoint(0.0,0.25,0,rw);	
		rightWing[3][1][0]=addBPoint(0.25,0.25,0,rw);	
		rightWing[4][1][0]=addBPoint(0.5,0.25,0,rw);	
		rightWing[0][1][1]=addBPoint(-0.5,0.25,1.0,rw);
		rightWing[1][1][1]=addBPoint(-0.25,0.25,1.0,rw);
		rightWing[2][1][1]=addBPoint(0.0,0.25,1.0,rw);
		rightWing[3][1][1]=addBPoint(0.25,0.25,1.0,rw);
		rightWing[4][1][1]=addBPoint(0.5,0.25,1.0,rw);
		
		
		rightWing[0][2][0]=addBPoint(-0.5,0.5,0,rw);
		rightWing[1][2][0]=addBPoint(-0.25,0.5,0,rw);
		rightWing[2][2][0]=addBPoint(0.0,0.5,0,rw);	
		rightWing[3][2][0]=addBPoint(0.25,0.5,0,rw);	
		rightWing[4][2][0]=addBPoint(0.5,0.5,0,rw);	
		rightWing[0][2][1]=addBPoint(-0.5,0.5,1.0,rw);
		rightWing[1][2][1]=addBPoint(-0.25,0.5,1.0,rw);
		rightWing[2][2][1]=addBPoint(0.0,0.5,1.0,rw);
		rightWing[3][2][1]=addBPoint(0.25,0.5,1.0,rw);
		rightWing[4][2][1]=addBPoint(0.5,0.5,1.0,rw);
		
		rightWing[0][3][0]=addBPoint(-0.5,0.75,0,rw);
		rightWing[1][3][0]=addBPoint(-0.25,0.75,0,rw);
		rightWing[2][3][0]=addBPoint(0.0,0.75,0,rw);	
		rightWing[3][3][0]=addBPoint(0.25,0.75,0,rw);	
		rightWing[4][3][0]=addBPoint(0.5,0.75,0,rw);	
		rightWing[0][3][1]=addBPoint(-0.5,0.75,1.0,rw);
		rightWing[1][3][1]=addBPoint(-0.25,0.75,1.0,rw);
		rightWing[2][3][1]=addBPoint(0.0,0.75,1.0,rw);
		rightWing[3][3][1]=addBPoint(0.25,0.75,1.0,rw);
		rightWing[4][3][1]=addBPoint(0.5,0.75,1.0,rw);
		
		rightWing[0][4][0]=addBPoint(-0.5,1.0,0,rw);
		rightWing[1][4][0]=addBPoint(-0.25,1.0,0,rw);
		rightWing[2][4][0]=addBPoint(0.0,1.0,0,rw);	
		rightWing[3][4][0]=addBPoint(0.25,1.0,0,rw);	
		rightWing[4][4][0]=addBPoint(0.5,1.0,0,rw);	
		rightWing[0][4][1]=addBPoint(-0.5,1.0,1.0,rw);
		rightWing[1][4][1]=addBPoint(-0.25,1.0,1.0,rw);
		rightWing[2][4][1]=addBPoint(0.0,1.0,1.0,rw);
		rightWing[3][4][1]=addBPoint(0.25,1.0,1.0,rw);
		rightWing[4][4][1]=addBPoint(0.5,1.0,1.0,rw);
		
		for(int i=0;i<wnumx-1;i++){

			for (int j = 0; j < wnumy-1; j++) {


				for(int k=0;k<wnumz-1;k++){		

					if(i==0){
						addLine(rightWing[i][j][k],rightWing[i][j][k+1],rightWing[i][j+1][k+1],rightWing[i][j+1][k],Renderer3D.CAR_LEFT);
					}

					if(k==0)
						addLine(rightWing[i][j][k],rightWing[i][j+1][k],rightWing[i+1][j+1][k],rightWing[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					if(i+1==wnumx-1){
						addLine(rightWing[i+1][j][k],rightWing[i+1][j+1][k],rightWing[i+1][j+1][k+1],rightWing[i+1][j][k+1],Renderer3D.CAR_RIGHT);
					}

					if(j==0)
						addLine(rightWing[i][j][k],rightWing[i+1][j][k],rightWing[i+1][j][k+1],rightWing[i][j][k+1],Renderer3D.CAR_BACK);		

					if(j+1==wnumy-1)
						addLine(rightWing[i][j+1][k],rightWing[i][j+1][k+1],rightWing[i+1][j+1][k+1],rightWing[i+1][j+1][k],Renderer3D.CAR_FRONT);

					if(k+1==wnumz-1)
						addLine(rightWing[i][j][k+1],rightWing[i+1][j][k+1],rightWing[i+1][j+1][k+1],rightWing[i][j+1][k+1],Renderer3D.CAR_TOP);

				}

			}

		}
		
		BPoint[][][] rightUpright=new BPoint[unx][uny][unz];
		
		rightUpright[0][0][0]=body[1][2][0];
		rightUpright[0][0][1]=body[1][2][1];
		rightUpright[0][1][0]=body[1][3][0];
		rightUpright[0][1][1]=body[1][3][1];
		
		
		rightUpright[1][0][0]=rightWing[0][2][0];		
		rightUpright[1][0][1]=rightWing[0][2][1];		
		rightUpright[1][1][0]=rightWing[0][3][0];		
		rightUpright[1][1][1]=rightWing[0][3][1];
		
		for(int i=0;i<unx-1;i++){

			for (int j = 0; j < uny-1; j++) {


				for(int k=0;k<unz-1;k++){		

					if(i==0){
						addLine(rightUpright[i][j][k],rightUpright[i][j][k+1],rightUpright[i][j+1][k+1],rightUpright[i][j+1][k],Renderer3D.CAR_LEFT);
					}

					if(k==0)
						addLine(rightUpright[i][j][k],rightUpright[i][j+1][k],rightUpright[i+1][j+1][k],rightUpright[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					if(i+1==unx-1){
						addLine(rightUpright[i+1][j][k],rightUpright[i+1][j+1][k],rightUpright[i+1][j+1][k+1],rightUpright[i+1][j][k+1],Renderer3D.CAR_RIGHT);
					}

					if(j==0)
						addLine(rightUpright[i][j][k],rightUpright[i+1][j][k],rightUpright[i+1][j][k+1],rightUpright[i][j][k+1],Renderer3D.CAR_BACK);		

					if(j+1==uny-1)
						addLine(rightUpright[i][j+1][k],rightUpright[i][j+1][k+1],rightUpright[i+1][j+1][k+1],rightUpright[i+1][j+1][k],Renderer3D.CAR_FRONT);

					if(k+1==unz-1)
						addLine(rightUpright[i][j][k+1],rightUpright[i+1][j][k+1],rightUpright[i+1][j+1][k+1],rightUpright[i][j+1][k+1],Renderer3D.CAR_TOP);

				}

			}

		}
		
		PolygonMesh pm=new PolygonMesh(points,polyData);
		
		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildF1CarMesh() {
		
		points=new Vector();
		points.setSize(400);

		polyData=new Vector();
		
		n=0;
		
		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}
	
	private PolygonMesh buildShipMesh() {
		
		points=new Vector();
		points.setSize(400);

		polyData=new Vector();
		
		n=0;
		
		
		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildTruckMesh() {
		
		points=new Vector();
		points.setSize(500);

		polyData=new Vector();

		//basic sides: 
		
		n=0;
		
		
		//front:
		
		int fnx=5;
		int fny=6;
		int fnz=5;
		
		double xc=x_side*0.5;
		
		double lw=(wheel_radius*2.0/Math.sqrt(3))/front_length;
		double zw=wheel_radius/front_height;
		double yw=0.5;
		
		BPoint[][][] pFront=new BPoint[fnx][fny][fnz];
		
		Segments f0=new Segments(xc,front_width,back_length+y_side,front_length,0,front_height);
		
		pFront[0][0][0]=addBPoint(-0.5,0,0,f0);
		pFront[1][0][0]=addBPoint(-0.25,0,0,f0);
		pFront[2][0][0]=addBPoint(0.0,0.0,0,f0);
		pFront[3][0][0]=addBPoint(0.25,0,0,f0);	
		pFront[4][0][0]=addBPoint(0.5,0,0,f0);		
		pFront[0][1][0]=addBPoint(-0.5,yw-lw,0,f0);
		pFront[1][1][0]=addBPoint(-0.25,yw-lw,0,f0);
		pFront[2][1][0]=addBPoint(0.0,yw-lw,0,f0);
		pFront[3][1][0]=addBPoint(0.25,yw-lw,0,f0);
		pFront[4][1][0]=addBPoint(0.5,yw-lw,0,f0);		
		pFront[0][2][0]=addBPoint(-0.5,yw-lw*0.5,zw,f0);
		pFront[1][2][0]=addBPoint(-0.25,yw-lw*0.5,zw,f0);
		pFront[2][2][0]=addBPoint(0.0,yw-lw*0.5,zw,f0);
		pFront[3][2][0]=addBPoint(0.25,yw-lw*0.5,zw,f0);
		pFront[4][2][0]=addBPoint(0.5,yw-lw*0.5,zw,f0);		
		pFront[0][3][0]=addBPoint(-0.5,yw+lw*0.5,zw,f0);
		pFront[1][3][0]=addBPoint(-0.25,yw+lw*0.5,zw,f0);
		pFront[2][3][0]=addBPoint(0.0,yw+lw*0.5,zw,f0);
		pFront[3][3][0]=addBPoint(0.25,yw+lw*0.5,zw,f0);
		pFront[4][3][0]=addBPoint(0.5,yw+lw*0.5,zw,f0);		
		pFront[0][4][0]=addBPoint(-0.5,yw+lw,0,f0);
		pFront[1][4][0]=addBPoint(-0.25,yw+lw,0,f0);
		pFront[2][4][0]=addBPoint(0.0,yw+lw,0,f0);
		pFront[3][4][0]=addBPoint(0.25,yw+lw,0,f0);
		pFront[4][4][0]=addBPoint(0.5,yw+lw,0,f0);		
		pFront[0][5][0]=addBPoint(-0.5,0.9,0,f0);
		pFront[1][5][0]=addBPoint(-0.25,1.0,0,f0);
		pFront[2][5][0]=addBPoint(0.0,1.0,0,f0);
		pFront[3][5][0]=addBPoint(0.25,1.0,0,f0);
		pFront[4][5][0]=addBPoint(0.5,0.9,0,f0);		
	
		pFront[0][0][1]=addBPoint(-0.5,0.0,0.25,f0);	
		pFront[1][0][1]=addBPoint(-0.25,0.0,0.25,f0);	
		pFront[2][0][1]=addBPoint(0.0,0.0,0.25,f0);
		pFront[3][0][1]=addBPoint(0.25,0.0,0.25,f0);
		pFront[4][0][1]=addBPoint(0.5,0.0,0.25,f0);	
		pFront[0][1][1]=addBPoint(-0.5,0.2,0.25,f0);		
		pFront[4][1][1]=addBPoint(0.5,0.2,0.25,f0);
		pFront[0][2][1]=addBPoint(-0.5,0.4,0.25,f0);		
		pFront[4][2][1]=addBPoint(0.5,0.4,0.25,f0);
		pFront[0][3][1]=addBPoint(-0.5,0.6,0.25,f0);	
		pFront[4][3][1]=addBPoint(0.5,0.6,0.25,f0);
		pFront[0][4][1]=addBPoint(-0.5,0.8,0.25,f0);	
		pFront[4][4][1]=addBPoint(0.5,0.8,0.25,f0);
		pFront[0][5][1]=addBPoint(-0.5,0.9,0.25,f0);	
		pFront[1][5][1]=addBPoint(-0.25,1.0,0.25,f0);
		pFront[2][5][1]=addBPoint(0.0,1.0,0.25,f0);
		pFront[3][5][1]=addBPoint(0.25,1.0,0.25,f0);
		pFront[4][5][1]=addBPoint(0.5,0.9,0.25,f0);		
		
		pFront[0][0][2]=addBPoint(-0.5,0.0,0.5,f0);	
		pFront[1][0][2]=addBPoint(-0.25,0.0,0.5,f0);	
		pFront[2][0][2]=addBPoint(0.0,0.0,0.5,f0);
		pFront[3][0][2]=addBPoint(0.25,0.0,0.5,f0);
		pFront[4][0][2]=addBPoint(0.5,0.0,0.5,f0);	
		pFront[0][1][2]=addBPoint(-0.5,0.2,0.5,f0);		
		pFront[4][1][2]=addBPoint(0.5,0.2,0.5,f0);
		pFront[0][2][2]=addBPoint(-0.5,0.4,0.5,f0);		
		pFront[4][2][2]=addBPoint(0.5,0.4,0.5,f0);
		pFront[0][3][2]=addBPoint(-0.5,0.6,0.5,f0);	
		pFront[4][3][2]=addBPoint(0.5,0.6,0.5,f0);
		pFront[0][4][2]=addBPoint(-0.5,0.8,0.5,f0);	
		pFront[4][4][2]=addBPoint(0.5,0.8,0.5,f0);
		pFront[0][5][2]=addBPoint(-0.5,0.9,0.5,f0);	
		pFront[1][5][2]=addBPoint(-0.25,1.0,0.5,f0);
		pFront[2][5][2]=addBPoint(0.0,1.0,0.5,f0);
		pFront[3][5][2]=addBPoint(0.25,1.0,0.5,f0);
		pFront[4][5][2]=addBPoint(0.5,0.9,0.5,f0);		
		
		pFront[0][0][3]=addBPoint(-0.5,0.0,0.75,f0);	
		pFront[1][0][3]=addBPoint(-0.25,0.0,0.75,f0);	
		pFront[2][0][3]=addBPoint(0.0,0.0,0.75,f0);
		pFront[3][0][3]=addBPoint(0.25,0.0,0.75,f0);
		pFront[4][0][3]=addBPoint(0.5,0.0,0.75,f0);	
		pFront[0][1][3]=addBPoint(-0.5,0.2,0.75,f0);		
		pFront[4][1][3]=addBPoint(0.5,0.2,0.75,f0);
		pFront[0][2][3]=addBPoint(-0.5,0.4,0.75,f0);		
		pFront[4][2][3]=addBPoint(0.5,0.4,0.75,f0);
		pFront[0][3][3]=addBPoint(-0.5,0.6,0.75,f0);	
		pFront[4][3][3]=addBPoint(0.5,0.6,0.75,f0);
		pFront[0][4][3]=addBPoint(-0.5,0.8,0.75,f0);	
		pFront[4][4][3]=addBPoint(0.5,0.8,0.75,f0);
		pFront[0][5][3]=addBPoint(-0.5,0.9,0.75,f0);	
		pFront[1][5][3]=addBPoint(-0.25,1.0,0.75,f0);
		pFront[2][5][3]=addBPoint(0.0,1.0,0.75,f0);
		pFront[3][5][3]=addBPoint(0.25,1.0,0.75,f0);
		pFront[4][5][3]=addBPoint(0.5,0.9,0.75,f0);	
		
		pFront[0][0][4]=addBPoint(-0.5,0.0,1.0,f0);	
		pFront[1][0][4]=addBPoint(-0.25,0.0,1.0,f0);	
		pFront[2][0][4]=addBPoint(0.0,0.0,1.0,f0);
		pFront[3][0][4]=addBPoint(0.25,0.0,1.0,f0);
		pFront[4][0][4]=addBPoint(0.5,0.0,1.0,f0);	
		pFront[0][1][4]=addBPoint(-0.5,0.2,1.0,f0);
		pFront[1][1][4]=addBPoint(-0.25,0.2,1.0,f0);	
		pFront[2][1][4]=addBPoint(0.0,0.2,1.0,f0);
		pFront[3][1][4]=addBPoint(0.25,0.2,1.0,f0);
		pFront[4][1][4]=addBPoint(0.5,0.2,1.0,f0);
		pFront[0][2][4]=addBPoint(-0.5,0.4,1.0,f0);
		pFront[1][2][4]=addBPoint(-0.25,0.4,1.0,f0);	
		pFront[2][2][4]=addBPoint(0.0,0.4,1.0,f0);
		pFront[3][2][4]=addBPoint(0.25,0.4,1.0,f0);
		pFront[4][2][4]=addBPoint(0.5,0.4,1.0,f0);
		pFront[0][3][4]=addBPoint(-0.5,0.6,1.0,f0);
		pFront[1][3][4]=addBPoint(-0.25,0.6,1.0,f0);	
		pFront[2][3][4]=addBPoint(0.0,0.6,1.0,f0);
		pFront[3][3][4]=addBPoint(0.25,0.6,1.0,f0);
		pFront[4][3][4]=addBPoint(0.5,0.6,1.0,f0);
		pFront[0][4][4]=addBPoint(-0.5,0.8,1.0,f0);	
		pFront[1][4][4]=addBPoint(-0.25,0.8,1.0,f0);
		pFront[2][4][4]=addBPoint(0.0,0.8,1.0,f0);
		pFront[3][4][4]=addBPoint(0.25,0.8,1.0,f0);
		pFront[4][4][4]=addBPoint(0.5,0.8,1.0,f0);
		pFront[0][5][4]=addBPoint(-0.5,0.9,1.0,f0);	
		pFront[1][5][4]=addBPoint(-0.25,1.0,1.0,f0);
		pFront[2][5][4]=addBPoint(0.0,1.0,1.0,f0);
		pFront[3][5][4]=addBPoint(0.25,1.0,1.0,f0);
		pFront[4][5][4]=addBPoint(0.5,0.9,1.0,f0);
		
		
		
		buildWheel(f0.x(-0.5),f0.y(0.5),0,wheel_radius,wheel_width);
		buildWheel(f0.x(+0.5-wheel_width/front_width),f0.y(0.5),0,wheel_radius,wheel_width);
		
		for (int i = 0; i < fnx-1; i++) {


			for (int j = 0; j < fny-1; j++) {	

				for (int k = 0; k < fnz-1; k++) {


					if(i==0){

						LineData leftFrontLD=addLine(pFront[i][j][k],pFront[i][j][k+1],pFront[i][j+1][k+1],pFront[i][j+1][k],Renderer3D.CAR_LEFT);
					}



					if(k+1==fnz-1){
						LineData topFrontLD=addLine(pFront[i][j][k+1],pFront[i+1][j][k+1],pFront[i+1][j+1][k+1],pFront[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					if(k==0){
						LineData bottomFrontLD=addLine(pFront[i][j][k],pFront[i][j+1][k],pFront[i+1][j+1][k],pFront[i+1][j][k],Renderer3D.CAR_BOTTOM);
					}
					
					if(j==0){
						LineData backFrontLD=addLine(pFront[i][j][k],pFront[i+1][j][k],pFront[i+1][j][k+1],pFront[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==fny-1){
						LineData frontFrontLD=addLine(pFront[i][j+1][k],pFront[i][j+1][k+1],pFront[i+1][j+1][k+1],pFront[i+1][j+1][k],Renderer3D.CAR_FRONT);
					}


					if(i+1==fnx-1){

						LineData rightFrontLD=addLine(pFront[i+1][j][k],pFront[i+1][j+1][k],pFront[i+1][j+1][k+1],pFront[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}

				}

			}

		}
		
		
		
		//main body
		
		int pnx=5;
		int pny=5;
		int pnz=5;
		
		BPoint[][][] p=new BPoint[pnx][pny][pnz];
		
		Segments p0=new Segments(xc,x_side,back_length,y_side,0,z_side);

		p[0][0][0]=addBPoint(-0.5,0,0,p0);
		p[1][0][0]=addBPoint(-0.25,0.0,0,p0);
		p[2][0][0]=addBPoint(0.0,0.0,0,p0);
		p[3][0][0]=addBPoint(0.25,0.0,0,p0);
		p[4][0][0]=addBPoint(0.5,0.0,0,p0);
		p[0][1][0]=addBPoint(-0.5,0.25,0,p0);
		p[1][1][0]=addBPoint(-0.25,0.25,0,p0);
		p[2][1][0]=addBPoint(0.0,0.25,0,p0);
		p[3][1][0]=addBPoint(0.25,0.25,0,p0);
		p[4][1][0]=addBPoint(0.5,0.25,0,p0);
		p[0][2][0]=addBPoint(-0.5,0.5,0,p0);
		p[1][2][0]=addBPoint(-0.25,0.5,0,p0);
		p[2][2][0]=addBPoint(0.0,0.5,0,p0);
		p[3][2][0]=addBPoint(0.25,0.5,0,p0);
		p[4][2][0]=addBPoint(0.5,0.5,0,p0);
		p[0][3][0]=addBPoint(-0.5,0.75,0,p0);
		p[1][3][0]=addBPoint(-0.25,0.75,0,p0);
		p[2][3][0]=addBPoint(0.0,0.75,0,p0);
		p[3][3][0]=addBPoint(0.25,0.75,0,p0);
		p[4][3][0]=addBPoint(0.5,0.75,0,p0);
		p[0][4][0]=addBPoint(-0.5,1.0,0,p0);
		p[1][4][0]=addBPoint(-0.25,1.0,0,p0);
		p[2][4][0]=addBPoint(0.0,1.0,0,p0);
		p[3][4][0]=addBPoint(0.25,1.0,0,p0);
		p[4][4][0]=addBPoint(0.5,1.0,0,p0);
		
		
		p[0][0][1]=addBPoint(-0.5,0,0.25,p0);
		p[1][0][1]=addBPoint(-0.25,0.0,0.25,p0);
		p[2][0][1]=addBPoint(0.0,0.0,0.25,p0);
		p[3][0][1]=addBPoint(0.25,0.0,0.25,p0);
		p[4][0][1]=addBPoint(0.5,0.0,0.25,p0);
		p[0][1][1]=addBPoint(-0.5,0.25,0.25,p0);	
		p[4][1][1]=addBPoint(0.5,0.25,0.25,p0);
		p[0][2][1]=addBPoint(-0.5,0.5,0.25,p0);	
		p[4][2][1]=addBPoint(0.5,0.5,0.25,p0);
		p[0][3][1]=addBPoint(-0.5,0.75,0.25,p0);	
		p[4][3][1]=addBPoint(0.5,0.75,0.25,p0);
		p[0][4][1]=addBPoint(-0.5,1.0,0.25,p0);
		p[1][4][1]=addBPoint(-0.25,1.0,0.25,p0);
		p[2][4][1]=addBPoint(0.0,1.0,0.25,p0);
		p[3][4][1]=addBPoint(0.25,1.0,0.25,p0);
		p[4][4][1]=addBPoint(0.5,1.0,0.25,p0);
		
		p[0][0][2]=addBPoint(-0.5,0,0.5,p0);
		p[1][0][2]=addBPoint(-0.25,0.0,0.5,p0);
		p[2][0][2]=addBPoint(0.0,0.0,0.5,p0);
		p[3][0][2]=addBPoint(0.25,0.0,0.5,p0);
		p[4][0][2]=addBPoint(0.5,0.0,0.5,p0);
		p[0][1][2]=addBPoint(-0.5,0.25,0.5,p0);	
		p[4][1][2]=addBPoint(0.5,0.25,0.5,p0);
		p[0][2][2]=addBPoint(-0.5,0.5,0.5,p0);	
		p[4][2][2]=addBPoint(0.5,0.5,0.5,p0);
		p[0][3][2]=addBPoint(-0.5,0.75,0.5,p0);	
		p[4][3][2]=addBPoint(0.5,0.75,0.5,p0);
		p[0][4][2]=addBPoint(-0.5,1.0,0.5,p0);
		p[1][4][2]=addBPoint(-0.25,1.0,0.5,p0);
		p[2][4][2]=addBPoint(0.0,1.0,0.5,p0);
		p[3][4][2]=addBPoint(0.25,1.0,0.5,p0);
		p[4][4][2]=addBPoint(0.5,1.0,0.5,p0);
		
		p[0][0][3]=addBPoint(-0.5,0,0.75,p0);
		p[1][0][3]=addBPoint(-0.25,0.0,0.75,p0);
		p[2][0][3]=addBPoint(0.0,0.0,0.75,p0);
		p[3][0][3]=addBPoint(0.25,0.0,0.75,p0);
		p[4][0][3]=addBPoint(0.5,0.0,0.75,p0);
		p[0][1][3]=addBPoint(-0.5,0.25,0.75,p0);	
		p[4][1][3]=addBPoint(0.5,0.25,0.75,p0);
		p[0][2][3]=addBPoint(-0.5,0.5,0.75,p0);	
		p[4][2][3]=addBPoint(0.5,0.5,0.75,p0);
		p[0][3][3]=addBPoint(-0.5,0.75,0.75,p0);	
		p[4][3][3]=addBPoint(0.5,0.75,0.75,p0);
		p[0][4][3]=addBPoint(-0.5,1.0,0.75,p0);
		p[1][4][3]=addBPoint(-0.25,1.0,0.75,p0);
		p[2][4][3]=addBPoint(0.0,1.0,0.75,p0);
		p[3][4][3]=addBPoint(0.25,1.0,0.75,p0);
		p[4][4][3]=addBPoint(0.5,1.0,0.75,p0);
		
		p[0][0][4]=addBPoint(-0.5,0.0,1.0,p0);	
		p[1][0][4]=addBPoint(-0.25,0.0,1.0,p0);	
		p[2][0][4]=addBPoint(0.0,0.0,1.0,p0);
		p[3][0][4]=addBPoint(0.25,0.0,1.0,p0);	
		p[4][0][4]=addBPoint(0.5,0.0,1.0,p0);		
		p[0][1][4]=addBPoint(-0.5,0.25,1.0,p0);	
		p[1][1][4]=addBPoint(-0.25,0.25,1.0,p0);
		p[2][1][4]=addBPoint(0.0,0.25,1.0,p0);
		p[3][1][4]=addBPoint(0.25,0.25,1.0,p0);
		p[4][1][4]=addBPoint(0.5,0.25,1.0,p0);
		p[0][2][4]=addBPoint(-0.5,0.5,1.0,p0);
		p[1][2][4]=addBPoint(-0.25,0.5,1.0,p0);	
		p[2][2][4]=addBPoint(0.0,0.5,1.0,p0);
		p[3][2][4]=addBPoint(0.25,0.5,1.0,p0);
		p[4][2][4]=addBPoint(0.5,0.5,1.0,p0);
		p[0][3][4]=addBPoint(-0.5,0.75,1.0,p0);	
		p[1][3][4]=addBPoint(-0.25,0.75,1.0,p0);
		p[2][3][4]=addBPoint(0.0,0.75,1.0,p0);
		p[3][3][4]=addBPoint(0.25,0.75,1.0,p0);
		p[4][3][4]=addBPoint(0.5,0.75,1.0,p0);
		p[0][4][4]=addBPoint(-0.5,1.0,1.0,p0);
		p[1][4][4]=addBPoint(-0.25,1.0,1.0,p0);	
		p[2][4][4]=addBPoint(0.0,1.0,1.0,p0);
		p[3][4][4]=addBPoint(0.25,1.0,1.0,p0);
		p[4][4][4]=addBPoint(0.5,1.0,1.0,p0);
		
		for (int i = 0; i < pnx-1; i++) {


			for (int j = 0; j < pny-1; j++) {

				for (int k = 0; k < pnz-1; k++) {




					if(i==0){

						LineData leftLD=addLine(p[i][j][k],p[i][j][k+1],p[i][j+1][k+1],p[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						LineData bottomLD=addLine(p[i][j][k],p[i][j+1][k],p[i+1][j+1][k],p[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==pnz-1){
						LineData topLD=addLine(p[i][j][k+1],p[i+1][j][k+1],p[i+1][j+1][k+1],p[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						LineData backLD=addLine(p[i][j][k],p[i+1][j][k],p[i+1][j][k+1],p[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==pny-1){
						LineData frontLD=addLine(p[i][j+1][k],p[i][j+1][k+1],p[i+1][j+1][k+1],p[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==pnx-1){

						LineData rightLD=addLine(p[i+1][j][k],p[i+1][j+1][k],p[i+1][j+1][k+1],p[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}


		
		
		//roof:		
		
		int rnx=5;
		int rny=5;
		int rnz=3;
		
		BPoint[][][] pRoof=new BPoint[rnx][rny][rnz];
		
		Segments r0=new Segments(xc,roof_width,0,roof_length,z_side,roof_height);
		
		pRoof[0][0][0]=addBPoint(-0.5,0.0,0.0,r0);
		pRoof[1][0][0]=addBPoint(-0.25,0.0,0.0,r0);
		pRoof[2][0][0]=addBPoint(0.0,0.0,0.0,r0);
		pRoof[3][0][0]=addBPoint(0.25,0.0,0.0,r0);		
		pRoof[4][0][0]=addBPoint(0.5,0.0,0.0,r0);
		pRoof[0][1][0]=addBPoint(-0.5,0.25,0.0,r0);
		pRoof[1][1][0]=addBPoint(-0.25,0.25,0.0,r0);
		pRoof[2][1][0]=addBPoint(0.0,0.25,0.0,r0);
		pRoof[3][1][0]=addBPoint(0.25,0.25,0.0,r0);
		pRoof[4][1][0]=addBPoint(0.5,0.25,0.0,r0);
		pRoof[0][2][0]=addBPoint(-0.5,0.5,0.0,r0);
		pRoof[1][2][0]=addBPoint(-0.25,0.5,0.0,r0);
		pRoof[2][2][0]=addBPoint(0.0,0.5,0.0,r0);
		pRoof[3][2][0]=addBPoint(0.25,0.5,0.0,r0);
		pRoof[4][2][0]=addBPoint(0.5,0.5,0.0,r0);
		pRoof[0][3][0]=addBPoint(-0.5,0.75,0.0,r0);
		pRoof[1][3][0]=addBPoint(-0.25,0.75,0.0,r0);
		pRoof[2][3][0]=addBPoint(0.0,0.75,0.0,r0);
		pRoof[3][3][0]=addBPoint(0.25,0.75,0.0,r0);
		pRoof[4][3][0]=addBPoint(0.5,0.75,0.0,r0);		
		pRoof[0][4][0]=addBPoint(-0.5,1.0,0.0,r0);
		pRoof[1][4][0]=addBPoint(-0.25,1.0,0.0,r0);
		pRoof[2][4][0]=addBPoint(0.0,1.0,0.0,r0);
		pRoof[3][4][0]=addBPoint(0.25,1.0,0.0,r0);
		pRoof[4][4][0]=addBPoint(0.5,1.0,0.0,r0);
		
		pRoof[0][0][1]=addBPoint(-0.5,0.0,0.5,r0);
		pRoof[1][0][1]=addBPoint(-0.25,0.0,0.5,r0);	
		pRoof[2][0][1]=addBPoint(0.0,0.0,0.5,r0);
		pRoof[3][0][1]=addBPoint(0.25,0.0,0.5,r0);	
		pRoof[4][0][1]=addBPoint(0.5,0.0,0.5,r0);	
		pRoof[0][1][1]=addBPoint(-0.5,0.25,0.5,r0);	
		pRoof[4][1][1]=addBPoint(0.5,0.25,0.5,r0);
		pRoof[0][2][1]=addBPoint(-0.5,0.5,0.5,r0);		
		pRoof[4][2][1]=addBPoint(0.5,0.5,0.5,r0);
		pRoof[0][3][1]=addBPoint(-0.5,0.75,0.5,r0);			
		pRoof[4][3][1]=addBPoint(0.5,0.75,0.5,r0);
		pRoof[0][4][1]=addBPoint(-0.5,1.0,0.5,r0);	
		pRoof[1][4][1]=addBPoint(-0.25,1.0,0.5,r0);	
		pRoof[2][4][1]=addBPoint(0.0,1.0,0.5,r0);
		pRoof[3][4][1]=addBPoint(0.25,1.0,0.5,r0);
		pRoof[4][4][1]=addBPoint(0.5,1.0,0.5,r0);
		
		
		pRoof[0][0][2]=addBPoint(-0.5,0.0,1.0,r0);
		pRoof[1][0][2]=addBPoint(-0.25,0.0,1.0,r0);	
		pRoof[2][0][2]=addBPoint(0.0,0.0,1.0,r0);
		pRoof[3][0][2]=addBPoint(0.25,0.0,1.0,r0);	
		pRoof[4][0][2]=addBPoint(0.5,0.0,1.0,r0);	
		pRoof[0][1][2]=addBPoint(-0.5,0.25,1.0,r0);	
		pRoof[1][1][2]=addBPoint(-0.25,0.25,1.0,r0);	
		pRoof[2][1][2]=addBPoint(0.0,0.25,1.0,r0);
		pRoof[3][1][2]=addBPoint(0.25,0.25,1.0,r0);
		pRoof[4][1][2]=addBPoint(0.5,0.25,1.0,r0);
		pRoof[0][2][2]=addBPoint(-0.5,0.5,1.0,r0);	
		pRoof[1][2][2]=addBPoint(-0.25,0.5,1.0,r0);	
		pRoof[2][2][2]=addBPoint(0.0,0.5,1.0,r0);
		pRoof[3][2][2]=addBPoint(0.25,0.5,1.0,r0);
		pRoof[4][2][2]=addBPoint(0.5,0.5,1.0,r0);
		pRoof[0][3][2]=addBPoint(-0.5,0.75,1.0,r0);	
		pRoof[1][3][2]=addBPoint(-0.25,0.75,1.0,r0);	
		pRoof[2][3][2]=addBPoint(0.0,0.75,1.0,r0);
		pRoof[3][3][2]=addBPoint(0.25,0.75,1.0,r0);
		pRoof[4][3][2]=addBPoint(0.5,0.75,1.0,r0);
		pRoof[0][4][2]=addBPoint(-0.5,1.0,1.0,r0);	
		pRoof[1][4][2]=addBPoint(-0.25,1.0,1.0,r0);	
		pRoof[2][4][2]=addBPoint(0.0,1.0,1.0,r0);
		pRoof[3][4][2]=addBPoint(0.25,1.0,1.0,r0);
		pRoof[4][4][2]=addBPoint(0.5,1.0,1.0,r0);


		for (int i = 0; i < rnx-1; i++) {


			for (int j = 0; j < rny-1; j++) {	

				for (int k = 0; k < rnz-1; k++) {

					if(i==0){

						LineData leftRoofLD=addLine(pRoof[i][j][k],pRoof[i][j][k+1],pRoof[i][j+1][k+1],pRoof[i][j+1][k],Renderer3D.CAR_LEFT);
					}

					if(k==0){
						LineData bottomRoofLD=addLine(pRoof[i][j][k],pRoof[i][j+1][k],pRoof[i+1][j+1][k],pRoof[i+1][j][k],Renderer3D.CAR_BOTTOM);
					}
					if(k+1==rnz-1){
						LineData topRoofLD=addLine(pRoof[i][j][k+1],pRoof[i+1][j][k+1],pRoof[i+1][j+1][k+1],pRoof[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						LineData backRoofLD=addLine(pRoof[i][j][k],pRoof[i+1][j][k],pRoof[i+1][j][k+1],pRoof[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==rny-1){
						LineData frontRoofLD=addLine(pRoof[i][j+1][k],pRoof[i][j+1][k+1],pRoof[i+1][j+1][k+1],pRoof[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
					

					if(i+1==rnx-1){

						LineData rightRoofLD=addLine(pRoof[i+1][j][k],pRoof[i+1][j+1][k],pRoof[i+1][j+1][k+1],pRoof[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}

		//back:
		
		int bnx=5;
		int bny=6;
		int bnz=3;
		
		BPoint[][][] pBack=new BPoint[bnx][bny][bnz];
		
		lw=(wheel_radius*2.0/Math.sqrt(3))/back_length;
		zw=wheel_radius/back_height;
		yw=0.5;
		
		Segments b0=new Segments(xc,back_width,0,back_length,0,back_height);

		pBack[0][0][0]=addBPoint(-0.5,0.1,0,b0);
		pBack[1][0][0]=addBPoint(-0.25,0,0,b0);
		pBack[2][0][0]=addBPoint(0.0,0.0,0.0,b0);
		pBack[3][0][0]=addBPoint(0.25,0,0,b0);
		pBack[4][0][0]=addBPoint(0.5,0.1,0,b0);		
		pBack[0][1][0]=addBPoint(-0.5,yw-lw,0,b0);
		pBack[1][1][0]=addBPoint(-0.25,yw-lw,0,b0);
		pBack[2][1][0]=addBPoint(0.0,yw-lw,0,b0);
		pBack[3][1][0]=addBPoint(0.25,yw-lw,0,b0);
		pBack[4][1][0]=addBPoint(0.5,yw-lw,0,b0);
		pBack[0][2][0]=addBPoint(-0.5,yw-lw*0.5,zw,b0);
		pBack[1][2][0]=addBPoint(-0.25,yw-lw*0.5,zw,b0);
		pBack[2][2][0]=addBPoint(0.0,yw-lw*0.5,zw,b0);
		pBack[3][2][0]=addBPoint(0.25,yw-lw*0.5,zw,b0);
		pBack[4][2][0]=addBPoint(0.5,yw-lw*0.5,zw,b0);
		pBack[0][3][0]=addBPoint(-0.5,yw+lw*0.5,zw,b0);
		pBack[1][3][0]=addBPoint(-0.25,yw+lw*0.5,zw,b0);
		pBack[2][3][0]=addBPoint(0.0,yw+lw*0.5,zw,b0);
		pBack[3][3][0]=addBPoint(0.25,yw+lw*0.5,zw,b0);
		pBack[4][3][0]=addBPoint(0.5,yw+lw*0.5,zw,b0);
		pBack[0][4][0]=addBPoint(-0.5,yw+lw,0,b0);
		pBack[1][4][0]=addBPoint(-0.25,yw+lw,0,b0);
		pBack[2][4][0]=addBPoint(0.0,yw+lw,0,b0);
		pBack[3][4][0]=addBPoint(0.25,yw+lw,0,b0);
		pBack[4][4][0]=addBPoint(0.5,yw+lw,0,b0);
		pBack[0][5][0]=addBPoint(-0.5,1.0,0,b0);
		pBack[1][5][0]=addBPoint(-0.25,1.0,0,b0);
		pBack[2][5][0]=addBPoint(0.0,1.0,0,b0);
		pBack[3][5][0]=addBPoint(0.25,1.0,0,b0);
		pBack[4][5][0]=addBPoint(0.5,1.0,0,b0);

		
		pBack[0][0][1]=addBPoint(-0.5,0.1,0.5,b0);	
		pBack[1][0][1]=addBPoint(-0.25,0,0.5,b0);
		pBack[2][0][1]=addBPoint(0.0,0,0.5,b0);
		pBack[3][0][1]=addBPoint(0.25,0,0.5,b0);	
		pBack[4][0][1]=addBPoint(0.5,0.1,0.5,b0);
		pBack[0][1][1]=addBPoint(-0.5,0.2,0.5,b0);		
		pBack[4][1][1]=addBPoint(0.5,0.2,0.5,b0);
		pBack[0][2][1]=addBPoint(-0.5,0.4,0.5,b0);			
		pBack[4][2][1]=addBPoint(0.5,0.4,0.5,b0);
		pBack[0][3][1]=addBPoint(-0.5,0.6,0.5,b0);		
		pBack[4][3][1]=addBPoint(0.5,0.6,0.5,b0);
		pBack[0][4][1]=addBPoint(-0.5,0.8,0.5,b0);
		pBack[4][4][1]=addBPoint(0.5,0.8,0.5,b0);
		pBack[0][5][1]=addBPoint(-0.5,1.0,0.5,b0);
		pBack[1][5][1]=addBPoint(-0.25,1.0,0.5,b0);		
		pBack[2][5][1]=addBPoint(0.0,1.0,0.5,b0);
		pBack[3][5][1]=addBPoint(0.25,1.0,0.5,b0);
		pBack[4][5][1]=addBPoint(0.5,1.0,0.5,b0);
		
		pBack[0][0][2]=addBPoint(-0.5,0.1,1.0,b0);	
		pBack[1][0][2]=addBPoint(-0.25,0,1.0,b0);
		pBack[2][0][2]=addBPoint(0.0,0,1.0,b0);
		pBack[3][0][2]=addBPoint(0.25,0,1.0,b0);	
		pBack[4][0][2]=addBPoint(0.5,0.1,1.0,b0);
		pBack[0][1][2]=addBPoint(-0.5,0.2,1.0,b0);	
		pBack[1][1][2]=addBPoint(-0.25,0.2,1.0,b0);
		pBack[2][1][2]=addBPoint(0.0,0.2,1.0,b0);
		pBack[3][1][2]=addBPoint(0.25,0.2,1.0,b0);
		pBack[4][1][2]=addBPoint(0.5,0.2,1.0,b0);
		pBack[0][2][2]=addBPoint(-0.5,0.4,1.0,b0);	
		pBack[1][2][2]=addBPoint(-0.25,0.4,1.0,b0);
		pBack[2][2][2]=addBPoint(0.0,0.4,1.0,b0);
		pBack[3][2][2]=addBPoint(0.25,0.4,1.0,b0);
		pBack[4][2][2]=addBPoint(0.5,0.4,1.0,b0);
		pBack[0][3][2]=addBPoint(-0.5,0.6,1.0,b0);	
		pBack[1][3][2]=addBPoint(-0.25,0.6,1.0,b0);
		pBack[2][3][2]=addBPoint(0.0,0.6,1.0,b0);
		pBack[3][3][2]=addBPoint(0.25,0.6,1.0,b0);
		pBack[4][3][2]=addBPoint(0.5,0.6,1.0,b0);
		pBack[0][4][2]=addBPoint(-0.5,0.8,1.0,b0);
		pBack[1][4][2]=addBPoint(-0.25,0.8,1.0,b0);		
		pBack[2][4][2]=addBPoint(0.0,0.8,1.0,b0);
		pBack[3][4][2]=addBPoint(0.25,0.8,1.0,b0);
		pBack[4][4][2]=addBPoint(0.5,0.8,1.0,b0);
		pBack[0][5][2]=addBPoint(-0.5,1.0,1.0,b0);
		pBack[1][5][2]=addBPoint(-0.25,1.0,1.0,b0);		
		pBack[2][5][2]=addBPoint(0.0,1.0,1.0,b0);
		pBack[3][5][2]=addBPoint(0.25,1.0,1.0,b0);
		pBack[4][5][2]=addBPoint(0.5,1.0,1.0,b0);
		
		
		buildWheel(b0.x(-0.5),b0.y(0.5),0,wheel_radius,wheel_width);
		buildWheel(b0.x(+0.5-wheel_width/back_width),b0.y(0.5),0,wheel_radius,wheel_width);

		for (int i = 0; i < bnx-1; i++) {


			for (int j = 0; j < bny-1; j++) {		

				for (int k = 0; k < bnz-1; k++) {

					if(i==0){

						LineData leftBackLD=addLine(pBack[i][j][k],pBack[i][j][k+1],pBack[i][j+1][k+1],pBack[i][j+1][k],Renderer3D.CAR_LEFT);
					}				
						
					if(k==0){
						LineData bottomBackLD=addLine(pBack[i][j][k],pBack[i][j+1][k],pBack[i+1][j+1][k],pBack[i+1][j][k],Renderer3D.CAR_BOTTOM);
					}
					
					if(k+1==bnz-1){
						LineData topBackLD=addLine(pBack[i][j][k+1],pBack[i+1][j][k+1],pBack[i+1][j+1][k+1],pBack[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
				
					if(j==0){
						LineData backBackLD=addLine(pBack[i][j][k],pBack[i+1][j][k],pBack[i+1][j][k+1],pBack[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==bny-1){
						LineData frontBackLD=addLine(pBack[i][j+1][k],pBack[i][j+1][k+1],pBack[i+1][j+1][k+1],pBack[i+1][j+1][k],Renderer3D.CAR_FRONT);
					}
					

					if(i+1==bnx-1){

						LineData rightBackLD=addLine(pBack[i+1][j][k],pBack[i+1][j+1][k],pBack[i+1][j+1][k+1],pBack[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}

		/////////
		

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildBykeMesh() {
		
		points=new Vector();
		points.setSize(100);

		polyData=new Vector();

		//basic sides:
		
		n=0;
		

		
		buildWheel(0,back_length,wheel_radius,wheel_radius,wheel_width);
		buildWheel(0,back_length+y_side,wheel_radius,wheel_radius,wheel_width);
		
		
		BPoint[][][] leftFrame=new BPoint[2][2][2];
		
		
		double frame_side=(x_side-wheel_width)*0.5;
		double xc=-frame_side*0.5;
		
		Segments lf=new Segments(xc,frame_side,back_length,y_side,wheel_radius,2*z_side);

		leftFrame[0][0][0]=addBPoint(-0.5,0.0,0,lf);
		leftFrame[1][0][0]=addBPoint(0.5,0.0,0,lf);
		leftFrame[1][1][0]=addBPoint(0.5,0.42,0,lf);
		leftFrame[0][1][0]=addBPoint(-0.5,0.42,0,lf);
		
		leftFrame[0][0][1]=addBPoint(-0.5,0.26,1.0,lf);
		leftFrame[1][0][1]=addBPoint(0.5,0.26,1.0,lf);
		leftFrame[0][1][1]=addBPoint(-0.5,0.82,1.0,lf);
		leftFrame[1][1][1]=addBPoint(0.5,0.82,1.0,lf);	
			


		addLine(leftFrame[0][0][1],leftFrame[1][0][1],leftFrame[1][1][1],leftFrame[0][1][1],Renderer3D.CAR_TOP);

		addLine(leftFrame[0][0][0],leftFrame[0][1][0],leftFrame[1][1][0],leftFrame[1][0][0],Renderer3D.CAR_BOTTOM);

		addLine(leftFrame[0][0][0],leftFrame[0][0][1],leftFrame[0][1][1],leftFrame[0][1][0],Renderer3D.CAR_LEFT);

		addLine(leftFrame[1][0][0],leftFrame[1][1][0],leftFrame[1][1][1],leftFrame[1][0][1],Renderer3D.CAR_RIGHT);

		addLine(leftFrame[0][0][0],leftFrame[1][0][0],leftFrame[1][0][1],leftFrame[0][0][1],Renderer3D.CAR_BACK);

		addLine(leftFrame[0][1][0],leftFrame[0][1][1],leftFrame[1][1][1],leftFrame[1][1][0],Renderer3D.CAR_FRONT);		

		
		BPoint[][][] rightFrame=new BPoint[2][2][2];		
		
	
		xc=wheel_width+frame_side*0.5;
		
		Segments rf=new Segments(xc,frame_side,back_length,y_side,wheel_radius,wheel_radius+z_side);

		rightFrame[0][0][0]=addBPoint(-0.5,0.0,0,rf);
		rightFrame[1][0][0]=addBPoint(0.5,0.0,0,rf);
		rightFrame[1][1][0]=addBPoint(0.5,0.42,0,rf);
		rightFrame[0][1][0]=addBPoint(-0.5,0.42,0,rf);
				
		rightFrame[0][0][1]=addBPoint(-0.5,0.26,1.0,rf);	
		rightFrame[1][0][1]=addBPoint(0.5,0.26,1.0,rf);
		rightFrame[0][1][1]=addBPoint(-0.5,0.82,1.0,rf);		
		rightFrame[1][1][1]=addBPoint(0.5,0.82,1.0,rf);		


		addLine(rightFrame[0][0][1],rightFrame[1][0][1],rightFrame[1][1][1],rightFrame[0][1][1],Renderer3D.CAR_TOP);

		addLine(rightFrame[0][0][0],rightFrame[0][1][0],rightFrame[1][1][0],rightFrame[1][0][0],Renderer3D.CAR_BOTTOM);

		addLine(rightFrame[0][0][0],rightFrame[0][0][1],rightFrame[0][1][1],rightFrame[0][1][0],Renderer3D.CAR_LEFT);

		addLine(rightFrame[1][0][0],rightFrame[1][1][0],rightFrame[1][1][1],rightFrame[1][0][1],Renderer3D.CAR_RIGHT);

		addLine(rightFrame[0][0][0],rightFrame[1][0][0],rightFrame[1][0][1],rightFrame[0][0][1],Renderer3D.CAR_BACK);

		addLine(rightFrame[0][1][0],rightFrame[0][1][1],rightFrame[1][1][1],rightFrame[1][1][0],Renderer3D.CAR_FRONT);	
		
		
		
		BPoint[][][] leftFork=new BPoint[2][2][2];	
		
		leftFork[0][0][0]=addBPoint(-0.5,0.91,0,lf);
		leftFork[1][0][0]=addBPoint(0.5,0.91,0,lf);		
		leftFork[0][1][0]=addBPoint(-0.5,1.09,0,lf);
		leftFork[1][1][0]=addBPoint(0.5,1.09,0,lf);
				
		leftFork[0][0][1]=addBPoint(-0.5,0.82,1.0,lf);	
		leftFork[1][0][1]=addBPoint(0.5,0.82,1.0,lf);
		leftFork[0][1][1]=addBPoint(-0.5,1.0,1.0,lf);		
		leftFork[1][1][1]=addBPoint(0.5,1.0,1.0,lf);		


		addLine(leftFork[0][0][1],leftFork[1][0][1],leftFork[1][1][1],leftFork[0][1][1],Renderer3D.CAR_TOP);

		addLine(leftFork[0][0][0],leftFork[0][1][0],leftFork[1][1][0],leftFork[1][0][0],Renderer3D.CAR_BOTTOM);

		addLine(leftFork[0][0][0],leftFork[0][0][1],leftFork[0][1][1],leftFork[0][1][0],Renderer3D.CAR_LEFT);

		addLine(leftFork[1][0][0],leftFork[1][1][0],leftFork[1][1][1],leftFork[1][0][1],Renderer3D.CAR_RIGHT);

		addLine(leftFork[0][0][0],leftFork[1][0][0],leftFork[1][0][1],leftFork[0][0][1],Renderer3D.CAR_BACK);

		addLine(leftFork[0][1][0],leftFork[0][1][1],leftFork[1][1][1],leftFork[1][1][0],Renderer3D.CAR_FRONT);
		
		
		BPoint[][][] rightFork=new BPoint[2][2][2];	
		
		rightFork[0][0][0]=addBPoint(-0.5,0.91,0,rf);
		rightFork[1][0][0]=addBPoint(0.5,0.91,0,rf);	
		rightFork[0][1][0]=addBPoint(-0.5,1.09,0,rf);
		rightFork[1][1][0]=addBPoint(0.5,1.09,0,rf);
				
		rightFork[0][0][1]=addBPoint(-0.5,0.82,1.0,rf);	
		rightFork[1][0][1]=addBPoint(0.5,0.82,1.0,rf);
		rightFork[0][1][1]=addBPoint(-0.5,1.0,1.0,rf);		
		rightFork[1][1][1]=addBPoint(0.5,1.0,1.0,rf);		


		addLine(rightFork[0][0][1],rightFork[1][0][1],rightFork[1][1][1],rightFork[0][1][1],Renderer3D.CAR_TOP);

		addLine(rightFork[0][0][0],rightFork[0][1][0],rightFork[1][1][0],rightFork[1][0][0],Renderer3D.CAR_BOTTOM);

		addLine(rightFork[0][0][0],rightFork[0][0][1],rightFork[0][1][1],rightFork[0][1][0],Renderer3D.CAR_LEFT);

		addLine(rightFork[1][0][0],rightFork[1][1][0],rightFork[1][1][1],rightFork[1][0][1],Renderer3D.CAR_RIGHT);

		addLine(rightFork[0][0][0],rightFork[1][0][0],rightFork[1][0][1],rightFork[0][0][1],Renderer3D.CAR_BACK);

		addLine(rightFork[0][1][0],rightFork[0][1][1],rightFork[1][1][1],rightFork[1][1][0],Renderer3D.CAR_FRONT);
		
		xc=wheel_width/2.0;
		
		Segments bd=new Segments(xc,wheel_width,back_length,y_side,2*wheel_radius,z_side);
		
		BPoint[][][] body=new BPoint[2][2][2];	
		
		body[0][0][0]=addBPoint(-0.5,0.0,0,bd);
		body[1][0][0]=addBPoint(0.5,0.0,0,bd);
		body[1][1][0]=addBPoint(0.5,1.0,0,bd);
		body[0][1][0]=addBPoint(-0.5,1.0,0,bd);
				
		body[0][0][1]=addBPoint(-0.5,0.0,1.0,bd);	
		body[1][0][1]=addBPoint(0.5,0.0,1.0,bd);
		body[0][1][1]=addBPoint(-0.5,1.0,1.0,bd);		
		body[1][1][1]=addBPoint(0.5,1.0,1.0,bd);		


		addLine(body[0][0][1],body[1][0][1],body[1][1][1],body[0][1][1],Renderer3D.CAR_TOP);

		addLine(body[0][0][0],body[0][1][0],body[1][1][0],body[1][0][0],Renderer3D.CAR_BOTTOM);

		addLine(body[0][0][0],body[0][0][1],body[0][1][1],body[0][1][0],Renderer3D.CAR_LEFT);

		addLine(body[1][0][0],body[1][1][0],body[1][1][1],body[1][0][1],Renderer3D.CAR_RIGHT);

		addLine(body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1],Renderer3D.CAR_BACK);

		addLine(body[0][1][0],body[0][1][1],body[1][1][1],body[1][1][0],Renderer3D.CAR_FRONT);
		
		
		BPoint[][][] handlebar=new BPoint[2][2][2];	
		
		Segments hb=new Segments(xc,front_width,back_length+y_side*0.75,y_side*0.25,2*wheel_radius+z_side,front_height);
		
		handlebar[0][0][0]=addBPoint(-0.5,0.0,0,hb);
		handlebar[1][0][0]=addBPoint(0.5,0.0,0,hb);
		handlebar[1][1][0]=addBPoint(0.5,1.0,0,hb);
		handlebar[0][1][0]=addBPoint(-0.5,1.0,0,hb);
				
		handlebar[0][0][1]=addBPoint(-0.5,0.0,1.0,hb);	
		handlebar[1][0][1]=addBPoint(0.5,0.0,1.0,hb);
		handlebar[0][1][1]=addBPoint(-0.5,1.0,1.0,hb);		
		handlebar[1][1][1]=addBPoint(0.5,1.0,1.0,hb);		


		addLine(handlebar[0][0][1],handlebar[1][0][1],handlebar[1][1][1],handlebar[0][1][1],Renderer3D.CAR_TOP);

		addLine(handlebar[0][0][0],handlebar[0][1][0],handlebar[1][1][0],handlebar[1][0][0],Renderer3D.CAR_BOTTOM);

		addLine(handlebar[0][0][0],handlebar[0][0][1],handlebar[0][1][1],handlebar[0][1][0],Renderer3D.CAR_LEFT);

		addLine(handlebar[1][0][0],handlebar[1][1][0],handlebar[1][1][1],handlebar[1][0][1],Renderer3D.CAR_RIGHT);

		addLine(handlebar[0][0][0],handlebar[1][0][0],handlebar[1][0][1],handlebar[0][0][1],Renderer3D.CAR_BACK);

		addLine(handlebar[0][1][0],handlebar[0][1][1],handlebar[1][1][1],handlebar[1][1][0],Renderer3D.CAR_FRONT);

		
		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}
	

	private PolygonMesh buildTractorMesh() {
		
		points=new Vector();
		points.setSize(300);

		polyData=new Vector();

		//basic sides:
		
		n=0;
		
		double rr=wheel_radius;
		double fr=wheel_radius*0.753;
		
		double ftyre_width=wheel_width;
		double rtyre_width=wheel_width;
		
		
		buildWheel(-rtyre_width,0,rr,rr,rtyre_width);
		buildWheel(x_side,0,rr,rr,rtyre_width);
		
		buildWheel(-ftyre_width,y_side,fr,fr,ftyre_width);
		buildWheel(x_side,y_side,fr,fr,ftyre_width);
		
		double xc=x_side*0.5;
		
		
		int pnumx=5;
		int pnumy=5;
		int pnumz=2;
		
		BPoint[][][] body=new BPoint[pnumx][pnumy][pnumz]; 
		
		Segments p0=new Segments(xc,x_side,0,y_side,rr,z_side);
		
		
		body[0][0][0]=addBPoint(-0.5,0.0,0.0,p0);
		body[1][0][0]=addBPoint(-0.25,0.0,0.0,p0);
		body[2][0][0]=addBPoint(0.0,0.0,0.0,p0);
		body[3][0][0]=addBPoint(0.25,0.0,0.0,p0);	
		body[4][0][0]=addBPoint(0.5,0.0,0.0,p0);
		body[0][0][1]=addBPoint(-0.5,0.0,1.0,p0);	
		body[1][0][1]=addBPoint(-0.25,0.0,1.0,p0);
		body[2][0][1]=addBPoint(0.0,0.0,1.0,p0);
		body[3][0][1]=addBPoint(0.25,0.0,1.0,p0);
		body[4][0][1]=addBPoint(0.5,0.0,1.0,p0);
		
		body[0][1][1]=addBPoint(-0.5,0.25,1.0,p0);
		body[1][1][1]=addBPoint(-0.25,0.25,1.0,p0);
		body[2][1][1]=addBPoint(0.0,0.25,1.0,p0);
		body[3][1][1]=addBPoint(0.25,0.25,1.0,p0);
		body[4][1][1]=addBPoint(0.5,0.25,1.0,p0);
		body[0][1][0]=addBPoint(-0.5,0.25,0.0,p0);
		body[1][1][0]=addBPoint(-0.25,0.25,0.0,p0);
		body[2][1][0]=addBPoint(0.0,0.25,0.0,p0);
		body[3][1][0]=addBPoint(0.25,0.25,0.0,p0);
		body[4][1][0]=addBPoint(0.5,0.25,0.0,p0);
		
		body[0][2][1]=addBPoint(-0.5,0.5,1.0,p0);
		body[1][2][1]=addBPoint(-0.25,0.5,1.0,p0);
		body[2][2][1]=addBPoint(0.0,0.5,1.0,p0);
		body[3][2][1]=addBPoint(0.25,0.5,1.0,p0);
		body[4][2][1]=addBPoint(0.5,0.5,1.0,p0);
		body[0][2][0]=addBPoint(-0.5,0.5,0.0,p0);
		body[1][2][0]=addBPoint(-0.25,0.5,0.0,p0);
		body[2][2][0]=addBPoint(0.0,0.5,0.0,p0);
		body[3][2][0]=addBPoint(0.25,0.5,0.0,p0);
		body[4][2][0]=addBPoint(0.5,0.5,0.0,p0);
		
		body[0][3][1]=addBPoint(-0.5,0.75,1.0,p0);
		body[1][3][1]=addBPoint(-0.25,0.75,1.0,p0);
		body[2][3][1]=addBPoint(0.0,0.75,1.0,p0);
		body[3][3][1]=addBPoint(0.25,0.75,1.0,p0);
		body[4][3][1]=addBPoint(0.5,0.75,1.0,p0);
		body[0][3][0]=addBPoint(-0.5,0.75,0.0,p0);
		body[1][3][0]=addBPoint(-0.25,0.75,0.0,p0);
		body[2][3][0]=addBPoint(0.0,0.75,0.0,p0);
		body[3][3][0]=addBPoint(0.25,0.75,0.0,p0);
		body[4][3][0]=addBPoint(0.5,0.75,0.0,p0);
		
		body[0][4][1]=addBPoint(-0.5,1.0,1.0,p0);	
		body[1][4][1]=addBPoint(-0.25,1.0,1.0,p0);
		body[2][4][1]=addBPoint(0.0,1.0,1.0,p0);
		body[3][4][1]=addBPoint(0.25,1.0,1.0,p0);
		body[4][4][1]=addBPoint(0.5,1.0,1.0,p0);
		body[0][4][0]=addBPoint(-0.5,1.0,0.0,p0);
		body[1][4][0]=addBPoint(-0.25,1.0,0.0,p0);
		body[2][4][0]=addBPoint(0.0,1.0,0.0,p0);
		body[3][4][0]=addBPoint(0.25,1.0,0.0,p0);
		body[4][4][0]=addBPoint(0.5,1.0,0.0,p0);
		
		for (int i = 0; i < pnumx-1; i++) {
			
			for (int j = 0; j < pnumy-1; j++) {		
			
				
				if(i==0){
					
					addLine(body[i][j][0],body[i][j][1],body[i][j+1][1],body[i][j+1][0],Renderer3D.CAR_LEFT);
					
				}
					
					
				if(j==0){
					
					addLine(body[i][j][0],body[i+1][j][0],body[i+1][j][1],body[i][j][1],Renderer3D.CAR_BACK);
				}
				
					addLine(body[i][j][1],body[i+1][j][1],body[i+1][j+1][1],body[i][j+1][1],Renderer3D.CAR_TOP);
				
					addLine(body[i][j][0],body[i][j+1][0],body[i+1][j+1][0],body[i+1][j][0],Renderer3D.CAR_BOTTOM);
				
				if(j+1==pnumy-1){
					
					addLine(body[i][j+1][0],body[i][j+1][1],body[i+1][j+1][1],body[i+1][j+1][0],Renderer3D.CAR_FRONT);	
				}

	
				if(i+1==pnumx-1){
					
					addLine(body[i+1][j][0],body[i+1][j+1][0],body[i+1][j+1][1],body[i+1][j][1],Renderer3D.CAR_RIGHT);
				}
			
			}
			
		
		}
		
		int fnumx=5;
		int fnumy=5;
		int fnumz=2;
		
		BPoint[][][] pFront=new BPoint[fnumx][fnumy][fnumz]; 
		
		Segments f0=new Segments(xc,front_width,back_length-rr,front_length,rr+z_side,front_height);
		
		pFront[0][0][0]=addBPoint(-0.5,0.0,0.0,f0);
		pFront[1][0][0]=addBPoint(-0.25,0.0,0.0,f0);
		pFront[2][0][0]=addBPoint(0.0,0.0,0.0,f0);
		pFront[3][0][0]=addBPoint(0.25,0.0,0.0,f0);
		pFront[4][0][0]=addBPoint(0.5,0.0,0.0,f0);	
		pFront[0][0][1]=addBPoint(-0.5,0.0,1.0,f0);	
		pFront[1][0][1]=addBPoint(-0.25,0.0,1.0,f0);	
		pFront[2][0][1]=addBPoint(0.0,0.0,1.0,f0);	
		pFront[3][0][1]=addBPoint(0.25,0.0,1.0,f0);	
		pFront[4][0][1]=addBPoint(0.5,0.0,1.0,f0);	
		
		pFront[0][1][0]=addBPoint(-0.5,0.25,0.0,f0);
		pFront[1][1][0]=addBPoint(-0.25,0.25,0.0,f0);
		pFront[2][1][0]=addBPoint(0.0,0.25,0.0,f0);
		pFront[3][1][0]=addBPoint(0.25,0.25,0.0,f0);
		pFront[4][1][0]=addBPoint(0.5,0.25,0.0,f0);
		pFront[0][1][1]=addBPoint(-0.5,0.25,1.0,f0);	
		pFront[1][1][1]=addBPoint(-0.25,0.25,1.0,f0);
		pFront[2][1][1]=addBPoint(0.0,0.25,1.0,f0);
		pFront[3][1][1]=addBPoint(0.25,0.25,1.0,f0);
		pFront[4][1][1]=addBPoint(0.5,0.25,1.0,f0);
		
		pFront[0][2][0]=addBPoint(-0.5,0.5,0.0,f0);
		pFront[1][2][0]=addBPoint(-0.25,0.5,0.0,f0);
		pFront[2][2][0]=addBPoint(0.0,0.5,0.0,f0);
		pFront[3][2][0]=addBPoint(0.25,0.5,0.0,f0);
		pFront[4][2][0]=addBPoint(0.5,0.5,0.0,f0);
		pFront[0][2][1]=addBPoint(-0.5,0.5,1.0,f0);	
		pFront[1][2][1]=addBPoint(-0.25,0.5,1.0,f0);
		pFront[2][2][1]=addBPoint(0.0,0.5,1.0,f0);
		pFront[3][2][1]=addBPoint(0.25,0.5,1.0,f0);
		pFront[4][2][1]=addBPoint(0.5,0.5,1.0,f0);
		
		pFront[0][3][0]=addBPoint(-0.5,0.75,0.0,f0);
		pFront[1][3][0]=addBPoint(-0.25,0.75,0.0,f0);
		pFront[2][3][0]=addBPoint(0.0,0.75,0.0,f0);
		pFront[3][3][0]=addBPoint(0.25,0.75,0.0,f0);
		pFront[4][3][0]=addBPoint(0.5,0.75,0.0,f0);
		pFront[0][3][1]=addBPoint(-0.5,0.75,1.0,f0);	
		pFront[1][3][1]=addBPoint(-0.25,0.75,1.0,f0);
		pFront[2][3][1]=addBPoint(0.0,0.75,1.0,f0);
		pFront[3][3][1]=addBPoint(0.25,0.75,1.0,f0);
		pFront[4][3][1]=addBPoint(0.5,0.75,1.0,f0);
		
		pFront[0][4][0]=addBPoint(-0.5,1.0,0.0,f0);
		pFront[1][4][0]=addBPoint(-0.25,1.0,0.0,f0);
		pFront[2][4][0]=addBPoint(0.0,1.0,0.0,f0);
		pFront[3][4][0]=addBPoint(0.25,1.0,0.0,f0);
		pFront[4][4][0]=addBPoint(0.5,1.0,0.0,f0);
		pFront[0][4][1]=addBPoint(-0.5,1.0,1.0,f0);	
		pFront[1][4][1]=addBPoint(-0.25,1.0,1.0,f0);
		pFront[2][4][1]=addBPoint(0.0,1.0,1.0,f0);
		pFront[3][4][1]=addBPoint(0.25,1.0,1.0,f0);
		pFront[4][4][1]=addBPoint(0.5,1.0,1.0,f0);
		
		
		for (int i = 0; i < fnumx-1; i++) {
			
			for (int j = 0; j < fnumy-1; j++) {		
			
				
				if(i==0){
					
					addLine(pFront[i][j][0],pFront[i][j][1],pFront[i][j+1][1],pFront[i][j+1][0],Renderer3D.CAR_LEFT);
					
				}

					
					
				if(j==0){
					
					addLine(pFront[i][j][0],pFront[i+1][j][0],pFront[i+1][j][1],pFront[i][j][1],Renderer3D.CAR_BACK);
				}
				
				addLine(pFront[i][j][1],pFront[i+1][j][1],pFront[i+1][j+1][1],pFront[i][j+1][1],Renderer3D.CAR_TOP);
				
				addLine(pFront[i][j][0],pFront[i][j+1][0],pFront[i+1][j+1][0],pFront[i+1][j][0],Renderer3D.CAR_BOTTOM);
				
				if(j+1==fnumy-1){
					
					addLine(pFront[i][j+1][0],pFront[i][j+1][1],pFront[i+1][j+1][1],pFront[i+1][j+1][0],Renderer3D.CAR_FRONT);
				}

	
				if(i+1==fnumx-1){
					
					addLine(pFront[i+1][j][0],pFront[i+1][j+1][0],pFront[i+1][j+1][1],pFront[i+1][j][1],Renderer3D.CAR_RIGHT);
				}
			
			}
			
		
		}
		
		int fcnumx=2;
		int fcnumy=2;
		int fcnumz=2;
		
		BPoint[][][] pFrontCarriage=new BPoint[fcnumx][fcnumy][fcnumz]; 
		
		
			
		Segments fc0=new Segments(xc,x_side,y_side-fr,2*fr,fr,rr-fr);
		
		pFrontCarriage[0][0][0]=addBPoint(-0.5,0.0,0.0,fc0);
		pFrontCarriage[1][0][0]=addBPoint(0.5,0.0,0.0,fc0);
		pFrontCarriage[0][0][1]=addBPoint(-0.5,0.0,1.0,fc0);	
		pFrontCarriage[1][0][1]=addBPoint(0.5,0.0,1.0,fc0);	
		pFrontCarriage[0][1][0]=addBPoint(-0.5,1.0,0.0,fc0);
		pFrontCarriage[1][1][0]=addBPoint(0.5,1.0,0.0,fc0);
		pFrontCarriage[0][1][1]=addBPoint(-0.5,1.0,1.0,fc0);	
		pFrontCarriage[1][1][1]=addBPoint(0.5,1.0,1.0,fc0);	
		
		for (int i = 0; i < fcnumx-1; i++) {
			
			for (int j = 0; j < fcnumy-1; j++) {		
			
				
				if(i==0){
					
					addLine(pFrontCarriage[i][j][0],pFrontCarriage[i][j][1],pFrontCarriage[i][j+1][1],pFrontCarriage[i][j+1][0],Renderer3D.CAR_LEFT);
					
				}

					
					
				if(j==0){
					
					addLine(pFrontCarriage[i][j][0],pFrontCarriage[i+1][j][0],pFrontCarriage[i+1][j][1],pFrontCarriage[i][j][1],Renderer3D.CAR_BACK);
				}
				
				addLine(pFrontCarriage[i][j][1],pFrontCarriage[i+1][j][1],pFrontCarriage[i+1][j+1][1],pFrontCarriage[i][j+1][1],Renderer3D.CAR_TOP);
				
				addLine(pFrontCarriage[i][j][0],pFrontCarriage[i][j+1][0],pFrontCarriage[i+1][j+1][0],pFrontCarriage[i+1][j][0],Renderer3D.CAR_BOTTOM);
				
				if(j+1==fcnumy-1){
					
					addLine(pFrontCarriage[i][j+1][0],pFrontCarriage[i][j+1][1],pFrontCarriage[i+1][j+1][1],pFrontCarriage[i+1][j+1][0],Renderer3D.CAR_FRONT);
				}

	
				if(i+1==fcnumx-1){
					
					addLine(pFrontCarriage[i+1][j][0],pFrontCarriage[i+1][j+1][0],pFrontCarriage[i+1][j+1][1],pFrontCarriage[i+1][j][1],Renderer3D.CAR_RIGHT);
				}
			
			}
			
		
		}
		
		int bnumx=5;
		int bnumy=5;
		int bnumz=2;
		
		BPoint[][][] pBack=new BPoint[bnumx][bnumy][bnumz]; 
		
		Segments b0=new Segments(xc,back_width,-rr,back_length,rr+z_side,back_height);
		
		
		pBack[0][0][0]=addBPoint(-0.5,0,0.8,b0);
		pBack[1][0][0]=addBPoint(-0.25,0,0.8,b0);
		pBack[2][0][0]=addBPoint(0.0,0,0.8,b0);
		pBack[3][0][0]=addBPoint(0.25,0,0.8,b0);
		pBack[4][0][0]=addBPoint(0.5,0,0.8,b0);		
		pBack[0][0][1]=addBPoint(-0.5,0,0.9,b0);
		pBack[1][0][1]=addBPoint(-0.25,0,0.9,b0);
		pBack[2][0][1]=addBPoint(0.0,0,0.9,b0);
		pBack[3][0][1]=addBPoint(0.25,0,0.9,b0);
		pBack[4][0][1]=addBPoint(0.5,0,0.9,b0);	
		
		pBack[0][1][0]=addBPoint(-0.5,0.25,0.9,b0);
		pBack[1][1][0]=addBPoint(-0.25,0.25,0.9,b0);
		pBack[2][1][0]=addBPoint(0.0,0.25,0.9,b0);
		pBack[3][1][0]=addBPoint(0.25,0.25,0.9,b0);
		pBack[4][1][0]=addBPoint(0.5,0.25,0.9,b0);		
		pBack[0][1][1]=addBPoint(-0.5,0.25,1.0,b0);
		pBack[1][1][1]=addBPoint(-0.25,0.25,1.0,b0);
		pBack[2][1][1]=addBPoint(0.0,0.25,1.0,b0);
		pBack[3][1][1]=addBPoint(0.25,0.25,1.0,b0);
		pBack[4][1][1]=addBPoint(0.5,0.25,1.0,b0);	
		
		pBack[0][2][0]=addBPoint(-0.5,0.55,0.9,b0);
		pBack[1][2][0]=addBPoint(-0.25,0.55,0.9,b0);
		pBack[2][2][0]=addBPoint(0.0,0.55,0.9,b0);
		pBack[3][2][0]=addBPoint(0.25,0.55,0.9,b0);
		pBack[4][2][0]=addBPoint(0.5,0.55,0.9,b0);		
		pBack[0][2][1]=addBPoint(-0.5,0.55,1.0,b0);
		pBack[1][2][1]=addBPoint(-0.25,0.55,1.0,b0);
		pBack[2][2][1]=addBPoint(0.0,0.55,1.0,b0);
		pBack[3][2][1]=addBPoint(0.25,0.55,1.0,b0);
		pBack[4][2][1]=addBPoint(0.5,0.55,1.0,b0);	
		
		pBack[0][3][0]=addBPoint(-0.5,0.8,0.0,b0);
		pBack[1][3][0]=addBPoint(-0.25,0.8,0.0,b0);
		pBack[2][3][0]=addBPoint(0.0,0.8,0.0,b0);
		pBack[3][3][0]=addBPoint(0.25,0.8,0.0,b0);
		pBack[4][3][0]=addBPoint(0.5,0.8,0.0,b0);		
		pBack[0][3][1]=addBPoint(-0.5,0.8,0.25,b0);
		pBack[1][3][1]=addBPoint(-0.25,0.8,0.25,b0);
		pBack[2][3][1]=addBPoint(0.0,0.8,0.25,b0);
		pBack[3][3][1]=addBPoint(0.25,0.8,0.25,b0);
		pBack[4][3][1]=addBPoint(0.5,0.8,0.25,b0);	
		
		pBack[0][4][0]=addBPoint(-0.5,1.0,0.0,b0);
		pBack[1][4][0]=addBPoint(-0.25,1.0,0.0,b0);
		pBack[2][4][0]=addBPoint(0.0,1.0,0.0,b0);
		pBack[3][4][0]=addBPoint(0.25,1.0,0.0,b0);
		pBack[4][4][0]=addBPoint(0.5,1.0,0.0,b0);
		pBack[0][4][1]=addBPoint(-0.5,1.0,0.25,b0);	
		pBack[1][4][1]=addBPoint(-0.25,1.0,0.25,b0);	
		pBack[2][4][1]=addBPoint(0.0,1.0,0.25,b0);	
		pBack[3][4][1]=addBPoint(0.25,1.0,0.25,b0);	
		pBack[4][4][1]=addBPoint(0.5,1.0,0.25,b0);
		
		
		for (int i = 0; i < bnumx-1; i++) {
			
			for (int j = 0; j < bnumy-1; j++) {	
				
				
							
				
				if(i==0){
					
					addLine(pBack[i][j][0],pBack[i][j][1],pBack[i][j+1][1],pBack[i][j+1][0],Renderer3D.CAR_LEFT);
					
				}
	
	
				if(j==0){
					
					addLine(pBack[i][j][0],pBack[i+1][j][0],pBack[i+1][j][1],pBack[i][j][1],Renderer3D.CAR_BACK);
				}
				
				//exclude the part covered by the roof to have a closed form:
				
				if((i==1 || i==2) && (j>0))
					;
				else
					addLine(pBack[i][j][1],pBack[i+1][j][1],pBack[i+1][j+1][1],pBack[i][j+1][1],Renderer3D.CAR_TOP);
				
				addLine(pBack[i][j][0],pBack[i][j+1][0],pBack[i+1][j+1][0],pBack[i+1][j][0],Renderer3D.CAR_BOTTOM);
				
				if(j+1==bnumy-1){
					
					addLine(pBack[i][j+1][0],pBack[i][j+1][1],pBack[i+1][j+1][1],pBack[i+1][j+1][0],Renderer3D.CAR_FRONT);
				}

	
				if(i+1==bnumx-1){
					
					addLine(pBack[i+1][j][0],pBack[i+1][j+1][0],pBack[i+1][j+1][1],pBack[i+1][j][1],Renderer3D.CAR_RIGHT);
				}
			
			}
			
		
		}

		int rnumx=3;
		int rnumy=4;
		int rnumz=2;
		
		BPoint[][][] roof=new BPoint[rnumx][rnumy][rnumz]; 
		
		Segments r0=new Segments(xc,roof_width,-rr+back_length-roof_length,roof_length,rr+z_side,roof_height);
		
		
		roof[0][0][0]=pBack[1][1][1];
		roof[1][0][0]=pBack[2][1][1];
		roof[2][0][0]=pBack[3][1][1];	
		roof[0][0][1]=addBPoint(-0.5,0,1.0,r0);
		roof[1][0][1]=addBPoint(0.0,0,1.0,r0);
		roof[2][0][1]=addBPoint(0.5,0,1.0,r0);	
		
		roof[0][1][0]=pBack[1][2][1];
		roof[1][1][0]=pBack[2][2][1];
		roof[2][1][0]=pBack[3][2][1];		
		roof[0][1][1]=addBPoint(-0.5,0.33,1.0,r0);
		roof[1][1][1]=addBPoint(0.0,0.33,1.0,r0);
		roof[2][1][1]=addBPoint(0.5,0.33,1.0,r0);	
		
		roof[0][2][0]=pBack[1][3][1];
		roof[1][2][0]=pBack[2][3][1];
		roof[2][2][0]=pBack[3][3][1];		
		roof[0][2][1]=addBPoint(-0.5,0.66,1.0,r0);
		roof[1][2][1]=addBPoint(0.0,0.66,1.0,r0);
		roof[2][2][1]=addBPoint(0.5,0.66,1.0,r0);	
		

		roof[0][3][0]=pBack[1][4][1];
		roof[1][3][0]=pBack[2][4][1];
		roof[2][3][0]=pBack[3][4][1];		
		roof[0][3][1]=addBPoint(-0.5,1.0,1.0,r0);
		roof[1][3][1]=addBPoint(0.0,1.0,1.0,r0);
		roof[2][3][1]=addBPoint(0.5,1.0,1.0,r0);
		
		
		for (int i = 0; i < rnumx-1; i++) {
			
			for (int j = 0; j < rnumy-1; j++) {		
			
				
				if(i==0){
					
					addLine(roof[i][j][0],roof[i][j][1],roof[i][j+1][1],roof[i][j+1][0],Renderer3D.CAR_LEFT);
					
				}
	
	
				if(j==0){
					
					addLine(roof[i][j][0],roof[i+1][j][0],roof[i+1][j][1],roof[i][j][1],Renderer3D.CAR_BACK);
				}
				
				addLine(roof[i][j][1],roof[i+1][j][1],roof[i+1][j+1][1],roof[i][j+1][1],Renderer3D.CAR_TOP);
				
		
				//addLine(roof[i][j][0],roof[i][j+1][0],roof[i+1][j+1][0],roof[i+1][j][0],Renderer3D.CAR_BOTTOM);
				
				if(j+1==rnumy-1){
					
					addLine(roof[i][j+1][0],roof[i][j+1][1],roof[i+1][j+1][1],roof[i+1][j+1][0],Renderer3D.CAR_FRONT);
				}

	
				if(i+1==rnumx-1){
					
					addLine(roof[i+1][j][0],roof[i+1][j+1][0],roof[i+1][j+1][1],roof[i+1][j][1],Renderer3D.CAR_RIGHT);
				}
			
			}
			
		
		}
		
		
		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}
	

	private PolygonMesh buildAirplaneMesh() {
		
		
		points=new Vector();
		points.setSize(300);

		polyData=new Vector();

		//basic sides:
		
		n=0;
		
		
		
		int pnx=2;
		int bny=4;
		int pny=2;
		int fny=4;
		int pnz=2;
		
		int numy=bny+pny+fny;
		
		BPoint[][][] body=new BPoint[pnx][numy][pnz];
		
		double back_width1=back_width*(1-0.3)+x_side*0.3; 
		double back_width2=back_width*(1-0.6)+x_side*0.6; 
		double back_width3=back_width*(1-0.9)+x_side*0.9; 
		
		double back_height1=back_height*(1-0.3)+z_side*0.3; 
		double back_height2=back_height*(1-0.6)+z_side*0.6; 
		double back_height3=back_height*(1-0.9)+z_side*0.9; 		
		
		Segments b0=new Segments(0,back_width,0,back_length,z_side-back_height,back_height);
		Segments b1=new Segments(0,back_width1,0,back_length,z_side-back_height1,back_height1);
		Segments b2=new Segments(0,back_width2,0,back_length,z_side-back_height2,back_height2);
		Segments b3=new Segments(0,back_width3,0,back_length,z_side-back_height3,back_height3);
		
		body[0][0][0]=addBPoint(-0.5,0.0,0,b0);
		body[1][0][0]=addBPoint(0.5,0.0,0,b0);
		body[0][0][1]=addBPoint(-0.5,0.0,1.0,b0);
		body[1][0][1]=addBPoint(0.5,0.0,1.0,b0);
		
		body[0][1][0]=addBPoint(-0.5,0.25,0,b1);
		body[1][1][0]=addBPoint(0.5,0.25,0,b1);
		body[0][1][1]=addBPoint(-0.5,0.25,1.0,b1);
		body[1][1][1]=addBPoint(0.5,0.25,1.0,b1);
		
		body[0][2][0]=addBPoint(-0.5,0.5,0,b2);
		body[1][2][0]=addBPoint(0.5,0.5,0,b2);
		body[0][2][1]=addBPoint(-0.5,0.5,1.0,b2);
		body[1][2][1]=addBPoint(0.5,0.5,1.0,b2);
		
		body[0][3][0]=addBPoint(-0.5,0.75,0,b3);
		body[1][3][0]=addBPoint(0.5,0.75,0,b3);
		body[0][3][1]=addBPoint(-0.5,0.75,1.0,b3);
		body[1][3][1]=addBPoint(0.5,0.75,1.0,b3);

		
		Segments p0=new Segments(0,x_side,back_length,y_side,0,z_side);
		
		body[0][bny][0]=addBPoint(-0.5,0.0,0,p0);
		body[1][bny][0]=addBPoint(0.5,0.0,0,p0);
		body[0][bny][1]=addBPoint(-0.5,0.0,1.0,p0);
		body[1][bny][1]=addBPoint(0.5,0.0,1.0,p0);
		
		body[0][bny+1][0]=addBPoint(-0.5,1.0,0,p0);
		body[1][bny+1][0]=addBPoint(0.5,1.0,0,p0);
		body[0][bny+1][1]=addBPoint(-0.5,1.0,1.0,p0);
		body[1][bny+1][1]=addBPoint(0.5,1.0,1.0,p0);
		
		double front_width0=front_width*(1-0.75)+x_side*0.75; 
		double front_width1=front_width*(1-0.7)+x_side*0.7; 
		double front_width2=front_width*(1-0.65)+x_side*0.65; 
 
		double front_height0=front_height*(1-0.75)+z_side*0.75; 
		double front_height1=front_height*(1-0.7)+z_side*0.7; 
		double front_height2=front_height*(1-0.65)+z_side*0.65; 			
		
		Segments f0=new Segments(0,front_width0,back_length+y_side,front_length,0,front_height0);
		Segments f1=new Segments(0,front_width1,back_length+y_side,front_length,0,front_height1);
		Segments f2=new Segments(0,front_width2,back_length+y_side,front_length,0,front_height2);
		Segments f3=new Segments(0,front_width,back_length+y_side,front_length,0,front_height);
		
		
		body[0][bny+pny][0]=addBPoint(-0.5,0.25,0,f0);
		body[1][bny+pny][0]=addBPoint(0.5,0.25,0,f0);
		body[0][bny+pny][1]=addBPoint(-0.5,0.25,1.0,f0);
		body[1][bny+pny][1]=addBPoint(0.5,0.25,1.0,f0);

		body[0][bny+pny+1][0]=addBPoint(-0.5,0.5,0,f1);
		body[1][bny+pny+1][0]=addBPoint(0.5,0.5,0,f1);
		body[0][bny+pny+1][1]=addBPoint(-0.5,0.5,1.0,f1);
		body[1][bny+pny+1][1]=addBPoint(0.5,0.5,1.0,f1);
		
		body[0][bny+pny+2][0]=addBPoint(-0.5,0.75,0,f2);
		body[1][bny+pny+2][0]=addBPoint(0.5,0.75,0,f2);
		body[0][bny+pny+2][1]=addBPoint(-0.5,0.75,1.0,f2);
		body[1][bny+pny+2][1]=addBPoint(0.5,0.75,1.0,f2);
		
		body[0][bny+pny+3][0]=addBPoint(-0.5,1.0,0,f3);
		body[1][bny+pny+3][0]=addBPoint(0.5,1.0,0,f3);
		body[0][bny+pny+3][1]=addBPoint(-0.5,1.0,1.0,f3);
		body[1][bny+pny+3][1]=addBPoint(0.5,1.0,1.0,f3);
		
		
		
		for (int i = 0; i < pnx-1; i++) {


			for (int j = 0; j < numy-1; j++) {

				for (int k = 0; k < pnz-1; k++) {




					if(i==0){

						LineData leftLD=addLine(body[i][j][k],body[i][j][k+1],body[i][j+1][k+1],body[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						LineData bottomLD=addLine(body[i][j][k],body[i][j+1][k],body[i+1][j+1][k],body[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==pnz-1){
						LineData topLD=addLine(body[i][j][k+1],body[i+1][j][k+1],body[i+1][j+1][k+1],body[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						LineData backLD=addLine(body[i][j][k],body[i+1][j][k],body[i+1][j][k+1],body[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==numy-1){
						LineData frontLD=addLine(body[i][j+1][k],body[i][j+1][k+1],body[i+1][j+1][k+1],body[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==pnx-1){

						LineData rightLD=addLine(body[i+1][j][k],body[i+1][j+1][k],body[i+1][j+1][k+1],body[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		///////tail
		
		
		int twnx=2;
		int twny=2;
		int twnz=2;	
		
		double twDX=65;
	
		BPoint[][][] tailRightWing=new BPoint[twnx][twny][twnz];	

		Segments trWing0=new Segments(0,twDX,0,back_length*0.125,z_side-back_height,back_height);

		tailRightWing[0][0][0]=body[1][0][0];
		tailRightWing[1][0][0]=addBPoint(1.0,0.0,0.0,trWing0);
		tailRightWing[0][0][1]=body[1][0][1]; 
		tailRightWing[1][0][1]=addBPoint(1.0,0.0,1.0,trWing0);

		tailRightWing[0][1][0]=body[1][1][0];
		tailRightWing[1][1][0]=addBPoint(1.0,1.0,0,trWing0);
		tailRightWing[0][1][1]=body[1][1][1];
		tailRightWing[1][1][1]=addBPoint(1.0,1.0,1.0,trWing0); 

		for (int i = 0; i < twnx-1; i++) {


			for (int j = 0; j < twny-1; j++) {

				for (int k = 0; k < twnz-1; k++) {




					if(i==0){

						addLine(tailRightWing[i][j][k],tailRightWing[i][j][k+1],tailRightWing[i][j+1][k+1],tailRightWing[i][j+1][k],Renderer3D.CAR_LEFT);
					}



					if(k==0){

						addLine(tailRightWing[i][j][k],tailRightWing[i][j+1][k],tailRightWing[i+1][j+1][k],tailRightWing[i+1][j][k],Renderer3D.CAR_BOTTOM);

					}

					if(k+1==twnz-1){
						addLine(tailRightWing[i][j][k+1],tailRightWing[i+1][j][k+1],tailRightWing[i+1][j+1][k+1],tailRightWing[i][j+1][k+1],Renderer3D.CAR_TOP);
					}

					if(j==0){
						addLine(tailRightWing[i][j][k],tailRightWing[i+1][j][k],tailRightWing[i+1][j][k+1],tailRightWing[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==twny-1){
						addLine(tailRightWing[i][j+1][k],tailRightWing[i][j+1][k+1],tailRightWing[i+1][j+1][k+1],tailRightWing[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}


					if(i+1==twnx-1){

						addLine(tailRightWing[i+1][j][k],tailRightWing[i+1][j+1][k],tailRightWing[i+1][j+1][k+1],tailRightWing[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
	
	
		BPoint[][][] tailLeftWing=new BPoint[twnx][twny][twnz];		
		
		Segments tlWing0=new Segments(0,twDX,0,back_length*0.125,z_side-back_height,back_height);
		
		tailLeftWing[0][0][0]=addBPoint(-1.0,0,0,tlWing0);
		tailLeftWing[1][0][0]=body[0][0][0];
		tailLeftWing[0][0][1]=addBPoint(-1.0,0.0,1.0,tlWing0);
		tailLeftWing[1][0][1]=body[0][0][1];
		
		tailLeftWing[0][1][0]=addBPoint(-1.0,1.0,0,tlWing0);
		tailLeftWing[1][1][0]=body[0][1][0];
		tailLeftWing[0][1][1]=addBPoint(-1.0,1.0,1.0,tlWing0);
		tailLeftWing[1][1][1]=body[0][1][1];

		for (int i = 0; i < twnx-1; i++) {


			for (int j = 0; j < twny-1; j++) {

				for (int k = 0; k < twnz-1; k++) {




					if(i==0){

						addLine(tailLeftWing[i][j][k],tailLeftWing[i][j][k+1],tailLeftWing[i][j+1][k+1],tailLeftWing[i][j+1][k],Renderer3D.CAR_LEFT);
					}



					if(k==0){

						addLine(tailLeftWing[i][j][k],tailLeftWing[i][j+1][k],tailLeftWing[i+1][j+1][k],tailLeftWing[i+1][j][k],Renderer3D.CAR_BOTTOM);

					}

					if(k+1==twnz-1){
						addLine(tailLeftWing[i][j][k+1],tailLeftWing[i+1][j][k+1],tailLeftWing[i+1][j+1][k+1],tailLeftWing[i][j+1][k+1],Renderer3D.CAR_TOP);
					}

					if(j==0){
						addLine(tailLeftWing[i][j][k],tailLeftWing[i+1][j][k],tailLeftWing[i+1][j][k+1],tailLeftWing[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==twny-1){
						addLine(tailLeftWing[i][j+1][k],tailLeftWing[i][j+1][k+1],tailLeftWing[i+1][j+1][k+1],tailLeftWing[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}


					if(i+1==twnx-1){

						addLine(tailLeftWing[i+1][j][k],tailLeftWing[i+1][j+1][k],tailLeftWing[i+1][j+1][k+1],tailLeftWing[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}

		
		int trnx=2;
		int trny=2;
		int trnz=2;		
	
		BPoint[][][] tailRudder=new BPoint[trnx][trny][trnz];	
		
		Segments rudder0=new Segments(0,back_width,0,back_length*0.25,z_side-back_height,back_height+71);
		
		tailRudder[0][0][0]=body[0][0][1];
		tailRudder[1][0][0]=body[1][0][1];
		tailRudder[0][1][0]=body[0][1][1];
		tailRudder[1][1][0]=body[1][1][1];
		
		
		tailRudder[0][0][1]=addBPoint(0,0.0,1.0,rudder0);
		tailRudder[0][1][1]=addBPoint(0.0,1.0,1.0,rudder0);

		addLine(tailRudder[0][0][0],tailRudder[0][0][1],tailRudder[0][1][1],tailRudder[0][1][0],Renderer3D.CAR_LEFT);

		//addLine(tailRudder[0][0][0],tailRudder[0][1][0],tailRudder[1][1][0],tailRudder[1][0][0],Renderer3D.CAR_BOTTOM);	

		addLine(tailRudder[0][0][0],tailRudder[1][0][0],tailRudder[0][0][1],null,Renderer3D.CAR_BACK);
	
		addLine(tailRudder[0][1][0],tailRudder[0][1][1],tailRudder[1][1][0],null,Renderer3D.CAR_FRONT);	

		addLine(tailRudder[1][0][0],tailRudder[1][1][0],tailRudder[0][1][1],tailRudder[0][0][1],Renderer3D.CAR_RIGHT);


		
		//////wings
		
		int wnx=2;
		int wny=2;
		int wnz=2;
		
		double q=Math.PI*10.0/180.0;
		double sq=Math.sin(q);
		double cq=Math.cos(q);
		
		double sq1=sq*roof_width/roof_height;
		double ry=back_length+0.2*y_side;
	
		BPoint[][][] rightWing=new BPoint[wnx][wny][wnz];
		Segments rWing0=new Segments(x_side*0.5,roof_width/cq,ry,roof_length,0,roof_height);
		
		rightWing[0][0][0]=addBPoint(0,0.0,0,rWing0);
		rightWing[1][0][0]=addBPoint(cq,0.0,sq1,rWing0);
		rightWing[0][0][1]=addBPoint(0.0,0.0,1.0,rWing0);
		rightWing[1][0][1]=addBPoint(cq,0.0,1.0+sq1,rWing0);
		
		rightWing[0][1][0]=addBPoint(0.0,1.0,0,rWing0);
		rightWing[1][1][0]=addBPoint(cq,0.5,sq1,rWing0);
		rightWing[0][1][1]=addBPoint(0.0,1.0,1.0,rWing0);
		rightWing[1][1][1]=addBPoint(cq,0.5,1.0+sq1,rWing0);
		
		
		
		for (int i = 0; i < wnx-1; i++) {


			for (int j = 0; j < wny-1; j++) {

				for (int k = 0; k < wnz-1; k++) {




					if(i==0){

						LineData leftLD=addLine(rightWing[i][j][k],rightWing[i][j][k+1],rightWing[i][j+1][k+1],rightWing[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						LineData bottomLD=addLine(rightWing[i][j][k],rightWing[i][j+1][k],rightWing[i+1][j+1][k],rightWing[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==wnz-1){
						LineData topLD=addLine(rightWing[i][j][k+1],rightWing[i+1][j][k+1],rightWing[i+1][j+1][k+1],rightWing[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						LineData backLD=addLine(rightWing[i][j][k],rightWing[i+1][j][k],rightWing[i+1][j][k+1],rightWing[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==wny-1){
						LineData roofLD=addLine(rightWing[i][j+1][k],rightWing[i][j+1][k+1],rightWing[i+1][j+1][k+1],rightWing[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==wnx-1){

						LineData rightLD=addLine(rightWing[i+1][j][k],rightWing[i+1][j+1][k],rightWing[i+1][j+1][k+1],rightWing[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		
		BPoint[][][] leftWing=new BPoint[wnx][wny][wnz];
		
		Segments lWing0=new Segments(-x_side*0.5,roof_width/cq,ry,roof_length,0,roof_height);
		
		leftWing[0][0][0]=addBPoint(-cq,0.0,sq1,lWing0);
		leftWing[1][0][0]=addBPoint(0.0,0.0,0,lWing0);
		leftWing[0][0][1]=addBPoint(-cq,0.0,1.0+sq1,lWing0);
		leftWing[1][0][1]=addBPoint(0.0,0.0,1.0,lWing0);
		
		leftWing[0][1][0]=addBPoint(-cq,0.5,sq1,lWing0);
		leftWing[1][1][0]=addBPoint(0.0,1.0,0,lWing0);
		leftWing[0][1][1]=addBPoint(-cq,0.5,1.0+sq1,lWing0);
		leftWing[1][1][1]=addBPoint(0.0,1.0,1.0,lWing0);
		
		
		
		for (int i = 0; i < wnx-1; i++) {


			for (int j = 0; j < wny-1; j++) {

				for (int k = 0; k < wnz-1; k++) {




					if(i==0){

						LineData leftLD=addLine(leftWing[i][j][k],leftWing[i][j][k+1],leftWing[i][j+1][k+1],leftWing[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						LineData bottomLD=addLine(leftWing[i][j][k],leftWing[i][j+1][k],leftWing[i+1][j+1][k],leftWing[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==wnz-1){
						LineData topLD=addLine(leftWing[i][j][k+1],leftWing[i+1][j][k+1],leftWing[i+1][j+1][k+1],leftWing[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						LineData backLD=addLine(leftWing[i][j][k],leftWing[i+1][j][k],leftWing[i+1][j][k+1],leftWing[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==wny-1){
						LineData roofLD=addLine(leftWing[i][j+1][k],leftWing[i][j+1][k+1],leftWing[i+1][j+1][k+1],leftWing[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==wnx-1){

						LineData rightLD=addLine(leftWing[i+1][j][k],leftWing[i+1][j+1][k],leftWing[i+1][j+1][k+1],leftWing[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		PolygonMesh pm=new PolygonMesh(points,polyData);
		
		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildRailroadCarMesh() {
				
		points=new Vector();
		points.setSize(300);

		polyData=new Vector();

		//basic sides:
		
		n=0;
		
		int pnx=2;
		int pny=2;
		int pnz=2;
		
		BPoint[][][] body=new BPoint[pnx][pny][pnz];
		
		Segments p0=new Segments(0,x_side,0,y_side,back_height,z_side);
		
		body[0][0][0]=addBPoint(-0.5,0.0,0,p0);
		body[1][0][0]=addBPoint(0.5,0.0,0,p0);
		body[0][0][1]=addBPoint(-0.5,0.0,1.0,p0);
		body[1][0][1]=addBPoint(0.5,0.0,1.0,p0);
		
		body[0][1][0]=addBPoint(-0.5,1.0,0,p0);
		body[1][1][0]=addBPoint(0.5,1.0,0,p0);
		body[0][1][1]=addBPoint(-0.5,1.0,1.0,p0);
		body[1][1][1]=addBPoint(0.5,1.0,1.0,p0);		
	
		
		
		for (int i = 0; i < pnx-1; i++) {


			for (int j = 0; j < pny-1; j++) {

				for (int k = 0; k < pnz-1; k++) {




					if(i==0){

						LineData leftLD=addLine(body[i][j][k],body[i][j][k+1],body[i][j+1][k+1],body[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						LineData bottomLD=addLine(body[i][j][k],body[i][j+1][k],body[i+1][j+1][k],body[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==pnz-1){
						LineData topLD=addLine(body[i][j][k+1],body[i+1][j][k+1],body[i+1][j+1][k+1],body[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						LineData backLD=addLine(body[i][j][k],body[i+1][j][k],body[i+1][j][k+1],body[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==pny-1){
						LineData frontLD=addLine(body[i][j+1][k],body[i][j+1][k+1],body[i+1][j+1][k+1],body[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==pnx-1){

						LineData rightLD=addLine(body[i+1][j][k],body[i+1][j+1][k],body[i+1][j+1][k+1],body[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		
		
		int bnx=2;
		int bny=2;
		int bnz=2;
				
		BPoint[][][] back=new BPoint[bnx][bny][bnz];
		
		Segments b0=new Segments(0,back_width,rear_overhang,back_length,0,back_height);
		
		back[0][0][0]=addBPoint(-0.5,0.0,0,b0);
		back[1][0][0]=addBPoint(0.5,0.0,0,b0);
		back[0][0][1]=addBPoint(-0.5,0.0,1.0,b0);
		back[1][0][1]=addBPoint(0.5,0.0,1.0,b0);
		
		back[0][1][0]=addBPoint(-0.5,1.0,0,b0);
		back[1][1][0]=addBPoint(0.5,1.0,0,b0);
		back[0][1][1]=addBPoint(-0.5,1.0,1.0,b0);
		back[1][1][1]=addBPoint(0.5,1.0,1.0,b0);		
		
		buildWheel(-back_width*0.5-wheel_width,rear_overhang,0,wheel_radius,wheel_width);
		buildWheel(back_width*0.5,rear_overhang,0,wheel_radius,wheel_width);
		
		buildWheel(-back_width*0.5-wheel_width,rear_overhang+back_length,0,wheel_radius,wheel_width);
		buildWheel(back_width*0.5,rear_overhang+back_length,0,wheel_radius,wheel_width);
		
		for (int i = 0; i < bnx-1; i++) {


			for (int j = 0; j < bny-1; j++) {

				for (int k = 0; k < bnz-1; k++) {




					if(i==0){

						LineData leftLD=addLine(back[i][j][k],back[i][j][k+1],back[i][j+1][k+1],back[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						LineData bottomLD=addLine(back[i][j][k],back[i][j+1][k],back[i+1][j+1][k],back[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==bnz-1){
						LineData topLD=addLine(back[i][j][k+1],back[i+1][j][k+1],back[i+1][j+1][k+1],back[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						LineData backLD=addLine(back[i][j][k],back[i+1][j][k],back[i+1][j][k+1],back[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==bny-1){
						LineData frontLD=addLine(back[i][j+1][k],back[i][j+1][k+1],back[i+1][j+1][k+1],back[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==bnx-1){

						LineData rightLD=addLine(back[i+1][j][k],back[i+1][j+1][k],back[i+1][j+1][k+1],back[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		int fnx=2;
		int fny=2;
		int fnz=2;
		
		BPoint[][][] front=new BPoint[fnx][fny][fnz];
		
		double fy=y_side-front_length-front_overhang;
		
		Segments f0=new Segments(0,front_width,fy,front_length,0,front_height);
		
		front[0][0][0]=addBPoint(-0.5,0.0,0,f0);
		front[1][0][0]=addBPoint(0.5,0.0,0,f0);
		front[0][0][1]=addBPoint(-0.5,0.0,1.0,f0);
		front[1][0][1]=addBPoint(0.5,0.0,1.0,f0);
		
		front[0][1][0]=addBPoint(-0.5,1.0,0,f0);
		front[1][1][0]=addBPoint(0.5,1.0,0,f0);
		front[0][1][1]=addBPoint(-0.5,1.0,1.0,f0);
		front[1][1][1]=addBPoint(0.5,1.0,1.0,f0);
		
		buildWheel(-front_width*0.5-wheel_width,fy,0,wheel_radius,wheel_width);
		buildWheel(front_width*0.5,fy,0,wheel_radius,wheel_width);
		
		buildWheel(-front_width*0.5-wheel_width,fy+front_length,0,wheel_radius,wheel_width);
		buildWheel(front_width*0.5,fy+front_length,0,wheel_radius,wheel_width);
		
		for (int i = 0; i < fnx-1; i++) {


			for (int j = 0; j < fny-1; j++) {

				for (int k = 0; k < fnz-1; k++) {




					if(i==0){

						LineData leftLD=addLine(front[i][j][k],front[i][j][k+1],front[i][j+1][k+1],front[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						LineData bottomLD=addLine(front[i][j][k],front[i][j+1][k],front[i+1][j+1][k],front[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==fnz-1){
						LineData topLD=addLine(front[i][j][k+1],front[i+1][j][k+1],front[i+1][j+1][k+1],front[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						LineData backLD=addLine(front[i][j][k],front[i+1][j][k],front[i+1][j][k+1],front[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==fny-1){
						LineData frontLD=addLine(front[i][j+1][k],front[i][j+1][k+1],front[i+1][j+1][k+1],front[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==fnx-1){

						LineData rightLD=addLine(front[i+1][j][k],front[i+1][j+1][k],front[i+1][j+1][k+1],front[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		if(car_type==CAR_TYPE_RAILROAD_CAR_0){

			int rnx=2;
			int rny=2;
			int rnz=2;

			BPoint[][][] roof=new BPoint[rnx][rny][rnz];

			double rdy=(y_side-roof_length)*0.5;

			Segments r0=new Segments(0,roof_width,rdy,roof_length,back_height+z_side,roof_height);

			roof[0][0][0]=addBPoint(-0.5,0.0,0,r0);
			roof[1][0][0]=addBPoint(0.5,0.0,0,r0);
			roof[0][0][1]=addBPoint(-0.5,0.0,1.0,r0);
			roof[1][0][1]=addBPoint(0.5,0.0,1.0,r0);

			roof[0][1][0]=addBPoint(-0.5,1.0,0,r0);
			roof[1][1][0]=addBPoint(0.5,1.0,0,r0);
			roof[0][1][1]=addBPoint(-0.5,1.0,1.0,r0);
			roof[1][1][1]=addBPoint(0.5,1.0,1.0,r0);



			for (int i = 0; i < pnx-1; i++) {


				for (int j = 0; j < pny-1; j++) {

					for (int k = 0; k < pnz-1; k++) {




						if(i==0){

							LineData leftLD=addLine(roof[i][j][k],roof[i][j][k+1],roof[i][j+1][k+1],roof[i][j+1][k],Renderer3D.CAR_LEFT);
						}



						if(k==0){

							LineData bottomLD=addLine(roof[i][j][k],roof[i][j+1][k],roof[i+1][j+1][k],roof[i+1][j][k],Renderer3D.CAR_BOTTOM);

						}

						if(k+1==pnz-1){
							LineData topLD=addLine(roof[i][j][k+1],roof[i+1][j][k+1],roof[i+1][j+1][k+1],roof[i][j+1][k+1],Renderer3D.CAR_TOP);
						}

						if(j==0){
							LineData backLD=addLine(roof[i][j][k],roof[i+1][j][k],roof[i+1][j][k+1],roof[i][j][k+1],Renderer3D.CAR_BACK);
						}
						if(j+1==pny-1){
							LineData roofLD=addLine(roof[i][j+1][k],roof[i][j+1][k+1],roof[i+1][j+1][k+1],roof[i+1][j+1][k],Renderer3D.CAR_FRONT);	
						}


						if(i+1==pnx-1){

							LineData rightLD=addLine(roof[i+1][j][k],roof[i+1][j+1][k],roof[i+1][j+1][k+1],roof[i+1][j][k+1],Renderer3D.CAR_RIGHT);

						}
					}
				}

			}

		}else if(car_type==CAR_TYPE_RAILROAD_CAR_1){
			
			double rdy=(y_side-roof_length)*0.5;
			
			double radius=roof_width*0.5;
			
			addCylinder(0,rdy,back_height+z_side+radius,radius,roof_length,20);
		
		}else if(car_type==CAR_TYPE_RAILROAD_CAR_2){
			
			double rdy=(y_side-roof_length)*0.5;

			Segments r0=new Segments(0,roof_width,rdy,roof_length,back_height+z_side,roof_height);
			
			double dy=4.0/roof_length;
			double dx=4.0/roof_width;
				
			Prism prismRight=new Prism(4);			
			
			prismRight.lowerBase[0]=addBPoint(0.5-dx,0,0,r0);
			prismRight.lowerBase[1]=addBPoint(0.5,0,0,r0);
			prismRight.lowerBase[2]=addBPoint(0.5,1.0,0,r0);
			prismRight.lowerBase[3]=addBPoint(0.5-dx,1.0,0,r0);
			
			prismRight.upperBase[0]=addBPoint(0.5-dx,0,1.0,r0);
			prismRight.upperBase[1]=addBPoint(0.5,0,1.0,r0);
			prismRight.upperBase[2]=addBPoint(0.5,1.0,1.0,r0);
			prismRight.upperBase[3]=addBPoint(0.5-dx,1.0,1.0,r0);
		
			addPrism(prismRight);
			
			
			Prism prismLeft=new Prism(4);			
			
			prismLeft.lowerBase[0]=addBPoint(-0.5,0,0,r0);
			prismLeft.lowerBase[1]=addBPoint(-0.5+dx,0,0,r0);
			prismLeft.lowerBase[2]=addBPoint(-0.5+dx,1.0,0,r0);
			prismLeft.lowerBase[3]=addBPoint(-0.5,1.0,0,r0);
			
			prismLeft.upperBase[0]=addBPoint(-0.5,0,1.0,r0);
			prismLeft.upperBase[1]=addBPoint(-0.5+dx,0,1.0,r0);
			prismLeft.upperBase[2]=addBPoint(-0.5+dx,1.0,1.0,r0);
			prismLeft.upperBase[3]=addBPoint(-0.5,1.0,1.0,r0);
		
			addPrism(prismLeft);			
			
			Prism prismBack=new Prism(4);			
			
			prismBack.lowerBase[0]=addBPoint(-(0.5-dx),0,0,r0);
			prismBack.lowerBase[1]=addBPoint(0.5-dx,0,0,r0);
			prismBack.lowerBase[2]=addBPoint(0.5-dx,dy,0,r0);
			prismBack.lowerBase[3]=addBPoint(-(0.5-dx),dy,0,r0);
			
			prismBack.upperBase[0]=addBPoint(-(0.5-dx),0,1.0,r0);
			prismBack.upperBase[1]=addBPoint(0.5-dx,0,1.0,r0);
			prismBack.upperBase[2]=addBPoint(0.5-dx,dy,1.0,r0);
			prismBack.upperBase[3]=addBPoint(-(0.5-dx),dy,1.0,r0);
		
			addPrism(prismBack);
			
			
			Prism prismFront=new Prism(4);			
			
			prismFront.lowerBase[0]=addBPoint(-(0.5-dx),1-dy,0,r0);
			prismFront.lowerBase[1]=addBPoint(0.5-dx,1-dy,0,r0);
			prismFront.lowerBase[2]=addBPoint(0.5-dx,1.0,0,r0);
			prismFront.lowerBase[3]=addBPoint(-(0.5-dx),1.0,0,r0);
			
			prismFront.upperBase[0]=addBPoint(-(0.5-dx),1-dy,1.0,r0);
			prismFront.upperBase[1]=addBPoint(0.5-dx,1-dy,1.0,r0);
			prismFront.upperBase[2]=addBPoint(0.5-dx,1.0,1.0,r0);
			prismFront.upperBase[3]=addBPoint(-(0.5-dx),1.0,1.0,r0);
		
			addPrism(prismFront);
			
		}else if(car_type==CAR_TYPE_RAILROAD_CAR_3){
			
			
			double urdy=(y_side-roof_length)*0.5;
			
			double lry_side=y_side-front_length-back_length;
            double lrdy=(y_side-lry_side)*0.5;
			
			Segments lr=new Segments(0,x_side,lrdy,lry_side,back_height+z_side,roof_height);
			
			Segments ur=new Segments(0,roof_width,urdy,roof_length,back_height+z_side,roof_height);
			
			double dy=8.0/roof_length;
			double dx=8.0/roof_width;
				
			Prism prismRight=new Prism(4);			
			
			prismRight.lowerBase[0]=addBPoint(0.5-dx,0,0,lr);
			prismRight.lowerBase[1]=addBPoint(0.5,0,0,lr);
			prismRight.lowerBase[2]=addBPoint(0.5,1.0,0,lr);
			prismRight.lowerBase[3]=addBPoint(0.5-dx,1.0,0,lr);
			
			prismRight.upperBase[0]=addBPoint(0.5-dx,0,1.0,ur);
			prismRight.upperBase[1]=addBPoint(0.5,0,1.0,ur);
			prismRight.upperBase[2]=addBPoint(0.5,1.0,1.0,ur);
			prismRight.upperBase[3]=addBPoint(0.5-dx,1.0,1.0,ur);
		
			addPrism(prismRight);
			
			
			Prism prismLeft=new Prism(4);			
			
			prismLeft.lowerBase[0]=addBPoint(-0.5,0,0,lr);
			prismLeft.lowerBase[1]=addBPoint(-0.5+dx,0,0,lr);
			prismLeft.lowerBase[2]=addBPoint(-0.5+dx,1.0,0,lr);
			prismLeft.lowerBase[3]=addBPoint(-0.5,1.0,0,lr);
			
			prismLeft.upperBase[0]=addBPoint(-0.5,0,1.0,ur);
			prismLeft.upperBase[1]=addBPoint(-0.5+dx,0,1.0,ur);
			prismLeft.upperBase[2]=addBPoint(-0.5+dx,1.0,1.0,ur);
			prismLeft.upperBase[3]=addBPoint(-0.5,1.0,1.0,ur);
		
			addPrism(prismLeft);			
			
			Prism prismBack=new Prism(4);			
			
			prismBack.lowerBase[0]=addBPoint(-(0.5-dx),0,0,lr);
			prismBack.lowerBase[1]=addBPoint(0.5-dx,0,0,lr);
			prismBack.lowerBase[2]=addBPoint(0.5-dx,dy,0,lr);
			prismBack.lowerBase[3]=addBPoint(-(0.5-dx),dy,0,lr);
			
			prismBack.upperBase[0]=addBPoint(-(0.5-dx),0,1.0,ur);
			prismBack.upperBase[1]=addBPoint(0.5-dx,0,1.0,ur);
			prismBack.upperBase[2]=addBPoint(0.5-dx,dy,1.0,ur);
			prismBack.upperBase[3]=addBPoint(-(0.5-dx),dy,1.0,ur);
		
			addPrism(prismBack);
			
			
			Prism prismFront=new Prism(4);			
			
			prismFront.lowerBase[0]=addBPoint(-(0.5-dx),1-dy,0,lr);
			prismFront.lowerBase[1]=addBPoint(0.5-dx,1-dy,0,lr);
			prismFront.lowerBase[2]=addBPoint(0.5-dx,1.0,0,lr);
			prismFront.lowerBase[3]=addBPoint(-(0.5-dx),1.0,0,lr);
			
			prismFront.upperBase[0]=addBPoint(-(0.5-dx),1-dy,1.0,ur);
			prismFront.upperBase[1]=addBPoint(0.5-dx,1-dy,1.0,ur);
			prismFront.upperBase[2]=addBPoint(0.5-dx,1.0,1.0,ur);
			prismFront.upperBase[3]=addBPoint(-(0.5-dx),1.0,1.0,ur);
		
			addPrism(prismFront);
			
		}else if(car_type==CAR_TYPE_RAILROAD_CAR_4){
			
			Segments r0=new Segments(0,roof_width,0,roof_length,back_height+z_side,roof_height);
			
			if(roof_height>0){
			
					double h=roof_height;
					double l=roof_width*0.5;
					
					double r=h*0.5+l*l/(2.0*h);
					double zc=back_height+z_side+h*0.5-l*l/(2.0*h);
					
					//System.out.println("zcp="+(h*0.5-l*l/(2.0*h))+" ,  r="+r);
					
					double teta0=Math.atan((r-h)/l);			
					double teta1=Math.PI*0.25+0.5*teta0;
					
					int rnumx=5;
					int rnumy=2;
					
					BPoint[][] roof=new BPoint[rnumx][rnumy];
					
					roof[0][0]=body[0][0][pnz-1];
					roof[1][0]=addBPoint(-r*Math.cos(teta1),0,zc+r*Math.sin(teta1));
					roof[2][0]=addBPoint(0,0,zc+r);
					roof[3][0]=addBPoint(+r*Math.cos(teta1),0,zc+r*Math.sin(teta1));
					roof[4][0]=body[pnx-1][0][pnz-1];
					
					roof[0][1]=body[0][pny-1][pnz-1];
					roof[1][1]=addBPoint(-r*Math.cos(teta1),roof_length,zc+r*Math.sin(teta1));
					roof[2][1]=addBPoint(0,roof_length,zc+r);
					roof[3][1]=addBPoint(+r*Math.cos(teta1),roof_length,zc+r*Math.sin(teta1));
					roof[4][1]=body[pnx-1][pny-1][pnz-1];
					
					
					for(int i=0;i<rnumx-1;i++){
						
						for (int j = 0; j < rnumy-1; j++) {
							
							addLine(roof[i][j],roof[i+1][j],roof[i+1][j+1],roof[i][j+1],Renderer3D.CAR_TOP);
							
						}
						
						
					}
					
					LineData backRoof=new LineData();
					for(int i=rnumx-1;i>=0;i--){
						backRoof.addIndex(roof[i][0].getIndex());
					}
					polyData.add(backRoof);
					
					LineData frontRoof=new LineData();
					for(int i=0;i<rnumx;i++){
						frontRoof.addIndex(roof[i][1].getIndex());
					}
					polyData.add(frontRoof);
			
			}
			
		}
		
		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
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
					tyreRearWheel.setData(""+Renderer3D.CAR_TOP);
					
					polyData.add(tyreRearWheel);
				}		
			
		
	}


}	