package com.editors.models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.Segments;
import com.main.Renderer3D;
/**
 * 
 * TWO TEXTURES
 * 
 * @author Administrator
 *
 */
public class Ship0Model extends MeshModel{

	private int bx=10;
	private int by=10;

	private double dx = 0;
	private double dy = 0;
	private double dz = 0;

	private double dxRear = 0;
	private double dyRear = 0;
	private double dzRear = 0;

	private double dxFront= 0;
	private double dyFront = 0;
	private double dzFront = 0;

	private double dxRoof = 0;
	private double dyRoof = 0;
	private double dzRoof = 0;


	double x0=0;
	double y0=0;
	double z0=0;

	private int[][][] faces;

	public Ship0Model(
			double dx, double dy, double dz, 
			double dxFront, double dyFront, double dzFront,
			double dxRear, double dyRear, double dzRear,
			double dxRoof, double dyRoof, double dzRoof
			) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;

		this.dxFront = dxFront;
		this.dyFront = dyFront;
		this.dzFront = dzFront;

		this.dxRear = dxRear;
		this.dyRear = dyRear;
		this.dzRear = dzRear;

		this.dxRoof = dxRoof;
		this.dyRoof = dyRoof;
		this.dzRoof = dzRoof;
	}





	@Override
	public void initMesh() {
		points=new Vector<Point3D>();
		texturePoints=new Vector();

		Segments s0=new Segments(x0,dx,y0,dy,z0,dz);

		int nx=9;
		int ny=4;


		double y1=dyRear/dy;
		double y2=(dy-dyFront)/dy;


		BPoint[][] hull=new BPoint[ny][nx];

		hull[0][0]=addBPoint(0, 0, 1.0,s0);
		hull[0][1]=addBPoint(0.125, 0, 0.35,s0);
		hull[0][2]=addBPoint(0.25, 0, 0.2,s0);
		hull[0][3]=addBPoint(0.375, 0, 0.05,s0);
		hull[0][4]=addBPoint(0.5, 0, 0,s0);
		hull[0][5]=addBPoint(0.625, 0, 0.05,s0);
		hull[0][6]=addBPoint(0.75, 0, 0.2,s0);
		hull[0][7]=addBPoint(0.875, 0, 0.35,s0);
		hull[0][8]=addBPoint(1.0, 0, 1.0,s0);

		hull[1][0]=addBPoint(0, y1, 1.0,s0);
		hull[1][1]=addBPoint(0.125, y1, 0.35,s0);
		hull[1][2]=addBPoint(0.25, y1, 0.2,s0);
		hull[1][3]=addBPoint(0.375, y1, 0.05,s0);
		hull[1][4]=addBPoint(0.5, y1, 0,s0);
		hull[1][5]=addBPoint(0.625, y1, 0.05,s0);
		hull[1][6]=addBPoint(0.75, y1, 0.2,s0);
		hull[1][7]=addBPoint(0.875,y1, 0.35,s0);
		hull[1][8]=addBPoint(1.0, y1, 1.0,s0);

		hull[2][0]=addBPoint(0, y2, 1.0,s0);
		hull[2][1]=addBPoint(0.125, y2, 0.35,s0);
		hull[2][2]=addBPoint(0.25, y2, 0.2,s0);
		hull[2][3]=addBPoint(0.375, y2, 0.05,s0);
		hull[2][4]=addBPoint(0.5, y2, 0,s0);
		hull[2][5]=addBPoint(0.625, y2, 0.05,s0);
		hull[2][6]=addBPoint(0.75, y2, 0.2,s0);
		hull[2][7]=addBPoint(0.875,y2, 0.35,s0);
		hull[2][8]=addBPoint(1.0, y2, 1.0,s0);

		hull[3][0]=addBPoint(0, 1.0, 1.0,s0);
		hull[3][1]=addBPoint(0.125, 1.0, 0.35,s0);
		hull[3][2]=addBPoint(0.25, 1.0, 0.2,s0);
		hull[3][3]=addBPoint(0.375, 1.0, 0.05,s0);
		hull[3][4]=addBPoint(0.5, 1.0, 0,s0);
		hull[3][5]=addBPoint(0.625, 1.0, 0.05,s0);
		hull[3][6]=addBPoint(0.75, 1.0, 0.2,s0);
		hull[3][7]=addBPoint(0.875, 1.0, 0.35,s0);
		hull[3][8]=addBPoint(1.0, 1.0, 1.0,s0);

		//Segments s1=new Segments(x0,dxRear,y0,dyRear,z0+dz,dzRear);


		BPoint[][] afterCastle=new BPoint[2][4];
		afterCastle[0][0]=hull[0][0];
		afterCastle[0][1]=hull[0][nx-1];
		afterCastle[0][2]=hull[1][nx-1];
		afterCastle[0][3]=hull[1][0];


		afterCastle[1][0]=addBPoint(hull[0][0].x,hull[0][0].y,hull[0][0].z+dzRear);
		afterCastle[1][1]=addBPoint(hull[0][nx-1].x,hull[0][nx-1].y,hull[0][nx-1].z+dzRear);
		afterCastle[1][2]=addBPoint(hull[1][nx-1].x,hull[1][nx-1].y,hull[1][nx-1].z+dzRear);
		afterCastle[1][3]=addBPoint(hull[1][0].x,hull[1][0].y,hull[1][0].z+dzRear);


		//Segments s2=new Segments(x0,dxFront,y0+dy-dyFront,dyFront,z0+dz,dzFront);

		BPoint[][] foreCastle=new BPoint[2][4];
		foreCastle[0][0]=hull[ny-2][0];
		foreCastle[0][1]=hull[ny-2][nx-1];
		foreCastle[0][2]=hull[ny-1][nx-1];
		foreCastle[0][3]=hull[ny-1][0];


		foreCastle[1][0]=addBPoint(hull[ny-2][0].x,hull[ny-2][0].y,hull[ny-2][0].z+dzFront);
		foreCastle[1][1]=addBPoint(hull[ny-2][nx-1].x,hull[ny-2][nx-1].y,hull[ny-2][nx-1].z+dzFront);
		foreCastle[1][2]=addBPoint(hull[ny-1][nx-1].x,hull[ny-1][nx-1].y,hull[ny-1][nx-1].z+dzFront);
		foreCastle[1][3]=addBPoint(hull[ny-1][0].x,hull[ny-1][0].y,hull[ny-1][0].z+dzFront);


		Segments s3=new Segments(x0,dxRoof,y0+dyRear-dyRoof,dyRoof,z0+dz+dzRear,dzRoof);

		BPoint[][] mainBridge=new BPoint[2][4];

		mainBridge[0][0]=addBPoint(0.0,0.0,0,s3);
		mainBridge[0][1]=addBPoint(1.0,0.0,0,s3);
		mainBridge[0][2]=addBPoint(1.0,1.0,0,s3);
		mainBridge[0][3]=addBPoint(0.0,1.0,0,s3);		

		mainBridge[1][0]=addBPoint(0.0,0.0,1.0,s3);
		mainBridge[1][1]=addBPoint(1.0,0.0,1.0,s3);
		mainBridge[1][2]=addBPoint(1.0,1.0,1.0,s3);
		mainBridge[1][3]=addBPoint(0.0,1.0,1.0,s3);

		double dxt=200;
		double dyt=200;
		
		buildTextures(dxt,dyt);


		//faces
		//hull
		int NF=(nx-1)*(ny-1);
		//hull closures
		NF+=(nx-1);
		//deck
		NF+=ny-1;
		//main bridge
		NF+=6;
		//castles
		NF+=12;

		faces=new int[NF][3][4];


		//build the hull
		int counter=0;
		counter=buildFaces(counter,nx,ny,hull,mainBridge,afterCastle,foreCastle);



		IMG_WIDTH=(int) (2*bx+dxt+dxt);
		IMG_HEIGHT=(int) (2*by+dyt);

	}

	private void buildTextures(double dxt, double dyt) {

		//Texture points

		double y=by;
		double x=bx;


		//hull
		addTPoint(x,y,0);
		addTPoint(x+dxt,y,0);
		addTPoint(x+dxt, y+dyt,0);
		addTPoint(x,y+dyt,0);


		//main deck		
		addTPoint(x+dxt,y,0);
		addTPoint(x+dxt+dxt,y,0);
		addTPoint(x+dxt+dxt, y+dyt,0);
		addTPoint(x+dxt,y+dyt,0);

	}

	private int buildFaces(int counter, int nx, int ny, BPoint[][] hull, BPoint[][] mainBridge, BPoint[][] afterCastle, BPoint[][] foreCastle) {

		int h0=0;
		int h1=1;
		int h2=2;
		int h3=3;

		int d0=4;
		int d1=5;
		int d2=6; 
		int d3=7;

		for (int i = 0; i < ny-1; i++) {
			for (int j = 0; j < nx-1; j++) {
				faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, hull[i][j],hull[i+1][j],hull[i+1][j+1],hull[i][j+1], h0, h1, h2, h3);
			}

		}

		//closing front hull
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, hull[ny-1][0],hull[ny-1][nx-1],hull[ny-1][nx-2],hull[ny-1][1], h0, h1, h2, h3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, hull[ny-1][1],hull[ny-1][nx-2],hull[ny-1][nx-3],hull[ny-1][2], h0, h1, h2, h3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, hull[ny-1][2],hull[ny-1][nx-3],hull[ny-1][nx-4],hull[ny-1][3], h0, h1, h2, h3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, hull[ny-1][3],hull[ny-1][nx-4],hull[ny-1][4], 0, 1, 2);


		//closing back hull
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, hull[0][0],hull[0][1],hull[0][nx-2],hull[0][nx-1], h0, h1, h2, h3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, hull[0][1],hull[0][2],hull[0][nx-3],hull[0][nx-2], h0, h1, h2, h3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, hull[0][2],hull[0][3],hull[0][nx-4],hull[0][nx-3], h0, h1, h2, h3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, hull[0][3],hull[0][4],hull[0][nx-4], 0, 1, 2);

		//build the main deck		
		for (int i = 0; i < ny-1; i++) {
			faces[counter++]=buildFace(Renderer3D.CAR_TOP, hull[i][0],hull[i][nx-1],hull[i+1][nx-1],hull[i+1][0], d0,d1,d2,d3);
		}

		//mainBridge
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, mainBridge[0][0],mainBridge[0][3],mainBridge[0][2],mainBridge[0][1], d0,d1,d2,d3);

		for (int k = 0; k < 2-1; k++) {

			faces[counter++]=buildFace(Renderer3D.CAR_LEFT, mainBridge[k][0],mainBridge[k+1][0],mainBridge[k+1][3],mainBridge[k][3], d0,d1,d2,d3);
			faces[counter++]=buildFace(Renderer3D.CAR_BACK, mainBridge[k][0],mainBridge[k][1],mainBridge[k+1][1],mainBridge[k+1][0], d0,d1,d2,d3);
			faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, mainBridge[k][1],mainBridge[k][2],mainBridge[k+1][2],mainBridge[k+1][1], d0,d1,d2,d3);
			faces[counter++]=buildFace(Renderer3D.CAR_FRONT, mainBridge[k][2],mainBridge[k][3],mainBridge[k+1][3],mainBridge[k+1][2], d0,d1,d2,d3);

		}

		faces[counter++]=buildFace(Renderer3D.CAR_TOP,mainBridge[2-1][0],mainBridge[2-1][1],mainBridge[2-1][2],mainBridge[2-1][3], d0,d1,d2,d3);

		////after castle
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, afterCastle[0][0],afterCastle[0][3],afterCastle[0][2],afterCastle[0][1], d0,d1,d2,d3);

		for (int k = 0; k < 2-1; k++) {

			faces[counter++]=buildFace(Renderer3D.CAR_LEFT, afterCastle[k][0],afterCastle[k+1][0],afterCastle[k+1][3],afterCastle[k][3], d0,d1,d2,d3);
			faces[counter++]=buildFace(Renderer3D.CAR_BACK, afterCastle[k][0],afterCastle[k][1],afterCastle[k+1][1],afterCastle[k+1][0], d0,d1,d2,d3);
			faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, afterCastle[k][1],afterCastle[k][2],afterCastle[k+1][2],afterCastle[k+1][1], d0,d1,d2,d3);
			faces[counter++]=buildFace(Renderer3D.CAR_FRONT, afterCastle[k][2],afterCastle[k][3],afterCastle[k+1][3],afterCastle[k+1][2], d0,d1,d2,d3);

		}

		faces[counter++]=buildFace(Renderer3D.CAR_TOP,afterCastle[2-1][0],afterCastle[2-1][1],afterCastle[2-1][2],afterCastle[2-1][3], d0,d1,d2,d3);

		///fore castle
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, foreCastle[0][0],foreCastle[0][3],foreCastle[0][2],foreCastle[0][1], d0,d1,d2,d3);

		for (int k = 0; k < 2-1; k++) {

			faces[counter++]=buildFace(Renderer3D.CAR_LEFT, foreCastle[k][0],foreCastle[k+1][0],foreCastle[k+1][3],foreCastle[k][3], d0,d1,d2,d3);
			faces[counter++]=buildFace(Renderer3D.CAR_BACK, foreCastle[k][0],foreCastle[k][1],foreCastle[k+1][1],foreCastle[k+1][0], d0,d1,d2,d3);
			faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, foreCastle[k][1],foreCastle[k][2],foreCastle[k+1][2],foreCastle[k+1][1], d0,d1,d2,d3);
			faces[counter++]=buildFace(Renderer3D.CAR_FRONT, foreCastle[k][2],foreCastle[k][3],foreCastle[k+1][3],foreCastle[k+1][2], d0,d1,d2,d3);

		}

		faces[counter++]=buildFace(Renderer3D.CAR_TOP,foreCastle[2-1][0],foreCastle[2-1][1],foreCastle[2-1][2],foreCastle[2-1][3], d0,d1,d2,d3);

		return counter;
	}





	@Override
	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);

	}

	@Override
	public void printTexture(Graphics2D bufGraphics) {


		bufGraphics.setColor(Color.RED);
		printTexturePolygon(bufGraphics, 0,1,2,3);

		bufGraphics.setColor(Color.BLACK);
		printTexturePolygon(bufGraphics, 4,5,6,7);
	}

}
