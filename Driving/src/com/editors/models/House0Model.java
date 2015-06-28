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

public class House0Model extends MeshModel{

	double dx=100;
	double dy=200;
	double dz=20;
	double roof_height=50;

	int bx=10;
	int by=10;
	
	public House0Model(double dx, double dy, double dz,double roof_height) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		this.roof_height = roof_height;
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

		/*print("f=[1]4/14 5/15 6/16 7/17");//TOP

		print("f=[0]0/4 1/5 5/10 4/9");//BACK
		print("f=[3]1/5 2/6 6/11 5/10");//RIGHT
		print("f=[4]2/6 3/7 7/12 6/11");//FRONT
		print("f=[2]3/7 0/8 4/13 7/12");///LEFT

		print("f=[5]0/0 3/1 2/2 1/3");//BOTTOM

		//plane

		print("f=[1]12/32 13/33 14/34 15/35");//TOP

		print("f=[0]8/22 9/23 13/28 12/27");//BACK
		print("f=[3]9/23 10/24 14/29 13/28");//RIGHT
		print("f=[4]10/24 11/25 15/30 14/29");//FRONT
		print("f=[2]11/25 8/26 12/31 15/30");///LEFT

		print("f=[5]8/18 11/19 10/20 9/21");//BOTTOM*/

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
		addPoint(dx*0.5,0,dz+roof_height);
		addPoint(dx*0.5,dy,dz+roof_height);

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
		
		y=by+dy+dz;
		addTPoint(x+dx*0.5,y+roof_height,0);	
		addTPoint(x+dx+dy+dx*0.5,y+roof_height,0);		

		x=dx;
		y=by+dy+dz+roof_height;

		//roof pitches
		addTPoint(x,y,0);
		addTPoint(x+dy,y,0);
		addTPoint(x,y+dx*0.5,0);
		addTPoint(x+dy,y+dx*0.5,0);
		addTPoint(x,y+dx,0);
		addTPoint(x+dy,y+dx,0);
		
		

		IMG_WIDTH=(int) (2*dy+2*dx+2*bx);
		IMG_HEIGHT=(int) (dy+dz+2*by+roof_height+dx);
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
		printTextureLine(bg,9,10);
		printTextureLine(bg,10,14);
		printTextureLine(bg,14,9);
		
		bg.setColor(Color.BLUE);
		printTextureLine(bg,11,12);
		printTextureLine(bg,12,15);
		printTextureLine(bg,15,11);
		
		//roof pitches
		printTextureLine(bg,16,17);
		printTextureLine(bg,17,19);
		printTextureLine(bg,19,18);
		printTextureLine(bg,18,16);
		
		printTextureLine(bg,18,19);
		printTextureLine(bg,19,21);
		printTextureLine(bg,21,20);
		printTextureLine(bg,20,18);

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
	
	
	private void codeGeneration() {
		
		int c=0;
		int t=0;
		
		for (int i = 0; i < faces.length; i++) {

			int[][] face=faces[i];

			int[] fts=face[0];
			int[] pts=face[1];
			int[] tts=face[2];

			String line="{"+"{"+fts[0]+"}";
			
			line+=",{";

			for (int j = 0; j < pts.length; j++) {

				if(j>0)
					line+=",";
				line+=(c+pts[j]);
			}
			
			line+="}";
			
			line+=",{";
			
			for (int j = 0; j < pts.length; j++) {

				if(j>0)
					line+=",";
				line+=(t+tts[j]);
			}
			
			line+="}";

			line+="},";
			
			System.out.println(line);

		}
		
	}
}
