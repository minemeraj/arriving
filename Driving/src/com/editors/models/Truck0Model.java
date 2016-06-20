package com.editors.models;

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

	private int bx=10;
	private int by=10;

	private double dx = 0;
	private double dy = 0;
	private double dz = 0;
	
	private double dxFront = 0;
	private double dyFront = 0;
	private double dzFront = 0;
	
	private double dxRear = 0;
	private double dyRear = 0;
	private double dzRear = 0;
	
	double x0=0;
	double y0=0;
	double z0=0;
	
	private double wheelRadius;
	private double wheelWidth;
	private int wheel_rays;

	private int[][][] faces;

	int basePoints=4;


	public Truck0Model(
			double dx, double dy, double dz, 
			double dxf, double dyf, double dzf, 
			double dxr, double dyr, double dzr, 
			double wheelRadius, double wheelWidth, int wheel_rays) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		
		this.dxFront = dxf;
		this.dyFront = dyf;
		this.dzFront = dzf;
		
		this.dxRear = dxr;
		this.dyRear = dyr;
		this.dzRear = dzr;
		
		this.wheelRadius = wheelRadius;
		this.wheelWidth = wheelWidth;
		this.wheel_rays = wheel_rays;
	}


	@Override
	public void initMesh() {
		points=new Vector<Point3D>();
		texturePoints=new Vector();


		Segments s0=new Segments(x0,dxFront,y0+dyRear,dyFront,z0,dzFront);
		
		int nzCab=3;
		
		BPoint[][] cab=new BPoint[nzCab][4];
		cab[0][0] = addBPoint(0.0,0.0,0.0,s0);
		cab[0][1] = addBPoint(1.0,0.0,0.0,s0);
		cab[0][2] = addBPoint(1.0,1.0,0.0,s0);
		cab[0][3] = addBPoint(0.0,1.0,0.0,s0);
		
		cab[1][0] = addBPoint(0.0,0.0,0.5,s0);
		cab[1][1] = addBPoint(1.0,0.0,0.5,s0);
		cab[1][2] = addBPoint(1.0,1.0,0.5,s0);
		cab[1][3] = addBPoint(0.0,1.0,0.5,s0);

		cab[2][0] = addBPoint(0.0,0.0,1.0,s0);
		cab[2][1] = addBPoint(1.0,0.0,1.0,s0);
		cab[2][2] = addBPoint(1.0,0.8,1.0,s0);
		cab[2][3] = addBPoint(0.0,0.8,1.0,s0);
		
		int nzRear=2;
		
		s0=new Segments(x0,dxRear,y0,dyRear,z0,dzRear);
		
		BPoint[][] rear=new BPoint[nzRear][4];
		rear[0][0] = addBPoint(0.0,0.0,0.0,s0);
		rear[0][1] = addBPoint(1.0,0.0,0.0,s0);
		rear[0][2] = addBPoint(1.0,1.0,0.0,s0);
		rear[0][3] = addBPoint(0.0,1.0,0.0,s0);
		
		rear[1][0] = addBPoint(0.0,0.0,1.0,s0);
		rear[1][1] = addBPoint(1.0,0.0,1.0,s0);
		rear[1][2] = addBPoint(1.0,1.0,1.0,s0);
		rear[1][3] = addBPoint(0.0,1.0,1.0,s0);
		
		int nzWagon=2;
		
		s0=new Segments(x0,dx,y0,dy,z0+dzRear,dz);
		
		BPoint[][] wagon=new BPoint[nzWagon][4];
		wagon[0][0] = addBPoint(0.0,0.0,0.0,s0);
		wagon[0][1] = addBPoint(1.0,0.0,0.0,s0);
		wagon[0][2] = addBPoint(1.0,1.0,0.0,s0);
		wagon[0][3] = addBPoint(0.0,1.0,0.0,s0);
		
		wagon[1][0] = addBPoint(0.0,0.0,1.0,s0);
		wagon[1][1] = addBPoint(1.0,0.0,1.0,s0);
		wagon[1][2] = addBPoint(1.0,1.0,1.0,s0);
		wagon[1][3] = addBPoint(0.0,1.0,1.0,s0);

		//Texture points

		double y=by;
		double x=bx;

		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx, y+dy,0);
		addTPoint(x,y+dy,0);
		
		int totBlockTexturesPoints=4;

		//wheel texture, a black square for simplicity:

		x=bx+dx;
		y=by;

		addTPoint(x,y,0);
		addTPoint(x+wheelWidth,y,0);
		addTPoint(x+wheelWidth,y+wheelWidth,0);
		addTPoint(x,y+wheelWidth,0);
		
		////
		double wz=0;
		double wx=dx*0.5-wheelWidth;		

		double yRearAxle=0.0;
		double yFrontAxle=1.0;

		BPoint[][] wheelLeftFront=buildWheel(-wx-wheelWidth, yFrontAxle,wz , wheelRadius, wheelWidth, wheel_rays);
		BPoint[][] wheelRightFront=buildWheel(wx, yFrontAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		BPoint[][] wheelLeftRear=buildWheel(-wx-wheelWidth, yRearAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		BPoint[][] wheelRightRear=buildWheel(wx, yRearAxle, wz, wheelRadius, wheelWidth, wheel_rays);

		int totWheelPolygon=wheel_rays+2*(wheel_rays-2);
		int NUM_WHEEL_FACES=4*totWheelPolygon;
		
		//faces
		int NF=2+(nzCab-1)*4;
		NF+=2+(nzRear-1)*4;
		NF+=2+(nzWagon-1)*4;

		faces=new int[NF+NUM_WHEEL_FACES][3][4];

		int counter=0;
		
		int c0=0;
		int c1=1;
		int c2=2;
		int c3=3;

		//cab
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, cab[0][0],cab[0][3],cab[0][2],cab[0][1], c0, c1, c2, c3);
		
		for (int k = 0; k < nzCab-1; k++) {
			
			faces[counter++]=buildFace(Renderer3D.CAR_LEFT, cab[k][0],cab[k+1][0],cab[k+1][3],cab[k][3], c0, c1, c2, c3);
			faces[counter++]=buildFace(Renderer3D.CAR_BACK, cab[k][0],cab[k][1],cab[k+1][1],cab[k+1][0], c0, c1, c2, c3);
			faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, cab[k][1],cab[k][2],cab[k+1][2],cab[k+1][1], c0, c1, c2, c3);
			faces[counter++]=buildFace(Renderer3D.CAR_FRONT, cab[k][2],cab[k][3],cab[k+1][3],cab[k+1][2], c0, c1, c2, c3);
			
		}

		faces[counter++]=buildFace(Renderer3D.CAR_TOP,cab[nzCab-1][0],cab[nzCab-1][1],cab[nzCab-1][2],cab[nzCab-1][3], c0, c1, c2, c3);
		///////
		
		//rear
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, rear[0][0],rear[0][3],rear[0][2],rear[0][1], c0, c1, c2, c3);
		
		for (int k = 0; k < nzRear-1; k++) {
			
			faces[counter++]=buildFace(Renderer3D.CAR_LEFT, rear[k][0],rear[k+1][0],rear[k+1][3],rear[k][3], c0, c1, c2, c3);
			faces[counter++]=buildFace(Renderer3D.CAR_BACK, rear[k][0],rear[k][1],rear[k+1][1],rear[k+1][0], c0, c1, c2, c3);
			faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, rear[k][1],rear[k][2],rear[k+1][2],rear[k+1][1], c0, c1, c2, c3);
			faces[counter++]=buildFace(Renderer3D.CAR_FRONT, rear[k][2],rear[k][3],rear[k+1][3],rear[k+1][2], c0, c1, c2, c3);
			
		}

		faces[counter++]=buildFace(Renderer3D.CAR_TOP,rear[nzRear-1][0],rear[nzRear-1][1],rear[nzRear-1][2],rear[nzRear-1][3], c0, c1, c2, c3);
		///////
		
		//wagon
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, wagon[0][0],wagon[0][3],wagon[0][2],wagon[0][1], c0, c1, c2, c3);
		
		for (int k = 0; k < nzWagon-1; k++) {
			
			faces[counter++]=buildFace(Renderer3D.CAR_LEFT, wagon[k][0],wagon[k+1][0],wagon[k+1][3],wagon[k][3], c0, c1, c2, c3);
			faces[counter++]=buildFace(Renderer3D.CAR_BACK, wagon[k][0],wagon[k][1],wagon[k+1][1],wagon[k+1][0], c0, c1, c2, c3);
			faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, wagon[k][1],wagon[k][2],wagon[k+1][2],wagon[k+1][1], c0, c1, c2, c3);
			faces[counter++]=buildFace(Renderer3D.CAR_FRONT, wagon[k][2],wagon[k][3],wagon[k+1][3],wagon[k+1][2], c0, c1, c2, c3);
			
		}

		faces[counter++]=buildFace(Renderer3D.CAR_TOP,wagon[nzWagon-1][0],wagon[nzWagon-1][1],wagon[nzWagon-1][2],wagon[nzWagon-1][3], c0, c1, c2, c3);
		///////
		
		///// WHEELS

		int[][][] wFaces = buildWheelFaces(wheelLeftFront,totBlockTexturesPoints);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++]=wFaces[i];
		}

		wFaces = buildWheelFaces(wheelRightFront,totBlockTexturesPoints);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++]=wFaces[i];
		}

		wFaces = buildWheelFaces(wheelLeftRear,totBlockTexturesPoints);
		for (int i = 0; i <totWheelPolygon; i++) {
			faces[counter++]=wFaces[i];
		}

		wFaces = buildWheelFaces(wheelRightRear,totBlockTexturesPoints);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++]=wFaces[i];
		}
		/////
		
		
		IMG_WIDTH=(int) (2*bx+dx+wheelWidth);
		IMG_HEIGHT=(int) (2*by+dy);

	}

	@Override
	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);

	}




	@Override
	public void printTexture(Graphics2D bufGraphics) {

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
