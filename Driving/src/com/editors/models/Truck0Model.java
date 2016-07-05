package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
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
public class Truck0Model extends MeshModel{

	protected int bx=10;
	protected int by=10;

	protected double dx = 0;
	protected double dy = 0;
	protected double dz = 0;
	
	protected double dxFront = 0;
	protected double dyFront = 0;
	protected double dzFront = 0;
	
	protected double dxRoof = 0;
	protected double dyRoof = 0;
	protected double dzRoof = 0;
	
	protected double x0=0;
	protected double y0=0;
	protected double z0=0;
	
	protected double wheelRadius;
	protected double wheelWidth;
	protected int wheel_rays;

	protected int[][][] faces;

	protected int basePoints=4;
	
	
	
	//body textures
	protected int c0=0, c1=1, c2=2, c3=3;
	//wagon texture
	protected int wa0=4, wa1=5, wa2=6, wa3=7;
	//window texture
	protected int wi0=8, wi1=9, wi2=10, wi3=11;
	//wheel texture
	protected int w0=12, w1=13, w2=14, w3=15;
	
	public Truck0Model(
			double dx, double dy, double dz, 
			double dxFront, double dyfront, double dzFront, 
			double dxRoof, double dyRoof, double dzRoof, 
			double wheelRadius, double wheelWidth, int wheel_rays) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		
		this.dxFront = dxFront;
		this.dyFront = dyfront;
		this.dzFront = dzFront;
		
		this.dxRoof = dxRoof;
		this.dyRoof = dyRoof;
		this.dzRoof = dzRoof;
		
		this.wheelRadius = wheelRadius;
		this.wheelWidth = wheelWidth;
		this.wheel_rays = wheel_rays;
	}


	@Override
	public void initMesh() {
		points=new Vector<Point3D>();
		texturePoints=new Vector();	
		
		x0=dxRoof*0.5;
		
		int nzCab=3;
		int nYcab=6;		
		BPoint[][][] cab=buildCabin(nYcab,nzCab);		
		
		int nzBody=2;				
		BPoint[][] body=buildBody(nzBody);

		int nzWagon=2;
		BPoint[][] wagon=buildWagon(nzWagon);

		buildTextures();

		////
		double wz=0;
		double wxLeft=dx*0.5+wheelWidth;
		double wxRight=dx*0.5;

		double yRearAxle=2.0*wheelRadius;
		double yFrontAxle=dy+dyFront*0.5;

		BPoint[][] wheelLeftFront=buildWheel(x0-wxLeft, yFrontAxle,wz , wheelRadius, wheelWidth, wheel_rays);
		BPoint[][] wheelRightFront=buildWheel(x0+wxRight, yFrontAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		BPoint[][] wheelLeftRear=buildWheel(x0-wxLeft, yRearAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		BPoint[][] wheelRightRear=buildWheel(x0+wxRight, yRearAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		
		int totWheelPolygon=wheel_rays+2*(wheel_rays-2);
		int NUM_WHEEL_FACES=4*totWheelPolygon;
		
		//faces
		int NF=2+(2+(nzCab-1))*(nYcab-1)*2;
		NF+=2+(nzBody-1)*4;
		NF+=2+(nzWagon-1)*4;

		faces=new int[NF+NUM_WHEEL_FACES][3][4];
		
		int counter=0;
		counter=buildBodyFaces(counter,nYcab,nzCab,nzBody,nzWagon,cab,body,wagon);
		counter=buildWheelFaces(counter,
				totWheelPolygon,
				wheelLeftFront,wheelRightFront,wheelLeftRear,wheelRightRear);



	}



	protected int buildBodyFaces(int counter,
			int nYcab,int nzCab,
			int nzRear,
			int nzWagon, 
			BPoint[][][] cab, 
			BPoint[][] body, 
			BPoint[][] wagon) {



		//cab
		for (int j = 0; j < nYcab-1; j++) {
			
			faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, cab[0][j][0],cab[0][j+1][0],cab[1][j+1][0],cab[1][j][0], c0, c1, c2, c3);
		
			for (int k = 0; k < nzCab-1; k++) {
				
				//set windows in the upper part
				int ca0=c0,ca1=c1,ca2=c2,ca3=c3; 
				if(k>0){
					ca0=wi0;ca1=wi1;ca2=wi2;ca3=wi3;
				}
				
				faces[counter++]=buildFace(Renderer3D.CAR_LEFT, cab[0][j][k],cab[0][j][k+1],cab[0][j+1][k+1],cab[0][j+1][k], ca0, ca1, ca2, ca3);
				if(j==0)
					faces[counter++]=buildFace(Renderer3D.CAR_BACK, cab[0][j][k],cab[1][j][k],cab[1][j][k+1],cab[0][j][k+1], c0, c1, c2, c3);
				faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, cab[1][j][k],cab[1][j+1][k],cab[1][j+1][k+1],cab[1][j][k+1], ca0, ca1, ca2, ca3);
				if(j==nYcab-2)
					faces[counter++]=buildFace(Renderer3D.CAR_FRONT, cab[0][nYcab-1][k],cab[0][nYcab-1][k+1],cab[1][nYcab-1][k+1],cab[1][nYcab-1][k], ca0, ca1, ca2, ca3);
				
				
			}
			faces[counter++]=buildFace(Renderer3D.CAR_TOP,cab[0][j][nzCab-1],cab[1][j][nzCab-1],cab[1][j+1][nzCab-1],cab[0][j+1][nzCab-1], c0, c1, c2, c3);
		}

		
		///////
		
		//rear
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, body[0][0],body[0][3],body[0][2],body[0][1], c0, c1, c2, c3);
		
		for (int k = 0; k < nzRear-1; k++) {
			
			faces[counter++]=buildFace(Renderer3D.CAR_LEFT, body[k][0],body[k+1][0],body[k+1][3],body[k][3], c0, c1, c2, c3);
			faces[counter++]=buildFace(Renderer3D.CAR_BACK, body[k][0],body[k][1],body[k+1][1],body[k+1][0], c0, c1, c2, c3);
			faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, body[k][1],body[k][2],body[k+1][2],body[k+1][1], c0, c1, c2, c3);
			faces[counter++]=buildFace(Renderer3D.CAR_FRONT, body[k][2],body[k][3],body[k+1][3],body[k+1][2], c0, c1, c2, c3);
			
		}

		faces[counter++]=buildFace(Renderer3D.CAR_TOP,body[nzRear-1][0],body[nzRear-1][1],body[nzRear-1][2],body[nzRear-1][3], c0, c1, c2, c3);
		///////
		counter=buildWagonFaces(counter,nzWagon,wagon); 
		

		return counter;
	}
	

	protected int buildWagonFaces(int counter, int nzWagon, BPoint[][] wagon) {
	
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, wagon[0][0],wagon[0][3],wagon[0][2],wagon[0][1], wa0, wa1, wa2, wa3);
		
		for (int k = 0; k < nzWagon-1; k++) {
			
			faces[counter++]=buildFace(Renderer3D.CAR_LEFT, wagon[k][0],wagon[k+1][0],wagon[k+1][3],wagon[k][3],  wa0, wa1, wa2, wa3);
			faces[counter++]=buildFace(Renderer3D.CAR_BACK, wagon[k][0],wagon[k][1],wagon[k+1][1],wagon[k+1][0],  wa0, wa1, wa2, wa3);
			faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, wagon[k][1],wagon[k][2],wagon[k+1][2],wagon[k+1][1],  wa0, wa1, wa2, wa3);
			faces[counter++]=buildFace(Renderer3D.CAR_FRONT, wagon[k][2],wagon[k][3],wagon[k+1][3],wagon[k+1][2],  wa0, wa1, wa2, wa3);
			
		}

		faces[counter++]=buildFace(Renderer3D.CAR_TOP,wagon[nzWagon-1][0],wagon[nzWagon-1][1],wagon[nzWagon-1][2],wagon[nzWagon-1][3],wa0, wa1, wa2, wa3);
		
		
		return counter;
	}


	protected int buildWheelFaces(
			
			int counter, 
			int totWheelPolygon,
			BPoint[][] wheelLeftFront, 
			BPoint[][] wheelRightFront, 
			BPoint[][] wheelLeftRear, 
			BPoint[][] wheelRightRear
			) {
		
		///// WHEELS

		int[][][] wFaces = buildWheelFaces(wheelLeftFront,w0);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++]=wFaces[i];
		}

		wFaces = buildWheelFaces(wheelRightFront,w0);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++]=wFaces[i];
		}

		wFaces = buildWheelFaces(wheelLeftRear,w0);
		for (int i = 0; i <totWheelPolygon; i++) {
			faces[counter++]=wFaces[i];
		}

		wFaces = buildWheelFaces(wheelRightRear,w0);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++]=wFaces[i];
		}
		
		return counter;

	}


	protected void buildTextures() {
		
		int shift=1;
		//body points

		double y=by;
		double x=bx;

		addTRect(x, y, dx, dy);
		
		//wagon points

		x=bx+dx+shift;
		y=by;

		addTRect(x, y, dx, dy);
		
		//window points

		x=bx+2*dx+2*shift;
		y=by;

		addTRect(x, y, dx, dy);


		//wheel texture, a black square for simplicity:

		x=bx+3*dx+3*shift;
		y=by;
		
		addTRect(x, y, wheelWidth, wheelWidth);

		IMG_WIDTH=(int) (2*bx+3*dx+wheelWidth+3*shift);
		IMG_HEIGHT=(int) (2*by+dy);
	}


	protected BPoint[][] buildBody(int nzBody) {
		
		Segments s0=new Segments(x0-dx*0.5,dx,y0,dy,z0,dz);
		
		BPoint[][] body=new BPoint[nzBody][4];
		
		body[0][0] = addBPoint(0.0,0.0,0.0,s0);
		body[0][1] = addBPoint(1.0,0.0,0.0,s0);
		body[0][2] = addBPoint(1.0,1.0,0.0,s0);
		body[0][3] = addBPoint(0.0,1.0,0.0,s0);
		
		body[1][0] = addBPoint(0.0,0.0,1.0,s0);
		body[1][1] = addBPoint(1.0,0.0,1.0,s0);
		body[1][2] = addBPoint(1.0,1.0,1.0,s0);
		body[1][3] = addBPoint(0.0,1.0,1.0,s0);
		
		return body;
	}


	protected BPoint[][][] buildCabin(int nYcab, int nzCab) {
		
		double wy=wheelRadius*1.0/dyFront;
		
		double fy0=0;
		double fy2=0.5-wy;
		double fy1=(0*0.25+fy2*0.75);		
		double fy3=0.5+wy;
		double fy5=1.0;
		double fy4=(fy3*0.75+fy5*0.25);
		
		double wz2=wheelRadius*1.0/dzFront;
		double wz3=wz2;
		
		double fz2=0.5;
		

		Segments s0=new Segments(x0-dxFront*0.5,dxFront,y0+dyRoof,dyFront,z0,dzFront);
	
		
		BPoint[][][] cab=new BPoint[2][nYcab][nzCab];
		cab[0][0][0] = addBPoint(0.0,fy0,0.0,s0);
		cab[0][0][1] = addBPoint(0.0,fy0,fz2,s0);
		cab[0][0][2] = addBPoint(0.0,fy0,1.0,s0);
		cab[1][0][0] = addBPoint(1.0,fy0,0.0,s0);
		cab[1][0][1] = addBPoint(1.0,fy0,fz2,s0);
		cab[1][0][2] = addBPoint(1.0,fy0,1.0,s0);
		
		cab[0][1][0] = addBPoint(0.0,fy1,0.0,s0);
		cab[0][1][1] = addBPoint(0.0,fy1,fz2,s0);
		cab[0][1][2] = addBPoint(0.0,fy1,1.0,s0);
		cab[1][1][0] = addBPoint(1.0,fy1,0.0,s0);
		cab[1][1][1] = addBPoint(1.0,fy1,fz2,s0);
		cab[1][1][2] = addBPoint(1.0,fy1,1.0,s0);
		
		cab[0][2][0] = addBPoint(0.0,fy2,wz2,s0);
		cab[0][2][1] = addBPoint(0.0,fy2,fz2,s0);
		cab[0][2][2] = addBPoint(0.0,fy2,1.0,s0);
		cab[1][2][0] = addBPoint(1.0,fy2,wz2,s0);
		cab[1][2][1] = addBPoint(1.0,fy2,fz2,s0);
		cab[1][2][2] = addBPoint(1.0,fy2,1.0,s0);
		
		cab[0][3][0] = addBPoint(0.0,fy3,wz3,s0);
		cab[0][3][1] = addBPoint(0.0,fy3,fz2,s0);
		cab[0][3][2] = addBPoint(0.0,fy3,1.0,s0);
		cab[1][3][0] = addBPoint(1.0,fy3,wz3,s0);
		cab[1][3][1] = addBPoint(1.0,fy3,fz2,s0);
		cab[1][3][2] = addBPoint(1.0,fy3,1.0,s0);
		
		cab[0][4][0] = addBPoint(0.0,fy4,0.0,s0);
		cab[0][4][1] = addBPoint(0.0,fy4,fz2,s0);
		cab[0][4][2] = addBPoint(0.0,fy4,1.0,s0);
		cab[1][4][0] = addBPoint(1.0,fy4,0.0,s0);
		cab[1][4][1] = addBPoint(1.0,fy4,fz2,s0);
		cab[1][4][2] = addBPoint(1.0,fy4,1.0,s0);
		
		cab[0][5][0] = addBPoint(0.0,fy5,0.0,s0);
		cab[0][5][1] = addBPoint(0.0,fy5,fz2,s0);
		cab[0][5][2] = addBPoint(0.0,fy5,1.0,s0);
		cab[1][5][0] = addBPoint(1.0,fy5,0.0,s0);
		cab[1][5][1] = addBPoint(1.0,fy5,fz2,s0);
		cab[1][5][2] = addBPoint(1.0,fy5,1.0,s0);
		
		return cab;

	}
	

	protected BPoint[][] buildWagon(int nzWagon) {
		

		

		Segments s0=new Segments(x0-dxRoof*0.5,dxRoof,y0,dyRoof,z0+dz,dzRoof);

		
		BPoint[][] wagon=new BPoint[nzWagon][4];
		wagon[0][0] = addBPoint(0.0,0.0,0.0,s0);
		wagon[0][1] = addBPoint(1.0,0.0,0.0,s0);
		wagon[0][2] = addBPoint(1.0,1.0,0.0,s0);
		wagon[0][3] = addBPoint(0.0,1.0,0.0,s0);
		
		wagon[1][0] = addBPoint(0.0,0.0,1.0,s0);
		wagon[1][1] = addBPoint(1.0,0.0,1.0,s0);
		wagon[1][2] = addBPoint(1.0,1.0,1.0,s0);
		wagon[1][3] = addBPoint(0.0,1.0,1.0,s0);
	
		return wagon;
	}


	@Override
	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);

	}




	@Override
	public void printTexture(Graphics2D bufGraphics) {
		
		bufGraphics.setColor(Color.BLACK);
		bufGraphics.setStroke(new BasicStroke(0.1f));

		for (int i = 0; i < faces.length; i++) {
			
			int[][] face = faces[i];
			int[] tPoints = face[2];
			if(tPoints.length==4)
				printTexturePolygon(bufGraphics, tPoints[0],tPoints[1],tPoints[2],tPoints[3]);
			else if(tPoints.length==3)
				printTexturePolygon(bufGraphics, tPoints[0],tPoints[1],tPoints[2]);
			
		}
		

	}

}
