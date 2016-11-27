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

	private BPoint[][] wheelFront;
	private BPoint[][] wheelRear;
	private int[][] tTopRear;
	private int[][] tTopFront;
	private int[][] tTopRoof;
	private int[][] tRightRear;
	private int[][] tRightBody;
	private int[][] tRightFront;
	private int[][] tRightRoof;
	private BPoint[][][] rearByke;
	private BPoint[][][] roofByke;
	private BPoint[][][] handlebar;

	private int nyHandleBar = 3;
	private int nyRoof=2;
	private int nyRear=2;
	private int nyBody=2;


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

		int firstWheelTexturePoint = 4;

		buildBody();

		buildTextures();

		//faces
		int NF = 6 * 3;
		//handlebar
		NF += (nyHandleBar - 1) * 4 + 1;

		int totWheelPolygon = wheel_rays + 2 * (wheel_rays - 2);
		int NUM_WHEEL_FACES = 2 * totWheelPolygon;

		faces = new int[NF + NUM_WHEEL_FACES][3][4];

		int counter = 0;
		counter = buildBodyFaces(counter, body, handlebar, rearByke, roofByke);
		counter = buildWheelFaces(counter, firstWheelTexturePoint, totWheelPolygon);

	}

	private void buildBody() {
		double frame_side = (dx - wheelWidth) * 0.5;
		xc = -frame_side * 0.5;
		xc = wheelWidth / 2.0;

		Segments bd = new Segments(xc, wheelWidth, 2 * wheelRadius, dy, 2 * wheelRadius - dz, dz);

		body = new BPoint[2][nyBody][2];

		body[0][0][0] = addBPoint(-0.5, 0.0, 0, bd);
		body[1][0][0] = addBPoint(0.5, 0.0, 0, bd);
		body[1][1][0] = addBPoint(0.5, 1.0, 0, bd);
		body[0][1][0] = addBPoint(-0.5, 1.0, 0, bd);

		body[0][0][1] = addBPoint(-0.5, 0.0, 1.0, bd);
		body[1][0][1] = addBPoint(0.5, 0.0, 1.0, bd);
		body[0][1][1] = addBPoint(-0.5, 1.0, 1.0, bd);
		body[1][1][1] = addBPoint(0.5, 1.0, 1.0, bd);

		Segments rb = new Segments(xc, dxRear, 2 * wheelRadius - dyRear, dyRear, 2 * wheelRadius, dzRear);

		rearByke = new BPoint[nyRear][2][2];

		double zrb = dzRoof / dzRear;

		rearByke[0][0][0] = addBPoint(-0.5, 0.0, zrb, rb);
		rearByke[0][1][0] = addBPoint(0.5, 0.0, zrb, rb);
		rearByke[0][1][1] = addBPoint(0.5, 0.0, 1.0, rb);
		rearByke[0][0][1] = addBPoint(-0.5, 0.0, 1.0, rb);

		rearByke[1][0][0] = addBPoint(-0.5, 1.0, 0.0, rb);
		rearByke[1][1][0] = addBPoint(0.5, 1.0, 0.0, rb);
		rearByke[1][1][1] = addBPoint(0.5, 1.0, 1.0, rb);
		rearByke[1][0][1] = addBPoint(-0.5, 1.0, 1.0, rb);

		Segments rob = new Segments(xc, dxRoof, 2 * wheelRadius, dyRoof, 2 * wheelRadius, dzRoof);

		roofByke = new BPoint[nyRoof][2][2];

		roofByke[0][0][0] = addBPoint(-0.5, 0.0, 0.0, rob);
		roofByke[0][1][0] = addBPoint(0.5, 0.0, 0.0, rob);
		roofByke[0][1][1] = addBPoint(0.5, 0.0, 1.0, rob);
		roofByke[0][0][1] = addBPoint(-0.5, 0.0, 1.0, rob);

		roofByke[1][0][0] = addBPoint(-0.5, 1.0, 0.0, rob);
		roofByke[1][1][0] = addBPoint(0.5, 1.0, 0.0, rob);
		roofByke[1][1][1] = addBPoint(0.5, 1.0, 1.0, rob);
		roofByke[1][0][1] = addBPoint(-0.5, 1.0, 1.0, rob);

		Segments hb = new Segments(xc, dxFront, 2 * wheelRadius + dyRoof, dyFront, 2 * wheelRadius, dzFront);


		handlebar = new BPoint[nyHandleBar][2][2];

		double zrf = dzRoof / dzFront;

		handlebar[0][0][0] = addBPoint(-0.5, 0.0, 0.0, hb);
		handlebar[0][1][0] = addBPoint(0.5, 0.0, 0.0, hb);
		handlebar[0][1][1] = addBPoint(0.5, 0.0, 1.0, hb);
		handlebar[0][0][1] = addBPoint(-0.5, 0.0, 1.0, hb);

		handlebar[1][0][0] = addBPoint(-0.5, 0.5, 0.0, hb);
		handlebar[1][1][0] = addBPoint(0.5, 0.5, 0.0, hb);
		handlebar[1][1][1] = addBPoint(0.5, 0.5, 1.0, hb);
		handlebar[1][0][1] = addBPoint(-0.5, 0.5, 1.0, hb);

		handlebar[2][0][0] = addBPoint(0.0, 1.0, 0.0, hb);
		handlebar[2][1][0] = addBPoint(0.0, 1.0, 0.0, hb);
		handlebar[2][1][1] = addBPoint(0.0, 1.0, zrf, hb);
		handlebar[2][0][1] = addBPoint(0.0, 1.0, zrf, hb);

		double wz = wheelRadius;
		double wx = wheelWidth * 0.5;

		wheelFront = buildWheel(xc - wx, wheelRadius, wz, wheelRadius, wheelWidth, wheel_rays);
		wheelRear = buildWheel(xc - wx, 2 * wheelRadius + dy + wheelRadius, wz, wheelRadius, wheelWidth,
				wheel_rays);

	}

	private void initTexturesArrays() {
		int c = 0;
		c = initDoubleArrayValues(tBo = new int[1][4], c);
		c = initDoubleArrayValues(tLeftRear = new int[nyRear-1][4], c);
		c = initDoubleArrayValues(tLeftBody = new int[nyBody-1][4], c);
		c = initDoubleArrayValues(tLeftFront = new int[nyHandleBar-1][4], c);
		c = initDoubleArrayValues(tLeftRoof = new int[nyRoof-1][4], c);
		c = initDoubleArrayValues(tTopRear = new int[nyRear-1][4], c);
		c = initDoubleArrayValues(tTopFront = new int[nyHandleBar-1][4], c);
		c = initDoubleArrayValues(tTopRoof = new int[nyRoof-1][4], c);
		c = initDoubleArrayValues(tRightRear = new int[nyRear-1][4], c);
		c = initDoubleArrayValues(tRightBody = new int[nyBody-1][4], c);
		c = initDoubleArrayValues(tRightFront = new int[nyHandleBar-1][4], c);
		c = initDoubleArrayValues(tRightRoof = new int[nyRoof-1][4], c);
		c = initSingleArrayValues(tWh = new int[4], c);
	}

	private void buildTextures() {

		int shift = 1;
		double y = by;
		double x = bx;

		double maxDX=Math.max(dx, Math.max(dxFront,dxRear));
		double totDZ=Math.max(dxRear, dzFront)+Math.max(dz, 2*wheelRadius);

		addTRect(x, y, dx, dy);
		x+=dx+shift;
		buildLeftTextures(x,y,totDZ);
		x+=totDZ+shift;
		buildTopTextures(x,y,maxDX);
		x+=maxDX+shift;
		buildRightTextures(x,y,totDZ);
		x+=totDZ+ shift;
		y = by;
		buildWheelTexture(x,y);
		x+=wheelWidth;

		IMG_WIDTH = (int) ( bx + x);
		IMG_HEIGHT = (int) (2 * by + dy+dyRear+dyFront);

	}

	private void buildWheelTexture(double x, double y) {
		addTRect(x, y, wheelWidth, wheelWidth);
	}

	private void buildRightTextures(double x, double y, double totDZ) {

		addTPoint(x+rearByke[0][0][0].z,y+rearByke[0][0][0].y,0);
		addTPoint(x+rearByke[0][0][1].z,y+rearByke[0][0][1].y,0);
		addTPoint(x+rearByke[1][0][1].z,y+rearByke[1][0][1].y,0);
		addTPoint(x+rearByke[1][0][0].z,y+rearByke[1][0][0].y,0);

		for (int i = 0; i < nyHandleBar-1; i++) {
			addTPoint(x+handlebar[i][0][0].z,y+handlebar[i][0][0].y,0);
			addTPoint(x+handlebar[i][0][1].z,y+handlebar[i][0][1].y,0);
			addTPoint(x+handlebar[i+1][0][1].z,y+handlebar[i+1][0][1].y,0);
			addTPoint(x+handlebar[i+1][0][0].z,y+handlebar[i+1][0][0].y,0);
		}

		addTPoint(x+body[0][0][0].z,y+body[0][0][0].y,0);
		addTPoint(x+body[0][1][0].z,y+body[0][1][0].y,0);
		addTPoint(x+body[1][1][1].z,y+body[0][1][1].y,0);
		addTPoint(x+body[0][0][1].z,y+body[0][0][1].y,0);

		addTPoint(x+roofByke[0][0][0].z,y+roofByke[0][0][0].y,0);
		addTPoint(x+roofByke[0][0][1].z,y+roofByke[0][0][1].y,0);
		addTPoint(x+roofByke[1][0][1].z,y+roofByke[1][0][1].y,0);
		addTPoint(x+roofByke[1][0][0].z,y+roofByke[1][0][0].y,0);
	}

	private void buildTopTextures(double x, double y, double maxDX) {

		double deltaRE= (maxDX-dxRear)*0.5;
		double deltaRo= (maxDX-dxRoof)*0.5;
		double deltaFR= (maxDX-dxFront)*0.5;

		addTRect(x+deltaRE, y, dxRear, dyRear);
		addTRect(x+deltaRo, y+dyRear, dxRoof, dyRoof);
		for (int i = 0; i < nyHandleBar-1; i++) {
			addTRect(x+deltaFR, y+dyRear+dyRoof, dxFront, dyFront);
		}

	}

	private void buildLeftTextures(double x, double y, double totDZ) {

		addTPoint(x+rearByke[0][0][0].z,y+rearByke[0][0][0].y,0);
		addTPoint(x+rearByke[0][0][1].z,y+rearByke[0][0][1].y,0);
		addTPoint(x+rearByke[1][0][1].z,y+rearByke[1][0][1].y,0);
		addTPoint(x+rearByke[1][0][0].z,y+rearByke[1][0][0].y,0);

		for (int i = 0; i < nyHandleBar-1; i++) {
			addTPoint(x+handlebar[i][0][0].z,y+handlebar[i][0][0].y,0);
			addTPoint(x+handlebar[i][0][1].z,y+handlebar[i][0][1].y,0);
			addTPoint(x+handlebar[i+1][0][1].z,y+handlebar[i+1][0][1].y,0);
			addTPoint(x+handlebar[i+1][0][0].z,y+handlebar[i+1][0][0].y,0);
		}

		addTPoint(x+body[0][0][0].z,y+body[0][0][0].y,0);
		addTPoint(x+body[0][1][0].z,y+body[0][1][0].y,0);
		addTPoint(x+body[1][1][1].z,y+body[0][1][1].y,0);
		addTPoint(x+body[0][0][1].z,y+body[0][0][1].y,0);

		addTPoint(x+roofByke[0][0][0].z,y+roofByke[0][0][0].y,0);
		addTPoint(x+roofByke[0][0][1].z,y+roofByke[0][0][1].y,0);
		addTPoint(x+roofByke[1][0][1].z,y+roofByke[1][0][1].y,0);
		addTPoint(x+roofByke[1][0][0].z,y+roofByke[1][0][0].y,0);
	}

	private int buildWheelFaces(int counter, int totBlockTexturesPoints, int totWheelPolygon) {

		int[][][] wFaces = buildWheelFaces(wheelFront, tWh);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}

		wFaces = buildWheelFaces(wheelRear, tWh);
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
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, body[0][0][0], body[0][0][1], body[0][1][1], body[0][1][0],
				tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, body[1][0][0], body[1][1][0], body[1][1][1], body[1][0][1],
				tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, body[0][0][0], body[1][0][0], body[1][0][1], body[0][0][1],
				tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, body[0][1][0], body[0][1][1], body[1][1][1], body[1][1][0],
				tBo[0]);

		int nyh = handlebar.length;
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, handlebar[0][0][0], handlebar[0][1][0], handlebar[0][1][1],
				handlebar[0][0][1], tBo[0]);
		for (int k = 0; k < nyh - 1; k++) {

			if (k < nyh - 2) {
				faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, handlebar[k][0][0], handlebar[k + 1][0][0],
						handlebar[k + 1][1][0], handlebar[k][1][0], tBo[0]);
			} else {
				faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, handlebar[k][0][0], handlebar[k + 1][0][0],
						handlebar[k + 1][1][0], tBo[0]);
			}

			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, handlebar[k][0][0], handlebar[k][0][1],
					handlebar[k + 1][0][1], handlebar[k + 1][0][0], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, handlebar[k][1][0], handlebar[k + 1][1][0],
					handlebar[k + 1][1][1], handlebar[k][1][1], tBo[0]);

			if (k < nyh - 2) {
				faces[counter++] = buildFace(Renderer3D.CAR_TOP, handlebar[k][0][1], handlebar[k][1][1],
						handlebar[k + 1][1][1], handlebar[k + 1][0][1], tBo[0]);
			} else {
				faces[counter++] = buildFace(Renderer3D.CAR_TOP, handlebar[k][0][1], handlebar[k][1][1],
						handlebar[k + 1][0][1], tBo[0]);
			}
		}

		///////
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, rearByke[0][0][0], rearByke[0][1][0], rearByke[0][1][1],
				rearByke[0][0][1], tBo[0]);
		for (int k = 0; k < 2 - 1; k++) {

			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, rearByke[k][0][0], rearByke[k + 1][0][0],
					rearByke[k + 1][1][0], rearByke[k][1][0], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, rearByke[k][0][0], rearByke[k][0][1],
					rearByke[k + 1][0][1], rearByke[k + 1][0][0], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, rearByke[k][1][0], rearByke[k + 1][1][0],
					rearByke[k + 1][1][1], rearByke[k][1][1], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_TOP, rearByke[k][0][1], rearByke[k][1][1],
					rearByke[k + 1][1][1], rearByke[k + 1][0][1], tBo[0]);

		}
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, rearByke[2 - 1][0][0], rearByke[2 - 1][0][1],
				rearByke[2 - 1][1][1], rearByke[2 - 1][1][0], tBo[0]);

		///////////
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, roofByke[0][0][0], roofByke[0][1][0], roofByke[0][1][1],
				roofByke[0][0][1], tBo[0]);
		for (int k = 0; k < 2 - 1; k++) {

			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, roofByke[k][0][0], roofByke[k + 1][0][0],
					roofByke[k + 1][1][0], roofByke[k][1][0], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, roofByke[k][0][0], roofByke[k][0][1],
					roofByke[k + 1][0][1], roofByke[k + 1][0][0], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, roofByke[k][1][0], roofByke[k + 1][1][0],
					roofByke[k + 1][1][1], roofByke[k][1][1], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_TOP, roofByke[k][0][1], roofByke[k][1][1],
					roofByke[k + 1][1][1], roofByke[k + 1][0][1], tBo[0]);

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
	}

}
