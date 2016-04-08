package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.Point3D;

public class Car0Model extends MeshModel{

	private double dx = 0;
	private double dy = 0;
	private double dz = 0;

	private int[][][] faces; 

	private int nBasePoints=6;

	private int bx=10;
	private int by=10;

	public Car0Model(double dx, double dy, double dz) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}

	@Override
	public void initMesh() {

		points=new Vector<Point3D>();
		texturePoints=new Vector();

		int numSections=body.length;

		int totalBodyPoints=0;

		for(int k=0;k<numSections;k++){

			double[][] d=body[k];

			double yi=d[0][0];
			
			double x0=d[1][0];
			double x1=d[1][1];
			double x2=d[1][2];
			
			double z0=d[2][0];
			double z1=d[2][1];
			double z2=d[2][2];

			double y=yi*dy;
			
			double deltax0=dx*x0*0.5;
			double deltax1=dx*x1*0.5;
			double deltax2=dx*x2*0.5;
			
			double deltaz0=dz*z0;
			double deltaz1=dz*z1;
			double deltaz2=dz*z2;

			double x=0;
			double z=0;

			addPoint(x-deltax0, y,z+deltaz0);
			addPoint(x-deltax1, y,z+deltaz1);
			addPoint(x-deltax2, y,z+deltaz2);
			addPoint(x+deltax2, y,z+deltaz2);
			addPoint(x+deltax1, y,z+deltaz1);
			addPoint(x+deltax0, y,z+deltaz0);

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

				if(p0==0 || p0==4)
					x+=dz;
				else if(p0==1 || p0==3)
					x+=dx*0.25;
				else if(p0==2)
					x+=dx*0.5;
				else if(p0==5)
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
	 * [y],[x0,x1,x2],[z0,z1,z2]
	 * 
	 * i from bottom to top
	 * 
	 * measurs from mazda-6 2008
	 * 
	 */
	private final double[][][] body={

			{{0.0000},{1.0,1.0,0.75},{0.1044,0.3655,0.3655}},
			{{0.0329},{1.0,1.0,0.75},{0.0763,0.7068,0.7068}},
			{{0.1186},{1.0,1.0,0.6279},{0.0562,0.7108,0.7108}},
			{{0.1405},{1.0,1.0,0.6279},{0.0482,0.7068,0.7590}},
			{{0.1471},{1.0,1.0,0.6279},{0.2490,0.7028,0.7711}},
			{{0.1778},{1.0,1.0,0.6279},{0.3775,0.6867,0.8273}},
			{{0.2217},{1.0,1.0,0.6279},{0.4217,0.7269,0.8916}},
			{{0.2667},{1.0,1.0,0.6279},{0.3775,0.6707,0.9438}},			
			{{0.2964},{1.0,1.0,0.6279},{0.2490,0.6667,0.9639}},
			{{0.3063},{1.0,1.0,0.6279},{0.0000,0.6627,0.9679}},
			{{0.4281},{1.0,1.0,0.6279},{0.0000,0.6426,0.9839}},
			{{0.5653},{1.0,1.0,0.8343},{0.0000,0.6145,0.9157}},			
			{{0.6894},{1.0,1.0,0.8343},{0.0000,0.6104,0.7108}},			
			{{0.7102},{1.0,1.0,0.75},{0.0000,0.5984,0.6747}},
			{{0.7201},{1.0,1.0,0.75},{0.2490,0.5984,0.6586}},
			{{0.7519},{1.0,1.0,0.75},{0.3775,0.5944,0.6305}},
			{{0.7980},{1.0,1.0,0.75},{0.4257,0.6225,0.6225}},
			{{0.8419},{1.0,1.0,0.75},{0.2490,0.5984,0.5984}},
			{{0.8782},{1.0,1.0,0.75},{0.0000,0.5703,0.5703}},
			{{0.9759},{1.0,1.0,0.75},{0.0000,0.4378,0.4378}},
			{{1.0000},{1.0,1.0,0.75},{0.1727,0.2811,0.2811}},
	
	};


}
