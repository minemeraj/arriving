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
		super(dxRoof, dyRoof, dzRoof, dxFront, dyfront, dzFront, dxRear, dyRear, dzRear, dxWagon, dyWagon, dzWagon,
				rearOverhang, frontOverhang, rearOverhang1, frontOverhang1, wheelRadius, wheelWidth, wheel_rays);
	}

	@Override
	public void initMesh() {

		initTexturesArrays();

		points = new Vector<Point3D>();
		texturePoints = new Vector();

		x0 = dxWagon * 0.5;

		buildCabin();
		buildRear();
		buildWagon();
		buildWheels();

		buildTextures();

		int totWheelPolygon = wheel_rays + 2 * (wheel_rays - 2);
		int NUM_WHEEL_FACES = 4 * totWheelPolygon;

		// faces
		int NF =2 *(nzCab* nYcab - 1) ;
		NF += 2 + (nzBody - 1) * 4;
		NF += (nWagonSides) + 2*(nWagonSides - 2);
		// cabin roof
		NF += 8;

		faces = new int[NF + NUM_WHEEL_FACES][3][4];

		int counter = 0;
		counter = buildBodyFaces(counter, nzBody);
		counter = buildWheelFaces(counter, totWheelPolygon);

	}

	@Override
	protected void initTexturesArrays() {

		nWagonSides = 10;
		int c = 0;
		c = initDoubleArrayValues(tRe = new int[1][4], c);
		c = initSingleArrayValues(tBackRear = new int[4], c);
		c = initDoubleArrayValues(tBackWagon = new int[1][nWagonSides], c);
		c = initDoubleArrayValues(tLeftRear = new int[nyBody - 1][4], c);
		c = initDoubleArrayValues(tLeftFront = new int[nYcab - 1][4], c);
		c = initDoubleArrayValues(tLeftRoof = new int[2][4], c);
		c = initDoubleArrayValues(tWagon = new int[nWagonSides][4], c);
		c = initSingleArrayValues(tTopRoof = new int[4], c);
		c = initDoubleArrayValues(tFrontCabin = new int[nzCab-1][4], c);
		c = initDoubleArrayValues(tRightRear = new int[nyBody - 1][4], c);
		c = initDoubleArrayValues(tRightFront = new int[nYcab - 1][4], c);
		c = initDoubleArrayValues(tRightRoof = new int[2][4], c);
		c = initDoubleArrayValues(tWi = new int[1][4], c);
		c = initDoubleArrayValues(tWh = new int[1][4], c);

	}


	@Override
	protected double buildTopTextures(double x, double y, int shift, double deltaXF) {
		buildTankWagonSideTexture(x,y);
		addTRect(x + deltaXF, y + dyWagon, dxRoof, dyRoof);
		double maxDX = Math.max(dxRear, Math.PI*dxWagon);
		return maxDX;
	}

	@Override
	protected void buildLeftWagonTexture(double x, double y) {
		//DO NOTHING
	}

	@Override
	protected void buildRightWagonTexture(double x, double y) {
		//DO NOTHING
	}

	protected void buildTankWagonSideTexture(double x, double y) {

		double dxWidth=Math.PI*dxWagon/nWagonSides;
		for (int i = 0; i < nWagonSides; i++) {
			addTRect(x, y, dxWidth, dyWagon);
			x += dxWidth;
		}
	}

	@Override
	protected void buildBackTextures(double x, double y, int shift, double deltaXR) {
		double xx=x + dzRear + dzWagon + deltaXR;
		addTRect(xx, y, dxRear, dzRear);
		y += dzRear + shift;
		buildBackTankWagonTexture(xx, y);
	}

	protected void buildBackTankWagonTexture(double x, double y) {
		double radius=dxWagon*0.5;
		buildWheelTextures(x, y, radius, radius, radius, nWagonSides,-1, -Math.PI*0.5);
	}

	protected void buildFrontTankWagonTexture(double x, double y) {
		double radius=dxWagon*0.5;
		buildWheelTextures(x, y, radius, radius, radius, nWagonSides,-1, -Math.PI*0.5);
	}

	@Override
	protected void buildWagon() {
		wagon = addYCylinder(x0, 0, dzRear + dxWagon * 0.5, dxWagon * 0.5, dyWagon, nWagonSides);
	}

	@Override
	protected int buildWagonFaces(int counter) {

		for (int i = 0; i < wagon.length; i++) {
			faces[counter++] = buildFace(Renderer3D.CAR_TOP,wagon[i][0],wagon[(i + 1) % wagon.length][0],wagon[(i + 1) % wagon.length][1], wagon[i][1],
					tWagon[i]);
		}

		int[] tWheel = tBackWagon[0];
		for (int i = 1; i < nWagonSides - 1; i++) {
			BPoint p0 = wagon[0][1];
			BPoint p1 = wagon[(i + 1) % nWagonSides][1];
			BPoint p2 = wagon[i][1];
			faces[counter++] = buildFace(Renderer3D.CAR_FRONT, p0, p2, p1, tWheel[0], tWheel[i], tWheel[(i + 1) % wagon.length]);
		}

		for (int i = 1; i < nWagonSides - 1; i++) {
			BPoint p0 = wagon[0][0];
			BPoint p1 = wagon[i][0];
			BPoint p2 = wagon[(i + 1) % nWagonSides][0];
			faces[counter++] = buildFace(Renderer3D.CAR_BACK, p0, p2, p1,  tWheel[0], tWheel[(i + 1) % wagon.length], tWheel[i]);
		}

		return counter;
	}

}
