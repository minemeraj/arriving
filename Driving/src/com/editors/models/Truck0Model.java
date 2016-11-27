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
public class Truck0Model extends VehicleModel {

	protected double dxWagon = 0;
	protected double dyWagon = 0;
	protected double dzWagon = 0;

	protected double wheelRadius;
	protected double wheelWidth;
	protected int wheel_rays;

	// rear and cabin textures
	protected int[] tBackRear = null;
	protected int[][] tRe = null;

	int[][] tLeftRear = null;
	int[][] tLeftFront = null;
	protected int[][] tLeftRoof = null;
	protected int[] tTopRoof = null;
	protected int[][] tRightRoof = null;
	int[][] tRightRear = null;
	int[][] tRightFront = null;

	// wagon textures
	protected int[][] tBackWagon = null;
	protected int[][] tLeftWagon = null;
	protected int[][] tWagon = null;
	protected int[][] tRightWagon = null;

	// window texture
	protected int[][] tWi = null;
	// wheel texture
	protected int[][] tWh = null;

	protected double dyTexture = 50;
	protected double dxTexture = 50;
	protected BPoint[][] wheelLeftFront;
	protected BPoint[][] wheelRightFront;
	protected BPoint[][] wheelLeftRear;
	protected BPoint[][] wheelRightRear;

	protected int nzCab = 2;
	protected int nYcab = 6;
	protected int nzBody = 2;
	protected int nyBody = 2;
	protected int nWagonSides=1;
	public static final String NAME = "Truck";

	public Truck0Model(double dxRoof, double dyRoof, double dzRoof, double dxFront, double dyfront, double dzFront,
			double dxRear, double dyRear, double dzRear, double dxWagon, double dyWagon, double dzWagon,
			double rearOverhang, double frontOverhang, double rearOverhang1, double frontOverhang1, double wheelRadius,
			double wheelWidth, int wheel_rays) {
		super();
		this.dxRear = dxRear;
		this.dyRear = dyRear;
		this.dzRear = dzRear;

		this.dxFront = dxFront;
		this.dyFront = dyfront;
		this.dzFront = dzFront;

		this.dxWagon = dxWagon;
		this.dyWagon = dyWagon;
		this.dzWagon = dzWagon;

		this.dxRoof = dxRoof;
		this.dyRoof = dyRoof;
		this.dzRoof = dzRoof;

		this.rearOverhang = rearOverhang;
		this.frontOverhang = frontOverhang;

		this.rearOverhang1 = rearOverhang1;
		this.frontOverhang1 = frontOverhang1;

		this.wheelRadius = wheelRadius;
		this.wheelWidth = wheelWidth;
		this.wheel_rays = wheel_rays;
	}

	@Override
	public void initMesh() {
		points = new Vector<Point3D>();
		texturePoints = new Vector<Point3D>();

		initTexturesArrays();



		x0 = dxWagon * 0.5;

		buildCabin();
		buildRear();
		buildWagon();
		buildWheels();

		buildTextures();

		int totWheelPolygon = wheel_rays + 2 * (wheel_rays - 2);
		int NUM_WHEEL_FACES = 4 * totWheelPolygon;

		// faces
		//int NF = 2 + (2 + (nzCab - 1)) * (nYcab - 1) * 2;
		int NF =2 *(nzCab* nYcab - 1) ;
		NF += 2 + (nzBody - 1) * 4;
		NF += 2 + nWagonSides * 4;
		// cabin roof
		NF += 8;

		faces = new int[NF + NUM_WHEEL_FACES][3][4];

		int counter = 0;
		counter = buildBodyFaces(counter, nzBody);
		counter = buildWheelFaces(counter, totWheelPolygon);

	}

	protected void initTexturesArrays() {
		int c = 0;
		c = initDoubleArrayValues(tRe = new int[1][4], c);
		c = initSingleArrayValues(tBackRear = new int[4], c);
		c = initDoubleArrayValues(tBackWagon = new int[1][4], c);
		c = initDoubleArrayValues(tLeftRear = new int[nyBody - 1][4], c);
		c = initDoubleArrayValues(tLeftFront = new int[nYcab - 1][4], c);
		c = initDoubleArrayValues(tLeftRoof = new int[2][4], c);
		c = initDoubleArrayValues(tLeftWagon = new int[1][4], c);
		c = initDoubleArrayValues(tWagon = new int[nWagonSides][4], c);
		c = initSingleArrayValues(tTopRoof = new int[4], c);
		c = initDoubleArrayValues(tRightRear = new int[nyBody - 1][4], c);
		c = initDoubleArrayValues(tRightFront = new int[nYcab - 1][4], c);
		c = initDoubleArrayValues(tRightRoof = new int[2][4], c);
		c = initDoubleArrayValues(tRightWagon = new int[1][4], c);
		c = initDoubleArrayValues(tWi = new int[1][4], c);
		c = initDoubleArrayValues(tWh = new int[1][4], c);

	}

	protected void buildWheels() {

		double wz = 0;
		double wxLeft = dxRear * 0.5 + wheelWidth;
		double wxRight = dxRear * 0.5;

		double yRearAxle = rearOverhang;
		double yFrontAxle = dyRear + dyFront * 0.5;

		wheelLeftFront = buildWheel(x0 - wxLeft, yFrontAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		wheelRightFront = buildWheel(x0 + wxRight, yFrontAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		wheelLeftRear = buildWheel(x0 - wxLeft, yRearAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		wheelRightRear = buildWheel(x0 + wxRight, yRearAxle, wz, wheelRadius, wheelWidth, wheel_rays);

	}

	protected int buildBodyFaces(int counter, int nzRear) {

		counter = buildCabinFaces(counter, nYcab, nzCab);
		counter = buildRearFaces(counter, nzRear);
		counter = buildWagonFaces(counter);

		return counter;
	}

	protected int buildRearFaces(int counter, int nzRear) {

		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, rear[0][0], rear[0][3], rear[0][2], rear[0][1], tRe[0]);

		for (int k = 0; k < nzRear - 1; k++) {

			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, rear[k][0], rear[k + 1][0], rear[k + 1][3], rear[k][3],
					tLeftRear[k]);
			faces[counter++] = buildFace(Renderer3D.CAR_BACK, rear[k][0], rear[k][1], rear[k + 1][1], rear[k + 1][0],
					tBackRear);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, rear[k][1], rear[k][2], rear[k + 1][2], rear[k + 1][1],
					tRightRear[k]);
			faces[counter++] = buildFace(Renderer3D.CAR_FRONT, rear[k][2], rear[k][3], rear[k + 1][3], rear[k + 1][2],
					tRe[0]);

		}

		faces[counter++] = buildFace(Renderer3D.CAR_TOP, rear[nzRear - 1][0], rear[nzRear - 1][1], rear[nzRear - 1][2],
				rear[nzRear - 1][3], tRe[0]);

		return counter;
	}

	protected int buildRearYFaces(int counter, int nzRear) {

		int numSections = rear.length;

		faces[counter++] = buildFace(Renderer3D.CAR_BACK, rear[0][0], rear[0][1], rear[0][2], rear[0][3], tBackRear);

		for (int i = 0; i < numSections - 1; i++) {

			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, rear[i + 1][0], rear[i][0], rear[i][3], rear[i + 1][3],
					tLeftRear[i]);
			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, rear[i][0], rear[i + 1][0], rear[i + 1][1], rear[i][1],
					tRe[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, rear[i][1], rear[i + 1][1], rear[i + 1][2], rear[i][2],
					tRightRear[i]);
			faces[counter++] = buildFace(Renderer3D.CAR_TOP, rear[i][2], rear[i + 1][2], rear[i + 1][3], rear[i][3],
					tRe[0]);

		}
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, rear[numSections - 1][0], rear[numSections - 1][3],
				rear[numSections - 1][2], rear[numSections - 1][1], tRe[0]);
		return counter;

	}

	protected int buildCabinFaces(int counter, int nYcab, int nzCab) {
		// cab
		for (int j = 0; j < nYcab - 1; j++) {

			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, cab[0][j][0], cab[0][j + 1][0], cab[1][j + 1][0],
					cab[1][j][0], tRe[0]);

			for (int k = 0; k < nzCab - 1; k++) {

				faces[counter++] = buildFace(Renderer3D.CAR_LEFT, cab[0][j][k], cab[0][j][k + 1], cab[0][j + 1][k + 1],
						cab[0][j + 1][k], tLeftFront[j]);
				if (j == 0) {
					faces[counter++] = buildFace(Renderer3D.CAR_BACK, cab[0][j][k], cab[1][j][k], cab[1][j][k + 1],
							cab[0][j][k + 1], tRe[0]);
				}
				faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, cab[1][j][k], cab[1][j + 1][k], cab[1][j + 1][k + 1],
						cab[1][j][k + 1], tRightFront[j]);
				if (j == nYcab - 2) {
					faces[counter++] = buildFace(Renderer3D.CAR_FRONT, cab[0][nYcab - 1][k], cab[0][nYcab - 1][k + 1],
							cab[1][nYcab - 1][k + 1], cab[1][nYcab - 1][k], tRe[0]);
				}

			}
			faces[counter++] = buildFace(Renderer3D.CAR_TOP, cab[0][j][nzCab - 1], cab[1][j][nzCab - 1],
					cab[1][j + 1][nzCab - 1], cab[0][j + 1][nzCab - 1], tRe[0]);
		}

		faces[counter++] = buildFace(Renderer3D.CAR_BACK, roof[0][0][0], roof[1][0][0], roof[1][0][1], roof[0][0][1],
				tWi[0]);

		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, roof[0][0][0], roof[0][0][1], roof[0][1][1], roof[0][1][0],
				tLeftRoof[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, roof[0][1][0], roof[0][1][1], roof[0][2][1], roof[0][2][0],
				tLeftRoof[1]);
		faces[counter++] = buildFace(Renderer3D.CAR_TOP, roof[0][0][1], roof[1][0][1], roof[1][1][1], roof[0][1][1],
				tTopRoof);
		faces[counter++] = buildFace(Renderer3D.CAR_TOP, roof[0][1][1], roof[1][1][1], roof[1][2][1], roof[0][2][1],
				tTopRoof);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, roof[1][0][0], roof[1][1][0], roof[1][1][1], roof[1][0][1],
				tRightRoof[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, roof[1][1][0], roof[1][2][0], roof[1][2][1], roof[1][1][1],
				tRightRoof[1]);

		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, roof[0][2][1], roof[1][2][1], roof[1][2][0], roof[0][2][0],
				tWi[0]);

		return counter;

	}

	/**
	 *
	 * BUILD WAGON BY Z SECTIONS
	 *
	 * @param nzBody
	 * @return
	 */
	protected int buildWagonFaces(int counter ) {

		int nzWagon=nWagonSides+1;

		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, wagon[0][0], wagon[0][3], wagon[0][2], wagon[0][1],
				tWagon[0]);

		for (int k = 0; k < nzWagon - 1; k++) {

			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, wagon[k][0], wagon[k + 1][0], wagon[k + 1][3],
					wagon[k][3], tLeftWagon[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_BACK, wagon[k][0], wagon[k][1], wagon[k + 1][1],
					wagon[k + 1][0], tBackWagon[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, wagon[k + 1][1], wagon[k][1], wagon[k][2],
					wagon[k + 1][2], tRightWagon[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_FRONT, wagon[k][2], wagon[k][3], wagon[k + 1][3],
					wagon[k + 1][2], tWagon[0]);

		}

		faces[counter++] = buildFace(Renderer3D.CAR_TOP, wagon[nzWagon - 1][0], wagon[nzWagon - 1][1],
				wagon[nzWagon - 1][2], wagon[nzWagon - 1][3], tWagon[0]);

		return counter;
	}

	protected int buildWheelFaces(int counter, int totWheelPolygon) {

		///// WHEELS

		int[][][] wFaces = buildWheelFaces(wheelLeftFront, tWh[0][0]);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}

		wFaces = buildWheelFaces(wheelRightFront, tWh[0][0]);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}

		wFaces = buildWheelFaces(wheelLeftRear, tWh[0][0]);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}

		wFaces = buildWheelFaces(wheelRightRear, tWh[0][0]);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}

		return counter;

	}

	protected void buildTextures() {

		int shift = 1;
		double deltaXF = (dxWagon - dxFront) * 0.5;
		double deltaXR = (dxWagon - dxRear) * 0.5;
		double maxDX = Math.max(dxRear, dxWagon);

		// generic rear
		double y = by;
		double x = bx;
		addTRect(x, y, dxTexture, dyTexture);

		x += dxTexture + shift;
		y = by;
		buildBackTextures(x, y, shift, deltaXR);
		y += dzRear + shift + dzWagon;

		buildLefTextures(x, y, shift);
		x += dzRear + dzWagon;
		x+=buildTopTextures(x, y, shift, deltaXF);

		buildRightTextures(x, y, shift);

		// window points
		x +=(dzWagon + dzRear) + shift;
		y = by;
		addTRect(x, y, dxTexture, dyTexture);

		// wheel texture, a black square for simplicity:
		x += dxTexture + shift;
		y = by;
		addTRect(x, y, wheelWidth, wheelWidth);

		IMG_WIDTH = (int) (bx + x);
		IMG_HEIGHT = (int) (2 * by + Math.max(dyTexture, dyWagon + dyFront + dzWagon + dzRear + 2 * shift));
	}

	protected void buildBackTextures(double x, double y, int shift, double deltaXR) {

		addTRect(x + dzRear + dzWagon + deltaXR, y, dxRear, dzRear);
		y += dzRear + shift;
		addTRect(x + dzRear + dzWagon, y, dxWagon, dzWagon);

	}

	protected double buildTopTextures(double x, double y, int shift, double deltaXF) {
		addTRect(x, y, dxWagon, dyWagon);
		addTRect(x + deltaXF, y + dyWagon, dxRoof, dyRoof);
		double maxDX = Math.max(dxRear, dxWagon);
		return maxDX;
	}

	protected void buildLefTextures(double x, double y, int shift) {

		for (int i = 0; i < rear.length - 1; i++) {
			addTPoint(x + rear[i][0].z, y + rear[i][0].y, 0);
			addTPoint(x + rear[i][3].z, y + rear[i][3].y, 0);
			addTPoint(x + rear[i + 1][3].z, y + rear[i + 1][3].y, 0);
			addTPoint(x + rear[i + 1][0].z, y + rear[i + 1][0].y, 0);
		}

		//// left front texture

		for (int i = 0; i < nYcab - 1; i++) {

			addTPoint(x + cab[0][i][0].z, y + cab[0][i][0].y, 0);
			addTPoint(x + cab[0][i][1].z, y + cab[0][i][1].y, 0);
			addTPoint(x + cab[0][i + 1][1].z, y + cab[0][i + 1][1].y, 0);
			addTPoint(x + cab[0][i + 1][0].z, y + cab[0][i + 1][0].y, 0);
		}

		addTPoint(x + roof[0][0][0].z, y + roof[0][0][0].y, 0);
		addTPoint(x + roof[0][0][1].z, y + roof[0][0][1].y, 0);
		addTPoint(x + roof[0][1][1].z, y + roof[0][1][1].y, 0);
		addTPoint(x + roof[0][1][0].z, y + roof[0][1][0].y, 0);

		addTPoint(x + roof[0][1][0].z, y + roof[0][1][0].y, 0);
		addTPoint(x + roof[0][1][1].z, y + roof[0][1][1].y, 0);
		addTPoint(x + roof[0][2][1].z, y + roof[0][2][1].y, 0);
		addTPoint(x + roof[0][2][0].z, y + roof[0][2][0].y, 0);
		buildLeftWagonTexture(x,y);


	}

	protected void buildLeftWagonTexture(double x, double y) {
		addTRect(x + dzRear, y, dzWagon, dyWagon);

	}

	protected void buildRightTextures(double x, double y, int shift) {

		double dzRight = dzWagon + dzRear;

		for (int i = 0; i < rear.length - 1; i++) {
			addTPoint(x + dzRight - rear[i][3].z, y + rear[i][3].y, 0);
			addTPoint(x + dzRight - rear[i][0].z, y + rear[i][0].y, 0);
			addTPoint(x + dzRight - rear[i + 1][0].z, y + rear[i + 1][0].y, 0);
			addTPoint(x + dzRight - rear[i + 1][3].z, y + rear[i + 1][3].y, 0);

		}

		for (int i = 0; i < nYcab - 1; i++) {

			addTPoint(x + dzRight - cab[0][i][1].z, y + cab[0][i][1].y, 0);
			addTPoint(x + dzRight - cab[0][i][0].z, y + cab[0][i][0].y, 0);
			addTPoint(x + dzRight - cab[0][i + 1][0].z, y + cab[0][i + 1][0].y, 0);
			addTPoint(x + dzRight - cab[0][i + 1][1].z, y + cab[0][i + 1][1].y, 0);

		}

		addTPoint(x + dzRight - roof[0][0][1].z, y + roof[0][0][1].y, 0);
		addTPoint(x + dzRight - roof[0][0][0].z, y + roof[0][0][0].y, 0);
		addTPoint(x + dzRight - roof[0][1][0].z, y + roof[0][1][0].y, 0);
		addTPoint(x + dzRight - roof[0][1][1].z, y + roof[0][1][1].y, 0);

		addTPoint(x + dzRight - roof[0][1][1].z, y + roof[0][1][1].y, 0);
		addTPoint(x + dzRight - roof[0][1][0].z, y + roof[0][1][0].y, 0);
		addTPoint(x + dzRight - roof[0][2][0].z, y + roof[0][2][0].y, 0);
		addTPoint(x + dzRight - roof[0][2][1].z, y + roof[0][2][1].y, 0);

		buildRightWagonTexture(x,y);

	}

	protected void buildRightWagonTexture(double x, double y) {
		addTRect(x, y, dzWagon, dyWagon);

	}

	protected void buildRear() {

		Segments s0 = new Segments(x0 - dxRear * 0.5, dxRear, y0, dyRear, z0, dzRear);

		rear = new BPoint[nyBody][4];

		rear[0][0] = addBPoint(0.0, 0.0, 0.0, s0);
		rear[0][1] = addBPoint(1.0, 0.0, 0.0, s0);
		rear[0][2] = addBPoint(1.0, 1.0, 0.0, s0);
		rear[0][3] = addBPoint(0.0, 1.0, 0.0, s0);

		rear[1][0] = addBPoint(0.0, 0.0, 1.0, s0);
		rear[1][1] = addBPoint(1.0, 0.0, 1.0, s0);
		rear[1][2] = addBPoint(1.0, 1.0, 1.0, s0);
		rear[1][3] = addBPoint(0.0, 1.0, 1.0, s0);

	}

	protected void buildCabin() {

		double wy = wheelRadius * 1.0 / dyFront;

		double fy0 = 0;
		double fy2 = 0.5 - wy;
		double fy1 = (0 * 0.25 + fy2 * 0.75);
		double fy3 = 0.5 + wy;
		double fy5 = 1.0;
		double fy4 = (fy3 * 0.75 + fy5 * 0.25);

		double wz2 = wheelRadius * 1.0 / dzFront;
		double wz3 = wz2;

		double fz2 = 1.0;

		Segments s0 = new Segments(x0 - dxFront * 0.5, dxFront, y0 + dyWagon, dyFront, z0, dzFront);

		cab = new BPoint[2][nYcab][nzCab];
		cab[0][0][0] = addBPoint(0.0, fy0, 0.0, s0);
		cab[0][0][1] = addBPoint(0.0, fy0, fz2, s0);
		cab[1][0][0] = addBPoint(1.0, fy0, 0.0, s0);
		cab[1][0][1] = addBPoint(1.0, fy0, fz2, s0);

		cab[0][1][0] = addBPoint(0.0, fy1, 0.0, s0);
		cab[0][1][1] = addBPoint(0.0, fy1, fz2, s0);
		cab[1][1][0] = addBPoint(1.0, fy1, 0.0, s0);
		cab[1][1][1] = addBPoint(1.0, fy1, fz2, s0);

		cab[0][2][0] = addBPoint(0.0, fy2, wz2, s0);
		cab[0][2][1] = addBPoint(0.0, fy2, fz2, s0);
		cab[1][2][0] = addBPoint(1.0, fy2, wz2, s0);
		cab[1][2][1] = addBPoint(1.0, fy2, fz2, s0);

		cab[0][3][0] = addBPoint(0.0, fy3, wz3, s0);
		cab[0][3][1] = addBPoint(0.0, fy3, fz2, s0);
		cab[1][3][0] = addBPoint(1.0, fy3, wz3, s0);
		cab[1][3][1] = addBPoint(1.0, fy3, fz2, s0);

		cab[0][4][0] = addBPoint(0.0, fy4, 0.0, s0);
		cab[0][4][1] = addBPoint(0.0, fy4, fz2, s0);
		cab[1][4][0] = addBPoint(1.0, fy4, 0.0, s0);
		cab[1][4][1] = addBPoint(1.0, fy4, fz2, s0);

		cab[0][5][0] = addBPoint(0.0, fy5, 0.0, s0);
		cab[0][5][1] = addBPoint(0.0, fy5, fz2, s0);
		cab[1][5][0] = addBPoint(1.0, fy5, 0.0, s0);
		cab[1][5][1] = addBPoint(1.0, fy5, fz2, s0);

		Segments r0 = new Segments(x0 - dxRoof * 0.5, dxRoof, y0 + dyRear, dyRoof, z0 + dzFront, dzRoof);

		double dyRup = (dyFront - frontOverhang1) / dyRoof;

		roof = new BPoint[2][3][2];
		roof[0][0][0] = addBPoint(0.0, 0, 0, r0);
		roof[1][0][0] = addBPoint(1.0, 0, 0, r0);
		roof[0][1][0] = addBPoint(0.0, dyRup, 0, r0);
		roof[1][1][0] = addBPoint(1.0, dyRup, 0, r0);
		roof[0][2][0] = addBPoint(0.0, 1.0, 0, r0);
		roof[1][2][0] = addBPoint(1.0, 1.0, 0, r0);

		roof[0][0][1] = addBPoint(0.0, 0, 1.0, r0);
		roof[1][0][1] = addBPoint(1.0, 0, 1.0, r0);
		roof[0][1][1] = addBPoint(0.0, dyRup, 1.0, r0);
		roof[1][1][1] = addBPoint(1.0, dyRup, 1.0, r0);
		roof[0][2][1] = addBPoint(0.0, 1.0, 1.0, r0);
		roof[1][2][1] = addBPoint(1.0, 1.0, 1.0, r0);

	}

	protected void buildWagon() {

		int nzWagon=nWagonSides+1;

		Segments s0 = new Segments(x0 - dxWagon * 0.5, dxWagon, y0, dyWagon, z0 + dzRear, dzWagon);

		wagon = new BPoint[nzWagon][4];
		wagon[0][0] = addBPoint(0.0, 0.0, 0.0, s0);
		wagon[0][1] = addBPoint(1.0, 0.0, 0.0, s0);
		wagon[0][2] = addBPoint(1.0, 1.0, 0.0, s0);
		wagon[0][3] = addBPoint(0.0, 1.0, 0.0, s0);

		wagon[1][0] = addBPoint(0.0, 0.0, 1.0, s0);
		wagon[1][1] = addBPoint(1.0, 0.0, 1.0, s0);
		wagon[1][2] = addBPoint(1.0, 1.0, 1.0, s0);
		wagon[1][3] = addBPoint(0.0, 1.0, 1.0, s0);
	}

	@Override
	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);

	}

	@Override
	public void printTexture(Graphics2D bufGraphics) {

		Color rearColor = new Color(255, 0, 0);
		Color frontColor = new Color(217, 15, 27);
		Color wagonColor = new Color(255, 255, 255);
		Color windowColor = new Color(0, 0, 255);

		bufGraphics.setStroke(new BasicStroke(0.1f));

		bufGraphics.setColor(rearColor);
		printTexturePolygon(bufGraphics, tRe[0]);
		printTexturePolygon(bufGraphics, tBackRear);
		bufGraphics.setColor(wagonColor);
		printTexturePolygon(bufGraphics, tBackWagon[0]);

		printTexturePolygon(bufGraphics, tLeftWagon);
		bufGraphics.setColor(rearColor);
		for (int i = 0; i < tLeftRear.length; i++) {
			printTexturePolygon(bufGraphics, tLeftRear[i]);
		}
		bufGraphics.setColor(frontColor);
		for (int i = 0; i < tLeftFront.length; i++) {
			printTexturePolygon(bufGraphics, tLeftFront[i]);
		}
		for (int i = 0; i < tLeftRoof.length; i++) {
			printTexturePolygon(bufGraphics, tLeftRoof[i]);
		}

		bufGraphics.setColor(wagonColor);
		printTexturePolygon(bufGraphics, tWagon);
		bufGraphics.setColor(frontColor);
		printTexturePolygon(bufGraphics, tTopRoof);

		bufGraphics.setColor(rearColor);
		for (int i = 0; i < tRightRear.length; i++) {
			printTexturePolygon(bufGraphics, tRightRear[i]);
		}
		bufGraphics.setColor(frontColor);
		for (int i = 0; i < tRightFront.length; i++) {
			printTexturePolygon(bufGraphics, tRightFront[i]);
		}
		bufGraphics.setColor(frontColor);
		for (int i = 0; i < tRightRoof.length; i++) {
			printTexturePolygon(bufGraphics, tRightRoof[i]);
		}
		bufGraphics.setColor(wagonColor);
		printTexturePolygon(bufGraphics, tRightWagon);

		bufGraphics.setColor(windowColor);
		printTexturePolygon(bufGraphics, tWi[0]);

		bufGraphics.setColor(Color.BLACK);
		printTexturePolygon(bufGraphics, tWh[0]);

	}

}
