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

public class House3Model extends MeshModel{

	double dx=100;
	double dx1=100;
	double dy=200;
	double dy1=200;
	double dy2=200;
	double dy3=200;
	double dz=20;
	double roof_height=50;

	int bx=10;
	int by=10;
	
	public static String NAME="Gable3";
	
	public House3Model(double dx, double dy, double dz,double roof_height,double dy1) {
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

			addPoint(dy3,0.0,z);
			addPoint(dy3+dx,0.0,z);
			addPoint(dy3+dx,dy,z);
			addPoint(dy3+dx+dy1,dy,z);
			addPoint(dy3+dx+dy1,dy+dx1,z);
			addPoint(dy3+dx,dy+dx1,z);
			addPoint(dy3+dx,dy+dx1+dy2,z);
			addPoint(dy3,dy+dx1+dy2,z);
			addPoint(dy3,dy+dx1,z);
			addPoint(0,dy+dx1,z);
			addPoint(0,dy,z);
			addPoint(dy3,dy,z);
		}
	

		texturePoints=new Vector();

	
		///////main plane

		//lower base
		double y=by;
		double x=bx;

		addTPoint(x,y,0);
		/*addTPoint(x+dx,y,0);
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
	
		//gable tops
		y=by+dy+dz;
		addTPoint(x+dx*0.5,y+roof_height,0);	
		addTPoint(x+dx+dy+dx*0.5,y+roof_height,0);		

		x=dx;
		y=by+dy+dz+roof_height;

		//roof pitches
		addTPoint(x,y,0);
		addTPoint(x+dy,y,0);
		addTPoint(x+d,y+dx*0.5,0);
		addTPoint(x+dy-d,y+dx*0.5,0);
		addTPoint(x,y+dx,0);
		addTPoint(x+dy,y+dx,0);
		*/
		

		IMG_WIDTH=(int) (2*bx+2*dy+2*dx);
		IMG_HEIGHT=(int) (2*by+dy+dz+roof_height+dx);
	}





	public void printTexture(Graphics2D bg) {


		//draw lines for reference

		bg.setColor(Color.RED);
		bg.setStroke(new BasicStroke(0.1f));

		//lower base
		/*printTextureLine(bg,0,1);
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
		printTextureLine(bg,20,18);*/

	}

	int[][][] faces={

			//base
			
			{{Renderer3D.CAR_BOTTOM},{0,1,2,11},{0,0,0,0}},
			{{Renderer3D.CAR_BOTTOM},{10,3,4,9},{0,0,0,0}},
			{{Renderer3D.CAR_BOTTOM},{8,5,6,7},{0,0,0,0}},

			//faces
			{{Renderer3D.CAR_BACK},{0,1,13,12},{0,0,0,0}},
			{{Renderer3D.CAR_RIGHT},{1,2,14,13},{0,0,0,0}},
			{{Renderer3D.CAR_BACK},{2,3,15,14},{0,0,0,0}},
			{{Renderer3D.CAR_BACK},{3,4,16,15},{0,0,0,0}},
			{{Renderer3D.CAR_BACK},{4,5,17,16},{0,0,0,0}},
			{{Renderer3D.CAR_BACK},{5,6,18,17},{0,0,0,0}},
			{{Renderer3D.CAR_BACK},{6,7,19,18},{0,0,0,0}},
			{{Renderer3D.CAR_BACK},{7,8,20,19},{0,0,0,0}},
			{{Renderer3D.CAR_BACK},{8,9,21,20},{0,0,0,0}},
			{{Renderer3D.CAR_BACK},{9,10,22,21},{0,0,0,0}},
			{{Renderer3D.CAR_BACK},{10,11,23,22},{0,0,0,0}},
			{{Renderer3D.CAR_BACK},{11,0,12,23},{0,0,0,0}},
			/*{{Renderer3D.CAR_BACK},{0,1,5,4},{4,5,10,9}},
			{{Renderer3D.CAR_RIGHT},{1,2,6,5},{5,6,11,10}},
			{{Renderer3D.CAR_FRONT},{2,3,7,6},{6,7,12,11}},
			{{Renderer3D.CAR_LEFT},{3,0,4,7},{7,8,13,12}},
			{{Renderer3D.CAR_BOTTOM},{0,3,2,1},{0,1,2,3}},
			

			//gables
			{{Renderer3D.CAR_BACK},{4,5,8},{9,10,14}},
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
