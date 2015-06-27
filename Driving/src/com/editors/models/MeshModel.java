package com.editors.models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.Point3D;

public class MeshModel {
	

	Vector texturePoints=null;
	Vector points=null;
	
	Color backgroundColor=Color.green;
	
	int IMG_WIDTH=100;
	int IMG_HEIGHT=100;



	String title="Mesh model";

	
	public MeshModel(){		
		

		
	}
	

	public void printMeshData(PrintWriter pw) {
		
		for(int i=0;i<points.size();i++){
			
			
			Point3D p=(Point3D) points.elementAt(i);
			print(pw,"v="+p.x+" "+p.y+" "+p.z);
			
		}
	
		
		for (int i = 0; i < texturePoints.size(); i++) {
			Point3D p = (Point3D) texturePoints.elementAt(i);
			print(pw,"vt="+p.x+" "+p.y);
		}
		
	}

	public void initMesh(){
		
		texturePoints=new Vector();
	}
	
	
	public void print(PrintWriter pw, String string) {
		
		pw.println(string);
		
	}
	
	
	
	
	public void printTexture(Graphics2D bufGraphics) {
		// TODO Auto-generated method stub
		
	}

	public void printTextureLine(Graphics2D graphics, int indx0,int indx1){
		
		
		Point3D p0=(Point3D) texturePoints.elementAt(indx0);
		Point3D p1=(Point3D) texturePoints.elementAt(indx1);
		

		graphics.drawLine(cX(p0.x),cY(p0.y),cX(p1.x),cY(p1.y));	
		
	}

	private int cX(double x) {
		return (int) x;
	}

	private int cY(double y) {
		
		return (int) (IMG_HEIGHT-y);
	}

	
	public void printTexture(File file){


		BufferedImage buf=new BufferedImage(IMG_WIDTH,IMG_HEIGHT,BufferedImage.TYPE_BYTE_INDEXED);

		try {


			Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();

			bufGraphics.setColor(backgroundColor);
			bufGraphics.fillRect(0,0,IMG_WIDTH,IMG_HEIGHT);

			printTexture(bufGraphics);   


			ImageIO.write(buf,"gif",file);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	
	public void addPoint(double x, double y, double z) {
	
    	points.add(new Point3D(x,y,z));
		
	}
	
	public void addTPoint(double x, double y, double z) {
		
		texturePoints.add(new Point3D(x,y,z));
		
	}
	

}
