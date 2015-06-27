package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.editors.DoubleTextField;

public class House0Model extends MeshModel{

	double dx=100;
	double dy=200;
	double dz=20;

	int bx=10;
	int by=10;
	
	public House0Model(double dx, double dy, double dz) {
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

		//upper base
		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx,y+dy,0);
		addTPoint(x,y+dy,0);


		IMG_WIDTH=(int) (2*dy+2*dx+2*bx);
		IMG_HEIGHT=(int) (dy*2+dz+2*by);
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

		//upper base
		bg.setColor(Color.BLUE);
		printTextureLine(bg,14,15);
		printTextureLine(bg,15,16);
		printTextureLine(bg,16,17);
		printTextureLine(bg,17,14);


		

	}

	int[][][] faces={

			//base
			{{1},{4,5,6,7},{14,15,16,17}},
			{{0},{0,1,5,4},{4,5,10,9}},
			{{3},{1,2,6,5},{5,6,11,10}},
			{{4},{2,3,7,6},{6,7,12,11}},
			{{2},{3,0,4,7},{7,8,13,12}},
			{{5},{0,3,2,1},{0,1,2,3}},
			

	};
	



	String lub0="17-16";
	String lub1="14-15";
	String llf0="09-10-11-12-13";
	String llf1="04-05-06-07-08";
	String llb0="03-02";
	String llb1="00-01";
	
	
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
