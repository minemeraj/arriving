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
	
	double leg_side=0;
	double leg_length=0;
	
	public static int ANIMAL_TYPE_QUADRUPED=0;
	public static int ANIMAL_TYPE_HUMAN=1;
	
	public int animal_type=ANIMAL_TYPE_HUMAN;


	public Animal(){}

	public Animal(double x_side, double y_side,double z_side,int animal_type,
			double leg_length,double leg_side
			) {
		super();

		this.x_side = x_side;
		this.y_side = y_side;
		this.z_side = z_side;
		this.animal_type = animal_type;
		this.leg_length = leg_length;
		this.leg_side = leg_side;
	}

	public Object clone(){

		Animal animal=new Animal(x_side,y_side,z_side,animal_type,leg_length,leg_side);
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
		ret+=","+leg_length+","+leg_side;
				
		return ret;		
	}

	public static Animal buildAnimal(String str) {

		String[] vals = str.split(",");


		double x_side =Double.parseDouble(vals[0]);
		double y_side = Double.parseDouble(vals[1]);
		double z_side = Double.parseDouble(vals[2]); 
		int animal_type =Integer.parseInt(vals[3]);
		double legLength = Double.parseDouble(vals[4]);
		double legSide = Double.parseDouble(vals[5]); 

		Animal grid=new Animal(x_side,y_side,z_side,animal_type,legLength,legSide);

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

	
	public double getLeg_length() {
		return leg_length;
	}

	public void setLeg_length(double leg_length) {
		this.leg_length = leg_length;
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

		Vector points=new Vector();
		points.setSize(100);

		Vector polyData=new Vector();

		int n=0;


		//body:

		BPoint body000=new BPoint(0,0,leg_length,n++);
		BPoint body100=new BPoint(x_side,0,leg_length,n++);
		BPoint body010=new BPoint(0,y_side,leg_length,n++);
		BPoint body110=new BPoint(x_side,y_side,leg_length,n++);

		BPoint body011=new BPoint(0,y_side,leg_length+z_side,n++);
		BPoint body101=new BPoint(x_side,0,leg_length+z_side,n++);
		BPoint body111=new BPoint(x_side,y_side,leg_length+z_side,n++);
		BPoint body001=new BPoint(0,0,leg_length+z_side,n++);

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
		
		double head_length=50;
		double hx=(x_side-head_length)/2.0;
		double hy=(y_side-head_length)/2.0;

		BPoint head000=new BPoint(hx,hy,leg_length+z_side,n++);
		BPoint head100=new BPoint(hx+head_length,hy,leg_length+z_side,n++);
		BPoint head010=new BPoint(hx,hy+head_length,leg_length+z_side,n++);
		BPoint head110=new BPoint(hx+head_length,hy+head_length,leg_length+z_side,n++);

		BPoint head011=new BPoint(hx,hy+head_length,leg_length+z_side+head_length,n++);
		BPoint head101=new BPoint(hx+head_length,hy,leg_length+z_side+head_length,n++);
		BPoint head111=new BPoint(hx+head_length,hy+head_length,leg_length+z_side+head_length,n++);
		BPoint head001=new BPoint(hx,hy,leg_length+z_side+head_length,n++);

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


		BPoint pBackLeftLeg001=new BPoint(0,0,leg_length,n++);
		BPoint pBackLeftLeg101=new BPoint(leg_side,0,leg_length,n++);
		BPoint pBackLeftLeg111=new BPoint(leg_side,leg_side,leg_length,n++);
		BPoint pBackLeftLeg011=new BPoint(0,leg_side,leg_length,n++);

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


		BPoint pBackRightLeg001=new BPoint(x_side-leg_side,0,leg_length,n++);
		BPoint pBackRightLeg101=new BPoint(x_side,0,leg_length,n++);
		BPoint pBackRightLeg111=new BPoint(x_side,leg_side,leg_length,n++);
		BPoint pBackRightLeg011=new BPoint(x_side-leg_side,leg_side,leg_length,n++);

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
		
		//Arms:
		
		//Left arm
		
		double ax=50;
		double az=leg_length;

		BPoint pFrontLeftArm000=new BPoint(-ax,y_side-leg_side,az,n++);
		BPoint pFrontLeftArm100=new BPoint(-ax+leg_side,y_side-leg_side,az,n++);
		BPoint pFrontLeftArm110=new BPoint(-ax+leg_side,y_side,az,n++);
		BPoint pFrontLeftArm010=new BPoint(-ax,y_side,az,n++);
		
		points.setElementAt(pFrontLeftArm000,pFrontLeftArm000.getIndex());
		points.setElementAt(pFrontLeftArm100,pFrontLeftArm100.getIndex());
		points.setElementAt(pFrontLeftArm110,pFrontLeftArm110.getIndex());
		points.setElementAt(pFrontLeftArm010,pFrontLeftArm010.getIndex());
		
		LineData bottomLeftArm=buildLine(pFrontLeftArm000,pFrontLeftArm010,pFrontLeftArm110,pFrontLeftArm100,Renderer3D.CAR_BOTTOM);
		polyData.add(bottomLeftArm);
		
		BPoint pFrontLeftArm001=new BPoint(-ax,y_side-leg_side,az+z_side,n++);
		BPoint pFrontLeftArm101=new BPoint(-ax+leg_side,y_side-leg_side,az+z_side,n++);
		BPoint pFrontLeftArm111=new BPoint(-ax+leg_side,y_side,az+z_side,n++);
		BPoint pFrontLeftArm011=new BPoint(-ax,y_side,az+z_side,n++);
		
		points.setElementAt(pFrontLeftArm001,pFrontLeftArm001.getIndex());
		points.setElementAt(pFrontLeftArm101,pFrontLeftArm101.getIndex());
		points.setElementAt(pFrontLeftArm111,pFrontLeftArm111.getIndex());
		points.setElementAt(pFrontLeftArm011,pFrontLeftArm011.getIndex());
		
		LineData topLeftArm=buildLine(pFrontLeftArm001,pFrontLeftArm101,pFrontLeftArm111,pFrontLeftArm011,Renderer3D.CAR_TOP);
		polyData.add(topLeftArm);
		
		LineData FrontLeftArmS0=buildLine(pFrontLeftArm000,pFrontLeftArm001,pFrontLeftArm011,pFrontLeftArm010,Renderer3D.CAR_LEFT);
		polyData.add(FrontLeftArmS0);
		LineData FrontLeftArmS1=buildLine(pFrontLeftArm010,pFrontLeftArm011,pFrontLeftArm111,pFrontLeftArm110,Renderer3D.CAR_FRONT);
		polyData.add(FrontLeftArmS1);
		LineData FrontLeftArmS2=buildLine(pFrontLeftArm110,pFrontLeftArm111,pFrontLeftArm101,pFrontLeftArm100,Renderer3D.CAR_RIGHT);
		polyData.add(FrontLeftArmS2);
		LineData FrontLeftArmS3=buildLine(pFrontLeftArm100,pFrontLeftArm101,pFrontLeftArm001,pFrontLeftArm000,Renderer3D.CAR_BACK);
		polyData.add(FrontLeftArmS3);
		
		//Right arm
		
		BPoint pFrontRightArm000=new BPoint(ax+x_side-leg_side,y_side-leg_side,az,n++);
		BPoint pFrontRightArm100=new BPoint(ax+x_side,y_side-leg_side,az,n++);
		BPoint pFrontRightArm110=new BPoint(ax+x_side,y_side,az,n++);
		BPoint pFrontRightArm010=new BPoint(ax+x_side-leg_side,y_side,az,n++);
		
		points.setElementAt(pFrontRightArm000,pFrontRightArm000.getIndex());
		points.setElementAt(pFrontRightArm100,pFrontRightArm100.getIndex());
		points.setElementAt(pFrontRightArm110,pFrontRightArm110.getIndex());
		points.setElementAt(pFrontRightArm010,pFrontRightArm010.getIndex());
		
		LineData FrontRightArm=buildLine(pFrontRightArm000,pFrontRightArm010,pFrontRightArm110,pFrontRightArm100,Renderer3D.CAR_BOTTOM);
		polyData.add(FrontRightArm);

		
		BPoint pFrontRightArm001=new BPoint(ax+x_side-leg_side,y_side-leg_side,az+z_side,n++);
		BPoint pFrontRightArm101=new BPoint(ax+x_side,y_side-leg_side,az+z_side,n++);
		BPoint pFrontRightArm111=new BPoint(ax+x_side,y_side,az+z_side,n++);
		BPoint pFrontRightArm011=new BPoint(ax+x_side-leg_side,y_side,az+z_side,n++);
		
		points.setElementAt(pFrontRightArm001,pFrontRightArm001.getIndex());
		points.setElementAt(pFrontRightArm101,pFrontRightArm101.getIndex());
		points.setElementAt(pFrontRightArm111,pFrontRightArm111.getIndex());
		points.setElementAt(pFrontRightArm011,pFrontRightArm011.getIndex());
		
		LineData topRightArm=buildLine(pFrontRightArm001,pFrontRightArm101,pFrontRightArm111,pFrontRightArm011,Renderer3D.CAR_TOP);
		polyData.add(topRightArm);
		
		LineData frontRightArmS0=buildLine(pFrontRightArm000,pFrontRightArm001,pFrontRightArm011,pFrontRightArm010,Renderer3D.CAR_LEFT);
		polyData.add(frontRightArmS0);
		LineData frontRightArmS1=buildLine(pFrontRightArm010,pFrontRightArm011,pFrontRightArm111,pFrontRightArm110,Renderer3D.CAR_FRONT);
		polyData.add(frontRightArmS1);
		LineData frontRightArmS2=buildLine(pFrontRightArm110,pFrontRightArm111,pFrontRightArm101,pFrontRightArm100,Renderer3D.CAR_RIGHT);
		polyData.add(frontRightArmS2);
		LineData frontRightArmS3=buildLine(pFrontRightArm100,pFrontRightArm101,pFrontRightArm001,pFrontRightArm000,Renderer3D.CAR_BACK);
		polyData.add(frontRightArmS3);
		
		///shoulders:
		
		double sx=ax-leg_side;
		double shDZ=20; 
		
		//left shoulder
		
		BPoint pFrontLeftSho000=new BPoint(-sx,y_side-leg_side,az+z_side-shDZ,n++);
		BPoint pFrontLeftSho100=new BPoint(-sx+leg_side,y_side-leg_side,az+z_side-shDZ,n++);
		BPoint pFrontLeftSho110=new BPoint(-sx+leg_side,y_side,az+z_side-shDZ,n++);
		BPoint pFrontLeftSho010=new BPoint(-sx,y_side,az+z_side-shDZ,n++);
		
		points.setElementAt(pFrontLeftSho000,pFrontLeftSho000.getIndex());
		points.setElementAt(pFrontLeftSho100,pFrontLeftSho100.getIndex());
		points.setElementAt(pFrontLeftSho110,pFrontLeftSho110.getIndex());
		points.setElementAt(pFrontLeftSho010,pFrontLeftSho010.getIndex());
		
		LineData FrontLeftSho=buildLine(pFrontLeftSho000,pFrontLeftSho010,pFrontLeftSho110,pFrontLeftSho100,Renderer3D.CAR_BOTTOM);
		polyData.add(FrontLeftSho);

		
		BPoint pFrontLeftSho001=new BPoint(-sx,y_side-leg_side,az+z_side,n++);
		BPoint pFrontLeftSho101=new BPoint(-sx+leg_side,y_side-leg_side,az+z_side,n++);
		BPoint pFrontLeftSho111=new BPoint(-sx+leg_side,y_side,az+z_side,n++);
		BPoint pFrontLeftSho011=new BPoint(-sx,y_side,az+z_side,n++);
		
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
		
		BPoint pFrontRightSho000=new BPoint(sx+x_side-leg_side,y_side-leg_side,az+z_side-shDZ,n++);
		BPoint pFrontRightSho100=new BPoint(sx+x_side,y_side-leg_side,az+z_side-shDZ,n++);
		BPoint pFrontRightSho110=new BPoint(sx+x_side,y_side,az+z_side-shDZ,n++);
		BPoint pFrontRightSho010=new BPoint(sx+x_side-leg_side,y_side,az+z_side-shDZ,n++);
		
		points.setElementAt(pFrontRightSho000,pFrontRightSho000.getIndex());
		points.setElementAt(pFrontRightSho100,pFrontRightSho100.getIndex());
		points.setElementAt(pFrontRightSho110,pFrontRightSho110.getIndex());
		points.setElementAt(pFrontRightSho010,pFrontRightSho010.getIndex());
		
		LineData FrontRightSho=buildLine(pFrontRightSho000,pFrontRightSho010,pFrontRightSho110,pFrontRightSho100,Renderer3D.CAR_BOTTOM);
		polyData.add(FrontRightSho);

		
		BPoint pFrontRightSho001=new BPoint(sx+x_side-leg_side,y_side-leg_side,az+z_side,n++);
		BPoint pFrontRightSho101=new BPoint(sx+x_side,y_side-leg_side,az+z_side,n++);
		BPoint pFrontRightSho111=new BPoint(sx+x_side,y_side,az+z_side,n++);
		BPoint pFrontRightSho011=new BPoint(sx+x_side-leg_side,y_side,az+z_side,n++);
		
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

		BPoint p000=new BPoint(0,0,leg_length,n++);
		BPoint p100=new BPoint(x_side,0,leg_length,n++);
		BPoint p010=new BPoint(0,y_side,leg_length,n++);
		BPoint p001=new BPoint(0,0,leg_length+z_side,n++);
		BPoint p110=new BPoint(x_side,y_side,leg_length,n++);
		BPoint p011=new BPoint(0,y_side,leg_length+z_side,n++);
		BPoint p101=new BPoint(x_side,0,leg_length+z_side,n++);
		BPoint p111=new BPoint(x_side,y_side,leg_length+z_side,n++);

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
		
		double headDZ=50;
		double headDX=50;
		double headDY=100;
		double hx=(x_side-headDZ)/2.0;
		double hy=(y_side-headDZ);
		
		double neck=50;
			
		double hz=leg_length+z_side+neck;

		BPoint head000=new BPoint(hx,hy,hz,n++);
		BPoint head100=new BPoint(hx+headDX,hy,hz,n++);
		BPoint head010=new BPoint(hx,hy+headDY,hz,n++);
		BPoint head110=new BPoint(hx+headDX,hy+headDY,hz,n++);

		BPoint head011=new BPoint(hx,hy+headDY,hz+headDZ,n++);
		BPoint head101=new BPoint(hx+headDX,hy,hz+headDZ,n++);
		BPoint head111=new BPoint(hx+headDX,hy+headDY,hz+headDZ,n++);
		BPoint head001=new BPoint(hx,hy,hz+headDZ,n++);

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
		
		double neckDX=50;
		double nz=leg_length+z_side;
	
		BPoint neck000=new BPoint(hx,hy,nz,n++);
		BPoint neck100=new BPoint(hx+neckDX,hy,nz,n++);
		BPoint neck010=new BPoint(hx,hy+neckDX,nz,n++);
		BPoint neck110=new BPoint(hx+neckDX,hy+neckDX,nz,n++);

		BPoint neck011=new BPoint(hx,hy+neckDX,nz+neck,n++);
		BPoint neck101=new BPoint(hx+neckDX,hy,nz+neck,n++);
		BPoint neck111=new BPoint(hx+neckDX,hy+neckDX,nz+neck,n++);
		BPoint neck001=new BPoint(hx,hy,nz+neck,n++);

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

		
		BPoint pBackLeftLeg001=new BPoint(0,0,leg_length,n++);
		BPoint pBackLeftLeg101=new BPoint(leg_side,0,leg_length,n++);
		BPoint pBackLeftLeg111=new BPoint(leg_side,leg_side,leg_length,n++);
		BPoint pBackLeftLeg011=new BPoint(0,leg_side,leg_length,n++);
		
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

		
		BPoint pBackRightLeg001=new BPoint(x_side-leg_side,0,leg_length,n++);
		BPoint pBackRightLeg101=new BPoint(x_side,0,leg_length,n++);
		BPoint pBackRightLeg111=new BPoint(x_side,leg_side,leg_length,n++);
		BPoint pBackRightLeg011=new BPoint(x_side-leg_side,leg_side,leg_length,n++);
		
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
		
		//frontLeftLeg
		
		BPoint pFrontLeftLeg000=new BPoint(0,y_side-leg_side,0,n++);
		BPoint pFrontLeftLeg100=new BPoint(leg_side,y_side-leg_side,0,n++);
		BPoint pFrontLeftLeg110=new BPoint(leg_side,y_side,0,n++);
		BPoint pFrontLeftLeg010=new BPoint(0,y_side,0,n++);
		
		points.setElementAt(pFrontLeftLeg000,pFrontLeftLeg000.getIndex());
		points.setElementAt(pFrontLeftLeg100,pFrontLeftLeg100.getIndex());
		points.setElementAt(pFrontLeftLeg110,pFrontLeftLeg110.getIndex());
		points.setElementAt(pFrontLeftLeg010,pFrontLeftLeg010.getIndex());
		
		LineData FrontLeftLeg=buildLine(pFrontLeftLeg000,pFrontLeftLeg010,pFrontLeftLeg110,pFrontLeftLeg100,Renderer3D.CAR_FRONT);
		polyData.add(FrontLeftLeg);

		
		BPoint pFrontLeftLeg001=new BPoint(0,y_side-leg_side,leg_length,n++);
		BPoint pFrontLeftLeg101=new BPoint(leg_side,y_side-leg_side,leg_length,n++);
		BPoint pFrontLeftLeg111=new BPoint(leg_side,y_side,leg_length,n++);
		BPoint pFrontLeftLeg011=new BPoint(0,y_side,leg_length,n++);
		
		points.setElementAt(pFrontLeftLeg001,pFrontLeftLeg001.getIndex());
		points.setElementAt(pFrontLeftLeg101,pFrontLeftLeg101.getIndex());
		points.setElementAt(pFrontLeftLeg111,pFrontLeftLeg111.getIndex());
		points.setElementAt(pFrontLeftLeg011,pFrontLeftLeg011.getIndex());
		
		LineData FrontLeftLegS0=buildLine(pFrontLeftLeg000,pFrontLeftLeg001,pFrontLeftLeg011,pFrontLeftLeg010,Renderer3D.CAR_LEFT);
		polyData.add(FrontLeftLegS0);
		LineData FrontLeftLegS1=buildLine(pFrontLeftLeg010,pFrontLeftLeg011,pFrontLeftLeg111,pFrontLeftLeg110,Renderer3D.CAR_FRONT);
		polyData.add(FrontLeftLegS1);
		LineData FrontLeftLegS2=buildLine(pFrontLeftLeg110,pFrontLeftLeg111,pFrontLeftLeg101,pFrontLeftLeg100,Renderer3D.CAR_RIGHT);
		polyData.add(FrontLeftLegS2);
		LineData FrontLeftLegS3=buildLine(pFrontLeftLeg100,pFrontLeftLeg101,pFrontLeftLeg001,pFrontLeftLeg000,Renderer3D.CAR_BACK);
		polyData.add(FrontLeftLegS3);
		
		//frontRightLeg
		
		BPoint pFrontRightLeg000=new BPoint(x_side-leg_side,y_side-leg_side,0,n++);
		BPoint pFrontRightLeg100=new BPoint(x_side,y_side-leg_side,0,n++);
		BPoint pFrontRightLeg110=new BPoint(x_side,y_side,0,n++);
		BPoint pFrontRightLeg010=new BPoint(x_side-leg_side,y_side,0,n++);
		
		points.setElementAt(pFrontRightLeg000,pFrontRightLeg000.getIndex());
		points.setElementAt(pFrontRightLeg100,pFrontRightLeg100.getIndex());
		points.setElementAt(pFrontRightLeg110,pFrontRightLeg110.getIndex());
		points.setElementAt(pFrontRightLeg010,pFrontRightLeg010.getIndex());
		
		LineData FrontRightLeg=buildLine(pFrontRightLeg000,pFrontRightLeg010,pFrontRightLeg110,pFrontRightLeg100,Renderer3D.CAR_FRONT);
		polyData.add(FrontRightLeg);

		
		BPoint pFrontRightLeg001=new BPoint(x_side-leg_side,y_side-leg_side,leg_length,n++);
		BPoint pFrontRightLeg101=new BPoint(x_side,y_side-leg_side,leg_length,n++);
		BPoint pFrontRightLeg111=new BPoint(x_side,y_side,leg_length,n++);
		BPoint pFrontRightLeg011=new BPoint(x_side-leg_side,y_side,leg_length,n++);
		
		points.setElementAt(pFrontRightLeg001,pFrontRightLeg001.getIndex());
		points.setElementAt(pFrontRightLeg101,pFrontRightLeg101.getIndex());
		points.setElementAt(pFrontRightLeg111,pFrontRightLeg111.getIndex());
		points.setElementAt(pFrontRightLeg011,pFrontRightLeg011.getIndex());
		
		LineData frontRightLegS0=buildLine(pFrontRightLeg000,pFrontRightLeg001,pFrontRightLeg011,pFrontRightLeg010,Renderer3D.CAR_LEFT);
		polyData.add(frontRightLegS0);
		LineData frontRightLegS1=buildLine(pFrontRightLeg010,pFrontRightLeg011,pFrontRightLeg111,pFrontRightLeg110,Renderer3D.CAR_FRONT);
		polyData.add(frontRightLegS1);
		LineData frontRightLegS2=buildLine(pFrontRightLeg110,pFrontRightLeg111,pFrontRightLeg101,pFrontRightLeg100,Renderer3D.CAR_RIGHT);
		polyData.add(frontRightLegS2);
		LineData frontRightLegS3=buildLine(pFrontRightLeg100,pFrontRightLeg101,pFrontRightLeg001,pFrontRightLeg000,Renderer3D.CAR_BACK);
		polyData.add(frontRightLegS3);
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	

	



}	