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

		for(int k=0;k<numSections;k++){

			double[][] d=body[k];

			double yi=d[0][0];
			
			double xi=d[1][0];
			double z0=d[2][0];
			double z1=d[2][1];

			double y=yi*dy;
			
			double deltax=dx*xi*0.5;
			double deltaz0=dz*z0;
			double deltaz1=dz*z1;

			double x=0;
			double z=0;

			addPoint(x-deltax, y,z+deltaz0);
			addPoint(x-deltax, y,z+deltaz1);
			addPoint(x+deltax, y,z+deltaz1);
			addPoint(x+deltax, y,z+deltaz0);

			totalBodyPoints+=4;


		}

		//single block texture

		for(int k=0;k<numSections;k++){

			double[][] d=body[k];

			double yi=d[0][0];

			double y=by+yi*dy;
			double x=bx;

			for (int p0 = 0; p0 <= nBasePoints; p0++) {
				addTPoint(x,y,0);

				if(p0%2==0)
					x+=dz;
				else
					x+=dx;
				
			}
		}



		faces=buildSingleBlockFaces(nBasePoints,numSections,0,0);


		IMG_WIDTH=(int) (2*bx+2*(dx+dz));
		IMG_HEIGHT=(int) (2*by+dy);
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
	 * [y],[x],[z0,z1]
	 * 
	 */
	public static final double[][][] body={

			{{0.0},{1.0},{0.0,0.4}},
			{{0.1538},{1.0},{0.0,0.8}},
			{{0.2308},{1.0},{0.2,1.0}},
			{{0.3077},{1.0},{0.0,1.0}},
			{{0.6923},{1.0},{0.0,1.0}},
			{{0.7692},{1.0},{0.2,0.6}},
			{{0.8462},{1.0},{0.0,0.56}},
			{{1.0},{1.0},{0.0,0.4}}
	};


}
