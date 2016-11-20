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

	protected BPoint[][][] tailLeftWing=null;
	protected BPoint[][][] tailRightWing=null;
	protected BPoint[][][][] tailRudder=null;
	protected BPoint[][][] rightWing=null;
	protected BPoint[][][] leftWing=null;

	protected BPoint[][][] frontCabin=null;

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
	protected int[][] tLeftFront= null;
	protected int[][] tTopFront = null;
	protected int[][] tRightFront = null;

	protected int[][] tWheel = null;
	protected double wheelRadius;
	protected double wheelWidth;
	protected int wheelRays;
	protected BPoint[][] wheelLeftRear=null;
	protected BPoint[][] wheelRightRear=null;
	protected BPoint[][] wheelFront=null;

	int tailWingNX = 2;
	int tailWingNY = 2;
	int tailWingNZ = 2;

	int tailRudderNX = 1;
	int tailRudderNY = 2;
	int tailRudderNZ = 2;

	int wingNX = 2;
	int wingNY = 2;
	int wingNZ = 2;

	protected int fuselageNY=0;
	protected int bodyNX = 2;
	protected int bodyNZ = 2;
	protected int backNY=0;
	protected int frontNY=0;
	protected int bodyNY=0;

	/**
	 * BOTTOM-UP SECTIONS [y],[x0],[z0,z1]
	 **/
	protected double[][][] pRear = null;
	protected double[][][] pFuselage = null;
	protected double[][][] pFront = null;

	private double[][][] mainRear = {
			{ { 0.00 }, { 0.1 }, { 0.70, 0.8333} },
			{ { 0.18 }, { 0.4333 }, { 0.5333, 0.9000 } },
			{ { 0.42 }, { 0.6000 }, { 0.3333, 0.9000} },
			{ { 0.61 }, { 0.833 }, { 0.2000, 0.9333 } },
			{ { 0.75 }, { 0.9333 }, { 0.1000, 0.9333 } } };

	private double[][][] mainFuselage = {
			{ { 0.00 }, { 1.0 }, { 0.0, 1.000} },
			{ { 1.00 }, { 1.0 }, { 0.0, 1.000} },
	};

	private double[][][] mainFront = {
			{ { 0.39 }, { 0.9000 }, { 0.0667, 0.9333 } },
			{ { 0.63 }, { 0.7667 }, { 0.1000, 0.7667 } },
			{ { 0.76 }, { 0.6000 }, { 0.1333, 0.6000 } },
			{ { 0.90 }, { 0.3333 }, { 0.2000, 0.5000 } },
			{ { 1.00 }, { 0.0000 }, { 0.3333, 0.3333 } } };

	public Airplane0Model(double dx, double dy, double dz, double dxf, double dyf, double dzf, double dxr, double dyr,
			double dzr, double dxRoof, double dyRoof, double dzRoof, double dxBottom, double dyBottom, double dzBottom,
			double rearOverhang, double frontOverhang, double rearOverhang1, double frontOverhang1,
			double wheelRadius, double wheelWidth,int wheelRays) {
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

		this.wheelRadius = wheelRadius;
		this.wheelWidth = wheelWidth;
		this.wheelRays = wheelRays;

	}

	@Override
	public void initMesh() {

		pRear = mainRear;
		pFuselage=mainFuselage;
		pFront = mainFront;
		backNY = pRear.length;
		fuselageNY=pFuselage.length;
		frontNY = pFront.length;
		bodyNY = backNY + fuselageNY + frontNY;

		z0=dzBottom;

		this.dyRudder = dyRear *pRear[1][0][0];
		this.dzRudder = dzRear-dz*pRear[0][2][1];

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
		c = initDoubleArrayValues(tWheel = new int[1][4], c);

		points = new Vector<Point3D>();
		texturePoints = new Vector();

		buildBody();
		buildTextures();

		// faces
		int NF = (bodyNY - 1) * 4+1;// body
		NF += 12;// wings
		NF += 12;// tail wings
		NF += tailRudder.length * 2;// rudder
		int totWheelPolygon = wheelRays + 2 * (wheelRays - 2);
		int NUM_WHEEL_FACES = 3 * totWheelPolygon;

		faces = new int[NF+NUM_WHEEL_FACES][3][4];

		int counter = 0;
		counter = buildFaces(counter, bodyNY);

	}

	protected int buildFaces(int counter, int numy) {

		counter = buildCabinfaces(counter, numy);
		counter = buildWingFaces(counter);
		counter = buildTailFaces(counter);
		counter = buildWheelsFaces(counter);
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

	private int buildWheelsFaces(int counter) {

		int totWheelPolygon = wheelRays + 2 * (wheelRays - 2);

		if(tWheel!=null){
			int[][][] wFaces = buildWheelFaces(wheelFront, tWheel[0]);
			for (int i = 0; i < totWheelPolygon; i++) {
				faces[counter++] = wFaces[i];
			}
			wFaces = buildWheelFaces(wheelLeftRear, tWheel[0]);
			for (int i = 0; i < totWheelPolygon; i++) {
				faces[counter++] = wFaces[i];
			}
			wFaces = buildWheelFaces(wheelRightRear, tWheel[0]);
			for (int i = 0; i < totWheelPolygon; i++) {
				faces[counter++] = wFaces[i];
			}
		}
		return counter;
	}

	protected int buildCabinfaces(int counter, int numy) {

		faces[counter++] = buildFace(Renderer3D.CAR_BACK, body[0][0][0], body[1][0][0], body[1][0][1],
				body[0][0][1], tBo[0]);

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
		buildWheels();

	}


	protected void buildCabin() {

		body = new BPoint[bodyNX][bodyNY][bodyNZ];

		Segments b0 = new Segments(x0, dx, y0, dyRear, z0, dz);
		for (int j = 0; j < pRear.length; j++) {

			double yy = pRear[j][0][0];
			double xx = pRear[j][1][0];
			double zz0 = pRear[j][2][0];
			double zz1 = pRear[j][2][1];

			body[0][j][0] = addBPoint(-0.5 * xx, yy, zz0, b0);
			body[1][j][0] = addBPoint(0.5 * xx, yy, zz0, b0);
			body[0][j][1] = addBPoint(-0.5 * xx, yy, zz1, b0);
			body[1][j][1] = addBPoint(0.5 * xx, yy, zz1, b0);

		}

		for (int j = 0; j < fuselageNY; j++) {
			Segments p0 = new Segments(x0, dx, y0+dyRear, dy, z0, dz);

			double yy = pFuselage[j][0][0];
			double xx = pFuselage[j][1][0];
			double zz0 = pFuselage[j][2][0];
			double zz1 = pFuselage[j][2][1];

			body[0][backNY+j][0] = addBPoint(-0.5 * xx, yy, zz0, p0);
			body[1][backNY+j][0] = addBPoint(0.5 * xx, yy, zz0, p0);
			body[0][backNY+j][1] = addBPoint(-0.5 * xx, yy, zz1, p0);
			body[1][backNY+j][1] = addBPoint(0.5 * xx, yy, zz1, p0);
		}

		Segments f0 = new Segments(x0, dx, y0+dyRear+dy, dyFront, z0, dz);
		for (int j = 0; j < pFront.length; j++) {

			double yy = pFront[j][0][0];
			double xx = pFront[j][1][0];
			double zz0 = pFront[j][2][0];
			double zz1 = pFront[j][2][1];

			if(j==pFront.length-1){
				body[0][backNY + fuselageNY + j][0] = addBPoint(0.0, yy, zz0, f0);
				body[1][backNY + fuselageNY + j][0] = body[0][backNY + fuselageNY + j][0];
				body[0][backNY + fuselageNY + j][1] = body[0][backNY + fuselageNY + j][0];
				body[1][backNY + fuselageNY + j][1] = body[0][backNY + fuselageNY + j][0];
			}else{
				body[0][backNY + fuselageNY+j][0] = addBPoint(-0.5*xx, yy, zz0, f0);
				body[1][backNY + fuselageNY+j][0] = addBPoint(0.5*xx, yy, zz0, f0);
				body[0][backNY + fuselageNY+j][1] = addBPoint(-0.5*xx, yy, zz1, f0);
				body[1][backNY + fuselageNY+j][1] = addBPoint(0.5*xx, yy, zz1, f0);
			}

		}
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


	protected void buildWheels() {
		if(tWheel!=null){
			wheelFront = buildWheel(-dxBottom * 0.5 - wheelWidth, dyBottom * 0.5, wheelRadius,
					wheelRadius, wheelWidth, wheelRays);
			wheelLeftRear = buildWheel(-dxBottom * 0.5 - wheelWidth, dyBottom * 0.5, wheelRadius,
					wheelRadius, wheelWidth, wheelRays);
			wheelRightRear = buildWheel(-dxBottom * 0.5 - wheelWidth, dyBottom * 0.5, wheelRadius,
					wheelRadius, wheelWidth, wheelRays);
		}
	}

	protected void buildTail() {

		double back_width = dxRear;
		double zzBack0=dz*pRear[0][2][0];
		double zzBack1=dz*pRear[0][2][1];
		double back_height = (zzBack1-zzBack0);

		double twDX = 65;

		tailRightWing = new BPoint[tailWingNX][tailWingNY][tailWingNZ];

		Segments trWing0 = new Segments(0, twDX, 0, dyRear * 0.125,  zzBack0, back_height);

		tailRightWing[0][0][0] = body[1][0][0];
		tailRightWing[1][0][0] = addBPoint(1.0, 0.0, 0.0, trWing0);
		tailRightWing[0][0][1] = body[1][0][1];
		tailRightWing[1][0][1] = addBPoint(1.0, 0.0, 1.0, trWing0);

		tailRightWing[0][1][0] = body[1][1][0];
		tailRightWing[1][1][0] = addBPoint(1.0, 1.0, 0, trWing0);
		tailRightWing[0][1][1] = body[1][1][1];
		tailRightWing[1][1][1] = addBPoint(1.0, 1.0, 1.0, trWing0);

		tailLeftWing = new BPoint[tailWingNX][tailWingNY][tailWingNZ];

		Segments tlWing0 = new Segments(0, twDX, 0, dyRear * 0.125, zzBack0, back_height);

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
		x += dz+dzBottom + dxRoof;
		buildTopTextures(x + dx * 0.5, y, shift);
		x += dx + dxRoof;
		buildRightTextures(x, y, shift);
		x += dz+dzBottom;
		buildRudderTextures(x, y);
		x += 2 * dyRudder;

		if(tWheel!=null){
			addTRect(x, y,wheelWidth , wheelWidth);
			x+=wheelWidth;
		}

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

		if(frontCabin!=null){
			for (int i = 0; i < frontNY - 1; i++) {
				addTPoint(x + frontCabin[0][i][0].z, y + frontCabin[0][i][0].y, 0);
				addTPoint(x + frontCabin[0][i][1].z, y + frontCabin[0][i][1].y, 0);
				addTPoint(x + frontCabin[0][i + 1][1].z, y + frontCabin[0][i + 1][1].y, 0);
				addTPoint(x + frontCabin[0][i + 1][0].z, y + frontCabin[0][i + 1][0].y, 0);
			}
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

		if(frontCabin!=null){
			for (int i = 0; i < frontNY - 1; i++) {
				addTPoint(x + frontCabin[0][i][0].x, y + frontCabin[0][i][0].y, 0);
				addTPoint(x + frontCabin[1][i][0].x, y + frontCabin[1][i][0].y, 0);
				addTPoint(x + frontCabin[1][i + 1][0].x, y + frontCabin[1][i + 1][0].y, 0);
				addTPoint(x + frontCabin[0][i + 1][0].x, y + frontCabin[0][i + 1][0].y, 0);
			}
		}
	}

	protected void buildRightTextures(double x, double y, int shift) {

		double maxDZ = Math.max(dz, Math.max(dz, dzFront))+dzBottom;

		for (int i = 0; i < bodyNY - 1; i++) {
			addTPoint(x + maxDZ - body[1][i][0].z, y + body[1][i][0].y, 0);
			addTPoint(x + maxDZ - body[1][i][1].z, y + body[1][i][1].y, 0);
			addTPoint(x + maxDZ - body[1][i + 1][1].z, y + body[1][i + 1][1].y, 0);
			addTPoint(x + maxDZ - body[1][i + 1][0].z, y + body[1][i + 1][0].y, 0);
		}

		if(frontCabin!=null){
			for (int i = 0; i < frontNY - 1; i++) {
				addTPoint(x + maxDZ - frontCabin[1][i][0].z, y + frontCabin[1][i][0].y, 0);
				addTPoint(x + maxDZ - frontCabin[1][i][1].z, y + frontCabin[1][i][1].y, 0);
				addTPoint(x + maxDZ - frontCabin[1][i + 1][1].z, y + frontCabin[1][i + 1][1].y, 0);
				addTPoint(x + maxDZ - frontCabin[1][i + 1][0].z, y + frontCabin[1][i + 1][0].y, 0);
			}
		}
	}

	protected void buildRudderTextures(double x, double y) {

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

		if(frontCabin!=null){
			for (int i = 0; i < frontNY - 1; i++) {
				printTexturePolygon(bufGraphics, tLeftFront[i]);
				printTexturePolygon(bufGraphics, tTopFront[i]);
				printTexturePolygon(bufGraphics, tRightFront[i]);
			}
		}

		if(tWheel!=null){
			printTexturePolygon(bufGraphics, tWheel[0]);
		}

	}

}
