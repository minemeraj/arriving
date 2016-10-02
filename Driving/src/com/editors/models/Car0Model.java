package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.main.CarFrame;

public class Car0Model extends MeshModel{

	private double dx = 0;
	private double dy = 0;
	private double dz = 0;

	private int[][][] faces;

	private int nBasePoints=6;

	private int bx=10;
	private int by=10;

	public static String NAME="Car0";

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

		BPoint[][] bodyPoints=new BPoint[numSections][6];

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

			bodyPoints[k][0]=addBPoint(x-deltax0, y,z+deltaz0);
			bodyPoints[k][1]=addBPoint(x-deltax1, y,z+deltaz1);
			bodyPoints[k][2]=addBPoint(x-deltax2, y,z+deltaz2);
			bodyPoints[k][3]=addBPoint(x+deltax2, y,z+deltaz2);
			bodyPoints[k][4]=addBPoint(x+deltax1, y,z+deltaz1);
			bodyPoints[k][5]=addBPoint(x+deltax0, y,z+deltaz0);



		}
		//these wheel data should be read from the editor
		int rays_number=10;
		int wheel_width=20;
		double wheel_radius=0.06703*dy;



		double wz=0.03293*dy;//wheel_radius;
		double wx=dx*0.5-wheel_width;

		double yRearAxle=axles[0]*dy;
		double yFrontAxle=axles[1]*dy;

		BPoint[][] wheelLeftFront=buildWheel(-wx-wheel_width, yFrontAxle,wz , wheel_radius, wheel_width, rays_number);
		BPoint[][] wheelRightFront=buildWheel(wx, yFrontAxle, wz, wheel_radius, wheel_width, rays_number);
		BPoint[][] wheelLeftRear=buildWheel(-wx-wheel_width, yRearAxle, wz, wheel_radius, wheel_width, rays_number);
		BPoint[][] wheelRightRear=buildWheel(wx, yRearAxle, wz, wheel_radius, wheel_width, rays_number);

		//single block texture

		int totBlockTexturesPoints=0;

		for(int k=0;k<numSections;k++){

			double[][] d=body[k];

			double yi=d[0][0];

			double y=by+yi*dy+dz;
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

				totBlockTexturesPoints++;
			}
		}

		//closing back and front texture
		for(int i=0;i<2;i++){

			double x=bx+dz;
			double y=by;

			if(i==0){

				addTPoint(x,y,0);
				addTPoint(x,y+dz,0);
				addTPoint(x+dx*0.25,y+dz,0);
				addTPoint(x+dx*0.75,y+dz,0);
				addTPoint(x+dx,y+dz,0);
				addTPoint(x+dx,y,0);
			}else if(i==1){
				y=by+dy+dz;

				addTPoint(x+dx,y+dz,0);
				addTPoint(x+dx,y,0);
				addTPoint(x+dx*0.75,y,0);
				addTPoint(x+dx*0.25,y,0);
				addTPoint(x,y,0);
				addTPoint(x,y+dz,0);




			}



		}


		//wheel texture, a black square for simplicity:

		double x=bx+dz*2+dx*2;
		double y=by;

		addTPoint(x,y,0);
		addTPoint(x+wheel_width,y,0);
		addTPoint(x+wheel_width,y+wheel_width,0);
		addTPoint(x,y+wheel_width,0);



		//////
		int totWheelPolygon=rays_number+2*(rays_number-2);

		int NUM_WHEEL_FACES=4*totWheelPolygon;
		int NUM_FACES=nBasePoints*(numSections-1);

		faces=new int[NUM_FACES+NUM_WHEEL_FACES+6][][];
		int counter=0;
		counter=buildBodyFaces(counter,NUM_FACES,numSections,totBlockTexturesPoints,bodyPoints);
		totBlockTexturesPoints+=6;
		totBlockTexturesPoints+=6;
		////
		counter=buildWheelFaces(counter,totBlockTexturesPoints,totWheelPolygon,
				wheelLeftFront,
				wheelRightFront,
				wheelLeftRear,
				wheelRightRear
				);

		IMG_WIDTH=(int) (2*bx+2*(dx+dz))+wheel_width;
		IMG_HEIGHT=(int) (2*by+dy+2*dz);
	}




	private int buildBodyFaces(int counter,
			int NUM_FACES,
			int numSections,
			int totBlockTexturesPoints,
			BPoint[][] bodyPoints) {

		int[][][] bFaces = buildSingleBlockFaces(nBasePoints,numSections,0,0);

		for (int i = 0; i < NUM_FACES; i++) {
			faces[counter++]=bFaces[i];
		}
		//closing the mesh back	with 3 panels
		int totPanel=totBlockTexturesPoints;
		for(int i=0;i<3;i++){

			if(i==0){
				faces[counter++]=buildFace(CarFrame.CAR_BACK,
						bodyPoints[0][0],
						bodyPoints[0][2],
						bodyPoints[0][1],
						totPanel, totPanel+2, totPanel+1);
			}
			else if(i==1){
				faces[counter++]=buildFace(CarFrame.CAR_BACK,
						bodyPoints[0][0],
						bodyPoints[0][5],
						bodyPoints[0][3],
						bodyPoints[0][2],
						totPanel, totPanel+5, totPanel+3,totPanel+2);
			}else if(i==2){
				faces[counter++]=buildFace(CarFrame.CAR_BACK,
						bodyPoints[0][3],
						bodyPoints[0][5],
						bodyPoints[0][4],
						totPanel+3, totPanel+5, totPanel+4);
			}
		}

		/////
		//closing the mesh front with 3 panels
		totPanel=totBlockTexturesPoints;
		for(int i=0;i<3;i++){

			if(i==0){
				faces[counter++]=buildFace(CarFrame.CAR_FRONT,
						bodyPoints[numSections-1][0],
						bodyPoints[numSections-1][1],
						bodyPoints[numSections-1][2],
						totPanel, totPanel+1, totPanel+2);
			}
			else if(i==1){
				faces[counter++]=buildFace(CarFrame.CAR_FRONT,
						bodyPoints[numSections-1][0],
						bodyPoints[numSections-1][2],
						bodyPoints[numSections-1][3],
						bodyPoints[numSections-1][5],
						totPanel, totPanel+2, totPanel+3,totPanel+5);
			}else if(i==2){
				faces[counter++]=buildFace(CarFrame.CAR_FRONT,
						bodyPoints[numSections-1][3],
						bodyPoints[numSections-1][4],
						bodyPoints[numSections-1][5],
						totPanel+3, totPanel+4, totPanel+5);
			}
		}


		return counter;
	}

	private int buildWheelFaces(int counter, int totBlockTexturesPoints, int totWheelPolygon,
			BPoint[][] wheelLeftFront,
			BPoint[][] wheelRightFront,
			BPoint[][] wheelLeftRear,
			BPoint[][] wheelRightRear) {

		//build wheels faces
		int[][][] wFaces = buildWheelFaces(wheelLeftFront,totBlockTexturesPoints);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++]=wFaces[i];
		}

		wFaces = buildWheelFaces(wheelRightFront,totBlockTexturesPoints);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++]=wFaces[i];
		}

		wFaces = buildWheelFaces(wheelLeftRear,totBlockTexturesPoints);
		for (int i = 0; i <totWheelPolygon; i++) {
			faces[counter++]=wFaces[i];
		}

		wFaces = buildWheelFaces(wheelRightRear,totBlockTexturesPoints);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++]=wFaces[i];
		}

		return counter;
	}

	@Override
	public void printTexture(Graphics2D bg) {
		//draw lines for reference

		bg.setColor(Color.BLACK);
		bg.setStroke(new BasicStroke(0.1f));
		printTextureFaces(bg,faces);

	}


	@Override
	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);

	}

	/**
	 * BOTTOM-UP SECTIONS
	 * [y],[x0,x1,x2],[z0,z1,z2]
	 *
	 * i from bottom to top, counterclockwise
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
			{{0.8419},{1.0,1.0,0.75},{0.3775,0.5984,0.5984}},
			{{0.8694},{1.0,1.0,0.75},{0.2490,0.5703,0.5703}},
			{{0.8782},{1.0,1.0,0.75},{0.0000,0.5703,0.5703}},
			{{0.9759},{1.0,1.0,0.75},{0.0000,0.4378,0.4378}},
			{{1.0000},{1.0,1.0,0.75},{0.1727,0.2811,0.2811}},

	};

	private final double[] axles={0.2217,0.7980};



}
