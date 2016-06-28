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

	private double dx=100;
	private double dx1=100;
	private double dy=200;
	private double dy1=200;
	private double dy2=200;
	private double dy3=200;
	private double dz=20;
	private double roof_height=50;

	private int bx=10;
	private int by=10;
	
	public static String NAME="Church";
	
	public Church0Model(double dx, double dy, double dz,double roof_height,
			double dx1,double dy1,double dy2,double dy3
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
	}


	@Override
	public void initMesh() {
		
		BPoint[][][] mainCross=new BPoint[4][4][2];
		
		BPoint[][][] crossRoof=new BPoint[3][3][1];

		BPoint[][][] swAisle=new BPoint[2][2][3];
		BPoint[][][] seAisle=new BPoint[2][2][3];
		BPoint[][][] nwAisle=new BPoint[2][2][3];
		BPoint[][][] neAisle=new BPoint[2][2][3];
		
		points=new Vector();
		//lower and upper base
		for(int k=0;k<2;k++){

			double z=dz*k;

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
			swAisle[0][0][k]=addBPoint(dy3+dx,0.0,z);
			swAisle[1][0][k]=addBPoint(dy3+dx+dy1,0.0,z);
			swAisle[1][1][k]=addBPoint(dy3+dx+dy1,dy,z);
			swAisle[0][1][k]=addBPoint(dy3+dx,dy,z);			
			if(k==2){
				
				swAisle[0][0][k+1]=addBPoint(dy3+dx*0.5,0.0,z+roof_height);
				swAisle[0][1][k+1]=addBPoint(dy3+dx*0.5,0.0,z+roof_height);
			}
			
			seAisle[0][0][k]=addBPoint(dy3+dx,dy+dx1,z);
			seAisle[1][0][k]=addBPoint(dy3+dx+dy1,dy+dx1,z);
			seAisle[1][1][k]=addBPoint(dy3+dx+dy1,dy+dx1+dy2,z);
			seAisle[0][1][k]=addBPoint(dy3+dx,dy+dx1+dy2,z);
			if(k==2){
				
				seAisle[0][0][k+1]=addBPoint(dy3+dx+dy1*0.5,dy+dx1,z+roof_height);
				seAisle[0][1][k+1]=addBPoint(dy3+dx+dy1*0.5,dy+dx1,z+roof_height);
			}
			
			nwAisle[0][0][k]=addBPoint(0,dy+dx1,z);
			nwAisle[1][0][k]=addBPoint(dy3,dy+dx1,z);
			nwAisle[1][1][k]=addBPoint(dy3,dy+dx1+dy2,z);
			nwAisle[0][1][k]=addBPoint(0,dy+dx1+dy2,z);
			if(k==2){
				
				nwAisle[0][0][k+1]=addBPoint(dy3+dx+dy1*0.5,0.0,z+roof_height);
				nwAisle[0][1][k+1]=addBPoint(dy3+dx+dy1*0.5,0.0,z+roof_height);
			}
			
			neAisle[0][0][k]=addBPoint(0,0.0,z);
			neAisle[1][0][k]=addBPoint(dy3,0.0,z);
			neAisle[1][1][k]=addBPoint(dy3,dy,z);
			neAisle[0][1][k]=addBPoint(0,dy,z);
			if(k==2){
				
				neAisle[0][0][k+1]=addBPoint(dy3+dx*0.5,dy+dx1,z+roof_height);
				neAisle[0][1][k+1]=addBPoint(dy3+dx*0.5,dy+dx1,z+roof_height);
			}
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
		NF+=12+8;//CROSS+CROSS_ROOF

		faces=new int[NF][3][4];
		
		int counter=0;
		counter=buildCross(counter,mainCross,crossRoof);
		counter=buildAisle(counter,swAisle);
		counter=buildAisle(counter,seAisle);
		counter=buildAisle(counter,nwAisle);
		counter=buildAisle(counter,neAisle);
		

		IMG_WIDTH=(int) (2*bx+dx);
		IMG_HEIGHT=(int) (2*by+dy);
	}

	private int buildCross(int counter, BPoint[][][] mainCross, BPoint[][][] crossRoof) {
		
		int c0=0;
		int c1=1;
		int c2=2;
		int c3=3;
		
		//first wing
		faces[counter++]=buildFace(Renderer3D.CAR_LEFT, mainCross[1][0][0],mainCross[1][0][1],mainCross[1][1][1],mainCross[1][1][0], c0, c1, c2, c3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, mainCross[1][0][0],mainCross[2][0][0],mainCross[2][0][1],mainCross[1][0][1], c0, c1, c2, c3);
		faces[counter++]=buildFace(Renderer3D.CAR_RIGHT,mainCross[2][0][0],mainCross[2][1][0],mainCross[2][1][1],mainCross[2][0][1], c0, c1, c2, c3);
		//roof, to test later
		faces[counter++]=buildFace(Renderer3D.CAR_TOP, mainCross[1][0][1],crossRoof[1][0][0],crossRoof[1][1][0],mainCross[1][1][1], c0, c1, c2, c3);
		faces[counter++]=buildFace(Renderer3D.CAR_TOP, mainCross[2][0][1],mainCross[2][1][1],crossRoof[1][1][0],crossRoof[1][0][0], c0, c1, c2, c3);
		
		//second wing
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, mainCross[2][1][0],mainCross[3][1][0],mainCross[3][1][1],mainCross[2][1][1], c0, c1, c2, c3);
		faces[counter++]=buildFace(Renderer3D.CAR_RIGHT,mainCross[3][1][0],mainCross[3][2][0],mainCross[3][2][1],mainCross[3][1][1], c0, c1, c2, c3);
		faces[counter++]=buildFace(Renderer3D.CAR_FRONT,mainCross[2][2][0],mainCross[2][2][1],mainCross[3][2][1],mainCross[3][2][0], c0, c1, c2, c3);
		//roof, to test later
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,mainCross[2][1][1],mainCross[3][1][1],crossRoof[2][1][0],crossRoof[1][1][0], c0, c1, c2, c3);
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,mainCross[2][2][1],crossRoof[1][1][0],crossRoof[2][1][0],mainCross[3][2][1], c0, c1, c2, c3);
		
		//third wing
		faces[counter++]=buildFace(Renderer3D.CAR_FRONT,mainCross[1][3][0],mainCross[1][3][1],mainCross[2][3][1],mainCross[2][3][0], c0, c1, c2, c3);		
		faces[counter++]=buildFace(Renderer3D.CAR_RIGHT,mainCross[2][2][0],mainCross[2][3][0],mainCross[2][3][1],mainCross[2][2][1], c0, c1, c2, c3);
		faces[counter++]=buildFace(Renderer3D.CAR_LEFT, mainCross[1][2][0],mainCross[1][2][1],mainCross[1][3][1],mainCross[1][3][0], c0, c1, c2, c3);
		//roof, to test later	
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,mainCross[2][2][1],mainCross[2][3][1],crossRoof[1][2][0],crossRoof[1][1][0], c0, c1, c2, c3);
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,mainCross[1][3][1],mainCross[1][2][1],crossRoof[1][1][0],crossRoof[1][2][0], c0, c1, c2, c3);
		
		//fourth wing
		faces[counter++]=buildFace(Renderer3D.CAR_FRONT,mainCross[0][2][0],mainCross[0][2][1],mainCross[1][2][1],mainCross[1][2][0], c0, c1, c2, c3);		
		faces[counter++]=buildFace(Renderer3D.CAR_LEFT,mainCross[0][1][0],mainCross[0][1][1],mainCross[0][2][1],mainCross[0][2][0], c0, c1, c2, c3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK,mainCross[0][1][0],mainCross[1][1][0],mainCross[1][1][1],mainCross[0][1][1], c0, c1, c2, c3);		
		//roof, to test later
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,mainCross[0][1][1],mainCross[1][1][1],crossRoof[1][1][0],crossRoof[0][1][0], c0, c1, c2, c3);
		faces[counter++]=buildFace(Renderer3D.CAR_TOP,mainCross[1][2][1],mainCross[0][2][1],crossRoof[0][1][0],crossRoof[1][1][0], c0, c1, c2, c3);
		
		return counter;
	}


	private int buildAisle(int counter, BPoint[][][] swAisle) {
		
		int c0=0;
		int c1=1;
		int c2=2;
		int c3=3;
		
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, swAisle[0][0][0],swAisle[0][1][0],swAisle[1][1][0],swAisle[0][1][0], c0, c1, c2, c3);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, swAisle[0][0][0],swAisle[1][0][0],swAisle[1][0][1],swAisle[0][0][1], c0, c1, c2, c3);
		faces[counter++]=buildFace(Renderer3D.CAR_LEFT, swAisle[0][0][0],swAisle[0][0][1],swAisle[0][1][1],swAisle[0][1][0], c0, c1, c2, c3);
		faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, swAisle[1][0][0],swAisle[1][1][0],swAisle[1][1][1],swAisle[1][0][1], c0, c1, c2, c3);
		faces[counter++]=buildFace(Renderer3D.CAR_TOP, swAisle[0][0][1],swAisle[1][0][1],swAisle[1][1][1],swAisle[0][1][1], c0, c1, c2, c3);
		faces[counter++]=buildFace(Renderer3D.CAR_FRONT, swAisle[0][1][0],swAisle[0][1][1],swAisle[1][1][1],swAisle[1][1][0], c0, c1, c2, c3);
		
		
		//roof, to test after
		//faces[counter++]=buildFace(Renderer3D.CAR_TOP, swAisle[0][0][1],swAisle[0][0][2],swAisle[0][1][2],swAisle[0][1][1], c0, c1, c2, c3);
		//faces[counter++]=buildFace(Renderer3D.CAR_TOP, swAisle[1][0][1],swAisle[1][1][1],swAisle[0][1][2],swAisle[0][0][2], c0, c1, c2, c3);
		
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

	private int[][][] faces=null;
	

}
