package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.Point3D;
import com.editors.cars.data.Car;
import com.main.Renderer3D;

public class Car0Model extends MeshModel{

	double dx = 0;
	double dy = 0;
	double dz = 0;

	private int[][][] faces; 

	int nBasePoints=4;

	int bx=10;
	int by=10;

	public Car0Model(double dx, double dy, double dz) {
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


		int totalBodyPoints=0;
		int totalLegPoints=0;
		int totalArmPoints=0;

		for(int k=0;k<numSections;k++){

			double[] d=body[k];

			double xi=d[1];
			double yi=d[2];
			double zi=d[0];

			double z=zi*dz;
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


		int totalBodyTexturePoints=0;

		//single block texture

		for(int k=0;k<numSections;k++){

			double[] d=body[k];

			double xi=d[1];
			double yi=d[2];
			double zi=d[0];

			double z=by+zi*dz;


			double x=bx;

			for (int p0 = 0; p0 <= nBasePoints; p0++) {
				addTPoint(x,z,0);

				totalBodyTexturePoints++;

				if(p0%2==0)
					x+=dx;
				else
					x+=dy;
				
			}
		}



		faces=buildSingleBlockFaces(nBasePoints,numSections,0,0);


		IMG_WIDTH=(int) (2*bx+2*(dx+dy)+dy);
		IMG_HEIGHT=(int) (2*by+dz);
	}




	@Override
	public void printTexture(Graphics2D bg) {
		//draw lines for reference

		bg.setColor(Color.BLACK);
		bg.setStroke(new BasicStroke(0.1f));
		printTextureFaces(bg,faces);

	}



	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);

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
