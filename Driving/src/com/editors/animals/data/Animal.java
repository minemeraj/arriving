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

		Vector points=new Vector();
		points.setSize(100);

		Vector polyData=new Vector();

		int n=0;


		//body:

		BPoint body000=new BPoint(0,0,femur_length+shinbone_length,n++);
		BPoint body100=new BPoint(x_side,0,femur_length+shinbone_length,n++);
		BPoint body010=new BPoint(0,y_side,femur_length+shinbone_length,n++);
		BPoint body110=new BPoint(x_side,y_side,femur_length+shinbone_length,n++);

		BPoint body011=new BPoint(0,y_side,femur_length+shinbone_length+z_side,n++);
		BPoint body101=new BPoint(x_side,0,femur_length+shinbone_length+z_side,n++);
		BPoint body111=new BPoint(x_side,y_side,femur_length+shinbone_length+z_side,n++);
		BPoint body001=new BPoint(0,0,femur_length+shinbone_length+z_side,n++);

		points.setElementAt(body000,body000.getIndex());
		points.setElementAt(body100,body100.getIndex());
		points.setElementAt(body010,body010.getIndex());
		points.setElementAt(body001,body001.getIndex());
		points.setElementAt(body110,body110.getIndex());
		points.setElementAt(body011,body011.getIndex());
		points.setElementAt(body101,body101.getIndex());
		points.setElementAt(body111,body111.getIndex());


		LineData topLD=buildLine(body001,body101,body111,body011,Renderer3D.CAR_TOP);
		polyData.add(topLD);

		LineData bottomLD=buildLine(body000,body010,body110,body100,Renderer3D.CAR_BOTTOM);
		polyData.add(bottomLD);

		LineData leftLD=buildLine(body000,body001,body011,body010,Renderer3D.CAR_LEFT);
		polyData.add(leftLD);


		LineData rightLD=buildLine(body100,body110,body111,body101,Renderer3D.CAR_RIGHT);
		polyData.add(rightLD);


		LineData backLD=buildLine(body000,body100,body101,body001,Renderer3D.CAR_BACK);
		polyData.add(backLD);

		LineData frontLD=buildLine(body010,body011,body111,body110,Renderer3D.CAR_FRONT);
		polyData.add(frontLD);


		//head:
		
		
		double hx=(x_side-head_DX)/2.0;
		double hy=(y_side-head_DY)/2.0;

		BPoint head000=new BPoint(hx,hy,femur_length+shinbone_length+z_side+neck_length,n++);
		BPoint head100=new BPoint(hx+head_DX,hy,femur_length+shinbone_length+z_side+neck_length,n++);
		BPoint head010=new BPoint(hx,hy+head_DY,femur_length+shinbone_length+z_side+neck_length,n++);
		BPoint head110=new BPoint(hx+head_DX,hy+head_DY,femur_length+shinbone_length+z_side+neck_length,n++);

		BPoint head011=new BPoint(hx,hy+head_DY,femur_length+shinbone_length+z_side+head_DZ+neck_length,n++);
		BPoint head101=new BPoint(hx+head_DX,hy,femur_length+shinbone_length+z_side+head_DZ+neck_length,n++);
		BPoint head111=new BPoint(hx+head_DX,hy+head_DY,femur_length+shinbone_length+z_side+head_DZ+neck_length,n++);
		BPoint head001=new BPoint(hx,hy,femur_length+shinbone_length+z_side+head_DZ+neck_length,n++);

		points.setElementAt(head000,head000.getIndex());
		points.setElementAt(head100,head100.getIndex());
		points.setElementAt(head010,head010.getIndex());
		points.setElementAt(head001,head001.getIndex());
		points.setElementAt(head110,head110.getIndex());
		points.setElementAt(head011,head011.getIndex());
		points.setElementAt(head101,head101.getIndex());
		points.setElementAt(head111,head111.getIndex());


		LineData topHeadLD=buildLine(head001,head101,head111,head011,Renderer3D.CAR_TOP);
		polyData.add(topHeadLD);

		LineData bottomHeadLD=buildLine(head000,head010,head110,head100,Renderer3D.CAR_BOTTOM);
		polyData.add(bottomHeadLD);

		LineData leftHeadLD=buildLine(head000,head001,head011,head010,Renderer3D.CAR_LEFT);
		polyData.add(leftHeadLD);


		LineData rightHeadLD=buildLine(head100,head110,head111,head101,Renderer3D.CAR_RIGHT);
		polyData.add(rightHeadLD);


		LineData backHeadLD=buildLine(head000,head100,head101,head001,Renderer3D.CAR_BACK);
		polyData.add(backHeadLD);

		LineData frontHeadLD=buildLine(head010,head011,head111,head110,Renderer3D.CAR_FRONT);
		polyData.add(frontHeadLD);
		
		//neck:

		double nx=(x_side-neck_side)/2.0;

		BPoint neck000=new BPoint(nx,0,femur_length+shinbone_length+z_side,n++);
		BPoint neck100=new BPoint(nx+neck_side,0,femur_length+shinbone_length+z_side,n++);
		BPoint neck010=new BPoint(nx,y_side,femur_length+shinbone_length+z_side,n++);
		BPoint neck110=new BPoint(nx+neck_side,y_side,femur_length+shinbone_length+z_side,n++);

		BPoint neck011=new BPoint(nx,y_side,femur_length+shinbone_length+z_side+neck_length,n++);
		BPoint neck101=new BPoint(nx+neck_side,0,femur_length+shinbone_length+z_side+neck_length,n++);
		BPoint neck111=new BPoint(nx+neck_side,y_side,femur_length+shinbone_length+z_side+neck_length,n++);
		BPoint neck001=new BPoint(nx,0,femur_length+shinbone_length+z_side+neck_length,n++);

		points.setElementAt(neck000,neck000.getIndex());
		points.setElementAt(neck100,neck100.getIndex());
		points.setElementAt(neck010,neck010.getIndex());
		points.setElementAt(neck001,neck001.getIndex());
		points.setElementAt(neck110,neck110.getIndex());
		points.setElementAt(neck011,neck011.getIndex());
		points.setElementAt(neck101,neck101.getIndex());
		points.setElementAt(neck111,neck111.getIndex());


		LineData topneckLD=buildLine(neck001,neck101,neck111,neck011,Renderer3D.CAR_TOP);
		polyData.add(topneckLD);

		LineData bottomneckLD=buildLine(neck000,neck010,neck110,neck100,Renderer3D.CAR_BOTTOM);
		polyData.add(bottomneckLD);

		LineData leftneckLD=buildLine(neck000,neck001,neck011,neck010,Renderer3D.CAR_LEFT);
		polyData.add(leftneckLD);


		LineData rightneckLD=buildLine(neck100,neck110,neck111,neck101,Renderer3D.CAR_RIGHT);
		polyData.add(rightneckLD);


		LineData backneckLD=buildLine(neck000,neck100,neck101,neck001,Renderer3D.CAR_BACK);
		polyData.add(backneckLD);

		LineData frontneckLD=buildLine(neck010,neck011,neck111,neck110,Renderer3D.CAR_FRONT);
		polyData.add(frontneckLD);

		//legs:	
		
		double thigh_indentation=femur_length*Math.sin(q_angle);
		double thigh_side=leg_side+thigh_indentation;
		

		//LeftLeg

		BPoint pBackLeftLeg000=new BPoint(thigh_indentation,0,0,n++);
		BPoint pBackLeftLeg100=new BPoint(thigh_indentation+leg_side,0,0,n++);
		BPoint pBackLeftLeg110=new BPoint(thigh_indentation+leg_side,leg_side,0,n++);
		BPoint pBackLeftLeg010=new BPoint(thigh_indentation,leg_side,0,n++);

		points.setElementAt(pBackLeftLeg000,pBackLeftLeg000.getIndex());
		points.setElementAt(pBackLeftLeg100,pBackLeftLeg100.getIndex());
		points.setElementAt(pBackLeftLeg110,pBackLeftLeg110.getIndex());
		points.setElementAt(pBackLeftLeg010,pBackLeftLeg010.getIndex());

		LineData backLeftLeg=buildLine(pBackLeftLeg000,pBackLeftLeg010,pBackLeftLeg110,pBackLeftLeg100,Renderer3D.CAR_FRONT);
		polyData.add(backLeftLeg);


		BPoint pBackLeftLeg001=new BPoint(thigh_indentation,0,shinbone_length,n++);
		BPoint pBackLeftLeg101=new BPoint(thigh_indentation+leg_side,0,shinbone_length,n++);
		BPoint pBackLeftLeg111=new BPoint(thigh_indentation+leg_side,leg_side,shinbone_length,n++);
		BPoint pBackLeftLeg011=new BPoint(thigh_indentation,leg_side,shinbone_length,n++);

		points.setElementAt(pBackLeftLeg001,pBackLeftLeg001.getIndex());
		points.setElementAt(pBackLeftLeg101,pBackLeftLeg101.getIndex());
		points.setElementAt(pBackLeftLeg111,pBackLeftLeg111.getIndex());
		points.setElementAt(pBackLeftLeg011,pBackLeftLeg011.getIndex());

		LineData backLeftLegS0=buildLine(pBackLeftLeg000,pBackLeftLeg001,pBackLeftLeg011,pBackLeftLeg010,Renderer3D.CAR_LEFT);
		polyData.add(backLeftLegS0);
		LineData backLeftLegS1=buildLine(pBackLeftLeg010,pBackLeftLeg011,pBackLeftLeg111,pBackLeftLeg110,Renderer3D.CAR_FRONT);
		polyData.add(backLeftLegS1);
		LineData backLeftLegS2=buildLine(pBackLeftLeg110,pBackLeftLeg111,pBackLeftLeg101,pBackLeftLeg100,Renderer3D.CAR_RIGHT);
		polyData.add(backLeftLegS2);
		LineData backLeftLegS3=buildLine(pBackLeftLeg100,pBackLeftLeg101,pBackLeftLeg001,pBackLeftLeg000,Renderer3D.CAR_BACK);
		polyData.add(backLeftLegS3);
		
		//left thigh

		
	
		
		BPoint pBackLeftThigh001=new BPoint(0,0,femur_length+shinbone_length,n++);
		BPoint pBackLeftThigh101=new BPoint(thigh_side,0,femur_length+shinbone_length,n++);
		BPoint pBackLeftThigh111=new BPoint(thigh_side,leg_side,femur_length+shinbone_length,n++);
		BPoint pBackLeftThigh011=new BPoint(0,leg_side,femur_length+shinbone_length,n++);

		points.setElementAt(pBackLeftThigh001,pBackLeftThigh001.getIndex());
		points.setElementAt(pBackLeftThigh101,pBackLeftThigh101.getIndex());
		points.setElementAt(pBackLeftThigh111,pBackLeftThigh111.getIndex());
		points.setElementAt(pBackLeftThigh011,pBackLeftThigh011.getIndex());

		LineData backLeftThighS0=buildLine(pBackLeftLeg001,pBackLeftThigh001,pBackLeftThigh011,pBackLeftLeg011,Renderer3D.CAR_LEFT);
		polyData.add(backLeftThighS0);
		LineData backLeftThighS1=buildLine(pBackLeftLeg011,pBackLeftThigh011,pBackLeftThigh111,pBackLeftLeg111,Renderer3D.CAR_FRONT);
		polyData.add(backLeftThighS1);
		LineData backLeftThighS2=buildLine(pBackLeftLeg111,pBackLeftThigh111,pBackLeftThigh101,pBackLeftLeg101,Renderer3D.CAR_RIGHT);
		polyData.add(backLeftThighS2);
		LineData backLeftThighS3=buildLine(pBackLeftLeg101,pBackLeftThigh101,pBackLeftThigh001,pBackLeftLeg001,Renderer3D.CAR_BACK);
		polyData.add(backLeftThighS3);

		///RightLeg

		BPoint pBackRightLeg000=new BPoint(x_side-leg_side-thigh_indentation,0,0,n++);
		BPoint pBackRightLeg100=new BPoint(x_side-thigh_indentation,0,0,n++);
		BPoint pBackRightLeg110=new BPoint(x_side-thigh_indentation,leg_side,0,n++);
		BPoint pBackRightLeg010=new BPoint(x_side-leg_side-thigh_indentation,leg_side,0,n++);

		points.setElementAt(pBackRightLeg000,pBackRightLeg000.getIndex());
		points.setElementAt(pBackRightLeg100,pBackRightLeg100.getIndex());
		points.setElementAt(pBackRightLeg110,pBackRightLeg110.getIndex());
		points.setElementAt(pBackRightLeg010,pBackRightLeg010.getIndex());

		LineData backRightLeg=buildLine(pBackRightLeg000,pBackRightLeg010,pBackRightLeg110,pBackRightLeg100,Renderer3D.CAR_FRONT);
		polyData.add(backRightLeg);


		BPoint pBackRightLeg001=new BPoint(x_side-leg_side-thigh_indentation,0,shinbone_length,n++);
		BPoint pBackRightLeg101=new BPoint(x_side-thigh_indentation,0,shinbone_length,n++);
		BPoint pBackRightLeg111=new BPoint(x_side-thigh_indentation,leg_side,shinbone_length,n++);
		BPoint pBackRightLeg011=new BPoint(x_side-leg_side-thigh_indentation,leg_side,shinbone_length,n++);

		points.setElementAt(pBackRightLeg001,pBackRightLeg001.getIndex());
		points.setElementAt(pBackRightLeg101,pBackRightLeg101.getIndex());
		points.setElementAt(pBackRightLeg111,pBackRightLeg111.getIndex());
		points.setElementAt(pBackRightLeg011,pBackRightLeg011.getIndex());

		LineData backRightLegS0=buildLine(pBackRightLeg000,pBackRightLeg001,pBackRightLeg011,pBackRightLeg010,Renderer3D.CAR_LEFT);
		polyData.add(backRightLegS0);
		LineData backRightLegS1=buildLine(pBackRightLeg010,pBackRightLeg011,pBackRightLeg111,pBackRightLeg110,Renderer3D.CAR_FRONT);
		polyData.add(backRightLegS1);
		LineData backRightLegS2=buildLine(pBackRightLeg110,pBackRightLeg111,pBackRightLeg101,pBackRightLeg100,Renderer3D.CAR_RIGHT);
		polyData.add(backRightLegS2);
		LineData backRightLegS3=buildLine(pBackRightLeg100,pBackRightLeg101,pBackRightLeg001,pBackRightLeg000,Renderer3D.CAR_BACK);
		polyData.add(backRightLegS3);
		
		//right thigh
		
		BPoint pBackRightThigh001=new BPoint(x_side-thigh_side,0,femur_length+shinbone_length,n++);
		BPoint pBackRightThigh101=new BPoint(x_side,0,femur_length+shinbone_length,n++);
		BPoint pBackRightThigh111=new BPoint(x_side,leg_side,femur_length+shinbone_length,n++);
		BPoint pBackRightThigh011=new BPoint(x_side-thigh_side,leg_side,femur_length+shinbone_length,n++);

		points.setElementAt(pBackRightThigh001,pBackRightThigh001.getIndex());
		points.setElementAt(pBackRightThigh101,pBackRightThigh101.getIndex());
		points.setElementAt(pBackRightThigh111,pBackRightThigh111.getIndex());
		points.setElementAt(pBackRightThigh011,pBackRightThigh011.getIndex());

		LineData backRightThighS0=buildLine(pBackRightLeg001,pBackRightThigh001,pBackRightThigh011,pBackRightLeg011,Renderer3D.CAR_LEFT);
		polyData.add(backRightThighS0);
		LineData backRightThighS1=buildLine(pBackRightLeg011,pBackRightThigh011,pBackRightThigh111,pBackRightLeg111,Renderer3D.CAR_FRONT);
		polyData.add(backRightThighS1);
		LineData backRightThighS2=buildLine(pBackRightLeg111,pBackRightThigh111,pBackRightThigh101,pBackRightLeg101,Renderer3D.CAR_RIGHT);
		polyData.add(backRightThighS2);
		LineData backRightThighS3=buildLine(pBackRightLeg101,pBackRightThigh101,pBackRightThigh001,pBackRightLeg001,Renderer3D.CAR_BACK);
		polyData.add(backRightThighS3);
		
		//Arms:
		
		//Left fore arm
		
		double ax=2*leg_side;
		double az=femur_length+shinbone_length+z_side-humerus_length-radius_length;

		BPoint pFrontLeftForearm000=new BPoint(-ax,y_side-leg_side,az,n++);
		BPoint pFrontLeftForearm100=new BPoint(-ax+leg_side,y_side-leg_side,az,n++);
		BPoint pFrontLeftForearm110=new BPoint(-ax+leg_side,y_side,az,n++);
		BPoint pFrontLeftForearm010=new BPoint(-ax,y_side,az,n++);
		
		points.setElementAt(pFrontLeftForearm000,pFrontLeftForearm000.getIndex());
		points.setElementAt(pFrontLeftForearm100,pFrontLeftForearm100.getIndex());
		points.setElementAt(pFrontLeftForearm110,pFrontLeftForearm110.getIndex());
		points.setElementAt(pFrontLeftForearm010,pFrontLeftForearm010.getIndex());
		
		LineData bottomLeftForearm=buildLine(pFrontLeftForearm000,pFrontLeftForearm010,pFrontLeftForearm110,pFrontLeftForearm100,Renderer3D.CAR_BOTTOM);
		polyData.add(bottomLeftForearm);
		
		BPoint pFrontLeftForearm001=new BPoint(-ax,y_side-leg_side,az+radius_length,n++);
		BPoint pFrontLeftForearm101=new BPoint(-ax+leg_side,y_side-leg_side,az+radius_length,n++);
		BPoint pFrontLeftForearm111=new BPoint(-ax+leg_side,y_side,az+radius_length,n++);
		BPoint pFrontLeftForearm011=new BPoint(-ax,y_side,az+radius_length,n++);
		
		points.setElementAt(pFrontLeftForearm001,pFrontLeftForearm001.getIndex());
		points.setElementAt(pFrontLeftForearm101,pFrontLeftForearm101.getIndex());
		points.setElementAt(pFrontLeftForearm111,pFrontLeftForearm111.getIndex());
		points.setElementAt(pFrontLeftForearm011,pFrontLeftForearm011.getIndex());
		
		//LineData topLeftForearm=buildLine(pFrontLeftForearm001,pFrontLeftForearm101,pFrontLeftForearm111,pFrontLeftForearm011,Renderer3D.CAR_TOP);
		//polyData.add(topLeftForearm);
		
		LineData FrontLeftForearmS0=buildLine(pFrontLeftForearm000,pFrontLeftForearm001,pFrontLeftForearm011,pFrontLeftForearm010,Renderer3D.CAR_LEFT);
		polyData.add(FrontLeftForearmS0);
		LineData FrontLeftForearmS1=buildLine(pFrontLeftForearm010,pFrontLeftForearm011,pFrontLeftForearm111,pFrontLeftForearm110,Renderer3D.CAR_FRONT);
		polyData.add(FrontLeftForearmS1);
		LineData FrontLeftForearmS2=buildLine(pFrontLeftForearm110,pFrontLeftForearm111,pFrontLeftForearm101,pFrontLeftForearm100,Renderer3D.CAR_RIGHT);
		polyData.add(FrontLeftForearmS2);
		LineData FrontLeftForearmS3=buildLine(pFrontLeftForearm100,pFrontLeftForearm101,pFrontLeftForearm001,pFrontLeftForearm000,Renderer3D.CAR_BACK);
		polyData.add(FrontLeftForearmS3);
		
		//left arm
		
		BPoint pFrontLeftArm001=new BPoint(-ax,y_side-leg_side,az+radius_length+humerus_length,n++);
		BPoint pFrontLeftArm101=new BPoint(-ax+leg_side,y_side-leg_side,az+radius_length+humerus_length,n++);
		BPoint pFrontLeftArm111=new BPoint(-ax+leg_side,y_side,az+radius_length+humerus_length,n++);
		BPoint pFrontLeftArm011=new BPoint(-ax,y_side,az+radius_length+humerus_length,n++);
		
		points.setElementAt(pFrontLeftArm001,pFrontLeftArm001.getIndex());
		points.setElementAt(pFrontLeftArm101,pFrontLeftArm101.getIndex());
		points.setElementAt(pFrontLeftArm111,pFrontLeftArm111.getIndex());
		points.setElementAt(pFrontLeftArm011,pFrontLeftArm011.getIndex());
		
		LineData topLeftArm=buildLine(pFrontLeftArm001,pFrontLeftArm101,pFrontLeftArm111,pFrontLeftArm011,Renderer3D.CAR_TOP);
		polyData.add(topLeftArm);
		
		LineData FrontLeftArmS0=buildLine(pFrontLeftForearm000,pFrontLeftArm001,pFrontLeftArm011,pFrontLeftForearm010,Renderer3D.CAR_LEFT);
		polyData.add(FrontLeftArmS0);
		LineData FrontLeftArmS1=buildLine(pFrontLeftForearm010,pFrontLeftArm011,pFrontLeftArm111,pFrontLeftForearm110,Renderer3D.CAR_FRONT);
		polyData.add(FrontLeftArmS1);
		LineData FrontLeftArmS2=buildLine(pFrontLeftForearm110,pFrontLeftArm111,pFrontLeftArm101,pFrontLeftForearm100,Renderer3D.CAR_RIGHT);
		polyData.add(FrontLeftArmS2);
		LineData FrontLeftArmS3=buildLine(pFrontLeftForearm100,pFrontLeftArm101,pFrontLeftArm001,pFrontLeftForearm000,Renderer3D.CAR_BACK);
		polyData.add(FrontLeftArmS3);
		
		
		
		//Right forearm
		
		BPoint pFrontRightForearm000=new BPoint(ax+x_side-leg_side,y_side-leg_side,az,n++);
		BPoint pFrontRightForearm100=new BPoint(ax+x_side,y_side-leg_side,az,n++);
		BPoint pFrontRightForearm110=new BPoint(ax+x_side,y_side,az,n++);
		BPoint pFrontRightForearm010=new BPoint(ax+x_side-leg_side,y_side,az,n++);
		
		points.setElementAt(pFrontRightForearm000,pFrontRightForearm000.getIndex());
		points.setElementAt(pFrontRightForearm100,pFrontRightForearm100.getIndex());
		points.setElementAt(pFrontRightForearm110,pFrontRightForearm110.getIndex());
		points.setElementAt(pFrontRightForearm010,pFrontRightForearm010.getIndex());
		
		LineData FrontRightForearm=buildLine(pFrontRightForearm000,pFrontRightForearm010,pFrontRightForearm110,pFrontRightForearm100,Renderer3D.CAR_BOTTOM);
		polyData.add(FrontRightForearm);

		
		BPoint pFrontRightForearm001=new BPoint(ax+x_side-leg_side,y_side-leg_side,az+radius_length,n++);
		BPoint pFrontRightForearm101=new BPoint(ax+x_side,y_side-leg_side,az+radius_length,n++);
		BPoint pFrontRightForearm111=new BPoint(ax+x_side,y_side,az+radius_length,n++);
		BPoint pFrontRightForearm011=new BPoint(ax+x_side-leg_side,y_side,az+radius_length,n++);
		
		points.setElementAt(pFrontRightForearm001,pFrontRightForearm001.getIndex());
		points.setElementAt(pFrontRightForearm101,pFrontRightForearm101.getIndex());
		points.setElementAt(pFrontRightForearm111,pFrontRightForearm111.getIndex());
		points.setElementAt(pFrontRightForearm011,pFrontRightForearm011.getIndex());
		
		//LineData topRightForearm=buildLine(pFrontRightForearm001,pFrontRightForearm101,pFrontRightForearm111,pFrontRightForearm011,Renderer3D.CAR_TOP);
		//polyData.add(topRightForearm);
		
		LineData frontRightForearmS0=buildLine(pFrontRightForearm000,pFrontRightForearm001,pFrontRightForearm011,pFrontRightForearm010,Renderer3D.CAR_LEFT);
		polyData.add(frontRightForearmS0);
		LineData frontRightForearmS1=buildLine(pFrontRightForearm010,pFrontRightForearm011,pFrontRightForearm111,pFrontRightForearm110,Renderer3D.CAR_FRONT);
		polyData.add(frontRightForearmS1);
		LineData frontRightForearmS2=buildLine(pFrontRightForearm110,pFrontRightForearm111,pFrontRightForearm101,pFrontRightForearm100,Renderer3D.CAR_RIGHT);
		polyData.add(frontRightForearmS2);
		LineData frontRightForearmS3=buildLine(pFrontRightForearm100,pFrontRightForearm101,pFrontRightForearm001,pFrontRightForearm000,Renderer3D.CAR_BACK);
		polyData.add(frontRightForearmS3);
		
		//right arm
		
		BPoint pFrontRightArm001=new BPoint(ax+x_side-leg_side,y_side-leg_side,az+radius_length+humerus_length,n++);
		BPoint pFrontRightArm101=new BPoint(ax+x_side,y_side-leg_side,az+radius_length+humerus_length,n++);
		BPoint pFrontRightArm111=new BPoint(ax+x_side,y_side,az+radius_length+humerus_length,n++);
		BPoint pFrontRightArm011=new BPoint(ax+x_side-leg_side,y_side,az+radius_length+humerus_length,n++);
		
		points.setElementAt(pFrontRightArm001,pFrontRightArm001.getIndex());
		points.setElementAt(pFrontRightArm101,pFrontRightArm101.getIndex());
		points.setElementAt(pFrontRightArm111,pFrontRightArm111.getIndex());
		points.setElementAt(pFrontRightArm011,pFrontRightArm011.getIndex());
		
		LineData topRightArm=buildLine(pFrontRightArm001,pFrontRightArm101,pFrontRightArm111,pFrontRightArm011,Renderer3D.CAR_TOP);
		polyData.add(topRightArm);
		
		LineData frontRightArmS0=buildLine(pFrontRightForearm000,pFrontRightArm001,pFrontRightArm011,pFrontRightForearm010,Renderer3D.CAR_LEFT);
		polyData.add(frontRightArmS0);
		LineData frontRightArmS1=buildLine(pFrontRightForearm010,pFrontRightArm011,pFrontRightArm111,pFrontRightForearm110,Renderer3D.CAR_FRONT);
		polyData.add(frontRightArmS1);
		LineData frontRightArmS2=buildLine(pFrontRightForearm110,pFrontRightArm111,pFrontRightArm101,pFrontRightForearm100,Renderer3D.CAR_RIGHT);
		polyData.add(frontRightArmS2);
		LineData frontRightArmS3=buildLine(pFrontRightForearm100,pFrontRightArm101,pFrontRightArm001,pFrontRightForearm000,Renderer3D.CAR_BACK);
		polyData.add(frontRightArmS3);
		
		
		
		///shoulders:
		
		double sx=ax-leg_side;
		double shDZ=20; 
		
		//left shoulder
		
		BPoint pFrontLeftSho000=new BPoint(-sx,y_side-leg_side,az+humerus_length+radius_length-shDZ,n++);
		BPoint pFrontLeftSho100=new BPoint(-sx+leg_side,y_side-leg_side,az+humerus_length+radius_length-shDZ,n++);
		BPoint pFrontLeftSho110=new BPoint(-sx+leg_side,y_side,az+humerus_length+radius_length-shDZ,n++);
		BPoint pFrontLeftSho010=new BPoint(-sx,y_side,az+humerus_length+radius_length-shDZ,n++);
		
		points.setElementAt(pFrontLeftSho000,pFrontLeftSho000.getIndex());
		points.setElementAt(pFrontLeftSho100,pFrontLeftSho100.getIndex());
		points.setElementAt(pFrontLeftSho110,pFrontLeftSho110.getIndex());
		points.setElementAt(pFrontLeftSho010,pFrontLeftSho010.getIndex());
		
		LineData FrontLeftSho=buildLine(pFrontLeftSho000,pFrontLeftSho010,pFrontLeftSho110,pFrontLeftSho100,Renderer3D.CAR_BOTTOM);
		polyData.add(FrontLeftSho);

		
		BPoint pFrontLeftSho001=new BPoint(-sx,y_side-leg_side,az+humerus_length+radius_length,n++);
		BPoint pFrontLeftSho101=new BPoint(-sx+leg_side,y_side-leg_side,az+humerus_length+radius_length,n++);
		BPoint pFrontLeftSho111=new BPoint(-sx+leg_side,y_side,az+humerus_length+radius_length,n++);
		BPoint pFrontLeftSho011=new BPoint(-sx,y_side,az+humerus_length+radius_length,n++);
		
		points.setElementAt(pFrontLeftSho001,pFrontLeftSho001.getIndex());
		points.setElementAt(pFrontLeftSho101,pFrontLeftSho101.getIndex());
		points.setElementAt(pFrontLeftSho111,pFrontLeftSho111.getIndex());
		points.setElementAt(pFrontLeftSho011,pFrontLeftSho011.getIndex());
		
		LineData TopLeftSho=buildLine(pFrontLeftSho001,pFrontLeftSho101,pFrontLeftSho111,pFrontLeftSho011,Renderer3D.CAR_BOTTOM);
		polyData.add(TopLeftSho);
		
		LineData FrontLeftShoS0=buildLine(pFrontLeftSho000,pFrontLeftSho001,pFrontLeftSho011,pFrontLeftSho010,Renderer3D.CAR_LEFT);
		polyData.add(FrontLeftShoS0);
		LineData FrontLeftShoS1=buildLine(pFrontLeftSho010,pFrontLeftSho011,pFrontLeftSho111,pFrontLeftSho110,Renderer3D.CAR_FRONT);
		polyData.add(FrontLeftShoS1);
		LineData FrontLeftShoS2=buildLine(pFrontLeftSho110,pFrontLeftSho111,pFrontLeftSho101,pFrontLeftSho100,Renderer3D.CAR_RIGHT);
		polyData.add(FrontLeftShoS2);
		LineData FrontLeftShoS3=buildLine(pFrontLeftSho100,pFrontLeftSho101,pFrontLeftSho001,pFrontLeftSho000,Renderer3D.CAR_BACK);
		polyData.add(FrontLeftShoS3);
		
		//Right shoulder
		
		BPoint pFrontRightSho000=new BPoint(sx+x_side-leg_side,y_side-leg_side,az+humerus_length+radius_length-shDZ,n++);
		BPoint pFrontRightSho100=new BPoint(sx+x_side,y_side-leg_side,az+humerus_length+radius_length-shDZ,n++);
		BPoint pFrontRightSho110=new BPoint(sx+x_side,y_side,az+humerus_length+radius_length-shDZ,n++);
		BPoint pFrontRightSho010=new BPoint(sx+x_side-leg_side,y_side,az+humerus_length+radius_length-shDZ,n++);
		
		points.setElementAt(pFrontRightSho000,pFrontRightSho000.getIndex());
		points.setElementAt(pFrontRightSho100,pFrontRightSho100.getIndex());
		points.setElementAt(pFrontRightSho110,pFrontRightSho110.getIndex());
		points.setElementAt(pFrontRightSho010,pFrontRightSho010.getIndex());
		
		LineData FrontRightSho=buildLine(pFrontRightSho000,pFrontRightSho010,pFrontRightSho110,pFrontRightSho100,Renderer3D.CAR_BOTTOM);
		polyData.add(FrontRightSho);

		
		BPoint pFrontRightSho001=new BPoint(sx+x_side-leg_side,y_side-leg_side,az+humerus_length+radius_length,n++);
		BPoint pFrontRightSho101=new BPoint(sx+x_side,y_side-leg_side,az+humerus_length+radius_length,n++);
		BPoint pFrontRightSho111=new BPoint(sx+x_side,y_side,az+humerus_length+radius_length,n++);
		BPoint pFrontRightSho011=new BPoint(sx+x_side-leg_side,y_side,az+humerus_length+radius_length,n++);
		
		points.setElementAt(pFrontRightSho001,pFrontRightSho001.getIndex());
		points.setElementAt(pFrontRightSho101,pFrontRightSho101.getIndex());
		points.setElementAt(pFrontRightSho111,pFrontRightSho111.getIndex());
		points.setElementAt(pFrontRightSho011,pFrontRightSho011.getIndex());
		
		LineData topRightSho=buildLine(pFrontRightSho001,pFrontRightSho101,pFrontRightSho111,pFrontRightSho011,Renderer3D.CAR_TOP);
		polyData.add(topRightSho);
		
		LineData frontRightShoS0=buildLine(pFrontRightSho000,pFrontRightSho001,pFrontRightSho011,pFrontRightSho010,Renderer3D.CAR_LEFT);
		polyData.add(frontRightShoS0);
		LineData frontRightShoS1=buildLine(pFrontRightSho010,pFrontRightSho011,pFrontRightSho111,pFrontRightSho110,Renderer3D.CAR_FRONT);
		polyData.add(frontRightShoS1);
		LineData frontRightShoS2=buildLine(pFrontRightSho110,pFrontRightSho111,pFrontRightSho101,pFrontRightSho100,Renderer3D.CAR_RIGHT);
		polyData.add(frontRightShoS2);
		LineData frontRightShoS3=buildLine(pFrontRightSho100,pFrontRightSho101,pFrontRightSho001,pFrontRightSho000,Renderer3D.CAR_BACK);
		polyData.add(frontRightShoS3);

		/////////



		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildQuadrupedMesh() {


		Vector points=new Vector();
		points.setSize(100);

		Vector polyData=new Vector();
		
		int n=0;
		


		//main body:

		BPoint p000=new BPoint(0,0,femur_length+shinbone_length,n++);
		BPoint p100=new BPoint(x_side,0,femur_length+shinbone_length,n++);
		BPoint p010=new BPoint(0,y_side,femur_length+shinbone_length,n++);
		BPoint p001=new BPoint(0,0,femur_length+shinbone_length+z_side,n++);
		BPoint p110=new BPoint(x_side,y_side,femur_length+shinbone_length,n++);
		BPoint p011=new BPoint(0,y_side,femur_length+shinbone_length+z_side,n++);
		BPoint p101=new BPoint(x_side,0,femur_length+shinbone_length+z_side,n++);
		BPoint p111=new BPoint(x_side,y_side,femur_length+shinbone_length+z_side,n++);

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
		
		//head:
		
		
		
		double hx=(x_side-head_DX)/2.0;
		double hy=(y_side-neck_side);
		
		
			
		double hz=femur_length+shinbone_length+z_side+neck_length;

		BPoint head000=new BPoint(hx,hy,hz,n++);
		BPoint head100=new BPoint(hx+head_DX,hy,hz,n++);
		BPoint head010=new BPoint(hx,hy+head_DY,hz,n++);
		BPoint head110=new BPoint(hx+head_DX,hy+head_DY,hz,n++);

		BPoint head011=new BPoint(hx,hy+head_DY,hz+head_DZ,n++);
		BPoint head101=new BPoint(hx+head_DX,hy,hz+head_DZ,n++);
		BPoint head111=new BPoint(hx+head_DX,hy+head_DY,hz+head_DZ,n++);
		BPoint head001=new BPoint(hx,hy,hz+head_DZ,n++);

		points.setElementAt(head000,head000.getIndex());
		points.setElementAt(head100,head100.getIndex());
		points.setElementAt(head010,head010.getIndex());
		points.setElementAt(head001,head001.getIndex());
		points.setElementAt(head110,head110.getIndex());
		points.setElementAt(head011,head011.getIndex());
		points.setElementAt(head101,head101.getIndex());
		points.setElementAt(head111,head111.getIndex());


		LineData topHeadLD=buildLine(head001,head101,head111,head011,Renderer3D.CAR_TOP);
		polyData.add(topHeadLD);

		LineData bottomHeadLD=buildLine(head000,head010,head110,head100,Renderer3D.CAR_BOTTOM);
		polyData.add(bottomHeadLD);

		LineData leftHeadLD=buildLine(head000,head001,head011,head010,Renderer3D.CAR_LEFT);
		polyData.add(leftHeadLD);


		LineData rightHeadLD=buildLine(head100,head110,head111,head101,Renderer3D.CAR_RIGHT);
		polyData.add(rightHeadLD);


		LineData backHeadLD=buildLine(head000,head100,head101,head001,Renderer3D.CAR_BACK);
		polyData.add(backHeadLD);

		LineData frontHeadLD=buildLine(head010,head011,head111,head110,Renderer3D.CAR_FRONT);
		polyData.add(frontHeadLD);
		
		//neck:
		
		
		double nz=femur_length+shinbone_length+z_side;
		double nx=(x_side-neck_side)/2.0;
		double ny=y_side-neck_side;
	
		BPoint neck000=new BPoint(nx,ny,nz,n++);
		BPoint neck100=new BPoint(nx+neck_side,ny,nz,n++);
		BPoint neck010=new BPoint(nx,ny+neck_side,nz,n++);
		BPoint neck110=new BPoint(nx+neck_side,ny+neck_side,nz,n++);

		BPoint neck011=new BPoint(nx,ny+neck_side,nz+neck_length,n++);
		BPoint neck101=new BPoint(nx+neck_side,ny,nz+neck_length,n++);
		BPoint neck111=new BPoint(nx+neck_side,ny+neck_side,nz+neck_length,n++);
		BPoint neck001=new BPoint(nx,ny,nz+neck_length,n++);

		points.setElementAt(neck000,neck000.getIndex());
		points.setElementAt(neck100,neck100.getIndex());
		points.setElementAt(neck010,neck010.getIndex());
		points.setElementAt(neck001,neck001.getIndex());
		points.setElementAt(neck110,neck110.getIndex());
		points.setElementAt(neck011,neck011.getIndex());
		points.setElementAt(neck101,neck101.getIndex());
		points.setElementAt(neck111,neck111.getIndex());


		LineData topNeckLD=buildLine(neck001,neck101,neck111,neck011,Renderer3D.CAR_TOP);
		polyData.add(topNeckLD);

		LineData bottomNeckLD=buildLine(neck000,neck010,neck110,neck100,Renderer3D.CAR_BOTTOM);
		polyData.add(bottomNeckLD);

		LineData leftNeckLD=buildLine(neck000,neck001,neck011,neck010,Renderer3D.CAR_LEFT);
		polyData.add(leftNeckLD);


		LineData rightNeckLD=buildLine(neck100,neck110,neck111,neck101,Renderer3D.CAR_RIGHT);
		polyData.add(rightNeckLD);


		LineData backNeckLD=buildLine(neck000,neck100,neck101,neck001,Renderer3D.CAR_BACK);
		polyData.add(backNeckLD);

		LineData frontNeckLD=buildLine(neck010,neck011,neck111,neck110,Renderer3D.CAR_FRONT);
		polyData.add(frontNeckLD);
		
		//neck:
		
		
		//legs:	
		
		//backLeftLeg
		
		BPoint pBackLeftLeg000=new BPoint(0,0,0,n++);
		BPoint pBackLeftLeg100=new BPoint(leg_side,0,0,n++);
		BPoint pBackLeftLeg110=new BPoint(leg_side,leg_side,0,n++);
		BPoint pBackLeftLeg010=new BPoint(0,leg_side,0,n++);
		
		points.setElementAt(pBackLeftLeg000,pBackLeftLeg000.getIndex());
		points.setElementAt(pBackLeftLeg100,pBackLeftLeg100.getIndex());
		points.setElementAt(pBackLeftLeg110,pBackLeftLeg110.getIndex());
		points.setElementAt(pBackLeftLeg010,pBackLeftLeg010.getIndex());
		
		LineData backLeftLeg=buildLine(pBackLeftLeg000,pBackLeftLeg010,pBackLeftLeg110,pBackLeftLeg100,Renderer3D.CAR_FRONT);
		polyData.add(backLeftLeg);

		
		BPoint pBackLeftLeg001=new BPoint(0,0,shinbone_length,n++);
		BPoint pBackLeftLeg101=new BPoint(leg_side,0,shinbone_length,n++);
		BPoint pBackLeftLeg111=new BPoint(leg_side,leg_side,shinbone_length,n++);
		BPoint pBackLeftLeg011=new BPoint(0,leg_side,shinbone_length,n++);
		
		points.setElementAt(pBackLeftLeg001,pBackLeftLeg001.getIndex());
		points.setElementAt(pBackLeftLeg101,pBackLeftLeg101.getIndex());
		points.setElementAt(pBackLeftLeg111,pBackLeftLeg111.getIndex());
		points.setElementAt(pBackLeftLeg011,pBackLeftLeg011.getIndex());
		
		LineData backLeftLegS0=buildLine(pBackLeftLeg000,pBackLeftLeg001,pBackLeftLeg011,pBackLeftLeg010,Renderer3D.CAR_LEFT);
		polyData.add(backLeftLegS0);
		LineData backLeftLegS1=buildLine(pBackLeftLeg010,pBackLeftLeg011,pBackLeftLeg111,pBackLeftLeg110,Renderer3D.CAR_FRONT);
		polyData.add(backLeftLegS1);
		LineData backLeftLegS2=buildLine(pBackLeftLeg110,pBackLeftLeg111,pBackLeftLeg101,pBackLeftLeg100,Renderer3D.CAR_RIGHT);
		polyData.add(backLeftLegS2);
		LineData backLeftLegS3=buildLine(pBackLeftLeg100,pBackLeftLeg101,pBackLeftLeg001,pBackLeftLeg000,Renderer3D.CAR_BACK);
		polyData.add(backLeftLegS3);
		
		//back left thigh
		
		BPoint pBackLeftThigh001=new BPoint(0,0,femur_length+shinbone_length,n++);
		BPoint pBackLeftThigh101=new BPoint(leg_side,0,femur_length+shinbone_length,n++);
		BPoint pBackLeftThigh111=new BPoint(leg_side,leg_side,femur_length+shinbone_length,n++);
		BPoint pBackLeftThigh011=new BPoint(0,leg_side,femur_length+shinbone_length,n++);

		points.setElementAt(pBackLeftThigh001,pBackLeftThigh001.getIndex());
		points.setElementAt(pBackLeftThigh101,pBackLeftThigh101.getIndex());
		points.setElementAt(pBackLeftThigh111,pBackLeftThigh111.getIndex());
		points.setElementAt(pBackLeftThigh011,pBackLeftThigh011.getIndex());

		LineData backLeftThighS0=buildLine(pBackLeftLeg001,pBackLeftThigh001,pBackLeftThigh011,pBackLeftLeg011,Renderer3D.CAR_LEFT);
		polyData.add(backLeftThighS0);
		LineData backLeftThighS1=buildLine(pBackLeftLeg011,pBackLeftThigh011,pBackLeftThigh111,pBackLeftLeg111,Renderer3D.CAR_FRONT);
		polyData.add(backLeftThighS1);
		LineData backLeftThighS2=buildLine(pBackLeftLeg111,pBackLeftThigh111,pBackLeftThigh101,pBackLeftLeg101,Renderer3D.CAR_RIGHT);
		polyData.add(backLeftThighS2);
		LineData backLeftThighS3=buildLine(pBackLeftLeg101,pBackLeftThigh101,pBackLeftThigh001,pBackLeftLeg001,Renderer3D.CAR_BACK);
		polyData.add(backLeftThighS3);
		
		//backRightLeg
		
		BPoint pBackRightLeg000=new BPoint(x_side-leg_side,0,0,n++);
		BPoint pBackRightLeg100=new BPoint(x_side,0,0,n++);
		BPoint pBackRightLeg110=new BPoint(x_side,leg_side,0,n++);
		BPoint pBackRightLeg010=new BPoint(x_side-leg_side,leg_side,0,n++);
		
		points.setElementAt(pBackRightLeg000,pBackRightLeg000.getIndex());
		points.setElementAt(pBackRightLeg100,pBackRightLeg100.getIndex());
		points.setElementAt(pBackRightLeg110,pBackRightLeg110.getIndex());
		points.setElementAt(pBackRightLeg010,pBackRightLeg010.getIndex());
		
		LineData backRightLeg=buildLine(pBackRightLeg000,pBackRightLeg010,pBackRightLeg110,pBackRightLeg100,Renderer3D.CAR_FRONT);
		polyData.add(backRightLeg);

		
		BPoint pBackRightLeg001=new BPoint(x_side-leg_side,0,shinbone_length,n++);
		BPoint pBackRightLeg101=new BPoint(x_side,0,shinbone_length,n++);
		BPoint pBackRightLeg111=new BPoint(x_side,leg_side,shinbone_length,n++);
		BPoint pBackRightLeg011=new BPoint(x_side-leg_side,leg_side,shinbone_length,n++);
		
		points.setElementAt(pBackRightLeg001,pBackRightLeg001.getIndex());
		points.setElementAt(pBackRightLeg101,pBackRightLeg101.getIndex());
		points.setElementAt(pBackRightLeg111,pBackRightLeg111.getIndex());
		points.setElementAt(pBackRightLeg011,pBackRightLeg011.getIndex());
		
		LineData backRightLegS0=buildLine(pBackRightLeg000,pBackRightLeg001,pBackRightLeg011,pBackRightLeg010,Renderer3D.CAR_LEFT);
		polyData.add(backRightLegS0);
		LineData backRightLegS1=buildLine(pBackRightLeg010,pBackRightLeg011,pBackRightLeg111,pBackRightLeg110,Renderer3D.CAR_FRONT);
		polyData.add(backRightLegS1);
		LineData backRightLegS2=buildLine(pBackRightLeg110,pBackRightLeg111,pBackRightLeg101,pBackRightLeg100,Renderer3D.CAR_RIGHT);
		polyData.add(backRightLegS2);
		LineData backRightLegS3=buildLine(pBackRightLeg100,pBackRightLeg101,pBackRightLeg001,pBackRightLeg000,Renderer3D.CAR_BACK);
		polyData.add(backRightLegS3);
		
		//back right thigh
		
		BPoint pBackRightThigh001=new BPoint(x_side-leg_side,0,femur_length+shinbone_length,n++);
		BPoint pBackRightThigh101=new BPoint(x_side,0,femur_length+shinbone_length,n++);
		BPoint pBackRightThigh111=new BPoint(x_side,leg_side,femur_length+shinbone_length,n++);
		BPoint pBackRightThigh011=new BPoint(x_side-leg_side,leg_side,femur_length+shinbone_length,n++);

		points.setElementAt(pBackRightThigh001,pBackRightThigh001.getIndex());
		points.setElementAt(pBackRightThigh101,pBackRightThigh101.getIndex());
		points.setElementAt(pBackRightThigh111,pBackRightThigh111.getIndex());
		points.setElementAt(pBackRightThigh011,pBackRightThigh011.getIndex());

		LineData backRightThighS0=buildLine(pBackRightLeg001,pBackRightThigh001,pBackRightThigh011,pBackRightLeg011,Renderer3D.CAR_LEFT);
		polyData.add(backRightThighS0);
		LineData backRightThighS1=buildLine(pBackRightLeg011,pBackRightThigh011,pBackRightThigh111,pBackRightLeg111,Renderer3D.CAR_FRONT);
		polyData.add(backRightThighS1);
		LineData backRightThighS2=buildLine(pBackRightLeg111,pBackRightThigh111,pBackRightThigh101,pBackRightLeg101,Renderer3D.CAR_RIGHT);
		polyData.add(backRightThighS2);
		LineData backRightThighS3=buildLine(pBackRightLeg101,pBackRightThigh101,pBackRightThigh001,pBackRightLeg001,Renderer3D.CAR_BACK);
		polyData.add(backRightThighS3);
		
		//frontLeftLeg
		
		BPoint pFrontLeftForearm000=new BPoint(0,y_side-leg_side,0,n++);
		BPoint pFrontLeftForearm100=new BPoint(leg_side,y_side-leg_side,0,n++);
		BPoint pFrontLeftForearm110=new BPoint(leg_side,y_side,0,n++);
		BPoint pFrontLeftForearm010=new BPoint(0,y_side,0,n++);
		
		points.setElementAt(pFrontLeftForearm000,pFrontLeftForearm000.getIndex());
		points.setElementAt(pFrontLeftForearm100,pFrontLeftForearm100.getIndex());
		points.setElementAt(pFrontLeftForearm110,pFrontLeftForearm110.getIndex());
		points.setElementAt(pFrontLeftForearm010,pFrontLeftForearm010.getIndex());
		
		LineData FrontLeftForearm=buildLine(pFrontLeftForearm000,pFrontLeftForearm010,pFrontLeftForearm110,pFrontLeftForearm100,Renderer3D.CAR_FRONT);
		polyData.add(FrontLeftForearm);

		
		BPoint pFrontLeftForearm001=new BPoint(0,y_side-leg_side,radius_length,n++);
		BPoint pFrontLeftForearm101=new BPoint(leg_side,y_side-leg_side,radius_length,n++);
		BPoint pFrontLeftForearm111=new BPoint(leg_side,y_side,radius_length,n++);
		BPoint pFrontLeftForearm011=new BPoint(0,y_side,radius_length,n++);
		
		points.setElementAt(pFrontLeftForearm001,pFrontLeftForearm001.getIndex());
		points.setElementAt(pFrontLeftForearm101,pFrontLeftForearm101.getIndex());
		points.setElementAt(pFrontLeftForearm111,pFrontLeftForearm111.getIndex());
		points.setElementAt(pFrontLeftForearm011,pFrontLeftForearm011.getIndex());
		
		LineData FrontLeftForearmS0=buildLine(pFrontLeftForearm000,pFrontLeftForearm001,pFrontLeftForearm011,pFrontLeftForearm010,Renderer3D.CAR_LEFT);
		polyData.add(FrontLeftForearmS0);
		LineData FrontLeftForearmS1=buildLine(pFrontLeftForearm010,pFrontLeftForearm011,pFrontLeftForearm111,pFrontLeftForearm110,Renderer3D.CAR_FRONT);
		polyData.add(FrontLeftForearmS1);
		LineData FrontLeftForearmS2=buildLine(pFrontLeftForearm110,pFrontLeftForearm111,pFrontLeftForearm101,pFrontLeftForearm100,Renderer3D.CAR_RIGHT);
		polyData.add(FrontLeftForearmS2);
		LineData FrontLeftForearmS3=buildLine(pFrontLeftForearm100,pFrontLeftForearm101,pFrontLeftForearm001,pFrontLeftForearm000,Renderer3D.CAR_BACK);
		polyData.add(FrontLeftForearmS3);
		
		//left arm
		
		BPoint pFrontLeftArm001=new BPoint(0,y_side-leg_side,radius_length+humerus_length,n++);
		BPoint pFrontLeftArm101=new BPoint(leg_side,y_side-leg_side,radius_length+humerus_length,n++);
		BPoint pFrontLeftArm111=new BPoint(leg_side,y_side,radius_length+humerus_length,n++);
		BPoint pFrontLeftArm011=new BPoint(0,y_side,radius_length+humerus_length,n++);
		
		points.setElementAt(pFrontLeftArm001,pFrontLeftArm001.getIndex());
		points.setElementAt(pFrontLeftArm101,pFrontLeftArm101.getIndex());
		points.setElementAt(pFrontLeftArm111,pFrontLeftArm111.getIndex());
		points.setElementAt(pFrontLeftArm011,pFrontLeftArm011.getIndex());
		
		LineData topLeftArm=buildLine(pFrontLeftArm001,pFrontLeftArm101,pFrontLeftArm111,pFrontLeftArm011,Renderer3D.CAR_TOP);
		polyData.add(topLeftArm);
		
		LineData FrontLeftArmS0=buildLine(pFrontLeftForearm000,pFrontLeftArm001,pFrontLeftArm011,pFrontLeftForearm010,Renderer3D.CAR_LEFT);
		polyData.add(FrontLeftArmS0);
		LineData FrontLeftArmS1=buildLine(pFrontLeftForearm010,pFrontLeftArm011,pFrontLeftArm111,pFrontLeftForearm110,Renderer3D.CAR_FRONT);
		polyData.add(FrontLeftArmS1);
		LineData FrontLeftArmS2=buildLine(pFrontLeftForearm110,pFrontLeftArm111,pFrontLeftArm101,pFrontLeftForearm100,Renderer3D.CAR_RIGHT);
		polyData.add(FrontLeftArmS2);
		LineData FrontLeftArmS3=buildLine(pFrontLeftForearm100,pFrontLeftArm101,pFrontLeftArm001,pFrontLeftForearm000,Renderer3D.CAR_BACK);
		polyData.add(FrontLeftArmS3);
		
		//frontRightLeg
		
		BPoint pFrontRightForearm000=new BPoint(x_side-leg_side,y_side-leg_side,0,n++);
		BPoint pFrontRightForearm100=new BPoint(x_side,y_side-leg_side,0,n++);
		BPoint pFrontRightForearm110=new BPoint(x_side,y_side,0,n++);
		BPoint pFrontRightForearm010=new BPoint(x_side-leg_side,y_side,0,n++);
		
		points.setElementAt(pFrontRightForearm000,pFrontRightForearm000.getIndex());
		points.setElementAt(pFrontRightForearm100,pFrontRightForearm100.getIndex());
		points.setElementAt(pFrontRightForearm110,pFrontRightForearm110.getIndex());
		points.setElementAt(pFrontRightForearm010,pFrontRightForearm010.getIndex());
		
		LineData FrontRightForearm=buildLine(pFrontRightForearm000,pFrontRightForearm010,pFrontRightForearm110,pFrontRightForearm100,Renderer3D.CAR_FRONT);
		polyData.add(FrontRightForearm);

		
		BPoint pFrontRightForearm001=new BPoint(x_side-leg_side,y_side-leg_side,radius_length,n++);
		BPoint pFrontRightForearm101=new BPoint(x_side,y_side-leg_side,radius_length,n++);
		BPoint pFrontRightForearm111=new BPoint(x_side,y_side,radius_length,n++);
		BPoint pFrontRightForearm011=new BPoint(x_side-leg_side,y_side,radius_length,n++);
		
		points.setElementAt(pFrontRightForearm001,pFrontRightForearm001.getIndex());
		points.setElementAt(pFrontRightForearm101,pFrontRightForearm101.getIndex());
		points.setElementAt(pFrontRightForearm111,pFrontRightForearm111.getIndex());
		points.setElementAt(pFrontRightForearm011,pFrontRightForearm011.getIndex());
		
		LineData frontRightForearmS0=buildLine(pFrontRightForearm000,pFrontRightForearm001,pFrontRightForearm011,pFrontRightForearm010,Renderer3D.CAR_LEFT);
		polyData.add(frontRightForearmS0);
		LineData frontRightForearmS1=buildLine(pFrontRightForearm010,pFrontRightForearm011,pFrontRightForearm111,pFrontRightForearm110,Renderer3D.CAR_FRONT);
		polyData.add(frontRightForearmS1);
		LineData frontRightForearmS2=buildLine(pFrontRightForearm110,pFrontRightForearm111,pFrontRightForearm101,pFrontRightForearm100,Renderer3D.CAR_RIGHT);
		polyData.add(frontRightForearmS2);
		LineData frontRightForearmS3=buildLine(pFrontRightForearm100,pFrontRightForearm101,pFrontRightForearm001,pFrontRightForearm000,Renderer3D.CAR_BACK);
		polyData.add(frontRightForearmS3);
		

		//right arm
		
		BPoint pFrontRightArm001=new BPoint(x_side-leg_side,y_side-leg_side,radius_length+humerus_length,n++);
		BPoint pFrontRightArm101=new BPoint(x_side,y_side-leg_side,radius_length+humerus_length,n++);
		BPoint pFrontRightArm111=new BPoint(x_side,y_side,radius_length+humerus_length,n++);
		BPoint pFrontRightArm011=new BPoint(x_side-leg_side,y_side,radius_length+humerus_length,n++);
		
		points.setElementAt(pFrontRightArm001,pFrontRightArm001.getIndex());
		points.setElementAt(pFrontRightArm101,pFrontRightArm101.getIndex());
		points.setElementAt(pFrontRightArm111,pFrontRightArm111.getIndex());
		points.setElementAt(pFrontRightArm011,pFrontRightArm011.getIndex());
		
		LineData topRightArm=buildLine(pFrontRightArm001,pFrontRightArm101,pFrontRightArm111,pFrontRightArm011,Renderer3D.CAR_TOP);
		polyData.add(topRightArm);
		
		LineData frontRightArmS0=buildLine(pFrontRightForearm000,pFrontRightArm001,pFrontRightArm011,pFrontRightForearm010,Renderer3D.CAR_LEFT);
		polyData.add(frontRightArmS0);
		LineData frontRightArmS1=buildLine(pFrontRightForearm010,pFrontRightArm011,pFrontRightArm111,pFrontRightForearm110,Renderer3D.CAR_FRONT);
		polyData.add(frontRightArmS1);
		LineData frontRightArmS2=buildLine(pFrontRightForearm110,pFrontRightArm111,pFrontRightArm101,pFrontRightForearm100,Renderer3D.CAR_RIGHT);
		polyData.add(frontRightArmS2);
		LineData frontRightArmS3=buildLine(pFrontRightForearm100,pFrontRightArm101,pFrontRightArm001,pFrontRightForearm000,Renderer3D.CAR_BACK);
		polyData.add(frontRightArmS3);
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}




	

	



}	