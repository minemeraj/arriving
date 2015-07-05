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

public class House4Model extends MeshModel{

	double dx=100;
	double dx1=100;
	double dx2=100;
	double dy=200;
	double dy1=200;
	double dz=20;
	double roof_height=50;

	int bx=10;
	int by=10;
	
	public static String NAME="Gable4";
	
	public House4Model(double dx, double dy, double dz,double roof_height,double dy1) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		this.roof_height = roof_height;
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
			addPoint(dx+dx1,dy,z);
			addPoint(dx+dx1,0,z);
			addPoint(dx+dx1+dx,0,z);
			addPoint(dx+dx1+dx,dy1,z);
			addPoint(0,dy1,z);
		}
		
		//roof			
		addPoint(dx*0.5,0.0,dz+roof_height);
		addPoint(dx+dx1+dx*0.5,0.0,dz+roof_height);
		addPoint(0,dy,dz+roof_height);
		
		addPoint(dx,dy,dz+roof_height);
		addPoint(dx+dx1,dy,dz+roof_height);
		addPoint(dx+dx1+dx,dy,dz+roof_height);
		
		addPoint(dx*0.5,dy,dz+roof_height);
		addPoint(dx+dx1+dx*0.5,dy,dz+roof_height);
		
		addPoint(0,dy1,dz+roof_height);
		addPoint(dx+dx1+dx,dy1,dz+roof_height);
		
		texturePoints=new Vector();

	
		///////main plane

		//lower base
		double y=by;
		double x=bx;

		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx,y+dy,0);
		addTPoint(x+dx+dx1,y+dy,0);
		addTPoint(x+dx+dx1,y,0);
		addTPoint(x+dx+dx1+dx,y,0);
		addTPoint(x+dx+dx1+dx,y+dy1,0);
		addTPoint(x,y+dy1,0);
		
		//faces
		y=by+dy1;
		for(int k=0;k<2;k++){

			double z=y+dz*k;
			addTPoint(x,z,0);
			addTPoint(x+dx,z,0);
			addTPoint(x+dx+dy,z,0);
			addTPoint(x+dx+dy+dx1,z,0);
			addTPoint(x+dx+dy+dx1+dy,z,0);
			addTPoint(x+dx+dy+dx1+dy+dx2,z,0);
			addTPoint(x+dx+dy+dx1+dy+dx2+dy1,z,0);
			addTPoint(x+dx+dy+dx1+dy+dx2+dy1+(dx+dx1+dx2),z,0);
			addTPoint(x+dx+dy+dx1+dy+dx2+dy1+(dx+dx1+dx2)+dy1,z,0);
		}	
		
		//gables tops
		y=by+dy1+dz+roof_height;
		addTPoint(x+dx*0.5,y,0);
		addTPoint(x+dx+dy+dx1+dy+dx2*0.5,y,0);
		
		//roof pitches
		y=by+dy1+dz+roof_height;
		addTPoint(x,y,0);
		addTPoint(x+dx*0.5,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx+dx1,y,0);
		addTPoint(x+dx+dx1+dx2*0.5,y,0);
		addTPoint(x+dx+dx1+dx2,y,0);
		
		y=by+dy1+dz+roof_height+dy;
		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx+dx1,y,0);
		addTPoint(x+dx+dx1+dx2,y,0);
		
		double dd=(dy1-dy)*0.5;
		y=by+dy1+dz+roof_height+dy+dd;

		addTPoint(x+dx*0.5,y,0);
		addTPoint(x+dx+dx1+dx2*0.5,y,0);

		y=by+dy1+dz+roof_height+dy1;
		addTPoint(x,y,0);
		addTPoint(x+dx+dx1+dx2,y,0);

		IMG_WIDTH=(int) (2*bx+2*(dx+dx1+dx2+dy+dy1));
		IMG_HEIGHT=(int) (2*by+2*dy1+dz+roof_height);
	}





	public void printTexture(Graphics2D bg) {


		//draw lines for reference

		bg.setColor(Color.RED);
		bg.setStroke(new BasicStroke(0.1f));

		//lower base
		printTexturePolygon(bg,0,7,2,1);
		printTexturePolygon(bg,2,7,6,3);
		printTexturePolygon(bg,4,3,6,5);

		//lateral faces
		bg.setColor(Color.BLACK);
		printTexturePolygon(bg,7,9,18,17);
		printTextureLine(bg,9,10,19,18);
		printTextureLine(bg,10,11,20,19);
		printTextureLine(bg,11,12,21,20);
		printTextureLine(bg,12,13,22,21);
		printTextureLine(bg,13,14,23,22);
		printTextureLine(bg,14,15,24,23);
		printTextureLine(bg,15,16,25,24);
		//gables
		bg.setColor(Color.BLUE);
		printTexturePolygon(bg,17,18,26);
		printTexturePolygon(bg,21,22,27);
		
		//roof pitches
		bg.setColor(Color.BLACK);
		printTexturePolygon(bg,28,29,38,34);
		printTextureLine(bg,29,30,35,38);
		printTextureLine(bg,35,36,39,38);
		printTextureLine(bg,36,31,32,39);
		printTextureLine(bg,32,33,37,39);
		printTextureLine(bg,37,41,39);
		printTextureLine(bg,38,34,28,29);
		printTextureLine(bg,38,40,34);
		printTextureLine(bg,40,41);
	}

	int[][][] faces={

			//base
			{{Renderer3D.CAR_BOTTOM},{0,7,2,1},{0,7,2,1}},
			{{Renderer3D.CAR_BOTTOM},{2,7,6,3},{2,7,6,3}},
			{{Renderer3D.CAR_BOTTOM},{4,3,6,5},{4,3,6,5}},

			//gables
			/*{{Renderer3D.CAR_BACK},{4,5,8},{9,10,14}},
			{{Renderer3D.CAR_FRONT},{6,7,9},{11,12,15}},
			
			//roof pitches
			{{Renderer3D.CAR_TOP},{5,6,9,8},{16,17,19,18}},
			{{Renderer3D.CAR_TOP},{8,9,7,4},{18,19,21,20}},*/
	};
	
	String roo2="20-21";
	String roo1="18-19";
	String roo0="16-17";	
	String gables_top="14-15";
	String llf1="09-10-11-12-13";
	String llf0="04-05-06-07-08";
	String llb1="03-02";
	String llb0="00-01";
	
	String points_level2="8,9";
	String points_level1="4,5,6,7";
	String points_level0="0,1,2,3";
	

}
