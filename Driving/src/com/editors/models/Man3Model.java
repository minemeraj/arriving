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
	
	int nzBody=5;
	int radBody=8;
	private BPoint[][] body;
	
	int nzArm=4;
	int radArm=8;
	private BPoint[][] leftArm;
	
	int nzLeg=4;
	int radLeg=8;
	private BPoint[][] leftLeg;
	private BPoint[][] rightArm;
	private BPoint[][] rightLeg;

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
		buildLegs();
		
		buildTextures();

		//faces
		int NF=radBody*(nzBody-1)+6;
		NF+=2*(radArm*(nzArm-1)+3);
		NF+=2*(radLeg*(nzLeg-1)+3);

		faces=new int[NF][3][4];

		int counter=0;
		counter=buildFaces(counter);

	}

	private int buildFaces(int counter) {
		
		counter=buildBodyFaces(counter);
		
		counter=buildArmFaces(counter);
		
		counter=buildLegFaces(counter);

		return counter;
	}






	private int buildBodyFaces(int counter) {
		for (int k = 0; k < nzBody-1; k++) {
			
			
			for(int i=0;i<radBody;i++){
				
				int ii=(i+1)%radBody;
			
				faces[counter++]=buildFace(Renderer3D.CAR_LEFT, body[k][i],body[k][ii],body[k+1][ii],body[k+1][i], bo[0]);
				
			}
		}
		
		//only for radBody==6
		for(int i=0;i<3;i++){
			faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, body[0][i],body[0][radBody-1-i],body[0][radBody-2-i], body[0][i+1],bo[0]);
			faces[counter++]=buildFace(Renderer3D.CAR_TOP, body[nzBody-1][i],body[nzBody-1][i+1],body[nzBody-1][radBody-2-i], body[nzBody-1][radBody-1-i],bo[0]);

		}
		
		return counter;
	}
	
	private int buildArmFaces(int counter) {
		
		for (int k = 0; k < nzArm-1; k++) {
			
			for(int i=0;i<radArm;i++){
				
				int ii=(i+1)%radArm;
				
				BPoint l0 = leftArm[k][i];
				BPoint l1 = leftArm[k][ii];
				BPoint l2 = leftArm[k+1][ii];
				BPoint l3 = leftArm[k+1][i];
				
				if(l2.getIndex()==l3.getIndex())
					faces[counter++]=buildFace(Renderer3D.CAR_LEFT, l0,l1,l2,bo[0]);
				else
					faces[counter++]=buildFace(Renderer3D.CAR_LEFT, l0,l1,l2,l3, bo[0]);

			}
		}	
		
		//only for radArm==8:
		for(int i=0;i<3;i++){
			faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, leftArm[0][i],leftArm[0][radArm-1-i],leftArm[0][radArm-2-i], leftArm[0][i+1],bo[0]);
		}
		
		/////////////
		
		for (int k = 0; k < nzArm-1; k++) {
			
			for(int i=0;i<radArm;i++){
				
				int ii=(i+1)%radArm;
				
				BPoint l0 = rightArm[k][i];
				BPoint l1 = rightArm[k][ii];
				BPoint l2 = rightArm[k+1][ii];
				BPoint l3 = rightArm[k+1][i];
				
				if(l2.getIndex()==l3.getIndex())
					faces[counter++]=buildFace(Renderer3D.CAR_LEFT, l0,l1,l2,bo[0]);
				else
					faces[counter++]=buildFace(Renderer3D.CAR_LEFT, l0,l1,l2,l3, bo[0]);

			}
		}	
		
		//only for radArm==8:
		for(int i=0;i<3;i++){
			faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, rightArm[0][i],rightArm[0][radArm-1-i],rightArm[0][radArm-2-i], rightArm[0][i+1],bo[0]);
		}
		
		
		return counter;
	}


	private int buildLegFaces(int counter) {
		
		//top and lateral faces
		for (int k = 0; k < nzLeg-1; k++) {
			
			for(int i=0;i<radLeg;i++){
				
			int ii=(i+1)%radLeg;
				
			BPoint l0 = leftLeg[k][i];
			BPoint l1 = leftLeg[k][ii];
			BPoint l2 = leftLeg[k+1][ii];
			BPoint l3 = leftLeg[k+1][i];
			
			if(l2.getIndex()==l3.getIndex())
				faces[counter++]=buildFace(Renderer3D.CAR_LEFT, l0,l1,l2,bo[0]);
			else
				faces[counter++]=buildFace(Renderer3D.CAR_LEFT, l0,l1,l2,l3, bo[0]);
			}
		}
		
		//bottom faces, only for radLeg==6:
		for(int i=0;i<3;i++){
			faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, leftLeg[0][i],leftLeg[0][radLeg-1-i],leftLeg[0][radLeg-2-i], leftLeg[0][i+1],bo[0]);

		}
		
		///////////////
		
		//top and lateral faces
		for (int k = 0; k < nzLeg-1; k++) {
			
			for(int i=0;i<radLeg;i++){
				
			int ii=(i+1)%radLeg;
				
			BPoint l0 = rightLeg[k][i];
			BPoint l1 = rightLeg[k][ii];
			BPoint l2 = rightLeg[k+1][ii];
			BPoint l3 = rightLeg[k+1][i];
			
			if(l2.getIndex()==l3.getIndex())
				faces[counter++]=buildFace(Renderer3D.CAR_LEFT, l0,l1,l2,bo[0]);
			else
				faces[counter++]=buildFace(Renderer3D.CAR_LEFT, l0,l1,l2,l3, bo[0]);
			}
		}
		
		//bottom faces, only for radLeg==6:
		for(int i=0;i<3;i++){
			faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, rightLeg[0][i],rightLeg[0][radLeg-1-i],rightLeg[0][radLeg-2-i], rightLeg[0][i+1],bo[0]);

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
		
		Segments s0=new Segments(0,dx*0.5,0,dy,dzRear,dz);
		
		body=new BPoint[nzBody][radBody];
		
		body[0][0]=addBPoint(-0.8,0.0,0,s0);
		body[0][1]=addBPoint(-0.5,-0.1,0,s0);
		body[0][2]=addBPoint(0.5,-0.1,0,s0);
		body[0][3]=addBPoint(0.8,0.0,0,s0);
		body[0][4]=addBPoint(0.8,1.0,0,s0);			
		body[0][5]=addBPoint(0.5,1.1,0,s0);
		body[0][6]=addBPoint(-0.5,1.1,0,s0);
		body[0][7]=addBPoint(-0.8,1.0,0,s0);
		
		body[1][0]=addBPoint(-1.0,0.0,0.25,s0);
		body[1][1]=addBPoint(-0.5,-0.1,0.25,s0);
		body[1][2]=addBPoint(0.5,-0.1,0.25,s0);
		body[1][3]=addBPoint(1.0,0.0,0.25,s0);
		body[1][4]=addBPoint(1.0,1.0,0.25,s0);		
		body[1][5]=addBPoint(0.5,1.1,0.25,s0);
		body[1][6]=addBPoint(-0.5,1.1,0.25,s0);
		body[1][7]=addBPoint(-1.0,1.0,0.25,s0);
		
		body[2][0]=addBPoint(-1.0,0.0,0.5,s0);
		body[2][1]=addBPoint(-0.5,-0.1,0.5,s0);
		body[2][2]=addBPoint(0.5,-0.1,0.5,s0);
		body[2][3]=addBPoint(1.0,0.0,0.5,s0);
		body[2][4]=addBPoint(1.0,1.0,0.5,s0);		
		body[2][5]=addBPoint(0.5,1.1,0.5,s0);
		body[2][6]=addBPoint(-0.5,1.1,0.5,s0);
		body[2][7]=addBPoint(-1.0,1.0,0.5,s0);
		
		body[3][0]=addBPoint(-1.0,0.0,0.75,s0);
		body[3][1]=addBPoint(-0.5,-0.1,0.75,s0);
		body[3][2]=addBPoint(0.5,-0.1,0.75,s0);
		body[3][3]=addBPoint(1.0,0.0,0.75,s0);
		body[3][4]=addBPoint(1.0,1.0,0.75,s0);		
		body[3][5]=addBPoint(0.5,1.1,0.75,s0);
		body[3][6]=addBPoint(-0.5,1.1,0.75,s0);
		body[3][7]=addBPoint(-1.0,1.0,0.75,s0);

		body[4][0]=addBPoint(-1.0,0.0,1.0,s0);
		body[4][1]=addBPoint(-0.5,-0.1,1.0,s0);
		body[4][2]=addBPoint(0.5,-0.1,1.0,s0);
		body[4][3]=addBPoint(1.0,0.0,1.0,s0);
		body[4][4]=addBPoint(1.0,1.0,1.0,s0);		
		body[4][5]=addBPoint(0.5,1.1,1.0,s0);
		body[4][6]=addBPoint(-0.5,1.1,1.0,s0);
		body[4][7]=addBPoint(-1.0,1.0,1.0,s0);	
	}
	

	private void buildArms() {
		
		Segments s0=new Segments(-dx*0.5-dxFront*0.5,dxFront*0.5,0,dyFront,dzRear,dzFront);
		
		leftArm=new BPoint[nzArm][radArm];
		
		leftArm[0][0]=addBPoint(-1.0,0.0,0,s0);
		leftArm[0][1]=addBPoint(-0.5,0.0,0,s0);
		leftArm[0][2]=addBPoint(0.5,0.0,0,s0);
		leftArm[0][3]=addBPoint(1.0,0.0,0,s0);
		leftArm[0][4]=addBPoint(1.0,1.0,0,s0);			
		leftArm[0][5]=addBPoint(0.5,1.1,0,s0);
		leftArm[0][6]=addBPoint(-0.5,1.1,0,s0);
		leftArm[0][7]=addBPoint(-1.0,1.0,0,s0);
		
		leftArm[1][0]=addBPoint(-1.0,0.0,0.5,s0);
		leftArm[1][1]=addBPoint(-0.5,-0.1,0.5,s0);
		leftArm[1][2]=addBPoint(0.5,-0.1,0.5,s0);
		leftArm[1][3]=addBPoint(1.0,0.0,0.5,s0);
		leftArm[1][4]=addBPoint(1.0,1.0,0.5,s0);			
		leftArm[1][5]=addBPoint(0.5,1.1,0.5,s0);
		leftArm[1][6]=addBPoint(-0.5,1.1,0.5,s0);
		leftArm[1][7]=addBPoint(-1.0,1.0,0.5,s0);
		
		leftArm[2][0]=addBPoint(-1.0,0.0,0.75,s0);
		leftArm[2][1]=addBPoint(-0.5,-0.1,0.75,s0);
		leftArm[2][2]=addBPoint(0.5,-0.1,0.75,s0);
		leftArm[2][3]=body[3][0];
		leftArm[2][4]=body[3][7];
		leftArm[2][5]=addBPoint(0.5,1.1,0.75,s0);
		leftArm[2][6]=addBPoint(-0.5,1.1,0.75,s0);
		leftArm[2][7]=addBPoint(-1.0,1.0,0.75,s0);

		leftArm[3][0]=body[4][0];
		leftArm[3][1]=body[4][0];
		leftArm[3][2]=body[4][0];
		leftArm[3][3]=body[4][0];
		leftArm[3][4]=body[4][7];		
		leftArm[3][5]=body[4][7];
		leftArm[3][6]=body[4][7];	
		leftArm[3][7]=body[4][7];
		
		s0=new Segments(dx*0.5+dxFront*0.5,dxFront*0.5,0,dyFront,dzRear,dzFront);
		
		rightArm=new BPoint[nzArm][radArm];
		
		rightArm[0][0]=addBPoint(-1.0,0.0,0,s0);
		rightArm[0][1]=addBPoint(-0.5,0.0,0,s0);
		rightArm[0][2]=addBPoint(0.5,0.0,0,s0);
		rightArm[0][3]=addBPoint(1.0,0.0,0,s0);
		rightArm[0][4]=addBPoint(1.0,1.0,0,s0);			
		rightArm[0][5]=addBPoint(0.5,1.1,0,s0);
		rightArm[0][6]=addBPoint(-0.5,1.1,0,s0);
		rightArm[0][7]=addBPoint(-1.0,1.0,0,s0);
		
		rightArm[1][0]=addBPoint(-1.0,0.0,0.5,s0);
		rightArm[1][1]=addBPoint(-0.5,-0.1,0.5,s0);
		rightArm[1][2]=addBPoint(0.5,-0.1,0.5,s0);
		rightArm[1][3]=addBPoint(1.0,0.0,0.5,s0);
		rightArm[1][4]=addBPoint(1.0,1.0,0.5,s0);			
		rightArm[1][5]=addBPoint(0.5,1.1,0.5,s0);
		rightArm[1][6]=addBPoint(-0.5,1.1,0.5,s0);
		rightArm[1][7]=addBPoint(-1.0,1.0,0.5,s0);
		
		rightArm[2][0]=body[3][3];
		rightArm[2][1]=addBPoint(-0.5,-0.1,0.75,s0);
		rightArm[2][2]=addBPoint(0.5,-0.1,0.75,s0);
		rightArm[2][3]=addBPoint(1.0,0.0,0.75,s0);
		rightArm[2][4]=addBPoint(1.0,1.0,0.75,s0);
		rightArm[2][5]=addBPoint(0.5,1.1,0.75,s0);
		rightArm[2][6]=addBPoint(-0.5,1.1,0.75,s0);
		rightArm[2][7]=body[3][4];

		rightArm[3][0]=body[4][3];
		rightArm[3][1]=body[4][3];
		rightArm[3][2]=body[4][3];
		rightArm[3][3]=body[4][3];
		rightArm[3][4]=body[4][4];		
		rightArm[3][5]=body[4][4];
		rightArm[3][6]=body[4][4];	
		rightArm[3][7]=body[4][4];
	}
	

	private void buildLegs() {
		Segments s0=new Segments(-dx*0.5-dxRear*0.5,dxRear*0.5,0,dyRear,0,dzRear);
		leftLeg=new BPoint[nzLeg][radLeg];
		
		leftLeg[0][0]=addBPoint(-1.0,0.0,0,s0);
		leftLeg[0][1]=addBPoint(-0.5,-0.1,0,s0);
		leftLeg[0][2]=addBPoint(0.5,-0.1,0,s0);
		leftLeg[0][3]=addBPoint(1.0,0.0,0,s0);
		leftLeg[0][4]=addBPoint(1.0,1.0,0,s0);		
		leftLeg[0][5]=addBPoint(0.5,1.1,0,s0);
		leftLeg[0][6]=addBPoint(-0.5,1.1,0,s0);
		leftLeg[0][7]=addBPoint(-1.0,1.0,0,s0);
		
		leftLeg[1][0]=addBPoint(-1.0,0.0,0.5,s0);
		leftLeg[1][1]=addBPoint(-0.5,-0.1,0.5,s0);
		leftLeg[1][2]=addBPoint(0.5,-0.1,0.5,s0);
		leftLeg[1][3]=addBPoint(1.0,0.0,0.5,s0);
		leftLeg[1][4]=addBPoint(1.0,1.0,0.5,s0);		
		leftLeg[1][5]=addBPoint(0.5,1.1,0.5,s0);
		leftLeg[1][6]=addBPoint(-0.5,1.1,0.5,s0);
		leftLeg[1][7]=addBPoint(-1.0,1.0,0.5,s0);
		
		leftLeg[2][0]=addBPoint(-1.0,0.0,0.75,s0);
		leftLeg[2][1]=addBPoint(-0.5,-0.1,0.75,s0);
		leftLeg[2][2]=addBPoint(0.5,-0.1,0.75,s0);
		leftLeg[2][3]=body[0][0];
		leftLeg[2][4]=body[0][7];			
		leftLeg[2][5]=addBPoint(0.5,1.1,0.75,s0);
		leftLeg[2][6]=addBPoint(-0.5,1.1,0.75,s0);
		leftLeg[2][7]=addBPoint(-1.0,1.0,0.75,s0);
		
		leftLeg[3][0]=body[1][0];
		leftLeg[3][1]=body[1][0];
		leftLeg[3][2]=body[1][0];
		leftLeg[3][3]=body[1][0];
		leftLeg[3][4]=body[1][7];
		leftLeg[3][5]=body[1][7];
		leftLeg[3][6]=body[1][7];
		leftLeg[3][7]=body[1][7];
		
		
		
		s0=new Segments(+dx*0.5+dxRear*0.5,dxRear*0.5,0,dyRear,0,dzRear);
		rightLeg=new BPoint[nzLeg][radLeg];
		
		rightLeg[0][0]=addBPoint(-1.0,0.0,0,s0);
		rightLeg[0][1]=addBPoint(-0.5,-0.1,0,s0);
		rightLeg[0][2]=addBPoint(0.5,-0.1,0,s0);
		rightLeg[0][3]=addBPoint(1.0,0.0,0,s0);
		rightLeg[0][4]=addBPoint(1.0,1.0,0,s0);		
		rightLeg[0][5]=addBPoint(0.5,1.1,0,s0);
		rightLeg[0][6]=addBPoint(-0.5,1.1,0,s0);
		rightLeg[0][7]=addBPoint(-1.0,1.0,0,s0);
		
		rightLeg[1][0]=addBPoint(-1.0,0.0,0.5,s0);
		rightLeg[1][1]=addBPoint(-0.5,-0.1,0.5,s0);
		rightLeg[1][2]=addBPoint(0.5,-0.1,0.5,s0);
		rightLeg[1][3]=addBPoint(1.0,0.0,0.5,s0);
		rightLeg[1][4]=addBPoint(1.0,1.0,0.5,s0);		
		rightLeg[1][5]=addBPoint(0.5,1.1,0.5,s0);
		rightLeg[1][6]=addBPoint(-0.5,1.1,0.5,s0);
		rightLeg[1][7]=addBPoint(-1.0,1.0,0.5,s0);
		
		rightLeg[2][0]=body[0][3];
		rightLeg[2][1]=addBPoint(-0.5,-0.1,0.75,s0);
		rightLeg[2][2]=addBPoint(0.5,-0.1,0.75,s0);
		rightLeg[2][3]=addBPoint(1.0,0.0,0.75,s0);
		rightLeg[2][4]=addBPoint(1.0,1.0,0.75,s0);	
		rightLeg[2][5]=addBPoint(0.5,1.1,0.75,s0);
		rightLeg[2][6]=addBPoint(-0.5,1.1,0.75,s0);
		rightLeg[2][7]=body[0][4];
		
		rightLeg[3][0]=body[1][3];
		rightLeg[3][1]=body[1][3];
		rightLeg[3][2]=body[1][3];
		rightLeg[3][3]=body[1][3];
		rightLeg[3][4]=body[1][4];
		rightLeg[3][5]=body[1][4];
		rightLeg[3][6]=body[1][4];
		rightLeg[3][7]=body[1][4];
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
