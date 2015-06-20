package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;

import com.Point3D;

public class CubeModel extends MeshModel{
	
	public CubeModel(){
		
		super();

		
	}

	
	 public static void main(String[] args) {
		
		 CubeModel cm=new CubeModel();
			 
	}
	 
	 
	
	public void printMesh() {
	
		
		print("v=0.0 0.0 0.0");
		print("v=137.0 0.0 0.0");
		print("v=137.0 89.0 0.0");
		print("v=0.0 89.0 0.0");
		print("v=0.0 0.0 398.0");
		print("v=137.0 0.0 398.0");
		print("v=137.0 89.0 398.0");
		print("v=0.0 89.0 398.0");
		
		for (int i = 0; i < texturePoints.size(); i++) {
			Point3D p = (Point3D) texturePoints.elementAt(i);
			print("vt="+p.x+" "+p.y);
		}
		print("f=[1]4/0 5/1 6/2 7/3");
		print("f=[5]3/4 2/5 1/6 0/7");
		print("f=[0]0/8 1/9 5/14 4/13");
		print("f=[3]1/9 2/10 6/15 5/14");
		print("f=[4]2/10 3/11 7/16 6/15");
		print("f=[2]3/11 0/12 4/17 7/16");
		
	}



    public void initTexturePoints() {
    
    	texturePoints=new Vector();
    	texturePoints.add(new Point3D(10.0,497,0));
    	texturePoints.add(new Point3D(147.0, 497.0,0));
    	texturePoints.add(new Point3D(147.0, 586.0,0));
    	texturePoints.add(new Point3D(10.0, 586.0,0));
    	texturePoints.add(new Point3D(10.0, 10.0,0));
    	texturePoints.add(new Point3D(10.0, 99.0,0));
    	texturePoints.add(new Point3D(147.0, 99.0,0));
    	texturePoints.add(new Point3D(147.0, 10.0,0));
    	texturePoints.add(new Point3D(10.0, 99.0,0));
    	texturePoints.add(new Point3D(147.0, 99.0,0));
    	texturePoints.add(new Point3D(236.0, 99.0,0));
    	texturePoints.add(new Point3D(373.0, 99.0,0));
    	texturePoints.add(new Point3D(462.0, 99.0,0));
    	texturePoints.add(new Point3D(10.0, 497.0,0));
    	texturePoints.add(new Point3D(147.0, 497.0,0));
    	texturePoints.add(new Point3D(236.0, 497.0,0));
    	texturePoints.add(new Point3D(373.0, 497.0,0));
    	texturePoints.add(new Point3D(462.0, 497.0,0));

		IMG_WIDTH=800;
		IMG_HEIGHT=800;
    }
    

    public void printTexture(Graphics2D bufGraphics) {
    

		//draw lines for reference

		bufGraphics.setColor(Color.RED);
		bufGraphics.setStroke(new BasicStroke(0.1f));
		
		printTextureLine(bufGraphics,0,1);
    }

	
}
