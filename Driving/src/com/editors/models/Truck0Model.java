package com.editors.models;

import java.awt.Graphics2D;
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
	
	double x0=0;
	double y0=0;
	double z0=0;

	private int[][][] faces;

	int basePoints=4;

	public Truck0Model(double dx, double dy, double dz) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}


	@Override
	public void initMesh() {
		points=new Vector<Point3D>();
		texturePoints=new Vector();


		Segments s0=new Segments(x0,dx,y0+dy,dy,z0,dz);
		
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
		
		int nzRear=3;
		
		s0=new Segments(x0,dx,y0,dy,z0,dz*0.25);
		
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
		
		s0=new Segments(x0,dx,y0,dy,z0+dz*0.5,dz*0.75);
		
		BPoint[][] wagon=new BPoint[nzWagon][4];
		wagon[0][0] = addBPoint(0.0,0.2,0.0,s0);
		wagon[0][1] = addBPoint(1.0,0.2,0.0,s0);
		wagon[0][2] = addBPoint(1.0,1.0,0.0,s0);
		wagon[0][3] = addBPoint(0.0,1.0,0.0,s0);
		
		wagon[1][0] = addBPoint(0.0,0.2,1.0,s0);
		wagon[1][1] = addBPoint(1.0,0.2,1.0,s0);
		wagon[1][2] = addBPoint(1.0,1.0,1.0,s0);
		wagon[1][3] = addBPoint(0.0,1.0,1.0,s0);

		//Texture points

		double y=by;
		double x=bx;

		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx, y+dy,0);
		addTPoint(x,y+dy,0);

		//faces
		int NF=2+(nzCab-1)*4;
		NF+=2+(nzRear-1)*4;
		NF+=2+(nzWagon-1)*4;

		faces=new int[NF][3][4];

		int counter=0;

		//cab
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, cab[0][0],cab[0][3],cab[0][2],cab[0][1], 0, 1, 2, 3);
		
		for (int k = 0; k < nzCab; k++) {
			
			faces[counter++]=buildFace(Renderer3D.CAR_LEFT, cab[k][0],cab[k+1][0],cab[k+1][3],cab[k][3], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_BACK, cab[k][0],cab[k][1],cab[k+1][1],cab[k+1][0], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, cab[k][1],cab[k][2],cab[k+1][2],cab[k+1][1], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_FRONT, cab[k][2],cab[k][3],cab[k+1][3],cab[k+1][2], 0, 1, 2, 3);
			
		}

		faces[counter++]=buildFace(Renderer3D.CAR_TOP,cab[nzCab-1][0],cab[nzCab-1][1],cab[nzCab-1][2],cab[nzCab-1][3], 0, 1, 2, 3);
		///////
		
		//rear
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, rear[0][0],rear[0][3],rear[0][2],rear[0][1], 0, 1, 2, 3);
		
		for (int k = 0; k < nzRear; k++) {
			
			faces[counter++]=buildFace(Renderer3D.CAR_LEFT, rear[k][0],rear[k+1][0],rear[k+1][3],rear[k][3], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_BACK, rear[k][0],rear[k][1],rear[k+1][1],rear[k+1][0], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, rear[k][1],rear[k][2],rear[k+1][2],rear[k+1][1], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_FRONT, rear[k][2],rear[k][3],rear[k+1][3],rear[k+1][2], 0, 1, 2, 3);
			
		}

		faces[counter++]=buildFace(Renderer3D.CAR_TOP,rear[nzRear-1][0],rear[nzRear-1][1],rear[nzRear][2],rear[nzRear-1][3], 0, 1, 2, 3);
		///////
		
		//wagon
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, wagon[0][0],wagon[0][3],wagon[0][2],wagon[0][1], 0, 1, 2, 3);
		
		for (int k = 0; k < nzWagon; k++) {
			
			faces[counter++]=buildFace(Renderer3D.CAR_LEFT, wagon[k][0],wagon[k+1][0],wagon[k+1][3],wagon[k][3], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_BACK, wagon[k][0],wagon[k][1],wagon[k+1][1],wagon[k+1][0], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, wagon[k][1],wagon[k][2],wagon[k+1][2],wagon[k+1][1], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_FRONT, wagon[k][2],wagon[k][3],wagon[k+1][3],wagon[k+1][2], 0, 1, 2, 3);
			
		}

		faces[counter++]=buildFace(Renderer3D.CAR_TOP,wagon[nzWagon-1][0],wagon[nzWagon-1][1],wagon[nzWagon][2],wagon[nzWagon-1][3], 0, 1, 2, 3);
		///////
		
		
		IMG_WIDTH=(int) (2*bx+dx);
		IMG_HEIGHT=(int) (2*by+dy);

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
