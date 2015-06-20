package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.Vector;

import javax.imageio.ImageIO;

import com.Point3D;

public class CubeModel extends MeshModel{
	
	double dx=100;
	double dy=200;
	double dz=300;
	
	int bx=10;
	int by=10;
	
	public CubeModel(){
		
		super();

		
	}

	
	 public static void main(String[] args) {
		
		 CubeModel cm=new CubeModel();
			 
	}
	 
	 
	
	public void printMeshData() {
		
		super.printMeshData();
	

		
		
		print("f=[1]4/14 5/15 6/16 7/17");//TOP
		
		print("f=[0]0/4 1/5 5/10 4/9");//BACK
		print("f=[3]1/5 2/6 6/11 5/10");//RIGHT
		print("f=[4]2/6 3/7 7/12 6/11");//FRONT
		print("f=[2]3/7 0/8 4/13 7/12");///LEFT
		
		print("f=[5]0/0 3/1 2/2 1/3");//BOTTOM
		
	}



    public void initMesh() {
    	
    	
    	points=new Vector();
    	
    	
    	points.add(new Point3D(0.0,0.0,0.0));
		points.add(new Point3D(dx,0.0,0.0));
		points.add(new Point3D(dx,dy,0.0));
		points.add(new Point3D(0.0,dy,0.0));
		points.add(new Point3D(0.0,0.0,dz));
		points.add(new Point3D(dx,0.0,dz));
		points.add(new Point3D(dx,dy,dz));
		points.add(new Point3D(0.0,dy,dz));
    	
    
    	texturePoints=new Vector();

    	//lower base
    	double y=by;
    	double x=bx;
    	
     	texturePoints.add(new Point3D(x,y,0));
    	texturePoints.add(new Point3D(x+dx,y,0));
    	texturePoints.add(new Point3D(x+dx, y+dy,0));
    	texturePoints.add(new Point3D(x,y+dy,0));
    	
    	//faces
    	y=by+dy;
        
    	texturePoints.add(new Point3D(x,y,0));
    	texturePoints.add(new Point3D(x+dx,y,0));
    	texturePoints.add(new Point3D(x+dy+dx,y,0));
    	texturePoints.add(new Point3D(x+dy+2*dx,y,0));
    	texturePoints.add(new Point3D(x+2*dy+2*dx,y,0));    	
    	
    	
    	texturePoints.add(new Point3D(x,y+dz,0));
    	texturePoints.add(new Point3D(x+dx,y+dz,0));
    	texturePoints.add(new Point3D(x+dy+dx,y+dz,0));
    	texturePoints.add(new Point3D(x+dy+2*dx,y+dz,0));
    	texturePoints.add(new Point3D(x+2*dy+2*dx,y+dz,0));
    	
    	y=by+dy+dz;
    	
    	//upper base
      	texturePoints.add(new Point3D(x,y,0));
    	texturePoints.add(new Point3D(x+dx,y,0));
    	texturePoints.add(new Point3D(x+dx,y+dy,0));
    	texturePoints.add(new Point3D(x,y+dy,0));


		IMG_WIDTH=(int) (2*dy+2*dx+2*bx);
		IMG_HEIGHT=(int) (dy*2+dz+2*by);
    }
    

    public void printTexture(Graphics2D bufGraphics) {
    

		//draw lines for reference

		bufGraphics.setColor(Color.RED);
		bufGraphics.setStroke(new BasicStroke(0.1f));
		
		//lower base
		printTextureLine(bufGraphics,0,1);
		printTextureLine(bufGraphics,1,2);
		printTextureLine(bufGraphics,2,3);
		printTextureLine(bufGraphics,3,0);
		
		//lateral faces
		bufGraphics.setColor(Color.BLACK);
		printTextureLine(bufGraphics,4,5);
		printTextureLine(bufGraphics,5,10);
		printTextureLine(bufGraphics,10,9);
		printTextureLine(bufGraphics,9,4);
		
		printTextureLine(bufGraphics,5,6);
		printTextureLine(bufGraphics,6,11);
		printTextureLine(bufGraphics,11,10);
		
		printTextureLine(bufGraphics,6,7);
		printTextureLine(bufGraphics,7,12);
		printTextureLine(bufGraphics,12,11);
		
		printTextureLine(bufGraphics,7,8);
		printTextureLine(bufGraphics,8,13);
		printTextureLine(bufGraphics,13,12);
		
		//upper base
		bufGraphics.setColor(Color.BLUE);
		printTextureLine(bufGraphics,14,15);
		printTextureLine(bufGraphics,15,16);
		printTextureLine(bufGraphics,16,17);
		printTextureLine(bufGraphics,17,14);
	
	
    }

	
}
