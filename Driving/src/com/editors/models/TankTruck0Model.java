package com.editors.models;

import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.main.Renderer3D;

/**
 * One texture model Summing up the best creation logic so far
 *
 * @author Administrator
 *
 */
public class TankTruck0Model extends Truck0Model {

	public static final String NAME = "Tank truck";

	public TankTruck0Model(double dxFront, double dyfront, double dzFront, double dxRoof, double dyRoof, double dzRoof,
			double dxRear, double dyRear, double dzRear, double dxWagon, double dyWagon, double dzWagon,
			double rearOverhang, double frontOverhang, double rearOverhang1, double frontOverhang1, double wheelRadius,
			double wheelWidth, int wheel_rays) {
		super(dxFront, dyfront, dzFront, dxRoof, dyRoof, dzRoof, dxRear, dyRear, dzRear, dxWagon, dyWagon, dzWagon,
				rearOverhang, frontOverhang, rearOverhang1, frontOverhang1, wheelRadius, wheelWidth, wheel_rays);
	}

	@Override
	public void initMesh() {

		int c = 0;
		initDoubleArrayValues(tRe = new int[1][4], c);
		initDoubleArrayValues(tWa = new int[1][4], c);
		initDoubleArrayValues(tWi = new int[1][4], c);
		initDoubleArrayValues(tWh = new int[1][4], c);

		points = new Vector<Point3D>();
		texturePoints = new Vector();

		x0 = dxWagon * 0.5;

		buildCabin();

		buildRear();

		int nWagonMeridians = 10;
		buildWagon(nWagonMeridians);

		buildWheels();

		buildTextures();

		int totWheelPolygon = wheel_rays + 2 * (wheel_rays - 2);
		int NUM_WHEEL_FACES = 4 * totWheelPolygon;

		// faces
		int NF = 2 + (2 + (nzCab - 1)) * (nYcab - 1) * 2;
		NF += 2 + (nzBody - 1) * 4;
		NF += 2 + (nWagonMeridians) + nWagonMeridians * 2;

		faces = new int[NF + NUM_WHEEL_FACES][3][4];

		int counter = 0;
		counter = buildBodyFaces(counter, nzBody, nWagonMeridians);
		counter = buildWheelFaces(counter, totWheelPolygon);

		IMG_WIDTH = (int) (2 * bx + dxRear + wheelWidth);
		IMG_HEIGHT = (int) (2 * by + dyRear);

	}

	@Override
	protected void buildWagon(int nWagongMeridians) {

		wagon = addYCylinder(x0, 0, dzRear + dxWagon * 0.5, dxWagon * 0.5, dyWagon, nWagongMeridians);

	}

	@Override
	protected int buildWagonFaces(int counter, int nWagonMeridians) {

		for (int i = 0; i < wagon.length; i++) {

			faces[counter++] = buildFace(Renderer3D.CAR_TOP, wagon[i][0], wagon[(i + 1) % wagon.length][0],
					wagon[(i + 1) % wagon.length][1], wagon[i][1], tRe[0]);
		}

		for (int i = 1; i < nWagonMeridians - 1; i++) {

			BPoint p0 = wagon[0][1];
			BPoint p1 = wagon[(i + 1) % nWagonMeridians][1];
			BPoint p2 = wagon[i][1];

			faces[counter++] = buildFace(0, p0, p1, p2, tRe[0]);
		}

		for (int i = 1; i < nWagonMeridians - 1; i++) {

			BPoint p0 = wagon[0][0];
			BPoint p1 = wagon[i][0];
			BPoint p2 = wagon[(i + 1) % nWagonMeridians][0];

			faces[counter++] = buildFace(0, p0, p1, p2, tRe[0]);
		}

		return counter;
	}

}
