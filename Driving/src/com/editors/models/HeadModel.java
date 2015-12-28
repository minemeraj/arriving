package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

public class HeadModel extends MeshModel {
	
	private int[][][] splacFaces; 
	private int[][][] neuroFaces;
	
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
		
		int tetaNumSections=10;
		
		double deltax=dx/(xNumSections-1);
		double deltaz=dz/(zNumSections-1);
		
	
		int nSplacnoPoints=0;
		
		//splanchnocranium
		for (int k = 0; k < zNumSections; k++) { 
			
			double z=deltaz*k; 
			
			for (int i = 0; i < xNumSections; i++) {
				
				double x=deltax*i;
				double y=0;
				
				addPoint(x, y, z);
				
				nSplacnoPoints++;
			}
			
		}
		
		//neurocranium
		
		double dTeta=Math.PI/(tetaNumSections);
		for (int k = 0; k < tetaNumSections; k++) { 
			
			double z=deltaz*k; 
			
			for (int i = 0; i < xNumSections; i++) {
				
				double teta=dTeta*i;
				
				double x=100*Math.cos(teta);
				double y=100*Math.sin(teta);
				
				addPoint(x, y, z);
				
			}
			
		}
		
		//splanchnocranium
		
		int nSplacnoTPoints=0;
		
		for (int k = 0; k < zNumSections; k++) { 
			
			double z=by+deltaz*k; 
			
			for (int i = 0; i < tetaNumSections; i++) {
				
				double x=bx+deltax*i;
				double y=0;
				
				addTPoint(x, z, 0);
				
				nSplacnoTPoints++;
			}
			
		}
		
		//neurocranium
		double deltaTeta=(dy*Math.PI)/(tetaNumSections-1);
		
		for (int k = 0; k < zNumSections; k++) { 
			
			double z=by+deltaz*k; 
			
			for (int i = 0; i < tetaNumSections; i++) {
				
				double x=bx+dx+deltaTeta*i;
				double y=0;
				
				addTPoint(x, z, 0);
				
			}
			
		}
		
		splacFaces=buildSinglePlaneFaces(xNumSections, zNumSections,
				0, 0);
		neuroFaces=buildSinglePlaneFaces(tetaNumSections, zNumSections, 
				nSplacnoPoints, nSplacnoTPoints);
		
		IMG_WIDTH=(int) (2*bx+dx+dy*Math.PI);
		IMG_HEIGHT=(int) (2*by+dz);

	}


	@Override
	public void printTexture(Graphics2D bg) {
	
		bg.setColor(Color.BLACK);
		bg.setStroke(new BasicStroke(0.1f));
		printTextureFaces(bg,splacFaces);

		bg.setColor(Color.BLUE);
		bg.setStroke(new BasicStroke(0.1f));
		printTextureFaces(bg,neuroFaces);
	}
	
	@Override
	public void printMeshData(PrintWriter pw) {
		super.printMeshData(pw);
		super.printFaces(pw, splacFaces);
		super.printFaces(pw, neuroFaces);
	}

}
