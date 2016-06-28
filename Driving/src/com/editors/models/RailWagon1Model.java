package com.editors.models;

import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.Segments;
import com.main.Renderer3D;
/**
 * One texture model
 * Summing up the best creation logic so far
 * 
 * @author Administrator
 *
 */
public class RailWagon1Model extends RailWagon0Model{

	public RailWagon1Model(
			double dx, double dy, double dz, 
			double dxFront, double dyFront, double dzFront, 
			double dxRear, double dyRear,	double dzRear, 
			double dxRoof, double dyRoof,double dzRoof,
			double wheelRadius, double wheelWidth,int wheelRays) {
		super(dx, dy, dz, 
				dxFront, dyFront, dzFront, 
				dxRear, dyRear, dzRear, 
				dxRoof, dyRoof, dzRoof, 
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
		int NF=6*3;
		
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
		
		int rnx=2;
		int rny=2;
		int rnz=2;

		wagon=new BPoint[rnx][rny][rnz];

		double rdy=(dy-dyRoof)*0.5;

		Segments r0=new Segments(0,dxRoof,rdy,dyRoof,dzRear+dz,dzRoof);

		wagon[0][0][0]=addBPoint(-0.5,0.0,0,r0);
		wagon[1][0][0]=addBPoint(0.5,0.0,0,r0);
		wagon[0][0][1]=addBPoint(-0.5,0.0,1.0,r0);
		wagon[1][0][1]=addBPoint(0.5,0.0,1.0,r0);

		wagon[0][1][0]=addBPoint(-0.5,1.0,0,r0);
		wagon[1][1][0]=addBPoint(0.5,1.0,0,r0);
		wagon[0][1][1]=addBPoint(-0.5,1.0,1.0,r0);
		wagon[1][1][1]=addBPoint(0.5,1.0,1.0,r0);
	
		
	}

	@Override
	protected int buildWagonFaces(int counter, BPoint[][][] wagon) {
		
		faces[counter++]=buildFace(Renderer3D.CAR_TOP, wagon[0][0][1],wagon[1][0][1],wagon[1][1][1],wagon[0][1][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_LEFT, wagon[0][0][0],wagon[0][0][1],wagon[0][1][1],wagon[0][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, wagon[1][0][0],wagon[1][1][0],wagon[1][1][1],wagon[1][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_FRONT, wagon[0][1][0],wagon[0][1][1],wagon[1][1][1],wagon[1][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, wagon[0][0][0],wagon[1][0][0],wagon[1][0][1],wagon[0][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, wagon[0][0][0],wagon[0][1][0],wagon[1][1][0],wagon[1][0][0], 0, 1, 2, 3);
		
		return counter;
		
	}

	
	
	
}
