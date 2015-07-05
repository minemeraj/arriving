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
		
		//roof
		addPoint(dy3+dx*0.5,0.0,dz+roof_height);
		addPoint(0,dy+dx1*0.5,dz+roof_height);
		addPoint(dy3+dx*0.5,dy+dx1*0.5,dz+roof_height);
		addPoint(dy3+dx+dy1,dy+dx1*0.5,dz+roof_height);
		addPoint(dy3+dx*0.5,dy+dx1+dy2,dz+roof_height);
		
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
		printTextureLine(bg,0,1,2,3);
		printTextureLine(bg,4,5,6,7);
		printTextureLine(bg,8,9,10,11);
		printTextureLine(bg,11,0);
		
		//lateral faces
		bg.setColor(Color.BLACK);
		printTexturePolygon(bg,12,13,26,25);
		printTextureLine(bg,13,14,27,26);
		printTextureLine(bg,14,15,28,27);
		printTextureLine(bg,15,16,29,28);
		printTextureLine(bg,16,17,30,29);
		printTextureLine(bg,17,18,31,30);
		printTextureLine(bg,18,19,32,31);
		printTextureLine(bg,19,20,33,32);
		printTextureLine(bg,20,21,34,33);
		printTextureLine(bg,21,22,35,34);
		printTextureLine(bg,22,23,36,35);
		printTextureLine(bg,23,24,37,36);
		printTextureLine(bg,24,25,38,37);
		
		 //gables
		bg.setColor(Color.BLUE);
		printTexturePolygon(bg,25,26,38);
		printTexturePolygon(bg,28,29,39);
		printTexturePolygon(bg,31,32,40);
		printTexturePolygon(bg,34,35,41);
		
		//roof pitches
		bg.setColor(Color.BLACK);
		printTexturePolygon(bg,43,44,47,50);
		printTextureLine(bg,47,48,51,50);
		printTextureLine(bg,51,55,54,50);
		printTextureLine(bg,54,58,57,60);
		printTextureLine(bg,57,56,53,60);
		printTextureLine(bg,53,52,49,50);
		printTextureLine(bg,49,45,46,50);
		printTextureLine(bg,46,42,43);

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
			
			//gables
			{{Renderer3D.CAR_BACK},{12,13,24},{0,0,0}},
			{{Renderer3D.CAR_BACK},{15,16,27},{0,0,0}},
			{{Renderer3D.CAR_BACK},{18,19,28},{0,0,0}},
			{{Renderer3D.CAR_BACK},{21,22,25},{0,0,0}},
			
			//roof pitches
			{{Renderer3D.CAR_BACK},{13,14,26,24},{0,0,0,0}},
			{{Renderer3D.CAR_BACK},{14,15,27,26},{0,0,0,0}},
			{{Renderer3D.CAR_BACK},{26,27,16,17},{0,0,0,0}},
			{{Renderer3D.CAR_BACK},{17,18,28,26},{0,0,0,0}},
			{{Renderer3D.CAR_BACK},{26,28,19,20},{0,0,0,0}},
			{{Renderer3D.CAR_BACK},{25,26,20,21},{0,0,0,0}},
			{{Renderer3D.CAR_BACK},{22,23,26,25},{0,0,0,0}},
			{{Renderer3D.CAR_BACK},{23,12,24,26},{0,0,0,0}},
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
