package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.main.Renderer3D;

public class Mansard0Model extends MeshModel{

	private double dx=100;
	private double dy=200;
	private double dx1=100;
	private double dy1=200;
	private double dz=20;
	private double roof_height=50;

	private int bx=10;
	private int by=10;
	
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


	@Override
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
		
		double z=dz+roof_height;
		
		addPoint(e,d,z);
		addPoint(dx-e,d,z);
		addPoint(dx-e,dy-d,z);
		addPoint(e,dy-d,z);

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
		
		x=bx+d+dx;
		
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

	private int[][][] faces={

			//base

			{{Renderer3D.CAR_BACK},{0,1,5,4},{4,5,10,9}},
			{{Renderer3D.CAR_RIGHT},{1,2,6,5},{5,6,11,10}},
			{{Renderer3D.CAR_FRONT},{2,3,7,6},{6,7,12,11}},
			{{Renderer3D.CAR_LEFT},{3,0,4,7},{7,8,13,12}},
			{{Renderer3D.CAR_BOTTOM},{0,3,2,1},{0,1,2,3}},
			

			//gables
			{{Renderer3D.CAR_BACK},{4,5,9,8},{9,10,15,14}},
			{{Renderer3D.CAR_RIGHT},{5,6,10,9},{10,11,16,15}},
			{{Renderer3D.CAR_FRONT},{6,7,11,10},{11,12,17,16}},
			{{Renderer3D.CAR_LEFT},{7,4,8,11},{12,13,18,17}},
			//top plain
			{{Renderer3D.CAR_TOP},{8,9,10,11},{19,20,21,22}},
		
	};
	
	String top1="22-21";
	String top0="19-20";
	String gabl="14-15-16-17-18";	
	String llf1="09-10-11-12-13";
	String llf0="04-05-06-07-08";
	String llb1="03-02";
	String llb0="00-01";
	
	String points_level2="8,9,10,11";
	String points_level1="4,5,6,7";
	String points_level0="0,1,2,3";
	

}
