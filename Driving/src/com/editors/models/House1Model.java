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

public class House1Model extends MeshModel{

	double dx=100;
	double dy=200;
	double dz=20;
	double roof_height=50;

	int bx=10;
	int by=10;
	
	public House1Model(double dx, double dy, double dz,double roof_height) {
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
		double dy2=dy;

		//lower and upper base
		for(int k=0;k<2;k++){

			double z=dz*k;

			addPoint(0.0,0.0,z);
			addPoint(dx,0.0,z);
			addPoint(dx,dy,z);
			addPoint(dx+dy2,dy,z);
			addPoint(dx+dy2,dy+dx,z);
			addPoint(0,dy+dx,z);
		}
		
		//roof		
		addPoint(dx*0.5,0,dz+roof_height);
		addPoint(dx*0.5,dy+dx*0.5,dz+roof_height);
		addPoint(dx+dy2,dy+dx*0.5,dz+roof_height);
		
		texturePoints=new Vector();

	
		///////main plane

		//lower base
		double y=by;
		double x=bx;
		
		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx,y+dy,0);
		addTPoint(x+dx+dy2,y+dy,0);
		addTPoint(x+dx+dy2,y+dy+dx,0);
		addTPoint(x,y+dy+dx,0);

	
		//faces
		y=by+dy+dx;

		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx+dy,y,0);
		addTPoint(x+dx+dy+dy2,y,0);
		addTPoint(x+dx+dy+dy2+dx,y,0);    	
		addTPoint(x+dx+dy+dy2+dx+(dx+dy2),y,0);
		addTPoint(x+dx+dy+dy2+dx+(dx+dy2)+(dx+dy),y,0);
		
		y=by+dy+dx+dz;

		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx+dy,y,0);
		addTPoint(x+dx+dy+dy2,y,0);
		addTPoint(x+dx+dy+dy2+dx,y,0);    	
		addTPoint(x+dx+dy+dy2+dx+(dx+dy2),y,0);
		addTPoint(x+dx+dy+dy2+dx+(dx+dy2)+(dx+dy),y,0);
		
		//gables tops
		y=by+dy+dx+dz+roof_height;
		
		addTPoint(x+dx*0.5,y,0);
		addTPoint(x+dx+dy+dy2+dx*0.5,y,0); 
		
		y=by+dy+dx+dz+roof_height;

		//roof
		addTPoint(x,y,0);
		addTPoint(x+dx*0.5,y,0);
		addTPoint(x+dx,y,0);
		
		addTPoint(x+dx,y+dy,0);
		addTPoint(x+dx+dy2,y+dy,0);
		
		addTPoint(x+dx*0.5,y+dy+dx*0.5,0);
		addTPoint(x+dx+dy2,y+dy+dx*0.5,0);
		
		addTPoint(x,y+dy+dx,0);
		addTPoint(x+dx+dy2,y+dy+dx,0);

		IMG_WIDTH=(int) (2*bx+dx+dy+dy2+dx+(dx+dy2)+(dx+dy));
		IMG_HEIGHT=(int) (2*by+(dx+dy)+dz+roof_height+(dx+dy));
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
		printTextureLine(bg,22,23);
		printTextureLine(bg,23,27);
		printTextureLine(bg,27,29);
		printTextureLine(bg,29,22);
		
		printTextureLine(bg,23,24);
		printTextureLine(bg,24,25);
		printTextureLine(bg,25,27);
		
		printTextureLine(bg,25,26);
		printTextureLine(bg,26,28);
		printTextureLine(bg,28,27);
		
		printTextureLine(bg,27,28);
		printTextureLine(bg,28,30);
		printTextureLine(bg,30,29);
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
			{{Renderer3D.CAR_RIGHT},{9,10,14},{16,17,21}},
			
			
			//roof pitches
			{{Renderer3D.CAR_TOP},{12,7,8,13},{23,24,25,27}},
			{{Renderer3D.CAR_TOP},{8,9,14,13},{25,26,28,27}},
			{{Renderer3D.CAR_TOP},{13,14,10,11},{27,28,30,29}},
			{{Renderer3D.CAR_TOP},{12,13,11,6},{23,27,29,22}},
	};
	
	String roof3="29-30";
	String roof2="27-28";
	String roof1="25-26";
	String roof0="22-23-24";
	
	String gables_top="20-21";
	String llf1="13-14-15-16-17-18-19";
	String llf0="06-07-08-09-10-11-12";
	String lineb2="05-04";
	String lineb1="02-03";
	String lineb0="00-01";
	
	String points_level2="12,13,14";
	String points_level1="6,7,8,9,10,11";
	String points_level0="0,1,2,4,5";
	
	
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
