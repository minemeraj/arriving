package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.Segments;
import com.main.Renderer3D;

public class Jeep0Model extends PickupModel {

	public static final String NAME = "Jeep";

	BPoint[][][] wagonSides = null;
	BPoint[][] wagonBack = null;
	BPoint[][] wagonBottom = null;

	private BPoint[][][][] cabSides;

	public Jeep0Model(double dxFront, double dyfront, double dzFront, double dxRoof, double dyRoof, double dzRoof,
			double dxRear, double dyRear, double dzRear, double dxWagon, double dyWagon, double dzWagon,
			double rearOverhang, double frontOverhang, double rearOverhang1, double frontOverhang1, double wheelRadius,
			double wheelWidth, int wheel_rays) {

		super(dxFront, dyfront, dzFront, dxRoof, dyRoof, dzRoof, dxRear, dyRear, dzRear, dxWagon, dyWagon, dzWagon,
				rearOverhang, frontOverhang, rearOverhang1, frontOverhang1, wheelRadius, wheelWidth, wheel_rays);

		nWagonUnits = 2;
		nYcab = 4;
	}

	@Override
	public void initMesh() {

		int c = 0;
		c = initDoubleArrayValues(tBackWagon = new int[1][4], c);
		c = initSingleArrayValues(tTopWagon = new int[4], c);
		c = initSingleArrayValues(tTopRear = new int[4], c);
		c = initSingleArrayValues(tTopFront = new int[4], c);
		c = initDoubleArrayValues(tRe = new int[1][4], c);
		c = initDoubleArrayValues(tWa = new int[1][4], c);
		c = initDoubleArrayValues(tWi = new int[1][4], c);
		c = initDoubleArrayValues(tWh = new int[1][4], c);

		points = new Vector<Point3D>();
		texturePoints = new Vector();

		x0 = dxWagon * 0.5;

		wheelZ = -0.1122 * wheelRadius;

		buildCabin();

		buildRear();
		buildWagon();

		buildWheels();
		buildTextures();

		int totWheelPolygon = wheel_rays + 2 * (wheel_rays - 2);
		int NUM_WHEEL_FACES = 4 * totWheelPolygon;

		// cabin faces
		int NF = 3 * (2 + (2 + (nzCab - 1)) * (nYcab - 1) * 2);
		// cabin roof
		NF += 7;
		// rear
		NF += 6;
		// wagon
		NF += 2 * (2 + (nyBody - 1) * 4) + 6 + 6;

		faces = new int[NF + NUM_WHEEL_FACES][3][4];

		int counter = 0;
		counter = buildBodyFaces(counter, nzBody, nWagonUnits);
		counter = buildWheelFaces(counter, totWheelPolygon);

	}

	@Override
	protected void buildTextures() {

		int shift = 1;

		double y = by;
		double x = bx;

		double dxMaxR = Math.max(dxRear, dxWagon);
		dxMaxR = Math.max(dxMaxR, dxFront);
		double midX = bx + (dxMaxR) * 0.5;
		double deltaXRe = (midX - dxRear * 0.5 - bx);
		double deltaXFr = (midX - dxFront * 0.5 - bx);

		addTRect(x, y, dxWagon, dzWagon);
		y += dzWagon + shift;
		addTRect(x, y, dxWagon, dyWagon);
		y += dyWagon + shift;
		addTRect(x + deltaXRe, y, dxRear, dyRear);
		y += dyRear + shift;
		addTRect(x + deltaXFr, y, dxFront, dyFront);

		// rear points
		x += dxMaxR + shift;
		y = by;
		addTRect(x, y, dxTexture, dyTexture);

		// wagon points
		x += dxTexture + shift;
		y = by;
		addTRect(x, y, dxTexture, dyTexture);

		// window points
		x += dxTexture + shift;
		y = by;
		addTRect(x, y, dxTexture, dyTexture);

		// wheel texture, a black square for simplicity:
		x += dxTexture + shift;
		y = by;
		addTRect(x, y, wheelWidth, wheelWidth);

		IMG_WIDTH = (int) (2 * bx + 3 * dxTexture + wheelWidth + 4 * shift + dxMaxR);
		IMG_HEIGHT = (int) (2 * by + +dzWagon + dyWagon + dyRear + dyFront + 3 * shift);
	}

	@Override
	protected int buildBodyFaces(int counter, int nzRear, int nzWagon) {

		counter = buildCabinFaces(counter, nYcab, nzCab);
		counter = buildRearFaces(counter, nzRear);
		counter = buildWagonYFaces(counter, nzWagon);

		return counter;
	}

	@Override
	protected void buildCabin() {

		double yAxle = (dyFront - frontOverhang) / dyFront;
		double wy = wheelRadius * 1.0 / dyFront;

		double fy0 = 0;
		double fy2 = yAxle - wy;
		double fy1 = (0 * 0.25 + fy2 * 0.75);
		double fy3 = 1.0;

		double wz2 = (wheelRadius + wheelZ) / dzFront;
		double wz3 = wz2;

		double fz2 = 1.0;

		cabSides = new BPoint[2][2][nYcab][nzCab];

		for (int i = 0; i < cabSides.length; i++) {

			Segments s0 = null;
			if (i == 0)
				s0 = new Segments(x0 - dxFront * 0.5, wheelWidth, y0 + dyRear + dyWagon, dyFront, z0, dzFront);
			else
				s0 = new Segments(x0 + dxFront * 0.5 - wheelWidth, wheelWidth, y0 + dyRear + dyWagon, dyFront, z0,
						dzFront);

			cabSides[i][0][0][0] = addBPoint(0.0, fy0, 0.0, s0);
			cabSides[i][0][0][1] = addBPoint(0.0, fy0, fz2, s0);
			cabSides[i][1][0][0] = addBPoint(1.0, fy0, 0.0, s0);
			cabSides[i][1][0][1] = addBPoint(1.0, fy0, fz2, s0);

			cabSides[i][0][1][0] = addBPoint(0.0, fy1, 0.0, s0);
			cabSides[i][0][1][1] = addBPoint(0.0, fy1, fz2, s0);
			cabSides[i][1][1][0] = addBPoint(1.0, fy1, 0.0, s0);
			cabSides[i][1][1][1] = addBPoint(1.0, fy1, fz2, s0);

			cabSides[i][0][2][0] = addBPoint(0.0, fy2, wz2, s0);
			cabSides[i][0][2][1] = addBPoint(0.0, fy2, fz2, s0);
			cabSides[i][1][2][0] = addBPoint(1.0, fy2, wz2, s0);
			cabSides[i][1][2][1] = addBPoint(1.0, fy2, fz2, s0);

			cabSides[i][0][3][0] = addBPoint(0.0, fy3, wz3, s0);
			cabSides[i][0][3][1] = addBPoint(0.0, fy3, fz2, s0);
			cabSides[i][1][3][0] = addBPoint(1.0, fy3, wz3, s0);
			cabSides[i][1][3][1] = addBPoint(1.0, fy3, fz2, s0);
		}

		Segments c0 = new Segments(x0 - dxFront * 0.5 + wheelWidth, dxFront - 2 * wheelWidth, y0 + dyRear + dyWagon,
				dyFront, z0, dzFront);

		cab = new BPoint[2][nYcab][nzCab];

		cab[0][0][0] = addBPoint(0.0, fy0, 0.0, c0);
		cab[0][0][1] = addBPoint(0.0, fy0, fz2, c0);
		cab[1][0][0] = addBPoint(1.0, fy0, 0.0, c0);
		cab[1][0][1] = addBPoint(1.0, fy0, fz2, c0);

		cab[0][1][0] = addBPoint(0.0, fy1, 0.0, c0);
		cab[0][1][1] = addBPoint(0.0, fy1, fz2, c0);
		cab[1][1][0] = addBPoint(1.0, fy1, 0.0, c0);
		cab[1][1][1] = addBPoint(1.0, fy1, fz2, c0);

		cab[0][2][0] = addBPoint(0.0, fy2, 0.0, c0);
		cab[0][2][1] = addBPoint(0.0, fy2, fz2, c0);
		cab[1][2][0] = addBPoint(1.0, fy2, 0.0, c0);
		cab[1][2][1] = addBPoint(1.0, fy2, fz2, c0);

		cab[0][3][0] = addBPoint(0.0, fy3, 0.0, c0);
		cab[0][3][1] = addBPoint(0.0, fy3, fz2, c0);
		cab[1][3][0] = addBPoint(1.0, fy3, 0.0, c0);
		cab[1][3][1] = addBPoint(1.0, fy3, fz2, c0);

		Segments r0 = new Segments(x0 - dxRoof * 0.5, dxRoof, y0 + dyRear + dyWagon - dyRoof, dyRoof, z0 + dzFront,
				dzRoof);

		roof = new BPoint[2][2][2];
		roof[0][0][0] = addBPoint(0.0, 0, 0, r0);
		roof[1][0][0] = addBPoint(1.0, 0, 0, r0);
		roof[0][1][0] = addBPoint(0.0, 1.0, 0, r0);
		roof[1][1][0] = addBPoint(1.0, 1.0, 0, r0);

		roof[0][0][1] = addBPoint(0.0, 0, 1.0, r0);
		roof[1][0][1] = addBPoint(1.0, 0, 1.0, r0);
	}

	@Override
	protected int buildCabinFaces(int counter, int nYcab, int nzCab) {

		// cab
		for (int i = 0; i < cabSides.length; i++) {

			for (int j = 0; j < nYcab - 1; j++) {

				faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, cabSides[i][0][j][0], cabSides[i][0][j + 1][0],
						cabSides[i][1][j + 1][0], cabSides[i][1][j][0], tRe[0]);

				for (int k = 0; k < nzCab - 1; k++) {

					faces[counter++] = buildFace(Renderer3D.CAR_LEFT, cabSides[i][0][j][k], cabSides[i][0][j][k + 1],
							cabSides[i][0][j + 1][k + 1], cabSides[i][0][j + 1][k], tRe[0]);
					if (j == 0) {
						faces[counter++] = buildFace(Renderer3D.CAR_BACK, cabSides[i][0][j][k], cabSides[i][1][j][k],
								cabSides[i][1][j][k + 1], cabSides[i][0][j][k + 1], tRe[0]);
					}
					faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, cabSides[i][1][j][k], cabSides[i][1][j + 1][k],
							cabSides[i][1][j + 1][k + 1], cabSides[i][1][j][k + 1], tRe[0]);
					if (j == nYcab - 2) {
						faces[counter++] = buildFace(Renderer3D.CAR_FRONT, cabSides[i][0][nYcab - 1][k],
								cabSides[i][0][nYcab - 1][k + 1], cabSides[i][1][nYcab - 1][k + 1],
								cabSides[i][1][nYcab - 1][k], tRe[0]);
					}

				}

				faces[counter++] = buildFace(Renderer3D.CAR_TOP, cabSides[i][0][j][nzCab - 1],
						cabSides[i][1][j][nzCab - 1], cabSides[i][1][j + 1][nzCab - 1],
						cabSides[i][0][j + 1][nzCab - 1], tTopFront);

			}

		}

		for (int j = 0; j < nYcab - 1; j++) {

			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, cab[0][j][0], cab[0][j + 1][0], cab[1][j + 1][0],
					cab[1][j][0], tRe[0]);

			for (int k = 0; k < nzCab - 1; k++) {

				faces[counter++] = buildFace(Renderer3D.CAR_LEFT, cab[0][j][k], cab[0][j][k + 1], cab[0][j + 1][k + 1],
						cab[0][j + 1][k], tRe[0]);
				if (j == 0) {
					faces[counter++] = buildFace(Renderer3D.CAR_BACK, cab[0][j][k], cab[1][j][k], cab[1][j][k + 1],
							cab[0][j][k + 1], tRe[0]);
				}
				faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, cab[1][j][k], cab[1][j + 1][k], cab[1][j + 1][k + 1],
						cab[1][j][k + 1], tRe[0]);
				if (j == nYcab - 2) {
					faces[counter++] = buildFace(Renderer3D.CAR_FRONT, cab[0][nYcab - 1][k], cab[0][nYcab - 1][k + 1],
							cab[1][nYcab - 1][k + 1], cab[1][nYcab - 1][k], tRe[0]);
				}

			}

			faces[counter++] = buildFace(Renderer3D.CAR_TOP, cab[0][j][nzCab - 1], cab[1][j][nzCab - 1],
					cab[1][j + 1][nzCab - 1], cab[0][j + 1][nzCab - 1], tTopFront);

		}

		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, roof[0][0][0], roof[0][0][1], roof[0][1][0], tWi[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_TOP, roof[0][0][1], roof[1][0][1], roof[1][1][0], roof[0][1][0],
				tWi[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, roof[0][0][0], roof[1][0][0], roof[1][0][1], roof[0][0][1],
				tWi[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, roof[1][0][0], roof[1][1][0], roof[1][0][1], tWi[0]);

		return counter;
	}

	protected void buildWagon() {

		double yRearAxle = rearOverhang / dyWagon;

		double wy = wheelRadius * 1.0 / dyWagon;

		double fy0 = 0;
		double fy2 = yRearAxle - wy;
		double fy1 = (0 * 0.25 + fy2 * 0.75);
		double fy3 = yRearAxle + wy;
		double fy5 = 1.0;
		double fy4 = yRearAxle + (yRearAxle - fy1);

		double wz2 = (wheelRadius + wheelZ) / dzWagon;
		double wz3 = wz2;

		double fz1 = 1.0;
		wagonSides = new BPoint[2][nyBody][4];

		for (int i = 0; i < 2; i++) {
			Segments s0 = null;
			if (i == 0) {
				s0 = new Segments(x0 - dxWagon * 0.5, wheelWidth, y0, dyWagon, z0, dzWagon);
			} else if (i == 1) {
				s0 = new Segments(x0 + dxWagon * 0.5 - wheelWidth, wheelWidth, y0, dyWagon, z0, dzWagon);
			}

			wagonSides[i][0][0] = addBPoint(0.0, fy0, 0.0, s0);
			wagonSides[i][0][1] = addBPoint(1.0, fy0, 0.0, s0);
			wagonSides[i][0][2] = addBPoint(1.0, fy0, fz1, s0);
			wagonSides[i][0][3] = addBPoint(0.0, fy0, fz1, s0);

			wagonSides[i][1][0] = addBPoint(0.0, fy1, 0.0, s0);
			wagonSides[i][1][1] = addBPoint(1.0, fy1, 0.0, s0);
			wagonSides[i][1][2] = addBPoint(1.0, fy1, fz1, s0);
			wagonSides[i][1][3] = addBPoint(0.0, fy1, fz1, s0);

			wagonSides[i][2][0] = addBPoint(0.0, fy2, wz2, s0);
			wagonSides[i][2][1] = addBPoint(1.0, fy2, wz2, s0);
			wagonSides[i][2][2] = addBPoint(1.0, fy2, fz1, s0);
			wagonSides[i][2][3] = addBPoint(0.0, fy2, fz1, s0);

			wagonSides[i][3][0] = addBPoint(0.0, fy3, wz3, s0);
			wagonSides[i][3][1] = addBPoint(1.0, fy3, wz3, s0);
			wagonSides[i][3][2] = addBPoint(1.0, fy3, fz1, s0);
			wagonSides[i][3][3] = addBPoint(0.0, fy3, fz1, s0);

			wagonSides[i][4][0] = addBPoint(0.0, fy4, 0.0, s0);
			wagonSides[i][4][1] = addBPoint(1.0, fy4, 0.0, s0);
			wagonSides[i][4][2] = addBPoint(1.0, fy4, fz1, s0);
			wagonSides[i][4][3] = addBPoint(0.0, fy4, fz1, s0);

			wagonSides[i][5][0] = addBPoint(0.0, fy5, 0.0, s0);
			wagonSides[i][5][1] = addBPoint(1.0, fy5, 0.0, s0);
			wagonSides[i][5][2] = addBPoint(1.0, fy5, fz1, s0);
			wagonSides[i][5][3] = addBPoint(0.0, fy5, fz1, s0);
		}

		wagonBack = new BPoint[2][4];
		Segments wb = new Segments(x0 - dxWagon * 0.5 + wheelWidth, dxWagon - 2 * wheelWidth, y0, wheelWidth, z0,
				dzWagon);

		wagonBack = new BPoint[2][4];
		wagonBack[0][0] = addBPoint(0.0, 0.0, 0.0, wb);
		wagonBack[0][1] = addBPoint(1.0, 0.0, 0.0, wb);
		wagonBack[0][2] = addBPoint(1.0, 0.0, 1.0, wb);
		wagonBack[0][3] = addBPoint(0.0, 0.0, 1.0, wb);

		wagonBack[1][0] = addBPoint(0.0, 1.0, 0.0, wb);
		wagonBack[1][1] = addBPoint(1.0, 1.0, 0.0, wb);
		wagonBack[1][2] = addBPoint(1.0, 1.0, 1.0, wb);
		wagonBack[1][3] = addBPoint(0.0, 1.0, 1.0, wb);

		wagonBottom = new BPoint[2][4];
		wb = new Segments(x0 - dxWagon * 0.5 + wheelWidth, dxWagon - 2 * wheelWidth, y0 + wheelWidth,
				dyWagon - wheelWidth, z0, dzRear);

		wagonBottom = new BPoint[2][4];
		wagonBottom[0][0] = addBPoint(0.0, 0.0, 0.0, wb);
		wagonBottom[0][1] = addBPoint(1.0, 0.0, 0.0, wb);
		wagonBottom[0][2] = addBPoint(1.0, 0.0, 1.0, wb);
		wagonBottom[0][3] = addBPoint(0.0, 0.0, 1.0, wb);

		wagonBottom[1][0] = addBPoint(0.0, 1.0, 0.0, wb);
		wagonBottom[1][1] = addBPoint(1.0, 1.0, 0.0, wb);
		wagonBottom[1][2] = addBPoint(1.0, 1.0, 1.0, wb);
		wagonBottom[1][3] = addBPoint(0.0, 1.0, 1.0, wb);

	}

	protected int buildWagonYFaces(int counter, int nzWagon) {

		counter = buildWagonYFace(counter, wagonSides[0], nzWagon);
		counter = buildWagonYFace(counter, wagonSides[1], nzWagon);
		counter = buildWagonYFace(counter, wagonBack, nzWagon);
		counter = buildWagonYFace(counter, wagonBottom, nzWagon);
		return counter;
	}

	protected int buildWagonYFace(int counter, BPoint[][] wagonBlock, int nzWagon) {

		int numSections = wagonBlock.length;

		int[] backTexture = tWa[0];
		if (wagonBlock == wagonBack) {
			backTexture = tBackWagon[0];
		}

		faces[counter++] = buildFace(Renderer3D.CAR_BACK, wagonBlock[0][0], wagonBlock[0][1], wagonBlock[0][2],
				wagonBlock[0][3], backTexture);

		for (int i = 0; i < numSections - 1; i++) {

			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, wagonBlock[i + 1][0], wagonBlock[i][0], wagonBlock[i][3],
					wagonBlock[i + 1][3], tWa[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, wagonBlock[i][0], wagonBlock[i + 1][0],
					wagonBlock[i + 1][1], wagonBlock[i][1], tWa[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, wagonBlock[i][1], wagonBlock[i + 1][1],
					wagonBlock[i + 1][2], wagonBlock[i][2], tWa[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_TOP, wagonBlock[i][3], wagonBlock[i][2], wagonBlock[i + 1][2],
					wagonBlock[i + 1][3], tTopWagon);

		}
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, wagonBlock[numSections - 1][0],
				wagonBlock[numSections - 1][3], wagonBlock[numSections - 1][2], wagonBlock[numSections - 1][1], tWa[0]);
		return counter;

	}

	@Override
	protected void buildRear() {

		Segments s0 = new Segments(x0 - dxRear * 0.5, dxRear, y0 + dyWagon, dyRear, z0, dzRear);

		rear = new BPoint[2][4];
		rear[0][0] = addBPoint(0.0, 0.0, 0.0, s0);
		rear[0][1] = addBPoint(1.0, 0.0, 0.0, s0);
		rear[0][2] = addBPoint(1.0, 1.0, 0.0, s0);
		rear[0][3] = addBPoint(0.0, 1.0, 0.0, s0);

		rear[1][0] = addBPoint(0.0, 0.0, 1.0, s0);
		rear[1][1] = addBPoint(1.0, 0.0, 1.0, s0);
		rear[1][2] = addBPoint(1.0, 1.0, 1.0, s0);
		rear[1][3] = addBPoint(0.0, 1.0, 1.0, s0);
	}

	protected int buildRearFaces(int counter, int nzRear) {

		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, rear[0][0], rear[0][3], rear[0][2], rear[0][1], tWa[0]);

		for (int k = 0; k < nzRear - 1; k++) {

			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, rear[k][0], rear[k + 1][0], rear[k + 1][3], rear[k][3],
					tWa[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_BACK, rear[k][0], rear[k][1], rear[k + 1][1], rear[k + 1][0],
					tWa[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, rear[k][1], rear[k][2], rear[k + 1][2], rear[k + 1][1],
					tWa[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_FRONT, rear[k][2], rear[k][3], rear[k + 1][3], rear[k + 1][2],
					tWa[0]);

		}

		faces[counter++] = buildFace(Renderer3D.CAR_TOP, rear[nzRear - 1][0], rear[nzRear - 1][1], rear[nzRear - 1][2],
				rear[nzRear - 1][3], tTopRear);

		return counter;
	}

	@Override
	protected void buildWheels() {

		////
		double wz = wheelZ;
		double wxLeft = dxRear * 0.5;
		double wxRight = dxRear * 0.5 - wheelWidth;

		double yRearAxle = rearOverhang;
		double yFrontAxle = dyWagon + dyRear + dyFront - frontOverhang;

		wheelLeftFront = buildWheel(x0 - wxLeft, yFrontAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		wheelRightFront = buildWheel(x0 + wxRight, yFrontAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		wheelLeftRear = buildWheel(x0 - wxLeft, yRearAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		wheelRightRear = buildWheel(x0 + wxRight, yRearAxle, wz, wheelRadius, wheelWidth, wheel_rays);

	}

	@Override
	public void printTexture(Graphics2D bufGraphics) {

		bufGraphics.setStroke(new BasicStroke(0.1f));

		bufGraphics.setColor(new Color(132, 126, 90));
		printTexturePolygon(bufGraphics, tBackWagon[0]);
		printTexturePolygon(bufGraphics, tTopWagon);
		bufGraphics.setColor(new Color(173, 162, 134));
		printTexturePolygon(bufGraphics, tTopRear);
		bufGraphics.setColor(new Color(111, 140, 112));
		printTexturePolygon(bufGraphics, tTopFront);

		bufGraphics.setColor(new Color(111, 140, 112));
		printTexturePolygon(bufGraphics, tRe[0]);
		bufGraphics.setColor(new Color(123, 110, 79));
		printTexturePolygon(bufGraphics, tWa[0]);
		bufGraphics.setColor(Color.BLUE);
		printTexturePolygon(bufGraphics, tWi[0]);
		bufGraphics.setColor(Color.BLACK);
		printTexturePolygon(bufGraphics, tWh[0]);

	}

}
