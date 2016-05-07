package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.main.Renderer3D;
/**
 * 
 * STAND ASCENDING IN Y DIRECTION
 * @author francesco
 *
 */
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
		double dyStep=dy/numSteps;

		//lower and upper base
		for(int k=0;k<numSteps;k++){

	
			double x0=0;
			double y0=dyStep*k;
			double y1=dy;
			double z0=dzStep*k;
			double z1=dzStep*(k+1);
			

			//lower step base
			addPoint(x0,y0,z0);
			addPoint(x0+dx,y0,z0);
			addPoint(x0+dx,y1,z0);
			addPoint(x0,y1,z0);

			//upper step base
			addPoint(x0,y0,z1);
			addPoint(x0+dx,y0,z1);
			addPoint(x0+dx,y1,z1);
			addPoint(x0,y1,z1);

		}


		texturePoints=new Vector();

		//bottom?

		
		//left
		for(int k=0;k<numSteps;k++){

			double x0=bx; 
			double x1=bx+dy-dyStep*k;
			double y=(by+dy)+dzStep*k;


			addTPoint(x0,y,0);
			addTPoint(x1,y,0);
			addTPoint(x1,y+dzStep,0);
			addTPoint(x0,y+dzStep,0);
		}

		//body back

		for(int k=0;k<numSteps;k++){

			double x=bx+dy;
			double y=(by+dy)+k*2*dzStep;

			addTPoint(x,y,0);
			addTPoint(x+dx,y,0);
			addTPoint(x+dx,y+dzStep,0);
			addTPoint(x,y+dzStep,0);

		}

		//top side
		for(int k=0;k<numSteps;k++){

			double x=bx+dy;
			double y=(by+dy)+k*2*dzStep+dzStep;

			addTPoint(x,y,0);
			addTPoint(x+dx,y,0);
			addTPoint(x+dx,y+dzStep,0);
			addTPoint(x,y+dzStep,0);
		}

		//right
		for(int k=0;k<numSteps;k++){

			double x0=bx+dy+dx+k*dyStep;
			double x1=bx+dy+dx+dy;
			double y=(by+dy)+dzStep*k;


			addTPoint(x0,y,0);
			addTPoint(x1,y,0);
			addTPoint(x1,y+dzStep,0);
			addTPoint(x0,y+dzStep,0);
		}


		//body front

		for(int k=0;k<numSteps;k++){

			double x=bx+dy+dx+dy;
			double y=(by+dy)+k*dzStep;

			//front
			addTPoint(x,y,0);
			addTPoint(x+dx,y,0);
			addTPoint(x+dx,y+dzStep,0);
			addTPoint(x,y+dzStep,0);
		}
		
		//bottom

		double xx=bx+dy;
		double yy=by;

		addTPoint(xx,yy,0);
		addTPoint(xx+dx,yy,0);
		addTPoint(xx+dx,yy+dy,0);
		addTPoint(xx,yy+dy,0);
		
		int NF=numSteps;
		faces=new int[5*NF+1][3][4];


		//left side
		for (int i = 0; i < NF; i++) {

			int b=i*8;
			int c=i*4;

			faces[i][0][0]=Renderer3D.CAR_LEFT;


			faces[i][1][0]=b;
			faces[i][1][1]=b+4;
			faces[i][1][2]=b+3+4;
			faces[i][1][3]=b+3;

			faces[i][2][0]=c;
			faces[i][2][1]=c+1;
			faces[i][2][2]=c+2;
			faces[i][2][3]=c+3;
		}

		//back side
		for (int i = 0; i < NF; i++) {

			int b=i*8;
			int c=NF*4+i*4;

			//back side
			faces[i+NF][0][0]=Renderer3D.CAR_BACK;


			faces[i+NF][1][0]=b;
			faces[i+NF][1][1]=b+1;
			faces[i+NF][1][2]=b+1+4;
			faces[i+NF][1][3]=b+4;

			
			faces[i+NF][2][0]=c;
			faces[i+NF][2][1]=c+1;
			faces[i+NF][2][2]=c+2;
			faces[i+NF][2][3]=c+3;

		}

		//top side
		for (int i = 0; i < NF; i++) {

			int b=i*8;
			int c=NF*8+i*4;

			faces[i+2*NF][0][0]=Renderer3D.CAR_TOP;


			faces[i+2*NF][1][0]=b+4;
			faces[i+2*NF][1][1]=b+1+4;
			faces[i+2*NF][1][2]=b+2+4;
			faces[i+2*NF][1][3]=b+3+4;
			
			faces[i+2*NF][2][0]=c;
			faces[i+2*NF][2][1]=c+1;
			faces[i+2*NF][2][2]=c+2;
			faces[i+2*NF][2][3]=c+3;


		}

		//right side
		for (int i = 0; i < NF; i++) {

			int b=i*8;
			int c=NF*12+i*4;

			faces[i+3*NF][0][0]=Renderer3D.CAR_RIGHT;


			faces[i+3*NF][1][0]=b+1;
			faces[i+3*NF][1][1]=b+2;
			faces[i+3*NF][1][2]=b+2+4;
			faces[i+3*NF][1][3]=b+1+4;
			
			faces[i+3*NF][2][0]=c;
			faces[i+3*NF][2][1]=c+1;
			faces[i+3*NF][2][2]=c+2;
			faces[i+3*NF][2][3]=c+3;

		}

		//front side
		for (int i = 0; i < NF; i++) {

			int b=i*8;
			int c=NF*16+i*4;

			faces[i+4*NF][0][0]=Renderer3D.CAR_FRONT;


			faces[i+4*NF][1][0]=b+2;
			faces[i+4*NF][1][1]=b+3;
			faces[i+4*NF][1][2]=b+3+4;
			faces[i+4*NF][1][3]=b+2+4;

			faces[i+4*NF][2][0]=c;
			faces[i+4*NF][2][1]=c+1;
			faces[i+4*NF][2][2]=c+2;
			faces[i+4*NF][2][3]=c+3;

		}
		
		//bottom
		int cf=NF*20;
		
		faces[5*NF][0][0]=Renderer3D.CAR_FRONT;

		faces[5*NF][1][0]=0;
		faces[5*NF][1][1]=3;
		faces[5*NF][1][2]=2;
		faces[5*NF][1][3]=1;

		faces[5*NF][2][0]=cf;
		faces[5*NF][2][1]=cf+1;
		faces[5*NF][2][2]=cf+2;
		faces[5*NF][2][3]=cf+3;

		IMG_WIDTH=(int) (2*bx+2*dy+2*dx);
		IMG_HEIGHT=(int) (2*by+dy+dz1*2);
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
			printTexturePolygon(bg,b,b+1,b+2,b+3);
			

		}

		//back 
		bg.setColor(Color.BLACK);
		for (int i = 0; i < NF; i++) {

			int b=NF*4+i*4;
			printTexturePolygon(bg,b,b+1,b+2,b+3);
			
		}
		//top
		bg.setColor(Color.BLUE);
		for (int i = 0; i < NF; i++) {

			int b=NF*8+i*4;
			printTexturePolygon(bg,b,b+1,b+2,b+3);
			
		}

		bg.setColor(Color.MAGENTA);
		//right side
		 for (int i = 0; i < NF; i++) {

			int b=NF*12+i*4;
			printTexturePolygon(bg,b,b+1,b+2,b+3);
			
		}

		//front side
		 bg.setColor(Color.CYAN);
		for (int i = 0; i < NF; i++) {

			int b=NF*16+i*4;
			printTexturePolygon(bg,b,b+1,b+2,b+3);
			

		}
		
		//bottom
		int cf=NF*20;
		bg.setColor(Color.YELLOW);
		printTextureLine(bg,cf,cf+1);
		printTextureLine(bg,cf+1,cf+2);
		printTextureLine(bg,cf+2,cf+3);
		printTextureLine(bg,cf+3,cf);
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
