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
	public static int ANIMAL_TYPE_MANHEAD=2;
	public static int ANIMAL_TYPE_MANHAND=3;
	
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

		body[0][0][0]=addBPoint(-0.5,-0.5,0.1,b0);
		body[1][0][0]=addBPoint(-0.25,-0.5,0.0,b0);
		body[2][0][0]=addBPoint(0.0,-0.5,0.0,b0);
		body[3][0][0]=addBPoint(0.25,-0.5,0.0,b0);
		body[4][0][0]=addBPoint(0.5,-0.5,0.1,b0);
		body[0][1][0]=addBPoint(-0.5,-0.25,0.1,b0);
		body[1][1][0]=addBPoint(-0.25,-0.25,0.0,b0);
		body[2][1][0]=addBPoint(0.0,-0.25,0.0,b0);
		body[3][1][0]=addBPoint(0.25,-0.25,0.0,b0);
		body[4][1][0]=addBPoint(0.5,-0.25,0.1,b0);	
		body[0][2][0]=addBPoint(-0.5,0.0,0.1,b0);
		body[1][2][0]=addBPoint(-0.25,0.0,0.0,b0);
		body[2][2][0]=addBPoint(0.0,0.0,0.0,b0);
		body[3][2][0]=addBPoint(0.25,0.0,0.0,b0);
		body[4][2][0]=addBPoint(0.5,0.0,0.1,b0);
		body[0][3][0]=addBPoint(-0.5,0.25,0.1,b0);
		body[1][3][0]=addBPoint(-0.25,0.25,0.0,b0);
		body[2][3][0]=addBPoint(0.0,0.25,0.0,b0);
		body[3][3][0]=addBPoint(0.25,0.25,0.0,b0);
		body[4][3][0]=addBPoint(0.5,0.25,0.1,b0);
		body[0][4][0]=addBPoint(-0.5,0.5,0.1,b0);
		body[1][4][0]=addBPoint(-0.25,0.5,0.0,b0);
		body[2][4][0]=addBPoint(0.0,0.5,0.0,b0);
		body[3][4][0]=addBPoint(0.25,0.5,0.0,b0);
		body[4][4][0]=addBPoint(0.5,0.5,0.1,b0);
		
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
		double sw=leg_side/y_side;
		
		body[0][0][3]=addBPoint(-sr,-sw*0.5,0.75,b0);
		body[1][0][3]=addBPoint(-sr*0.5,-0.5,0.75,b0);
		body[2][0][3]=addBPoint(0.0,-0.5,0.75,b0);
		body[3][0][3]=addBPoint(sr*0.5,-0.5,0.75,b0);
		body[4][0][3]=addBPoint(sr,-sw*0.5,0.75,b0);
		body[0][1][3]=addBPoint(-sr,-sw*0.25,0.75,b0);		
		body[4][1][3]=addBPoint(sr,-sw*0.25,0.75,b0);
		body[0][2][3]=addBPoint(-sr,0.0,0.75,b0);	
		body[4][2][3]=addBPoint(sr,0.0,0.75,b0);
		body[0][3][3]=addBPoint(-sr,sw*0.25,0.75,b0);		
		body[4][3][3]=addBPoint(sr,sw*0.25,0.75,b0);
		body[0][4][3]=addBPoint(-sr,sw*0.5,0.75,b0);
		body[1][4][3]=addBPoint(-sr*0.5,0.5,0.75,b0);
		body[2][4][3]=addBPoint(0.0,0.5,0.75,b0);
		body[3][4][3]=addBPoint(sr*0.5,0.5,0.75,b0);
		body[4][4][3]=addBPoint(sr,sw*0.5,0.75,b0);
		
		body[0][0][4]=addBPoint(-sr,-sw*0.5,1.0,b0);
		body[1][0][4]=addBPoint(-sr*0.5,-0.5,1.0,b0);
		body[2][0][4]=addBPoint(0.0,-0.5,1.0,b0);
		body[3][0][4]=addBPoint(sr*0.5,-0.5,1.0,b0);
		body[4][0][4]=addBPoint(sr,-sw*0.5,1.0,b0);
		body[0][1][4]=addBPoint(-sr,-sw*0.25,1.0,b0);		
		body[4][1][4]=addBPoint(sr,-sw*0.25,1.0,b0);
		body[0][2][4]=addBPoint(-sr,0.0,1.0,b0);		
		body[4][2][4]=addBPoint(sr,0.0,1.0,b0);
		body[0][3][4]=addBPoint(-sr,sw*0.25,1.0,b0);		
		body[4][3][4]=addBPoint(sr,sw*0.25,1.0,b0);
		body[0][4][4]=addBPoint(-sr,sw*0.5,1.0,b0);
		body[1][4][4]=addBPoint(-sr*0.5,0.5,1.0,b0);
		body[2][4][4]=addBPoint(0.0,0.5,1.0,b0);
		body[3][4][4]=addBPoint(sr*0.5,0.5,1.0,b0);
		body[4][4][4]=addBPoint(sr,sw*0.5,1.0,b0);
		
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
		
		
		BPoint[][][] pBackLeftLeg=new BPoint[2][2][2];

		pBackLeftLeg[0][0][0]=addBPoint(thigh_indentation,LEG_DY,0);
		pBackLeftLeg[1][0][0]=addBPoint(thigh_indentation+leg_side,LEG_DY,0);
		pBackLeftLeg[1][1][0]=addBPoint(thigh_indentation+leg_side,leg_side+LEG_DY,0);
		pBackLeftLeg[0][1][0]=addBPoint(thigh_indentation,leg_side+LEG_DY,0);

		addLine(pBackLeftLeg[0][0][0],pBackLeftLeg[0][1][0],pBackLeftLeg[1][1][0],pBackLeftLeg[1][0][0],Renderer3D.CAR_FRONT);


		pBackLeftLeg[0][0][1]=addBPoint(thigh_indentation,LEG_DY,shinbone_length);
		pBackLeftLeg[1][0][1]=addBPoint(thigh_indentation+leg_side,LEG_DY,shinbone_length);
		pBackLeftLeg[1][1][1]=addBPoint(thigh_indentation+leg_side,LEG_DY+leg_side,shinbone_length);
		pBackLeftLeg[0][1][1]=addBPoint(thigh_indentation,LEG_DY+leg_side,shinbone_length);


		addLine(pBackLeftLeg[0][0][0],pBackLeftLeg[0][0][1],pBackLeftLeg[0][1][1],pBackLeftLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pBackLeftLeg[0][1][0],pBackLeftLeg[0][1][1],pBackLeftLeg[1][1][1],pBackLeftLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(pBackLeftLeg[1][1][0],pBackLeftLeg[1][1][1],pBackLeftLeg[1][0][1],pBackLeftLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(pBackLeftLeg[1][0][0],pBackLeftLeg[1][0][1],pBackLeftLeg[0][0][1],pBackLeftLeg[0][0][0],Renderer3D.CAR_BACK);
		
		//left thigh
		
		
		BPoint[][][] pBackLeftThigh=new BPoint[2][2][1];

		
		pBackLeftThigh[0][0][0]=body[0][0][0];
		pBackLeftThigh[1][0][0]=body[1][0][0];
		pBackLeftThigh[1][1][0]=body[1][numy-1][0];
		pBackLeftThigh[0][1][0]=body[0][numy-1][0];

		addLine(pBackLeftLeg[0][0][1],pBackLeftThigh[0][0][0],pBackLeftThigh[0][1][0],pBackLeftLeg[0][1][1],Renderer3D.CAR_LEFT);

		addLine(pBackLeftLeg[0][1][1],pBackLeftThigh[0][1][0],pBackLeftThigh[1][1][0],pBackLeftLeg[1][1][1],Renderer3D.CAR_FRONT);

		addLine(pBackLeftLeg[1][1][1],pBackLeftThigh[1][1][0],pBackLeftThigh[1][0][0],pBackLeftLeg[1][0][1],Renderer3D.CAR_RIGHT);

		addLine(pBackLeftLeg[1][0][1],pBackLeftThigh[1][0][0],pBackLeftThigh[0][0][0],pBackLeftLeg[0][0][1],Renderer3D.CAR_BACK);


		///RightLeg
		
		BPoint[][][] pBackRightLeg=new BPoint[2][2][2];

		pBackRightLeg[0][0][0]=addBPoint(x_side-leg_side-thigh_indentation,LEG_DY,0);
		pBackRightLeg[1][0][0]=addBPoint(x_side-thigh_indentation,LEG_DY,0);
		pBackRightLeg[1][1][0]=addBPoint(x_side-thigh_indentation,LEG_DY+leg_side,0);
		pBackRightLeg[0][1][0]=addBPoint(x_side-leg_side-thigh_indentation,LEG_DY+leg_side,0);

		addLine(pBackRightLeg[0][0][0],pBackRightLeg[0][1][0],pBackRightLeg[1][1][0],pBackRightLeg[1][0][0],Renderer3D.CAR_FRONT);

		pBackRightLeg[0][0][1]=addBPoint(x_side-leg_side-thigh_indentation,LEG_DY,shinbone_length);
		pBackRightLeg[1][0][1]=addBPoint(x_side-thigh_indentation,LEG_DY,shinbone_length);
		pBackRightLeg[1][1][1]=addBPoint(x_side-thigh_indentation,LEG_DY+leg_side,shinbone_length);
		pBackRightLeg[0][1][1]=addBPoint(x_side-leg_side-thigh_indentation,LEG_DY+leg_side,shinbone_length);

		addLine(pBackRightLeg[0][0][0],pBackRightLeg[0][0][1],pBackRightLeg[0][1][1],pBackRightLeg[0][1][0],Renderer3D.CAR_LEFT);
	
		addLine(pBackRightLeg[0][1][0],pBackRightLeg[0][1][1],pBackRightLeg[1][1][1],pBackRightLeg[1][1][0],Renderer3D.CAR_FRONT);
	
		addLine(pBackRightLeg[1][1][0],pBackRightLeg[1][1][1],pBackRightLeg[1][0][1],pBackRightLeg[1][0][0],Renderer3D.CAR_RIGHT);
		
		addLine(pBackRightLeg[1][0][0],pBackRightLeg[1][0][1],pBackRightLeg[0][0][1],pBackRightLeg[0][0][0],Renderer3D.CAR_BACK);
	
		
		//right thigh
		
		BPoint[][][] pBackRightThigh=new BPoint[2][2][1];
		
		pBackRightThigh[0][0][0]=body[numx-2][0][0];
		pBackRightThigh[1][0][0]=body[numx-1][0][0];
		pBackRightThigh[1][1][0]=body[numx-1][numy-1][0];
		pBackRightThigh[0][1][0]=body[numx-2][numy-1][0];

		addLine(pBackRightLeg[0][0][1],pBackRightThigh[0][0][0],pBackRightThigh[0][1][0],pBackRightLeg[0][1][1],Renderer3D.CAR_LEFT);
	
		addLine(pBackRightLeg[0][1][1],pBackRightThigh[0][1][0],pBackRightThigh[1][1][0],pBackRightLeg[1][1][1],Renderer3D.CAR_FRONT);
	
		addLine(pBackRightLeg[1][1][1],pBackRightThigh[1][1][0],pBackRightThigh[1][0][0],pBackRightLeg[1][0][1],Renderer3D.CAR_RIGHT);
	
		addLine(pBackRightLeg[1][0][1],pBackRightThigh[1][0][0],pBackRightThigh[0][0][0],pBackRightLeg[0][0][1],Renderer3D.CAR_BACK);
	
		
		//Arms:
		
		//Left fore arm
		
		double ax=SHOULDER_DX+leg_side;
		double az=femur_length+shinbone_length+z_side-humerus_length-radius_length;
		double ay=(y_side-leg_side)/2.0;
		
		
		BPoint[][][] pFrontLeftForearm=new BPoint[2][2][2];
		
		pFrontLeftForearm[0][0][0]=addBPoint(-ax,ay,az);
		pFrontLeftForearm[1][0][0]=addBPoint(-ax+leg_side,ay,az);
		pFrontLeftForearm[1][1][0]=addBPoint(-ax+leg_side,ay+leg_side,az);
		pFrontLeftForearm[0][1][0]=addBPoint(-ax,ay+leg_side,az);

		
		addLine(pFrontLeftForearm[0][0][0],pFrontLeftForearm[0][1][0],pFrontLeftForearm[1][1][0],pFrontLeftForearm[1][0][0],Renderer3D.CAR_BOTTOM);

		
		pFrontLeftForearm[0][0][1]=addBPoint(-ax,ay,az+radius_length);
		pFrontLeftForearm[1][0][1]=addBPoint(-ax+leg_side,ay,az+radius_length);
		pFrontLeftForearm[1][1][1]=addBPoint(-ax+leg_side,ay+leg_side,az+radius_length);
		pFrontLeftForearm[0][1][1]=addBPoint(-ax,ay+leg_side,az+radius_length);
		

		
		addLine(pFrontLeftForearm[0][0][0],pFrontLeftForearm[0][0][1],pFrontLeftForearm[0][1][1],pFrontLeftForearm[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pFrontLeftForearm[0][1][0],pFrontLeftForearm[0][1][1],pFrontLeftForearm[1][1][1],pFrontLeftForearm[1][1][0],Renderer3D.CAR_FRONT);

		addLine(pFrontLeftForearm[1][1][0],pFrontLeftForearm[1][1][1],pFrontLeftForearm[1][0][1],pFrontLeftForearm[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(pFrontLeftForearm[1][0][0],pFrontLeftForearm[1][0][1],pFrontLeftForearm[0][0][1],pFrontLeftForearm[0][0][0],Renderer3D.CAR_BACK);
		
		
		//left arm
		
		BPoint[][][] pFrontLeftArm=new BPoint[2][2][2];
		
		pFrontLeftArm[0][0][0]=addBPoint(-ax,ay,body[0][0][3].z);
		pFrontLeftArm[1][0][0]=addBPoint(-ax+leg_side,ay,body[0][0][3].z);
		pFrontLeftArm[1][1][0]=addBPoint(-ax+leg_side,ay+leg_side,body[0][0][3].z);
		pFrontLeftArm[0][1][0]=addBPoint(-ax,ay+leg_side,body[0][0][3].z);
		
		
		pFrontLeftArm[0][0][1]=addBPoint(-ax,ay,body[0][0][4].z);
		pFrontLeftArm[1][0][1]=addBPoint(-ax+leg_side,ay,body[0][0][4].z);
		pFrontLeftArm[1][1][1]=addBPoint(-ax+leg_side,ay+leg_side,body[0][0][4].z);
		pFrontLeftArm[0][1][1]=addBPoint(-ax,ay+leg_side,body[0][0][4].z);
			
		addLine(pFrontLeftForearm[0][0][1],pFrontLeftArm[0][0][0],pFrontLeftArm[0][1][0],pFrontLeftForearm[0][1][1],Renderer3D.CAR_LEFT);

		addLine(pFrontLeftForearm[0][1][1],pFrontLeftArm[0][1][0],pFrontLeftArm[1][1][0],pFrontLeftForearm[1][1][1],Renderer3D.CAR_FRONT);

		addLine(pFrontLeftForearm[1][1][1],pFrontLeftArm[1][1][0],pFrontLeftArm[1][0][0],pFrontLeftForearm[1][0][1],Renderer3D.CAR_RIGHT);

		addLine(pFrontLeftForearm[1][0][1],pFrontLeftArm[1][0][0],pFrontLeftArm[0][0][0],pFrontLeftForearm[0][0][1],Renderer3D.CAR_BACK);
		
		
		addLine(pFrontLeftArm[0][0][1],pFrontLeftArm[1][0][1],pFrontLeftArm[1][1][1],pFrontLeftArm[0][1][1],Renderer3D.CAR_TOP);
		
		
		addLine(pFrontLeftArm[0][0][0],pFrontLeftArm[0][0][1],pFrontLeftArm[0][1][1],pFrontLeftArm[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pFrontLeftArm[0][1][0],pFrontLeftArm[0][1][1],pFrontLeftArm[1][1][1],pFrontLeftArm[1][1][0],Renderer3D.CAR_FRONT);

		addLine(pFrontLeftArm[1][1][0],pFrontLeftArm[1][1][1],pFrontLeftArm[1][0][1],pFrontLeftArm[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(pFrontLeftArm[1][0][0],pFrontLeftArm[1][0][1],pFrontLeftArm[0][0][1],pFrontLeftArm[0][0][0],Renderer3D.CAR_BACK);
	
		
		//Right forearm
		
		
		BPoint[][][] pFrontRightForearm=new BPoint[2][2][2];
		
		pFrontRightForearm[0][0][0]=addBPoint(ax+x_side-leg_side,ay,az);
		pFrontRightForearm[1][0][0]=addBPoint(ax+x_side,ay,az);
		pFrontRightForearm[1][1][0]=addBPoint(ax+x_side,ay+leg_side,az);
		pFrontRightForearm[0][1][0]=addBPoint(ax+x_side-leg_side,ay+leg_side,az);

		addLine(pFrontRightForearm[0][0][0],pFrontRightForearm[0][1][0],pFrontRightForearm[1][1][0],pFrontRightForearm[1][0][0],Renderer3D.CAR_BOTTOM);

		
		pFrontRightForearm[0][0][1]=addBPoint(ax+x_side-leg_side,ay,az+radius_length);
		pFrontRightForearm[1][0][1]=addBPoint(ax+x_side,ay,az+radius_length);
		pFrontRightForearm[1][1][1]=addBPoint(ax+x_side,ay+leg_side,az+radius_length);
		pFrontRightForearm[0][1][1]=addBPoint(ax+x_side-leg_side,ay+leg_side,az+radius_length);

		
		addLine(pFrontRightForearm[0][0][0],pFrontRightForearm[0][0][1],pFrontRightForearm[0][1][1],pFrontRightForearm[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pFrontRightForearm[0][1][0],pFrontRightForearm[0][1][1],pFrontRightForearm[1][1][1],pFrontRightForearm[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(pFrontRightForearm[1][1][0],pFrontRightForearm[1][1][1],pFrontRightForearm[1][0][1],pFrontRightForearm[1][0][0],Renderer3D.CAR_RIGHT);
	
		addLine(pFrontRightForearm[1][0][0],pFrontRightForearm[1][0][1],pFrontRightForearm[0][0][1],pFrontRightForearm[0][0][0],Renderer3D.CAR_BACK);

		
		//right arm
		
		BPoint[][][] pFrontRightArm=new BPoint[2][2][2];
		
		pFrontRightArm[0][0][0]=addBPoint(ax+x_side-leg_side,ay,body[0][0][3].z);
		pFrontRightArm[1][0][0]=addBPoint(ax+x_side,ay,body[0][0][3].z);
		pFrontRightArm[1][1][0]=addBPoint(ax+x_side,ay+leg_side,body[0][0][3].z);
		pFrontRightArm[0][1][0]=addBPoint(ax+x_side-leg_side,ay+leg_side,body[0][0][3].z);
		
		
		pFrontRightArm[0][0][1]=addBPoint(ax+x_side-leg_side,ay,body[0][0][4].z);
		pFrontRightArm[1][0][1]=addBPoint(ax+x_side,ay,body[0][0][4].z);
		pFrontRightArm[1][1][1]=addBPoint(ax+x_side,ay+leg_side,body[0][0][4].z);
		pFrontRightArm[0][1][1]=addBPoint(ax+x_side-leg_side,ay+leg_side,body[0][0][4].z);
					
		addLine(pFrontRightForearm[0][0][1],pFrontRightArm[0][0][0],pFrontRightArm[0][1][0],pFrontRightForearm[0][1][1],Renderer3D.CAR_LEFT);

		addLine(pFrontRightForearm[0][1][1],pFrontRightArm[0][1][0],pFrontRightArm[1][1][0],pFrontRightForearm[1][1][1],Renderer3D.CAR_FRONT);
	
		addLine(pFrontRightForearm[1][1][1],pFrontRightArm[1][1][0],pFrontRightArm[1][0][0],pFrontRightForearm[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(pFrontRightForearm[1][0][1],pFrontRightArm[1][0][0],pFrontRightArm[0][0][0],pFrontRightForearm[0][0][1],Renderer3D.CAR_BACK);
		
		
		addLine(pFrontRightArm[0][0][1],pFrontRightArm[1][0][1],pFrontRightArm[1][1][1],pFrontRightArm[0][1][1],Renderer3D.CAR_TOP);
		
		
		addLine(pFrontRightArm[0][0][0],pFrontRightArm[0][0][1],pFrontRightArm[0][1][1],pFrontRightArm[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pFrontRightArm[0][1][0],pFrontRightArm[0][1][1],pFrontRightArm[1][1][1],pFrontRightArm[1][1][0],Renderer3D.CAR_FRONT);
	
		addLine(pFrontRightArm[1][1][0],pFrontRightArm[1][1][1],pFrontRightArm[1][0][1],pFrontRightArm[1][0][0],Renderer3D.CAR_RIGHT);
		
		addLine(pFrontRightArm[1][0][0],pFrontRightArm[1][0][1],pFrontRightArm[0][0][1],pFrontRightArm[0][0][0],Renderer3D.CAR_BACK);
		

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
		head[1][0][0]=addBPoint(-e3.x,-e3.y*0.5,0.0,n0);
		head[2][0][0]=addBPoint(e4.x,-e4.y*0.5,0.0,n0);
		head[3][0][0]=addBPoint(e3.x,-e3.y*0.5,0.0,n0);
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
		head[1][1][4]=addBPoint(-e1.x*0.5*0.75,-e1.y*0.75,1.0,h0);
		head[2][1][4]=addBPoint(0.0,-e1.y*0.75,1.0,h0);
		head[3][1][4]=addBPoint(e1.x*0.5*0.75,-e1.y*0.75,1.0,h0);
		head[4][1][4]=addBPoint(e1.x*0.75,-e1.y*0.75,1.0,h0);
		head[0][2][4]=addBPoint(-e0.x*0.75,e0.y,1.0,h0);
		head[1][2][4]=addBPoint(-e0.x*0.5*0.75,e0.y,1.0,h0);
		head[2][2][4]=addBPoint(0.0,e0.y,1.0,h0);
		head[3][2][4]=addBPoint(e0.x*0.5*0.75,e0.y,1.0,h0);
		head[4][2][4]=addBPoint(e0.x*0.75,e0.y,1.0,h0);
		head[0][3][4]=addBPoint(-e1.x*0.75,e1.y*0.75,1.0,h0);
		head[1][3][4]=addBPoint(-e1.x*0.5*0.75,e1.y*0.75,1.0,h0);
		head[2][3][4]=addBPoint(0.0,e1.y*0.75,1.0,h0);
		head[3][3][4]=addBPoint(e1.x*0.5*0.75,e1.y*0.75,1.0,h0);
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
		
		Segments pb0=new Segments(xc,x_side,0,y_side,femur_length+shinbone_length,z_side);
		Segments pf0=new Segments(xc,x_side,0,y_side,radius_length+humerus_length,z_side+dz);

		body[0][0][0]=addBPoint(-0.5,0.0,0.4,pb0);
		body[1][0][0]=addBPoint(-0.25,0.0,0.0,pb0);
		body[2][0][0]=addBPoint(0.0,0.0,0.0,pb0);
		body[3][0][0]=addBPoint(0.25,0.0,0.0,pb0);
		body[4][0][0]=addBPoint(0.5,0.0,0.4,pb0);
		body[0][0][1]=addBPoint(-0.5,0.0,0.45,pb0);	
		body[1][0][1]=addBPoint(-0.25,0.0,0.25,pb0);
		body[2][0][1]=addBPoint(0.0,0.0,0.25,pb0);
		body[3][0][1]=addBPoint(0.25,0.0,0.25,pb0);
		body[4][0][1]=addBPoint(0.5,0.0,0.45,pb0);
		body[0][0][2]=addBPoint(-0.5,0.0,0.5,pb0);	
		body[1][0][2]=addBPoint(-0.25,0.0,0.5,pb0);
		body[2][0][2]=addBPoint(0.0,0.0,0.5,pb0);
		body[3][0][2]=addBPoint(0.25,0.0,0.5,pb0);
		body[4][0][2]=addBPoint(0.5,0.0,0.5,pb0);
		body[0][0][3]=addBPoint(-0.5,0.0,0.55,pb0);	
		body[1][0][3]=addBPoint(-0.25,0.0,0.75,pb0);
		body[2][0][3]=addBPoint(0.0,0.0,0.75,pb0);
		body[3][0][3]=addBPoint(0.25,0.0,0.75,pb0);
		body[4][0][3]=addBPoint(0.5,0.0,0.55,pb0);
		body[0][0][4]=addBPoint(-0.5,0.0,0.7,pb0);	
		body[1][0][4]=addBPoint(-0.25,0.0,1.0,pb0);
		body[2][0][4]=addBPoint(0.0,0.0,1.0,pb0);
		body[3][0][4]=addBPoint(0.25,0.0,1.0,pb0);
		body[4][0][4]=addBPoint(0.5,0.0,0.7,pb0);
		
		body[0][1][0]=addBPoint(-0.5,0.25,0.4,pb0);
		body[1][1][0]=addBPoint(-0.25,0.25,0.0,pb0);
		body[2][1][0]=addBPoint(0.0,0.25,0.0,pb0);
		body[3][1][0]=addBPoint(0.25,0.25,0.0,pb0);
		body[4][1][0]=addBPoint(0.5,0.25,0.4,pb0);	
		body[0][1][1]=addBPoint(-0.5,0.25,0.45,pb0);	
		body[1][1][1]=addBPoint(-0.25,0.25,0.25,pb0);
		body[2][1][1]=addBPoint(0.0,0.25,0.25,pb0);
		body[3][1][1]=addBPoint(0.25,0.25,0.25,pb0);
		body[4][1][1]=addBPoint(0.5,0.25,0.45,pb0);
		body[0][1][2]=addBPoint(-0.5,0.25,0.5,pb0);	
		body[1][1][2]=addBPoint(-0.25,0.25,0.5,pb0);
		body[2][1][2]=addBPoint(0.0,0.25,0.5,pb0);
		body[3][1][2]=addBPoint(0.25,0.25,0.5,pb0);
		body[4][1][2]=addBPoint(0.5,0.25,0.5,pb0);
		body[0][1][3]=addBPoint(-0.5,0.25,0.55,pb0);	
		body[1][1][3]=addBPoint(-0.25,0.25,0.75,pb0);
		body[2][1][3]=addBPoint(0.0,0.25,0.75,pb0);
		body[3][1][3]=addBPoint(0.25,0.25,0.75,pb0);
		body[4][1][3]=addBPoint(0.5,0.25,0.55,pb0);
		body[0][1][4]=addBPoint(-0.5,0.25,0.7,pb0);	
		body[1][1][4]=addBPoint(-0.25,0.25,1.0,pb0);
		body[2][1][4]=addBPoint(0.0,0.25,1.0,pb0);
		body[3][1][4]=addBPoint(0.25,0.25,1.0,pb0);
		body[4][1][4]=addBPoint(0.5,0.25,0.7,pb0);
		
		body[0][2][0]=addBPoint(-0.5,0.5,0.4,pb0);
		body[1][2][0]=addBPoint(-0.25,0.5,0.0,pb0);
		body[2][2][0]=addBPoint(0.0,0.5,0.0,pb0);
		body[3][2][0]=addBPoint(0.25,0.5,0.0,pb0);
		body[4][2][0]=addBPoint(0.5,0.5,0.4,pb0);
		body[0][2][1]=addBPoint(-0.5,0.5,0.45,pb0);	
		body[1][2][1]=addBPoint(-0.25,0.5,0.25,pb0);
		body[2][2][1]=addBPoint(0.0,0.5,0.25,pb0);
		body[3][2][1]=addBPoint(0.25,0.5,0.25,pb0);
		body[4][2][1]=addBPoint(0.5,0.5,0.45,pb0);
		body[0][2][2]=addBPoint(-0.5,0.5,0.5,pb0);	
		body[1][2][2]=addBPoint(-0.25,0.5,0.5,pb0);
		body[2][2][2]=addBPoint(0.0,0.5,0.5,pb0);
		body[3][2][2]=addBPoint(0.25,0.5,0.5,pb0);
		body[4][2][2]=addBPoint(0.5,0.5,0.5,pb0);
		body[0][2][3]=addBPoint(-0.5,0.5,0.55,pb0);	
		body[1][2][3]=addBPoint(-0.25,0.5,0.75,pb0);
		body[2][2][3]=addBPoint(0.0,0.5,0.75,pb0);
		body[3][2][3]=addBPoint(0.25,0.5,0.75,pb0);
		body[4][2][3]=addBPoint(0.5,0.5,0.55,pb0);
		body[0][2][4]=addBPoint(-0.5,0.5,0.7,pb0);	
		body[1][2][4]=addBPoint(-0.25,0.5,1.0,pb0);
		body[2][2][4]=addBPoint(0.0,0.5,1.0,pb0);
		body[3][2][4]=addBPoint(0.25,0.5,1.0,pb0);
		body[4][2][4]=addBPoint(0.5,0.5,0.7,pb0);
		
		body[0][3][0]=addBPoint(-0.5,0.75,0.4,pf0);
		body[1][3][0]=addBPoint(-0.25,0.75,0.0,pf0);
		body[2][3][0]=addBPoint(0.0,0.75,0.0,pf0);	
		body[3][3][0]=addBPoint(0.25,0.75,0.0,pf0);
		body[4][3][0]=addBPoint(0.5,0.75,0.4,pf0);
		body[0][3][1]=addBPoint(-0.5,0.75,0.45,pf0);		
		body[1][3][1]=addBPoint(-0.25,0.75,0.25,pf0);
		body[2][3][1]=addBPoint(0.0,0.75,0.25,pf0);
		body[3][3][1]=addBPoint(0.25,0.75,0.25,pf0);
		body[4][3][1]=addBPoint(0.5,0.75,0.45,pf0);
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
		
		body[0][4][0]=addBPoint(-0.5,1.0,0.4,pf0);
		body[1][4][0]=addBPoint(-0.25,1.0,0.0,pf0);	
		body[2][4][0]=addBPoint(0.0,1.0,0.0,pf0);	
		body[3][4][0]=addBPoint(0.25,1.0,0.0,pf0);		
		body[4][4][0]=addBPoint(0.5,1.0,0.4,pf0);	
		body[0][4][1]=addBPoint(-0.5,1.0,0.45,pf0);		
		body[1][4][1]=addBPoint(-0.25,1.0,0.25,pf0);
		body[2][4][1]=addBPoint(0.0,1.0,0.25,pf0);
		body[3][4][1]=addBPoint(0.25,1.0,0.25,pf0);
		body[4][4][1]=addBPoint(0.5,1.0,0.45,pf0);
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
		
		
		BPoint[][][] pBackLeftLeg=new BPoint[2][2][2];
		
		pBackLeftLeg[0][0][0]=addBPoint(0,0,0);
		pBackLeftLeg[1][0][0]=addBPoint(leg_side,0,0);
		pBackLeftLeg[1][1][0]=addBPoint(leg_side,leg_side,0);
		pBackLeftLeg[0][1][0]=addBPoint(0,leg_side,0);

		
		LineData backLeftLeg=addLine(pBackLeftLeg[0][0][0],pBackLeftLeg[0][1][0],pBackLeftLeg[1][1][0],pBackLeftLeg[1][0][0],Renderer3D.CAR_FRONT);
		
		pBackLeftLeg[0][0][1]=addBPoint(0,0,shinbone_length);
		pBackLeftLeg[1][0][1]=addBPoint(leg_side,0,shinbone_length);
		pBackLeftLeg[1][1][1]=addBPoint(leg_side,leg_side,shinbone_length);
		pBackLeftLeg[0][1][1]=addBPoint(0,leg_side,shinbone_length);
		
		LineData backLeftLegS0=addLine(pBackLeftLeg[0][0][0],pBackLeftLeg[0][0][1],pBackLeftLeg[0][1][1],pBackLeftLeg[0][1][0],Renderer3D.CAR_LEFT);

		LineData backLeftLegS1=addLine(pBackLeftLeg[0][1][0],pBackLeftLeg[0][1][1],pBackLeftLeg[1][1][1],pBackLeftLeg[1][1][0],Renderer3D.CAR_FRONT);
	
		LineData backLeftLegS2=addLine(pBackLeftLeg[1][1][0],pBackLeftLeg[1][1][1],pBackLeftLeg[1][0][1],pBackLeftLeg[1][0][0],Renderer3D.CAR_RIGHT);
	
		LineData backLeftLegS3=addLine(pBackLeftLeg[1][0][0],pBackLeftLeg[1][0][1],pBackLeftLeg[0][0][1],pBackLeftLeg[0][0][0],Renderer3D.CAR_BACK);
	
		
		//back left thigh
		
		BPoint[][][] pBackLeftThigh=new BPoint[2][2][1];
		
		pBackLeftThigh[0][0][0]=body[0][0][0];
		pBackLeftThigh[1][0][0]=body[1][0][0];
		pBackLeftThigh[1][1][0]=body[1][1][0];
		pBackLeftThigh[0][1][0]=body[0][1][0];

		LineData backLeftThighS0=addLine(pBackLeftLeg[0][0][1],pBackLeftThigh[0][0][0],pBackLeftThigh[0][1][0],pBackLeftLeg[0][1][1],Renderer3D.CAR_LEFT);
	
		LineData backLeftThighS1=addLine(pBackLeftLeg[0][1][1],pBackLeftThigh[0][1][0],pBackLeftThigh[1][1][0],pBackLeftLeg[1][1][1],Renderer3D.CAR_FRONT);
	
		LineData backLeftThighS2=addLine(pBackLeftLeg[1][1][1],pBackLeftThigh[1][1][0],pBackLeftThigh[1][0][0],pBackLeftLeg[1][0][1],Renderer3D.CAR_RIGHT);

		LineData backLeftThighS3=addLine(pBackLeftLeg[1][0][1],pBackLeftThigh[1][0][0],pBackLeftThigh[0][0][0],pBackLeftLeg[0][0][1],Renderer3D.CAR_BACK);

		//LineData backLeftTopThigh=addLine(pBackLeftThigh[0][0][0],pBackLeftThigh[1][0][0],pBackLeftThigh[1][1][0],pBackLeftThigh[0][1][0],Renderer3D.CAR_TOP);
		//backRightLeg
		
		
		BPoint[][][] pBackRightLeg=new BPoint[2][2][2];
		
		pBackRightLeg[0][0][0]=addBPoint(x_side-leg_side,0,0);
		pBackRightLeg[1][0][0]=addBPoint(x_side,0,0);
		pBackRightLeg[1][1][0]=addBPoint(x_side,leg_side,0);
		pBackRightLeg[0][1][0]=addBPoint(x_side-leg_side,leg_side,0);
		
		LineData backRightLeg=addLine(pBackRightLeg[0][0][0],pBackRightLeg[0][1][0],pBackRightLeg[1][1][0],pBackRightLeg[1][0][0],Renderer3D.CAR_FRONT);


		
		pBackRightLeg[0][0][1]=addBPoint(x_side-leg_side,0,shinbone_length);
		pBackRightLeg[1][0][1]=addBPoint(x_side,0,shinbone_length);
		pBackRightLeg[1][1][1]=addBPoint(x_side,leg_side,shinbone_length);
		pBackRightLeg[0][1][1]=addBPoint(x_side-leg_side,leg_side,shinbone_length);

		
		LineData backRightLegS0=addLine(pBackRightLeg[0][0][0],pBackRightLeg[0][0][1],pBackRightLeg[0][1][1],pBackRightLeg[0][1][0],Renderer3D.CAR_LEFT);
	
		LineData backRightLegS1=addLine(pBackRightLeg[0][1][0],pBackRightLeg[0][1][1],pBackRightLeg[1][1][1],pBackRightLeg[1][1][0],Renderer3D.CAR_FRONT);
	
		LineData backRightLegS2=addLine(pBackRightLeg[1][1][0],pBackRightLeg[1][1][1],pBackRightLeg[1][0][1],pBackRightLeg[1][0][0],Renderer3D.CAR_RIGHT);
	
		LineData backRightLegS3=addLine(pBackRightLeg[1][0][0],pBackRightLeg[1][0][1],pBackRightLeg[0][0][1],pBackRightLeg[0][0][0],Renderer3D.CAR_BACK);
	
		
		//back right thigh
		
		
		BPoint[][][] pBackRightThigh=new BPoint[2][2][1];
		
		pBackRightThigh[0][0][0]=body[numx-2][0][0];
		pBackRightThigh[1][0][0]=body[numx-1][0][0];
		pBackRightThigh[1][1][0]=body[numx-1][1][0];
		pBackRightThigh[0][1][0]=body[numx-2][1][0];

		LineData backRightThighS0=addLine(pBackRightLeg[0][0][1],pBackRightThigh[0][0][0],pBackRightThigh[0][1][0],pBackRightLeg[0][1][1],Renderer3D.CAR_LEFT);
	
		LineData backRightThighS1=addLine(pBackRightLeg[0][1][1],pBackRightThigh[0][1][0],pBackRightThigh[1][1][0],pBackRightLeg[1][1][1],Renderer3D.CAR_FRONT);

		LineData backRightThighS2=addLine(pBackRightLeg[1][1][1],pBackRightThigh[1][1][0],pBackRightThigh[1][0][0],pBackRightLeg[1][0][1],Renderer3D.CAR_RIGHT);
	
		LineData backRightThighS3=addLine(pBackRightLeg[1][0][1],pBackRightThigh[1][0][0],pBackRightThigh[0][0][0],pBackRightLeg[0][0][1],Renderer3D.CAR_BACK);

		//LineData backRightTopThigh=addLine(pBackRightThigh[0][0][0],pBackRightThigh[1][0][0],pBackRightThigh[1][1][0],pBackRightThigh[0][1][0],Renderer3D.CAR_TOP);
		
		//frontLeftLeg
		
		BPoint[][][] pFrontLeftForearm=new BPoint[2][2][2];
		
		pFrontLeftForearm[0][0][0]=addBPoint(0,y_side-leg_side,0);
		pFrontLeftForearm[1][0][0]=addBPoint(leg_side,y_side-leg_side,0);
		pFrontLeftForearm[1][1][0]=addBPoint(leg_side,y_side,0);
		pFrontLeftForearm[0][1][0]=addBPoint(0,y_side,0);
		
		LineData FrontLeftForearm=addLine(pFrontLeftForearm[0][0][0],pFrontLeftForearm[0][1][0],pFrontLeftForearm[1][1][0],pFrontLeftForearm[1][0][0],Renderer3D.CAR_FRONT);

		
		pFrontLeftForearm[0][0][1]=addBPoint(0,y_side-leg_side,radius_length);
		pFrontLeftForearm[1][0][1]=addBPoint(leg_side,y_side-leg_side,radius_length);
		pFrontLeftForearm[1][1][1]=addBPoint(leg_side,y_side,radius_length);
		pFrontLeftForearm[0][1][1]=addBPoint(0,y_side,radius_length);
		
		LineData FrontLeftForearmS0=addLine(pFrontLeftForearm[0][0][0],pFrontLeftForearm[0][0][1],pFrontLeftForearm[0][1][1],pFrontLeftForearm[0][1][0],Renderer3D.CAR_LEFT);
	
		LineData FrontLeftForearmS1=addLine(pFrontLeftForearm[0][1][0],pFrontLeftForearm[0][1][1],pFrontLeftForearm[1][1][1],pFrontLeftForearm[1][1][0],Renderer3D.CAR_FRONT);

		LineData FrontLeftForearmS2=addLine(pFrontLeftForearm[1][1][0],pFrontLeftForearm[1][1][1],pFrontLeftForearm[1][0][1],pFrontLeftForearm[1][0][0],Renderer3D.CAR_RIGHT);
	
		LineData FrontLeftForearmS3=addLine(pFrontLeftForearm[1][0][0],pFrontLeftForearm[1][0][1],pFrontLeftForearm[0][0][1],pFrontLeftForearm[0][0][0],Renderer3D.CAR_BACK);
	
		
		//left arm
		
		
		BPoint[][][] pFrontLeftArm=new BPoint[2][2][1];
		
		pFrontLeftArm[0][0][0]=body[0][numy-2][0];
		pFrontLeftArm[1][0][0]=body[1][numy-2][0];
		pFrontLeftArm[1][1][0]=body[1][numy-1][0];
		pFrontLeftArm[0][1][0]=body[0][numy-1][0];
		
		LineData topLeftArm=addLine(pFrontLeftArm[0][0][0],pFrontLeftArm[1][0][0],pFrontLeftArm[1][1][0],pFrontLeftArm[0][1][0],Renderer3D.CAR_TOP);
	
		
		LineData FrontLeftArmS0=addLine(pFrontLeftForearm[0][0][1],pFrontLeftArm[0][0][0],pFrontLeftArm[0][1][0],pFrontLeftForearm[0][1][1],Renderer3D.CAR_LEFT);

		LineData FrontLeftArmS1=addLine(pFrontLeftForearm[0][1][1],pFrontLeftArm[0][1][0],pFrontLeftArm[1][1][0],pFrontLeftForearm[1][1][1],Renderer3D.CAR_FRONT);
	
		LineData FrontLeftArmS2=addLine(pFrontLeftForearm[1][1][1],pFrontLeftArm[1][1][0],pFrontLeftArm[1][0][0],pFrontLeftForearm[1][0][1],Renderer3D.CAR_RIGHT);

		LineData FrontLeftArmS3=addLine(pFrontLeftForearm[1][0][1],pFrontLeftArm[1][0][0],pFrontLeftArm[0][0][0],pFrontLeftForearm[0][0][1],Renderer3D.CAR_BACK);
	
		
		//frontRightLeg
		
		BPoint[][][] pFrontRightForearm=new BPoint[2][2][2];
		
		pFrontRightForearm[0][0][0]=addBPoint(x_side-leg_side,y_side-leg_side,0);
		pFrontRightForearm[1][0][0]=addBPoint(x_side,y_side-leg_side,0);
		pFrontRightForearm[1][1][0]=addBPoint(x_side,y_side,0);
		pFrontRightForearm[0][1][0]=addBPoint(x_side-leg_side,y_side,0);
		
		LineData FrontRightForearm=addLine(pFrontRightForearm[0][0][0],pFrontRightForearm[0][1][0],pFrontRightForearm[1][1][0],pFrontRightForearm[1][0][0],Renderer3D.CAR_FRONT);
	

		
		pFrontRightForearm[0][0][1]=addBPoint(x_side-leg_side,y_side-leg_side,radius_length);
		pFrontRightForearm[1][0][1]=addBPoint(x_side,y_side-leg_side,radius_length);
		pFrontRightForearm[1][1][1]=addBPoint(x_side,y_side,radius_length);
		pFrontRightForearm[0][1][1]=addBPoint(x_side-leg_side,y_side,radius_length);

		
		LineData frontRightForearmS0=addLine(pFrontRightForearm[0][0][0],pFrontRightForearm[0][0][1],pFrontRightForearm[0][1][1],pFrontRightForearm[0][1][0],Renderer3D.CAR_LEFT);

		LineData frontRightForearmS1=addLine(pFrontRightForearm[0][1][0],pFrontRightForearm[0][1][1],pFrontRightForearm[1][1][1],pFrontRightForearm[1][1][0],Renderer3D.CAR_FRONT);

		LineData frontRightForearmS2=addLine(pFrontRightForearm[1][1][0],pFrontRightForearm[1][1][1],pFrontRightForearm[1][0][1],pFrontRightForearm[1][0][0],Renderer3D.CAR_RIGHT);
	
		LineData frontRightForearmS3=addLine(pFrontRightForearm[1][0][0],pFrontRightForearm[1][0][1],pFrontRightForearm[0][0][1],pFrontRightForearm[0][0][0],Renderer3D.CAR_BACK);
	
		

		//right arm
		
		BPoint[][][] pFrontRightArm=new BPoint[2][2][1];
		
		pFrontRightArm[0][0][0]=body[numx-2][numy-2][0];
		pFrontRightArm[1][0][0]=body[numx-1][numy-2][0];
		pFrontRightArm[1][1][0]=body[numx-1][numy-1][0];
		pFrontRightArm[0][1][0]=body[numx-2][numy-1][0];
		
		LineData topRightArm=addLine(pFrontRightArm[0][0][0],pFrontRightArm[1][0][0],pFrontRightArm[1][1][0],pFrontRightArm[0][1][0],Renderer3D.CAR_TOP);	
		
		LineData frontRightArmS0=addLine(pFrontRightForearm[0][0][1],pFrontRightArm[0][0][0],pFrontRightArm[0][1][0],pFrontRightForearm[0][1][1],Renderer3D.CAR_LEFT);
	
		LineData frontRightArmS1=addLine(pFrontRightForearm[0][1][1],pFrontRightArm[0][1][0],pFrontRightArm[1][1][0],pFrontRightForearm[1][1][1],Renderer3D.CAR_FRONT);
	
		LineData frontRightArmS2=addLine(pFrontRightForearm[1][1][1],pFrontRightArm[1][1][0],pFrontRightArm[1][0][0],pFrontRightForearm[1][0][1],Renderer3D.CAR_RIGHT);
	
		LineData frontRightArmS3=addLine(pFrontRightForearm[1][0][1],pFrontRightArm[1][0][0],pFrontRightArm[0][0][0],pFrontRightForearm[0][0][1],Renderer3D.CAR_BACK);
	
	
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




	private PolygonMesh buildManHeadMesh() {
		points=new Vector();
		points.setSize(400);

		polyData=new Vector();

		n=0;
		
		double xc=x_side/2.0;
		double yc=y_side/2.0;
	
		Segments n0=new Segments(xc,head_DX*0.5,yc,head_DY,0,head_DZ);
		Segments h0=new Segments(xc,head_DX*0.5,yc,head_DY*0.5,0,head_DZ);
		
		
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
		head[1][0][0]=addBPoint(-e3.x,-e3.y*0.5,0.0,n0);
		head[2][0][0]=addBPoint(e4.x,-e4.y*0.5,0.0,n0);
		head[3][0][0]=addBPoint(e3.x,-e3.y*0.5,0.0,n0);
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
		head[1][1][4]=addBPoint(-e1.x*0.5*0.75,-e1.y*0.75,1.0,h0);
		head[2][1][4]=addBPoint(0.0,-e1.y*0.75,1.0,h0);
		head[3][1][4]=addBPoint(e1.x*0.5*0.75,-e1.y*0.75,1.0,h0);
		head[4][1][4]=addBPoint(e1.x*0.75,-e1.y*0.75,1.0,h0);	
		head[0][2][4]=addBPoint(-e0.x*0.75,e0.y*0.75,1.0,h0);
		head[1][2][4]=addBPoint(-e0.x*0.5*0.75,e0.y*0.75,1.0,h0);
		head[2][2][4]=addBPoint(0.0,e0.y*0.75,1.0,h0);
		head[3][2][4]=addBPoint(e0.x*0.5*0.75,e0.y*0.75,1.0,h0);
		head[4][2][4]=addBPoint(e0.x*0.75,e0.y*0.75,1.0,h0);			
		head[0][3][4]=addBPoint(-e1.x*0.75,e1.y*0.75,1.0,h0);
		head[1][3][4]=addBPoint(-e1.x*0.5*0.75,e1.y*0.75,1.0,h0);
		head[2][3][4]=addBPoint(0.0,e1.y*0.75,1.0,h0);
		head[3][3][4]=addBPoint(e1.x*0.5*0.75,e1.y*0.75,1.0,h0);
		head[4][3][4]=addBPoint(e1.x*0.75,e1.y*0.75,1.0,h0);		
		head[0][4][4]=addBPoint(-e2.x*0.75,e2.y*0.75,1.0,h0);
		head[1][4][4]=addBPoint(-e3.x*0.75,e3.y*0.75,1.0,h0);
		head[2][4][4]=addBPoint(e4.x*0.75,e4.y*0.75,1.0,h0);
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
		
		Segments p0=new Segments(xc,x_side,0,y_side,0,z_side);

		palm[0][0][0]=addBPoint(0.0,0,0,p0);
		palm[1][0][0]=addBPoint(0.125,0.0,0,p0);
		palm[2][0][0]=addBPoint(0.25,0.0,0,p0);
		palm[3][0][0]=addBPoint(0.375,0.0,0,p0);
		palm[4][0][0]=addBPoint(0.5,0.0,0,p0);
		palm[5][0][0]=addBPoint(0.625,0.0,0,p0);
		palm[6][0][0]=addBPoint(0.75,0.0,0,p0);
		palm[7][0][0]=addBPoint(0.875,0.0,0,p0);
		palm[8][0][0]=addBPoint(1.0,0.0,0,p0);
		palm[0][1][0]=addBPoint(0.0,0.25,0,p0);
		palm[1][1][0]=addBPoint(0.125,0.25,0,p0);
		palm[2][1][0]=addBPoint(0.25,0.25,0,p0);
		palm[3][1][0]=addBPoint(0.375,0.25,0,p0);
		palm[4][1][0]=addBPoint(0.5,0.25,0,p0);
		palm[5][1][0]=addBPoint(0.625,0.25,0,p0);
		palm[6][1][0]=addBPoint(0.75,0.25,0,p0);
		palm[7][1][0]=addBPoint(0.875,0.25,0,p0);
		palm[8][1][0]=addBPoint(1.0,0.25,0,p0);
		palm[0][2][0]=addBPoint(0.0,0.5,0,p0);
		palm[1][2][0]=addBPoint(0.125,0.5,0,p0);
		palm[2][2][0]=addBPoint(0.25,0.5,0,p0);
		palm[3][2][0]=addBPoint(0.375,0.5,0,p0);
		palm[4][2][0]=addBPoint(0.5,0.5,0,p0);
		palm[5][2][0]=addBPoint(0.625,0.5,0,p0);
		palm[6][2][0]=addBPoint(0.75,0.5,0,p0);
		palm[7][2][0]=addBPoint(0.875,0.5,0,p0);
		palm[8][2][0]=addBPoint(1.0,0.5,0,p0);
		palm[0][3][0]=addBPoint(0.0,0.75,0,p0);
		palm[1][3][0]=addBPoint(0.125,0.75,0,p0);
		palm[2][3][0]=addBPoint(0.25,0.75,0,p0);
		palm[3][3][0]=addBPoint(0.375,0.75,0,p0);
		palm[4][3][0]=addBPoint(0.5,0.75,0,p0);
		palm[5][3][0]=addBPoint(0.625,0.75,0,p0);
		palm[6][3][0]=addBPoint(0.75,0.75,0,p0);
		palm[7][3][0]=addBPoint(0.875,0.75,0,p0);
		palm[8][3][0]=addBPoint(1.0,0.75,0,p0);
		palm[0][4][0]=addBPoint(0.0,1.0,0,p0);
		palm[1][4][0]=addBPoint(0.125,1.0,0,p0);
		palm[2][4][0]=addBPoint(0.25,1.0,0,p0);
		palm[3][4][0]=addBPoint(0.375,1.0,0,p0);
		palm[4][4][0]=addBPoint(0.5,1.0,0,p0);
		palm[5][4][0]=addBPoint(0.625,1.0,0,p0);
		palm[6][4][0]=addBPoint(0.75,1.0,0,p0);
		palm[7][4][0]=addBPoint(0.875,1.0,0,p0);
		palm[8][4][0]=addBPoint(1.0,1.0,0,p0);
		
		palm[0][0][1]=addBPoint(0.0,0,1.0,p0);
		palm[1][0][1]=addBPoint(0.125,0.0,1.0,p0);
		palm[2][0][1]=addBPoint(0.25,0.0,1.0,p0);
		palm[3][0][1]=addBPoint(0.375,0.0,1.0,p0);
		palm[4][0][1]=addBPoint(0.5,0.0,1.0,p0);
		palm[5][0][1]=addBPoint(0.625,0.0,1.0,p0);
		palm[6][0][1]=addBPoint(0.75,0.0,1.0,p0);
		palm[7][0][1]=addBPoint(0.875,0.0,1.0,p0);
		palm[8][0][1]=addBPoint(1.0,0.0,1.0,p0);
		palm[0][1][1]=addBPoint(0.0,0.25,1.0,p0);
		palm[1][1][1]=addBPoint(0.125,0.25,1.0,p0);
		palm[2][1][1]=addBPoint(0.25,0.25,1.0,p0);
		palm[3][1][1]=addBPoint(0.375,0.25,1.0,p0);
		palm[4][1][1]=addBPoint(0.5,0.25,1.0,p0);
		palm[5][1][1]=addBPoint(0.625,0.25,1.0,p0);
		palm[6][1][1]=addBPoint(0.75,0.25,1.0,p0);
		palm[7][1][1]=addBPoint(0.875,0.25,1.0,p0);
		palm[8][1][1]=addBPoint(1.0,0.25,1.0,p0);
		palm[0][2][1]=addBPoint(0.0,0.5,1.0,p0);
		palm[1][2][1]=addBPoint(0.125,0.5,1.0,p0);
		palm[2][2][1]=addBPoint(0.25,0.5,1.0,p0);
		palm[3][2][1]=addBPoint(0.375,0.5,1.0,p0);
		palm[4][2][1]=addBPoint(0.5,0.5,1.0,p0);
		palm[5][2][1]=addBPoint(0.625,0.5,1.0,p0);
		palm[6][2][1]=addBPoint(0.75,0.5,1.0,p0);
		palm[7][2][1]=addBPoint(0.875,0.5,1.0,p0);
		palm[8][2][1]=addBPoint(1.0,0.5,1.0,p0);
		palm[0][3][1]=addBPoint(0.0,0.75,1.0,p0);
		palm[1][3][1]=addBPoint(0.125,0.75,1.0,p0);
		palm[2][3][1]=addBPoint(0.25,0.75,1.0,p0);
		palm[3][3][1]=addBPoint(0.375,0.75,1.0,p0);
		palm[4][3][1]=addBPoint(0.5,0.75,1.0,p0);
		palm[5][3][1]=addBPoint(0.625,0.75,1.0,p0);
		palm[6][3][1]=addBPoint(0.75,0.75,1.0,p0);
		palm[7][3][1]=addBPoint(0.875,0.75,1.0,p0);
		palm[8][3][1]=addBPoint(1.0,0.75,1.0,p0);
		palm[0][4][1]=addBPoint(0.0,1.0,1.0,p0);
		palm[1][4][1]=addBPoint(0.125,1.0,1.0,p0);
		palm[2][4][1]=addBPoint(0.25,1.0,1.0,p0);
		palm[3][4][1]=addBPoint(0.375,1.0,1.0,p0);
		palm[4][4][1]=addBPoint(0.5,1.0,1.0,p0);
		palm[5][4][1]=addBPoint(0.625,1.0,1.0,p0);
		palm[6][4][1]=addBPoint(0.75,1.0,1.0,p0);
		palm[7][4][1]=addBPoint(0.875,1.0,1.0,p0);
		palm[8][4][1]=addBPoint(1.0,1.0,1.0,p0);

		
		
		for (int i = 0; i < pnx-1; i++) {


			for (int j = 0; j < pny-1; j++) {

				for (int k = 0; k < pnz-1; k++) {




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
		
		Segments ff0=new Segments(x_side*0.125,x_side*0.125,y_side,y_side*0.9,0,z_side);
		
		foreFinger[0][0][0]=palm[1][pny-1][0];
		foreFinger[1][0][0]=palm[2][pny-1][0];
		foreFinger[0][0][1]=palm[1][pny-1][1];
		foreFinger[1][0][1]=palm[2][pny-1][1];
		
		foreFinger[0][1][0]=addBPoint(0.0,0.5,0,ff0);
		foreFinger[1][1][0]=addBPoint(1.0,0.5,0,ff0);
		foreFinger[0][1][1]=addBPoint(0.0,0.5,1.0,ff0);
		foreFinger[1][1][1]=addBPoint(1.0,0.5,1.0,ff0);
		
		foreFinger[0][2][0]=addBPoint(0.0,0.75,0,ff0);
		foreFinger[1][2][0]=addBPoint(1.0,0.75,0,ff0);
		foreFinger[0][2][1]=addBPoint(0.0,0.75,1.0,ff0);
		foreFinger[1][2][1]=addBPoint(1.0,0.75,1.0,ff0);
		
		foreFinger[0][3][0]=addBPoint(0.0,1.0,0,ff0);
		foreFinger[1][3][0]=addBPoint(1.0,1.0,0,ff0);
		foreFinger[0][3][1]=addBPoint(0.0,1.0,1.0,ff0);
		foreFinger[1][3][1]=addBPoint(1.0,1.0,1.0,ff0);
		
		
		
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
		
		Segments mf0=new Segments(xc+x_side*0.375,x_side*0.125,y_side,y_side,0,z_side);
		
		middleFinger[0][0][0]=palm[3][pny-1][0];
		middleFinger[1][0][0]=palm[4][pny-1][0];
		middleFinger[0][0][1]=palm[3][pny-1][1];
		middleFinger[1][0][1]=palm[4][pny-1][1];
		
		middleFinger[0][1][0]=addBPoint(0.0,0.5,0,mf0);
		middleFinger[1][1][0]=addBPoint(1.0,0.5,0,mf0);
		middleFinger[0][1][1]=addBPoint(0.0,0.5,1.0,mf0);
		middleFinger[1][1][1]=addBPoint(1.0,0.5,1.0,mf0);
		
		middleFinger[0][2][0]=addBPoint(0.0,0.75,0,mf0);
		middleFinger[1][2][0]=addBPoint(1.0,0.75,0,mf0);
		middleFinger[0][2][1]=addBPoint(0.0,0.75,1.0,mf0);
		middleFinger[1][2][1]=addBPoint(1.0,0.75,1.0,mf0);
		
		middleFinger[0][3][0]=addBPoint(0.0,1.0,0,mf0);
		middleFinger[1][3][0]=addBPoint(1.0,1.0,0,mf0);
		middleFinger[0][3][1]=addBPoint(0.0,1.0,1.0,mf0);
		middleFinger[1][3][1]=addBPoint(1.0,1.0,1.0,mf0);
		
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
		
		Segments rf0=new Segments(xc+x_side*0.625,x_side*0.125,y_side,y_side*0.8,0,z_side);
		
		ringFinger[0][0][0]=palm[5][pny-1][0];
		ringFinger[1][0][0]=palm[6][pny-1][0];
		ringFinger[0][0][1]=palm[5][pny-1][1];
		ringFinger[1][0][1]=palm[6][pny-1][1];
		
		ringFinger[0][1][0]=addBPoint(0.0,0.5,0,rf0);
		ringFinger[1][1][0]=addBPoint(1.0,0.5,0,rf0);
		ringFinger[0][1][1]=addBPoint(0.0,0.5,1.0,rf0);
		ringFinger[1][1][1]=addBPoint(1.0,0.5,1.0,rf0);

		ringFinger[0][2][0]=addBPoint(0.0,0.75,0,rf0);
		ringFinger[1][2][0]=addBPoint(1.0,0.75,0,rf0);
		ringFinger[0][2][1]=addBPoint(0.0,0.75,1.0,rf0);
		ringFinger[1][2][1]=addBPoint(1.0,0.75,1.0,rf0);
		
		ringFinger[0][3][0]=addBPoint(0.0,1.0,0,rf0);
		ringFinger[1][3][0]=addBPoint(1.0,1.0,0,rf0);
		ringFinger[0][3][1]=addBPoint(0.0,1.0,1.0,rf0);
		ringFinger[1][3][1]=addBPoint(1.0,1.0,1.0,rf0);
		
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
		
		Segments lf0=new Segments(xc+x_side*0.875,x_side*0.125,y_side,y_side*0.7,0,z_side);
		
		littleFinger[0][0][0]=palm[7][pny-1][0];
		littleFinger[1][0][0]=palm[8][pny-1][0];
		littleFinger[0][0][1]=palm[7][pny-1][1];
		littleFinger[1][0][1]=palm[8][pny-1][1];
		
		littleFinger[0][1][0]=addBPoint(0.0,0.5,0,lf0);
		littleFinger[1][1][0]=addBPoint(1.0,0.5,0,lf0);
		littleFinger[0][1][1]=addBPoint(0.0,0.5,1.0,lf0);
		littleFinger[1][1][1]=addBPoint(1.0,0.5,1.0,lf0);
		
		littleFinger[0][2][0]=addBPoint(0.0,0.75,0,lf0);
		littleFinger[1][2][0]=addBPoint(1.0,0.75,0,lf0);
		littleFinger[0][2][1]=addBPoint(0.0,0.75,1.0,lf0);
		littleFinger[1][2][1]=addBPoint(1.0,0.75,1.0,lf0);
		
		littleFinger[0][3][0]=addBPoint(0.0,1.0,0,lf0);
		littleFinger[1][3][0]=addBPoint(1.0,1.0,0,lf0);
		littleFinger[0][3][1]=addBPoint(0.0,1.0,1.0,lf0);
		littleFinger[1][3][1]=addBPoint(1.0,1.0,1.0,lf0);
		
		
		for (int i = 0; i < rfnx-1; i++) {


			for (int j = 0; j < rfny-1; j++) {

				for (int k = 0; k < rfnz-1; k++) {




					if(i==0){

						LineData leftLD=addLine(littleFinger[i][j][k],littleFinger[i][j][k+1],littleFinger[i][j+1][k+1],littleFinger[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						LineData bottomLD=addLine(littleFinger[i][j][k],littleFinger[i][j+1][k],littleFinger[i+1][j+1][k],littleFinger[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==rfnz-1){
						LineData topLD=addLine(littleFinger[i][j][k+1],littleFinger[i+1][j][k+1],littleFinger[i+1][j+1][k+1],littleFinger[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						LineData backLD=addLine(littleFinger[i][j][k],littleFinger[i+1][j][k],littleFinger[i+1][j][k+1],littleFinger[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==rfny-1){
						LineData frontLD=addLine(littleFinger[i][j+1][k],littleFinger[i][j+1][k+1],littleFinger[i+1][j+1][k+1],littleFinger[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==rfnx-1){

						LineData rightLD=addLine(littleFinger[i+1][j][k],littleFinger[i+1][j+1][k],littleFinger[i+1][j+1][k+1],littleFinger[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		int tnx=2;
		int tny=5;
		int tnz=2;
		
		BPoint[][][] thumb=new BPoint[tnx][tny][tnz];
		
		double thumb_dy=1.125*y_side;
		
		Segments tf0=new Segments(xc,40,y_side*0.25,thumb_dy,0,z_side);
		
		double thumb_y0=-y_side*0.25/thumb_dy;
		
		
		thumb[0][0][0]=addBPoint(-0.5,thumb_y0,0,tf0);		
		thumb[1][0][0]=addBPoint(0.0,thumb_y0,0.0,tf0);
		thumb[0][0][1]=addBPoint(-0.5,thumb_y0,1.0,tf0);
		thumb[1][0][1]=addBPoint(0.0,thumb_y0,1.0,tf0);
		
		thumb[0][1][0]=addBPoint(-0.5,0.0,0,tf0);		
		thumb[1][1][0]=addBPoint(0.0,0.0,0.0,tf0);
		thumb[0][1][1]=addBPoint(-0.5,0.0,1.0,tf0);
		thumb[1][1][1]=addBPoint(0.0,0.0,1.0,tf0);
		
		thumb[0][2][0]=addBPoint(-1.0,0.5,0,tf0);		
		thumb[1][2][0]=addBPoint(-0.5,0.5,0.0,tf0);
		thumb[0][2][1]=addBPoint(-1.0,0.5,1.0,tf0);
		thumb[1][2][1]=addBPoint(-0.5,0.5,1.0,tf0);
		
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

						LineData rightLD=addLine(thumb[i+1][j][k],thumb[i+1][j+1][k],thumb[i+1][j+1][k+1],thumb[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		
		PolygonMesh pm=new PolygonMesh(points,polyData);
		
		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}




}	