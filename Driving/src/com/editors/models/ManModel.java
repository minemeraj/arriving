package com.editors.models;

import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

public class ManModel extends MeshModel{
	
	double dx = 0;
	double dy = 0;
	double dz = 0;
	
	private int[][][] faces; 
	
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
		
		
	}

	
	@Override
	public void printTexture(Graphics2D bufGraphics) {
		// TODO Auto-generated method stub
		
	}
	
	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw, faces);

	}


}
