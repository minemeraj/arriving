package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.editors.cars.data.Car;
import com.main.Renderer3D;

public class ManModel extends MeshModel{
	
	double dx = 0;
	double dy = 0;
	double dz = 0;
	
	private int[][][] faces; 
	
	int numSections=10;
	int nBasePoints=4;
	
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
		
		//single block texture
		/*for(int k=0;k<numSections;k++){
			
			double z=by+k*deltaz;
			
			double x=bx;
			
			for (int p0 = 0; p0 <= nBasePoints; p0++) {
	
				addTPoint(x,z,0);
				
				x+=(p0%2==0?dx:dy);
			
			}
		}*/
		
		//double block texture
		for(int tex=0;tex<2;tex++){
			for(int k=0;k<numSections;k++){
				
				double z=by+k*deltaz;
				
				double x=bx;
				if(tex==1)
					x=x+(2*dy+dx);
				
				for (int p0 = 0; p0 <= nBasePoints/2; p0++) {
		
					addTPoint(x,z,0);
					
					x+=(p0%2==0?dx:dy);
				
				}
			}
		}
		
		faces=MeshModel.buildDoubleBlockFaces(nBasePoints,numSections);
		
		
		IMG_WIDTH=(int) (2*bx+2*(dx+dy)+dy);
		IMG_HEIGHT=(int) (2*by+dz);
	}

	
	

	@Override
	public void printTexture(Graphics2D bg) {
		//draw lines for reference

		bg.setColor(Color.RED);
		bg.setStroke(new BasicStroke(0.1f));
		
		for (int i = 0; i < faces.length; i++) {
			
			int[][] face=faces[i];

			int[] fts=face[0];
			int[] pts=face[1];
			int[] tts=face[2];
			
			if(tts.length==4){
			
				int idx0=tts[0];
				int idx1=tts[1];
				int idx2=tts[2];
				int idx3=tts[3];
				
				printTexturePolygon(bg,idx0,idx1,idx2,idx3);
			
			}
		}
		
	}
	
	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);

	}


}
