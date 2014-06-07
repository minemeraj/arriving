package com.editors.forniture.data;

import java.util.Vector;

import com.BPoint;
import com.CustomData;
import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.Segments;
import com.main.Renderer3D;

public class Forniture extends CustomData{



	double x_side=0;
	double y_side=0;
	double z_side=0;
		
	public static int FORNITURE_TYPE_TABLE=0;
	public static int FORNITURE_TYPE_CHAIR=1;
	public static int FORNITURE_TYPE_BED=2;
	public static int FORNITURE_TYPE_SOFA=3;
	public static int FORNITURE_TYPE_WARDROBE=4;
	public static int FORNITURE_TYPE_BOOKCASE=5;
	public static int FORNITURE_TYPE_TOILET=6;
	public static int FORNITURE_TYPE_CUPBOARD=7;
	public static int FORNITURE_TYPE_STREETLIGHT=8;
	
	public int forniture_type=FORNITURE_TYPE_TABLE;
	
	double leg_side=0;
	double leg_length=0;
	double back_height=0;
	double front_height=0;
	
	double side_width=0;
	double side_length=0;
	double side_height=0;
	
	double upper_width=0;
	double upper_length=0;
	double upper_height=0;
	

	public Forniture(){}

	public Forniture( double x_side, double y_side,double z_side,int forniture_type,
			double legLength, double legSide,double frontHeight, double backHeight,
			double side_width,double side_length, double side_height,
			double upper_width,double upper_length, double upper_height
			) {
		super();

		this.x_side = x_side;
		this.y_side = y_side;
		this.z_side = z_side;
		this.forniture_type = forniture_type;
		this.leg_length = legLength;
		this.leg_side = legSide;
		this.back_height = backHeight;
		this.front_height = frontHeight;
		
		this.side_width = side_width;
		this.side_length = side_length;
		this.side_height = side_height;
		
		this.upper_width = upper_width;
		this.upper_length = upper_length;
		this.upper_height = upper_height;
	}
	

	public Object clone(){

		Forniture grid=new Forniture(x_side,y_side,z_side,forniture_type,
				leg_length,leg_side,
				front_height,back_height,
				side_width, side_length,side_height,
				upper_width, upper_length, upper_height
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


	public String toString() {

		String ret="F="+x_side+","+y_side+","+z_side+","+forniture_type;
		ret+=","+leg_length+","+leg_side+","+front_height+","+back_height
				+","+side_width+","+side_length+","+side_height
				+","+upper_width+","+upper_length+","+upper_height;
		return ret;
	}

	public static Forniture buildForniture(String str) {

		String[] vals = str.split(",");

		double x_side =Double.parseDouble(vals[0]);
		double y_side = Double.parseDouble(vals[1]);
		double z_side = Double.parseDouble(vals[2]); 
		int forniture_type=Integer.parseInt(vals[3]);
		double legLength = Double.parseDouble(vals[4]); 
		double legSide = Double.parseDouble(vals[5]); 
		double frontHeight = Double.parseDouble(vals[6]); 
		double backHeight = Double.parseDouble(vals[7]); 
		double side_width = Double.parseDouble(vals[8]); 
		double side_length = Double.parseDouble(vals[9]); 
		double side_height = Double.parseDouble(vals[10]); 		
		double upper_width=Double.parseDouble(vals[11]); 
		double upper_length=Double.parseDouble(vals[12]); 
		double upper_height=Double.parseDouble(vals[13]); 

		Forniture grid=new Forniture(x_side,y_side,z_side,forniture_type,
				legLength,legSide,
				frontHeight,backHeight,
				side_width, side_length,side_height,
				upper_width,upper_length,upper_height);

		return grid;
	}



	public double getZ_side() {
		return z_side;
	}

	public void setZ_side(double z_side) {
		this.z_side = z_side;
	}

	

	public int getForniture_type() {
		return forniture_type;
	}

	public void setForniture_type(int forniture_type) {
		this.forniture_type = forniture_type;
	}

	public double getLeg_side() {
		return leg_side;
	}

	public void setLeg_side(double leg_side) {
		this.leg_side = leg_side;
	}

	public double getLeg_length() {
		return leg_length;
	}

	public void setLeg_length(double leg_length) {
		this.leg_length = leg_length;
	}

	public double getBack_height() {
		return back_height;
	}

	public void setBack_height(double back_length) {
		this.back_height = back_length;
	}

	public int pos(int i, int j, int k){

		return (i+(1+1)*j)*2+k;
	}


	public PolygonMesh buildMesh(){
		
		if(FORNITURE_TYPE_TABLE==forniture_type){			
			
			return buildTableMesh();
			
		}
		else if(FORNITURE_TYPE_CHAIR==forniture_type){			
			
			return buildChairMesh();
			
		}
		else if(FORNITURE_TYPE_BED==forniture_type){			
			
			return buildBedMesh();
			
		}
		else if(FORNITURE_TYPE_SOFA==forniture_type){			
			
			return buildSofaMesh();
			
		}
		else if(FORNITURE_TYPE_WARDROBE==forniture_type){			
			
			return buildWardrobeMesh();
			
		}
		else if(FORNITURE_TYPE_TOILET==forniture_type){
			
			return buildToiletMesh();
		}
		else if(FORNITURE_TYPE_BOOKCASE==forniture_type){
			
			return buildBookcaseMesh();
		}
		else if(FORNITURE_TYPE_CUPBOARD==forniture_type){
			
			return buildCupboardMesh();
		}
		else if(FORNITURE_TYPE_STREETLIGHT==forniture_type){
			return buildStreetLighteMesh();	
		}
		else	
			return buildWardrobeMesh();


	}


	private PolygonMesh buildCupboardMesh() {
		
		points=new Vector();
		points.setSize(400);

		polyData=new Vector();
		
		n=0;	
		
		Segments b0=new Segments(0,x_side,0,y_side,0,z_side);
		
		BPoint[][][] body=new BPoint[2][2][2];
		
		body[0][0][0]=addBPoint(0,0,0,b0);
		body[1][0][0]=addBPoint(1.0,0,0,b0);
		body[1][1][0]=addBPoint(1.0,1.0,0,b0);
		body[0][1][0]=addBPoint(0,1.0,0,b0);
		
		body[0][0][1]=addBPoint(0,0,1.0,b0);
		body[1][0][1]=addBPoint(1.0,0,1.0,b0);
		body[1][1][1]=addBPoint(1.0,1.0,1.0,b0);
		body[0][1][1]=addBPoint(0,1.0,1.0,b0);
		
		addLine(body[0][0][1],body[1][0][1],body[1][1][1],body[0][1][1],Renderer3D.CAR_TOP);
		

		addLine(body[0][0][0],body[0][0][1],body[0][1][1],body[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(body[1][0][0],body[1][1][0],body[1][1][1],body[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(body[0][1][0],body[0][1][1],body[1][1][1],body[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1],Renderer3D.CAR_BACK);
		
		addLine(body[0][0][0],body[0][1][0],body[1][1][0],body[1][0][0],Renderer3D.CAR_BOTTOM);
		
		Segments lcol0=new Segments((x_side-upper_width)*0.5,side_width,y_side-upper_length,side_length,z_side,side_height);
		
		BPoint[][][] leftColumn=new BPoint[2][2][2];
		
		leftColumn[0][0][0]=addBPoint(0,0.0,0.0,lcol0);
		leftColumn[1][0][0]=addBPoint(1.0,0.0,0.0,lcol0);
		leftColumn[1][1][0]=addBPoint(1.0,1.0,0.0,lcol0);
		leftColumn[0][1][0]=addBPoint(0,1.0,0.0,lcol0);
		
		leftColumn[0][0][1]=addBPoint(0,0.0,1,lcol0);
		leftColumn[1][0][1]=addBPoint(1,0.0,1,lcol0);
		leftColumn[1][1][1]=addBPoint(1,1.0,1,lcol0);
		leftColumn[0][1][1]=addBPoint(0,1,1,lcol0);

		addLine(leftColumn[0][0][0],leftColumn[0][0][1],leftColumn[0][1][1],leftColumn[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(leftColumn[1][0][0],leftColumn[1][1][0],leftColumn[1][1][1],leftColumn[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(leftColumn[0][1][0],leftColumn[0][1][1],leftColumn[1][1][1],leftColumn[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(leftColumn[0][0][0],leftColumn[1][0][0],leftColumn[1][0][1],leftColumn[0][0][1],Renderer3D.CAR_BACK);
		
		
		
		Segments rcol0=new Segments((x_side+upper_width)*0.5-side_width,side_width,y_side-upper_length,side_length,z_side,side_height);
		
		BPoint[][][] rightColumn=new BPoint[2][2][2];
		
		rightColumn[0][0][0]=addBPoint(0.0,0.0,0.0,rcol0);
		rightColumn[1][0][0]=addBPoint(1.0,0.0,0.0,rcol0);
		rightColumn[1][1][0]=addBPoint(1.0,1.0,0.0,rcol0);
		rightColumn[0][1][0]=addBPoint(0.0,1.0,0.0,rcol0);
		
		rightColumn[0][0][1]=addBPoint(0.0,0.0,1.0,rcol0);
		rightColumn[1][0][1]=addBPoint(1.0,0.0,1.0,rcol0);
		rightColumn[1][1][1]=addBPoint(1.0,1.0,1.0,rcol0);
		rightColumn[0][1][1]=addBPoint(0.0,1.0,1.0,rcol0);
		

		addLine(rightColumn[0][0][0],rightColumn[0][0][1],rightColumn[0][1][1],rightColumn[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(rightColumn[1][0][0],rightColumn[1][1][0],rightColumn[1][1][1],rightColumn[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(rightColumn[0][1][0],rightColumn[0][1][1],rightColumn[1][1][1],rightColumn[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(rightColumn[0][0][0],rightColumn[1][0][0],rightColumn[1][0][1],rightColumn[0][0][1],Renderer3D.CAR_BACK);
		
		Segments back0=new Segments(0,x_side,0.8*y_side,0.2*y_side,z_side,side_height);
		
		BPoint[][][] backPanel=new BPoint[2][2][2];
		
		backPanel[0][0][0]=addBPoint(0.0,0.0,0.0,back0);
		backPanel[1][0][0]=addBPoint(1.0,0.0,0.0,back0);
		backPanel[1][1][0]=addBPoint(1.0,1.0,0.0,back0);
		backPanel[0][1][0]=addBPoint(0.0,1.0,0.0,back0);
		
		backPanel[0][0][1]=addBPoint(0.0,0.0,1.0,back0);
		backPanel[1][0][1]=addBPoint(1.0,0.0,1.0,back0);
		backPanel[1][1][1]=addBPoint(1.0,1.0,1.0,back0);
		backPanel[0][1][1]=addBPoint(0.0,1.0,1.0,back0);

		addLine(backPanel[0][0][0],backPanel[0][0][1],backPanel[0][1][1],backPanel[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(backPanel[1][0][0],backPanel[1][1][0],backPanel[1][1][1],backPanel[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(backPanel[0][1][0],backPanel[0][1][1],backPanel[1][1][1],backPanel[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(backPanel[0][0][0],backPanel[1][0][0],backPanel[1][0][1],backPanel[0][0][1],Renderer3D.CAR_BACK);
	
		Segments u0=new Segments((x_side-upper_width)*0.5,upper_width,y_side-upper_length,upper_length,z_side+side_height,upper_height);
		
		BPoint[][][] upper=new BPoint[2][2][2];
		
		upper[0][0][0]=addBPoint(0,0.0,0.0,u0);
		upper[1][0][0]=addBPoint(1.0,0.0,0.0,u0);
		upper[1][1][0]=addBPoint(1.0,1.0,0.0,u0);
		upper[0][1][0]=addBPoint(0,1.0,0.0,u0);
		
		upper[0][0][1]=addBPoint(0,0.0,1.0,u0);
		upper[1][0][1]=addBPoint(1.0,0.0,1.0,u0);
		upper[1][1][1]=addBPoint(1.0,1.0,1.0,u0);
		upper[0][1][1]=addBPoint(0,1.0,1.0,u0);
		
		addLine(upper[0][0][1],upper[1][0][1],upper[1][1][1],upper[0][1][1],Renderer3D.CAR_TOP);
		

		addLine(upper[0][0][0],upper[0][0][1],upper[0][1][1],upper[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(upper[1][0][0],upper[1][1][0],upper[1][1][1],upper[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(upper[0][1][0],upper[0][1][1],upper[1][1][1],upper[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(upper[0][0][0],upper[1][0][0],upper[1][0][1],upper[0][0][1],Renderer3D.CAR_BACK);
		
		addLine(upper[0][0][0],upper[0][1][0],upper[1][1][0],upper[1][0][0],Renderer3D.CAR_BOTTOM);
		
		
		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildBookcaseMesh() {
		
		points=new Vector();
		points.setSize(200);

		polyData=new Vector();
		
		n=0;
		
		Segments b0=new Segments(0,x_side,0,y_side,0,z_side);
		
		int lev=3;
		int shz=lev*2;
		
		BPoint[][][] shelves=new BPoint[4][4][shz];
		
		double rz0=0.1;
		double rz1=(1.0-lev*rz0)/(lev-1);
		
		for (int i = 0; i < shz; i=i+2) {
			
		
			double rz=(rz0+rz1)*(i/2);
			
			double dx=(x_side*0.5-leg_side)/x_side;
			double dy=(leg_side)/y_side;
			
			shelves[0][0][i]=addBPoint(-0.5,0,rz,b0);
			shelves[1][0][i]=addBPoint(-dx,0,rz,b0);
			shelves[2][0][i]=addBPoint(dx,0,rz,b0);
			shelves[3][0][i]=addBPoint(0.5,0,rz,b0);
			
			shelves[0][1][i]=addBPoint(-0.5,dy,rz,b0);
			shelves[1][1][i]=addBPoint(-dx,dy,rz,b0);
			shelves[2][1][i]=addBPoint(dx,dy,rz,b0);
			shelves[3][1][i]=addBPoint(0.5,dy,rz,b0);
			
			shelves[0][2][i]=addBPoint(-0.5,1.0-dy,rz,b0);
			shelves[1][2][i]=addBPoint(-dx,1.0-dy,rz,b0);
			shelves[2][2][i]=addBPoint(dx,1.0-dy,rz,b0);
			shelves[3][2][i]=addBPoint(0.5,1.0-dy,rz,b0);
			
			shelves[0][3][i]=addBPoint(-0.5,1.0,rz,b0);
			shelves[1][3][i]=addBPoint(-dx,1.0,rz,b0);
			shelves[2][3][i]=addBPoint(dx,1.0,rz,b0);
			shelves[3][3][i]=addBPoint(0.5,1.0,rz,b0);	
			
			
			shelves[0][0][i+1]=addBPoint(-0.5,0,rz+rz0,b0);
			shelves[1][0][i+1]=addBPoint(-dx,0,rz+rz0,b0);
			shelves[2][0][i+1]=addBPoint(dx,0,rz+rz0,b0);
			shelves[3][0][i+1]=addBPoint(0.5,0,rz+rz0,b0);
			
			shelves[0][1][i+1]=addBPoint(-0.5,dy,rz+rz0,b0);
			shelves[1][1][i+1]=addBPoint(-dx,dy,rz+rz0,b0);
			shelves[2][1][i+1]=addBPoint(dx,dy,rz+rz0,b0);
			shelves[3][1][i+1]=addBPoint(0.5,dy,rz+rz0,b0);
			
			shelves[0][2][i+1]=addBPoint(-0.5,1.0-dy,rz+rz0,b0);
			shelves[1][2][i+1]=addBPoint(-dx,1.0-dy,rz+rz0,b0);
			shelves[2][2][i+1]=addBPoint(dx,1.0-dy,rz+rz0,b0);
			shelves[3][2][i+1]=addBPoint(0.5,1.0-dy,rz+rz0,b0);
			
			shelves[0][3][i+1]=addBPoint(-0.5,1.0,rz+rz0,b0);
			shelves[1][3][i+1]=addBPoint(-dx,1.0,rz+rz0,b0);
			shelves[2][3][i+1]=addBPoint(dx,1.0,rz+rz0,b0);
			shelves[3][3][i+1]=addBPoint(0.5,1.0,rz+rz0,b0);	
		}
		
		for (int i = 0; i < shz; i++) {
			
			if(i%2==0){
			
				addLine(shelves[0][0][i],shelves[0][3][i],shelves[3][3][i],shelves[3][0][i],Renderer3D.CAR_BOTTOM);
				addLine(shelves[0][0][i+1],shelves[0][3][i+1],shelves[3][3][i+1],shelves[3][0][i+1],Renderer3D.CAR_TOP);
			
	
				addLine(shelves[0][0][i],shelves[0][0][i+1],shelves[0][3][i+1],shelves[0][3][i],Renderer3D.CAR_LEFT);				

				addLine(shelves[3][0][i],shelves[3][3][i],shelves[3][3][i+1],shelves[3][0][i+1],Renderer3D.CAR_RIGHT);
				
				addLine(shelves[0][3][i],shelves[0][3][i+1],shelves[3][3][i+1],shelves[3][3][i],Renderer3D.CAR_FRONT);
				
				addLine(shelves[0][0][i],shelves[3][0][i],shelves[3][0][i+1],shelves[0][0][i+1],Renderer3D.CAR_BACK);
			
			}
			
			if(i%2==1 && i<shz-1){
				
				//front panel
				addLine(shelves[0][3][i],shelves[0][3][i+1],shelves[3][3][i+1],shelves[3][3][i],Renderer3D.CAR_FRONT);
				addLine(shelves[1][2][i],shelves[2][2][i],shelves[2][2][i+1],shelves[1][2][i+1],Renderer3D.CAR_BACK);
				
				//left panel
				addLine(shelves[1][0][i],shelves[1][2][i],shelves[1][2][i+1],shelves[1][0][i+1],Renderer3D.CAR_RIGHT);
				addLine(shelves[0][0][i],shelves[0][0][i+1],shelves[0][3][i+1],shelves[0][3][i],Renderer3D.CAR_LEFT);
				
				//back panel left
				addLine(shelves[0][0][i],shelves[1][0][i],shelves[1][0][i+1],shelves[0][0][i+1],Renderer3D.CAR_BACK);
				
				//right panel
				addLine(shelves[3][0][i],shelves[3][3][i],shelves[3][3][i+1],shelves[3][0][i+1],Renderer3D.CAR_RIGHT);
				addLine(shelves[2][0][i],shelves[2][0][i+1],shelves[2][2][i+1],shelves[2][2][i],Renderer3D.CAR_LEFT);
				
				//back panel right
				addLine(shelves[2][0][i],shelves[3][0][i],shelves[3][0][i+1],shelves[2][0][i+1],Renderer3D.CAR_BACK);
				
			}

		}
		
		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildToiletMesh() { 
		
		points=new Vector();
		points.setSize(800);

		polyData=new Vector();
		
		n=0;
		
		int hnx=9;
		int hny=9;
		int hnz=9;

		BPoint[][][] body=new BPoint[hnx][hny][hnz];
		
		double xc=0;
		
		Segments h0=new Segments(xc,x_side,0,y_side,0,z_side);
		/*Segments h1=new Segments(xc,x_side,0,y_side,0,z_side);
		Segments h2=new Segments(xc,x_side,0,y_side,0,z_side);
		Segments h3=new Segments(xc,x_side,0,y_side,0,z_side);
		Segments h4=new Segments(xc,x_side,0,y_side,0,z_side);*/
	
		body[0][0][0]=addBPoint(-0.29,0.202,0.0,h0);
		body[1][0][0]=addBPoint(-0.217,0.202,0.0,h0);
		body[2][0][0]=addBPoint(-0.145,0.202,0.0,h0);
		body[3][0][0]=addBPoint(-0.073,0.202,0.0,h0);
		body[4][0][0]=addBPoint(0.0,0.202,0.0,h0);
		body[5][0][0]=addBPoint(0.073,0.202,0.0,h0);
		body[6][0][0]=addBPoint(0.145,0.202,0.0,h0);
		body[7][0][0]=addBPoint(0.217,0.202,0.0,h0);
		body[8][0][0]=addBPoint(0.29,0.202,0.0,h0);
		body[0][0][1]=addBPoint(-0.247,0.181,0.248,h0);
		body[1][0][1]=addBPoint(-0.185,0.181,0.248,h0);
		body[2][0][1]=addBPoint(-0.123,0.181,0.248,h0);
		body[3][0][1]=addBPoint(-0.062,0.181,0.248,h0);
		body[4][0][1]=addBPoint(0.0,0.181,0.248,h0);
		body[5][0][1]=addBPoint(0.062,0.181,0.248,h0);
		body[6][0][1]=addBPoint(0.123,0.181,0.248,h0);
		body[7][0][1]=addBPoint(0.185,0.181,0.248,h0);
		body[8][0][1]=addBPoint(0.247,0.181,0.248,h0);
		body[0][0][2]=addBPoint(-0.228,0.137,0.416,h0);
		body[1][0][2]=addBPoint(-0.171,0.137,0.416,h0);
		body[2][0][2]=addBPoint(-0.114,0.137,0.416,h0);
		body[3][0][2]=addBPoint(-0.057,0.137,0.416,h0);
		body[4][0][2]=addBPoint(0.0,0.137,0.416,h0);
		body[5][0][2]=addBPoint(0.057,0.137,0.416,h0);
		body[6][0][2]=addBPoint(0.114,0.137,0.416,h0);
		body[7][0][2]=addBPoint(0.171,0.137,0.416,h0);
		body[8][0][2]=addBPoint(0.228,0.137,0.416,h0);
		body[0][0][3]=addBPoint(-0.216,0.116,0.478,h0);
		body[1][0][3]=addBPoint(-0.162,0.116,0.478,h0);
		body[2][0][3]=addBPoint(-0.108,0.116,0.478,h0);
		body[3][0][3]=addBPoint(-0.054,0.116,0.478,h0);
		body[4][0][3]=addBPoint(0.0,0.116,0.478,h0);
		body[5][0][3]=addBPoint(0.054,0.116,0.478,h0);
		body[6][0][3]=addBPoint(0.108,0.116,0.478,h0);
		body[7][0][3]=addBPoint(0.162,0.116,0.478,h0);
		body[8][0][3]=addBPoint(0.216,0.116,0.478,h0);
		body[0][0][4]=addBPoint(-0.302,0.101,0.565,h0);
		body[1][0][4]=addBPoint(-0.226,0.101,0.565,h0);
		body[2][0][4]=addBPoint(-0.151,0.101,0.565,h0);
		body[3][0][4]=addBPoint(-0.076,0.101,0.565,h0);
		body[4][0][4]=addBPoint(0.0,0.101,0.565,h0);
		body[5][0][4]=addBPoint(0.076,0.101,0.565,h0);
		body[6][0][4]=addBPoint(0.151,0.101,0.565,h0);
		body[7][0][4]=addBPoint(0.226,0.101,0.565,h0);
		body[8][0][4]=addBPoint(0.302,0.101,0.565,h0);
		body[0][0][5]=addBPoint(-0.401,0.079,0.671,h0);
		body[1][0][5]=addBPoint(-0.301,0.079,0.671,h0);
		body[2][0][5]=addBPoint(-0.2,0.079,0.671,h0);
		body[3][0][5]=addBPoint(-0.1,0.079,0.671,h0);
		body[4][0][5]=addBPoint(0.0,0.079,0.671,h0);
		body[5][0][5]=addBPoint(0.1,0.079,0.671,h0);
		body[6][0][5]=addBPoint(0.2,0.079,0.671,h0);
		body[7][0][5]=addBPoint(0.301,0.079,0.671,h0);
		body[8][0][5]=addBPoint(0.401,0.079,0.671,h0);
		body[0][0][6]=addBPoint(-0.451,0.054,0.783,h0);
		body[1][0][6]=addBPoint(-0.338,0.054,0.783,h0);
		body[2][0][6]=addBPoint(-0.225,0.054,0.783,h0);
		body[3][0][6]=addBPoint(-0.113,0.054,0.783,h0);
		body[4][0][6]=addBPoint(0.0,0.054,0.783,h0);
		body[5][0][6]=addBPoint(0.113,0.054,0.783,h0);
		body[6][0][6]=addBPoint(0.225,0.054,0.783,h0);
		body[7][0][6]=addBPoint(0.338,0.054,0.783,h0);
		body[8][0][6]=addBPoint(0.451,0.054,0.783,h0);
		body[0][0][7]=addBPoint(-0.481,0.025,0.907,h0);
		body[1][0][7]=addBPoint(-0.361,0.025,0.907,h0);
		body[2][0][7]=addBPoint(-0.24,0.025,0.907,h0);
		body[3][0][7]=addBPoint(-0.12,0.025,0.907,h0);
		body[4][0][7]=addBPoint(0.0,0.025,0.907,h0);
		body[5][0][7]=addBPoint(0.12,0.025,0.907,h0);
		body[6][0][7]=addBPoint(0.24,0.025,0.907,h0);
		body[7][0][7]=addBPoint(0.361,0.025,0.907,h0);
		body[8][0][7]=addBPoint(0.481,0.025,0.907,h0);
		body[0][0][8]=addBPoint(-0.5,0.0,1.0,h0);
		body[1][0][8]=addBPoint(-0.375,0.0,1.0,h0);
		body[2][0][8]=addBPoint(-0.25,0.0,1.0,h0);
		body[3][0][8]=addBPoint(-0.125,0.0,1.0,h0);
		body[4][0][8]=addBPoint(0.0,0.0,1.0,h0);
		body[5][0][8]=addBPoint(0.125,0.0,1.0,h0);
		body[6][0][8]=addBPoint(0.25,0.0,1.0,h0);
		body[7][0][8]=addBPoint(0.375,0.0,1.0,h0);
		body[8][0][8]=addBPoint(0.5,0.0,1.0,h0);

		body[0][1][0]=addBPoint(-0.29,0.265,0.0,h0);
		body[1][1][0]=addBPoint(-0.217,0.278,0.0,h0);
		body[2][1][0]=addBPoint(-0.145,0.281,0.0,h0);
		body[3][1][0]=addBPoint(-0.073,0.283,0.0,h0);
		body[4][1][0]=addBPoint(0.0,0.284,0.0,h0);
		body[5][1][0]=addBPoint(0.073,0.283,0.0,h0);
		body[6][1][0]=addBPoint(0.145,0.281,0.0,h0);
		body[7][1][0]=addBPoint(0.217,0.278,0.0,h0);
		body[8][1][0]=addBPoint(0.29,0.265,0.0,h0);
		body[0][1][1]=addBPoint(-0.247,0.246,0.248,h0);
		body[8][1][1]=addBPoint(0.247,0.246,0.248,h0);
		body[0][1][2]=addBPoint(-0.228,0.207,0.416,h0);
		body[8][1][2]=addBPoint(0.228,0.207,0.416,h0);
		body[0][1][3]=addBPoint(-0.216,0.189,0.478,h0);
		body[8][1][3]=addBPoint(0.216,0.189,0.478,h0);
		body[0][1][4]=addBPoint(-0.302,0.179,0.565,h0);
		body[8][1][4]=addBPoint(0.302,0.179,0.565,h0);
		body[0][1][5]=addBPoint(-0.401,0.16,0.671,h0);
		body[8][1][5]=addBPoint(0.401,0.16,0.671,h0);
		body[0][1][6]=addBPoint(-0.451,0.138,0.75,h0);
		body[8][1][6]=addBPoint(0.451,0.138,0.75,h0);
		body[0][1][7]=addBPoint(-0.481,0.113,0.875,h0);
		body[8][1][7]=addBPoint(0.481,0.113,0.875,h0);
		body[0][1][8]=addBPoint(-0.5,0.093,1.0,h0);
		body[1][1][8]=addBPoint(-0.375,0.114,1.0,h0);
		body[2][1][8]=addBPoint(-0.25,0.121,1.0,h0);
		body[3][1][8]=addBPoint(-0.125,0.124,1.0,h0);
		body[4][1][8]=addBPoint(0.0,0.125,1.0,h0);
		body[5][1][8]=addBPoint(0.125,0.124,1.0,h0);
		body[6][1][8]=addBPoint(0.25,0.121,1.0,h0);
		body[7][1][8]=addBPoint(0.375,0.114,1.0,h0);
		body[8][1][8]=addBPoint(0.5,0.093,1.0,h0);

		body[0][2][0]=addBPoint(-0.29,0.328,0.0,h0);
		body[1][2][0]=addBPoint(-0.217,0.353,0.0,h0);
		body[2][2][0]=addBPoint(-0.145,0.361,0.0,h0);
		body[3][2][0]=addBPoint(-0.073,0.365,0.0,h0);
		body[4][2][0]=addBPoint(0.0,0.366,0.0,h0);
		body[5][2][0]=addBPoint(0.073,0.365,0.0,h0);
		body[6][2][0]=addBPoint(0.145,0.361,0.0,h0);
		body[7][2][0]=addBPoint(0.217,0.353,0.0,h0);
		body[8][2][0]=addBPoint(0.29,0.328,0.0,h0);
		body[0][2][1]=addBPoint(-0.247,0.311,0.248,h0);
		body[8][2][1]=addBPoint(0.247,0.311,0.248,h0);
		body[0][2][2]=addBPoint(-0.228,0.277,0.416,h0);
		body[8][2][2]=addBPoint(0.228,0.277,0.416,h0);
		body[0][2][3]=addBPoint(-0.216,0.262,0.478,h0);
		body[8][2][3]=addBPoint(0.216,0.262,0.478,h0);
		body[0][2][4]=addBPoint(-0.302,0.258,0.565,h0);
		body[8][2][4]=addBPoint(0.302,0.258,0.565,h0);
		body[0][2][5]=addBPoint(-0.401,0.241,0.671,h0);
		body[8][2][5]=addBPoint(0.401,0.241,0.671,h0);
		body[0][2][6]=addBPoint(-0.451,0.222,0.783,h0);
		body[8][2][6]=addBPoint(0.451,0.222,0.783,h0);
		body[0][2][7]=addBPoint(-0.481,0.2,0.907,h0);
		body[8][2][7]=addBPoint(0.481,0.2,0.907,h0);
		body[0][2][8]=addBPoint(-0.5,0.185,1.0,h0);
		body[1][2][8]=addBPoint(-0.375,0.228,1.0,h0);
		body[2][2][8]=addBPoint(-0.25,0.241,1.0,h0);
		body[3][2][8]=addBPoint(-0.125,0.248,1.0,h0);
		body[4][2][8]=addBPoint(0.0,0.25,1.0,h0);
		body[5][2][8]=addBPoint(0.125,0.248,1.0,h0);
		body[6][2][8]=addBPoint(0.25,0.241,1.0,h0);
		body[7][2][8]=addBPoint(0.375,0.228,1.0,h0);
		body[8][2][8]=addBPoint(0.5,0.185,1.0,h0);

		body[0][3][0]=addBPoint(-0.29,0.391,0.0,h0);
		body[1][3][0]=addBPoint(-0.217,0.428,0.0,h0);
		body[2][3][0]=addBPoint(-0.145,0.439,0.0,h0);
		body[3][3][0]=addBPoint(-0.073,0.445,0.0,h0);
		body[4][3][0]=addBPoint(0.0,0.447,0.0,h0);
		body[5][3][0]=addBPoint(0.073,0.445,0.0,h0);
		body[6][3][0]=addBPoint(0.145,0.439,0.0,h0);
		body[7][3][0]=addBPoint(0.217,0.428,0.0,h0);
		body[8][3][0]=addBPoint(0.29,0.391,0.0,h0);
		body[0][3][1]=addBPoint(-0.247,0.376,0.248,h0);
		body[8][3][1]=addBPoint(0.247,0.376,0.248,h0);
		body[0][3][2]=addBPoint(-0.228,0.347,0.416,h0);
		body[8][3][2]=addBPoint(0.228,0.347,0.416,h0);
		body[0][3][3]=addBPoint(-0.216,0.335,0.478,h0);
		body[8][3][3]=addBPoint(0.216,0.335,0.478,h0);
		body[0][3][4]=addBPoint(-0.302,0.336,0.565,h0);
		body[8][3][4]=addBPoint(0.302,0.336,0.565,h0);
		body[0][3][5]=addBPoint(-0.401,0.322,0.671,h0);
		body[8][3][5]=addBPoint(0.401,0.322,0.671,h0);
		body[0][3][6]=addBPoint(-0.451,0.306,0.783,h0);
		body[8][3][6]=addBPoint(0.451,0.306,0.783,h0);
		body[0][3][7]=addBPoint(-0.481,0.287,0.907,h0);
		body[8][3][7]=addBPoint(0.481,0.287,0.907,h0);
		body[0][3][8]=addBPoint(-0.5,0.278,1.0,h0);
		body[1][3][8]=addBPoint(-0.375,0.342,1.0,h0);
		body[2][3][8]=addBPoint(-0.25,0.347,0.6,h0);
		body[3][3][8]=addBPoint(-0.125,0.347,0.5,h0);
		body[4][3][8]=addBPoint(0.0,0.347,0.5,h0);
		body[5][3][8]=addBPoint(0.125,0.347,0.5,h0);
		body[6][3][8]=addBPoint(0.25,0.347,0.6,h0);
		body[7][3][8]=addBPoint(0.375,0.342,1.0,h0);
		body[8][3][8]=addBPoint(0.5,0.278,1.0,h0);

		body[0][4][0]=addBPoint(-0.29,0.454,0.0,h0);
		body[1][4][0]=addBPoint(-0.217,0.504,0.0,h0);
		body[2][4][0]=addBPoint(-0.145,0.519,0.0,h0);
		body[3][4][0]=addBPoint(-0.073,0.527,0.0,h0);
		body[4][4][0]=addBPoint(0.0,0.529,0.0,h0);
		body[5][4][0]=addBPoint(0.073,0.527,0.0,h0);
		body[6][4][0]=addBPoint(0.145,0.519,0.0,h0);
		body[7][4][0]=addBPoint(0.217,0.504,0.0,h0);
		body[8][4][0]=addBPoint(0.29,0.454,0.0,h0);
		body[0][4][1]=addBPoint(-0.247,0.442,0.248,h0);
		body[8][4][1]=addBPoint(0.247,0.442,0.248,h0);
		body[0][4][2]=addBPoint(-0.228,0.418,0.416,h0);
		body[8][4][2]=addBPoint(0.228,0.418,0.416,h0);
		body[0][4][3]=addBPoint(-0.216,0.408,0.478,h0);
		body[8][4][3]=addBPoint(0.216,0.408,0.478,h0);
		body[0][4][4]=addBPoint(-0.302,0.414,0.565,h0);
		body[8][4][4]=addBPoint(0.302,0.414,0.565,h0);
		body[0][4][5]=addBPoint(-0.401,0.403,0.671,h0);
		body[8][4][5]=addBPoint(0.401,0.403,0.671,h0);
		body[0][4][6]=addBPoint(-0.451,0.39,0.783,h0);
		body[8][4][6]=addBPoint(0.451,0.39,0.783,h0);
		body[0][4][7]=addBPoint(-0.481,0.375,0.907,h0);
		body[8][4][7]=addBPoint(0.481,0.375,0.907,h0);
		body[0][4][8]=addBPoint(-0.5,0.37,1.0,h0);
		body[1][4][8]=addBPoint(-0.375,0.456,1.0,h0);
		body[2][4][8]=addBPoint(-0.25,0.408,0.7,h0);
		body[3][4][8]=addBPoint(-0.125,0.408,0.25,h0);
		body[4][4][8]=addBPoint(0.0,0.408,0.25,h0);
		body[5][4][8]=addBPoint(0.125,0.408,0.25,h0);
		body[6][4][8]=addBPoint(0.25,0.408,0.7,h0);
		body[7][4][8]=addBPoint(0.375,0.456,1.0,h0);
		body[8][4][8]=addBPoint(0.5,0.37,1.0,h0);

		body[0][5][0]=addBPoint(-0.29,0.517,0.0,h0);
		body[1][5][0]=addBPoint(-0.217,0.579,0.0,h0);
		body[2][5][0]=addBPoint(-0.145,0.598,0.0,h0);
		body[3][5][0]=addBPoint(-0.073,0.608,0.0,h0);
		body[4][5][0]=addBPoint(0.0,0.611,0.0,h0);
		body[5][5][0]=addBPoint(0.073,0.608,0.0,h0);
		body[6][5][0]=addBPoint(0.145,0.598,0.0,h0);
		body[7][5][0]=addBPoint(0.217,0.579,0.0,h0);
		body[8][5][0]=addBPoint(0.29,0.517,0.0,h0);
		body[0][5][1]=addBPoint(-0.247,0.507,0.248,h0);
		body[8][5][1]=addBPoint(0.247,0.507,0.248,h0);
		body[0][5][2]=addBPoint(-0.228,0.487,0.416,h0);
		body[8][5][2]=addBPoint(0.228,0.487,0.416,h0);
		body[0][5][3]=addBPoint(-0.216,0.481,0.478,h0);
		body[8][5][3]=addBPoint(0.216,0.481,0.478,h0);
		body[0][5][4]=addBPoint(-0.302,0.492,0.565,h0);
		body[8][5][4]=addBPoint(0.302,0.492,0.565,h0);
		body[0][5][5]=addBPoint(-0.401,0.484,0.671,h0);
		body[8][5][5]=addBPoint(0.401,0.484,0.671,h0);
		body[0][5][6]=addBPoint(-0.451,0.473,0.783,h0);
		body[8][5][6]=addBPoint(0.451,0.473,0.783,h0);
		body[0][5][7]=addBPoint(-0.481,0.463,0.907,h0);
		body[8][5][7]=addBPoint(0.481,0.463,0.907,h0);
		body[0][5][8]=addBPoint(-0.5,0.463,1.0,h0);
		body[1][5][8]=addBPoint(-0.375,0.57,1.0,h0);
		body[2][5][8]=addBPoint(-0.25,0.481,0.7,h0);
		body[3][5][8]=addBPoint(-0.125,0.481,0.25,h0);
		body[4][5][8]=addBPoint(0.0,0.481,0.25,h0);
		body[5][5][8]=addBPoint(0.125,0.481,0.25,h0);
		body[6][5][8]=addBPoint(0.25,0.481,0.7,h0);
		body[7][5][8]=addBPoint(0.375,0.57,1.0,h0);
		body[8][5][8]=addBPoint(0.5,0.463,1.0,h0);

		body[0][6][0]=addBPoint(-0.29,0.579,0.0,h0);
		body[1][6][0]=addBPoint(-0.217,0.654,0.0,h0);
		body[2][6][0]=addBPoint(-0.145,0.677,0.0,h0);
		body[3][6][0]=addBPoint(-0.073,0.688,0.0,h0);
		body[4][6][0]=addBPoint(0.0,0.692,0.0,h0);
		body[5][6][0]=addBPoint(0.073,0.688,0.0,h0);
		body[6][6][0]=addBPoint(0.145,0.677,0.0,h0);
		body[7][6][0]=addBPoint(0.217,0.654,0.0,h0);
		body[8][6][0]=addBPoint(0.29,0.579,0.0,h0);
		body[0][6][1]=addBPoint(-0.247,0.572,0.248,h0);
		body[8][6][1]=addBPoint(0.247,0.572,0.248,h0);
		body[0][6][2]=addBPoint(-0.228,0.557,0.416,h0);
		body[8][6][2]=addBPoint(0.228,0.557,0.416,h0);
		body[0][6][3]=addBPoint(-0.216,0.554,0.478,h0);
		body[8][6][3]=addBPoint(0.216,0.554,0.478,h0);
		body[0][6][4]=addBPoint(-0.302,0.57,0.565,h0);
		body[8][6][4]=addBPoint(0.302,0.57,0.565,h0);
		body[0][6][5]=addBPoint(-0.401,0.565,0.671,h0);
		body[8][6][5]=addBPoint(0.401,0.565,0.671,h0);
		body[0][6][6]=addBPoint(-0.451,0.558,0.783,h0);
		body[8][6][6]=addBPoint(0.451,0.558,0.783,h0);
		body[0][6][7]=addBPoint(-0.481,0.55,0.907,h0);
		body[8][6][7]=addBPoint(0.481,0.55,0.907,h0);
		body[0][6][8]=addBPoint(-0.5,0.555,1.0,h0);
		body[1][6][8]=addBPoint(-0.375,0.684,1.0,h0);
		body[2][6][8]=addBPoint(-0.25,0.554,0.75,h0);
		body[3][6][8]=addBPoint(-0.125,0.554,0.75,h0);
		body[4][6][8]=addBPoint(0.0,0.554,0.75,h0);
		body[5][6][8]=addBPoint(0.125,0.554,0.75,h0);
		body[6][6][8]=addBPoint(0.25,0.554,0.75,h0);
		body[7][6][8]=addBPoint(0.375,0.684,1.0,h0);
		body[8][6][8]=addBPoint(0.5,0.555,1.0,h0);

		body[0][7][0]=addBPoint(-0.29,0.642,0.0,h0);
		body[1][7][0]=addBPoint(-0.217,0.73,0.0,h0);
		body[2][7][0]=addBPoint(-0.145,0.756,0.0,h0);
		body[3][7][0]=addBPoint(-0.073,0.77,0.0,h0);
		body[4][7][0]=addBPoint(0.0,0.774,0.0,h0);
		body[5][7][0]=addBPoint(0.073,0.77,0.0,h0);
		body[6][7][0]=addBPoint(0.145,0.756,0.0,h0);
		body[7][7][0]=addBPoint(0.217,0.73,0.0,h0);
		body[8][7][0]=addBPoint(0.29,0.642,0.0,h0);
		body[0][7][1]=addBPoint(-0.247,0.637,0.248,h0);
		body[8][7][1]=addBPoint(0.247,0.637,0.248,h0);
		body[0][7][2]=addBPoint(-0.228,0.627,0.416,h0);
		body[8][7][2]=addBPoint(0.228,0.627,0.416,h0);
		body[0][7][3]=addBPoint(-0.216,0.627,0.478,h0);
		body[8][7][3]=addBPoint(0.216,0.627,0.478,h0);
		body[0][7][4]=addBPoint(-0.302,0.649,0.565,h0);
		body[8][7][4]=addBPoint(0.302,0.649,0.565,h0);
		body[0][7][5]=addBPoint(-0.401,0.646,0.671,h0);
		body[8][7][5]=addBPoint(0.401,0.646,0.671,h0);
		body[0][7][6]=addBPoint(-0.451,0.642,0.783,h0);
		body[8][7][6]=addBPoint(0.451,0.642,0.783,h0);
		body[0][7][7]=addBPoint(-0.481,0.637,0.907,h0);
		body[8][7][7]=addBPoint(0.481,0.637,0.907,h0);
		body[0][7][8]=addBPoint(-0.5,0.648,1.0,h0);
		body[1][7][8]=addBPoint(-0.375,0.798,1.0,h0);
		body[2][7][8]=addBPoint(-0.25,0.845,1.0,h0);
		body[3][7][8]=addBPoint(-0.125,0.868,1.0,h0);
		body[4][7][8]=addBPoint(0.0,0.875,1.0,h0);
		body[5][7][8]=addBPoint(0.125,0.868,1.0,h0);
		body[6][7][8]=addBPoint(0.25,0.845,1.0,h0);
		body[7][7][8]=addBPoint(0.375,0.798,1.0,h0);
		body[8][7][8]=addBPoint(0.5,0.648,1.0,h0);

		body[0][8][0]=addBPoint(-0.29,0.705,0.0,h0);
		body[1][8][0]=addBPoint(-0.217,0.805,0.0,h0);
		body[2][8][0]=addBPoint(-0.145,0.836,0.0,h0);
		body[3][8][0]=addBPoint(-0.073,0.851,0.0,h0);
		body[4][8][0]=addBPoint(0.0,0.856,0.0,h0);
		body[5][8][0]=addBPoint(0.073,0.851,0.0,h0);
		body[6][8][0]=addBPoint(0.145,0.836,0.0,h0);
		body[7][8][0]=addBPoint(0.217,0.805,0.0,h0);
		body[8][8][0]=addBPoint(0.29,0.705,0.0,h0);
		body[0][8][1]=addBPoint(-0.247,0.702,0.248,h0);
		body[1][8][1]=addBPoint(-0.185,0.787,0.248,h0);
		body[2][8][1]=addBPoint(-0.123,0.813,0.248,h0);
		body[3][8][1]=addBPoint(-0.062,0.826,0.248,h0);
		body[4][8][1]=addBPoint(0.0,0.83,0.248,h0);
		body[5][8][1]=addBPoint(0.062,0.826,0.248,h0);
		body[6][8][1]=addBPoint(0.123,0.813,0.248,h0);
		body[7][8][1]=addBPoint(0.185,0.787,0.248,h0);
		body[8][8][1]=addBPoint(0.247,0.702,0.248,h0);
		body[0][8][2]=addBPoint(-0.228,0.697,0.416,h0);
		body[1][8][2]=addBPoint(-0.171,0.776,0.416,h0);
		body[2][8][2]=addBPoint(-0.114,0.8,0.416,h0);
		body[3][8][2]=addBPoint(-0.057,0.812,0.416,h0);
		body[4][8][2]=addBPoint(0.0,0.816,0.416,h0);
		body[5][8][2]=addBPoint(0.057,0.812,0.416,h0);
		body[6][8][2]=addBPoint(0.114,0.8,0.416,h0);
		body[7][8][2]=addBPoint(0.171,0.776,0.416,h0);
		body[8][8][2]=addBPoint(0.228,0.697,0.416,h0);
		body[0][8][3]=addBPoint(-0.216,0.7,0.478,h0);
		body[1][8][3]=addBPoint(-0.162,0.774,0.478,h0);
		body[2][8][3]=addBPoint(-0.108,0.797,0.478,h0);
		body[3][8][3]=addBPoint(-0.054,0.808,0.478,h0);
		body[4][8][3]=addBPoint(0.0,0.812,0.478,h0);
		body[5][8][3]=addBPoint(0.054,0.808,0.478,h0);
		body[6][8][3]=addBPoint(0.108,0.797,0.478,h0);
		body[7][8][3]=addBPoint(0.162,0.774,0.478,h0);
		body[8][8][3]=addBPoint(0.216,0.7,0.478,h0);
		body[0][8][4]=addBPoint(-0.302,0.727,0.565,h0);
		body[1][8][4]=addBPoint(-0.226,0.831,0.565,h0);
		body[2][8][4]=addBPoint(-0.151,0.863,0.565,h0);
		body[3][8][4]=addBPoint(-0.076,0.879,0.565,h0);
		body[4][8][4]=addBPoint(0.0,0.884,0.565,h0);
		body[5][8][4]=addBPoint(0.076,0.879,0.565,h0);
		body[6][8][4]=addBPoint(0.151,0.863,0.565,h0);
		body[7][8][4]=addBPoint(0.226,0.831,0.565,h0);
		body[8][8][4]=addBPoint(0.302,0.727,0.565,h0);
		body[0][8][5]=addBPoint(-0.401,0.726,0.671,h0);
		body[1][8][5]=addBPoint(-0.301,0.864,0.671,h0);
		body[2][8][5]=addBPoint(-0.2,0.907,0.671,h0);
		body[3][8][5]=addBPoint(-0.1,0.928,0.671,h0);
		body[4][8][5]=addBPoint(0.0,0.935,0.671,h0);
		body[5][8][5]=addBPoint(0.1,0.928,0.671,h0);
		body[6][8][5]=addBPoint(0.2,0.907,0.671,h0);
		body[7][8][5]=addBPoint(0.301,0.864,0.671,h0);
		body[8][8][5]=addBPoint(0.401,0.726,0.671,h0);
		body[0][8][6]=addBPoint(-0.451,0.725,0.783,h0);
		body[1][8][6]=addBPoint(-0.338,0.881,0.783,h0);
		body[2][8][6]=addBPoint(-0.225,0.929,0.783,h0);
		body[3][8][6]=addBPoint(-0.113,0.953,0.783,h0);
		body[4][8][6]=addBPoint(0.0,0.96,0.783,h0);
		body[5][8][6]=addBPoint(0.113,0.953,0.783,h0);
		body[6][8][6]=addBPoint(0.225,0.929,0.783,h0);
		body[7][8][6]=addBPoint(0.338,0.881,0.783,h0);
		body[8][8][6]=addBPoint(0.451,0.725,0.783,h0);
		body[0][8][7]=addBPoint(-0.481,0.725,0.907,h0);
		body[1][8][7]=addBPoint(-0.361,0.89,0.907,h0);
		body[2][8][7]=addBPoint(-0.24,0.942,0.907,h0);
		body[3][8][7]=addBPoint(-0.12,0.967,0.907,h0);
		body[4][8][7]=addBPoint(0.0,0.975,0.907,h0);
		body[5][8][7]=addBPoint(0.12,0.967,0.907,h0);
		body[6][8][7]=addBPoint(0.24,0.942,0.907,h0);
		body[7][8][7]=addBPoint(0.361,0.89,0.907,h0);
		body[8][8][7]=addBPoint(0.481,0.725,0.907,h0);
		body[0][8][8]=addBPoint(-0.5,0.74,1.0,h0);
		body[1][8][8]=addBPoint(-0.375,0.912,1.0,h0);
		body[2][8][8]=addBPoint(-0.25,0.965,1.0,h0);
		body[3][8][8]=addBPoint(-0.125,0.992,1.0,h0);
		body[4][8][8]=addBPoint(0.0,1.0,1.0,h0);
		body[5][8][8]=addBPoint(0.125,0.992,1.0,h0);
		body[6][8][8]=addBPoint(0.25,0.965,1.0,h0);
		body[7][8][8]=addBPoint(0.375,0.912,1.0,h0);
		body[8][8][8]=addBPoint(0.5,0.74,1.0,h0);

		//**********
		

		for (int i = 0;  i < hnx-1; i++) {			

		

			for (int j = 0; j < hny-1; j++) {
				
		
				for (int k = 0; k < hnz-1; k++) {			
					
					//if(j!=5) continue;

					if(j==0){


						addLine(body[i][j][k],body[i+1][j][k],body[i+1][j][k+1],body[i][j][k+1],Renderer3D.CAR_BACK);
					}



                   // System.out.println(i+" "+j+" "+k); 

					if(k+1==hnz-1)
						addLine(body[i][j][k+1],body[i+1][j][k+1],body[i+1][j+1][k+1],body[i][j+1][k+1],Renderer3D.CAR_TOP);

					if(k==0)
						addLine(body[i][j][k],body[i][j+1][k],body[i+1][j+1][k],body[i+1][j][k],Renderer3D.CAR_BOTTOM);

					if(i==0)
						addLine(body[i][j][k],body[i][j][k+1],body[i][j+1][k+1],body[i][j+1][k],Renderer3D.CAR_LEFT);

					if(i+1==hnx-1)
						addLine(body[i+1][j][k],body[i+1][j+1][k],body[i+1][j+1][k+1],body[i+1][j][k+1],Renderer3D.CAR_RIGHT);


					if(j+1==hny-1){

						addLine(body[i][j+1][k],body[i][j+1][k+1],body[i+1][j+1][k+1],body[i+1][j+1][k],Renderer3D.CAR_FRONT);

					}

				}

			}


		}
		
		
	

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildWardrobeMesh() {

		points=new Vector();
		points.setSize(50);

		polyData=new Vector();
		
		n=0;
		
	

		//basic sides:
		buildBox(0,0,0,x_side,y_side,z_side);
	
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildSofaMesh() {


		points=new Vector();
		points.setSize(100);

		polyData=new Vector();
		
		n=0;
		
		
		Segments b0=new Segments(0,x_side,0,y_side,leg_length,z_side);
		
		BPoint[][][] body=new BPoint[2][2][2];
		
		body[0][0][0]=addBPoint(0,0,0,b0);
		body[1][0][0]=addBPoint(1.0,0,0,b0);
		body[1][1][0]=addBPoint(1.0,1.0,0,b0);
		body[0][1][0]=addBPoint(0,1.0,0,b0);
		
		body[0][0][1]=addBPoint(0,0,1.0,b0);
		body[1][0][1]=addBPoint(1.0,0,1.0,b0);
		body[1][1][1]=addBPoint(1.0,1.0,1.0,b0);
		body[0][1][1]=addBPoint(0,1.0,1.0,b0);
		
		addLine(body[0][0][1],body[1][0][1],body[1][1][1],body[0][1][1],Renderer3D.CAR_TOP);		

		addLine(body[0][0][0],body[0][0][1],body[0][1][1],body[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(body[1][0][0],body[1][1][0],body[1][1][1],body[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(body[0][1][0],body[0][1][1],body[1][1][1],body[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1],Renderer3D.CAR_BACK);
		
		addLine(body[0][0][0],body[0][1][0],body[1][1][0],body[1][0][0],Renderer3D.CAR_BOTTOM);
		
				
		/// back left leg
		
		Segments balLeg=new Segments(0,leg_side,0,leg_side,0,leg_length);
		
		BPoint[][][] blLeg=new BPoint[2][2][2];
				
		blLeg[0][0][0]=addBPoint(0,0,0,balLeg);
		blLeg[1][0][0]=addBPoint(1,0,0,balLeg);
		blLeg[1][1][0]=addBPoint(1,1,0,balLeg);
		blLeg[0][1][0]=addBPoint(0,1,0,balLeg);
		
	
		addLine(blLeg[0][0][0],blLeg[0][1][0],blLeg[1][1][0],blLeg[1][0][0],Renderer3D.CAR_BOTTOM);
		
		
		blLeg[0][0][1]=addBPoint(0,0,1,balLeg);
		blLeg[1][0][1]=addBPoint(1,0,1,balLeg);
		blLeg[1][1][1]=addBPoint(1,1,1,balLeg);
		blLeg[0][1][1]=addBPoint(0,1,1,balLeg);
		
		addLine(blLeg[0][0][0],blLeg[0][0][1],blLeg[0][1][1],blLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(blLeg[0][1][0],blLeg[0][1][1],blLeg[1][1][1],blLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(blLeg[1][1][0],blLeg[1][1][1],blLeg[1][0][1],blLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(blLeg[1][0][0],blLeg[1][0][1],blLeg[0][0][1],blLeg[0][0][0],Renderer3D.CAR_BACK);
		
		/// back right leg
		
		Segments barLeg=new Segments(x_side-leg_side,leg_side,0,leg_side,0,leg_length);
		
		BPoint[][][] brLeg=new BPoint[2][2][2];
		
		brLeg[0][0][0]=addBPoint(0,0,0,barLeg);
		brLeg[1][0][0]=addBPoint(1,0,0,barLeg);
		brLeg[1][1][0]=addBPoint(1,1,0,barLeg);
		brLeg[0][1][0]=addBPoint(0,1,0,barLeg);
		
	
		addLine(brLeg[0][0][0],brLeg[0][1][0],brLeg[1][1][0],brLeg[1][0][0],Renderer3D.CAR_BOTTOM);

		
		
		brLeg[0][0][1]=addBPoint(0,0,1,barLeg);
		brLeg[1][0][1]=addBPoint(1,0,1,barLeg);
		brLeg[1][1][1]=addBPoint(1,1,1,barLeg);
		brLeg[0][1][1]=addBPoint(0,1,1,barLeg);
		
		addLine(brLeg[0][0][0],brLeg[0][0][1],brLeg[0][1][1],brLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(brLeg[0][1][0],brLeg[0][1][1],brLeg[1][1][1],brLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(brLeg[1][1][0],brLeg[1][1][1],brLeg[1][0][1],brLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(brLeg[1][0][0],brLeg[1][0][1],brLeg[0][0][1],brLeg[0][0][0],Renderer3D.CAR_BACK);
		
		/// front left leg
		
		Segments frlLeg=new Segments(0,leg_side,y_side-leg_side,leg_side,0,leg_length);
		
		BPoint[][][] flLeg=new BPoint[2][2][2];
		
		flLeg[0][0][0]=addBPoint(0,0,0,frlLeg);
		flLeg[1][0][0]=addBPoint(1,0,0,frlLeg);
		flLeg[1][1][0]=addBPoint(1,1,0,frlLeg);
		flLeg[0][1][0]=addBPoint(0,1,0,frlLeg);
		
	
		addLine(flLeg[0][0][0],flLeg[0][1][0],flLeg[1][1][0],flLeg[1][0][0],Renderer3D.CAR_BOTTOM);
		
		
		flLeg[0][0][1]=addBPoint(0,0,1,frlLeg);
		flLeg[1][0][1]=addBPoint(1,0,1,frlLeg);
		flLeg[1][1][1]=addBPoint(1,1,1,frlLeg);
		flLeg[0][1][1]=addBPoint(0,1,1,frlLeg);
		
		addLine(flLeg[0][0][0],flLeg[0][0][1],flLeg[0][1][1],flLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(flLeg[0][1][0],flLeg[0][1][1],flLeg[1][1][1],flLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(flLeg[1][1][0],flLeg[1][1][1],flLeg[1][0][1],flLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(flLeg[1][0][0],flLeg[1][0][1],flLeg[0][0][1],flLeg[0][0][0],Renderer3D.CAR_BACK);
		
		/// front right leg
		
		Segments frrLeg=new Segments(x_side-leg_side,leg_side,y_side-leg_side,leg_side,0,leg_length);
		
		BPoint[][][] frLeg=new BPoint[2][2][2];
		
		frLeg[0][0][0]=addBPoint(0,0,0,frrLeg);
		frLeg[1][0][0]=addBPoint(1,0,0,frrLeg);
		frLeg[1][1][0]=addBPoint(1,1,0,frrLeg);
		frLeg[0][1][0]=addBPoint(0,1,0,frrLeg);
		
	
		addLine(frLeg[0][0][0],frLeg[0][1][0],frLeg[1][1][0],frLeg[1][0][0],Renderer3D.CAR_BOTTOM);
		
		frLeg[0][0][1]=addBPoint(0,0,1,frrLeg);
		frLeg[1][0][1]=addBPoint(1,0,1,frrLeg);
		frLeg[1][1][1]=addBPoint(1,1,1,frrLeg);
		frLeg[0][1][1]=addBPoint(0,1,1,frrLeg);
		
		addLine(frLeg[0][0][0],frLeg[0][0][1],frLeg[0][1][1],frLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(frLeg[0][1][0],frLeg[0][1][1],frLeg[1][1][1],frLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(frLeg[1][1][0],frLeg[1][1][1],frLeg[1][0][1],frLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(frLeg[1][0][0],frLeg[1][0][1],frLeg[0][0][1],frLeg[0][0][0],Renderer3D.CAR_BACK);

				
		//chair back: 
		Segments sofa_back=new Segments(0,x_side,0,leg_side,leg_length+z_side,back_height);
		
		BPoint[][][] back0=new BPoint[2][2][2];
		
		back0[0][0][0]=addBPoint(0,0,0,sofa_back);
		back0[1][0][0]=addBPoint(1,0,0,sofa_back);
		back0[1][1][0]=addBPoint(1,1,0,sofa_back);
		back0[0][1][0]=addBPoint(0,1,0,sofa_back);
		
	
		addLine(back0[0][0][0],back0[0][1][0],back0[1][1][0],back0[1][0][0],Renderer3D.CAR_BOTTOM);

		
		back0[0][0][1]=addBPoint(0,0,1,sofa_back);
		back0[1][0][1]=addBPoint(1,0,1,sofa_back);
		back0[1][1][1]=addBPoint(1,1,1,sofa_back);
		back0[0][1][1]=addBPoint(0,1,1,sofa_back);
		
		
		addLine(back0[0][0][1],back0[1][0][1],back0[1][1][1],back0[0][1][1],Renderer3D.CAR_TOP);
		
		addLine(back0[0][0][0],back0[0][0][1],back0[0][1][1],back0[0][1][0],Renderer3D.CAR_LEFT);

		addLine(back0[0][1][0],back0[0][1][1],back0[1][1][1],back0[1][1][0],Renderer3D.CAR_FRONT);

		addLine(back0[1][1][0],back0[1][1][1],back0[1][0][1],back0[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(back0[1][0][0],back0[1][0][1],back0[0][0][1],back0[0][0][0],Renderer3D.CAR_BACK);

		
		
		//left_side: 
		Segments leftSide=new Segments(0,side_width,leg_side,side_length,leg_length+z_side,side_height);
		
		BPoint[][][] left_side=new BPoint[2][2][2];
		
		left_side[0][0][0]=addBPoint(0,0,0,leftSide);
		left_side[1][0][0]=addBPoint(1,0,0,leftSide);
		left_side[1][1][0]=addBPoint(1,1,0,leftSide);
		left_side[0][1][0]=addBPoint(0,1,0,leftSide);
		
	
		addLine(left_side[0][0][0],left_side[0][1][0],left_side[1][1][0],left_side[1][0][0],Renderer3D.CAR_BOTTOM);

		
		left_side[0][0][1]=addBPoint(0,0,1,leftSide);
		left_side[1][0][1]=addBPoint(1,0,1,leftSide);
		left_side[1][1][1]=addBPoint(1,1,1,leftSide);
		left_side[0][1][1]=addBPoint(0,1,1,leftSide);
		
		
		addLine(left_side[0][0][1],left_side[1][0][1],left_side[1][1][1],left_side[0][1][1],Renderer3D.CAR_TOP);
		
		addLine(left_side[0][0][0],left_side[0][0][1],left_side[0][1][1],left_side[0][1][0],Renderer3D.CAR_LEFT);

		addLine(left_side[0][1][0],left_side[0][1][1],left_side[1][1][1],left_side[1][1][0],Renderer3D.CAR_FRONT);

		addLine(left_side[1][1][0],left_side[1][1][1],left_side[1][0][1],left_side[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(left_side[1][0][0],left_side[1][0][1],left_side[0][0][1],left_side[0][0][0],Renderer3D.CAR_BACK);
		
		
		//right_side: 
		Segments rightSide=new Segments(x_side-side_width,side_width,leg_side,side_length,leg_length+z_side,side_height);
		
		BPoint[][][] right_side=new BPoint[2][2][2];
		
		right_side[0][0][0]=addBPoint(0,0,0,rightSide);
		right_side[1][0][0]=addBPoint(1,0,0,rightSide);
		right_side[1][1][0]=addBPoint(1,1,0,rightSide);
		right_side[0][1][0]=addBPoint(0,1,0,rightSide);
		
	
		addLine(right_side[0][0][0],right_side[0][1][0],right_side[1][1][0],right_side[1][0][0],Renderer3D.CAR_BOTTOM);

		
		right_side[0][0][1]=addBPoint(0,0,1,rightSide);
		right_side[1][0][1]=addBPoint(1,0,1,rightSide);
		right_side[1][1][1]=addBPoint(1,1,1,rightSide);
		right_side[0][1][1]=addBPoint(0,1,1,rightSide);
		
		
		addLine(right_side[0][0][1],right_side[1][0][1],right_side[1][1][1],right_side[0][1][1],Renderer3D.CAR_TOP);
		
		addLine(right_side[0][0][0],right_side[0][0][1],right_side[0][1][1],right_side[0][1][0],Renderer3D.CAR_LEFT);

		addLine(right_side[0][1][0],right_side[0][1][1],right_side[1][1][1],right_side[1][1][0],Renderer3D.CAR_FRONT);

		addLine(right_side[1][1][0],right_side[1][1][1],right_side[1][0][1],right_side[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(right_side[1][0][0],right_side[1][0][1],right_side[0][0][1],right_side[0][0][0],Renderer3D.CAR_BACK);

		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildBedMesh() {


		points=new Vector();
		points.setSize(100);

		polyData=new Vector();

		n=0;

		Segments b0=new Segments(0,x_side,0,y_side,leg_length,z_side);

		BPoint[][][] body=new BPoint[2][2][2];

		body[0][0][0]=addBPoint(0,0,0,b0);
		body[1][0][0]=addBPoint(1.0,0,0,b0);
		body[1][1][0]=addBPoint(1.0,1.0,0,b0);
		body[0][1][0]=addBPoint(0,1.0,0,b0);

		body[0][0][1]=addBPoint(0,0,1.0,b0);
		body[1][0][1]=addBPoint(1.0,0,1.0,b0);
		body[1][1][1]=addBPoint(1.0,1.0,1.0,b0);
		body[0][1][1]=addBPoint(0,1.0,1.0,b0);

		addLine(body[0][0][1],body[1][0][1],body[1][1][1],body[0][1][1],Renderer3D.CAR_TOP);		

		addLine(body[0][0][0],body[0][0][1],body[0][1][1],body[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(body[1][0][0],body[1][1][0],body[1][1][1],body[1][0][1],Renderer3D.CAR_RIGHT);

		addLine(body[0][1][0],body[0][1][1],body[1][1][1],body[1][1][0],Renderer3D.CAR_FRONT);

		addLine(body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1],Renderer3D.CAR_BACK);

		addLine(body[0][0][0],body[0][1][0],body[1][1][0],body[1][0][0],Renderer3D.CAR_BOTTOM);

		/// footboard

		Segments footEdge=new Segments(0,x_side,-leg_side,leg_side,leg_length,front_height);

		BPoint[][][] footboard=new BPoint[2][2][2];

		footboard[0][0][0]=addBPoint(0,0,0,footEdge);
		footboard[1][0][0]=addBPoint(1,0,0,footEdge);
		footboard[1][1][0]=addBPoint(1,1,0,footEdge);
		footboard[0][1][0]=addBPoint(0,1,0,footEdge);


		addLine(footboard[0][0][0],footboard[0][1][0],footboard[1][1][0],footboard[1][0][0],Renderer3D.CAR_BOTTOM);


		footboard[0][0][1]=addBPoint(0,0,1,footEdge);
		footboard[1][0][1]=addBPoint(1,0,1,footEdge);
		footboard[1][1][1]=addBPoint(1,1,1,footEdge);
		footboard[0][1][1]=addBPoint(0,1,1,footEdge);

		addLine(footboard[0][0][0],footboard[0][0][1],footboard[0][1][1],footboard[0][1][0],Renderer3D.CAR_LEFT);

		addLine(footboard[0][1][0],footboard[0][1][1],footboard[1][1][1],footboard[1][1][0],Renderer3D.CAR_FRONT);

		addLine(footboard[1][1][0],footboard[1][1][1],footboard[1][0][1],footboard[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(footboard[1][0][0],footboard[1][0][1],footboard[0][0][1],footboard[0][0][0],Renderer3D.CAR_BACK);

		addLine(footboard[0][0][1],footboard[1][0][1],footboard[1][1][1],footboard[0][1][1],Renderer3D.CAR_TOP);	




		/// back left leg

		Segments balLeg=new Segments(0,leg_side,-leg_side,leg_side,0,leg_length);

		BPoint[][][] blLeg=new BPoint[2][2][2];

		blLeg[0][0][0]=addBPoint(0,0,0,balLeg);
		blLeg[1][0][0]=addBPoint(1,0,0,balLeg);
		blLeg[1][1][0]=addBPoint(1,1,0,balLeg);
		blLeg[0][1][0]=addBPoint(0,1,0,balLeg);


		addLine(blLeg[0][0][0],blLeg[0][1][0],blLeg[1][1][0],blLeg[1][0][0],Renderer3D.CAR_BOTTOM);


		blLeg[0][0][1]=addBPoint(0,0,1,balLeg);
		blLeg[1][0][1]=addBPoint(1,0,1,balLeg);
		blLeg[1][1][1]=addBPoint(1,1,1,balLeg);
		blLeg[0][1][1]=addBPoint(0,1,1,balLeg);

		addLine(blLeg[0][0][0],blLeg[0][0][1],blLeg[0][1][1],blLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(blLeg[0][1][0],blLeg[0][1][1],blLeg[1][1][1],blLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(blLeg[1][1][0],blLeg[1][1][1],blLeg[1][0][1],blLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(blLeg[1][0][0],blLeg[1][0][1],blLeg[0][0][1],blLeg[0][0][0],Renderer3D.CAR_BACK);

		/// back right leg

		Segments barLeg=new Segments(x_side-leg_side,leg_side,-leg_side,leg_side,0,leg_length);

		BPoint[][][] brLeg=new BPoint[2][2][2];

		brLeg[0][0][0]=addBPoint(0,0,0,barLeg);
		brLeg[1][0][0]=addBPoint(1,0,0,barLeg);
		brLeg[1][1][0]=addBPoint(1,1,0,barLeg);
		brLeg[0][1][0]=addBPoint(0,1,0,barLeg);


		addLine(brLeg[0][0][0],brLeg[0][1][0],brLeg[1][1][0],brLeg[1][0][0],Renderer3D.CAR_BOTTOM);

		brLeg[0][0][1]=addBPoint(0,0,1,barLeg);
		brLeg[1][0][1]=addBPoint(1,0,1,barLeg);
		brLeg[1][1][1]=addBPoint(1,1,1,barLeg);
		brLeg[0][1][1]=addBPoint(0,1,1,barLeg);

		addLine(brLeg[0][0][0],brLeg[0][0][1],brLeg[0][1][1],brLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(brLeg[0][1][0],brLeg[0][1][1],brLeg[1][1][1],brLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(brLeg[1][1][0],brLeg[1][1][1],brLeg[1][0][1],brLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(brLeg[1][0][0],brLeg[1][0][1],brLeg[0][0][1],brLeg[0][0][0],Renderer3D.CAR_BACK);


		/// headboard

		Segments headEdge=new Segments(0,x_side,y_side,leg_side,leg_length,back_height);

		BPoint[][][] headboard=new BPoint[2][2][2];

		headboard[0][0][0]=addBPoint(0,0,0,headEdge);
		headboard[1][0][0]=addBPoint(1,0,0,headEdge);
		headboard[1][1][0]=addBPoint(1,1,0,headEdge);
		headboard[0][1][0]=addBPoint(0,1,0,headEdge);


		addLine(headboard[0][0][0],headboard[0][1][0],headboard[1][1][0],headboard[1][0][0],Renderer3D.CAR_BOTTOM);


		headboard[0][0][1]=addBPoint(0,0,1,headEdge);
		headboard[1][0][1]=addBPoint(1,0,1,headEdge);
		headboard[1][1][1]=addBPoint(1,1,1,headEdge);
		headboard[0][1][1]=addBPoint(0,1,1,headEdge);

		addLine(headboard[0][0][0],headboard[0][0][1],headboard[0][1][1],headboard[0][1][0],Renderer3D.CAR_LEFT);

		addLine(headboard[0][1][0],headboard[0][1][1],headboard[1][1][1],headboard[1][1][0],Renderer3D.CAR_FRONT);

		addLine(headboard[1][1][0],headboard[1][1][1],headboard[1][0][1],headboard[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(headboard[1][0][0],headboard[1][0][1],headboard[0][0][1],headboard[0][0][0],Renderer3D.CAR_BACK);

		addLine(headboard[0][0][1],headboard[1][0][1],headboard[1][1][1],headboard[0][1][1],Renderer3D.CAR_TOP);	


		/// front left leg

		Segments frlLeg=new Segments(0,leg_side,y_side,leg_side,0,leg_length);

		BPoint[][][] flLeg=new BPoint[2][2][2];

		flLeg[0][0][0]=addBPoint(0,0,0,frlLeg);
		flLeg[1][0][0]=addBPoint(1,0,0,frlLeg);
		flLeg[1][1][0]=addBPoint(1,1,0,frlLeg);
		flLeg[0][1][0]=addBPoint(0,1,0,frlLeg);


		addLine(flLeg[0][0][0],flLeg[0][1][0],flLeg[1][1][0],flLeg[1][0][0],Renderer3D.CAR_BOTTOM);


		flLeg[0][0][1]=addBPoint(0,0,1,frlLeg);
		flLeg[1][0][1]=addBPoint(1,0,1,frlLeg);
		flLeg[1][1][1]=addBPoint(1,1,1,frlLeg);
		flLeg[0][1][1]=addBPoint(0,1,1,frlLeg);

		addLine(flLeg[0][0][0],flLeg[0][0][1],flLeg[0][1][1],flLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(flLeg[0][1][0],flLeg[0][1][1],flLeg[1][1][1],flLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(flLeg[1][1][0],flLeg[1][1][1],flLeg[1][0][1],flLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(flLeg[1][0][0],flLeg[1][0][1],flLeg[0][0][1],flLeg[0][0][0],Renderer3D.CAR_BACK);

		/// front right leg

		Segments frrLeg=new Segments(x_side-leg_side,leg_side,y_side,leg_side,0,leg_length);

		BPoint[][][] frLeg=new BPoint[2][2][2];

		frLeg[0][0][0]=addBPoint(0,0,0,frrLeg);
		frLeg[1][0][0]=addBPoint(1,0,0,frrLeg);
		frLeg[1][1][0]=addBPoint(1,1,0,frrLeg);
		frLeg[0][1][0]=addBPoint(0,1,0,frrLeg);


		addLine(frLeg[0][0][0],frLeg[0][1][0],frLeg[1][1][0],frLeg[1][0][0],Renderer3D.CAR_BOTTOM);


		frLeg[0][0][1]=addBPoint(0,0,1,frrLeg);
		frLeg[1][0][1]=addBPoint(1,0,1,frrLeg);
		frLeg[1][1][1]=addBPoint(1,1,1,frrLeg);
		frLeg[0][1][1]=addBPoint(0,1,1,frrLeg);

		addLine(frLeg[0][0][0],frLeg[0][0][1],frLeg[0][1][1],frLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(frLeg[0][1][0],frLeg[0][1][1],frLeg[1][1][1],frLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(frLeg[1][1][0],frLeg[1][1][1],frLeg[1][0][1],frLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(frLeg[1][0][0],frLeg[1][0][1],frLeg[0][0][1],frLeg[0][0][0],Renderer3D.CAR_BACK);
		
		
		//pillow
		
		double pillow_width=x_side-10;
		double pillow_lenght=pillow_width*0.571;		
		double pillow_height=pillow_width*0.186;
		
		Segments p0=new Segments(x_side*0.5,pillow_width,y_side-pillow_lenght,pillow_lenght,leg_length+z_side,pillow_height);

		int pnumx=2;
		int pnumy=2;
		int pnumz=2;
		
		BPoint[][][] pillow=new BPoint[pnumx][pnumy][pnumz];

		pillow[0][0][0]=addBPoint(-0.5,0,0,p0);
		pillow[1][0][0]=addBPoint(0.5,0,0,p0);
		pillow[1][1][0]=addBPoint(0.5,1.0,0,p0);
		pillow[0][1][0]=addBPoint(-0.5,1.0,0,p0);

		pillow[0][0][1]=addBPoint(-0.5,0,1.0,p0);
		pillow[1][0][1]=addBPoint(0.5,0,1.0,p0);
		pillow[1][1][1]=addBPoint(0.5,1.0,1.0,p0);
		pillow[0][1][1]=addBPoint(-0.5,1.0,1.0,p0);

		for (int i = 0; i < pnumx-1; i++) {


			for (int j = 0; j < pnumy-1; j++) {	

				for (int k = 0; k < pnumz-1; k++) {


					if(i==0){

						LineData leftFrontLD=addLine(pillow[i][j][k],pillow[i][j][k+1],pillow[i][j+1][k+1],pillow[i][j+1][k],Renderer3D.CAR_LEFT);
					}



					if(k+1==pnumz-1){
						LineData topillowLD=addLine(pillow[i][j][k+1],pillow[i+1][j][k+1],pillow[i+1][j+1][k+1],pillow[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					if(k==0){
						LineData bottomFrontLD=addLine(pillow[i][j][k],pillow[i][j+1][k],pillow[i+1][j+1][k],pillow[i+1][j][k],Renderer3D.CAR_BOTTOM);
					}
					
					if(j==0){
						LineData backFrontLD=addLine(pillow[i][j][k],pillow[i+1][j][k],pillow[i+1][j][k+1],pillow[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==pnumy-1){
						LineData frontFrontLD=addLine(pillow[i][j+1][k],pillow[i][j+1][k+1],pillow[i+1][j+1][k+1],pillow[i+1][j+1][k],Renderer3D.CAR_FRONT);
					}


					if(i+1==pnumx-1){

						LineData rightFrontLD=addLine(pillow[i+1][j][k],pillow[i+1][j+1][k],pillow[i+1][j+1][k+1],pillow[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}

				}

			}

		}
		

		/////////


		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildChairMesh() {


		points=new Vector();
		points.setSize(50);

		polyData=new Vector();
		
		n=0;
		
		
		Segments b0=new Segments(0,x_side,0,y_side,leg_length,z_side);
		
		BPoint[][][] body=new BPoint[2][2][2];
		
		body[0][0][0]=addBPoint(0,0,0,b0);
		body[1][0][0]=addBPoint(1.0,0,0,b0);
		body[1][1][0]=addBPoint(1.0,1.0,0,b0);
		body[0][1][0]=addBPoint(0,1.0,0,b0);
		
		body[0][0][1]=addBPoint(0,0,1.0,b0);
		body[1][0][1]=addBPoint(1.0,0,1.0,b0);
		body[1][1][1]=addBPoint(1.0,1.0,1.0,b0);
		body[0][1][1]=addBPoint(0,1.0,1.0,b0);
		
		addLine(body[0][0][1],body[1][0][1],body[1][1][1],body[0][1][1],Renderer3D.CAR_TOP);		

		addLine(body[0][0][0],body[0][0][1],body[0][1][1],body[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(body[1][0][0],body[1][1][0],body[1][1][1],body[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(body[0][1][0],body[0][1][1],body[1][1][1],body[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1],Renderer3D.CAR_BACK);
		
		addLine(body[0][0][0],body[0][1][0],body[1][1][0],body[1][0][0],Renderer3D.CAR_BOTTOM);
		
				
		/// back left leg
		
		Segments balLeg=new Segments(0,leg_side,0,leg_side,0,leg_length);
		
		BPoint[][][] blLeg=new BPoint[2][2][2];
				
		blLeg[0][0][0]=addBPoint(0,0,0,balLeg);
		blLeg[1][0][0]=addBPoint(1,0,0,balLeg);
		blLeg[1][1][0]=addBPoint(1,1,0,balLeg);
		blLeg[0][1][0]=addBPoint(0,1,0,balLeg);
		
	
		addLine(blLeg[0][0][0],blLeg[0][1][0],blLeg[1][1][0],blLeg[1][0][0],Renderer3D.CAR_BOTTOM);
		
		
		blLeg[0][0][1]=addBPoint(0,0,1,balLeg);
		blLeg[1][0][1]=addBPoint(1,0,1,balLeg);
		blLeg[1][1][1]=addBPoint(1,1,1,balLeg);
		blLeg[0][1][1]=addBPoint(0,1,1,balLeg);
		
		addLine(blLeg[0][0][0],blLeg[0][0][1],blLeg[0][1][1],blLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(blLeg[0][1][0],blLeg[0][1][1],blLeg[1][1][1],blLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(blLeg[1][1][0],blLeg[1][1][1],blLeg[1][0][1],blLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(blLeg[1][0][0],blLeg[1][0][1],blLeg[0][0][1],blLeg[0][0][0],Renderer3D.CAR_BACK);
		
		/// back right leg
		
		Segments barLeg=new Segments(x_side-leg_side,leg_side,0,leg_side,0,leg_length);
		
		BPoint[][][] brLeg=new BPoint[2][2][2];
		
		brLeg[0][0][0]=addBPoint(0,0,0,barLeg);
		brLeg[1][0][0]=addBPoint(1,0,0,barLeg);
		brLeg[1][1][0]=addBPoint(1,1,0,barLeg);
		brLeg[0][1][0]=addBPoint(0,1,0,barLeg);
		
	
		addLine(brLeg[0][0][0],brLeg[0][1][0],brLeg[1][1][0],brLeg[1][0][0],Renderer3D.CAR_BOTTOM);

		
		
		brLeg[0][0][1]=addBPoint(0,0,1,barLeg);
		brLeg[1][0][1]=addBPoint(1,0,1,barLeg);
		brLeg[1][1][1]=addBPoint(1,1,1,barLeg);
		brLeg[0][1][1]=addBPoint(0,1,1,barLeg);
		
		addLine(brLeg[0][0][0],brLeg[0][0][1],brLeg[0][1][1],brLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(brLeg[0][1][0],brLeg[0][1][1],brLeg[1][1][1],brLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(brLeg[1][1][0],brLeg[1][1][1],brLeg[1][0][1],brLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(brLeg[1][0][0],brLeg[1][0][1],brLeg[0][0][1],brLeg[0][0][0],Renderer3D.CAR_BACK);
		
		/// front left leg
		
		Segments frlLeg=new Segments(0,leg_side,y_side-leg_side,leg_side,0,leg_length);
		
		BPoint[][][] flLeg=new BPoint[2][2][2];
		
		flLeg[0][0][0]=addBPoint(0,0,0,frlLeg);
		flLeg[1][0][0]=addBPoint(1,0,0,frlLeg);
		flLeg[1][1][0]=addBPoint(1,1,0,frlLeg);
		flLeg[0][1][0]=addBPoint(0,1,0,frlLeg);
		
	
		addLine(flLeg[0][0][0],flLeg[0][1][0],flLeg[1][1][0],flLeg[1][0][0],Renderer3D.CAR_BOTTOM);
		
		
		flLeg[0][0][1]=addBPoint(0,0,1,frlLeg);
		flLeg[1][0][1]=addBPoint(1,0,1,frlLeg);
		flLeg[1][1][1]=addBPoint(1,1,1,frlLeg);
		flLeg[0][1][1]=addBPoint(0,1,1,frlLeg);
		
		addLine(flLeg[0][0][0],flLeg[0][0][1],flLeg[0][1][1],flLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(flLeg[0][1][0],flLeg[0][1][1],flLeg[1][1][1],flLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(flLeg[1][1][0],flLeg[1][1][1],flLeg[1][0][1],flLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(flLeg[1][0][0],flLeg[1][0][1],flLeg[0][0][1],flLeg[0][0][0],Renderer3D.CAR_BACK);
		
		/// front right leg
		
		Segments frrLeg=new Segments(x_side-leg_side,leg_side,y_side-leg_side,leg_side,0,leg_length);
		
		BPoint[][][] frLeg=new BPoint[2][2][2];
		
		frLeg[0][0][0]=addBPoint(0,0,0,frrLeg);
		frLeg[1][0][0]=addBPoint(1,0,0,frrLeg);
		frLeg[1][1][0]=addBPoint(1,1,0,frrLeg);
		frLeg[0][1][0]=addBPoint(0,1,0,frrLeg);
		
	
		addLine(frLeg[0][0][0],frLeg[0][1][0],frLeg[1][1][0],frLeg[1][0][0],Renderer3D.CAR_BOTTOM);
		
		frLeg[0][0][1]=addBPoint(0,0,1,frrLeg);
		frLeg[1][0][1]=addBPoint(1,0,1,frrLeg);
		frLeg[1][1][1]=addBPoint(1,1,1,frrLeg);
		frLeg[0][1][1]=addBPoint(0,1,1,frrLeg);
		
		addLine(frLeg[0][0][0],frLeg[0][0][1],frLeg[0][1][1],frLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(frLeg[0][1][0],frLeg[0][1][1],frLeg[1][1][1],frLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(frLeg[1][1][0],frLeg[1][1][1],frLeg[1][0][1],frLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(frLeg[1][0][0],frLeg[1][0][1],frLeg[0][0][1],frLeg[0][0][0],Renderer3D.CAR_BACK);

				
		//chair back:
		Segments chair_back=new Segments(0,x_side,0,leg_side,leg_length+z_side,back_height);
		
		BPoint[][][] back0=new BPoint[2][2][2];
		
		back0[0][0][0]=addBPoint(0,0,0,chair_back);
		back0[1][0][0]=addBPoint(1,0,0,chair_back);
		back0[1][1][0]=addBPoint(1,1,0,chair_back);
		back0[0][1][0]=addBPoint(0,1,0,chair_back);
		
	
		addLine(back0[0][0][0],back0[0][1][0],back0[1][1][0],back0[1][0][0],Renderer3D.CAR_BOTTOM);

		
		back0[0][0][1]=addBPoint(0,0,1,chair_back);
		back0[1][0][1]=addBPoint(1,0,1,chair_back);
		back0[1][1][1]=addBPoint(1,1,1,chair_back);
		back0[0][1][1]=addBPoint(0,1,1,chair_back);
		
		
		addLine(back0[0][0][1],back0[1][0][1],back0[1][1][1],back0[0][1][1],Renderer3D.CAR_TOP);
		
		addLine(back0[0][0][0],back0[0][0][1],back0[0][1][1],back0[0][1][0],Renderer3D.CAR_LEFT);

		addLine(back0[0][1][0],back0[0][1][1],back0[1][1][1],back0[1][1][0],Renderer3D.CAR_FRONT);

		addLine(back0[1][1][0],back0[1][1][1],back0[1][0][1],back0[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(back0[1][0][0],back0[1][0][1],back0[0][0][1],back0[0][0][0],Renderer3D.CAR_BACK);

		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildTableMesh() {


		points=new Vector();
		points.setSize(50);

		polyData=new Vector();
		
		n=0;
		
		Segments b0=new Segments(0,x_side,0,y_side,leg_length,z_side);
		
		BPoint[][][] body=new BPoint[2][2][2];
		
		body[0][0][0]=addBPoint(0,0,0,b0);
		body[1][0][0]=addBPoint(1.0,0,0,b0);
		body[1][1][0]=addBPoint(1.0,1.0,0,b0);
		body[0][1][0]=addBPoint(0,1.0,0,b0);
		
		body[0][0][1]=addBPoint(0,0,1.0,b0);
		body[1][0][1]=addBPoint(1.0,0,1.0,b0);
		body[1][1][1]=addBPoint(1.0,1.0,1.0,b0);
		body[0][1][1]=addBPoint(0,1.0,1.0,b0);
		
		addLine(body[0][0][1],body[1][0][1],body[1][1][1],body[0][1][1],Renderer3D.CAR_TOP);		

		addLine(body[0][0][0],body[0][0][1],body[0][1][1],body[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(body[1][0][0],body[1][1][0],body[1][1][1],body[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(body[0][1][0],body[0][1][1],body[1][1][1],body[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1],Renderer3D.CAR_BACK);
		
		addLine(body[0][0][0],body[0][1][0],body[1][1][0],body[1][0][0],Renderer3D.CAR_BOTTOM);
		
				
		/// back left leg
		
		Segments balLeg=new Segments(0,leg_side,0,leg_side,0,leg_length);
		
		BPoint[][][] blLeg=new BPoint[2][2][2];
				
		blLeg[0][0][0]=addBPoint(0,0,0,balLeg);
		blLeg[1][0][0]=addBPoint(1,0,0,balLeg);
		blLeg[1][1][0]=addBPoint(1,1,0,balLeg);
		blLeg[0][1][0]=addBPoint(0,1,0,balLeg);
		
	
		addLine(blLeg[0][0][0],blLeg[0][1][0],blLeg[1][1][0],blLeg[1][0][0],Renderer3D.CAR_BOTTOM);
			
		
		blLeg[0][0][1]=addBPoint(0,0,1,balLeg);
		blLeg[1][0][1]=addBPoint(1,0,1,balLeg);
		blLeg[1][1][1]=addBPoint(1,1,1,balLeg);
		blLeg[0][1][1]=addBPoint(0,1,1,balLeg);
		
		addLine(blLeg[0][0][0],blLeg[0][0][1],blLeg[0][1][1],blLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(blLeg[0][1][0],blLeg[0][1][1],blLeg[1][1][1],blLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(blLeg[1][1][0],blLeg[1][1][1],blLeg[1][0][1],blLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(blLeg[1][0][0],blLeg[1][0][1],blLeg[0][0][1],blLeg[0][0][0],Renderer3D.CAR_BACK);
		
		/// back right leg
		
		Segments barLeg=new Segments(x_side-leg_side,leg_side,0,leg_side,0,leg_length);
		
		BPoint[][][] brLeg=new BPoint[2][2][2];
		
		brLeg[0][0][0]=addBPoint(0,0,0,barLeg);
		brLeg[1][0][0]=addBPoint(1,0,0,barLeg);
		brLeg[1][1][0]=addBPoint(1,1,0,barLeg);
		brLeg[0][1][0]=addBPoint(0,1,0,barLeg);
		
	
		addLine(brLeg[0][0][0],brLeg[0][1][0],brLeg[1][1][0],brLeg[1][0][0],Renderer3D.CAR_BOTTOM);
		
		brLeg[0][0][1]=addBPoint(0,0,1,barLeg);
		brLeg[1][0][1]=addBPoint(1,0,1,barLeg);
		brLeg[1][1][1]=addBPoint(1,1,1,barLeg);
		brLeg[0][1][1]=addBPoint(0,1,1,barLeg);
		
		addLine(brLeg[0][0][0],brLeg[0][0][1],brLeg[0][1][1],brLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(brLeg[0][1][0],brLeg[0][1][1],brLeg[1][1][1],brLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(brLeg[1][1][0],brLeg[1][1][1],brLeg[1][0][1],brLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(brLeg[1][0][0],brLeg[1][0][1],brLeg[0][0][1],brLeg[0][0][0],Renderer3D.CAR_BACK);
		
		/// front left leg
		
		Segments frlLeg=new Segments(0,leg_side,y_side-leg_side,leg_side,0,leg_length);
		
		BPoint[][][] flLeg=new BPoint[2][2][2];
		
		flLeg[0][0][0]=addBPoint(0,0,0,frlLeg);
		flLeg[1][0][0]=addBPoint(1,0,0,frlLeg);
		flLeg[1][1][0]=addBPoint(1,1,0,frlLeg);
		flLeg[0][1][0]=addBPoint(0,1,0,frlLeg);
		
	
		addLine(flLeg[0][0][0],flLeg[0][1][0],flLeg[1][1][0],flLeg[1][0][0],Renderer3D.CAR_BOTTOM);
	
		
		flLeg[0][0][1]=addBPoint(0,0,1,frlLeg);
		flLeg[1][0][1]=addBPoint(1,0,1,frlLeg);
		flLeg[1][1][1]=addBPoint(1,1,1,frlLeg);
		flLeg[0][1][1]=addBPoint(0,1,1,frlLeg);
		
		addLine(flLeg[0][0][0],flLeg[0][0][1],flLeg[0][1][1],flLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(flLeg[0][1][0],flLeg[0][1][1],flLeg[1][1][1],flLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(flLeg[1][1][0],flLeg[1][1][1],flLeg[1][0][1],flLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(flLeg[1][0][0],flLeg[1][0][1],flLeg[0][0][1],flLeg[0][0][0],Renderer3D.CAR_BACK);
		
		/// front right leg
		
		Segments frrLeg=new Segments(x_side-leg_side,leg_side,y_side-leg_side,leg_side,0,leg_length);
		
		BPoint[][][] frLeg=new BPoint[2][2][2];
		
		frLeg[0][0][0]=addBPoint(0,0,0,frrLeg);
		frLeg[1][0][0]=addBPoint(1,0,0,frrLeg);
		frLeg[1][1][0]=addBPoint(1,1,0,frrLeg);
		frLeg[0][1][0]=addBPoint(0,1,0,frrLeg);
		
	
		addLine(frLeg[0][0][0],frLeg[0][1][0],frLeg[1][1][0],frLeg[1][0][0],Renderer3D.CAR_BOTTOM);
	
		
		frLeg[0][0][1]=addBPoint(0,0,1,frrLeg);
		frLeg[1][0][1]=addBPoint(1,0,1,frrLeg);
		frLeg[1][1][1]=addBPoint(1,1,1,frrLeg);
		frLeg[0][1][1]=addBPoint(0,1,1,frrLeg);
		
		addLine(frLeg[0][0][0],frLeg[0][0][1],frLeg[0][1][1],frLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(frLeg[0][1][0],frLeg[0][1][1],frLeg[1][1][1],frLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(frLeg[1][1][0],frLeg[1][1][1],frLeg[1][0][1],frLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(frLeg[1][0][0],frLeg[1][0][1],frLeg[0][0][1],frLeg[0][0][0],Renderer3D.CAR_BACK);
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildStreetLighteMesh() {
		
		points=new Vector();
		points.setSize(600);

		polyData=new Vector();
		
		n=0;
		
		int trunk_meridians=10;
		int trunk_parallels=10;
		double trunk_radius=x_side*0.5;
		double trunk_lenght=z_side;
		
		//trunk: 
		
		BPoint[][] trunkpoints=new BPoint[trunk_parallels][trunk_meridians];
		
		for (int k = 0; k < trunk_parallels; k++) {
			
			
			for (int i = 0; i < trunk_meridians; i++) {
				
				double x=trunk_radius*Math.cos(2*Math.PI/trunk_meridians*i);
				double y=trunk_radius*Math.sin(2*Math.PI/trunk_meridians*i);
				
				double z=0;
				
				double dz0=trunk_lenght*2.0/3.0;
				double dz1=(trunk_lenght-dz0);
				
				if(k<2){
					
					z=dz0*k;
				}
				else{
					
					z=dz0+dz1/(trunk_parallels-2.0)*(k-1);
				}
				
								
				trunkpoints[k][i]=addBPoint(x,y,z);
				
			}
			
		}
		


		LineData topLD=new LineData();
		
		for (int i = 0; i < trunk_meridians; i++) {
			
			topLD.addIndex(trunkpoints[trunk_parallels-1][i].getIndex());
			
		}
		topLD.setData(""+Renderer3D.CAR_TOP);
		polyData.add(topLD);
		



		LineData bottomLD=new LineData();
		
		for (int i = trunk_meridians-1; i >=0; i--) {
			
			bottomLD.addIndex(trunkpoints[0][i].getIndex());
			
		}
		bottomLD.setData(""+Renderer3D.CAR_BOTTOM);
		polyData.add(bottomLD);
		
		for (int k = 0; k < trunk_parallels-1; k++) {
			
			for (int i = 0; i < trunk_meridians; i++) {
				
				LineData sideLD=new LineData();
				
				sideLD.addIndex(trunkpoints[k][i].getIndex());
				sideLD.addIndex(trunkpoints[k][(i+1)%trunk_meridians].getIndex());
				sideLD.addIndex(trunkpoints[k+1][(i+1)%trunk_meridians].getIndex());
				sideLD.addIndex(trunkpoints[k+1][i].getIndex());	
				sideLD.setData(""+getFace(sideLD,points));
				polyData.add(sideLD);
				
			}
			
		}
		
		//lamp


		Segments l0=new Segments(0,upper_width,-0.457*upper_height,upper_height,z_side,upper_length);

		int pnumx=2;
		int pnumy=2;
		int pnumz=10;

		BPoint[][][] lamp=new BPoint[pnumx][pnumy][pnumz];

			
		lamp[0][0][0]=addBPoint(-0.116,0.215,0,l0);
		lamp[1][0][0]=addBPoint(0.116,0.215,0,l0);
		lamp[0][1][0]=addBPoint(-0.116,0.699,0,l0);
		lamp[1][1][0]=addBPoint(0.116,0.699,0,l0);	
		
	
		lamp[0][0][1]=addBPoint(-0.113,0.221,0.156,l0);
		lamp[1][0][1]=addBPoint(0.113,0.221,0.156,l0);
		lamp[0][1][1]=addBPoint(-0.113,0.699,0.156,l0);
		lamp[1][1][1]=addBPoint(0.113,0.699,0.156,l0);	
					
		lamp[0][0][2]=addBPoint(-0.175,0.202,0.192,l0);
		lamp[1][0][2]=addBPoint(0.175,0.202,0.192,l0);
		lamp[0][1][2]=addBPoint(-0.175,1.0,0.192,l0);
		lamp[1][1][2]=addBPoint(0.175,1.0,0.192,l0);	
		
		
		lamp[0][0][3]=addBPoint(-0.322,0.16,0.259,l0);
		lamp[1][0][3]=addBPoint(0.322,0.16,0.259,l0);
		lamp[0][1][3]=addBPoint(-0.322,1.0,0.259,l0);
		lamp[1][1][3]=addBPoint(0.322,1.0,0.259,l0);	
		
	
		lamp[0][0][4]=addBPoint(-0.472,0.049,0.44,l0);
		lamp[1][0][4]=addBPoint(0.472,0.049,0.44,l0);
		lamp[0][1][4]=addBPoint(-0.472,1.0,0.44,l0);
		lamp[1][1][4]=addBPoint(0.472,1.0,0.44,l0);	
		
	
		lamp[0][0][5]=addBPoint(-0.5,0.0,0.578,l0);
		lamp[1][0][5]=addBPoint(0.5,0.0,0.578,l0);
		lamp[0][1][5]=addBPoint(-0.5,1.0,0.578,l0);
		lamp[1][1][5]=addBPoint(0.5,1.0,0.578,l0);	
		
		
		lamp[0][0][6]=addBPoint(-0.463,0.074,0.748,l0);
		lamp[1][0][6]=addBPoint(0.463,0.074,0.748,l0);
		lamp[0][1][6]=addBPoint(-0.463,1.0,0.748,l0);
		lamp[1][1][6]=addBPoint(0.463,1.0,0.748,l0);
		
		
		lamp[0][0][7]=addBPoint(-0.391,0.221,0.846,l0);
		lamp[1][0][7]=addBPoint(0.391,0.221,0.846,l0);
		lamp[0][1][7]=addBPoint(-0.391,1.0,0.846,l0);
		lamp[1][1][7]=addBPoint(0.391,1.0,0.846,l0);
		
		
		lamp[0][0][8]=addBPoint(-0.307,0.417,0.918,l0);
		lamp[1][0][8]=addBPoint(0.307,0.417,0.918,l0);
		lamp[0][1][8]=addBPoint(-0.307,1.0,0.918,l0);
		lamp[1][1][8]=addBPoint(0.307,1.0,0.918,l0);
		
	
		lamp[0][0][9]=addBPoint(-0.163,0.706,0.98,l0);
		lamp[1][0][9]=addBPoint(0.163,0.706,0.98,l0);
		lamp[0][1][9]=addBPoint(-0.163,1.0,0.98,l0);
		lamp[1][1][9]=addBPoint(0.163,1.0,0.98,l0);	


		for (int i = 0; i < pnumx-1; i++) {


			for (int j = 0; j < pnumy-1; j++) {	

				for (int k = 0; k < pnumz-1; k++) {


					if(i==0){

						LineData leftFrontLD=addLine(lamp[i][j][k],lamp[i][j][k+1],lamp[i][j+1][k+1],lamp[i][j+1][k],Renderer3D.CAR_LEFT);
					}



					if(k+1==pnumz-1){
						LineData topillowLD=addLine(lamp[i][j][k+1],lamp[i+1][j][k+1],lamp[i+1][j+1][k+1],lamp[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					if(k==0){
						LineData bottomFrontLD=addLine(lamp[i][j][k],lamp[i][j+1][k],lamp[i+1][j+1][k],lamp[i+1][j][k],Renderer3D.CAR_BOTTOM);
					}

					if(j==0){
						LineData backFrontLD=addLine(lamp[i][j][k],lamp[i+1][j][k],lamp[i+1][j][k+1],lamp[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==pnumy-1){
						LineData frontFrontLD=addLine(lamp[i][j+1][k],lamp[i][j+1][k+1],lamp[i+1][j+1][k+1],lamp[i+1][j+1][k],Renderer3D.CAR_FRONT);
					}


					if(i+1==pnumx-1){

						LineData rightFrontLD=addLine(lamp[i+1][j][k],lamp[i+1][j+1][k],lamp[i+1][j+1][k+1],lamp[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}

				}

			}

		}


		/////////



		
		//bending of the streetlight
		
		double[] q=new double[trunk_parallels];
		
		q[1]=0.2;
		q[2]=0.2;
		q[3]=0.2;
		q[4]=0.2;
		q[5]=0.2;
		q[6]=0.2;
		q[7]=0.2;
		q[8]=0.2;
	

		
		for (int i = 0; i < q.length; i++) {
			
			if(q[i]==0)
				continue;
			
			BPoint[] refSlice = trunkpoints[i];
			BPoint p0= refSlice[0];
			
			rotateYZ(lamp,  p0.y,  p0.z,  q[i]);
			
			for (int k = 0; k < trunk_parallels; k++) {
				
				if(k<=i)
					continue;				

				BPoint[] slice = trunkpoints[k];
				
				rotateYZ(slice,  p0.y,  p0.z,  q[i]);
				
			
			}
			
		}
        
		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}


}	