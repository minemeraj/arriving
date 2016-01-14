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

		int xNumSections=35;  
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


		BPoint[][] head=new BPoint[21][34];

		double deltax=dx*0.5;
		double deltay=dy;
		double deltaz=dz;



		head[0][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.0);
		head[0][1]= addBPoint(-deltax*0.875,-deltay*0.16151388,deltaz*0.0);
		head[0][2]= addBPoint(-deltax*0.75,-deltay*0.32302776,deltaz*0.0);
		head[0][3]= addBPoint(-deltax*0.625,-deltay*0.37299108000000003,deltaz*0.0);
		head[0][4]= addBPoint(-deltax*0.5,-deltay*0.4229544,deltaz*0.0);
		head[0][5]= addBPoint(-deltax*0.375,-deltay*0.44791164,deltaz*0.0);
		head[0][6]= addBPoint(-deltax*0.25,-deltay*0.47286888,deltaz*0.0);
		head[0][7]= addBPoint(-deltax*0.125,-deltay*0.48063444,deltaz*0.0);
		head[0][8]= addBPoint(-deltax*0.0,-deltay*0.4884,deltaz*0.0);
		head[0][9]= addBPoint(deltax*0.125,-deltay*0.48063444,deltaz*0.0);
		head[0][10]= addBPoint(deltax*0.25,-deltay*0.47286888,deltaz*0.0);
		head[0][11]= addBPoint(deltax*0.375,-deltay*0.44791164,deltaz*0.0);
		head[0][12]= addBPoint(deltax*0.5,-deltay*0.4229544,deltaz*0.0);
		head[0][13]= addBPoint(deltax*0.625,-deltay*0.37299108000000003,deltaz*0.0);
		head[0][14]= addBPoint(deltax*0.75,-deltay*0.32302776,deltaz*0.0);
		head[0][15]= addBPoint(deltax*0.875,-deltay*0.16151388,deltaz*0.0);
		head[0][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.0);
		head[0][17]= addBPoint(deltax*1.0,deltay*0.5337158,deltaz*0.0);
		head[0][18]= addBPoint(deltax*0.875,deltay*0.55251295,deltaz*0.0);
		head[0][19]= addBPoint(deltax*0.75,deltay*0.5713101,deltaz*0.0);
		head[0][20]= addBPoint(deltax*0.625,deltay*0.58400588,deltaz*0.0);
		head[0][21]= addBPoint(deltax*0.5,deltay*0.5967016599999999,deltaz*0.0);
		head[0][22]= addBPoint(deltax*0.375,deltay*0.6040972599999999,deltaz*0.0);
		head[0][23]= addBPoint(deltax*0.25,deltay*0.6114928599999999,deltaz*0.0);
		head[0][24]= addBPoint(deltax*0.125,deltay*0.6138964299999999,deltaz*0.0);
		head[0][25]= addBPoint(deltax*0.0,deltay*0.6163,deltaz*0.0);
		head[0][26]= addBPoint(-deltax*0.125,deltay*0.6138964299999999,deltaz*0.0);
		head[0][27]= addBPoint(-deltax*0.25,deltay*0.6114928599999999,deltaz*0.0);
		head[0][28]= addBPoint(-deltax*0.375,deltay*0.6040972599999999,deltaz*0.0);
		head[0][29]= addBPoint(-deltax*0.5,deltay*0.5967016599999999,deltaz*0.0);
		head[0][30]= addBPoint(-deltax*0.625,deltay*0.58400588,deltaz*0.0);
		head[0][31]= addBPoint(-deltax*0.75,deltay*0.5713101,deltaz*0.0);
		head[0][32]= addBPoint(-deltax*0.875,deltay*0.55251295,deltaz*0.0);
		head[0][33]= addBPoint(-deltax*1.0,deltay*0.5337158,deltaz*0.0);

		head[1][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.1148);
		head[1][1]= addBPoint(-deltax*0.875,-deltay*0.13846409,deltaz*0.1148);
		head[1][2]= addBPoint(-deltax*0.75,-deltay*0.27692818,deltaz*0.1148);
		head[1][3]= addBPoint(-deltax*0.625,-deltay*0.31976119000000003,deltaz*0.1148);
		head[1][4]= addBPoint(-deltax*0.5,-deltay*0.36259420000000003,deltaz*0.1148);
		head[1][5]= addBPoint(-deltax*0.375,-deltay*0.38398977,deltaz*0.1148);
		head[1][6]= addBPoint(-deltax*0.25,-deltay*0.40538534,deltaz*0.1148);
		head[1][7]= addBPoint(-deltax*0.125,-deltay*0.41204267,deltaz*0.1148);
		head[1][8]= addBPoint(-deltax*0.0,-deltay*0.4187,deltaz*0.1148);
		head[1][9]= addBPoint(deltax*0.125,-deltay*0.41204267,deltaz*0.1148);
		head[1][10]= addBPoint(deltax*0.25,-deltay*0.40538534,deltaz*0.1148);
		head[1][11]= addBPoint(deltax*0.375,-deltay*0.38398977,deltaz*0.1148);
		head[1][12]= addBPoint(deltax*0.5,-deltay*0.36259420000000003,deltaz*0.1148);
		head[1][13]= addBPoint(deltax*0.625,-deltay*0.31976119000000003,deltaz*0.1148);
		head[1][14]= addBPoint(deltax*0.75,-deltay*0.27692818,deltaz*0.1148);
		head[1][15]= addBPoint(deltax*0.875,-deltay*0.13846409,deltaz*0.1148);
		head[1][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.1148);
		head[1][17]= addBPoint(deltax*1.0,deltay*0.5538936,deltaz*0.1148);
		head[1][18]= addBPoint(deltax*0.875,deltay*0.5734014000000001,deltaz*0.1148);
		head[1][19]= addBPoint(deltax*0.75,deltay*0.5929092,deltaz*0.1148);
		head[1][20]= addBPoint(deltax*0.625,deltay*0.60608496,deltaz*0.1148);
		head[1][21]= addBPoint(deltax*0.5,deltay*0.6192607199999999,deltaz*0.1148);
		head[1][22]= addBPoint(deltax*0.375,deltay*0.62693592,deltaz*0.1148);
		head[1][23]= addBPoint(deltax*0.25,deltay*0.63461112,deltaz*0.1148);
		head[1][24]= addBPoint(deltax*0.125,deltay*0.63710556,deltaz*0.1148);
		head[1][25]= addBPoint(deltax*0.0,deltay*0.6396,deltaz*0.1148);
		head[1][26]= addBPoint(-deltax*0.125,deltay*0.63710556,deltaz*0.1148);
		head[1][27]= addBPoint(-deltax*0.25,deltay*0.63461112,deltaz*0.1148);
		head[1][28]= addBPoint(-deltax*0.375,deltay*0.62693592,deltaz*0.1148);
		head[1][29]= addBPoint(-deltax*0.5,deltay*0.6192607199999999,deltaz*0.1148);
		head[1][30]= addBPoint(-deltax*0.625,deltay*0.60608496,deltaz*0.1148);
		head[1][31]= addBPoint(-deltax*0.75,deltay*0.5929092,deltaz*0.1148);
		head[1][32]= addBPoint(-deltax*0.875,deltay*0.5734014000000001,deltaz*0.1148);
		head[1][33]= addBPoint(-deltax*1.0,deltay*0.5538936,deltaz*0.1148);

		head[2][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.1803);
		head[2][1]= addBPoint(-deltax*0.875,-deltay*0.13846409,deltaz*0.1803);
		head[2][2]= addBPoint(-deltax*0.75,-deltay*0.27692818,deltaz*0.1803);
		head[2][3]= addBPoint(-deltax*0.625,-deltay*0.31976119000000003,deltaz*0.1803);
		head[2][4]= addBPoint(-deltax*0.5,-deltay*0.36259420000000003,deltaz*0.1803);
		head[2][5]= addBPoint(-deltax*0.375,-deltay*0.38398977,deltaz*0.1803);
		head[2][6]= addBPoint(-deltax*0.25,-deltay*0.40538534,deltaz*0.1803);
		head[2][7]= addBPoint(-deltax*0.125,-deltay*0.41204267,deltaz*0.1803);
		head[2][8]= addBPoint(-deltax*0.0,-deltay*0.4187,deltaz*0.1803);
		head[2][9]= addBPoint(deltax*0.125,-deltay*0.41204267,deltaz*0.1803);
		head[2][10]= addBPoint(deltax*0.25,-deltay*0.40538534,deltaz*0.1803);
		head[2][11]= addBPoint(deltax*0.375,-deltay*0.38398977,deltaz*0.1803);
		head[2][12]= addBPoint(deltax*0.5,-deltay*0.36259420000000003,deltaz*0.1803);
		head[2][13]= addBPoint(deltax*0.625,-deltay*0.31976119000000003,deltaz*0.1803);
		head[2][14]= addBPoint(deltax*0.75,-deltay*0.27692818,deltaz*0.1803);
		head[2][15]= addBPoint(deltax*0.875,-deltay*0.13846409,deltaz*0.1803);
		head[2][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.1803);
		head[2][17]= addBPoint(deltax*1.0,deltay*0.5538936,deltaz*0.1803);
		head[2][18]= addBPoint(deltax*0.875,deltay*0.5734014000000001,deltaz*0.1803);
		head[2][19]= addBPoint(deltax*0.75,deltay*0.5929092,deltaz*0.1803);
		head[2][20]= addBPoint(deltax*0.625,deltay*0.60608496,deltaz*0.1803);
		head[2][21]= addBPoint(deltax*0.5,deltay*0.6192607199999999,deltaz*0.1803);
		head[2][22]= addBPoint(deltax*0.375,deltay*0.62693592,deltaz*0.1803);
		head[2][23]= addBPoint(deltax*0.25,deltay*0.63461112,deltaz*0.1803);
		head[2][24]= addBPoint(deltax*0.125,deltay*0.63710556,deltaz*0.1803);
		head[2][25]= addBPoint(deltax*0.0,deltay*0.6396,deltaz*0.1803);
		head[2][26]= addBPoint(-deltax*0.125,deltay*0.63710556,deltaz*0.1803);
		head[2][27]= addBPoint(-deltax*0.25,deltay*0.63461112,deltaz*0.1803);
		head[2][28]= addBPoint(-deltax*0.375,deltay*0.62693592,deltaz*0.1803);
		head[2][29]= addBPoint(-deltax*0.5,deltay*0.6192607199999999,deltaz*0.1803);
		head[2][30]= addBPoint(-deltax*0.625,deltay*0.60608496,deltaz*0.1803);
		head[2][31]= addBPoint(-deltax*0.75,deltay*0.5929092,deltaz*0.1803);
		head[2][32]= addBPoint(-deltax*0.875,deltay*0.5734014000000001,deltaz*0.1803);
		head[2][33]= addBPoint(-deltax*1.0,deltay*0.5538936,deltaz*0.1803);

		head[3][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.2787);
		head[3][1]= addBPoint(-deltax*0.875,-deltay*0.15380857,deltaz*0.2787);
		head[3][2]= addBPoint(-deltax*0.75,-deltay*0.30761714,deltaz*0.2787);
		head[3][3]= addBPoint(-deltax*0.625,-deltay*0.35519687,deltaz*0.2787);
		head[3][4]= addBPoint(-deltax*0.5,-deltay*0.4027766,deltaz*0.2787);
		head[3][5]= addBPoint(-deltax*0.375,-deltay*0.42654320999999995,deltaz*0.2787);
		head[3][6]= addBPoint(-deltax*0.25,-deltay*0.45030981999999997,deltaz*0.2787);
		head[3][7]= addBPoint(-deltax*0.125,-deltay*0.45770491,deltaz*0.2787);
		head[3][8]= addBPoint(-deltax*0.0,-deltay*0.4651,deltaz*0.2787);
		head[3][9]= addBPoint(deltax*0.125,-deltay*0.45770491,deltaz*0.2787);
		head[3][10]= addBPoint(deltax*0.25,-deltay*0.45030981999999997,deltaz*0.2787);
		head[3][11]= addBPoint(deltax*0.375,-deltay*0.426543215,deltaz*0.2787);
		head[3][12]= addBPoint(deltax*0.5,-deltay*0.40277661,deltaz*0.2787);
		head[3][13]= addBPoint(deltax*0.625,-deltay*0.35519687499999997,deltaz*0.2787);
		head[3][14]= addBPoint(deltax*0.75,-deltay*0.30761714,deltaz*0.2787);
		head[3][15]= addBPoint(deltax*0.875,-deltay*0.15380857,deltaz*0.2787);
		head[3][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.2787);
		head[3][17]= addBPoint(deltax*1.0,deltay*0.5941626,deltaz*0.2787);
		head[3][18]= addBPoint(deltax*0.875,deltay*0.61508865,deltaz*0.2787);
		head[3][19]= addBPoint(deltax*0.75,deltay*0.6360147,deltaz*0.2787);
		head[3][20]= addBPoint(deltax*0.625,deltay*0.65014836,deltaz*0.2787);
		head[3][21]= addBPoint(deltax*0.5,deltay*0.66428202,deltaz*0.2787);
		head[3][22]= addBPoint(deltax*0.375,deltay*0.67251522,deltaz*0.2787);
		head[3][23]= addBPoint(deltax*0.25,deltay*0.68074842,deltaz*0.2787);
		head[3][24]= addBPoint(deltax*0.125,deltay*0.6834242100000001,deltaz*0.2787);
		head[3][25]= addBPoint(deltax*0.0,deltay*0.6861,deltaz*0.2787);
		head[3][26]= addBPoint(-deltax*0.125,deltay*0.6834242100000001,deltaz*0.2787);
		head[3][27]= addBPoint(-deltax*0.25,deltay*0.68074842,deltaz*0.2787);
		head[3][28]= addBPoint(-deltax*0.375,deltay*0.67251522,deltaz*0.2787);
		head[3][29]= addBPoint(-deltax*0.5,deltay*0.66428202,deltaz*0.2787);
		head[3][30]= addBPoint(-deltax*0.625,deltay*0.65014836,deltaz*0.2787);
		head[3][31]= addBPoint(-deltax*0.75,deltay*0.6360147,deltaz*0.2787);
		head[3][32]= addBPoint(-deltax*0.875,deltay*0.61508865,deltaz*0.2787);
		head[3][33]= addBPoint(-deltax*1.0,deltay*0.5941626,deltaz*0.2787);

		head[4][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.3279);
		head[4][1]= addBPoint(-deltax*0.875,-deltay*0.16151388,deltaz*0.3279);
		head[4][2]= addBPoint(-deltax*0.75,-deltay*0.32302776,deltaz*0.3279);
		head[4][3]= addBPoint(-deltax*0.625,-deltay*0.37299108000000003,deltaz*0.3279);
		head[4][4]= addBPoint(-deltax*0.5,-deltay*0.4229544,deltaz*0.3279);
		head[4][5]= addBPoint(-deltax*0.375,-deltay*0.44791164,deltaz*0.3279);
		head[4][6]= addBPoint(-deltax*0.25,-deltay*0.47286888,deltaz*0.3279);
		head[4][7]= addBPoint(-deltax*0.125,-deltay*0.48063444,deltaz*0.3279);
		head[4][8]= addBPoint(-deltax*0.0,-deltay*0.4884,deltaz*0.3279);
		head[4][9]= addBPoint(deltax*0.125,-deltay*0.48063444,deltaz*0.3279);
		head[4][10]= addBPoint(deltax*0.25,-deltay*0.47286888,deltaz*0.3279);
		head[4][11]= addBPoint(deltax*0.375,-deltay*0.44791164,deltaz*0.3279);
		head[4][12]= addBPoint(deltax*0.5,-deltay*0.4229544,deltaz*0.3279);
		head[4][13]= addBPoint(deltax*0.625,-deltay*0.37299108000000003,deltaz*0.3279);
		head[4][14]= addBPoint(deltax*0.75,-deltay*0.32302776,deltaz*0.3279);
		head[4][15]= addBPoint(deltax*0.875,-deltay*0.16151388,deltaz*0.3279);
		head[4][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.3279);
		head[4][17]= addBPoint(deltax*1.0,deltay*0.6747006,deltaz*0.3279);
		head[4][18]= addBPoint(deltax*0.875,deltay*0.69846315,deltaz*0.3279);
		head[4][19]= addBPoint(deltax*0.75,deltay*0.7222257000000001,deltaz*0.3279);
		head[4][20]= addBPoint(deltax*0.625,deltay*0.73827516,deltaz*0.3279);
		head[4][21]= addBPoint(deltax*0.5,deltay*0.75432462,deltaz*0.3279);
		head[4][22]= addBPoint(deltax*0.375,deltay*0.76367382,deltaz*0.3279);
		head[4][23]= addBPoint(deltax*0.25,deltay*0.77302302,deltaz*0.3279);
		head[4][24]= addBPoint(deltax*0.125,deltay*0.77606151,deltaz*0.3279);
		head[4][25]= addBPoint(deltax*0.0,deltay*0.7791,deltaz*0.3279);
		head[4][26]= addBPoint(-deltax*0.125,deltay*0.77606151,deltaz*0.3279);
		head[4][27]= addBPoint(-deltax*0.25,deltay*0.77302302,deltaz*0.3279);
		head[4][28]= addBPoint(-deltax*0.375,deltay*0.76367382,deltaz*0.3279);
		head[4][29]= addBPoint(-deltax*0.5,deltay*0.75432462,deltaz*0.3279);
		head[4][30]= addBPoint(-deltax*0.625,deltay*0.73827516,deltaz*0.3279);
		head[4][31]= addBPoint(-deltax*0.75,deltay*0.7222257000000001,deltaz*0.3279);
		head[4][32]= addBPoint(-deltax*0.875,deltay*0.69846315,deltaz*0.3279);
		head[4][33]= addBPoint(-deltax*1.0,deltay*0.6747006,deltaz*0.3279);

		head[5][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.4754);
		head[5][1]= addBPoint(-deltax*0.875,-deltay*0.18843285999999998,deltaz*0.4754);
		head[5][2]= addBPoint(-deltax*0.75,-deltay*0.37686571999999996,deltaz*0.4754);
		head[5][3]= addBPoint(-deltax*0.625,-deltay*0.43515625999999996,deltaz*0.4754);
		head[5][4]= addBPoint(-deltax*0.5,-deltay*0.49344679999999996,deltaz*0.4754);
		head[5][5]= addBPoint(-deltax*0.375,-deltay*0.5225635799999999,deltaz*0.4754);
		head[5][6]= addBPoint(-deltax*0.25,-deltay*0.55168036,deltaz*0.4754);
		head[5][7]= addBPoint(-deltax*0.125,-deltay*0.56074018,deltaz*0.4754);
		head[5][8]= addBPoint(-deltax*0.0,-deltay*0.5698,deltaz*0.4754);
		head[5][9]= addBPoint(deltax*0.125,-deltay*0.56074018,deltaz*0.4754);
		head[5][10]= addBPoint(deltax*0.25,-deltay*0.55168036,deltaz*0.4754);
		head[5][11]= addBPoint(deltax*0.375,-deltay*0.5225635799999999,deltaz*0.4754);
		head[5][12]= addBPoint(deltax*0.5,-deltay*0.49344679999999996,deltaz*0.4754);
		head[5][13]= addBPoint(deltax*0.625,-deltay*0.43515625999999996,deltaz*0.4754);
		head[5][14]= addBPoint(deltax*0.75,-deltay*0.37686571999999996,deltaz*0.4754);
		head[5][15]= addBPoint(deltax*0.875,-deltay*0.18843285999999998,deltaz*0.4754);
		head[5][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.4754);
		head[5][17]= addBPoint(deltax*1.0,deltay*0.5941626,deltaz*0.4754);
		head[5][18]= addBPoint(deltax*0.875,deltay*0.61508865,deltaz*0.4754);
		head[5][19]= addBPoint(deltax*0.75,deltay*0.6360147,deltaz*0.4754);
		head[5][20]= addBPoint(deltax*0.625,deltay*0.65014836,deltaz*0.4754);
		head[5][21]= addBPoint(deltax*0.5,deltay*0.66428202,deltaz*0.4754);
		head[5][22]= addBPoint(deltax*0.375,deltay*0.67251522,deltaz*0.4754);
		head[5][23]= addBPoint(deltax*0.25,deltay*0.68074842,deltaz*0.4754);
		head[5][24]= addBPoint(deltax*0.125,deltay*0.6834242100000001,deltaz*0.4754);
		head[5][25]= addBPoint(deltax*0.0,deltay*0.6861,deltaz*0.4754);
		head[5][26]= addBPoint(-deltax*0.125,deltay*0.6834242100000001,deltaz*0.4754);
		head[5][27]= addBPoint(-deltax*0.25,deltay*0.68074842,deltaz*0.4754);
		head[5][28]= addBPoint(-deltax*0.375,deltay*0.67251522,deltaz*0.4754);
		head[5][29]= addBPoint(-deltax*0.5,deltay*0.66428202,deltaz*0.4754);
		head[5][30]= addBPoint(-deltax*0.625,deltay*0.65014836,deltaz*0.4754);
		head[5][31]= addBPoint(-deltax*0.75,deltay*0.6360147,deltaz*0.4754);
		head[5][32]= addBPoint(-deltax*0.875,deltay*0.61508865,deltaz*0.4754);
		head[5][33]= addBPoint(-deltax*1.0,deltay*0.5941626,deltaz*0.4754);

		head[6][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.6066);
		head[6][1]= addBPoint(-deltax*0.875,-deltay*0.19226898,deltaz*0.6066);
		head[6][2]= addBPoint(-deltax*0.75,-deltay*0.38453796,deltaz*0.6066);
		head[6][3]= addBPoint(-deltax*0.625,-deltay*0.44401518000000006,deltaz*0.6066);
		head[6][4]= addBPoint(-deltax*0.5,-deltay*0.5034924000000001,deltaz*0.6066);
		head[6][5]= addBPoint(-deltax*0.375,-deltay*0.5332019400000001,deltaz*0.6066);
		head[6][6]= addBPoint(-deltax*0.25,-deltay*0.56291148,deltaz*0.6066);
		head[6][7]= addBPoint(-deltax*0.125,-deltay*0.57215574,deltaz*0.6066);
		head[6][8]= addBPoint(-deltax*0.0,-deltay*0.5814,deltaz*0.6066);
		head[6][9]= addBPoint(deltax*0.125,-deltay*0.57215574,deltaz*0.6066);
		head[6][10]= addBPoint(deltax*0.25,-deltay*0.56291148,deltaz*0.6066);
		head[6][11]= addBPoint(deltax*0.375,-deltay*0.5332019400000001,deltaz*0.6066);
		head[6][12]= addBPoint(deltax*0.5,-deltay*0.5034924000000001,deltaz*0.6066);
		head[6][13]= addBPoint(deltax*0.625,-deltay*0.44401518000000006,deltaz*0.6066);
		head[6][14]= addBPoint(deltax*0.75,-deltay*0.38453796,deltaz*0.6066);
		head[6][15]= addBPoint(deltax*0.875,-deltay*0.19226898,deltaz*0.6066);
		head[6][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.6066);
		head[6][17]= addBPoint(deltax*1.0,deltay*0.5739848,deltaz*0.6066);
		head[6][18]= addBPoint(deltax*0.875,deltay*0.5942002,deltaz*0.6066);
		head[6][19]= addBPoint(deltax*0.75,deltay*0.6144156,deltaz*0.6066);
		head[6][20]= addBPoint(deltax*0.625,deltay*0.62806928,deltaz*0.6066);
		head[6][21]= addBPoint(deltax*0.5,deltay*0.64172296,deltaz*0.6066);
		head[6][22]= addBPoint(deltax*0.375,deltay*0.64967656,deltaz*0.6066);
		head[6][23]= addBPoint(deltax*0.25,deltay*0.65763016,deltaz*0.6066);
		head[6][24]= addBPoint(deltax*0.125,deltay*0.66021508,deltaz*0.6066);
		head[6][25]= addBPoint(deltax*0.0,deltay*0.6628,deltaz*0.6066);
		head[6][26]= addBPoint(-deltax*0.125,deltay*0.66021508,deltaz*0.6066);
		head[6][27]= addBPoint(-deltax*0.25,deltay*0.65763016,deltaz*0.6066);
		head[6][28]= addBPoint(-deltax*0.375,deltay*0.64967656,deltaz*0.6066);
		head[6][29]= addBPoint(-deltax*0.5,deltay*0.64172296,deltaz*0.6066);
		head[6][30]= addBPoint(-deltax*0.625,deltay*0.62806928,deltaz*0.6066);
		head[6][31]= addBPoint(-deltax*0.75,deltay*0.6144156,deltaz*0.6066);
		head[6][32]= addBPoint(-deltax*0.875,deltay*0.5942002,deltaz*0.6066);
		head[6][33]= addBPoint(-deltax*1.0,deltay*0.5739848,deltaz*0.6066);

		head[7][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.6885);
		head[7][1]= addBPoint(-deltax*0.875,-deltay*0.19997429,deltaz*0.6885);
		head[7][2]= addBPoint(-deltax*0.75,-deltay*0.39994858,deltaz*0.6885);
		head[7][3]= addBPoint(-deltax*0.625,-deltay*0.46180939,deltaz*0.6885);
		head[7][4]= addBPoint(-deltax*0.5,-deltay*0.5236702,deltaz*0.6885);
		head[7][5]= addBPoint(-deltax*0.375,-deltay*0.55457037,deltaz*0.6885);
		head[7][6]= addBPoint(-deltax*0.25,-deltay*0.58547054,deltaz*0.6885);
		head[7][7]= addBPoint(-deltax*0.125,-deltay*0.59508527,deltaz*0.6885);
		head[7][8]= addBPoint(-deltax*0.0,-deltay*0.6047,deltaz*0.6885);
		head[7][9]= addBPoint(deltax*0.125,-deltay*0.59508527,deltaz*0.6885);
		head[7][10]= addBPoint(deltax*0.25,-deltay*0.58547054,deltaz*0.6885);
		head[7][11]= addBPoint(deltax*0.375,-deltay*0.55457037,deltaz*0.6885);
		head[7][12]= addBPoint(deltax*0.5,-deltay*0.5236702,deltaz*0.6885);
		head[7][13]= addBPoint(deltax*0.625,-deltay*0.46180939,deltaz*0.6885);
		head[7][14]= addBPoint(deltax*0.75,-deltay*0.39994858,deltaz*0.6885);
		head[7][15]= addBPoint(deltax*0.875,-deltay*0.19997429,deltaz*0.6885);
		head[7][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.6885);
		head[7][17]= addBPoint(deltax*1.0,deltay*0.5337158,deltaz*0.6885);
		head[7][18]= addBPoint(deltax*0.875,deltay*0.55251295,deltaz*0.6885);
		head[7][19]= addBPoint(deltax*0.75,deltay*0.5713101,deltaz*0.6885);
		head[7][20]= addBPoint(deltax*0.625,deltay*0.58400588,deltaz*0.6885);
		head[7][21]= addBPoint(deltax*0.5,deltay*0.5967016599999999,deltaz*0.6885);
		head[7][22]= addBPoint(deltax*0.375,deltay*0.6040972599999999,deltaz*0.6885);
		head[7][23]= addBPoint(deltax*0.25,deltay*0.6114928599999999,deltaz*0.6885);
		head[7][24]= addBPoint(deltax*0.125,deltay*0.6138964299999999,deltaz*0.6885);
		head[7][25]= addBPoint(deltax*0.0,deltay*0.6163,deltaz*0.6885);
		head[7][26]= addBPoint(-deltax*0.125,deltay*0.6138964299999999,deltaz*0.6885);
		head[7][27]= addBPoint(-deltax*0.25,deltay*0.6114928599999999,deltaz*0.6885);
		head[7][28]= addBPoint(-deltax*0.375,deltay*0.6040972599999999,deltaz*0.6885);
		head[7][29]= addBPoint(-deltax*0.5,deltay*0.5967016599999999,deltaz*0.6885);
		head[7][30]= addBPoint(-deltax*0.625,deltay*0.58400588,deltaz*0.6885);
		head[7][31]= addBPoint(-deltax*0.75,deltay*0.5713101,deltaz*0.6885);
		head[7][32]= addBPoint(-deltax*0.875,deltay*0.55251295,deltaz*0.6885);
		head[7][33]= addBPoint(-deltax*1.0,deltay*0.5337158,deltaz*0.6885);

		head[8][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.8525);
		head[8][1]= addBPoint(-deltax*0.875,-deltay*0.15489988,deltaz*0.8525);
		head[8][2]= addBPoint(-deltax*0.75,-deltay*0.30979976,deltaz*0.8525);
		head[8][3]= addBPoint(-deltax*0.625,-deltay*0.35771708,deltaz*0.8525);
		head[8][4]= addBPoint(-deltax*0.5,-deltay*0.4056344,deltaz*0.8525);
		head[8][5]= addBPoint(-deltax*0.375,-deltay*0.42956964,deltaz*0.8525);
		head[8][6]= addBPoint(-deltax*0.25,-deltay*0.45350487999999994,deltaz*0.8525);
		head[8][7]= addBPoint(-deltax*0.125,-deltay*0.46095243999999996,deltaz*0.8525);
		head[8][8]= addBPoint(-deltax*0.0,-deltay*0.4684,deltaz*0.8525);
		head[8][9]= addBPoint(deltax*0.125,-deltay*0.46095243999999996,deltaz*0.8525);
		head[8][10]= addBPoint(deltax*0.25,-deltay*0.45350487999999994,deltaz*0.8525);
		head[8][11]= addBPoint(deltax*0.375,-deltay*0.42956964,deltaz*0.8525);
		head[8][12]= addBPoint(deltax*0.5,-deltay*0.4056344,deltaz*0.8525);
		head[8][13]= addBPoint(deltax*0.625,-deltay*0.35771708,deltaz*0.8525);
		head[8][14]= addBPoint(deltax*0.75,-deltay*0.30979976,deltaz*0.8525);
		head[8][15]= addBPoint(deltax*0.875,-deltay*0.15489988,deltaz*0.8525);
		head[8][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.8525);
		head[8][17]= addBPoint(deltax*1.0,deltay*0.4129088,deltaz*0.8525);
		head[8][18]= addBPoint(deltax*0.875,deltay*0.42745120000000003,deltaz*0.8525);
		head[8][19]= addBPoint(deltax*0.75,deltay*0.44199360000000004,deltaz*0.8525);
		head[8][20]= addBPoint(deltax*0.625,deltay*0.45181568,deltaz*0.8525);
		head[8][21]= addBPoint(deltax*0.5,deltay*0.46163775999999995,deltaz*0.8525);
		head[8][22]= addBPoint(deltax*0.375,deltay*0.46735936,deltaz*0.8525);
		head[8][23]= addBPoint(deltax*0.25,deltay*0.47308096,deltaz*0.8525);
		head[8][24]= addBPoint(deltax*0.125,deltay*0.47494048,deltaz*0.8525);
		head[8][25]= addBPoint(deltax*0.0,deltay*0.4768,deltaz*0.8525);
		head[8][26]= addBPoint(-deltax*0.125,deltay*0.47494048,deltaz*0.8525);
		head[8][27]= addBPoint(-deltax*0.25,deltay*0.47308096,deltaz*0.8525);
		head[8][28]= addBPoint(-deltax*0.375,deltay*0.46735936,deltaz*0.8525);
		head[8][29]= addBPoint(-deltax*0.5,deltay*0.46163775999999995,deltaz*0.8525);
		head[8][30]= addBPoint(-deltax*0.625,deltay*0.45181568,deltaz*0.8525);
		head[8][31]= addBPoint(-deltax*0.75,deltay*0.44199360000000004,deltaz*0.8525);
		head[8][32]= addBPoint(-deltax*0.875,deltay*0.42745120000000003,deltaz*0.8525);
		head[8][33]= addBPoint(-deltax*1.0,deltay*0.4129088,deltaz*0.8525);

		head[9][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.9344);
		head[9][1]= addBPoint(-deltax*0.875,-deltay*0.11921735,deltaz*0.9344);
		head[9][2]= addBPoint(-deltax*0.75,-deltay*0.2384347,deltaz*0.9344);
		head[9][3]= addBPoint(-deltax*0.625,-deltay*0.27531384999999997,deltaz*0.9344);
		head[9][4]= addBPoint(-deltax*0.5,-deltay*0.312193,deltaz*0.9344);
		head[9][5]= addBPoint(-deltax*0.375,-deltay*0.33061454999999995,deltaz*0.9344);
		head[9][6]= addBPoint(-deltax*0.25,-deltay*0.34903609999999996,deltaz*0.9344);
		head[9][7]= addBPoint(-deltax*0.125,-deltay*0.35476805,deltaz*0.9344);
		head[9][8]= addBPoint(-deltax*0.0,-deltay*0.3605,deltaz*0.9344);
		head[9][9]= addBPoint(deltax*0.125,-deltay*0.35476805,deltaz*0.9344);
		head[9][10]= addBPoint(deltax*0.25,-deltay*0.34903609999999996,deltaz*0.9344);
		head[9][11]= addBPoint(deltax*0.375,-deltay*0.33061454999999995,deltaz*0.9344);
		head[9][12]= addBPoint(deltax*0.5,-deltay*0.312193,deltaz*0.9344);
		head[9][13]= addBPoint(deltax*0.625,-deltay*0.27531384999999997,deltaz*0.9344);
		head[9][14]= addBPoint(deltax*0.75,-deltay*0.2384347,deltaz*0.9344);
		head[9][15]= addBPoint(deltax*0.875,-deltay*0.11921735,deltaz*0.9344);
		head[9][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.9344);
		head[9][17]= addBPoint(deltax*1.0,deltay*0.3222386,deltaz*0.9344);
		head[9][18]= addBPoint(deltax*0.875,deltay*0.33358765,deltaz*0.9344);
		head[9][19]= addBPoint(deltax*0.75,deltay*0.3449367,deltaz*0.9344);
		head[9][20]= addBPoint(deltax*0.625,deltay*0.35260196,deltaz*0.9344);
		head[9][21]= addBPoint(deltax*0.5,deltay*0.36026721999999994,deltaz*0.9344);
		head[9][22]= addBPoint(deltax*0.375,deltay*0.36473241999999995,deltaz*0.9344);
		head[9][23]= addBPoint(deltax*0.25,deltay*0.36919761999999995,deltaz*0.9344);
		head[9][24]= addBPoint(deltax*0.125,deltay*0.37064880999999994,deltaz*0.9344);
		head[9][25]= addBPoint(deltax*0.0,deltay*0.3721,deltaz*0.9344);
		head[9][26]= addBPoint(-deltax*0.125,deltay*0.37064880999999994,deltaz*0.9344);
		head[9][27]= addBPoint(-deltax*0.25,deltay*0.36919761999999995,deltaz*0.9344);
		head[9][28]= addBPoint(-deltax*0.375,deltay*0.36473241999999995,deltaz*0.9344);
		head[9][29]= addBPoint(-deltax*0.5,deltay*0.36026721999999994,deltaz*0.9344);
		head[9][30]= addBPoint(-deltax*0.625,deltay*0.35260196,deltaz*0.9344);
		head[9][31]= addBPoint(-deltax*0.75,deltay*0.3449367,deltaz*0.9344);
		head[9][32]= addBPoint(-deltax*0.875,deltay*0.33358765,deltaz*0.9344);
		head[9][33]= addBPoint(-deltax*1.0,deltay*0.3222386,deltaz*0.9344);

		head[10][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*1.0);
		head[10][1]= addBPoint(-deltax*0.875,-deltay*0.0,deltaz*1.0);
		head[10][2]= addBPoint(-deltax*0.75,-deltay*0.0,deltaz*1.0);
		head[10][3]= addBPoint(-deltax*0.625,-deltay*0.0,deltaz*1.0);
		head[10][4]= addBPoint(-deltax*0.5,-deltay*0.0,deltaz*1.0);
		head[10][5]= addBPoint(-deltax*0.375,-deltay*0.0,deltaz*1.0);
		head[10][6]= addBPoint(-deltax*0.25,-deltay*0.0,deltaz*1.0);
		head[10][7]= addBPoint(-deltax*0.125,-deltay*0.0,deltaz*1.0);
		head[10][8]= addBPoint(-deltax*0.0,-deltay*0.0,deltaz*1.0);
		head[10][9]= addBPoint(deltax*0.125,-deltay*0.0,deltaz*1.0);
		head[10][10]= addBPoint(deltax*0.25,-deltay*0.0,deltaz*1.0);
		head[10][11]= addBPoint(deltax*0.375,-deltay*0.0,deltaz*1.0);
		head[10][12]= addBPoint(deltax*0.5,-deltay*0.0,deltaz*1.0);
		head[10][13]= addBPoint(deltax*0.625,-deltay*0.0,deltaz*1.0);
		head[10][14]= addBPoint(deltax*0.75,-deltay*0.0,deltaz*1.0);
		head[10][15]= addBPoint(deltax*0.875,-deltay*0.0,deltaz*1.0);
		head[10][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*1.0);
		head[10][17]= addBPoint(deltax*1.0,deltay*0.0,deltaz*1.0);
		head[10][18]= addBPoint(deltax*0.875,deltay*0.0,deltaz*1.0);
		head[10][19]= addBPoint(deltax*0.75,deltay*0.0,deltaz*1.0);
		head[10][20]= addBPoint(deltax*0.625,deltay*0.0,deltaz*1.0);
		head[10][21]= addBPoint(deltax*0.5,deltay*0.0,deltaz*1.0);
		head[10][22]= addBPoint(deltax*0.375,deltay*0.0,deltaz*1.0);
		head[10][23]= addBPoint(deltax*0.25,deltay*0.0,deltaz*1.0);
		head[10][24]= addBPoint(deltax*0.125,deltay*0.0,deltaz*1.0);
		head[10][25]= addBPoint(deltax*0.0,deltay*0.0,deltaz*1.0);
		head[10][26]= addBPoint(-deltax*0.125,deltay*0.0,deltaz*1.0);
		head[10][27]= addBPoint(-deltax*0.25,deltay*0.0,deltaz*1.0);
		head[10][28]= addBPoint(-deltax*0.375,deltay*0.0,deltaz*1.0);
		head[10][29]= addBPoint(-deltax*0.5,deltay*0.0,deltaz*1.0);
		head[10][30]= addBPoint(-deltax*0.625,deltay*0.0,deltaz*1.0);
		head[10][31]= addBPoint(-deltax*0.75,deltay*0.0,deltaz*1.0);
		head[10][32]= addBPoint(-deltax*0.875,deltay*0.0,deltaz*1.0);
		head[10][33]= addBPoint(-deltax*1.0,deltay*0.0,deltaz*1.0);



		head[0][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.0);
		head[0][1]= addBPoint(-deltax*0.875,-deltay*0.16151388,deltaz*0.0);
		head[0][2]= addBPoint(-deltax*0.75,-deltay*0.32302776,deltaz*0.0);
		head[0][3]= addBPoint(-deltax*0.625,-deltay*0.37299108000000003,deltaz*0.0);
		head[0][4]= addBPoint(-deltax*0.5,-deltay*0.4229544,deltaz*0.0);
		head[0][5]= addBPoint(-deltax*0.375,-deltay*0.44791164,deltaz*0.0);
		head[0][6]= addBPoint(-deltax*0.25,-deltay*0.47286888,deltaz*0.0);
		head[0][7]= addBPoint(-deltax*0.125,-deltay*0.48063444,deltaz*0.0);
		head[0][8]= addBPoint(-deltax*0.0,-deltay*0.4884,deltaz*0.0);
		head[0][9]= addBPoint(deltax*0.125,-deltay*0.48063444,deltaz*0.0);
		head[0][10]= addBPoint(deltax*0.25,-deltay*0.47286888,deltaz*0.0);
		head[0][11]= addBPoint(deltax*0.375,-deltay*0.44791164,deltaz*0.0);
		head[0][12]= addBPoint(deltax*0.5,-deltay*0.4229544,deltaz*0.0);
		head[0][13]= addBPoint(deltax*0.625,-deltay*0.37299108000000003,deltaz*0.0);
		head[0][14]= addBPoint(deltax*0.75,-deltay*0.32302776,deltaz*0.0);
		head[0][15]= addBPoint(deltax*0.875,-deltay*0.16151388,deltaz*0.0);
		head[0][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.0);
		head[0][17]= addBPoint(deltax*1.0,deltay*0.5337158,deltaz*0.0);
		head[0][18]= addBPoint(deltax*0.875,deltay*0.55251295,deltaz*0.0);
		head[0][19]= addBPoint(deltax*0.75,deltay*0.5713101,deltaz*0.0);
		head[0][20]= addBPoint(deltax*0.625,deltay*0.58400588,deltaz*0.0);
		head[0][21]= addBPoint(deltax*0.5,deltay*0.5967016599999999,deltaz*0.0);
		head[0][22]= addBPoint(deltax*0.375,deltay*0.6040972599999999,deltaz*0.0);
		head[0][23]= addBPoint(deltax*0.25,deltay*0.6114928599999999,deltaz*0.0);
		head[0][24]= addBPoint(deltax*0.125,deltay*0.6138964299999999,deltaz*0.0);
		head[0][25]= addBPoint(deltax*0.0,deltay*0.6163,deltaz*0.0);
		head[0][26]= addBPoint(-deltax*0.125,deltay*0.6138964299999999,deltaz*0.0);
		head[0][27]= addBPoint(-deltax*0.25,deltay*0.6114928599999999,deltaz*0.0);
		head[0][28]= addBPoint(-deltax*0.375,deltay*0.6040972599999999,deltaz*0.0);
		head[0][29]= addBPoint(-deltax*0.5,deltay*0.5967016599999999,deltaz*0.0);
		head[0][30]= addBPoint(-deltax*0.625,deltay*0.58400588,deltaz*0.0);
		head[0][31]= addBPoint(-deltax*0.75,deltay*0.5713101,deltaz*0.0);
		head[0][32]= addBPoint(-deltax*0.875,deltay*0.55251295,deltaz*0.0);
		head[0][33]= addBPoint(-deltax*1.0,deltay*0.5337158,deltaz*0.0);

		head[1][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.0574);
		head[1][1]= addBPoint(-deltax*0.875,-deltay*0.14998898500000002,deltaz*0.0574);
		head[1][2]= addBPoint(-deltax*0.75,-deltay*0.29997797000000004,deltaz*0.0574);
		head[1][3]= addBPoint(-deltax*0.625,-deltay*0.34637613500000003,deltaz*0.0574);
		head[1][4]= addBPoint(-deltax*0.5,-deltay*0.3927743,deltaz*0.0574);
		head[1][5]= addBPoint(-deltax*0.375,-deltay*0.415950705,deltaz*0.0574);
		head[1][6]= addBPoint(-deltax*0.25,-deltay*0.43912711,deltaz*0.0574);
		head[1][7]= addBPoint(-deltax*0.125,-deltay*0.44633855499999997,deltaz*0.0574);
		head[1][8]= addBPoint(-deltax*0.0,-deltay*0.45355,deltaz*0.0574);
		head[1][9]= addBPoint(deltax*0.125,-deltay*0.44633855499999997,deltaz*0.0574);
		head[1][10]= addBPoint(deltax*0.25,-deltay*0.43912711,deltaz*0.0574);
		head[1][11]= addBPoint(deltax*0.375,-deltay*0.415950705,deltaz*0.0574);
		head[1][12]= addBPoint(deltax*0.5,-deltay*0.3927743,deltaz*0.0574);
		head[1][13]= addBPoint(deltax*0.625,-deltay*0.34637613500000003,deltaz*0.0574);
		head[1][14]= addBPoint(deltax*0.75,-deltay*0.29997797000000004,deltaz*0.0574);
		head[1][15]= addBPoint(deltax*0.875,-deltay*0.14998898500000002,deltaz*0.0574);
		head[1][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.0574);
		head[1][17]= addBPoint(deltax*1.0,deltay*0.5438046999999999,deltaz*0.0574);
		head[1][18]= addBPoint(deltax*0.875,deltay*0.562957175,deltaz*0.0574);
		head[1][19]= addBPoint(deltax*0.75,deltay*0.58210965,deltaz*0.0574);
		head[1][20]= addBPoint(deltax*0.625,deltay*0.5950454199999999,deltaz*0.0574);
		head[1][21]= addBPoint(deltax*0.5,deltay*0.6079811899999998,deltaz*0.0574);
		head[1][22]= addBPoint(deltax*0.375,deltay*0.6155165899999999,deltaz*0.0574);
		head[1][23]= addBPoint(deltax*0.25,deltay*0.62305199,deltaz*0.0574);
		head[1][24]= addBPoint(deltax*0.125,deltay*0.6255009949999999,deltaz*0.0574);
		head[1][25]= addBPoint(deltax*0.0,deltay*0.62795,deltaz*0.0574);
		head[1][26]= addBPoint(-deltax*0.125,deltay*0.6255009949999999,deltaz*0.0574);
		head[1][27]= addBPoint(-deltax*0.25,deltay*0.62305199,deltaz*0.0574);
		head[1][28]= addBPoint(-deltax*0.375,deltay*0.6155165899999999,deltaz*0.0574);
		head[1][29]= addBPoint(-deltax*0.5,deltay*0.6079811899999998,deltaz*0.0574);
		head[1][30]= addBPoint(-deltax*0.625,deltay*0.5950454199999999,deltaz*0.0574);
		head[1][31]= addBPoint(-deltax*0.75,deltay*0.58210965,deltaz*0.0574);
		head[1][32]= addBPoint(-deltax*0.875,deltay*0.562957175,deltaz*0.0574);
		head[1][33]= addBPoint(-deltax*1.0,deltay*0.5438046999999999,deltaz*0.0574);

		head[2][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.1148);
		head[2][1]= addBPoint(-deltax*0.875,-deltay*0.13846409,deltaz*0.1148);
		head[2][2]= addBPoint(-deltax*0.75,-deltay*0.27692818,deltaz*0.1148);
		head[2][3]= addBPoint(-deltax*0.625,-deltay*0.31976119000000003,deltaz*0.1148);
		head[2][4]= addBPoint(-deltax*0.5,-deltay*0.36259420000000003,deltaz*0.1148);
		head[2][5]= addBPoint(-deltax*0.375,-deltay*0.38398977,deltaz*0.1148);
		head[2][6]= addBPoint(-deltax*0.25,-deltay*0.40538534,deltaz*0.1148);
		head[2][7]= addBPoint(-deltax*0.125,-deltay*0.41204267,deltaz*0.1148);
		head[2][8]= addBPoint(-deltax*0.0,-deltay*0.4187,deltaz*0.1148);
		head[2][9]= addBPoint(deltax*0.125,-deltay*0.41204267,deltaz*0.1148);
		head[2][10]= addBPoint(deltax*0.25,-deltay*0.40538534,deltaz*0.1148);
		head[2][11]= addBPoint(deltax*0.375,-deltay*0.38398977,deltaz*0.1148);
		head[2][12]= addBPoint(deltax*0.5,-deltay*0.36259420000000003,deltaz*0.1148);
		head[2][13]= addBPoint(deltax*0.625,-deltay*0.31976119000000003,deltaz*0.1148);
		head[2][14]= addBPoint(deltax*0.75,-deltay*0.27692818,deltaz*0.1148);
		head[2][15]= addBPoint(deltax*0.875,-deltay*0.13846409,deltaz*0.1148);
		head[2][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.1148);
		head[2][17]= addBPoint(deltax*1.0,deltay*0.5538936,deltaz*0.1148);
		head[2][18]= addBPoint(deltax*0.875,deltay*0.5734014000000001,deltaz*0.1148);
		head[2][19]= addBPoint(deltax*0.75,deltay*0.5929092,deltaz*0.1148);
		head[2][20]= addBPoint(deltax*0.625,deltay*0.60608496,deltaz*0.1148);
		head[2][21]= addBPoint(deltax*0.5,deltay*0.6192607199999999,deltaz*0.1148);
		head[2][22]= addBPoint(deltax*0.375,deltay*0.62693592,deltaz*0.1148);
		head[2][23]= addBPoint(deltax*0.25,deltay*0.63461112,deltaz*0.1148);
		head[2][24]= addBPoint(deltax*0.125,deltay*0.63710556,deltaz*0.1148);
		head[2][25]= addBPoint(deltax*0.0,deltay*0.6396,deltaz*0.1148);
		head[2][26]= addBPoint(-deltax*0.125,deltay*0.63710556,deltaz*0.1148);
		head[2][27]= addBPoint(-deltax*0.25,deltay*0.63461112,deltaz*0.1148);
		head[2][28]= addBPoint(-deltax*0.375,deltay*0.62693592,deltaz*0.1148);
		head[2][29]= addBPoint(-deltax*0.5,deltay*0.6192607199999999,deltaz*0.1148);
		head[2][30]= addBPoint(-deltax*0.625,deltay*0.60608496,deltaz*0.1148);
		head[2][31]= addBPoint(-deltax*0.75,deltay*0.5929092,deltaz*0.1148);
		head[2][32]= addBPoint(-deltax*0.875,deltay*0.5734014000000001,deltaz*0.1148);
		head[2][33]= addBPoint(-deltax*1.0,deltay*0.5538936,deltaz*0.1148);

		head[3][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.14755);
		head[3][1]= addBPoint(-deltax*0.875,-deltay*0.13846409,deltaz*0.14755);
		head[3][2]= addBPoint(-deltax*0.75,-deltay*0.27692818,deltaz*0.14755);
		head[3][3]= addBPoint(-deltax*0.625,-deltay*0.31976119000000003,deltaz*0.14755);
		head[3][4]= addBPoint(-deltax*0.5,-deltay*0.36259420000000003,deltaz*0.14755);
		head[3][5]= addBPoint(-deltax*0.375,-deltay*0.38398977,deltaz*0.14755);
		head[3][6]= addBPoint(-deltax*0.25,-deltay*0.40538534,deltaz*0.14755);
		head[3][7]= addBPoint(-deltax*0.125,-deltay*0.41204267,deltaz*0.14755);
		head[3][8]= addBPoint(-deltax*0.0,-deltay*0.4187,deltaz*0.14755);
		head[3][9]= addBPoint(deltax*0.125,-deltay*0.41204267,deltaz*0.14755);
		head[3][10]= addBPoint(deltax*0.25,-deltay*0.40538534,deltaz*0.14755);
		head[3][11]= addBPoint(deltax*0.375,-deltay*0.38398977,deltaz*0.14755);
		head[3][12]= addBPoint(deltax*0.5,-deltay*0.36259420000000003,deltaz*0.14755);
		head[3][13]= addBPoint(deltax*0.625,-deltay*0.31976119000000003,deltaz*0.14755);
		head[3][14]= addBPoint(deltax*0.75,-deltay*0.27692818,deltaz*0.14755);
		head[3][15]= addBPoint(deltax*0.875,-deltay*0.13846409,deltaz*0.14755);
		head[3][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.14755);
		head[3][17]= addBPoint(deltax*1.0,deltay*0.5538936,deltaz*0.14755);
		head[3][18]= addBPoint(deltax*0.875,deltay*0.5734014000000001,deltaz*0.14755);
		head[3][19]= addBPoint(deltax*0.75,deltay*0.5929092,deltaz*0.14755);
		head[3][20]= addBPoint(deltax*0.625,deltay*0.60608496,deltaz*0.14755);
		head[3][21]= addBPoint(deltax*0.5,deltay*0.6192607199999999,deltaz*0.14755);
		head[3][22]= addBPoint(deltax*0.375,deltay*0.62693592,deltaz*0.14755);
		head[3][23]= addBPoint(deltax*0.25,deltay*0.63461112,deltaz*0.14755);
		head[3][24]= addBPoint(deltax*0.125,deltay*0.63710556,deltaz*0.14755);
		head[3][25]= addBPoint(deltax*0.0,deltay*0.6396,deltaz*0.14755);
		head[3][26]= addBPoint(-deltax*0.125,deltay*0.63710556,deltaz*0.14755);
		head[3][27]= addBPoint(-deltax*0.25,deltay*0.63461112,deltaz*0.14755);
		head[3][28]= addBPoint(-deltax*0.375,deltay*0.62693592,deltaz*0.14755);
		head[3][29]= addBPoint(-deltax*0.5,deltay*0.6192607199999999,deltaz*0.14755);
		head[3][30]= addBPoint(-deltax*0.625,deltay*0.60608496,deltaz*0.14755);
		head[3][31]= addBPoint(-deltax*0.75,deltay*0.5929092,deltaz*0.14755);
		head[3][32]= addBPoint(-deltax*0.875,deltay*0.5734014000000001,deltaz*0.14755);
		head[3][33]= addBPoint(-deltax*1.0,deltay*0.5538936,deltaz*0.14755);

		head[4][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.1803);
		head[4][1]= addBPoint(-deltax*0.875,-deltay*0.13846409,deltaz*0.1803);
		head[4][2]= addBPoint(-deltax*0.75,-deltay*0.27692818,deltaz*0.1803);
		head[4][3]= addBPoint(-deltax*0.625,-deltay*0.31976119000000003,deltaz*0.1803);
		head[4][4]= addBPoint(-deltax*0.5,-deltay*0.36259420000000003,deltaz*0.1803);
		head[4][5]= addBPoint(-deltax*0.375,-deltay*0.38398977,deltaz*0.1803);
		head[4][6]= addBPoint(-deltax*0.25,-deltay*0.40538534,deltaz*0.1803);
		head[4][7]= addBPoint(-deltax*0.125,-deltay*0.41204267,deltaz*0.1803);
		head[4][8]= addBPoint(-deltax*0.0,-deltay*0.4187,deltaz*0.1803);
		head[4][9]= addBPoint(deltax*0.125,-deltay*0.41204267,deltaz*0.1803);
		head[4][10]= addBPoint(deltax*0.25,-deltay*0.40538534,deltaz*0.1803);
		head[4][11]= addBPoint(deltax*0.375,-deltay*0.38398977,deltaz*0.1803);
		head[4][12]= addBPoint(deltax*0.5,-deltay*0.36259420000000003,deltaz*0.1803);
		head[4][13]= addBPoint(deltax*0.625,-deltay*0.31976119000000003,deltaz*0.1803);
		head[4][14]= addBPoint(deltax*0.75,-deltay*0.27692818,deltaz*0.1803);
		head[4][15]= addBPoint(deltax*0.875,-deltay*0.13846409,deltaz*0.1803);
		head[4][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.1803);
		head[4][17]= addBPoint(deltax*1.0,deltay*0.5538936,deltaz*0.1803);
		head[4][18]= addBPoint(deltax*0.875,deltay*0.5734014000000001,deltaz*0.1803);
		head[4][19]= addBPoint(deltax*0.75,deltay*0.5929092,deltaz*0.1803);
		head[4][20]= addBPoint(deltax*0.625,deltay*0.60608496,deltaz*0.1803);
		head[4][21]= addBPoint(deltax*0.5,deltay*0.6192607199999999,deltaz*0.1803);
		head[4][22]= addBPoint(deltax*0.375,deltay*0.62693592,deltaz*0.1803);
		head[4][23]= addBPoint(deltax*0.25,deltay*0.63461112,deltaz*0.1803);
		head[4][24]= addBPoint(deltax*0.125,deltay*0.63710556,deltaz*0.1803);
		head[4][25]= addBPoint(deltax*0.0,deltay*0.6396,deltaz*0.1803);
		head[4][26]= addBPoint(-deltax*0.125,deltay*0.63710556,deltaz*0.1803);
		head[4][27]= addBPoint(-deltax*0.25,deltay*0.63461112,deltaz*0.1803);
		head[4][28]= addBPoint(-deltax*0.375,deltay*0.62693592,deltaz*0.1803);
		head[4][29]= addBPoint(-deltax*0.5,deltay*0.6192607199999999,deltaz*0.1803);
		head[4][30]= addBPoint(-deltax*0.625,deltay*0.60608496,deltaz*0.1803);
		head[4][31]= addBPoint(-deltax*0.75,deltay*0.5929092,deltaz*0.1803);
		head[4][32]= addBPoint(-deltax*0.875,deltay*0.5734014000000001,deltaz*0.1803);
		head[4][33]= addBPoint(-deltax*1.0,deltay*0.5538936,deltaz*0.1803);

		head[5][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.22949999999999998);
		head[5][1]= addBPoint(-deltax*0.875,-deltay*0.14613633,deltaz*0.22949999999999998);
		head[5][2]= addBPoint(-deltax*0.75,-deltay*0.29227266,deltaz*0.22949999999999998);
		head[5][3]= addBPoint(-deltax*0.625,-deltay*0.33747903,deltaz*0.22949999999999998);
		head[5][4]= addBPoint(-deltax*0.5,-deltay*0.3826854,deltaz*0.22949999999999998);
		head[5][5]= addBPoint(-deltax*0.375,-deltay*0.40526649,deltaz*0.22949999999999998);
		head[5][6]= addBPoint(-deltax*0.25,-deltay*0.42784758,deltaz*0.22949999999999998);
		head[5][7]= addBPoint(-deltax*0.125,-deltay*0.43487379000000004,deltaz*0.22949999999999998);
		head[5][8]= addBPoint(-deltax*0.0,-deltay*0.4419,deltaz*0.22949999999999998);
		head[5][9]= addBPoint(deltax*0.125,-deltay*0.43487379000000004,deltaz*0.22949999999999998);
		head[5][10]= addBPoint(deltax*0.25,-deltay*0.42784758,deltaz*0.22949999999999998);
		head[5][11]= addBPoint(deltax*0.375,-deltay*0.4052664925,deltaz*0.22949999999999998);
		head[5][12]= addBPoint(deltax*0.5,-deltay*0.38268540500000003,deltaz*0.22949999999999998);
		head[5][13]= addBPoint(deltax*0.625,-deltay*0.3374790325,deltaz*0.22949999999999998);
		head[5][14]= addBPoint(deltax*0.75,-deltay*0.29227266,deltaz*0.22949999999999998);
		head[5][15]= addBPoint(deltax*0.875,-deltay*0.14613633,deltaz*0.22949999999999998);
		head[5][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.22949999999999998);
		head[5][17]= addBPoint(deltax*1.0,deltay*0.5740281,deltaz*0.22949999999999998);
		head[5][18]= addBPoint(deltax*0.875,deltay*0.594245025,deltaz*0.22949999999999998);
		head[5][19]= addBPoint(deltax*0.75,deltay*0.61446195,deltaz*0.22949999999999998);
		head[5][20]= addBPoint(deltax*0.625,deltay*0.62811666,deltaz*0.22949999999999998);
		head[5][21]= addBPoint(deltax*0.5,deltay*0.64177137,deltaz*0.22949999999999998);
		head[5][22]= addBPoint(deltax*0.375,deltay*0.64972557,deltaz*0.22949999999999998);
		head[5][23]= addBPoint(deltax*0.25,deltay*0.65767977,deltaz*0.22949999999999998);
		head[5][24]= addBPoint(deltax*0.125,deltay*0.660264885,deltaz*0.22949999999999998);
		head[5][25]= addBPoint(deltax*0.0,deltay*0.6628499999999999,deltaz*0.22949999999999998);
		head[5][26]= addBPoint(-deltax*0.125,deltay*0.660264885,deltaz*0.22949999999999998);
		head[5][27]= addBPoint(-deltax*0.25,deltay*0.65767977,deltaz*0.22949999999999998);
		head[5][28]= addBPoint(-deltax*0.375,deltay*0.64972557,deltaz*0.22949999999999998);
		head[5][29]= addBPoint(-deltax*0.5,deltay*0.64177137,deltaz*0.22949999999999998);
		head[5][30]= addBPoint(-deltax*0.625,deltay*0.62811666,deltaz*0.22949999999999998);
		head[5][31]= addBPoint(-deltax*0.75,deltay*0.61446195,deltaz*0.22949999999999998);
		head[5][32]= addBPoint(-deltax*0.875,deltay*0.594245025,deltaz*0.22949999999999998);
		head[5][33]= addBPoint(-deltax*1.0,deltay*0.5740281,deltaz*0.22949999999999998);

		head[6][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.2787);
		head[6][1]= addBPoint(-deltax*0.875,-deltay*0.15380857,deltaz*0.2787);
		head[6][2]= addBPoint(-deltax*0.75,-deltay*0.30761714,deltaz*0.2787);
		head[6][3]= addBPoint(-deltax*0.625,-deltay*0.35519687,deltaz*0.2787);
		head[6][4]= addBPoint(-deltax*0.5,-deltay*0.4027766,deltaz*0.2787);
		head[6][5]= addBPoint(-deltax*0.375,-deltay*0.42654320999999995,deltaz*0.2787);
		head[6][6]= addBPoint(-deltax*0.25,-deltay*0.45030981999999997,deltaz*0.2787);
		head[6][7]= addBPoint(-deltax*0.125,-deltay*0.45770491,deltaz*0.2787);
		head[6][8]= addBPoint(-deltax*0.0,-deltay*0.4651,deltaz*0.2787);
		head[6][9]= addBPoint(deltax*0.125,-deltay*0.45770491,deltaz*0.2787);
		head[6][10]= addBPoint(deltax*0.25,-deltay*0.45030981999999997,deltaz*0.2787);
		head[6][11]= addBPoint(deltax*0.375,-deltay*0.426543215,deltaz*0.2787);
		head[6][12]= addBPoint(deltax*0.5,-deltay*0.40277661,deltaz*0.2787);
		head[6][13]= addBPoint(deltax*0.625,-deltay*0.35519687499999997,deltaz*0.2787);
		head[6][14]= addBPoint(deltax*0.75,-deltay*0.30761714,deltaz*0.2787);
		head[6][15]= addBPoint(deltax*0.875,-deltay*0.15380857,deltaz*0.2787);
		head[6][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.2787);
		head[6][17]= addBPoint(deltax*1.0,deltay*0.5941626,deltaz*0.2787);
		head[6][18]= addBPoint(deltax*0.875,deltay*0.61508865,deltaz*0.2787);
		head[6][19]= addBPoint(deltax*0.75,deltay*0.6360147,deltaz*0.2787);
		head[6][20]= addBPoint(deltax*0.625,deltay*0.65014836,deltaz*0.2787);
		head[6][21]= addBPoint(deltax*0.5,deltay*0.66428202,deltaz*0.2787);
		head[6][22]= addBPoint(deltax*0.375,deltay*0.67251522,deltaz*0.2787);
		head[6][23]= addBPoint(deltax*0.25,deltay*0.68074842,deltaz*0.2787);
		head[6][24]= addBPoint(deltax*0.125,deltay*0.6834242100000001,deltaz*0.2787);
		head[6][25]= addBPoint(deltax*0.0,deltay*0.6861,deltaz*0.2787);
		head[6][26]= addBPoint(-deltax*0.125,deltay*0.6834242100000001,deltaz*0.2787);
		head[6][27]= addBPoint(-deltax*0.25,deltay*0.68074842,deltaz*0.2787);
		head[6][28]= addBPoint(-deltax*0.375,deltay*0.67251522,deltaz*0.2787);
		head[6][29]= addBPoint(-deltax*0.5,deltay*0.66428202,deltaz*0.2787);
		head[6][30]= addBPoint(-deltax*0.625,deltay*0.65014836,deltaz*0.2787);
		head[6][31]= addBPoint(-deltax*0.75,deltay*0.6360147,deltaz*0.2787);
		head[6][32]= addBPoint(-deltax*0.875,deltay*0.61508865,deltaz*0.2787);
		head[6][33]= addBPoint(-deltax*1.0,deltay*0.5941626,deltaz*0.2787);

		head[7][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.3033);
		head[7][1]= addBPoint(-deltax*0.875,-deltay*0.15766122500000002,deltaz*0.3033);
		head[7][2]= addBPoint(-deltax*0.75,-deltay*0.31532245000000003,deltaz*0.3033);
		head[7][3]= addBPoint(-deltax*0.625,-deltay*0.364093975,deltaz*0.3033);
		head[7][4]= addBPoint(-deltax*0.5,-deltay*0.4128655,deltaz*0.3033);
		head[7][5]= addBPoint(-deltax*0.375,-deltay*0.437227425,deltaz*0.3033);
		head[7][6]= addBPoint(-deltax*0.25,-deltay*0.46158935,deltaz*0.3033);
		head[7][7]= addBPoint(-deltax*0.125,-deltay*0.469169675,deltaz*0.3033);
		head[7][8]= addBPoint(-deltax*0.0,-deltay*0.47675,deltaz*0.3033);
		head[7][9]= addBPoint(deltax*0.125,-deltay*0.469169675,deltaz*0.3033);
		head[7][10]= addBPoint(deltax*0.25,-deltay*0.46158935,deltaz*0.3033);
		head[7][11]= addBPoint(deltax*0.375,-deltay*0.4372274275,deltaz*0.3033);
		head[7][12]= addBPoint(deltax*0.5,-deltay*0.41286550499999997,deltaz*0.3033);
		head[7][13]= addBPoint(deltax*0.625,-deltay*0.3640939775,deltaz*0.3033);
		head[7][14]= addBPoint(deltax*0.75,-deltay*0.31532245000000003,deltaz*0.3033);
		head[7][15]= addBPoint(deltax*0.875,-deltay*0.15766122500000002,deltaz*0.3033);
		head[7][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.3033);
		head[7][17]= addBPoint(deltax*1.0,deltay*0.6344316,deltaz*0.3033);
		head[7][18]= addBPoint(deltax*0.875,deltay*0.6567759,deltaz*0.3033);
		head[7][19]= addBPoint(deltax*0.75,deltay*0.6791202000000001,deltaz*0.3033);
		head[7][20]= addBPoint(deltax*0.625,deltay*0.69421176,deltaz*0.3033);
		head[7][21]= addBPoint(deltax*0.5,deltay*0.7093033200000001,deltaz*0.3033);
		head[7][22]= addBPoint(deltax*0.375,deltay*0.71809452,deltaz*0.3033);
		head[7][23]= addBPoint(deltax*0.25,deltay*0.7268857200000001,deltaz*0.3033);
		head[7][24]= addBPoint(deltax*0.125,deltay*0.72974286,deltaz*0.3033);
		head[7][25]= addBPoint(deltax*0.0,deltay*0.7326,deltaz*0.3033);
		head[7][26]= addBPoint(-deltax*0.125,deltay*0.72974286,deltaz*0.3033);
		head[7][27]= addBPoint(-deltax*0.25,deltay*0.7268857200000001,deltaz*0.3033);
		head[7][28]= addBPoint(-deltax*0.375,deltay*0.71809452,deltaz*0.3033);
		head[7][29]= addBPoint(-deltax*0.5,deltay*0.7093033200000001,deltaz*0.3033);
		head[7][30]= addBPoint(-deltax*0.625,deltay*0.69421176,deltaz*0.3033);
		head[7][31]= addBPoint(-deltax*0.75,deltay*0.6791202000000001,deltaz*0.3033);
		head[7][32]= addBPoint(-deltax*0.875,deltay*0.6567759,deltaz*0.3033);
		head[7][33]= addBPoint(-deltax*1.0,deltay*0.6344316,deltaz*0.3033);

		head[8][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.3279);
		head[8][1]= addBPoint(-deltax*0.875,-deltay*0.16151388,deltaz*0.3279);
		head[8][2]= addBPoint(-deltax*0.75,-deltay*0.32302776,deltaz*0.3279);
		head[8][3]= addBPoint(-deltax*0.625,-deltay*0.37299108000000003,deltaz*0.3279);
		head[8][4]= addBPoint(-deltax*0.5,-deltay*0.4229544,deltaz*0.3279);
		head[8][5]= addBPoint(-deltax*0.375,-deltay*0.44791164,deltaz*0.3279);
		head[8][6]= addBPoint(-deltax*0.25,-deltay*0.47286888,deltaz*0.3279);
		head[8][7]= addBPoint(-deltax*0.125,-deltay*0.48063444,deltaz*0.3279);
		head[8][8]= addBPoint(-deltax*0.0,-deltay*0.4884,deltaz*0.3279);
		head[8][9]= addBPoint(deltax*0.125,-deltay*0.48063444,deltaz*0.3279);
		head[8][10]= addBPoint(deltax*0.25,-deltay*0.47286888,deltaz*0.3279);
		head[8][11]= addBPoint(deltax*0.375,-deltay*0.44791164,deltaz*0.3279);
		head[8][12]= addBPoint(deltax*0.5,-deltay*0.4229544,deltaz*0.3279);
		head[8][13]= addBPoint(deltax*0.625,-deltay*0.37299108000000003,deltaz*0.3279);
		head[8][14]= addBPoint(deltax*0.75,-deltay*0.32302776,deltaz*0.3279);
		head[8][15]= addBPoint(deltax*0.875,-deltay*0.16151388,deltaz*0.3279);
		head[8][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.3279);
		head[8][17]= addBPoint(deltax*1.0,deltay*0.6747006,deltaz*0.3279);
		head[8][18]= addBPoint(deltax*0.875,deltay*0.69846315,deltaz*0.3279);
		head[8][19]= addBPoint(deltax*0.75,deltay*0.7222257000000001,deltaz*0.3279);
		head[8][20]= addBPoint(deltax*0.625,deltay*0.73827516,deltaz*0.3279);
		head[8][21]= addBPoint(deltax*0.5,deltay*0.75432462,deltaz*0.3279);
		head[8][22]= addBPoint(deltax*0.375,deltay*0.76367382,deltaz*0.3279);
		head[8][23]= addBPoint(deltax*0.25,deltay*0.77302302,deltaz*0.3279);
		head[8][24]= addBPoint(deltax*0.125,deltay*0.77606151,deltaz*0.3279);
		head[8][25]= addBPoint(deltax*0.0,deltay*0.7791,deltaz*0.3279);
		head[8][26]= addBPoint(-deltax*0.125,deltay*0.77606151,deltaz*0.3279);
		head[8][27]= addBPoint(-deltax*0.25,deltay*0.77302302,deltaz*0.3279);
		head[8][28]= addBPoint(-deltax*0.375,deltay*0.76367382,deltaz*0.3279);
		head[8][29]= addBPoint(-deltax*0.5,deltay*0.75432462,deltaz*0.3279);
		head[8][30]= addBPoint(-deltax*0.625,deltay*0.73827516,deltaz*0.3279);
		head[8][31]= addBPoint(-deltax*0.75,deltay*0.7222257000000001,deltaz*0.3279);
		head[8][32]= addBPoint(-deltax*0.875,deltay*0.69846315,deltaz*0.3279);
		head[8][33]= addBPoint(-deltax*1.0,deltay*0.6747006,deltaz*0.3279);

		head[9][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.40165);
		head[9][1]= addBPoint(-deltax*0.875,-deltay*0.17497337,deltaz*0.40165);
		head[9][2]= addBPoint(-deltax*0.75,-deltay*0.34994674,deltaz*0.40165);
		head[9][3]= addBPoint(-deltax*0.625,-deltay*0.40407367,deltaz*0.40165);
		head[9][4]= addBPoint(-deltax*0.5,-deltay*0.45820059999999996,deltaz*0.40165);
		head[9][5]= addBPoint(-deltax*0.375,-deltay*0.48523761,deltaz*0.40165);
		head[9][6]= addBPoint(-deltax*0.25,-deltay*0.51227462,deltaz*0.40165);
		head[9][7]= addBPoint(-deltax*0.125,-deltay*0.52068731,deltaz*0.40165);
		head[9][8]= addBPoint(-deltax*0.0,-deltay*0.5291,deltaz*0.40165);
		head[9][9]= addBPoint(deltax*0.125,-deltay*0.52068731,deltaz*0.40165);
		head[9][10]= addBPoint(deltax*0.25,-deltay*0.51227462,deltaz*0.40165);
		head[9][11]= addBPoint(deltax*0.375,-deltay*0.48523761,deltaz*0.40165);
		head[9][12]= addBPoint(deltax*0.5,-deltay*0.45820059999999996,deltaz*0.40165);
		head[9][13]= addBPoint(deltax*0.625,-deltay*0.40407367,deltaz*0.40165);
		head[9][14]= addBPoint(deltax*0.75,-deltay*0.34994674,deltaz*0.40165);
		head[9][15]= addBPoint(deltax*0.875,-deltay*0.17497337,deltaz*0.40165);
		head[9][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.40165);
		head[9][17]= addBPoint(deltax*1.0,deltay*0.6344316,deltaz*0.40165);
		head[9][18]= addBPoint(deltax*0.875,deltay*0.6567759,deltaz*0.40165);
		head[9][19]= addBPoint(deltax*0.75,deltay*0.6791202000000001,deltaz*0.40165);
		head[9][20]= addBPoint(deltax*0.625,deltay*0.69421176,deltaz*0.40165);
		head[9][21]= addBPoint(deltax*0.5,deltay*0.7093033200000001,deltaz*0.40165);
		head[9][22]= addBPoint(deltax*0.375,deltay*0.71809452,deltaz*0.40165);
		head[9][23]= addBPoint(deltax*0.25,deltay*0.7268857200000001,deltaz*0.40165);
		head[9][24]= addBPoint(deltax*0.125,deltay*0.72974286,deltaz*0.40165);
		head[9][25]= addBPoint(deltax*0.0,deltay*0.7326,deltaz*0.40165);
		head[9][26]= addBPoint(-deltax*0.125,deltay*0.72974286,deltaz*0.40165);
		head[9][27]= addBPoint(-deltax*0.25,deltay*0.7268857200000001,deltaz*0.40165);
		head[9][28]= addBPoint(-deltax*0.375,deltay*0.71809452,deltaz*0.40165);
		head[9][29]= addBPoint(-deltax*0.5,deltay*0.7093033200000001,deltaz*0.40165);
		head[9][30]= addBPoint(-deltax*0.625,deltay*0.69421176,deltaz*0.40165);
		head[9][31]= addBPoint(-deltax*0.75,deltay*0.6791202000000001,deltaz*0.40165);
		head[9][32]= addBPoint(-deltax*0.875,deltay*0.6567759,deltaz*0.40165);
		head[9][33]= addBPoint(-deltax*1.0,deltay*0.6344316,deltaz*0.40165);

		head[10][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.4754);
		head[10][1]= addBPoint(-deltax*0.875,-deltay*0.18843285999999998,deltaz*0.4754);
		head[10][2]= addBPoint(-deltax*0.75,-deltay*0.37686571999999996,deltaz*0.4754);
		head[10][3]= addBPoint(-deltax*0.625,-deltay*0.43515625999999996,deltaz*0.4754);
		head[10][4]= addBPoint(-deltax*0.5,-deltay*0.49344679999999996,deltaz*0.4754);
		head[10][5]= addBPoint(-deltax*0.375,-deltay*0.5225635799999999,deltaz*0.4754);
		head[10][6]= addBPoint(-deltax*0.25,-deltay*0.55168036,deltaz*0.4754);
		head[10][7]= addBPoint(-deltax*0.125,-deltay*0.56074018,deltaz*0.4754);
		head[10][8]= addBPoint(-deltax*0.0,-deltay*0.5698,deltaz*0.4754);
		head[10][9]= addBPoint(deltax*0.125,-deltay*0.56074018,deltaz*0.4754);
		head[10][10]= addBPoint(deltax*0.25,-deltay*0.55168036,deltaz*0.4754);
		head[10][11]= addBPoint(deltax*0.375,-deltay*0.5225635799999999,deltaz*0.4754);
		head[10][12]= addBPoint(deltax*0.5,-deltay*0.49344679999999996,deltaz*0.4754);
		head[10][13]= addBPoint(deltax*0.625,-deltay*0.43515625999999996,deltaz*0.4754);
		head[10][14]= addBPoint(deltax*0.75,-deltay*0.37686571999999996,deltaz*0.4754);
		head[10][15]= addBPoint(deltax*0.875,-deltay*0.18843285999999998,deltaz*0.4754);
		head[10][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.4754);
		head[10][17]= addBPoint(deltax*1.0,deltay*0.5941626,deltaz*0.4754);
		head[10][18]= addBPoint(deltax*0.875,deltay*0.61508865,deltaz*0.4754);
		head[10][19]= addBPoint(deltax*0.75,deltay*0.6360147,deltaz*0.4754);
		head[10][20]= addBPoint(deltax*0.625,deltay*0.65014836,deltaz*0.4754);
		head[10][21]= addBPoint(deltax*0.5,deltay*0.66428202,deltaz*0.4754);
		head[10][22]= addBPoint(deltax*0.375,deltay*0.67251522,deltaz*0.4754);
		head[10][23]= addBPoint(deltax*0.25,deltay*0.68074842,deltaz*0.4754);
		head[10][24]= addBPoint(deltax*0.125,deltay*0.6834242100000001,deltaz*0.4754);
		head[10][25]= addBPoint(deltax*0.0,deltay*0.6861,deltaz*0.4754);
		head[10][26]= addBPoint(-deltax*0.125,deltay*0.6834242100000001,deltaz*0.4754);
		head[10][27]= addBPoint(-deltax*0.25,deltay*0.68074842,deltaz*0.4754);
		head[10][28]= addBPoint(-deltax*0.375,deltay*0.67251522,deltaz*0.4754);
		head[10][29]= addBPoint(-deltax*0.5,deltay*0.66428202,deltaz*0.4754);
		head[10][30]= addBPoint(-deltax*0.625,deltay*0.65014836,deltaz*0.4754);
		head[10][31]= addBPoint(-deltax*0.75,deltay*0.6360147,deltaz*0.4754);
		head[10][32]= addBPoint(-deltax*0.875,deltay*0.61508865,deltaz*0.4754);
		head[10][33]= addBPoint(-deltax*1.0,deltay*0.5941626,deltaz*0.4754);

		head[11][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.541);
		head[11][1]= addBPoint(-deltax*0.875,-deltay*0.19035091999999998,deltaz*0.541);
		head[11][2]= addBPoint(-deltax*0.75,-deltay*0.38070183999999996,deltaz*0.541);
		head[11][3]= addBPoint(-deltax*0.625,-deltay*0.43958572,deltaz*0.541);
		head[11][4]= addBPoint(-deltax*0.5,-deltay*0.4984696,deltaz*0.541);
		head[11][5]= addBPoint(-deltax*0.375,-deltay*0.52788276,deltaz*0.541);
		head[11][6]= addBPoint(-deltax*0.25,-deltay*0.5572959200000001,deltaz*0.541);
		head[11][7]= addBPoint(-deltax*0.125,-deltay*0.5664479600000001,deltaz*0.541);
		head[11][8]= addBPoint(-deltax*0.0,-deltay*0.5756,deltaz*0.541);
		head[11][9]= addBPoint(deltax*0.125,-deltay*0.5664479600000001,deltaz*0.541);
		head[11][10]= addBPoint(deltax*0.25,-deltay*0.5572959200000001,deltaz*0.541);
		head[11][11]= addBPoint(deltax*0.375,-deltay*0.52788276,deltaz*0.541);
		head[11][12]= addBPoint(deltax*0.5,-deltay*0.4984696,deltaz*0.541);
		head[11][13]= addBPoint(deltax*0.625,-deltay*0.43958572,deltaz*0.541);
		head[11][14]= addBPoint(deltax*0.75,-deltay*0.38070183999999996,deltaz*0.541);
		head[11][15]= addBPoint(deltax*0.875,-deltay*0.19035091999999998,deltaz*0.541);
		head[11][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.541);
		head[11][17]= addBPoint(deltax*1.0,deltay*0.5840737,deltaz*0.541);
		head[11][18]= addBPoint(deltax*0.875,deltay*0.604644425,deltaz*0.541);
		head[11][19]= addBPoint(deltax*0.75,deltay*0.62521515,deltaz*0.541);
		head[11][20]= addBPoint(deltax*0.625,deltay*0.6391088199999999,deltaz*0.541);
		head[11][21]= addBPoint(deltax*0.5,deltay*0.65300249,deltaz*0.541);
		head[11][22]= addBPoint(deltax*0.375,deltay*0.6610958899999999,deltaz*0.541);
		head[11][23]= addBPoint(deltax*0.25,deltay*0.66918929,deltaz*0.541);
		head[11][24]= addBPoint(deltax*0.125,deltay*0.671819645,deltaz*0.541);
		head[11][25]= addBPoint(deltax*0.0,deltay*0.67445,deltaz*0.541);
		head[11][26]= addBPoint(-deltax*0.125,deltay*0.671819645,deltaz*0.541);
		head[11][27]= addBPoint(-deltax*0.25,deltay*0.66918929,deltaz*0.541);
		head[11][28]= addBPoint(-deltax*0.375,deltay*0.6610958899999999,deltaz*0.541);
		head[11][29]= addBPoint(-deltax*0.5,deltay*0.65300249,deltaz*0.541);
		head[11][30]= addBPoint(-deltax*0.625,deltay*0.6391088199999999,deltaz*0.541);
		head[11][31]= addBPoint(-deltax*0.75,deltay*0.62521515,deltaz*0.541);
		head[11][32]= addBPoint(-deltax*0.875,deltay*0.604644425,deltaz*0.541);
		head[11][33]= addBPoint(-deltax*1.0,deltay*0.5840737,deltaz*0.541);

		head[12][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.6066);
		head[12][1]= addBPoint(-deltax*0.875,-deltay*0.19226898,deltaz*0.6066);
		head[12][2]= addBPoint(-deltax*0.75,-deltay*0.38453796,deltaz*0.6066);
		head[12][3]= addBPoint(-deltax*0.625,-deltay*0.44401518000000006,deltaz*0.6066);
		head[12][4]= addBPoint(-deltax*0.5,-deltay*0.5034924000000001,deltaz*0.6066);
		head[12][5]= addBPoint(-deltax*0.375,-deltay*0.5332019400000001,deltaz*0.6066);
		head[12][6]= addBPoint(-deltax*0.25,-deltay*0.56291148,deltaz*0.6066);
		head[12][7]= addBPoint(-deltax*0.125,-deltay*0.57215574,deltaz*0.6066);
		head[12][8]= addBPoint(-deltax*0.0,-deltay*0.5814,deltaz*0.6066);
		head[12][9]= addBPoint(deltax*0.125,-deltay*0.57215574,deltaz*0.6066);
		head[12][10]= addBPoint(deltax*0.25,-deltay*0.56291148,deltaz*0.6066);
		head[12][11]= addBPoint(deltax*0.375,-deltay*0.5332019400000001,deltaz*0.6066);
		head[12][12]= addBPoint(deltax*0.5,-deltay*0.5034924000000001,deltaz*0.6066);
		head[12][13]= addBPoint(deltax*0.625,-deltay*0.44401518000000006,deltaz*0.6066);
		head[12][14]= addBPoint(deltax*0.75,-deltay*0.38453796,deltaz*0.6066);
		head[12][15]= addBPoint(deltax*0.875,-deltay*0.19226898,deltaz*0.6066);
		head[12][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.6066);
		head[12][17]= addBPoint(deltax*1.0,deltay*0.5739848,deltaz*0.6066);
		head[12][18]= addBPoint(deltax*0.875,deltay*0.5942002,deltaz*0.6066);
		head[12][19]= addBPoint(deltax*0.75,deltay*0.6144156,deltaz*0.6066);
		head[12][20]= addBPoint(deltax*0.625,deltay*0.62806928,deltaz*0.6066);
		head[12][21]= addBPoint(deltax*0.5,deltay*0.64172296,deltaz*0.6066);
		head[12][22]= addBPoint(deltax*0.375,deltay*0.64967656,deltaz*0.6066);
		head[12][23]= addBPoint(deltax*0.25,deltay*0.65763016,deltaz*0.6066);
		head[12][24]= addBPoint(deltax*0.125,deltay*0.66021508,deltaz*0.6066);
		head[12][25]= addBPoint(deltax*0.0,deltay*0.6628,deltaz*0.6066);
		head[12][26]= addBPoint(-deltax*0.125,deltay*0.66021508,deltaz*0.6066);
		head[12][27]= addBPoint(-deltax*0.25,deltay*0.65763016,deltaz*0.6066);
		head[12][28]= addBPoint(-deltax*0.375,deltay*0.64967656,deltaz*0.6066);
		head[12][29]= addBPoint(-deltax*0.5,deltay*0.64172296,deltaz*0.6066);
		head[12][30]= addBPoint(-deltax*0.625,deltay*0.62806928,deltaz*0.6066);
		head[12][31]= addBPoint(-deltax*0.75,deltay*0.6144156,deltaz*0.6066);
		head[12][32]= addBPoint(-deltax*0.875,deltay*0.5942002,deltaz*0.6066);
		head[12][33]= addBPoint(-deltax*1.0,deltay*0.5739848,deltaz*0.6066);

		head[13][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.6475500000000001);
		head[13][1]= addBPoint(-deltax*0.875,-deltay*0.19612163500000002,deltaz*0.6475500000000001);
		head[13][2]= addBPoint(-deltax*0.75,-deltay*0.39224327000000003,deltaz*0.6475500000000001);
		head[13][3]= addBPoint(-deltax*0.625,-deltay*0.452912285,deltaz*0.6475500000000001);
		head[13][4]= addBPoint(-deltax*0.5,-deltay*0.5135813,deltaz*0.6475500000000001);
		head[13][5]= addBPoint(-deltax*0.375,-deltay*0.543886155,deltaz*0.6475500000000001);
		head[13][6]= addBPoint(-deltax*0.25,-deltay*0.5741910100000001,deltaz*0.6475500000000001);
		head[13][7]= addBPoint(-deltax*0.125,-deltay*0.5836205050000001,deltaz*0.6475500000000001);
		head[13][8]= addBPoint(-deltax*0.0,-deltay*0.5930500000000001,deltaz*0.6475500000000001);
		head[13][9]= addBPoint(deltax*0.125,-deltay*0.5836205050000001,deltaz*0.6475500000000001);
		head[13][10]= addBPoint(deltax*0.25,-deltay*0.5741910100000001,deltaz*0.6475500000000001);
		head[13][11]= addBPoint(deltax*0.375,-deltay*0.543886155,deltaz*0.6475500000000001);
		head[13][12]= addBPoint(deltax*0.5,-deltay*0.5135813,deltaz*0.6475500000000001);
		head[13][13]= addBPoint(deltax*0.625,-deltay*0.452912285,deltaz*0.6475500000000001);
		head[13][14]= addBPoint(deltax*0.75,-deltay*0.39224327000000003,deltaz*0.6475500000000001);
		head[13][15]= addBPoint(deltax*0.875,-deltay*0.19612163500000002,deltaz*0.6475500000000001);
		head[13][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.6475500000000001);
		head[13][17]= addBPoint(deltax*1.0,deltay*0.5538502999999999,deltaz*0.6475500000000001);
		head[13][18]= addBPoint(deltax*0.875,deltay*0.573356575,deltaz*0.6475500000000001);
		head[13][19]= addBPoint(deltax*0.75,deltay*0.5928628499999999,deltaz*0.6475500000000001);
		head[13][20]= addBPoint(deltax*0.625,deltay*0.60603758,deltaz*0.6475500000000001);
		head[13][21]= addBPoint(deltax*0.5,deltay*0.61921231,deltaz*0.6475500000000001);
		head[13][22]= addBPoint(deltax*0.375,deltay*0.6268869099999999,deltaz*0.6475500000000001);
		head[13][23]= addBPoint(deltax*0.25,deltay*0.6345615099999999,deltaz*0.6475500000000001);
		head[13][24]= addBPoint(deltax*0.125,deltay*0.637055755,deltaz*0.6475500000000001);
		head[13][25]= addBPoint(deltax*0.0,deltay*0.63955,deltaz*0.6475500000000001);
		head[13][26]= addBPoint(-deltax*0.125,deltay*0.637055755,deltaz*0.6475500000000001);
		head[13][27]= addBPoint(-deltax*0.25,deltay*0.6345615099999999,deltaz*0.6475500000000001);
		head[13][28]= addBPoint(-deltax*0.375,deltay*0.6268869099999999,deltaz*0.6475500000000001);
		head[13][29]= addBPoint(-deltax*0.5,deltay*0.61921231,deltaz*0.6475500000000001);
		head[13][30]= addBPoint(-deltax*0.625,deltay*0.60603758,deltaz*0.6475500000000001);
		head[13][31]= addBPoint(-deltax*0.75,deltay*0.5928628499999999,deltaz*0.6475500000000001);
		head[13][32]= addBPoint(-deltax*0.875,deltay*0.573356575,deltaz*0.6475500000000001);
		head[13][33]= addBPoint(-deltax*1.0,deltay*0.5538502999999999,deltaz*0.6475500000000001);

		head[14][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.6885);
		head[14][1]= addBPoint(-deltax*0.875,-deltay*0.19997429,deltaz*0.6885);
		head[14][2]= addBPoint(-deltax*0.75,-deltay*0.39994858,deltaz*0.6885);
		head[14][3]= addBPoint(-deltax*0.625,-deltay*0.46180939,deltaz*0.6885);
		head[14][4]= addBPoint(-deltax*0.5,-deltay*0.5236702,deltaz*0.6885);
		head[14][5]= addBPoint(-deltax*0.375,-deltay*0.55457037,deltaz*0.6885);
		head[14][6]= addBPoint(-deltax*0.25,-deltay*0.58547054,deltaz*0.6885);
		head[14][7]= addBPoint(-deltax*0.125,-deltay*0.59508527,deltaz*0.6885);
		head[14][8]= addBPoint(-deltax*0.0,-deltay*0.6047,deltaz*0.6885);
		head[14][9]= addBPoint(deltax*0.125,-deltay*0.59508527,deltaz*0.6885);
		head[14][10]= addBPoint(deltax*0.25,-deltay*0.58547054,deltaz*0.6885);
		head[14][11]= addBPoint(deltax*0.375,-deltay*0.55457037,deltaz*0.6885);
		head[14][12]= addBPoint(deltax*0.5,-deltay*0.5236702,deltaz*0.6885);
		head[14][13]= addBPoint(deltax*0.625,-deltay*0.46180939,deltaz*0.6885);
		head[14][14]= addBPoint(deltax*0.75,-deltay*0.39994858,deltaz*0.6885);
		head[14][15]= addBPoint(deltax*0.875,-deltay*0.19997429,deltaz*0.6885);
		head[14][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.6885);
		head[14][17]= addBPoint(deltax*1.0,deltay*0.5337158,deltaz*0.6885);
		head[14][18]= addBPoint(deltax*0.875,deltay*0.55251295,deltaz*0.6885);
		head[14][19]= addBPoint(deltax*0.75,deltay*0.5713101,deltaz*0.6885);
		head[14][20]= addBPoint(deltax*0.625,deltay*0.58400588,deltaz*0.6885);
		head[14][21]= addBPoint(deltax*0.5,deltay*0.5967016599999999,deltaz*0.6885);
		head[14][22]= addBPoint(deltax*0.375,deltay*0.6040972599999999,deltaz*0.6885);
		head[14][23]= addBPoint(deltax*0.25,deltay*0.6114928599999999,deltaz*0.6885);
		head[14][24]= addBPoint(deltax*0.125,deltay*0.6138964299999999,deltaz*0.6885);
		head[14][25]= addBPoint(deltax*0.0,deltay*0.6163,deltaz*0.6885);
		head[14][26]= addBPoint(-deltax*0.125,deltay*0.6138964299999999,deltaz*0.6885);
		head[14][27]= addBPoint(-deltax*0.25,deltay*0.6114928599999999,deltaz*0.6885);
		head[14][28]= addBPoint(-deltax*0.375,deltay*0.6040972599999999,deltaz*0.6885);
		head[14][29]= addBPoint(-deltax*0.5,deltay*0.5967016599999999,deltaz*0.6885);
		head[14][30]= addBPoint(-deltax*0.625,deltay*0.58400588,deltaz*0.6885);
		head[14][31]= addBPoint(-deltax*0.75,deltay*0.5713101,deltaz*0.6885);
		head[14][32]= addBPoint(-deltax*0.875,deltay*0.55251295,deltaz*0.6885);
		head[14][33]= addBPoint(-deltax*1.0,deltay*0.5337158,deltaz*0.6885);

		head[15][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.7705);
		head[15][1]= addBPoint(-deltax*0.875,-deltay*0.177437085,deltaz*0.7705);
		head[15][2]= addBPoint(-deltax*0.75,-deltay*0.35487417,deltaz*0.7705);
		head[15][3]= addBPoint(-deltax*0.625,-deltay*0.409763235,deltaz*0.7705);
		head[15][4]= addBPoint(-deltax*0.5,-deltay*0.4646523,deltaz*0.7705);
		head[15][5]= addBPoint(-deltax*0.375,-deltay*0.492070005,deltaz*0.7705);
		head[15][6]= addBPoint(-deltax*0.25,-deltay*0.5194877099999999,deltaz*0.7705);
		head[15][7]= addBPoint(-deltax*0.125,-deltay*0.528018855,deltaz*0.7705);
		head[15][8]= addBPoint(-deltax*0.0,-deltay*0.53655,deltaz*0.7705);
		head[15][9]= addBPoint(deltax*0.125,-deltay*0.528018855,deltaz*0.7705);
		head[15][10]= addBPoint(deltax*0.25,-deltay*0.5194877099999999,deltaz*0.7705);
		head[15][11]= addBPoint(deltax*0.375,-deltay*0.492070005,deltaz*0.7705);
		head[15][12]= addBPoint(deltax*0.5,-deltay*0.4646523,deltaz*0.7705);
		head[15][13]= addBPoint(deltax*0.625,-deltay*0.409763235,deltaz*0.7705);
		head[15][14]= addBPoint(deltax*0.75,-deltay*0.35487417,deltaz*0.7705);
		head[15][15]= addBPoint(deltax*0.875,-deltay*0.177437085,deltaz*0.7705);
		head[15][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.7705);
		head[15][17]= addBPoint(deltax*1.0,deltay*0.4733123,deltaz*0.7705);
		head[15][18]= addBPoint(deltax*0.875,deltay*0.489982075,deltaz*0.7705);
		head[15][19]= addBPoint(deltax*0.75,deltay*0.50665185,deltaz*0.7705);
		head[15][20]= addBPoint(deltax*0.625,deltay*0.51791078,deltaz*0.7705);
		head[15][21]= addBPoint(deltax*0.5,deltay*0.5291697099999999,deltaz*0.7705);
		head[15][22]= addBPoint(deltax*0.375,deltay*0.5357283099999999,deltaz*0.7705);
		head[15][23]= addBPoint(deltax*0.25,deltay*0.54228691,deltaz*0.7705);
		head[15][24]= addBPoint(deltax*0.125,deltay*0.544418455,deltaz*0.7705);
		head[15][25]= addBPoint(deltax*0.0,deltay*0.54655,deltaz*0.7705);
		head[15][26]= addBPoint(-deltax*0.125,deltay*0.544418455,deltaz*0.7705);
		head[15][27]= addBPoint(-deltax*0.25,deltay*0.54228691,deltaz*0.7705);
		head[15][28]= addBPoint(-deltax*0.375,deltay*0.5357283099999999,deltaz*0.7705);
		head[15][29]= addBPoint(-deltax*0.5,deltay*0.5291697099999999,deltaz*0.7705);
		head[15][30]= addBPoint(-deltax*0.625,deltay*0.51791078,deltaz*0.7705);
		head[15][31]= addBPoint(-deltax*0.75,deltay*0.50665185,deltaz*0.7705);
		head[15][32]= addBPoint(-deltax*0.875,deltay*0.489982075,deltaz*0.7705);
		head[15][33]= addBPoint(-deltax*1.0,deltay*0.4733123,deltaz*0.7705);

		head[16][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.8525);
		head[16][1]= addBPoint(-deltax*0.875,-deltay*0.15489988,deltaz*0.8525);
		head[16][2]= addBPoint(-deltax*0.75,-deltay*0.30979976,deltaz*0.8525);
		head[16][3]= addBPoint(-deltax*0.625,-deltay*0.35771708,deltaz*0.8525);
		head[16][4]= addBPoint(-deltax*0.5,-deltay*0.4056344,deltaz*0.8525);
		head[16][5]= addBPoint(-deltax*0.375,-deltay*0.42956964,deltaz*0.8525);
		head[16][6]= addBPoint(-deltax*0.25,-deltay*0.45350487999999994,deltaz*0.8525);
		head[16][7]= addBPoint(-deltax*0.125,-deltay*0.46095243999999996,deltaz*0.8525);
		head[16][8]= addBPoint(-deltax*0.0,-deltay*0.4684,deltaz*0.8525);
		head[16][9]= addBPoint(deltax*0.125,-deltay*0.46095243999999996,deltaz*0.8525);
		head[16][10]= addBPoint(deltax*0.25,-deltay*0.45350487999999994,deltaz*0.8525);
		head[16][11]= addBPoint(deltax*0.375,-deltay*0.42956964,deltaz*0.8525);
		head[16][12]= addBPoint(deltax*0.5,-deltay*0.4056344,deltaz*0.8525);
		head[16][13]= addBPoint(deltax*0.625,-deltay*0.35771708,deltaz*0.8525);
		head[16][14]= addBPoint(deltax*0.75,-deltay*0.30979976,deltaz*0.8525);
		head[16][15]= addBPoint(deltax*0.875,-deltay*0.15489988,deltaz*0.8525);
		head[16][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.8525);
		head[16][17]= addBPoint(deltax*1.0,deltay*0.4129088,deltaz*0.8525);
		head[16][18]= addBPoint(deltax*0.875,deltay*0.42745120000000003,deltaz*0.8525);
		head[16][19]= addBPoint(deltax*0.75,deltay*0.44199360000000004,deltaz*0.8525);
		head[16][20]= addBPoint(deltax*0.625,deltay*0.45181568,deltaz*0.8525);
		head[16][21]= addBPoint(deltax*0.5,deltay*0.46163775999999995,deltaz*0.8525);
		head[16][22]= addBPoint(deltax*0.375,deltay*0.46735936,deltaz*0.8525);
		head[16][23]= addBPoint(deltax*0.25,deltay*0.47308096,deltaz*0.8525);
		head[16][24]= addBPoint(deltax*0.125,deltay*0.47494048,deltaz*0.8525);
		head[16][25]= addBPoint(deltax*0.0,deltay*0.4768,deltaz*0.8525);
		head[16][26]= addBPoint(-deltax*0.125,deltay*0.47494048,deltaz*0.8525);
		head[16][27]= addBPoint(-deltax*0.25,deltay*0.47308096,deltaz*0.8525);
		head[16][28]= addBPoint(-deltax*0.375,deltay*0.46735936,deltaz*0.8525);
		head[16][29]= addBPoint(-deltax*0.5,deltay*0.46163775999999995,deltaz*0.8525);
		head[16][30]= addBPoint(-deltax*0.625,deltay*0.45181568,deltaz*0.8525);
		head[16][31]= addBPoint(-deltax*0.75,deltay*0.44199360000000004,deltaz*0.8525);
		head[16][32]= addBPoint(-deltax*0.875,deltay*0.42745120000000003,deltaz*0.8525);
		head[16][33]= addBPoint(-deltax*1.0,deltay*0.4129088,deltaz*0.8525);

		head[17][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.8934500000000001);
		head[17][1]= addBPoint(-deltax*0.875,-deltay*0.137058615,deltaz*0.8934500000000001);
		head[17][2]= addBPoint(-deltax*0.75,-deltay*0.27411723,deltaz*0.8934500000000001);
		head[17][3]= addBPoint(-deltax*0.625,-deltay*0.316515465,deltaz*0.8934500000000001);
		head[17][4]= addBPoint(-deltax*0.5,-deltay*0.3589137,deltaz*0.8934500000000001);
		head[17][5]= addBPoint(-deltax*0.375,-deltay*0.38009209499999996,deltaz*0.8934500000000001);
		head[17][6]= addBPoint(-deltax*0.25,-deltay*0.4012704899999999,deltaz*0.8934500000000001);
		head[17][7]= addBPoint(-deltax*0.125,-deltay*0.40786024499999995,deltaz*0.8934500000000001);
		head[17][8]= addBPoint(-deltax*0.0,-deltay*0.41445,deltaz*0.8934500000000001);
		head[17][9]= addBPoint(deltax*0.125,-deltay*0.40786024499999995,deltaz*0.8934500000000001);
		head[17][10]= addBPoint(deltax*0.25,-deltay*0.4012704899999999,deltaz*0.8934500000000001);
		head[17][11]= addBPoint(deltax*0.375,-deltay*0.38009209499999996,deltaz*0.8934500000000001);
		head[17][12]= addBPoint(deltax*0.5,-deltay*0.3589137,deltaz*0.8934500000000001);
		head[17][13]= addBPoint(deltax*0.625,-deltay*0.316515465,deltaz*0.8934500000000001);
		head[17][14]= addBPoint(deltax*0.75,-deltay*0.27411723,deltaz*0.8934500000000001);
		head[17][15]= addBPoint(deltax*0.875,-deltay*0.137058615,deltaz*0.8934500000000001);
		head[17][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.8934500000000001);
		head[17][17]= addBPoint(deltax*1.0,deltay*0.3675737,deltaz*0.8934500000000001);
		head[17][18]= addBPoint(deltax*0.875,deltay*0.380519425,deltaz*0.8934500000000001);
		head[17][19]= addBPoint(deltax*0.75,deltay*0.39346515000000004,deltaz*0.8934500000000001);
		head[17][20]= addBPoint(deltax*0.625,deltay*0.40220882,deltaz*0.8934500000000001);
		head[17][21]= addBPoint(deltax*0.5,deltay*0.41095248999999995,deltaz*0.8934500000000001);
		head[17][22]= addBPoint(deltax*0.375,deltay*0.41604589,deltaz*0.8934500000000001);
		head[17][23]= addBPoint(deltax*0.25,deltay*0.42113928999999994,deltaz*0.8934500000000001);
		head[17][24]= addBPoint(deltax*0.125,deltay*0.42279464499999997,deltaz*0.8934500000000001);
		head[17][25]= addBPoint(deltax*0.0,deltay*0.42445,deltaz*0.8934500000000001);
		head[17][26]= addBPoint(-deltax*0.125,deltay*0.42279464499999997,deltaz*0.8934500000000001);
		head[17][27]= addBPoint(-deltax*0.25,deltay*0.42113928999999994,deltaz*0.8934500000000001);
		head[17][28]= addBPoint(-deltax*0.375,deltay*0.41604589,deltaz*0.8934500000000001);
		head[17][29]= addBPoint(-deltax*0.5,deltay*0.41095248999999995,deltaz*0.8934500000000001);
		head[17][30]= addBPoint(-deltax*0.625,deltay*0.40220882,deltaz*0.8934500000000001);
		head[17][31]= addBPoint(-deltax*0.75,deltay*0.39346515000000004,deltaz*0.8934500000000001);
		head[17][32]= addBPoint(-deltax*0.875,deltay*0.380519425,deltaz*0.8934500000000001);
		head[17][33]= addBPoint(-deltax*1.0,deltay*0.3675737,deltaz*0.8934500000000001);

		head[18][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.9344);
		head[18][1]= addBPoint(-deltax*0.875,-deltay*0.11921735,deltaz*0.9344);
		head[18][2]= addBPoint(-deltax*0.75,-deltay*0.2384347,deltaz*0.9344);
		head[18][3]= addBPoint(-deltax*0.625,-deltay*0.27531384999999997,deltaz*0.9344);
		head[18][4]= addBPoint(-deltax*0.5,-deltay*0.312193,deltaz*0.9344);
		head[18][5]= addBPoint(-deltax*0.375,-deltay*0.33061454999999995,deltaz*0.9344);
		head[18][6]= addBPoint(-deltax*0.25,-deltay*0.34903609999999996,deltaz*0.9344);
		head[18][7]= addBPoint(-deltax*0.125,-deltay*0.35476805,deltaz*0.9344);
		head[18][8]= addBPoint(-deltax*0.0,-deltay*0.3605,deltaz*0.9344);
		head[18][9]= addBPoint(deltax*0.125,-deltay*0.35476805,deltaz*0.9344);
		head[18][10]= addBPoint(deltax*0.25,-deltay*0.34903609999999996,deltaz*0.9344);
		head[18][11]= addBPoint(deltax*0.375,-deltay*0.33061454999999995,deltaz*0.9344);
		head[18][12]= addBPoint(deltax*0.5,-deltay*0.312193,deltaz*0.9344);
		head[18][13]= addBPoint(deltax*0.625,-deltay*0.27531384999999997,deltaz*0.9344);
		head[18][14]= addBPoint(deltax*0.75,-deltay*0.2384347,deltaz*0.9344);
		head[18][15]= addBPoint(deltax*0.875,-deltay*0.11921735,deltaz*0.9344);
		head[18][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.9344);
		head[18][17]= addBPoint(deltax*1.0,deltay*0.3222386,deltaz*0.9344);
		head[18][18]= addBPoint(deltax*0.875,deltay*0.33358765,deltaz*0.9344);
		head[18][19]= addBPoint(deltax*0.75,deltay*0.3449367,deltaz*0.9344);
		head[18][20]= addBPoint(deltax*0.625,deltay*0.35260196,deltaz*0.9344);
		head[18][21]= addBPoint(deltax*0.5,deltay*0.36026721999999994,deltaz*0.9344);
		head[18][22]= addBPoint(deltax*0.375,deltay*0.36473241999999995,deltaz*0.9344);
		head[18][23]= addBPoint(deltax*0.25,deltay*0.36919761999999995,deltaz*0.9344);
		head[18][24]= addBPoint(deltax*0.125,deltay*0.37064880999999994,deltaz*0.9344);
		head[18][25]= addBPoint(deltax*0.0,deltay*0.3721,deltaz*0.9344);
		head[18][26]= addBPoint(-deltax*0.125,deltay*0.37064880999999994,deltaz*0.9344);
		head[18][27]= addBPoint(-deltax*0.25,deltay*0.36919761999999995,deltaz*0.9344);
		head[18][28]= addBPoint(-deltax*0.375,deltay*0.36473241999999995,deltaz*0.9344);
		head[18][29]= addBPoint(-deltax*0.5,deltay*0.36026721999999994,deltaz*0.9344);
		head[18][30]= addBPoint(-deltax*0.625,deltay*0.35260196,deltaz*0.9344);
		head[18][31]= addBPoint(-deltax*0.75,deltay*0.3449367,deltaz*0.9344);
		head[18][32]= addBPoint(-deltax*0.875,deltay*0.33358765,deltaz*0.9344);
		head[18][33]= addBPoint(-deltax*1.0,deltay*0.3222386,deltaz*0.9344);

		head[19][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.9672000000000001);
		head[19][1]= addBPoint(-deltax*0.875,-deltay*0.059608675,deltaz*0.9672000000000001);
		head[19][2]= addBPoint(-deltax*0.75,-deltay*0.11921735,deltaz*0.9672000000000001);
		head[19][3]= addBPoint(-deltax*0.625,-deltay*0.13765692499999999,deltaz*0.9672000000000001);
		head[19][4]= addBPoint(-deltax*0.5,-deltay*0.1560965,deltaz*0.9672000000000001);
		head[19][5]= addBPoint(-deltax*0.375,-deltay*0.16530727499999998,deltaz*0.9672000000000001);
		head[19][6]= addBPoint(-deltax*0.25,-deltay*0.17451804999999998,deltaz*0.9672000000000001);
		head[19][7]= addBPoint(-deltax*0.125,-deltay*0.177384025,deltaz*0.9672000000000001);
		head[19][8]= addBPoint(-deltax*0.0,-deltay*0.18025,deltaz*0.9672000000000001);
		head[19][9]= addBPoint(deltax*0.125,-deltay*0.177384025,deltaz*0.9672000000000001);
		head[19][10]= addBPoint(deltax*0.25,-deltay*0.17451804999999998,deltaz*0.9672000000000001);
		head[19][11]= addBPoint(deltax*0.375,-deltay*0.16530727499999998,deltaz*0.9672000000000001);
		head[19][12]= addBPoint(deltax*0.5,-deltay*0.1560965,deltaz*0.9672000000000001);
		head[19][13]= addBPoint(deltax*0.625,-deltay*0.13765692499999999,deltaz*0.9672000000000001);
		head[19][14]= addBPoint(deltax*0.75,-deltay*0.11921735,deltaz*0.9672000000000001);
		head[19][15]= addBPoint(deltax*0.875,-deltay*0.059608675,deltaz*0.9672000000000001);
		head[19][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.9672000000000001);
		head[19][17]= addBPoint(deltax*1.0,deltay*0.1611193,deltaz*0.9672000000000001);
		head[19][18]= addBPoint(deltax*0.875,deltay*0.166793825,deltaz*0.9672000000000001);
		head[19][19]= addBPoint(deltax*0.75,deltay*0.17246835,deltaz*0.9672000000000001);
		head[19][20]= addBPoint(deltax*0.625,deltay*0.17630098,deltaz*0.9672000000000001);
		head[19][21]= addBPoint(deltax*0.5,deltay*0.18013360999999997,deltaz*0.9672000000000001);
		head[19][22]= addBPoint(deltax*0.375,deltay*0.18236620999999997,deltaz*0.9672000000000001);
		head[19][23]= addBPoint(deltax*0.25,deltay*0.18459880999999997,deltaz*0.9672000000000001);
		head[19][24]= addBPoint(deltax*0.125,deltay*0.18532440499999997,deltaz*0.9672000000000001);
		head[19][25]= addBPoint(deltax*0.0,deltay*0.18605,deltaz*0.9672000000000001);
		head[19][26]= addBPoint(-deltax*0.125,deltay*0.18532440499999997,deltaz*0.9672000000000001);
		head[19][27]= addBPoint(-deltax*0.25,deltay*0.18459880999999997,deltaz*0.9672000000000001);
		head[19][28]= addBPoint(-deltax*0.375,deltay*0.18236620999999997,deltaz*0.9672000000000001);
		head[19][29]= addBPoint(-deltax*0.5,deltay*0.18013360999999997,deltaz*0.9672000000000001);
		head[19][30]= addBPoint(-deltax*0.625,deltay*0.17630098,deltaz*0.9672000000000001);
		head[19][31]= addBPoint(-deltax*0.75,deltay*0.17246835,deltaz*0.9672000000000001);
		head[19][32]= addBPoint(-deltax*0.875,deltay*0.166793825,deltaz*0.9672000000000001);
		head[19][33]= addBPoint(-deltax*1.0,deltay*0.1611193,deltaz*0.9672000000000001);

		head[20][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*1.0);
		head[20][1]= addBPoint(-deltax*0.875,-deltay*0.0,deltaz*1.0);
		head[20][2]= addBPoint(-deltax*0.75,-deltay*0.0,deltaz*1.0);
		head[20][3]= addBPoint(-deltax*0.625,-deltay*0.0,deltaz*1.0);
		head[20][4]= addBPoint(-deltax*0.5,-deltay*0.0,deltaz*1.0);
		head[20][5]= addBPoint(-deltax*0.375,-deltay*0.0,deltaz*1.0);
		head[20][6]= addBPoint(-deltax*0.25,-deltay*0.0,deltaz*1.0);
		head[20][7]= addBPoint(-deltax*0.125,-deltay*0.0,deltaz*1.0);
		head[20][8]= addBPoint(-deltax*0.0,-deltay*0.0,deltaz*1.0);
		head[20][9]= addBPoint(deltax*0.125,-deltay*0.0,deltaz*1.0);
		head[20][10]= addBPoint(deltax*0.25,-deltay*0.0,deltaz*1.0);
		head[20][11]= addBPoint(deltax*0.375,-deltay*0.0,deltaz*1.0);
		head[20][12]= addBPoint(deltax*0.5,-deltay*0.0,deltaz*1.0);
		head[20][13]= addBPoint(deltax*0.625,-deltay*0.0,deltaz*1.0);
		head[20][14]= addBPoint(deltax*0.75,-deltay*0.0,deltaz*1.0);
		head[20][15]= addBPoint(deltax*0.875,-deltay*0.0,deltaz*1.0);
		head[20][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*1.0);
		head[20][17]= addBPoint(deltax*1.0,deltay*0.0,deltaz*1.0);
		head[20][18]= addBPoint(deltax*0.875,deltay*0.0,deltaz*1.0);
		head[20][19]= addBPoint(deltax*0.75,deltay*0.0,deltaz*1.0);
		head[20][20]= addBPoint(deltax*0.625,deltay*0.0,deltaz*1.0);
		head[20][21]= addBPoint(deltax*0.5,deltay*0.0,deltaz*1.0);
		head[20][22]= addBPoint(deltax*0.375,deltay*0.0,deltaz*1.0);
		head[20][23]= addBPoint(deltax*0.25,deltay*0.0,deltaz*1.0);
		head[20][24]= addBPoint(deltax*0.125,deltay*0.0,deltaz*1.0);
		head[20][25]= addBPoint(deltax*0.0,deltay*0.0,deltaz*1.0);
		head[20][26]= addBPoint(-deltax*0.125,deltay*0.0,deltaz*1.0);
		head[20][27]= addBPoint(-deltax*0.25,deltay*0.0,deltaz*1.0);
		head[20][28]= addBPoint(-deltax*0.375,deltay*0.0,deltaz*1.0);
		head[20][29]= addBPoint(-deltax*0.5,deltay*0.0,deltaz*1.0);
		head[20][30]= addBPoint(-deltax*0.625,deltay*0.0,deltaz*1.0);
		head[20][31]= addBPoint(-deltax*0.75,deltay*0.0,deltaz*1.0);
		head[20][32]= addBPoint(-deltax*0.875,deltay*0.0,deltaz*1.0);
		head[20][33]= addBPoint(-deltax*1.0,deltay*0.0,deltaz*1.0);




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
	
	public static void main(String[] args) {
		//new Head1Model(0, 0, 0).writeNewCode();
	}


	public void writeNewCode(){



		String[] codes={


				"head[0][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.0);",
				"head[0][1]= addBPoint(-deltax*0.875,-deltay*0.16151388,deltaz*0.0);",
				"head[0][2]= addBPoint(-deltax*0.75,-deltay*0.32302776,deltaz*0.0);",
				"head[0][3]= addBPoint(-deltax*0.625,-deltay*0.37299108000000003,deltaz*0.0);",
				"head[0][4]= addBPoint(-deltax*0.5,-deltay*0.4229544,deltaz*0.0);",
				"head[0][5]= addBPoint(-deltax*0.375,-deltay*0.44791164,deltaz*0.0);",
				"head[0][6]= addBPoint(-deltax*0.25,-deltay*0.47286888,deltaz*0.0);",
				"head[0][7]= addBPoint(-deltax*0.125,-deltay*0.48063444,deltaz*0.0);",
				"head[0][8]= addBPoint(-deltax*0.0,-deltay*0.4884,deltaz*0.0);",
				"head[0][9]= addBPoint(deltax*0.125,-deltay*0.48063444,deltaz*0.0);",
				"head[0][10]= addBPoint(deltax*0.25,-deltay*0.47286888,deltaz*0.0);",
				"head[0][11]= addBPoint(deltax*0.375,-deltay*0.44791164,deltaz*0.0);",
				"head[0][12]= addBPoint(deltax*0.5,-deltay*0.4229544,deltaz*0.0);",
				"head[0][13]= addBPoint(deltax*0.625,-deltay*0.37299108000000003,deltaz*0.0);",
				"head[0][14]= addBPoint(deltax*0.75,-deltay*0.32302776,deltaz*0.0);",
				"head[0][15]= addBPoint(deltax*0.875,-deltay*0.16151388,deltaz*0.0);",
				"head[0][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.0);",
				"head[0][17]= addBPoint(deltax*1.0,deltay*0.5337158,deltaz*0.0);",
				"head[0][18]= addBPoint(deltax*0.875,deltay*0.55251295,deltaz*0.0);",
				"head[0][19]= addBPoint(deltax*0.75,deltay*0.5713101,deltaz*0.0);",
				"head[0][20]= addBPoint(deltax*0.625,deltay*0.58400588,deltaz*0.0);",
				"head[0][21]= addBPoint(deltax*0.5,deltay*0.5967016599999999,deltaz*0.0);",
				"head[0][22]= addBPoint(deltax*0.375,deltay*0.6040972599999999,deltaz*0.0);",
				"head[0][23]= addBPoint(deltax*0.25,deltay*0.6114928599999999,deltaz*0.0);",
				"head[0][24]= addBPoint(deltax*0.125,deltay*0.6138964299999999,deltaz*0.0);",
				"head[0][25]= addBPoint(deltax*0.0,deltay*0.6163,deltaz*0.0);",
				"head[0][26]= addBPoint(-deltax*0.125,deltay*0.6138964299999999,deltaz*0.0);",
				"head[0][27]= addBPoint(-deltax*0.25,deltay*0.6114928599999999,deltaz*0.0);",
				"head[0][28]= addBPoint(-deltax*0.375,deltay*0.6040972599999999,deltaz*0.0);",
				"head[0][29]= addBPoint(-deltax*0.5,deltay*0.5967016599999999,deltaz*0.0);",
				"head[0][30]= addBPoint(-deltax*0.625,deltay*0.58400588,deltaz*0.0);",
				"head[0][31]= addBPoint(-deltax*0.75,deltay*0.5713101,deltaz*0.0);",
				"head[0][32]= addBPoint(-deltax*0.875,deltay*0.55251295,deltaz*0.0);",
				"head[0][33]= addBPoint(-deltax*1.0,deltay*0.5337158,deltaz*0.0);",

				"head[1][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.1148);",
				"head[1][1]= addBPoint(-deltax*0.875,-deltay*0.13846409,deltaz*0.1148);",
				"head[1][2]= addBPoint(-deltax*0.75,-deltay*0.27692818,deltaz*0.1148);",
				"head[1][3]= addBPoint(-deltax*0.625,-deltay*0.31976119000000003,deltaz*0.1148);",
				"head[1][4]= addBPoint(-deltax*0.5,-deltay*0.36259420000000003,deltaz*0.1148);",
				"head[1][5]= addBPoint(-deltax*0.375,-deltay*0.38398977,deltaz*0.1148);",
				"head[1][6]= addBPoint(-deltax*0.25,-deltay*0.40538534,deltaz*0.1148);",
				"head[1][7]= addBPoint(-deltax*0.125,-deltay*0.41204267,deltaz*0.1148);",
				"head[1][8]= addBPoint(-deltax*0.0,-deltay*0.4187,deltaz*0.1148);",
				"head[1][9]= addBPoint(deltax*0.125,-deltay*0.41204267,deltaz*0.1148);",
				"head[1][10]= addBPoint(deltax*0.25,-deltay*0.40538534,deltaz*0.1148);",
				"head[1][11]= addBPoint(deltax*0.375,-deltay*0.38398977,deltaz*0.1148);",
				"head[1][12]= addBPoint(deltax*0.5,-deltay*0.36259420000000003,deltaz*0.1148);",
				"head[1][13]= addBPoint(deltax*0.625,-deltay*0.31976119000000003,deltaz*0.1148);",
				"head[1][14]= addBPoint(deltax*0.75,-deltay*0.27692818,deltaz*0.1148);",
				"head[1][15]= addBPoint(deltax*0.875,-deltay*0.13846409,deltaz*0.1148);",
				"head[1][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.1148);",
				"head[1][17]= addBPoint(deltax*1.0,deltay*0.5538936,deltaz*0.1148);",
				"head[1][18]= addBPoint(deltax*0.875,deltay*0.5734014000000001,deltaz*0.1148);",
				"head[1][19]= addBPoint(deltax*0.75,deltay*0.5929092,deltaz*0.1148);",
				"head[1][20]= addBPoint(deltax*0.625,deltay*0.60608496,deltaz*0.1148);",
				"head[1][21]= addBPoint(deltax*0.5,deltay*0.6192607199999999,deltaz*0.1148);",
				"head[1][22]= addBPoint(deltax*0.375,deltay*0.62693592,deltaz*0.1148);",
				"head[1][23]= addBPoint(deltax*0.25,deltay*0.63461112,deltaz*0.1148);",
				"head[1][24]= addBPoint(deltax*0.125,deltay*0.63710556,deltaz*0.1148);",
				"head[1][25]= addBPoint(deltax*0.0,deltay*0.6396,deltaz*0.1148);",
				"head[1][26]= addBPoint(-deltax*0.125,deltay*0.63710556,deltaz*0.1148);",
				"head[1][27]= addBPoint(-deltax*0.25,deltay*0.63461112,deltaz*0.1148);",
				"head[1][28]= addBPoint(-deltax*0.375,deltay*0.62693592,deltaz*0.1148);",
				"head[1][29]= addBPoint(-deltax*0.5,deltay*0.6192607199999999,deltaz*0.1148);",
				"head[1][30]= addBPoint(-deltax*0.625,deltay*0.60608496,deltaz*0.1148);",
				"head[1][31]= addBPoint(-deltax*0.75,deltay*0.5929092,deltaz*0.1148);",
				"head[1][32]= addBPoint(-deltax*0.875,deltay*0.5734014000000001,deltaz*0.1148);",
				"head[1][33]= addBPoint(-deltax*1.0,deltay*0.5538936,deltaz*0.1148);",

				"head[2][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.1803);",
				"head[2][1]= addBPoint(-deltax*0.875,-deltay*0.13846409,deltaz*0.1803);",
				"head[2][2]= addBPoint(-deltax*0.75,-deltay*0.27692818,deltaz*0.1803);",
				"head[2][3]= addBPoint(-deltax*0.625,-deltay*0.31976119000000003,deltaz*0.1803);",
				"head[2][4]= addBPoint(-deltax*0.5,-deltay*0.36259420000000003,deltaz*0.1803);",
				"head[2][5]= addBPoint(-deltax*0.375,-deltay*0.38398977,deltaz*0.1803);",
				"head[2][6]= addBPoint(-deltax*0.25,-deltay*0.40538534,deltaz*0.1803);",
				"head[2][7]= addBPoint(-deltax*0.125,-deltay*0.41204267,deltaz*0.1803);",
				"head[2][8]= addBPoint(-deltax*0.0,-deltay*0.4187,deltaz*0.1803);",
				"head[2][9]= addBPoint(deltax*0.125,-deltay*0.41204267,deltaz*0.1803);",
				"head[2][10]= addBPoint(deltax*0.25,-deltay*0.40538534,deltaz*0.1803);",
				"head[2][11]= addBPoint(deltax*0.375,-deltay*0.38398977,deltaz*0.1803);",
				"head[2][12]= addBPoint(deltax*0.5,-deltay*0.36259420000000003,deltaz*0.1803);",
				"head[2][13]= addBPoint(deltax*0.625,-deltay*0.31976119000000003,deltaz*0.1803);",
				"head[2][14]= addBPoint(deltax*0.75,-deltay*0.27692818,deltaz*0.1803);",
				"head[2][15]= addBPoint(deltax*0.875,-deltay*0.13846409,deltaz*0.1803);",
				"head[2][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.1803);",
				"head[2][17]= addBPoint(deltax*1.0,deltay*0.5538936,deltaz*0.1803);",
				"head[2][18]= addBPoint(deltax*0.875,deltay*0.5734014000000001,deltaz*0.1803);",
				"head[2][19]= addBPoint(deltax*0.75,deltay*0.5929092,deltaz*0.1803);",
				"head[2][20]= addBPoint(deltax*0.625,deltay*0.60608496,deltaz*0.1803);",
				"head[2][21]= addBPoint(deltax*0.5,deltay*0.6192607199999999,deltaz*0.1803);",
				"head[2][22]= addBPoint(deltax*0.375,deltay*0.62693592,deltaz*0.1803);",
				"head[2][23]= addBPoint(deltax*0.25,deltay*0.63461112,deltaz*0.1803);",
				"head[2][24]= addBPoint(deltax*0.125,deltay*0.63710556,deltaz*0.1803);",
				"head[2][25]= addBPoint(deltax*0.0,deltay*0.6396,deltaz*0.1803);",
				"head[2][26]= addBPoint(-deltax*0.125,deltay*0.63710556,deltaz*0.1803);",
				"head[2][27]= addBPoint(-deltax*0.25,deltay*0.63461112,deltaz*0.1803);",
				"head[2][28]= addBPoint(-deltax*0.375,deltay*0.62693592,deltaz*0.1803);",
				"head[2][29]= addBPoint(-deltax*0.5,deltay*0.6192607199999999,deltaz*0.1803);",
				"head[2][30]= addBPoint(-deltax*0.625,deltay*0.60608496,deltaz*0.1803);",
				"head[2][31]= addBPoint(-deltax*0.75,deltay*0.5929092,deltaz*0.1803);",
				"head[2][32]= addBPoint(-deltax*0.875,deltay*0.5734014000000001,deltaz*0.1803);",
				"head[2][33]= addBPoint(-deltax*1.0,deltay*0.5538936,deltaz*0.1803);",

				"head[3][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.2787);",
				"head[3][1]= addBPoint(-deltax*0.875,-deltay*0.15380857,deltaz*0.2787);",
				"head[3][2]= addBPoint(-deltax*0.75,-deltay*0.30761714,deltaz*0.2787);",
				"head[3][3]= addBPoint(-deltax*0.625,-deltay*0.35519687,deltaz*0.2787);",
				"head[3][4]= addBPoint(-deltax*0.5,-deltay*0.4027766,deltaz*0.2787);",
				"head[3][5]= addBPoint(-deltax*0.375,-deltay*0.42654320999999995,deltaz*0.2787);",
				"head[3][6]= addBPoint(-deltax*0.25,-deltay*0.45030981999999997,deltaz*0.2787);",
				"head[3][7]= addBPoint(-deltax*0.125,-deltay*0.45770491,deltaz*0.2787);",
				"head[3][8]= addBPoint(-deltax*0.0,-deltay*0.4651,deltaz*0.2787);",
				"head[3][9]= addBPoint(deltax*0.125,-deltay*0.45770491,deltaz*0.2787);",
				"head[3][10]= addBPoint(deltax*0.25,-deltay*0.45030981999999997,deltaz*0.2787);",
				"head[3][11]= addBPoint(deltax*0.375,-deltay*0.426543215,deltaz*0.2787);",
				"head[3][12]= addBPoint(deltax*0.5,-deltay*0.40277661,deltaz*0.2787);",
				"head[3][13]= addBPoint(deltax*0.625,-deltay*0.35519687499999997,deltaz*0.2787);",
				"head[3][14]= addBPoint(deltax*0.75,-deltay*0.30761714,deltaz*0.2787);",
				"head[3][15]= addBPoint(deltax*0.875,-deltay*0.15380857,deltaz*0.2787);",
				"head[3][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.2787);",
				"head[3][17]= addBPoint(deltax*1.0,deltay*0.5941626,deltaz*0.2787);",
				"head[3][18]= addBPoint(deltax*0.875,deltay*0.61508865,deltaz*0.2787);",
				"head[3][19]= addBPoint(deltax*0.75,deltay*0.6360147,deltaz*0.2787);",
				"head[3][20]= addBPoint(deltax*0.625,deltay*0.65014836,deltaz*0.2787);",
				"head[3][21]= addBPoint(deltax*0.5,deltay*0.66428202,deltaz*0.2787);",
				"head[3][22]= addBPoint(deltax*0.375,deltay*0.67251522,deltaz*0.2787);",
				"head[3][23]= addBPoint(deltax*0.25,deltay*0.68074842,deltaz*0.2787);",
				"head[3][24]= addBPoint(deltax*0.125,deltay*0.6834242100000001,deltaz*0.2787);",
				"head[3][25]= addBPoint(deltax*0.0,deltay*0.6861,deltaz*0.2787);",
				"head[3][26]= addBPoint(-deltax*0.125,deltay*0.6834242100000001,deltaz*0.2787);",
				"head[3][27]= addBPoint(-deltax*0.25,deltay*0.68074842,deltaz*0.2787);",
				"head[3][28]= addBPoint(-deltax*0.375,deltay*0.67251522,deltaz*0.2787);",
				"head[3][29]= addBPoint(-deltax*0.5,deltay*0.66428202,deltaz*0.2787);",
				"head[3][30]= addBPoint(-deltax*0.625,deltay*0.65014836,deltaz*0.2787);",
				"head[3][31]= addBPoint(-deltax*0.75,deltay*0.6360147,deltaz*0.2787);",
				"head[3][32]= addBPoint(-deltax*0.875,deltay*0.61508865,deltaz*0.2787);",
				"head[3][33]= addBPoint(-deltax*1.0,deltay*0.5941626,deltaz*0.2787);",

				"head[4][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.3279);",
				"head[4][1]= addBPoint(-deltax*0.875,-deltay*0.16151388,deltaz*0.3279);",
				"head[4][2]= addBPoint(-deltax*0.75,-deltay*0.32302776,deltaz*0.3279);",
				"head[4][3]= addBPoint(-deltax*0.625,-deltay*0.37299108000000003,deltaz*0.3279);",
				"head[4][4]= addBPoint(-deltax*0.5,-deltay*0.4229544,deltaz*0.3279);",
				"head[4][5]= addBPoint(-deltax*0.375,-deltay*0.44791164,deltaz*0.3279);",
				"head[4][6]= addBPoint(-deltax*0.25,-deltay*0.47286888,deltaz*0.3279);",
				"head[4][7]= addBPoint(-deltax*0.125,-deltay*0.48063444,deltaz*0.3279);",
				"head[4][8]= addBPoint(-deltax*0.0,-deltay*0.4884,deltaz*0.3279);",
				"head[4][9]= addBPoint(deltax*0.125,-deltay*0.48063444,deltaz*0.3279);",
				"head[4][10]= addBPoint(deltax*0.25,-deltay*0.47286888,deltaz*0.3279);",
				"head[4][11]= addBPoint(deltax*0.375,-deltay*0.44791164,deltaz*0.3279);",
				"head[4][12]= addBPoint(deltax*0.5,-deltay*0.4229544,deltaz*0.3279);",
				"head[4][13]= addBPoint(deltax*0.625,-deltay*0.37299108000000003,deltaz*0.3279);",
				"head[4][14]= addBPoint(deltax*0.75,-deltay*0.32302776,deltaz*0.3279);",
				"head[4][15]= addBPoint(deltax*0.875,-deltay*0.16151388,deltaz*0.3279);",
				"head[4][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.3279);",
				"head[4][17]= addBPoint(deltax*1.0,deltay*0.6747006,deltaz*0.3279);",
				"head[4][18]= addBPoint(deltax*0.875,deltay*0.69846315,deltaz*0.3279);",
				"head[4][19]= addBPoint(deltax*0.75,deltay*0.7222257000000001,deltaz*0.3279);",
				"head[4][20]= addBPoint(deltax*0.625,deltay*0.73827516,deltaz*0.3279);",
				"head[4][21]= addBPoint(deltax*0.5,deltay*0.75432462,deltaz*0.3279);",
				"head[4][22]= addBPoint(deltax*0.375,deltay*0.76367382,deltaz*0.3279);",
				"head[4][23]= addBPoint(deltax*0.25,deltay*0.77302302,deltaz*0.3279);",
				"head[4][24]= addBPoint(deltax*0.125,deltay*0.77606151,deltaz*0.3279);",
				"head[4][25]= addBPoint(deltax*0.0,deltay*0.7791,deltaz*0.3279);",
				"head[4][26]= addBPoint(-deltax*0.125,deltay*0.77606151,deltaz*0.3279);",
				"head[4][27]= addBPoint(-deltax*0.25,deltay*0.77302302,deltaz*0.3279);",
				"head[4][28]= addBPoint(-deltax*0.375,deltay*0.76367382,deltaz*0.3279);",
				"head[4][29]= addBPoint(-deltax*0.5,deltay*0.75432462,deltaz*0.3279);",
				"head[4][30]= addBPoint(-deltax*0.625,deltay*0.73827516,deltaz*0.3279);",
				"head[4][31]= addBPoint(-deltax*0.75,deltay*0.7222257000000001,deltaz*0.3279);",
				"head[4][32]= addBPoint(-deltax*0.875,deltay*0.69846315,deltaz*0.3279);",
				"head[4][33]= addBPoint(-deltax*1.0,deltay*0.6747006,deltaz*0.3279);",

				"head[5][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.4754);",
				"head[5][1]= addBPoint(-deltax*0.875,-deltay*0.18843285999999998,deltaz*0.4754);",
				"head[5][2]= addBPoint(-deltax*0.75,-deltay*0.37686571999999996,deltaz*0.4754);",
				"head[5][3]= addBPoint(-deltax*0.625,-deltay*0.43515625999999996,deltaz*0.4754);",
				"head[5][4]= addBPoint(-deltax*0.5,-deltay*0.49344679999999996,deltaz*0.4754);",
				"head[5][5]= addBPoint(-deltax*0.375,-deltay*0.5225635799999999,deltaz*0.4754);",
				"head[5][6]= addBPoint(-deltax*0.25,-deltay*0.55168036,deltaz*0.4754);",
				"head[5][7]= addBPoint(-deltax*0.125,-deltay*0.56074018,deltaz*0.4754);",
				"head[5][8]= addBPoint(-deltax*0.0,-deltay*0.5698,deltaz*0.4754);",
				"head[5][9]= addBPoint(deltax*0.125,-deltay*0.56074018,deltaz*0.4754);",
				"head[5][10]= addBPoint(deltax*0.25,-deltay*0.55168036,deltaz*0.4754);",
				"head[5][11]= addBPoint(deltax*0.375,-deltay*0.5225635799999999,deltaz*0.4754);",
				"head[5][12]= addBPoint(deltax*0.5,-deltay*0.49344679999999996,deltaz*0.4754);",
				"head[5][13]= addBPoint(deltax*0.625,-deltay*0.43515625999999996,deltaz*0.4754);",
				"head[5][14]= addBPoint(deltax*0.75,-deltay*0.37686571999999996,deltaz*0.4754);",
				"head[5][15]= addBPoint(deltax*0.875,-deltay*0.18843285999999998,deltaz*0.4754);",
				"head[5][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.4754);",
				"head[5][17]= addBPoint(deltax*1.0,deltay*0.5941626,deltaz*0.4754);",
				"head[5][18]= addBPoint(deltax*0.875,deltay*0.61508865,deltaz*0.4754);",
				"head[5][19]= addBPoint(deltax*0.75,deltay*0.6360147,deltaz*0.4754);",
				"head[5][20]= addBPoint(deltax*0.625,deltay*0.65014836,deltaz*0.4754);",
				"head[5][21]= addBPoint(deltax*0.5,deltay*0.66428202,deltaz*0.4754);",
				"head[5][22]= addBPoint(deltax*0.375,deltay*0.67251522,deltaz*0.4754);",
				"head[5][23]= addBPoint(deltax*0.25,deltay*0.68074842,deltaz*0.4754);",
				"head[5][24]= addBPoint(deltax*0.125,deltay*0.6834242100000001,deltaz*0.4754);",
				"head[5][25]= addBPoint(deltax*0.0,deltay*0.6861,deltaz*0.4754);",
				"head[5][26]= addBPoint(-deltax*0.125,deltay*0.6834242100000001,deltaz*0.4754);",
				"head[5][27]= addBPoint(-deltax*0.25,deltay*0.68074842,deltaz*0.4754);",
				"head[5][28]= addBPoint(-deltax*0.375,deltay*0.67251522,deltaz*0.4754);",
				"head[5][29]= addBPoint(-deltax*0.5,deltay*0.66428202,deltaz*0.4754);",
				"head[5][30]= addBPoint(-deltax*0.625,deltay*0.65014836,deltaz*0.4754);",
				"head[5][31]= addBPoint(-deltax*0.75,deltay*0.6360147,deltaz*0.4754);",
				"head[5][32]= addBPoint(-deltax*0.875,deltay*0.61508865,deltaz*0.4754);",
				"head[5][33]= addBPoint(-deltax*1.0,deltay*0.5941626,deltaz*0.4754);",

				"head[6][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.6066);",
				"head[6][1]= addBPoint(-deltax*0.875,-deltay*0.19226898,deltaz*0.6066);",
				"head[6][2]= addBPoint(-deltax*0.75,-deltay*0.38453796,deltaz*0.6066);",
				"head[6][3]= addBPoint(-deltax*0.625,-deltay*0.44401518000000006,deltaz*0.6066);",
				"head[6][4]= addBPoint(-deltax*0.5,-deltay*0.5034924000000001,deltaz*0.6066);",
				"head[6][5]= addBPoint(-deltax*0.375,-deltay*0.5332019400000001,deltaz*0.6066);",
				"head[6][6]= addBPoint(-deltax*0.25,-deltay*0.56291148,deltaz*0.6066);",
				"head[6][7]= addBPoint(-deltax*0.125,-deltay*0.57215574,deltaz*0.6066);",
				"head[6][8]= addBPoint(-deltax*0.0,-deltay*0.5814,deltaz*0.6066);",
				"head[6][9]= addBPoint(deltax*0.125,-deltay*0.57215574,deltaz*0.6066);",
				"head[6][10]= addBPoint(deltax*0.25,-deltay*0.56291148,deltaz*0.6066);",
				"head[6][11]= addBPoint(deltax*0.375,-deltay*0.5332019400000001,deltaz*0.6066);",
				"head[6][12]= addBPoint(deltax*0.5,-deltay*0.5034924000000001,deltaz*0.6066);",
				"head[6][13]= addBPoint(deltax*0.625,-deltay*0.44401518000000006,deltaz*0.6066);",
				"head[6][14]= addBPoint(deltax*0.75,-deltay*0.38453796,deltaz*0.6066);",
				"head[6][15]= addBPoint(deltax*0.875,-deltay*0.19226898,deltaz*0.6066);",
				"head[6][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.6066);",
				"head[6][17]= addBPoint(deltax*1.0,deltay*0.5739848,deltaz*0.6066);",
				"head[6][18]= addBPoint(deltax*0.875,deltay*0.5942002,deltaz*0.6066);",
				"head[6][19]= addBPoint(deltax*0.75,deltay*0.6144156,deltaz*0.6066);",
				"head[6][20]= addBPoint(deltax*0.625,deltay*0.62806928,deltaz*0.6066);",
				"head[6][21]= addBPoint(deltax*0.5,deltay*0.64172296,deltaz*0.6066);",
				"head[6][22]= addBPoint(deltax*0.375,deltay*0.64967656,deltaz*0.6066);",
				"head[6][23]= addBPoint(deltax*0.25,deltay*0.65763016,deltaz*0.6066);",
				"head[6][24]= addBPoint(deltax*0.125,deltay*0.66021508,deltaz*0.6066);",
				"head[6][25]= addBPoint(deltax*0.0,deltay*0.6628,deltaz*0.6066);",
				"head[6][26]= addBPoint(-deltax*0.125,deltay*0.66021508,deltaz*0.6066);",
				"head[6][27]= addBPoint(-deltax*0.25,deltay*0.65763016,deltaz*0.6066);",
				"head[6][28]= addBPoint(-deltax*0.375,deltay*0.64967656,deltaz*0.6066);",
				"head[6][29]= addBPoint(-deltax*0.5,deltay*0.64172296,deltaz*0.6066);",
				"head[6][30]= addBPoint(-deltax*0.625,deltay*0.62806928,deltaz*0.6066);",
				"head[6][31]= addBPoint(-deltax*0.75,deltay*0.6144156,deltaz*0.6066);",
				"head[6][32]= addBPoint(-deltax*0.875,deltay*0.5942002,deltaz*0.6066);",
				"head[6][33]= addBPoint(-deltax*1.0,deltay*0.5739848,deltaz*0.6066);",

				"head[7][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.6885);",
				"head[7][1]= addBPoint(-deltax*0.875,-deltay*0.19997429,deltaz*0.6885);",
				"head[7][2]= addBPoint(-deltax*0.75,-deltay*0.39994858,deltaz*0.6885);",
				"head[7][3]= addBPoint(-deltax*0.625,-deltay*0.46180939,deltaz*0.6885);",
				"head[7][4]= addBPoint(-deltax*0.5,-deltay*0.5236702,deltaz*0.6885);",
				"head[7][5]= addBPoint(-deltax*0.375,-deltay*0.55457037,deltaz*0.6885);",
				"head[7][6]= addBPoint(-deltax*0.25,-deltay*0.58547054,deltaz*0.6885);",
				"head[7][7]= addBPoint(-deltax*0.125,-deltay*0.59508527,deltaz*0.6885);",
				"head[7][8]= addBPoint(-deltax*0.0,-deltay*0.6047,deltaz*0.6885);",
				"head[7][9]= addBPoint(deltax*0.125,-deltay*0.59508527,deltaz*0.6885);",
				"head[7][10]= addBPoint(deltax*0.25,-deltay*0.58547054,deltaz*0.6885);",
				"head[7][11]= addBPoint(deltax*0.375,-deltay*0.55457037,deltaz*0.6885);",
				"head[7][12]= addBPoint(deltax*0.5,-deltay*0.5236702,deltaz*0.6885);",
				"head[7][13]= addBPoint(deltax*0.625,-deltay*0.46180939,deltaz*0.6885);",
				"head[7][14]= addBPoint(deltax*0.75,-deltay*0.39994858,deltaz*0.6885);",
				"head[7][15]= addBPoint(deltax*0.875,-deltay*0.19997429,deltaz*0.6885);",
				"head[7][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.6885);",
				"head[7][17]= addBPoint(deltax*1.0,deltay*0.5337158,deltaz*0.6885);",
				"head[7][18]= addBPoint(deltax*0.875,deltay*0.55251295,deltaz*0.6885);",
				"head[7][19]= addBPoint(deltax*0.75,deltay*0.5713101,deltaz*0.6885);",
				"head[7][20]= addBPoint(deltax*0.625,deltay*0.58400588,deltaz*0.6885);",
				"head[7][21]= addBPoint(deltax*0.5,deltay*0.5967016599999999,deltaz*0.6885);",
				"head[7][22]= addBPoint(deltax*0.375,deltay*0.6040972599999999,deltaz*0.6885);",
				"head[7][23]= addBPoint(deltax*0.25,deltay*0.6114928599999999,deltaz*0.6885);",
				"head[7][24]= addBPoint(deltax*0.125,deltay*0.6138964299999999,deltaz*0.6885);",
				"head[7][25]= addBPoint(deltax*0.0,deltay*0.6163,deltaz*0.6885);",
				"head[7][26]= addBPoint(-deltax*0.125,deltay*0.6138964299999999,deltaz*0.6885);",
				"head[7][27]= addBPoint(-deltax*0.25,deltay*0.6114928599999999,deltaz*0.6885);",
				"head[7][28]= addBPoint(-deltax*0.375,deltay*0.6040972599999999,deltaz*0.6885);",
				"head[7][29]= addBPoint(-deltax*0.5,deltay*0.5967016599999999,deltaz*0.6885);",
				"head[7][30]= addBPoint(-deltax*0.625,deltay*0.58400588,deltaz*0.6885);",
				"head[7][31]= addBPoint(-deltax*0.75,deltay*0.5713101,deltaz*0.6885);",
				"head[7][32]= addBPoint(-deltax*0.875,deltay*0.55251295,deltaz*0.6885);",
				"head[7][33]= addBPoint(-deltax*1.0,deltay*0.5337158,deltaz*0.6885);",

				"head[8][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.8525);",
				"head[8][1]= addBPoint(-deltax*0.875,-deltay*0.15489988,deltaz*0.8525);",
				"head[8][2]= addBPoint(-deltax*0.75,-deltay*0.30979976,deltaz*0.8525);",
				"head[8][3]= addBPoint(-deltax*0.625,-deltay*0.35771708,deltaz*0.8525);",
				"head[8][4]= addBPoint(-deltax*0.5,-deltay*0.4056344,deltaz*0.8525);",
				"head[8][5]= addBPoint(-deltax*0.375,-deltay*0.42956964,deltaz*0.8525);",
				"head[8][6]= addBPoint(-deltax*0.25,-deltay*0.45350487999999994,deltaz*0.8525);",
				"head[8][7]= addBPoint(-deltax*0.125,-deltay*0.46095243999999996,deltaz*0.8525);",
				"head[8][8]= addBPoint(-deltax*0.0,-deltay*0.4684,deltaz*0.8525);",
				"head[8][9]= addBPoint(deltax*0.125,-deltay*0.46095243999999996,deltaz*0.8525);",
				"head[8][10]= addBPoint(deltax*0.25,-deltay*0.45350487999999994,deltaz*0.8525);",
				"head[8][11]= addBPoint(deltax*0.375,-deltay*0.42956964,deltaz*0.8525);",
				"head[8][12]= addBPoint(deltax*0.5,-deltay*0.4056344,deltaz*0.8525);",
				"head[8][13]= addBPoint(deltax*0.625,-deltay*0.35771708,deltaz*0.8525);",
				"head[8][14]= addBPoint(deltax*0.75,-deltay*0.30979976,deltaz*0.8525);",
				"head[8][15]= addBPoint(deltax*0.875,-deltay*0.15489988,deltaz*0.8525);",
				"head[8][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.8525);",
				"head[8][17]= addBPoint(deltax*1.0,deltay*0.4129088,deltaz*0.8525);",
				"head[8][18]= addBPoint(deltax*0.875,deltay*0.42745120000000003,deltaz*0.8525);",
				"head[8][19]= addBPoint(deltax*0.75,deltay*0.44199360000000004,deltaz*0.8525);",
				"head[8][20]= addBPoint(deltax*0.625,deltay*0.45181568,deltaz*0.8525);",
				"head[8][21]= addBPoint(deltax*0.5,deltay*0.46163775999999995,deltaz*0.8525);",
				"head[8][22]= addBPoint(deltax*0.375,deltay*0.46735936,deltaz*0.8525);",
				"head[8][23]= addBPoint(deltax*0.25,deltay*0.47308096,deltaz*0.8525);",
				"head[8][24]= addBPoint(deltax*0.125,deltay*0.47494048,deltaz*0.8525);",
				"head[8][25]= addBPoint(deltax*0.0,deltay*0.4768,deltaz*0.8525);",
				"head[8][26]= addBPoint(-deltax*0.125,deltay*0.47494048,deltaz*0.8525);",
				"head[8][27]= addBPoint(-deltax*0.25,deltay*0.47308096,deltaz*0.8525);",
				"head[8][28]= addBPoint(-deltax*0.375,deltay*0.46735936,deltaz*0.8525);",
				"head[8][29]= addBPoint(-deltax*0.5,deltay*0.46163775999999995,deltaz*0.8525);",
				"head[8][30]= addBPoint(-deltax*0.625,deltay*0.45181568,deltaz*0.8525);",
				"head[8][31]= addBPoint(-deltax*0.75,deltay*0.44199360000000004,deltaz*0.8525);",
				"head[8][32]= addBPoint(-deltax*0.875,deltay*0.42745120000000003,deltaz*0.8525);",
				"head[8][33]= addBPoint(-deltax*1.0,deltay*0.4129088,deltaz*0.8525);",

				"head[9][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*0.9344);",
				"head[9][1]= addBPoint(-deltax*0.875,-deltay*0.11921735,deltaz*0.9344);",
				"head[9][2]= addBPoint(-deltax*0.75,-deltay*0.2384347,deltaz*0.9344);",
				"head[9][3]= addBPoint(-deltax*0.625,-deltay*0.27531384999999997,deltaz*0.9344);",
				"head[9][4]= addBPoint(-deltax*0.5,-deltay*0.312193,deltaz*0.9344);",
				"head[9][5]= addBPoint(-deltax*0.375,-deltay*0.33061454999999995,deltaz*0.9344);",
				"head[9][6]= addBPoint(-deltax*0.25,-deltay*0.34903609999999996,deltaz*0.9344);",
				"head[9][7]= addBPoint(-deltax*0.125,-deltay*0.35476805,deltaz*0.9344);",
				"head[9][8]= addBPoint(-deltax*0.0,-deltay*0.3605,deltaz*0.9344);",
				"head[9][9]= addBPoint(deltax*0.125,-deltay*0.35476805,deltaz*0.9344);",
				"head[9][10]= addBPoint(deltax*0.25,-deltay*0.34903609999999996,deltaz*0.9344);",
				"head[9][11]= addBPoint(deltax*0.375,-deltay*0.33061454999999995,deltaz*0.9344);",
				"head[9][12]= addBPoint(deltax*0.5,-deltay*0.312193,deltaz*0.9344);",
				"head[9][13]= addBPoint(deltax*0.625,-deltay*0.27531384999999997,deltaz*0.9344);",
				"head[9][14]= addBPoint(deltax*0.75,-deltay*0.2384347,deltaz*0.9344);",
				"head[9][15]= addBPoint(deltax*0.875,-deltay*0.11921735,deltaz*0.9344);",
				"head[9][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*0.9344);",
				"head[9][17]= addBPoint(deltax*1.0,deltay*0.3222386,deltaz*0.9344);",
				"head[9][18]= addBPoint(deltax*0.875,deltay*0.33358765,deltaz*0.9344);",
				"head[9][19]= addBPoint(deltax*0.75,deltay*0.3449367,deltaz*0.9344);",
				"head[9][20]= addBPoint(deltax*0.625,deltay*0.35260196,deltaz*0.9344);",
				"head[9][21]= addBPoint(deltax*0.5,deltay*0.36026721999999994,deltaz*0.9344);",
				"head[9][22]= addBPoint(deltax*0.375,deltay*0.36473241999999995,deltaz*0.9344);",
				"head[9][23]= addBPoint(deltax*0.25,deltay*0.36919761999999995,deltaz*0.9344);",
				"head[9][24]= addBPoint(deltax*0.125,deltay*0.37064880999999994,deltaz*0.9344);",
				"head[9][25]= addBPoint(deltax*0.0,deltay*0.3721,deltaz*0.9344);",
				"head[9][26]= addBPoint(-deltax*0.125,deltay*0.37064880999999994,deltaz*0.9344);",
				"head[9][27]= addBPoint(-deltax*0.25,deltay*0.36919761999999995,deltaz*0.9344);",
				"head[9][28]= addBPoint(-deltax*0.375,deltay*0.36473241999999995,deltaz*0.9344);",
				"head[9][29]= addBPoint(-deltax*0.5,deltay*0.36026721999999994,deltaz*0.9344);",
				"head[9][30]= addBPoint(-deltax*0.625,deltay*0.35260196,deltaz*0.9344);",
				"head[9][31]= addBPoint(-deltax*0.75,deltay*0.3449367,deltaz*0.9344);",
				"head[9][32]= addBPoint(-deltax*0.875,deltay*0.33358765,deltaz*0.9344);",
				"head[9][33]= addBPoint(-deltax*1.0,deltay*0.3222386,deltaz*0.9344);",

				"head[10][0]= addBPoint(-deltax*1.0,-deltay*0.0,deltaz*1.0);",
				"head[10][1]= addBPoint(-deltax*0.875,-deltay*0.0,deltaz*1.0);",
				"head[10][2]= addBPoint(-deltax*0.75,-deltay*0.0,deltaz*1.0);",
				"head[10][3]= addBPoint(-deltax*0.625,-deltay*0.0,deltaz*1.0);",
				"head[10][4]= addBPoint(-deltax*0.5,-deltay*0.0,deltaz*1.0);",
				"head[10][5]= addBPoint(-deltax*0.375,-deltay*0.0,deltaz*1.0);",
				"head[10][6]= addBPoint(-deltax*0.25,-deltay*0.0,deltaz*1.0);",
				"head[10][7]= addBPoint(-deltax*0.125,-deltay*0.0,deltaz*1.0);",
				"head[10][8]= addBPoint(-deltax*0.0,-deltay*0.0,deltaz*1.0);",
				"head[10][9]= addBPoint(deltax*0.125,-deltay*0.0,deltaz*1.0);",
				"head[10][10]= addBPoint(deltax*0.25,-deltay*0.0,deltaz*1.0);",
				"head[10][11]= addBPoint(deltax*0.375,-deltay*0.0,deltaz*1.0);",
				"head[10][12]= addBPoint(deltax*0.5,-deltay*0.0,deltaz*1.0);",
				"head[10][13]= addBPoint(deltax*0.625,-deltay*0.0,deltaz*1.0);",
				"head[10][14]= addBPoint(deltax*0.75,-deltay*0.0,deltaz*1.0);",
				"head[10][15]= addBPoint(deltax*0.875,-deltay*0.0,deltaz*1.0);",
				"head[10][16]= addBPoint(deltax*1.0,-deltay*0.0,deltaz*1.0);",
				"head[10][17]= addBPoint(deltax*1.0,deltay*0.0,deltaz*1.0);",
				"head[10][18]= addBPoint(deltax*0.875,deltay*0.0,deltaz*1.0);",
				"head[10][19]= addBPoint(deltax*0.75,deltay*0.0,deltaz*1.0);",
				"head[10][20]= addBPoint(deltax*0.625,deltay*0.0,deltaz*1.0);",
				"head[10][21]= addBPoint(deltax*0.5,deltay*0.0,deltaz*1.0);",
				"head[10][22]= addBPoint(deltax*0.375,deltay*0.0,deltaz*1.0);",
				"head[10][23]= addBPoint(deltax*0.25,deltay*0.0,deltaz*1.0);",
				"head[10][24]= addBPoint(deltax*0.125,deltay*0.0,deltaz*1.0);",
				"head[10][25]= addBPoint(deltax*0.0,deltay*0.0,deltaz*1.0);",
				"head[10][26]= addBPoint(-deltax*0.125,deltay*0.0,deltaz*1.0);",
				"head[10][27]= addBPoint(-deltax*0.25,deltay*0.0,deltaz*1.0);",
				"head[10][28]= addBPoint(-deltax*0.375,deltay*0.0,deltaz*1.0);",
				"head[10][29]= addBPoint(-deltax*0.5,deltay*0.0,deltaz*1.0);",
				"head[10][30]= addBPoint(-deltax*0.625,deltay*0.0,deltaz*1.0);",
				"head[10][31]= addBPoint(-deltax*0.75,deltay*0.0,deltaz*1.0);",
				"head[10][32]= addBPoint(-deltax*0.875,deltay*0.0,deltaz*1.0);",
				"head[10][33]= addBPoint(-deltax*1.0,deltay*0.0,deltaz*1.0);",
		};
		
		int c=0;
		int n=0;
		for (int i = 0; i < codes.length; i++) {

			String[] s0=getSextuple(codes[i]); 

			System.out.println("head["+n+"]["+(c++)+"]= addBPoint("+s0[0]+"*"+v(s0[1])+","+s0[2]+"*"+v(s0[3])+","+s0[4]+"*"+v(s0[5])+");");
			
			if(c==34){
				
				System.out.println();
				
				int d=0;
				n++;
				
				if(i < codes.length-34)
				for (int j = i-33; j <= i; j++) {
					
					
					
					String[] s1=getSextuple(codes[j]); 
					String[] s2=getSextuple(codes[j+34]); 

					System.out.println("head["+n+"]["+(d++)+"]= addBPoint("+s1[0]+"*"+m(s1[1],s2[1])+","+s1[2]+"*"+m(s1[3],s2[3])+","+s1[4]+"*"+m(s1[5],s2[5])+");");
					
				}	
				
				
			}
			
			if(c==34){
				c=0;
				n++;
				System.out.println();
			}	

		}
		/*int c=0;
		int n=0;
		for (int i = 0; i < codes.length; i++) {

			String[] s0=getSextuple(codes[i]); 

			System.out.println("head["+n+"]["+(c++)+"]= addBPoint("+s0[0]+"*"+v(s0[1])+","+s0[2]+"*"+v(s0[3])+","+s0[4]+"*"+v(s0[5])+");");

			if(i%18!=8 && i%18!=17){

				String[]  s1=getSextuple(codes[i+1]); 


				System.out.println("head["+n+"]["+(c++)+"]= addBPoint("+s1[0]+"*"+m(s0[1],s1[1])+","+s0[2]+"*"+m(s0[3],s1[3])+","+s0[4]+"*"+m(s0[5],s1[5])+");");
			}
			//System.out.println("head["+n+"]["+(c++)+"]= addBPoint("+s1[0]+"*"+v(s1[1])+","+s1[2]+"*"+v(s1[3])+","+s1[4]+"*"+v(s1[5])+");");

			if(c==34){
				c=0;
				n++;
				System.out.println();
			}	
		}*/
	}

	private double m(String str0,String str1){

		double d0=v(str0);
		double d1=v(str1);

		return (d0+d1)*0.5;
	}

	private double v(String str){
		return Double.parseDouble(str);

	}

	private String[] getSextuple(String str) {

		String[] sex=new String[6];
		//extract data:

		str=str.substring(str.indexOf("(")+1,str.indexOf(")"));

		String[] vals=str.split(",");


		for (int j = 0; j < vals.length; j++) {


			String value=vals[j];
			String numVal="";
			String charVal="";
			for (int k = 0; k < value.length(); k++) {
				char ch=value.charAt(k);
				if(Character.isDigit(ch) || ch=='.')
					numVal+=ch;
				else if(ch!='*')
					charVal+=ch;
			}
			if(numVal.equals(""))
				numVal="1";
			//System.out.println(charVal+"->"+numVal);

			sex[2*j]=charVal;
			sex[2*j+1]=numVal;



		}
		return sex;
	}


}
