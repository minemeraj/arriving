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
		BPoint[][] head=buildHeadBPoints(dx,dy,dz);

		double deltax=100;
		double deltay=100;

		int xNumSections=19;  
		int zNumSections=head.length;
		int  NUMFACES=(xNumSections-1)*(zNumSections-1);



		for (int k = 0; k < zNumSections; k++) { 


			double y=by+deltay*k;

			for (int i = 0; i < xNumSections; i++) {

				double x=bx+deltax*i;


				addTPoint(x, y, 0);

			}

		}

		int ns=xNumSections-1;

		int[][][] tFaces = new int[NUMFACES][3][4];

		int counter=0;

		for(int k=0;k<head.length-1;k++){
			for (int i = 0; i < ns; i++) {

				buildFace(tFaces,counter++,
						head[k][i],
						head[k][(i+1)%ns],
						head[k+1][(i+1)%ns],
						head[k+1][(i)],
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


		BPoint[][] head=new BPoint[11][18];

		double deltax=dx*0.5;
		double deltay=dy;
		double deltaz=dz;



		head[0][0]= addBPoint(-deltax,-deltay*0.4884,deltaz*0);
		head[0][1]= addBPoint(-deltax*0.75,-deltay*0.4884,deltaz*0);
		head[0][2]= addBPoint(-deltax*0.5,-deltay*0.4884,deltaz*0);
		head[0][3]= addBPoint(-deltax*0.25,-deltay*0.4884,deltaz*0);
		head[0][4]= addBPoint(-deltax*0.0,-deltay*0.4884,deltaz*0);
		head[0][5]= addBPoint(deltax*0.25,-deltay*0.4884,deltaz*0);
		head[0][6]= addBPoint(deltax*0.5,-deltay*0.4884,deltaz*0);
		head[0][7]= addBPoint(deltax*0.75,-deltay*0.4884,deltaz*0);
		head[0][8]= addBPoint(deltax,-deltay*0.4884,deltaz*0);
		head[0][9]= addBPoint(deltax,deltay*0.6163,deltaz*0);
		head[0][10]= addBPoint(deltax*0.75,deltay*0.6163,deltaz*0);
		head[0][11]= addBPoint(deltax*0.5,deltay*0.6163,deltaz*0);
		head[0][12]= addBPoint(deltax*0.25,deltay*0.6163,deltaz*0);
		head[0][13]= addBPoint(deltax*0.0,deltay*0.6163,deltaz*0);
		head[0][14]= addBPoint(-deltax*0.25,deltay*0.6163,deltaz*0);
		head[0][15]= addBPoint(-deltax*0.5,deltay*0.6163,deltaz*0);
		head[0][16]= addBPoint(-deltax*0.75,deltay*0.6163,deltaz*0);
		head[0][17]= addBPoint(-deltax,deltay*0.6163,deltaz*0);

		head[1][0]= addBPoint(-deltax,-deltay*0.4187,deltaz*0.1148);
		head[1][1]= addBPoint(-deltax*0.75,-deltay*0.4187,deltaz*0.1148);
		head[1][2]= addBPoint(-deltax*0.5,-deltay*0.4187,deltaz*0.1148);
		head[1][3]= addBPoint(-deltax*0.25,-deltay*0.4187,deltaz*0.1148);
		head[1][4]= addBPoint(-deltax*0.0,-deltay*0.4187,deltaz*0.1148);
		head[1][5]= addBPoint(deltax*0.25,-deltay*0.4187,deltaz*0.1148);
		head[1][6]= addBPoint(deltax*0.5,-deltay*0.4187,deltaz*0.1148);
		head[1][7]= addBPoint(deltax*0.75,-deltay*0.4187,deltaz*0.1148);
		head[1][8]= addBPoint(deltax,-deltay*0.4187,deltaz*0.1148);
		head[1][9]= addBPoint(deltax,deltay*0.6396,deltaz*0.1148);
		head[1][10]= addBPoint(deltax*0.75,deltay*0.6396,deltaz*0.1148);
		head[1][11]= addBPoint(deltax*0.5,deltay*0.6396,deltaz*0.1148);
		head[1][12]= addBPoint(deltax*0.25,deltay*0.6396,deltaz*0.1148);
		head[1][13]= addBPoint(deltax*0.0,deltay*0.6396,deltaz*0.1148);
		head[1][14]= addBPoint(-deltax*0.25,deltay*0.6396,deltaz*0.1148);
		head[1][15]= addBPoint(-deltax*0.5,deltay*0.6396,deltaz*0.1148);
		head[1][16]= addBPoint(-deltax*0.75,deltay*0.6396,deltaz*0.1148);
		head[1][17]= addBPoint(-deltax,deltay*0.6396,deltaz*0.1148);

		head[2][0]= addBPoint(-deltax,-deltay*0.4187,deltaz*0.1803);
		head[2][1]= addBPoint(-deltax*0.75,-deltay*0.4187,deltaz*0.1803);
		head[2][2]= addBPoint(-deltax*0.5,-deltay*0.4187,deltaz*0.1803);
		head[2][3]= addBPoint(-deltax*0.25,-deltay*0.4187,deltaz*0.1803);
		head[2][4]= addBPoint(-deltax*0.0,-deltay*0.4187,deltaz*0.1803);
		head[2][5]= addBPoint(deltax*0.25,-deltay*0.4187,deltaz*0.1803);
		head[2][6]= addBPoint(deltax*0.5,-deltay*0.4187,deltaz*0.1803);
		head[2][7]= addBPoint(deltax*0.75,-deltay*0.4187,deltaz*0.1803);
		head[2][8]= addBPoint(deltax,-deltay*0.4187,deltaz*0.1803);
		head[2][9]= addBPoint(deltax,deltay*0.6396,deltaz*0.1803);
		head[2][10]= addBPoint(deltax*0.75,deltay*0.6396,deltaz*0.1803);
		head[2][11]= addBPoint(deltax*0.5,deltay*0.6396,deltaz*0.1803);
		head[2][12]= addBPoint(deltax*0.25,deltay*0.6396,deltaz*0.1803);
		head[2][13]= addBPoint(deltax*0.0,deltay*0.6396,deltaz*0.1803);
		head[2][14]= addBPoint(-deltax*0.25,deltay*0.6396,deltaz*0.1803);
		head[2][15]= addBPoint(-deltax*0.5,deltay*0.6396,deltaz*0.1803);
		head[2][16]= addBPoint(-deltax*0.75,deltay*0.6396,deltaz*0.1803);
		head[2][17]= addBPoint(-deltax,deltay*0.6396,deltaz*0.1803);

		head[3][0]= addBPoint(-deltax,-deltay*0.4651,deltaz*0.2787);
		head[3][1]= addBPoint(-deltax*0.75,-deltay*0.4651,deltaz*0.2787);
		head[3][2]= addBPoint(-deltax*0.5,-deltay*0.4651,deltaz*0.2787);
		head[3][3]= addBPoint(-deltax*0.25,-deltay*0.4651,deltaz*0.2787);
		head[3][4]= addBPoint(-deltax*0.0,-deltay*0.4651,deltaz*0.2787);
		head[3][5]= addBPoint(deltax*0.25,-deltay*0.4651,deltaz*0.2787);
		head[3][6]= addBPoint(deltax*0.5,-deltay*0.4651,deltaz*0.2787);
		head[3][7]= addBPoint(deltax*0.75,-deltay*0.4651,deltaz*0.2787);
		head[3][8]= addBPoint(deltax,-deltay*0.4651,deltaz*0.2787);
		head[3][9]= addBPoint(deltax,deltay*0.6861,deltaz*0.2787);
		head[3][10]= addBPoint(deltax*0.75,deltay*0.6861,deltaz*0.2787);
		head[3][11]= addBPoint(deltax*0.5,deltay*0.6861,deltaz*0.2787);
		head[3][12]= addBPoint(deltax*0.25,deltay*0.6861,deltaz*0.2787);
		head[3][13]= addBPoint(deltax*0.0,deltay*0.6861,deltaz*0.2787);
		head[3][14]= addBPoint(-deltax*0.25,deltay*0.6861,deltaz*0.2787);
		head[3][15]= addBPoint(-deltax*0.5,deltay*0.6861,deltaz*0.2787);
		head[3][16]= addBPoint(-deltax*0.75,deltay*0.6861,deltaz*0.2787);
		head[3][17]= addBPoint(-deltax,deltay*0.6861,deltaz*0.2787);

		head[4][0]= addBPoint(-deltax,-deltay*0.4884,deltaz*0.3279);
		head[4][1]= addBPoint(-deltax*0.75,-deltay*0.4884,deltaz*0.3279);
		head[4][2]= addBPoint(-deltax*0.5,-deltay*0.4884,deltaz*0.3279);
		head[4][3]= addBPoint(-deltax*0.25,-deltay*0.4884,deltaz*0.3279);
		head[4][4]= addBPoint(-deltax*0.0,-deltay*0.4884,deltaz*0.3279);
		head[4][5]= addBPoint(deltax*0.25,-deltay*0.4884,deltaz*0.3279);
		head[4][6]= addBPoint(deltax*0.5,-deltay*0.4884,deltaz*0.3279);
		head[4][7]= addBPoint(deltax*0.75,-deltay*0.4884,deltaz*0.3279);
		head[4][8]= addBPoint(deltax,-deltay*0.4884,deltaz*0.3279);
		head[4][9]= addBPoint(deltax,deltay*0.7791,deltaz*0.3279);
		head[4][10]= addBPoint(deltax*0.75,deltay*0.7791,deltaz*0.3279);
		head[4][11]= addBPoint(deltax*0.5,deltay*0.7791,deltaz*0.3279);
		head[4][12]= addBPoint(deltax*0.25,deltay*0.7791,deltaz*0.3279);
		head[4][13]= addBPoint(deltax*0.0,deltay*0.7791,deltaz*0.3279);
		head[4][14]= addBPoint(-deltax*0.25,deltay*0.7791,deltaz*0.3279);
		head[4][15]= addBPoint(-deltax*0.5,deltay*0.7791,deltaz*0.3279);
		head[4][16]= addBPoint(-deltax*0.75,deltay*0.7791,deltaz*0.3279);
		head[4][17]= addBPoint(-deltax,deltay*0.7791,deltaz*0.3279);

		head[5][0]= addBPoint(-deltax,-deltay*0.5698,deltaz*0.4754);
		head[5][1]= addBPoint(-deltax*0.75,-deltay*0.5698,deltaz*0.4754);
		head[5][2]= addBPoint(-deltax*0.5,-deltay*0.5698,deltaz*0.4754);
		head[5][3]= addBPoint(-deltax*0.25,-deltay*0.5698,deltaz*0.4754);
		head[5][4]= addBPoint(-deltax*0.0,-deltay*0.5698,deltaz*0.4754);
		head[5][5]= addBPoint(deltax*0.25,-deltay*0.5698,deltaz*0.4754);
		head[5][6]= addBPoint(deltax*0.5,-deltay*0.5698,deltaz*0.4754);
		head[5][7]= addBPoint(deltax*0.75,-deltay*0.5698,deltaz*0.4754);
		head[5][8]= addBPoint(deltax,-deltay*0.5698,deltaz*0.4754);
		head[5][9]= addBPoint(deltax,deltay*0.6861,deltaz*0.4754);
		head[5][10]= addBPoint(deltax*0.75,deltay*0.6861,deltaz*0.4754);
		head[5][11]= addBPoint(deltax*0.5,deltay*0.6861,deltaz*0.4754);
		head[5][12]= addBPoint(deltax*0.25,deltay*0.6861,deltaz*0.4754);
		head[5][13]= addBPoint(deltax*0.0,deltay*0.6861,deltaz*0.4754);
		head[5][14]= addBPoint(-deltax*0.25,deltay*0.6861,deltaz*0.4754);
		head[5][15]= addBPoint(-deltax*0.5,deltay*0.6861,deltaz*0.4754);
		head[5][16]= addBPoint(-deltax*0.75,deltay*0.6861,deltaz*0.4754);
		head[5][17]= addBPoint(-deltax,deltay*0.6861,deltaz*0.4754);

		head[6][0]= addBPoint(-deltax,-deltay*0.5814,deltaz*0.6066);
		head[6][1]= addBPoint(-deltax*0.75,-deltay*0.5814,deltaz*0.6066);
		head[6][2]= addBPoint(-deltax*0.5,-deltay*0.5814,deltaz*0.6066);
		head[6][3]= addBPoint(-deltax*0.25,-deltay*0.5814,deltaz*0.6066);
		head[6][4]= addBPoint(-deltax*0.0,-deltay*0.5814,deltaz*0.6066);
		head[6][5]= addBPoint(deltax*0.25,-deltay*0.5814,deltaz*0.6066);
		head[6][6]= addBPoint(deltax*0.5,-deltay*0.5814,deltaz*0.6066);
		head[6][7]= addBPoint(deltax*0.75,-deltay*0.5814,deltaz*0.6066);
		head[6][8]= addBPoint(deltax,-deltay*0.5814,deltaz*0.6066);
		head[6][9]= addBPoint(deltax,deltay*0.6628,deltaz*0.6066);
		head[6][10]= addBPoint(deltax*0.75,deltay*0.6628,deltaz*0.6066);
		head[6][11]= addBPoint(deltax*0.5,deltay*0.6628,deltaz*0.6066);
		head[6][12]= addBPoint(deltax*0.25,deltay*0.6628,deltaz*0.6066);
		head[6][13]= addBPoint(deltax*0.0,deltay*0.6628,deltaz*0.6066);
		head[6][14]= addBPoint(-deltax*0.25,deltay*0.6628,deltaz*0.6066);
		head[6][15]= addBPoint(-deltax*0.5,deltay*0.6628,deltaz*0.6066);
		head[6][16]= addBPoint(-deltax*0.75,deltay*0.6628,deltaz*0.6066);
		head[6][17]= addBPoint(-deltax,deltay*0.6628,deltaz*0.6066);

		head[7][0]= addBPoint(-deltax,-deltay*0.6047,deltaz*0.6885);
		head[7][1]= addBPoint(-deltax*0.75,-deltay*0.6047,deltaz*0.6885);
		head[7][2]= addBPoint(-deltax*0.5,-deltay*0.6047,deltaz*0.6885);
		head[7][3]= addBPoint(-deltax*0.25,-deltay*0.6047,deltaz*0.6885);
		head[7][4]= addBPoint(-deltax*0.0,-deltay*0.6047,deltaz*0.6885);
		head[7][5]= addBPoint(deltax*0.25,-deltay*0.6047,deltaz*0.6885);
		head[7][6]= addBPoint(deltax*0.5,-deltay*0.6047,deltaz*0.6885);
		head[7][7]= addBPoint(deltax*0.75,-deltay*0.6047,deltaz*0.6885);
		head[7][8]= addBPoint(deltax,-deltay*0.6047,deltaz*0.6885);
		head[7][9]= addBPoint(deltax,deltay*0.6163,deltaz*0.6885);
		head[7][10]= addBPoint(deltax*0.75,deltay*0.6163,deltaz*0.6885);
		head[7][11]= addBPoint(deltax*0.5,deltay*0.6163,deltaz*0.6885);
		head[7][12]= addBPoint(deltax*0.25,deltay*0.6163,deltaz*0.6885);
		head[7][13]= addBPoint(deltax*0.0,deltay*0.6163,deltaz*0.6885);
		head[7][14]= addBPoint(-deltax*0.25,deltay*0.6163,deltaz*0.6885);
		head[7][15]= addBPoint(-deltax*0.5,deltay*0.6163,deltaz*0.6885);
		head[7][16]= addBPoint(-deltax*0.75,deltay*0.6163,deltaz*0.6885);
		head[7][17]= addBPoint(-deltax,deltay*0.6163,deltaz*0.6885);

		head[8][0]= addBPoint(-deltax,-deltay*0.4684,deltaz*0.8525);
		head[8][1]= addBPoint(-deltax*0.75,-deltay*0.4684,deltaz*0.8525);
		head[8][2]= addBPoint(-deltax*0.5,-deltay*0.4684,deltaz*0.8525);
		head[8][3]= addBPoint(-deltax*0.25,-deltay*0.4684,deltaz*0.8525);
		head[8][4]= addBPoint(-deltax*0.0,-deltay*0.4684,deltaz*0.8525);
		head[8][5]= addBPoint(deltax*0.25,-deltay*0.4684,deltaz*0.8525);
		head[8][6]= addBPoint(deltax*0.5,-deltay*0.4684,deltaz*0.8525);
		head[8][7]= addBPoint(deltax*0.75,-deltay*0.4684,deltaz*0.8525);
		head[8][8]= addBPoint(deltax,-deltay*0.4684,deltaz*0.8525);
		head[8][9]= addBPoint(deltax,deltay*0.4768,deltaz*0.8525);
		head[8][10]= addBPoint(deltax*0.75,deltay*0.4768,deltaz*0.8525);
		head[8][11]= addBPoint(deltax*0.5,deltay*0.4768,deltaz*0.8525);
		head[8][12]= addBPoint(deltax*0.25,deltay*0.4768,deltaz*0.8525);
		head[8][13]= addBPoint(deltax*0.0,deltay*0.4768,deltaz*0.8525);
		head[8][14]= addBPoint(-deltax*0.25,deltay*0.4768,deltaz*0.8525);
		head[8][15]= addBPoint(-deltax*0.5,deltay*0.4768,deltaz*0.8525);
		head[8][16]= addBPoint(-deltax*0.75,deltay*0.4768,deltaz*0.8525);
		head[8][17]= addBPoint(-deltax,deltay*0.4768,deltaz*0.8525);

		head[9][0]= addBPoint(-deltax,-deltay*0.3605,deltaz*0.9344);
		head[9][1]= addBPoint(-deltax*0.75,-deltay*0.3605,deltaz*0.9344);
		head[9][2]= addBPoint(-deltax*0.5,-deltay*0.3605,deltaz*0.9344);
		head[9][3]= addBPoint(-deltax*0.25,-deltay*0.3605,deltaz*0.9344);
		head[9][4]= addBPoint(-deltax*0.0,-deltay*0.3605,deltaz*0.9344);
		head[9][5]= addBPoint(deltax*0.25,-deltay*0.3605,deltaz*0.9344);
		head[9][6]= addBPoint(deltax*0.5,-deltay*0.3605,deltaz*0.9344);
		head[9][7]= addBPoint(deltax*0.75,-deltay*0.3605,deltaz*0.9344);
		head[9][8]= addBPoint(deltax,-deltay*0.3605,deltaz*0.9344);
		head[9][9]= addBPoint(deltax,deltay*0.3721,deltaz*0.9344);
		head[9][10]= addBPoint(deltax*0.75,deltay*0.3721,deltaz*0.9344);
		head[9][11]= addBPoint(deltax*0.5,deltay*0.3721,deltaz*0.9344);
		head[9][12]= addBPoint(deltax*0.25,deltay*0.3721,deltaz*0.9344);
		head[9][13]= addBPoint(deltax*0.0,deltay*0.3721,deltaz*0.9344);
		head[9][14]= addBPoint(-deltax*0.25,deltay*0.3721,deltaz*0.9344);
		head[9][15]= addBPoint(-deltax*0.5,deltay*0.3721,deltaz*0.9344);
		head[9][16]= addBPoint(-deltax*0.75,deltay*0.3721,deltaz*0.9344);
		head[9][17]= addBPoint(-deltax,deltay*0.3721,deltaz*0.9344);

		head[10][0]= addBPoint(-deltax,-deltay*0,deltaz*1.0);
		head[10][1]= addBPoint(-deltax*0.75,-deltay*0,deltaz*1.0);
		head[10][2]= addBPoint(-deltax*0.5,-deltay*0,deltaz*1.0);
		head[10][3]= addBPoint(-deltax*0.25,-deltay*0,deltaz*1.0);
		head[10][4]= addBPoint(-deltax*0.0,-deltay*0,deltaz*1.0);
		head[10][5]= addBPoint(deltax*0.25,-deltay*0,deltaz*1.0);
		head[10][6]= addBPoint(deltax*0.5,-deltay*0,deltaz*1.0);
		head[10][7]= addBPoint(deltax*0.75,-deltay*0,deltaz*1.0);
		head[10][8]= addBPoint(deltax,-deltay*0,deltaz*1.0);
		head[10][9]= addBPoint(deltax,deltay*0,deltaz*1.0);
		head[10][10]= addBPoint(deltax*0.75,deltay*0,deltaz*1.0);
		head[10][11]= addBPoint(deltax*0.5,deltay*0,deltaz*1.0);
		head[10][12]= addBPoint(deltax*0.25,deltay*0,deltaz*1.0);
		head[10][13]= addBPoint(deltax*0.0,deltay*0,deltaz*1.0);
		head[10][14]= addBPoint(-deltax*0.25,deltay*0,deltaz*1.0);
		head[10][15]= addBPoint(-deltax*0.5,deltay*0,deltaz*1.0);
		head[10][16]= addBPoint(-deltax*0.75,deltay*0,deltaz*1.0);
		head[10][17]= addBPoint(-deltax,deltay*0,deltaz*1.0);


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


	/*public void writeNewCode(){

		String[] codes={
				"head[0][0]= addBPoint(-deltax,-deltay*0.4884,deltaz*0);",
				"head[0][1]= addBPoint(-deltax*0.5,-deltay*0.4884,deltaz*0);",
				"head[0][2]= addBPoint(-deltax*0.0,-deltay*0.4884,deltaz*0);",
				"head[0][3]= addBPoint(deltax*0.5,-deltay*0.4884,deltaz*0);",
				"head[0][4]= addBPoint(deltax,-deltay*0.4884,deltaz*0);",
				"head[0][5]= addBPoint(deltax,deltay*0.6163,deltaz*0);",
				"head[0][6]= addBPoint(deltax*0.5,deltay*0.6163,deltaz*0);",
				"head[0][7]= addBPoint(deltax*0.0,deltay*0.6163,deltaz*0);",
				"head[0][8]= addBPoint(-deltax*0.5,deltay*0.6163,deltaz*0);",
				"head[0][9]= addBPoint(-deltax,deltay*0.6163,deltaz*0);",

				"head[1][0]= addBPoint(-deltax,-deltay*0.4187,deltaz*0.1148);",
				"head[1][1]= addBPoint(-deltax*0.5,-deltay*0.4187,deltaz*0.1148);",
				"head[1][2]= addBPoint(-deltax*0.0,-deltay*0.4187,deltaz*0.1148);",
				"head[1][3]= addBPoint(deltax*0.5,-deltay*0.4187,deltaz*0.1148);",
				"head[1][4]= addBPoint(deltax,-deltay*0.4187,deltaz*0.1148);",
				"head[1][5]= addBPoint(deltax,deltay*0.6396,deltaz*0.1148);",
				"head[1][6]= addBPoint(deltax*0.5,deltay*0.6396,deltaz*0.1148);",
				"head[1][7]= addBPoint(deltax*0.0,deltay*0.6396,deltaz*0.1148);",
				"head[1][8]= addBPoint(-deltax*0.5,deltay*0.6396,deltaz*0.1148);",
				"head[1][9]= addBPoint(-deltax,deltay*0.6396,deltaz*0.1148);",

				"head[2][0]= addBPoint(-deltax,-deltay*0.4187,deltaz*0.1803);",
				"head[2][1]= addBPoint(-deltax*0.5,-deltay*0.4187,deltaz*0.1803);",
				"head[2][2]= addBPoint(-deltax*0.0,-deltay*0.4187,deltaz*0.1803);",
				"head[2][3]= addBPoint(deltax*0.5,-deltay*0.4187,deltaz*0.1803);",
				"head[2][4]= addBPoint(deltax,-deltay*0.4187,deltaz*0.1803);",
				"head[2][5]= addBPoint(deltax,deltay*0.6396,deltaz*0.1803);",
				"head[2][6]= addBPoint(deltax*0.5,deltay*0.6396,deltaz*0.1803);",
				"head[2][7]= addBPoint(deltax*0.0,deltay*0.6396,deltaz*0.1803);",
				"head[2][8]= addBPoint(-deltax*0.5,deltay*0.6396,deltaz*0.1803);",
				"head[2][9]= addBPoint(-deltax,deltay*0.6396,deltaz*0.1803);",

				"head[3][0]= addBPoint(-deltax,-deltay*0.4651,deltaz*0.2787);",
				"head[3][1]= addBPoint(-deltax*0.5,-deltay*0.4651,deltaz*0.2787);",
				"head[3][2]= addBPoint(-deltax*0.0,-deltay*0.4651,deltaz*0.2787);",
				"head[3][3]= addBPoint(deltax*0.5,-deltay*0.4651,deltaz*0.2787);",
				"head[3][4]= addBPoint(deltax,-deltay*0.4651,deltaz*0.2787);",
				"head[3][5]= addBPoint(deltax,deltay*0.6861,deltaz*0.2787);",
				"head[3][6]= addBPoint(deltax*0.5,deltay*0.6861,deltaz*0.2787);",
				"head[3][7]= addBPoint(deltax*0.0,deltay*0.6861,deltaz*0.2787);",
				"head[3][8]= addBPoint(-deltax*0.5,deltay*0.6861,deltaz*0.2787);",
				"head[3][9]= addBPoint(-deltax,deltay*0.6861,deltaz*0.2787);",

				"head[4][0]= addBPoint(-deltax,-deltay*0.4884,deltaz*0.3279);",
				"head[4][1]= addBPoint(-deltax*0.5,-deltay*0.4884,deltaz*0.3279);",
				"head[4][2]= addBPoint(-deltax*0.0,-deltay*0.4884,deltaz*0.3279);",
				"head[4][3]= addBPoint(deltax*0.5,-deltay*0.4884,deltaz*0.3279);",
				"head[4][4]= addBPoint(deltax,-deltay*0.4884,deltaz*0.3279);",
				"head[4][5]= addBPoint(deltax,deltay*0.7791,deltaz*0.3279);",
				"head[4][6]= addBPoint(deltax*0.5,deltay*0.7791,deltaz*0.3279);",
				"head[4][7]= addBPoint(deltax*0.0,deltay*0.7791,deltaz*0.3279);",
				"head[4][8]= addBPoint(-deltax*0.5,deltay*0.7791,deltaz*0.3279);",
				"head[4][9]= addBPoint(-deltax,deltay*0.7791,deltaz*0.3279);",

				"head[5][0]= addBPoint(-deltax,-deltay*0.5698,deltaz*0.4754);",
				"head[5][1]= addBPoint(-deltax*0.5,-deltay*0.5698,deltaz*0.4754);",
				"head[5][2]= addBPoint(-deltax*0.0,-deltay*0.5698,deltaz*0.4754);",
				"head[5][3]= addBPoint(deltax*0.5,-deltay*0.5698,deltaz*0.4754);",
				"head[5][4]= addBPoint(deltax,-deltay*0.5698,deltaz*0.4754);",
				"head[5][5]= addBPoint(deltax,deltay*0.6861,deltaz*0.4754);",
				"head[5][6]= addBPoint(deltax*0.5,deltay*0.6861,deltaz*0.4754);",
				"head[5][7]= addBPoint(deltax*0.0,deltay*0.6861,deltaz*0.4754);",
				"head[5][8]= addBPoint(-deltax*0.5,deltay*0.6861,deltaz*0.4754);",
				"head[5][9]= addBPoint(-deltax,deltay*0.6861,deltaz*0.4754);",

				"head[6][0]= addBPoint(-deltax,-deltay*0.5814,deltaz*0.6066);",
				"head[6][1]= addBPoint(-deltax*0.5,-deltay*0.5814,deltaz*0.6066);",
				"head[6][2]= addBPoint(-deltax*0.0,-deltay*0.5814,deltaz*0.6066);",
				"head[6][3]= addBPoint(deltax*0.5,-deltay*0.5814,deltaz*0.6066);",
				"head[6][4]= addBPoint(deltax,-deltay*0.5814,deltaz*0.6066);",
				"head[6][5]= addBPoint(deltax,deltay*0.6628,deltaz*0.6066);",
				"head[6][6]= addBPoint(deltax*0.5,deltay*0.6628,deltaz*0.6066);",
				"head[6][7]= addBPoint(deltax*0.0,deltay*0.6628,deltaz*0.6066);",
				"head[6][8]= addBPoint(-deltax*0.5,deltay*0.6628,deltaz*0.6066);",
				"head[6][9]= addBPoint(-deltax,deltay*0.6628,deltaz*0.6066);",

				"head[7][0]= addBPoint(-deltax,-deltay*0.6047,deltaz*0.6885);",
				"head[7][1]= addBPoint(-deltax*0.5,-deltay*0.6047,deltaz*0.6885);",
				"head[7][2]= addBPoint(-deltax*0.0,-deltay*0.6047,deltaz*0.6885);",
				"head[7][3]= addBPoint(deltax*0.5,-deltay*0.6047,deltaz*0.6885);",
				"head[7][4]= addBPoint(deltax,-deltay*0.6047,deltaz*0.6885);",
				"head[7][5]= addBPoint(deltax,deltay*0.6163,deltaz*0.6885);",
				"head[7][6]= addBPoint(deltax*0.5,deltay*0.6163,deltaz*0.6885);",
				"head[7][7]= addBPoint(deltax*0.0,deltay*0.6163,deltaz*0.6885);",
				"head[7][8]= addBPoint(-deltax*0.5,deltay*0.6163,deltaz*0.6885);",
				"head[7][9]= addBPoint(-deltax,deltay*0.6163,deltaz*0.6885);",

				"head[8][0]= addBPoint(-deltax,-deltay*0.4684,deltaz*0.8525);",
				"head[8][1]= addBPoint(-deltax*0.5,-deltay*0.4684,deltaz*0.8525);",
				"head[8][2]= addBPoint(-deltax*0.0,-deltay*0.4684,deltaz*0.8525);",
				"head[8][3]= addBPoint(deltax*0.5,-deltay*0.4684,deltaz*0.8525);",
				"head[8][4]= addBPoint(deltax,-deltay*0.4684,deltaz*0.8525);",
				"head[8][5]= addBPoint(deltax,deltay*0.4768,deltaz*0.8525);",
				"head[8][6]= addBPoint(deltax*0.5,deltay*0.4768,deltaz*0.8525);",
				"head[8][7]= addBPoint(deltax*0.0,deltay*0.4768,deltaz*0.8525);",
				"head[8][8]= addBPoint(-deltax*0.5,deltay*0.4768,deltaz*0.8525);",
				"head[8][9]= addBPoint(-deltax,deltay*0.4768,deltaz*0.8525);",

				"head[9][0]= addBPoint(-deltax,-deltay*0.3605,deltaz*0.9344);",
				"head[9][1]= addBPoint(-deltax*0.5,-deltay*0.3605,deltaz*0.9344);",
				"head[9][2]= addBPoint(-deltax*0.0,-deltay*0.3605,deltaz*0.9344);",
				"head[9][3]= addBPoint(deltax*0.5,-deltay*0.3605,deltaz*0.9344);",
				"head[9][4]= addBPoint(deltax,-deltay*0.3605,deltaz*0.9344);",
				"head[9][5]= addBPoint(deltax,deltay*0.3721,deltaz*0.9344);",
				"head[9][6]= addBPoint(deltax*0.5,deltay*0.3721,deltaz*0.9344);",
				"head[9][7]= addBPoint(deltax*0.0,deltay*0.3721,deltaz*0.9344);",
				"head[9][8]= addBPoint(-deltax*0.5,deltay*0.3721,deltaz*0.9344);",
				"head[9][9]= addBPoint(-deltax,deltay*0.3721,deltaz*0.9344);",

				"head[10][0]= addBPoint(-deltax,-deltay*0,deltaz*1.0);",
				"head[10][1]= addBPoint(-deltax*0.5,-deltay*0,deltaz*1.0);",
				"head[10][2]= addBPoint(-deltax*0.0,-deltay*0,deltaz*1.0);",
				"head[10][3]= addBPoint(deltax*0.5,-deltay*0,deltaz*1.0);",
				"head[10][4]= addBPoint(deltax,-deltay*0,deltaz*1.0);",
				"head[10][5]= addBPoint(deltax,deltay*0,deltaz*1.0);",
				"head[10][6]= addBPoint(deltax*0.5,deltay*0,deltaz*1.0);",
				"head[10][7]= addBPoint(deltax*0.0,deltay*0,deltaz*1.0);",
				"head[10][8]= addBPoint(-deltax*0.5,deltay*0,deltaz*1.0);",
				"head[10][9]= addBPoint(-deltax,deltay*0,deltaz*1.0);",
		};

		for (int i = 0; i < codes.length; i++) {

			int[] rep={0,2,4,6,8,9,11,13,15,17};	
			if(i%10==0)
				System.out.println();

			System.out.println(codes[i].replaceAll("\\["+(i%10)+"\\]=","["+rep[(i%10)]+"]="));


			if(i%10==0 || i%10==1 || i%10==2 || i%10==3 || i%10==5 || i%10==6 || i%10==7 || i%10==8)
				System.out.println(codes[i]);


			/*	
			System.out.println(codes[i].replaceAll("\\["+(i%6)+"\\]=","["+rep[(i%6)]+"]="));
			if(i%6==0)
				System.out.println(codes[i].replaceAll("-deltax", "-deltax*0.5").replaceAll("\\[0\\]=","[1]="));
			else if(i%6==1)
				System.out.println(codes[i].replaceAll("-deltax\\*0.0", "deltax*0.5").replaceAll("\\[1\\]=","[3]="));
			else if(i%6==3)
				System.out.println(codes[i].replaceAll("deltax", "deltax*0.5").replaceAll("\\[3\\]=","[6]="));
			else if(i%6==4)
				System.out.println(codes[i].replaceAll("deltax\\*0.0", "-deltax*0.5").replaceAll("\\[4\\]=","[8]="));
			
		}
	}*/


}
