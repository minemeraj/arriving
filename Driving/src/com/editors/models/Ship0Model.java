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
	
	private double dxr = 0;
	private double dyr = 0;
	private double dzr = 0;
	
	private double dxRoof = 0;
	private double dyRoof = 0;
	private double dzRoof = 0;

	
	double x0=0;
	double y0=0;
	double z0=0;
	
	private int[][][] faces;
	
	public Ship0Model(
			double dx, double dy, double dz, 
			double dxr, double dyr, double dzr,
			double dxRoof, double dyRoof, double dzRoof
			) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		
		this.dxr = dxr;
		this.dyr = dyr;
		this.dzr = dzr;
		
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
		int ny=3;
		
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
		
		hull[1][0]=addBPoint(0, 0.5, 1.0,s0);
		hull[1][1]=addBPoint(0.125, 0.5, 0.35,s0);
		hull[1][2]=addBPoint(0.25, 0.5, 0.2,s0);
		hull[1][3]=addBPoint(0.375, 0.5, 0.05,s0);
		hull[1][4]=addBPoint(0.5, 0.5, 0,s0);
		hull[1][5]=addBPoint(0.625, 0.5, 0.05,s0);
		hull[1][6]=addBPoint(0.75, 0.5, 0.2,s0);
		hull[1][7]=addBPoint(0.875, 0.5, 0.35,s0);
		hull[1][8]=addBPoint(1.0, 0.5, 1.0,s0);
		
		hull[2][0]=addBPoint(0, 1.0, 1.0,s0);
		hull[2][1]=addBPoint(0.125, 1.0, 0.35,s0);
		hull[2][2]=addBPoint(0.25, 1.0, 0.2,s0);
		hull[2][3]=addBPoint(0.375, 1.0, 0.05,s0);
		hull[2][4]=addBPoint(0.5, 1.0, 0,s0);
		hull[2][5]=addBPoint(0.625, 1.0, 0.05,s0);
		hull[2][6]=addBPoint(0.75, 1.0, 0.2,s0);
		hull[2][7]=addBPoint(0.875, 1.0, 0.35,s0);
		hull[2][8]=addBPoint(1.0, 1.0, 1.0,s0);
		
		Segments s1=new Segments(x0,dxRoof,y0+dyr,dyRoof,z0+dz,dzRoof);
		
		BPoint[][] mainBridge=new BPoint[2][4];
		
		mainBridge[0][0]=addBPoint(0.0,0.0,0,s1);
		mainBridge[0][1]=addBPoint(1.0,0.0,0,s1);
		mainBridge[0][2]=addBPoint(1.0,1.0,0,s1);
		mainBridge[0][3]=addBPoint(0.0,1.0,0,s1);		
		
		mainBridge[1][0]=addBPoint(0.0,0.0,1.0,s1);
		mainBridge[1][1]=addBPoint(1.0,0.0,1.0,s1);
		mainBridge[1][2]=addBPoint(1.0,1.0,1.0,s1);
		mainBridge[1][3]=addBPoint(0.0,1.0,1.0,s1);
		

		//Texture points

		double y=by;
		double x=bx;
		
		double dxt=200;
		double dyt=200;

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

		//faces
		//hull
		int NF=(nx-1)*(ny-1);
		//hull closures
		NF+=(nx-1);
		//deck
		NF+=ny-1;
		//main bridge
		NF+=6;
		
		int c0=0;
		int c1=1;
		int c2=2;
		int c3=3;
		
		int c4=4;
		int c5=5;
		int c6=6;
		int c7=7;
		
		faces=new int[NF][3][4];


		//build the hull
		int counter=0;
		
		for (int i = 0; i < ny-1; i++) {
			for (int j = 0; j < nx-1; j++) {
				faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, hull[i][j],hull[i+1][j],hull[i+1][j+1],hull[i][j+1], c0, c1, c2, c3);
			}
			
		}
		
		//closing front hull
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, hull[ny-1][0],hull[ny-1][nx-1],hull[ny-1][nx-2],hull[ny-1][1], c0, c1, c2, c3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, hull[ny-1][1],hull[ny-1][nx-2],hull[ny-1][nx-3],hull[ny-1][2], c0, c1, c2, c3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, hull[ny-1][2],hull[ny-1][nx-3],hull[ny-1][nx-4],hull[ny-1][3], c0, c1, c2, c3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, hull[ny-1][3],hull[ny-1][nx-4],hull[ny-1][4], 0, 1, 2);

		
		//closing back hull
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, hull[0][0],hull[0][1],hull[0][nx-2],hull[0][nx-1], c0, c1, c2, c3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, hull[0][1],hull[0][2],hull[0][nx-3],hull[0][nx-2], c0, c1, c2, c3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, hull[0][2],hull[0][3],hull[0][nx-4],hull[0][nx-3], c0, c1, c2, c3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, hull[0][3],hull[0][4],hull[0][nx-4], 0, 1, 2);
		
		//build the main deck		
		for (int i = 0; i < ny-1; i++) {
			faces[counter++]=buildFace(Renderer3D.CAR_TOP, hull[i][0],hull[i][nx-1],hull[i+1][nx-1],hull[i+1][0], c4,c5,c6,c7);
		}
		
	
		
		
		//mainBridge
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, mainBridge[0][0],mainBridge[0][3],mainBridge[0][2],mainBridge[0][1], c4,c5,c6,c7);
		
		for (int k = 0; k < 2-1; k++) {
			
			faces[counter++]=buildFace(Renderer3D.CAR_LEFT, mainBridge[k][0],mainBridge[k+1][0],mainBridge[k+1][3],mainBridge[k][3], c4,c5,c6,c7);
			faces[counter++]=buildFace(Renderer3D.CAR_BACK, mainBridge[k][0],mainBridge[k][1],mainBridge[k+1][1],mainBridge[k+1][0], c4,c5,c6,c7);
			faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, mainBridge[k][1],mainBridge[k][2],mainBridge[k+1][2],mainBridge[k+1][1], c4,c5,c6,c7);
			faces[counter++]=buildFace(Renderer3D.CAR_FRONT, mainBridge[k][2],mainBridge[k][3],mainBridge[k+1][3],mainBridge[k+1][2], c4,c5,c6,c7);
			
		}
		
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,mainBridge[2-1][0],mainBridge[2-1][1],mainBridge[2-1][2],mainBridge[2-1][3], c4,c5,c6,c7);
		

		IMG_WIDTH=(int) (2*bx+dxt+dxt);
		IMG_HEIGHT=(int) (2*by+dyt);

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
