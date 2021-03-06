package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.main.CarFrame;

public class Car0Model extends VehicleModel {

	protected int nBasePoints = 6;

	// these wheel data should be read from the editor
	protected int rays_number = 10;
	protected double wheel_width = 0;
	protected double wheel_radius;

	public static String NAME = "Car0";

	/**
	 * BOTTOM-UP SECTIONS [y],[x0,x1,x2],[z0,z1,z2]
	 *
	 * i from bottom to top, counterclockwise
	 *
	 * measurs from mazda-6 2008
	 *
	 */
	protected double[][][] pBody = null;

	protected double[] axles = null;
	private BPoint[][] wheelLeftFront;
	private BPoint[][] wheelRightFront;
	private BPoint[][] wheelLeftRear;
	private BPoint[][] wheelRightRear;
	private BPoint[][] bodyPoints;

	public Car0Model(double dx, double dy, double dz) {
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}

	@Override
	public void initMesh() {

		points = new Vector<Point3D>();
		texturePoints = new Vector();

		double[][][] mainBody = {

				{ { 0.0000 }, { 1.0, 1.0, 0.75 }, { 0.1044, 0.3655, 0.3655 } },
				{ { 0.0329 }, { 1.0, 1.0, 0.75 }, { 0.0763, 0.7068, 0.7068 } },
				{ { 0.1186 }, { 1.0, 1.0, 0.6279 }, { 0.0562, 0.7108, 0.7108 } },
				{ { 0.1405 }, { 1.0, 1.0, 0.6279 }, { 0.0482, 0.7068, 0.7590 } },
				{ { 0.1471 }, { 1.0, 1.0, 0.6279 }, { 0.2490, 0.7028, 0.7711 } },
				{ { 0.1778 }, { 1.0, 1.0, 0.6279 }, { 0.3775, 0.6867, 0.8273 } },
				{ { 0.2217 }, { 1.0, 1.0, 0.6279 }, { 0.4217, 0.7269, 0.8916 } },
				{ { 0.2667 }, { 1.0, 1.0, 0.6279 }, { 0.3775, 0.6707, 0.9438 } },
				{ { 0.2964 }, { 1.0, 1.0, 0.6279 }, { 0.2490, 0.6667, 0.9639 } },
				{ { 0.3063 }, { 1.0, 1.0, 0.6279 }, { 0.0000, 0.6627, 0.9679 } },
				{ { 0.4281 }, { 1.0, 1.0, 0.6279 }, { 0.0000, 0.6426, 0.9839 } },
				{ { 0.5653 }, { 1.0, 1.0, 0.8343 }, { 0.0000, 0.6145, 0.9157 } },
				{ { 0.6894 }, { 1.0, 1.0, 0.8343 }, { 0.0000, 0.6104, 0.7108 } },
				{ { 0.7102 }, { 1.0, 1.0, 0.75 }, { 0.0000, 0.5984, 0.6747 } },
				{ { 0.7201 }, { 1.0, 1.0, 0.75 }, { 0.2490, 0.5984, 0.6586 } },
				{ { 0.7519 }, { 1.0, 1.0, 0.75 }, { 0.3775, 0.5944, 0.6305 } },
				{ { 0.7980 }, { 1.0, 1.0, 0.75 }, { 0.4257, 0.6225, 0.6225 } },
				{ { 0.8419 }, { 1.0, 1.0, 0.75 }, { 0.3775, 0.5984, 0.5984 } },
				{ { 0.8694 }, { 1.0, 1.0, 0.75 }, { 0.2490, 0.5703, 0.5703 } },
				{ { 0.8782 }, { 1.0, 1.0, 0.75 }, { 0.0000, 0.5703, 0.5703 } },
				{ { 0.9759 }, { 1.0, 1.0, 0.75 }, { 0.0000, 0.4378, 0.4378 } },
				{ { 1.0000 }, { 1.0, 1.0, 0.75 }, { 0.1727, 0.2811, 0.2811 } },

		};

		double[] mainAxles = { 0.2217, 0.7980 };

		pBody = mainBody;
		axles = mainAxles;

		wheel_radius = 0.06703 * dy;
		wheelZ = 0.541 * wheel_radius;
		wheel_width = 0.129 * dx;

		buildCar();

	}

	protected void buildCar() {

		buildBodyPoints();
		buildWheels();

		buildFaces();

	}

	private void buildBodyPoints() {

		int numSections = pBody.length;

		bodyPoints = new BPoint[numSections][6];

		for (int k = 0; k < numSections; k++) {

			double[][] d = pBody[k];

			double yi = d[0][0];

			double x0 = d[1][0];
			double x1 = d[1][1];
			double x2 = d[1][2];

			double z0 = d[2][0];
			double z1 = d[2][1];
			double z2 = d[2][2];

			double y = yi * dy;

			double deltax0 = dx * x0 * 0.5;
			double deltax1 = dx * x1 * 0.5;
			double deltax2 = dx * x2 * 0.5;

			double deltaz0 = dz * z0;
			double deltaz1 = dz * z1;
			double deltaz2 = dz * z2;

			double x = 0;
			double z = 0;

			bodyPoints[k][0] = addBPoint(x - deltax0, y, z + deltaz0);
			bodyPoints[k][1] = addBPoint(x - deltax1, y, z + deltaz1);
			bodyPoints[k][2] = addBPoint(x - deltax2, y, z + deltaz2);
			bodyPoints[k][3] = addBPoint(x + deltax2, y, z + deltaz2);
			bodyPoints[k][4] = addBPoint(x + deltax1, y, z + deltaz1);
			bodyPoints[k][5] = addBPoint(x + deltax0, y, z + deltaz0);

		}

	}

	private void buildWheels() {

		double wx = dx * 0.5 - wheel_width;
		double yRearAxle = axles[0] * dy;
		double yFrontAxle = axles[1] * dy;

		wheelLeftFront = buildWheel(-wx - wheel_width, yFrontAxle, wheelZ, wheel_radius, wheel_width, rays_number);
		wheelRightFront = buildWheel(wx, yFrontAxle, wheelZ, wheel_radius, wheel_width, rays_number);
		wheelLeftRear = buildWheel(-wx - wheel_width, yRearAxle, wheelZ, wheel_radius, wheel_width, rays_number);
		wheelRightRear = buildWheel(wx, yRearAxle, wheelZ, wheel_radius, wheel_width, rays_number);

	}

	private void buildFaces() {

		int numSections = pBody.length;

		int totBlockTexturesPoints = buildTexture();

		int totWheelPolygon = rays_number + 2 * (rays_number - 2);

		int NUM_WHEEL_FACES = 4 * totWheelPolygon;
		int NUM_FACES = nBasePoints * (numSections - 1);

		faces = new int[NUM_FACES + NUM_WHEEL_FACES + 6][][];
		int counter = 0;
		counter = buildBodyFaces(counter, NUM_FACES, totBlockTexturesPoints);
		totBlockTexturesPoints += 6;
		totBlockTexturesPoints += 6;

		counter = buildWheelFaces(counter, totBlockTexturesPoints, totWheelPolygon);

	}

	private int buildTexture() {

		int numSections = pBody.length;

		// single block texture

		int totBlockTexturesPoints = 0;

		for (int k = 0; k < numSections - 1; k++) {

			double[][] d = pBody[k];
			double yi = d[0][0];
			double x0 = d[1][0];
			double x1 = d[1][1];
			double x2 = d[1][2];
			double z0 = d[2][0];
			double z1 = d[2][1];
			double z2 = d[2][2];

			double[][] d_plus_1 = pBody[k + 1];
			double yipl = d_plus_1[0][0];
			double x0pl = d_plus_1[1][0];
			double x1pl = d_plus_1[1][1];
			double x2pl = d_plus_1[1][2];
			double z0pl = d_plus_1[2][0];
			double z1pl = d_plus_1[2][1];
			double z2pl = d_plus_1[2][2];

			double incrementY = (yipl - yi) * dy;

			double y = by + yi * dy + dz;
			double x = bx;

			double dSide = Math.sqrt(dz * (z2 - z1) * dz * (z2 - z1) + dx * (x2 - x1) * dx * (x2 - x1));
			double dSidePl = Math
					.sqrt(dz * (z2pl - z1pl) * dz * (z2pl - z1pl) + dx * (x2pl - x1pl) * dx * (x2pl - x1pl));
			double dRoofX = dx * x2;
			double dRoofXPl = dx * x2pl;

			for (int p0 = 0; p0 < nBasePoints; p0++) {

				// left
				if (p0 == 0) {

					addTPoint(x + dz * z0, y, 0);
					addTPoint(x + dz * z1, y, 0);
					addTPoint(x + dz * z1pl, y + incrementY, 0);
					addTPoint(x + dz * z0pl, y + incrementY, 0);

				} else if (p0 == 1) {

					addTPoint(x + dz * z1, y, 0);
					addTPoint(x + dz * z1 + dSide, y, 0);
					addTPoint(x + dz * z1pl + dSidePl, y + incrementY, 0);
					addTPoint(x + dz * z1pl, y + incrementY, 0);
					x += dz;

				} else if (p0 == 2) {

					addTPoint(x + (dx - dRoofX) * 0.5, y, 0);
					addTPoint(x + (dx + dRoofX) * 0.5, y, 0);
					addTPoint(x + (dx + dRoofXPl) * 0.5, y + incrementY, 0);
					addTPoint(x + (dx - dRoofXPl) * 0.5, y + incrementY, 0);
					x += dx;

				} else if (p0 == 3) {

					addTPoint(x + dz * (1.0 - z1) - dSide, y, 0);
					addTPoint(x + dz * (1.0 - z1), y, 0);
					addTPoint(x + dz * (1.0 - z1pl), y + incrementY, 0);
					addTPoint(x + dz * (1.0 - z1pl) - dSidePl, y + incrementY, 0);

				}
				// right
				else if (p0 == 4) {

					addTPoint(x + dz * (1.0 - z1), y, 0);
					addTPoint(x + dz * (1.0 - z0), y, 0);
					addTPoint(x + dz * (1.0 - z0pl), y + incrementY, 0);
					addTPoint(x + dz * (1.0 - z1pl), y + incrementY, 0);
					x += dz;

				} else if (p0 == 5) {

					addTPoint(x, y, 0);
					addTPoint(x + dx, y, 0);
					addTPoint(x + dx, y + incrementY, 0);
					addTPoint(x, y + incrementY, 0);
					x += dx;
				}

				totBlockTexturesPoints += 4;
			}
		}

		// closing back and front texture

		double[][] dBack = pBody[0];
		double x1Ba = dBack[1][1];
		double x2Ba = dBack[1][2];
		double z0Ba = dBack[2][0];
		double z2Ba = dBack[2][2];

		double[][] dFront = pBody[pBody.length - 1];
		double x1Fr = dFront[1][1];
		double x2Fr = dFront[1][2];
		double z0Fr = dFront[2][0];
		double z2Fr = dFront[2][2];

		for (int i = 0; i < 2; i++) {

			double x = bx + dz;
			double y = by;

			if (i == 0) {

				double dzz = dz * (z2Ba - z0Ba);
				y = by + dz - dzz;

				addTPoint(x, y, 0);
				addTPoint(x, y + dzz, 0);
				addTPoint(x + 0.5 * dx * (1.0 - x2Ba), y + dzz, 0);
				addTPoint(x + 0.5 * dx * (1.0 + x2Ba), y + dzz, 0);
				addTPoint(x + dx, y + dzz, 0);
				addTPoint(x + dx, y, 0);
			} else if (i == 1) {
				y = by + dy + dz;

				double dzz = dz * (z2Fr - z0Fr);

				addTPoint(x + dx, y + dzz, 0);
				addTPoint(x + dx, y, 0);
				addTPoint(x + 0.5 * dx * (1.0 + x2Fr), y, 0);
				addTPoint(x + 0.5 * dx * (1.0 - x2Fr), y, 0);
				addTPoint(x, y, 0);
				addTPoint(x, y + dzz, 0);

			}
		}

		// wheel texture, a black square for simplicity:

		double x = bx + dz * 2 + dx * 2;
		double y = by;

		addTRect(x, y, wheel_width, wheel_width);

		IMG_WIDTH = (int) (2 * bx + 2 * (dx + dz) + wheel_width);
		IMG_HEIGHT = (int) (2 * by + dy + 2 * dz);

		return totBlockTexturesPoints;
	}

	private int buildBodyFaces(int counter, int NUM_FACES, int totBlockTexturesPoints) {

		int numSections = pBody.length;

		int[][][] bFaces = buildRedundantSingleBlockFaces(nBasePoints, numSections, 0, 0);

		for (int i = 0; i < NUM_FACES; i++) {
			faces[counter++] = bFaces[i];
		}
		// closing the mesh back with 3 panels
		int totPanel = totBlockTexturesPoints;
		for (int i = 0; i < 3; i++) {

			if (i == 0) {
				faces[counter++] = buildFace(CarFrame.CAR_BACK, bodyPoints[0][0], bodyPoints[0][2], bodyPoints[0][1],
						totPanel, totPanel + 2, totPanel + 1);
			} else if (i == 1) {
				faces[counter++] = buildFace(CarFrame.CAR_BACK, bodyPoints[0][0], bodyPoints[0][5], bodyPoints[0][3],
						bodyPoints[0][2], totPanel, totPanel + 5, totPanel + 3, totPanel + 2);
			} else if (i == 2) {
				faces[counter++] = buildFace(CarFrame.CAR_BACK, bodyPoints[0][3], bodyPoints[0][5], bodyPoints[0][4],
						totPanel + 3, totPanel + 5, totPanel + 4);
			}
		}

		/////
		// closing the mesh front with 3 panels
		totPanel = totBlockTexturesPoints + 6;
		for (int i = 0; i < 3; i++) {

			if (i == 0) {
				faces[counter++] = buildFace(CarFrame.CAR_FRONT, bodyPoints[numSections - 1][0],
						bodyPoints[numSections - 1][1], bodyPoints[numSections - 1][2], totPanel, totPanel + 1,
						totPanel + 2);
			} else if (i == 1) {
				faces[counter++] = buildFace(CarFrame.CAR_FRONT, bodyPoints[numSections - 1][0],
						bodyPoints[numSections - 1][2], bodyPoints[numSections - 1][3], bodyPoints[numSections - 1][5],
						totPanel, totPanel + 2, totPanel + 3, totPanel + 5);
			} else if (i == 2) {
				faces[counter++] = buildFace(CarFrame.CAR_FRONT, bodyPoints[numSections - 1][3],
						bodyPoints[numSections - 1][4], bodyPoints[numSections - 1][5], totPanel + 3, totPanel + 4,
						totPanel + 5);
			}
		}

		return counter;
	}

	private int buildWheelFaces(int counter, int totBlockTexturesPoints, int totWheelPolygon) {

		// build wheels faces
		int[][][] wFaces = buildWheelFaces(wheelLeftFront, totBlockTexturesPoints);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}

		wFaces = buildWheelFaces(wheelRightFront, totBlockTexturesPoints);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}

		wFaces = buildWheelFaces(wheelLeftRear, totBlockTexturesPoints);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}

		wFaces = buildWheelFaces(wheelRightRear, totBlockTexturesPoints);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}

		return counter;
	}

	@Override
	public void printTexture(Graphics2D bg) {
		// draw lines for reference

		bg.setColor(Color.BLACK);
		bg.setStroke(new BasicStroke(0.1f));
		printTextureFaces(bg, faces);

	}

	@Override
	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);

	}

}
