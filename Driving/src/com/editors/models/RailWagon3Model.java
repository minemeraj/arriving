package com.editors.models;

import java.util.Vector;

import com.Point3D;
import com.Prism;
import com.Segments;
/**
 * One texture model
 * Summing up the best creation logic so far
 * 
 * @author Administrator
 *
 */
public class RailWagon3Model extends RailWagon2Model{

	
	public static String NAME="RailWagon";


	public RailWagon3Model(
			double dx, double dy, double dz, 
			double dxFront, double dyFront, double dzFront, 
			double dxRear, double dyRear,	double dzRear,
			double dxRoof,double dyRoof,double dzRoof,
			double rearOverhang, double frontOverhang, 
			double wheelRadius, double wheelWidth, int wheelRays
			) {

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

		buildTextures();


		//faces
		int NF=6*4;
		
		int totWheelPolygon=wheelRays+2*(wheelRays-2);
		int NUM_WHEEL_FACES=8*totWheelPolygon;

		faces=new int[NF+NUM_WHEEL_FACES][3][4];

		int counter=0;


		
		counter=buildBodyFaces(counter,w0,totWheelPolygon);
		
		counter=buildWagonFaces(counter);

	}

	@Override
	protected void buildWagon() {
		
	
		double urdy=(dy-dyRoof)*0.5;
		
		double lry_side=dy-dyFront-dyRear;
        double lrdy=(dy-lry_side)*0.5;
		
		Segments lr=new Segments(0,dx,lrdy,lry_side,dzRear+dz,dzRoof);
		
		Segments ur=new Segments(0,dxRoof,urdy,dyRoof,dzRear+dz,dzRoof);
		
		double dy=8.0/dyRoof;
		double dx=8.0/dxRoof;
			
		prismRight=new Prism(4);			
		
		prismRight.lowerBase[0]=addBPoint(0.5-dx,0,0,lr);
		prismRight.lowerBase[1]=addBPoint(0.5,0,0,lr);
		prismRight.lowerBase[2]=addBPoint(0.5,1.0,0,lr);
		prismRight.lowerBase[3]=addBPoint(0.5-dx,1.0,0,lr);
		
		prismRight.upperBase[0]=addBPoint(0.5-dx,0,1.0,ur);
		prismRight.upperBase[1]=addBPoint(0.5,0,1.0,ur);
		prismRight.upperBase[2]=addBPoint(0.5,1.0,1.0,ur);
		prismRight.upperBase[3]=addBPoint(0.5-dx,1.0,1.0,ur);

		
		
		prismLeft=new Prism(4);			
		
		prismLeft.lowerBase[0]=addBPoint(-0.5,0,0,lr);
		prismLeft.lowerBase[1]=addBPoint(-0.5+dx,0,0,lr);
		prismLeft.lowerBase[2]=addBPoint(-0.5+dx,1.0,0,lr);
		prismLeft.lowerBase[3]=addBPoint(-0.5,1.0,0,lr);
		
		prismLeft.upperBase[0]=addBPoint(-0.5,0,1.0,ur);
		prismLeft.upperBase[1]=addBPoint(-0.5+dx,0,1.0,ur);
		prismLeft.upperBase[2]=addBPoint(-0.5+dx,1.0,1.0,ur);
		prismLeft.upperBase[3]=addBPoint(-0.5,1.0,1.0,ur);
			
		
		prismBack=new Prism(4);			
		
		prismBack.lowerBase[0]=addBPoint(-(0.5-dx),0,0,lr);
		prismBack.lowerBase[1]=addBPoint(0.5-dx,0,0,lr);
		prismBack.lowerBase[2]=addBPoint(0.5-dx,dy,0,lr);
		prismBack.lowerBase[3]=addBPoint(-(0.5-dx),dy,0,lr);
		
		prismBack.upperBase[0]=addBPoint(-(0.5-dx),0,1.0,ur);
		prismBack.upperBase[1]=addBPoint(0.5-dx,0,1.0,ur);
		prismBack.upperBase[2]=addBPoint(0.5-dx,dy,1.0,ur);
		prismBack.upperBase[3]=addBPoint(-(0.5-dx),dy,1.0,ur);
		
		
		prismFront=new Prism(4);			
		
		prismFront.lowerBase[0]=addBPoint(-(0.5-dx),1-dy,0,lr);
		prismFront.lowerBase[1]=addBPoint(0.5-dx,1-dy,0,lr);
		prismFront.lowerBase[2]=addBPoint(0.5-dx,1.0,0,lr);
		prismFront.lowerBase[3]=addBPoint(-(0.5-dx),1.0,0,lr);
		
		prismFront.upperBase[0]=addBPoint(-(0.5-dx),1-dy,1.0,ur);
		prismFront.upperBase[1]=addBPoint(0.5-dx,1-dy,1.0,ur);
		prismFront.upperBase[2]=addBPoint(0.5-dx,1.0,1.0,ur);
		prismFront.upperBase[3]=addBPoint(-(0.5-dx),1.0,1.0,ur);


		
	}



}
