package com.editors.animals.data;

import java.util.Vector;

import com.BPoint;
import com.CustomData;
import com.LineData;
import com.PolygonMesh;
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
	
	double leg_side=0;
	
	double femur_length=0; 
	double shinbone_length=0;
	
	double foot_length=0;
	
	public static int ANIMAL_TYPE_QUADRUPED=0;
	public static int ANIMAL_TYPE_HUMAN=1;
	
	public int animal_type=ANIMAL_TYPE_HUMAN;


	public Animal(){}

	public Animal(double x_side, double y_side,double z_side,int animal_type,
			double femur_length,double shinbone_length,double leg_side,
			double head_DZ,double head_DX,double head_DY,double neck_length,double neck_side,
			double humerus_length,double radius_length,double foot_length
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
	}

	public Object clone(){

		Animal animal=new Animal(
				x_side,y_side,z_side,animal_type,femur_length,shinbone_length,leg_side,
				head_DZ,head_DX,head_DY,neck_length,neck_side,humerus_length,radius_length,
				foot_length
				);
		return animal;

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
				+humerus_length+","+radius_length+","+foot_length;
				
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
		
		double footLength=Double.parseDouble(vals[14]);

		Animal grid=new Animal(x_side,y_side,z_side,animal_type,
				femurLength,shinboneLength,legSide,
				headDZ,headDX,headDY,neckLength,neckSide,
				humerusLength,radiusLength,footLength);

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

	public PolygonMesh buildMesh(){
		
		
		if(ANIMAL_TYPE_HUMAN==animal_type){	
			
			return buildHumanMesh();
			
			
			
		}
		else if(ANIMAL_TYPE_QUADRUPED==animal_type){			
			
			return buildQuadrupedMesh();
			
		}
		else
			return null;

		


	}

	private PolygonMesh buildHumanMesh() {
		
		double q_angle=12*2*Math.PI/360.0;

		points=new Vector();
		points.setSize(200);

		polyData=new Vector();

		n=0;

		double xc=x_side/2.0;
		double yc=y_side/2.0;

		//body:
		
		double bz0=femur_length+shinbone_length;
		double bz1=femur_length+shinbone_length+z_side/4.0;
		double bz2=femur_length+shinbone_length+z_side/2.0;
		double bz3=femur_length+shinbone_length+z_side*3.0/4.0;
		double bz4=femur_length+shinbone_length+z_side;
		
		double SHOULDER_DX=10;		
		double WAIST_DX=x_side*0.1;
		
		int numx=3;
		int numy=2;
		int numz=7;
		
		BPoint[][][] body=new BPoint[numx][numy][numz]; 

		body[0][0][0]=addBPoint(xc-x_side*0.5,yc-y_side*0.5,bz0);
		body[1][0][0]=addBPoint(xc,yc-y_side*0.5,bz0);
		body[2][0][0]=addBPoint(xc+x_side*0.5,yc-y_side*0.5,bz0);
		body[0][1][0]=addBPoint(xc-x_side*0.5,yc+y_side*0.5,bz0);
		body[1][1][0]=addBPoint(xc,yc+y_side*0.5,bz0);
		body[2][1][0]=addBPoint(xc+x_side*0.5,yc+y_side*0.5,bz0);		
		
		body[0][0][1]=addBPoint(xc-x_side*0.5,yc-y_side*0.5,bz1);
		body[1][0][1]=addBPoint(xc,yc-y_side*0.5,bz1);
		body[2][0][1]=addBPoint(xc+x_side*0.5,yc-y_side*0.5,bz1);		
		body[0][1][1]=addBPoint(xc-x_side*0.5,yc+y_side*0.5,bz1);
		body[1][1][1]=addBPoint(xc,yc+y_side*0.5,bz1);
		body[2][1][1]=addBPoint(xc+x_side*0.5,yc+y_side*0.5,bz1);
		
		body[0][0][2]=addBPoint(xc-(x_side*0.5-WAIST_DX),yc-y_side*0.5,bz2);
		body[1][0][2]=addBPoint(xc,yc-y_side*0.5,bz2);
		body[2][0][2]=addBPoint(xc+(x_side*0.5-WAIST_DX),yc-y_side*0.5,bz2);		
		body[0][1][2]=addBPoint(xc-(x_side*0.5-WAIST_DX),yc+y_side*0.5,bz2);
		body[1][1][2]=addBPoint(xc,yc+y_side*0.5,bz2);
		body[2][1][2]=addBPoint(xc+(x_side*0.5-WAIST_DX),yc+y_side*0.5,bz2);
		
		body[0][0][3]=addBPoint(xc-(x_side*0.5+SHOULDER_DX),yc-y_side*0.5,bz3);
		body[1][0][3]=addBPoint(xc,yc-y_side*0.5,bz3);
		body[2][0][3]=addBPoint(xc+(x_side*0.5+SHOULDER_DX),yc-y_side*0.5,bz3);
		body[0][1][3]=addBPoint(xc-(x_side*0.5+SHOULDER_DX),yc+y_side*0.5,bz3);
		body[1][1][3]=addBPoint(xc,yc+y_side*0.5,bz3);
		body[2][1][3]=addBPoint(xc+(x_side*0.5+SHOULDER_DX),yc+y_side*0.5,bz3);		
		
		body[0][0][4]=addBPoint(xc-(x_side*0.5+SHOULDER_DX),yc-y_side*0.5,bz4);
		body[1][0][4]=addBPoint(xc,yc-y_side*0.5,bz4);
		body[2][0][4]=addBPoint(xc+(x_side*0.5+SHOULDER_DX),yc-y_side*0.5,bz4);		
		body[0][1][4]=addBPoint(xc-(x_side*0.5+SHOULDER_DX),yc+y_side*0.5,bz4);
		body[1][1][4]=addBPoint(xc,yc+y_side*0.5,bz4);
		body[2][1][4]=addBPoint(xc+(x_side*0.5+SHOULDER_DX),yc+y_side*0.5,bz4);
		
		double nz0=femur_length+shinbone_length+z_side+neck_length/2.0;
		double nz1=femur_length+shinbone_length+z_side+neck_length;		

		body[0][0][5]=addBPoint(xc-neck_side*0.5,yc-y_side*0.5,nz0);
		body[1][0][5]=addBPoint(xc,yc-y_side*0.5,nz0);
		body[2][0][5]=addBPoint(xc+neck_side*0.5,yc-y_side*0.5,nz0);		
		body[0][1][5]=addBPoint(xc-neck_side*0.5,yc+y_side*0.5,nz0);
		body[1][1][5]=addBPoint(xc,yc+y_side*0.5,nz0);
		body[2][1][5]=addBPoint(xc+neck_side*0.5,yc+y_side*0.5,nz0);

		body[0][0][6]=addBPoint(xc-neck_side*0.5,yc-y_side*0.5,nz1);
		body[1][0][6]=addBPoint(xc,yc-y_side*0.5,nz1);
		body[2][0][6]=addBPoint(xc+neck_side*0.5,yc-y_side*0.5,nz1);
		body[0][1][6]=addBPoint(xc-neck_side*0.5,yc+y_side*0.5,nz1);		
		body[1][1][6]=addBPoint(xc,yc+y_side*0.5,nz1);
		body[2][1][6]=addBPoint(xc+neck_side*0.5,yc+y_side*0.5,nz1);

		for(int k=0;k<numz-1;k++){
			
			if(k==0)
				addLine(body[0][0][k],body[0][1][k],body[1][1][k],body[1][0][k],Renderer3D.CAR_BOTTOM);			
		
			addLine(body[0][0][k],body[0][0][k+1],body[0][1][k+1],body[0][1][k],Renderer3D.CAR_LEFT);
	
			addLine(body[numx-1][0][k],body[numx-1][1][k],body[numx-1][1][k+1],body[numx-1][0][k+1],Renderer3D.CAR_RIGHT);
			
			for(int i=0;i<numx-1;i++){
	
				addLine(body[i][0][k],body[i+1][0][k],body[i+1][0][k+1],body[i][0][k+1],Renderer3D.CAR_BACK);
		
				addLine(body[i][1][k],body[i][1][k+1],body[i+1][1][k+1],body[i+1][1][k],Renderer3D.CAR_FRONT);
			
			}
		
		}

		///////////
		//head:
		
		BPoint[][][] head=buildHumanHeadMesh();


		//legs:	
		
		double thigh_indentation=femur_length*Math.sin(q_angle);
		double thigh_side=leg_side+thigh_indentation;
		double LEG_DY=(y_side-leg_side)/2.0;

		//LeftLeg

		BPoint pBackLeftLeg000=addBPoint(thigh_indentation,LEG_DY,0);
		BPoint pBackLeftLeg100=addBPoint(thigh_indentation+leg_side,LEG_DY,0);
		BPoint pBackLeftLeg110=addBPoint(thigh_indentation+leg_side,leg_side+LEG_DY,0);
		BPoint pBackLeftLeg010=addBPoint(thigh_indentation,leg_side+LEG_DY,0);

		addLine(pBackLeftLeg000,pBackLeftLeg010,pBackLeftLeg110,pBackLeftLeg100,Renderer3D.CAR_FRONT);


		BPoint pBackLeftLeg001=addBPoint(thigh_indentation,LEG_DY,shinbone_length);
		BPoint pBackLeftLeg101=addBPoint(thigh_indentation+leg_side,LEG_DY,shinbone_length);
		BPoint pBackLeftLeg111=addBPoint(thigh_indentation+leg_side,LEG_DY+leg_side,shinbone_length);
		BPoint pBackLeftLeg011=addBPoint(thigh_indentation,LEG_DY+leg_side,shinbone_length);


		addLine(pBackLeftLeg000,pBackLeftLeg001,pBackLeftLeg011,pBackLeftLeg010,Renderer3D.CAR_LEFT);

		addLine(pBackLeftLeg010,pBackLeftLeg011,pBackLeftLeg111,pBackLeftLeg110,Renderer3D.CAR_FRONT);

		addLine(pBackLeftLeg110,pBackLeftLeg111,pBackLeftLeg101,pBackLeftLeg100,Renderer3D.CAR_RIGHT);

		addLine(pBackLeftLeg100,pBackLeftLeg101,pBackLeftLeg001,pBackLeftLeg000,Renderer3D.CAR_BACK);
		
		//left thigh

		BPoint pBackLeftThigh001=addBPoint(0,LEG_DY,femur_length+shinbone_length);
		BPoint pBackLeftThigh101=addBPoint(thigh_side,LEG_DY,femur_length+shinbone_length);
		BPoint pBackLeftThigh111=addBPoint(thigh_side,LEG_DY+leg_side,femur_length+shinbone_length);
		BPoint pBackLeftThigh011=addBPoint(0,LEG_DY+leg_side,femur_length+shinbone_length);

		addLine(pBackLeftLeg001,pBackLeftThigh001,pBackLeftThigh011,pBackLeftLeg011,Renderer3D.CAR_LEFT);

		addLine(pBackLeftLeg011,pBackLeftThigh011,pBackLeftThigh111,pBackLeftLeg111,Renderer3D.CAR_FRONT);

		addLine(pBackLeftLeg111,pBackLeftThigh111,pBackLeftThigh101,pBackLeftLeg101,Renderer3D.CAR_RIGHT);

		addLine(pBackLeftLeg101,pBackLeftThigh101,pBackLeftThigh001,pBackLeftLeg001,Renderer3D.CAR_BACK);


		///RightLeg

		BPoint pBackRightLeg000=addBPoint(x_side-leg_side-thigh_indentation,LEG_DY,0);
		BPoint pBackRightLeg100=addBPoint(x_side-thigh_indentation,LEG_DY,0);
		BPoint pBackRightLeg110=addBPoint(x_side-thigh_indentation,LEG_DY+leg_side,0);
		BPoint pBackRightLeg010=addBPoint(x_side-leg_side-thigh_indentation,LEG_DY+leg_side,0);

		addLine(pBackRightLeg000,pBackRightLeg010,pBackRightLeg110,pBackRightLeg100,Renderer3D.CAR_FRONT);

		BPoint pBackRightLeg001=addBPoint(x_side-leg_side-thigh_indentation,LEG_DY,shinbone_length);
		BPoint pBackRightLeg101=addBPoint(x_side-thigh_indentation,LEG_DY,shinbone_length);
		BPoint pBackRightLeg111=addBPoint(x_side-thigh_indentation,LEG_DY+leg_side,shinbone_length);
		BPoint pBackRightLeg011=addBPoint(x_side-leg_side-thigh_indentation,LEG_DY+leg_side,shinbone_length);

		addLine(pBackRightLeg000,pBackRightLeg001,pBackRightLeg011,pBackRightLeg010,Renderer3D.CAR_LEFT);
	
		addLine(pBackRightLeg010,pBackRightLeg011,pBackRightLeg111,pBackRightLeg110,Renderer3D.CAR_FRONT);
	
		addLine(pBackRightLeg110,pBackRightLeg111,pBackRightLeg101,pBackRightLeg100,Renderer3D.CAR_RIGHT);
		
		addLine(pBackRightLeg100,pBackRightLeg101,pBackRightLeg001,pBackRightLeg000,Renderer3D.CAR_BACK);
	
		
		//right thigh
		
		BPoint pBackRightThigh001=addBPoint(x_side-thigh_side,LEG_DY,femur_length+shinbone_length);
		BPoint pBackRightThigh101=addBPoint(x_side,LEG_DY,femur_length+shinbone_length);
		BPoint pBackRightThigh111=addBPoint(x_side,LEG_DY+leg_side,femur_length+shinbone_length);
		BPoint pBackRightThigh011=addBPoint(x_side-thigh_side,LEG_DY+leg_side,femur_length+shinbone_length);

		addLine(pBackRightLeg001,pBackRightThigh001,pBackRightThigh011,pBackRightLeg011,Renderer3D.CAR_LEFT);
	
		addLine(pBackRightLeg011,pBackRightThigh011,pBackRightThigh111,pBackRightLeg111,Renderer3D.CAR_FRONT);
	
		addLine(pBackRightLeg111,pBackRightThigh111,pBackRightThigh101,pBackRightLeg101,Renderer3D.CAR_RIGHT);
	
		addLine(pBackRightLeg101,pBackRightThigh101,pBackRightThigh001,pBackRightLeg001,Renderer3D.CAR_BACK);
	
		
		//Arms:
		
		//Left fore arm
		
		double ax=SHOULDER_DX+leg_side;
		double az=femur_length+shinbone_length+z_side-humerus_length-radius_length;
		double ay=(y_side-leg_side)/2.0;
		
		BPoint pFrontLeftForearm000=addBPoint(-ax,ay,az);
		BPoint pFrontLeftForearm100=addBPoint(-ax+leg_side,ay,az);
		BPoint pFrontLeftForearm110=addBPoint(-ax+leg_side,ay+leg_side,az);
		BPoint pFrontLeftForearm010=addBPoint(-ax,ay+leg_side,az);

		
		addLine(pFrontLeftForearm000,pFrontLeftForearm010,pFrontLeftForearm110,pFrontLeftForearm100,Renderer3D.CAR_BOTTOM);

		
		BPoint pFrontLeftForearm001=addBPoint(-ax,ay,az+radius_length);
		BPoint pFrontLeftForearm101=addBPoint(-ax+leg_side,ay,az+radius_length);
		BPoint pFrontLeftForearm111=addBPoint(-ax+leg_side,ay+leg_side,az+radius_length);
		BPoint pFrontLeftForearm011=addBPoint(-ax,ay+leg_side,az+radius_length);
		
		//LineData topLeftForearm=addLine(pFrontLeftForearm001,pFrontLeftForearm101,pFrontLeftForearm111,pFrontLeftForearm011,Renderer3D.CAR_TOP);
		//polyData.add(topLeftForearm);
		
		addLine(pFrontLeftForearm000,pFrontLeftForearm001,pFrontLeftForearm011,pFrontLeftForearm010,Renderer3D.CAR_LEFT);

		addLine(pFrontLeftForearm010,pFrontLeftForearm011,pFrontLeftForearm111,pFrontLeftForearm110,Renderer3D.CAR_FRONT);

		addLine(pFrontLeftForearm110,pFrontLeftForearm111,pFrontLeftForearm101,pFrontLeftForearm100,Renderer3D.CAR_RIGHT);

		addLine(pFrontLeftForearm100,pFrontLeftForearm101,pFrontLeftForearm001,pFrontLeftForearm000,Renderer3D.CAR_BACK);
		
		
		//left arm
		
		BPoint pFrontLeftArm001=addBPoint(-ax,ay,az+humerus_length+radius_length);
		BPoint pFrontLeftArm101=addBPoint(-ax+leg_side,ay,az+humerus_length+radius_length);
		BPoint pFrontLeftArm111=addBPoint(-ax+leg_side,ay+leg_side,az+humerus_length+radius_length);
		BPoint pFrontLeftArm011=addBPoint(-ax,ay+leg_side,az+humerus_length+radius_length);
		
		addLine(pFrontLeftArm001,pFrontLeftArm101,pFrontLeftArm111,pFrontLeftArm011,Renderer3D.CAR_TOP);
		
		addLine(pFrontLeftForearm001,pFrontLeftArm001,pFrontLeftArm011,pFrontLeftForearm011,Renderer3D.CAR_LEFT);

		addLine(pFrontLeftForearm011,pFrontLeftArm011,pFrontLeftArm111,pFrontLeftForearm111,Renderer3D.CAR_FRONT);

		addLine(pFrontLeftForearm111,pFrontLeftArm111,pFrontLeftArm101,pFrontLeftForearm101,Renderer3D.CAR_RIGHT);

		addLine(pFrontLeftForearm101,pFrontLeftArm101,pFrontLeftArm001,pFrontLeftForearm001,Renderer3D.CAR_BACK);
		
		
		//Right forearm
		
		BPoint pFrontRightForearm000=addBPoint(ax+x_side-leg_side,ay,az);
		BPoint pFrontRightForearm100=addBPoint(ax+x_side,ay,az);
		BPoint pFrontRightForearm110=addBPoint(ax+x_side,ay+leg_side,az);
		BPoint pFrontRightForearm010=addBPoint(ax+x_side-leg_side,ay+leg_side,az);

		addLine(pFrontRightForearm000,pFrontRightForearm010,pFrontRightForearm110,pFrontRightForearm100,Renderer3D.CAR_BOTTOM);

		
		BPoint pFrontRightForearm001=addBPoint(ax+x_side-leg_side,ay,az+radius_length);
		BPoint pFrontRightForearm101=addBPoint(ax+x_side,ay,az+radius_length);
		BPoint pFrontRightForearm111=addBPoint(ax+x_side,ay+leg_side,az+radius_length);
		BPoint pFrontRightForearm011=addBPoint(ax+x_side-leg_side,ay+leg_side,az+radius_length);
		
		//LineData topRightForearm=addLine(pFrontRightForearm001,pFrontRightForearm101,pFrontRightForearm111,pFrontRightForearm011,Renderer3D.CAR_TOP);
		//polyData.add(topRightForearm);
		
		addLine(pFrontRightForearm000,pFrontRightForearm001,pFrontRightForearm011,pFrontRightForearm010,Renderer3D.CAR_LEFT);

		addLine(pFrontRightForearm010,pFrontRightForearm011,pFrontRightForearm111,pFrontRightForearm110,Renderer3D.CAR_FRONT);
		
		addLine(pFrontRightForearm110,pFrontRightForearm111,pFrontRightForearm101,pFrontRightForearm100,Renderer3D.CAR_RIGHT);
	
		addLine(pFrontRightForearm100,pFrontRightForearm101,pFrontRightForearm001,pFrontRightForearm000,Renderer3D.CAR_BACK);

		
		//right arm
		
		BPoint pFrontRightArm001=addBPoint(ax+x_side-leg_side,ay,az+radius_length+humerus_length);
		BPoint pFrontRightArm101=addBPoint(ax+x_side,ay,az+radius_length+humerus_length);
		BPoint pFrontRightArm111=addBPoint(ax+x_side,ay+leg_side,az+radius_length+humerus_length);
		BPoint pFrontRightArm011=addBPoint(ax+x_side-leg_side,ay+leg_side,az+radius_length+humerus_length);
		
		addLine(pFrontRightArm001,pFrontRightArm101,pFrontRightArm111,pFrontRightArm011,Renderer3D.CAR_TOP);
			
		addLine(pFrontRightForearm001,pFrontRightArm001,pFrontRightArm011,pFrontRightForearm011,Renderer3D.CAR_LEFT);

		addLine(pFrontRightForearm011,pFrontRightArm011,pFrontRightArm111,pFrontRightForearm111,Renderer3D.CAR_FRONT);
	
		addLine(pFrontRightForearm111,pFrontRightArm111,pFrontRightArm101,pFrontRightForearm101,Renderer3D.CAR_RIGHT);
		
		addLine(pFrontRightForearm101,pFrontRightArm101,pFrontRightArm001,pFrontRightForearm001,Renderer3D.CAR_BACK);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private BPoint[][][] buildHumanHeadMesh() {
		
		double xc=x_side/2.0;
		double yc=y_side/2.0;

		double hz0=femur_length+shinbone_length+z_side+neck_length;
		double hz1=femur_length+shinbone_length+z_side+neck_length+head_DZ/4.0;
		double hz2=femur_length+shinbone_length+z_side+neck_length+head_DZ/2.0;
		double hz3=femur_length+shinbone_length+z_side+neck_length+head_DZ*3.0/4.0;
		double hz4=femur_length+shinbone_length+z_side+neck_length+head_DZ;
		
		int numx=3;
		int numy=2;
		int numz=5;
		
		BPoint[][][] head=new BPoint[numx][numy][numz];

		head[0][0][0]=addBPoint(xc-neck_side*0.5,yc-y_side*0.5,hz0);
		head[1][0][0]=addBPoint(xc,yc-y_side*0.5,hz0);
		head[2][0][0]=addBPoint(xc+neck_side*0.5,yc-y_side*0.5,hz0);
		head[0][1][0]=addBPoint(xc-neck_side*0.5,yc+y_side*0.5,hz0);
		head[1][1][0]=addBPoint(xc,yc+y_side*0.5,hz0);
		head[2][1][0]=addBPoint(xc+neck_side*0.5,yc+y_side*0.5,hz0);

		head[0][1][1]=addBPoint(xc-head_DX*0.5*0.85,yc+head_DY*0.5*0.85,hz1);
		head[1][0][1]=addBPoint(xc,yc-head_DY*0.5*0.85,hz1);
		head[2][0][1]=addBPoint(xc+head_DX*0.5*0.85,yc-head_DY*0.5*0.85,hz1);
		head[0][0][1]=addBPoint(xc-head_DX*0.5*0.85,yc-head_DY*0.5*0.85,hz1);
		head[1][1][1]=addBPoint(xc,yc+head_DY*0.5*0.85,hz1);
		head[2][1][1]=addBPoint(xc+head_DX*0.5*0.85,yc+head_DY*0.5*0.85,hz1);
		
		head[0][1][2]=addBPoint(xc-head_DX*0.5,yc+head_DY*0.5,hz2);
		head[1][0][2]=addBPoint(xc,yc-head_DY*0.5,hz2);
		head[2][0][2]=addBPoint(xc+head_DX*0.5,yc-head_DY*0.5,hz2);
		head[0][0][2]=addBPoint(xc-head_DX*0.5,yc-head_DY*0.5,hz2);
		head[1][1][2]=addBPoint(xc,yc+head_DY*0.5,hz2);
		head[2][1][2]=addBPoint(xc+head_DX*0.5,yc+head_DY*0.5,hz2);
		
		head[0][1][3]=addBPoint(xc-head_DX*0.5,yc+head_DY*0.5,hz3);
		head[1][0][3]=addBPoint(xc,yc-head_DY*0.5,hz3);
		head[2][0][3]=addBPoint(xc+head_DX*0.5,yc-head_DY*0.5,hz3);
		head[0][0][3]=addBPoint(xc-head_DX*0.5,yc-head_DY*0.5,hz3);
		head[1][1][3]=addBPoint(xc,yc+head_DY*0.5,hz3);
		head[2][1][3]=addBPoint(xc+head_DX*0.5,yc+head_DY*0.5,hz3);
		
		head[0][1][4]=addBPoint(xc-head_DX*0.5*0.75,yc+head_DY*0.5*0.75,hz4);
		head[1][0][4]=addBPoint(xc,yc-head_DY*0.5*0.75,hz4);
		head[2][0][4]=addBPoint(xc+head_DX*0.5*0.75,yc-head_DY*0.5*0.75,hz4);		
		head[0][0][4]=addBPoint(xc-head_DX*0.5*0.75,yc-head_DY*0.5*0.75,hz4);
		head[1][1][4]=addBPoint(xc,yc+head_DY*0.5*0.75,hz4);
		head[2][1][4]=addBPoint(xc+head_DX*0.5*0.75,yc+head_DY*0.5*0.75,hz4);


		for(int k=0;k<numz-1;k++){			

			addLine(head[0][0][k],head[0][0][k+1],head[0][1][k+1],head[0][1][k],Renderer3D.CAR_LEFT);

			addLine(head[2][0][k],head[2][1][k],head[2][1][k+1],head[2][0][k+1],Renderer3D.CAR_RIGHT);
			
			for(int i=0;i<numx-1;i++){

				addLine(head[i][0][k],head[i+1][0][k],head[i+1][0][k+1],head[i][0][k+1],Renderer3D.CAR_BACK);		
	
				addLine(head[i][1][k],head[i][1][k+1],head[i+1][1][k+1],head[i+1][1][k],Renderer3D.CAR_FRONT);
				
				if(k==numz-2)
					addLine(head[i][0][k+1],head[i+1][0][k+1],head[i+1][1][k+1],head[i][1][k+1],Renderer3D.CAR_TOP);

			}

			
		}
		

		
		return head;
	}

	private PolygonMesh buildQuadrupedMesh() {


		points=new Vector();
		points.setSize(200);

		polyData=new Vector();
		
		n=0;
		


		//main body:
		
		double bz0=femur_length+shinbone_length;
		double bz1=femur_length+shinbone_length+z_side;
		
		double bfz0=radius_length+humerus_length;
		
		double by0=0;
		double by1=y_side/4.0;
		double by2=y_side/2.0;
		double by3=y_side*3.0/4.0;
		double by4=y_side;
		
		int numx=2;
		int numy=5;
		int numz=2;
		
		BPoint[][][] body=new BPoint[numx][numy][numz]; 

		body[0][0][0]=addBPoint(0,by0,bz0);
		body[1][0][0]=addBPoint(x_side,by0,bz0);
		body[0][0][1]=addBPoint(0,by0,bz1);	
		body[1][0][1]=addBPoint(x_side,by0,bz1);
		
		body[0][1][0]=addBPoint(0,by1,bz0);
		body[1][1][0]=addBPoint(x_side,by1,bz0);			
		body[0][1][1]=addBPoint(0,by1,bz1);		
		body[1][1][1]=addBPoint(x_side,by1,bz1);

		body[0][2][0]=addBPoint(0,by2,bz0);
		body[1][2][0]=addBPoint(x_side,by2,bz0);			
		body[0][2][1]=addBPoint(0,by2,bz1);		
		body[1][2][1]=addBPoint(x_side,by2,bz1);		
		
		body[0][3][0]=addBPoint(0,by3,bfz0);
		body[1][3][0]=addBPoint(x_side,by3,bfz0);			
		body[0][3][1]=addBPoint(0,by3,bz1);		
		body[1][3][1]=addBPoint(x_side,by3,bz1);
		
		body[0][4][0]=addBPoint(0,by4,bfz0);
		body[1][4][0]=addBPoint(x_side,by4,bfz0);			
		body[0][4][1]=addBPoint(0,by4,bz1);		
		body[1][4][1]=addBPoint(x_side,by4,bz1);



		addLine(body[0][0][1],body[1][0][1],body[1][1][1],body[0][1][1],Renderer3D.CAR_TOP);
		
		addLine(body[0][0][0],body[0][1][0],body[1][1][0],body[1][0][0],Renderer3D.CAR_BOTTOM);

		addLine(body[0][0][0],body[0][0][1],body[0][1][1],body[0][1][0],Renderer3D.CAR_LEFT);

		addLine(body[1][0][0],body[1][1][0],body[1][1][1],body[1][0][1],Renderer3D.CAR_RIGHT);

		addLine(body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1],Renderer3D.CAR_BACK);

		//addLine(body[0][1][0],body[0][1][1],body[1][1][1],body[1][1][0],Renderer3D.CAR_FRONT);		
		
		
		
		addLine(body[0][1][1],body[1][1][1],body[1][2][1],body[0][2][1],Renderer3D.CAR_TOP);
		
		addLine(body[0][1][0],body[0][2][0],body[1][2][0],body[1][1][0],Renderer3D.CAR_BOTTOM);

		addLine(body[0][1][0],body[0][1][1],body[0][2][1],body[0][2][0],Renderer3D.CAR_LEFT);

		addLine(body[1][1][0],body[1][2][0],body[1][2][1],body[1][1][1],Renderer3D.CAR_RIGHT);

		//addLine(body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1],Renderer3D.CAR_BACK);

		//addLine(body[0][1][0],body[0][1][1],body[1][1][1],body[1][1][0],Renderer3D.CAR_FRONT);	
		
		
		
		addLine(body[0][2][1],body[1][2][1],body[1][3][1],body[0][3][1],Renderer3D.CAR_TOP);
		
		addLine(body[0][2][0],body[0][3][0],body[1][3][0],body[1][2][0],Renderer3D.CAR_BOTTOM);

		addLine(body[0][2][0],body[0][2][1],body[0][3][1],body[0][3][0],Renderer3D.CAR_LEFT);

		addLine(body[1][2][0],body[1][3][0],body[1][3][1],body[1][2][1],Renderer3D.CAR_RIGHT);

		//addLine(body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1],Renderer3D.CAR_BACK);

		//addLine(body[0][1][0],body[0][1][1],body[1][1][1],body[1][1][0],Renderer3D.CAR_FRONT);	
		  
		
		
		//addLine(body[0][3][1],body[1][3][1],body[1][4][1],body[0][4][1],Renderer3D.CAR_TOP);
		
		addLine(body[0][3][0],body[0][4][0],body[1][4][0],body[1][3][0],Renderer3D.CAR_BOTTOM);

		addLine(body[0][3][0],body[0][3][1],body[0][4][1],body[0][4][0],Renderer3D.CAR_LEFT);

		addLine(body[1][3][0],body[1][4][0],body[1][4][1],body[1][3][1],Renderer3D.CAR_RIGHT);

		//addLine(body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1],Renderer3D.CAR_BACK);

		addLine(body[0][4][0],body[0][4][1],body[1][4][1],body[1][4][0],Renderer3D.CAR_FRONT);
		
		/////////head:		
		

		
		BPoint[][][] head=buildQuadrupedHeadMesh();
		
		//neck:
		
		
		double nz0=femur_length+shinbone_length+z_side+neck_length/2.0;
		double nz1=femur_length+shinbone_length+z_side+neck_length;
		double nx=(x_side-neck_side)/2.0;
		double ny=y_side-neck_side;
		
		BPoint[][][] neck=new BPoint[2][2][2];
	
		neck[0][0][0]=addBPoint(nx,ny,nz0);
		neck[1][0][0]=addBPoint(nx+neck_side,ny,nz0);
		neck[0][1][0]=addBPoint(nx,ny+neck_side,nz0);
		neck[1][1][0]=addBPoint(nx+neck_side,ny+neck_side,nz0);

		neck[0][1][1]=addBPoint(nx,ny+neck_side,nz1);
		neck[1][0][1]=addBPoint(nx+neck_side,ny,nz1);
		neck[1][1][1]=addBPoint(nx+neck_side,ny+neck_side,nz1);
		neck[0][0][1]=addBPoint(nx,ny,nz1);
		
	
		addLine(body[0][3][1],neck[0][0][0],neck[0][1][0],body[0][4][1],Renderer3D.CAR_LEFT);

		addLine(body[1][3][1],body[1][4][1],neck[1][1][0],neck[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(body[0][3][1],body[1][3][1],neck[1][0][0],neck[0][0][0],Renderer3D.CAR_BACK);

		addLine(body[0][4][1],neck[0][1][0],neck[1][1][0],body[1][4][1],Renderer3D.CAR_FRONT);
		
		///

		addLine(neck[0][0][1],neck[1][0][1],neck[1][1][1],neck[0][1][1],Renderer3D.CAR_TOP);

		addLine(neck[0][0][0],neck[0][1][0],neck[1][1][0],neck[1][0][0],Renderer3D.CAR_BOTTOM);

		addLine(neck[0][0][0],neck[0][0][1],neck[0][1][1],neck[0][1][0],Renderer3D.CAR_LEFT);

		addLine(neck[1][0][0],neck[1][1][0],neck[1][1][1],neck[1][0][1],Renderer3D.CAR_RIGHT);

		addLine(neck[0][0][0],neck[1][0][0],neck[1][0][1],neck[0][0][1],Renderer3D.CAR_BACK);

		addLine(neck[0][1][0],neck[0][1][1],neck[1][1][1],neck[1][1][0],Renderer3D.CAR_FRONT);




		//legs:	
		
		//backLeftLeg
		
		BPoint pBackLeftLeg000=addBPoint(0,0,0);
		BPoint pBackLeftLeg100=addBPoint(leg_side,0,0);
		BPoint pBackLeftLeg110=addBPoint(leg_side,leg_side,0);
		BPoint pBackLeftLeg010=addBPoint(0,leg_side,0);

		
		LineData backLeftLeg=addLine(pBackLeftLeg000,pBackLeftLeg010,pBackLeftLeg110,pBackLeftLeg100,Renderer3D.CAR_FRONT);
		
		BPoint pBackLeftLeg001=addBPoint(0,0,shinbone_length);
		BPoint pBackLeftLeg101=addBPoint(leg_side,0,shinbone_length);
		BPoint pBackLeftLeg111=addBPoint(leg_side,leg_side,shinbone_length);
		BPoint pBackLeftLeg011=addBPoint(0,leg_side,shinbone_length);
		
		LineData backLeftLegS0=addLine(pBackLeftLeg000,pBackLeftLeg001,pBackLeftLeg011,pBackLeftLeg010,Renderer3D.CAR_LEFT);

		LineData backLeftLegS1=addLine(pBackLeftLeg010,pBackLeftLeg011,pBackLeftLeg111,pBackLeftLeg110,Renderer3D.CAR_FRONT);
	
		LineData backLeftLegS2=addLine(pBackLeftLeg110,pBackLeftLeg111,pBackLeftLeg101,pBackLeftLeg100,Renderer3D.CAR_RIGHT);
	
		LineData backLeftLegS3=addLine(pBackLeftLeg100,pBackLeftLeg101,pBackLeftLeg001,pBackLeftLeg000,Renderer3D.CAR_BACK);
	
		
		//back left thigh
		
		BPoint pBackLeftThigh001=addBPoint(0,0,femur_length+shinbone_length);
		BPoint pBackLeftThigh101=addBPoint(leg_side,0,femur_length+shinbone_length);
		BPoint pBackLeftThigh111=addBPoint(leg_side,leg_side,femur_length+shinbone_length);
		BPoint pBackLeftThigh011=addBPoint(0,leg_side,femur_length+shinbone_length);

		LineData backLeftThighS0=addLine(pBackLeftLeg001,pBackLeftThigh001,pBackLeftThigh011,pBackLeftLeg011,Renderer3D.CAR_LEFT);
	
		LineData backLeftThighS1=addLine(pBackLeftLeg011,pBackLeftThigh011,pBackLeftThigh111,pBackLeftLeg111,Renderer3D.CAR_FRONT);
	
		LineData backLeftThighS2=addLine(pBackLeftLeg111,pBackLeftThigh111,pBackLeftThigh101,pBackLeftLeg101,Renderer3D.CAR_RIGHT);

		LineData backLeftThighS3=addLine(pBackLeftLeg101,pBackLeftThigh101,pBackLeftThigh001,pBackLeftLeg001,Renderer3D.CAR_BACK);

		
		//backRightLeg
		
		BPoint pBackRightLeg000=addBPoint(x_side-leg_side,0,0);
		BPoint pBackRightLeg100=addBPoint(x_side,0,0);
		BPoint pBackRightLeg110=addBPoint(x_side,leg_side,0);
		BPoint pBackRightLeg010=addBPoint(x_side-leg_side,leg_side,0);
		
		LineData backRightLeg=addLine(pBackRightLeg000,pBackRightLeg010,pBackRightLeg110,pBackRightLeg100,Renderer3D.CAR_FRONT);


		
		BPoint pBackRightLeg001=addBPoint(x_side-leg_side,0,shinbone_length);
		BPoint pBackRightLeg101=addBPoint(x_side,0,shinbone_length);
		BPoint pBackRightLeg111=addBPoint(x_side,leg_side,shinbone_length);
		BPoint pBackRightLeg011=addBPoint(x_side-leg_side,leg_side,shinbone_length);

		
		LineData backRightLegS0=addLine(pBackRightLeg000,pBackRightLeg001,pBackRightLeg011,pBackRightLeg010,Renderer3D.CAR_LEFT);
	
		LineData backRightLegS1=addLine(pBackRightLeg010,pBackRightLeg011,pBackRightLeg111,pBackRightLeg110,Renderer3D.CAR_FRONT);
	
		LineData backRightLegS2=addLine(pBackRightLeg110,pBackRightLeg111,pBackRightLeg101,pBackRightLeg100,Renderer3D.CAR_RIGHT);
	
		LineData backRightLegS3=addLine(pBackRightLeg100,pBackRightLeg101,pBackRightLeg001,pBackRightLeg000,Renderer3D.CAR_BACK);
	
		
		//back right thigh
		
		BPoint pBackRightThigh001=addBPoint(x_side-leg_side,0,femur_length+shinbone_length);
		BPoint pBackRightThigh101=addBPoint(x_side,0,femur_length+shinbone_length);
		BPoint pBackRightThigh111=addBPoint(x_side,leg_side,femur_length+shinbone_length);
		BPoint pBackRightThigh011=addBPoint(x_side-leg_side,leg_side,femur_length+shinbone_length);

		LineData backRightThighS0=addLine(pBackRightLeg001,pBackRightThigh001,pBackRightThigh011,pBackRightLeg011,Renderer3D.CAR_LEFT);
	
		LineData backRightThighS1=addLine(pBackRightLeg011,pBackRightThigh011,pBackRightThigh111,pBackRightLeg111,Renderer3D.CAR_FRONT);

		LineData backRightThighS2=addLine(pBackRightLeg111,pBackRightThigh111,pBackRightThigh101,pBackRightLeg101,Renderer3D.CAR_RIGHT);
	
		LineData backRightThighS3=addLine(pBackRightLeg101,pBackRightThigh101,pBackRightThigh001,pBackRightLeg001,Renderer3D.CAR_BACK);

		
		//frontLeftLeg
		
		BPoint pFrontLeftForearm000=addBPoint(0,y_side-leg_side,0);
		BPoint pFrontLeftForearm100=addBPoint(leg_side,y_side-leg_side,0);
		BPoint pFrontLeftForearm110=addBPoint(leg_side,y_side,0);
		BPoint pFrontLeftForearm010=addBPoint(0,y_side,0);
		
		LineData FrontLeftForearm=addLine(pFrontLeftForearm000,pFrontLeftForearm010,pFrontLeftForearm110,pFrontLeftForearm100,Renderer3D.CAR_FRONT);

		
		BPoint pFrontLeftForearm001=addBPoint(0,y_side-leg_side,radius_length);
		BPoint pFrontLeftForearm101=addBPoint(leg_side,y_side-leg_side,radius_length);
		BPoint pFrontLeftForearm111=addBPoint(leg_side,y_side,radius_length);
		BPoint pFrontLeftForearm011=addBPoint(0,y_side,radius_length);
		
		LineData FrontLeftForearmS0=addLine(pFrontLeftForearm000,pFrontLeftForearm001,pFrontLeftForearm011,pFrontLeftForearm010,Renderer3D.CAR_LEFT);
	
		LineData FrontLeftForearmS1=addLine(pFrontLeftForearm010,pFrontLeftForearm011,pFrontLeftForearm111,pFrontLeftForearm110,Renderer3D.CAR_FRONT);

		LineData FrontLeftForearmS2=addLine(pFrontLeftForearm110,pFrontLeftForearm111,pFrontLeftForearm101,pFrontLeftForearm100,Renderer3D.CAR_RIGHT);
	
		LineData FrontLeftForearmS3=addLine(pFrontLeftForearm100,pFrontLeftForearm101,pFrontLeftForearm001,pFrontLeftForearm000,Renderer3D.CAR_BACK);
	
		
		//left arm
		
		BPoint pFrontLeftArm001=addBPoint(0,y_side-leg_side,radius_length+humerus_length);
		BPoint pFrontLeftArm101=addBPoint(leg_side,y_side-leg_side,radius_length+humerus_length);
		BPoint pFrontLeftArm111=addBPoint(leg_side,y_side,radius_length+humerus_length);
		BPoint pFrontLeftArm011=addBPoint(0,y_side,radius_length+humerus_length);
		
		LineData topLeftArm=addLine(pFrontLeftArm001,pFrontLeftArm101,pFrontLeftArm111,pFrontLeftArm011,Renderer3D.CAR_TOP);
	
		
		LineData FrontLeftArmS0=addLine(pFrontLeftForearm000,pFrontLeftArm001,pFrontLeftArm011,pFrontLeftForearm010,Renderer3D.CAR_LEFT);

		LineData FrontLeftArmS1=addLine(pFrontLeftForearm010,pFrontLeftArm011,pFrontLeftArm111,pFrontLeftForearm110,Renderer3D.CAR_FRONT);
	
		LineData FrontLeftArmS2=addLine(pFrontLeftForearm110,pFrontLeftArm111,pFrontLeftArm101,pFrontLeftForearm100,Renderer3D.CAR_RIGHT);

		LineData FrontLeftArmS3=addLine(pFrontLeftForearm100,pFrontLeftArm101,pFrontLeftArm001,pFrontLeftForearm000,Renderer3D.CAR_BACK);
	
		
		//frontRightLeg
		
		BPoint pFrontRightForearm000=addBPoint(x_side-leg_side,y_side-leg_side,0);
		BPoint pFrontRightForearm100=addBPoint(x_side,y_side-leg_side,0);
		BPoint pFrontRightForearm110=addBPoint(x_side,y_side,0);
		BPoint pFrontRightForearm010=addBPoint(x_side-leg_side,y_side,0);
		
		LineData FrontRightForearm=addLine(pFrontRightForearm000,pFrontRightForearm010,pFrontRightForearm110,pFrontRightForearm100,Renderer3D.CAR_FRONT);
	

		
		BPoint pFrontRightForearm001=addBPoint(x_side-leg_side,y_side-leg_side,radius_length);
		BPoint pFrontRightForearm101=addBPoint(x_side,y_side-leg_side,radius_length);
		BPoint pFrontRightForearm111=addBPoint(x_side,y_side,radius_length);
		BPoint pFrontRightForearm011=addBPoint(x_side-leg_side,y_side,radius_length);

		
		LineData frontRightForearmS0=addLine(pFrontRightForearm000,pFrontRightForearm001,pFrontRightForearm011,pFrontRightForearm010,Renderer3D.CAR_LEFT);

		LineData frontRightForearmS1=addLine(pFrontRightForearm010,pFrontRightForearm011,pFrontRightForearm111,pFrontRightForearm110,Renderer3D.CAR_FRONT);

		LineData frontRightForearmS2=addLine(pFrontRightForearm110,pFrontRightForearm111,pFrontRightForearm101,pFrontRightForearm100,Renderer3D.CAR_RIGHT);
	
		LineData frontRightForearmS3=addLine(pFrontRightForearm100,pFrontRightForearm101,pFrontRightForearm001,pFrontRightForearm000,Renderer3D.CAR_BACK);
	
		

		//right arm
		
		BPoint pFrontRightArm001=addBPoint(x_side-leg_side,y_side-leg_side,radius_length+humerus_length);
		BPoint pFrontRightArm101=addBPoint(x_side,y_side-leg_side,radius_length+humerus_length);
		BPoint pFrontRightArm111=addBPoint(x_side,y_side,radius_length+humerus_length);
		BPoint pFrontRightArm011=addBPoint(x_side-leg_side,y_side,radius_length+humerus_length);
		
		LineData topRightArm=addLine(pFrontRightArm001,pFrontRightArm101,pFrontRightArm111,pFrontRightArm011,Renderer3D.CAR_TOP);	
		
		LineData frontRightArmS0=addLine(pFrontRightForearm000,pFrontRightArm001,pFrontRightArm011,pFrontRightForearm010,Renderer3D.CAR_LEFT);
	
		LineData frontRightArmS1=addLine(pFrontRightForearm010,pFrontRightArm011,pFrontRightArm111,pFrontRightForearm110,Renderer3D.CAR_FRONT);
	
		LineData frontRightArmS2=addLine(pFrontRightForearm110,pFrontRightArm111,pFrontRightArm101,pFrontRightForearm100,Renderer3D.CAR_RIGHT);
	
		LineData frontRightArmS3=addLine(pFrontRightForearm100,pFrontRightArm101,pFrontRightArm001,pFrontRightForearm000,Renderer3D.CAR_BACK);
	
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private BPoint[][][] buildQuadrupedHeadMesh() {
		
		double xc=x_side*0.5;
		
		double hy0=y_side-neck_side;			
		double hy1=y_side-neck_side+head_DY/4.0;
		double hy2=y_side-neck_side+head_DY/2.0;
		double hy3=y_side-neck_side+head_DY*3.0/4.0;
		double hy4=y_side-neck_side+head_DY;
		double hz=femur_length+shinbone_length+z_side+neck_length;

		BPoint[][][] head=new BPoint[2][5][2];

		head[0][0][0]=addBPoint(xc-head_DX*0.5,hy0,hz);
		head[1][0][0]=addBPoint(xc+head_DX*0.5,hy0,hz);
		head[1][0][1]=addBPoint(xc+head_DX*0.5,hy0,hz+head_DZ);
		head[0][0][1]=addBPoint(xc-head_DX*0.5,hy0,hz+head_DZ);
		
		head[0][1][0]=addBPoint(xc-head_DX*0.5,hy1,hz);
		head[1][1][0]=addBPoint(xc+head_DX*0.5,hy1,hz);
		head[1][1][1]=addBPoint(xc+head_DX*0.5,hy1,hz+head_DZ);
		head[0][1][1]=addBPoint(xc-head_DX*0.5,hy1,hz+head_DZ);		
		
		head[0][2][0]=addBPoint(xc-head_DX*0.5,hy2,hz);
		head[1][2][0]=addBPoint(xc+head_DX*0.5,hy2,hz);
		head[1][2][1]=addBPoint(xc+head_DX*0.5,hy2,hz+head_DZ);
		head[0][2][1]=addBPoint(xc-head_DX*0.5,hy2,hz+head_DZ);	
		
		head[0][3][0]=addBPoint(xc-head_DX*0.5,hy3,hz);
		head[1][3][0]=addBPoint(xc+head_DX*0.5,hy3,hz);
		head[1][3][1]=addBPoint(xc+head_DX*0.5,hy3,hz+head_DZ);
		head[0][3][1]=addBPoint(xc-head_DX*0.5,hy3,hz+head_DZ);	
		
		head[0][4][0]=addBPoint(xc-head_DX*0.5,hy4,hz);
		head[1][4][0]=addBPoint(xc+head_DX*0.5,hy4,hz);
		head[1][4][1]=addBPoint(xc+head_DX*0.5,hy4,hz+head_DZ);
		head[0][4][1]=addBPoint(xc-head_DX*0.5,hy4,hz+head_DZ);	

		addLine(head[0][0][1],head[1][0][1],head[1][1][1],head[0][1][1],Renderer3D.CAR_TOP);

		addLine(head[0][0][0],head[0][1][0],head[1][1][0],head[1][0][0],Renderer3D.CAR_BOTTOM);

		addLine(head[0][0][0],head[0][0][1],head[0][1][1],head[0][1][0],Renderer3D.CAR_LEFT);

		addLine(head[1][0][0],head[1][1][0],head[1][1][1],head[1][0][1],Renderer3D.CAR_RIGHT);

		addLine(head[0][0][0],head[1][0][0],head[1][0][1],head[0][0][1],Renderer3D.CAR_BACK);

		//addLine(head[0][1][0],head[0][1][1],head[1][1][1],head[1][1][0],Renderer3D.CAR_FRONT);
		
		////
		
		addLine(head[0][1][1],head[1][1][1],head[1][2][1],head[0][2][1],Renderer3D.CAR_TOP);

		addLine(head[0][1][0],head[0][2][0],head[1][2][0],head[1][1][0],Renderer3D.CAR_BOTTOM);

		addLine(head[0][1][0],head[0][1][1],head[0][2][1],head[0][2][0],Renderer3D.CAR_LEFT);

		addLine(head[1][1][0],head[1][2][0],head[1][2][1],head[1][1][1],Renderer3D.CAR_RIGHT);

		//addLine(head[0][0][0],head[1][0][0],head[1][0][1],head[0][0][1],Renderer3D.CAR_BACK);

		//addLine(head[0][1][0],head[0][1][1],head[1][1][1],head[1][1][0],Renderer3D.CAR_FRONT);
		
		////
		
		addLine(head[0][2][1],head[1][2][1],head[1][3][1],head[0][3][1],Renderer3D.CAR_TOP);

		addLine(head[0][2][0],head[0][3][0],head[1][3][0],head[1][2][0],Renderer3D.CAR_BOTTOM);

		addLine(head[0][2][0],head[0][2][1],head[0][3][1],head[0][3][0],Renderer3D.CAR_LEFT);

		addLine(head[1][2][0],head[1][3][0],head[1][3][1],head[1][2][1],Renderer3D.CAR_RIGHT);

		//addLine(head[0][0][0],head[1][0][0],head[1][0][1],head[0][0][1],Renderer3D.CAR_BACK);

		//addLine(head[0][1][0],head[0][1][1],head[1][1][1],head[1][1][0],Renderer3D.CAR_FRONT);
		
		////
		
		addLine(head[0][3][1],head[1][3][1],head[1][4][1],head[0][4][1],Renderer3D.CAR_TOP);

		addLine(head[0][3][0],head[0][4][0],head[1][4][0],head[1][3][0],Renderer3D.CAR_BOTTOM);

		addLine(head[0][3][0],head[0][3][1],head[0][4][1],head[0][4][0],Renderer3D.CAR_LEFT);

		addLine(head[1][3][0],head[1][4][0],head[1][4][1],head[1][3][1],Renderer3D.CAR_RIGHT);

		//addLine(head[0][0][0],head[1][0][0],head[1][0][1],head[0][0][1],Renderer3D.CAR_BACK);

		addLine(head[0][4][0],head[0][4][1],head[1][4][1],head[1][4][0],Renderer3D.CAR_FRONT);
		
		////
		
		return head;
	}




	

	



}	