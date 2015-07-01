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

public class Shed0Model extends MeshModel{

	double dx=100;
	double dy=200;
	double dz1=200;
	double dz=20;

	int bx=10;
	int by=10;
	
	public static String NAME="Shed0";
	
	public Shed0Model(double dx, double dy, double dz,double dz1) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		this.dz1 = dz1;
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
		
		double dzm=Math.max(dz,dz1);

		//lower and upper base
		for(int k=0;k<2;k++){

			double z=dz*k;
			double z1=dz1*k;

			addPoint(0.0,0.0,z);
			addPoint(dx,0.0,z1);
			addPoint(dx,dy,z1);
			addPoint(0.0,dy,z);

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
		addTPoint(x+dx,y+dz1,0);
		addTPoint(x+dy+dx,y+dz1,0);
		addTPoint(x+dy+2*dx,y+dz,0);
		addTPoint(x+2*dy+2*dx,y+dz,0);
	
		//roof
		y=by+dy+dzm;

		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx, y+dy,0);
		addTPoint(x,y+dy,0);
		

		IMG_WIDTH=(int) (2*bx+2*dy+2*dx);
		IMG_HEIGHT=(int) (2*by+2*dy+dzm);
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

        //roof
		bg.setColor(Color.BLUE);
		printTextureLine(bg,14,15);
		printTextureLine(bg,15,16);
		printTextureLine(bg,16,17);
		printTextureLine(bg,17,14);

	}

	int[][][] faces={

			//base

			{{Renderer3D.CAR_BACK},{0,1,5,4},{4,5,10,9}},
			{{Renderer3D.CAR_RIGHT},{1,2,6,5},{5,6,11,10}},
			{{Renderer3D.CAR_FRONT},{2,3,7,6},{6,7,12,11}},
			{{Renderer3D.CAR_LEFT},{3,0,4,7},{7,8,13,12}},
			{{Renderer3D.CAR_BOTTOM},{0,3,2,1},{0,1,2,3}},
			
			
			//roof 
			{{Renderer3D.CAR_TOP},{4,5,6,7},{14,15,16,17}},
	
	};
	

	String roo1="17-16";
	String roo0="14-15";	
	String llf1="09-10-11-12-13";
	String llf0="04-05-06-07-08";
	String llb1="03-02";
	String llb0="00-01";
	
	String points_level1="4,5,6,7";
	String points_level0="0,1,2,3";
	

}
