package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.BPoint;
import com.main.Renderer3D;
/**
 * 
 * CROSS HOUSE
 * 
 * @author Administrator
 *
 */
public class Church0Model extends MeshModel{

	protected double dx=100;
	protected double dx1=100;
	protected double dy=200;
	protected double dy1=200;
	protected double dy2=200;
	protected double dy3=200;
	protected double dz=20;
	protected double dz1=20;
	protected double roof_height=50;

	protected int bx=10;
	protected int by=10;
	
	public static String NAME="Church0";
	
	//wall texture points
	protected int w0=0,w1=1,w2=2,w3=3;	
	//roof texture points
	protected int r0=4,r1=5,r2=6,r3=7;
	//facade points, from the below
	protected int f00=8,f10=9,f20=10,f30=11;
	protected int f01=12,f11=13,f21=14,f31=15;
	protected int f02=16,f12=17;
	protected int f03=18;
	
	protected static int AISLE_SW=0;
	protected static int AISLE_SE=1;
	protected static int AISLE_NE=2;
	protected static int AISLE_NW=3;
	
	public Church0Model(double dx, double dy, double dz,double roof_height,
			double dx1,double dy1,double dy2,double dy3, double dz1
			) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		this.roof_height = roof_height;
		this.dx1 = dx1;
		this.dy1 = dy1;
		this.dy2 = dy2;
		this.dy3 = dy3;
		
		this.dz1 = dz1;
	}


	@Override
	public void initMesh() {
		
		BPoint[][][] mainCross=new BPoint[4][4][2];
		
		BPoint[][][] crossRoof=new BPoint[3][3][1];

		BPoint[][][] swAisle=new BPoint[2][2][2];
		BPoint[][][] seAisle=new BPoint[2][2][2];
		BPoint[][][] nwAisle=new BPoint[2][2][2];
		BPoint[][][] neAisle=new BPoint[2][2][2];
		
		points=new Vector();
		//lower and upper base
		for(int k=0;k<2;k++){

			double z=dz*k;
			double z1=dz1*k;
			

			mainCross[1][0][k]=addBPoint(dy3,0.0,z);
			mainCross[2][0][k]=addBPoint(dy3+dx,0.0,z);
			mainCross[2][1][k]=addBPoint(dy3+dx,dy,z);
			mainCross[3][1][k]=addBPoint(dy3+dx+dy1,dy,z);
			mainCross[3][2][k]=addBPoint(dy3+dx+dy1,dy+dx1,z);
			mainCross[2][2][k]=addBPoint(dy3+dx,dy+dx1,z);
			mainCross[2][3][k]=addBPoint(dy3+dx,dy+dx1+dy2,z);
			mainCross[1][3][k]=addBPoint(dy3,dy+dx1+dy2,z);
			mainCross[1][2][k]=addBPoint(dy3,dy+dx1,z);
			mainCross[0][2][k]=addBPoint(0,dy+dx1,z);
			mainCross[0][1][k]=addBPoint(0,dy,z);
			mainCross[1][1][k]=addBPoint(dy3,dy,z);
			
			//adding the 4 aisles in anti-clockwise order,as distinct blocks
			swAisle[0][0][k]=mainCross[2][0][k];
			swAisle[1][0][k]=addBPoint(dy3+dx+dy1,0.0,z1);
			swAisle[1][1][k]=addBPoint(dy3+dx+dy1,dy,z1);
			swAisle[0][1][k]=mainCross[2][1][k];		

			
			seAisle[0][0][k]=mainCross[2][2][k];
			seAisle[1][0][k]=addBPoint(dy3+dx+dy1,dy+dx1,z1);
			seAisle[1][1][k]=addBPoint(dy3+dx+dy1,dy+dx1+dy2,z1);
			seAisle[0][1][k]=mainCross[2][3][k];

			
			nwAisle[0][0][k]=addBPoint(0,dy+dx1,z1);
			nwAisle[1][0][k]=mainCross[1][2][k];
			nwAisle[1][1][k]=mainCross[1][3][k];
			nwAisle[0][1][k]=addBPoint(0,dy+dx1+dy2,z1);

			
			neAisle[0][0][k]=addBPoint(0,0.0,z1);
			neAisle[1][0][k]=mainCross[1][0][k];
			neAisle[1][1][k]=mainCross[1][1][k];
			neAisle[0][1][k]=addBPoint(0,dy,z1);

		}
				
		
		//cross roof
		crossRoof[1][0][0]=addBPoint(dy3+dx*0.5,0.0,dz+roof_height);
		crossRoof[0][1][0]=addBPoint(0,dy+dx1*0.5,dz+roof_height);
		crossRoof[1][1][0]=addBPoint(dy3+dx*0.5,dy+dx1*0.5,dz+roof_height);
		crossRoof[2][1][0]=addBPoint(dy3+dx+dy1,dy+dx1*0.5,dz+roof_height);
		crossRoof[1][2][0]=addBPoint(dy3+dx*0.5,dy+dx1+dy2,dz+roof_height);
		
		texturePoints=new Vector();

		buildTextures();
		
		int NF=6*4;//AISLES
		NF+=12+8+4;//CROSS+CROSS_ROOF+GABLES

		faces=new int[NF][3][4];
		
		int counter=0;
		counter=buildCross(counter,mainCross,crossRoof);
		counter=buildAisle(counter,swAisle,AISLE_SW);
		counter=buildAisle(counter,seAisle,AISLE_SE);
		counter=buildAisle(counter,neAisle,AISLE_NE);
		counter=buildAisle(counter,nwAisle,AISLE_NW);
		
		
		int maxX=(int) (dx+dx+(dy3+dx+dy));
		int maxY=(int) Math.max(dz+roof_height,dy);
		IMG_WIDTH= 2*bx+maxX;		
		IMG_HEIGHT= 2*by+maxY;
	}

	protected int buildCross(int counter, BPoint[][][] mainCross, BPoint[][][] crossRoof) {
		

		
		//first wing
		faces[counter++]=buildFace(Renderer3D.CAR_LEFT, mainCross[1][0][0],mainCross[1][0][1],mainCross[1][1][1],mainCross[1][1][0], w0, w1, w2, w3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, mainCross[1][0][0],mainCross[2][0][0],mainCross[2][0][1],mainCross[1][0][1], f10, f20, f02, f12);
		faces[counter++]=buildFace(Renderer3D.CAR_RIGHT,mainCross[2][0][0],mainCross[2][1][0],mainCross[2][1][1],mainCross[2][0][1], w0, w1, w2, w3);
		//roof
		faces[counter++]=buildFace(Renderer3D.CAR_TOP, mainCross[1][0][1],crossRoof[1][0][0],crossRoof[1][1][0],mainCross[1][1][1], r0, r1, r2, r3);
		faces[counter++]=buildFace(Renderer3D.CAR_TOP, mainCross[2][0][1],mainCross[2][1][1],crossRoof[1][1][0],crossRoof[1][0][0], r0, r1, r2, r3);
		//gable
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, mainCross[1][0][1],mainCross[2][0][1],crossRoof[1][0][0], f02, f12, f03);
		
		//second wing
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, mainCross[2][1][0],mainCross[3][1][0],mainCross[3][1][1],mainCross[2][1][1], w0, w1, w2, w3);
		faces[counter++]=buildFace(Renderer3D.CAR_RIGHT,mainCross[3][1][0],mainCross[3][2][0],mainCross[3][2][1],mainCross[3][1][1], w0, w1, w2, w3);
		faces[counter++]=buildFace(Renderer3D.CAR_FRONT,mainCross[2][2][0],mainCross[2][2][1],mainCross[3][2][1],mainCross[3][2][0], w0, w1, w2, w3);
		//roof
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,mainCross[2][1][1],mainCross[3][1][1],crossRoof[2][1][0],crossRoof[1][1][0], r0, r1, r2, r3);
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,mainCross[2][2][1],crossRoof[1][1][0],crossRoof[2][1][0],mainCross[3][2][1], r0, r1, r2, r3);
		//gable
		faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, mainCross[3][1][1],mainCross[3][2][1],crossRoof[2][1][0], w0, w1, w2);
		
		//third wing
		faces[counter++]=buildFace(Renderer3D.CAR_FRONT,mainCross[1][3][0],mainCross[1][3][1],mainCross[2][3][1],mainCross[2][3][0], w0, w1, w2, w3);		
		faces[counter++]=buildFace(Renderer3D.CAR_RIGHT,mainCross[2][2][0],mainCross[2][3][0],mainCross[2][3][1],mainCross[2][2][1], w0, w1, w2, w3);
		faces[counter++]=buildFace(Renderer3D.CAR_LEFT, mainCross[1][2][0],mainCross[1][2][1],mainCross[1][3][1],mainCross[1][3][0], w0, w1, w2, w3);
		//roof
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,mainCross[2][2][1],mainCross[2][3][1],crossRoof[1][2][0],crossRoof[1][1][0], r0, r1, r2, r3);
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,mainCross[1][3][1],mainCross[1][2][1],crossRoof[1][1][0],crossRoof[1][2][0], r0, r1, r2, r3);
		//gable
		faces[counter++]=buildFace(Renderer3D.CAR_FRONT, mainCross[2][3][1],mainCross[1][3][1],crossRoof[1][2][0], w0, w1, w2);
		
		//fourth wing
		faces[counter++]=buildFace(Renderer3D.CAR_FRONT,mainCross[0][2][0],mainCross[0][2][1],mainCross[1][2][1],mainCross[1][2][0], w0, w1, w2, w3);		
		faces[counter++]=buildFace(Renderer3D.CAR_LEFT,mainCross[0][1][0],mainCross[0][1][1],mainCross[0][2][1],mainCross[0][2][0], w0, w1, w2, w3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK,mainCross[0][1][0],mainCross[1][1][0],mainCross[1][1][1],mainCross[0][1][1], w0, w1, w2, w3);		
		//roof
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,mainCross[0][1][1],mainCross[1][1][1],crossRoof[1][1][0],crossRoof[0][1][0], r0, r1, r2, r3);
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,mainCross[1][2][1],mainCross[0][2][1],crossRoof[0][1][0],crossRoof[1][1][0], r0, r1, r2, r3);
		//gable
		faces[counter++]=buildFace(Renderer3D.CAR_LEFT, mainCross[0][2][1],mainCross[0][1][1],crossRoof[0][1][0], w0, w1, w2);
		
		return counter;
	}


	private int buildAisle(int counter, BPoint[][][] swAisle, int aisleType) {

		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, swAisle[0][0][0],swAisle[0][1][0],swAisle[1][1][0],swAisle[0][1][0], w0, w1, w2, w3);
		
		if(aisleType==AISLE_SW)
			faces[counter++]=buildFace(Renderer3D.CAR_BACK, swAisle[0][0][0],swAisle[1][0][0],swAisle[1][0][1],swAisle[0][0][1],f00, f10, f11, f01);
		else if(aisleType==AISLE_SE)
			faces[counter++]=buildFace(Renderer3D.CAR_BACK, swAisle[0][0][0],swAisle[1][0][0],swAisle[1][0][1],swAisle[0][0][1],f30, f30, f31, f21);
		else
			faces[counter++]=buildFace(Renderer3D.CAR_BACK, swAisle[0][0][0],swAisle[1][0][0],swAisle[1][0][1],swAisle[0][0][1],w0, w1, w2, w3);
		
		faces[counter++]=buildFace(Renderer3D.CAR_LEFT, swAisle[0][0][0],swAisle[0][0][1],swAisle[0][1][1],swAisle[0][1][0],w0, w1, w2, w3);
		faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, swAisle[1][0][0],swAisle[1][1][0],swAisle[1][1][1],swAisle[1][0][1],w0, w1, w2, w3);
		faces[counter++]=buildFace(Renderer3D.CAR_TOP, swAisle[0][0][1],swAisle[1][0][1],swAisle[1][1][1],swAisle[0][1][1],r0, r1, r2, r3);
		faces[counter++]=buildFace(Renderer3D.CAR_FRONT, swAisle[0][1][0],swAisle[0][1][1],swAisle[1][1][1],swAisle[1][1][0],w0, w1, w2, w3);
		
		
		return counter;
	}


	protected void buildTextures() {
		//Texture points

		double y=by;
		double x=bx;

		//walls
		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx, y+dy,0);
		addTPoint(x,y+dy,0);
		
		//roof
		x+=dx;
		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx, y+dy,0);
		addTPoint(x,y+dy,0);

		//facade made by the aisle and the first wing, points ranged in z levels
		x+=dx;
		addTPoint(x,y,0);
		addTPoint(x+dy3,y,0);
		addTPoint(x+dy3+dx,y,0);
		addTPoint(x+dy3+dx+dy2,y,0);
		
		y=by+dz1;
		addTPoint(x,y,0);
		addTPoint(x+dy3,y,0);
		addTPoint(x+dy3+dx,y,0);
		addTPoint(x+dy3+dx+dy2,y,0);
		
		y=by+dz;
		addTPoint(x+dy3,y,0);
		addTPoint(x+dy3+dx,y,0);
		
		y=by+dz+roof_height;
		addTPoint(x+dy3+dx*0.5,y,0);
		///////////
	}


	@Override
	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);

	}

	
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

	protected int[][][] faces=null;
	

}
