package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.Segments;
import com.main.Renderer3D;

/**
 * One texture model *
 *
 * @author Administrator
 *
 */
public class Byke0Model extends VehicleModel {

	private double wheelRadius;
	private double wheelWidth;
	private int wheel_rays;

	double xc = 0;

	public static final String NAME = "Byke";

	private int[][] tBo = null;
	private int[] tWh = null;
	private int[][] tLeftRear;
	private int[][] tLeftBody;
	private int[][] tLeftFront;
	private int[][] tLeftRoof;
	private int[][] tTopRear;
	private int[][] tTopFront;
	private int[][] tTopRoof;
	private int[][] tRightRear;
	private int[][] tRightBody;
	private int[][] tRightFront;
	private int[][] tRightRoof;
	private int[][] tWheelSide = null;

	private BPoint[][] wheelFront;
	private BPoint[][] wheelRear;
	private BPoint[][][] rearByke;
	private BPoint[][][] roofByke;
	private BPoint[][][] handlebar;

	/**
	 * BOTTOM-UP SECTIONS [y],[x0],[z0,z1]
	 **/
	private double[][][] pBody = {
			{ { 0.00 }, { 1.0 }, { 0.0,1.0} },
			{ { 1.00 }, { 1.0 }, { 0.0,1.0} },
	};
	private double[][][] pRear = {
			{ { 0.00 }, { 1.0 }, { 0.2,1.0} },
			{ { 1.00 }, { 1.0 }, { 0.0,1.0} },
	};
	private double[][][] pHandler = {
			{ { 0.00 }, { 1.0 }, { 0.0, 1.0 } },
			{ { 0.50 }, { 0.5 }, { 0.0, 1.0 } },
			{ { 1.00 }, { 0.5 }, { 0.0, 0.9 } },
	};

	public Byke0Model(double dx, double dy, double dz, double dxf, double dyf, double dzf, double dxr, double dyr,
			double dzr, double dxRoof, double dyRoof, double dzRoof, double wheelRadius, double wheelWidth,
			int wheel_rays) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;

		this.dxFront = dxf;
		this.dyFront = dyf;
		this.dzFront = dzf;

		this.dxRear = dxr;
		this.dyRear = dyr;
		this.dzRear = dzr;

		this.dxRoof = dxRoof;
		this.dyRoof = dyRoof;
		this.dzRoof = dzRoof;

		this.wheelRadius = wheelRadius;
		this.wheelWidth = wheelWidth;
		this.wheel_rays = wheel_rays;
	}

	@Override
	public void initMesh() {


		initTexturesArrays();

		points = new Vector<Point3D>();
		texturePoints = new Vector<Point3D>();
		buildBody();

		buildTextures();

		//faces
		int NF = 6 * 3;
		//handlebar
		NF += (pHandler.length - 1) * 4 + 2;

		int totWheelPolygon = wheel_rays + 2 * (wheel_rays - 2);
		int NUM_WHEEL_FACES = 2 * totWheelPolygon;

		faces = new int[NF + NUM_WHEEL_FACES][3][4];

		int counter = 0;
		counter = buildBodyFaces(counter, body, handlebar, rearByke, roofByke);
		counter = buildWheelFaces(counter, totWheelPolygon);

	}

	private void buildBody() {

		xc = wheelWidth / 2.0;

		Segments bd = new Segments(xc, wheelWidth, 2 * wheelRadius, dy, 2 * wheelRadius - dz, dz);

		body = new BPoint[2][pBody.length][2];

		for (int j = 0; j < pBody.length; j++) {
			double yy = pBody[j][0][0];
			double xx = pBody[j][1][0];
			double zz0 = pBody[j][2][0];
			double zz1 = pBody[j][2][1];

			body[0][j][0] = addBPoint(-0.5*xx, yy, zz0, bd);
			body[1][j][0] = addBPoint(0.5*xx, yy, zz0, bd);
			body[0][j][1] = addBPoint(-0.5*xx, yy, zz1, bd);
			body[1][j][1] = addBPoint(0.5*xx, yy, zz1, bd);
		}

		Segments rb = new Segments(xc, dxRear, 2 * wheelRadius - dyRear, dyRear, 2 * wheelRadius, dzRear);

		rearByke = new BPoint[2][pRear.length][2];

		for (int j = 0; j < pRear.length; j++) {

			double yy = pRear[j][0][0];
			double xx = pRear[j][1][0];
			double zz0 = pRear[j][2][0];
			double zz1 = pRear[j][2][1];

			rearByke[0][j][0] = addBPoint(-0.5*xx, yy, zz0, rb);
			rearByke[1][j][0] = addBPoint(0.5*xx, yy, zz0, rb);
			rearByke[1][j][1] = addBPoint(0.5*xx, yy, zz1, rb);
			rearByke[0][j][1] = addBPoint(-0.5*xx, yy, zz1, rb);
		}

		Segments rob = new Segments(xc, dxRoof, 2 * wheelRadius, dyRoof, 2 * wheelRadius, dzRoof);

		roofByke = new BPoint[pBody.length][2][2];

		roofByke[0][0][0] = addBPoint(-0.5, 0.0, 0.0, rob);
		roofByke[0][1][0] = addBPoint(0.5, 0.0, 0.0, rob);
		roofByke[0][1][1] = addBPoint(0.5, 0.0, 1.0, rob);
		roofByke[0][0][1] = addBPoint(-0.5, 0.0, 1.0, rob);

		roofByke[1][0][0] = addBPoint(-0.5, 1.0, 0.0, rob);
		roofByke[1][1][0] = addBPoint(0.5, 1.0, 0.0, rob);
		roofByke[1][1][1] = addBPoint(0.5, 1.0, 1.0, rob);
		roofByke[1][0][1] = addBPoint(-0.5, 1.0, 1.0, rob);

		Segments hb = new Segments(xc, dxFront, 2 * wheelRadius + dyRoof, dyFront, 2 * wheelRadius, dzFront);
		handlebar = new BPoint[2][pHandler.length][2];

		for (int j = 0; j < pHandler.length; j++) {

			double yy = pHandler[j][0][0];
			double xx = pHandler[j][1][0];
			double zz0 = pHandler[j][2][0];
			double zz1 = pHandler[j][2][1];

			handlebar[0][j][0] = addBPoint(-0.5*xx, yy, zz0, hb);
			handlebar[1][j][0] = addBPoint(0.5*xx, yy, zz0, hb);
			handlebar[1][j][1] = addBPoint(0.5*xx, yy, zz1, hb);
			handlebar[0][j][1] = addBPoint(-0.5*xx,yy, zz1, hb);

		}

		double wz = wheelRadius;
		double wx = wheelWidth * 0.5;

		wheelFront = buildWheel(xc - wx, wheelRadius, wz, wheelRadius, wheelWidth, wheel_rays);
		wheelRear = buildWheel(xc - wx, 2 * wheelRadius + dy + wheelRadius, wz, wheelRadius, wheelWidth,
				wheel_rays);

	}

	private void initTexturesArrays() {
		int c = 0;
		c = initDoubleArrayValues(tBo = new int[1][4], c);
		c = initDoubleArrayValues(tLeftRear = new int[pRear.length-1][4], c);
		c = initDoubleArrayValues(tLeftBody = new int[pBody.length-1][4], c);
		c = initDoubleArrayValues(tLeftRoof = new int[pBody.length-1][4], c);
		c = initDoubleArrayValues(tLeftFront = new int[pHandler.length-1][4], c);
		c = initDoubleArrayValues(tTopRear = new int[pRear.length-1][4], c);
		c = initDoubleArrayValues(tTopRoof = new int[pBody.length-1][4], c);
		c = initDoubleArrayValues(tTopFront = new int[pHandler.length-1][4], c);
		c = initDoubleArrayValues(tRightRear = new int[pRear.length-1][4], c);
		c = initDoubleArrayValues(tRightBody = new int[pBody.length-1][4], c);
		c = initDoubleArrayValues(tRightRoof = new int[pBody.length-1][4], c);
		c = initDoubleArrayValues(tRightFront = new int[pHandler.length-1][4], c);
		c = initSingleArrayValues(tWh = new int[4], c);
		c = initDoubleArrayValues(tWheelSide = new int[1][wheel_rays], c);

	}

	private void buildTextures() {

		int shift = 1;
		double y = by;
		double x = bx;

		double maxDX=Math.max(dx, Math.max(dxFront,dxRear));
		double totDZ=Math.max(dxRear, dzFront)+Math.max(dz, 2*wheelRadius);
		double maxDY=Math.max(dy+dyRear+dyFront,2*wheelRadius);

		addTRect(x, y, dx, dy);
		x+=dx+shift;
		buildLeftTextures(x,y,totDZ);
		x+=totDZ+shift;
		buildTopTextures(x,y,maxDX);
		x+=maxDX+shift;
		buildRightTextures(x,y,totDZ);
		x+=totDZ+ shift;
		y = by;
		buildWheelTexture(x,y,shift);
		x+=wheelWidth+shift;
		x+=2*wheelRadius;

		IMG_WIDTH = (int) ( bx + x);
		IMG_HEIGHT = (int) (2 * by + maxDY);

	}

	private void buildWheelTexture(double x, double y, int shift) {
		addTRect(x, y, wheelWidth, wheelRadius*2);
		x+=wheelWidth+shift;
		buildWheelTextures(x, y, wheelRadius, wheelRadius, wheelRadius, wheel_rays, +1,0);
	}

	private void buildRightTextures(double x, double y, double totDZ) {

		addTPoint(x+totDZ-rearByke[1][0][0].z,y+rearByke[1][0][0].y,0);
		addTPoint(x+totDZ-rearByke[1][0][1].z,y+rearByke[1][0][1].y,0);
		addTPoint(x+totDZ-rearByke[1][1][1].z,y+rearByke[1][1][1].y,0);
		addTPoint(x+totDZ-rearByke[1][1][0].z,y+rearByke[1][1][0].y,0);


		addTPoint(x+totDZ-body[0][0][0].z,y+body[0][0][0].y,0);
		addTPoint(x+totDZ-body[0][1][0].z,y+body[0][1][0].y,0);
		addTPoint(x+totDZ-body[1][1][1].z,y+body[0][1][1].y,0);
		addTPoint(x+totDZ-body[0][0][1].z,y+body[0][0][1].y,0);

		addTPoint(x+totDZ-roofByke[0][0][0].z,y+roofByke[0][0][0].y,0);
		addTPoint(x+totDZ-roofByke[0][0][1].z,y+roofByke[0][0][1].y,0);
		addTPoint(x+totDZ-roofByke[1][0][1].z,y+roofByke[1][0][1].y,0);
		addTPoint(x+totDZ-roofByke[1][0][0].z,y+roofByke[1][0][0].y,0);

		for (int i = 0; i < pHandler.length-1; i++) {
			addTPoint(x+totDZ-handlebar[0][i][0].z,y+handlebar[0][i][0].y,0);
			addTPoint(x+totDZ-handlebar[0][i][1].z,y+handlebar[0][i][1].y,0);
			addTPoint(x+totDZ-handlebar[0][i+1][1].z,y+handlebar[0][i+1][1].y,0);
			addTPoint(x+totDZ-handlebar[0][i+1][0].z,y+handlebar[0][i+1][0].y,0);
		}
	}

	private void buildTopTextures(double x, double y, double maxDX) {

		double middleX=x+maxDX*0.5-xc;

		addTPoint(middleX+rearByke[0][0][0].x,y+rearByke[0][0][0].y,0);
		addTPoint(middleX+rearByke[1][0][0].x,y+rearByke[1][0][0].y,0);
		addTPoint(middleX+rearByke[1][0+1][0].x,y+rearByke[1][0+1][0].y,0);
		addTPoint(middleX+rearByke[0][0+1][0].x,y+rearByke[0][0+1][0].y,0);

		addTPoint(middleX+roofByke[0][0][0].x,y+roofByke[0][0][0].y,0);
		addTPoint(middleX+roofByke[0][1][0].x,y+roofByke[0][1][0].y,0);
		addTPoint(middleX+roofByke[0+1][1][0].x,y+roofByke[0+1][1][0].y,0);
		addTPoint(middleX+roofByke[0+1][0][0].x,y+roofByke[0+1][0][0].y,0);

		for (int i = 0; i < pHandler.length-1; i++) {
			addTPoint(middleX+handlebar[0][i][0].x,y+handlebar[0][i][0].y,0);
			addTPoint(middleX+handlebar[1][i][0].x,y+handlebar[1][i][0].y,0);
			addTPoint(middleX+handlebar[1][i+1][0].x,y+handlebar[1][i+1][0].y,0);
			addTPoint(middleX+handlebar[0][i+1][0].x,y+handlebar[0][i+1][0].y,0);
		}

	}

	private void buildLeftTextures(double x, double y, double totDZ) {

		addTPoint(x+rearByke[0][0][0].z,y+rearByke[0][0][0].y,0);
		addTPoint(x+rearByke[0][0][1].z,y+rearByke[0][0][1].y,0);
		addTPoint(x+rearByke[0][1][1].z,y+rearByke[0][1][1].y,0);
		addTPoint(x+rearByke[0][1][0].z,y+rearByke[0][1][0].y,0);

		addTPoint(x+body[0][0][0].z,y+body[0][0][0].y,0);
		addTPoint(x+body[0][1][0].z,y+body[0][1][0].y,0);
		addTPoint(x+body[1][1][1].z,y+body[0][1][1].y,0);
		addTPoint(x+body[0][0][1].z,y+body[0][0][1].y,0);

		addTPoint(x+roofByke[0][0][0].z,y+roofByke[0][0][0].y,0);
		addTPoint(x+roofByke[0][0][1].z,y+roofByke[0][0][1].y,0);
		addTPoint(x+roofByke[1][0][1].z,y+roofByke[1][0][1].y,0);
		addTPoint(x+roofByke[1][0][0].z,y+roofByke[1][0][0].y,0);

		for (int i = 0; i < pHandler.length-1; i++) {
			addTPoint(x+handlebar[0][i][0].z,y+handlebar[0][i][0].y,0);
			addTPoint(x+handlebar[0][i][1].z,y+handlebar[0][i][1].y,0);
			addTPoint(x+handlebar[0][i+1][1].z,y+handlebar[0][i+1][1].y,0);
			addTPoint(x+handlebar[0][i+1][0].z,y+handlebar[0][i+1][0].y,0);
		}
	}

	private int buildWheelFaces(int counter,int totWheelPolygon) {

		int[][][] wFaces = buildWheelFaces(wheelFront,tWh, tWheelSide[0]);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}
		wFaces = buildWheelFaces(wheelRear,tWh, tWheelSide[0]);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}

		return counter;
	}

	private int buildBodyFaces(int counter,

			BPoint[][][] body, BPoint[][][] handlebar, BPoint[][][] rearByke, BPoint[][][] roofByke) {

		/*
		 * faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * leftFrame[0][0][1],leftFrame[1][0][1],leftFrame[1][1][1],leftFrame[0]
		 * [1][1], 0, 1, 2, 3);
		 * faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * leftFrame[0][0][0],leftFrame[0][1][0],leftFrame[1][1][0],leftFrame[1]
		 * [0][0], 0, 1, 2, 3);
		 * faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * leftFrame[0][0][0],leftFrame[0][0][1],leftFrame[0][1][1],leftFrame[0]
		 * [1][0], 0, 1, 2, 3);
		 * faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * leftFrame[1][0][0],leftFrame[1][1][0],leftFrame[1][1][1],leftFrame[1]
		 * [0][1], 0, 1, 2, 3);
		 * faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * leftFrame[0][0][0],leftFrame[1][0][0],leftFrame[1][0][1],leftFrame[0]
		 * [0][1], 0, 1, 2, 3);
		 * faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * leftFrame[0][1][0],leftFrame[0][1][1],leftFrame[1][1][1],leftFrame[1]
		 * [1][0], 0, 1, 2, 3);
		 *
		 *
		 * faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * rightFrame[0][0][1],rightFrame[1][0][1],rightFrame[1][1][1],
		 * rightFrame[0][1][1], 0, 1, 2, 3);
		 * faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * rightFrame[0][0][0],rightFrame[0][1][0],rightFrame[1][1][0],
		 * rightFrame[1][0][0], 0, 1, 2, 3);
		 * faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * rightFrame[0][0][0],rightFrame[0][0][1],rightFrame[0][1][1],
		 * rightFrame[0][1][0], 0, 1, 2, 3);
		 * faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * rightFrame[1][0][0],rightFrame[1][1][0],rightFrame[1][1][1],
		 * rightFrame[1][0][1], 0, 1, 2, 3);
		 * faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * rightFrame[0][0][0],rightFrame[1][0][0],rightFrame[1][0][1],
		 * rightFrame[0][0][1], 0, 1, 2, 3);
		 * faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * rightFrame[0][1][0],rightFrame[0][1][1],rightFrame[1][1][1],
		 * rightFrame[1][1][0], 0, 1, 2, 3);
		 *
		 * faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * leftFork[0][0][1],leftFork[1][0][1],leftFork[1][1][1],leftFork[0][1][
		 * 1], 0, 1, 2, 3); faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * leftFork[0][0][0],leftFork[0][1][0],leftFork[1][1][0],leftFork[1][0][
		 * 0], 0, 1, 2, 3); faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * leftFork[0][0][0],leftFork[0][0][1],leftFork[0][1][1],leftFork[0][1][
		 * 0], 0, 1, 2, 3); faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * leftFork[1][0][0],leftFork[1][1][0],leftFork[1][1][1],leftFork[1][0][
		 * 1], 0, 1, 2, 3); faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * leftFork[0][0][0],leftFork[1][0][0],leftFork[1][0][1],leftFork[0][0][
		 * 1], 0, 1, 2, 3); faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * leftFork[0][1][0],leftFork[0][1][1],leftFork[1][1][1],leftFork[1][1][
		 * 0], 0, 1, 2, 3);
		 *
		 * faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * rightFork[0][0][1],rightFork[1][0][1],rightFork[1][1][1],rightFork[0]
		 * [1][1], 0, 1, 2, 3);
		 * faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * rightFork[0][0][0],rightFork[0][1][0],rightFork[1][1][0],rightFork[1]
		 * [0][0], 0, 1, 2, 3);
		 * faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * rightFork[0][0][0],rightFork[0][0][1],rightFork[0][1][1],rightFork[0]
		 * [1][0], 0, 1, 2, 3);
		 * faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * rightFork[1][0][0],rightFork[1][1][0],rightFork[1][1][1],rightFork[1]
		 * [0][1], 0, 1, 2, 3);
		 * faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * rightFork[0][0][0],rightFork[1][0][0],rightFork[1][0][1],rightFork[0]
		 * [0][1], 0, 1, 2, 3);
		 * faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
		 * rightFork[0][1][0],rightFork[0][1][1],rightFork[1][1][1],rightFork[1]
		 * [1][0], 0, 1, 2, 3);
		 */

		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, body[0][0][1], body[1][0][1], body[1][1][1], body[0][1][1],
				tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, body[0][0][0], body[0][1][0], body[1][1][0], body[1][0][0],
				tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, body[0][0][0], body[0][0][1], body[0][1][1], body[0][1][0],
				tLeftBody[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, body[1][0][0], body[1][1][0], body[1][1][1], body[1][0][1],
				tRightBody[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, body[0][0][0], body[1][0][0], body[1][0][1], body[0][0][1],
				tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, body[0][1][0], body[0][1][1], body[1][1][1], body[1][1][0],
				tBo[0]);

		faces[counter++] = buildFace(Renderer3D.CAR_BACK, handlebar[0][0][0], handlebar[1][0][0], handlebar[1][0][1],
				handlebar[0][0][1], tBo[0]);
		for (int k = 0; k < pHandler.length - 1; k++) {


			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, handlebar[0][k][0], handlebar[0][k + 1][0],
					handlebar[1][k + 1][0], handlebar[1][k][0], tBo[0]);


			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, handlebar[0][k][0], handlebar[0][k][1],
					handlebar[0][k + 1][1], handlebar[0][k + 1][0], tLeftFront[k]);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, handlebar[1][k][0], handlebar[1][k + 1][0],
					handlebar[1][k + 1][1], handlebar[1][k][1], tRightFront[k]);

			faces[counter++] = buildFace(Renderer3D.CAR_TOP, handlebar[0][k][1], handlebar[1][k][1],
					handlebar[1][k + 1][1], handlebar[0][k + 1][1], tTopFront[k]);

		}
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, handlebar[1][pHandler.length - 1][0], handlebar[0][pHandler.length - 1][0],
				handlebar[0][pHandler.length - 1][1],handlebar[1][pHandler.length - 1][1], tBo[0]);

		///////
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, rearByke[0][0][0], rearByke[1][0][0], rearByke[1][0][1],
				rearByke[0][0][1], tBo[0]);
		for (int k = 0; k < 2 - 1; k++) {

			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, rearByke[0][k][0], rearByke[0][k + 1][0],
					rearByke[1][k + 1][0], rearByke[1][k][0], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, rearByke[0][k][0], rearByke[0][k][1],
					rearByke[0][k + 1][1], rearByke[0][k + 1][0], tLeftRear[k]);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, rearByke[1][k][0], rearByke[1][k + 1][0],
					rearByke[1][k + 1][1], rearByke[1][k][1], tRightRear[k]);
			faces[counter++] = buildFace(Renderer3D.CAR_TOP, rearByke[0][k][1], rearByke[1][k][1],
					rearByke[1][k + 1][1], rearByke[0][k + 1][1], tTopRear[k]);

		}
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, rearByke[0][2 - 1][0], rearByke[0][2 - 1][1],
				rearByke[1][2 - 1][1], rearByke[1][2 - 1][0], tBo[0]);

		///////////
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, roofByke[0][0][0], roofByke[0][1][0], roofByke[0][1][1],
				roofByke[0][0][1], tBo[0]);
		for (int k = 0; k < 2 - 1; k++) {

			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, roofByke[k][0][0], roofByke[k + 1][0][0],
					roofByke[k + 1][1][0], roofByke[k][1][0], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, roofByke[k][0][0], roofByke[k][0][1],
					roofByke[k + 1][0][1], roofByke[k + 1][0][0], tLeftRoof[k]);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, roofByke[k][1][0], roofByke[k + 1][1][0],
					roofByke[k + 1][1][1], roofByke[k][1][1], tRightRoof[k]);
			faces[counter++] = buildFace(Renderer3D.CAR_TOP, roofByke[k][0][1], roofByke[k][1][1],
					roofByke[k + 1][1][1], roofByke[k + 1][0][1], tTopRoof[k]);

		}
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, roofByke[2 - 1][0][0], roofByke[2 - 1][0][1],
				roofByke[2 - 1][1][1], roofByke[2 - 1][1][0], tBo[0]);

		return counter;
	}

	@Override
	public void printMeshData(PrintWriter pw) {
		super.printMeshData(pw);
		super.printFaces(pw, faces);
	}

	@Override
	public void printTexture(Graphics2D bufGraphics) {

		bufGraphics.setColor(Color.BLACK);
		bufGraphics.setStroke(new BasicStroke(0.1f));

		bufGraphics.setColor(new Color(255, 40, 1));
		printTexturePolygon(bufGraphics, tBo[0]);

		bufGraphics.setColor(new Color(0, 0, 255));
		printTexturePolygon(bufGraphics, tLeftRear);
		printTexturePolygon(bufGraphics, tLeftBody);
		printTexturePolygon(bufGraphics, tLeftFront);
		printTexturePolygon(bufGraphics, tLeftRoof);

		bufGraphics.setColor(new Color(0, 255, 255));
		printTexturePolygon(bufGraphics, tTopRear);
		printTexturePolygon(bufGraphics, tTopFront);
		printTexturePolygon(bufGraphics, tTopRoof);

		bufGraphics.setColor(new Color(0, 0, 255));
		printTexturePolygon(bufGraphics, tRightRear);
		printTexturePolygon(bufGraphics, tRightBody);
		printTexturePolygon(bufGraphics, tRightFront);
		printTexturePolygon(bufGraphics, tRightRoof);

		bufGraphics.setColor(new Color(0, 0, 0));
		printTexturePolygon(bufGraphics, tWh);
		printTexturePolygon(bufGraphics, tWheelSide);
	}

}
