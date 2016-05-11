package com.editors.models;

import java.awt.Graphics2D;
import java.util.Vector;

import com.Point3D;
import com.main.Renderer3D;

public class Truck0Model extends MeshModel{

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

		double x0=dx*0.5;
		double y0=0;

		//sections on z direction
		for (int i = 0; i < cabSections.length; i++) {


			double xFront=cabSections[i][0][0];
			double xRear=cabSections[i][0][1];

			double yFront=cabSections[i][1][0];
			double yRear=cabSections[i][1][1];

			double z=cabSections[i][2][0];

			addPoint(x0-xRear*dx,y0+yRear*dy,z*dz);
			addPoint(x0+xRear*dx,y0+yRear*dy,z*dz);
			addPoint(x0+xFront*dx,y0+yFront*dy,z*dz);
			addPoint(x0-xFront*dx,y0+yFront*dy,z*dz);
		}
		//sections on y direction
		for (int i = 0; i < bodySections.length; i++) {


			double xBottom=cabSections[i][0][0];
			double xTop=cabSections[i][0][1];

			double y=cabSections[i][1][0];

			double zBottom=cabSections[i][2][0];
			double zTop=cabSections[i][2][0];


			addPoint(x0-xBottom*dx,y0+y*dy,zBottom*dz);
			addPoint(x0+xBottom*dx,y0+y*dy,zBottom*dz);
			addPoint(x0+xTop*dx,y0+y*dy,zTop*dz);
			addPoint(x0-xTop*dx,y0+y*dy,zTop*dz);
		}

		//texture points

		//faces
		int NF=cabSections.length;
		faces=new int[NF][3][4];
		
		for (int i = 0; i <cabSections.length; i++) {

			int b=i*4;
			int c=0;
			
			faces[i]=buildFace(Renderer3D.CAR_LEFT,b,b+1,b+1+4,b+4,c,c,c,c);
			

		}

		IMG_WIDTH=(int) (2*bx+2*(dx+dz));
		IMG_HEIGHT=(int) (2*by+dy);

	}

	@Override
	public void printTexture(Graphics2D bufGraphics) {


	}
	//section x,y,z directions
	private final double[][][] cabSections={
			{{0.0,1.0},{0.0,1.0},{0.0000}},
			{{0.0,1.0},{0.0,1.0},{1.0000}}
	};

	public double[][][] bodySections={
			{{0.0,1.0},{0.0},{0.0000,1.0}},
			{{0.0,1.0},{1.0},{0.0000,1.0}}
	};

}
