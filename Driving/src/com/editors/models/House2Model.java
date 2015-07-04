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

public class House2Model extends MeshModel{

	double dx=100;
	double dx1=100;
	double dy=200;
	double dy1=200;
	double dy2=200;
	double dz=20;
	double roof_height=50;

	int bx=10;
	int by=10;
	
	public static String NAME="Gable2";
	
	public House2Model(double dx, double dy, double dz,double roof_height,double dy1) {
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

			addPoint(dy2,0.0,z);
			addPoint(dy2+dx,0.0,z);
			addPoint(dy2+dx,dy,z);
			addPoint(dy2+dx+dy1,dy,z);
			addPoint(dy2+dx+dy1,dy+dx1,z);
			addPoint(0,dy+dx1,z);
			addPoint(0,dy,z);
			addPoint(dy2,dy,z);
		}
		
		//roof			
		
		addPoint(dy2+dx*0.5,0,dz+roof_height);		
		addPoint(0,dy+dx1*0.5,dz+roof_height);
		addPoint(dy2+dx*0.5,dy+dx1*0.5,dz+roof_height);
		addPoint(dy2+dx+dy1,dy+dx1*0.5,dz+roof_height);
		
		texturePoints=new Vector();

	
		///////main plane

		//lower base
		double y=by;
		double x=bx;

		addTPoint(x+dy2,y,0);
		addTPoint(x+dy2+dx,y,0);
		addTPoint(x+dy2+dx,y+dy,0);
		addTPoint(x+dy2+dx+dy1,y+dy,0);
		addTPoint(x+dy2+dx+dy1,y+dy+dx1,0);
		addTPoint(x,y+dy+dx1,0);
		addTPoint(x,y+dy,0);
		addTPoint(x+dy2,y+dy,0);

		//faces
		y=by+dy+dx1;

		for(int k=0;k<2;k++){
		
			double z=y+dz*k;
			
			addTPoint(x,z,0);
			addTPoint(x+dx,z,0);
			addTPoint(x+dx+dy,z,0);
			addTPoint(x+dx+dy+dy1,z,0);
			addTPoint(x+dx+dy+dy1+dx1,z,0);
			addTPoint(x+dx+dy+dy1+dx1+(dx+dy1+dy2),z,0);
			addTPoint(x+dx+dy+dy1+dx1+(dx+dy1+dy2)+dx1,z,0);
			addTPoint(x+dx+dy+dy1+dx1+(dx+dy1+dy2)+dx1+dy2,z,0);
			addTPoint(x+dx+dy+dy1+dx1+(dx+dy1+dy2)+dx1+dy2+dy,z,0);
		}
	
		//gable tops
		y=by+dy+dx1+dz;
		addTPoint(x+dx*0.5,y+roof_height,0);	
		addTPoint(x+dx+dy+dy1+dx*0.5,y+roof_height,0);		
		addTPoint(x+dx+dy+dy1+dx+(dx+dy1+dy2)+dx1*0.5,y+roof_height,0);

		x=bx;
		y=by+dy+dx1+dz+roof_height;

		//roof pitches
		addTPoint(x+dy2,y,0);
		addTPoint(x+dy2+dx*0.5,y,0);
		addTPoint(x+dy2+dx,y,0);
		
		addTPoint(x,y+dy,0);
		addTPoint(x+dy2,y+dy,0);
		addTPoint(x+dy2+dx,y+dy,0);
		addTPoint(x+dy2+dx+dy1,y+dy,0);
		
		addTPoint(x,y+dy+dx1*0.5,0);
		addTPoint(x+dy2+dx*0.5,y+dy+dx1*0.5,0);
		addTPoint(x+dy2+dx+dy1,y+dy+dx1*0.5,0);
		
		addTPoint(x,y+dy+dx1,0);
		addTPoint(x+dy2+dx+dy1,y+dy+dx1,0);
	
		
		addTPoint(x+dy2,y+dy,0);
		

		IMG_WIDTH=(int) (2*bx+2*(dx+dx1+dy+dy1+dy2));
		IMG_HEIGHT=(int) (2*by+2*(dx1+dy)+dz+roof_height);
	}





	public void printTexture(Graphics2D bg) {


		//draw lines for reference

		bg.setColor(Color.RED);
		bg.setStroke(new BasicStroke(0.1f));

		//lower base
		printTextureLine(bg,0,1,2,3);
		printTextureLine(bg,3,4,5,6);
		printTextureLine(bg,6,7,0);

		//lateral faces
		bg.setColor(Color.BLACK);
		printTexturePolygon(bg,8,9,18,17);
		
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
		printTexturePolygon(bg,20,21,27);
		printTexturePolygon(bg,22,23,28);

		
		//roof pitches
		printTexturePolygon(bg,29,30,37,33);
		printTexturePolygon(bg,30,31,34,37);
		printTexturePolygon(bg,34,35,38,37);
		printTexturePolygon(bg,36,38,40,39);
		printTexturePolygon(bg,32,33,37,36);

	}

	int[][][] faces={

			//base

			{{Renderer3D.CAR_BOTTOM},{7,2,1,0},{7,2,1,0}},
			{{Renderer3D.CAR_BOTTOM},{6,5,4,3,2,7},{6,5,4,3,2,7}},
			
			//faces
			{{Renderer3D.CAR_BACK},{0,1,9,8},{0,0,0,0}},
			{{Renderer3D.CAR_RIGHT},{1,2,10,9},{0,0,0,0}},
			{{Renderer3D.CAR_BACK},{2,3,11,10},{0,0,0,0}},
			{{Renderer3D.CAR_RIGHT},{3,4,12,11},{0,0,0,0}},
			{{Renderer3D.CAR_FRONT},{4,5,13,12},{0,0,0,0}},
			{{Renderer3D.CAR_LEFT},{5,6,14,13},{0,0,0,0}},
			{{Renderer3D.CAR_BACK},{6,7,15,14},{0,0,0,0}},
			{{Renderer3D.CAR_LEFT},{7,0,8,15},{0,0,0,0}},
			
			//gables
			{{Renderer3D.CAR_BACK},{8,9,16},{0,0,0}},
			{{Renderer3D.CAR_RIGHT},{11,12,19},{0,0,0}},
			{{Renderer3D.CAR_LEFT},{13,14,17},{0,0,0}},
			
					
			//roof pitches
			{{Renderer3D.CAR_TOP},{15,8,16,18},{0,0,0,0}},
			{{Renderer3D.CAR_TOP},{9,10,18,16},{0,0,0,0}},
			{{Renderer3D.CAR_TOP},{10,11,19,18},{0,0,0,0}},
			{{Renderer3D.CAR_TOP},{12,13,17,19},{0,0,0,0}},
			{{Renderer3D.CAR_TOP},{14,15,18,17},{0,0,0,0}},

		
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
