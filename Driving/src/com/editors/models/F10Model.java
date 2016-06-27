package com.editors.models;

import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.Segments;
import com.main.Renderer3D;

public class F10Model extends MeshModel{
	
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
	private double dRear = 0;
	
	private double dxRoof;
	private double dyRoof;
	private double dzRoof;
	
	private double wheelRadius;
	private double wheelWidth;
	private int wheel_rays;
	
	private int[][][] faces;
	
	double x0=0;
	double y0=0;
	double z0=0;

	
	public F10Model(
			double dx, double dy, double dz, 
			double dxf, double dyf, double dzf, 
			double dxr, double dyr,	double dzr,
			double dxRoof,double dyRoof,double dzRoof,
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
		this.dRear = dzr;
		
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
		
		Segments rs=new Segments(0,dx*0.5,0,50,dRear,30);
		
		BPoint[][][] backSpoiler=new BPoint[2][2][2];
		 
		backSpoiler[0][0][0]=addBPoint(-1.0,0.0,0,rs);
		backSpoiler[1][0][0]=addBPoint(1.0,0.0,0,rs);
		backSpoiler[0][1][0]=addBPoint(-1.0,1.0,0,rs);
		backSpoiler[1][1][0]=addBPoint(1.0,1.0,0,rs);		
		
		backSpoiler[0][0][1]=addBPoint(-1.0,0.0,1.0,rs);
		backSpoiler[1][0][1]=addBPoint(1.0,0.0,1.0,rs);
		backSpoiler[0][1][1]=addBPoint(-1.0,1.0,1.0,rs);
		backSpoiler[1][1][1]=addBPoint(1.0,1.0,1.0,rs);
		

		
		Segments s0=new Segments(0,dxRear*0.5,0,dyRear,0,dRear);
		
		BPoint[][][] back=new BPoint[2][2][2];
		
		back[0][0][0]=addBPoint(-1.0,0.0,0,s0);
		back[1][0][0]=addBPoint(1.0,0.0,0,s0);
		back[0][1][0]=addBPoint(-1.0,1.0,0,s0); 
		back[1][1][0]=addBPoint(1.0,1.0,0,s0);		
		
		back[0][0][1]=addBPoint(-1.0,0.0,1.0,s0);
		back[1][0][1]=addBPoint(1.0,0.0,1.0,s0);
		back[0][1][1]=addBPoint(-1.0,1.0,1.0,s0);
		back[1][1][1]=addBPoint(1.0,1.0,1.0,s0);

		

		
		Segments s1=new Segments(0,dx*0.5,dyRear,dy,0,dz);
		
		BPoint[][][] body=new BPoint[2][2][2];
		
		body[0][0][0]=addBPoint(-1.0,0.0,0,s1);
		body[1][0][0]=addBPoint(1.0,0.0,0,s1);
		body[0][1][0]=addBPoint(-1.0,1.0,0,s1); 
		body[1][1][0]=addBPoint(1.0,1.0,0,s1);		
		
		body[0][0][1]=addBPoint(-1.0,0.0,1.0,s1);
		body[1][0][1]=addBPoint(1.0,0.0,1.0,s1);
		body[0][1][1]=addBPoint(-1.0,1.0,1.0,s1);
		body[1][1][1]=addBPoint(1.0,1.0,1.0,s1);

		
		
		Segments s2=new Segments(0,dxFront*0.5,dyRear+dy,dyFront,0,dzFront);
		
		BPoint[][][] front=new BPoint[2][2][2];
		
		front[0][0][0]=addBPoint(-1.0,0.0,0,s2);
		front[1][0][0]=addBPoint(1.0,0.0,0,s2);
		front[0][1][0]=addBPoint(-1.0,1.0,0,s2);
		front[1][1][0]=addBPoint(1.0,1.0,0,s2);		
		
		front[0][0][1]=addBPoint(-1.0,0.0,1.0,s2);
		front[1][0][1]=addBPoint(1.0,0.0,1.0,s2);
		front[0][1][1]=addBPoint(-1.0,1.0,1.0,s2);
		front[1][1][1]=addBPoint(1.0,1.0,1.0,s2);
		

		
		
		Segments s3=new Segments(0,dxRoof*0.5,dyRear,dyRoof,dz,dzRoof);
		
		BPoint[][][] roof=new BPoint[2][2][2];
		
		roof[0][0][0]=addBPoint(-1.0,0.0,0,s3);
		roof[1][0][0]=addBPoint(1.0,0.0,0,s3);
		roof[0][1][0]=addBPoint(-1.0,1.0,0,s3);
		roof[1][1][0]=addBPoint(1.0,1.0,0,s3);		
		
		roof[0][0][1]=addBPoint(-1.0,0.0,1.0,s3);
		roof[1][0][1]=addBPoint(1.0,0.0,1.0,s3);
		roof[0][1][1]=addBPoint(-1.0,1.0,1.0,s3);
		roof[1][1][1]=addBPoint(1.0,1.0,1.0,s3);

		
		Segments fs=new Segments(0,dx*0.5,dyRear+dy+dyFront,50,0,30);
		
		BPoint[][][] frontSpoiler=new BPoint[2][2][2];
		 
		frontSpoiler[0][0][0]=addBPoint(-1.0,0.0,0,fs);
		frontSpoiler[1][0][0]=addBPoint(1.0,0.0,0,fs);
		frontSpoiler[0][1][0]=addBPoint(-1.0,1.0,0,fs);
		frontSpoiler[1][1][0]=addBPoint(1.0,1.0,0,fs);		
		
		frontSpoiler[0][0][1]=addBPoint(-1.0,0.0,1.0,fs);
		frontSpoiler[1][0][1]=addBPoint(1.0,0.0,1.0,fs);
		frontSpoiler[0][1][1]=addBPoint(-1.0,1.0,1.0,fs);
		frontSpoiler[1][1][1]=addBPoint(1.0,1.0,1.0,fs);
		
		double wz=0;
		double wx=wheelWidth;		

		BPoint[][] wheelLeftFront=buildWheel(-dx*0.5-wx, dyRear+dy+dyFront*0.5,wz , wheelRadius, wheelWidth, wheel_rays);
		BPoint[][] wheelRightFront=buildWheel(dx*0.5, dyRear+dy+dyFront*0.5, wz, wheelRadius, wheelWidth, wheel_rays);
		BPoint[][] wheelLeftRear=buildWheel(-dx*0.5-wx, dyRear*0.5, wz, wheelRadius, wheelWidth, wheel_rays);
		BPoint[][] wheelRightRear=buildWheel(dx*0.5, dyRear*0.5, wz, wheelRadius, wheelWidth, wheel_rays);

		
		int totWheelPolygon=wheel_rays+2*(wheel_rays-2);
		int NUM_WHEEL_FACES=4*totWheelPolygon;

		//faces
		int NF=6*6;
		
		int counter=0;

		faces=new int[NF+NUM_WHEEL_FACES][3][4];
		
		counter=buildBodyFaces(counter,backSpoiler,back,body,front,frontSpoiler,roof);

		counter=buildWheelFaces(counter,totBlockTexturesPoints,totWheelPolygon,
				
				wheelLeftFront,
				wheelRightFront,
				wheelLeftRear,
				wheelRightRear
				);


		IMG_WIDTH=(int) (2*bx+dx+wheelWidth);
		IMG_HEIGHT=(int) (2*by+dy);

	}
	
	private int buildWheelFaces(int counter, int totBlockTexturesPoints, int totWheelPolygon,
			BPoint[][] wheelLeftFront,
			BPoint[][] wheelRightFront,
			BPoint[][] wheelLeftRear,
			BPoint[][] wheelRightRear) {
		
		
		
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
		return 0;
	}


	private int buildBodyFaces(int counter, 
			BPoint[][][] backSpoiler, 
			BPoint[][][] back, 
			BPoint[][][] body, 
			BPoint[][][] front, 
			BPoint[][][] frontSpoiler, 
			BPoint[][][] roof) {
		
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,backSpoiler[0][0][1],backSpoiler[1][0][1],backSpoiler[1][1][1],backSpoiler[0][1][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_LEFT,backSpoiler[0][0][0],backSpoiler[0][0][1],backSpoiler[0][1][1],backSpoiler[0][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_RIGHT,backSpoiler[1][0][0],backSpoiler[1][1][0],backSpoiler[1][1][1],backSpoiler[1][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK,backSpoiler[0][1][0],backSpoiler[0][1][1],backSpoiler[1][1][1],backSpoiler[1][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK,backSpoiler[0][0][0],backSpoiler[1][0][0],backSpoiler[1][0][1],backSpoiler[0][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,backSpoiler[0][0][0],backSpoiler[0][1][0],backSpoiler[1][1][0],backSpoiler[1][0][0], 0, 1, 2, 3);
		
		
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,back[0][0][1],back[1][0][1],back[1][1][1],back[0][1][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,back[0][0][0],back[0][0][1],back[0][1][1],back[0][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,back[1][0][0],back[1][1][0],back[1][1][1],back[1][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,back[0][1][0],back[0][1][1],back[1][1][1],back[1][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,back[0][0][0],back[1][0][0],back[1][0][1],back[0][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,back[0][0][0],back[0][1][0],back[1][1][0],back[1][0][0], 0, 1, 2, 3);
		
		
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,body[0][0][1],body[1][0][1],body[1][1][1],body[0][1][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_LEFT,body[0][0][0],body[0][0][1],body[0][1][1],body[0][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_RIGHT,body[1][0][0],body[1][1][0],body[1][1][1],body[1][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_FRONT,body[0][1][0],body[0][1][1],body[1][1][1],body[1][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK,body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,body[0][0][0],body[0][1][0],body[1][1][0],body[1][0][0], 0, 1, 2, 3);


		faces[counter++]=buildFace(Renderer3D.CAR_TOP,front[0][0][1],front[1][0][1],front[1][1][1],front[0][1][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_LEFT,front[0][0][0],front[0][0][1],front[0][1][1],front[0][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_RIGHT,front[1][0][0],front[1][1][0],front[1][1][1],front[1][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_FRONT,front[0][1][0],front[0][1][1],front[1][1][1],front[1][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK,front[0][0][0],front[1][0][0],front[1][0][1],front[0][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,front[0][0][0],front[0][1][0],front[1][1][0],front[1][0][0], 0, 1, 2, 3);
		
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,frontSpoiler[0][0][1],frontSpoiler[1][0][1],frontSpoiler[1][1][1],frontSpoiler[0][1][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_LEFT,frontSpoiler[0][0][0],frontSpoiler[0][0][1],frontSpoiler[0][1][1],frontSpoiler[0][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_RIGHT,frontSpoiler[1][0][0],frontSpoiler[1][1][0],frontSpoiler[1][1][1],frontSpoiler[1][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK,frontSpoiler[0][1][0],frontSpoiler[0][1][1],frontSpoiler[1][1][1],frontSpoiler[1][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK,frontSpoiler[0][0][0],frontSpoiler[1][0][0],frontSpoiler[1][0][1],frontSpoiler[0][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,frontSpoiler[0][0][0],frontSpoiler[0][1][0],frontSpoiler[1][1][0],frontSpoiler[1][0][0], 0, 1, 2, 3);
		
		
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,roof[0][0][1],roof[1][0][1],roof[1][1][1],roof[0][1][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_LEFT,roof[0][0][0],roof[0][0][1],roof[0][1][1],roof[0][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_RIGHT,roof[1][0][0],roof[1][1][0],roof[1][1][1],roof[1][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK,roof[0][1][0],roof[0][1][1],roof[1][1][1],roof[1][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK,roof[0][0][0],roof[1][0][0],roof[1][0][1],roof[0][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,roof[0][0][0],roof[0][1][0],roof[1][1][0],roof[1][0][0], 0, 1, 2, 3);
		
		return counter;
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
