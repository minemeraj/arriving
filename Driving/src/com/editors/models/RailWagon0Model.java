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
public class RailWagon0Model extends MeshModel{

	protected int bx=10;
	protected int by=10;

	protected double dx = 0;
	protected double dy = 0;
	protected double dz = 0;
	
	protected double dxFront = 0;
	protected double dyFront = 0;
	protected double dzFront = 0;
	
	protected double dxRear = 0;
	protected double dyRear = 0;
	protected double dzRear = 0;
	
	protected double dxRoof;
	protected double dyRoof;
	protected double dzRoof;
	
	protected double wheelRadius;
	protected double wheelWidth;
	protected int wheelRays;
	
	double x0=0;
	double y0=0;
	double z0=0;

	protected int[][][] faces;

	int basePoints=4;
	protected BPoint[][][] body;
	
	private BPoint[][][] back;	
	private BPoint[][] rWheelLeftFront;
	private BPoint[][] rWheelRightFront;
	private BPoint[][] rWheelLeftRear;
	private BPoint[][] rWheelRightRear;
	
	private BPoint[][][] front;
	private BPoint[][] fWheelLeftFront;
	private BPoint[][] fWheelRightFront;
	private BPoint[][] fWheelLeftRear;
	private BPoint[][] fWheelRightRear;
	protected BPoint[][][] wagon;
	private double rearOverhang;
	private double frontOverhang;
	
	public static String NAME="RailWagon";
	
	//body textures
	protected int b0=0,b1=1,b2=2,b3=3;
	//wheel textures
	protected int w0=4,w1=5,w2=6,w3=7;

	
	public RailWagon0Model(
			double dx, double dy, double dz, 
			double dxf, double dyf, double dzf, 
			double dxr, double dyr,	double dzr,
			double dxRoof,double dyRoof,double dzRoof,
			double rearOverhang, double frontOverhang, 
			double wheelRadius, double wheelWidth, int wheelRays
			) {
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
		
		this.rearOverhang = rearOverhang;
		this.frontOverhang = frontOverhang;

		
		this.wheelRadius = wheelRadius;
		this.wheelWidth = wheelWidth;
		this.wheelRays = wheelRays;
	}


	@Override
	public void initMesh() {
		points=new Vector<Point3D>();
		texturePoints=new Vector();
		
		int pnx=2;
		int pny=2;
		int pnz=2;
		
		buildBody(pnx,pny,pnz);	
		
		int fnx=2;
		int fny=2;
		int fnz=2;
		
		buildWagon();

		buildTextures();


		//faces
		int NF=6*4;
		
		int totWheelPolygon=wheelRays+2*(wheelRays-2);
		int NUM_WHEEL_FACES=8*totWheelPolygon;

		faces=new int[NF+NUM_WHEEL_FACES][3][4];

		int counter=0;


		
		counter=buildBodyFaces(counter,w0,totWheelPolygon);
		
		counter=buildWagonFaces(counter,wagon);
		

		
		IMG_WIDTH=(int) (2*bx+dx);
		IMG_HEIGHT=(int) (2*by+dy);

	}
	
	
	protected void buildBody(int pnx, int pny, int pnz) {
		
		body=new BPoint[pnx][pny][pnz];
		
		Segments p0=new Segments(0,dx,0,dy,dzRear,dz);
		
		body[0][0][0]=addBPoint(-0.5,0.0,0,p0);
		body[1][0][0]=addBPoint(0.5,0.0,0,p0);
		body[0][0][1]=addBPoint(-0.5,0.0,1.0,p0);
		body[1][0][1]=addBPoint(0.5,0.0,1.0,p0);
		
		body[0][1][0]=addBPoint(-0.5,1.0,0,p0);
		body[1][1][0]=addBPoint(0.5,1.0,0,p0);
		body[0][1][1]=addBPoint(-0.5,1.0,1.0,p0);
		body[1][1][1]=addBPoint(0.5,1.0,1.0,p0);
		
		int bnx=2;
		int bny=2;
		int bnz=2;
				
		back=new BPoint[bnx][bny][bnz];
		
		Segments b0=new Segments(0,dxRear,rearOverhang,dyRear,0,dzRear);
		
		back[0][0][0]=addBPoint(-0.5,0.0,0,b0);
		back[1][0][0]=addBPoint(0.5,0.0,0,b0);
		back[0][0][1]=addBPoint(-0.5,0.0,1.0,b0);
		back[1][0][1]=addBPoint(0.5,0.0,1.0,b0);
		
		back[0][1][0]=addBPoint(-0.5,1.0,0,b0);
		back[1][1][0]=addBPoint(0.5,1.0,0,b0);
		back[0][1][1]=addBPoint(-0.5,1.0,1.0,b0);
		back[1][1][1]=addBPoint(0.5,1.0,1.0,b0);		
		
		fWheelLeftFront=buildWheel(-dxRear*0.5-wheelWidth,rearOverhang,0,wheelRadius,wheelWidth,wheelRays);
		fWheelRightFront=buildWheel(dxRear*0.5,rearOverhang,0,wheelRadius,wheelWidth,wheelRays);
		
		fWheelLeftRear=buildWheel(-dxRear*0.5-wheelWidth,rearOverhang+dyRear,0,wheelRadius,wheelWidth,wheelRays);
		fWheelRightRear=buildWheel(dxRear*0.5,rearOverhang+dyRear,0,wheelRadius,wheelWidth,wheelRays);

		
		int fnx=2;
		int fny=2;
		int fnz=2;
		
		front=new BPoint[fnx][fny][fnz];
		
		double fy=dy-dyFront-frontOverhang;
		
		Segments f0=new Segments(0,dxFront,fy,dyFront,0,dzFront);
		
		front[0][0][0]=addBPoint(-0.5,0.0,0,f0);
		front[1][0][0]=addBPoint(0.5,0.0,0,f0);
		front[0][0][1]=addBPoint(-0.5,0.0,1.0,f0);
		front[1][0][1]=addBPoint(0.5,0.0,1.0,f0);
		
		front[0][1][0]=addBPoint(-0.5,1.0,0,f0);
		front[1][1][0]=addBPoint(0.5,1.0,0,f0);
		front[0][1][1]=addBPoint(-0.5,1.0,1.0,f0);
		front[1][1][1]=addBPoint(0.5,1.0,1.0,f0);
		
		
		rWheelLeftFront=buildWheel(-dxFront*0.5-wheelWidth,fy,0,wheelRadius,wheelWidth,wheelRays);
		rWheelRightFront=buildWheel(dxFront*0.5,fy,0,wheelRadius,wheelWidth,wheelRays);
		
		rWheelLeftRear=buildWheel(-dxFront*0.5-wheelWidth,fy+dyFront,0,wheelRadius,wheelWidth,wheelRays);
		rWheelRightRear=buildWheel(dxFront*0.5,fy+dyFront,0,wheelRadius,wheelWidth,wheelRays);

	}


	protected int buildBodyFaces(int counter, int firstWheelTexturePoint, int totWheelPolygon) {
		
		faces[counter++]=buildFace(Renderer3D.CAR_TOP, body[0][0][1],body[1][0][1],body[1][1][1],body[0][1][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_LEFT, body[0][0][0],body[0][0][1],body[0][1][1],body[0][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, body[1][0][0],body[1][1][0],body[1][1][1],body[1][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_FRONT, body[0][1][0],body[0][1][1],body[1][1][1],body[1][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, body[0][0][0],body[0][1][0],body[1][1][0],body[1][0][0], 0, 1, 2, 3);
		
		faces[counter++]=buildFace(Renderer3D.CAR_TOP, back[0][0][1],back[1][0][1],back[1][1][1],back[0][1][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_LEFT, back[0][0][0],back[0][0][1],back[0][1][1],back[0][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, back[1][0][0],back[1][1][0],back[1][1][1],back[1][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_FRONT, back[0][1][0],back[0][1][1],back[1][1][1],back[1][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, back[0][0][0],back[1][0][0],back[1][0][1],back[0][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, back[0][0][0],back[0][1][0],back[1][1][0],back[1][0][0], 0, 1, 2, 3);
		
		faces[counter++]=buildFace(Renderer3D.CAR_TOP, front[0][0][1],front[1][0][1],front[1][1][1],front[0][1][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_LEFT, front[0][0][0],front[0][0][1],front[0][1][1],front[0][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, front[1][0][0],front[1][1][0],front[1][1][1],front[1][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_FRONT, front[0][1][0],front[0][1][1],front[1][1][1],front[1][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, front[0][0][0],front[1][0][0],front[1][0][1],front[0][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, front[0][0][0],front[0][1][0],front[1][1][0],front[1][0][0], 0, 1, 2, 3);
		
		//front bogie
		counter=buildWheelFaces(counter,
				firstWheelTexturePoint,
				totWheelPolygon,
				fWheelLeftFront,fWheelRightFront,fWheelLeftRear,fWheelRightRear);
		
		//rear bogie
		counter=buildWheelFaces(counter,
				firstWheelTexturePoint,
				totWheelPolygon,
				rWheelLeftFront,rWheelRightFront,rWheelLeftRear,rWheelRightRear);
		
		return counter;
	}


	protected void buildWagon() {
		
		int rnx=2;
		int rny=2;
		int rnz=2;

		wagon=new BPoint[rnx][rny][rnz];

		double rdy=(dy-dyRoof)*0.5;

		Segments r0=new Segments(0,dxRoof,rdy,dyRoof,dzRear+dz,dzRoof);

		wagon[0][0][0]=addBPoint(-0.5,0.0,0,r0);
		wagon[1][0][0]=addBPoint(0.5,0.0,0,r0);
		wagon[0][0][1]=addBPoint(-0.5,0.0,1.0,r0);
		wagon[1][0][1]=addBPoint(0.5,0.0,1.0,r0);

		wagon[0][1][0]=addBPoint(-0.5,1.0,0,r0);
		wagon[1][1][0]=addBPoint(0.5,1.0,0,r0);
		wagon[0][1][1]=addBPoint(-0.5,1.0,1.0,r0);
		wagon[1][1][1]=addBPoint(0.5,1.0,1.0,r0);

		
	}


	protected int buildWagonFaces(int counter, BPoint[][][] wagon) {
		
		faces[counter++]=buildFace(Renderer3D.CAR_TOP, wagon[0][0][1],wagon[1][0][1],wagon[1][1][1],wagon[0][1][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_LEFT, wagon[0][0][0],wagon[0][0][1],wagon[0][1][1],wagon[0][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, wagon[1][0][0],wagon[1][1][0],wagon[1][1][1],wagon[1][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_FRONT, wagon[0][1][0],wagon[0][1][1],wagon[1][1][1],wagon[1][1][0], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, wagon[0][0][0],wagon[1][0][0],wagon[1][0][1],wagon[0][0][1], 0, 1, 2, 3);
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, wagon[0][0][0],wagon[0][1][0],wagon[1][1][0],wagon[1][0][0], 0, 1, 2, 3);
		
		return counter;
		
	}


	protected void buildTextures() {
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
	
	protected int buildWheelFaces(
			
			int counter, 
			int firstWheelTexturePoint,
			int totWheelPolygon,
			BPoint[][] wheelLeftFront, 
			BPoint[][] wheelRightFront, 
			BPoint[][] wheelLeftRear, 
			BPoint[][] wheelRightRear
			) {

		int[][][] wFaces = buildWheelFaces(wheelLeftFront,firstWheelTexturePoint);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++]=wFaces[i];
		}

		wFaces = buildWheelFaces(wheelRightFront,firstWheelTexturePoint);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++]=wFaces[i];
		}

		wFaces = buildWheelFaces(wheelLeftRear,firstWheelTexturePoint);
		for (int i = 0; i <totWheelPolygon; i++) {
			faces[counter++]=wFaces[i];
		}

		wFaces = buildWheelFaces(wheelRightRear,firstWheelTexturePoint);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++]=wFaces[i];
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
