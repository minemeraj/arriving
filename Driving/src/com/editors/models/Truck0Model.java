package com.editors.models;

import java.awt.Graphics2D;
import java.util.Vector;

import com.Point3D;

public class Truck0Model extends MeshModel{
	
	private int bx=10;
	private int by=10;
	
	private double dx = 0;
	private double dy = 0;
	private double dz = 0;
	
	private int[][][] faces;

	@Override
	public void initMesh() {
		points=new Vector<Point3D>();
		texturePoints=new Vector();
		
		//points
		
		double x0=dx*0.5;
		double y0=0;
		
		for (int i = 0; i < cabSections.length; i++) {
			double z=cabSections[i][0][0];
			
			double xFront=cabSections[i][1][0];
			double xRear=cabSections[i][1][1];
			
			double yFront=cabSections[i][2][0];
			double yRear=cabSections[i][2][1];
			
			addPoint(x0-xRear*dx,y0+yRear*dy,z*dz);
			addPoint(x0+xRear*dx,y0+yRear*dy,z*dz);
			addPoint(x0+xFront*dx,y0+yFront*dy,z*dz);
			addPoint(x0-xFront*dx,y0+yFront*dy,z*dz);
		}
		
		//texture points
		
		//faces
		
		IMG_WIDTH=(int) (2*bx+2*(dx+dz));
		IMG_HEIGHT=(int) (2*by+dy);
		
	}

	@Override
	public void printTexture(Graphics2D bufGraphics) {
		
		
	}
	//section z,x,Y directions
	private final double[][][] cabSections={
			{{0.0000},{0.0,1.0},{0.0,1.0}}
			};

	public double[][][] bodySections={};

}
