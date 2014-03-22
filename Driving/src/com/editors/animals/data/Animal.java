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
		points.setSize(400);

		polyData=new Vector();

		n=0;

		double xc=x_side/2.0;
		double yc=y_side/2.0;

		//body:
		
	
		double SHOULDER_DX=10;	
		double SHOULDER_WIDTH=SHOULDER_DX+x_side*0.5;		
		double WAIST_DX=x_side*0.1;
		double WAIST_WIDTH=x_side*0.5-WAIST_DX;

		Segments b0=new Segments(xc,x_side,yc,y_side,femur_length+shinbone_length,z_side);
		
		int numx=5;
		int numy=5;
		int numz=7;
		

		
		BPoint[][][] body=new BPoint[numx][numy][numz]; 

		body[0][0][0]=addBPoint(-0.5,-0.5,0.0,b0);
		body[1][0][0]=addBPoint(-0.25,-0.5,0.0,b0);
		body[2][0][0]=addBPoint(0.0,-0.5,0.0,b0);
		body[3][0][0]=addBPoint(0.25,-0.5,0.0,b0);
		body[4][0][0]=addBPoint(0.5,-0.5,0.0,b0);
		body[0][1][0]=addBPoint(-0.5,-0.25,0.0,b0);
		body[1][1][0]=addBPoint(-0.25,-0.25,0.0,b0);
		body[2][1][0]=addBPoint(0.0,-0.25,0.0,b0);
		body[3][1][0]=addBPoint(0.25,-0.25,0.0,b0);
		body[4][1][0]=addBPoint(0.5,-0.25,0.0,b0);	
		body[0][2][0]=addBPoint(-0.5,0.0,0.0,b0);
		body[1][2][0]=addBPoint(-0.25,0.0,0.0,b0);
		body[2][2][0]=addBPoint(0.0,0.0,0.0,b0);
		body[3][2][0]=addBPoint(0.25,0.0,0.0,b0);
		body[4][2][0]=addBPoint(0.5,0.0,0.0,b0);
		body[0][3][0]=addBPoint(-0.5,0.25,0.0,b0);
		body[1][3][0]=addBPoint(-0.25,0.25,0.0,b0);
		body[2][3][0]=addBPoint(0.0,0.25,0.0,b0);
		body[3][3][0]=addBPoint(0.25,0.25,0.0,b0);
		body[4][3][0]=addBPoint(0.5,0.25,0.0,b0);
		body[0][4][0]=addBPoint(-0.5,0.5,0.0,b0);
		body[1][4][0]=addBPoint(-0.25,0.5,0.0,b0);
		body[2][4][0]=addBPoint(0.0,0.5,0.0,b0);
		body[3][4][0]=addBPoint(0.25,0.5,0.0,b0);
		body[4][4][0]=addBPoint(0.5,0.5,0.0,b0);
		
		body[0][0][1]=addBPoint(-0.5,-0.5,0.25,b0);
		body[1][0][1]=addBPoint(-0.25,-0.5,0.25,b0);
		body[2][0][1]=addBPoint(0.0,-0.5,0.25,b0);
		body[3][0][1]=addBPoint(0.25,-0.5,0.25,b0);
		body[4][0][1]=addBPoint(0.5,-0.5,0.25,b0);		
		body[0][1][1]=addBPoint(-0.5,-0.25,0.25,b0);
		body[4][1][1]=addBPoint(0.5,-0.25,0.25,b0);
		body[0][2][1]=addBPoint(-0.5,0.0,0.25,b0);
		body[4][2][1]=addBPoint(0.5,0.0,0.25,b0);
		body[0][3][1]=addBPoint(-0.5,0.25,0.25,b0);
		body[4][3][1]=addBPoint(0.5,0.25,0.25,b0);
		body[0][4][1]=addBPoint(-0.5,0.5,0.25,b0);
		body[1][4][1]=addBPoint(-0.25,0.5,0.25,b0);
		body[2][4][1]=addBPoint(0.0,0.5,0.25,b0);
		body[3][4][1]=addBPoint(0.25,0.5,0.25,b0);
		body[4][4][1]=addBPoint(0.5,0.5,0.25,b0);
		
		double wr=WAIST_WIDTH/x_side;
		
		body[0][0][2]=addBPoint(-wr,-0.5,0.5,b0);
		body[1][0][2]=addBPoint(-wr*0.5,-0.5,0.5,b0);
		body[2][0][2]=addBPoint(0.0,-0.5,0.5,b0);
		body[3][0][2]=addBPoint(wr*0.5,-0.5,0.5,b0);
		body[4][0][2]=addBPoint(wr,-0.5,0.5,b0);	
		body[0][1][2]=addBPoint(-wr,-0.25,0.5,b0);		
		body[4][1][2]=addBPoint(wr,-0.25,0.5,b0);
		body[0][2][2]=addBPoint(-wr,0.0,0.5,b0);		
		body[4][2][2]=addBPoint(wr,0.0,0.5,b0);
		body[0][3][2]=addBPoint(-wr,0.25,0.5,b0);
		body[4][3][2]=addBPoint(wr,0.25,0.5,b0);
		body[0][4][2]=addBPoint(-wr,0.5,0.5,b0);
		body[1][4][2]=addBPoint(-wr*0.5,0.5,0.5,b0);
		body[2][4][2]=addBPoint(0.0,0.5,0.5,b0);
		body[3][4][2]=addBPoint(wr*0.5,0.5,0.5,b0);
		body[4][4][2]=addBPoint(wr,0.5,0.5,b0);
		
		double sr=SHOULDER_WIDTH/x_side;
		
		body[0][0][3]=addBPoint(-sr,-0.5,0.75,b0);
		body[1][0][3]=addBPoint(-sr*0.5,-0.5,0.75,b0);
		body[2][0][3]=addBPoint(0.0,-0.5,0.75,b0);
		body[3][0][3]=addBPoint(sr*0.5,-0.5,0.75,b0);
		body[4][0][3]=addBPoint(sr,-0.5,0.75,b0);
		body[0][1][3]=addBPoint(-sr,-0.25,0.75,b0);		
		body[4][1][3]=addBPoint(sr,-0.25,0.75,b0);
		body[0][2][3]=addBPoint(-sr,0.0,0.75,b0);	
		body[4][2][3]=addBPoint(sr,0.0,0.75,b0);
		body[0][3][3]=addBPoint(-sr,0.25,0.75,b0);		
		body[4][3][3]=addBPoint(sr,0.25,0.75,b0);
		body[0][4][3]=addBPoint(-sr,0.5,0.75,b0);
		body[1][4][3]=addBPoint(-sr*0.5,0.5,0.75,b0);
		body[2][4][3]=addBPoint(0.0,0.5,0.75,b0);
		body[3][4][3]=addBPoint(sr*0.5,0.5,0.75,b0);
		body[4][4][3]=addBPoint(sr,0.5,0.75,b0);
		
		body[0][0][4]=addBPoint(-sr,-0.5,1.0,b0);
		body[1][0][4]=addBPoint(-sr*0.5,-0.5,1.0,b0);
		body[2][0][4]=addBPoint(0.0,-0.5,1.0,b0);
		body[3][0][4]=addBPoint(sr*0.5,-0.5,1.0,b0);
		body[4][0][4]=addBPoint(sr,-0.5,1.0,b0);
		body[0][1][4]=addBPoint(-sr,-0.25,1.0,b0);		
		body[4][1][4]=addBPoint(sr,-0.25,1.0,b0);
		body[0][2][4]=addBPoint(-sr,0.0,1.0,b0);		
		body[4][2][4]=addBPoint(sr,0.0,1.0,b0);
		body[0][3][4]=addBPoint(-sr,0.25,1.0,b0);		
		body[4][3][4]=addBPoint(sr,0.25,1.0,b0);
		body[0][4][4]=addBPoint(-sr,0.5,1.0,b0);
		body[1][4][4]=addBPoint(-sr*0.5,0.5,1.0,b0);
		body[2][4][4]=addBPoint(0.0,0.5,1.0,b0);
		body[3][4][4]=addBPoint(sr*0.5,0.5,1.0,b0);
		body[4][4][4]=addBPoint(sr,0.5,1.0,b0);
		
		///////////
		//head:
		
		BPoint[][][] head=buildHumanHeadMesh();
			
		Segments n0=new Segments(xc,neck_side,yc,y_side,femur_length+shinbone_length+z_side,neck_length);

		body[0][0][5]=addBPoint(-0.5,-0.5,0.5,n0);
		body[1][0][5]=addBPoint(-0.25,-0.5,0.5,n0);
		body[2][0][5]=addBPoint(0.0,-0.5,0.5,n0);
		body[3][0][5]=addBPoint(0.25,-0.5,0.5,n0);
		body[4][0][5]=addBPoint(0.5,-0.5,0.5,n0);
		body[0][1][5]=addBPoint(-0.5,-0.25,0.5,n0);		
		body[4][1][5]=addBPoint(0.5,-0.25,0.5,n0);
		body[0][2][5]=addBPoint(-0.5,0.0,0.5,n0);		
		body[4][2][5]=addBPoint(0.5,0.0,0.5,n0);
		body[0][3][5]=addBPoint(-0.5,0.25,0.5,n0);
		body[4][3][5]=addBPoint(0.5,0.25,0.5,n0);
		body[0][4][5]=addBPoint(-0.5,0.5,0.5,n0);
		body[1][4][5]=addBPoint(-0.25,0.5,0.5,n0);
		body[2][4][5]=addBPoint(0.0,0.5,0.5,n0);
		body[3][4][5]=addBPoint(0.25,0.5,0.5,n0);
		body[4][4][5]=addBPoint(0.5,0.5,0.5,n0);

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

		head[0][0][0]=addBPoint(-e2.x*1.0,-e2.y*0.5,0.0,n0);
		head[1][0][0]=addBPoint(-e3.x*0.5,-e3.y*0.5,0.0,n0);
		head[2][0][0]=addBPoint(e4.x,-e4.y*0.5,0.0,n0);
		head[3][0][0]=addBPoint(e3.x*0.5,-e3.y*0.5,0.0,n0);
		head[4][0][0]=addBPoint(e2.x,-e2.y*0.5,0.0,n0);
		head[0][1][0]=addBPoint(-e1.x,-e2.y*0.25,0.0,n0);		
		head[4][1][0]=addBPoint(e1.x,-e2.y*0.25,0.0,n0);
		head[0][2][0]=addBPoint(-e0.x,0.0,0.0,n0);	
		head[4][2][0]=addBPoint(e0.x,0.0,0.0,n0);
		head[0][3][0]=addBPoint(-e1.x,e2.y*0.25,0.0,n0);	
		head[4][3][0]=addBPoint(e1.x,e2.y*0.25,0.0,n0);
		head[0][4][0]=addBPoint(-e2.x,e2.y*0.5,0.0,n0);
		head[1][4][0]=addBPoint(-e3.x,e3.y*0.5,0.0,n0);
		head[2][4][0]=addBPoint(e4.x,e4.y*0.5,0.0,n0);
		head[3][4][0]=addBPoint(e3.x,e3.y*0.5,0.0,n0);
		head[4][4][0]=addBPoint(e2.x,e2.y*0.5,0.0,n0);

		head[0][0][1]=addBPoint(-e2.x*0.85,-e2.y*0.85,0.25,h0);
		head[1][0][1]=addBPoint(-e3.x*0.85,-e3.y*0.85,0.25,h0);
		head[2][0][1]=addBPoint(e4.x,-e4.y*0.85,0.25,h0);
		head[3][0][1]=addBPoint(e3.x*0.85,-e3.y*0.85,0.25,h0);
		head[4][0][1]=addBPoint(e2.x*0.85,-e2.y*0.85,0.25,h0);		
		head[0][1][1]=addBPoint(-e1.x*0.85,-e1.y*0.85,0.25,h0);		
		head[4][1][1]=addBPoint(e1.x*0.85,-e1.y*0.85,0.25,h0);
		head[0][2][1]=addBPoint(-e0.x*0.85,e0.y,0.25,h0);	
		head[4][2][1]=addBPoint(e0.x*0.85,e0.y,0.25,h0);
		head[0][3][1]=addBPoint(-e1.x*0.85,e1.y*0.85,0.25,h0);		
		head[4][3][1]=addBPoint(e1.x*0.85,e1.y*0.85,0.25,h0);
		head[0][4][1]=addBPoint(-e2.x*0.85,e2.y*0.85,0.25,h0);
		head[1][4][1]=addBPoint(-e3.x*0.85,e3.y*0.85,0.25,h0);
		head[2][4][1]=addBPoint(e4.x,e4.y*0.85,0.25,h0);
		head[3][4][1]=addBPoint(e3.x*0.85,e3.y*0.85,0.25,h0);
		head[4][4][1]=addBPoint(e2.x*0.85,e2.y*0.85,0.25,h0);
		
		head[0][0][2]=addBPoint(-e2.x,-e2.y,0.5,h0);
		head[1][0][2]=addBPoint(-e3.x,-e3.y,0.5,h0);
		head[2][0][2]=addBPoint(e4.x,-e4.y,0.5,h0);
		head[3][0][2]=addBPoint(e3.x,-e3.y,0.5,h0);
		head[4][0][2]=addBPoint(e2.x,-e2.y,0.5,h0);
		head[0][1][2]=addBPoint(-e1.x,-e1.y,0.5,h0);		
		head[4][1][2]=addBPoint(e1.x,-e1.y,0.5,h0);
		head[0][2][2]=addBPoint(-e0.x,e0.y,0.5,h0);	
		head[4][2][2]=addBPoint(e0.x,e0.y,0.5,h0);
		head[0][3][2]=addBPoint(-e1.x,e1.y,0.5,h0);		
		head[4][3][2]=addBPoint(e1.x,e1.y,0.5,h0);
		head[0][4][2]=addBPoint(-e2.x,e2.y,0.5,h0);
		head[1][4][2]=addBPoint(-e3.x,e3.y,0.5,h0);
		head[2][4][2]=addBPoint(e4.x,e4.y,0.5,h0);
		head[3][4][2]=addBPoint(e3.x,e3.y,0.5,h0);
		head[4][4][2]=addBPoint(e2.x,e2.y,0.5,h0);	
	
		head[0][0][3]=addBPoint(-e2.x,-e2.y,0.75,h0);
		head[1][0][3]=addBPoint(-e3.x,-e3.y,0.75,h0);
		head[2][0][3]=addBPoint(e4.x,-e4.y,0.75,h0);
		head[3][0][3]=addBPoint(e3.x,-e3.y,0.75,h0);
		head[4][0][3]=addBPoint(e2.x,-e2.y,0.75,h0);
		head[0][1][3]=addBPoint(-e1.x,-e1.y,0.75,h0);		
		head[4][1][3]=addBPoint(e1.x,-e1.y,0.75,h0);
		head[0][2][3]=addBPoint(-e0.x,e0.y,0.75,h0);	
		head[4][2][3]=addBPoint(e0.x,e0.y,0.75,h0);
		head[0][3][3]=addBPoint(-e1.x,e1.y,0.75,h0);		
		head[4][3][3]=addBPoint(e1.x,e1.y,0.75,h0);
		head[0][4][3]=addBPoint(-e2.x,e2.y,0.75,h0);
		head[1][4][3]=addBPoint(-e3.x,e3.y,0.75,h0);
		head[2][4][3]=addBPoint(e4.x,e4.y,0.75,h0);
		head[3][4][3]=addBPoint(e3.x,e3.y,0.75,h0);
		head[4][4][3]=addBPoint(e2.x,e2.y,0.75,h0);
		
		head[0][0][4]=addBPoint(-e2.x*0.75,-e2.y*0.75,1.0,h0);
		head[1][0][4]=addBPoint(-e3.x*0.75,-e3.y*0.75,1.0,h0);
		head[2][0][4]=addBPoint(e4.x*0.75,-e4.y*0.75,1.0,h0);
		head[3][0][4]=addBPoint(e3.x*0.75,-e3.y*0.75,1.0,h0);
		head[4][0][4]=addBPoint(e2.x*0.75,-e2.y*0.75,1.0,h0);	
		head[0][1][4]=addBPoint(-e1.x*0.75,-e1.y*0.75,1.0,h0);
		head[1][1][4]=addBPoint(-e1.x*0.5*0.75,-e1.y*0.5*0.75,1.0,h0);
		head[2][1][4]=addBPoint(0.0,-e1.y*0.5*0.75,1.0,h0);
		head[3][1][4]=addBPoint(e1.x*0.5*0.75,-e1.y*0.5*0.75,1.0,h0);
		head[4][1][4]=addBPoint(e1.x*0.75,-e1.y*0.75,1.0,h0);
		head[0][2][4]=addBPoint(-e0.x*0.75,e0.y,1.0,h0);
		head[1][2][4]=addBPoint(-e0.x*0.75,e0.y,1.0,h0);
		head[2][2][4]=addBPoint(0.0,e0.y,1.0,h0);
		head[3][2][4]=addBPoint(e0.x*0.5*0.75,e0.y,1.0,h0);
		head[4][2][4]=addBPoint(e0.x*0.75,e0.y,1.0,h0);
		head[0][3][4]=addBPoint(-e1.x*0.75,e1.y*0.75,1.0,h0);
		head[1][3][4]=addBPoint(-e1.x*0.5*0.75,e1.y*0.5*0.75,1.0,h0);
		head[2][3][4]=addBPoint(0.0,e1.y*0.5*0.75,1.0,h0);
		head[3][3][4]=addBPoint(e1.x*0.5*0.75,e1.y*0.5*0.75,1.0,h0);
		head[4][3][4]=addBPoint(e1.x*0.75,e1.y*0.75,1.0,h0);
		head[0][4][4]=addBPoint(-e2.x*0.75,e2.y*0.75,1.0,h0);
		head[1][4][4]=addBPoint(-e3.x*0.75,e3.y*0.75,1.0,h0);
		head[2][4][4]=addBPoint(e4.x,e4.y*0.75,1.0,h0);
		head[3][4][4]=addBPoint(e3.x*0.75,e3.y*0.75,1.0,h0);
		head[4][4][4]=addBPoint(e2.x*0.75,e2.y*0.75,1.0,h0);

		
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
		

		double xc=x_side*0.5;

		//main body:
		
		double dz=femur_length+shinbone_length-(radius_length+humerus_length);
		
		int numx=5;
		int numy=5;
		int numz=5;
		
		BPoint[][][] body=new BPoint[numx][numy][numz]; 
		
		Segments pb0=new Segments(xc,x_side,0,y_side,femur_length+shinbone_length-z_side*0.5,z_side);
		Segments pf0=new Segments(xc,x_side,0,y_side,radius_length+humerus_length-z_side*0.5,z_side+dz);

		body[0][0][0]=addBPoint(-0.5,0.0,0.0,pb0);
		body[1][0][0]=addBPoint(-0.25,0.0,0.0,pb0);
		body[2][0][0]=addBPoint(0.0,0.0,0.0,pb0);
		body[3][0][0]=addBPoint(0.25,0.0,0.0,pb0);
		body[4][0][0]=addBPoint(0.5,0.0,0.0,pb0);
		body[0][0][1]=addBPoint(-0.5,0.0,0.25,pb0);	
		body[1][0][1]=addBPoint(-0.25,0.0,0.25,pb0);
		body[2][0][1]=addBPoint(0.0,0.0,0.25,pb0);
		body[3][0][1]=addBPoint(0.25,0.0,0.25,pb0);
		body[4][0][1]=addBPoint(0.5,0.0,0.25,pb0);
		body[0][0][2]=addBPoint(-0.5,0.0,0.5,pb0);	
		body[1][0][2]=addBPoint(-0.25,0.0,0.5,pb0);
		body[2][0][2]=addBPoint(0.0,0.0,0.5,pb0);
		body[3][0][2]=addBPoint(0.25,0.0,0.5,pb0);
		body[4][0][2]=addBPoint(0.5,0.0,0.5,pb0);
		body[0][0][3]=addBPoint(-0.5,0.0,0.75,pb0);	
		body[1][0][3]=addBPoint(-0.25,0.0,0.75,pb0);
		body[2][0][3]=addBPoint(0.0,0.0,0.75,pb0);
		body[3][0][3]=addBPoint(0.25,0.0,0.75,pb0);
		body[4][0][3]=addBPoint(0.5,0.0,0.75,pb0);
		body[0][0][4]=addBPoint(-0.5,0.0,1.0,pb0);	
		body[1][0][4]=addBPoint(-0.25,0.0,1.0,pb0);
		body[2][0][4]=addBPoint(0.0,0.0,1.0,pb0);
		body[3][0][4]=addBPoint(0.25,0.0,1.0,pb0);
		body[4][0][4]=addBPoint(0.5,0.0,1.0,pb0);
		
		body[0][1][0]=addBPoint(-0.5,0.25,0.0,pb0);
		body[1][1][0]=addBPoint(-0.25,0.25,0.0,pb0);
		body[2][1][0]=addBPoint(0.0,0.25,0.0,pb0);
		body[3][1][0]=addBPoint(0.25,0.25,0.0,pb0);
		body[4][1][0]=addBPoint(0.5,0.25,0.0,pb0);	
		body[0][1][1]=addBPoint(-0.5,0.25,0.25,pb0);	
		body[1][1][1]=addBPoint(-0.25,0.25,0.25,pb0);
		body[2][1][1]=addBPoint(0.0,0.25,0.25,pb0);
		body[3][1][1]=addBPoint(0.25,0.25,0.25,pb0);
		body[4][1][1]=addBPoint(0.5,0.25,0.25,pb0);
		body[0][1][2]=addBPoint(-0.5,0.25,0.5,pb0);	
		body[1][1][2]=addBPoint(-0.25,0.25,0.5,pb0);
		body[2][1][2]=addBPoint(0.0,0.25,0.5,pb0);
		body[3][1][2]=addBPoint(0.25,0.25,0.5,pb0);
		body[4][1][2]=addBPoint(0.5,0.25,0.5,pb0);
		body[0][1][3]=addBPoint(-0.5,0.25,0.75,pb0);	
		body[1][1][3]=addBPoint(-0.25,0.25,0.75,pb0);
		body[2][1][3]=addBPoint(0.0,0.25,0.75,pb0);
		body[3][1][3]=addBPoint(0.25,0.25,0.75,pb0);
		body[4][1][3]=addBPoint(0.5,0.25,0.75,pb0);
		body[0][1][4]=addBPoint(-0.5,0.25,1.0,pb0);	
		body[1][1][4]=addBPoint(-0.25,0.25,1.0,pb0);
		body[2][1][4]=addBPoint(0.0,0.25,1.0,pb0);
		body[3][1][4]=addBPoint(0.25,0.25,1.0,pb0);
		body[4][1][4]=addBPoint(0.5,0.25,1.0,pb0);
		
		body[0][2][0]=addBPoint(-0.5,0.5,0.0,pb0);
		body[1][2][0]=addBPoint(-0.25,0.5,0.0,pb0);
		body[2][2][0]=addBPoint(0.0,0.5,0.0,pb0);
		body[3][2][0]=addBPoint(0.25,0.5,0.0,pb0);
		body[4][2][0]=addBPoint(0.5,0.5,0.0,pb0);
		body[0][2][1]=addBPoint(-0.5,0.5,0.25,pb0);	
		body[1][2][1]=addBPoint(-0.25,0.5,0.25,pb0);
		body[2][2][1]=addBPoint(0.0,0.5,0.25,pb0);
		body[3][2][1]=addBPoint(0.25,0.5,0.25,pb0);
		body[4][2][1]=addBPoint(0.5,0.5,0.25,pb0);
		body[0][2][2]=addBPoint(-0.5,0.5,0.5,pb0);	
		body[1][2][2]=addBPoint(-0.25,0.5,0.5,pb0);
		body[2][2][2]=addBPoint(0.0,0.5,0.5,pb0);
		body[3][2][2]=addBPoint(0.25,0.5,0.5,pb0);
		body[4][2][2]=addBPoint(0.5,0.5,0.5,pb0);
		body[0][2][3]=addBPoint(-0.5,0.5,0.75,pb0);	
		body[1][2][3]=addBPoint(-0.25,0.5,0.75,pb0);
		body[2][2][3]=addBPoint(0.0,0.5,0.75,pb0);
		body[3][2][3]=addBPoint(0.25,0.5,0.75,pb0);
		body[4][2][3]=addBPoint(0.5,0.5,0.75,pb0);
		body[0][2][4]=addBPoint(-0.5,0.5,1.0,pb0);	
		body[1][2][4]=addBPoint(-0.25,0.5,1.0,pb0);
		body[2][2][4]=addBPoint(0.0,0.5,1.0,pb0);
		body[3][2][4]=addBPoint(0.25,0.5,1.0,pb0);
		body[4][2][4]=addBPoint(0.5,0.5,1.0,pb0);
		
		body[0][3][0]=addBPoint(-0.5,0.75,0.0,pf0);
		body[1][3][0]=addBPoint(-0.25,0.75,0.0,pf0);
		body[2][3][0]=addBPoint(0.0,0.75,0.0,pf0);	
		body[3][3][0]=addBPoint(0.25,0.75,0.0,pf0);
		body[4][3][0]=addBPoint(0.5,0.75,0.0,pf0);
		body[0][3][1]=addBPoint(-0.5,0.75,0.25,pf0);		
		body[1][3][1]=addBPoint(-0.25,0.75,0.25,pf0);
		body[2][3][1]=addBPoint(0.0,0.75,0.25,pf0);
		body[3][3][1]=addBPoint(0.25,0.75,0.25,pf0);
		body[4][3][1]=addBPoint(0.5,0.75,0.25,pf0);
		body[0][3][2]=addBPoint(-0.5,0.75,0.5,pf0);		
		body[1][3][2]=addBPoint(-0.25,0.75,0.5,pf0);
		body[2][3][2]=addBPoint(0.0,0.75,0.5,pf0);
		body[3][3][2]=addBPoint(0.25,0.75,0.5,pf0);
		body[4][3][2]=addBPoint(0.5,0.75,0.5,pf0);
		body[0][3][3]=addBPoint(-0.5,0.75,0.75,pf0);		
		body[1][3][3]=addBPoint(-0.25,0.75,0.75,pf0);
		body[2][3][3]=addBPoint(0.0,0.75,0.75,pf0);
		body[3][3][3]=addBPoint(0.25,0.75,0.75,pf0);
		body[4][3][3]=addBPoint(0.5,0.75,0.75,pf0);
		body[0][3][4]=addBPoint(-0.5,0.75,1.0,pf0);		
		body[1][3][4]=addBPoint(-0.25,0.75,1.0,pf0);
		body[2][3][4]=addBPoint(0.0,0.75,1.0,pf0);
		body[3][3][4]=addBPoint(0.25,0.75,1.0,pf0);
		body[4][3][4]=addBPoint(0.5,0.75,1.0,pf0);
		
		body[0][4][0]=addBPoint(-0.5,1.0,0.0,pf0);
		body[1][4][0]=addBPoint(-0.25,1.0,0.0,pf0);	
		body[2][4][0]=addBPoint(0.0,1.0,0.0,pf0);	
		body[3][4][0]=addBPoint(0.25,1.0,0.0,pf0);		
		body[4][4][0]=addBPoint(0.5,1.0,0.0,pf0);	
		body[0][4][1]=addBPoint(-0.5,1.0,0.25,pf0);		
		body[1][4][1]=addBPoint(-0.25,1.0,0.25,pf0);
		body[2][4][1]=addBPoint(0.0,1.0,0.25,pf0);
		body[3][4][1]=addBPoint(0.25,1.0,0.25,pf0);
		body[4][4][1]=addBPoint(0.5,1.0,0.25,pf0);
		body[0][4][2]=addBPoint(-0.5,1.0,0.5,pf0);		
		body[1][4][2]=addBPoint(-0.25,1.0,0.5,pf0);
		body[2][4][2]=addBPoint(0.0,1.0,0.5,pf0);
		body[3][4][2]=addBPoint(0.25,1.0,0.5,pf0);
		body[4][4][2]=addBPoint(0.5,1.0,0.5,pf0);
		body[0][4][3]=addBPoint(-0.5,1.0,0.75,pf0);		
		body[1][4][3]=addBPoint(-0.25,1.0,0.75,pf0);
		body[2][4][3]=addBPoint(0.0,1.0,0.75,pf0);
		body[3][4][3]=addBPoint(0.25,1.0,0.75,pf0);
		body[4][4][3]=addBPoint(0.5,1.0,0.75,pf0);
		body[0][4][4]=addBPoint(-0.5,1.0,1.0,pf0);		
		body[1][4][4]=addBPoint(-0.25,1.0,1.0,pf0);
		body[2][4][4]=addBPoint(0.0,1.0,1.0,pf0);
		body[3][4][4]=addBPoint(0.25,1.0,1.0,pf0);
		body[4][4][4]=addBPoint(0.5,1.0,1.0,pf0);
		
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


		//neck:

		int ney=2;
		int nez=3;
		
		double nx0=xc-neck_side*0.5;
		double nx1=xc-neck_side*0.25;
		double nx2=xc;
		double nx3=xc+neck_side*0.25;
		double nx4=xc+neck_side*0.5;

		double nz0=femur_length+shinbone_length+z_side+neck_length/2.0;
		double nz1=femur_length+shinbone_length+z_side+neck_length;

		double ny0=y_side-neck_side;
		double ny1=y_side;
		
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
		
		neck[0][0][1]=addBPoint(nx0,ny0,nz0);
		neck[1][0][1]=addBPoint(nx1,ny0,nz0);
		neck[2][0][1]=addBPoint(nx2,ny0,nz0);
		neck[3][0][1]=addBPoint(nx3,ny0,nz0);
		neck[4][0][1]=addBPoint(nx4,ny0,nz0);
		neck[0][1][1]=addBPoint(nx0,ny1,nz0);
		neck[1][1][1]=addBPoint(nx1,ny1,nz0);
		neck[2][1][1]=addBPoint(nx2,ny1,nz0);
		neck[3][1][1]=addBPoint(nx3,ny1,nz0);
		neck[4][1][1]=addBPoint(nx4,ny1,nz0);
		
		neck[0][0][2]=addBPoint(nx0,ny0,nz1);
		neck[1][0][2]=addBPoint(nx1,ny0,nz1);
		neck[2][0][2]=addBPoint(nx2,ny0,nz1);
		neck[3][0][2]=addBPoint(nx3,ny0,nz1);
		neck[4][0][2]=addBPoint(nx4,ny0,nz1);		
		neck[0][1][2]=addBPoint(nx0,ny1,nz1);
		neck[1][1][2]=addBPoint(nx1,ny1,nz1);
		neck[2][1][2]=addBPoint(nx2,ny1,nz1);
		neck[3][1][2]=addBPoint(nx3,ny1,nz1);				
		neck[4][1][2]=addBPoint(nx4,ny1,nz1);
		

		
		for (int i = 0; i < numx-1; i++) {
			
			
			for (int k = 0; k < nez-1; k++) {
							
				if(i==0){
					
					addLine(neck[i][0][k],neck[i][0][k+1],neck[i][1][k+1],neck[i][1][k],Renderer3D.CAR_LEFT);
				}
	
				
				addLine(neck[i][0][k],neck[i+1][0][k],neck[i+1][0][k+1],neck[i][0][k+1],Renderer3D.CAR_BACK);
	
				addLine(neck[i][1][k],neck[i][1][k+1],neck[i+1][1][k+1],neck[i+1][1][k],Renderer3D.CAR_FRONT);
				
				
				if(i+1==numx-1){
					
					addLine(neck[i+1][0][k],neck[i+1][1][k],neck[i+1][1][k+1],neck[i+1][0][k+1],Renderer3D.CAR_RIGHT);
				}

			}
			
		}
		
		
		/////////head:		

		BPoint[][][] head=buildQuadrupedHeadMesh();
		


		//legs:	
		
		//backLeftLeg
		
		double lgx=-leg_side;
		
		BPoint pBackLeftLeg000=addBPoint(lgx,0,0);
		BPoint pBackLeftLeg100=addBPoint(lgx+leg_side,0,0);
		BPoint pBackLeftLeg110=addBPoint(lgx+leg_side,leg_side,0);
		BPoint pBackLeftLeg010=addBPoint(lgx,leg_side,0);

		
		LineData backLeftLeg=addLine(pBackLeftLeg000,pBackLeftLeg010,pBackLeftLeg110,pBackLeftLeg100,Renderer3D.CAR_FRONT);
		
		BPoint pBackLeftLeg001=addBPoint(lgx,0,shinbone_length);
		BPoint pBackLeftLeg101=addBPoint(lgx+leg_side,0,shinbone_length);
		BPoint pBackLeftLeg111=addBPoint(lgx+leg_side,leg_side,shinbone_length);
		BPoint pBackLeftLeg011=addBPoint(lgx,leg_side,shinbone_length);
		
		LineData backLeftLegS0=addLine(pBackLeftLeg000,pBackLeftLeg001,pBackLeftLeg011,pBackLeftLeg010,Renderer3D.CAR_LEFT);

		LineData backLeftLegS1=addLine(pBackLeftLeg010,pBackLeftLeg011,pBackLeftLeg111,pBackLeftLeg110,Renderer3D.CAR_FRONT);
	
		LineData backLeftLegS2=addLine(pBackLeftLeg110,pBackLeftLeg111,pBackLeftLeg101,pBackLeftLeg100,Renderer3D.CAR_RIGHT);
	
		LineData backLeftLegS3=addLine(pBackLeftLeg100,pBackLeftLeg101,pBackLeftLeg001,pBackLeftLeg000,Renderer3D.CAR_BACK);
	
		
		//back left thigh
		
		BPoint pBackLeftThigh001=addBPoint(lgx,0,femur_length+shinbone_length);
		BPoint pBackLeftThigh101=addBPoint(lgx+leg_side,0,femur_length+shinbone_length);
		BPoint pBackLeftThigh111=addBPoint(lgx+leg_side,leg_side,femur_length+shinbone_length);
		BPoint pBackLeftThigh011=addBPoint(lgx,leg_side,femur_length+shinbone_length);

		LineData backLeftThighS0=addLine(pBackLeftLeg001,pBackLeftThigh001,pBackLeftThigh011,pBackLeftLeg011,Renderer3D.CAR_LEFT);
	
		LineData backLeftThighS1=addLine(pBackLeftLeg011,pBackLeftThigh011,pBackLeftThigh111,pBackLeftLeg111,Renderer3D.CAR_FRONT);
	
		LineData backLeftThighS2=addLine(pBackLeftLeg111,pBackLeftThigh111,pBackLeftThigh101,pBackLeftLeg101,Renderer3D.CAR_RIGHT);

		LineData backLeftThighS3=addLine(pBackLeftLeg101,pBackLeftThigh101,pBackLeftThigh001,pBackLeftLeg001,Renderer3D.CAR_BACK);

		LineData backLeftTopThigh=addLine(pBackLeftThigh001,pBackLeftThigh101,pBackLeftThigh111,pBackLeftThigh011,Renderer3D.CAR_TOP);
		//backRightLeg
		
		BPoint pBackRightLeg000=addBPoint(-lgx+x_side-leg_side,0,0);
		BPoint pBackRightLeg100=addBPoint(-lgx+x_side,0,0);
		BPoint pBackRightLeg110=addBPoint(-lgx+x_side,leg_side,0);
		BPoint pBackRightLeg010=addBPoint(-lgx+x_side-leg_side,leg_side,0);
		
		LineData backRightLeg=addLine(pBackRightLeg000,pBackRightLeg010,pBackRightLeg110,pBackRightLeg100,Renderer3D.CAR_FRONT);


		
		BPoint pBackRightLeg001=addBPoint(-lgx+x_side-leg_side,0,shinbone_length);
		BPoint pBackRightLeg101=addBPoint(-lgx+x_side,0,shinbone_length);
		BPoint pBackRightLeg111=addBPoint(-lgx+x_side,leg_side,shinbone_length);
		BPoint pBackRightLeg011=addBPoint(-lgx+x_side-leg_side,leg_side,shinbone_length);

		
		LineData backRightLegS0=addLine(pBackRightLeg000,pBackRightLeg001,pBackRightLeg011,pBackRightLeg010,Renderer3D.CAR_LEFT);
	
		LineData backRightLegS1=addLine(pBackRightLeg010,pBackRightLeg011,pBackRightLeg111,pBackRightLeg110,Renderer3D.CAR_FRONT);
	
		LineData backRightLegS2=addLine(pBackRightLeg110,pBackRightLeg111,pBackRightLeg101,pBackRightLeg100,Renderer3D.CAR_RIGHT);
	
		LineData backRightLegS3=addLine(pBackRightLeg100,pBackRightLeg101,pBackRightLeg001,pBackRightLeg000,Renderer3D.CAR_BACK);
	
		
		//back right thigh
		
		BPoint pBackRightThigh001=addBPoint(-lgx+x_side-leg_side,0,femur_length+shinbone_length);
		BPoint pBackRightThigh101=addBPoint(-lgx+x_side,0,femur_length+shinbone_length);
		BPoint pBackRightThigh111=addBPoint(-lgx+x_side,leg_side,femur_length+shinbone_length);
		BPoint pBackRightThigh011=addBPoint(-lgx+x_side-leg_side,leg_side,femur_length+shinbone_length);

		LineData backRightThighS0=addLine(pBackRightLeg001,pBackRightThigh001,pBackRightThigh011,pBackRightLeg011,Renderer3D.CAR_LEFT);
	
		LineData backRightThighS1=addLine(pBackRightLeg011,pBackRightThigh011,pBackRightThigh111,pBackRightLeg111,Renderer3D.CAR_FRONT);

		LineData backRightThighS2=addLine(pBackRightLeg111,pBackRightThigh111,pBackRightThigh101,pBackRightLeg101,Renderer3D.CAR_RIGHT);
	
		LineData backRightThighS3=addLine(pBackRightLeg101,pBackRightThigh101,pBackRightThigh001,pBackRightLeg001,Renderer3D.CAR_BACK);

		LineData backRightTopThigh=addLine(pBackRightThigh001,pBackRightThigh101,pBackRightThigh111,pBackRightThigh011,Renderer3D.CAR_TOP);
		//frontLeftLeg
		
		BPoint pFrontLeftForearm000=addBPoint(lgx,y_side-leg_side,0);
		BPoint pFrontLeftForearm100=addBPoint(lgx+leg_side,y_side-leg_side,0);
		BPoint pFrontLeftForearm110=addBPoint(lgx+leg_side,y_side,0);
		BPoint pFrontLeftForearm010=addBPoint(lgx,y_side,0);
		
		LineData FrontLeftForearm=addLine(pFrontLeftForearm000,pFrontLeftForearm010,pFrontLeftForearm110,pFrontLeftForearm100,Renderer3D.CAR_FRONT);

		
		BPoint pFrontLeftForearm001=addBPoint(lgx,y_side-leg_side,radius_length);
		BPoint pFrontLeftForearm101=addBPoint(lgx+leg_side,y_side-leg_side,radius_length);
		BPoint pFrontLeftForearm111=addBPoint(lgx+leg_side,y_side,radius_length);
		BPoint pFrontLeftForearm011=addBPoint(lgx,y_side,radius_length);
		
		LineData FrontLeftForearmS0=addLine(pFrontLeftForearm000,pFrontLeftForearm001,pFrontLeftForearm011,pFrontLeftForearm010,Renderer3D.CAR_LEFT);
	
		LineData FrontLeftForearmS1=addLine(pFrontLeftForearm010,pFrontLeftForearm011,pFrontLeftForearm111,pFrontLeftForearm110,Renderer3D.CAR_FRONT);

		LineData FrontLeftForearmS2=addLine(pFrontLeftForearm110,pFrontLeftForearm111,pFrontLeftForearm101,pFrontLeftForearm100,Renderer3D.CAR_RIGHT);
	
		LineData FrontLeftForearmS3=addLine(pFrontLeftForearm100,pFrontLeftForearm101,pFrontLeftForearm001,pFrontLeftForearm000,Renderer3D.CAR_BACK);
	
		
		//left arm
		
		BPoint pFrontLeftArm001=addBPoint(lgx,y_side-leg_side,radius_length+humerus_length);
		BPoint pFrontLeftArm101=addBPoint(lgx+leg_side,y_side-leg_side,radius_length+humerus_length);
		BPoint pFrontLeftArm111=addBPoint(lgx+leg_side,y_side,radius_length+humerus_length);
		BPoint pFrontLeftArm011=addBPoint(lgx,y_side,radius_length+humerus_length);
		
		LineData topLeftArm=addLine(pFrontLeftArm001,pFrontLeftArm101,pFrontLeftArm111,pFrontLeftArm011,Renderer3D.CAR_TOP);
	
		
		LineData FrontLeftArmS0=addLine(pFrontLeftForearm001,pFrontLeftArm001,pFrontLeftArm011,pFrontLeftForearm011,Renderer3D.CAR_LEFT);

		LineData FrontLeftArmS1=addLine(pFrontLeftForearm011,pFrontLeftArm011,pFrontLeftArm111,pFrontLeftForearm111,Renderer3D.CAR_FRONT);
	
		LineData FrontLeftArmS2=addLine(pFrontLeftForearm111,pFrontLeftArm111,pFrontLeftArm101,pFrontLeftForearm101,Renderer3D.CAR_RIGHT);

		LineData FrontLeftArmS3=addLine(pFrontLeftForearm101,pFrontLeftArm101,pFrontLeftArm001,pFrontLeftForearm001,Renderer3D.CAR_BACK);
	
		
		//frontRightLeg
		
		BPoint pFrontRightForearm000=addBPoint(-lgx+x_side-leg_side,y_side-leg_side,0);
		BPoint pFrontRightForearm100=addBPoint(-lgx+x_side,y_side-leg_side,0);
		BPoint pFrontRightForearm110=addBPoint(-lgx+x_side,y_side,0);
		BPoint pFrontRightForearm010=addBPoint(-lgx+x_side-leg_side,y_side,0);
		
		LineData FrontRightForearm=addLine(pFrontRightForearm000,pFrontRightForearm010,pFrontRightForearm110,pFrontRightForearm100,Renderer3D.CAR_FRONT);
	

		
		BPoint pFrontRightForearm001=addBPoint(-lgx+x_side-leg_side,y_side-leg_side,radius_length);
		BPoint pFrontRightForearm101=addBPoint(-lgx+x_side,y_side-leg_side,radius_length);
		BPoint pFrontRightForearm111=addBPoint(-lgx+x_side,y_side,radius_length);
		BPoint pFrontRightForearm011=addBPoint(-lgx+x_side-leg_side,y_side,radius_length);

		
		LineData frontRightForearmS0=addLine(pFrontRightForearm000,pFrontRightForearm001,pFrontRightForearm011,pFrontRightForearm010,Renderer3D.CAR_LEFT);

		LineData frontRightForearmS1=addLine(pFrontRightForearm010,pFrontRightForearm011,pFrontRightForearm111,pFrontRightForearm110,Renderer3D.CAR_FRONT);

		LineData frontRightForearmS2=addLine(pFrontRightForearm110,pFrontRightForearm111,pFrontRightForearm101,pFrontRightForearm100,Renderer3D.CAR_RIGHT);
	
		LineData frontRightForearmS3=addLine(pFrontRightForearm100,pFrontRightForearm101,pFrontRightForearm001,pFrontRightForearm000,Renderer3D.CAR_BACK);
	
		

		//right arm
		
		BPoint pFrontRightArm001=addBPoint(-lgx+x_side-leg_side,y_side-leg_side,radius_length+humerus_length);
		BPoint pFrontRightArm101=addBPoint(-lgx+x_side,y_side-leg_side,radius_length+humerus_length);
		BPoint pFrontRightArm111=addBPoint(-lgx+x_side,y_side,radius_length+humerus_length);
		BPoint pFrontRightArm011=addBPoint(-lgx+x_side-leg_side,y_side,radius_length+humerus_length);
		
		LineData topRightArm=addLine(pFrontRightArm001,pFrontRightArm101,pFrontRightArm111,pFrontRightArm011,Renderer3D.CAR_TOP);	
		
		LineData frontRightArmS0=addLine(pFrontRightForearm001,pFrontRightArm001,pFrontRightArm011,pFrontRightForearm011,Renderer3D.CAR_LEFT);
	
		LineData frontRightArmS1=addLine(pFrontRightForearm011,pFrontRightArm011,pFrontRightArm111,pFrontRightForearm111,Renderer3D.CAR_FRONT);
	
		LineData frontRightArmS2=addLine(pFrontRightForearm111,pFrontRightArm111,pFrontRightArm101,pFrontRightForearm101,Renderer3D.CAR_RIGHT);
	
		LineData frontRightArmS3=addLine(pFrontRightForearm101,pFrontRightArm101,pFrontRightArm001,pFrontRightForearm001,Renderer3D.CAR_BACK);
	
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private BPoint[][][] buildQuadrupedHeadMesh() {
		
		double xc=x_side*0.5;
		
		double hz=femur_length+shinbone_length+z_side+neck_length;
		
		Segments h0=new Segments(xc,head_DX,y_side-neck_side,head_DY,hz,head_DZ);
		
		int hnx=5;
		int hny=5;
		int hnz=5;

		BPoint[][][] head=new BPoint[hnx][hny][hnz];

		head[0][0][0]=addBPoint(-0.5,0.0,0,h0);
		head[1][0][0]=addBPoint(-0.25,0.0,0,h0);
		head[2][0][0]=addBPoint(0.0,0.0,0,h0);	
		head[3][0][0]=addBPoint(0.25,0.0,0,h0);	
		head[4][0][0]=addBPoint(0.5,0.0,0,h0);	
		head[0][0][1]=addBPoint(-0.5,0.0,0.25,h0);
		head[1][0][1]=addBPoint(-0.25,0.0,0.25,h0);
		head[2][0][1]=addBPoint(0.0,0.0,0.25,h0);
		head[3][0][1]=addBPoint(0.25,0.0,0.25,h0);
		head[4][0][1]=addBPoint(0.5,0.0,0.25,h0);
		head[0][0][2]=addBPoint(-0.5,0.0,0.5,h0);
		head[1][0][2]=addBPoint(-0.25,0.0,0.5,h0);
		head[2][0][2]=addBPoint(0.0,0.0,0.5,h0);
		head[3][0][2]=addBPoint(0.25,0.0,0.5,h0);
		head[4][0][2]=addBPoint(0.5,0.0,0.5,h0);
		head[0][0][3]=addBPoint(-0.5,0.0,0.75,h0);
		head[1][0][3]=addBPoint(-0.25,0.0,0.75,h0);
		head[2][0][3]=addBPoint(0.0,0.0,0.75,h0);
		head[3][0][3]=addBPoint(0.25,0.0,0.75,h0);
		head[4][0][3]=addBPoint(0.5,0.0,0.75,h0);
		head[0][0][4]=addBPoint(-0.5,0.0,1.0,h0);
		head[1][0][4]=addBPoint(-0.25,0.0,1.0,h0);
		head[2][0][4]=addBPoint(0.0,0.0,1.0,h0);
		head[3][0][4]=addBPoint(0.25,0.0,1.0,h0);
		head[4][0][4]=addBPoint(0.5,0.0,1.0,h0);
		
		head[0][1][0]=addBPoint(-0.5,.25,0,h0);
		head[1][1][0]=addBPoint(-0.25,.25,0,h0);
		head[2][1][0]=addBPoint(0.0,.25,0,h0);		
		head[3][1][0]=addBPoint(0.25,.25,0,h0);	
		head[4][1][0]=addBPoint(0.5,.25,0,h0);
		head[0][1][1]=addBPoint(-0.5,.25,0.25,h0);
		head[4][1][1]=addBPoint(0.5,.25,0.25,h0);
		head[0][1][2]=addBPoint(-0.5,.25,0.5,h0);		
		head[4][1][2]=addBPoint(0.5,.25,0.5,h0);
		head[0][1][3]=addBPoint(-0.5,.25,0.75,h0);		
		head[4][1][3]=addBPoint(0.5,.25,0.75,h0);
		head[0][1][4]=addBPoint(-0.5,.25,1.0,h0);	
		head[1][1][4]=addBPoint(-0.25,.25,1.0,h0);		
		head[2][1][4]=addBPoint(0.0,.25,1.0,h0);	
		head[3][1][4]=addBPoint(0.25,.25,1.0,h0);	
		head[4][1][4]=addBPoint(0.5,.25,1.0,h0);	
		
		head[0][2][0]=addBPoint(-0.5,0.5,0,h0);
		head[1][2][0]=addBPoint(-0.25,0.5,0,h0);
		head[2][2][0]=addBPoint(0.0,0.5,0,h0);	
		head[3][2][0]=addBPoint(0.25,0.5,0,h0);
		head[4][2][0]=addBPoint(0.5,0.5,0,h0);
		head[0][2][1]=addBPoint(-0.5,0.5,0.25,h0);		
		head[4][2][1]=addBPoint(0.5,0.5,0.25,h0);
		head[0][2][2]=addBPoint(-0.5,0.5,0.5,h0);		
		head[4][2][2]=addBPoint(0.5,0.5,0.5,h0);
		head[0][2][3]=addBPoint(-0.5,0.5,0.75,h0);		
		head[4][2][3]=addBPoint(0.5,0.5,0.75,h0);
		head[0][2][4]=addBPoint(-0.5,0.5,1.0,h0);	
		head[1][2][4]=addBPoint(-0.25,0.5,1.0,h0);	
		head[2][2][4]=addBPoint(0.0,0.5,1.0,h0);
		head[3][2][4]=addBPoint(0.25,0.5,1.0,h0);
		head[4][2][4]=addBPoint(0.5,0.5,1.0,h0);
		
		head[0][3][0]=addBPoint(-0.5,0.75,0,h0);
		head[1][3][0]=addBPoint(-0.25,0.75,0,h0);
		head[2][3][0]=addBPoint(0.0,0.75,0,h0);
		head[3][3][0]=addBPoint(0.25,0.75,0,h0);
		head[4][3][0]=addBPoint(0.5,0.75,0,h0);
		head[0][3][1]=addBPoint(-0.5,0.75,0.25,h0);
		head[4][3][1]=addBPoint(0.5,0.75,0.25,h0);
		head[0][3][2]=addBPoint(-0.5,0.75,0.5,h0);
		head[4][3][2]=addBPoint(0.5,0.75,0.5,h0);
		head[0][3][3]=addBPoint(-0.5,0.75,0.75,h0);
		head[4][3][3]=addBPoint(0.5,0.75,0.75,h0);
		head[0][3][4]=addBPoint(-0.5,0.75,1.0,h0);	
		head[1][3][4]=addBPoint(-0.25,0.75,1.0,h0);	
		head[2][3][4]=addBPoint(0.0,0.75,1.0,h0);	
		head[3][3][4]=addBPoint(0.25,0.75,1.0,h0);	
		head[4][3][4]=addBPoint(0.5,0.75,1.0,h0);	
		
		head[0][4][0]=addBPoint(-0.5,1.0,0.0,h0);
		head[1][4][0]=addBPoint(-0.25,1.0,0.0,h0);
		head[2][4][0]=addBPoint(0.0,1.0,0.0,h0);
		head[3][4][0]=addBPoint(0.25,1.0,0.0,h0);
		head[4][4][0]=addBPoint(0.5,1.0,0.0,h0);
		head[0][4][1]=addBPoint(-0.5,1.0,0.25,h0);
		head[1][4][1]=addBPoint(-0.25,1.0,0.25,h0);
		head[2][4][1]=addBPoint(0.0,1.0,0.25,h0);
		head[3][4][1]=addBPoint(0.25,1.0,0.25,h0);
		head[4][4][1]=addBPoint(0.5,1.0,0.25,h0);
		head[0][4][2]=addBPoint(-0.5,1.0,0.5,h0);
		head[1][4][2]=addBPoint(-0.25,1.0,0.5,h0);
		head[2][4][2]=addBPoint(0.0,1.0,0.5,h0);
		head[3][4][2]=addBPoint(0.25,1.0,0.5,h0);
		head[4][4][2]=addBPoint(0.5,1.0,0.5,h0);
		head[0][4][3]=addBPoint(-0.5,1.0,0.75,h0);
		head[1][4][3]=addBPoint(-0.25,1.0,0.75,h0);
		head[2][4][3]=addBPoint(0.0,1.0,0.75,h0);
		head[3][4][3]=addBPoint(0.25,1.0,0.75,h0);
		head[4][4][3]=addBPoint(0.5,1.0,0.75,h0);
		head[0][4][4]=addBPoint(-0.5*0.5,1.0,0.75,h0);
		head[1][4][4]=addBPoint(-0.25*0.5,1.0,0.75,h0);
		head[2][4][4]=addBPoint(0.0,1.0,0.75,h0);
		head[3][4][4]=addBPoint(0.25*0.5,1.0,0.75,h0);
		head[4][4][4]=addBPoint(0.5*0.5,1.0,0.75,h0);

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




	

	



}	