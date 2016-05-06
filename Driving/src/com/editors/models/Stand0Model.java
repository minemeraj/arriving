package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.main.Renderer3D;

public class Stand0Model extends MeshModel{

	private double dx=100;
	private double dy=200;
	private double dz0=20;
	private double dz1=200;

	private int bx=10;
	private int by=10;

	public static String NAME="Stand0";

	public Stand0Model(double dx, double dy, double dz0,double dz1) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz0 = dz0;
		this.dz1 = dz1;
	}


	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);

		for (int i = 0; faces!=null && i < faces.length; i++) {

			int[][] face=faces[i];

			int[] fts=face[0];
			int[] pts=face[1];
			int[] tts=face[2];

			String line="f=["+fts[0]+"]";

			for (int j = 0; j < pts.length; j++) {

				if(j>0)
					line+=" ";
				line+=(pts[j]+"/"+tts[j]);
			}

			print(pw,line);

		}

	}


	@Override
	public void initMesh() {

		points=new Vector();

		int numSteps=(int) (dz1/dz0);

		double dzStep=dz0;
		double dxStep=dx/numSteps;

		//lower and upper base
		for(int k=0;k<numSteps;k++){

			double z0=dzStep*k;
			double z1=dzStep*(k+1);

			double x0=dxStep*k;
			double x1=dx;

			//lower step base
			addPoint(x0,0.0,z0);
			addPoint(x0+dx,0.0,z0);
			addPoint(x1,dy,z0);
			addPoint(x1,dy,z0);

			//upper step base
			addPoint(x0,0.0,z1);
			addPoint(x0+dx,0.0,z1);
			addPoint(x1,dy,z1);
			addPoint(x1,dy,z1);

		}


		texturePoints=new Vector();

		//bottom?


		//left
		for(int k=0;k<numSteps;k++){

			double x=bx+dxStep*k;
			double y=by+dzStep*k;


			addTPoint(x,y,0);
			addTPoint(x+dx,y,0);
			addTPoint(x+dx,y+dzStep,0);
			addTPoint(x,y+dzStep,0);
		}

		//body back and top

		for(int k=0;k<numSteps;k++){

			double x=bx+dx;
			double y=by+k*2*dzStep;

			//front
			addTPoint(x,y,0);
			addTPoint(x+dy,y,0);
			addTPoint(x+dy,y+dzStep,0);
			addTPoint(x,y+dzStep,0);

			//top
			addTPoint(x,y+dzStep,0);
			addTPoint(x+dy,y+dzStep,0);
			addTPoint(x+dy,y+2*dzStep,0);
			addTPoint(x,y+2*dzStep,0);
		}


		//right
		for(int k=0;k<numSteps;k++){

			double x=bx+dx+dy;
			double y=by+dzStep*k;


			addTPoint(x,y,0);
			addTPoint(x+dx-(dxStep*k),y,0);
			addTPoint(x+dx-(dxStep*k),y+dzStep,0);
			addTPoint(x,y+dzStep,0);
		}


		//body front

		for(int k=0;k<numSteps;k++){

			double x=bx+dx+dy+dx;
			double y=by+k*dzStep;

			//front
			addTPoint(x,y,0);
			addTPoint(x+dy,y,0);
			addTPoint(x+dy,y+dzStep,0);
			addTPoint(x,y+dzStep,0);
		}


		int NF=numSteps;
		faces=new int[5*NF][3][4];


		//left side
		for (int i = 0; i < NF; i++) {

			int b=i*4;
			int c=i*4;

			faces[i][0][0]=Renderer3D.CAR_LEFT;


			faces[i][1][0]=b;
			faces[i][1][1]=b+4;
			faces[i][1][2]=b+3+4;
			faces[i][1][3]=b+3;

			faces[i][2][0]=c;
			faces[i][2][1]=c+1;
			faces[i][2][2]=c+1+4;
			faces[i][2][3]=c+4;

		}

		//back and side
		for (int i = 0; i < NF; i++) {

			int b=i*4;
			int c=NF+i*4;

			//back side
			faces[i+NF][0][0]=Renderer3D.CAR_BACK;


			faces[i+NF][1][0]=b;
			faces[i+NF][1][1]=b+1;
			faces[i+NF][1][2]=b+1+4;
			faces[i+NF][1][3]=b+4;

			faces[i+NF][2][0]=c;
			faces[i+NF][2][1]=c+1;
			faces[i+NF][2][2]=c+1+4;
			faces[i+NF][2][3]=c+4;

		}

		//top side
		for (int i = 0; i < NF; i++) {

			int b=i*4;
			int c=2*NF+i*4;

			faces[i+2*NF][0][0]=Renderer3D.CAR_TOP;


			faces[i+2*NF][1][0]=b+4;
			faces[i+2*NF][1][1]=b+1+4;
			faces[i+2*NF][1][2]=b+1+8;
			faces[i+2*NF][1][3]=b+8;

			faces[i+2*NF][2][0]=c;
			faces[i+2*NF][2][1]=c+1;
			faces[i+2*NF][2][2]=c+1+4;
			faces[i+2*NF][2][3]=c+4;

		}

		//right side
		for (int i = 0; i < NF; i++) {

			int b=i*4;
			int c=3*NF+i*4;

			faces[i+3*NF][0][0]=Renderer3D.CAR_RIGHT;


			faces[i+3*NF][1][0]=b+1;
			faces[i+3*NF][1][1]=b+2;
			faces[i+3*NF][1][2]=b+2+4;
			faces[i+3*NF][1][3]=b+1+4;

			faces[i+3*NF][2][0]=c;
			faces[i+3*NF][2][1]=c+1;
			faces[i+3*NF][2][2]=c+1+4;
			faces[i+3*NF][2][3]=c+4;

		}

		//front side
		for (int i = 0; i < NF; i++) {

			int b=i*4;
			int c=4*NF+i*4;

			faces[i+4*NF][0][0]=Renderer3D.CAR_FRONT;


			faces[i+4*NF][1][0]=b+2;
			faces[i+4*NF][1][1]=b+3;
			faces[i+4*NF][1][2]=b+3+4;
			faces[i+4*NF][1][3]=b+2+4;

			faces[i+4*NF][2][0]=c;
			faces[i+4*NF][2][1]=c+1;
			faces[i+4*NF][2][2]=c+1+4;
			faces[i+4*NF][2][3]=c+4;

		}


		IMG_WIDTH=(int) (2*bx+2*dy+2*dx);
		IMG_HEIGHT=(int) (2*by+dz1*2);
	}





	public void printTexture(Graphics2D bg) {


		//draw lines for reference

		bg.setColor(Color.RED);
		bg.setStroke(new BasicStroke(0.1f));
		
		int numSteps=(int) (dz1/dz0);

		int NF=numSteps;
		

		//left side
		for (int i = 0; i < NF; i++) {

			int b=i*4;
			printTexturePolygon(bg,b,b+1,b+1+4,b+4);
			

		}

		//back side
		for (int i = 0; i < NF; i++) {

			int b=NF+i*8;
			printTexturePolygon(bg,b,b+1,b+1+4,b+4);
			
		}

		//top side
		for (int i = 0; i < NF; i++) {

			int b=2*NF+4+i*8;
			printTexturePolygon(bg,b,b+1,b+1+4,b+4);
			

		}

		//right side
		for (int i = 0; i < NF; i++) {

			int b=3*NF+i*4;
			printTexturePolygon(bg,b,b+1,b+1+4,b+4);
			
		}

		//front side
		for (int i = 0; i < NF; i++) {

			int b=4*NF+i*4;
			printTexturePolygon(bg,b,b+1,b+1+4,b+4);
			

		}

	}

	private int[][][] faces=null;


	String roo1="17-16";
	String roo0="14-15";	
	String llf1="09-10-11-12-13";
	String llf0="04-05-06-07-08";
	String llb1="03-02";
	String llb0="00-01";

	String points_level1="4,5,6,7";
	String points_level0="0,1,2,3";


}
