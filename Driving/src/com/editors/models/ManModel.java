package com.editors.models;

import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

public class ManModel extends MeshModel{
	
	double dx = 0;
	double dy = 0;
	double dz = 0;
	
	private int[][][] faces; 
	
	int numSections=10;
	int nPoints=4;
	
	int bx=10;
	int by=10;
	
	public ManModel(double dx, double dy, double dz) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}

	@Override
	public void initMesh() {
		
		points=new Vector();
		texturePoints=new Vector();
		
		double deltaz=dz/(numSections-1);
		
		for(int k=0;k<numSections;k++){
			
			double z=k*deltaz;
			
			double x=0;
			double y=0;
			
			addPoint(x, y, z);
			addPoint(x+dx, y, z);
			addPoint(x+dx, y+dy, z);
			addPoint(x, y+dy, z);
		}
		
		for(int k=0;k<numSections-1;k++){
			
			double z=k*deltaz;
			
			double x=0;
			
			addTPoint(x,z,0);
			addTPoint(x+dx,z,0);
			addTPoint(x+dx,z+deltaz,0);
			addTPoint(x,z+deltaz,0);
		}
		int NUM_FACES=nPoints*(numSections-1);
		faces=new int[NUM_FACES][3][nPoints];
		
		IMG_WIDTH=(int) (2*bx+2*(dx+dy));
		IMG_HEIGHT=(int) (2*by+dz);
	}

	
	@Override
	public void printTexture(Graphics2D bufGraphics) {
		// TODO Auto-generated method stub
		
	}
	
	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);

	}


}
