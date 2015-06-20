package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import com.Point3D;
import com.editors.DoubleTextField;

public class CubeModel extends MeshModel{
	
	double dx=100;
	double dy=200;
	double dz=300;
	
	int bx=10;
	int by=10;
	
	String title="Cube model";
	private DoubleTextField dz_text;
	private DoubleTextField dy_text;
	private DoubleTextField dx_text;
	

	
	public CubeModel(){
		
		super(200,250);
		setTitle(title);
		
	}

	
	 public static void main(String[] args) {
		
		 CubeModel cm=new CubeModel();
			 
	}
	 
	 public void buildCenter() {

		 double dx=100;
		 double dy=200;
		 double dz=300;

		 int r=10;

		 JLabel lx=new JLabel("dx:");
		 lx.setBounds(5,r,20,20);
		 center.add(lx);
		 dx_text=new DoubleTextField(8);
		 dx_text.setBounds(30,r,120,20);
		 dx_text.setText(dx);
		 center.add(dx_text);

		 r+=30;

		 JLabel ly=new JLabel("dy:");
		 ly.setBounds(5,r,20,20);
		 center.add(ly);
		 dy_text=new DoubleTextField(8);
		 dy_text.setBounds(30,r,120,20);
		 dy_text.setText(dy);
		 center.add(dy_text);


		 r+=30;

		 JLabel lz=new JLabel("dz:");
		 lz.setBounds(5,r,20,20);
		 center.add(lz);
		 dz_text=new DoubleTextField(8);
		 dz_text.setBounds(30,r,120,20);
		 dz_text.setText(dz);
		 center.add(dz_text);

		 r+=30;

		 meshButton=new JButton("Mesh");
		 meshButton.setBounds(10,r,80,20);
		 meshButton.addActionListener(this);
		 center.add(meshButton);

		 r+=30;

		 textureButton=new JButton("Texture");
		 textureButton.setBounds(10,r,90,20);
		 textureButton.addActionListener(this);
		 center.add(textureButton);

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
    	
    	dx=dx_text.getvalue();
    	dy=dy_text.getvalue();
    	dz=dz_text.getvalue();
    	
    	points=new Vector();
    	
    	//lower base
    	points.add(new Point3D(0.0,0.0,0.0));
		points.add(new Point3D(dx,0.0,0.0));
		points.add(new Point3D(dx,dy,0.0));
		points.add(new Point3D(0.0,dy,0.0));
		//upper base
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
