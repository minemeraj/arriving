package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.main.Renderer3D;

public class Gambrel0Model extends MeshModel{

	double dx=100;
	double dy=200;
	double dz=20;


	int bx=10;
	int by=10;
	
	public static String NAME="Gambrel0";
	
	public Gambrel0Model(double dx, double dy, double dz) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}


	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);

		for (int i = 0; i < faces.length; i++) {

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



	public void initMesh() {

		points=new Vector();

		//lower and upper base
		for(int k=0;k<2;k++){

			double z=dz*k;

			addPoint(0.0,0.0,z);
			addPoint(dx,0.0,z);
			addPoint(dx,dy,z);
			addPoint(0.0,dy,z);

		}
		
		//roof
		
		double teta=Math.PI/4;
		
		for(int j=0;j<2;j++){
		
			for(int i=1;i<4;i++){
				
				double dt=teta*i;
				
				double xx=dx*0.5-dx*0.5*Math.cos(dt);
				double yy=j*dy;
				double zz=dz+dx*0.5*Math.sin(dt);
				
				addPoint(xx,yy,zz);
			}

		}
		
		texturePoints=new Vector();

	
		///////main plane

		//lower base
		double y=by;
		double x=bx;

		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx, y+dy,0);
		addTPoint(x,y+dy,0);

		//faces
		y=by+dy;

		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dy+dx,y,0);
		addTPoint(x+dy+2*dx,y,0);
		addTPoint(x+2*dy+2*dx,y,0);    	


		addTPoint(x,y+dz,0);
		addTPoint(x+dx,y+dz,0);
		addTPoint(x+dy+dx,y+dz,0);
		addTPoint(x+dy+2*dx,y+dz,0);
		addTPoint(x+2*dy+2*dx,y+dz,0);
		
		
		
		//gambrel gables
		y=by+dy+dz;
			
		for(int i=1;i<4;i++){
			
			double dt=teta*i;
			
			double xx=x+dx*0.5-dx*0.5*Math.cos(dt);
			double yy=dx*0.5*Math.sin(dt)+y;
			
			
			addTPoint(xx,yy,0);
		}
		
		for(int i=1;i<4;i++){
			
			double dt=teta*i;
			
			double xx=x+dx+dy+dx*0.5-dx*0.5*Math.cos(dt);
			double yy=dx*0.5*Math.sin(dt)+y;
			
			
			addTPoint(xx,yy,0);
		}
		x=bx+dx;
		y=by+dy+dz+dx*0.5;

		//roof pitches
		addTPoint(x,y,0);
		addTPoint(x+dy,y,0);
		addTPoint(x,y+dx*0.25,0);
		addTPoint(x+dy,y+dx*0.25,0);
		addTPoint(x,y+dx*0.5,0);
		addTPoint(x+dy,y+dx*0.5,0);
		addTPoint(x,y+dx*0.75,0);
		addTPoint(x+dy,y+dx*0.75,0);
		addTPoint(x,y+dx,0);
		addTPoint(x+dy,y+dx,0);
		
		

		IMG_WIDTH=(int) (2*dy+2*dx+2*bx);
		IMG_HEIGHT=(int) (2*by+1.5*dx+dz+dy);
	}





	public void printTexture(Graphics2D bg) {


		//draw lines for reference

		bg.setColor(Color.RED);
		bg.setStroke(new BasicStroke(0.1f));

		//lower base
		printTextureLine(bg,0,1);
		printTextureLine(bg,1,2);
		printTextureLine(bg,2,3);
		printTextureLine(bg,3,0);

		//lateral faces
		bg.setColor(Color.BLACK);
		printTextureLine(bg,4,5);
		printTextureLine(bg,5,10);
		printTextureLine(bg,10,9);
		printTextureLine(bg,9,4);

		printTextureLine(bg,5,6);
		printTextureLine(bg,6,11);
		printTextureLine(bg,11,10);

		printTextureLine(bg,6,7);
		printTextureLine(bg,7,12);
		printTextureLine(bg,12,11);

		printTextureLine(bg,7,8);
		printTextureLine(bg,8,13);
		printTextureLine(bg,13,12);

        //gambrel gables
		bg.setColor(Color.BLUE);
		printTextureLine(bg,9,10);
		printTextureLine(bg,10,16);
		printTextureLine(bg,16,15);
		printTextureLine(bg,15,14);
		printTextureLine(bg,14,9);
		
		bg.setColor(Color.BLUE);
		printTextureLine(bg,11,12);
		printTextureLine(bg,12,19);
		printTextureLine(bg,19,18);
		printTextureLine(bg,18,17);
		printTextureLine(bg,17,11);
		
		//roof pitches
		printTextureLine(bg,20,21);
		printTextureLine(bg,21,23);
		printTextureLine(bg,23,22);
		printTextureLine(bg,22,20);
		
		printTextureLine(bg,23,25);
		printTextureLine(bg,25,24);
		printTextureLine(bg,24,22);

		printTextureLine(bg,25,27);
		printTextureLine(bg,27,26);
		printTextureLine(bg,26,24);
		
		printTextureLine(bg,27,29);
		printTextureLine(bg,29,28);
		printTextureLine(bg,28,26);
	}

	int[][][] faces={

			//base

			{{Renderer3D.CAR_BACK},{0,1,5,4},{4,5,10,9}},
			{{Renderer3D.CAR_RIGHT},{1,2,6,5},{5,6,11,10}},
			{{Renderer3D.CAR_FRONT},{2,3,7,6},{6,7,12,11}},
			{{Renderer3D.CAR_LEFT},{3,0,4,7},{7,8,13,12}},
			{{Renderer3D.CAR_BOTTOM},{0,3,2,1},{0,1,2,3}},
			

			//gables
			{{Renderer3D.CAR_BACK},{4,5,10,9,8},{9,10,16,15,14}},
			{{Renderer3D.CAR_FRONT},{6,7,11,12,13},{11,12,19,18,17}},
						
			//roof pitches
			{{Renderer3D.CAR_TOP},{4,8,11,7},{28,26,27,29}},
			{{Renderer3D.CAR_TOP},{8,9,12,11},{26,24,25,27}},
			{{Renderer3D.CAR_TOP},{9,10,13,12},{24,22,23,25}},
			{{Renderer3D.CAR_TOP},{10,5,6,13},{22,20,21,23}},
			
	};
	
	String roo4="28-29";
	String roo3="26-27";
	String roo2="24-25";
	String roo1="22-23";
	String roo0="20-21";	
	
	String gables_top="14-15-16"+"17-18-19";
	String llf1="09-10-11-12-13";
	String llf0="04-05-06-07-08";
	String llb1="03-02";
	String llb0="00-01";
	
	String points_level2="8,9,10"+"11,12,13";
	String points_level1="4,5,6,7";
	String points_level0="0,1,2,3";
	
	

}
