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

public class Courtyard0Model extends MeshModel{

	double dx=100;
	double dx1=50;
	double dy=100;
	double dy1=50;
	double dz=20;
	double roof_height=50;

	int bx=10;
	int by=10;
	
	public static String NAME="Courtyard0";
	
	public Courtyard0Model(double dx, double dy, double dz,double roof_height,double dx1,double dy1) {
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
		
		//roof	
		double e=(dx-dx1)*0.5;
		double d=(dy-dy1)*0.5;
		

		//lower and upper base
		for(int k=0;k<2;k++){

			double z=dz*k;

			addPoint(0.0,0.0,z);
			addPoint(dx,0.0,z);
			addPoint(dx,dy,z);
			addPoint(0.0,dy,z);
			
			
			addPoint(e,d,z);
			addPoint(dx-e,d,z);
			addPoint(dx-e,dy-d,z);
			addPoint(e,dy-d,z);

		}
				
		addPoint(d,e,dz+roof_height);
		addPoint(dx-d,e,dz+roof_height);
		addPoint(dx-d,dy-e,dz+roof_height);
		addPoint(d,dy-e,dz+roof_height);

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
		addTPoint(x+dy,y+dx,0);*/
		
		

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

			//bottom
			{{Renderer3D.CAR_BACK},{0,4,5,1},{0,0,0,0}},
			{{Renderer3D.CAR_BACK},{0,3,7,4},{0,0,0,0}},
			{{Renderer3D.CAR_BACK},{3,2,6,7},{0,0,0,0}},
			{{Renderer3D.CAR_BACK},{6,2,1,5},{0,0,0,0}},
			
			//faces
			{{Renderer3D.CAR_BACK},{0,1,9,8},{0,0,0,0}},
			{{Renderer3D.CAR_RIGHT},{1,2,10,9},{0,0,0,0}},
			{{Renderer3D.CAR_FRONT},{2,3,11,10},{0,0,0,0}},
			{{Renderer3D.CAR_LEFT},{3,0,8,11},{0,0,0,0}},
			//inner faces
			{{Renderer3D.CAR_BACK},{5,4,12,13},{0,0,0,0}},
			{{Renderer3D.CAR_RIGHT},{4,12,15,7},{0,0,0,0}},
			{{Renderer3D.CAR_FRONT},{7,15,14,6},{0,0,0,0}},
			{{Renderer3D.CAR_LEFT},{6,5,13,14},{0,0,0,0}},
	
			
			//roof pitches
			{{Renderer3D.CAR_TOP},{8,9,17,16},{0,0,0,0}},
			{{Renderer3D.CAR_TOP},{16,17,13,12},{0,0,0,0}},
			{{Renderer3D.CAR_TOP},{9,10,18,17},{0,0,0,0}},
			{{Renderer3D.CAR_TOP},{17,18,16,13},{0,0,0,0}},
			{{Renderer3D.CAR_TOP},{10,11,19,18},{0,0,0,0}},
			{{Renderer3D.CAR_TOP},{18,19,15,14},{0,0,0,0}},
			{{Renderer3D.CAR_TOP},{11,8,16,19},{0,0,0,0}},
			{{Renderer3D.CAR_TOP},{19,16,12,15},{0,0,0,0}},
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
