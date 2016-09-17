package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.Point3D;

public class CubeModel extends MeshModel{

	private double dx=100;
	private double dy=200;
	private double dz=300;


	private int bx=10;
	private int by=10;

	public CubeModel(double dx, double dy, double dz) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}

	@Override
	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);

		print(pw,"f=[1]4/14 5/15 6/16 7/17");//TOP

		print(pw,"f=[0]0/4 1/5 5/10 4/9");//BACK
		print(pw,"f=[3]1/5 2/6 6/11 5/10");//RIGHT
		print(pw,"f=[4]2/6 3/7 7/12 6/11");//FRONT
		print(pw,"f=[2]3/7 0/8 4/13 7/12");///LEFT
		print(pw,"f=[5]0/0 3/1 2/2 1/3");//BOTTOM

	}


	@Override
    public void initMesh() {

    	buildBody();

    	buildTexture();

    }


	private void buildTexture() {

		texturePoints=new Vector<Point3D>();

    	//lower base
    	double y=by;
    	double x=bx;

     	addTPoint(x,y,0);
    	seqTPoint(dx,0);
    	seqTPoint(0,dy);
    	seqTPoint(-dx,0);

    	//faces
    	y=by+dy;

    	addTPoint(x,y,0);
    	seqTPoint(dx,0);
    	seqTPoint(dy,0);
    	seqTPoint(dx,0);
    	seqTPoint(dy,0);


    	addTPoint(x,y+dz,0);
    	seqTPoint(dx,0);
    	seqTPoint(dy,0);
    	seqTPoint(dx,0);
    	seqTPoint(dy,0);

    	y=by+dy+dz;

    	//upper base
      	addTPoint(x,y,0);
    	seqTPoint(dx,0);
    	seqTPoint(0,dy);
    	seqTPoint(-dx,0);


		IMG_WIDTH=(int) (2*dy+2*dx+2*bx);
		IMG_HEIGHT=(int) (dy*2+dz+2*by);

	}

	private void buildBody() {

    	points=new Vector<Point3D>();

    	//lower and upper base
    	for(int k=0;k<2;k++){

    		double z=dz*k;

    		addPoint(0.0,0.0,z);
    		addPoint(dx,0.0,z);
    		addPoint(dx,dy,z);
    		addPoint(0.0,dy,z);

    	}

	}

	@Override
	public void printTexture(Graphics2D bg) {


		//draw lines for reference

		bg.setColor(Color.BLUE);
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


    }

    String ub0="17-16";
    String ub1="14-15";
    String lf0="09-10-11-12-13";
    String lf1="04-05-06-07-08";
    String lb0="03-02";
    String lb1="00-01";
}
