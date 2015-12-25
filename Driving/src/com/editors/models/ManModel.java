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
	private int[][][] rightArmFaces;
	private int[][][] leftArmFaces;
	private int[][][] rightLegFaces;
	private int[][][] leftLegFaces;
	
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
		
		int numSections=body.length;
		
		double leg_length=100;
		
		int totalBodyPoints=0;
		
		for(int k=0;k<numSections;k++){
			
		    double[] d=body[k];
		    
		    double xi=d[1];
		    double yi=d[2];
		    double zi=d[0];
			
			double z=zi*dz+leg_length;
			double deltax=dx*xi*0.5;
			double deltay=dy*yi*0.5;
			
			double x=0;
			double y=0;
			
			addPoint(x-deltax, y-deltay, z);
			addPoint(x+deltax, y-deltay, z);
			addPoint(x+deltax, y+deltay, z);
			addPoint(x-deltax, y+deltay, z);
			
			totalBodyPoints+=4;
		}
		
		//right leg
		for (int i = 0; i < 2; i++) {
			//here right arm points
			double z=leg_length*i;
			
			double x=-dx;
			double y=0;
			
			double deltax=10;
			double deltay=10;
			
			addPoint(x-deltax, y-deltay, z);
			addPoint(x+deltax, y-deltay, z);
			addPoint(x+deltax, y+deltay, z);
			addPoint(x-deltax, y+deltay, z);
		}
		
		//left leg
		for (int i = 0; i < 2; i++) {
			//here right arm points
			double z=leg_length*i;
			
			double x=-dx+dx+dy;
			double y=0;
			
			double deltax=10;
			double deltay=10;
			
			addPoint(x-deltax, y-deltay, z);
			addPoint(x+deltax, y-deltay, z);
			addPoint(x+deltax, y+deltay, z);
			addPoint(x-deltax, y+deltay, z);
		}

		
		int totalBodyTexturePoints=0;
		
		//double block texture
		for(int tex=0;tex<2;tex++){
			for(int k=0;k<numSections;k++){
				
			    double[] d=body[k];
			    
			    double xi=d[1];
			    double yi=d[2];
			    double zi=d[0];
				
				double z=by+zi*dz+leg_length;
				
				double x0=bx+dx/2;
				if(tex==1)
					x0=x0+(2*dy+dx);
				
				for (int p0 = 0; p0 <= nBasePoints/2; p0++) {
		
					double x=0;
				
					if(p0<nBasePoints/4){
						
						x=x0-dx*0.5*xi;
						
					}else if(p0>=nBasePoints/4 && p0<nBasePoints/2)
					{
						x=x0+dx*0.5*xi;
					}
					else if(p0==nBasePoints/2){
						
						x=x0+dx*0.5*xi+dy*yi;
					}
					
			
					
					addTPoint(x,z,0);
					
					totalBodyTexturePoints++;
					
				
				}
			}
		}
		
		//right leg
		for (int k = 0; k < 2; k++) {
			
			double z=leg_length*k; 
			
			double x=0;
			
			for(int s=0;s<=4;s++){

				addTPoint(x,z,0);
				
				x+=10;
			}

		}
		
		//left leg
		for (int k = 0; k < 2; k++) {
			
			double z=leg_length*k; 
			
			double x=dx+dy;
			
			for(int s=0;s<=4;s++){

				addTPoint(x,z,0);
				
				x+=10;
			}

		}
		
		faces=MeshModel.buildDoubleBlockFaces(nBasePoints,numSections,0,0);
	    rightLegFaces=MeshModel.buildSingleBlockFaces(4, 2, totalBodyPoints, totalBodyTexturePoints);
	    leftLegFaces=MeshModel.buildSingleBlockFaces(4, 2, totalBodyPoints+8, totalBodyTexturePoints+10);
		
		IMG_WIDTH=(int) (2*bx+2*(dx+dy)+dy);
		IMG_HEIGHT=(int) (2*by+dz+leg_length);
	}

	
	

	@Override
	public void printTexture(Graphics2D bg) {
		//draw lines for reference

		bg.setColor(Color.BLACK);
		bg.setStroke(new BasicStroke(0.1f));
		printTextureFaces(bg,faces);
		bg.setColor(Color.RED);
		printTextureFaces(bg,rightLegFaces);
		bg.setColor(Color.BLUE);
		printTextureFaces(bg,leftLegFaces);
		
	}
	


	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);
		super.printFaces(pw, rightLegFaces);
		super.printFaces(pw, leftLegFaces);
	}
	
	/**
	 * BOTTOM-UP SECTIONS
	 * z,x,y
	 * 
	 */
	public static final double[][] body={
			
			{0.0,0.1111,1.0},
			{0.1239,0.6032,1.0},
			{0.2743,0.5556,1.0},
			{0.4867,0.6508,1.0},
			{0.5221,1.0,1.0},
			{0.5929,0.9206,1.0},
			{0.6372,0.7778,1.0},
			{0.6991,0.2381,1.0},
			{0.7611,0.2540,1.0},
			{0.9027,0.3810,1.0},
			{0.9823,0.1905,1.0},
	
	};


}
