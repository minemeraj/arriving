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

public class Head1Model extends MeshModel{

	double dx = 0;
	double dy = 0;
	double dz = 0;

	private int[][][] faces; 



	int bx=10;
	int by=10;

	public Head1Model(double dx, double dy, double dz) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}

	@Override
	public void initMesh() {

		points=new Vector();
		texturePoints=new Vector();

		///BUST
		BPoint[][] bust=buildHeadBPoints(dx,dy,dz);

		double deltax=100;
		double deltay=100;

		int xNumSections=5;  
		int zNumSections=bust.length;
		int  NUMFACES=(xNumSections-1)*(zNumSections-1);

		for (int k = 0; k < zNumSections; k++) { 


			double y=by+deltay*k;

			for (int i = 0; i < xNumSections; i++) {

				double x=bx+deltax*i;


				addTPoint(x, y, 0);

			}

		}


		int[][][] tFaces = new int[NUMFACES][3][4];

		int counter=0;

		for(int k=0;k<bust.length-1;k++){
			for (int i = 0; i < 4; i++) {
	
				buildFace(tFaces,counter++,
						bust[k][i],
						bust[k][(i+1)%4],
						bust[k+1][(i+1)%4],
						bust[k+1][(i)],
						xNumSections,zNumSections);
	
			}
		}
		
		
		faces=new int[counter][3][];

		for (int i = 0; i < counter; i++) {

			faces[i] = (int[][]) tFaces[i];

		}


		IMG_WIDTH=(int) (2*bx+deltax*(xNumSections-1));
		IMG_HEIGHT=(int) (2*by+deltay*((zNumSections-1)));
	}


	private BPoint[][] buildHeadBPoints(double dx, double dy, double dz) {


		BPoint[][] head=new BPoint[11][4];
		
		double deltax=dx*0.5;
		double deltay=dy;
		double deltaz=dz;

		head[0][0]= addBPoint(-deltax,-deltay*0.4884,deltaz*0);
		head[0][1]= addBPoint(deltax,-deltay*0.4884,deltaz*0);
		head[0][2]= addBPoint(deltax,deltay*0.6163,deltaz*0);
		head[0][3]= addBPoint(-deltax,deltay*0.6163,deltaz*0);

		
		head[1][0]= addBPoint(-deltax,-deltay*0.4187,deltaz*0.1148);
		head[1][1]= addBPoint(deltax,-deltay*0.4187,deltaz*0.1148);
		head[1][2]= addBPoint(deltax,deltay*0.6396,deltaz*0.1148);
		head[1][3]= addBPoint(-deltax,deltay*0.6396,deltaz*0.1148);

		
		head[2][0]= addBPoint(-deltax,-deltay*0.4187,deltaz*0.1803);
		head[2][1]= addBPoint(deltax,-deltay*0.4187,deltaz*0.1803);
		head[2][2]= addBPoint(deltax,deltay*0.6396,deltaz*0.1803);
		head[2][3]= addBPoint(-deltax,deltay*0.6396,deltaz*0.1803);
		
		head[3][0]= addBPoint(-deltax,-deltay*0.4651,deltaz*0.2787);
		head[3][1]= addBPoint(deltax,-deltay*0.4651,deltaz*0.2787);
		head[3][2]= addBPoint(deltax,deltay*0.6861,deltaz*0.2787);
		head[3][3]= addBPoint(-deltax,deltay*0.6861,deltaz*0.2787);
		
		head[4][0]= addBPoint(-deltax,-deltay*0.4884,deltaz*0.3279);
		head[4][1]= addBPoint(deltax,-deltay*0.4884,deltaz*0.3279);
		head[4][2]= addBPoint(deltax,deltay*0.7791,deltaz*0.3279);
		head[4][3]= addBPoint(-deltax,deltay*0.7791,deltaz*0.3279);

		
		head[5][0]= addBPoint(-deltax,-deltay*0.5698,deltaz*0.4754);
		head[5][1]= addBPoint(deltax,-deltay*0.5698,deltaz*0.4754);
		head[5][2]= addBPoint(deltax,deltay*0.6861,deltaz*0.4754);
		head[5][3]= addBPoint(-deltax,deltay*0.6861,deltaz*0.4754);
		
		head[6][0]= addBPoint(-deltax,-deltay*0.5814,deltaz*0.6066);
		head[6][1]= addBPoint(deltax,-deltay*0.5814,deltaz*0.6066);
		head[6][2]= addBPoint(deltax,deltay*0.6628,deltaz*0.6066);
		head[6][3]= addBPoint(-deltax,deltay*0.6628,deltaz*0.6066);
		
		head[7][0]= addBPoint(-deltax,-deltay*0.6047,deltaz*0.6885);
		head[7][1]= addBPoint(deltax,-deltay*0.6047,deltaz*0.6885);
		head[7][2]= addBPoint(deltax,deltay*0.6163,deltaz*0.6885);
		head[7][3]= addBPoint(-deltax,deltay*0.6163,deltaz*0.6885);
		
		head[8][0]= addBPoint(-deltax,-deltay*0.4684,deltaz*0.8525);
		head[8][1]= addBPoint(deltax,-deltay*0.4684,deltaz*0.8525);
		head[8][2]= addBPoint(deltax,deltay*0.4768,deltaz*0.8525);
		head[8][3]= addBPoint(-deltax,deltay*0.4768,deltaz*0.8525);

		
		head[9][0]= addBPoint(-deltax,-deltay*0.3605,deltaz*0.9344);
		head[9][1]= addBPoint(deltax,-deltay*0.3605,deltaz*0.9344);
		head[9][2]= addBPoint(deltax,deltay*0.3721,deltaz*0.9344);
		head[9][3]= addBPoint(-deltax,deltay*0.3721,deltaz*0.9344);
		
		head[10][0]= addBPoint(-deltax,-deltay*0,deltaz*1.0);
		head[10][1]= addBPoint(deltax,-deltay*0,deltaz*1.0);
		head[10][2]= addBPoint(deltax,deltay*0,deltaz*1.0);
		head[10][3]= addBPoint(-deltax,deltay*0,deltaz*1.0);
		



		return head;
	}

	private void buildFace(int[][][] faces, 
			int counter, 
			BPoint p0, BPoint p1, BPoint p2, BPoint p3, 
			int xNumSections, 
			int zNumSections
			) {

		int sz=3;
		if(p3!=null)
			sz=4;


		faces[counter][0][MeshModel.FACE_TYPE_ORIENTATION]=Renderer3D.CAR_BACK;

		int[] pts = new int[sz];
		faces[counter][MeshModel.FACE_TYPE_BODY_INDEXES]=pts;

		pts[0]=p0.getIndex();
		pts[1]=p1.getIndex();
		pts[2]=p2.getIndex();
		if(p3!=null)
			pts[3]=p3.getIndex();

		int nx=(xNumSections-1);
		int m=counter/nx;
		int n=counter-m*nx;

		int p=(nx+1)*m+n;

		int[] tts = new int[sz];
		faces[counter][MeshModel.FACE_TYPE_TEXTURE_INDEXES]=tts;

		tts[0]=p;
		tts[1]=p+1;

		if(p3!=null){

			tts[2]=p+1+(nx+1);
			tts[3]=p+(nx+1);

		}else{

			tts[2]=p+(nx+1);
		}
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
