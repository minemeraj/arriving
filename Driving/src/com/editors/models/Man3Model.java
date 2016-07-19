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
public class Man3Model extends MeshModel{

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
	
	//body textures
	protected int[][] bo= {{0,1,2,3}};

	private int[][][] faces;	
	
	int nzBody=2;
	int radBody=6;
	private BPoint[][] body;
	
	int nzArm=2;
	int radArm=6;
	private BPoint[][] leftArm;

	public Man3Model(
			double dx, double dy, double dz, 
			double dxFront, double dyfront, double dzFront, 
			double dxr, double dyr,	double dzr,
			double dxRoof,double dyRoof,double dzRoof) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		
		this.dxFront = dxFront;
		this.dyFront = dyfront;
		this.dzFront = dzFront;
		
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
		buildArms();
		
		buildTextures();

		//faces
		int NF=radBody*(nzBody-1);
		NF+=radArm*(nzArm-1);

		faces=new int[NF][3][4];

		int counter=0;
		counter=buildFaces(counter);

	}




	private int buildFaces(int counter) {
		
		
		

		
		for (int k = 0; k < nzBody-1; k++) {
			
			for(int i=0;i<radBody;i++){
				
				int ii=(i+1)%radBody;
				
				faces[counter++]=buildFace(Renderer3D.CAR_LEFT, body[k][i],body[k][ii],body[k+1][ii],body[k+1][i], bo[0]);

			}
		}		
	
		for (int k = 0; k < nzArm-1; k++) {
			
			for(int i=0;i<radArm;i++){
				
				int ii=(i+1)%radArm;
				
				faces[counter++]=buildFace(Renderer3D.CAR_LEFT, leftArm[k][i],leftArm[k][ii],leftArm[k+1][ii],leftArm[k+1][i], bo[0]);

			}
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
		
		Segments s0=new Segments(0,dx*0.5,0,dy,0,dz);
		
		body=new BPoint[nzBody][radBody];
		
		body[0][0]=addBPoint(-1.0,0.0,0,s0);
		body[0][1]=addBPoint(0.0,0.0,0,s0);
		body[0][2]=addBPoint(1.0,0.0,0,s0);
		body[0][3]=addBPoint(1.0,1.0,0,s0);			
		body[0][4]=addBPoint(0.0,1.0,0,s0);
		body[0][5]=addBPoint(-1.0,1.0,0,s0);	

		body[1][0]=addBPoint(-1.0,0.0,1.0,s0);
		body[1][1]=addBPoint(0.0,0.0,1.0,s0);
		body[1][2]=addBPoint(1.0,0.0,1.0,s0);
		body[1][3]=addBPoint(1.0,1.0,1.0,s0);		
		body[1][4]=addBPoint(0.0,1.0,1.0,s0);
		body[1][5]=addBPoint(-1.0,1.0,1.0,s0);	
	}
	

	private void buildArms() {
		
		Segments s0=new Segments(-dxFront*0.5,dxFront*0.5,0,dyFront,0,dzFront);
		
		leftArm=new BPoint[nzArm][radArm];
		
		leftArm[0][0]=addBPoint(-1.0,0.0,0,s0);
		leftArm[0][1]=addBPoint(0.0,0.0,0,s0);
		leftArm[0][2]=body[0][0];
		leftArm[0][3]=body[0][5];			
		leftArm[0][4]=addBPoint(0.0,1.0,0,s0);
		leftArm[0][5]=addBPoint(-1.0,1.0,0,s0);

		leftArm[1][0]=addBPoint(-1.0,0.0,1.0,s0);
		leftArm[1][1]=addBPoint(0.0,0.0,1.0,s0);
		leftArm[1][2]=body[1][0];
		leftArm[1][3]=body[1][5];		
		leftArm[1][4]=addBPoint(0.0,1.0,1.0,s0);
		leftArm[1][5]=addBPoint(-1.0,1.0,1.0,s0);	
		
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
