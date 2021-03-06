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
	protected Prism prismRight;
	protected Prism prismLeft;
	protected Prism prismBack;
	protected Prism prismFront;

	protected int[] tTopWagon = null;
	protected int[] tTopRear = null;
	protected int[][] tTopFront = null;

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

		initTexturesArrays();

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
		NF += 6 * 4;

		faces = new int[NF + NUM_WHEEL_FACES][3][4];

		int counter = 0;
		counter = buildBodyFaces(counter, nzBody);
		counter = buildWheelFaces(counter, totWheelPolygon);

	}

	@Override
	protected void initTexturesArrays() {
		int c = 0;

		c = initDoubleArrayValues(tLeftRear = new int[nyBody - 1][4], c);
		c = initDoubleArrayValues(tLeftFront = new int[nYcab - 1][4], c);
		c = initDoubleArrayValues(tLeftRoof = new int[2][4], c);
		c = initDoubleArrayValues(tLeftWagon = new int[1][4], c);
		c = initSingleArrayValues(tBackRear = new int[4], c);
		c = initDoubleArrayValues(tBackWagon = new int[1][4], c);
		c = initSingleArrayValues(tTopRear = new int[4], c);
		c = initSingleArrayValues(tTopRoof = new int[4], c);
		c = initDoubleArrayValues(tTopFront = new int[nYcab - 1][4], c);
		c = initDoubleArrayValues(tFrontCabin = new int[1][4], c);
		c = initDoubleArrayValues(tRightRear = new int[nyBody - 1][4], c);
		c = initDoubleArrayValues(tRightFront = new int[nYcab - 1][4], c);
		c = initDoubleArrayValues(tRightRoof = new int[2][4], c);
		c = initDoubleArrayValues(tRightWagon = new int[1][4], c);
		c = initDoubleArrayValues(tRe = new int[1][4], c);
		c = initDoubleArrayValues(tWagon = new int[1][4], c);
		c = initDoubleArrayValues(tWi = new int[1][4], c);
		c = initDoubleArrayValues(tWh = new int[1][4], c);

	}

	@Override
	protected void buildTextures() {

		int shift = 1;

		double y = by;
		double x = bx;

		double dyFr = dzRear + dzWagon + shift;
		buildLefTextures(x, by + dyFr, shift);
		x += dzFront + dzRoof + shift;
		buildBackTextures(x, y, shift);
		y += dzRear + dzWagon+shift;
		buildTopTextures(x, y, shift);
		y +=dyRear+dyFront+shift;
		buildFrontTextures(x, y, shift);
		y=by;
		x += dxRear + shift;
		buildRightTextures(x, y + dyFr, shift);
		x += dzFront + dzRoof + shift;

		// body points
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
		x += dxTexture + shift;
		y = by;
		buildWheelTexture(x,y);
		x+=wheelWidth;

		IMG_WIDTH = (int) (bx + x);
		IMG_HEIGHT = (int) (2 * by + dzRear + dzWagon + dyRear + dyFront + dyRoof + shift);
	}

	private void buildWheelTexture(double x, double y) {
		addTRect(x, y, wheelWidth, wheelWidth);

	}

	@Override
	protected void buildRightTextures(double x, double y, int shift) {

		double dzRight = dzRoof + dzFront;

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
		addTPoint(x + dzRight - roof[0][2][0].z, y + roof[0][2][0].y, 0);

		addTRect(x + dzRight - dzRear - dzWagon, y, dzWagon, dyWagon);
	}

	protected void buildTopTextures(double x, double y, int shift) {
		addTRect(x, y, dxRear, dyRear);
		addTRect(x, y+dyRear, dxRoof, dyRoof);
		// top front
		for (int i = 0; i < nYcab-1; i++) {
			addTPoint(x + cab[0][i][0].x, y+cab[0][i][0].y,0);
			addTPoint(x + cab[1][i][0].x, y+cab[1][i][0].y,0);
			addTPoint(x + cab[1][i+1][0].x, y+cab[1][i+1][0].y,0);
			addTPoint(x + cab[0][i+1][0].x, y+cab[0][i+1][0].y,0);
		}
	}

	@Override
	protected void buildFrontTextures(double x, double y, int shift) {
		int ind=nYcab-1;
		for (int k = 0; k<nzCab-1; k++) {
			addTPoint(x + cab[0][ind][k].x, y + cab[0][ind][k].z, 0);
			addTPoint(x + cab[1][ind][k].x, y + cab[1][ind][k].z, 0);
			addTPoint(x + cab[1][ind][k+1].x, y + cab[1][ind][k+1].z, 0);
			addTPoint(x + cab[0][ind][k+1].x, y + cab[0][ind][k+1].z, 0);
		}
	}

	protected void buildBackTextures(double x, double y, int shift) {

		addTRect(x, y, dxRear, dzRear);
		y += dzRear;
		addTRect(x, y, dxWagon, dzWagon);
		y += dzWagon;

	}

	@Override
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
		addTPoint(x + roof[0][2][0].z, y + roof[0][2][0].y, 0);
		addTPoint(x + roof[0][2][0].z, y + roof[0][2][0].y, 0);

		addTRect(x + dzRear, y, dzWagon, dyWagon);

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

		double yRearAxle = rearOverhang / dyRear;

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
			faces[counter++] = buildFace(Renderer3D.CAR_TOP, rear[i][3], rear[i][2], rear[i + 1][2], rear[i + 1][3],
					tTopRear);

		}
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, rear[numSections - 1][0], rear[numSections - 1][3],
				rear[numSections - 1][2], rear[numSections - 1][1], tRe[0]);
		return counter;

	}

	@Override
	protected int buildBodyFaces(int counter, int nzRear) {

		counter = buildCabinFaces(counter, nYcab, nzCab);
		counter = buildRearYFaces(counter, nzRear);
		counter = buildWagonFaces(counter);

		return counter;
	}

	@Override
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
					faces[counter++] = buildFace(Renderer3D.CAR_FRONT,  cab[0][nYcab - 1][k + 1],
							cab[1][nYcab - 1][k + 1], cab[1][nYcab - 1][k],cab[0][nYcab - 1][k], tFrontCabin[k]);
				}

			}

			faces[counter++] = buildFace(Renderer3D.CAR_TOP, cab[0][j][nzCab - 1], cab[1][j][nzCab - 1],
					cab[1][j + 1][nzCab - 1], cab[0][j + 1][nzCab - 1], tTopFront[j]);

		}

		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, roof[0][0][0], roof[0][0][1], roof[0][1][1], roof[0][1][0],
				tLeftRoof[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, roof[0][1][0], roof[0][1][1], roof[0][2][0], tLeftRoof[1]);
		faces[counter++] = buildFace(Renderer3D.CAR_TOP, roof[0][0][1], roof[1][0][1], roof[1][1][1], roof[0][1][1],
				tTopRoof);
		faces[counter++] = buildFace(Renderer3D.CAR_TOP, roof[0][1][1], roof[1][1][1], roof[1][2][0], roof[0][2][0],
				tWi[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, roof[0][0][0], roof[1][0][0], roof[1][0][1], roof[0][0][1],
				tWi[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, roof[1][0][0], roof[1][1][0], roof[1][1][1], roof[1][0][1],
				tRightRoof[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, roof[1][1][0], roof[1][2][0], roof[1][1][1], tRightRoof[1]);

		return counter;
	}

	@Override
	protected void buildWheels() {

		////
		double wz = wheelZ;
		double wxLeft = dxRear * 0.5;
		double wxRight = dxRear * 0.5 - wheelWidth;

		double yRearAxle = rearOverhang;
		double yFrontAxle = dyRear + dyFront - frontOverhang;

		wheelLeftFront = buildWheel(x0 - wxLeft, yFrontAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		wheelRightFront = buildWheel(x0 + wxRight, yFrontAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		wheelLeftRear = buildWheel(x0 - wxLeft, yRearAxle, wz, wheelRadius, wheelWidth, wheel_rays);
		wheelRightRear = buildWheel(x0 + wxRight, yRearAxle, wz, wheelRadius, wheelWidth, wheel_rays);

	}

	@Override
	protected void buildWagon() {

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
	protected int buildWagonFaces(int counter) {

		counter = addPrism(prismRight, counter, tRightWagon[0]);
		counter = addPrism(prismLeft, counter, tLeftWagon[0]);
		counter = addPrism(prismBack, counter, tBackWagon[0]);
		counter = addPrism(prismFront, counter, tWagon[0]);

		return counter;
	}

	protected Color rearColor = new Color(217, 15, 27);
	protected Color frontColor = new Color(72, 178, 230);

	@Override
	public void printTexture(Graphics2D bufGraphics) {

		bufGraphics.setStroke(new BasicStroke(0.1f));

		bufGraphics.setColor(rearColor);
		printTexturePolygon(bufGraphics, tLeftRear);
		bufGraphics.setColor(frontColor);
		printTexturePolygon(bufGraphics, tLeftFront);
		bufGraphics.setColor(rearColor);
		printTexturePolygon(bufGraphics, tLeftWagon);
		bufGraphics.setColor(rearColor);
		printTexturePolygon(bufGraphics, tLeftRoof);

		bufGraphics.setColor(frontColor);
		printTexturePolygon(bufGraphics, tTopFront);
		printTexturePolygon(bufGraphics, tTopRoof);
		printTexturePolygon(bufGraphics, tTopRear);
		bufGraphics.setColor(Color.MAGENTA);
		printTexturePolygon(bufGraphics, tFrontCabin);

		bufGraphics.setColor(rearColor);
		printTexturePolygon(bufGraphics, tRightRear);
		bufGraphics.setColor(frontColor);
		printTexturePolygon(bufGraphics, tRightFront);
		bufGraphics.setColor(rearColor);
		printTexturePolygon(bufGraphics, tRightRoof);
		bufGraphics.setColor(rearColor);
		printTexturePolygon(bufGraphics, tRightWagon);

		printTexturePolygon(bufGraphics, tBackWagon[0]);
		printTexturePolygon(bufGraphics, tBackRear);
		bufGraphics.setColor(rearColor);
		printTexturePolygon(bufGraphics, tRe[0]);

		bufGraphics.setColor(rearColor);
		printTexturePolygon(bufGraphics, tWagon[0]);

		bufGraphics.setColor(Color.BLUE);
		printTexturePolygon(bufGraphics, tWi[0]);

		bufGraphics.setColor(Color.BLACK);
		printTexturePolygon(bufGraphics, tWh[0]);

	}

}
