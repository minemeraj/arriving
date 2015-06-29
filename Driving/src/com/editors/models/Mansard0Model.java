package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.editors.DoubleTextField;
import com.main.Renderer3D;

public class Mansard0Model extends MeshModel{

	double dx=100;
	double dy=200;
	double dx1=100;
	double dy1=200;
	double dz=20;
	double roof_height=50;

	int bx=10;
	int by=10;
	
	public static String NAME="Mansard0";
	
	public Mansard0Model(double dx, double dy, double dz,double roof_height,double dx1,double dy1) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		this.roof_height = roof_height;
		this.dx1 = dx1;
		this.dy1 = dy1;
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
		double d=(dy-dy1)*0.5;
		double e=(dx-dx1)*0.5;
		
		//addPoint(dx*0.5,d,dz+roof_height);
		//addPoint(dx*0.5,dy-d,dz+roof_height);

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
	
		//gable 
		y=by+dy+dz;
		addTPoint(x,y+roof_height,0);
		addTPoint(x+dx,y+roof_height,0);
		addTPoint(x+dy+dx,y+roof_height,0);
		addTPoint(x+dy+2*dx,y+roof_height,0);
		addTPoint(x+2*dy+2*dx,y+roof_height,0);		

		//top plain
		y=by+dy+dz+roof_height;
		
		addTPoint(x,y,0);
		addTPoint(x+dy1,y,0);
		addTPoint(x+dy1, y+dx1,0);
		addTPoint(x,y+dx1,0);
		
		

		IMG_WIDTH=(int) (2*dy+2*dx+2*bx);
		IMG_HEIGHT=(int) (dy+dz+2*by+roof_height+dx1);
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

        //gables
		bg.setColor(Color.BLUE);
		printTextureLine(bg,10,15);
		printTextureLine(bg,15,14);
		printTextureLine(bg,14,9);

		printTextureLine(bg,11,16);
		printTextureLine(bg,16,15);
		
		printTextureLine(bg,12,17);
		printTextureLine(bg,17,16);

		printTextureLine(bg,13,18);
		printTextureLine(bg,18,17);
		
		//top plain
		printTextureLine(bg,19,20);
		printTextureLine(bg,20,21);
		printTextureLine(bg,21,22);
		printTextureLine(bg,22,19);
	}

	int[][][] faces={

			//base

			{{Renderer3D.CAR_BACK},{0,1,5,4},{4,5,10,9}},
			{{Renderer3D.CAR_RIGHT},{1,2,6,5},{5,6,11,10}},
			{{Renderer3D.CAR_FRONT},{2,3,7,6},{6,7,12,11}},
			{{Renderer3D.CAR_LEFT},{3,0,4,7},{7,8,13,12}},
			{{Renderer3D.CAR_BOTTOM},{0,3,2,1},{0,1,2,3}},
			

			//gables
			{{Renderer3D.CAR_BACK},{4,5,8},{9,10,14}},
			{{Renderer3D.CAR_FRONT},{6,7,9},{11,12,15}},
			
			//roof pitches
			{{Renderer3D.CAR_TOP},{5,6,9,8},{16,17,19,18}},
			{{Renderer3D.CAR_TOP},{8,9,7,4},{18,19,21,20}},
	};
	
	/*String roo2="20-21";
	String roo1="18-19";
	String roo0="16-17";	
	String gables_top="14-15";*/
	
	String llf1="09-10-11-12-13";
	String llf0="04-05-06-07-08";
	String llb1="03-02";
	String llb0="00-01";
	
	String points_level2="8,9";
	String points_level1="4,5,6,7";
	String points_level0="0,1,2,3";
	

}
