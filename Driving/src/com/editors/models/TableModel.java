package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

public class TableModel extends MeshModel{

	private double dx=100;


	private double dy=200;
	private double dz=20;
	private double leg_length=50;
	private double leg_side=10;

	private int bx=10;
	private int by=10;
	
	public TableModel(double dx, double dy, double dz) {
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


	}


	@Override
	public void initMesh() {		
		
		buildBody();		
		
		buildTexture();
		
	}

	private void buildTexture() {
		
		texturePoints=new Vector();

		//lower base
		double y=by;
		double x=bx;

		addTPoint(x,y,0);
		addTPoint(x+leg_side,y,0);
		addTPoint(x+leg_side, y+leg_side,0);
		addTPoint(x,y+leg_side,0);

		//faces
		y=by+leg_side;

		addTPoint(x,y,0);
		addTPoint(x+leg_side,y,0);
		addTPoint(x+leg_side+leg_side,y,0);
		addTPoint(x+leg_side+2*leg_side,y,0);
		addTPoint(x+2*leg_side+2*leg_side,y,0);    	


		addTPoint(x,y+leg_length,0);
		addTPoint(x+leg_side,y+leg_length,0);
		addTPoint(x+leg_side+leg_side,y+leg_length,0);
		addTPoint(x+leg_side+2*leg_side,y+leg_length,0);
		addTPoint(x+2*leg_side+2*leg_side,y+leg_length,0);

		y=by+leg_side+leg_length;

		//upper base
		addTPoint(x,y,0);
		addTPoint(x+leg_side,y,0);
		addTPoint(x+leg_side,y+leg_side,0);
		addTPoint(x,y+leg_side,0);

		///////main plane

		//lower base
		y=by+leg_length+2*leg_side;
		x=bx;

		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx, y+dy,0);
		addTPoint(x,y+dy,0);

		//faces
		y=by+dy+leg_length+2*leg_side;

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

		y=by+dy+dz+leg_length+2*leg_side;

		//upper base
		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx,y+dy,0);
		addTPoint(x,y+dy,0);


		IMG_WIDTH=(int) (2*dy+2*dx+2*bx);
		IMG_HEIGHT=(int) (dy*2+dz+2*by+leg_length+2*leg_side);

	}


	private void buildBody() {
		
		points=new Vector();
		
		//legs
		
		for(int l=0;l<4;l++){
			for(int k=0;k<2;k++){
	
				double z=leg_length*k;
				double x=0;
				double y=0;
				
				if(l==1){
					
					x=dx-leg_side;
					
				}else if(l==2){
					
					y=dy-leg_side;
					
				}else if(l==3){
					
					y=dy-leg_side;
					x=dx-leg_side;
				}
	
				addPoint(x,y,z);
				addPoint(x+leg_side,y,z);
				addPoint(x+leg_side,y+leg_side,z);
				addPoint(x,y+leg_side,z);
	
			}
		}
		//lower and upper base
		for(int k=0;k<2;k++){

			double z=leg_length+dz*k;

			addPoint(0.0,0.0,z);
			addPoint(dx,0.0,z);
			addPoint(dx,dy,z);
			addPoint(0.0,dy,z);

		}

		
	}


	public void printTexture(Graphics2D bg) {


		//draw lines for reference

		bg.setColor(Color.RED);
		bg.setStroke(new BasicStroke(0.1f));

		//lower base
		printTexturePolygon(bg,0,1,2,3);

		//lateral faces
		bg.setColor(Color.BLACK);
		printTexturePolygon(bg,4,5,10,9);

		printTextureLine(bg,5,6,11,10);

		printTextureLine(bg,6,7,12,11);

		printTextureLine(bg,7,8,13,12);


		//upper base
		bg.setColor(Color.BLUE);
		printTexturePolygon(bg,14,15,16,17);

		int c=18;

		//lower base
		bg.setColor(Color.RED);
		printTexturePolygon(bg,c+0,c+1,c+2,c+3);

		//lateral faces
		bg.setColor(Color.BLACK);
		printTexturePolygon(bg,c+4,c+5,c+10,c+9);

		printTextureLine(bg,c+5,c+6,c+11,c+10);
		printTextureLine(bg,c+6,c+7,c+12,c+11);
		printTextureLine(bg,c+7,c+8,c+13,c+12);

		//upper base
		bg.setColor(Color.BLUE);
		printTexturePolygon(bg,c+14,c+15,c+16,c+17);

	}

	private int[][][] faces={

			//leg
			{{1},{4,5,6,7},{14,15,16,17}},
			{{0},{0,1,5,4},{4,5,10,9}},
			{{3},{1,2,6,5},{5,6,11,10}},
			{{4},{2,3,7,6},{6,7,12,11}},
			{{2},{3,0,4,7},{7,8,13,12}},
			{{5},{0,3,2,1},{0,1,2,3}},
			
			{{1},{12,13,14,15},{14,15,16,17}},
			{{0},{8,9,13,12},{4,5,10,9}},
			{{3},{9,10,14,13},{5,6,11,10}},
			{{4},{10,11,15,14},{6,7,12,11}},
			{{2},{11,8,12,15},{7,8,13,12}},
			{{5},{8,11,10,9},{0,1,2,3}},
			
			{{1},{20,21,22,23},{14,15,16,17}},
			{{0},{16,17,21,20},{4,5,10,9}},
			{{3},{17,18,22,21},{5,6,11,10}},
			{{4},{18,19,23,22},{6,7,12,11}},
			{{2},{19,16,20,23},{7,8,13,12}},
			{{5},{16,19,18,17},{0,1,2,3}},
			
			{{1},{28,29,30,31},{14,15,16,17}},
			{{0},{24,25,29,28},{4,5,10,9}},
			{{3},{25,26,30,29},{5,6,11,10}},
			{{4},{26,27,31,30},{6,7,12,11}},
			{{2},{27,24,28,31},{7,8,13,12}},
			{{5},{24,27,26,25},{0,1,2,3}},

			//body
			{{1},{36,37,38,39},{32,33,34,35}},
			{{0},{32,33,37,36},{22,23,28,27}},
			{{3},{33,34,38,37},{23,24,29,28}},
			{{4},{34,35,39,38},{24,25,30,29}},
			{{2},{35,32,36,39},{25,26,31,30}},
			{{5},{32,35,34,33},{18,19,20,21}},

	};
	



	String ub0="34-35";
	String ub1="32-33";
	String lf0="27-28-39-30-31";
	String lf1="22-23-24-25-26";
	String lb0="21-20";
	String lb1="18-19";

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
