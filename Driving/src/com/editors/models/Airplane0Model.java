package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
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
public class Airplane0Model extends VehicleModel {

	protected BPoint[][][] tailLeftWing;
	protected BPoint[][][] tailRightWing;
	protected BPoint[][][][] tailRudder;
	protected BPoint[][][] rightWing;
	protected BPoint[][][] leftWing;

	public static final String NAME = "Airplane";

	protected double dxTexture = 100;
	protected double dyTexture = 100;

	protected double dyRudder;
	protected double dzRudder;

	// body textures
	protected int[][] tBo = null;
	protected int[] tLeftWing = null;
	protected int[][] tLeftBody = null;
	protected int[][] tTopBody = null;
	protected int[][] tRightBody = null;
	protected int[] tRightWing = null;
	protected int[] tLeftTail;
	protected int[] tRightTail;
	protected int[][] tRudder;

	protected int backNY = 4;
	protected int fuselageNY = 2;
	protected int frontNY = 4;
	protected int bodyNX = 2;
	protected int bodyNY = backNY + fuselageNY + frontNY;
	protected int bodyNZ = 2;

	int tailWingNX = 2;
	int tailWingNY = 2;
	int tailWingNZ = 2;

	int tailRudderNX = 1;
	int tailRudderNY = 2;
	int tailRudderNZ = 2;

	int wingNX = 2;
	int wingNY = 2;
	int wingNZ = 2;

	public Airplane0Model(double dx, double dy, double dz, double dxf, double dyf, double dzf, double dxr, double dyr,
			double dzr, double dxRoof, double dyRoof, double dzRoof, double dxBottom, double dyBottom, double dzBottom,
			double rearOverhang, double frontOverhang, double rearOverhang1, double frontOverhang1) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;

		this.dxFront = dxf;
		this.dyFront = dyf;
		this.dzFront = dzf;

		this.dxRear = dxr;
		this.dyRear = dyr;
		this.dzRear = dzr;

		this.dxRoof = dxRoof;
		this.dyRoof = dyRoof;
		this.dzRoof = dzRoof;

		this.dxBottom = dxBottom;
		this.dyBottom = dyBottom;
		this.dzBottom = dzBottom;

		this.rearOverhang = rearOverhang;
		this.frontOverhang = frontOverhang;

		this.rearOverhang1 = rearOverhang1;
		this.frontOverhang1 = frontOverhang1;

		this.dyRudder = dyRear * 0.25;
		this.dzRudder = dzRear + 71;

	}

	@Override
	public void initMesh() {

		int c = 0;
		c = initDoubleArrayValues(tBo = new int[1][4], c);

		c = initDoubleArrayValues(tLeftBody = new int[bodyNY - 1][4], c);
		c = initSingleArrayValues(tLeftTail = new int[4], c);
		c = initSingleArrayValues(tLeftWing = new int[4], c);
		c = initDoubleArrayValues(tTopBody = new int[bodyNY - 1][4], c);
		c = initSingleArrayValues(tRightWing = new int[4], c);
		c = initSingleArrayValues(tRightTail = new int[4], c);
		c = initDoubleArrayValues(tRightBody = new int[bodyNY - 1][4], c);
		c = initDoubleArrayValues(tRudder = new int[2][4], c);

		points = new Vector<Point3D>();
		texturePoints = new Vector();

		buildBody();
		buildTextures();

		// faces
		int NF = (bodyNY - 1) * 4;// body
		NF += 12;// wings
		NF += 12;// tail wings
		NF += tailRudder.length * 2;// rudder
		faces = new int[NF][3][4];

		int counter = 0;
		counter = buildFaces(counter, bodyNY);

	}

	protected int buildFaces(int counter, int numy) {

		counter = buildCabinfaces(counter, numy);
		counter = buildWingFaces(counter);
		counter = buildTailFaces(counter);
		return counter;
	}

	protected int buildWingFaces(int counter) {

		faces[counter++] = buildFace(Renderer3D.CAR_BACK, leftWing[0][0][0], leftWing[1][0][0], leftWing[1][0][1],
				leftWing[0][0][1], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, leftWing[0][0][0], leftWing[0][0][1], leftWing[0][0 + 1][1],
				leftWing[0][0 + 1][0], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, leftWing[0][0][0], leftWing[0][0 + 1][0],
				leftWing[1][0 + 1][0], leftWing[1][0][0], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, leftWing[1][0][0], leftWing[1][0 + 1][0],
				leftWing[1][0 + 1][1], leftWing[1][0][1], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_TOP, leftWing[0][0][1], leftWing[1][0][1], leftWing[1][0 + 1][1],
				leftWing[0][0 + 1][1], tLeftWing);
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, leftWing[0][1][0], leftWing[0][1][1], leftWing[1][1][1],
				leftWing[1][1][0], tBo[0]);

		faces[counter++] = buildFace(Renderer3D.CAR_BACK, rightWing[0][0][0], rightWing[1][0][0], rightWing[1][0][1],
				rightWing[0][0][1], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, rightWing[0][0][0], rightWing[0][0][1],
				rightWing[0][0 + 1][1], rightWing[0][0 + 1][0], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, rightWing[0][0][0], rightWing[0][0 + 1][0],
				rightWing[1][0 + 1][0], rightWing[1][0][0], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, rightWing[1][0][0], rightWing[1][0 + 1][0],
				rightWing[1][0 + 1][1], rightWing[1][0][1], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_TOP, rightWing[0][0][1], rightWing[1][0][1], rightWing[1][0 + 1][1],
				rightWing[0][0 + 1][1], tRightWing);
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, rightWing[0][1][0], rightWing[0][1][1], rightWing[1][1][1],
				rightWing[1][1][0], tBo[0]);

		return counter;
	}

	protected int buildTailFaces(int counter) {

		faces[counter++] = buildFace(Renderer3D.CAR_BACK, tailLeftWing[0][0][0], tailLeftWing[1][0][0],
				tailLeftWing[1][0][1], tailLeftWing[0][0][1], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, tailLeftWing[0][0][0], tailLeftWing[0][0][1],
				tailLeftWing[0][0 + 1][1], tailLeftWing[0][0 + 1][0], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, tailLeftWing[0][0][0], tailLeftWing[0][0 + 1][0],
				tailLeftWing[1][0 + 1][0], tailLeftWing[1][0][0], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, tailLeftWing[1][0][0], tailLeftWing[1][0 + 1][0],
				tailLeftWing[1][0 + 1][1], tailLeftWing[1][0][1], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_TOP, tailLeftWing[0][0][1], tailLeftWing[1][0][1],
				tailLeftWing[1][0 + 1][1], tailLeftWing[0][0 + 1][1], tLeftTail);
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, tailLeftWing[0][1][0], tailLeftWing[0][1][1],
				tailLeftWing[1][1][1], tailLeftWing[1][1][0], tBo[0]);

		faces[counter++] = buildFace(Renderer3D.CAR_BACK, tailRightWing[0][0][0], tailRightWing[1][0][0],
				tailRightWing[1][0][1], tailRightWing[0][0][1], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, tailRightWing[0][0][0], tailRightWing[0][0][1],
				tailRightWing[0][0 + 1][1], tailRightWing[0][0 + 1][0], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, tailRightWing[0][0][0], tailRightWing[0][0 + 1][0],
				tailRightWing[1][0 + 1][0], tailRightWing[1][0][0], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, tailRightWing[1][0][0], tailRightWing[1][0 + 1][0],
				tailRightWing[1][0 + 1][1], tailRightWing[1][0][1], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_TOP, tailRightWing[0][0][1], tailRightWing[1][0][1],
				tailRightWing[1][0 + 1][1], tailRightWing[0][0 + 1][1], tRightTail);
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, tailRightWing[0][1][0], tailRightWing[0][1][1],
				tailRightWing[1][1][1], tailRightWing[1][1][0], tBo[0]);

		for (int r = 0; r < tailRudder.length; r++) {

			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, tailRudder[r][0][0][0], tailRudder[r][0][0][1],
					tailRudder[r][0][0 + 1][1], tailRudder[r][0][0 + 1][0], tRudder[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, tailRudder[r][0][0][0], tailRudder[r][0][0 + 1][0],
					tailRudder[r][0][0 + 1][1], tailRudder[r][0][0][1], tRudder[1]);

		}
		return counter;
	}

	protected int buildCabinfaces(int counter, int numy) {

		for (int k = 0; k < numy - 1; k++) {

			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, body[0][k][0], body[0][k][1], body[0][k + 1][1],
					body[0][k + 1][0], tLeftBody[k]);
			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, body[0][k][0], body[0][k + 1][0], body[1][k + 1][0],
					body[1][k][0], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, body[1][k][0], body[1][k + 1][0], body[1][k + 1][1],
					body[1][k][1], tRightBody[k]);
			faces[counter++] = buildFace(Renderer3D.CAR_TOP, body[0][k][1], body[1][k][1], body[1][k + 1][1],
					body[0][k + 1][1], tTopBody[k]);

		}

		return counter;
	}

	protected void buildBody() {

		buildCabin();
		buildTail();
		buildWings();

	}

	protected void buildCabin() {

		body = new BPoint[bodyNX][bodyNY][bodyNZ];

		double back_width = dxRear;
		double back_width1 = dxRear * (1 - 0.3) + dx * 0.3;
		double back_width2 = dxRear * (1 - 0.6) + dx * 0.6;
		double back_width3 = dxRear * (1 - 0.9) + dx * 0.9;

		double back_height = dzRear;
		double back_height1 = dzRear * (1 - 0.3) + dz * 0.3;
		double back_height2 = dzRear * (1 - 0.6) + dz * 0.6;
		double back_height3 = dzRear * (1 - 0.9) + dz * 0.9;

		Segments b0 = new Segments(0, back_width, 0, dyRear, dz - dzRear, back_height);
		Segments b1 = new Segments(0, back_width1, 0, dyRear, dz - back_height1, back_height1);
		Segments b2 = new Segments(0, back_width2, 0, dyRear, dz - back_height2, back_height2);
		Segments b3 = new Segments(0, back_width3, 0, dyRear, dz - back_height3, back_height3);

		body[0][0][0] = addBPoint(0.0, 0.0, 0, b0);
		body[1][0][0] = body[0][0][0];
		body[0][0][1] = addBPoint(0.0, 0.0, 1.0, b0);
		body[1][0][1] = body[0][0][1];

		body[0][1][0] = addBPoint(-0.5, 0.25, 0, b1);
		body[1][1][0] = addBPoint(0.5, 0.25, 0, b1);
		body[0][1][1] = addBPoint(-0.5, 0.25, 1.0, b1);
		body[1][1][1] = addBPoint(0.5, 0.25, 1.0, b1);

		body[0][2][0] = addBPoint(-0.5, 0.5, 0, b2);
		body[1][2][0] = addBPoint(0.5, 0.5, 0, b2);
		body[0][2][1] = addBPoint(-0.5, 0.5, 1.0, b2);
		body[1][2][1] = addBPoint(0.5, 0.5, 1.0, b2);

		body[0][3][0] = addBPoint(-0.5, 0.75, 0, b3);
		body[1][3][0] = addBPoint(0.5, 0.75, 0, b3);
		body[0][3][1] = addBPoint(-0.5, 0.75, 1.0, b3);
		body[1][3][1] = addBPoint(0.5, 0.75, 1.0, b3);

		Segments p0 = new Segments(0, dx, dyRear, dy, 0, dz);

		body[0][backNY][0] = addBPoint(-0.5, 0.0, 0, p0);
		body[1][backNY][0] = addBPoint(0.5, 0.0, 0, p0);
		body[0][backNY][1] = addBPoint(-0.5, 0.0, 1.0, p0);
		body[1][backNY][1] = addBPoint(0.5, 0.0, 1.0, p0);

		body[0][backNY + 1][0] = addBPoint(-0.5, 1.0, 0, p0);
		body[1][backNY + 1][0] = addBPoint(0.5, 1.0, 0, p0);
		body[0][backNY + 1][1] = addBPoint(-0.5, 1.0, 1.0, p0);
		body[1][backNY + 1][1] = addBPoint(0.5, 1.0, 1.0, p0);

		double front_width = dxFront;
		double front_width0 = front_width * (1 - 0.75) + dx * 0.75;
		double front_width1 = front_width * (1 - 0.7) + dx * 0.7;
		double front_width2 = front_width * (1 - 0.65) + dx * 0.65;

		double front_height0 = dzFront * (1 - 0.75) + dz * 0.75;
		double front_height1 = dzFront * (1 - 0.7) + dz * 0.7;
		double front_height2 = dzFront * (1 - 0.65) + dz * 0.65;

		Segments f0 = new Segments(0, front_width0, dyRear + dy, dyFront, 0, front_height0);
		Segments f1 = new Segments(0, front_width1, dyRear + dy, dyFront, 0, front_height1);
		Segments f2 = new Segments(0, front_width2, dyRear + dy, dyFront, 0, front_height2);
		Segments f3 = new Segments(0, front_width, dyRear + dy, dyFront, 0, dzFront);

		body[0][backNY + fuselageNY][0] = addBPoint(-0.5, 0.25, 0, f0);
		body[1][backNY + fuselageNY][0] = addBPoint(0.5, 0.25, 0, f0);
		body[0][backNY + fuselageNY][1] = addBPoint(-0.5, 0.25, 1.0, f0);
		body[1][backNY + fuselageNY][1] = addBPoint(0.5, 0.25, 1.0, f0);

		body[0][backNY + fuselageNY + 1][0] = addBPoint(-0.5, 0.5, 0, f1);
		body[1][backNY + fuselageNY + 1][0] = addBPoint(0.5, 0.5, 0, f1);
		body[0][backNY + fuselageNY + 1][1] = addBPoint(-0.5, 0.5, 1.0, f1);
		body[1][backNY + fuselageNY + 1][1] = addBPoint(0.5, 0.5, 1.0, f1);

		body[0][backNY + fuselageNY + 2][0] = addBPoint(-0.5, 0.75, 0, f2);
		body[1][backNY + fuselageNY + 2][0] = addBPoint(0.5, 0.75, 0, f2);
		body[0][backNY + fuselageNY + 2][1] = addBPoint(-0.5, 0.75, 1.0, f2);
		body[1][backNY + fuselageNY + 2][1] = addBPoint(0.5, 0.75, 1.0, f2);

		body[0][backNY + fuselageNY + 3][0] = addBPoint(0.0, 1.0, 0, f3);
		body[1][backNY + fuselageNY + 3][0] = body[0][backNY + fuselageNY + 3][0];
		body[0][backNY + fuselageNY + 3][1] = addBPoint(0.0, 1.0, 1.0, f3);
		body[1][backNY + fuselageNY + 3][1] = body[0][backNY + fuselageNY + 3][1];

	}

	protected void buildWings() {

		double q = Math.PI * 10.0 / 180.0;
		double sq = Math.sin(q);
		double cq = Math.cos(q);

		double sq1 = sq * dxRoof / dzRoof;
		double rearWY = rearOverhang;

		rightWing = new BPoint[wingNX][wingNY][wingNZ];
		Segments rWing0 = new Segments(dx * 0.5, dxRoof / cq, rearWY, dyRoof, 0, dzRoof);

		rightWing[0][0][0] = addBPoint(0, 0.0, 0, rWing0);
		rightWing[1][0][0] = addBPoint(cq, 0.0, sq1, rWing0);
		rightWing[0][0][1] = addBPoint(0.0, 0.0, 1.0, rWing0);
		rightWing[1][0][1] = addBPoint(cq, 0.0, 1.0 + sq1, rWing0);

		rightWing[0][1][0] = addBPoint(0.0, 1.0, 0, rWing0);
		rightWing[1][1][0] = addBPoint(cq, 0.5, sq1, rWing0);
		rightWing[0][1][1] = addBPoint(0.0, 1.0, 1.0, rWing0);
		rightWing[1][1][1] = addBPoint(cq, 0.5, 1.0 + sq1, rWing0);

		leftWing = new BPoint[wingNX][wingNY][wingNZ];

		Segments lWing0 = new Segments(-dx * 0.5, dxRoof / cq, rearWY, dyRoof, 0, dzRoof);

		leftWing[0][0][0] = addBPoint(-cq, 0.0, sq1, lWing0);
		leftWing[1][0][0] = addBPoint(0.0, 0.0, 0, lWing0);
		leftWing[0][0][1] = addBPoint(-cq, 0.0, 1.0 + sq1, lWing0);
		leftWing[1][0][1] = addBPoint(0.0, 0.0, 1.0, lWing0);

		leftWing[0][1][0] = addBPoint(-cq, 0.5, sq1, lWing0);
		leftWing[1][1][0] = addBPoint(0.0, 1.0, 0, lWing0);
		leftWing[0][1][1] = addBPoint(-cq, 0.5, 1.0 + sq1, lWing0);
		leftWing[1][1][1] = addBPoint(0.0, 1.0, 1.0, lWing0);

	}

	protected void buildTail() {

		/////// tail

		double back_width = dxRear;
		double back_height = dzRear;

		double twDX = 65;

		tailRightWing = new BPoint[tailWingNX][tailWingNY][tailWingNZ];

		Segments trWing0 = new Segments(0, twDX, 0, dyRear * 0.125, dz - back_height, back_height);

		tailRightWing[0][0][0] = body[1][0][0];
		tailRightWing[1][0][0] = addBPoint(1.0, 0.0, 0.0, trWing0);
		tailRightWing[0][0][1] = body[1][0][1];
		tailRightWing[1][0][1] = addBPoint(1.0, 0.0, 1.0, trWing0);

		tailRightWing[0][1][0] = body[1][1][0];
		tailRightWing[1][1][0] = addBPoint(1.0, 1.0, 0, trWing0);
		tailRightWing[0][1][1] = body[1][1][1];
		tailRightWing[1][1][1] = addBPoint(1.0, 1.0, 1.0, trWing0);

		tailLeftWing = new BPoint[tailWingNX][tailWingNY][tailWingNZ];

		Segments tlWing0 = new Segments(0, twDX, 0, dyRear * 0.125, dz - back_height, back_height);

		tailLeftWing[0][0][0] = addBPoint(-1.0, 0, 0, tlWing0);
		tailLeftWing[1][0][0] = body[0][0][0];
		tailLeftWing[0][0][1] = addBPoint(-1.0, 0.0, 1.0, tlWing0);
		tailLeftWing[1][0][1] = body[0][0][1];

		tailLeftWing[0][1][0] = addBPoint(-1.0, 1.0, 0, tlWing0);
		tailLeftWing[1][1][0] = body[0][1][0];
		tailLeftWing[0][1][1] = addBPoint(-1.0, 1.0, 1.0, tlWing0);
		tailLeftWing[1][1][1] = body[0][1][1];

		tailRudder = new BPoint[1][tailRudderNX][tailRudderNY][tailRudderNZ];

		Segments rudder0 = new Segments(0, back_width, 0, dyRudder, dz - back_height, dzRudder);

		tailRudder[0][0][0][0] = addBPoint((body[0][0][1].x + body[1][0][1].x) * 0.5, body[0][0][1].y, body[0][0][1].z);
		tailRudder[0][0][1][0] = addBPoint((body[0][1][1].x + body[1][1][1].x) * 0.5, body[0][1][1].y, body[0][1][1].z);
		tailRudder[0][0][0][1] = addBPoint(0, 0.0, 1.0, rudder0);
		tailRudder[0][0][1][1] = addBPoint(0, 1.0, 1.0, rudder0);

	}

	protected void buildTextures() {
		// Texture points

		double maxDY = Math.max(dyTexture, dy + dyRear + dyFront);

		int shift = 1;

		double y = by;
		double x = bx;

		addTRect(x, y, dxTexture, dyTexture);
		x += dxTexture;
		buildLefTextures(x, y, shift);
		x += dz + dxRoof;
		buildTopTextures(x + dx * 0.5, y, shift);
		x += dx + dxRoof;
		buildRightTextures(x, y, shift);
		x += dz;
		buildRudderTextures(x, y);
		x += 2 * dyRudder;

		IMG_WIDTH = (int) (bx + x);
		IMG_HEIGHT = (int) (2 * by + maxDY);

	}

	protected void buildLefTextures(double x, double y, int shift) {

		for (int i = 0; i < bodyNY - 1; i++) {
			addTPoint(x + body[0][i][0].z, y + body[0][i][0].y, 0);
			addTPoint(x + body[0][i][1].z, y + body[0][i][1].y, 0);
			addTPoint(x + body[0][i + 1][1].z, y + body[0][i + 1][1].y, 0);
			addTPoint(x + body[0][i + 1][0].z, y + body[0][i + 1][0].y, 0);
		}
	}

	protected void buildTopTextures(double x, double y, int shift) {

		addTPoint(x + tailLeftWing[0][0][0].x, y + tailLeftWing[0][0][0].y, 0);
		addTPoint(x + tailLeftWing[1][0][0].x, y + tailLeftWing[1][0][0].y, 0);
		addTPoint(x + tailLeftWing[1][1][0].x, y + tailLeftWing[1][1][0].y, 0);
		addTPoint(x + tailLeftWing[0][1][0].x, y + tailLeftWing[0][1][0].y, 0);

		addTPoint(x + leftWing[0][0][0].x, y + leftWing[0][0][0].y, 0);
		addTPoint(x + leftWing[1][0][0].x, y + leftWing[1][0][0].y, 0);
		addTPoint(x + leftWing[1][0 + 1][0].x, y + leftWing[1][0 + 1][0].y, 0);
		addTPoint(x + leftWing[0][0 + 1][0].x, y + leftWing[0][0 + 1][0].y, 0);

		for (int i = 0; i < bodyNY - 1; i++) {
			addTPoint(x + body[0][i][0].x, y + body[0][i][0].y, 0);
			addTPoint(x + body[1][i][0].x, y + body[1][i][0].y, 0);
			addTPoint(x + body[1][i + 1][0].x, y + body[1][i + 1][0].y, 0);
			addTPoint(x + body[0][i + 1][0].x, y + body[0][i + 1][0].y, 0);
		}

		addTPoint(x + rightWing[0][0][0].x, y + rightWing[0][0][0].y, 0);
		addTPoint(x + rightWing[1][0][0].x, y + rightWing[1][0][0].y, 0);
		addTPoint(x + rightWing[1][0 + 1][0].x, y + rightWing[1][0 + 1][0].y, 0);
		addTPoint(x + rightWing[0][0 + 1][0].x, y + rightWing[0][0 + 1][0].y, 0);

		addTPoint(x + tailRightWing[0][0][0].x, y + tailRightWing[0][0][0].y, 0);
		addTPoint(x + tailRightWing[1][0][0].x, y + tailRightWing[1][0][0].y, 0);
		addTPoint(x + tailRightWing[1][1][0].x, y + tailRightWing[1][1][0].y, 0);
		addTPoint(x + tailRightWing[0][1][0].x, y + tailRightWing[0][1][0].y, 0);
	}

	protected void buildRightTextures(double x, double y, int shift) {

		double maxDZ = Math.max(dz, Math.max(dzRear, dzFront));

		for (int i = 0; i < bodyNY - 1; i++) {
			addTPoint(x + maxDZ - body[1][i][0].z, y + body[1][i][0].y, 0);
			addTPoint(x + maxDZ - body[1][i][1].z, y + body[1][i][1].y, 0);
			addTPoint(x + maxDZ - body[1][i + 1][1].z, y + body[1][i + 1][1].y, 0);
			addTPoint(x + maxDZ - body[1][i + 1][0].z, y + body[1][i + 1][0].y, 0);
		}
	}

	private void buildRudderTextures(double x, double y) {

		addTRect(x, y, dyRudder, dzRudder);
		addTRect(x + dyRudder, y, dyRudder, dzRudder);
	}

	@Override
	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);

	}

	@Override
	public void printTexture(Graphics2D bufGraphics) {

		Color bodyColor = new Color(255, 255, 255);
		Color topBodyColor = new Color(255, 255, 255);

		bufGraphics.setStroke(new BasicStroke(0.1f));

		bufGraphics.setColor(bodyColor);
		printTexturePolygon(bufGraphics, tBo[0]);

		bufGraphics.setColor(bodyColor);
		for (int i = 0; i < tLeftBody.length; i++) {
			printTexturePolygon(bufGraphics, tLeftBody[i]);
		}

		bufGraphics.setColor(topBodyColor);
		printTexturePolygon(bufGraphics, tLeftTail);
		printTexturePolygon(bufGraphics, tLeftWing);
		for (int i = 0; i < tTopBody.length; i++) {
			printTexturePolygon(bufGraphics, tTopBody[i]);
		}
		printTexturePolygon(bufGraphics, tRightWing);
		printTexturePolygon(bufGraphics, tRightTail);

		bufGraphics.setColor(bodyColor);
		for (int i = 0; i < tRightBody.length; i++) {
			printTexturePolygon(bufGraphics, tRightBody[i]);
		}
		for (int i = 0; i < tRudder.length; i++) {
			printTexturePolygon(bufGraphics, tRudder[i]);
		}

	}

}
