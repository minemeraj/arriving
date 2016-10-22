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
		points = new Vector<Point3D>();
		texturePoints = new Vector<Point3D>();

		buildTextures();

		int firstWheelTexturePoint = 4;

		double frame_side = (dx - wheelWidth) * 0.5;
		xc = -frame_side * 0.5;

		/*
		 * BPoint[][][] leftFrame=new BPoint[2][2][2];
		 *
		 *
		 *
		 * Segments lf=new
		 * Segments(xc,frame_side,dyRear,dy,wheelRadius,wheelRadius+dz);
		 *
		 * leftFrame[0][0][0]=addBPoint(-0.5,0.0,0,lf);
		 * leftFrame[1][0][0]=addBPoint(0.5,0.0,0,lf);
		 * leftFrame[1][1][0]=addBPoint(0.5,0.42,0,lf);
		 * leftFrame[0][1][0]=addBPoint(-0.5,0.42,0,lf);
		 *
		 * leftFrame[0][0][1]=addBPoint(-0.5,0.26,1.0,lf);
		 * leftFrame[1][0][1]=addBPoint(0.5,0.26,1.0,lf);
		 * leftFrame[0][1][1]=addBPoint(-0.5,0.82,1.0,lf);
		 * leftFrame[1][1][1]=addBPoint(0.5,0.82,1.0,lf);
		 *
		 * BPoint[][][] rightFrame=new BPoint[2][2][2];
		 *
		 *
		 * xc=wheelWidth+frame_side*0.5;
		 *
		 * Segments rf=new
		 * Segments(xc,frame_side,dyRear,dy,wheelRadius,wheelRadius+dz);
		 *
		 * rightFrame[0][0][0]=addBPoint(-0.5,0.0,0,rf);
		 * rightFrame[1][0][0]=addBPoint(0.5,0.0,0,rf);
		 * rightFrame[1][1][0]=addBPoint(0.5,0.42,0,rf);
		 * rightFrame[0][1][0]=addBPoint(-0.5,0.42,0,rf);
		 *
		 * rightFrame[0][0][1]=addBPoint(-0.5,0.26,1.0,rf);
		 * rightFrame[1][0][1]=addBPoint(0.5,0.26,1.0,rf);
		 * rightFrame[0][1][1]=addBPoint(-0.5,0.82,1.0,rf);
		 * rightFrame[1][1][1]=addBPoint(0.5,0.82,1.0,rf);
		 *
		 *
		 *
		 *
		 * BPoint[][][] leftFork=new BPoint[2][2][2];
		 *
		 * leftFork[0][0][0]=addBPoint(-0.5,0.91,0,lf);
		 * leftFork[1][0][0]=addBPoint(0.5,0.91,0,lf);
		 * leftFork[0][1][0]=addBPoint(-0.5,1.09,0,lf);
		 * leftFork[1][1][0]=addBPoint(0.5,1.09,0,lf);
		 *
		 * leftFork[0][0][1]=addBPoint(-0.5,0.82,1.0,lf);
		 * leftFork[1][0][1]=addBPoint(0.5,0.82,1.0,lf);
		 * leftFork[0][1][1]=addBPoint(-0.5,1.0,1.0,lf);
		 * leftFork[1][1][1]=addBPoint(0.5,1.0,1.0,lf);
		 *
		 *
		 *
		 *
		 *
		 * BPoint[][][] rightFork=new BPoint[2][2][2];
		 *
		 * rightFork[0][0][0]=addBPoint(-0.5,0.91,0,rf);
		 * rightFork[1][0][0]=addBPoint(0.5,0.91,0,rf);
		 * rightFork[0][1][0]=addBPoint(-0.5,1.09,0,rf);
		 * rightFork[1][1][0]=addBPoint(0.5,1.09,0,rf);
		 *
		 * rightFork[0][0][1]=addBPoint(-0.5,0.82,1.0,rf);
		 * rightFork[1][0][1]=addBPoint(0.5,0.82,1.0,rf);
		 * rightFork[0][1][1]=addBPoint(-0.5,1.0,1.0,rf);
		 * rightFork[1][1][1]=addBPoint(0.5,1.0,1.0,rf);
		 */

		xc = wheelWidth / 2.0;

		Segments bd = new Segments(xc, wheelWidth, 2 * wheelRadius, dy, 2 * wheelRadius - dz, dz);

		BPoint[][][] body = new BPoint[2][2][2];

		body[0][0][0] = addBPoint(-0.5, 0.0, 0, bd);
		body[1][0][0] = addBPoint(0.5, 0.0, 0, bd);
		body[1][1][0] = addBPoint(0.5, 1.0, 0, bd);
		body[0][1][0] = addBPoint(-0.5, 1.0, 0, bd);

		body[0][0][1] = addBPoint(-0.5, 0.0, 1.0, bd);
		body[1][0][1] = addBPoint(0.5, 0.0, 1.0, bd);
		body[0][1][1] = addBPoint(-0.5, 1.0, 1.0, bd);
		body[1][1][1] = addBPoint(0.5, 1.0, 1.0, bd);

		Segments rb = new Segments(xc, dxRear, 2 * wheelRadius - dyRear, dyRear, 2 * wheelRadius, dzRear);

		BPoint[][][] rearByke = new BPoint[2][2][2];

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

		BPoint[][][] roofByke = new BPoint[2][2][2];

		roofByke[0][0][0] = addBPoint(-0.5, 0.0, 0.0, rob);
		roofByke[0][1][0] = addBPoint(0.5, 0.0, 0.0, rob);
		roofByke[0][1][1] = addBPoint(0.5, 0.0, 1.0, rob);
		roofByke[0][0][1] = addBPoint(-0.5, 0.0, 1.0, rob);

		roofByke[1][0][0] = addBPoint(-0.5, 1.0, 0.0, rob);
		roofByke[1][1][0] = addBPoint(0.5, 1.0, 0.0, rob);
		roofByke[1][1][1] = addBPoint(0.5, 1.0, 1.0, rob);
		roofByke[1][0][1] = addBPoint(-0.5, 1.0, 1.0, rob);

		Segments hb = new Segments(xc, dxFront, 2 * wheelRadius + dyRoof, dyFront, 2 * wheelRadius, dzFront);

		int nyh = 3;
		BPoint[][][] handlebar = new BPoint[nyh][2][2];

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

		BPoint[][] wheelFront = buildWheel(xc - wx, wheelRadius, wz, wheelRadius, wheelWidth, wheel_rays);
		BPoint[][] wheelRear = buildWheel(xc - wx, 2 * wheelRadius + dy + wheelRadius, wz, wheelRadius, wheelWidth,
				wheel_rays);

		// faces
		int NF = 6 * 3;
		NF += (nyh - 1) * 4 + 1;// handlebar

		int totWheelPolygon = wheel_rays + 2 * (wheel_rays - 2);
		int NUM_WHEEL_FACES = 2 * totWheelPolygon;

		faces = new int[NF + NUM_WHEEL_FACES][3][4];

		int counter = 0;
		counter = buildBodyFaces(counter, body, handlebar, rearByke, roofByke);

		counter = buildWheelFaces(counter, firstWheelTexturePoint, totWheelPolygon, wheelFront, wheelRear);

		IMG_WIDTH = (int) (2 * bx + dx + wheelWidth);
		IMG_HEIGHT = (int) (2 * by + dy);

	}

	private void buildTextures() {
		// Texture points

		double y = by;
		double x = bx;

		addTPoint(x, y, 0);
		addTPoint(x + dx, y, 0);
		addTPoint(x + dx, y + dy, 0);
		addTPoint(x, y + dy, 0);

		// wheel texture, a black square for simplicity:

		x = bx + dx;
		y = by;

		addTPoint(x, y, 0);
		addTPoint(x + wheelWidth, y, 0);
		addTPoint(x + wheelWidth, y + wheelWidth, 0);
		addTPoint(x, y + wheelWidth, 0);

	}

	private int buildWheelFaces(int counter, int totBlockTexturesPoints, int totWheelPolygon, BPoint[][] wheelFront,
			BPoint[][] wheelRear) {

		int[][][] wFaces = buildWheelFaces(wheelFront, totBlockTexturesPoints);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}

		wFaces = buildWheelFaces(wheelRear, totBlockTexturesPoints);
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
				0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, body[0][0][0], body[0][1][0], body[1][1][0], body[1][0][0],
				0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, body[0][0][0], body[0][0][1], body[0][1][1], body[0][1][0],
				0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, body[1][0][0], body[1][1][0], body[1][1][1], body[1][0][1],
				0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, body[0][0][0], body[1][0][0], body[1][0][1], body[0][0][1],
				0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, body[0][1][0], body[0][1][1], body[1][1][1], body[1][1][0],
				0, 1, 2, 3);

		int nyh = handlebar.length;
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, handlebar[0][0][0], handlebar[0][1][0], handlebar[0][1][1],
				handlebar[0][0][1], 0, 1, 2, 3);
		for (int k = 0; k < nyh - 1; k++) {

			if (k < nyh - 2) {
				faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, handlebar[k][0][0], handlebar[k + 1][0][0],
						handlebar[k + 1][1][0], handlebar[k][1][0], 0, 1, 2, 3);
			} else {
				faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, handlebar[k][0][0], handlebar[k + 1][0][0],
						handlebar[k + 1][1][0], 0, 1, 2);
			}

			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, handlebar[k][0][0], handlebar[k][0][1],
					handlebar[k + 1][0][1], handlebar[k + 1][0][0], 0, 1, 2, 3);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, handlebar[k][1][0], handlebar[k + 1][1][0],
					handlebar[k + 1][1][1], handlebar[k][1][1], 0, 1, 2, 3);

			if (k < nyh - 2) {
				faces[counter++] = buildFace(Renderer3D.CAR_TOP, handlebar[k][0][1], handlebar[k][1][1],
						handlebar[k + 1][1][1], handlebar[k + 1][0][1], 0, 1, 2, 3);
			} else {
				faces[counter++] = buildFace(Renderer3D.CAR_TOP, handlebar[k][0][1], handlebar[k][1][1],
						handlebar[k + 1][0][1], 0, 1, 2);
			}
		}

		///////
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, rearByke[0][0][0], rearByke[0][1][0], rearByke[0][1][1],
				rearByke[0][0][1], 0, 1, 2, 3);
		for (int k = 0; k < 2 - 1; k++) {

			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, rearByke[k][0][0], rearByke[k + 1][0][0],
					rearByke[k + 1][1][0], rearByke[k][1][0], 0, 1, 2, 3);
			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, rearByke[k][0][0], rearByke[k][0][1],
					rearByke[k + 1][0][1], rearByke[k + 1][0][0], 0, 1, 2, 3);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, rearByke[k][1][0], rearByke[k + 1][1][0],
					rearByke[k + 1][1][1], rearByke[k][1][1], 0, 1, 2, 3);
			faces[counter++] = buildFace(Renderer3D.CAR_TOP, rearByke[k][0][1], rearByke[k][1][1],
					rearByke[k + 1][1][1], rearByke[k + 1][0][1], 0, 1, 2, 3);

		}
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, rearByke[2 - 1][0][0], rearByke[2 - 1][0][1],
				rearByke[2 - 1][1][1], rearByke[2 - 1][1][0], 0, 1, 2, 3);

		///////////
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, roofByke[0][0][0], roofByke[0][1][0], roofByke[0][1][1],
				roofByke[0][0][1], 0, 1, 2, 3);
		for (int k = 0; k < 2 - 1; k++) {

			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, roofByke[k][0][0], roofByke[k + 1][0][0],
					roofByke[k + 1][1][0], roofByke[k][1][0], 0, 1, 2, 3);
			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, roofByke[k][0][0], roofByke[k][0][1],
					roofByke[k + 1][0][1], roofByke[k + 1][0][0], 0, 1, 2, 3);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, roofByke[k][1][0], roofByke[k + 1][1][0],
					roofByke[k + 1][1][1], roofByke[k][1][1], 0, 1, 2, 3);
			faces[counter++] = buildFace(Renderer3D.CAR_TOP, roofByke[k][0][1], roofByke[k][1][1],
					roofByke[k + 1][1][1], roofByke[k + 1][0][1], 0, 1, 2, 3);

		}
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, roofByke[2 - 1][0][0], roofByke[2 - 1][0][1],
				roofByke[2 - 1][1][1], roofByke[2 - 1][1][0], 0, 1, 2, 3);

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

		for (int i = 0; i < faces.length; i++) {

			int[][] face = faces[i];
			int[] tPoints = face[2];
			if (tPoints.length == 4) {
				printTexturePolygon(bufGraphics, tPoints[0], tPoints[1], tPoints[2], tPoints[3]);
			} else if (tPoints.length == 3) {
				printTexturePolygon(bufGraphics, tPoints[0], tPoints[1], tPoints[2]);
			}

		}

	}

}
