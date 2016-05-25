package com.editors.models;

import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.Segments;
import com.main.Renderer3D;

public class StarShip0Model extends MeshModel{

	private int bx=10;
	private int by=10;

	private double dx = 0;
	private double dy = 0;
	private double dz = 0;

	private double dxf = 0;
	private double dyf = 0;
	private double dzf = 0;

	private double dxr = 0;
	private double dyr = 0;
	private double dzr = 0;

	private int[][][] faces;

	double x0=0;
	double y0=0;
	double z0=0;

	public StarShip0Model(
			double dx, double dy, double dz, 
			double dxf, double dyf, double dzf, 
			double dxr, double dyr,	double dzr) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;

		this.dxf = dxf;
		this.dyf = dyf;
		this.dzf = dzf;

		this.dxr = dxr;
		this.dyr = dyr;
		this.dzr = dzr;
	}



	@Override
	public void initMesh() {
		points=new Vector<Point3D>();
		texturePoints=new Vector();

		Segments s0=new Segments(x0-dxf*0.5,dxf,y0+dy,dyf,z0+dz*2,dzf);

		int nz=2;

		BPoint[][] head=new BPoint[2][4];
		head[0][0] = addBPoint(0.0,0.0,0.0,s0);
		head[0][1] = addBPoint(1.0,0.0,0.0,s0);
		head[0][2] = addBPoint(1.0,1.0,0.0,s0);
		head[0][3] = addBPoint(0.0,1.0,0.0,s0);

		head[1][0] = addBPoint(0.0,0.0,1.0,s0);
		head[1][1] = addBPoint(1.0,0.0,1.0,s0);
		head[1][2] = addBPoint(1.0,1.0,1.0,s0);
		head[1][3] = addBPoint(0.0,1.0,1.0,s0);
		
		s0=new Segments(x0-dx*0.5,dx,y0,dy,z0,dz);


		BPoint[][] body=new BPoint[2][4];
		body[0][0] = addBPoint(0.0,0.0,0.0,s0);
		body[0][1] = addBPoint(1.0,0.0,0.0,s0);
		body[0][2] = addBPoint(1.0,1.0,0.0,s0);
		body[0][3] = addBPoint(0.0,1.0,0.0,s0);

		body[1][0] = addBPoint(0.0,0.0,1.0,s0);
		body[1][1] = addBPoint(1.0,0.0,1.0,s0);
		body[1][2] = addBPoint(1.0,1.0,1.0,s0);
		body[1][3] = addBPoint(0.0,1.0,1.0,s0);
		
		double y0Wing=dy-dyr;
		
		s0=new Segments(x0-dxf*2-dx*0.5,dx,y0Wing,dy,z0+dz*2,dz);


		BPoint[][] leftWing=new BPoint[2][4];
		leftWing[0][0] = addBPoint(0.0,0.0,0.0,s0);
		leftWing[0][1] = addBPoint(1.0,0.0,0.0,s0);
		leftWing[0][2] = addBPoint(1.0,1.0,0.0,s0);
		leftWing[0][3] = addBPoint(0.0,1.0,0.0,s0);

		leftWing[1][0] = addBPoint(0.0,0.0,1.0,s0);
		leftWing[1][1] = addBPoint(1.0,0.0,1.0,s0);
		leftWing[1][2] = addBPoint(1.0,1.0,1.0,s0);
		leftWing[1][3] = addBPoint(0.0,1.0,1.0,s0);
		
		s0=new Segments(x0+dxf*2+dx*0.5,dx,y0Wing,dy,z0+dz*2,dz);

		BPoint[][] rightWing=new BPoint[2][4];
		rightWing[0][0] = addBPoint(0.0,0.0,0.0,s0);
		rightWing[0][1] = addBPoint(1.0,0.0,0.0,s0);
		rightWing[0][2] = addBPoint(1.0,1.0,0.0,s0);
		rightWing[0][3] = addBPoint(0.0,1.0,0.0,s0);

		rightWing[1][0] = addBPoint(0.0,0.0,1.0,s0);
		rightWing[1][1] = addBPoint(1.0,0.0,1.0,s0);
		rightWing[1][2] = addBPoint(1.0,1.0,1.0,s0);
		rightWing[1][3] = addBPoint(0.0,1.0,1.0,s0);

		//Texture points

		double y=by;
		double x=bx;

		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx, y+dy,0);
		addTPoint(x,y+dy,0);

		//faces
		int NF=6*4;

		faces=new int[NF][3][4];

		int counter=0;

		//head
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, head[0][0],head[0][3],head[0][2],head[0][1], 0, 1, 2, 3);

		for (int k = 0; k < nz; k++) {

			faces[counter++]=buildFace(Renderer3D.CAR_LEFT, head[k][0],head[k+1][0],head[k+1][3],head[k][3], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_BACK, head[k][0],head[k][1],head[k+1][1],head[k+1][0], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, head[k][1],head[k][2],head[k+1][2],head[k+1][1], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_FRONT, head[k][2],head[k][3],head[k+1][3],head[k+1][2], 0, 1, 2, 3);

		}

		faces[counter++]=buildFace(Renderer3D.CAR_TOP,head[nz-1][0],head[nz-1][1],head[nz-1][2],head[nz-1][3], 0, 1, 2, 3);

		//body

		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, body[0][0],body[0][3],body[0][2],body[0][1], 0, 1, 2, 3);

		for (int k = 0; k < nz; k++) {

			faces[counter++]=buildFace(Renderer3D.CAR_LEFT, body[k][0],body[k+1][0],body[k+1][3],body[k][3], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_BACK, body[k][0],body[k][1],body[k+1][1],body[k+1][0], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, body[k][1],body[k][2],body[k+1][2],body[k+1][1], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_FRONT, body[k][2],body[k][3],body[k+1][3],body[k+1][2], 0, 1, 2, 3);

		}

		faces[counter++]=buildFace(Renderer3D.CAR_TOP,body[nz-1][0],body[nz-1][1],body[nz-1][2],body[nz-1][3], 0, 1, 2, 3);

		//left wing

		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, leftWing[0][0],leftWing[0][3],leftWing[0][2],leftWing[0][1], 0, 1, 2, 3);

		for (int k = 0; k < nz; k++) {

			faces[counter++]=buildFace(Renderer3D.CAR_LEFT, leftWing[k][0],leftWing[k+1][0],leftWing[k+1][3],leftWing[k][3], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_BACK, leftWing[k][0],leftWing[k][1],leftWing[k+1][1],leftWing[k+1][0], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, leftWing[k][1],leftWing[k][2],leftWing[k+1][2],leftWing[k+1][1], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_FRONT, leftWing[k][2],leftWing[k][3],leftWing[k+1][3],leftWing[k+1][2], 0, 1, 2, 3);

		}

		faces[counter++]=buildFace(Renderer3D.CAR_TOP,leftWing[nz-1][0],leftWing[nz-1][1],leftWing[nz-1][2],leftWing[nz-1][3], 0, 1, 2, 3);

		//right wing

		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, rightWing[0][0],rightWing[0][3],rightWing[0][2],rightWing[0][1], 0, 1, 2, 3);

		for (int k = 0; k < nz; k++) {

			faces[counter++]=buildFace(Renderer3D.CAR_LEFT, rightWing[k][0],rightWing[k+1][0],rightWing[k+1][3],rightWing[k][3], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_BACK, rightWing[k][0],rightWing[k][1],rightWing[k+1][1],rightWing[k+1][0], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, rightWing[k][1],rightWing[k][2],rightWing[k+1][2],rightWing[k+1][1], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_FRONT, rightWing[k][2],rightWing[k][3],rightWing[k+1][3],rightWing[k+1][2], 0, 1, 2, 3);

		}

		faces[counter++]=buildFace(Renderer3D.CAR_TOP,rightWing[nz-1][0],rightWing[nz-1][1],rightWing[nz-1][2],rightWing[nz-1][3], 0, 1, 2, 3);

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
