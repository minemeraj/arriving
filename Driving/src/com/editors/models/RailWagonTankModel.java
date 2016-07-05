package com.editors.models;

import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.main.Renderer3D;
/**
 * One texture model
 * Summing up the best creation logic so far
 * 
 * @author Administrator
 *
 */
public class RailWagonTankModel extends RailWagon0Model{

	private int wagonRays=20;

	public RailWagonTankModel(
			double dx, double dy, double dz, 
			double dxFront, double dyFront, double dzFront, 
			double dxRear, double dyRear,	double dzRear, 
			double dxRoof, double dyRoof,double dzRoof,
			double rearOverhang, double frontOverhang, 
			double wheelRadius, double wheelWidth,int wheelRays) {
		super(dx, dy, dz, 
				dxFront, dyFront, dzFront, 
				dxRear, dyRear, dzRear, 
				dxRoof, dyRoof, dzRoof, 
				rearOverhang, frontOverhang, 
				wheelRadius, wheelWidth, wheelRays);
	}
	
	@Override
	public void initMesh() {
		points=new Vector<Point3D>();
		texturePoints=new Vector();
		
		int pnx=2;
		int pny=2;
		int pnz=2;
		
		buildBody(pnx,pny,pnz);	
		
		buildWagon();

		
		int firstWheelTexturePoint=4;
		buildTextures();
		
		//faces
		int NF=6*3+wagonRays;
		
		int totWheelPolygon=wheelRays+2*(wheelRays-2);
		int NUM_WHEEL_FACES=8*totWheelPolygon;

		faces=new int[NF+NUM_WHEEL_FACES][3][4];

		int counter=0;
		
		counter=buildBodyFaces(counter,firstWheelTexturePoint,totWheelPolygon);
		
		counter=buildWagonFaces(counter,wagon);
		
		IMG_WIDTH=(int) (2*bx+dx);
		IMG_HEIGHT=(int) (2*by+dy);

	}
	
	@Override
	protected void buildWagon() {
		
		double rdy=(dy-dyRoof)*0.5;
		
		double radius=dxRoof*0.5;
		
		BPoint[][] cylinder = addYCylinder(0,rdy,dzRear+dz+radius,radius,dyRoof,wagonRays);
		wagon=new BPoint[1][][];
		wagon[0]=cylinder;

		
	}

	@Override
	protected int buildWagonFaces(int counter, BPoint[][][] wagon) {

		
		BPoint[][] cylinder =wagon[0];
		
		for (int i = 0; i < cylinder.length; i++) {
			
			faces[counter++]=buildFace(Renderer3D.CAR_TOP, 
					cylinder[i][0],
					cylinder[(i+1)%wagon.length][0],
					cylinder[(i+1)%wagon.length][1],
					cylinder[i][1], 
					b0, b1, b2, b3);
		}
		
		return counter;
		
	}

	
	
	
}
