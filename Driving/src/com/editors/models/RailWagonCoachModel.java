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
public class RailWagonCoachModel extends RailWagonBulkModel{
	
	public static String NAME="RailWagon";
	
	
	private int rnumx=5;
	private int rnumy=2;


	public RailWagonCoachModel(
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
		texturePoints=new Vector<Point3D>();
		
		
		int pnx=2;
		int pny=2;
		int pnz=2;
		
		buildBody(pnx,pny,pnz);	
		
		buildWagon();

		buildTextures();


		//faces
		int NF=6*3+(rnumx-1)*(rnumy-1)+2;
		
		int totWheelPolygon=wheelRays+2*(wheelRays-2);
		int NUM_WHEEL_FACES=8*totWheelPolygon;

		faces=new int[NF+NUM_WHEEL_FACES][3][4];

		int counter=0;


		
		counter=buildBodyFaces(counter,w[0][0],totWheelPolygon);
		
		counter=buildWagonFaces(counter);

	}





	protected void buildWagon() {
		
		int pnx=2;
		int pny=2;
		int pnz=2;
		
		if(dzRoof>0){
		
			double h=dzRoof;
				double l=dxRoof*0.5;
				
				double r=h*0.5+l*l/(2.0*h);
				double zc=dzRear+dz+h*0.5-l*l/(2.0*h);
				
				double teta0=Math.atan((r-h)/l);			
				double teta1=Math.PI*0.25+0.5*teta0;
				
				wagon=new BPoint[1][rnumx][rnumy];
				
				wagon[0][0][0]=body[0][0][pnz-1];
				wagon[0][1][0]=addBPoint(-r*Math.cos(teta1),0,zc+r*Math.sin(teta1));
				wagon[0][2][0]=addBPoint(0,0,zc+r);
				wagon[0][3][0]=addBPoint(+r*Math.cos(teta1),0,zc+r*Math.sin(teta1));
				wagon[0][4][0]=body[pnx-1][0][pnz-1];
				
				wagon[0][0][1]=body[0][pny-1][pnz-1];
				wagon[0][1][1]=addBPoint(-r*Math.cos(teta1),dyRoof,zc+r*Math.sin(teta1));
				wagon[0][2][1]=addBPoint(0,dyRoof,zc+r);
				wagon[0][3][1]=addBPoint(+r*Math.cos(teta1),dyRoof,zc+r*Math.sin(teta1));
				wagon[0][4][1]=body[pnx-1][pny-1][pnz-1];

		}


		
	}
	
	@Override
	protected int buildWagonFaces(int counter) {

		
		BPoint[][] roof = wagon[0];
		
		for(int i=0;i<rnumx-1;i++){
			
			for (int j = 0; j < rnumy-1; j++) {
				
				faces[counter++]=buildFace(Renderer3D.CAR_TOP,roof[i][j],roof[i+1][j],roof[i+1][j+1],roof[i][j+1],bo[0]);
				
			}
			
			
		}
		
		//back roof
		faces[counter++]=buildFace(Renderer3D.CAR_BACK,roof[2][1],roof[1][1],roof[0][1],bo[0]);
		
		//front roof
		faces[counter++]=buildFace(Renderer3D.CAR_FRONT,roof[0][1],roof[1][1],roof[2][1],bo[0]);


		return counter;
	}



}
