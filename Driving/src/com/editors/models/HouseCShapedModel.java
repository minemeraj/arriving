package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.main.Renderer3D;
/**
 * CI SHAPED HOUSE
 * 
 * @author Administrator
 *
 */
public class HouseCShapedModel extends MeshModel{

	private double dx=100;
	private double dx1=100;
	private double dx2=100;
	private double dy=200;
	private double dy1=200;
	private double dz=20;
	private double roof_height=50;

	private int bx=10;
	private int by=10;
	
	public static String NAME="C Shaped";
	
	public HouseCShapedModel(double dx, double dy, double dz,double roof_height,double dx1,double dy1,double dx2) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		this.roof_height = roof_height;
		this.dx1 = dx1;
		this.dy1 = dy1;
		this.dx2 = dx2;
	}


	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);

		
	}


	@Override
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
		
		addPoint(0,dy,dz);
		addPoint(dx+dx1+dx,dy,dz);
		
		double dd=(dy1-dy)*0.5;
		
		addPoint(dx*0.5,dy+dd,dz+roof_height);
		addPoint(dx+dx1+dx*0.5,dy+dd,dz+roof_height);

		
		texturePoints=new Vector();

	
		///////main plane

		//lower base
		double y=by;
		double x=bx;

		addTPoint(x,y,0);
		seqTPoint(dx,0);
		seqTPoint(0,dy);
		seqTPoint(dx1,0);
		seqTPoint(0,-dy);
		seqTPoint(dx2,0);
		seqTPoint(0,dy1);
		seqTPoint(-dx-dx1-dx2,0);
		
		//faces
		y=by+dy1;
		for(int k=0;k<2;k++){

			double z=y+dz*k;
			addTPoint(x,z,0);
			seqTPoint(dx,0);
			seqTPoint(dy,0);
			seqTPoint(dx1,0);
			seqTPoint(dy,0);
			seqTPoint(dx2,0);
			seqTPoint(dy1,0);
			seqTPoint(dx+dx1+dx2,0);
			seqTPoint(dy1,0);
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

	private int[][][] faces={

			//base
			{{Renderer3D.CAR_BOTTOM},{0,7,2,1},{0,7,2,1}},
			{{Renderer3D.CAR_BOTTOM},{2,7,6,3},{2,7,6,3}},
			{{Renderer3D.CAR_BOTTOM},{4,3,6,5},{4,3,6,5}},
			
			//faces
			{{Renderer3D.CAR_BACK},{0,1,9,8},{8,9,18,17}},
			{{Renderer3D.CAR_RIGHT},{1,2,10,9},{9,10,19,18}},
			{{Renderer3D.CAR_BACK},{2,3,11,10},{10,11,20,19}},
			{{Renderer3D.CAR_LEFT},{3,4,12,11},{11,12,21,20}},
			{{Renderer3D.CAR_BACK},{4,5,13,12},{12,13,22,21}},
			{{Renderer3D.CAR_RIGHT},{5,6,14,13},{13,14,23,22}},
			{{Renderer3D.CAR_FRONT},{6,7,15,14},{14,15,24,23}},
			{{Renderer3D.CAR_LEFT},{7,0,8,15},{15,16,25,24}},
				
			//gables
			{{Renderer3D.CAR_BACK},{8,9,16},{17,18,26}},
			{{Renderer3D.CAR_BACK},{12,13,17},{21,22,27}},
			
			//roof pitches
			{{Renderer3D.CAR_TOP},{9,10,20,16},{29,30,35,38}},
			{{Renderer3D.CAR_TOP},{10,11,21,20},{35,36,39,38}},
			{{Renderer3D.CAR_TOP},{11,12,17,21},{36,31,32,39}},
			{{Renderer3D.CAR_TOP},{17,13,19,21},{32,33,37,39}},
			{{Renderer3D.CAR_TOP},{19,14,21},{37,41,39}},
			{{Renderer3D.CAR_TOP},{20,21,14,15},{38,39,41,40}},
			{{Renderer3D.CAR_TOP},{15,18,20},{40,34,38}},
			{{Renderer3D.CAR_TOP},{18,8,16,20},{34,28,29,38}},

	};
	
	String roo3="40-41";
	String roo2="38-39";
	String roo1="34-35-36-37";
	String roo0="28-29-30--31-32-33";
	String gab0="26-27";
	String fac1="17-18-19-20-21-22-23-24-25";
	String fac0="08-09-10-11-12-13-14-15-16";
	String llb0="00-01-02-03-04-05-06-07";
	
	String points_level4="20-21";
	String points_level3="18-19";
	String points_level2="16-17";
	String points_level1="08-09-10-11-12-13-14-15";
	String points_level0="00-01-02-03-04-05-06-07";
	

}
