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
public class Tank0Model extends MeshModel{

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

	public Tank0Model(double dx, double dy, double dz) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}


	@Override
	public void initMesh() {
		points=new Vector<Point3D>();
		texturePoints=new Vector();


		Segments s0=new Segments(x0,y0,z0,dx,dy,dz);
		
		int nz=2;
		
		BPoint[][] cab=new BPoint[2][4];
		cab[0][0] = addBPoint(0.0,0.0,0.5,s0);
		cab[0][1] = addBPoint(1.0,0.0,0.5,s0);
		cab[0][2] = addBPoint(1.0,1.0,0.5,s0);
		cab[0][3] = addBPoint(0.0,1.0,0.5,s0);

		cab[1][0] = addBPoint(0.0,0.0,1.0,s0);
		cab[1][1] = addBPoint(1.0,0.0,1.0,s0);
		cab[1][2] = addBPoint(1.0,1.0,1.0,s0);
		cab[1][3] = addBPoint(0.0,1.0,1.0,s0);

		//Texture points

		double y=by;
		double x=bx;

		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx, y+dy,0);
		addTPoint(x,y+dy,0);

		//faces
		int NF=6;

		faces=new int[NF][3][4];

		int counter=0;

		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, cab[0][0],cab[0][3],cab[0][2],cab[0][1], 0, 1, 2, 3);
		
		for (int k = 0; k < nz; k++) {
			
			faces[counter++]=buildFace(Renderer3D.CAR_LEFT, cab[k][0],cab[k+1][0],cab[k+1][3],cab[k][3], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_BACK, cab[k][0],cab[k][1],cab[k+1][1],cab[k+1][0], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, cab[k][1],cab[k][2],cab[k+1][2],cab[k+1][1], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_FRONT, cab[k][2],cab[k][3],cab[k+1][3],cab[k+1][2], 0, 1, 2, 3);
			
		}

		faces[counter++]=buildFace(Renderer3D.CAR_TOP,cab[nz-1][0],cab[nz-1][1],cab[nz-1][2],cab[nz-1][3], 0, 1, 2, 3);

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
