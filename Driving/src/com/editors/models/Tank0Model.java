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
public class Tank0Model extends MeshModel{

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
	
	private double dxRoof;
	private double dyRoof;
	private double dzRoof;
	
	double x0=0;
	double y0=0;
	double z0=0;

	private int[][][] faces;

	int basePoints=4;
	


	public Tank0Model(
			double dx, double dy, double dz, 
			double dxf, double dyf, double dzf, 
			double dxr, double dyr,	double dzr,
			double dxRoof,double dyRoof,double dzRoof) {
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
		
		this.dxRoof = dxRoof;
		this.dyRoof = dyRoof;
		this.dzRoof = dzRoof;
	}


	@Override
	public void initMesh() {
		points=new Vector<Point3D>();
		texturePoints=new Vector();
		
		Segments s0=new Segments(0,dx*0.5,0,dy,0,dz);
		
		BPoint[][][] body=new BPoint[2][2][2];
		
		body[0][0][0]=addBPoint(-1.0,0.0,0,s0);
		body[1][0][0]=addBPoint(1.0,0.0,0,s0);
		body[0][1][0]=addBPoint(-1.0,1.0,0,s0);
		body[1][1][0]=addBPoint(1.0,1.0,0,s0);		
		
		body[0][0][1]=addBPoint(-1.0,0.0,1.0,s0);
		body[1][0][1]=addBPoint(1.0,0.0,1.0,s0);
		body[0][1][1]=addBPoint(-1.0,1.0,1.0,s0);
		body[1][1][1]=addBPoint(1.0,1.0,1.0,s0);

		
		
		Segments s1=new Segments(0,dxRoof*0.5,dyRear,dyRoof,dz,dzRoof);
		
		BPoint[][][] turret=new BPoint[2][2][2];
		
		turret[0][0][0]=addBPoint(-1.0,0.0,0,s1);
		turret[1][0][0]=addBPoint(1.0,0.0,0,s1);
		turret[0][1][0]=addBPoint(-1.0,1.0,0,s1);
		turret[1][1][0]=addBPoint(1.0,1.0,0,s1);		
		
		turret[0][0][1]=addBPoint(-1.0,0.0,1.0,s1);
		turret[1][0][1]=addBPoint(1.0,0.0,1.0,s1);
		turret[0][1][1]=addBPoint(-1.0,1.0,1.0,s1);
		turret[1][1][1]=addBPoint(1.0,1.0,1.0,s1);
		

		BPoint[][] cannon_barrel = addYCylinder(0,dyRoof+dyRear,dz+dzRear*0.5,10,100,10);

		//Texture points

		double y=by;
		double x=bx;

		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx, y+dy,0);
		addTPoint(x,y+dy,0);

		//faces
		int NF=6*2;

		faces=new int[NF+cannon_barrel.length][3][4];

		int counter=0;

		faces[counter++]=buildFace(Renderer3D.CAR_TOP, body[0][0][1],body[1][0][1],body[1][1][1],body[0][1][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_LEFT, body[0][0][0],body[0][0][1],body[0][1][1],body[0][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, body[1][0][0],body[1][1][0],body[1][1][1],body[1][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_FRONT, body[0][1][0],body[0][1][1],body[1][1][1],body[1][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, body[0][0][0],body[0][1][0],body[1][1][0],body[1][0][0], 0, 1, 2, 3);
		
		
		faces[counter++]=buildFace(Renderer3D.CAR_TOP, turret[0][0][1],turret[1][0][1],turret[1][1][1],turret[0][1][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_LEFT, turret[0][0][0],turret[0][0][1],turret[0][1][1],turret[0][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, turret[1][0][0],turret[1][1][0],turret[1][1][1],turret[1][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_FRONT, turret[0][1][0],turret[0][1][1],turret[1][1][1],turret[1][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, turret[0][0][0],turret[1][0][0],turret[1][0][1],turret[0][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, turret[0][0][0],turret[0][1][0],turret[1][1][0],turret[1][0][0], 0, 1, 2, 3);

		
		for (int i = 0; i < cannon_barrel.length; i++) {
			
			faces[counter++]=buildFace(Renderer3D.CAR_TOP, 
					cannon_barrel[i][0],
					cannon_barrel[(i+1)%cannon_barrel.length][0],
					cannon_barrel[(i+1)%cannon_barrel.length][1],
					cannon_barrel[i][1], 
					0, 1, 2, 3);
		}
		
		IMG_WIDTH=(int) (2*bx+dx);
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
