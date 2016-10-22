package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.Prism;
import com.Segments;
import com.main.Renderer3D;

/**
 * One texture model Summing up the best creation logic so far
 *
 * @author Administrator
 *
 */
public class PickupModel extends Truck0Model {

	public static final String NAME = "Pickup";
	private Prism prismRight;
	private Prism prismLeft;
	private Prism prismBack;
	private Prism prismFront;

	protected int nWagonUnits = 10;

	protected double wheelZ = 0;

	int[] tBackWagon = null;
	int[] tTopRear = null;
	int[] tTopFront = null;
	int[] tTopRoof = null;

	public PickupModel(double dxFront, double dyfront, double dzFront, double dxRoof, double dyRoof, double dzRoof,
			double dxRear, double dyRear, double dzRear, double dxWagon, double dyWagon, double dzWagon,
			double rearOverhang, double frontOverhang, double rearOverhang1, double frontOverhang1, double wheelRadius,
			double wheelWidth, int wheel_rays) {
		super(dxFront, dyfront, dzFront, dxRoof, dyRoof, dzRoof, dxRear, dyRear, dzRear, dxWagon, dyWagon, dzWagon,
				rearOverhang, frontOverhang, rearOverhang1, frontOverhang1, wheelRadius, wheelWidth, wheel_rays);

		nzCab = 2;
		nzBody = 2;
		nyBody = 6;

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
	protected void buildTextures() {

		int shift = 1;

		double y = by;
		double x = bx;

		// top and rear
		addTRect(x, y, dxRear, dzRear);
		y += dzRear;
		addTRect(x, y, dxWagon, dzWagon);
		y += dzWagon;
		addTRect(x, y, dxRear, dyRear);
		y += dyRear;
		addTRect(x, y, dxRoof, dyRoof);
		y += dyRoof;
		addTRect(x, y, dxFront, dyFront);

		// body points
		x += dxRear + shift;
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

		IMG_WIDTH = (int) (2 * bx + 3 * dxTexture + wheelWidth + 4 * shift + dxRear);
		IMG_HEIGHT = (int) (2 * by + dzRear + dzWagon + dyRear + dyFront + dyRoof);
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

		Segments s0 = new Segments(x0 - dxFront * 0.5, dxFront, y0 + dyRear, dyFront, z0, dzFront);

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
	}

	@Override
	protected void buildRear() {

		double yRearAxle = 2.0 * wheelRadius / dyRear;

		double wy = wheelRadius * 1.0 / dyRear;

		double fy0 = 0;
		double fy2 = yRearAxle - wy;
		double fy1 = (0 * 0.25 + fy2 * 0.75);
		double fy3 = yRearAxle + wy;
		double fy5 = 1.0;
		double fy4 = yRearAxle + (yRearAxle - fy1);

		double wz2 = (wheelRadius + wheelZ) / dzRear;
		double wz3 = wz2;

		double fz1 = 1.0;

		rear = new BPoint[nyBody][4];

		Segments s0 = new Segments(x0 - dxRear * 0.5, dxRear, y0, dyRear, z0, dzRear);

		rear[0][0] = addBPoint(0.0, fy0, 0.0, s0);
		rear[0][1] = addBPoint(1.0, fy0, 0.0, s0);
		rear[0][2] = addBPoint(1.0, fy0, fz1, s0);
		rear[0][3] = addBPoint(0.0, fy0, fz1, s0);

		rear[1][0] = addBPoint(0.0, fy1, 0.0, s0);
		rear[1][1] = addBPoint(1.0, fy1, 0.0, s0);
		rear[1][2] = addBPoint(1.0, fy1, fz1, s0);
		rear[1][3] = addBPoint(0.0, fy1, fz1, s0);

		rear[2][0] = addBPoint(0.0, fy2, wz2, s0);
		rear[2][1] = addBPoint(1.0, fy2, wz2, s0);
		rear[2][2] = addBPoint(1.0, fy2, fz1, s0);
		rear[2][3] = addBPoint(0.0, fy2, fz1, s0);

		rear[3][0] = addBPoint(0.0, fy3, wz3, s0);
		rear[3][1] = addBPoint(1.0, fy3, wz3, s0);
		rear[3][2] = addBPoint(1.0, fy3, fz1, s0);
		rear[3][3] = addBPoint(0.0, fy3, fz1, s0);

		rear[4][0] = addBPoint(0.0, fy4, 0.0, s0);
		rear[4][1] = addBPoint(1.0, fy4, 0.0, s0);
		rear[4][2] = addBPoint(1.0, fy4, fz1, s0);
		rear[4][3] = addBPoint(0.0, fy4, fz1, s0);

		rear[5][0] = addBPoint(0.0, fy5, 0.0, s0);
		rear[5][1] = addBPoint(1.0, fy5, 0.0, s0);
		rear[5][2] = addBPoint(1.0, fy5, fz1, s0);
		rear[5][3] = addBPoint(0.0, fy5, fz1, s0);

	}

	@Override
	protected int buildRearYFaces(int counter, int nzRear, int nzWagon) {

		int numSections = rear.length;

		faces[counter++] = buildFace(Renderer3D.CAR_BACK, rear[0][0], rear[0][1], rear[0][2], rear[0][3], tBackRear);

		for (int i = 0; i < numSections - 1; i++) {

			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, rear[i + 1][0], rear[i][0], rear[i][3], rear[i + 1][3],
					tRe[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, rear[i][0], rear[i + 1][0], rear[i + 1][1], rear[i][1],
					tRe[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, rear[i][1], rear[i + 1][1], rear[i + 1][2], rear[i][2],
					tRe[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_TOP, rear[i][3], rear[i][2], rear[i + 1][2], rear[i + 1][3],
					tTopRear);

		}
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, rear[numSections - 1][0], rear[numSections - 1][3],
				rear[numSections - 1][2], rear[numSections - 1][1], tRe[0]);
		return counter;

	}

	@Override
	protected int buildBodyFaces(int counter, int nzRear, int nzWagon) {

		counter = buildCabinFaces(counter, nYcab, nzCab);
		counter = buildRearYFaces(counter, nzRear, nzWagon);
		counter = buildWagonFaces(counter, nzWagon);

		return counter;
	}

	@Override
	protected int buildCabinFaces(int counter, int nYcab, int nzCab) {

		// cab
		for (int j = 0; j < nYcab - 1; j++) {

			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, cab[0][j][0], cab[0][j + 1][0], cab[1][j + 1][0],
					cab[1][j][0], tRe[0]);

			for (int k = 0; k < nzCab - 1; k++) {

				// set windows in the upper part
				int[] ca = null;
				if (k > 0) {
					ca = tWi[0];
				} else {
					ca = tRe[0];
				}

				faces[counter++] = buildFace(Renderer3D.CAR_LEFT, cab[0][j][k], cab[0][j][k + 1], cab[0][j + 1][k + 1],
						cab[0][j + 1][k], ca);
				if (j == 0) {
					faces[counter++] = buildFace(Renderer3D.CAR_BACK, cab[0][j][k], cab[1][j][k], cab[1][j][k + 1],
							cab[0][j][k + 1], tRe[0]);
				}
				faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, cab[1][j][k], cab[1][j + 1][k], cab[1][j + 1][k + 1],
						cab[1][j][k + 1], ca);
				if (j == nYcab - 2) {
					faces[counter++] = buildFace(Renderer3D.CAR_FRONT, cab[0][nYcab - 1][k], cab[0][nYcab - 1][k + 1],
							cab[1][nYcab - 1][k + 1], cab[1][nYcab - 1][k], ca);
				}

			}

			faces[counter++] = buildFace(Renderer3D.CAR_TOP, cab[0][j][nzCab - 1], cab[1][j][nzCab - 1],
					cab[1][j + 1][nzCab - 1], cab[0][j + 1][nzCab - 1], tTopFront);

		}

		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, roof[0][0][0], roof[0][0][1], roof[0][1][1], roof[0][1][0],
				tWi[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, roof[0][1][0], roof[0][1][1], roof[0][2][0], tWi[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_TOP, roof[0][0][1], roof[1][0][1], roof[1][1][1], roof[0][1][1],
				tTopRoof);
		faces[counter++] = buildFace(Renderer3D.CAR_TOP, roof[0][1][1], roof[1][1][1], roof[1][2][0], roof[0][2][0],
				tWi[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, roof[0][0][0], roof[1][0][0], roof[1][0][1], roof[0][0][1],
				tWi[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, roof[1][0][0], roof[1][1][0], roof[1][1][1], roof[1][0][1],
				tWi[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, roof[1][1][0], roof[1][2][0], roof[1][1][1], tWi[0]);

		return counter;
	}

	@Override
	protected void buildWheels() {

		////
		double wz = wheelZ;
		double wxLeft = dxRear * 0.5;
		double wxRight = dxRear * 0.5 - wheelWidth;

		double yRearAxle = 2.0 * wheelRadius;
		double yFrontAxle = dyRear + dyFront - frontOverhang;

		wheelLeftFront = buildWheel(x0 - wxLeft, yFrontAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		wheelRightFront = buildWheel(x0 + wxRight, yFrontAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		wheelLeftRear = buildWheel(x0 - wxLeft, yRearAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		wheelRightRear = buildWheel(x0 + wxRight, yRearAxle, wz, wheelRadius, wheelWidth, wheel_rays);

	}

	@Override
	protected void buildWagon(int nWagongMeridians) {

		double rdy = (dyRear - dyWagon) * 0.5;

		Segments r0 = new Segments(0, dxWagon, rdy, dyWagon, dzRear, dzWagon);

		double dy = 4.0 / dyWagon;
		double dx = 4.0 / dxWagon;

		prismRight = new Prism(4);

		prismRight.lowerBase[0] = addBPoint(1.0 - dx, 0, 0, r0);
		prismRight.lowerBase[1] = addBPoint(1.0, 0, 0, r0);
		prismRight.lowerBase[2] = addBPoint(1.0, 1.0, 0, r0);
		prismRight.lowerBase[3] = addBPoint(1.0 - dx, 1.0, 0, r0);

		prismRight.upperBase[0] = addBPoint(1.0 - dx, 0, 1.0, r0);
		prismRight.upperBase[1] = addBPoint(1.0, 0, 1.0, r0);
		prismRight.upperBase[2] = addBPoint(1.0, 1.0, 1.0, r0);
		prismRight.upperBase[3] = addBPoint(1.0 - dx, 1.0, 1.0, r0);

		prismLeft = new Prism(4);

		prismLeft.lowerBase[0] = addBPoint(0, 0, 0, r0);
		prismLeft.lowerBase[1] = addBPoint(dx, 0, 0, r0);
		prismLeft.lowerBase[2] = addBPoint(dx, 1.0, 0, r0);
		prismLeft.lowerBase[3] = addBPoint(0, 1.0, 0, r0);

		prismLeft.upperBase[0] = addBPoint(0, 0, 1.0, r0);
		prismLeft.upperBase[1] = addBPoint(dx, 0, 1.0, r0);
		prismLeft.upperBase[2] = addBPoint(dx, 1.0, 1.0, r0);
		prismLeft.upperBase[3] = addBPoint(0, 1.0, 1.0, r0);

		prismBack = new Prism(4);

		prismBack.lowerBase[0] = addBPoint(dx, 0, 0, r0);
		prismBack.lowerBase[1] = addBPoint(1.0 - dx, 0, 0, r0);
		prismBack.lowerBase[2] = addBPoint(1.0 - dx, dy, 0, r0);
		prismBack.lowerBase[3] = addBPoint(dx, dy, 0, r0);

		prismBack.upperBase[0] = addBPoint(dx, 0, 1.0, r0);
		prismBack.upperBase[1] = addBPoint(1.0 - dx, 0, 1.0, r0);
		prismBack.upperBase[2] = addBPoint(1.0 - dx, dy, 1.0, r0);
		prismBack.upperBase[3] = addBPoint(dx, dy, 1.0, r0);

		prismFront = new Prism(4);

		prismFront.lowerBase[0] = addBPoint(dx, 1 - dy, 0, r0);
		prismFront.lowerBase[1] = addBPoint(1.0 - dx, 1 - dy, 0, r0);
		prismFront.lowerBase[2] = addBPoint(1.0 - dx, 1.0, 0, r0);
		prismFront.lowerBase[3] = addBPoint(dx, 1.0, 0, r0);

		prismFront.upperBase[0] = addBPoint(dx, 1 - dy, 1.0, r0);
		prismFront.upperBase[1] = addBPoint(1.0 - dx, 1 - dy, 1.0, r0);
		prismFront.upperBase[2] = addBPoint(1.0 - dx, 1.0, 1.0, r0);
		prismFront.upperBase[3] = addBPoint(dx, 1.0, 1.0, r0);

	}

	@Override
	protected int buildWagonFaces(int counter, int nWagonMeridians) {

		counter = addPrism(prismRight, counter, tWa[0]);
		counter = addPrism(prismLeft, counter, tWa[0]);
		counter = addPrism(prismBack, counter, tBackWagon);
		counter = addPrism(prismFront, counter, tWa[0]);

		return counter;
	}

	@Override
	public void printTexture(Graphics2D bufGraphics) {

		bufGraphics.setStroke(new BasicStroke(0.1f));

		bufGraphics.setColor(new Color(72, 178, 230));
		printTexturePolygon(bufGraphics, tTopFront);
		bufGraphics.setColor(new Color(217, 15, 27));
		printTexturePolygon(bufGraphics, tTopRoof);
		printTexturePolygon(bufGraphics, tTopRear);
		printTexturePolygon(bufGraphics, tBackWagon);
		printTexturePolygon(bufGraphics, tBackRear);

		bufGraphics.setColor(new Color(217, 15, 27));
		printTexturePolygon(bufGraphics, tRe[0]);

		bufGraphics.setColor(new Color(217, 15, 27));
		printTexturePolygon(bufGraphics, tWa[0]);

		bufGraphics.setColor(Color.BLUE);
		printTexturePolygon(bufGraphics, tWi[0]);

		bufGraphics.setColor(Color.BLACK);
		printTexturePolygon(bufGraphics, tWh[0]);

	}

}
