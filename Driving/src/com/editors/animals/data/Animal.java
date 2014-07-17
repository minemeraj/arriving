package com.editors.animals.data;

import java.util.Vector;

import com.BPoint;
import com.CustomData;
import com.LineData;
import com.Point3D;
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
		
		double q_angle=12*2*Math.PI/360.0;

		points=new Vector();
		points.setSize(400);

		polyData=new Vector();

		n=0;

		double xc=0.0;
		double yc=y_side*0.5;

		//body:
		
	
		double SHOULDER_DX=leg_side*0.5;	
		double SHOULDER_WIDTH=SHOULDER_DX+x_side*0.5;		
		double WAIST_DX=x_side*0.1;
		double WAIST_WIDTH=x_side*0.5-WAIST_DX;

		Segments b0=new Segments(xc,x_side,yc,y_side,femur_length+shinbone_length,z_side);
		
		int numx=5;
		int numy=5;
		int numz=7;
		

		
		BPoint[][][] body=new BPoint[numx][numy][numz]; 

		body[0][0][0]=addBPoint(-0.5,-0.5,0.1,TRUNK,b0);
		body[1][0][0]=addBPoint(-0.15,-0.5,0.0,TRUNK,b0);
		body[2][0][0]=addBPoint(0.0,-0.5,0.0,TRUNK,b0);
		body[3][0][0]=addBPoint(0.15,-0.5,0.0,TRUNK,b0);
		body[4][0][0]=addBPoint(0.5,-0.5,0.1,TRUNK,b0);
		body[0][1][0]=addBPoint(-0.5,-0.25,0.1,TRUNK,b0);
		body[1][1][0]=addBPoint(-0.15,-0.25,0.0,TRUNK,b0);
		body[2][1][0]=addBPoint(0.0,-0.25,0.0,TRUNK,b0);
		body[3][1][0]=addBPoint(0.15,-0.25,0.0,TRUNK,b0);
		body[4][1][0]=addBPoint(0.5,-0.25,0.1,TRUNK,b0);	
		body[0][2][0]=addBPoint(-0.5,0.0,0.1,TRUNK,b0);
		body[1][2][0]=addBPoint(-0.15,0.0,0.0,TRUNK,b0);
		body[2][2][0]=addBPoint(0.0,0.0,0.0,TRUNK,b0);
		body[3][2][0]=addBPoint(0.15,0.0,0.0,TRUNK,b0);
		body[4][2][0]=addBPoint(0.5,0.0,0.1,TRUNK,b0);
		body[0][3][0]=addBPoint(-0.5,0.25,0.1,TRUNK,b0);
		body[1][3][0]=addBPoint(-0.15,0.25,0.0,TRUNK,b0);
		body[2][3][0]=addBPoint(0.0,0.25,0.0,TRUNK,b0);
		body[3][3][0]=addBPoint(0.15,0.25,0.0,TRUNK,b0);
		body[4][3][0]=addBPoint(0.5,0.25,0.1,TRUNK,b0);
		body[0][4][0]=addBPoint(-0.5,0.5,0.1,TRUNK,b0);
		body[1][4][0]=addBPoint(-0.15,0.5,0.0,TRUNK,b0);
		body[2][4][0]=addBPoint(0.0,0.5,0.0,TRUNK,b0);
		body[3][4][0]=addBPoint(0.15,0.5,0.0,TRUNK,b0);
		body[4][4][0]=addBPoint(0.5,0.5,0.1,TRUNK,b0);
		
		body[0][0][1]=addBPoint(-0.5,-0.5,0.25,TRUNK,b0);
		body[1][0][1]=addBPoint(-0.25,-0.5,0.25,TRUNK,b0);
		body[2][0][1]=addBPoint(0.0,-0.5,0.25,TRUNK,b0);
		body[3][0][1]=addBPoint(0.25,-0.5,0.25,TRUNK,b0);
		body[4][0][1]=addBPoint(0.5,-0.5,0.25,TRUNK,b0);		
		body[0][1][1]=addBPoint(-0.5,-0.25,0.25,TRUNK,b0);
		body[4][1][1]=addBPoint(0.5,-0.25,0.25,TRUNK,b0);
		body[0][2][1]=addBPoint(-0.5,0.0,0.25,TRUNK,b0);
		body[4][2][1]=addBPoint(0.5,0.0,0.25,TRUNK,b0);
		body[0][3][1]=addBPoint(-0.5,0.25,0.25,TRUNK,b0);
		body[4][3][1]=addBPoint(0.5,0.25,0.25,TRUNK,b0);
		body[0][4][1]=addBPoint(-0.5,0.5,0.25,TRUNK,b0);
		body[1][4][1]=addBPoint(-0.25,0.5,0.25,TRUNK,b0);
		body[2][4][1]=addBPoint(0.0,0.5,0.25,TRUNK,b0);
		body[3][4][1]=addBPoint(0.25,0.5,0.25,TRUNK,b0);
		body[4][4][1]=addBPoint(0.5,0.5,0.25,TRUNK,b0);
		
		double wr=WAIST_WIDTH/x_side;
		
		body[0][0][2]=addBPoint(-wr,-0.5,0.5,TRUNK,b0);
		body[1][0][2]=addBPoint(-wr*0.5,-0.5,0.5,TRUNK,b0);
		body[2][0][2]=addBPoint(0.0,-0.5,0.5,TRUNK,b0);
		body[3][0][2]=addBPoint(wr*0.5,-0.5,0.5,TRUNK,b0);
		body[4][0][2]=addBPoint(wr,-0.5,0.5,TRUNK,b0);	
		body[0][1][2]=addBPoint(-wr,-0.25,0.5,TRUNK,b0);		
		body[4][1][2]=addBPoint(wr,-0.25,0.5,TRUNK,b0);
		body[0][2][2]=addBPoint(-wr,0.0,0.5,TRUNK,b0);		
		body[4][2][2]=addBPoint(wr,0.0,0.5,TRUNK,b0);
		body[0][3][2]=addBPoint(-wr,0.25,0.5,TRUNK,b0);
		body[4][3][2]=addBPoint(wr,0.25,0.5,TRUNK,b0);
		body[0][4][2]=addBPoint(-wr,0.5,0.5,TRUNK,b0);
		body[1][4][2]=addBPoint(-wr*0.5,0.5,0.5,TRUNK,b0);
		body[2][4][2]=addBPoint(0.0,0.5,0.5,TRUNK,b0);
		body[3][4][2]=addBPoint(wr*0.5,0.5,0.5,TRUNK,b0);
		body[4][4][2]=addBPoint(wr,0.5,0.5,TRUNK,b0);
		
		double sr=SHOULDER_WIDTH/x_side;
		double sw=leg_side/y_side;
		
		body[0][0][3]=addBPoint(-sr,-sw*0.5,0.75,TRUNK,b0);
		body[1][0][3]=addBPoint(-sr*0.5,-0.5,0.75,TRUNK,b0);
		body[2][0][3]=addBPoint(0.0,-0.5,0.75,TRUNK,b0);
		body[3][0][3]=addBPoint(sr*0.5,-0.5,0.75,TRUNK,b0);
		body[4][0][3]=addBPoint(sr,-sw*0.5,0.75,TRUNK,b0);
		body[0][1][3]=addBPoint(-sr,-sw*0.25,0.75,TRUNK,b0);		
		body[4][1][3]=addBPoint(sr,-sw*0.25,0.75,TRUNK,b0);
		body[0][2][3]=addBPoint(-sr,0.0,0.75,TRUNK,b0);	
		body[4][2][3]=addBPoint(sr,0.0,0.75,TRUNK,b0);
		body[0][3][3]=addBPoint(-sr,sw*0.25,0.75,TRUNK,b0);		
		body[4][3][3]=addBPoint(sr,sw*0.25,0.75,TRUNK,b0);
		body[0][4][3]=addBPoint(-sr,sw*0.5,0.75,TRUNK,b0);
		body[1][4][3]=addBPoint(-sr*0.5,0.5,0.75,TRUNK,b0);
		body[2][4][3]=addBPoint(0.0,0.5,0.75,TRUNK,b0);
		body[3][4][3]=addBPoint(sr*0.5,0.5,0.75,TRUNK,b0);
		body[4][4][3]=addBPoint(sr,sw*0.5,0.75,TRUNK,b0);
		
		body[0][0][4]=addBPoint(-sr,-sw*0.5,1.0,TRUNK,b0);
		body[1][0][4]=addBPoint(-sr*0.5,-0.5,1.0,TRUNK,b0);
		body[2][0][4]=addBPoint(0.0,-0.5,1.0,TRUNK,b0);
		body[3][0][4]=addBPoint(sr*0.5,-0.5,1.0,TRUNK,b0);
		body[4][0][4]=addBPoint(sr,-sw*0.5,1.0,TRUNK,b0);
		body[0][1][4]=addBPoint(-sr,-sw*0.25,1.0,TRUNK,b0);		
		body[4][1][4]=addBPoint(sr,-sw*0.25,1.0,TRUNK,b0);
		body[0][2][4]=addBPoint(-sr,0.0,1.0,TRUNK,b0);		
		body[4][2][4]=addBPoint(sr,0.0,1.0,TRUNK,b0);
		body[0][3][4]=addBPoint(-sr,sw*0.25,1.0,TRUNK,b0);		
		body[4][3][4]=addBPoint(sr,sw*0.25,1.0,TRUNK,b0);
		body[0][4][4]=addBPoint(-sr,sw*0.5,1.0,TRUNK,b0);
		body[1][4][4]=addBPoint(-sr*0.5,0.5,1.0,TRUNK,b0);
		body[2][4][4]=addBPoint(0.0,0.5,1.0,TRUNK,b0);
		body[3][4][4]=addBPoint(sr*0.5,0.5,1.0,TRUNK,b0);
		body[4][4][4]=addBPoint(sr,sw*0.5,1.0,TRUNK,b0);
		
		///////////
		//head:
		
		BPoint[][][] head=buildHumanHeadMesh(xc);
			
		Segments n0=new Segments(xc,neck_side,yc,y_side,femur_length+shinbone_length+z_side,neck_length);

		body[0][0][5]=addBPoint(-0.5,-0.5,0.5,TRUNK,n0);
		body[1][0][5]=addBPoint(-0.25,-0.5,0.5,TRUNK,n0);
		body[2][0][5]=addBPoint(0.0,-0.5,0.5,TRUNK,n0);
		body[3][0][5]=addBPoint(0.25,-0.5,0.5,TRUNK,n0);
		body[4][0][5]=addBPoint(0.5,-0.5,0.5,TRUNK,n0);
		body[0][1][5]=addBPoint(-0.5,-0.25,0.5,TRUNK,n0);		
		body[4][1][5]=addBPoint(0.5,-0.25,0.5,TRUNK,n0);
		body[0][2][5]=addBPoint(-0.5,0.0,0.5,TRUNK,n0);		
		body[4][2][5]=addBPoint(0.5,0.0,0.5,TRUNK,n0);
		body[0][3][5]=addBPoint(-0.5,0.25,0.5,TRUNK,n0);
		body[4][3][5]=addBPoint(0.5,0.25,0.5,TRUNK,n0);
		body[0][4][5]=addBPoint(-0.5,0.5,0.5,TRUNK,n0);
		body[1][4][5]=addBPoint(-0.25,0.5,0.5,TRUNK,n0);
		body[2][4][5]=addBPoint(0.0,0.5,0.5,TRUNK,n0);
		body[3][4][5]=addBPoint(0.25,0.5,0.5,TRUNK,n0);
		body[4][4][5]=addBPoint(0.5,0.5,0.5,TRUNK,n0);

		body[0][0][6]=head[0][0][0];
		body[1][0][6]=head[1][0][0];
		body[2][0][6]=head[2][0][0];
		body[3][0][6]=head[3][0][0];
		body[4][0][6]=head[4][0][0];		
		body[0][1][6]=head[0][1][0];	
		body[4][1][6]=head[4][1][0];		
		body[0][2][6]=head[0][2][0];
		body[4][2][6]=head[4][2][0];
		body[0][3][6]=head[0][3][0];
		body[4][3][6]=head[4][3][0];
		body[0][4][6]=head[0][4][0];	
		body[1][4][6]=head[1][4][0];
		body[2][4][6]=head[2][4][0];
		body[3][4][6]=head[3][4][0];
		body[4][4][6]=head[4][4][0];
		
		for(int i=0;i<numx-1;i++){

			for (int j = 0; j < numy-1; j++) {

				for(int k=0;k<numz-1;k++){

					if(k==0)
						addLine(body[i][j][k],body[i][j+1][k],body[i+1][j+1][k],body[i+1][j][k],Renderer3D.CAR_BOTTOM);			

					if(i==0)
						addLine(body[i][j][k],body[i][j][k+1],body[i][j+1][k+1],body[i][j+1][k],Renderer3D.CAR_LEFT);

					if(i+1==numx-1)
						addLine(body[i+1][j][k],body[i+1][j+1][k],body[i+1][j+1][k+1],body[i+1][j][k+1],Renderer3D.CAR_RIGHT);
					
					if(j==0)
						addLine(body[i][j][k],body[i+1][j][k],body[i+1][j][k+1],body[i][j][k+1],Renderer3D.CAR_BACK);
					if(j+1==numy-1)
						addLine(body[i][j+1][k],body[i][j+1][k+1],body[i+1][j+1][k+1],body[i+1][j+1][k],Renderer3D.CAR_FRONT);

				}
			}
		}




		//legs:	
		
		double thigh_indentation=femur_length*Math.sin(q_angle);
		double thigh_side=leg_side+thigh_indentation;
		double LEG_DY=(y_side-leg_side)/2.0;

		//LeftLeg
		
		
		BPoint[][][] pBackLeftLeg=new BPoint[2][2][2];

		pBackLeftLeg[0][0][0]=addBPoint(xc-x_side*0.5+thigh_indentation,LEG_DY,0,LEFT_HOMERUS);
		pBackLeftLeg[1][0][0]=addBPoint(xc-x_side*0.5+thigh_indentation+leg_side,LEG_DY,0,LEFT_HOMERUS);
		pBackLeftLeg[1][1][0]=addBPoint(xc-x_side*0.5+thigh_indentation+leg_side,leg_side+LEG_DY,0,LEFT_HOMERUS);
		pBackLeftLeg[0][1][0]=addBPoint(xc-x_side*0.5+thigh_indentation,leg_side+LEG_DY,0,LEFT_HOMERUS);

		addLine(pBackLeftLeg[0][0][0],pBackLeftLeg[0][1][0],pBackLeftLeg[1][1][0],pBackLeftLeg[1][0][0],Renderer3D.CAR_FRONT);


		pBackLeftLeg[0][0][1]=addBPoint(xc-x_side*0.5+thigh_indentation,LEG_DY,shinbone_length,LEFT_SHINBONE);
		pBackLeftLeg[1][0][1]=addBPoint(xc-x_side*0.5+thigh_indentation+leg_side,LEG_DY,shinbone_length,LEFT_SHINBONE);
		pBackLeftLeg[1][1][1]=addBPoint(xc-x_side*0.5+thigh_indentation+leg_side,LEG_DY+leg_side,shinbone_length,LEFT_SHINBONE);
		pBackLeftLeg[0][1][1]=addBPoint(xc-x_side*0.5+thigh_indentation,LEG_DY+leg_side,shinbone_length,LEFT_SHINBONE);


		addLine(pBackLeftLeg[0][0][0],pBackLeftLeg[0][0][1],pBackLeftLeg[0][1][1],pBackLeftLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pBackLeftLeg[0][1][0],pBackLeftLeg[0][1][1],pBackLeftLeg[1][1][1],pBackLeftLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(pBackLeftLeg[1][1][0],pBackLeftLeg[1][1][1],pBackLeftLeg[1][0][1],pBackLeftLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(pBackLeftLeg[1][0][0],pBackLeftLeg[1][0][1],pBackLeftLeg[0][0][1],pBackLeftLeg[0][0][0],Renderer3D.CAR_BACK);
		
		//left thigh
		
		
		BPoint[][][] pBackLeftThigh=new BPoint[2][2][2];

		
		pBackLeftThigh[0][0][0]=pBackLeftLeg[0][0][1];
		pBackLeftThigh[1][0][0]=pBackLeftLeg[1][0][1];
		pBackLeftThigh[1][1][0]=pBackLeftLeg[1][1][1];
		pBackLeftThigh[0][1][0]=pBackLeftLeg[0][1][1];
		
		pBackLeftThigh[0][0][1]=body[0][0][0];
		pBackLeftThigh[1][0][1]=body[1][0][0];
		pBackLeftThigh[1][1][1]=body[1][numy-1][0];
		pBackLeftThigh[0][1][1]=body[0][numy-1][0];

		addLine(pBackLeftThigh[0][0][0],pBackLeftThigh[0][0][1],pBackLeftThigh[0][1][1],pBackLeftThigh[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pBackLeftThigh[0][1][0],pBackLeftThigh[0][1][1],pBackLeftThigh[1][1][1],pBackLeftThigh[1][1][0],Renderer3D.CAR_FRONT);

		addLine(pBackLeftThigh[1][1][0],pBackLeftThigh[1][1][1],pBackLeftThigh[1][0][1],pBackLeftThigh[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(pBackLeftThigh[1][0][0],pBackLeftThigh[1][0][1],pBackLeftThigh[0][0][1],pBackLeftThigh[0][0][0],Renderer3D.CAR_BACK);

		//left foot
		
		BPoint[][][] leftFoot=new BPoint[2][2][2];
		
		leftFoot[0][0][0]=pBackLeftLeg[0][1][0];
		leftFoot[1][0][0]=pBackLeftLeg[1][1][0];
		leftFoot[0][0][1]=addBPoint(xc-x_side*0.5+thigh_indentation,LEG_DY+leg_side,leg_side,LEFT_SHINBONE);
		leftFoot[1][0][1]=addBPoint(xc-x_side*0.5+thigh_indentation+leg_side,LEG_DY+leg_side,leg_side,LEFT_SHINBONE);

		leftFoot[0][1][0]=addBPoint(xc-x_side*0.5+thigh_indentation,LEG_DY+foot_length,0,LEFT_SHINBONE);
		leftFoot[1][1][0]=addBPoint(xc-x_side*0.5+thigh_indentation+leg_side,LEG_DY+foot_length,0,LEFT_SHINBONE);
		
		addLine(leftFoot[0][0][0],leftFoot[0][0][1],leftFoot[0][1][0],null,Renderer3D.CAR_LEFT);
		addLine(leftFoot[1][0][0],leftFoot[1][1][0],leftFoot[1][0][1],null,Renderer3D.CAR_RIGHT);
		addLine(leftFoot[0][0][0],leftFoot[0][1][0],leftFoot[1][1][0],leftFoot[1][0][0],Renderer3D.CAR_BOTTOM);
		addLine(leftFoot[0][0][1],leftFoot[1][0][1],leftFoot[1][1][0],leftFoot[0][1][0],Renderer3D.CAR_TOP);
		
		///RightLeg
		
		BPoint[][][] pBackRightLeg=new BPoint[2][2][2];

		pBackRightLeg[0][0][0]=addBPoint(xc+x_side*0.5-leg_side-thigh_indentation,LEG_DY,0,RIGHT_FEMUR);
		pBackRightLeg[1][0][0]=addBPoint(xc+x_side*0.5-thigh_indentation,LEG_DY,0,RIGHT_FEMUR);
		pBackRightLeg[1][1][0]=addBPoint(xc+x_side*0.5-thigh_indentation,LEG_DY+leg_side,0,RIGHT_FEMUR);
		pBackRightLeg[0][1][0]=addBPoint(xc+x_side*0.5-leg_side-thigh_indentation,LEG_DY+leg_side,0,RIGHT_FEMUR);

		addLine(pBackRightLeg[0][0][0],pBackRightLeg[0][1][0],pBackRightLeg[1][1][0],pBackRightLeg[1][0][0],Renderer3D.CAR_FRONT);

		pBackRightLeg[0][0][1]=addBPoint(xc+x_side*0.5-leg_side-thigh_indentation,LEG_DY,shinbone_length,RIGHT_SHINBONE);
		pBackRightLeg[1][0][1]=addBPoint(xc+x_side*0.5-thigh_indentation,LEG_DY,shinbone_length,RIGHT_SHINBONE);
		pBackRightLeg[1][1][1]=addBPoint(xc+x_side*0.5-thigh_indentation,LEG_DY+leg_side,shinbone_length,RIGHT_SHINBONE);
		pBackRightLeg[0][1][1]=addBPoint(xc+x_side*0.5-leg_side-thigh_indentation,LEG_DY+leg_side,shinbone_length,RIGHT_SHINBONE);

		addLine(pBackRightLeg[0][0][0],pBackRightLeg[0][0][1],pBackRightLeg[0][1][1],pBackRightLeg[0][1][0],Renderer3D.CAR_LEFT);
	
		addLine(pBackRightLeg[0][1][0],pBackRightLeg[0][1][1],pBackRightLeg[1][1][1],pBackRightLeg[1][1][0],Renderer3D.CAR_FRONT);
	
		addLine(pBackRightLeg[1][1][0],pBackRightLeg[1][1][1],pBackRightLeg[1][0][1],pBackRightLeg[1][0][0],Renderer3D.CAR_RIGHT);
		
		addLine(pBackRightLeg[1][0][0],pBackRightLeg[1][0][1],pBackRightLeg[0][0][1],pBackRightLeg[0][0][0],Renderer3D.CAR_BACK);
	
		
		//right thigh
		
		BPoint[][][] pBackRightThigh=new BPoint[2][2][2];
		
		pBackRightThigh[0][0][0]=pBackRightLeg[0][0][1];
		pBackRightThigh[1][0][0]=pBackRightLeg[1][0][1];
		pBackRightThigh[1][1][0]=pBackRightLeg[1][1][1];
		pBackRightThigh[0][1][0]=pBackRightLeg[0][1][1];
		
		pBackRightThigh[0][0][1]=body[numx-2][0][0];
		pBackRightThigh[1][0][1]=body[numx-1][0][0];
		pBackRightThigh[1][1][1]=body[numx-1][numy-1][0];
		pBackRightThigh[0][1][1]=body[numx-2][numy-1][0];

		addLine(pBackRightThigh[0][0][0],pBackRightThigh[0][0][1],pBackRightThigh[0][1][1],pBackRightThigh[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pBackRightThigh[0][1][0],pBackRightThigh[0][1][1],pBackRightThigh[1][1][1],pBackRightThigh[1][1][0],Renderer3D.CAR_FRONT);

		addLine(pBackRightThigh[1][1][0],pBackRightThigh[1][1][1],pBackRightThigh[1][0][1],pBackRightThigh[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(pBackRightThigh[1][0][0],pBackRightThigh[1][0][1],pBackRightThigh[0][0][1],pBackRightThigh[0][0][0],Renderer3D.CAR_BACK);
	
		//right foot
		
		BPoint[][][] rightFoot=new BPoint[2][2][2];
		
		rightFoot[0][0][0]=pBackRightLeg[0][1][0];
		rightFoot[1][0][0]=pBackRightLeg[1][1][0];
		rightFoot[0][0][1]=addBPoint(xc+x_side*0.5-leg_side-thigh_indentation,LEG_DY+leg_side,leg_side,RIGHT_SHINBONE);
		rightFoot[1][0][1]=addBPoint(xc+x_side*0.5-thigh_indentation,LEG_DY+leg_side,leg_side,RIGHT_SHINBONE);

		rightFoot[0][1][0]=addBPoint(xc+x_side*0.5-leg_side-thigh_indentation,LEG_DY+foot_length,0,RIGHT_SHINBONE);
		rightFoot[1][1][0]=addBPoint(xc+x_side*0.5-thigh_indentation,LEG_DY+foot_length,0,RIGHT_SHINBONE);
		
		addLine(rightFoot[0][0][0],rightFoot[0][0][1],rightFoot[0][1][0],null,Renderer3D.CAR_LEFT);
		addLine(rightFoot[1][0][0],rightFoot[1][1][0],rightFoot[1][0][1],null,Renderer3D.CAR_RIGHT);
		addLine(rightFoot[0][0][0],rightFoot[0][1][0],rightFoot[1][1][0],rightFoot[1][0][0],Renderer3D.CAR_BOTTOM);
		addLine(rightFoot[0][0][1],rightFoot[1][0][1],rightFoot[1][1][0],rightFoot[0][1][0],Renderer3D.CAR_TOP);
		
		
		//Arms:
		
		//Left fore arm
		
		double ax=x_side*0.5+SHOULDER_DX+leg_side;
		double az=femur_length+shinbone_length+z_side-humerus_length-radius_length;
		double ay=(y_side-leg_side)/2.0;
		
		
		BPoint[][][] pFrontLeftForearm=new BPoint[2][2][2];
		
		pFrontLeftForearm[0][0][0]=addBPoint(xc-ax,ay,az,LEFT_RADIUS);
		pFrontLeftForearm[1][0][0]=addBPoint(xc-ax+leg_side,ay,az,LEFT_RADIUS);
		pFrontLeftForearm[1][1][0]=addBPoint(xc-ax+leg_side,ay+leg_side,az,LEFT_RADIUS);
		pFrontLeftForearm[0][1][0]=addBPoint(xc-ax,ay+leg_side,az,LEFT_RADIUS);

	

		
		pFrontLeftForearm[0][0][1]=addBPoint(xc-ax,ay,az+radius_length,LEFT_RADIUS);
		pFrontLeftForearm[1][0][1]=addBPoint(xc-ax+leg_side,ay,az+radius_length,LEFT_RADIUS);
		pFrontLeftForearm[1][1][1]=addBPoint(xc-ax+leg_side,ay+leg_side,az+radius_length,LEFT_RADIUS);
		pFrontLeftForearm[0][1][1]=addBPoint(xc-ax,ay+leg_side,az+radius_length,LEFT_RADIUS);
		

		
		addLine(pFrontLeftForearm[0][0][0],pFrontLeftForearm[0][0][1],pFrontLeftForearm[0][1][1],pFrontLeftForearm[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pFrontLeftForearm[0][1][0],pFrontLeftForearm[0][1][1],pFrontLeftForearm[1][1][1],pFrontLeftForearm[1][1][0],Renderer3D.CAR_FRONT);

		addLine(pFrontLeftForearm[1][1][0],pFrontLeftForearm[1][1][1],pFrontLeftForearm[1][0][1],pFrontLeftForearm[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(pFrontLeftForearm[1][0][0],pFrontLeftForearm[1][0][1],pFrontLeftForearm[0][0][1],pFrontLeftForearm[0][0][0],Renderer3D.CAR_BACK);
		
		
		//left arm
		
		BPoint[][][] pFrontLeftArm=new BPoint[2][2][2];
		
		pFrontLeftArm[0][0][0]=addBPoint(xc-ax,ay,body[0][0][3].z,LEFT_HOMERUS);
		pFrontLeftArm[1][0][0]=addBPoint(xc-ax+leg_side,ay,body[0][0][3].z,LEFT_HOMERUS);
		pFrontLeftArm[1][1][0]=addBPoint(xc-ax+leg_side,ay+leg_side,body[0][0][3].z,LEFT_HOMERUS);
		pFrontLeftArm[0][1][0]=addBPoint(xc-ax,ay+leg_side,body[0][0][3].z,LEFT_HOMERUS);
		
		
		pFrontLeftArm[0][0][1]=addBPoint(xc-ax,ay,body[0][0][4].z,LEFT_HOMERUS);
		pFrontLeftArm[1][0][1]=addBPoint(xc-ax+leg_side,ay,body[0][0][4].z,LEFT_HOMERUS);
		pFrontLeftArm[1][1][1]=addBPoint(xc-ax+leg_side,ay+leg_side,body[0][0][4].z,LEFT_HOMERUS);
		pFrontLeftArm[0][1][1]=addBPoint(xc-ax,ay+leg_side,body[0][0][4].z,LEFT_HOMERUS);
			
		addLine(pFrontLeftForearm[0][0][1],pFrontLeftArm[0][0][0],pFrontLeftArm[0][1][0],pFrontLeftForearm[0][1][1],Renderer3D.CAR_LEFT);

		addLine(pFrontLeftForearm[0][1][1],pFrontLeftArm[0][1][0],pFrontLeftArm[1][1][0],pFrontLeftForearm[1][1][1],Renderer3D.CAR_FRONT);

		addLine(pFrontLeftForearm[1][1][1],pFrontLeftArm[1][1][0],pFrontLeftArm[1][0][0],pFrontLeftForearm[1][0][1],Renderer3D.CAR_RIGHT);

		addLine(pFrontLeftForearm[1][0][1],pFrontLeftArm[1][0][0],pFrontLeftArm[0][0][0],pFrontLeftForearm[0][0][1],Renderer3D.CAR_BACK);
		
		
		addLine(pFrontLeftArm[0][0][1],pFrontLeftArm[1][0][1],pFrontLeftArm[1][1][1],pFrontLeftArm[0][1][1],Renderer3D.CAR_TOP);
		
		
		addLine(pFrontLeftArm[0][0][0],pFrontLeftArm[0][0][1],pFrontLeftArm[0][1][1],pFrontLeftArm[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pFrontLeftArm[0][1][0],pFrontLeftArm[0][1][1],pFrontLeftArm[1][1][1],pFrontLeftArm[1][1][0],Renderer3D.CAR_FRONT);

		addLine(pFrontLeftArm[1][1][0],pFrontLeftArm[1][1][1],pFrontLeftArm[1][0][1],pFrontLeftArm[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(pFrontLeftArm[1][0][0],pFrontLeftArm[1][0][1],pFrontLeftArm[0][0][1],pFrontLeftArm[0][0][0],Renderer3D.CAR_BACK);
	
		//left hand
		
		BPoint[][][] pLeftHand=new BPoint[2][2][2];	
		
		pLeftHand[0][0][1]=pFrontLeftForearm[0][0][0];
		pLeftHand[1][0][1]=pFrontLeftForearm[1][0][0];
		pLeftHand[1][1][1]=pFrontLeftForearm[1][1][0];
		pLeftHand[0][1][1]=pFrontLeftForearm[0][1][0];
		
		pLeftHand[0][0][0]=addBPoint(pLeftHand[0][0][1].x,pLeftHand[0][0][1].y,pLeftHand[0][0][1].z-hand_length,LEFT_RADIUS);
		pLeftHand[1][0][0]=addBPoint(pLeftHand[1][0][1].x,pLeftHand[1][0][1].y,pLeftHand[1][0][1].z-hand_length,LEFT_RADIUS);
		pLeftHand[1][1][0]=addBPoint(pLeftHand[1][1][1].x,pLeftHand[1][1][1].y,pLeftHand[1][1][1].z-hand_length,LEFT_RADIUS);
		pLeftHand[0][1][0]=addBPoint(pLeftHand[0][1][1].x,pLeftHand[0][1][1].y,pLeftHand[0][1][1].z-hand_length,LEFT_RADIUS);
		
		addLine(pLeftHand[0][0][0],pLeftHand[0][0][1],pLeftHand[0][1][1],pLeftHand[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pLeftHand[0][1][0],pLeftHand[0][1][1],pLeftHand[1][1][1],pLeftHand[1][1][0],Renderer3D.CAR_FRONT);

		addLine(pLeftHand[1][1][0],pLeftHand[1][1][1],pLeftHand[1][0][1],pLeftHand[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(pLeftHand[1][0][0],pLeftHand[1][0][1],pLeftHand[0][0][1],pLeftHand[0][0][0],Renderer3D.CAR_BACK);		
		
		addLine(pLeftHand[0][0][0],pLeftHand[0][1][0],pLeftHand[1][1][0],pLeftHand[1][0][0],Renderer3D.CAR_BOTTOM);
		
		//Right forearm
		
		
		BPoint[][][] pFrontRightForearm=new BPoint[2][2][2];
		
		pFrontRightForearm[0][0][0]=addBPoint(xc+ax-leg_side,ay,az,RIGHT_RADIUS);
		pFrontRightForearm[1][0][0]=addBPoint(xc+ax,ay,az,RIGHT_RADIUS);
		pFrontRightForearm[1][1][0]=addBPoint(xc+ax,ay+leg_side,az,RIGHT_RADIUS);
		pFrontRightForearm[0][1][0]=addBPoint(xc+ax-leg_side,ay+leg_side,az,RIGHT_RADIUS);

		addLine(pFrontRightForearm[0][0][0],pFrontRightForearm[0][1][0],pFrontRightForearm[1][1][0],pFrontRightForearm[1][0][0],Renderer3D.CAR_BOTTOM);

		
		pFrontRightForearm[0][0][1]=addBPoint(xc+ax-leg_side,ay,az+radius_length,RIGHT_RADIUS);
		pFrontRightForearm[1][0][1]=addBPoint(xc+ax,ay,az+radius_length,RIGHT_RADIUS);
		pFrontRightForearm[1][1][1]=addBPoint(xc+ax,ay+leg_side,az+radius_length,RIGHT_RADIUS);
		pFrontRightForearm[0][1][1]=addBPoint(xc+ax-leg_side,ay+leg_side,az+radius_length,RIGHT_RADIUS);

		
		addLine(pFrontRightForearm[0][0][0],pFrontRightForearm[0][0][1],pFrontRightForearm[0][1][1],pFrontRightForearm[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pFrontRightForearm[0][1][0],pFrontRightForearm[0][1][1],pFrontRightForearm[1][1][1],pFrontRightForearm[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(pFrontRightForearm[1][1][0],pFrontRightForearm[1][1][1],pFrontRightForearm[1][0][1],pFrontRightForearm[1][0][0],Renderer3D.CAR_RIGHT);
	
		addLine(pFrontRightForearm[1][0][0],pFrontRightForearm[1][0][1],pFrontRightForearm[0][0][1],pFrontRightForearm[0][0][0],Renderer3D.CAR_BACK);

		
		//right arm
		
		BPoint[][][] pFrontRightArm=new BPoint[2][2][2];
		
		pFrontRightArm[0][0][0]=addBPoint(xc+ax-leg_side,ay,body[0][0][3].z,RIGHT_HOMERUS);
		pFrontRightArm[1][0][0]=addBPoint(xc+ax,ay,body[0][0][3].z,RIGHT_HOMERUS);
		pFrontRightArm[1][1][0]=addBPoint(xc+ax,ay+leg_side,body[0][0][3].z,RIGHT_HOMERUS);
		pFrontRightArm[0][1][0]=addBPoint(xc+ax-leg_side,ay+leg_side,body[0][0][3].z,RIGHT_HOMERUS);
		
		
		pFrontRightArm[0][0][1]=addBPoint(xc+ax-leg_side,ay,body[0][0][4].z,RIGHT_HOMERUS);
		pFrontRightArm[1][0][1]=addBPoint(xc+ax,ay,body[0][0][4].z,RIGHT_HOMERUS);
		pFrontRightArm[1][1][1]=addBPoint(xc+ax,ay+leg_side,body[0][0][4].z,RIGHT_HOMERUS);
		pFrontRightArm[0][1][1]=addBPoint(xc+ax-leg_side,ay+leg_side,body[0][0][4].z,RIGHT_HOMERUS);
					
		addLine(pFrontRightForearm[0][0][1],pFrontRightArm[0][0][0],pFrontRightArm[0][1][0],pFrontRightForearm[0][1][1],Renderer3D.CAR_LEFT);

		addLine(pFrontRightForearm[0][1][1],pFrontRightArm[0][1][0],pFrontRightArm[1][1][0],pFrontRightForearm[1][1][1],Renderer3D.CAR_FRONT);
	
		addLine(pFrontRightForearm[1][1][1],pFrontRightArm[1][1][0],pFrontRightArm[1][0][0],pFrontRightForearm[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(pFrontRightForearm[1][0][1],pFrontRightArm[1][0][0],pFrontRightArm[0][0][0],pFrontRightForearm[0][0][1],Renderer3D.CAR_BACK);
		
		
		addLine(pFrontRightArm[0][0][1],pFrontRightArm[1][0][1],pFrontRightArm[1][1][1],pFrontRightArm[0][1][1],Renderer3D.CAR_TOP);
		
		
		addLine(pFrontRightArm[0][0][0],pFrontRightArm[0][0][1],pFrontRightArm[0][1][1],pFrontRightArm[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pFrontRightArm[0][1][0],pFrontRightArm[0][1][1],pFrontRightArm[1][1][1],pFrontRightArm[1][1][0],Renderer3D.CAR_FRONT);
	
		addLine(pFrontRightArm[1][1][0],pFrontRightArm[1][1][1],pFrontRightArm[1][0][1],pFrontRightArm[1][0][0],Renderer3D.CAR_RIGHT);
		
		addLine(pFrontRightArm[1][0][0],pFrontRightArm[1][0][1],pFrontRightArm[0][0][1],pFrontRightArm[0][0][0],Renderer3D.CAR_BACK);
		
		
		//left hand
		
		BPoint[][][] pRightHand=new BPoint[2][2][2];	
		
		pRightHand[0][0][1]=pFrontRightForearm[0][0][0];
		pRightHand[1][0][1]=pFrontRightForearm[1][0][0];
		pRightHand[1][1][1]=pFrontRightForearm[1][1][0];
		pRightHand[0][1][1]=pFrontRightForearm[0][1][0];
		
		pRightHand[0][0][0]=addBPoint(pRightHand[0][0][1].x,pRightHand[0][0][1].y,pRightHand[0][0][1].z-hand_length,RIGHT_RADIUS);
		pRightHand[1][0][0]=addBPoint(pRightHand[1][0][1].x,pRightHand[1][0][1].y,pRightHand[1][0][1].z-hand_length,RIGHT_RADIUS);
		pRightHand[1][1][0]=addBPoint(pRightHand[1][1][1].x,pRightHand[1][1][1].y,pRightHand[1][1][1].z-hand_length,RIGHT_RADIUS);
		pRightHand[0][1][0]=addBPoint(pRightHand[0][1][1].x,pRightHand[0][1][1].y,pRightHand[0][1][1].z-hand_length,RIGHT_RADIUS);
		
		addLine(pRightHand[0][0][0],pRightHand[0][0][1],pRightHand[0][1][1],pRightHand[0][1][0],Renderer3D.CAR_RIGHT);

		addLine(pRightHand[0][1][0],pRightHand[0][1][1],pRightHand[1][1][1],pRightHand[1][1][0],Renderer3D.CAR_FRONT);

		addLine(pRightHand[1][1][0],pRightHand[1][1][1],pRightHand[1][0][1],pRightHand[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(pRightHand[1][0][0],pRightHand[1][0][1],pRightHand[0][0][1],pRightHand[0][0][0],Renderer3D.CAR_BACK);		
		
		addLine(pRightHand[0][0][0],pRightHand[0][1][0],pRightHand[1][1][0],pRightHand[1][0][0],Renderer3D.CAR_BOTTOM);
		

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private BPoint[][][] buildHumanHeadMesh(double xc) {
	
		double yc=y_side/2.0;
	
		Segments n0=new Segments(xc,neck_side*0.5,yc,y_side,femur_length+shinbone_length+z_side+neck_length,head_DZ);
		Segments h0=new Segments(xc,head_DX*0.5,yc,head_DY*0.5,femur_length+shinbone_length+z_side+neck_length,head_DZ);
		
		
		Point3D e0=new Point3D(1.0,0.0,0.0);
		Point3D e1=new Point3D(Math.cos(Math.PI/8.0),Math.sin(Math.PI/8.0),0.0);
		Point3D e2=new Point3D(Math.cos(Math.PI/4.0),Math.sin(Math.PI/4.0),0.0);
		Point3D e3=new Point3D(Math.cos(Math.PI*3.0/8.0),Math.sin(Math.PI*3.0/8.0),0.0);
		Point3D e4=new Point3D(0,1.0,0.0);

		int numx=5;
		int numy=5;
		int numz=5;
		
		BPoint[][][] head=new BPoint[numx][numy][numz];

		head[0][0][0]=addBPoint(-e2.x*1.0,-e2.y*0.5,0.0,HEAD,n0);
		head[1][0][0]=addBPoint(-e3.x,-e3.y*0.5,0.0,HEAD,n0);
		head[2][0][0]=addBPoint(e4.x,-e4.y*0.5,0.0,HEAD,n0);
		head[3][0][0]=addBPoint(e3.x,-e3.y*0.5,0.0,HEAD,n0);
		head[4][0][0]=addBPoint(e2.x,-e2.y*0.5,0.0,HEAD,n0);
		head[0][1][0]=addBPoint(-e1.x,-e2.y*0.25,0.0,HEAD,n0);		
		head[4][1][0]=addBPoint(e1.x,-e2.y*0.25,0.0,HEAD,n0);
		head[0][2][0]=addBPoint(-e0.x,0.0,0.0,HEAD,n0);	
		head[4][2][0]=addBPoint(e0.x,0.0,0.0,HEAD,n0);
		head[0][3][0]=addBPoint(-e1.x,e2.y*0.25,0.0,HEAD,n0);	
		head[4][3][0]=addBPoint(e1.x,e2.y*0.25,0.0,HEAD,n0);
		head[0][4][0]=addBPoint(-e2.x,e2.y*0.5,0.0,HEAD,n0);
		head[1][4][0]=addBPoint(-e3.x,e3.y*0.5,0.0,HEAD,n0);
		head[2][4][0]=addBPoint(e4.x,e4.y*0.5,0.0,HEAD,n0);
		head[3][4][0]=addBPoint(e3.x,e3.y*0.5,0.0,HEAD,n0);
		head[4][4][0]=addBPoint(e2.x,e2.y*0.5,0.0,HEAD,n0);

		head[0][0][1]=addBPoint(-e2.x*0.85,-e2.y*0.85,0.25,HEAD,h0);
		head[1][0][1]=addBPoint(-e3.x*0.85,-e3.y*0.85,0.25,HEAD,h0);
		head[2][0][1]=addBPoint(e4.x,-e4.y*0.85,0.25,HEAD,h0);
		head[3][0][1]=addBPoint(e3.x*0.85,-e3.y*0.85,0.25,HEAD,h0);
		head[4][0][1]=addBPoint(e2.x*0.85,-e2.y*0.85,0.25,HEAD,h0);		
		head[0][1][1]=addBPoint(-e1.x*0.85,-e1.y*0.85,0.25,HEAD,h0);		
		head[4][1][1]=addBPoint(e1.x*0.85,-e1.y*0.85,0.25,HEAD,h0);
		head[0][2][1]=addBPoint(-e0.x*0.85,e0.y,0.25,HEAD,h0);	
		head[4][2][1]=addBPoint(e0.x*0.85,e0.y,0.25,HEAD,h0);
		head[0][3][1]=addBPoint(-e1.x*0.85,e1.y*0.85,0.25,HEAD,h0);		
		head[4][3][1]=addBPoint(e1.x*0.85,e1.y*0.85,0.25,HEAD,h0);
		head[0][4][1]=addBPoint(-e2.x*0.85,e2.y*0.85,0.25,HEAD,h0);
		head[1][4][1]=addBPoint(-e3.x*0.85,e3.y*0.85,0.25,HEAD,h0);
		head[2][4][1]=addBPoint(e4.x,e4.y*0.85,0.25,HEAD,h0);
		head[3][4][1]=addBPoint(e3.x*0.85,e3.y*0.85,0.25,HEAD,h0);
		head[4][4][1]=addBPoint(e2.x*0.85,e2.y*0.85,0.25,HEAD,h0);
		
		head[0][0][2]=addBPoint(-e2.x,-e2.y,0.5,HEAD,h0);
		head[1][0][2]=addBPoint(-e3.x,-e3.y,0.5,HEAD,h0);
		head[2][0][2]=addBPoint(e4.x,-e4.y,0.5,HEAD,h0);
		head[3][0][2]=addBPoint(e3.x,-e3.y,0.5,HEAD,h0);
		head[4][0][2]=addBPoint(e2.x,-e2.y,0.5,HEAD,h0);
		head[0][1][2]=addBPoint(-e1.x,-e1.y,0.5,HEAD,h0);		
		head[4][1][2]=addBPoint(e1.x,-e1.y,0.5,HEAD,h0);
		head[0][2][2]=addBPoint(-e0.x,e0.y,0.5,HEAD,h0);	
		head[4][2][2]=addBPoint(e0.x,e0.y,0.5,HEAD,h0);
		head[0][3][2]=addBPoint(-e1.x,e1.y,0.5,HEAD,h0);		
		head[4][3][2]=addBPoint(e1.x,e1.y,0.5,HEAD,h0);
		head[0][4][2]=addBPoint(-e2.x,e2.y,0.5,HEAD,h0);
		head[1][4][2]=addBPoint(-e3.x,e3.y,0.5,HEAD,h0);
		head[2][4][2]=addBPoint(e4.x,e4.y,0.5,HEAD,h0);
		head[3][4][2]=addBPoint(e3.x,e3.y,0.5,HEAD,h0);
		head[4][4][2]=addBPoint(e2.x,e2.y,0.5,HEAD,h0);	
	
		head[0][0][3]=addBPoint(-e2.x,-e2.y,0.75,HEAD,h0);
		head[1][0][3]=addBPoint(-e3.x,-e3.y,0.75,HEAD,h0);
		head[2][0][3]=addBPoint(e4.x,-e4.y,0.75,HEAD,h0);
		head[3][0][3]=addBPoint(e3.x,-e3.y,0.75,HEAD,h0);
		head[4][0][3]=addBPoint(e2.x,-e2.y,0.75,HEAD,h0);
		head[0][1][3]=addBPoint(-e1.x,-e1.y,0.75,HEAD,h0);		
		head[4][1][3]=addBPoint(e1.x,-e1.y,0.75,HEAD,h0);
		head[0][2][3]=addBPoint(-e0.x,e0.y,0.75,HEAD,h0);	
		head[4][2][3]=addBPoint(e0.x,e0.y,0.75,HEAD,h0);
		head[0][3][3]=addBPoint(-e1.x,e1.y,0.75,HEAD,h0);		
		head[4][3][3]=addBPoint(e1.x,e1.y,0.75,HEAD,h0);
		head[0][4][3]=addBPoint(-e2.x,e2.y,0.75,HEAD,h0);
		head[1][4][3]=addBPoint(-e3.x,e3.y,0.75,HEAD,h0);
		head[2][4][3]=addBPoint(e4.x,e4.y,0.75,HEAD,h0);
		head[3][4][3]=addBPoint(e3.x,e3.y,0.75,HEAD,h0);
		head[4][4][3]=addBPoint(e2.x,e2.y,0.75,HEAD,h0);
		
		head[0][0][4]=addBPoint(-e2.x*0.75,-e2.y*0.75,1.0,HEAD,h0);
		head[1][0][4]=addBPoint(-e3.x*0.75,-e3.y*0.75,1.0,HEAD,h0);
		head[2][0][4]=addBPoint(e4.x*0.75,-e4.y*0.75,1.0,HEAD,h0);
		head[3][0][4]=addBPoint(e3.x*0.75,-e3.y*0.75,1.0,HEAD,h0);
		head[4][0][4]=addBPoint(e2.x*0.75,-e2.y*0.75,1.0,HEAD,h0);	
		head[0][1][4]=addBPoint(-e1.x*0.75,-e1.y*0.75,1.0,HEAD,h0);
		head[1][1][4]=addBPoint(-e1.x*0.5*0.75,-e1.y*0.75,1.0,HEAD,h0);
		head[2][1][4]=addBPoint(0.0,-e1.y*0.75,1.0,HEAD,h0);
		head[3][1][4]=addBPoint(e1.x*0.5*0.75,-e1.y*0.75,1.0,HEAD,h0);
		head[4][1][4]=addBPoint(e1.x*0.75,-e1.y*0.75,1.0,HEAD,h0);
		head[0][2][4]=addBPoint(-e0.x*0.75,e0.y,1.0,HEAD,h0);
		head[1][2][4]=addBPoint(-e0.x*0.5*0.75,e0.y,1.0,HEAD,h0);
		head[2][2][4]=addBPoint(0.0,e0.y,1.0,HEAD,h0);
		head[3][2][4]=addBPoint(e0.x*0.5*0.75,e0.y,1.0,HEAD,h0);
		head[4][2][4]=addBPoint(e0.x*0.75,e0.y,1.0,HEAD,h0);
		head[0][3][4]=addBPoint(-e1.x*0.75,e1.y*0.75,1.0,HEAD,h0);
		head[1][3][4]=addBPoint(-e1.x*0.5*0.75,e1.y*0.75,1.0,HEAD,h0);
		head[2][3][4]=addBPoint(0.0,e1.y*0.75,1.0,HEAD,h0);
		head[3][3][4]=addBPoint(e1.x*0.5*0.75,e1.y*0.75,1.0,HEAD,h0);
		head[4][3][4]=addBPoint(e1.x*0.75,e1.y*0.75,1.0,HEAD,h0);
		head[0][4][4]=addBPoint(-e2.x*0.75,e2.y*0.75,1.0,HEAD,h0);
		head[1][4][4]=addBPoint(-e3.x*0.75,e3.y*0.75,1.0,HEAD,h0);
		head[2][4][4]=addBPoint(e4.x,e4.y*0.75,1.0,HEAD,h0);
		head[3][4][4]=addBPoint(e3.x*0.75,e3.y*0.75,1.0,HEAD,h0);
		head[4][4][4]=addBPoint(e2.x*0.75,e2.y*0.75,1.0,HEAD,h0);

		
		for(int i=0;i<numx-1;i++){

			for (int j = 0; j < numy-1; j++) {


				for(int k=0;k<numz-1;k++){		

					if(i==0){
						addLine(head[i][j][k],head[i][j][k+1],head[i][j+1][k+1],head[i][j+1][k],Renderer3D.CAR_LEFT);
					}

					if(i+1==numx-1){
						addLine(head[i+1][j][k],head[i+1][j+1][k],head[i+1][j+1][k+1],head[i+1][j][k+1],Renderer3D.CAR_RIGHT);
					}

					if(j==0)
						addLine(head[i][j][k],head[i+1][j][k],head[i+1][j][k+1],head[i][j][k+1],Renderer3D.CAR_BACK);		

					if(j+1==numy-1)
						addLine(head[i][j+1][k],head[i][j+1][k+1],head[i+1][j+1][k+1],head[i+1][j+1][k],Renderer3D.CAR_FRONT);

					if(k+1==numz-1)
						addLine(head[i][j][k+1],head[i+1][j][k+1],head[i+1][j+1][k+1],head[i][j+1][k+1],Renderer3D.CAR_TOP);

				}

			}

		}
		

		
		return head;
	}

	private PolygonMesh buildQuadrupedMesh() {


		points=new Vector();
		points.setSize(400);

		polyData=new Vector();
		
		
		
		n=0;
		

		double xc=0;
		
		//legs angles
		
		double bq0=-0.1;
		double bq1=0.5;
		double bq2=-0.4;
		
		double fq0=0.2;
		double fq1=-0.2;
		double fq2=-Math.PI/4;

		
		double rearZ=femur_length*Math.cos(bq0)+shinbone_length*Math.cos(bq0+bq1)+foot_length*Math.cos(bq0+bq1+bq2);
		double frontZ=humerus_length*Math.cos(fq0)+radius_length*Math.cos(fq0+fq1)+hand_length*Math.cos(fq0+fq1+fq2);
		
		//System.out.println("rear:"+(foot_length+femur_length+shinbone_length)+","+rearZ);
		//System.out.println("front:"+(radius_length+humerus_length+hand_length)+","+frontZ);
		//main body:
		
		double dz=rearZ-(frontZ);
		
		int numx=5;
		int numy=6;
		int numz=5;
		
		BPoint[][][] body=new BPoint[numx][numy][numz]; 
		
		Segments pb0=new Segments(xc,x_side,0,y_side,rearZ,z_side);
		Segments pf0=new Segments(xc,x_side,0,y_side,frontZ,z_side+dz);

		body[0][0][0]=addBPoint(-0.5,0.0,0.4,TRUNK,pb0);
		body[1][0][0]=addBPoint(-0.25,0.0,0.0,TRUNK,pb0);
		body[2][0][0]=addBPoint(0.0,0.0,0.0,TRUNK,pb0);
		body[3][0][0]=addBPoint(0.25,0.0,0.0,TRUNK,pb0);
		body[4][0][0]=addBPoint(0.5,0.0,0.4,TRUNK,pb0);
		body[0][0][1]=addBPoint(-0.5,0.0,0.45,TRUNK,pb0);	
		body[1][0][1]=addBPoint(-0.25,0.0,0.25,TRUNK,pb0);
		body[2][0][1]=addBPoint(0.0,0.0,0.25,TRUNK,pb0);
		body[3][0][1]=addBPoint(0.25,0.0,0.25,TRUNK,pb0);
		body[4][0][1]=addBPoint(0.5,0.0,0.45,TRUNK,pb0);
		body[0][0][2]=addBPoint(-0.5,0.0,0.5,TRUNK,pb0);	
		body[1][0][2]=addBPoint(-0.25,0.0,0.5,TRUNK,pb0);
		body[2][0][2]=addBPoint(0.0,0.0,0.5,TRUNK,pb0);
		body[3][0][2]=addBPoint(0.25,0.0,0.5,TRUNK,pb0);
		body[4][0][2]=addBPoint(0.5,0.0,0.5,TRUNK,pb0);
		body[0][0][3]=addBPoint(-0.5,0.0,0.55,TRUNK,pb0);	
		body[1][0][3]=addBPoint(-0.25,0.0,0.75,TRUNK,pb0);
		body[2][0][3]=addBPoint(0.0,0.0,0.75,TRUNK,pb0);
		body[3][0][3]=addBPoint(0.25,0.0,0.75,TRUNK,pb0);
		body[4][0][3]=addBPoint(0.5,0.0,0.55,TRUNK,pb0);
		body[0][0][4]=addBPoint(-0.5,0.0,0.7,TRUNK,pb0);	
		body[1][0][4]=addBPoint(-0.25,0.0,1.0,TRUNK,pb0);
		body[2][0][4]=addBPoint(0.0,0.0,1.0,TRUNK,pb0);
		body[3][0][4]=addBPoint(0.25,0.0,1.0,TRUNK,pb0);
		body[4][0][4]=addBPoint(0.5,0.0,0.7,TRUNK,pb0);
		
		body[0][1][0]=addBPoint(-0.5,0.25,0.4,TRUNK,pb0);
		body[1][1][0]=addBPoint(-0.25,0.25,0.0,TRUNK,pb0);
		body[2][1][0]=addBPoint(0.0,0.25,0.0,TRUNK,pb0);
		body[3][1][0]=addBPoint(0.25,0.25,0.0,TRUNK,pb0);
		body[4][1][0]=addBPoint(0.5,0.25,0.4,TRUNK,pb0);	
		body[0][1][1]=addBPoint(-0.5,0.25,0.45,TRUNK,pb0);	
		body[1][1][1]=addBPoint(-0.25,0.25,0.25,TRUNK,pb0);
		body[2][1][1]=addBPoint(0.0,0.25,0.25,TRUNK,pb0);
		body[3][1][1]=addBPoint(0.25,0.25,0.25,TRUNK,pb0);
		body[4][1][1]=addBPoint(0.5,0.25,0.45,TRUNK,pb0);
		body[0][1][2]=addBPoint(-0.5,0.25,0.5,TRUNK,pb0);	
		body[1][1][2]=addBPoint(-0.25,0.25,0.5,TRUNK,pb0);
		body[2][1][2]=addBPoint(0.0,0.25,0.5,TRUNK,pb0);
		body[3][1][2]=addBPoint(0.25,0.25,0.5,TRUNK,pb0);
		body[4][1][2]=addBPoint(0.5,0.25,0.5,TRUNK,pb0);
		body[0][1][3]=addBPoint(-0.5,0.25,0.55,TRUNK,pb0);	
		body[1][1][3]=addBPoint(-0.25,0.25,0.75,TRUNK,pb0);
		body[2][1][3]=addBPoint(0.0,0.25,0.75,TRUNK,pb0);
		body[3][1][3]=addBPoint(0.25,0.25,0.75,TRUNK,pb0);
		body[4][1][3]=addBPoint(0.5,0.25,0.55,TRUNK,pb0);
		body[0][1][4]=addBPoint(-0.5,0.25,0.7,TRUNK,pb0);	
		body[1][1][4]=addBPoint(-0.25,0.25,1.0,TRUNK,pb0);
		body[2][1][4]=addBPoint(0.0,0.25,1.0,TRUNK,pb0);
		body[3][1][4]=addBPoint(0.25,0.25,1.0,TRUNK,pb0);
		body[4][1][4]=addBPoint(0.5,0.25,0.7,TRUNK,pb0);
		
		body[0][2][0]=addBPoint(-0.5,0.5,0.4,TRUNK,pb0);
		body[1][2][0]=addBPoint(-0.25,0.5,0.0,TRUNK,pb0);
		body[2][2][0]=addBPoint(0.0,0.5,0.0,TRUNK,pb0);
		body[3][2][0]=addBPoint(0.25,0.5,0.0,TRUNK,pb0);
		body[4][2][0]=addBPoint(0.5,0.5,0.4,TRUNK,pb0);
		body[0][2][1]=addBPoint(-0.5,0.5,0.45,TRUNK,pb0);	
		body[1][2][1]=addBPoint(-0.25,0.5,0.25,TRUNK,pb0);
		body[2][2][1]=addBPoint(0.0,0.5,0.25,TRUNK,pb0);
		body[3][2][1]=addBPoint(0.25,0.5,0.25,TRUNK,pb0);
		body[4][2][1]=addBPoint(0.5,0.5,0.45,TRUNK,pb0);
		body[0][2][2]=addBPoint(-0.5,0.5,0.5,TRUNK,pb0);	
		body[1][2][2]=addBPoint(-0.25,0.5,0.5,TRUNK,pb0);
		body[2][2][2]=addBPoint(0.0,0.5,0.5,TRUNK,pb0);
		body[3][2][2]=addBPoint(0.25,0.5,0.5,TRUNK,pb0);
		body[4][2][2]=addBPoint(0.5,0.5,0.5,TRUNK,pb0);
		body[0][2][3]=addBPoint(-0.5,0.5,0.55,TRUNK,pb0);	
		body[1][2][3]=addBPoint(-0.25,0.5,0.75,TRUNK,pb0);
		body[2][2][3]=addBPoint(0.0,0.5,0.75,TRUNK,pb0);
		body[3][2][3]=addBPoint(0.25,0.5,0.75,TRUNK,pb0);
		body[4][2][3]=addBPoint(0.5,0.5,0.55,TRUNK,pb0);
		body[0][2][4]=addBPoint(-0.5,0.5,0.7,TRUNK,pb0);	
		body[1][2][4]=addBPoint(-0.25,0.5,1.0,TRUNK,pb0);
		body[2][2][4]=addBPoint(0.0,0.5,1.0,TRUNK,pb0);
		body[3][2][4]=addBPoint(0.25,0.5,1.0,TRUNK,pb0);
		body[4][2][4]=addBPoint(0.5,0.5,0.7,TRUNK,pb0);
		
		body[0][3][0]=addBPoint(-0.5,0.75,0.4,TRUNK,pf0);
		body[1][3][0]=addBPoint(-0.25,0.75,0.0,TRUNK,pf0);
		body[2][3][0]=addBPoint(0.0,0.75,0.0,TRUNK,pf0);	
		body[3][3][0]=addBPoint(0.25,0.75,0.0,TRUNK,pf0);
		body[4][3][0]=addBPoint(0.5,0.75,0.4,TRUNK,pf0);
		body[0][3][1]=addBPoint(-0.5,0.75,0.45,TRUNK,pf0);		
		body[1][3][1]=addBPoint(-0.25,0.75,0.25,TRUNK,pf0);
		body[2][3][1]=addBPoint(0.0,0.75,0.25,TRUNK,pf0);
		body[3][3][1]=addBPoint(0.25,0.75,0.25,TRUNK,pf0);
		body[4][3][1]=addBPoint(0.5,0.75,0.45,TRUNK,pf0);
		body[0][3][2]=addBPoint(-0.5,0.75,0.5,TRUNK,pf0);		
		body[1][3][2]=addBPoint(-0.25,0.75,0.5,TRUNK,pf0);
		body[2][3][2]=addBPoint(0.0,0.75,0.5,TRUNK,pf0);
		body[3][3][2]=addBPoint(0.25,0.75,0.5,TRUNK,pf0);
		body[4][3][2]=addBPoint(0.5,0.75,0.5,TRUNK,pf0);
		body[0][3][3]=addBPoint(-0.5,0.75,0.75,TRUNK,pf0);		
		body[1][3][3]=addBPoint(-0.25,0.75,0.75,TRUNK,pf0);
		body[2][3][3]=addBPoint(0.0,0.75,0.75,TRUNK,pf0);
		body[3][3][3]=addBPoint(0.25,0.75,0.75,TRUNK,pf0);
		body[4][3][3]=addBPoint(0.5,0.75,0.75,TRUNK,pf0);
		body[0][3][4]=addBPoint(-0.5,0.75,1.0,TRUNK,pf0);		
		body[1][3][4]=addBPoint(-0.25,0.75,1.0,TRUNK,pf0);
		body[2][3][4]=addBPoint(0.0,0.75,1.0,TRUNK,pf0);
		body[3][3][4]=addBPoint(0.25,0.75,1.0,TRUNK,pf0);
		body[4][3][4]=addBPoint(0.5,0.75,1.0,TRUNK,pf0);
		
		body[0][4][0]=addBPoint(-0.5,0.875,0.4,TRUNK,pf0);
		body[1][4][0]=addBPoint(-0.25,0.875,0.0,TRUNK,pf0);	
		body[2][4][0]=addBPoint(0.0,0.875,0.0,TRUNK,pf0);	
		body[3][4][0]=addBPoint(0.25,0.875,0.0,TRUNK,pf0);		
		body[4][4][0]=addBPoint(0.5,0.875,0.4,TRUNK,pf0);	
		body[0][4][1]=addBPoint(-0.5,0.875,0.45,TRUNK,pf0);		
		body[1][4][1]=addBPoint(-0.25,0.875,0.25,TRUNK,pf0);
		body[2][4][1]=addBPoint(0.0,0.875,0.25,TRUNK,pf0);
		body[3][4][1]=addBPoint(0.25,0.875,0.25,TRUNK,pf0);
		body[4][4][1]=addBPoint(0.5,0.875,0.45,TRUNK,pf0);
		body[0][4][2]=addBPoint(-0.5,0.875,0.5,TRUNK,pf0);		
		body[1][4][2]=addBPoint(-0.25,0.875,0.5,TRUNK,pf0);
		body[2][4][2]=addBPoint(0.0,0.875,0.5,TRUNK,pf0);
		body[3][4][2]=addBPoint(0.25,0.875,0.5,TRUNK,pf0);
		body[4][4][2]=addBPoint(0.5,0.875,0.5,TRUNK,pf0);
		body[0][4][3]=addBPoint(-0.5,0.875,0.75,TRUNK,pf0);		
		body[1][4][3]=addBPoint(-0.25,0.875,0.75,TRUNK,pf0);
		body[2][4][3]=addBPoint(0.0,0.875,0.75,TRUNK,pf0);
		body[3][4][3]=addBPoint(0.25,0.875,0.75,TRUNK,pf0);
		body[4][4][3]=addBPoint(0.5,0.875,0.75,TRUNK,pf0);
		body[0][4][4]=addBPoint(-0.5,0.875,1.0,TRUNK,pf0);		
		body[1][4][4]=addBPoint(-0.25,0.875,1.0,TRUNK,pf0);
		body[2][4][4]=addBPoint(0.0,0.875,1.0,TRUNK,pf0);
		body[3][4][4]=addBPoint(0.25,0.875,1.0,TRUNK,pf0);
		body[4][4][4]=addBPoint(0.5,0.875,1.0,TRUNK,pf0);
		
		body[0][5][0]=addBPoint(-0.5,1.0,0.4,TRUNK,pf0);
		body[1][5][0]=addBPoint(-0.25,1.0,0.0,TRUNK,pf0);	
		body[2][5][0]=addBPoint(0.0,1.0,0.0,TRUNK,pf0);	
		body[3][5][0]=addBPoint(0.25,1.0,0.0,TRUNK,pf0);		
		body[4][5][0]=addBPoint(0.5,1.0,0.4,TRUNK,pf0);	
		body[0][5][1]=addBPoint(-0.5,1.0,0.45,TRUNK,pf0);		
		body[1][5][1]=addBPoint(-0.25,1.0,0.25,TRUNK,pf0);
		body[2][5][1]=addBPoint(0.0,1.0,0.25,TRUNK,pf0);
		body[3][5][1]=addBPoint(0.25,1.0,0.25,TRUNK,pf0);
		body[4][5][1]=addBPoint(0.5,1.0,0.45,TRUNK,pf0);
		body[0][5][2]=addBPoint(-0.5,1.0,0.5,TRUNK,pf0);		
		body[1][5][2]=addBPoint(-0.25,1.0,0.5,TRUNK,pf0);
		body[2][5][2]=addBPoint(0.0,1.0,0.5,TRUNK,pf0);
		body[3][5][2]=addBPoint(0.25,1.0,0.5,TRUNK,pf0);
		body[4][5][2]=addBPoint(0.5,1.0,0.5,TRUNK,pf0);
		body[0][5][3]=addBPoint(-0.5,1.0,0.75,TRUNK,pf0);		
		body[1][5][3]=addBPoint(-0.25,1.0,0.75,TRUNK,pf0);
		body[2][5][3]=addBPoint(0.0,1.0,0.75,TRUNK,pf0);
		body[3][5][3]=addBPoint(0.25,1.0,0.75,TRUNK,pf0);
		body[4][5][3]=addBPoint(0.5,1.0,0.75,TRUNK,pf0);
		body[0][5][4]=addBPoint(-0.5,1.0,1.0,TRUNK,pf0);		
		body[1][5][4]=addBPoint(-0.25,1.0,1.0,TRUNK,pf0);
		body[2][5][4]=addBPoint(0.0,1.0,1.0,TRUNK,pf0);
		body[3][5][4]=addBPoint(0.25,1.0,1.0,TRUNK,pf0);
		body[4][5][4]=addBPoint(0.5,1.0,1.0,TRUNK,pf0);
		
		for (int i = 0; i < numx-1; i++) {
			
		
		
			for (int j = 0; j < numy-1; j++) {
				
				for (int k = 0; k < numz-1; k++) {
			
					if(j==0){

						addLine(body[i][j][k],body[i+1][j][k],body[i+1][j][k+1],body[i][j][k+1],Renderer3D.CAR_BACK);
						
					}				
						
					if(i==0)	
						addLine(body[i][j][k],body[i][j][k+1],body[i][j+1][k+1],body[i][j+1][k],Renderer3D.CAR_LEFT);

						
					if(k==0)
						addLine(body[i][j][k],body[i][j+1][k],body[i+1][j+1][k],body[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					if(k+1==numz-1)							
						addLine(body[i][j][k+1],body[i+1][j][k+1],body[i+1][j+1][k+1],body[i][j+1][k+1],Renderer3D.CAR_TOP);
				
					

					if(i+1==numx-1)
						addLine(body[i+1][j][k],body[i+1][j+1][k],body[i+1][j+1][k+1],body[i+1][j][k+1],Renderer3D.CAR_RIGHT);
						
				
					
					if(j+1==numy-1){
						
						addLine(body[i][j+1][k],body[i][j+1][k+1],body[i+1][j+1][k+1],body[i+1][j+1][k],Renderer3D.CAR_FRONT);
						
					}
				
				}
				
			}
			
		}

		/////////head and neck:		
		
		double nz0=frontZ+z_side+dz;		
		
		BPoint[][][] head=buildQuadrupedHeadMesh(nz0+neck_length,xc);		

		int ney=3;
		int nez=3;		
		
		Segments n0=new Segments(xc,neck_side,y_side-neck_side,neck_side,nz0,neck_length);
		
		BPoint[][][] neck=new BPoint[numx][ney][nez];
	
		neck[0][0][0]=body[0][3][numz-1];
		neck[1][0][0]=body[1][3][numz-1];
		neck[2][0][0]=body[2][3][numz-1];
		neck[3][0][0]=body[3][3][numz-1];
		neck[4][0][0]=body[4][3][numz-1];
		neck[0][1][0]=body[0][4][numz-1];
		neck[1][1][0]=body[1][4][numz-1];
		neck[2][1][0]=body[2][4][numz-1];
		neck[3][1][0]=body[3][4][numz-1];
		neck[4][1][0]=body[4][4][numz-1];
		neck[0][2][0]=body[0][5][numz-1];
		neck[1][2][0]=body[1][5][numz-1];
		neck[2][2][0]=body[2][5][numz-1];
		neck[3][2][0]=body[3][5][numz-1];
		neck[4][2][0]=body[4][5][numz-1];
		
		neck[0][0][1]=addBPoint(-0.5,0.0,0.5,TRUNK,n0);
		neck[1][0][1]=addBPoint(-0.25,0.0,0.5,TRUNK,n0);
		neck[2][0][1]=addBPoint(0.0,0.0,0.5,TRUNK,n0);
		neck[3][0][1]=addBPoint(0.25,0.0,0.5,TRUNK,n0);
		neck[4][0][1]=addBPoint(0.5,0.0,0.5,TRUNK,n0);
		neck[0][1][1]=addBPoint(-0.5,0.5,0.5,TRUNK,n0);
		neck[1][1][1]=addBPoint(-0.25,0.5,0.5,TRUNK,n0);
		neck[2][1][1]=addBPoint(0.0,0.5,0.5,TRUNK,n0);
		neck[3][1][1]=addBPoint(0.25,0.5,0.5,TRUNK,n0);
		neck[4][1][1]=addBPoint(0.5,0.5,0.5,TRUNK,n0);
		neck[0][2][1]=addBPoint(-0.5,1.0,0.5,TRUNK,n0);
		neck[1][2][1]=addBPoint(-0.25,1.0,0.5,TRUNK,n0);
		neck[2][2][1]=addBPoint(0.0,1.0,0.5,TRUNK,n0);
		neck[3][2][1]=addBPoint(0.25,1.0,0.5,TRUNK,n0);
		neck[4][2][1]=addBPoint(0.5,1.0,0.5,TRUNK,n0);
		
		neck[0][0][2]=head[0][0][0];
		neck[1][0][2]=head[1][0][0];
		neck[2][0][2]=head[2][0][0];
		neck[3][0][2]=head[3][0][0];
		neck[4][0][2]=head[4][0][0];		
		neck[0][1][2]=head[0][1][0];
		neck[1][1][2]=head[1][1][0];
		neck[2][1][2]=head[2][1][0];
		neck[3][1][2]=head[3][1][0];			
		neck[4][1][2]=head[4][1][0];
		neck[0][2][2]=head[0][2][0];
		neck[1][2][2]=head[1][2][0];
		neck[2][2][2]=head[2][2][0];
		neck[3][2][2]=head[3][2][0];			
		neck[4][2][2]=head[4][2][0];

		
		for (int i = 0; i < numx-1; i++) {

			for (int j = 0; j < ney-1; j++) {
				
				for (int k = 0; k < nez-1; k++) {

					if(i==0){

						addLine(neck[i][j][k],neck[i][j][k+1],neck[i][j+1][k+1],neck[i][j+1][k],Renderer3D.CAR_LEFT);
					}


					if(j==0){
					
						addLine(neck[i][j][k],neck[i+1][j][k],neck[i+1][j][k+1],neck[i][j][k+1],Renderer3D.CAR_BACK);

					}
					
					if(j+1==ney-1){		

						addLine(neck[i][j+1][k],neck[i][j+1][k+1],neck[i+1][j+1][k+1],neck[i+1][j+1][k],Renderer3D.CAR_FRONT);

					}

					if(i+1==numx-1){

						addLine(neck[i+1][j][k],neck[i+1][j+1][k],neck[i+1][j+1][k+1],neck[i+1][j][k+1],Renderer3D.CAR_RIGHT);
					}

				}
			}

		}
		
		
	
		
		//limbs:
		
		Segments lefLegFoot0=new Segments(xc-x_side*0.5,leg_side,0,leg_side,0,foot_length);
		Segments leflegShin0=new Segments(xc-x_side*0.5,leg_side,0,leg_side,foot_length,shinbone_length);
		Segments lefLegFem0=new Segments(xc-x_side*0.5,leg_side,0,leg_side,foot_length+shinbone_length,femur_length);
		
		Segments rigLegFoot0=new Segments(xc+x_side*0.5-leg_side,leg_side,0,leg_side,0,foot_length);
		Segments rigLegShin0=new Segments(xc+x_side*0.5-leg_side,leg_side,0,leg_side,foot_length,shinbone_length);
		Segments rigLegFem0=new Segments(xc+x_side*0.5-leg_side,leg_side,0,leg_side,foot_length+shinbone_length,femur_length);
	
		Segments lefArmHan0=new Segments(xc-x_side*0.5,leg_side,y_side-leg_side,leg_side,0,hand_length);
		Segments lefArmRad0=new Segments(xc-x_side*0.5,leg_side,y_side-leg_side,leg_side,hand_length,radius_length);
		Segments lefArmHum0=new Segments(xc-x_side*0.5,leg_side,y_side-leg_side,leg_side,hand_length+radius_length,humerus_length);
		
		Segments rigArmHan0=new Segments(xc+x_side*0.5-leg_side,leg_side,y_side-leg_side,leg_side,0,hand_length);
		Segments rigArmRad0=new Segments(xc+x_side*0.5-leg_side,leg_side,y_side-leg_side,leg_side,hand_length,radius_length);
		Segments rigArmHum0=new Segments(xc+x_side*0.5-leg_side,leg_side,y_side-leg_side,leg_side,hand_length+radius_length,humerus_length);
		
		//legs:	


		
		//back left thigh
		
		BPoint[][][] pBackLeftThigh=new BPoint[2][2][2];
		
		pBackLeftThigh[0][0][0]=addBPoint(0,0,0.0,LEFT_FEMUR,lefLegFem0);
		pBackLeftThigh[1][0][0]=addBPoint(1.0,0,0.0,LEFT_FEMUR,lefLegFem0);
		pBackLeftThigh[1][1][0]=addBPoint(1.0,1.0,0.0,LEFT_FEMUR,lefLegFem0);
		pBackLeftThigh[0][1][0]=addBPoint(0,1.0,0.0,LEFT_FEMUR,lefLegFem0);
		
		pBackLeftThigh[0][0][1]=body[0][0][0];
		pBackLeftThigh[1][0][1]=body[1][0][0];
		pBackLeftThigh[1][1][1]=body[1][1][0];
		pBackLeftThigh[0][1][1]=body[0][1][0];
		
		rotateYZ(pBackLeftThigh,pBackLeftThigh[0][1][1].y,pBackLeftThigh[0][1][1].z,bq0);

		LineData backLeftThighS0=addLine(pBackLeftThigh[0][0][0],pBackLeftThigh[0][0][1],pBackLeftThigh[0][1][1],pBackLeftThigh[0][1][0],Renderer3D.CAR_LEFT);
	
		LineData backLeftThighS1=addLine(pBackLeftThigh[0][1][0],pBackLeftThigh[0][1][1],pBackLeftThigh[1][1][1],pBackLeftThigh[1][1][0],Renderer3D.CAR_FRONT);
	
		LineData backLeftThighS2=addLine(pBackLeftThigh[1][1][0],pBackLeftThigh[1][1][1],pBackLeftThigh[1][0][1],pBackLeftThigh[1][0][0],Renderer3D.CAR_RIGHT);

		LineData backLeftThighS3=addLine(pBackLeftThigh[1][0][0],pBackLeftThigh[1][0][1],pBackLeftThigh[0][0][1],pBackLeftThigh[0][0][0],Renderer3D.CAR_BACK);

		//LineData backLeftTopThigh=addLine(pBackLeftThigh[0][0][0],pBackLeftThigh[1][0][0],pBackLeftThigh[1][1][0],pBackLeftThigh[0][1][0],Renderer3D.CAR_TOP);
		
		//backLeftLeg
			
		
		BPoint[][][] pBackLeftLeg=new BPoint[2][2][2];	
		
		pBackLeftLeg[0][0][0]=addBPoint(0,0,0,LEFT_SHINBONE,leflegShin0);
		pBackLeftLeg[1][0][0]=addBPoint(1.0,0,0,LEFT_SHINBONE,leflegShin0);
		pBackLeftLeg[1][1][0]=addBPoint(1.0,1.0,0,LEFT_SHINBONE,leflegShin0);
		pBackLeftLeg[0][1][0]=addBPoint(0,1.0,0,LEFT_SHINBONE,leflegShin0);
		
		pBackLeftLeg[0][0][1]=addBPoint(0,0,1.0,LEFT_SHINBONE,leflegShin0);
		pBackLeftLeg[1][0][1]=addBPoint(1.0,0,1.0,LEFT_SHINBONE,leflegShin0);
		pBackLeftLeg[1][1][1]=addBPoint(1.0,1.0,1.0,LEFT_SHINBONE,leflegShin0);
		pBackLeftLeg[0][1][1]=addBPoint(0,1.0,1.0,LEFT_SHINBONE,leflegShin0);
		
		rotateYZ(pBackLeftLeg,pBackLeftThigh[0][1][1].y,pBackLeftThigh[0][1][1].z,bq0);
		rotateYZ(pBackLeftLeg,pBackLeftLeg[0][0][1].y,pBackLeftLeg[0][0][1].z,bq1);

		
		LineData backLeftLeg=addLine(pBackLeftLeg[0][0][0],pBackLeftLeg[0][1][0],pBackLeftLeg[1][1][0],pBackLeftLeg[1][0][0],Renderer3D.CAR_BOTTOM);
				
		LineData backLeftLegS0=addLine(pBackLeftLeg[0][0][0],pBackLeftLeg[0][0][1],pBackLeftLeg[0][1][1],pBackLeftLeg[0][1][0],Renderer3D.CAR_LEFT);

		LineData backLeftLegS1=addLine(pBackLeftLeg[0][1][0],pBackLeftLeg[0][1][1],pBackLeftLeg[1][1][1],pBackLeftLeg[1][1][0],Renderer3D.CAR_FRONT);
	
		LineData backLeftLegS2=addLine(pBackLeftLeg[1][1][0],pBackLeftLeg[1][1][1],pBackLeftLeg[1][0][1],pBackLeftLeg[1][0][0],Renderer3D.CAR_RIGHT);
	
		LineData backLeftLegS3=addLine(pBackLeftLeg[1][0][0],pBackLeftLeg[1][0][1],pBackLeftLeg[0][0][1],pBackLeftLeg[0][0][0],Renderer3D.CAR_BACK);
		
		LineData backLeftKnee0=addLine(pBackLeftThigh[0][1][0],pBackLeftThigh[1][1][0],pBackLeftLeg[1][1][1],pBackLeftLeg[0][1][1],Renderer3D.CAR_FRONT);
		LineData backLeftKnee1=addLine(pBackLeftThigh[0][0][0],pBackLeftThigh[0][1][0],pBackLeftLeg[0][1][1],null,Renderer3D.CAR_LEFT);
		LineData backLeftKnee2=addLine(pBackLeftThigh[1][1][0],pBackLeftThigh[1][0][0],pBackLeftLeg[1][1][1],null,Renderer3D.CAR_RIGHT);
		
		//back left foot
		
		BPoint[][][] pBackLeftFoot=new BPoint[2][2][2];
		
		pBackLeftFoot[0][0][0]=addBPoint(0,0,0.0,LEFT_SHINBONE,lefLegFoot0);
		pBackLeftFoot[1][0][0]=addBPoint(1.0,0,0,LEFT_SHINBONE,lefLegFoot0);
		pBackLeftFoot[1][1][0]=addBPoint(1.0,1.0,0,LEFT_SHINBONE,lefLegFoot0);
		pBackLeftFoot[0][1][0]=addBPoint(0,1.0,0,LEFT_SHINBONE,lefLegFoot0);
		
		pBackLeftFoot[0][0][1]=addBPoint(0,0,1.0,LEFT_SHINBONE,lefLegFoot0);
		pBackLeftFoot[1][0][1]=addBPoint(1.0,0,1.0,LEFT_SHINBONE,lefLegFoot0);
		pBackLeftFoot[1][1][1]=addBPoint(1.0,1.0,1.0,LEFT_SHINBONE,lefLegFoot0);
		pBackLeftFoot[0][1][1]=addBPoint(0,1.0,1.0,LEFT_SHINBONE,lefLegFoot0);
		
		rotateYZ(pBackLeftFoot,pBackLeftThigh[0][1][1].y,pBackLeftThigh[0][1][1].z,bq0);
		rotateYZ(pBackLeftFoot,pBackLeftLeg[0][0][1].y,pBackLeftLeg[0][0][1].z,bq1);
		rotateYZ(pBackLeftFoot,pBackLeftFoot[0][1][1].y,pBackLeftFoot[0][1][1].z,bq2);
		
		LineData backLeftFoot=addLine(pBackLeftFoot[0][0][0],pBackLeftFoot[0][1][0],pBackLeftFoot[1][1][0],pBackLeftFoot[1][0][0],Renderer3D.CAR_BOTTOM);
				
		LineData backLeftFootS0=addLine(pBackLeftFoot[0][0][0],pBackLeftFoot[0][0][1],pBackLeftFoot[0][1][1],pBackLeftFoot[0][1][0],Renderer3D.CAR_LEFT);

		LineData backLeftFootS1=addLine(pBackLeftFoot[0][1][0],pBackLeftFoot[0][1][1],pBackLeftFoot[1][1][1],pBackLeftFoot[1][1][0],Renderer3D.CAR_FRONT);
	
		LineData backLeftFootS2=addLine(pBackLeftFoot[1][1][0],pBackLeftFoot[1][1][1],pBackLeftFoot[1][0][1],pBackLeftFoot[1][0][0],Renderer3D.CAR_RIGHT);
	
		LineData backLeftFootS3=addLine(pBackLeftFoot[1][0][0],pBackLeftFoot[1][0][1],pBackLeftFoot[0][0][1],pBackLeftFoot[0][0][0],Renderer3D.CAR_BACK);
		
		LineData backLeftAnkle0=addLine(pBackLeftFoot[0][0][1],pBackLeftFoot[1][0][1],pBackLeftLeg[1][0][0],pBackLeftLeg[0][0][0],Renderer3D.CAR_BACK);
		LineData backLeftAnkle1=addLine(pBackLeftLeg[0][0][0],pBackLeftLeg[0][1][0],pBackLeftFoot[0][0][1],null,Renderer3D.CAR_LEFT);
		LineData backLeftAnkle2=addLine( pBackLeftFoot[1][0][1],pBackLeftLeg[1][1][0],pBackLeftLeg[1][0][0],null,Renderer3D.CAR_RIGHT);

		//back right thigh
		
		
		BPoint[][][] pBackRightThigh=new BPoint[2][2][2];
		
		pBackRightThigh[0][0][0]=addBPoint(0,0,0,RIGHT_FEMUR,rigLegFem0);
		pBackRightThigh[1][0][0]=addBPoint(1.0,0,0,RIGHT_FEMUR,rigLegFem0);
		pBackRightThigh[1][1][0]=addBPoint(1.0,1.0,0,RIGHT_FEMUR,rigLegFem0);
		pBackRightThigh[0][1][0]=addBPoint(0,1.0,0,RIGHT_FEMUR,rigLegFem0);
		
		pBackRightThigh[0][0][1]=body[numx-2][0][0];
		pBackRightThigh[1][0][1]=body[numx-1][0][0];
		pBackRightThigh[1][1][1]=body[numx-1][1][0];
		pBackRightThigh[0][1][1]=body[numx-2][1][0];
		
		rotateYZ(pBackRightThigh,pBackRightThigh[1][1][1].y,pBackRightThigh[1][1][1].z,bq0);

		LineData backRightThighS0=addLine(pBackRightThigh[0][0][0],pBackRightThigh[0][0][1],pBackRightThigh[0][1][1],pBackRightThigh[0][1][0],Renderer3D.CAR_LEFT);
	
		LineData backRightThighS1=addLine(pBackRightThigh[0][1][0],pBackRightThigh[0][1][1],pBackRightThigh[1][1][1],pBackRightThigh[1][1][0],Renderer3D.CAR_FRONT);

		LineData backRightThighS2=addLine(pBackRightThigh[1][1][0],pBackRightThigh[1][1][1],pBackRightThigh[1][0][1],pBackRightThigh[1][0][0],Renderer3D.CAR_RIGHT);
	
		LineData backRightThighS3=addLine(pBackRightThigh[1][0][0],pBackRightThigh[1][0][1],pBackRightThigh[0][0][1],pBackRightThigh[0][0][0],Renderer3D.CAR_BACK);

				
		
		//LineData backRightTopThigh=addLine(pBackRightThigh[0][0][0],pBackRightThigh[1][0][0],pBackRightThigh[1][1][0],pBackRightThigh[0][1][0],Renderer3D.CAR_TOP);
		
		
		//backRightLeg
		
		
		BPoint[][][] pBackRightLeg=new BPoint[2][2][2];
		
		pBackRightLeg[0][0][0]=addBPoint(0,0,0,RIGHT_SHINBONE,rigLegShin0);
		pBackRightLeg[1][0][0]=addBPoint(1.0,0,0,RIGHT_SHINBONE,rigLegShin0);
		pBackRightLeg[1][1][0]=addBPoint(1.0,1.0,0,RIGHT_SHINBONE,rigLegShin0);
		pBackRightLeg[0][1][0]=addBPoint(0,1.0,0,RIGHT_SHINBONE,rigLegShin0);
		
		pBackRightLeg[0][0][1]=addBPoint(0,0,1.0,RIGHT_SHINBONE,rigLegShin0);
		pBackRightLeg[1][0][1]=addBPoint(1.0,0,1.0,RIGHT_SHINBONE,rigLegShin0);
		pBackRightLeg[1][1][1]=addBPoint(1.0,1.0,1.0,RIGHT_SHINBONE,rigLegShin0);
		pBackRightLeg[0][1][1]=addBPoint(0,1.0,1.0,RIGHT_SHINBONE,rigLegShin0);
		
		rotateYZ(pBackRightLeg,pBackRightThigh[1][1][1].y,pBackRightThigh[1][1][1].z,bq0);
		rotateYZ(pBackRightLeg,pBackRightLeg[0][0][1].y,pBackRightLeg[0][0][1].z,bq1);
		
		LineData backRightLeg=addLine(pBackRightLeg[0][0][0],pBackRightLeg[0][1][0],pBackRightLeg[1][1][0],pBackRightLeg[1][0][0],Renderer3D.CAR_BOTTOM);
		
		LineData backRightLegS0=addLine(pBackRightLeg[0][0][0],pBackRightLeg[0][0][1],pBackRightLeg[0][1][1],pBackRightLeg[0][1][0],Renderer3D.CAR_LEFT);
	
		LineData backRightLegS1=addLine(pBackRightLeg[0][1][0],pBackRightLeg[0][1][1],pBackRightLeg[1][1][1],pBackRightLeg[1][1][0],Renderer3D.CAR_FRONT);
	
		LineData backRightLegS2=addLine(pBackRightLeg[1][1][0],pBackRightLeg[1][1][1],pBackRightLeg[1][0][1],pBackRightLeg[1][0][0],Renderer3D.CAR_RIGHT);
	
		LineData backRightLegS3=addLine(pBackRightLeg[1][0][0],pBackRightLeg[1][0][1],pBackRightLeg[0][0][1],pBackRightLeg[0][0][0],Renderer3D.CAR_BACK);
		
		LineData backRightKnee0=addLine(pBackRightThigh[0][1][0],pBackRightThigh[1][1][0],pBackRightLeg[1][1][1],pBackRightLeg[0][1][1],Renderer3D.CAR_FRONT);
		LineData backRightKnee1=addLine(pBackRightThigh[0][0][0],pBackRightThigh[0][1][0],pBackRightLeg[0][1][1],null,Renderer3D.CAR_LEFT);
		LineData backRightKnee2=addLine(pBackRightThigh[1][1][0],pBackRightThigh[1][0][0],pBackRightLeg[1][1][1],null,Renderer3D.CAR_RIGHT);
	
		//back Right Foot
		
		
		BPoint[][][] pBackRightFoot=new BPoint[2][2][2];
		
		pBackRightFoot[0][0][0]=addBPoint(0,0,0,RIGHT_SHINBONE,rigLegFoot0);
		pBackRightFoot[1][0][0]=addBPoint(1.0,0,0,RIGHT_SHINBONE,rigLegFoot0);
		pBackRightFoot[1][1][0]=addBPoint(1.0,1,0,RIGHT_SHINBONE,rigLegFoot0);
		pBackRightFoot[0][1][0]=addBPoint(0,1,0,RIGHT_SHINBONE,rigLegFoot0);
		
		pBackRightFoot[0][0][1]=addBPoint(0,0,1,RIGHT_SHINBONE,rigLegFoot0);
		pBackRightFoot[1][0][1]=addBPoint(1.0,0,1,RIGHT_SHINBONE,rigLegFoot0);
		pBackRightFoot[1][1][1]=addBPoint(1.0,1.0,1,RIGHT_SHINBONE,rigLegFoot0);
		pBackRightFoot[0][1][1]=addBPoint(0,1.0,1,RIGHT_SHINBONE,rigLegFoot0);
		
		rotateYZ(pBackRightFoot,pBackRightThigh[1][1][1].y,pBackRightThigh[1][1][1].z,bq0);
		rotateYZ(pBackRightFoot,pBackRightLeg[0][0][1].y,pBackRightLeg[0][0][1].z,bq1);
		rotateYZ(pBackRightFoot,pBackRightFoot[0][1][1].y,pBackRightFoot[0][1][1].z,bq2);
		
		LineData backRightFoot=addLine(pBackRightFoot[0][0][0],pBackRightFoot[0][1][0],pBackRightFoot[1][1][0],pBackRightFoot[1][0][0],Renderer3D.CAR_BOTTOM);
		
		LineData backRightFootS0=addLine(pBackRightFoot[0][0][0],pBackRightFoot[0][0][1],pBackRightFoot[0][1][1],pBackRightFoot[0][1][0],Renderer3D.CAR_LEFT);
	
		LineData backRightFootS1=addLine(pBackRightFoot[0][1][0],pBackRightFoot[0][1][1],pBackRightFoot[1][1][1],pBackRightFoot[1][1][0],Renderer3D.CAR_FRONT);
	
		LineData backRightFootS2=addLine(pBackRightFoot[1][1][0],pBackRightFoot[1][1][1],pBackRightFoot[1][0][1],pBackRightFoot[1][0][0],Renderer3D.CAR_RIGHT);
	
		LineData backRightFootS3=addLine(pBackRightFoot[1][0][0],pBackRightFoot[1][0][1],pBackRightFoot[0][0][1],pBackRightFoot[0][0][0],Renderer3D.CAR_BACK);
		
		LineData backRightAnkle0=addLine(pBackRightFoot[0][0][1],pBackRightFoot[1][0][1],pBackRightLeg[1][0][0],pBackRightLeg[0][0][0],Renderer3D.CAR_BACK);
		LineData backRightAnkle1=addLine(pBackRightLeg[0][0][0],pBackRightLeg[0][1][0],pBackRightFoot[0][0][1],null,Renderer3D.CAR_LEFT);
		LineData backRightAnkle2=addLine( pBackRightFoot[1][0][1],pBackRightLeg[1][1][0],pBackRightLeg[1][0][0],null,Renderer3D.CAR_RIGHT);


		//left arm
		
		
		BPoint[][][] pFrontLeftArm=new BPoint[2][2][2];
		
		pFrontLeftArm[0][0][0]=addBPoint(0,0,0,LEFT_HOMERUS,lefArmHum0);
		pFrontLeftArm[1][0][0]=addBPoint(1.0,0,0,LEFT_HOMERUS,lefArmHum0);
		pFrontLeftArm[1][1][0]=addBPoint(1.0,1.0,0,LEFT_HOMERUS,lefArmHum0);
		pFrontLeftArm[0][1][0]=addBPoint(0,1.0,0,LEFT_HOMERUS,lefArmHum0);
		
		pFrontLeftArm[0][0][1]=body[0][numy-2][0];
		pFrontLeftArm[1][0][1]=body[1][numy-2][0];
		pFrontLeftArm[1][1][1]=body[1][numy-1][0];
		pFrontLeftArm[0][1][1]=body[0][numy-1][0];
		
		rotateYZ(pFrontLeftArm,pFrontLeftArm[0][0][1].y,pFrontLeftArm[0][0][1].z,fq0);
		
		LineData bottomLeftArm=addLine(pFrontLeftArm[0][0][0],pFrontLeftArm[0][1][0],pFrontLeftArm[1][1][0],pFrontLeftArm[1][0][0],Renderer3D.CAR_BOTTOM);	
		
		LineData FrontLeftArmS0=addLine(pFrontLeftArm[0][0][0],pFrontLeftArm[0][0][1],pFrontLeftArm[0][1][1],pFrontLeftArm[0][1][0],Renderer3D.CAR_LEFT);

		LineData FrontLeftArmS1=addLine(pFrontLeftArm[0][1][0],pFrontLeftArm[0][1][1],pFrontLeftArm[1][1][1],pFrontLeftArm[1][1][0],Renderer3D.CAR_FRONT);
	
		LineData FrontLeftArmS2=addLine(pFrontLeftArm[1][1][0],pFrontLeftArm[1][1][1],pFrontLeftArm[1][0][1],pFrontLeftArm[1][0][0],Renderer3D.CAR_RIGHT);

		LineData FrontLeftArmS3=addLine(pFrontLeftArm[1][0][0],pFrontLeftArm[1][0][1],pFrontLeftArm[0][0][1],pFrontLeftArm[0][0][0],Renderer3D.CAR_BACK);
	
		
		//frontLeftForearm
		
		BPoint[][][] pFrontLeftForearm=new BPoint[2][2][2];
		
		pFrontLeftForearm[0][0][0]=addBPoint(0,0,0,LEFT_RADIUS,lefArmRad0);
		pFrontLeftForearm[1][0][0]=addBPoint(1.0,0,0,LEFT_RADIUS,lefArmRad0);
		pFrontLeftForearm[1][1][0]=addBPoint(1.0,1.0,0,LEFT_RADIUS,lefArmRad0);
		pFrontLeftForearm[0][1][0]=addBPoint(0,1.0,0,LEFT_RADIUS,lefArmRad0);
		
		pFrontLeftForearm[0][0][1]=addBPoint(0,0,1.0,LEFT_RADIUS,lefArmRad0);
		pFrontLeftForearm[1][0][1]=addBPoint(1.0,0,1.0,LEFT_RADIUS,lefArmRad0);
		pFrontLeftForearm[1][1][1]=addBPoint(1.0,1.0,1.0,LEFT_RADIUS,lefArmRad0);
		pFrontLeftForearm[0][1][1]=addBPoint(0,1.0,1.0,LEFT_RADIUS,lefArmRad0);
		
		rotateYZ(pFrontLeftForearm,pFrontLeftArm[0][0][1].y,pFrontLeftArm[0][0][1].z,fq0);
		rotateYZ(pFrontLeftForearm,pFrontLeftForearm[0][1][1].y,pFrontLeftForearm[0][1][1].z,fq1);
		
		LineData FrontLeftForearm=addLine(pFrontLeftForearm[0][0][0],pFrontLeftForearm[0][1][0],pFrontLeftForearm[1][1][0],pFrontLeftForearm[1][0][0],Renderer3D.CAR_BOTTOM);
		
		LineData FrontLeftForearmS0=addLine(pFrontLeftForearm[0][0][0],pFrontLeftForearm[0][0][1],pFrontLeftForearm[0][1][1],pFrontLeftForearm[0][1][0],Renderer3D.CAR_LEFT);
	
		LineData FrontLeftForearmS1=addLine(pFrontLeftForearm[0][1][0],pFrontLeftForearm[0][1][1],pFrontLeftForearm[1][1][1],pFrontLeftForearm[1][1][0],Renderer3D.CAR_FRONT);

		LineData FrontLeftForearmS2=addLine(pFrontLeftForearm[1][1][0],pFrontLeftForearm[1][1][1],pFrontLeftForearm[1][0][1],pFrontLeftForearm[1][0][0],Renderer3D.CAR_RIGHT);
	
		LineData FrontLeftForearmS3=addLine(pFrontLeftForearm[1][0][0],pFrontLeftForearm[1][0][1],pFrontLeftForearm[0][0][1],pFrontLeftForearm[0][0][0],Renderer3D.CAR_BACK);
	
		LineData backLeftElbow0=addLine(pFrontLeftForearm[0][0][1],pFrontLeftForearm[1][0][1],pFrontLeftArm[1][0][0],pFrontLeftArm[0][0][0],Renderer3D.CAR_BACK);
		LineData backLeftElbow1=addLine(pFrontLeftArm[0][0][0],pFrontLeftArm[0][1][0],pFrontLeftForearm[0][0][1],null,Renderer3D.CAR_LEFT);
		LineData backLeftElbow2=addLine( pFrontLeftForearm[1][0][1],pFrontLeftArm[1][1][0],pFrontLeftArm[1][0][0],null,Renderer3D.CAR_RIGHT);
		
		//frontLeft Hand
		
		BPoint[][][] pFrontLeftHand=new BPoint[2][2][2];
		
		pFrontLeftHand[0][0][0]=addBPoint(0,0,0,LEFT_RADIUS,lefArmHan0);
		pFrontLeftHand[1][0][0]=addBPoint(1.0,0,0,LEFT_RADIUS,lefArmHan0);
		pFrontLeftHand[1][1][0]=addBPoint(1.0,1.0,0,LEFT_RADIUS,lefArmHan0);
		pFrontLeftHand[0][1][0]=addBPoint(0,1.0,0,LEFT_RADIUS,lefArmHan0);
		
		pFrontLeftHand[0][0][1]=addBPoint(0,0,1.0,LEFT_RADIUS,lefArmHan0);
		pFrontLeftHand[1][0][1]=addBPoint(1.0,0,1.0,LEFT_RADIUS,lefArmHan0);
		pFrontLeftHand[1][1][1]=addBPoint(1.0,1.0,1.0,LEFT_RADIUS,lefArmHan0);
		pFrontLeftHand[0][1][1]=addBPoint(0,1.0,1.0,LEFT_RADIUS,lefArmHan0);
		
		rotateYZ(pFrontLeftHand,pFrontLeftArm[0][0][1].y,pFrontLeftArm[0][0][1].z,fq0);
		rotateYZ(pFrontLeftHand,pFrontLeftForearm[0][1][1].y,pFrontLeftForearm[0][1][1].z,fq1);
		rotateYZ(pFrontLeftHand,pFrontLeftHand[0][1][1].y,pFrontLeftHand[0][1][1].z,fq2);
		
		LineData FrontLeftHand=addLine(pFrontLeftHand[0][0][0],pFrontLeftHand[0][1][0],pFrontLeftHand[1][1][0],pFrontLeftHand[1][0][0],Renderer3D.CAR_BOTTOM);
		
		LineData FrontLeftHandS0=addLine(pFrontLeftHand[0][0][0],pFrontLeftHand[0][0][1],pFrontLeftHand[0][1][1],pFrontLeftHand[0][1][0],Renderer3D.CAR_LEFT);
	
		LineData FrontLeftHandS1=addLine(pFrontLeftHand[0][1][0],pFrontLeftHand[0][1][1],pFrontLeftHand[1][1][1],pFrontLeftHand[1][1][0],Renderer3D.CAR_FRONT);

		LineData FrontLeftHandS2=addLine(pFrontLeftHand[1][1][0],pFrontLeftHand[1][1][1],pFrontLeftHand[1][0][1],pFrontLeftHand[1][0][0],Renderer3D.CAR_RIGHT);
	
		LineData FrontLeftHandS3=addLine(pFrontLeftHand[1][0][0],pFrontLeftHand[1][0][1],pFrontLeftHand[0][0][1],pFrontLeftHand[0][0][0],Renderer3D.CAR_BACK);
		
		LineData backLeftWrist0=addLine(pFrontLeftHand[0][0][1],pFrontLeftHand[1][0][1],pFrontLeftForearm[1][0][0],pFrontLeftForearm[0][0][0],Renderer3D.CAR_BACK);
		LineData backLeftWrist1=addLine(pFrontLeftForearm[0][0][0],pFrontLeftForearm[0][1][0],pFrontLeftHand[0][0][1],null,Renderer3D.CAR_LEFT);
		LineData backLeftWrist2=addLine( pFrontLeftHand[1][0][1],pFrontLeftForearm[1][1][0],pFrontLeftForearm[1][0][0],null,Renderer3D.CAR_RIGHT);
		
		//right arm
		
		BPoint[][][] pFrontRightArm=new BPoint[2][2][2];
		
		pFrontRightArm[0][0][0]=addBPoint(0,0.0,0,RIGHT_HOMERUS,rigArmHum0);
		pFrontRightArm[1][0][0]=addBPoint(1.0,0.0,0,RIGHT_HOMERUS,rigArmHum0);
		pFrontRightArm[1][1][0]=addBPoint(1.0,1.0,0,RIGHT_HOMERUS,rigArmHum0);
		pFrontRightArm[0][1][0]=addBPoint(0,1.0,0,RIGHT_HOMERUS,rigArmHum0);
		
		pFrontRightArm[0][0][1]=body[numx-2][numy-2][0];
		pFrontRightArm[1][0][1]=body[numx-1][numy-2][0];
		pFrontRightArm[1][1][1]=body[numx-1][numy-1][0];
		pFrontRightArm[0][1][1]=body[numx-2][numy-1][0];
		
		rotateYZ(pFrontRightArm,pFrontRightArm[1][0][1].y,pFrontRightArm[1][0][1].z,fq0);
		
		LineData bottomRightArm=addLine(pFrontRightArm[0][0][0],pFrontRightArm[0][1][0],pFrontRightArm[1][1][0],pFrontRightArm[1][0][0],Renderer3D.CAR_BOTTOM);	
		
		LineData frontRightArmS0=addLine(pFrontRightArm[0][0][0],pFrontRightArm[0][0][1],pFrontRightArm[0][1][1],pFrontRightArm[0][1][0],Renderer3D.CAR_LEFT);
	
		LineData frontRightArmS1=addLine(pFrontRightArm[0][1][0],pFrontRightArm[0][1][1],pFrontRightArm[1][1][1],pFrontRightArm[1][1][0],Renderer3D.CAR_FRONT);
	
		LineData frontRightArmS2=addLine(pFrontRightArm[1][1][0],pFrontRightArm[1][1][1],pFrontRightArm[1][0][1],pFrontRightArm[1][0][0],Renderer3D.CAR_RIGHT);
	
		LineData frontRightArmS3=addLine(pFrontRightArm[1][0][0],pFrontRightArm[1][0][1],pFrontRightArm[0][0][1],pFrontRightArm[0][0][0],Renderer3D.CAR_BACK);
	
	
			
		//frontRightForeArm
		
		BPoint[][][] pFrontRightForearm=new BPoint[2][2][2];
		
		pFrontRightForearm[0][0][0]=addBPoint(0,0,0,RIGHT_RADIUS,rigArmRad0);
		pFrontRightForearm[1][0][0]=addBPoint(1,0,0,RIGHT_RADIUS,rigArmRad0);
		pFrontRightForearm[1][1][0]=addBPoint(1,1,0,RIGHT_RADIUS,rigArmRad0);
		pFrontRightForearm[0][1][0]=addBPoint(0,1,0,RIGHT_RADIUS,rigArmRad0);
		
		pFrontRightForearm[0][0][1]=addBPoint(0,0,1,RIGHT_RADIUS,rigArmRad0);
		pFrontRightForearm[1][0][1]=addBPoint(1,0,1,RIGHT_RADIUS,rigArmRad0);
		pFrontRightForearm[1][1][1]=addBPoint(1,1,1,RIGHT_RADIUS,rigArmRad0);
		pFrontRightForearm[0][1][1]=addBPoint(0,1,1,RIGHT_RADIUS,rigArmRad0);
		
		rotateYZ(pFrontRightForearm,pFrontRightArm[1][0][1].y,pFrontRightArm[1][0][1].z,fq0);
		rotateYZ(pFrontRightForearm,pFrontRightForearm[0][1][1].y,pFrontRightForearm[0][1][1].z,fq1);
		
		LineData FrontRightForearm=addLine(pFrontRightForearm[0][0][0],pFrontRightForearm[0][1][0],pFrontRightForearm[1][1][0],pFrontRightForearm[1][0][0],Renderer3D.CAR_FRONT);
			
		LineData frontRightForearmS0=addLine(pFrontRightForearm[0][0][0],pFrontRightForearm[0][0][1],pFrontRightForearm[0][1][1],pFrontRightForearm[0][1][0],Renderer3D.CAR_LEFT);

		LineData frontRightForearmS1=addLine(pFrontRightForearm[0][1][0],pFrontRightForearm[0][1][1],pFrontRightForearm[1][1][1],pFrontRightForearm[1][1][0],Renderer3D.CAR_FRONT);

		LineData frontRightForearmS2=addLine(pFrontRightForearm[1][1][0],pFrontRightForearm[1][1][1],pFrontRightForearm[1][0][1],pFrontRightForearm[1][0][0],Renderer3D.CAR_RIGHT);
	
		LineData frontRightForearmS3=addLine(pFrontRightForearm[1][0][0],pFrontRightForearm[1][0][1],pFrontRightForearm[0][0][1],pFrontRightForearm[0][0][0],Renderer3D.CAR_BACK);
			
		
		LineData backRightElbow0=addLine(pFrontRightForearm[0][0][1],pFrontRightForearm[1][0][1],pFrontRightArm[1][0][0],pFrontRightArm[0][0][0],Renderer3D.CAR_BACK);
		LineData backRightElbow1=addLine(pFrontRightArm[0][0][0],pFrontRightArm[0][1][0],pFrontRightForearm[0][0][1],null,Renderer3D.CAR_LEFT);
		LineData backRightElbow2=addLine( pFrontRightForearm[1][0][1],pFrontRightArm[1][1][0],pFrontRightArm[1][0][0],null,Renderer3D.CAR_RIGHT);
		
		//frontRight Hand
		
		BPoint[][][] pFrontRightHand=new BPoint[2][2][2];
		
		pFrontRightHand[0][0][0]=addBPoint(0,0,0,RIGHT_RADIUS,rigArmHan0);
		pFrontRightHand[1][0][0]=addBPoint(1,0,0,RIGHT_RADIUS,rigArmHan0);
		pFrontRightHand[1][1][0]=addBPoint(1,1,0,RIGHT_RADIUS,rigArmHan0);
		pFrontRightHand[0][1][0]=addBPoint(0,1,0,RIGHT_RADIUS,rigArmHan0);
		
		pFrontRightHand[0][0][1]=addBPoint(0,0,1,RIGHT_RADIUS,rigArmHan0);
		pFrontRightHand[1][0][1]=addBPoint(1,0,1,RIGHT_RADIUS,rigArmHan0);
		pFrontRightHand[1][1][1]=addBPoint(1,1,1,RIGHT_RADIUS,rigArmHan0);
		pFrontRightHand[0][1][1]=addBPoint(0,1,1,RIGHT_RADIUS,rigArmHan0);
		
		
		rotateYZ(pFrontRightHand,pFrontRightArm[1][0][1].y,pFrontRightArm[1][0][1].z,fq0);
		rotateYZ(pFrontRightHand,pFrontRightForearm[0][1][1].y,pFrontRightForearm[0][1][1].z,fq1);
		rotateYZ(pFrontRightHand,pFrontRightHand[0][1][1].y,pFrontRightHand[0][1][1].z,fq2);
			
		LineData FrontRightHand=addLine(pFrontRightHand[0][0][0],pFrontRightHand[0][1][0],pFrontRightHand[1][1][0],pFrontRightHand[1][0][0],Renderer3D.CAR_FRONT);
			
		LineData frontRightHandS0=addLine(pFrontRightHand[0][0][0],pFrontRightHand[0][0][1],pFrontRightHand[0][1][1],pFrontRightHand[0][1][0],Renderer3D.CAR_LEFT);

		LineData frontRightHandS1=addLine(pFrontRightHand[0][1][0],pFrontRightHand[0][1][1],pFrontRightHand[1][1][1],pFrontRightHand[1][1][0],Renderer3D.CAR_FRONT);

		LineData frontRightHandS2=addLine(pFrontRightHand[1][1][0],pFrontRightHand[1][1][1],pFrontRightHand[1][0][1],pFrontRightHand[1][0][0],Renderer3D.CAR_RIGHT);
	
		LineData frontRightHandS3=addLine(pFrontRightHand[1][0][0],pFrontRightHand[1][0][1],pFrontRightHand[0][0][1],pFrontRightHand[0][0][0],Renderer3D.CAR_BACK);
		
		LineData backRightWrist0=addLine(pFrontRightHand[0][0][1],pFrontRightHand[1][0][1],pFrontRightForearm[1][0][0],pFrontRightForearm[0][0][0],Renderer3D.CAR_BACK);
		LineData backRightWrist1=addLine(pFrontRightForearm[0][0][0],pFrontRightForearm[0][1][0],pFrontRightHand[0][0][1],null,Renderer3D.CAR_LEFT);
		LineData backRightWrist2=addLine( pFrontRightHand[1][0][1],pFrontRightForearm[1][1][0],pFrontRightForearm[1][0][0],null,Renderer3D.CAR_RIGHT);
	
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}



	private BPoint[][][] buildQuadrupedHeadMesh(double hz, double xc) {

		
		double height0=head_DZ;		
		double height1=head_DZ;
		double height2=head_DZ;
		double height3=head_DZ*0.6;
		double height4=head_DZ*0.4;
		
		double with0=head_DX*0.5;
		double with1=head_DX*0.957;
		double with2=head_DX*0.79;
		double with3=head_DX*0.39;
		double with4=head_DX*0.3;
		
		Segments h0=new Segments(xc,with0,y_side-neck_side,head_DY,hz,height0);
		Segments h1=new Segments(xc,with1,y_side-neck_side,head_DY,hz,height1);
		Segments h2=new Segments(xc,with2,y_side-neck_side,head_DY,hz,height2);
		Segments h3=new Segments(xc,with3,y_side-neck_side,head_DY,hz,height3);
		Segments h4=new Segments(xc,with4,y_side-neck_side,head_DY,hz,height4);
		
		int hnx=5;
		int hny=5;
		int hnz=5;

		BPoint[][][] head=new BPoint[hnx][hny][hnz];

		head[0][0][0]=addBPoint(-0.5,0.0,0,HEAD,h0);
		head[1][0][0]=addBPoint(-0.25,0.0,0,HEAD,h0);
		head[2][0][0]=addBPoint(0.0,0.0,0,HEAD,h0);	
		head[3][0][0]=addBPoint(0.25,0.0,0,HEAD,h0);	
		head[4][0][0]=addBPoint(0.5,0.0,0,HEAD,h0);	
		head[0][0][1]=addBPoint(-0.5,0.0,0.25,HEAD,h0);
		head[1][0][1]=addBPoint(-0.25,0.0,0.25,HEAD,h0);
		head[2][0][1]=addBPoint(0.0,0.0,0.25,HEAD,h0);
		head[3][0][1]=addBPoint(0.25,0.0,0.25,HEAD,h0);
		head[4][0][1]=addBPoint(0.5,0.0,0.25,HEAD,h0);
		head[0][0][2]=addBPoint(-0.5,0.0,0.5,HEAD,h0);
		head[1][0][2]=addBPoint(-0.25,0.0,0.5,HEAD,h0);
		head[2][0][2]=addBPoint(0.0,0.0,0.5,HEAD,h0);
		head[3][0][2]=addBPoint(0.25,0.0,0.5,HEAD,h0);
		head[4][0][2]=addBPoint(0.5,0.0,0.5,HEAD,h0);
		head[0][0][3]=addBPoint(-0.5,0.0,0.75,HEAD,h0);
		head[1][0][3]=addBPoint(-0.25,0.0,0.75,HEAD,h0);
		head[2][0][3]=addBPoint(0.0,0.0,0.75,HEAD,h0);
		head[3][0][3]=addBPoint(0.25,0.0,0.75,HEAD,h0);
		head[4][0][3]=addBPoint(0.5,0.0,0.75,HEAD,h0);
		head[0][0][4]=addBPoint(-0.5,0.0,1.0,HEAD,h0);
		head[1][0][4]=addBPoint(-0.25,0.0,1.0,HEAD,h0);
		head[2][0][4]=addBPoint(0.0,0.0,1.0,HEAD,h0);
		head[3][0][4]=addBPoint(0.25,0.0,1.0,HEAD,h0);
		head[4][0][4]=addBPoint(0.5,0.0,1.0,HEAD,h0);
		
		head[0][1][0]=addBPoint(-0.5,.25,0,HEAD,h1);
		head[1][1][0]=addBPoint(-0.25,.25,0,HEAD,h1);
		head[2][1][0]=addBPoint(0.0,.25,0,HEAD,h1);		
		head[3][1][0]=addBPoint(0.25,.25,0,HEAD,h1);	
		head[4][1][0]=addBPoint(0.5,.25,0,HEAD,h1);
		head[0][1][1]=addBPoint(-0.5,.25,0.25,HEAD,h1);
		head[4][1][1]=addBPoint(0.5,.25,0.25,HEAD,h1);
		head[0][1][2]=addBPoint(-0.5,.25,0.5,HEAD,h1);		
		head[4][1][2]=addBPoint(0.5,.25,0.5,HEAD,h1);
		head[0][1][3]=addBPoint(-0.5,.25,0.75,HEAD,h1);		
		head[4][1][3]=addBPoint(0.5,.25,0.75,HEAD,h1);
		head[0][1][4]=addBPoint(-0.5,.25,1.0,HEAD,h1);	
		head[1][1][4]=addBPoint(-0.25,.25,1.0,HEAD,h1);		
		head[2][1][4]=addBPoint(0.0,.25,1.0,HEAD,h1);	
		head[3][1][4]=addBPoint(0.25,.25,1.0,HEAD,h1);	
		head[4][1][4]=addBPoint(0.5,.25,1.0,HEAD,h1);	
		
		head[0][2][0]=addBPoint(-0.5,0.5,0,HEAD,h2);
		head[1][2][0]=addBPoint(-0.25,0.5,0,HEAD,h2);
		head[2][2][0]=addBPoint(0.0,0.5,0,HEAD,h2);	
		head[3][2][0]=addBPoint(0.25,0.5,0,HEAD,h2);
		head[4][2][0]=addBPoint(0.5,0.5,0,HEAD,h2);
		head[0][2][1]=addBPoint(-0.5,0.5,0.25,HEAD,h2);		
		head[4][2][1]=addBPoint(0.5,0.5,0.25,HEAD,h2);
		head[0][2][2]=addBPoint(-0.5,0.5,0.5,HEAD,h2);		
		head[4][2][2]=addBPoint(0.5,0.5,0.5,HEAD,h2);
		head[0][2][3]=addBPoint(-0.5,0.5,0.75,HEAD,h2);		
		head[4][2][3]=addBPoint(0.5,0.5,0.75,HEAD,h2);
		head[0][2][4]=addBPoint(-0.5,0.5,1.0,HEAD,h2);	
		head[1][2][4]=addBPoint(-0.25,0.5,1.0,HEAD,h2);	
		head[2][2][4]=addBPoint(0.0,0.5,1.0,HEAD,h2);
		head[3][2][4]=addBPoint(0.25,0.5,1.0,HEAD,h2);
		head[4][2][4]=addBPoint(0.5,0.5,1.0,HEAD,h2);
		
		head[0][3][0]=addBPoint(-0.5,0.75,0,HEAD,h3);
		head[1][3][0]=addBPoint(-0.25,0.75,0,HEAD,h3);
		head[2][3][0]=addBPoint(0.0,0.75,0,HEAD,h3);
		head[3][3][0]=addBPoint(0.25,0.75,0,HEAD,h3);
		head[4][3][0]=addBPoint(0.5,0.75,0,HEAD,h3);
		head[0][3][1]=addBPoint(-0.5,0.75,0.25,HEAD,h3);
		head[4][3][1]=addBPoint(0.5,0.75,0.25,HEAD,h3);
		head[0][3][2]=addBPoint(-0.5,0.75,0.5,HEAD,h3);
		head[4][3][2]=addBPoint(0.5,0.75,0.5,HEAD,h3);
		head[0][3][3]=addBPoint(-0.5,0.75,0.75,HEAD,h3);
		head[4][3][3]=addBPoint(0.5,0.75,0.75,HEAD,h3);
		head[0][3][4]=addBPoint(-0.5,0.75,1.0,HEAD,h3);	
		head[1][3][4]=addBPoint(-0.25,0.75,1.0,HEAD,h3);	
		head[2][3][4]=addBPoint(0.0,0.75,1.0,HEAD,h3);	
		head[3][3][4]=addBPoint(0.25,0.75,1.0,HEAD,h3);	
		head[4][3][4]=addBPoint(0.5,0.75,1.0,HEAD,h3);	
		
		head[0][4][0]=addBPoint(-0.5,1.0,0.0,HEAD,h4);
		head[1][4][0]=addBPoint(-0.25,1.0,0.0,HEAD,h4);
		head[2][4][0]=addBPoint(0.0,1.0,0.0,HEAD,h4);
		head[3][4][0]=addBPoint(0.25,1.0,0.0,HEAD,h4);
		head[4][4][0]=addBPoint(0.5,1.0,0.0,HEAD,h4);
		head[0][4][1]=addBPoint(-0.5,1.0,0.25,HEAD,h4);
		head[1][4][1]=addBPoint(-0.25,1.0,0.25,HEAD,h4);
		head[2][4][1]=addBPoint(0.0,1.0,0.25,HEAD,h4);
		head[3][4][1]=addBPoint(0.25,1.0,0.25,HEAD,h4);
		head[4][4][1]=addBPoint(0.5,1.0,0.25,HEAD,h4);
		head[0][4][2]=addBPoint(-0.5,1.0,0.5,HEAD,h4);
		head[1][4][2]=addBPoint(-0.25,1.0,0.5,HEAD,h4);
		head[2][4][2]=addBPoint(0.0,1.0,0.5,HEAD,h4);
		head[3][4][2]=addBPoint(0.25,1.0,0.5,HEAD,h4);
		head[4][4][2]=addBPoint(0.5,1.0,0.5,HEAD,h4);
		head[0][4][3]=addBPoint(-0.5,1.0,0.75,HEAD,h4);
		head[1][4][3]=addBPoint(-0.25,1.0,0.75,HEAD,h4);
		head[2][4][3]=addBPoint(0.0,1.0,0.75,HEAD,h4);
		head[3][4][3]=addBPoint(0.25,1.0,0.75,HEAD,h4);
		head[4][4][3]=addBPoint(0.5,1.0,0.75,HEAD,h4);
		head[0][4][4]=addBPoint(-0.5,1.0,1.0,HEAD,h4);
		head[1][4][4]=addBPoint(-0.25,1.0,1.0,HEAD,h4);
		head[2][4][4]=addBPoint(0.0,1.0,1.0,HEAD,h4);
		head[3][4][4]=addBPoint(0.25,1.0,1.0,HEAD,h4);
		head[4][4][4]=addBPoint(0.5,1.0,1.0,HEAD,h4);

		for (int i = 0; i < hnx-1; i++) {			


			for (int j = 0; j < hny-1; j++) {

				for (int k = 0; k < hnz-1; k++) {				

					if(j==0){


						addLine(head[i][j][k],head[i+1][j][k],head[i+1][j][k+1],head[i][j][k+1],Renderer3D.CAR_BACK);
					}





					if(k+1==hnz-1)
						addLine(head[i][j][k+1],head[i+1][j][k+1],head[i+1][j+1][k+1],head[i][j+1][k+1],Renderer3D.CAR_TOP);

					if(k==0)
						addLine(head[i][j][k],head[i][j+1][k],head[i+1][j+1][k],head[i+1][j][k],Renderer3D.CAR_BOTTOM);

					if(i==0)
						addLine(head[i][j][k],head[i][j][k+1],head[i][j+1][k+1],head[i][j+1][k],Renderer3D.CAR_LEFT);

					if(i+1==hnx-1)
						addLine(head[i+1][j][k],head[i+1][j+1][k],head[i+1][j+1][k+1],head[i+1][j][k+1],Renderer3D.CAR_RIGHT);


					if(j+1==hny-1){

						addLine(head[i][j+1][k],head[i][j+1][k+1],head[i+1][j+1][k+1],head[i+1][j+1][k],Renderer3D.CAR_FRONT);

					}

				}

			}


		}

		
		////
		
		return head;
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

		palm[0][0][0]=addBPoint(x[0],0,0,p0);
		palm[1][0][0]=addBPoint(x[1],0.0,0.4,p0);
		palm[2][0][0]=addBPoint(x[2],0.0,0.4,p0);
		palm[3][0][0]=addBPoint(x[3],0.0,0.6,p0);
		palm[4][0][0]=addBPoint(x[4],0.0,0.6,p0);
		palm[5][0][0]=addBPoint(x[5],0.0,0.4,p0);
		palm[6][0][0]=addBPoint(x[6],0.0,0.4,p0);
		palm[7][0][0]=addBPoint(x[7],0.0,0,p0);
		palm[8][0][0]=addBPoint(x[8],0.0,0,p0);
		palm[0][1][0]=addBPoint(x[0],0.25,0,p0);
		palm[1][1][0]=addBPoint(x[1],0.25,0.4,p0);
		palm[2][1][0]=addBPoint(x[2],0.25,0.4,p0);
		palm[3][1][0]=addBPoint(x[3],0.25,0.6,p0);
		palm[4][1][0]=addBPoint(x[4],0.25,0.6,p0);
		palm[5][1][0]=addBPoint(x[5],0.25,0.4,p0);
		palm[6][1][0]=addBPoint(x[6],0.25,0.4,p0);
		palm[7][1][0]=addBPoint(x[7],0.25,0,p0);
		palm[8][1][0]=addBPoint(x[8],0.25,0,p0);
		palm[0][2][0]=addBPoint(x[0],0.5,0,p0);
		palm[1][2][0]=addBPoint(x[1],0.5,0.4,p0);
		palm[2][2][0]=addBPoint(x[2],0.5,0.4,p0);
		palm[3][2][0]=addBPoint(x[3],0.5,0.6,p0);
		palm[4][2][0]=addBPoint(x[4],0.5,0.6,p0);
		palm[5][2][0]=addBPoint(x[5],0.5,0.4,p0);
		palm[6][2][0]=addBPoint(x[6],0.5,0.4,p0);
		palm[7][2][0]=addBPoint(x[7],0.5,0,p0);
		palm[8][2][0]=addBPoint(x[8],0.5,0,p0);
		palm[0][3][0]=addBPoint(x[0],0.75,0.5,p0);
		palm[1][3][0]=addBPoint(x[1],0.75,0.9,p0);
		palm[2][3][0]=addBPoint(x[2],0.75,0.9,p0);
		palm[3][3][0]=addBPoint(x[3],0.75,1.1,p0);
		palm[4][3][0]=addBPoint(x[4],0.75,1.1,p0);
		palm[5][3][0]=addBPoint(x[5],0.75,0.9,p0);
		palm[6][3][0]=addBPoint(x[6],0.75,0.9,p0);
		palm[7][3][0]=addBPoint(x[7],0.75,0.5,p0);
		palm[8][3][0]=addBPoint(x[8],0.75,0.5,p0);
		palm[0][4][0]=addBPoint(x[0],1.0,1.0,p0);
		palm[1][4][0]=addBPoint(x[1],1.0,1.4,p0);
		palm[2][4][0]=addBPoint(x[2],1.0,1.4,p0);
		palm[3][4][0]=addBPoint(x[3],1.0,1.6,p0);
		palm[4][4][0]=addBPoint(x[4],1.0,1.6,p0);
		palm[5][4][0]=addBPoint(x[5],1.0,1.4,p0);
		palm[6][4][0]=addBPoint(x[6],1.0,1.4,p0);
		palm[7][4][0]=addBPoint(x[7],1.0,1.0,p0);
		palm[8][4][0]=addBPoint(x[8],1.0,1.0,p0);
		
		palm[0][0][1]=addBPoint(x[0],0,1.0,p0);
		palm[1][0][1]=addBPoint(x[1],0.0,1.4,p0);
		palm[2][0][1]=addBPoint(x[2],0.0,1.4,p0);
		palm[3][0][1]=addBPoint(x[3],0.0,1.6,p0);
		palm[4][0][1]=addBPoint(x[4],0.0,1.6,p0);
		palm[5][0][1]=addBPoint(x[5],0.0,1.4,p0);
		palm[6][0][1]=addBPoint(x[6],0.0,1.4,p0);
		palm[7][0][1]=addBPoint(x[7],0.0,1.0,p0);
		palm[8][0][1]=addBPoint(x[8],0.0,1.0,p0);
		palm[0][1][1]=addBPoint(x[0],0.25,1.0,p0);
		palm[1][1][1]=addBPoint(x[1],0.25,1.4,p0);
		palm[2][1][1]=addBPoint(x[2],0.25,1.4,p0);
		palm[3][1][1]=addBPoint(x[3],0.25,1.6,p0);
		palm[4][1][1]=addBPoint(x[4],0.25,1.6,p0);
		palm[5][1][1]=addBPoint(x[5],0.25,1.4,p0);
		palm[6][1][1]=addBPoint(x[6],0.25,1.4,p0);
		palm[7][1][1]=addBPoint(x[7],0.25,1.0,p0);
		palm[8][1][1]=addBPoint(x[8],0.25,1.0,p0);
		palm[0][2][1]=addBPoint(x[0],0.5,1.0,p0);
		palm[1][2][1]=addBPoint(x[1],0.5,1.4,p0);
		palm[2][2][1]=addBPoint(x[2],0.5,1.4,p0);
		palm[3][2][1]=addBPoint(x[3],0.5,1.6,p0);
		palm[4][2][1]=addBPoint(x[4],0.5,1.6,p0);
		palm[5][2][1]=addBPoint(x[5],0.5,1.4,p0);
		palm[6][2][1]=addBPoint(x[6],0.5,1.4,p0);
		palm[7][2][1]=addBPoint(x[7],0.5,1.0,p0);
		palm[8][2][1]=addBPoint(x[8],0.5,1.0,p0);
		palm[0][3][1]=addBPoint(x[0],0.75,1.5,p0);
		palm[1][3][1]=addBPoint(x[1],0.75,1.9,p0);
		palm[2][3][1]=addBPoint(x[2],0.75,1.9,p0);
		palm[3][3][1]=addBPoint(x[3],0.75,2.1,p0);
		palm[4][3][1]=addBPoint(x[4],0.75,2.1,p0);
		palm[5][3][1]=addBPoint(x[5],0.75,1.9,p0);
		palm[6][3][1]=addBPoint(x[6],0.75,1.9,p0);
		palm[7][3][1]=addBPoint(x[7],0.75,1.5,p0);
		palm[8][3][1]=addBPoint(x[8],0.75,1.5,p0);
		palm[0][4][1]=addBPoint(x[0],1.0,2.0,p0);
		palm[1][4][1]=addBPoint(x[1],1.0,2.4,p0);
		palm[2][4][1]=addBPoint(x[2],1.0,2.4,p0);
		palm[3][4][1]=addBPoint(x[3],1.0,2.6,p0);
		palm[4][4][1]=addBPoint(x[4],1.0,2.6,p0);
		palm[5][4][1]=addBPoint(x[5],1.0,2.4,p0);
		palm[6][4][1]=addBPoint(x[6],1.0,2.4,p0);
		palm[7][4][1]=addBPoint(x[7],1.0,2.0,p0);
		palm[8][4][1]=addBPoint(x[8],1.0,2.0,p0);

		
		
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
		
		foreFinger[0][1][0]=addBPoint(-0.5,0.5,0.4,ff0);
		foreFinger[1][1][0]=addBPoint(0.5,0.5,0.4,ff0);
		foreFinger[0][1][1]=addBPoint(-0.5,0.5,1.4,ff0);
		foreFinger[1][1][1]=addBPoint(0.5,0.5,1.4,ff0);
		
		foreFinger[0][2][0]=addBPoint(-0.5,0.75,0.4,ff0);
		foreFinger[1][2][0]=addBPoint(0.5,0.75,0.4,ff0);
		foreFinger[0][2][1]=addBPoint(-0.5,0.75,1.4,ff0);
		foreFinger[1][2][1]=addBPoint(0.5,0.75,1.4,ff0);
		
		foreFinger[0][3][0]=addBPoint(-0.4,1.0,0.4,ff0);
		foreFinger[1][3][0]=addBPoint(0.4,1.0,0.4,ff0);
		foreFinger[0][3][1]=addBPoint(-0.4,1.0,1.4,ff0);
		foreFinger[1][3][1]=addBPoint(0.4,1.0,1.4,ff0);
		
		
		
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
		
		middleFinger[0][1][0]=addBPoint(-0.5,0.5,0.6,mf0);
		middleFinger[1][1][0]=addBPoint(0.5,0.5,0.6,mf0);
		middleFinger[0][1][1]=addBPoint(-0.5,0.5,1.6,mf0);
		middleFinger[1][1][1]=addBPoint(0.5,0.5,1.6,mf0);
		
		middleFinger[0][2][0]=addBPoint(-0.5,0.75,0.6,mf0);
		middleFinger[1][2][0]=addBPoint(0.5,0.75,0.6,mf0);
		middleFinger[0][2][1]=addBPoint(-0.5,0.75,1.6,mf0);
		middleFinger[1][2][1]=addBPoint(0.5,0.75,1.6,mf0);
		
		middleFinger[0][3][0]=addBPoint(-0.4,1.0,0.6,mf0);
		middleFinger[1][3][0]=addBPoint(0.4,1.0,0.6,mf0);
		middleFinger[0][3][1]=addBPoint(-0.4,1.0,1.6,mf0);
		middleFinger[1][3][1]=addBPoint(0.4,1.0,1.6,mf0);
		
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
		
		ringFinger[0][1][0]=addBPoint(-0.5,0.5,0.4,rf0);
		ringFinger[1][1][0]=addBPoint(0.5,0.5,0.4,rf0);
		ringFinger[0][1][1]=addBPoint(-0.5,0.5,1.4,rf0);
		ringFinger[1][1][1]=addBPoint(0.5,0.5,1.4,rf0);

		ringFinger[0][2][0]=addBPoint(-0.5,0.75,0.4,rf0);
		ringFinger[1][2][0]=addBPoint(0.5,0.75,0.4,rf0);
		ringFinger[0][2][1]=addBPoint(-0.5,0.75,1.4,rf0);
		ringFinger[1][2][1]=addBPoint(0.5,0.75,1.4,rf0);
		
		ringFinger[0][3][0]=addBPoint(-0.4,1.0,0.4,rf0);
		ringFinger[1][3][0]=addBPoint(0.4,1.0,0.4,rf0);
		ringFinger[0][3][1]=addBPoint(-0.4,1.0,1.4,rf0);
		ringFinger[1][3][1]=addBPoint(0.4,1.0,1.4,rf0);
		
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
		
		littleFinger[0][1][0]=addBPoint(-0.5,0.5,0,lf0);
		littleFinger[1][1][0]=addBPoint(0.5,0.5,0,lf0);
		littleFinger[0][1][1]=addBPoint(-0.5,0.5,1.0,lf0);
		littleFinger[1][1][1]=addBPoint(0.5,0.5,1.0,lf0);
		
		littleFinger[0][2][0]=addBPoint(-0.5,0.75,0,lf0);
		littleFinger[1][2][0]=addBPoint(0.5,0.75,0,lf0);
		littleFinger[0][2][1]=addBPoint(-0.5,0.75,1.0,lf0);
		littleFinger[1][2][1]=addBPoint(0.5,0.75,1.0,lf0);
		
		littleFinger[0][3][0]=addBPoint(-0.4,1.0,0,lf0);
		littleFinger[1][3][0]=addBPoint(0.4,1.0,0,lf0);
		littleFinger[0][3][1]=addBPoint(-0.4,1.0,1.0,lf0);
		littleFinger[1][3][1]=addBPoint(0.4,1.0,1.0,lf0);
		
		
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
		
		
		thumb[0][0][0]=addBPoint(-0.5,thumb_y0,0,tf0);		
		thumb[1][0][0]=addBPoint(0.0,thumb_y0,0.0,tf0);
		thumb[0][0][1]=addBPoint(-0.5,thumb_y0,1.0,tf0);
		thumb[1][0][1]=addBPoint(0.0,thumb_y0,1.0,tf0);
		
		thumb[0][1][0]=addBPoint(-0.5,0.0,0,tf0);		
		thumb[1][1][0]=addBPoint(0.0,0.0,0.0,tf0);
		thumb[0][1][1]=addBPoint(-0.5,0.0,1.0,tf0);
		thumb[1][1][1]=addBPoint(0.0,0.0,1.0,tf0);
		
		thumb[0][2][0]=addBPoint(-1.0,df1,0,tf0);		
		thumb[1][2][0]=addBPoint(-0.5,df1,0.0,tf0);
		thumb[0][2][1]=addBPoint(-1.0,df1,1.0,tf0);
		thumb[1][2][1]=addBPoint(-0.5,df1,1.0,tf0);
		
		thumb[0][3][0]=addBPoint(-1.0,0.75,0,tf0);		
		thumb[1][3][0]=addBPoint(-0.5,0.75,0.0,tf0);
		thumb[0][3][1]=addBPoint(-1.0,0.75,1.0,tf0);
		thumb[1][3][1]=addBPoint(-0.5,0.75,1.0,tf0);
		
		thumb[0][4][0]=addBPoint(-1.0,1.0,0,tf0);		
		thumb[1][4][0]=addBPoint(-0.5,1.0,0.0,tf0);
		thumb[0][4][1]=addBPoint(-1.0,1.0,1.0,tf0);
		thumb[1][4][1]=addBPoint(-0.5,1.0,1.0,tf0);

		
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