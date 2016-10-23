package com.editors.models;

import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.Segments;
import com.main.Renderer3D;

public class Jeep0Model extends PickupModel {

	public static final String NAME = "Jeep";

	public Jeep0Model(double dxFront, double dyfront, double dzFront, double dxRoof, double dyRoof, double dzRoof,
			double dxRear, double dyRear, double dzRear, double dxWagon, double dyWagon, double dzWagon,
			double rearOverhang, double frontOverhang, double rearOverhang1, double frontOverhang1, double wheelRadius,
			double wheelWidth, int wheel_rays) {

		super(dxFront, dyfront, dzFront, dxRoof, dyRoof, dzRoof, dxRear, dyRear, dzRear, dxWagon, dyWagon, dzWagon,
				rearOverhang, frontOverhang, rearOverhang1, frontOverhang1, wheelRadius, wheelWidth, wheel_rays);

		nWagonUnits = 2;
	}

	@Override
	public void initMesh() {

		int c = 0;
		c = initSingleArrayValues(tBackRear = new int[4], c);
		c = initSingleArrayValues(tBackWagon = new int[4], c);
		c = initSingleArrayValues(tTopRear = new int[4], c);
		c = initSingleArrayValues(tTopRoof = new int[4], c);
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

		// faces
		int NF = 2 + (2 + (nzCab - 1)) * (nYcab - 1) * 2;
		NF += 2 + (nyBody - 1) * 4;
		// cabin roof
		NF += 7;
		// wagon prisms
		NF += 6 * 3;

		faces = new int[NF + NUM_WHEEL_FACES][3][4];

		int counter = 0;
		counter = buildBodyFaces(counter, nzBody, nWagonUnits);
		counter = buildWheelFaces(counter, totWheelPolygon);

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
		double fy3 = yAxle + wy;
		double fy5 = 1.0;
		double fy4 = (fy3 * 0.75 + fy5 * 0.25);

		double wz2 = (wheelRadius + wheelZ) / dzFront;
		double wz3 = wz2;

		double fz2 = 1.0;

		Segments s0 = new Segments(x0 - dxFront * 0.5, dxFront, y0 + dyRear + dyWagon, dyFront, z0, dzFront);

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

		Segments r0 = new Segments(x0 - dxRoof * 0.5, dxRoof, y0 + dyRear + dyWagon, dyRoof, z0 + dzFront, dzRoof);

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
	}

	protected void buildWagon() {

		double yRearAxle = 2.0 * wheelRadius / dyWagon;

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

		wagon = new BPoint[nyBody][4];

		Segments s0 = new Segments(x0 - dxWagon * 0.5, wheelWidth, y0, dyWagon, z0, dzWagon);

		wagon[0][0] = addBPoint(0.0, fy0, 0.0, s0);
		wagon[0][1] = addBPoint(1.0, fy0, 0.0, s0);
		wagon[0][2] = addBPoint(1.0, fy0, fz1, s0);
		wagon[0][3] = addBPoint(0.0, fy0, fz1, s0);

		wagon[1][0] = addBPoint(0.0, fy1, 0.0, s0);
		wagon[1][1] = addBPoint(1.0, fy1, 0.0, s0);
		wagon[1][2] = addBPoint(1.0, fy1, fz1, s0);
		wagon[1][3] = addBPoint(0.0, fy1, fz1, s0);

		wagon[2][0] = addBPoint(0.0, fy2, wz2, s0);
		wagon[2][1] = addBPoint(1.0, fy2, wz2, s0);
		wagon[2][2] = addBPoint(1.0, fy2, fz1, s0);
		wagon[2][3] = addBPoint(0.0, fy2, fz1, s0);

		wagon[3][0] = addBPoint(0.0, fy3, wz3, s0);
		wagon[3][1] = addBPoint(1.0, fy3, wz3, s0);
		wagon[3][2] = addBPoint(1.0, fy3, fz1, s0);
		wagon[3][3] = addBPoint(0.0, fy3, fz1, s0);

		wagon[4][0] = addBPoint(0.0, fy4, 0.0, s0);
		wagon[4][1] = addBPoint(1.0, fy4, 0.0, s0);
		wagon[4][2] = addBPoint(1.0, fy4, fz1, s0);
		wagon[4][3] = addBPoint(0.0, fy4, fz1, s0);

		wagon[5][0] = addBPoint(0.0, fy5, 0.0, s0);
		wagon[5][1] = addBPoint(1.0, fy5, 0.0, s0);
		wagon[5][2] = addBPoint(1.0, fy5, fz1, s0);
		wagon[5][3] = addBPoint(0.0, fy5, fz1, s0);

	}

	protected int buildWagonYFaces(int counter, int nzWagon) {

		int numSections = wagon.length;

		faces[counter++] = buildFace(Renderer3D.CAR_BACK, wagon[0][0], wagon[0][1], wagon[0][2], wagon[0][3],
				tBackRear);

		for (int i = 0; i < numSections - 1; i++) {

			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, wagon[i + 1][0], wagon[i][0], wagon[i][3],
					wagon[i + 1][3], tRe[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, wagon[i][0], wagon[i + 1][0], wagon[i + 1][1],
					wagon[i][1], tRe[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, wagon[i][1], wagon[i + 1][1], wagon[i + 1][2],
					wagon[i][2], tRe[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_TOP, wagon[i][3], wagon[i][2], wagon[i + 1][2], wagon[i + 1][3],
					tTopRear);

		}
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, wagon[numSections - 1][0], wagon[numSections - 1][3],
				wagon[numSections - 1][2], wagon[numSections - 1][1], tRe[0]);
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
				rear[nzRear - 1][3], tWa[0]);

		return counter;
	}

	@Override
	protected void buildWheels() {

		////
		double wz = wheelZ;
		double wxLeft = dxRear * 0.5;
		double wxRight = dxRear * 0.5 - wheelWidth;

		double yRearAxle = 2.0 * wheelRadius;
		double yFrontAxle = dyWagon + dyRear + dyFront - frontOverhang;

		wheelLeftFront = buildWheel(x0 - wxLeft, yFrontAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		wheelRightFront = buildWheel(x0 + wxRight, yFrontAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		wheelLeftRear = buildWheel(x0 - wxLeft, yRearAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		wheelRightRear = buildWheel(x0 + wxRight, yRearAxle, wz, wheelRadius, wheelWidth, wheel_rays);

	}

}
