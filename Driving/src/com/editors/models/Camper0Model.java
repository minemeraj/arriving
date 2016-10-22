package com.editors.models;

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
public class Camper0Model extends PickupModel {

	public static String NAME = "Camper";
	private BPoint[][] wagon2;
	private double rearOverhang;
	private double frontOverhang;

	public Camper0Model(double dxFront, double dyfront, double dzFront, double dxRoof, double dyRoof, double dzRoof,
			double dxRear, double dyRear, double dzRear, double dxWagon, double dyWagon, double dzWagon,
			double rearOverhang, double frontOverhang, double rearOverhang1, double frontOverhang1, double wheelRadius,
			double wheelWidth, int wheel_rays) {
		super(dxFront, dyfront, dzFront, dxRoof, dyRoof, dzRoof, dxRear, dyRear, dzRear, dxWagon, dyWagon, dzWagon,
				rearOverhang, frontOverhang, rearOverhang1, frontOverhang1, wheelRadius, wheelWidth, wheel_rays);
		this.rearOverhang = rearOverhang;
		this.frontOverhang = frontOverhang;

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

		buildWagon(nWagonUnits);

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
		NF += 6 * 4;

		faces = new int[NF + NUM_WHEEL_FACES][3][4];

		int counter = 0;
		counter = buildBodyFaces(counter, nzBody, nWagonUnits);
		counter = buildWheelFaces(counter, totWheelPolygon);

	}

	@Override
	protected void buildWagon(int nzWagon) {

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

		double dyMansardRoof = dyRear + dyFront - frontOverhang;
		double dzMansardRoof = dzRear;

		Segments s1 = new Segments(x0 - dxWagon * 0.5, dxWagon, y0, dyMansardRoof, z0 + dzRear + dzWagon,
				dzMansardRoof);

		wagon2 = new BPoint[nzWagon][4];
		wagon2[0][0] = addBPoint(0.0, 0.0, 0.0, s1);
		wagon2[0][1] = addBPoint(1.0, 0.0, 0.0, s1);
		wagon2[0][2] = addBPoint(1.0, 1.0, 0.0, s1);
		wagon2[0][3] = addBPoint(0.0, 1.0, 0.0, s1);

		wagon2[1][0] = addBPoint(0.0, 0.0, 1.0, s1);
		wagon2[1][1] = addBPoint(1.0, 0.0, 1.0, s1);
		wagon2[1][2] = addBPoint(1.0, 1.0, 1.0, s1);
		wagon2[1][3] = addBPoint(0.0, 1.0, 1.0, s1);
	}

	/**
	 *
	 * BUILD WAGON BY Z SECTIONS
	 *
	 * @param nzBody
	 * @return
	 */
	@Override
	protected int buildWagonFaces(int counter, int nzWagon) {

		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, wagon[0][0], wagon[0][3], wagon[0][2], wagon[0][1], tWa[0]);

		for (int k = 0; k < nzWagon - 1; k++) {

			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, wagon[k][0], wagon[k + 1][0], wagon[k + 1][3],
					wagon[k][3], tWa[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_BACK, wagon[k][0], wagon[k][1], wagon[k + 1][1],
					wagon[k + 1][0], tWa[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, wagon[k][1], wagon[k][2], wagon[k + 1][2],
					wagon[k + 1][1], tWa[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_FRONT, wagon[k][2], wagon[k][3], wagon[k + 1][3],
					wagon[k + 1][2], tWa[0]);

		}

		faces[counter++] = buildFace(Renderer3D.CAR_TOP, wagon[nzWagon - 1][0], wagon[nzWagon - 1][1],
				wagon[nzWagon - 1][2], wagon[nzWagon - 1][3], tWa[0]);

		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, wagon2[0][0], wagon2[0][3], wagon2[0][2], wagon2[0][1],
				tWa[0]);

		for (int k = 0; k < nzWagon - 1; k++) {

			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, wagon2[k][0], wagon2[k + 1][0], wagon2[k + 1][3],
					wagon2[k][3], tWa[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_BACK, wagon2[k][0], wagon2[k][1], wagon2[k + 1][1],
					wagon2[k + 1][0], tWa[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, wagon2[k][1], wagon2[k][2], wagon2[k + 1][2],
					wagon2[k + 1][1], tWa[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_FRONT, wagon2[k][2], wagon2[k][3], wagon2[k + 1][3],
					wagon2[k + 1][2], tWa[0]);

		}

		faces[counter++] = buildFace(Renderer3D.CAR_TOP, wagon2[nzWagon - 1][0], wagon2[nzWagon - 1][1],
				wagon2[nzWagon - 1][2], wagon2[nzWagon - 1][3], tWa[0]);

		return counter;
	}

}
