package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

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
	
	public House3Model(double dx, double dy, double dz,double roof_height,
			double dx1,double dy1,double dy2,double dy3
			) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		this.roof_height = roof_height;
		this.dx1 = dx1;
		this.dy1 = dy1;
		this.dy2 = dy2;
		this.dy3 = dy3;
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

		addTPoint(x+dy3,y,0);
		addTPoint(x+dy3+dx,y,0);		
		addTPoint(x+dy3+dx,y+dy,0);
		addTPoint(x+dy3+dx+dy1,y+dy,0);
		addTPoint(x+dy3+dx+dy1,y+dy+dx1,0);
		addTPoint(x+dy3+dx,y+dy+dx1,0);		
		addTPoint(x+dy3+dx,y+dy+dx1+dy2,0);
		addTPoint(x+dy3,y+dy+dx1+dy2,0);
		addTPoint(x+dy3,y+dy+dx1,0);
		addTPoint(x,y+dy+dx1,0);		
		addTPoint(x,y+dy,0);
		addTPoint(x+dy3,y+dy,0);
		
		//faces
		y=by+dy+dx1+dy2;
		x=bx;
		
		for(int k=0;k<2;k++){

			double z=y+dz*k;
			
			addTPoint(x,z,0);
			addTPoint(x+dx,z,0);
			addTPoint(x+dx+dy,z,0);
			addTPoint(x+dx+dy+dy1,z,0);
			addTPoint(x+dx+dy+dy1+dx1,z,0);
			addTPoint(x+dx+dy+dy1+dx1+dy1,z,0);
			addTPoint(x+dx+dy+dy1+dx1+dy1+dy2,z,0);
			addTPoint(x+dx+dy+dy1+dx1+dy1+dy2+dx,z,0);
			addTPoint(x+dx+dy+dy1+dx1+dy1+dy2+dx+dy2,z,0);
			addTPoint(x+dx+dy+dy1+dx1+dy1+dy2+dx+dy2+dy3,z,0);
			addTPoint(x+dx+dy+dy1+dx1+dy1+dy2+dx+dy2+dy3+dx1,z,0);
			addTPoint(x+dx+dy+dy1+dx1+dy1+dy2+dx+dy2+dy3+dx1+dy3,z,0);
			addTPoint(x+dx+dy+dy1+dx1+dy1+dy2+dx+dy2+dy3+dx1+dy3+dy,z,0);
		}	
		
		//gable tops
		y=by+dy+dx1+dy2+dx+roof_height;
		x=bx;
		
		addTPoint(x+dx*0.5,y,0);
		addTPoint(x+dx+dy+dy1+dx1*0.5,y,0);
		addTPoint(x+dx+dy+dy1+dx1+dy1+dy2+dx*0.5,y,0);
		addTPoint(x+dx+dy+dy1+dx1+dy1+dy2+dx+dy2+dy3+dx1*0.5,y,0);
		
		//gable pitches
		
		addTPoint(x+dy3,y,0);
		addTPoint(x+dy3+dx*0.5,y,0);
		addTPoint(x+dy3+dx,y,0);
		addTPoint(x,y+dy,0);
		addTPoint(x+dy3,y+dy,0);
		addTPoint(x+dy3+dx,y+dy,0);
		addTPoint(x+dy3+dx+dy1,y+dy,0);
		
		addTPoint(x,y+dy+dx1*0.5,0);
		addTPoint(x+dy3+dx*0.5,y+dy+dx1*0.5,0);
		addTPoint(x+dy3+dx+dy1,y+dy+dx1*0.5,0);
		
		addTPoint(x,y+dy+dx1,0);
		addTPoint(x+dy3,y+dy+dx1,0);
		addTPoint(x+dy3+dx,y+dy+dx1,0);
		addTPoint(x+dy3+dx+dy1,y+dy+dx1,0);
		
		addTPoint(x+dy3,y+dy+dx1+dy2,0);
		addTPoint(x+dy3+dx*0.5,y+dy+dx1+dy2,0);
		addTPoint(x+dy3+dx,y+dy+dx1+dy2,0);

		IMG_WIDTH=(int) (2*bx+2*(dx+dx1+dy+dy1+dy2+dy3));
		IMG_HEIGHT=(int) (2*by+2*(dy+dx1+dy2)+dz+roof_height);
	}





	public void printTexture(Graphics2D bg) {


		//draw lines for reference

		bg.setColor(Color.RED);
		bg.setStroke(new BasicStroke(0.1f));

		//lower base
		printTextureLine(bg,0,1,2,3);
		printTextureLine(bg,3,4,5,6);
		printTextureLine(bg,6,7,8,9);
		printTextureLine(bg,9,10,11,0);
		
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
		printTextureLine(bg,54,58,57,50);
		printTextureLine(bg,57,56,53,50);
		printTextureLine(bg,53,52,49,50);
		printTextureLine(bg,49,45,46,50);
		printTextureLine(bg,46,42,43);

	}

	int[][][] faces={

			//base
			
			{{Renderer3D.CAR_BOTTOM},{11,2,1,0},{11,2,1,0}},
			{{Renderer3D.CAR_BOTTOM},{3,10,9,4},{3,10,9,4}},
			{{Renderer3D.CAR_BOTTOM},{5,8,7,6},{5,8,7,6}},

			//faces
			{{Renderer3D.CAR_BACK},{0,1,13,12},{12,13,26,25}},
			{{Renderer3D.CAR_RIGHT},{1,2,14,13},{13,14,27,26}},
			{{Renderer3D.CAR_BACK},{2,3,15,14},{14,15,28,27}},
			{{Renderer3D.CAR_RIGHT},{3,4,16,15},{15,16,29,28}},
			{{Renderer3D.CAR_FRONT},{4,5,17,16},{16,17,30,29}},
			{{Renderer3D.CAR_RIGHT},{5,6,18,17},{17,18,31,30}},
			{{Renderer3D.CAR_RIGHT},{6,7,19,18},{18,19,32,31}},
			{{Renderer3D.CAR_LEFT},{7,8,20,19},{19,20,33,32}},
			{{Renderer3D.CAR_FRONT},{8,9,21,20},{20,21,34,33}},
			{{Renderer3D.CAR_LEFT},{9,10,22,21},{21,22,35,34}},
			{{Renderer3D.CAR_BACK},{10,11,23,22},{22,23,36,35}},
			{{Renderer3D.CAR_LEFT},{11,0,12,23},{23,24,37,36}},
			
			//gables
			{{Renderer3D.CAR_BACK},{12,13,24},{25,26,38}},
			{{Renderer3D.CAR_RIGHT},{15,16,27},{28,29,39}},
			{{Renderer3D.CAR_FRONT},{18,19,28},{31,32,40}},
			{{Renderer3D.CAR_LEFT},{21,22,25},{34,35,41}},
			
			//roof pitches
			{{Renderer3D.CAR_TOP},{13,14,26,24},{44,47,50,43}},
			{{Renderer3D.CAR_TOP},{14,15,27,26},{47,48,51,50}},
			{{Renderer3D.CAR_TOP},{26,27,16,17},{50,51,55,54}},
			{{Renderer3D.CAR_TOP},{17,18,28,26},{54,58,57,50}},
			{{Renderer3D.CAR_TOP},{26,28,19,20},{50,57,56,53}},
			{{Renderer3D.CAR_TOP},{25,26,20,21},{49,50,53,52}},
			{{Renderer3D.CAR_TOP},{22,23,26,25},{45,46,50,49}},
			{{Renderer3D.CAR_TOP},{23,12,24,26},{46,42,43,50}},
	};
	
	String roof4="56-57-58";
	String roof3="52-53-54-55";
	String roof2="49-50-51";
	String roof1="45-46-47-48";
	String roof0="42-43-44";
	String gab="38-39-40";
	String fac1="25-26-27-28-29-30-31-32-33-34-35-36-37";
	String fac0="12-13-14-15-16-17-18-19-20-21-22-23-24";	
	String llb3="07-06";
	String llb2="09-08-05-04";
	String llb1="10-11-02-03";
	String llb0="00-01";
	
	
	String points_level4="28";
	String points_level3="25-26-27";
	String points_level2="24";
	String points_level1="12-13-14-15-16-17-18-19-20-21-22-23";
	String points_level0="00-01-02-03-04-05-06-07-08-09-10-11";
	

}
