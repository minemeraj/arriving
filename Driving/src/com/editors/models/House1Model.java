package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.main.Renderer3D;

public class House1Model extends MeshModel{

	double dx=100;
	double dy=200;
	double dz=20;
	double roof_height=50;
	double dx1=100;
	double dy1=200;

	int bx=10;
	int by=10;
	
	public static String NAME="Gable1";
	
	public House1Model(double dx, double dy, double dz,double roof_height,double dx1, double dy1) {
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
			addPoint(dx+dy1,dy,z);
			addPoint(dx+dy1,dy+dx1,z);
			addPoint(0,dy+dx1,z);
		}
		
		//roof	
		addPoint(dx*0.5,0,dz+roof_height);
		addPoint(0,dy,dz);
		addPoint(dx*0.5,dy+dx1*0.5,dz+roof_height);
		addPoint(dx+dy1,dy+dx1*0.5,dz+roof_height);
		
		texturePoints=new Vector();

	
		///////main plane

		//lower base
		double y=by;
		double x=bx;
		
		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx,y+dy,0);
		addTPoint(x+dx+dy1,y+dy,0);
		addTPoint(x+dx+dy1,y+dy+dx1,0);
		addTPoint(x,y+dy+dx1,0);

	
		//faces
		y=by+dy+dx1;

		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx+dy,y,0);
		addTPoint(x+dx+dy+dy1,y,0);
		addTPoint(x+dx+dy+dy1+dx1,y,0);    	
		addTPoint(x+dx+dy+dy1+dx1+(dx+dy1),y,0);
		addTPoint(x+dx+dy+dy1+dx1+(dx+dy1)+(dx1+dy),y,0);
		
		y=by+dy+dx1+dz;

		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx+dy,y,0);
		addTPoint(x+dx+dy+dy1,y,0);
		addTPoint(x+dx+dy+dy1+dx1,y,0);    	
		addTPoint(x+dx+dy+dy1+dx1+(dx+dy1),y,0);
		addTPoint(x+dx+dy+dy1+dx1+(dx+dy1)+(dx1+dy),y,0);
		
		//gables tops
		y=by+dy+dx1+dz+roof_height;
		
		addTPoint(x+dx*0.5,y,0);
		addTPoint(x+dx+dy+dy1+dx1*0.5,y,0); 
		
		y=by+dy+dx1+dz+roof_height;

		//roof
		addTPoint(x,y,0);
		addTPoint(x+dx*0.5,y,0);
		addTPoint(x+dx,y,0);
		
		addTPoint(x,y+dy,0);
		addTPoint(x+dx,y+dy,0);
		addTPoint(x+dx+dy1,y+dy,0);
		
		addTPoint(x+dx*0.5,y+dy+dx1*0.5,0);
		addTPoint(x+dx+dy1,y+dy+dx1*0.5,0);
		
		addTPoint(x,y+dy+dx1,0);
		addTPoint(x+dx+dy1,y+dy+dx1,0);

		IMG_WIDTH=(int) (2*bx+2*(dx+dy+dy1+dx1));
		IMG_HEIGHT=(int) (2*by+2*(dx1+dy)+dz+roof_height);
	}





	public void printTexture(Graphics2D bg) {


		//draw lines for reference

		bg.setColor(Color.RED);
		bg.setStroke(new BasicStroke(0.1f));

		//lower base
		printTextureLine(bg,0,1);
		printTextureLine(bg,1,2);
		printTextureLine(bg,2,3);
		printTextureLine(bg,3,4);
		printTextureLine(bg,4,5);
		printTextureLine(bg,5,0);

		//lateral faces
		bg.setColor(Color.BLACK);
		printTextureLine(bg,6,7);
		printTextureLine(bg,7,14);
		printTextureLine(bg,14,13);
		printTextureLine(bg,13,6);
		
		printTextureLine(bg,7,8);
		printTextureLine(bg,8,15);
		printTextureLine(bg,15,14);
		
		printTextureLine(bg,8,9);
		printTextureLine(bg,9,16);
		printTextureLine(bg,16,15);
		
		printTextureLine(bg,9,10);
		printTextureLine(bg,10,17);
		printTextureLine(bg,17,16);
		
		printTextureLine(bg,10,11);
		printTextureLine(bg,11,18);
		printTextureLine(bg,18,17);
		
		printTextureLine(bg,11,12);
		printTextureLine(bg,12,19);
		printTextureLine(bg,19,18);
	
		//gables
		bg.setColor(Color.BLUE);

		printTextureLine(bg,13,14);
		printTextureLine(bg,14,20);
		printTextureLine(bg,20,13);
		
		printTextureLine(bg,16,17);
		printTextureLine(bg,17,21);
		printTextureLine(bg,21,16);
		

		//roof
		bg.setColor(Color.RED);
		printTexturePolygon(bg,22,23,28,25);
		
		printTexturePolygon(bg,23,24,26,28);
		printTexturePolygon(bg,26,27,29,28);
		printTexturePolygon(bg,28,29,31,30);
		printTexturePolygon(bg,30,25,28);

	}

	int[][][] faces={


			//base
			{{Renderer3D.CAR_BACK},{0,5,2,1},{0,5,2,1}},
			{{Renderer3D.CAR_BACK},{5,4,3,2},{5,4,3,2}},
			
			//faces
			{{Renderer3D.CAR_BACK},{0,1,7,6},{6,7,14,13}},
			{{Renderer3D.CAR_RIGHT},{1,2,8,7},{7,8,15,14}},
			{{Renderer3D.CAR_BACK},{2,3,9,8},{8,9,16,15}},
			{{Renderer3D.CAR_RIGHT},{3,4,10,9},{9,10,17,16}},
			{{Renderer3D.CAR_FRONT},{4,5,11,10},{10,11,18,17}},
			{{Renderer3D.CAR_LEFT},{5,0,6,11},{11,12,19,18}},
			
			
			//gables
			{{Renderer3D.CAR_BACK},{6,7,12},{13,14,20}},
			{{Renderer3D.CAR_RIGHT},{9,10,15},{16,17,21}},
			
			
			//roof pitches
			{{Renderer3D.CAR_TOP},{12,7,8,14},{23,24,26,28}},
			{{Renderer3D.CAR_TOP},{8,9,15,14},{26,27,29,28}},
			{{Renderer3D.CAR_TOP},{14,15,10,11},{28,29,31,30}},
			{{Renderer3D.CAR_TOP},{6,12,14,13},{22,23,28,25}},
			{{Renderer3D.CAR_TOP},{11,13,14},{30,25,28}},
	};
	
	String roof3="30-31";
	String roof2="28-29";
	String roof1="25-26-27";
	String roof0="22-23-24";
	
	String gables_top="20-21";
	String llf1="13-14-15-16-17-18-19";
	String llf0="06-07-08-09-10-11-12";
	String lineb2="05-04";
	String lineb1="02-03";
	String lineb0="00-01";
	
	String points_level2="12,13,14";
	String points_level1="6,7,8,9,10,11";
	String points_level0="0,1,2,3,4,5";
	

}
