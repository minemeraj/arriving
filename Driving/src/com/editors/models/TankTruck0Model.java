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
		c = initDoubleArrayValues(tRe = new int[1][4], c);
		c = initSingleArrayValues(tBackRear = new int[4], c);
		c = initDoubleArrayValues(tBackWagon = new int[1][4], c);
		c = initDoubleArrayValues(tLeftRear = new int[nyBody - 1][4], c);
		c = initDoubleArrayValues(tLeftFront = new int[nYcab - 1][4], c);
		c = initDoubleArrayValues(tLeftRoof = new int[2][4], c);
		c = initDoubleArrayValues(tLeftWagon = new int[1][4], c);
		c = initDoubleArrayValues(tWagon = new int[1][4], c);
		c = initSingleArrayValues(tTopRoof = new int[4], c);
		c = initDoubleArrayValues(tRightRear = new int[nyBody - 1][4], c);
		c = initDoubleArrayValues(tRightFront = new int[nYcab - 1][4], c);
		c = initDoubleArrayValues(tRightRoof = new int[2][4], c);
		c = initDoubleArrayValues(tRightWagon = new int[1][4], c);
		c = initDoubleArrayValues(tWi = new int[1][4], c);
		c = initDoubleArrayValues(tWh = new int[1][4], c);

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
		int NF =2 *(nzCab* nYcab - 1) ;
		NF += 2 + (nzBody - 1) * 4;
		NF += (nWagonMeridians) + 2*(nWagonMeridians - 2);
		// cabin roof
		NF += 8;

		faces = new int[NF + NUM_WHEEL_FACES][3][4];

		int counter = 0;
		counter = buildBodyFaces(counter, nzBody, nWagonMeridians);
		counter = buildWheelFaces(counter, totWheelPolygon);

		IMG_WIDTH = (int) (2 * bx + dxRear + wheelWidth);
		IMG_HEIGHT = (int) (2 * by + dyRear);

	}

	@Override
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
		buildTopTextures(x + dzWagon, y, shift, deltaXF);
		buildRightTextures(x + dzWagon + dxWagon, y, shift);

		// window points
		x += maxDX + 2 * dzWagon + shift;
		y = by;
		addTRect(x, y, dxTexture, dyTexture);

		// wheel texture, a black square for simplicity:
		x += dxTexture + shift;
		y = by;
		addTRect(x, y, wheelWidth, wheelWidth);

		IMG_WIDTH = (int) (2 * bx + maxDX + 2 * dzWagon + 2 * dxTexture + wheelWidth + 3 * shift);
		IMG_HEIGHT = (int) (2 * by + Math.max(dyTexture, dyWagon + dzWagon + dzRear + 2 * shift));
	}

	protected void buildTankWagonTexture(double x, double y, int nWagongMeridians, double dxWidth) {

		for (int i = 0; i < nWagongMeridians; i++) {
			addTRect(x, y, dxWidth, dyWagon);
			x += dxWidth;
		}
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
