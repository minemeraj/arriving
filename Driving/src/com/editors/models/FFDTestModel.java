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
public class FFDTestModel extends MeshModel{

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
	
	//body textures
	protected int[][] bo= {{0,1,2,3}};

	private int[][][] faces;

	private BPoint[][][][] cubes;
	private int numCubes=27;
	


	public FFDTestModel(
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
		texturePoints=new Vector<Point3D>();
		
		buildBody();
		
		buildTextures();

		//faces
		int NF=6*numCubes;

		faces=new int[NF][3][4];

		int counter=0;
		counter=buildFaces(counter);

	}


	private int buildFaces(int counter) {
		
		for (int c = 0; c < numCubes; c++) {
	
			faces[counter++]=buildFace(Renderer3D.CAR_TOP, cubes[c][0][0][1],cubes[c][1][0][1],cubes[c][1][1][1],cubes[c][0][1][1],bo[0]);
			faces[counter++]=buildFace(Renderer3D.CAR_LEFT, cubes[c][0][0][0],cubes[c][0][0][1],cubes[c][0][1][1],cubes[c][0][1][0], bo[0]);
			faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, cubes[c][1][0][0],cubes[c][1][1][0],cubes[c][1][1][1],cubes[c][1][0][1], bo[0]);
			faces[counter++]=buildFace(Renderer3D.CAR_FRONT, cubes[c][0][1][0],cubes[c][0][1][1],cubes[c][1][1][1],cubes[c][1][1][0], bo[0]);
			faces[counter++]=buildFace(Renderer3D.CAR_BACK,  cubes[c][0][0][0],cubes[c][1][0][0],cubes[c][1][0][1],cubes[c][0][0][1], bo[0]);
			faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, cubes[c][0][0][0],cubes[c][0][1][0],cubes[c][1][1][0],cubes[c][1][0][0], bo[0]);

		}
		
		return counter;
	}


	private void buildTextures() {
		
		
		//Texture points

		double y=by;
		double x=bx;

		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx, y+dy,0);
		addTPoint(x,y+dy,0);
		
		IMG_WIDTH=(int) (2*bx+dx);
		IMG_HEIGHT=(int) (2*by+dy);
		
	}


	private void buildBody() {
		
		
		
		cubes=new BPoint[numCubes][2][2][2];
		
		
		
		for (int i = 0; i < 3; i++) {
			
			for (int j = 0; j < 3; j++) {
				
				for (int k = 0; k < 3; k++) {
					
					
					int c=i+3*j+k*9;
					
					double xc0=dx*0.4*i+0.1;
					double yc0=dy*0.4*j+0.1;
					double zc0=dz*0.4*k+0.1;
					
					Segments s0=new Segments(xc0,dx*0.1,yc0,dy*0.1,zc0,dz*0.1);
					
					cubes[c][0][0][0]=addBPoint(-1.0,-1.0,-1,s0);
					cubes[c][1][0][0]=addBPoint(1.0,-1.0,-1,s0);
					cubes[c][0][1][0]=addBPoint(-1.0,1.0,-1,s0);
					cubes[c][1][1][0]=addBPoint(1.0,1.0,-1,s0);		
					
					cubes[c][0][0][1]=addBPoint(-1.0,-1.0,1.0,s0);
					cubes[c][1][0][1]=addBPoint(1.0,-1.0,1.0,s0);
					cubes[c][0][1][1]=addBPoint(-1.0,1.0,1.0,s0);
					cubes[c][1][1][1]=addBPoint(1.0,1.0,1.0,s0);
					
				}
				
		
				
			}
			
		

			
		}


		
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
