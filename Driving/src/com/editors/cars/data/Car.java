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
    public static int CAR_TYPE_BYKE=2;
    public static int CAR_TYPE_TRACTOR=3;
    
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
		else if(car_type==CAR_TYPE_BYKE)
			return buildBykeMesh();		
		else
			return buildTractorMesh();

	}





	private PolygonMesh buildCarMesh() {
		
		points=new Vector();
		points.setSize(200);

		polyData=new Vector();

		//basic sides:
		
		double xc=x_side*0.5;
		
		n=0;
		
		
		//main body:
		
		int pnx=5;
		int pny=5;
		int pnz=2;
		
		BPoint[][][] p=new BPoint[pnx][pny][pnz];

		p[0][0][0]=addBPoint(xc-x_side*0.5,back_length,0);
		p[1][0][0]=addBPoint(xc-x_side*0.25,back_length,0);
		p[2][0][0]=addBPoint(xc,back_length,0);
		p[3][0][0]=addBPoint(xc+x_side*0.25,back_length,0);
		p[4][0][0]=addBPoint(xc+x_side*0.5,back_length,0);
		p[0][0][1]=addBPoint(xc-x_side*0.5,back_length,z_side);	
		p[1][0][1]=addBPoint(xc-x_side*0.25,back_length,z_side);	
		p[2][0][1]=addBPoint(xc,back_length,z_side);	
		p[3][0][1]=addBPoint(xc+x_side*0.25,back_length,z_side);	
		p[4][0][1]=addBPoint(xc+x_side*0.5,back_length,z_side);		
		
		p[0][1][0]=addBPoint(xc-x_side*0.5,back_length+y_side*0.25,0);	
		p[1][1][0]=addBPoint(xc-x_side*0.25,back_length+y_side*0.25,0);
		p[2][1][0]=addBPoint(xc,back_length+y_side*0.25,0);
		p[3][1][0]=addBPoint(xc+x_side*0.25,back_length+y_side*0.25,0);
		p[4][1][0]=addBPoint(xc+x_side*0.5,back_length+y_side*0.25,0);		
		p[0][1][1]=addBPoint(xc-x_side*0.5,back_length+y_side*0.25,z_side);
		p[1][1][1]=addBPoint(xc-x_side*0.25,back_length+y_side*0.25,z_side);
		p[2][1][1]=addBPoint(xc,back_length+y_side*0.25,z_side);
		p[3][1][1]=addBPoint(xc+x_side*0.25,back_length+y_side*0.25,z_side);
		p[4][1][1]=addBPoint(xc+x_side*0.5,back_length+y_side*0.25,z_side);
		
		
		p[0][2][0]=addBPoint(xc-x_side*0.5,back_length+y_side*0.5,0);	
		p[1][2][0]=addBPoint(xc-x_side*0.25,back_length+y_side*0.5,0);
		p[2][2][0]=addBPoint(xc,back_length+y_side*0.5,0);
		p[3][2][0]=addBPoint(xc+x_side*0.25,back_length+y_side*0.5,0);
		p[4][2][0]=addBPoint(xc+x_side*0.5,back_length+y_side*0.5,0);		
		p[0][2][1]=addBPoint(xc-x_side*0.5,back_length+y_side*0.5,z_side);
		p[1][2][1]=addBPoint(xc-x_side*0.25,back_length+y_side*0.5,z_side);
		p[2][2][1]=addBPoint(xc,back_length+y_side*0.5,z_side);
		p[3][2][1]=addBPoint(xc+x_side*0.25,back_length+y_side*0.5,z_side);
		p[4][2][1]=addBPoint(xc+x_side*0.5,back_length+y_side*0.5,z_side);
		
		p[0][3][0]=addBPoint(xc-x_side*0.5,back_length+y_side*0.75,0);	
		p[1][3][0]=addBPoint(xc-x_side*0.25,back_length+y_side*0.75,0);
		p[2][3][0]=addBPoint(xc,back_length+y_side*0.75,0);
		p[3][3][0]=addBPoint(xc+x_side*0.25,back_length+y_side*0.75,0);
		p[4][3][0]=addBPoint(xc+x_side*0.5,back_length+y_side*0.75,0);		
		p[0][3][1]=addBPoint(xc-x_side*0.5,back_length+y_side*0.75,z_side);
		p[1][3][1]=addBPoint(xc-x_side*0.25,back_length+y_side*0.75,z_side);
		p[2][3][1]=addBPoint(xc,back_length+y_side*0.75,z_side);
		p[3][3][1]=addBPoint(xc+x_side*0.25,back_length+y_side*0.75,z_side);
		p[4][3][1]=addBPoint(xc+x_side*0.5,back_length+y_side*0.75,z_side);
		
		
		p[0][4][0]=addBPoint(xc-x_side*0.5,back_length+y_side,0);	
		p[1][4][0]=addBPoint(xc-x_side*0.25,back_length+y_side,0);
		p[2][4][0]=addBPoint(xc,back_length+y_side,0);
		p[3][4][0]=addBPoint(xc+x_side*0.25,back_length+y_side,0);
		p[4][4][0]=addBPoint(xc+x_side*0.5,back_length+y_side,0);		
		p[0][4][1]=addBPoint(xc-x_side*0.5,back_length+y_side,z_side);
		p[1][4][1]=addBPoint(xc-x_side*0.25,back_length+y_side,z_side);
		p[2][4][1]=addBPoint(xc,back_length+y_side,z_side);
		p[3][4][1]=addBPoint(xc+x_side*0.25,back_length+y_side,z_side);
		p[4][4][1]=addBPoint(xc+x_side*0.5,back_length+y_side,z_side);
		
		for(int i=0;i<pnx;i++){

	        for(int j=0;j<pny-1;j++){ 
	        	
	        	if(i==0){
	        		
	        		LineData leftLD=addLine(p[i][j][0],p[i][j][1],p[i][j+1][1],p[i][j+1][0],Renderer3D.CAR_LEFT);
	        		
	        	}
	        	
	        	if(i>=0 && i<pnx-1){
	        		
	        		LineData bottomLD=addLine(p[i][j][0],p[i][j+1][0],p[i+1][j+1][0],p[i+1][j][0],Renderer3D.CAR_BOTTOM);
	        		
	        	}
	        	
	        	if(i==pnx-1){
	        		
	        		LineData rightLD=addLine(p[i][j][0],p[i][j+1][0],p[i][j+1][1],p[i][j][1],Renderer3D.CAR_RIGHT);
	        	}
	
	
	        }

		}
		
		//back part:
        
		int bny=2;
		int bnz=2;
		
		BPoint[][][] pBack=new BPoint[pnx][bny][bnz];
		
		double back_width1=back_width*(1-0.5)+x_side*0.5; 
		double back_height1=back_height*(1-0.75)+z_side*0.75; 
		
		pBack[0][0][0]=addBPoint(xc-back_width*0.5,0,0);
		pBack[1][0][0]=addBPoint(xc-back_width*0.25,0,0);
		pBack[2][0][0]=addBPoint(xc,0,0);
		pBack[3][0][0]=addBPoint(xc+back_width*0.25,0,0);
		pBack[4][0][0]=addBPoint(xc+back_width*0.5,0,0);
		pBack[0][0][1]=addBPoint(xc-back_width*0.5,0,back_height);
		pBack[1][0][1]=addBPoint(xc-back_width*0.25,0,back_height);
		pBack[2][0][1]=addBPoint(xc,0,back_height);
		pBack[3][0][1]=addBPoint(xc+back_width*0.25,0,back_height);
		pBack[4][0][1]=addBPoint(xc+back_width*0.5,0,back_height);
		
		pBack[0][1][0]=addBPoint(xc-back_width1*0.5,back_length*0.5,0);
		pBack[1][1][0]=addBPoint(xc-back_width1*0.25,back_length*0.5,0);
		pBack[2][1][0]=addBPoint(xc,back_length*0.5,0);	
		pBack[3][1][0]=addBPoint(xc+back_width1*0.25,back_length*0.5,0);	
		pBack[4][1][0]=addBPoint(xc+back_width1*0.5,back_length*0.5,0);	
		pBack[0][1][1]=addBPoint(xc-back_width1*0.5,back_length*0.5,back_height1);
		pBack[1][1][1]=addBPoint(xc-back_width1*0.25,back_length*0.5,back_height1);
		pBack[2][1][1]=addBPoint(xc,back_length*0.5,back_height1);
		pBack[3][1][1]=addBPoint(xc+back_width1*0.25,back_length*0.5,back_height1);
		pBack[4][1][1]=addBPoint(xc+back_width1*0.5,back_length*0.5,back_height1);
		

		for(int i=0;i<pnx;i++){
			
			if(i==0){
				
				LineData backLeft=addLine(pBack[i][bny-1][0],pBack[i][bny-1][1],p[0][0][1],p[0][0][0],Renderer3D.CAR_LEFT);
				
			}
			if(i>=0 && i<pnx-1){
				
				LineData backTop=addLine(pBack[i][bny-1][1],pBack[i+1][bny-1][1],p[i+1][0][1],p[i][0][1],Renderer3D.CAR_TOP);
				
				LineData backBottom=addLine(pBack[i+1][bny-1][0],pBack[i][bny-1][0],p[i][0][0],p[i+1][0][0],Renderer3D.CAR_BOTTOM);
			}
			
			if(i==pnx-1){
				
				LineData backRight=addLine(pBack[pnx-1][bny-1][1],pBack[pnx-1][bny-1][0],p[pnx-1][0][0],p[pnx-1][0][1],Renderer3D.CAR_RIGHT);
			}

		
		}
		
		for(int i=0;i<pnx;i++){
		
			for (int j = 0; j < bny-1; j++) {	
				
				if(i==0)
					addLine(pBack[i][j][0],pBack[i][j][1],pBack[i][j+1][1],pBack[i][j+1][0],Renderer3D.CAR_LEFT);
				
				if(i>=0 && i<pnx-1){
					
					if(j==0){
						LineData backMask=addLine(pBack[i][j][0],pBack[i+1][j][0],pBack[i+1][j][1],pBack[i][j][1],Renderer3D.CAR_BACK);	
					}				
				
					addLine(pBack[i][j][1],pBack[i+1][j][1],pBack[i+1][j+1][1],pBack[i][j+1][1],Renderer3D.CAR_TOP);
					
					addLine(pBack[i+1][j][0],pBack[i][j][0],pBack[i][j+1][0],pBack[i+1][j+1][0],Renderer3D.CAR_BOTTOM);					
					
				}
				
				if(i==pnx-1){
					
					addLine(pBack[i][j][0],pBack[i][j+1][0],pBack[i][j+1][1],pBack[i][j][1],Renderer3D.CAR_RIGHT);
					
				}		
					
				
			}

			
			
		}


		//front part:	
		
		int fny=2;
		int fnz=2;
		
		BPoint[][][] pFront=new BPoint[pnx][fny][fnz];
		
		double front_width0=front_width*(1-0.5)+x_side*0.5;  
		double front_heigh0=front_height*(1-0.75)+z_side*0.75;
		
		pFront[0][0][0]=addBPoint(xc-front_width0*0.5,back_length+y_side+front_length*0.5,0);
		pFront[1][0][0]=addBPoint(xc-front_width0*0.25,back_length+y_side+front_length*0.5,0);
		pFront[2][0][0]=addBPoint(xc,back_length+y_side+front_length*0.5,0);
		pFront[3][0][0]=addBPoint(xc+front_width0*0.25,back_length+y_side+front_length*0.5,0);
		pFront[4][0][0]=addBPoint(xc+front_width0*0.5,back_length+y_side+front_length*0.5,0);		
		pFront[0][0][1]=addBPoint(xc-front_width0*0.5,back_length+y_side+front_length*0.5,front_heigh0);
		pFront[1][0][1]=addBPoint(xc-front_width0*0.25,back_length+y_side+front_length*0.5,front_heigh0);
		pFront[2][0][1]=addBPoint(xc,back_length+y_side+front_length*0.5,front_heigh0);
		pFront[3][0][1]=addBPoint(xc+front_width0*0.25,back_length+y_side+front_length*0.5,front_heigh0);
		pFront[4][0][1]=addBPoint(xc+front_width0*0.5,back_length+y_side+front_length*0.5,front_heigh0);
		
		pFront[0][1][0]=addBPoint(xc-front_width*0.5,back_length+y_side+front_length,0);
		pFront[1][1][0]=addBPoint(xc-front_width*0.25,back_length+y_side+front_length,0);
		pFront[2][1][0]=addBPoint(xc,back_length+y_side+front_length,0);
		pFront[3][1][0]=addBPoint(xc+front_width*0.25,back_length+y_side+front_length,0);
		pFront[4][1][0]=addBPoint(xc+front_width*0.5,back_length+y_side+front_length,0);		
		pFront[0][1][1]=addBPoint(xc-front_width*0.5,back_length+y_side+front_length,front_height);
		pFront[1][1][1]=addBPoint(xc-front_width*0.25,back_length+y_side+front_length,front_height);
		pFront[2][1][1]=addBPoint(xc,back_length+y_side+front_length,front_height);
		pFront[3][1][1]=addBPoint(xc+front_width*0.25,back_length+y_side+front_length,front_height);
		pFront[4][1][1]=addBPoint(xc+front_width*0.5,back_length+y_side+front_length,front_height);
						

		for(int i=0;i<pnx;i++){
			
			
			if(i==0){
				
				LineData frontLeft=addLine(p[0][pny-1][0],p[0][pny-1][1],pFront[0][0][1],pFront[0][0][0],Renderer3D.CAR_LEFT);
			}
			
			if(i>=0 && i<pnx-1){
				
				
				LineData frontTop=addLine(p[i][pny-1][1],p[i+1][pny-1][1],pFront[i+1][0][1],pFront[i][0][1],Renderer3D.CAR_TOP);
					
				LineData frontBottom=addLine(p[i+1][pny-1][0],p[i][pny-1][0],pFront[i][0][0],pFront[i+1][0][0],Renderer3D.CAR_BOTTOM);
			}
			
			if(i==pnx-1){
				
				LineData frontRight=addLine(p[i][pny-1][1],p[i][pny-1][0],pFront[i][0][0],pFront[i][0][1],Renderer3D.CAR_RIGHT);
			}
		}
		
		for(int i=0;i<pnx;i++){
		
			for (int j = 0; j < fny-1; j++) {
				
				if(i==0){
					
					addLine(pFront[i][j][0],pFront[i][j][1],pFront[i][j+1][1],pFront[i][j+1][0],Renderer3D.CAR_LEFT);
				}
				
				if(i>=0 && i<pnx-1){
					
					
					if(j+1==fny-1){
						
						LineData frontMask=addLine(pFront[i][j+1][0],pFront[i][j+1][1],pFront[i+1][j+1][1],pFront[i+1][j+1][0],Renderer3D.CAR_FRONT);	
						
					}

					addLine(pFront[i][j][1],pFront[i+1][j][1],pFront[i+1][j+1][1],pFront[i][j+1][1],Renderer3D.CAR_TOP);
					
					addLine(pFront[i+1][j][0],pFront[i][j][0],pFront[i][j+1][0],pFront[i+1][j+1][0],Renderer3D.CAR_BOTTOM);
		
				}
				
				if(i==pnx-1){
					
					addLine(pFront[i][j][0],pFront[i][j+1][0],pFront[i][j+1][1],pFront[i][j][1],Renderer3D.CAR_RIGHT);
				}
	
			}
		
		}
		
			
		

		//roof:	
		
		int rnz=2;
		
		BPoint[][][] pr=new BPoint[pnx][pny][rnz];
		
		pr[0][0][0]=addBPoint(xc-x_side*0.5,back_length,z_side);
		pr[1][0][0]=addBPoint(xc-x_side*0.25,back_length,z_side);
		pr[2][0][0]=addBPoint(xc,back_length,z_side);
		pr[3][0][0]=addBPoint(xc+x_side*0.25,back_length,z_side);
		pr[4][0][0]=addBPoint(xc+x_side*0.5,back_length,z_side);
		
		pr[0][1][0]=addBPoint(xc-x_side*0.5,back_length+y_side*0.25,z_side);	
		pr[1][1][0]=addBPoint(xc-x_side*0.25,back_length+y_side*0.25,z_side);	
		pr[2][1][0]=addBPoint(xc,back_length+y_side*0.25,z_side);	
		pr[3][1][0]=addBPoint(xc+x_side*0.25,back_length+y_side*0.25,z_side);			
		pr[4][1][0]=addBPoint(xc+x_side*0.5,back_length+y_side*0.25,z_side);
		
		pr[0][2][0]=addBPoint(xc-x_side*0.5,back_length+y_side*0.5,z_side);	
		pr[1][2][0]=addBPoint(xc-x_side*0.25,back_length+y_side*0.5,z_side);	
		pr[2][2][0]=addBPoint(xc,back_length+y_side*0.5,z_side);	
		pr[3][2][0]=addBPoint(xc+x_side*0.25,back_length+y_side*0.5,z_side);			
		pr[4][2][0]=addBPoint(xc+x_side*0.5,back_length+y_side*0.5,z_side);
		
		pr[0][3][0]=addBPoint(xc-x_side*0.5,back_length+y_side*0.75,z_side);	
		pr[1][3][0]=addBPoint(xc-x_side*0.25,back_length+y_side*0.75,z_side);	
		pr[2][3][0]=addBPoint(xc,back_length+y_side*0.75,z_side);	
		pr[3][3][0]=addBPoint(xc+x_side*0.25,back_length+y_side*0.75,z_side);			
		pr[4][3][0]=addBPoint(xc+x_side*0.5,back_length+y_side*0.75,z_side);
		
		pr[0][4][0]=addBPoint(xc-x_side*0.5,back_length+y_side,z_side);	
		pr[1][4][0]=addBPoint(xc-x_side*0.25,back_length+y_side,z_side);	
		pr[2][4][0]=addBPoint(xc,back_length+y_side,z_side);	
		pr[3][4][0]=addBPoint(xc+x_side*0.25,back_length+y_side,z_side);			
		pr[4][4][0]=addBPoint(xc+x_side*0.5,back_length+y_side,z_side);
		
		double roofDY=(y_side-roof_length)/2.0;
		
		pr[0][0][1]=addBPoint(xc-roof_width*0.5,roofDY+back_length,z_side+roof_height);	
		pr[1][0][1]=addBPoint(xc-roof_width*0.25,roofDY+back_length,z_side+roof_height);	
		pr[2][0][1]=addBPoint(xc,roofDY+back_length,z_side+roof_height);	
		pr[3][0][1]=addBPoint(xc+roof_width*0.25,roofDY+back_length,z_side+roof_height);	
		pr[4][0][1]=addBPoint(xc+roof_width*0.5,roofDY+back_length,z_side+roof_height);
		
		pr[0][1][1]=addBPoint(xc-roof_width*0.5,y_side*0.25+back_length-roofDY,z_side+roof_height);	
		pr[1][1][1]=addBPoint(xc-roof_width*0.25,y_side*0.25+back_length-roofDY,z_side+roof_height);	
		pr[2][1][1]=addBPoint(xc,y_side*0.25+back_length-roofDY,z_side+roof_height);	
		pr[3][1][1]=addBPoint(xc+roof_width*0.25,y_side*0.25+back_length-roofDY,z_side+roof_height);	
		pr[4][1][1]=addBPoint(xc+roof_width*0.5,y_side*0.25+back_length-roofDY,z_side+roof_height);
		
		pr[0][2][1]=addBPoint(xc-roof_width*0.5,y_side*0.5+back_length-roofDY,z_side+roof_height);	
		pr[1][2][1]=addBPoint(xc-roof_width*0.25,y_side*0.5+back_length-roofDY,z_side+roof_height);	
		pr[2][2][1]=addBPoint(xc,y_side*0.5+back_length-roofDY,z_side+roof_height);	
		pr[3][2][1]=addBPoint(xc+roof_width*0.25,y_side*0.5+back_length-roofDY,z_side+roof_height);	
		pr[4][2][1]=addBPoint(xc+roof_width*0.5,y_side*0.5+back_length-roofDY,z_side+roof_height);
		
		pr[0][3][1]=addBPoint(xc-roof_width*0.5,y_side*0.75+back_length-roofDY,z_side+roof_height);	
		pr[1][3][1]=addBPoint(xc-roof_width*0.25,y_side*0.75+back_length-roofDY,z_side+roof_height);	
		pr[2][3][1]=addBPoint(xc,y_side*0.75+back_length-roofDY,z_side+roof_height);	
		pr[3][3][1]=addBPoint(xc+roof_width*0.25,y_side*0.75+back_length-roofDY,z_side+roof_height);	
		pr[4][3][1]=addBPoint(xc+roof_width*0.5,y_side*0.75+back_length-roofDY,z_side+roof_height);
		
		pr[0][4][1]=addBPoint(xc-roof_width*0.5,y_side+back_length-roofDY,z_side+roof_height);	
		pr[1][4][1]=addBPoint(xc-roof_width*0.25,y_side+back_length-roofDY,z_side+roof_height);	
		pr[2][4][1]=addBPoint(xc,y_side+back_length-roofDY,z_side+roof_height);	
		pr[3][4][1]=addBPoint(xc+roof_width*0.25,y_side+back_length-roofDY,z_side+roof_height);	
		pr[4][4][1]=addBPoint(xc+roof_width*0.5,y_side+back_length-roofDY,z_side+roof_height);
		
		for (int i = 0; i < pnx; i++) {
			
			

			for (int j = 0; j < pny-1; j++) {
				
				if(i==0){
					
						LineData leftRLD=addLine(pr[i][j+1][1],pr[i][j+1][0],pr[i][j][0],pr[i][j][1],Renderer3D.CAR_LEFT);
						
				}
				
				if(i>=0 && i<pnx-1){
					
					if(j==0){
						LineData backRLD=addLine(pr[i][j][0],pr[i+1][j][0],pr[i+1][j][1],pr[i][j][1],Renderer3D.CAR_BACK);
					}
					
					LineData topRLD=addLine(pr[i][j][1],pr[i+1][j][1],pr[i+1][j+1][1],pr[i][j+1][1],Renderer3D.CAR_TOP);
					
					if(j+1==pny-1){
						
						LineData frontRLD=addLine(pr[i][j+1][0],pr[i][j+1][1],pr[i+1][j+1][1],pr[i+1][j+1][0],Renderer3D.CAR_FRONT);	
					}
				}
				
				if(i==pnx-1){
					
						LineData rightRLD=addLine(pr[i][j][0],pr[i][j+1][0],pr[i][j+1][1],pr[i][j][1],Renderer3D.CAR_RIGHT);
					
				}

				
			}
			
		}


		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}



	private PolygonMesh buildTruckMesh() {
		
		points=new Vector();
		points.setSize(200);

		polyData=new Vector();

		//basic sides: 
		
		n=0;
		
		
		//front:
		
		int fnx=5;
		int fny=5;
		int fnz=2;
		
		double xc=x_side*0.5;
		
		BPoint[][][] pFront=new BPoint[fnx][fny][fnz];
		
		pFront[0][0][0]=addBPoint(xc-front_width*0.5,back_length+y_side,0);
		pFront[1][0][0]=addBPoint(xc-front_width*0.25,back_length+y_side,0);
		pFront[2][0][0]=addBPoint(xc,back_length+y_side,0);
		pFront[3][0][0]=addBPoint(xc+front_width*0.25,back_length+y_side,0);	
		pFront[4][0][0]=addBPoint(xc+front_width*0.5,back_length+y_side,0);
		pFront[0][1][0]=addBPoint(xc-front_width*0.5,back_length+y_side+front_length*0.25,0);
		pFront[1][1][0]=addBPoint(xc-front_width*0.25,back_length+y_side+front_length*0.25,0);
		pFront[2][1][0]=addBPoint(xc,back_length+y_side+front_length*0.25,0);
		pFront[3][1][0]=addBPoint(xc+front_width*0.25,back_length+y_side+front_length*0.25,0);
		pFront[4][1][0]=addBPoint(xc+front_width*0.5,back_length+y_side+front_length*0.25,0);
		pFront[0][2][0]=addBPoint(xc-front_width*0.5,back_length+y_side+front_length*0.5,0);
		pFront[1][2][0]=addBPoint(xc-front_width*0.25,back_length+y_side+front_length*0.5,0);
		pFront[2][2][0]=addBPoint(xc,back_length+y_side+front_length*0.5,0);
		pFront[3][2][0]=addBPoint(xc+front_width*0.25,back_length+y_side+front_length*0.5,0);
		pFront[4][2][0]=addBPoint(xc+front_width*0.5,back_length+y_side+front_length*0.5,0);
		pFront[0][3][0]=addBPoint(xc-front_width*0.5,back_length+y_side+front_length*0.75,0);
		pFront[1][3][0]=addBPoint(xc-front_width*0.25,back_length+y_side+front_length*0.75,0);
		pFront[2][3][0]=addBPoint(xc,back_length+y_side+front_length*0.75,0);
		pFront[3][3][0]=addBPoint(xc+front_width*0.25,back_length+y_side+front_length*0.75,0);
		pFront[4][3][0]=addBPoint(xc+front_width*0.5,back_length+y_side+front_length*0.75,0);
		pFront[0][4][0]=addBPoint(xc-front_width*0.5,back_length+y_side+front_length,0);
		pFront[1][4][0]=addBPoint(xc-front_width*0.25,back_length+y_side+front_length,0);
		pFront[2][4][0]=addBPoint(xc,back_length+y_side+front_length,0);
		pFront[3][4][0]=addBPoint(xc+front_width*0.25,back_length+y_side+front_length,0);
		pFront[4][4][0]=addBPoint(xc+front_width*0.5,back_length+y_side+front_length,0);
		
		pFront[0][0][1]=addBPoint(xc-front_width*0.5,back_length+y_side,front_height);	
		pFront[1][0][1]=addBPoint(xc-front_width*0.25,back_length+y_side,front_height);	
		pFront[2][0][1]=addBPoint(xc,back_length+y_side,front_height);
		pFront[3][0][1]=addBPoint(xc+front_width*0.25,back_length+y_side,front_height);
		pFront[4][0][1]=addBPoint(xc+front_width*0.5,back_length+y_side,front_height);	
		pFront[0][1][1]=addBPoint(xc-front_width*0.5,back_length+y_side+front_length*0.25,front_height);
		pFront[1][1][1]=addBPoint(xc-front_width*0.25,back_length+y_side+front_length*0.25,front_height);	
		pFront[2][1][1]=addBPoint(xc,back_length+y_side+front_length*0.25,front_height);
		pFront[3][1][1]=addBPoint(xc+front_width*0.25,back_length+y_side+front_length*0.25,front_height);
		pFront[4][1][1]=addBPoint(xc+front_width*0.5,back_length+y_side+front_length*0.25,front_height);
		pFront[0][2][1]=addBPoint(xc-front_width*0.5,back_length+y_side+front_length*0.5,front_height);
		pFront[1][2][1]=addBPoint(xc-front_width*0.25,back_length+y_side+front_length*0.5,front_height);	
		pFront[2][2][1]=addBPoint(xc,back_length+y_side+front_length*0.5,front_height);
		pFront[3][2][1]=addBPoint(xc+front_width*0.25,back_length+y_side+front_length*0.5,front_height);
		pFront[4][2][1]=addBPoint(xc+front_width*0.5,back_length+y_side+front_length*0.5,front_height);
		pFront[0][3][1]=addBPoint(xc-front_width*0.5,back_length+y_side+front_length*0.75,front_height);
		pFront[1][3][1]=addBPoint(xc-front_width*0.25,back_length+y_side+front_length*0.75,front_height);	
		pFront[2][3][1]=addBPoint(xc,back_length+y_side+front_length*0.75,front_height);
		pFront[3][3][1]=addBPoint(xc+front_width*0.25,back_length+y_side+front_length*0.75,front_height);
		pFront[4][3][1]=addBPoint(xc+front_width*0.5,back_length+y_side+front_length*0.75,front_height);
		pFront[0][4][1]=addBPoint(xc-front_width*0.5,back_length+y_side+front_length,front_height);	
		pFront[1][4][1]=addBPoint(xc-front_width*0.25,back_length+y_side+front_length,front_height);
		pFront[2][4][1]=addBPoint(xc,back_length+y_side+front_length,front_height);
		pFront[3][4][1]=addBPoint(xc+front_width*0.25,back_length+y_side+front_length,front_height);
		pFront[4][4][1]=addBPoint(xc+front_width*0.5,back_length+y_side+front_length,front_height);
		
		for (int i = 0; i < fnx; i++) {
			
			
			for (int j = 0; j < fny-1; j++) {				
			
			
				if(i==0){
					
					LineData leftFrontLD=addLine(pFront[i][j][0],pFront[i][j][1],pFront[i][j+1][1],pFront[i][j+1][0],Renderer3D.CAR_LEFT);
				}
				
				if(i>=0 && i<fnx-1){
					
					LineData topFrontLD=addLine(pFront[i][j][1],pFront[i+1][j][1],pFront[i+1][j+1][1],pFront[i][j+1][1],Renderer3D.CAR_TOP);

					LineData bottomFrontLD=addLine(pFront[i][j][0],pFront[i][j+1][0],pFront[i+1][j+1][0],pFront[i+1][j][0],Renderer3D.CAR_BOTTOM);
					
					if(j==0){
						LineData backFrontLD=addLine(pFront[i][j][0],pFront[i+1][j][0],pFront[i+1][j][1],pFront[i][j][1],Renderer3D.CAR_BACK);
					}
					if(j+1==fny-1){
						LineData frontFrontLD=addLine(pFront[i][j+1][0],pFront[i][j+1][1],pFront[i+1][j+1][1],pFront[i+1][j+1][0],Renderer3D.CAR_FRONT);
					}
				}
				
				if(i==fnx-1){
					
					LineData rightFrontLD=addLine(pFront[i][j][0],pFront[i][j+1][0],pFront[i][j+1][1],pFront[i][j][1],Renderer3D.CAR_RIGHT);
					
				}

			}
			
		}
		
		//main body
		
		int pnx=5;
		int pny=5;
		int pnz=2;
		
		BPoint[][][] p=new BPoint[pnx][pny][pnz];

		p[0][0][0]=addBPoint(xc-x_side*0.5,back_length,0);
		p[1][0][0]=addBPoint(xc-x_side*0.25,back_length,0);
		p[2][0][0]=addBPoint(xc,back_length,0);
		p[3][0][0]=addBPoint(xc+x_side*0.25,back_length,0);
		p[4][0][0]=addBPoint(xc+x_side*0.5,back_length,0);
		p[0][1][0]=addBPoint(xc-x_side*0.5,back_length+y_side*0.25,0);
		p[1][1][0]=addBPoint(xc-x_side*0.25,back_length+y_side*0.25,0);
		p[2][1][0]=addBPoint(xc,back_length+y_side*0.25,0);
		p[3][1][0]=addBPoint(xc+x_side*0.25,back_length+y_side*0.25,0);
		p[4][1][0]=addBPoint(xc+x_side*0.5,back_length+y_side*0.25,0);
		p[0][2][0]=addBPoint(xc-x_side*0.5,back_length+y_side*0.5,0);
		p[1][2][0]=addBPoint(xc-x_side*0.25,back_length+y_side*0.5,0);
		p[2][2][0]=addBPoint(xc,back_length+y_side*0.5,0);
		p[3][2][0]=addBPoint(xc+x_side*0.25,back_length+y_side*0.5,0);
		p[4][2][0]=addBPoint(xc+x_side*0.5,back_length+y_side*0.5,0);
		p[0][3][0]=addBPoint(xc-x_side*0.5,back_length+y_side*0.75,0);
		p[1][3][0]=addBPoint(xc-x_side*0.25,back_length+y_side*0.75,0);
		p[2][3][0]=addBPoint(xc,back_length+y_side*0.75,0);
		p[3][3][0]=addBPoint(xc+x_side*0.25,back_length+y_side*0.75,0);
		p[4][3][0]=addBPoint(xc+x_side*0.5,back_length+y_side*0.75,0);
		p[0][4][0]=addBPoint(xc-x_side*0.5,back_length+y_side,0);
		p[1][4][0]=addBPoint(xc-x_side*0.25,back_length+y_side,0);
		p[2][4][0]=addBPoint(xc,back_length+y_side,0);
		p[3][4][0]=addBPoint(xc+x_side*0.25,back_length+y_side,0);
		p[4][4][0]=addBPoint(xc+x_side*0.5,back_length+y_side,0);
		
		p[0][0][1]=addBPoint(xc-x_side*0.5,back_length,z_side);	
		p[1][0][1]=addBPoint(xc-x_side*0.25,back_length,z_side);	
		p[2][0][1]=addBPoint(xc,back_length,z_side);
		p[3][0][1]=addBPoint(xc+x_side*0.25,back_length,z_side);	
		p[4][0][1]=addBPoint(xc+x_side*0.5,back_length,z_side);		
		p[0][1][1]=addBPoint(xc-x_side*0.5,back_length+y_side*0.25,z_side);	
		p[1][1][1]=addBPoint(xc-x_side*0.25,back_length+y_side*0.25,z_side);
		p[2][1][1]=addBPoint(xc,back_length+y_side*0.25,z_side);
		p[3][1][1]=addBPoint(xc+x_side*0.25,back_length+y_side*0.25,z_side);
		p[4][1][1]=addBPoint(xc+x_side*0.5,back_length+y_side*0.25,z_side);
		p[0][2][1]=addBPoint(xc-x_side*0.5,back_length+y_side*0.5,z_side);
		p[1][2][1]=addBPoint(xc-x_side*0.25,back_length+y_side*0.5,z_side);	
		p[2][2][1]=addBPoint(xc,back_length+y_side*0.5,z_side);
		p[3][2][1]=addBPoint(xc+x_side*0.25,back_length+y_side*0.5,z_side);
		p[4][2][1]=addBPoint(xc+x_side*0.5,back_length+y_side*0.5,z_side);
		p[0][3][1]=addBPoint(xc-x_side*0.5,back_length+y_side*0.75,z_side);	
		p[1][3][1]=addBPoint(xc-x_side*0.25,back_length+y_side*0.75,z_side);
		p[2][3][1]=addBPoint(xc,back_length+y_side*0.75,z_side);
		p[3][3][1]=addBPoint(xc+x_side*0.25,back_length+y_side*0.75,z_side);
		p[4][3][1]=addBPoint(xc+x_side*0.5,back_length+y_side*0.75,z_side);
		p[0][4][1]=addBPoint(xc-x_side*0.5,back_length+y_side,z_side);
		p[1][4][1]=addBPoint(xc-x_side*0.25,back_length+y_side,z_side);	
		p[2][4][1]=addBPoint(xc,back_length+y_side,z_side);
		p[3][4][1]=addBPoint(xc+x_side*0.25,back_length+y_side,z_side);
		p[4][4][1]=addBPoint(xc+x_side*0.5,back_length+y_side,z_side);
		
		for (int i = 0; i < pnx; i++) {
			
			
			for (int j = 0; j < pny-1; j++) {				
			
			
				if(i==0){
					
					LineData leftLD=addLine(p[i][j][0],p[i][j][1],p[i][j+1][1],p[i][j+1][0],Renderer3D.CAR_LEFT);
				}
				
				if(i>=0 && i<pnx-1){
					
					LineData topLD=addLine(p[i][j][1],p[i+1][j][1],p[i+1][j+1][1],p[i][j+1][1],Renderer3D.CAR_TOP);
	
					LineData bottomLD=addLine(p[i][j][0],p[i][j+1][0],p[i+1][j+1][0],p[i+1][j][0],Renderer3D.CAR_BOTTOM);
					
					if(j==0){
						LineData backLD=addLine(p[i][j][0],p[i+1][j][0],p[i+1][j][1],p[i][j][1],Renderer3D.CAR_BACK);
					}
					if(j+1==pny-1){
						LineData frontLD=addLine(p[i][j+1][0],p[i][j+1][1],p[i+1][j+1][1],p[i+1][j+1][0],Renderer3D.CAR_FRONT);	
					}
				}
				
				if(i==pnx-1){
					
					LineData rightLD=addLine(p[i][j][0],p[i][j+1][0],p[i][j+1][1],p[i][j][1],Renderer3D.CAR_RIGHT);
					
				}

			}
			
		}


		
		
		//roof:		
		
		int rnx=5;
		int rny=5;
		int rnz=2;
		
		BPoint[][][] pRoof=new BPoint[rnx][rny][rnz];
		
		pRoof[0][0][0]=addBPoint(xc-roof_width*0.5,back_length,z_side);
		pRoof[1][0][0]=addBPoint(xc-roof_width*0.25,back_length,z_side);
		pRoof[2][0][0]=addBPoint(xc,back_length,z_side);
		pRoof[3][0][0]=addBPoint(xc+roof_width*0.25,back_length,z_side);		
		pRoof[4][0][0]=addBPoint(xc+roof_width*0.5,back_length,z_side);
		pRoof[0][1][0]=addBPoint(xc-roof_width*0.5,back_length+roof_length*0.25,z_side);
		pRoof[1][1][0]=addBPoint(xc-roof_width*0.25,back_length+roof_length*0.25,z_side);
		pRoof[2][1][0]=addBPoint(xc,back_length+roof_length*0.25,z_side);
		pRoof[3][1][0]=addBPoint(xc+roof_width*0.25,back_length+roof_length*0.25,z_side);
		pRoof[4][1][0]=addBPoint(xc+roof_width*0.5,back_length+roof_length*0.25,z_side);
		pRoof[0][2][0]=addBPoint(xc-roof_width*0.5,back_length+roof_length*0.5,z_side);
		pRoof[1][2][0]=addBPoint(xc-roof_width*0.25,back_length+roof_length*0.5,z_side);
		pRoof[2][2][0]=addBPoint(xc,back_length+roof_length*0.5,z_side);
		pRoof[3][2][0]=addBPoint(xc+roof_width*0.25,back_length+roof_length*0.5,z_side);
		pRoof[4][2][0]=addBPoint(xc+roof_width*0.5,back_length+roof_length*0.5,z_side);
		pRoof[0][3][0]=addBPoint(xc-roof_width*0.5,back_length+roof_length*0.75,z_side);
		pRoof[1][3][0]=addBPoint(xc-roof_width*0.25,back_length+roof_length*0.75,z_side);
		pRoof[2][3][0]=addBPoint(xc,back_length+roof_length*0.75,z_side);
		pRoof[3][3][0]=addBPoint(xc+roof_width*0.25,back_length+roof_length*0.75,z_side);
		pRoof[4][3][0]=addBPoint(xc+roof_width*0.5,back_length+roof_length*0.75,z_side);		
		pRoof[0][4][0]=addBPoint(xc-roof_width*0.5,back_length+roof_length,z_side);
		pRoof[1][4][0]=addBPoint(xc-roof_width*0.25,back_length+roof_length,z_side);
		pRoof[2][4][0]=addBPoint(xc,back_length+roof_length,z_side);
		pRoof[3][4][0]=addBPoint(xc+roof_width*0.25,back_length+roof_length,z_side);
		pRoof[4][4][0]=addBPoint(xc+roof_width*0.5,back_length+roof_length,z_side);
		
		pRoof[0][0][1]=addBPoint(xc-roof_width*0.5,back_length,z_side+roof_height);
		pRoof[1][0][1]=addBPoint(xc-roof_width*0.25,back_length,z_side+roof_height);	
		pRoof[2][0][1]=addBPoint(xc,back_length,z_side+roof_height);
		pRoof[3][0][1]=addBPoint(xc+roof_width*0.25,back_length,z_side+roof_height);	
		pRoof[4][0][1]=addBPoint(xc+roof_width*0.5,back_length,z_side+roof_height);	
		pRoof[0][1][1]=addBPoint(xc-roof_width*0.5,back_length+roof_length*0.25,z_side+roof_height);	
		pRoof[1][1][1]=addBPoint(xc-roof_width*0.25,back_length+roof_length*0.25,z_side+roof_height);	
		pRoof[2][1][1]=addBPoint(xc,back_length+roof_length*0.25,z_side+roof_height);
		pRoof[3][1][1]=addBPoint(xc+roof_width*0.25,back_length+roof_length*0.25,z_side+roof_height);
		pRoof[4][1][1]=addBPoint(xc+roof_width*0.5,back_length+roof_length*0.25,z_side+roof_height);
		pRoof[0][2][1]=addBPoint(xc-roof_width*0.5,back_length+roof_length*0.5,z_side+roof_height);	
		pRoof[1][2][1]=addBPoint(xc-roof_width*0.25,back_length+roof_length*0.5,z_side+roof_height);	
		pRoof[2][2][1]=addBPoint(xc,back_length+roof_length*0.5,z_side+roof_height);
		pRoof[3][2][1]=addBPoint(xc+roof_width*0.25,back_length+roof_length*0.5,z_side+roof_height);
		pRoof[4][2][1]=addBPoint(xc+roof_width*0.5,back_length+roof_length*0.5,z_side+roof_height);
		pRoof[0][3][1]=addBPoint(xc-roof_width*0.5,back_length+roof_length*0.75,z_side+roof_height);	
		pRoof[1][3][1]=addBPoint(xc-roof_width*0.25,back_length+roof_length*0.75,z_side+roof_height);	
		pRoof[2][3][1]=addBPoint(xc,back_length+roof_length*0.75,z_side+roof_height);
		pRoof[3][3][1]=addBPoint(xc+roof_width*0.25,back_length+roof_length*0.75,z_side+roof_height);
		pRoof[4][3][1]=addBPoint(xc+roof_width*0.5,back_length+roof_length*0.75,z_side+roof_height);
		pRoof[0][4][1]=addBPoint(xc-roof_width*0.5,back_length+roof_length,z_side+roof_height);	
		pRoof[1][4][1]=addBPoint(xc-roof_width*0.25,back_length+roof_length,z_side+roof_height);	
		pRoof[2][4][1]=addBPoint(xc,back_length+roof_length,z_side+roof_height);
		pRoof[3][4][1]=addBPoint(xc+roof_width*0.25,back_length+roof_length,z_side+roof_height);
		pRoof[4][4][1]=addBPoint(xc+roof_width*0.5,back_length+roof_length,z_side+roof_height);

		for (int i = 0; i < rnx; i++) {
			
			
			for (int j = 0; j < rny-1; j++) {				
			
			
				if(i==0){
					
					LineData leftRoofLD=addLine(pRoof[i][j][0],pRoof[i][j][1],pRoof[i][j+1][1],pRoof[i][j+1][0],Renderer3D.CAR_LEFT);
				}
				
				if(i>=0 && i<rnx-1){
					
					LineData topRoofLD=addLine(pRoof[i][j][1],pRoof[i+1][j][1],pRoof[i+1][j+1][1],pRoof[i][j+1][1],Renderer3D.CAR_TOP);
	
					LineData bottomRoofLD=addLine(pRoof[i][j][0],pRoof[i][j+1][0],pRoof[i+1][j+1][0],pRoof[i+1][j][0],Renderer3D.CAR_BOTTOM);
					
					if(j==0){
						LineData backRoofLD=addLine(pRoof[i][j][0],pRoof[i+1][j][0],pRoof[i+1][j][1],pRoof[i][j][1],Renderer3D.CAR_BACK);
					}
					if(j+1==rny-1){
						LineData frontRoofLD=addLine(pRoof[i][j+1][0],pRoof[i][j+1][1],pRoof[i+1][j+1][1],pRoof[i+1][j+1][0],Renderer3D.CAR_FRONT);	
					}
				}
				
				if(i==rnx-1){
					
					LineData rightRoofLD=addLine(pRoof[i][j][0],pRoof[i][j+1][0],pRoof[i][j+1][1],pRoof[i][j][1],Renderer3D.CAR_RIGHT);
					
				}

			}
			
		}

		//back:
		
		int bnx=5;
		int bny=5;
		int bnz=2;
		
		BPoint[][][] pBack=new BPoint[bnx][bny][bnz];

		pBack[0][0][0]=addBPoint(xc-back_width*0.5,0,0);
		pBack[1][0][0]=addBPoint(xc-back_width*0.25,0,0);
		pBack[2][0][0]=addBPoint(xc,0,0);
		pBack[3][0][0]=addBPoint(xc+back_width*0.25,0,0);
		pBack[4][0][0]=addBPoint(xc+back_width*0.5,0,0);		
		pBack[0][1][0]=addBPoint(xc-back_width*0.5,back_length*0.25,0);
		pBack[1][1][0]=addBPoint(xc-back_width*0.25,back_length*0.25,0);
		pBack[2][1][0]=addBPoint(xc,back_length*0.25,0);
		pBack[3][1][0]=addBPoint(xc+back_width*0.25,back_length*0.25,0);
		pBack[4][1][0]=addBPoint(xc+back_width*0.5,back_length*0.25,0);
		pBack[0][2][0]=addBPoint(xc-back_width*0.5,back_length*0.5,0);
		pBack[1][2][0]=addBPoint(xc-back_width*0.25,back_length*0.5,0);
		pBack[2][2][0]=addBPoint(xc,back_length*0.5,0);
		pBack[3][2][0]=addBPoint(xc+back_width*0.25,back_length*0.5,0);
		pBack[4][2][0]=addBPoint(xc+back_width*0.5,back_length*0.5,0);
		pBack[0][3][0]=addBPoint(xc-back_width*0.5,back_length*0.75,0);
		pBack[1][3][0]=addBPoint(xc-back_width*0.25,back_length*0.75,0);
		pBack[2][3][0]=addBPoint(xc,back_length*0.75,0);
		pBack[3][3][0]=addBPoint(xc+back_width*0.25,back_length*0.75,0);
		pBack[4][3][0]=addBPoint(xc+back_width*0.5,back_length*0.75,0);
		pBack[0][4][0]=addBPoint(xc-back_width*0.5,back_length,0);
		pBack[1][4][0]=addBPoint(xc-back_width*0.25,back_length,0);
		pBack[2][4][0]=addBPoint(xc,back_length,0);
		pBack[3][4][0]=addBPoint(xc+back_width*0.25,back_length,0);
		pBack[4][4][0]=addBPoint(xc+back_width*0.5,back_length,0);

		pBack[0][0][1]=addBPoint(xc-back_width*0.5,0,back_height);	
		pBack[1][0][1]=addBPoint(xc-back_width*0.25,0,back_height);
		pBack[2][0][1]=addBPoint(xc,0,back_height);
		pBack[3][0][1]=addBPoint(xc+back_width*0.25,0,back_height);	
		pBack[4][0][1]=addBPoint(xc+back_width*0.5,0,back_height);
		pBack[0][1][1]=addBPoint(xc-back_width*0.5,back_length*0.25,back_height);	
		pBack[1][1][1]=addBPoint(xc-back_width*0.25,back_length*0.25,back_height);
		pBack[2][1][1]=addBPoint(xc,back_length*0.25,back_height);
		pBack[3][1][1]=addBPoint(xc+back_width*0.25,back_length*0.25,back_height);
		pBack[4][1][1]=addBPoint(xc+back_width*0.5,back_length*0.25,back_height);
		pBack[0][2][1]=addBPoint(xc-back_width*0.5,back_length*0.5,back_height);	
		pBack[1][2][1]=addBPoint(xc-back_width*0.25,back_length*0.5,back_height);
		pBack[2][2][1]=addBPoint(xc,back_length*0.5,back_height);
		pBack[3][2][1]=addBPoint(xc+back_width*0.25,back_length*0.5,back_height);
		pBack[4][2][1]=addBPoint(xc+back_width*0.5,back_length*0.5,back_height);
		pBack[0][3][1]=addBPoint(xc-back_width*0.5,back_length*0.75,back_height);	
		pBack[1][3][1]=addBPoint(xc-back_width*0.25,back_length*0.75,back_height);
		pBack[2][3][1]=addBPoint(xc,back_length*0.75,back_height);
		pBack[3][3][1]=addBPoint(xc+back_width*0.25,back_length*0.75,back_height);
		pBack[4][3][1]=addBPoint(xc+back_width*0.5,back_length*0.75,back_height);
		pBack[0][4][1]=addBPoint(xc-back_width*0.5,back_length,back_height);
		pBack[1][4][1]=addBPoint(xc-back_width*0.25,back_length,back_height);		
		pBack[2][4][1]=addBPoint(xc,back_length,back_height);
		pBack[3][4][1]=addBPoint(xc+back_width*0.25,back_length,back_height);
		pBack[4][4][1]=addBPoint(xc+back_width*0.5,back_length,back_height);

		for (int i = 0; i < bnx; i++) {
			
			
			for (int j = 0; j < bny-1; j++) {				
			
			
				if(i==0){
					
					LineData leftBackLD=addLine(pBack[i][j][0],pBack[i][j][1],pBack[i][j+1][1],pBack[i][j+1][0],Renderer3D.CAR_LEFT);
				}
				
				if(i>=0 && i<bnx-1){
					
					LineData topBackLD=addLine(pBack[i][j][1],pBack[i+1][j][1],pBack[i+1][j+1][1],pBack[i][j+1][1],Renderer3D.CAR_TOP);
	
					LineData bottomBackLD=addLine(pBack[i][j][0],pBack[i][j+1][0],pBack[i+1][j+1][0],pBack[i+1][j][0],Renderer3D.CAR_BOTTOM);
					
					if(j==0){
						LineData backBackLD=addLine(pBack[i][j][0],pBack[i+1][j][0],pBack[i+1][j][1],pBack[i][j][1],Renderer3D.CAR_BACK);
					}
					if(j+1==bny-1){
						LineData frontBackLD=addLine(pBack[i][j+1][0],pBack[i][j+1][1],pBack[i+1][j+1][1],pBack[i+1][j+1][0],Renderer3D.CAR_FRONT);
					}
				}
				
				if(i==bnx-1){
					
					LineData rightBackLD=addLine(pBack[i][j][0],pBack[i][j+1][0],pBack[i][j+1][1],pBack[i][j][1],Renderer3D.CAR_RIGHT);
					
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
		
		double r=50;
		double track=20;
		
		buildWheel(0,back_length,r,r,track);
		buildWheel(0,back_length+y_side,r,r,track);
		
		
		BPoint[][][] leftFrame=new BPoint[2][2][2];
		
		
		double frame_side=30;
		double xc=-frame_side*0.5;

		leftFrame[0][0][0]=addBPoint(xc-frame_side*0.5,back_length,r);
		leftFrame[1][0][0]=addBPoint(xc+frame_side*0.5,back_length,r);
		leftFrame[1][1][0]=addBPoint(xc+frame_side*0.5,back_length+y_side,r);
		leftFrame[0][1][0]=addBPoint(xc-frame_side*0.5,back_length+y_side,r);
		
		leftFrame[0][0][1]=addBPoint(xc-frame_side*0.5,back_length,r+z_side);	
		leftFrame[1][0][1]=addBPoint(xc+frame_side*0.5,back_length,r+z_side);
		leftFrame[1][1][1]=addBPoint(xc+frame_side*0.5,back_length+y_side,r+z_side);
		leftFrame[0][1][1]=addBPoint(xc-frame_side*0.5,back_length+y_side,r+z_side);		


		addLine(leftFrame[0][0][1],leftFrame[1][0][1],leftFrame[1][1][1],leftFrame[0][1][1],Renderer3D.CAR_TOP);

		addLine(leftFrame[0][0][0],leftFrame[0][1][0],leftFrame[1][1][0],leftFrame[1][0][0],Renderer3D.CAR_BOTTOM);

		addLine(leftFrame[0][0][0],leftFrame[0][0][1],leftFrame[0][1][1],leftFrame[0][1][0],Renderer3D.CAR_LEFT);

		addLine(leftFrame[1][0][0],leftFrame[1][1][0],leftFrame[1][1][1],leftFrame[1][0][1],Renderer3D.CAR_RIGHT);

		addLine(leftFrame[0][0][0],leftFrame[1][0][0],leftFrame[1][0][1],leftFrame[0][0][1],Renderer3D.CAR_BACK);

		addLine(leftFrame[0][1][0],leftFrame[0][1][1],leftFrame[1][1][1],leftFrame[1][1][0],Renderer3D.CAR_FRONT);		

		
		BPoint[][][] rightFrame=new BPoint[2][2][2];
		
		
	
		xc=track+frame_side*0.5;

		rightFrame[0][0][0]=addBPoint(xc-frame_side*0.5,back_length,r);
		rightFrame[1][0][0]=addBPoint(xc+frame_side*0.5,back_length,r);
		rightFrame[1][1][0]=addBPoint(xc+frame_side*0.5,back_length+y_side,r);
		rightFrame[0][1][0]=addBPoint(xc-frame_side*0.5,back_length+y_side,r);
		
		rightFrame[0][0][1]=addBPoint(xc-frame_side*0.5,back_length,r+z_side);	
		rightFrame[1][0][1]=addBPoint(xc+frame_side*0.5,back_length,r+z_side);
		rightFrame[1][1][1]=addBPoint(xc+frame_side*0.5,back_length+y_side,r+z_side);
		rightFrame[0][1][1]=addBPoint(xc-frame_side*0.5,back_length+y_side,r+z_side);		


		addLine(rightFrame[0][0][1],rightFrame[1][0][1],rightFrame[1][1][1],rightFrame[0][1][1],Renderer3D.CAR_TOP);

		addLine(rightFrame[0][0][0],rightFrame[0][1][0],rightFrame[1][1][0],rightFrame[1][0][0],Renderer3D.CAR_BOTTOM);

		addLine(rightFrame[0][0][0],rightFrame[0][0][1],rightFrame[0][1][1],rightFrame[0][1][0],Renderer3D.CAR_LEFT);

		addLine(rightFrame[1][0][0],rightFrame[1][1][0],rightFrame[1][1][1],rightFrame[1][0][1],Renderer3D.CAR_RIGHT);

		addLine(rightFrame[0][0][0],rightFrame[1][0][0],rightFrame[1][0][1],rightFrame[0][0][1],Renderer3D.CAR_BACK);

		addLine(rightFrame[0][1][0],rightFrame[0][1][1],rightFrame[1][1][1],rightFrame[1][1][0],Renderer3D.CAR_FRONT);		

		
		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}
	

	private PolygonMesh buildTractorMesh() {
		
		points=new Vector();
		points.setSize(200);

		polyData=new Vector();

		//basic sides:
		
		n=0;
		
		double rr=50;
		double fr=25;
		
		double track=20;
		
		buildWheel(-track,0,rr,rr,track);
		buildWheel(x_side,0,rr,rr,track);
		
		buildWheel(-track,y_side,fr,fr,track);
		buildWheel(x_side,y_side,fr,fr,track);
		
		double xc=x_side*0.5;
		
		
		BPoint[][][] body=new BPoint[2][2][2]; 
		
		
		body[0][0][0]=addBPoint(xc-x_side*0.5,0,rr);
		body[1][0][0]=addBPoint(xc+x_side*0.5,0,rr);
		body[1][1][0]=addBPoint(xc+x_side*0.5,y_side,rr);
		body[0][1][0]=addBPoint(xc-x_side*0.5,y_side,rr);
		
		body[0][0][1]=addBPoint(xc-x_side*0.5,0,rr+z_side);	
		body[1][0][1]=addBPoint(xc+x_side*0.5,0,rr+z_side);
		body[1][1][1]=addBPoint(xc+x_side*0.5,y_side,rr+z_side);
		body[0][1][1]=addBPoint(xc-x_side*0.5,y_side,rr+z_side);		


		addLine(body[0][0][1],body[1][0][1],body[1][1][1],body[0][1][1],Renderer3D.CAR_TOP);

		addLine(body[0][0][0],body[0][1][0],body[1][1][0],body[1][0][0],Renderer3D.CAR_BOTTOM);

		addLine(body[0][0][0],body[0][0][1],body[0][1][1],body[0][1][0],Renderer3D.CAR_LEFT);

		addLine(body[1][0][0],body[1][1][0],body[1][1][1],body[1][0][1],Renderer3D.CAR_RIGHT);

		addLine(body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1],Renderer3D.CAR_BACK);

		addLine(body[0][1][0],body[0][1][1],body[1][1][1],body[1][1][0],Renderer3D.CAR_FRONT);	
		
		
		BPoint[][][] pFront=new BPoint[2][2][2]; 
		
		pFront[0][0][0]=addBPoint(xc-front_width*0.5,back_length,rr+z_side);
		pFront[1][0][0]=addBPoint(xc+front_width*0.5,back_length,rr+z_side);
		pFront[1][1][0]=addBPoint(xc+front_width*0.5,back_length+front_length,rr+z_side);
		pFront[0][1][0]=addBPoint(xc-front_width*0.5,back_length+front_length,rr+z_side);
		
		pFront[0][0][1]=addBPoint(xc-front_width*0.5,back_length,rr+z_side+front_height);	
		pFront[1][0][1]=addBPoint(xc+front_width*0.5,back_length,rr+z_side+front_height);
		pFront[1][1][1]=addBPoint(xc+front_width*0.5,back_length+front_length,rr+z_side+front_height);
		pFront[0][1][1]=addBPoint(xc-front_width*0.5,back_length+front_length,rr+z_side+front_height);		


		addLine(pFront[0][0][1],pFront[1][0][1],pFront[1][1][1],pFront[0][1][1],Renderer3D.CAR_TOP);

		addLine(pFront[0][0][0],pFront[0][1][0],pFront[1][1][0],pFront[1][0][0],Renderer3D.CAR_BOTTOM);

		addLine(pFront[0][0][0],pFront[0][0][1],pFront[0][1][1],pFront[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pFront[1][0][0],pFront[1][1][0],pFront[1][1][1],pFront[1][0][1],Renderer3D.CAR_RIGHT);

		addLine(pFront[0][0][0],pFront[1][0][0],pFront[1][0][1],pFront[0][0][1],Renderer3D.CAR_BACK);

		addLine(pFront[0][1][0],pFront[0][1][1],pFront[1][1][1],pFront[1][1][0],Renderer3D.CAR_FRONT);
		
		BPoint[][][] pBack=new BPoint[2][2][2]; 
		
		
		pBack[0][0][0]=addBPoint(xc-back_width*0.5,0,rr+z_side);
		pBack[1][0][0]=addBPoint(xc+back_width*0.5,0,rr+z_side);
		pBack[1][1][0]=addBPoint(xc+back_width*0.5,back_length,rr+z_side);
		pBack[0][1][0]=addBPoint(xc-back_width*0.5,back_length,rr+z_side);
		
		pBack[0][0][1]=addBPoint(xc-back_width*0.5,0,rr+z_side+back_height);	
		pBack[1][0][1]=addBPoint(xc+back_width*0.5,0,rr+z_side+back_height);
		pBack[1][1][1]=addBPoint(xc+back_width*0.5,back_length,rr+z_side+back_height);
		pBack[0][1][1]=addBPoint(xc-back_width*0.5,back_length,rr+z_side+back_height);		


		addLine(pBack[0][0][1],pBack[1][0][1],pBack[1][1][1],pBack[0][1][1],Renderer3D.CAR_TOP);

		addLine(pBack[0][0][0],pBack[0][1][0],pBack[1][1][0],pBack[1][0][0],Renderer3D.CAR_BOTTOM);

		addLine(pBack[0][0][0],pBack[0][0][1],pBack[0][1][1],pBack[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pBack[1][0][0],pBack[1][1][0],pBack[1][1][1],pBack[1][0][1],Renderer3D.CAR_RIGHT);

		addLine(pBack[0][0][0],pBack[1][0][0],pBack[1][0][1],pBack[0][0][1],Renderer3D.CAR_BACK);

		addLine(pBack[0][1][0],pBack[0][1][1],pBack[1][1][1],pBack[1][1][0],Renderer3D.CAR_FRONT);
		
		
	
		
		
		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private void buildWheel(double rxc, double ryc, double rzc,double r, double track) {
		
			
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
					rRearWheel[i]=addBPoint(x+track,y,z);
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
					
					
					polyData.add(tyreRearWheel);
				}		
			
		
	}

}	