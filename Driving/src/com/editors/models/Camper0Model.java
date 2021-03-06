package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
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

	private double dzWagon0;

	public Camper0Model(double dxFront, double dyfront, double dzFront, double dxRoof, double dyRoof, double dzRoof,
			double dxRear, double dyRear, double dzRear, double dxWagon, double dyWagon, double dzWagon,
			double rearOverhang, double frontOverhang, double rearOverhang1, double frontOverhang1, double wheelRadius,
			double wheelWidth, int wheel_rays) {
		super(dxFront, dyfront, dzFront, dxRoof, dyRoof, dzRoof, dxRear, dyRear, dzRear, dxWagon, dyWagon, dzWagon,
				rearOverhang, frontOverhang, rearOverhang1, frontOverhang1, wheelRadius, wheelWidth, wheel_rays);

		dzWagon0 = dzFront + dzRoof - dzRear;
	}

	@Override
	public void initMesh() {

		initTexturesArrays();



		points = new Vector<Point3D>();
		texturePoints = new Vector();

		x0 = dxWagon * 0.5;

		wheelZ = 0.0769 * wheelRadius;

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
		c = initDoubleArrayValues(tLeftWagon = new int[2][4], c);
		c = initSingleArrayValues(tBackRear = new int[4], c);
		c = initDoubleArrayValues(tBackWagon = new int[2][4], c);
		c = initSingleArrayValues(tTopWagon = new int[4], c);
		c = initDoubleArrayValues(tTopFront = new int[1][4], c);
		c = initDoubleArrayValues(tFrontCabin = new int[1][4], c);
		c = initDoubleArrayValues(tRightRear = new int[nyBody - 1][4], c);
		c = initDoubleArrayValues(tRightFront = new int[nYcab - 1][4], c);
		c = initDoubleArrayValues(tRightRoof = new int[2][4], c);
		c = initDoubleArrayValues(tRightWagon = new int[2][4], c);
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

		buildLeftTextures(x, y + dzRear + dzWagon0 + dzWagon + 2 * shift);
		x += dzRear + dzWagon0 + dzWagon;
		buildBackTextures(x, y, shift);
		y += dzRear + dzWagon0 + dzWagon + 2 * shift;
		buildTopTextures(x, y, shift);
		y += dyWagon+dyFront+2*shift;
		buildFrontTextures(x, y, shift);
		x += dxRear + shift;
		y = by;
		buildRightTextures(x - shift, y + dzRear + dzWagon0 + dzWagon + 2 * shift);
		x += dzRear + dzWagon0 + dzWagon + shift;
		// body points
		addTRect(x, y, dxTexture, dyTexture);
		// wagon points
		x += dxTexture + shift;
		addTRect(x, y, dxTexture, dyTexture);
		// window points
		x += dxTexture + shift;
		addTRect(x, y, dxTexture, dyTexture);
		// wheel texture, a black square for simplicity:
		x += dxTexture + shift;
		addTRect(x, y, wheelWidth, wheelWidth);
		x+=wheelWidth;

		IMG_WIDTH = (int) (bx+x);
		IMG_HEIGHT = (int) (2 * by + dzRear + dzWagon0 + dyWagon + dzRear +
				dyFront + dyRoof + dzFront+6 * shift);
	}

	@Override
	protected void buildTopTextures(double x, double y, int shift) {

		addTRect(x, y, dxWagon, dyWagon);
		y += dyWagon + shift;
		addTRect(x, y, dxFront, dyFront);
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

	@Override
	protected void buildBackTextures(double x, double y, int shift) {
		// top and rear
		addTRect(x, y, dxRear, dzRear);
		y += dzRear + shift;
		addTRect(x, y, dxWagon, dzWagon0);
		y += dzWagon0;
		addTRect(x, y, dxWagon, dzWagon);
		y += dzWagon + shift;
	}

	private void buildLeftTextures(double x, double y) {

		for (int i = 0; i < rear.length - 1; i++) {
			addTPoint(x + rear[i][0].z, y + rear[i][0].y, 0);
			addTPoint(x + rear[i][3].z, y + rear[i][3].y, 0);
			addTPoint(x + rear[i + 1][3].z, y + rear[i + 1][3].y, 0);
			addTPoint(x + rear[i + 1][0].z, y + rear[i + 1][0].y, 0);
		}
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

		x += dzRear;
		addTRect(x, y, dzWagon0, dyRear);
		x += dzWagon0;
		addTRect(x, y, dzWagon, dyWagon);

	}

	private void buildRightTextures(double x, double y) {

		double dzRight = dzRear + dzWagon + dzWagon0;

		for (int i = 0; i < rear.length - 1; i++) {
			addTPoint(x + dzRight - rear[i + 1][3].z, y + rear[i + 1][3].y, 0);
			addTPoint(x + dzRight - rear[i][3].z, y + rear[i][3].y, 0);
			addTPoint(x + dzRight - rear[i][0].z, y + rear[i][0].y, 0);
			addTPoint(x + dzRight - rear[i + 1][0].z, y + rear[i + 1][0].y, 0);
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

		addTRect(x + dzRight - dzRear - dzWagon0, y, dzWagon0, dyRear);
		addTRect(x + dzRight - dzRear - dzWagon0 - dzWagon, y, dzWagon, dyWagon);

	}

	@Override
	protected void buildWagon() {

		int nzWagon=nWagonSides+1;

		Segments s0 = new Segments(x0 - dxWagon * 0.5, dxWagon, y0, dyRear, z0 + dzRear, dzWagon0);

		wagon = new BPoint[nzWagon][4];
		wagon[0][0] = addBPoint(0.0, 0.0, 0.0, s0);
		wagon[0][1] = addBPoint(1.0, 0.0, 0.0, s0);
		wagon[0][2] = addBPoint(1.0, 1.0, 0.0, s0);
		wagon[0][3] = addBPoint(0.0, 1.0, 0.0, s0);

		wagon[1][0] = addBPoint(0.0, 0.0, 1.0, s0);
		wagon[1][1] = addBPoint(1.0, 0.0, 1.0, s0);
		wagon[1][2] = addBPoint(1.0, 1.0, 1.0, s0);
		wagon[1][3] = addBPoint(0.0, 1.0, 1.0, s0);

		Segments s1 = new Segments(x0 - dxWagon * 0.5, dxWagon, y0, dyWagon, z0 + dzFront + dzRoof, dzWagon);

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
	protected int buildWagonFaces(int counter) {

		int nzWagon=2;

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
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, wagon2[0][0], wagon2[0][3], wagon2[0][2], wagon2[0][1],
				tWagon[0]);

		for (int k = 0; k < nzWagon - 1; k++) {
			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, wagon2[k][0], wagon2[k + 1][0], wagon2[k + 1][3],
					wagon2[k][3], tLeftWagon[1]);
			faces[counter++] = buildFace(Renderer3D.CAR_BACK, wagon2[k][0], wagon2[k][1], wagon2[k + 1][1],
					wagon2[k + 1][0], tBackWagon[1]);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, wagon2[k + 1][1], wagon2[k][1], wagon2[k][2],
					wagon2[k + 1][2], tRightWagon[1]);
			faces[counter++] = buildFace(Renderer3D.CAR_FRONT, wagon2[k][2], wagon2[k][3], wagon2[k + 1][3],
					wagon2[k + 1][2], tWagon[0]);
		}

		faces[counter++] = buildFace(Renderer3D.CAR_TOP, wagon2[nzWagon - 1][0], wagon2[nzWagon - 1][1],
				wagon2[nzWagon - 1][2], wagon2[nzWagon - 1][3], tTopWagon);

		return counter;
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
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, rear[i + 1][2], rear[i][2], rear[i][1], rear[i + 1][1],
					tRightRear[i]);
			faces[counter++] = buildFace(Renderer3D.CAR_TOP, rear[i][3], rear[i][2], rear[i + 1][2], rear[i + 1][3],
					tRe[0]);
		}
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, rear[numSections - 1][0], rear[numSections - 1][3],
				rear[numSections - 1][2], rear[numSections - 1][1], tRe[0]);
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
				faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, cab[1][j][k + 1], cab[1][j][k], cab[1][j + 1][k],
						cab[1][j + 1][k + 1], tRightFront[j]);
				if (j == nYcab - 2) {
					faces[counter++] = buildFace(Renderer3D.CAR_FRONT,  cab[0][nYcab - 1][k + 1],
							cab[1][nYcab - 1][k + 1], cab[1][nYcab - 1][k],cab[0][nYcab - 1][k], tFrontCabin[k]);
				}
			}
			faces[counter++] = buildFace(Renderer3D.CAR_TOP, cab[0][j][nzCab - 1], cab[1][j][nzCab - 1],
					cab[1][j + 1][nzCab - 1], cab[0][j + 1][nzCab - 1], tTopFront[0]);
		}

		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, roof[0][0][0], roof[0][0][1], roof[0][1][1], roof[0][1][0],
				tLeftRoof[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, roof[0][1][0], roof[0][1][1], roof[0][2][0], tLeftRoof[1]);
		faces[counter++] = buildFace(Renderer3D.CAR_TOP, roof[0][0][1], roof[1][0][1], roof[1][1][1], roof[0][1][1],
				tTopFront[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_TOP, roof[0][1][1], roof[1][1][1], roof[1][2][0], roof[0][2][0],
				tWi[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, roof[0][0][0], roof[1][0][0], roof[1][0][1], roof[0][0][1],
				tWi[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, roof[1][0][1], roof[1][0][0], roof[1][1][0], roof[1][1][1],
				tRightRoof[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, roof[1][1][0], roof[1][2][0], roof[1][1][1], tRightRoof[1]);

		return counter;
	}

	@Override
	public void printTexture(Graphics2D bufGraphics) {

		bufGraphics.setStroke(new BasicStroke(0.1f));

		Color frontColor = new Color(72, 178, 230);
		Color wagonColor0 = new Color(255, 255, 255);
		Color wagonColor1 = new Color(217, 15, 27);

		bufGraphics.setColor(frontColor);
		printTexturePolygon(bufGraphics, tLeftFront);
		bufGraphics.setColor(frontColor);
		printTexturePolygon(bufGraphics, tLeftRoof);

		bufGraphics.setColor(new Color(51, 51, 51));
		printTexturePolygon(bufGraphics, tBackRear);
		printTexturePolygon(bufGraphics, tLeftRear);
		bufGraphics.setColor(wagonColor0);
		printTexturePolygon(bufGraphics, tLeftWagon);
		bufGraphics.setColor(wagonColor1);
		printTexturePolygon(bufGraphics, tBackWagon);
		bufGraphics.setColor(new Color(255, 255, 255));
		printTexturePolygon(bufGraphics, tTopWagon);
		bufGraphics.setColor(frontColor);
		printTexturePolygon(bufGraphics, tTopFront);
		bufGraphics.setColor(Color.MAGENTA);
		printTexturePolygon(bufGraphics, tFrontCabin);

		bufGraphics.setColor(frontColor);
		printTexturePolygon(bufGraphics, tRightFront);

		bufGraphics.setColor(frontColor);

		printTexturePolygon(bufGraphics, tRightRoof);

		bufGraphics.setColor(new Color(51, 51, 51));
		printTexturePolygon(bufGraphics, tBackRear);
		printTexturePolygon(bufGraphics, tRightRear);
		bufGraphics.setColor(wagonColor0);
		printTexturePolygon(bufGraphics, tRightWagon);

		bufGraphics.setColor(new Color(249, 238, 216));
		printTexturePolygon(bufGraphics, tRe[0]);

		bufGraphics.setColor(wagonColor1);
		printTexturePolygon(bufGraphics, tWagon[0]);

		bufGraphics.setColor(Color.BLUE);
		printTexturePolygon(bufGraphics, tWi[0]);

		bufGraphics.setColor(Color.BLACK);
		printTexturePolygon(bufGraphics, tWh[0]);

	}

}
