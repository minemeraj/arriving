package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

public class HeadModel extends MeshModel {
	
	private int[][][] faces; 
	
	double dx = 0;
	double dy = 0;
	double dz = 0;
	
	int bx=10;
	int by=10;

	public HeadModel(double dx, double dy, double dz) {
	
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}

	@Override
	public void initMesh() {
		
		points=new Vector();
		texturePoints=new Vector();
		
		int xNumSections=10;
		int yNumSections=10;
		int zNumSections=10;
		
		double deltax=dx/(xNumSections-1);
		double deltaz=dz/(zNumSections-1);
		
		for (int k = 0; k < zNumSections; k++) { 
			
			double z=deltaz*k; 
			
			for (int i = 0; i < xNumSections; i++) {
				
				double x=deltax*i;
				double y=0;
				
				addPoint(x, y, z);
				
			}
			
		}
		
		
		for (int k = 0; k < zNumSections; k++) { 
			
			double z=by+deltaz*k; 
			
			for (int i = 0; i < xNumSections; i++) {
				
				double x=bx+deltax*i;
				double y=0;
				
				addTPoint(x, z, 0);
				
			}
			
		}
		
		faces=buildSinglePlaneFaces(xNumSections, zNumSections, 0, 0);
		
		IMG_WIDTH=(int) (2*bx+dx);
		IMG_HEIGHT=(int) (2*by+dz);

	}


	@Override
	public void printTexture(Graphics2D bg) {
	
		bg.setColor(Color.BLACK);
		bg.setStroke(new BasicStroke(0.1f));
		printTextureFaces(bg,faces);

	}
	
	@Override
	public void printMeshData(PrintWriter pw) {
		super.printMeshData(pw);
		super.printFaces(pw, faces);
	}

}
