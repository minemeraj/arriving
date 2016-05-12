package com.editors.models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.Point3D;
import com.main.Renderer3D;

/**
 * 
 * A unique block, which, Like a bent mattress, can bend and become vertical from horizontal,
 * or viceversa.
 * 
 * @author Administrator
 *
 */
public class Truck0Model extends MeshModel{

	private int bx=10;
	private int by=10;

	private double dx = 0;
	private double dy = 0;
	private double dz = 0;

	private int[][][] faces;
	
	public static String NAME="Truck0";
	
	int basePoints=4;

	public Truck0Model(double dx, double dy, double dz) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}

	@Override
	public void initMesh() {
		points=new Vector<Point3D>();
		texturePoints=new Vector();

		//points

		double xx0=dx*0.5;
	


		//anti-clockwise sections on length direction
		//p0 on the lower left corner of the section
		//p3 on the upper left corner of the section
		for (int i = 0; i < bodySections.length; i++) {

			double x0=bodySections[i][0][0];
			double y0=bodySections[i][0][1];
			double z0=bodySections[i][0][2];
			
			double x3=bodySections[i][1][0];
			double y3=bodySections[i][1][1];
			double z3=bodySections[i][1][2];
	


			addPoint(xx0-x0*dx,y0*dy,z0*dz);
			addPoint(xx0+x0*dx,y0*dy,z0*dz);
			addPoint(xx0+x3*dx,y3*dy,z3*dz);
			addPoint(xx0-x3*dx,y3*dy,z3*dz);	
			
			
		}

		//texture points
		//single block texture
		
		double deltay=dy/(bodySections.length-1);

		for(int k=0;k<bodySections.length;k++){			

			double x0=bodySections[k][0][0];
			double x3=bodySections[k][1][0];
			
			double xi=2.0*Math.max(x0, x3);


			double y=by+k*deltay+dz;
			double x=bx;

			for (int p0 = 0; p0 <= basePoints; p0++) {
				addTPoint(x,y,0);

				if(p0==0 || p0==2)
					x+=dz;
				else if(p0==1 || p0==3)
					x+=dx;

			}
		}
		
		//back base
		double bbx=bx+dz;
		
		addTPoint(bbx,by,0);
		addTPoint(bbx+dx,by,0);
		addTPoint(bbx+dx,by+dz,0);
		addTPoint(bbx,by+dz,0);
		
		
		//front base
		
		double bby=by+dz+dy;
		addTPoint(bbx,bby,0);
		addTPoint(bbx+dx,bby,0);
		addTPoint(bbx+dx,bby+dz,0);
		addTPoint(bbx,bby+dz,0);
		

		//faces
		int NF=calculateFacesNumber(bodySections.length);
		faces=new int[NF][3][4];

		//length sections
		int count=0;
		for (int i = 0; i <bodySections.length-1; i++) {

			int b=i*4;
			int c0=i*(basePoints+1);
			int c3=(i+1)*(basePoints+1);

			
			faces[count++]=buildFace(Renderer3D.CAR_LEFT,b,b+3,b+3+4,b+4,c0,c0+1,c3+1,c3);
			faces[count++]=buildFace(Renderer3D.CAR_TOP,b+3,b+2,b+2+4,b+3+4,c0+1,c0+1+1,c3+1+1,c3+1);					
			faces[count++]=buildFace(Renderer3D.CAR_RIGHT,b+1,b+1+4,b+2+4,b+2,c0+2,c0+1+2,c3+1+2,c3+2);	
			faces[count++]=buildFace(Renderer3D.CAR_BOTTOM,b,b+4,b+1+4,b+1,c0+3,c0+1+3,c3+1+3,c3+3);
			
	
				
		}
		//back and front base
		
		int bb=0;
		int cc=(bodySections.length)*(basePoints+1);

		faces[count++]=buildFace(Renderer3D.CAR_BACK,bb,bb+1,bb+2,bb+3,cc,cc+1,cc+2,cc+3);
		
		bb+=(bodySections.length-1)*basePoints;
		cc+=basePoints;
		faces[count++]=buildFace(Renderer3D.CAR_FRONT,bb,bb+1,bb+2,bb+3,cc,cc+1,cc+2,cc+3);

		IMG_WIDTH=(int) (2*bx+2*(dx+dz));
		IMG_HEIGHT=(int) (2*by+dy+dz*2);

	}

	private int calculateFacesNumber(int sections) {
		//with bases
		return 2+(sections-1)*4;

	}
	
	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);

	}

	@Override
	public void printTexture(Graphics2D bufGraphics) {
		
		bufGraphics.setColor(Color.BLACK);

		for (int i = 0; i <bodySections.length-1; i++) {
			
			for (int p0 = 0; p0 < basePoints; p0++) {
				
				int c0=i*(basePoints+1)+p0;
				int c3=(i+1)*(basePoints+1)+p0;
				
				printTexturePolygon(bufGraphics,c0,c0+1,c3+1,c3);
			}
		}
		int cc=(bodySections.length)*(basePoints+1);
		printTexturePolygon(bufGraphics,cc,cc+1,cc+2,cc+3);
		cc+=basePoints;
		printTexturePolygon(bufGraphics,cc,cc+1,cc+2,cc+3);

	}

	//p0,p3 for every section, while p2,p3 are symmetric on the xz plane, or a parallel one
	public double[][][] bodySections={
			{{0.5,0.0,0.0},{0.5,0.0,1.0}},
			{{0.5,0.5,0.0},{0.5,0.5,1.0}},
			{{0.5,1.0,0.0},{0.5,1.0,1.0}},
	};

}
