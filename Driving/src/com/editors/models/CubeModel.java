package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

public class CubeModel extends MeshModel{
	
	double dx=100;


	double dy=200;
	double dz=300;

	
	int bx=10;
	int by=10;

	public CubeModel(double dx, double dy, double dz) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}
	
	public void printMeshData(PrintWriter pw) {
		
		super.printMeshData(pw);
	

		
		
		print(pw,"f=[1]4/14 5/15 6/16 7/17");//TOP
		
		print(pw,"f=[0]0/4 1/5 5/10 4/9");//BACK
		print(pw,"f=[3]1/5 2/6 6/11 5/10");//RIGHT
		print(pw,"f=[4]2/6 3/7 7/12 6/11");//FRONT
		print(pw,"f=[2]3/7 0/8 4/13 7/12");///LEFT		
		print(pw,"f=[5]0/0 3/1 2/2 1/3");//BOTTOM
		
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
    
    String ub0="17-16";
    String ub1="14-15";
    String lf0="09-10-11-12-13";
    String lf1="04-05-06-07-08";
    String lb0="03-02";
    String lb1="00-01";
}
