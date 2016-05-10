package com.editors.models;

import java.awt.Graphics2D;
import java.util.Vector;

import com.Point3D;

public class F10Model extends MeshModel{
	
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
		
		//texture points
		
		//faces
		
		IMG_WIDTH=(int) (2*bx+2*(dx+dz));
		IMG_HEIGHT=(int) (2*by+dy);
		
	}

	@Override
	public void printTexture(Graphics2D bufGraphics) {
		
		
	}

}
