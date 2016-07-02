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
 * * 
 * @author Administrator
 *
 */
public class Byke0Model extends MeshModel{

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

	private double wheelRadius;
	private double wheelWidth;
	private int wheel_rays;

	double x0=0;
	double y0=0;
	double z0=0;

	private int[][][] faces;

	int basePoints=4;

	public Byke0Model(
			double dx, double dy, double dz, 
			double dxf, double dyf, double dzf, 
			double dxr, double dyr,	double dzr,
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
		this.dzRear = dzr;

		this.wheelRadius = wheelRadius;
		this.wheelWidth = wheelWidth;
		this.wheel_rays = wheel_rays;
	}


	@Override
	public void initMesh() {
		points=new Vector<Point3D>();
		texturePoints=new Vector();

		buildTextures();

		int firstWheelTexturePoint=4;		

		BPoint[][][] leftFrame=new BPoint[2][2][2];


		double frame_side=(dx-wheelWidth)*0.5;
		double xc=-frame_side*0.5;

		Segments lf=new Segments(xc,frame_side,dyRear,dy,wheelRadius,wheelRadius+dz);

		leftFrame[0][0][0]=addBPoint(-0.5,0.0,0,lf);
		leftFrame[1][0][0]=addBPoint(0.5,0.0,0,lf);
		leftFrame[1][1][0]=addBPoint(0.5,0.42,0,lf);
		leftFrame[0][1][0]=addBPoint(-0.5,0.42,0,lf);

		leftFrame[0][0][1]=addBPoint(-0.5,0.26,1.0,lf);
		leftFrame[1][0][1]=addBPoint(0.5,0.26,1.0,lf);
		leftFrame[0][1][1]=addBPoint(-0.5,0.82,1.0,lf);
		leftFrame[1][1][1]=addBPoint(0.5,0.82,1.0,lf);

		BPoint[][][] rightFrame=new BPoint[2][2][2];	


		xc=wheelWidth+frame_side*0.5;

		Segments rf=new Segments(xc,frame_side,dyRear,dy,wheelRadius,wheelRadius+dz);

		rightFrame[0][0][0]=addBPoint(-0.5,0.0,0,rf);
		rightFrame[1][0][0]=addBPoint(0.5,0.0,0,rf);
		rightFrame[1][1][0]=addBPoint(0.5,0.42,0,rf);
		rightFrame[0][1][0]=addBPoint(-0.5,0.42,0,rf);

		rightFrame[0][0][1]=addBPoint(-0.5,0.26,1.0,rf);	
		rightFrame[1][0][1]=addBPoint(0.5,0.26,1.0,rf);
		rightFrame[0][1][1]=addBPoint(-0.5,0.82,1.0,rf);		
		rightFrame[1][1][1]=addBPoint(0.5,0.82,1.0,rf);




		BPoint[][][] leftFork=new BPoint[2][2][2];	

		leftFork[0][0][0]=addBPoint(-0.5,0.91,0,lf);
		leftFork[1][0][0]=addBPoint(0.5,0.91,0,lf);		
		leftFork[0][1][0]=addBPoint(-0.5,1.09,0,lf);
		leftFork[1][1][0]=addBPoint(0.5,1.09,0,lf);

		leftFork[0][0][1]=addBPoint(-0.5,0.82,1.0,lf);	
		leftFork[1][0][1]=addBPoint(0.5,0.82,1.0,lf);
		leftFork[0][1][1]=addBPoint(-0.5,1.0,1.0,lf);		
		leftFork[1][1][1]=addBPoint(0.5,1.0,1.0,lf);		





		BPoint[][][] rightFork=new BPoint[2][2][2];	

		rightFork[0][0][0]=addBPoint(-0.5,0.91,0,rf);
		rightFork[1][0][0]=addBPoint(0.5,0.91,0,rf);	
		rightFork[0][1][0]=addBPoint(-0.5,1.09,0,rf);
		rightFork[1][1][0]=addBPoint(0.5,1.09,0,rf);

		rightFork[0][0][1]=addBPoint(-0.5,0.82,1.0,rf);	
		rightFork[1][0][1]=addBPoint(0.5,0.82,1.0,rf);
		rightFork[0][1][1]=addBPoint(-0.5,1.0,1.0,rf);		
		rightFork[1][1][1]=addBPoint(0.5,1.0,1.0,rf);		



		xc=wheelWidth/2.0;

		Segments bd=new Segments(xc,wheelWidth,dyRear,dy,2*wheelRadius,dz);

		BPoint[][][] body=new BPoint[2][2][2];	

		body[0][0][0]=addBPoint(-0.5,0.0,0,bd);
		body[1][0][0]=addBPoint(0.5,0.0,0,bd);
		body[1][1][0]=addBPoint(0.5,1.0,0,bd);
		body[0][1][0]=addBPoint(-0.5,1.0,0,bd);

		body[0][0][1]=addBPoint(-0.5,0.0,1.0,bd);	
		body[1][0][1]=addBPoint(0.5,0.0,1.0,bd);
		body[0][1][1]=addBPoint(-0.5,1.0,1.0,bd);		
		body[1][1][1]=addBPoint(0.5,1.0,1.0,bd);	



		

		Segments hb=new Segments(xc,dxFront,dyRear+dy-dyFront*0.5,dyFront,2*wheelRadius+dz,dzFront);
		
		int nyh=3;
		BPoint[][][] handlebar=new BPoint[nyh][2][2];	
			
		handlebar[0][0][0]=addBPoint(-0.5,0.0,0.0, hb);
		handlebar[0][1][0]=addBPoint(0.5,0.0,0.0, hb);
		handlebar[0][1][1]=addBPoint(0.5,0.0,1.0, hb);
		handlebar[0][0][1]=addBPoint(-0.5,0.0,1.0, hb);
		
		handlebar[1][0][0]=addBPoint(-0.5,0.5,0.0, hb);
		handlebar[1][1][0]=addBPoint(0.5,0.5,0.0, hb);
		handlebar[1][1][1]=addBPoint(0.5,0.5,1.0, hb);
		handlebar[1][0][1]=addBPoint(-0.5,0.5,1.0, hb);
		
		handlebar[2][0][0]=addBPoint(0.0,1.0,0.0, hb);
		handlebar[2][1][0]=addBPoint(0.0,1.0,0.0, hb);
		handlebar[2][1][1]=addBPoint(0.0,1.0,1.0, hb);
		handlebar[2][0][1]=addBPoint(0.0,1.0,1.0, hb);
	

		double wz=wheelRadius;
		double wx=wheelWidth*0.5;		

		BPoint[][] wheelFront=buildWheel(xc-wx, dyRear,wz , wheelRadius, wheelWidth, wheel_rays);
		BPoint[][] wheelRear=buildWheel(xc-wx, dyRear+dy, wz, wheelRadius, wheelWidth, wheel_rays);

		//faces
		int NF=6*5;
		NF+=(nyh-1)*4+1;//handlebar

		int totWheelPolygon=wheel_rays+2*(wheel_rays-2);
		int NUM_WHEEL_FACES=2*totWheelPolygon;

		faces=new int[NF+NUM_WHEEL_FACES][3][4];

		int counter=0;
		counter=buildBodyFaces(
				counter, 				
				leftFrame,
				rightFrame,
				body,
				leftFork,
				rightFork,
				handlebar
				);


		counter=buildWheelFaces(counter,firstWheelTexturePoint,totWheelPolygon,wheelFront,wheelRear);


		IMG_WIDTH=(int) (2*bx+dx+wheelWidth);
		IMG_HEIGHT=(int) (2*by+dy);

	}


	private void buildTextures() {
		//Texture points

		double y=by;
		double x=bx;

		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx, y+dy,0);
		addTPoint(x,y+dy,0);



		//wheel texture, a black square for simplicity:

		x=bx+dx;
		y=by;

		addTPoint(x,y,0);
		addTPoint(x+wheelWidth,y,0);
		addTPoint(x+wheelWidth,y+wheelWidth,0);
		addTPoint(x,y+wheelWidth,0);

	}


	private int buildWheelFaces(int counter, 
			int totBlockTexturesPoints, 
			int totWheelPolygon, 
			BPoint[][] wheelFront, 
			BPoint[][] wheelRear) {

		int[][][] wFaces = buildWheelFaces(wheelFront,totBlockTexturesPoints);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++]=wFaces[i];
		}

		wFaces = buildWheelFaces(wheelRear,totBlockTexturesPoints);
		for (int i = 0; i <totWheelPolygon; i++) {
			faces[counter++]=wFaces[i];
		}

		return counter;
	}


	private int buildBodyFaces(int counter, 
			BPoint[][][] leftFrame, 
			BPoint[][][] rightFrame, 
			BPoint[][][] body, 
			BPoint[][][] leftFork, 
			BPoint[][][] rightFork, 
			BPoint[][][] handlebar) {

		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, leftFrame[0][0][1],leftFrame[1][0][1],leftFrame[1][1][1],leftFrame[0][1][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, leftFrame[0][0][0],leftFrame[0][1][0],leftFrame[1][1][0],leftFrame[1][0][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, leftFrame[0][0][0],leftFrame[0][0][1],leftFrame[0][1][1],leftFrame[0][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, leftFrame[1][0][0],leftFrame[1][1][0],leftFrame[1][1][1],leftFrame[1][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, leftFrame[0][0][0],leftFrame[1][0][0],leftFrame[1][0][1],leftFrame[0][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, leftFrame[0][1][0],leftFrame[0][1][1],leftFrame[1][1][1],leftFrame[1][1][0], 0, 1, 2, 3);


		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, rightFrame[0][0][1],rightFrame[1][0][1],rightFrame[1][1][1],rightFrame[0][1][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, rightFrame[0][0][0],rightFrame[0][1][0],rightFrame[1][1][0],rightFrame[1][0][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, rightFrame[0][0][0],rightFrame[0][0][1],rightFrame[0][1][1],rightFrame[0][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, rightFrame[1][0][0],rightFrame[1][1][0],rightFrame[1][1][1],rightFrame[1][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, rightFrame[0][0][0],rightFrame[1][0][0],rightFrame[1][0][1],rightFrame[0][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, rightFrame[0][1][0],rightFrame[0][1][1],rightFrame[1][1][1],rightFrame[1][1][0], 0, 1, 2, 3);

		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, leftFork[0][0][1],leftFork[1][0][1],leftFork[1][1][1],leftFork[0][1][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, leftFork[0][0][0],leftFork[0][1][0],leftFork[1][1][0],leftFork[1][0][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, leftFork[0][0][0],leftFork[0][0][1],leftFork[0][1][1],leftFork[0][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, leftFork[1][0][0],leftFork[1][1][0],leftFork[1][1][1],leftFork[1][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, leftFork[0][0][0],leftFork[1][0][0],leftFork[1][0][1],leftFork[0][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, leftFork[0][1][0],leftFork[0][1][1],leftFork[1][1][1],leftFork[1][1][0], 0, 1, 2, 3);

		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, rightFork[0][0][1],rightFork[1][0][1],rightFork[1][1][1],rightFork[0][1][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, rightFork[0][0][0],rightFork[0][1][0],rightFork[1][1][0],rightFork[1][0][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, rightFork[0][0][0],rightFork[0][0][1],rightFork[0][1][1],rightFork[0][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, rightFork[1][0][0],rightFork[1][1][0],rightFork[1][1][1],rightFork[1][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, rightFork[0][0][0],rightFork[1][0][0],rightFork[1][0][1],rightFork[0][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, rightFork[0][1][0],rightFork[0][1][1],rightFork[1][1][1],rightFork[1][1][0], 0, 1, 2, 3);

		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, body[0][0][1],body[1][0][1],body[1][1][1],body[0][1][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, body[0][0][0],body[0][1][0],body[1][1][0],body[1][0][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, body[0][0][0],body[0][0][1],body[0][1][1],body[0][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, body[1][0][0],body[1][1][0],body[1][1][1],body[1][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, body[0][1][0],body[0][1][1],body[1][1][1],body[1][1][0], 0, 1, 2, 3);

		int nyh=handlebar.length;
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, handlebar[0][0][0],handlebar[0][1][0],handlebar[0][1][1],handlebar[0][0][1], 0, 1, 2, 3);
		for (int k = 0; k < nyh-1; k++) {
			
			if(k<nyh-2)
				faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, handlebar[k][0][0],handlebar[k+1][0][0],handlebar[k+1][1][0],handlebar[k][1][0], 0, 1, 2, 3);
			else
				faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, handlebar[k][0][0],handlebar[k+1][0][0],handlebar[k+1][1][0],0, 1, 2);
			
			faces[counter++]=buildFace(Renderer3D.CAR_LEFT, handlebar[k][0][0],handlebar[k][0][1],handlebar[k+1][0][1],handlebar[k+1][0][0], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, handlebar[k][1][0],handlebar[k+1][1][0],handlebar[k+1][1][1],handlebar[k][1][1], 0, 1, 2, 3);
			
			if(k<nyh-2)
				faces[counter++]=buildFace(Renderer3D.CAR_TOP,handlebar[k][0][1],handlebar[k][1][1],handlebar[k+1][1][1],handlebar[k+1][0][1],0, 1, 2, 3);
			else
				faces[counter++]=buildFace(Renderer3D.CAR_TOP,handlebar[k][0][1],handlebar[k][1][1],handlebar[k+1][0][1],0, 1, 2);
		}
		
		
		return counter;
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
