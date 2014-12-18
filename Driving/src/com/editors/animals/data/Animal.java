package com.editors.animals.data;

import java.util.Vector;

import com.BPoint;
import com.CustomData;
import com.LineData;
import com.PolygonMesh;
import com.Segments;
import com.main.Renderer3D;

public class Animal extends CustomData{

	double x_side=0;
	double y_side=0;
	double z_side=0;	
	
	double head_DZ=0;
	double head_DX=0;
	double head_DY=0;
	
	double neck_length=0;
	double neck_side=0;
	
	double humerus_length=0;
	double radius_length=0;
	double hand_length=0;
	
	double leg_side=0;
	
	double femur_length=0; 
	double shinbone_length=0;
	
	double foot_length=0;
	
	public static int ANIMAL_TYPE_QUADRUPED=0;
	public static int ANIMAL_TYPE_HUMAN=1;
	public static int ANIMAL_TYPE_MANHEAD=2;
	public static int ANIMAL_TYPE_MANHAND=3;
	
	public int animal_type=ANIMAL_TYPE_HUMAN;
	
	public static int HEAD=0;
	public static int TRUNK=1;
	public static int LEFT_FEMUR=2;
	public static int RIGHT_FEMUR=3;
	public static int LEFT_HOMERUS=4;
	public static int RIGHT_HOMERUS=5;
	public static int LEFT_RADIUS=6;
	public static int RIGHT_RADIUS=7;
	public static int LEFT_SHINBONE=8;
	public static int RIGHT_SHINBONE=9;


	public Animal(){}

	public Animal(double x_side, double y_side,double z_side,int animal_type,
			double femur_length,double shinbone_length,double leg_side,
			double head_DZ,double head_DX,double head_DY,double neck_length,double neck_side,
			double humerus_length,double radius_length,double hand_length,double foot_length
			) {
		super();

		this.x_side = x_side;
		this.y_side = y_side;
		this.z_side = z_side;
		this.animal_type = animal_type;

		this.head_DZ = head_DZ;
		this.head_DX = head_DX;
		this.head_DY = head_DY;
		this.neck_length = neck_length; 
		this.neck_side = neck_side;
		
		this.humerus_length = humerus_length;
		this.radius_length = radius_length;
		this.foot_length = foot_length;
		
		this.femur_length = femur_length;
		this.shinbone_length = shinbone_length;
		this.leg_side = leg_side;
		
		this.hand_length = hand_length;
		this.foot_length = foot_length;
		
	}

	public Object clone(){

		Animal animal=new Animal(
				x_side,y_side,z_side,animal_type,femur_length,shinbone_length,leg_side,
				head_DZ,head_DX,head_DY,neck_length,neck_side,humerus_length,radius_length,
				hand_length,foot_length
				);
		return animal;

	}
	
	public BPoint addBPoint( double x, double y, double z,int body_part,Segments s){
		
		BPoint point=new BPoint(s.x(x), s.y(y), s.z(z), n++);
		point.setData(""+body_part);
		points.setElementAt(point,point.getIndex());
		return point;
		
	}
	
	public BPoint addBPoint(double x, double y, double z,int body_part){
		
		BPoint point=new BPoint(x, y, z, n++);
		point.setData(""+body_part);
		points.setElementAt(point,point.getIndex());
		return point;
		
	}

	public int getAnimal_type() {
		return animal_type;
	}

	public void setAnimal_type(int animal_type) {
		this.animal_type = animal_type;
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

		String ret= "A="+x_side+","+y_side+","+z_side+","+animal_type;
		ret+=","+femur_length+","+shinbone_length+","+leg_side+
				","+head_DZ+","+head_DX+","+head_DY+","+neck_length+","+neck_side+","
				+humerus_length+","+radius_length+","+hand_length+","+foot_length;
				
		return ret;		
	}

	public static Animal buildAnimal(String str) {

		String[] vals = str.split(",");


		double x_side =Double.parseDouble(vals[0]);
		double y_side = Double.parseDouble(vals[1]);
		double z_side = Double.parseDouble(vals[2]); 
		int animal_type =Integer.parseInt(vals[3]);		
		
		double femurLength=Double.parseDouble(vals[4]);		
		double shinboneLength=Double.parseDouble(vals[5]);			
		double legSide = Double.parseDouble(vals[6]);	
		
		double headDZ = Double.parseDouble(vals[7]); 
		double headDX = Double.parseDouble(vals[8]); 
		double headDY = Double.parseDouble(vals[9]);
		double neckLength = Double.parseDouble(vals[10]);
		double neckSide = Double.parseDouble(vals[11]);
		
		double humerusLength =Double.parseDouble(vals[12]);
		double radiusLength = Double.parseDouble(vals[13]);
		
		double handLength=Double.parseDouble(vals[14]);
		double footLength=Double.parseDouble(vals[15]);

		Animal grid=new Animal(x_side,y_side,z_side,animal_type,
				femurLength,shinboneLength,legSide,
				headDZ,headDX,headDY,neckLength,neckSide,
				humerusLength,radiusLength,handLength,footLength);

		return grid;
	}



	public double getZ_side() {
		return z_side;
	}
	
	public void setZ_side(double z_side) {
		this.z_side = z_side;
	}

	public double getLeg_side() {
		return leg_side;
	}

	public void setLeg_side(double leg_side) {
		this.leg_side = leg_side;
	}

	
	public double getFemur_length() {
		return femur_length;
	}

	public void setFemur_length(double leg_length) {
		this.femur_length = leg_length;
	}
	

	public double getHead_DY() {
		return head_DY;
	}

	public void setHead_DY(double head_DY) {
		this.head_DY = head_DY;
	}

	public double getHead_DZ() {
		return head_DZ;
	}

	public void setHead_DZ(double head_DZ) {
		this.head_DZ = head_DZ;
	}

	public double getHead_DX() {
		return head_DX;
	}

	public void setHead_DX(double head_DX) {
		this.head_DX = head_DX;
	}

	public double getNeck_length() {
		return neck_length;
	}

	public void setNeck_length(double neck_length) {
		this.neck_length = neck_length;
	}

	public double getHumerus_length() {
		return humerus_length;
	}

	public void setHumerus_length(double arm_length) {
		this.humerus_length = arm_length;
	}

	public double getNeck_side() {
		return neck_side;
	}

	public void setNeck_side(double neck_side) {
		this.neck_side = neck_side;
	}

	public double getRadius_length() {
		return radius_length;
	}

	public void setRadius_length(double radius_length) {
		this.radius_length = radius_length;
	}

	public double getShinbone_length() {
		return shinbone_length;
	}

	public void setShinbone_length(double shinbone_length) {
		this.shinbone_length = shinbone_length;
	}
	
	public double getFoot_length() {
		return foot_length;
	}

	public void setFoot_length(double foot_length) {
		this.foot_length = foot_length;
	}
	
	public double getHand_length() {
		return hand_length;
	}

	public void setHand_length(double hand_length) {
		this.hand_length = hand_length;
	}

	public PolygonMesh buildMesh(){
		
		
		if(ANIMAL_TYPE_HUMAN==animal_type){	
			
			return buildHumanMesh();
			
			
			
		}
		else if(ANIMAL_TYPE_QUADRUPED==animal_type){			
			
			return buildQuadrupedMesh();
			
		}
		else if(ANIMAL_TYPE_MANHEAD==animal_type){			
			
			return  buildManHeadMesh();
			
		}
		else
			return buildManHandMesh();

		


	}

	

	private PolygonMesh buildHumanMesh() {
		
		
		Man man=new Man( x_side,  y_side, z_side, animal_type,
				 femur_length, shinbone_length, leg_side,
				 head_DZ, head_DX, head_DY, neck_length, neck_side,
				 humerus_length, radius_length, hand_length, foot_length
			);
		return man.getMesh();
		
		
	}

	private PolygonMesh buildQuadrupedMesh() {
		
		Quadruped quadruped=new Quadruped( x_side,  y_side, z_side, animal_type,
				 femur_length, shinbone_length, leg_side,
				 head_DZ, head_DX, head_DY, neck_length, neck_side,
				 humerus_length, radius_length, hand_length, foot_length);
		return quadruped.getMesh();


	
	}



	private PolygonMesh buildManHeadMesh() {
		points=new Vector();
		points.setSize(400);

		polyData=new Vector();

		n=0;
		
		double xc=x_side/2.0;
		double yc=y_side/2.0;
	
		Segments n0=new Segments(xc,head_DX,yc,head_DY,0,head_DZ);
		Segments h0=new Segments(xc,head_DX,yc,head_DY,0,head_DZ);
		

		int numx=9;
		int numy=5;
		int numz=9;
		
		BPoint[][][] head=new BPoint[numx][numy][numz];
		
		

		head[0][0][0]=addBPoint(-0.02,-0.358,0.0,n0);
		head[1][0][0]=addBPoint(-0.015,-0.358,0.0,n0);
		head[2][0][0]=addBPoint(-0.01,-0.358,0.0,n0);
		head[3][0][0]=addBPoint(-0.005,-0.358,0.0,n0);
		head[4][0][0]=addBPoint(0.0,-0.358,0.0,n0);
		head[5][0][0]=addBPoint(0.005,-0.358,0.0,n0);
		head[6][0][0]=addBPoint(0.01,-0.358,0.0,n0);
		head[7][0][0]=addBPoint(0.015,-0.358,0.0,n0);
		head[8][0][0]=addBPoint(0.02,-0.358,0.0,n0);
		head[0][1][0]=addBPoint(-0.02,-0.179,0.0,n0);
		head[1][1][0]=addBPoint(-0.015,-0.179,0.0,n0);
		head[2][1][0]=addBPoint(-0.01,-0.179,0.0,n0);
		head[3][1][0]=addBPoint(-0.005,-0.179,0.0,n0);
		head[4][1][0]=addBPoint(0.0,-0.179,0.0,n0);
		head[5][1][0]=addBPoint(0.005,-0.179,0.0,n0);
		head[6][1][0]=addBPoint(0.01,-0.179,0.0,n0);
		head[7][1][0]=addBPoint(0.015,-0.179,0.0,n0);
		head[8][1][0]=addBPoint(0.02,-0.179,0.0,n0);
		head[0][2][0]=addBPoint(-0.02,0.0,0.0,n0);
		head[1][2][0]=addBPoint(-0.015,0.0,0.0,n0);
		head[2][2][0]=addBPoint(-0.01,0.0,0.0,n0);
		head[3][2][0]=addBPoint(-0.005,0.0,0.0,n0);
		head[4][2][0]=addBPoint(0.0,0.0,0.0,n0);
		head[5][2][0]=addBPoint(0.005,0.0,0.0,n0);
		head[6][2][0]=addBPoint(0.01,0.0,0.0,n0);
		head[7][2][0]=addBPoint(0.015,0.0,0.0,n0);
		head[8][2][0]=addBPoint(0.02,0.0,0.0,n0);
		head[0][3][0]=addBPoint(-0.02,0.209,0.0,n0);
		head[1][3][0]=addBPoint(-0.015,0.209,0.0,n0);
		head[2][3][0]=addBPoint(-0.01,0.209,0.0,n0);
		head[3][3][0]=addBPoint(-0.005,0.209,0.0,n0);
		head[4][3][0]=addBPoint(0.0,0.209,0.0,n0);
		head[5][3][0]=addBPoint(0.005,0.209,0.0,n0);
		head[6][3][0]=addBPoint(0.01,0.209,0.0,n0);
		head[7][3][0]=addBPoint(0.015,0.209,0.0,n0);
		head[8][3][0]=addBPoint(0.02,0.209,0.0,n0);
		head[0][4][0]=addBPoint(-0.02,0.418,0.0,n0);
		head[1][4][0]=addBPoint(-0.015,0.418,0.0,n0);
		head[2][4][0]=addBPoint(-0.01,0.418,0.0,n0);
		head[3][4][0]=addBPoint(-0.005,0.418,0.0,n0);
		head[4][4][0]=addBPoint(0.0,0.418,0.0,n0);
		head[5][4][0]=addBPoint(0.005,0.418,0.0,n0);
		head[6][4][0]=addBPoint(0.01,0.418,0.0,n0);
		head[7][4][0]=addBPoint(0.015,0.418,0.0,n0);
		head[8][4][0]=addBPoint(0.02,0.418,0.0,n0);

		head[0][0][1]=addBPoint(-0.153,-0.347,0.022,n0);
		head[1][0][1]=addBPoint(-0.115,-0.348,0.022,n0);
		head[2][0][1]=addBPoint(-0.077,-0.35,0.022,n0);
		head[3][0][1]=addBPoint(-0.038,-0.35,0.022,n0);
		head[4][0][1]=addBPoint(0.0,-0.351,0.022,n0);
		head[5][0][1]=addBPoint(0.039,-0.35,0.022,n0);
		head[6][0][1]=addBPoint(0.077,-0.35,0.022,n0);
		head[7][0][1]=addBPoint(0.115,-0.348,0.022,n0);
		head[8][0][1]=addBPoint(0.153,-0.347,0.022,n0);
		head[0][1][1]=addBPoint(-0.153,-0.173,0.022,n0);
		head[8][1][1]=addBPoint(0.153,-0.173,0.022,n0);
		head[0][2][1]=addBPoint(-0.153,0.0,0.022,n0);
		head[8][2][1]=addBPoint(0.153,0.0,0.022,n0);
		head[0][3][1]=addBPoint(-0.153,0.225,0.022,n0);
		head[8][3][1]=addBPoint(0.153,0.225,0.022,n0);
		head[0][4][1]=addBPoint(-0.153,0.45,0.022,n0);
		head[1][4][1]=addBPoint(-0.115,0.452,0.022,n0);
		head[2][4][1]=addBPoint(-0.077,0.454,0.022,n0);
		head[3][4][1]=addBPoint(-0.038,0.455,0.022,n0);
		head[4][4][1]=addBPoint(0.0,0.455,0.022,n0);
		head[5][4][1]=addBPoint(0.039,0.455,0.022,n0);
		head[6][4][1]=addBPoint(0.077,0.454,0.022,n0);
		head[7][4][1]=addBPoint(0.115,0.452,0.022,n0);
		head[8][4][1]=addBPoint(0.153,0.45,0.022,n0);

		head[0][0][2]=addBPoint(-0.337,-0.307,0.11,n0);
		head[1][0][2]=addBPoint(-0.253,-0.315,0.11,n0);
		head[2][0][2]=addBPoint(-0.169,-0.323,0.11,n0);
		head[3][0][2]=addBPoint(-0.084,-0.326,0.11,n0);
		head[4][0][2]=addBPoint(0.0,-0.328,0.11,n0);
		head[5][0][2]=addBPoint(0.085,-0.326,0.11,n0);
		head[6][0][2]=addBPoint(0.169,-0.323,0.11,n0);
		head[7][0][2]=addBPoint(0.253,-0.315,0.11,n0);
		head[8][0][2]=addBPoint(0.337,-0.307,0.11,n0);
		head[0][1][2]=addBPoint(-0.337,-0.153,0.11,n0);
		head[8][1][2]=addBPoint(0.337,-0.153,0.11,n0);
		head[0][2][2]=addBPoint(-0.337,0.0,0.11,n0);
		head[8][2][2]=addBPoint(0.337,0.0,0.11,n0);
		head[0][3][2]=addBPoint(-0.337,0.213,0.11,n0);
		head[8][3][2]=addBPoint(0.337,0.213,0.11,n0);
		head[0][4][2]=addBPoint(-0.337,0.425,0.11,n0);
		head[1][4][2]=addBPoint(-0.253,0.437,0.11,n0);
		head[2][4][2]=addBPoint(-0.169,0.448,0.11,n0);
		head[3][4][2]=addBPoint(-0.084,0.452,0.11,n0);
		head[4][4][2]=addBPoint(0.0,0.455,0.11,n0);
		head[5][4][2]=addBPoint(0.085,0.452,0.11,n0);
		head[6][4][2]=addBPoint(0.169,0.448,0.11,n0);
		head[7][4][2]=addBPoint(0.253,0.437,0.11,n0);
		head[8][4][2]=addBPoint(0.337,0.425,0.11,n0);

		head[0][0][3]=addBPoint(-0.462,-0.309,0.287,n0);
		head[1][0][3]=addBPoint(-0.347,-0.332,0.287,n0);
		head[2][0][3]=addBPoint(-0.231,-0.356,0.287,n0);
		head[3][0][3]=addBPoint(-0.115,-0.361,0.287,n0);
		head[4][0][3]=addBPoint(0.0,-0.366,0.287,n0);
		head[5][0][3]=addBPoint(0.116,-0.361,0.287,n0);
		head[6][0][3]=addBPoint(0.231,-0.356,0.287,n0);
		head[7][0][3]=addBPoint(0.347,-0.332,0.287,n0);
		head[8][0][3]=addBPoint(0.462,-0.309,0.287,n0);
		head[0][1][3]=addBPoint(-0.462,-0.154,0.287,n0);
		head[8][1][3]=addBPoint(0.462,-0.154,0.287,n0);
		head[0][2][3]=addBPoint(-0.462,0.0,0.287,n0);
		head[8][2][3]=addBPoint(0.462,0.0,0.287,n0);
		head[0][3][3]=addBPoint(-0.462,0.212,0.287,n0);
		head[8][3][3]=addBPoint(0.462,0.212,0.287,n0);
		head[0][4][3]=addBPoint(-0.462,0.423,0.287,n0);
		head[1][4][3]=addBPoint(-0.288,0.455,0.287,n0);
		head[2][4][3]=addBPoint(-0.114,0.486,0.287,n0);
		head[3][4][3]=addBPoint(-0.057,0.493,0.287,n0);
		head[4][4][3]=addBPoint(0.0,0.5,0.287,n0);
		head[5][4][3]=addBPoint(0.057,0.493,0.287,n0);
		head[6][4][3]=addBPoint(0.114,0.486,0.287,n0);
		head[7][4][3]=addBPoint(0.288,0.455,0.287,n0);
		head[8][4][3]=addBPoint(0.462,0.423,0.287,n0);

		head[0][0][4]=addBPoint(-0.5,-0.352,0.471,n0);
		head[1][0][4]=addBPoint(-0.375,-0.403,0.471,n0);
		head[2][0][4]=addBPoint(-0.25,-0.454,0.471,n0);
		head[3][0][4]=addBPoint(-0.125,-0.462,0.471,n0);
		head[4][0][4]=addBPoint(0.0,-0.47,0.471,n0);
		head[5][0][4]=addBPoint(0.125,-0.462,0.471,n0);
		head[6][0][4]=addBPoint(0.25,-0.454,0.471,n0);
		head[7][0][4]=addBPoint(0.375,-0.403,0.471,n0);
		head[8][0][4]=addBPoint(0.5,-0.352,0.471,n0);
		head[0][1][4]=addBPoint(-0.5,-0.176,0.471,n0);
		head[8][1][4]=addBPoint(0.5,-0.176,0.471,n0);
		head[0][2][4]=addBPoint(-0.5,0.0,0.471,n0);
		head[8][2][4]=addBPoint(0.5,0.0,0.471,n0);
		head[0][3][4]=addBPoint(-0.5,0.14,0.471,n0);
		head[8][3][4]=addBPoint(0.5,0.14,0.471,n0);
		head[0][4][4]=addBPoint(-0.5,0.28,0.471,n0);
		head[1][4][4]=addBPoint(-0.28,0.321,0.471,n0);
		head[2][4][4]=addBPoint(-0.06,0.361,0.471,n0);
		head[3][4][4]=addBPoint(-0.03,0.367,0.471,n0);
		head[4][4][4]=addBPoint(0.0,0.373,0.471,n0);
		head[5][4][4]=addBPoint(0.03,0.367,0.471,n0);
		head[6][4][4]=addBPoint(0.06,0.361,0.471,n0);
		head[7][4][4]=addBPoint(0.28,0.321,0.471,n0);
		head[8][4][4]=addBPoint(0.5,0.28,0.471,n0);

		head[0][0][5]=addBPoint(-0.5,-0.375,0.625,n0);
		head[1][0][5]=addBPoint(-0.375,-0.429,0.625,n0);
		head[2][0][5]=addBPoint(-0.25,-0.483,0.625,n0);
		head[3][0][5]=addBPoint(-0.125,-0.491,0.625,n0);
		head[4][0][5]=addBPoint(0.0,-0.5,0.625,n0);
		head[5][0][5]=addBPoint(0.125,-0.491,0.625,n0);
		head[6][0][5]=addBPoint(0.25,-0.483,0.625,n0);
		head[7][0][5]=addBPoint(0.375,-0.429,0.625,n0);
		head[8][0][5]=addBPoint(0.5,-0.375,0.625,n0);
		head[0][1][5]=addBPoint(-0.5,-0.187,0.625,n0);
		head[8][1][5]=addBPoint(0.5,-0.187,0.625,n0);
		head[0][2][5]=addBPoint(-0.5,0.0,0.625,n0);
		head[8][2][5]=addBPoint(0.5,0.0,0.625,n0);
		head[0][3][5]=addBPoint(-0.5,0.168,0.625,n0);
		head[8][3][5]=addBPoint(0.5,0.168,0.625,n0);
		head[0][4][5]=addBPoint(-0.5,0.336,0.551,n0);
		head[1][4][5]=addBPoint(-0.375,0.385,0.588,n0);
		head[2][4][5]=addBPoint(-0.25,0.433,0.625,n0);
		head[3][4][5]=addBPoint(-0.125,0.44,0.625,n0);
		head[4][4][5]=addBPoint(0.0,0.448,0.625,n0);
		head[5][4][5]=addBPoint(0.125,0.44,0.625,n0);
		head[6][4][5]=addBPoint(0.25,0.433,0.625,n0);
		head[7][4][5]=addBPoint(0.375,0.385,0.588,n0);
		head[8][4][5]=addBPoint(0.5,0.336,0.551,n0);

		head[0][0][6]=addBPoint(-0.462,-0.392,0.779,n0);
		head[1][0][6]=addBPoint(-0.347,-0.421,0.779,n0);
		head[2][0][6]=addBPoint(-0.231,-0.45,0.779,n0);
		head[3][0][6]=addBPoint(-0.115,-0.457,0.779,n0);
		head[4][0][6]=addBPoint(0.0,-0.463,0.779,n0);
		head[5][0][6]=addBPoint(0.116,-0.457,0.779,n0);
		head[6][0][6]=addBPoint(0.231,-0.45,0.779,n0);
		head[7][0][6]=addBPoint(0.347,-0.421,0.779,n0);
		head[8][0][6]=addBPoint(0.462,-0.392,0.779,n0);
		head[0][1][6]=addBPoint(-0.462,-0.196,0.779,n0);
		head[8][1][6]=addBPoint(0.462,-0.196,0.779,n0);
		head[0][2][6]=addBPoint(-0.462,0.0,0.779,n0);
		head[8][2][6]=addBPoint(0.462,0.0,0.779,n0);
		head[0][3][6]=addBPoint(-0.462,0.168,0.779,n0);
		head[8][3][6]=addBPoint(0.462,0.168,0.779,n0);
		head[0][4][6]=addBPoint(-0.462,0.335,0.779,n0);
		head[1][4][6]=addBPoint(-0.347,0.36,0.779,n0);
		head[2][4][6]=addBPoint(-0.231,0.385,0.779,n0);
		head[3][4][6]=addBPoint(-0.115,0.39,0.779,n0);
		head[4][4][6]=addBPoint(0.0,0.396,0.779,n0);
		head[5][4][6]=addBPoint(0.116,0.39,0.779,n0);
		head[6][4][6]=addBPoint(0.231,0.385,0.779,n0);
		head[7][4][6]=addBPoint(0.347,0.36,0.779,n0);
		head[8][4][6]=addBPoint(0.462,0.335,0.779,n0);

		head[0][0][7]=addBPoint(-0.277,-0.264,0.956,n0);
		head[1][0][7]=addBPoint(-0.208,-0.268,0.956,n0);
		head[2][0][7]=addBPoint(-0.139,-0.273,0.956,n0);
		head[3][0][7]=addBPoint(-0.069,-0.274,0.956,n0);
		head[4][0][7]=addBPoint(0.0,-0.276,0.956,n0);
		head[5][0][7]=addBPoint(0.07,-0.274,0.956,n0);
		head[6][0][7]=addBPoint(0.139,-0.273,0.956,n0);
		head[7][0][7]=addBPoint(0.208,-0.268,0.956,n0);
		head[8][0][7]=addBPoint(0.277,-0.264,0.956,n0);
		head[0][1][7]=addBPoint(-0.277,-0.132,0.956,n0);
		head[8][1][7]=addBPoint(0.277,-0.132,0.956,n0);
		head[0][2][7]=addBPoint(-0.277,0.0,0.956,n0);
		head[8][2][7]=addBPoint(0.277,0.0,0.956,n0);
		head[0][3][7]=addBPoint(-0.277,0.1,0.956,n0);
		head[8][3][7]=addBPoint(0.277,0.1,0.956,n0);
		head[0][4][7]=addBPoint(-0.277,0.2,0.956,n0);
		head[1][4][7]=addBPoint(-0.208,0.204,0.956,n0);
		head[2][4][7]=addBPoint(-0.139,0.207,0.956,n0);
		head[3][4][7]=addBPoint(-0.069,0.208,0.956,n0);
		head[4][4][7]=addBPoint(0.0,0.209,0.956,n0);
		head[5][4][7]=addBPoint(0.07,0.208,0.956,n0);
		head[6][4][7]=addBPoint(0.139,0.207,0.956,n0);
		head[7][4][7]=addBPoint(0.208,0.204,0.956,n0);
		head[8][4][7]=addBPoint(0.277,0.2,0.956,n0);

		head[0][0][8]=addBPoint(-0.141,-0.147,0.993,n0);
		head[1][0][8]=addBPoint(-0.106,-0.148,0.993,n0);
		head[2][0][8]=addBPoint(-0.071,-0.149,0.993,n0);
		head[3][0][8]=addBPoint(-0.035,-0.149,0.993,n0);
		head[4][0][8]=addBPoint(0.0,-0.149,0.993,n0);
		head[5][0][8]=addBPoint(0.036,-0.149,0.993,n0);
		head[6][0][8]=addBPoint(0.071,-0.149,0.993,n0);
		head[7][0][8]=addBPoint(0.106,-0.148,0.993,n0);
		head[8][0][8]=addBPoint(0.141,-0.147,0.993,n0);
		head[0][1][8]=addBPoint(-0.141,-0.073,1.0,n0);
		head[1][1][8]=addBPoint(-0.106,-0.073,1.0,n0);
		head[2][1][8]=addBPoint(-0.071,-0.074,1.0,n0);
		head[3][1][8]=addBPoint(-0.035,-0.074,1.0,n0);
		head[4][1][8]=addBPoint(0.0,-0.074,1.0,n0);
		head[5][1][8]=addBPoint(0.036,-0.074,1.0,n0);
		head[6][1][8]=addBPoint(0.071,-0.074,1.0,n0);
		head[7][1][8]=addBPoint(0.106,-0.073,1.0,n0);
		head[8][1][8]=addBPoint(0.141,-0.073,1.0,n0);
		head[0][2][8]=addBPoint(-0.141,0.0,1.0,n0);
		head[1][2][8]=addBPoint(-0.106,0.0,1.0,n0);
		head[2][2][8]=addBPoint(-0.071,0.0,1.0,n0);
		head[3][2][8]=addBPoint(-0.035,0.0,1.0,n0);
		head[4][2][8]=addBPoint(0.0,0.0,1.0,n0);
		head[5][2][8]=addBPoint(0.036,0.0,1.0,n0);
		head[6][2][8]=addBPoint(0.071,0.0,1.0,n0);
		head[7][2][8]=addBPoint(0.106,0.0,1.0,n0);
		head[8][2][8]=addBPoint(0.141,0.0,1.0,n0);
		head[0][3][8]=addBPoint(-0.141,0.056,1.0,n0);
		head[1][3][8]=addBPoint(-0.106,0.056,1.0,n0);
		head[2][3][8]=addBPoint(-0.071,0.056,1.0,n0);
		head[3][3][8]=addBPoint(-0.035,0.056,1.0,n0);
		head[4][3][8]=addBPoint(0.0,0.056,1.0,n0);
		head[5][3][8]=addBPoint(0.036,0.056,1.0,n0);
		head[6][3][8]=addBPoint(0.071,0.056,1.0,n0);
		head[7][3][8]=addBPoint(0.106,0.056,1.0,n0);
		head[8][3][8]=addBPoint(0.141,0.056,1.0,n0);
		head[0][4][8]=addBPoint(-0.141,0.111,0.993,n0);
		head[1][4][8]=addBPoint(-0.106,0.112,0.993,n0);
		head[2][4][8]=addBPoint(-0.071,0.112,0.993,n0);
		head[3][4][8]=addBPoint(-0.035,0.112,0.993,n0);
		head[4][4][8]=addBPoint(0.0,0.112,0.993,n0);
		head[5][4][8]=addBPoint(0.036,0.112,0.993,n0);
		head[6][4][8]=addBPoint(0.071,0.112,0.993,n0);
		head[7][4][8]=addBPoint(0.106,0.112,0.993,n0);
		head[8][4][8]=addBPoint(0.141,0.111,0.993,n0);

/*

		
		
			//////////
		
		for (int k = 0; k < numz; k++) {

			for (int j = 0; j < numy; j++) {


	
				for (int i = 0; i < numx; i++) {
					
					
			
					if(head[i][j][k]!=null) {
						
						int xIndex=i*2;
								
						double x=head[i][j][k].x;
						double y=head[i][j][k].y;
						double z=head[i][j][k].z;
						
						if(i>0 && head[i-1][j][k]!=null){ 
							
							double xx=(head[i-1][j][k].x+head[i][j][k].x)*0.5;
							double yy=(head[i-1][j][k].y+head[i][j][k].y)*0.5;
							
							System.out.println("head["+(xIndex-1)+"]["+j+"]["+k+"]=" +
									"addBPoint("
											+Math.round(xx/head_DX*1000)/1000.0+","
											+Math.round(yy/head_DY*1000)/1000.0+","
											+Math.round(z/head_DZ*1000)/1000.0+",n0);");
				
							
							
						}

						System.out.println("head["+xIndex+"]["+j+"]["+k+"]=" +
								"addBPoint("
										+Math.round(x/head_DX*1000)/1000.0+","
										+Math.round(y/head_DY*1000)/1000.0+","
										+Math.round(z/head_DZ*1000)/1000.0+",n0);");


					}
				}

				

			}
			System.out.println();
		}

*/

		
		for (int i = 0; i < numx-1; i++) {


			for (int j = 0; j < numy-1; j++) {

				for (int k = 0; k < numz-1; k++) {

             

					if(i==0){

						addLine(head[i][j][k],head[i][j][k+1],head[i][j+1][k+1],head[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						addLine(head[i][j][k],head[i][j+1][k],head[i+1][j+1][k],head[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==numz-1){
						addLine(head[i][j][k+1],head[i+1][j][k+1],head[i+1][j+1][k+1],head[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						addLine(head[i][j][k],head[i+1][j][k],head[i+1][j][k+1],head[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==numy-1){
						addLine(head[i][j+1][k],head[i][j+1][k+1],head[i+1][j+1][k+1],head[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==numx-1){

						addLine(head[i+1][j][k],head[i+1][j+1][k],head[i+1][j+1][k+1],head[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		//noze
		
		int nox=3;
		int noz=3;

		
		BPoint[][] noze=new BPoint[nox][noz];
		
		noze[0][0]=head[2][4][3];
		noze[1][0]=addBPoint(0.0,0.537,0.361,n0);
		noze[2][0]=head[6][4][3];
		noze[0][1]=head[2][4][4];
		noze[1][1]=addBPoint(0.0,0.463,0.5,n0);
		noze[2][1]=head[6][4][4];
		
		noze[1][2]=head[4][4][5];
		
		//base
		addLine(noze[0][0],noze[1][0],noze[2][0],null,Renderer3D.CAR_FRONT);
		//lower		
		addLine(noze[0][0],noze[0][1],noze[1][1],noze[1][0],Renderer3D.CAR_FRONT);	
		addLine(noze[1][0],noze[1][1],noze[2][1],noze[2][0],Renderer3D.CAR_FRONT);	
		//upper
		addLine(noze[0][1],noze[1][2],noze[1][1],null,Renderer3D.CAR_FRONT);
		addLine(noze[1][1],noze[1][2],noze[2][1],null,Renderer3D.CAR_FRONT);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}
	

	private PolygonMesh buildManHandMesh() {
		
		points=new Vector();
		points.setSize(400);

		polyData=new Vector();

		n=0;
		
		
		
		int pnx=9;
		int pny=5;
		int pnz=2;
		
		BPoint[][][] palm=new BPoint[pnx][pny][pnz];
		
		double xc=0;
		
		double df=0.175;
		double ds=(1.0-df*4.0)/4.0;
		
		double[] x={0,ds,ds+df,2*ds+df,2*ds+2*df,3*ds+2*df,3*ds+3*df,4*ds+3*df,4*ds+4*df};
		//middle fingers
		double[] mx={(ds+df)-df*0.5,2*(ds+df)-df*0.5,3*(ds+df)-df*0.5,4*(ds+df)-df*0.5};
		
		Segments p0=new Segments(xc,x_side,0,y_side,0,z_side);

		palm[0][0][0]=addBPoint(x[0],0,0,TRUNK,p0);
		palm[1][0][0]=addBPoint(x[1],0.0,0.4,TRUNK,p0);
		palm[2][0][0]=addBPoint(x[2],0.0,0.4,TRUNK,p0);
		palm[3][0][0]=addBPoint(x[3],0.0,0.6,TRUNK,p0);
		palm[4][0][0]=addBPoint(x[4],0.0,0.6,TRUNK,p0);
		palm[5][0][0]=addBPoint(x[5],0.0,0.4,TRUNK,p0);
		palm[6][0][0]=addBPoint(x[6],0.0,0.4,TRUNK,p0);
		palm[7][0][0]=addBPoint(x[7],0.0,0,TRUNK,p0);
		palm[8][0][0]=addBPoint(x[8],0.0,0,TRUNK,p0);
		palm[0][1][0]=addBPoint(x[0],0.25,0,TRUNK,p0);
		palm[1][1][0]=addBPoint(x[1],0.25,0.4,TRUNK,p0);
		palm[2][1][0]=addBPoint(x[2],0.25,0.4,TRUNK,p0);
		palm[3][1][0]=addBPoint(x[3],0.25,0.6,TRUNK,p0);
		palm[4][1][0]=addBPoint(x[4],0.25,0.6,TRUNK,p0);
		palm[5][1][0]=addBPoint(x[5],0.25,0.4,TRUNK,p0);
		palm[6][1][0]=addBPoint(x[6],0.25,0.4,TRUNK,p0);
		palm[7][1][0]=addBPoint(x[7],0.25,0,TRUNK,p0);
		palm[8][1][0]=addBPoint(x[8],0.25,0,TRUNK,p0);
		palm[0][2][0]=addBPoint(x[0],0.5,0,TRUNK,p0);
		palm[1][2][0]=addBPoint(x[1],0.5,0.4,TRUNK,p0);
		palm[2][2][0]=addBPoint(x[2],0.5,0.4,TRUNK,p0);
		palm[3][2][0]=addBPoint(x[3],0.5,0.6,TRUNK,p0);
		palm[4][2][0]=addBPoint(x[4],0.5,0.6,TRUNK,p0);
		palm[5][2][0]=addBPoint(x[5],0.5,0.4,TRUNK,p0);
		palm[6][2][0]=addBPoint(x[6],0.5,0.4,TRUNK,p0);
		palm[7][2][0]=addBPoint(x[7],0.5,0,TRUNK,p0);
		palm[8][2][0]=addBPoint(x[8],0.5,0,TRUNK,p0);
		palm[0][3][0]=addBPoint(x[0],0.75,0.5,TRUNK,p0);
		palm[1][3][0]=addBPoint(x[1],0.75,0.9,TRUNK,p0);
		palm[2][3][0]=addBPoint(x[2],0.75,0.9,TRUNK,p0);
		palm[3][3][0]=addBPoint(x[3],0.75,1.1,TRUNK,p0);
		palm[4][3][0]=addBPoint(x[4],0.75,1.1,TRUNK,p0);
		palm[5][3][0]=addBPoint(x[5],0.75,0.9,TRUNK,p0);
		palm[6][3][0]=addBPoint(x[6],0.75,0.9,TRUNK,p0);
		palm[7][3][0]=addBPoint(x[7],0.75,0.5,TRUNK,p0);
		palm[8][3][0]=addBPoint(x[8],0.75,0.5,TRUNK,p0);
		palm[0][4][0]=addBPoint(x[0],1.0,1.0,TRUNK,p0);
		palm[1][4][0]=addBPoint(x[1],1.0,1.4,TRUNK,p0);
		palm[2][4][0]=addBPoint(x[2],1.0,1.4,TRUNK,p0);
		palm[3][4][0]=addBPoint(x[3],1.0,1.6,TRUNK,p0);
		palm[4][4][0]=addBPoint(x[4],1.0,1.6,TRUNK,p0);
		palm[5][4][0]=addBPoint(x[5],1.0,1.4,TRUNK,p0);
		palm[6][4][0]=addBPoint(x[6],1.0,1.4,TRUNK,p0);
		palm[7][4][0]=addBPoint(x[7],1.0,1.0,TRUNK,p0);
		palm[8][4][0]=addBPoint(x[8],1.0,1.0,TRUNK,p0);
		
		palm[0][0][1]=addBPoint(x[0],0,1.0,TRUNK,p0);
		palm[1][0][1]=addBPoint(x[1],0.0,1.4,TRUNK,p0);
		palm[2][0][1]=addBPoint(x[2],0.0,1.4,TRUNK,p0);
		palm[3][0][1]=addBPoint(x[3],0.0,1.6,TRUNK,p0);
		palm[4][0][1]=addBPoint(x[4],0.0,1.6,TRUNK,p0);
		palm[5][0][1]=addBPoint(x[5],0.0,1.4,TRUNK,p0);
		palm[6][0][1]=addBPoint(x[6],0.0,1.4,TRUNK,p0);
		palm[7][0][1]=addBPoint(x[7],0.0,1.0,TRUNK,p0);
		palm[8][0][1]=addBPoint(x[8],0.0,1.0,TRUNK,p0);
		palm[0][1][1]=addBPoint(x[0],0.25,1.0,TRUNK,p0);
		palm[1][1][1]=addBPoint(x[1],0.25,1.4,TRUNK,p0);
		palm[2][1][1]=addBPoint(x[2],0.25,1.4,TRUNK,p0);
		palm[3][1][1]=addBPoint(x[3],0.25,1.6,TRUNK,p0);
		palm[4][1][1]=addBPoint(x[4],0.25,1.6,TRUNK,p0);
		palm[5][1][1]=addBPoint(x[5],0.25,1.4,TRUNK,p0);
		palm[6][1][1]=addBPoint(x[6],0.25,1.4,TRUNK,p0);
		palm[7][1][1]=addBPoint(x[7],0.25,1.0,TRUNK,p0);
		palm[8][1][1]=addBPoint(x[8],0.25,1.0,TRUNK,p0);
		palm[0][2][1]=addBPoint(x[0],0.5,1.0,TRUNK,p0);
		palm[1][2][1]=addBPoint(x[1],0.5,1.4,TRUNK,p0);
		palm[2][2][1]=addBPoint(x[2],0.5,1.4,TRUNK,p0);
		palm[3][2][1]=addBPoint(x[3],0.5,1.6,TRUNK,p0);
		palm[4][2][1]=addBPoint(x[4],0.5,1.6,TRUNK,p0);
		palm[5][2][1]=addBPoint(x[5],0.5,1.4,TRUNK,p0);
		palm[6][2][1]=addBPoint(x[6],0.5,1.4,TRUNK,p0);
		palm[7][2][1]=addBPoint(x[7],0.5,1.0,TRUNK,p0);
		palm[8][2][1]=addBPoint(x[8],0.5,1.0,TRUNK,p0);
		palm[0][3][1]=addBPoint(x[0],0.75,1.5,TRUNK,p0);
		palm[1][3][1]=addBPoint(x[1],0.75,1.9,TRUNK,p0);
		palm[2][3][1]=addBPoint(x[2],0.75,1.9,TRUNK,p0);
		palm[3][3][1]=addBPoint(x[3],0.75,2.1,TRUNK,p0);
		palm[4][3][1]=addBPoint(x[4],0.75,2.1,TRUNK,p0);
		palm[5][3][1]=addBPoint(x[5],0.75,1.9,TRUNK,p0);
		palm[6][3][1]=addBPoint(x[6],0.75,1.9,TRUNK,p0);
		palm[7][3][1]=addBPoint(x[7],0.75,1.5,TRUNK,p0);
		palm[8][3][1]=addBPoint(x[8],0.75,1.5,TRUNK,p0);
		palm[0][4][1]=addBPoint(x[0],1.0,2.0,TRUNK,p0);
		palm[1][4][1]=addBPoint(x[1],1.0,2.4,TRUNK,p0);
		palm[2][4][1]=addBPoint(x[2],1.0,2.4,TRUNK,p0);
		palm[3][4][1]=addBPoint(x[3],1.0,2.6,TRUNK,p0);
		palm[4][4][1]=addBPoint(x[4],1.0,2.6,TRUNK,p0);
		palm[5][4][1]=addBPoint(x[5],1.0,2.4,TRUNK,p0);
		palm[6][4][1]=addBPoint(x[6],1.0,2.4,TRUNK,p0);
		palm[7][4][1]=addBPoint(x[7],1.0,2.0,TRUNK,p0);
		palm[8][4][1]=addBPoint(x[8],1.0,2.0,TRUNK,p0);

		
		
		for (int i = 0; i < pnx-1; i++) {


			for (int j = 0; j < pny-1; j++) {

				for (int k = 0; k < pnz-1; k++) {

                    //if(j!=0) continue;


					if(i==0){

						LineData leftLD=addLine(palm[i][j][k],palm[i][j][k+1],palm[i][j+1][k+1],palm[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						LineData bottomLD=addLine(palm[i][j][k],palm[i][j+1][k],palm[i+1][j+1][k],palm[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==pnz-1){
						LineData topLD=addLine(palm[i][j][k+1],palm[i+1][j][k+1],palm[i+1][j+1][k+1],palm[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						LineData backLD=addLine(palm[i][j][k],palm[i+1][j][k],palm[i+1][j][k+1],palm[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==pny-1){
						LineData frontLD=addLine(palm[i][j+1][k],palm[i][j+1][k+1],palm[i+1][j+1][k+1],palm[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==pnx-1){

						LineData rightLD=addLine(palm[i+1][j][k],palm[i+1][j+1][k],palm[i+1][j+1][k+1],palm[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		int ffnx=2;
		int ffny=4;
		int ffnz=2;
		
		BPoint[][][] foreFinger=new BPoint[ffnx][ffny][ffnz];
		
		Segments ff0=new Segments(mx[0]*x_side,df*x_side,y_side,y_side*0.9,z_side,z_side);
		
		foreFinger[0][0][0]=palm[1][pny-1][0];
		foreFinger[1][0][0]=palm[2][pny-1][0];
		foreFinger[0][0][1]=palm[1][pny-1][1];
		foreFinger[1][0][1]=palm[2][pny-1][1];
		
		foreFinger[0][1][0]=addBPoint(-0.5,0.5,0.4,LEFT_HOMERUS,ff0);
		foreFinger[1][1][0]=addBPoint(0.5,0.5,0.4,LEFT_HOMERUS,ff0);
		foreFinger[0][1][1]=addBPoint(-0.5,0.5,1.4,LEFT_HOMERUS,ff0);
		foreFinger[1][1][1]=addBPoint(0.5,0.5,1.4,LEFT_HOMERUS,ff0);
		
		foreFinger[0][2][0]=addBPoint(-0.5,0.75,0.4,LEFT_HOMERUS,ff0);
		foreFinger[1][2][0]=addBPoint(0.5,0.75,0.4,LEFT_HOMERUS,ff0);
		foreFinger[0][2][1]=addBPoint(-0.5,0.75,1.4,LEFT_HOMERUS,ff0);
		foreFinger[1][2][1]=addBPoint(0.5,0.75,1.4,LEFT_HOMERUS,ff0);
		
		foreFinger[0][3][0]=addBPoint(-0.4,1.0,0.4,LEFT_HOMERUS,ff0);
		foreFinger[1][3][0]=addBPoint(0.4,1.0,0.4,LEFT_HOMERUS,ff0);
		foreFinger[0][3][1]=addBPoint(-0.4,1.0,1.4,LEFT_HOMERUS,ff0);
		foreFinger[1][3][1]=addBPoint(0.4,1.0,1.4,LEFT_HOMERUS,ff0);
		
		
		
		for (int i = 0; i < ffnx-1; i++) {


			for (int j = 0; j < ffny-1; j++) {

				for (int k = 0; k < ffnz-1; k++) {




					if(i==0){

						LineData leftLD=addLine(foreFinger[i][j][k],foreFinger[i][j][k+1],foreFinger[i][j+1][k+1],foreFinger[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						LineData bottomLD=addLine(foreFinger[i][j][k],foreFinger[i][j+1][k],foreFinger[i+1][j+1][k],foreFinger[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==ffnz-1){
						LineData topLD=addLine(foreFinger[i][j][k+1],foreFinger[i+1][j][k+1],foreFinger[i+1][j+1][k+1],foreFinger[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						LineData backLD=addLine(foreFinger[i][j][k],foreFinger[i+1][j][k],foreFinger[i+1][j][k+1],foreFinger[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==ffny-1){
						LineData frontLD=addLine(foreFinger[i][j+1][k],foreFinger[i][j+1][k+1],foreFinger[i+1][j+1][k+1],foreFinger[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==ffnx-1){

						LineData rightLD=addLine(foreFinger[i+1][j][k],foreFinger[i+1][j+1][k],foreFinger[i+1][j+1][k+1],foreFinger[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}

		
		int mfnx=2;
		int mfny=4;
		int mfnz=2;
		
		BPoint[][][] middleFinger=new BPoint[mfnx][mfny][mfnz];
		
		Segments mf0=new Segments(mx[1]*x_side,df*x_side,y_side,y_side,z_side,z_side);
		
		middleFinger[0][0][0]=palm[3][pny-1][0];
		middleFinger[1][0][0]=palm[4][pny-1][0];
		middleFinger[0][0][1]=palm[3][pny-1][1];
		middleFinger[1][0][1]=palm[4][pny-1][1];
		
		middleFinger[0][1][0]=addBPoint(-0.5,0.5,0.6,LEFT_FEMUR,mf0);
		middleFinger[1][1][0]=addBPoint(0.5,0.5,0.6,LEFT_FEMUR,mf0);
		middleFinger[0][1][1]=addBPoint(-0.5,0.5,1.6,LEFT_FEMUR,mf0);
		middleFinger[1][1][1]=addBPoint(0.5,0.5,1.6,LEFT_FEMUR,mf0);
		
		middleFinger[0][2][0]=addBPoint(-0.5,0.75,0.6,LEFT_FEMUR,mf0);
		middleFinger[1][2][0]=addBPoint(0.5,0.75,0.6,LEFT_FEMUR,mf0);
		middleFinger[0][2][1]=addBPoint(-0.5,0.75,1.6,LEFT_FEMUR,mf0);
		middleFinger[1][2][1]=addBPoint(0.5,0.75,1.6,LEFT_FEMUR,mf0);
		
		middleFinger[0][3][0]=addBPoint(-0.4,1.0,0.6,LEFT_FEMUR,mf0);
		middleFinger[1][3][0]=addBPoint(0.4,1.0,0.6,LEFT_FEMUR,mf0);
		middleFinger[0][3][1]=addBPoint(-0.4,1.0,1.6,LEFT_FEMUR,mf0);
		middleFinger[1][3][1]=addBPoint(0.4,1.0,1.6,LEFT_FEMUR,mf0);
		
		for (int i = 0; i < mfnx-1; i++) {


			for (int j = 0; j < mfny-1; j++) {

				for (int k = 0; k < mfnz-1; k++) {




					if(i==0){

						LineData leftLD=addLine(middleFinger[i][j][k],middleFinger[i][j][k+1],middleFinger[i][j+1][k+1],middleFinger[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						LineData bottomLD=addLine(middleFinger[i][j][k],middleFinger[i][j+1][k],middleFinger[i+1][j+1][k],middleFinger[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==mfnz-1){
						LineData topLD=addLine(middleFinger[i][j][k+1],middleFinger[i+1][j][k+1],middleFinger[i+1][j+1][k+1],middleFinger[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						LineData backLD=addLine(middleFinger[i][j][k],middleFinger[i+1][j][k],middleFinger[i+1][j][k+1],middleFinger[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==mfny-1){
						LineData frontLD=addLine(middleFinger[i][j+1][k],middleFinger[i][j+1][k+1],middleFinger[i+1][j+1][k+1],middleFinger[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==mfnx-1){

						LineData rightLD=addLine(middleFinger[i+1][j][k],middleFinger[i+1][j+1][k],middleFinger[i+1][j+1][k+1],middleFinger[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		
		int rfnx=2;
		int rfny=4;
		int rfnz=2;
		
		BPoint[][][] ringFinger=new BPoint[rfnx][rfny][rfnz];
		
		Segments rf0=new Segments(mx[2]*x_side,df*x_side,y_side,y_side*0.8,z_side,z_side);
		
		ringFinger[0][0][0]=palm[5][pny-1][0];
		ringFinger[1][0][0]=palm[6][pny-1][0];
		ringFinger[0][0][1]=palm[5][pny-1][1];
		ringFinger[1][0][1]=palm[6][pny-1][1];
		
		ringFinger[0][1][0]=addBPoint(-0.5,0.5,0.4,RIGHT_FEMUR,rf0);
		ringFinger[1][1][0]=addBPoint(0.5,0.5,0.4,RIGHT_FEMUR,rf0);
		ringFinger[0][1][1]=addBPoint(-0.5,0.5,1.4,RIGHT_FEMUR,rf0);
		ringFinger[1][1][1]=addBPoint(0.5,0.5,1.4,RIGHT_FEMUR,rf0);

		ringFinger[0][2][0]=addBPoint(-0.5,0.75,0.4,RIGHT_FEMUR,rf0);
		ringFinger[1][2][0]=addBPoint(0.5,0.75,0.4,RIGHT_FEMUR,rf0);
		ringFinger[0][2][1]=addBPoint(-0.5,0.75,1.4,RIGHT_FEMUR,rf0);
		ringFinger[1][2][1]=addBPoint(0.5,0.75,1.4,RIGHT_FEMUR,rf0);
		
		ringFinger[0][3][0]=addBPoint(-0.4,1.0,0.4,RIGHT_FEMUR,rf0);
		ringFinger[1][3][0]=addBPoint(0.4,1.0,0.4,RIGHT_FEMUR,rf0);
		ringFinger[0][3][1]=addBPoint(-0.4,1.0,1.4,RIGHT_FEMUR,rf0);
		ringFinger[1][3][1]=addBPoint(0.4,1.0,1.4,RIGHT_FEMUR,rf0);
		
		for (int i = 0; i < rfnx-1; i++) {


			for (int j = 0; j < rfny-1; j++) {

				for (int k = 0; k < rfnz-1; k++) {




					if(i==0){

						LineData leftLD=addLine(ringFinger[i][j][k],ringFinger[i][j][k+1],ringFinger[i][j+1][k+1],ringFinger[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						LineData bottomLD=addLine(ringFinger[i][j][k],ringFinger[i][j+1][k],ringFinger[i+1][j+1][k],ringFinger[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==rfnz-1){
						LineData topLD=addLine(ringFinger[i][j][k+1],ringFinger[i+1][j][k+1],ringFinger[i+1][j+1][k+1],ringFinger[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						LineData backLD=addLine(ringFinger[i][j][k],ringFinger[i+1][j][k],ringFinger[i+1][j][k+1],ringFinger[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==rfny-1){
						LineData frontLD=addLine(ringFinger[i][j+1][k],ringFinger[i][j+1][k+1],ringFinger[i+1][j+1][k+1],ringFinger[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==rfnx-1){

						LineData rightLD=addLine(ringFinger[i+1][j][k],ringFinger[i+1][j+1][k],ringFinger[i+1][j+1][k+1],ringFinger[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		
		int lfnx=2;
		int lfny=4;
		int lfnz=2;
		
		BPoint[][][] littleFinger=new BPoint[lfnx][lfny][lfnz];
		
		Segments lf0=new Segments(mx[3]*x_side,df*x_side,y_side,y_side*0.7,z_side,z_side);
		
		littleFinger[0][0][0]=palm[7][pny-1][0];
		littleFinger[1][0][0]=palm[8][pny-1][0];
		littleFinger[0][0][1]=palm[7][pny-1][1];
		littleFinger[1][0][1]=palm[8][pny-1][1];
		
		littleFinger[0][1][0]=addBPoint(-0.5,0.5,0,RIGHT_HOMERUS,lf0);
		littleFinger[1][1][0]=addBPoint(0.5,0.5,0,RIGHT_HOMERUS,lf0);
		littleFinger[0][1][1]=addBPoint(-0.5,0.5,1.0,RIGHT_HOMERUS,lf0);
		littleFinger[1][1][1]=addBPoint(0.5,0.5,1.0,RIGHT_HOMERUS,lf0);
		
		littleFinger[0][2][0]=addBPoint(-0.5,0.75,0,RIGHT_HOMERUS,lf0);
		littleFinger[1][2][0]=addBPoint(0.5,0.75,0,RIGHT_HOMERUS,lf0);
		littleFinger[0][2][1]=addBPoint(-0.5,0.75,1.0,RIGHT_HOMERUS,lf0);
		littleFinger[1][2][1]=addBPoint(0.5,0.75,1.0,RIGHT_HOMERUS,lf0);
		
		littleFinger[0][3][0]=addBPoint(-0.4,1.0,0,RIGHT_HOMERUS,lf0);
		littleFinger[1][3][0]=addBPoint(0.4,1.0,0,RIGHT_HOMERUS,lf0);
		littleFinger[0][3][1]=addBPoint(-0.4,1.0,1.0,RIGHT_HOMERUS,lf0);
		littleFinger[1][3][1]=addBPoint(0.4,1.0,1.0,RIGHT_HOMERUS,lf0);
		
		
		for (int i = 0; i < lfnx-1; i++) {


			for (int j = 0; j < lfny-1; j++) {

				for (int k = 0; k < lfnz-1; k++) {




					if(i==0){

						LineData leftLD=addLine(littleFinger[i][j][k],littleFinger[i][j][k+1],littleFinger[i][j+1][k+1],littleFinger[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						LineData bottomLD=addLine(littleFinger[i][j][k],littleFinger[i][j+1][k],littleFinger[i+1][j+1][k],littleFinger[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==lfnz-1){
						LineData topLD=addLine(littleFinger[i][j][k+1],littleFinger[i+1][j][k+1],littleFinger[i+1][j+1][k+1],littleFinger[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						LineData backLD=addLine(littleFinger[i][j][k],littleFinger[i+1][j][k],littleFinger[i+1][j][k+1],littleFinger[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==lfny-1){
						LineData frontLD=addLine(littleFinger[i][j+1][k],littleFinger[i][j+1][k+1],littleFinger[i+1][j+1][k+1],littleFinger[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==lfnx-1){

						LineData rightLD=addLine(littleFinger[i+1][j][k],littleFinger[i+1][j+1][k],littleFinger[i+1][j+1][k+1],littleFinger[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		int tnx=2;
		int tny=5;
		int tnz=2;
		
		BPoint[][][] thumb=new BPoint[tnx][tny][tnz];
		
		
		double dl0=1.125*y_side*0.5;
		double dl1=1.125*y_side*0.5;
		double dl1p=Math.sqrt(dl1*dl1-(df*x_side)*(df*x_side));
		
		
		double thumb_dy=dl0+dl1p;
		double df1=dl1p/thumb_dy;
		
		Segments tf0=new Segments(xc,df*2*x_side,y_side*0.25,thumb_dy,0,z_side);
		
		double thumb_y0=-y_side*0.25/thumb_dy;
		
		
		thumb[0][0][0]=addBPoint(-0.5,thumb_y0,0,TRUNK,tf0);		
		thumb[1][0][0]=addBPoint(0.0,thumb_y0,0.0,TRUNK,tf0);
		thumb[0][0][1]=addBPoint(-0.5,thumb_y0,1.0,TRUNK,tf0);
		thumb[1][0][1]=addBPoint(0.0,thumb_y0,1.0,TRUNK,tf0);
		
		thumb[0][1][0]=addBPoint(-0.5,0.0,0,TRUNK,tf0);		
		thumb[1][1][0]=addBPoint(0.0,0.0,0.0,TRUNK,tf0);
		thumb[0][1][1]=addBPoint(-0.5,0.0,1.0,TRUNK,tf0);
		thumb[1][1][1]=addBPoint(0.0,0.0,1.0,TRUNK,tf0);
		
		thumb[0][2][0]=addBPoint(-1.0,df1,0,TRUNK,tf0);		
		thumb[1][2][0]=addBPoint(-0.5,df1,0.0,TRUNK,tf0);
		thumb[0][2][1]=addBPoint(-1.0,df1,1.0,TRUNK,tf0);
		thumb[1][2][1]=addBPoint(-0.5,df1,1.0,TRUNK,tf0);
		
		thumb[0][3][0]=addBPoint(-1.0,0.75,0,TRUNK,tf0);		
		thumb[1][3][0]=addBPoint(-0.5,0.75,0.0,TRUNK,tf0);
		thumb[0][3][1]=addBPoint(-1.0,0.75,1.0,TRUNK,tf0);
		thumb[1][3][1]=addBPoint(-0.5,0.75,1.0,TRUNK,tf0);
		
		thumb[0][4][0]=addBPoint(-1.0,1.0,0,TRUNK,tf0);		
		thumb[1][4][0]=addBPoint(-0.5,1.0,0.0,TRUNK,tf0);
		thumb[0][4][1]=addBPoint(-1.0,1.0,1.0,TRUNK,tf0);
		thumb[1][4][1]=addBPoint(-0.5,1.0,1.0,TRUNK,tf0);

		
		for (int i = 0; i < tnx-1; i++) {


			for (int j = 0; j < tny-1; j++) {

				for (int k = 0; k < tnz-1; k++) {




					if(i==0){

						LineData leftLD=addLine(thumb[i][j][k],thumb[i][j][k+1],thumb[i][j+1][k+1],thumb[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						LineData bottomLD=addLine(thumb[i][j][k],thumb[i][j+1][k],thumb[i+1][j+1][k],thumb[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==tnz-1){
						LineData topLD=addLine(thumb[i][j][k+1],thumb[i+1][j][k+1],thumb[i+1][j+1][k+1],thumb[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						LineData backLD=addLine(thumb[i][j][k],thumb[i+1][j][k],thumb[i+1][j][k+1],thumb[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==tny-1){
						LineData frontLD=addLine(thumb[i][j+1][k],thumb[i][j+1][k+1],thumb[i+1][j+1][k+1],thumb[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==tnx-1){

						if(j>=2){
							LineData rightLD=addLine(thumb[i+1][j][k],thumb[i+1][j+1][k],thumb[i+1][j+1][k+1],thumb[i+1][j][k+1],Renderer3D.CAR_RIGHT);
						}
					}
				}
			}

		}
		//additional thumb polygons:
		addLine(thumb[1][1][0],thumb[1][2][0],palm[0][3][0],null,Renderer3D.CAR_BOTTOM);		
		addLine(thumb[1][2][0],thumb[1][2][1],palm[0][3][1],palm[0][3][0],Renderer3D.CAR_FRONT);
		addLine(palm[0][3][1],thumb[1][2][1],thumb[1][1][1],null,Renderer3D.CAR_TOP);
		
		PolygonMesh pm=new PolygonMesh(points,polyData);
		
		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}






}	