package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Vector;

import com.main.Renderer3D;

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
		
		int xNumSections=bodySections[0].length;

		int zNumSections=zSections.length;
		
		double deltax=dx/(xNumSections-1);
		double deltaz=dz/(zNumSections-1);
		
	
		int nBackPoints=0;
		
	    //back
		for (int k = 0; k < zNumSections; k++) { 
			
			double[] d=bodySections[k];
			
			double z=zSections[k]*dz; 
			
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
			
			double[] d=bodySections[k];
			
			double z=zSections[k]*dz; 
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
			
			double[] d=bodySections[k];
			
			double z=by+zSections[k]*dz; 
			
			for (int i = 0; i < xNumSections; i++) {
				
				double x=bx+deltax*i;
			
				
				addTPoint(x, z, 0);
				
				nBackTPoints++;
			}
			
		}
		
		//front
		for (int k = 0; k < zNumSections; k++) { 
			
			double[] d=bodySections[k];
			
			double z=by+zSections[k]*dz; 
			
			for (int i = 0; i < xNumSections; i++) {
				
				double x=bx+deltax*i+dx;
			
				
				addTPoint(x, z, 0);

			}
			
		}
		
		backFaces=buildSinglePlaneFaces(xNumSections, zNumSections,
				0, 0);
		frontFaces=buildSinglePlaneFaces(xNumSections, zNumSections, 
				nBackPoints, nBackTPoints);
		
		IMG_WIDTH=(int) (2*bx+dx+dy*Math.PI);
		IMG_HEIGHT=(int) (2*by+dz);

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
	
	public static int[][][] buildSinglePlaneFaces(int nBasePoints, int numSections, int pOffset, int tOffset) {

		int NUM_FACES=(nBasePoints-1)*(numSections-1);
		int[][][] faces=new int[NUM_FACES][3][nBasePoints];
		
		

		int counter=0;
		for (int k = 0;k < numSections-1; k++) {
			
			//int numLevelPoints=nBasePoints*(k+1);

			for (int p0 = 0; p0 < nBasePoints-1; p0++) {
				
				if(bodyGrid[k][p0]==0)
					continue;

				int p=p0+k*nBasePoints;
				int t=p0+k*nBasePoints;

				faces[counter][0][MeshModel.FACE_TYPE_ORIENTATION]=Renderer3D.CAR_BACK;

				int[] pts = new int[4];
				faces[counter][MeshModel.FACE_TYPE_BODY_INDEXES]=pts;
				pts[0]=p+pOffset;
				int pl=(p+1);
				pts[1]=pl+pOffset;
				pts[2]=pl+nBasePoints+pOffset;
				pts[3]=p+nBasePoints+pOffset;

				int[] tts = new int[4];
				faces[counter][MeshModel.FACE_TYPE_TEXTURE_INDEXES]=tts;
				tts[0]=t+tOffset;
				tts[1]=t+1+tOffset;
				tts[2]=t+1+nBasePoints+tOffset;
				tts[3]=t+nBasePoints+tOffset;

				counter++;

			}

		}

		return faces;

	}
	
	double[] zSections={
			
			0.0,
			0.25,
			0.375,
			0.5,
			0.625,
			0.75,
			0.875,
			1.0
			
	};
	
	double[][] bodySections={
			
			{0.0,0.4841,0.6614,0.7806,0.8660,0.9270,0.9682,0.9922,1.0,0.9922,0.9682,0.9270,0.8660,0.7806,0.6614,0.4841,0.0},
			{0.0,0.4841,0.6614,0.7806,0.8660,0.9270,0.9682,0.9922,1.0,0.9922,0.9682,0.9270,0.8660,0.7806,0.6614,0.4841,0.0},
			{0.0,0.4841,0.6614,0.7806,0.8660,0.9270,0.9682,0.9922,1.0,0.9922,0.9682,0.9270,0.8660,0.7806,0.6614,0.4841,0.0},
			{0.0,0.4841,0.6614,0.7806,0.8660,0.9270,0.9682,0.9922,1.0,0.9922,0.9682,0.9270,0.8660,0.7806,0.6614,0.4841,0.0},
			{0.0,0.4841,0.6614,0.7806,0.8660,0.9270,0.9682,0.9922,1.0,0.9922,0.9682,0.9270,0.8660,0.7806,0.6614,0.4841,0.0},
			{0.0,0.4841,0.6614,0.7806,0.8660,0.9270,0.9682,0.9922,1.0,0.9922,0.9682,0.9270,0.8660,0.7806,0.6614,0.4841,0.0},
			{0.0,0.4841,0.6614,0.7806,0.8660,0.9270,0.9682,0.9922,1.0,0.9922,0.9682,0.9270,0.8660,0.7806,0.6614,0.4841,0.0},
			{0.0,0.4841,0.6614,0.7806,0.8660,0.9270,0.9682,0.9922,1.0,0.9922,0.9682,0.9270,0.8660,0.7806,0.6614,0.4841,0.0},
	};
	
	public static double[][] bodyGrid={
			
			
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			
	};
	
	
}
