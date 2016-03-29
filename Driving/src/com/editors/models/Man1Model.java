package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

public class Man1Model extends MeshModel {
	
	private int[][][] backFaces; 
	private int[][][] frontFaces;
	
	double dx = 0;
	double dy = 0;
	double dz = 0;
	
	int bx=10;
	int by=10;

	

	public Man1Model(double dx, double dy, double dz) {
	
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}

	@Override
	public void initMesh() {
		
		points=new Vector();
		texturePoints=new Vector();
		
		int xNumSections=bodySections[0][1].length;

		int zNumSections=bodySections.length;
		
		double deltax=dx/(xNumSections-1);
		double deltaz=dz/(zNumSections-1);
		
	
		int nBackPoints=0;
		
	    //back
		for (int k = 0; k < zNumSections; k++) { 
			
			double[] d=bodySections[k][1];
			
			double z=bodySections[k][0][0]*dz; 
			
			for (int i = 0; i < xNumSections; i++) {
				
				//center points in x=0 with -dx/2;
				double x=deltax*i-dx*0.5;
				//double y=-s[i]*dy*d[2];
				
				double y=-dy*d[i];
				
				addPoint(x, y, z);
				
				nBackPoints++;
			}
			
		}
		
		//front
		
		for (int k = 0; k < zNumSections; k++) { 
			
			
			double rx=dx*0.5;
			double ry=dy;
			
			double[] d=bodySections[k][1];
			
			double z=bodySections[k][0][0]*dz; 
			ry=ry*d[1];
			
			for (int i = 0; i < xNumSections; i++) {

				//center points in x=0 with -dx/2;
				double x=(dx-deltax*i)-dx*0.5;
				//double y=-s[i]*dy*d[2];
				
				double y=dy*d[i];
				addPoint(x, y, z);
				
			}
			
		}
		
		//back
		
		int nBackTPoints=0;
		
		for (int k = 0; k < zNumSections; k++) { 
			
			double[] d=bodySections[k][1];
			
			double z=bodySections[k][0][0]*dz; 
			
			for (int i = 0; i < xNumSections; i++) {
				
				double x=bx+deltax*i;
			
				
				addTPoint(x, z, 0);
				
				nBackTPoints++;
			}
			
		}
		
		//front
		for (int k = 0; k < zNumSections; k++) { 
			
			double[] d=bodySections[k][1];
			
			double z=bodySections[k][0][0]*dz; 
			
			for (int i = 0; i < xNumSections; i++) {
				
				double x=bx+deltax*i+dx;
			
				
				addTPoint(x, z, 0);

			}
			
		}
		
		backFaces=buildSinglePlaneFaces(xNumSections, zNumSections,
				0, 0);
		frontFaces=buildSinglePlaneFaces(xNumSections, zNumSections, 
				nBackPoints, nBackTPoints);
		
		postProcessor(backFaces,frontFaces);
	
		
		IMG_WIDTH=(int) (2*bx+dx+dy*Math.PI);
		IMG_HEIGHT=(int) (2*by+dz);

	}


	private void postProcessor(int[][][] backFaces2, int[][][] frontFaces2) {
	
		Vector vFaces=new Vector();
		vFaces.add(backFaces2);
		vFaces.add(frontFaces2);
		
		super.postProcessor(vFaces);
	}

	@Override
	public void printTexture(Graphics2D bg) {
	
		bg.setColor(Color.BLACK);
		bg.setStroke(new BasicStroke(0.1f));
		printTextureFaces(bg,backFaces);

		bg.setColor(Color.BLUE);
		bg.setStroke(new BasicStroke(0.1f));
		printTextureFaces(bg,frontFaces);
	}
	
	@Override
	public void printMeshData(PrintWriter pw) {
		super.printMeshData(pw);
		super.printFaces(pw, backFaces);
		super.printFaces(pw, frontFaces);
	}
	
	@Override
	public boolean isFilter(int k, int p0) {
		return (bodyGrid[k][p0]==0);
	}
	/**
	 * 
	 * z sections and x[] sections
	 * 
	 */
	double[][][] bodySections={
			
			{{0.0},{0.0,0,0.0,0,0.0,0,0.0,1,1.0,1,0.0,0,0.0,0,0.0,1,1.0,1,0.0,0,0.0,0,0.0,0,0.0}},
			{{0.5},{0.0,1,1.0,1,0.0,0,0.0,1,1.0,1,0.0,1,1.0,1,0.0,1,1.0,1,0.0,0,0.0,1,1.0,1,0.0}},
			{{0.5625},{0.0,1,1.0,1,0.0,0,0.0,1,1.0,1,1.0,1,1.0,1,1.0,1,1.0,1,0.0,0,0.0,1,1.0,1,0.0}},
			{{0.8125},{0.0,1,1.0,1,0.0,0,0.0,1,1.0,1,1.0,1,1.0,1,1.0,1,1.0,1,0.0,0,0.0,1,1.0,1,0.0}},
			{{0.84375},{0.0,1,1.0,1,1.0,1,1.0,1,1.0,1,1.0,1,1.0,1,1.0,1,1.0,1,1.0,1,1.0,1,1.0,1,0.0}},
			{{0.875},{0.0,0,0.0,0,0.0,0,0.0,0,0.0,0,0.0,1,1.0,1,0.0,0,0.0,0,0.0,0,0.0,0,0.0,0,0.0}},
			{{0.90625},{0.0,0,0.0,0,0.0,0,0.0,0,0.0,1,1.0,1,1.0,1,1.0,1,0.0,0,0.0,0,0.0,0,0.0,0,0.0}},
			{{1.0},{0.0,0,0.0,0,0.0,0,0.0,0,0.0,1,1.0,1,1.0,1,1.0,1,0.0,0,0.0,0,0.0,0,0.0,0,0.0}},
	};
	
	public static int[][] bodyGrid={
			
			
			{0,0,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,0,0},
			{1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1},
			{1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0},
			
	};
	
	public static void main(String[] args) {
		//new Man1Model(0, 0, 0).printNewCode();
	}
	
	private void printNewCode() {
		
		for (int i = 0; i < bodySections.length; i++) {
			
			double[][] bs = bodySections[i];
			
			System.out.print("{");
			
				System.out.print("{");
				System.out.print(bs[0][0]);
				System.out.print("},");
				
				System.out.print("{");
				
				for (int j = 0; j < bs[1].length; j++) {
					if(j>0)
						System.out.print(",");
					System.out.print(bs[1][j]);
					if(j<bs[1].length-1){
						
						System.out.print(",");
						double d0=bs[1][j];
						double d1=bs[1][j+1];
						
						if(d0==1 || d1==1)
							System.out.print(1);
						else
							System.out.print(0);
					}
				}
				System.out.print("}");
			
			System.out.println("},");
		}
		
		for (int i = 0; i < bodyGrid.length; i++) {
			
			int[] fd0 = bodyGrid[i];
			
			System.out.print("{");
			
			for (int j = 0; j < fd0.length; j++) {
				if(j>0)
					System.out.print(",");
				System.out.print(fd0[j]);
				if(j<fd0.length-1){
					
					System.out.print(",");
					System.out.print(fd0[j]);
				}
				
			}
			
			System.out.println("},");
	
			
		}
		
	}

	private double m(double d, double e) {
		return (d+e)*0.5;
	}
	
	
}
