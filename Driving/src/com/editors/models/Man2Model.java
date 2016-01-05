package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.editors.cars.data.Car;
import com.main.Renderer3D;

public class Man2Model extends MeshModel{

	double dx = 0;
	double dy = 0;
	double dz = 0;

	private int[][][] faces; 



	int bx=10;
	int by=10;

	public Man2Model(double dx, double dy, double dz) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}

	@Override
	public void initMesh() {

		points=new Vector();
		texturePoints=new Vector();



		BPoint p0= addBPoint(0,0,0);
		BPoint p1= addBPoint(100,0,0);
		BPoint p2= addBPoint(100,0,100);
		BPoint p3= addBPoint(0,0,100);

		int zNumSections=2;
		int xNumSections=2;  

		double deltax=dx/(xNumSections-1);
		double deltay=dz/(zNumSections-1);

		for (int k = 0; k < zNumSections; k++) { 


			double y=by+deltay*k;

			for (int i = 0; i < xNumSections; i++) {

				double x=bx+deltax*i;


				addTPoint(x, y, 0);

			}

		}

		int  NUMFACES=1;
		faces=new int[NUMFACES][3][4];

		/*int m=NUMFACES/zNumSections;
		int n=NUMFACES/xNumSections;*/

		int counter=0;
		
		buildFace(faces,counter++,p0,p1,p2,p3);

		IMG_WIDTH=(int) (2*bx+2*(dx+dx));
		IMG_HEIGHT=(int) (2*by+dz);
	}




	private void buildFace(int[][][] faces, int counter, BPoint p0, BPoint p1, BPoint p2, BPoint p3) {
		

		faces[counter][0][MeshModel.FACE_TYPE_ORIENTATION]=Renderer3D.CAR_BACK;

		int[] pts = new int[4];
		faces[counter][MeshModel.FACE_TYPE_BODY_INDEXES]=pts;

		pts[0]=p0.getIndex();
		pts[1]=p1.getIndex();
		pts[2]=p2.getIndex();
		pts[3]=p3.getIndex();


		int[] tts = new int[4];
		faces[counter][MeshModel.FACE_TYPE_TEXTURE_INDEXES]=tts;

		tts[0]=0;
		tts[1]=1;
		tts[2]=3;
		tts[3]=2;
		
	}

	@Override
	public void printTexture(Graphics2D bg) {
		//draw lines for reference

		bg.setColor(Color.RED);
		bg.setStroke(new BasicStroke(0.1f));
		printTextureFaces(bg,faces);

	}



	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);

	}




}
