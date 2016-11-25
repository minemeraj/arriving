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
 * One texture model Summing up the best creation logic so far
 *
 * @author Administrator
 *
 */
public class Tractor0Model extends VehicleModel {

	protected double wheelRadius;
	protected double wheelWidth;
	protected int wheel_rays;

	// body textures
	protected int[][] bo = { { 0, 1, 2, 3 } };

	private double dxTexture = 200;
	private double dyTexture = 200;

	int pnumx = 5;
	int pnumy = 5;
	int pnumz = 2;

	int frontNumx = 5;
	int frontNumy = 5;
	int frontNumz = 2;

	int backNumx = 5;
	int backNumy = 5;
	int backNumz = 2;

	int frontCarriageNumx = 2;
	int frontCarriageNumy = 2;
	int frontCarriageNumz = 2;

	int roofNumx = 3;
	int roofNumy = 4;
	int roofNumz = 2;

	private BPoint[][][] pFront;
	private BPoint[][][] pBack;
	private BPoint[][][] pFrontCarriage;

	protected BPoint[][] wheelLeftFront;
	protected BPoint[][] wheelRightFront;
	protected BPoint[][] wheelLeftRear;
	protected BPoint[][] wheelRightRear;

	public static String NAME = "Tractor";

	public Tractor0Model(double dx, double dy, double dz, double dxf, double dyf, double dzf, double dxr, double dyr,
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

		buildBody();
		buildWheels();
		buildTextures();

		// faces body
		int NF = 2*(pnumx*pnumy-1);
		// back
		NF += 2*(backNumx*backNumy-1);
		// front
		NF += 2*(frontNumx*frontNumy-1);
		// front carriage
		NF += 6;
		// roof
		NF += 2*(roofNumx*roofNumy-1);

		int totWheelPolygon = wheel_rays + 2 * (wheel_rays - 2);

		faces = new int[NF + totWheelPolygon * 4][3][4];

		int counter = 0;
		counter = buildFaces(counter);
		counter = buildWheelsFaces(counter, totWheelPolygon);

	}

	private int buildFaces(int counter) {

		for (int i = 0; i < pnumx - 1; i++) {
			for (int j = 0; j < pnumy - 1; j++) {

				if (i == 0) {
					faces[counter++] = buildFace(Renderer3D.CAR_LEFT, body[i][j][0], body[i][j][1], body[i][j + 1][1],
							body[i][j + 1][0], bo[0]);
				}
				if (j == 0) {
					faces[counter++] = buildFace(Renderer3D.CAR_BACK, body[i][j][0], body[i + 1][j][0],
							body[i + 1][j][1], body[i][j][1], bo[0]);				}

				faces[counter++] = buildFace(Renderer3D.CAR_TOP, body[i][j][1], body[i + 1][j][1],
						body[i + 1][j + 1][1], body[i][j + 1][1], bo[0]);

				faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, body[i][j][0], body[i][j + 1][0],
						body[i + 1][j + 1][0], body[i + 1][j][0], bo[0]);
				if (j + 1 == pnumy - 1) {
					faces[counter++] = buildFace(Renderer3D.CAR_FRONT, body[i][j + 1][0], body[i][j + 1][1],
							body[i + 1][j + 1][1], body[i + 1][j + 1][0], bo[0]);
				}
				if (i + 1 == pnumx - 1) {
					faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, body[i + 1][j][0], body[i + 1][j + 1][0],
							body[i + 1][j + 1][1], body[i + 1][j][1], bo[0]);
				}
			}
		}

		for (int i = 0; i < frontNumx - 1; i++) {
			for (int j = 0; j < frontNumy - 1; j++) {
				if (i == 0) {
					faces[counter++] = buildFace(Renderer3D.CAR_LEFT, pFront[i][j][0], pFront[i][j][1],
							pFront[i][j + 1][1], pFront[i][j + 1][0], bo[0]);
				}
				if (j == 0) {
					faces[counter++] = buildFace(Renderer3D.CAR_BACK, pFront[i][j][0], pFront[i + 1][j][0],
							pFront[i + 1][j][1], pFront[i][j][1], bo[0]);
				}
				faces[counter++] = buildFace(Renderer3D.CAR_TOP, pFront[i][j][1], pFront[i + 1][j][1],
						pFront[i + 1][j + 1][1], pFront[i][j + 1][1], bo[0]);
				faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, pFront[i][j][0], pFront[i][j + 1][0],
						pFront[i + 1][j + 1][0], pFront[i + 1][j][0], bo[0]);
				if (j + 1 == frontNumy - 1) {
					faces[counter++] = buildFace(Renderer3D.CAR_FRONT, pFront[i][j + 1][0], pFront[i][j + 1][1],
							pFront[i + 1][j + 1][1], pFront[i + 1][j + 1][0], bo[0]);
				}
				if (i + 1 == frontNumx - 1) {
					faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, pFront[i + 1][j][0], pFront[i + 1][j + 1][0],
							pFront[i + 1][j + 1][1], pFront[i + 1][j][1], bo[0]);
				}
			}
		}

		for (int i = 0; i < frontCarriageNumx - 1; i++) {
			for (int j = 0; j < frontCarriageNumy - 1; j++) {

				if (i == 0) {
					faces[counter++] = buildFace(Renderer3D.CAR_LEFT, pFrontCarriage[i][j][0], pFrontCarriage[i][j][1],
							pFrontCarriage[i][j + 1][1], pFrontCarriage[i][j + 1][0], bo[0]);
				}
				if (j == 0) {
					faces[counter++] = buildFace(Renderer3D.CAR_BACK, pFrontCarriage[i][j][0],
							pFrontCarriage[i + 1][j][0], pFrontCarriage[i + 1][j][1], pFrontCarriage[i][j][1], bo[0]);
				}
				faces[counter++] = buildFace(Renderer3D.CAR_TOP, pFrontCarriage[i][j][1], pFrontCarriage[i + 1][j][1],
						pFrontCarriage[i + 1][j + 1][1], pFrontCarriage[i][j + 1][1], bo[0]);
				faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, pFrontCarriage[i][j][0],
						pFrontCarriage[i][j + 1][0], pFrontCarriage[i + 1][j + 1][0], pFrontCarriage[i + 1][j][0],
						bo[0]);
				if (j + 1 == frontCarriageNumy - 1) {
					faces[counter++] = buildFace(Renderer3D.CAR_FRONT, pFrontCarriage[i][j + 1][0],
							pFrontCarriage[i][j + 1][1], pFrontCarriage[i + 1][j + 1][1],
							pFrontCarriage[i + 1][j + 1][0], bo[0]);
				}
				if (i + 1 == frontCarriageNumx - 1) {
					faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, pFrontCarriage[i + 1][j][0],
							pFrontCarriage[i + 1][j + 1][0], pFrontCarriage[i + 1][j + 1][1],
							pFrontCarriage[i + 1][j][1], bo[0]);
				}
			}
		}

		for (int i = 0; i < backNumx - 1; i++) {
			for (int j = 0; j < backNumy - 1; j++) {
				if (i == 0) {
					faces[counter++] = buildFace(Renderer3D.CAR_LEFT, pBack[i][j][0], pBack[i][j][1],
							pBack[i][j + 1][1], pBack[i][j + 1][0], bo[0]);
				}

				if (j == 0) {
					faces[counter++] = buildFace(Renderer3D.CAR_BACK, pBack[i][j][0], pBack[i + 1][j][0],
							pBack[i + 1][j][1], pBack[i][j][1], bo[0]);
				}

				// exclude the part covered by the roof to have a closed form:

				if ((i == 1 || i == 2) && (j > 0)) {
					;
				} else {
					faces[counter++] = buildFace(Renderer3D.CAR_TOP, pBack[i][j][1], pBack[i + 1][j][1],
							pBack[i + 1][j + 1][1], pBack[i][j + 1][1], bo[0]);
				}

				faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, pBack[i][j][0], pBack[i][j + 1][0],
						pBack[i + 1][j + 1][0], pBack[i + 1][j][0], bo[0]);

				if (j + 1 == backNumy - 1) {

					faces[counter++] = buildFace(Renderer3D.CAR_FRONT, pBack[i][j + 1][0], pBack[i][j + 1][1],
							pBack[i + 1][j + 1][1], pBack[i + 1][j + 1][0], bo[0]);
				}

				if (i + 1 == backNumx - 1) {

					faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, pBack[i + 1][j][0], pBack[i + 1][j + 1][0],
							pBack[i + 1][j + 1][1], pBack[i + 1][j][1], bo[0]);
				}

			}

		}

		for (int i = 0; i < roofNumx - 1; i++) {

			for (int j = 0; j < roofNumy - 1; j++) {

				if (i == 0) {

					faces[counter++] = buildFace(Renderer3D.CAR_LEFT, roof[i][j][0], roof[i][j][1], roof[i][j + 1][1],
							roof[i][j + 1][0], bo[0]);

				}

				if (j == 0) {

					faces[counter++] = buildFace(Renderer3D.CAR_BACK, roof[i][j][0], roof[i + 1][j][0],
							roof[i + 1][j][1], roof[i][j][1], bo[0]);
				}

				faces[counter++] = buildFace(Renderer3D.CAR_TOP, roof[i][j][1], roof[i + 1][j][1],
						roof[i + 1][j + 1][1], roof[i][j + 1][1], bo[0]);

				// addLine(roof[i][j][0],roof[i][j+1][0],roof[i+1][j+1][0],roof[i+1][j][0],Renderer3D.CAR_BOTTOM);

				if (j + 1 == roofNumy - 1) {

					faces[counter++] = buildFace(Renderer3D.CAR_FRONT, roof[i][j + 1][0], roof[i][j + 1][1],
							roof[i + 1][j + 1][1], roof[i + 1][j + 1][0], bo[0]);
				}

				if (i + 1 == roofNumx - 1) {

					faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, roof[i + 1][j][0], roof[i + 1][j + 1][0],
							roof[i + 1][j + 1][1], roof[i + 1][j][1], bo[0]);
				}

			}

		}

		//////////

		return counter;
	}

	private void buildTextures() {

		// Texture points

		double y = by;
		double x = bx;

		addTPoint(x, y, 0);
		addTPoint(x + dxTexture, y, 0);
		addTPoint(x + dxTexture, y + dyTexture, 0);
		addTPoint(x, y + dyTexture, 0);

		IMG_WIDTH = (int) (2 * bx + dxTexture);
		IMG_HEIGHT = (int) (2 * by + dyTexture);

	}

	private void buildWheels() {

		/*
		 * buildWheel(-rtyre_width,0,rr,rr,rtyre_width);
		 * buildWheel(x_side,0,rr,rr,rtyre_width);
		 *
		 * buildWheel(-ftyre_width,y_side,fr,fr,ftyre_width);
		 * buildWheel(x_side,y_side,fr,fr,ftyre_width);
		 */

		////
		double wz = 0;
		double wxLeft = dx * 0.5 + wheelWidth;
		double wxRight = dx * 0.5;

		double yRearAxle = 2.0 * wheelRadius;
		double yFrontAxle = dy + dyFront * 0.5;

		wheelLeftFront = buildWheel(x0 - wxLeft, yFrontAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		wheelRightFront = buildWheel(x0 + wxRight, yFrontAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		wheelLeftRear = buildWheel(x0 - wxLeft, yRearAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		wheelRightRear = buildWheel(x0 + wxRight, yRearAxle, wz, wheelRadius, wheelWidth, wheel_rays);

	}

	private int buildWheelsFaces(int counter, int totWheelPolygon) {

		///// WHEELS

		int[][][] wFaces = buildWheelFaces(wheelLeftFront, bo[0][0]);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}

		wFaces = buildWheelFaces(wheelRightFront, bo[0][0]);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}

		wFaces = buildWheelFaces(wheelLeftRear, bo[0][0]);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}

		wFaces = buildWheelFaces(wheelRightRear, bo[0][0]);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}

		return counter;

	}

	private void buildBody() {

		// basic sides:

		double rr = wheelRadius;
		double fr = wheelRadius * 0.753;

		double ftyre_width = wheelWidth;
		double rtyre_width = wheelWidth;

		double xc = dx * 0.5;

		body = new BPoint[pnumx][pnumy][pnumz];

		Segments p0 = new Segments(xc, dx, 0, dy, rr, dz);

		body[0][0][0] = addBPoint(-0.5, 0.0, 0.0, p0);
		body[1][0][0] = addBPoint(-0.25, 0.0, 0.0, p0);
		body[2][0][0] = addBPoint(0.0, 0.0, 0.0, p0);
		body[3][0][0] = addBPoint(0.25, 0.0, 0.0, p0);
		body[4][0][0] = addBPoint(0.5, 0.0, 0.0, p0);
		body[0][0][1] = addBPoint(-0.5, 0.0, 1.0, p0);
		body[1][0][1] = addBPoint(-0.25, 0.0, 1.0, p0);
		body[2][0][1] = addBPoint(0.0, 0.0, 1.0, p0);
		body[3][0][1] = addBPoint(0.25, 0.0, 1.0, p0);
		body[4][0][1] = addBPoint(0.5, 0.0, 1.0, p0);

		body[0][1][1] = addBPoint(-0.5, 0.25, 1.0, p0);
		body[1][1][1] = addBPoint(-0.25, 0.25, 1.0, p0);
		body[2][1][1] = addBPoint(0.0, 0.25, 1.0, p0);
		body[3][1][1] = addBPoint(0.25, 0.25, 1.0, p0);
		body[4][1][1] = addBPoint(0.5, 0.25, 1.0, p0);
		body[0][1][0] = addBPoint(-0.5, 0.25, 0.0, p0);
		body[1][1][0] = addBPoint(-0.25, 0.25, 0.0, p0);
		body[2][1][0] = addBPoint(0.0, 0.25, 0.0, p0);
		body[3][1][0] = addBPoint(0.25, 0.25, 0.0, p0);
		body[4][1][0] = addBPoint(0.5, 0.25, 0.0, p0);

		body[0][2][1] = addBPoint(-0.5, 0.5, 1.0, p0);
		body[1][2][1] = addBPoint(-0.25, 0.5, 1.0, p0);
		body[2][2][1] = addBPoint(0.0, 0.5, 1.0, p0);
		body[3][2][1] = addBPoint(0.25, 0.5, 1.0, p0);
		body[4][2][1] = addBPoint(0.5, 0.5, 1.0, p0);
		body[0][2][0] = addBPoint(-0.5, 0.5, 0.0, p0);
		body[1][2][0] = addBPoint(-0.25, 0.5, 0.0, p0);
		body[2][2][0] = addBPoint(0.0, 0.5, 0.0, p0);
		body[3][2][0] = addBPoint(0.25, 0.5, 0.0, p0);
		body[4][2][0] = addBPoint(0.5, 0.5, 0.0, p0);

		body[0][3][1] = addBPoint(-0.5, 0.75, 1.0, p0);
		body[1][3][1] = addBPoint(-0.25, 0.75, 1.0, p0);
		body[2][3][1] = addBPoint(0.0, 0.75, 1.0, p0);
		body[3][3][1] = addBPoint(0.25, 0.75, 1.0, p0);
		body[4][3][1] = addBPoint(0.5, 0.75, 1.0, p0);
		body[0][3][0] = addBPoint(-0.5, 0.75, 0.0, p0);
		body[1][3][0] = addBPoint(-0.25, 0.75, 0.0, p0);
		body[2][3][0] = addBPoint(0.0, 0.75, 0.0, p0);
		body[3][3][0] = addBPoint(0.25, 0.75, 0.0, p0);
		body[4][3][0] = addBPoint(0.5, 0.75, 0.0, p0);

		body[0][4][1] = addBPoint(-0.5, 1.0, 1.0, p0);
		body[1][4][1] = addBPoint(-0.25, 1.0, 1.0, p0);
		body[2][4][1] = addBPoint(0.0, 1.0, 1.0, p0);
		body[3][4][1] = addBPoint(0.25, 1.0, 1.0, p0);
		body[4][4][1] = addBPoint(0.5, 1.0, 1.0, p0);
		body[0][4][0] = addBPoint(-0.5, 1.0, 0.0, p0);
		body[1][4][0] = addBPoint(-0.25, 1.0, 0.0, p0);
		body[2][4][0] = addBPoint(0.0, 1.0, 0.0, p0);
		body[3][4][0] = addBPoint(0.25, 1.0, 0.0, p0);
		body[4][4][0] = addBPoint(0.5, 1.0, 0.0, p0);

		pFront = new BPoint[frontNumx][frontNumy][frontNumz];

		Segments f0 = new Segments(xc, dxFront, dyRear - rr, dyFront, rr + dz, dzFront);

		pFront[0][0][0] = addBPoint(-0.5, 0.0, 0.0, f0);
		pFront[1][0][0] = addBPoint(-0.25, 0.0, 0.0, f0);
		pFront[2][0][0] = addBPoint(0.0, 0.0, 0.0, f0);
		pFront[3][0][0] = addBPoint(0.25, 0.0, 0.0, f0);
		pFront[4][0][0] = addBPoint(0.5, 0.0, 0.0, f0);
		pFront[0][0][1] = addBPoint(-0.5, 0.0, 1.0, f0);
		pFront[1][0][1] = addBPoint(-0.25, 0.0, 1.0, f0);
		pFront[2][0][1] = addBPoint(0.0, 0.0, 1.0, f0);
		pFront[3][0][1] = addBPoint(0.25, 0.0, 1.0, f0);
		pFront[4][0][1] = addBPoint(0.5, 0.0, 1.0, f0);

		pFront[0][1][0] = addBPoint(-0.5, 0.25, 0.0, f0);
		pFront[1][1][0] = addBPoint(-0.25, 0.25, 0.0, f0);
		pFront[2][1][0] = addBPoint(0.0, 0.25, 0.0, f0);
		pFront[3][1][0] = addBPoint(0.25, 0.25, 0.0, f0);
		pFront[4][1][0] = addBPoint(0.5, 0.25, 0.0, f0);
		pFront[0][1][1] = addBPoint(-0.5, 0.25, 1.0, f0);
		pFront[1][1][1] = addBPoint(-0.25, 0.25, 1.0, f0);
		pFront[2][1][1] = addBPoint(0.0, 0.25, 1.0, f0);
		pFront[3][1][1] = addBPoint(0.25, 0.25, 1.0, f0);
		pFront[4][1][1] = addBPoint(0.5, 0.25, 1.0, f0);

		pFront[0][2][0] = addBPoint(-0.5, 0.5, 0.0, f0);
		pFront[1][2][0] = addBPoint(-0.25, 0.5, 0.0, f0);
		pFront[2][2][0] = addBPoint(0.0, 0.5, 0.0, f0);
		pFront[3][2][0] = addBPoint(0.25, 0.5, 0.0, f0);
		pFront[4][2][0] = addBPoint(0.5, 0.5, 0.0, f0);
		pFront[0][2][1] = addBPoint(-0.5, 0.5, 1.0, f0);
		pFront[1][2][1] = addBPoint(-0.25, 0.5, 1.0, f0);
		pFront[2][2][1] = addBPoint(0.0, 0.5, 1.0, f0);
		pFront[3][2][1] = addBPoint(0.25, 0.5, 1.0, f0);
		pFront[4][2][1] = addBPoint(0.5, 0.5, 1.0, f0);

		pFront[0][3][0] = addBPoint(-0.5, 0.75, 0.0, f0);
		pFront[1][3][0] = addBPoint(-0.25, 0.75, 0.0, f0);
		pFront[2][3][0] = addBPoint(0.0, 0.75, 0.0, f0);
		pFront[3][3][0] = addBPoint(0.25, 0.75, 0.0, f0);
		pFront[4][3][0] = addBPoint(0.5, 0.75, 0.0, f0);
		pFront[0][3][1] = addBPoint(-0.5, 0.75, 1.0, f0);
		pFront[1][3][1] = addBPoint(-0.25, 0.75, 1.0, f0);
		pFront[2][3][1] = addBPoint(0.0, 0.75, 1.0, f0);
		pFront[3][3][1] = addBPoint(0.25, 0.75, 1.0, f0);
		pFront[4][3][1] = addBPoint(0.5, 0.75, 1.0, f0);

		pFront[0][4][0] = addBPoint(-0.5, 1.0, 0.0, f0);
		pFront[1][4][0] = addBPoint(-0.25, 1.0, 0.0, f0);
		pFront[2][4][0] = addBPoint(0.0, 1.0, 0.0, f0);
		pFront[3][4][0] = addBPoint(0.25, 1.0, 0.0, f0);
		pFront[4][4][0] = addBPoint(0.5, 1.0, 0.0, f0);
		pFront[0][4][1] = addBPoint(-0.5, 1.0, 1.0, f0);
		pFront[1][4][1] = addBPoint(-0.25, 1.0, 1.0, f0);
		pFront[2][4][1] = addBPoint(0.0, 1.0, 1.0, f0);
		pFront[3][4][1] = addBPoint(0.25, 1.0, 1.0, f0);
		pFront[4][4][1] = addBPoint(0.5, 1.0, 1.0, f0);

		pBack = new BPoint[backNumx][backNumy][backNumz];

		Segments b0 = new Segments(xc, dxRear, -rr, dyRear, rr + dz, dzRear);

		pBack[0][0][0] = addBPoint(-0.5, 0, 0.8, b0);
		pBack[1][0][0] = addBPoint(-0.25, 0, 0.8, b0);
		pBack[2][0][0] = addBPoint(0.0, 0, 0.8, b0);
		pBack[3][0][0] = addBPoint(0.25, 0, 0.8, b0);
		pBack[4][0][0] = addBPoint(0.5, 0, 0.8, b0);
		pBack[0][0][1] = addBPoint(-0.5, 0, 0.9, b0);
		pBack[1][0][1] = addBPoint(-0.25, 0, 0.9, b0);
		pBack[2][0][1] = addBPoint(0.0, 0, 0.9, b0);
		pBack[3][0][1] = addBPoint(0.25, 0, 0.9, b0);
		pBack[4][0][1] = addBPoint(0.5, 0, 0.9, b0);

		pBack[0][1][0] = addBPoint(-0.5, 0.25, 0.9, b0);
		pBack[1][1][0] = addBPoint(-0.25, 0.25, 0.9, b0);
		pBack[2][1][0] = addBPoint(0.0, 0.25, 0.9, b0);
		pBack[3][1][0] = addBPoint(0.25, 0.25, 0.9, b0);
		pBack[4][1][0] = addBPoint(0.5, 0.25, 0.9, b0);
		pBack[0][1][1] = addBPoint(-0.5, 0.25, 1.0, b0);
		pBack[1][1][1] = addBPoint(-0.25, 0.25, 1.0, b0);
		pBack[2][1][1] = addBPoint(0.0, 0.25, 1.0, b0);
		pBack[3][1][1] = addBPoint(0.25, 0.25, 1.0, b0);
		pBack[4][1][1] = addBPoint(0.5, 0.25, 1.0, b0);

		pBack[0][2][0] = addBPoint(-0.5, 0.55, 0.9, b0);
		pBack[1][2][0] = addBPoint(-0.25, 0.55, 0.9, b0);
		pBack[2][2][0] = addBPoint(0.0, 0.55, 0.9, b0);
		pBack[3][2][0] = addBPoint(0.25, 0.55, 0.9, b0);
		pBack[4][2][0] = addBPoint(0.5, 0.55, 0.9, b0);
		pBack[0][2][1] = addBPoint(-0.5, 0.55, 1.0, b0);
		pBack[1][2][1] = addBPoint(-0.25, 0.55, 1.0, b0);
		pBack[2][2][1] = addBPoint(0.0, 0.55, 1.0, b0);
		pBack[3][2][1] = addBPoint(0.25, 0.55, 1.0, b0);
		pBack[4][2][1] = addBPoint(0.5, 0.55, 1.0, b0);

		pBack[0][3][0] = addBPoint(-0.5, 0.8, 0.0, b0);
		pBack[1][3][0] = addBPoint(-0.25, 0.8, 0.0, b0);
		pBack[2][3][0] = addBPoint(0.0, 0.8, 0.0, b0);
		pBack[3][3][0] = addBPoint(0.25, 0.8, 0.0, b0);
		pBack[4][3][0] = addBPoint(0.5, 0.8, 0.0, b0);
		pBack[0][3][1] = addBPoint(-0.5, 0.8, 0.25, b0);
		pBack[1][3][1] = addBPoint(-0.25, 0.8, 0.25, b0);
		pBack[2][3][1] = addBPoint(0.0, 0.8, 0.25, b0);
		pBack[3][3][1] = addBPoint(0.25, 0.8, 0.25, b0);
		pBack[4][3][1] = addBPoint(0.5, 0.8, 0.25, b0);

		pBack[0][4][0] = addBPoint(-0.5, 1.0, 0.0, b0);
		pBack[1][4][0] = addBPoint(-0.25, 1.0, 0.0, b0);
		pBack[2][4][0] = addBPoint(0.0, 1.0, 0.0, b0);
		pBack[3][4][0] = addBPoint(0.25, 1.0, 0.0, b0);
		pBack[4][4][0] = addBPoint(0.5, 1.0, 0.0, b0);
		pBack[0][4][1] = addBPoint(-0.5, 1.0, 0.25, b0);
		pBack[1][4][1] = addBPoint(-0.25, 1.0, 0.25, b0);
		pBack[2][4][1] = addBPoint(0.0, 1.0, 0.25, b0);
		pBack[3][4][1] = addBPoint(0.25, 1.0, 0.25, b0);
		pBack[4][4][1] = addBPoint(0.5, 1.0, 0.25, b0);

		pFrontCarriage = new BPoint[frontCarriageNumx][frontCarriageNumy][frontCarriageNumz];

		Segments fc0 = new Segments(xc, dx, dy - fr, 2 * fr, fr, rr - fr);

		pFrontCarriage[0][0][0] = addBPoint(-0.5, 0.0, 0.0, fc0);
		pFrontCarriage[1][0][0] = addBPoint(0.5, 0.0, 0.0, fc0);
		pFrontCarriage[0][0][1] = addBPoint(-0.5, 0.0, 1.0, fc0);
		pFrontCarriage[1][0][1] = addBPoint(0.5, 0.0, 1.0, fc0);
		pFrontCarriage[0][1][0] = addBPoint(-0.5, 1.0, 0.0, fc0);
		pFrontCarriage[1][1][0] = addBPoint(0.5, 1.0, 0.0, fc0);
		pFrontCarriage[0][1][1] = addBPoint(-0.5, 1.0, 1.0, fc0);
		pFrontCarriage[1][1][1] = addBPoint(0.5, 1.0, 1.0, fc0);

		roof = new BPoint[roofNumx][roofNumy][roofNumz];

		Segments r0 = new Segments(xc, dxRoof, -rr + dyRear - dyRoof, dyRoof, rr + dz, dzRoof);

		roof[0][0][0] = pBack[1][1][1];
		roof[1][0][0] = pBack[2][1][1];
		roof[2][0][0] = pBack[3][1][1];
		roof[0][0][1] = addBPoint(-0.5, 0, 1.0, r0);
		roof[1][0][1] = addBPoint(0.0, 0, 1.0, r0);
		roof[2][0][1] = addBPoint(0.5, 0, 1.0, r0);

		roof[0][1][0] = pBack[1][2][1];
		roof[1][1][0] = pBack[2][2][1];
		roof[2][1][0] = pBack[3][2][1];
		roof[0][1][1] = addBPoint(-0.5, 0.33, 1.0, r0);
		roof[1][1][1] = addBPoint(0.0, 0.33, 1.0, r0);
		roof[2][1][1] = addBPoint(0.5, 0.33, 1.0, r0);

		roof[0][2][0] = pBack[1][3][1];
		roof[1][2][0] = pBack[2][3][1];
		roof[2][2][0] = pBack[3][3][1];
		roof[0][2][1] = addBPoint(-0.5, 0.66, 1.0, r0);
		roof[1][2][1] = addBPoint(0.0, 0.66, 1.0, r0);
		roof[2][2][1] = addBPoint(0.5, 0.66, 1.0, r0);

		roof[0][3][0] = pBack[1][4][1];
		roof[1][3][0] = pBack[2][4][1];
		roof[2][3][0] = pBack[3][4][1];
		roof[0][3][1] = addBPoint(-0.5, 1.0, 1.0, r0);
		roof[1][3][1] = addBPoint(0.0, 1.0, 1.0, r0);
		roof[2][3][1] = addBPoint(0.5, 1.0, 1.0, r0);

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
