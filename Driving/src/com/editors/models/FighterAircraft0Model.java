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
public class FighterAircraft0Model extends Airplane0Model {

	public static final String NAME = "Fighter aircraft";

	double[][][] fighterFront = {
			{ { 0.00 }, { 0.9000 }, { 0.0000, 1.0 } },
			{ { 0.39 }, { 0.9000 }, { 0.0667, 0.9333 } },
			{ { 0.63 }, { 0.7667 }, { 0.1000, 0.7667 } },
			{ { 0.76 }, { 0.6000 }, { 0.1333, 0.6000 } },
			{ { 0.90 }, { 0.3333 }, { 0.2000, 0.5000 } },
			{ { 1.00 }, { 0.0000 }, { 0.3333, 0.3333 } } };

	private double[][][] mainFuselage = {
			{ { 0.00 }, { 1.0 }, { 0.0, 1.000} },
			{ { 1.00 }, { 1.0 }, { 0.0, 1.000} },
	};

	public FighterAircraft0Model(double dx, double dy, double dz, double dxf, double dyf, double dzf, double dxr,
			double dyr, double dzr, double dxRoof, double dyRoof, double dzRoof, double dxBottom, double dyBottom,
			double dzBottom, double rearOverhang, double frontOverhang, double rearOverhang1, double frontOverhang1) {
		super(dx, dy, dz, dxf, dyf, dzf, dxr, dyr, dzr, dxRoof, dyRoof, dzRoof, dxBottom, dyBottom, dzBottom,
				rearOverhang, frontOverhang, rearOverhang1, frontOverhang1);

	}

	@Override
	public void initMesh() {

		pFront = fighterFront;
		pFuselage=mainFuselage;
		fuselageNY=pFuselage.length;
		backNY = 4;
		frontNY = pFront.length;
		bodyNY = backNY + fuselageNY;

		int c = 0;
		c = initDoubleArrayValues(tBo = new int[1][4], c);
		c = initDoubleArrayValues(tLeftBody = new int[bodyNY - 1][4], c);
		c = initSingleArrayValues(tLeftTail = new int[4], c);
		c = initSingleArrayValues(tLeftWing = new int[4], c);
		c = initDoubleArrayValues(tLeftFront = new int[frontNY - 1][4], c);
		c = initDoubleArrayValues(tTopBody = new int[bodyNY - 1][4], c);
		c = initSingleArrayValues(tRightWing = new int[4], c);
		c = initSingleArrayValues(tRightTail = new int[4], c);
		c = initDoubleArrayValues(tTopFront = new int[frontNY - 1][4], c);
		c = initDoubleArrayValues(tRightBody = new int[bodyNY - 1][4], c);
		c = initDoubleArrayValues(tRightFront = new int[frontNY - 1][4], c);
		c = initDoubleArrayValues(tRudder = new int[2][4], c);

		points = new Vector<Point3D>();
		texturePoints = new Vector();

		buildBody();
		buildTextures();

		// faces
		// body
		int NF = (bodyNY - 1) * 4 + 1;
		// wings
		NF += 12;
		// tail wings
		NF += 12;
		//front
		NF+=4*(frontNY-1)+2;
		NF += tailRudder.length * 2;// rudder
		faces = new int[NF][3][4];

		int counter = 0;
		counter = buildFaces(counter, bodyNY);

	}

	@Override
	protected void buildTextures() {
		// Texture points

		double maxDY = Math.max(dyTexture, dyRear + dyFront);
		double maxDZ = Math.max(dz,dzFront);

		int shift = 1;

		double y = by;
		double x = bx;

		addTRect(x, y, dxTexture, dyTexture);
		x += dxTexture;
		buildLefTextures(x, y, shift);
		x += maxDZ + dxRoof;
		buildTopTextures(x + dx * 0.5, y, shift);
		x += dx + dxRoof;
		buildRightTextures(x, y, shift);
		x += maxDZ;
		buildRudderTextures(x, y);
		x += 2 * dyRudder;

		IMG_WIDTH = (int) (bx + x);
		IMG_HEIGHT = (int) (2 * by + maxDY);

	}

	@Override
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

		body[0][0][0] = addBPoint(-0.5, 0.0, 0, b0);
		body[1][0][0] = addBPoint(0.5, 0.0, 0, b0);
		body[0][0][1] = addBPoint(-0.5, 0.0, 1.0, b0);
		body[1][0][1] = addBPoint(0.5, 0.0, 1.0, b0);

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

		frontCabin=new BPoint[2][frontNY][2];
		Segments f0 = new Segments(x0, dxFront, y0+dyRear, dyFront, z0, dzFront);
		for (int j = 0; j < pFront.length; j++) {

			double yy = pFront[j][0][0];
			double xx = pFront[j][1][0];
			double zz0 = pFront[j][2][0];
			double zz1 = pFront[j][2][1];

			if(j==pFront.length-1){
				frontCabin[0][j][0] = addBPoint(0.0, yy, zz0, f0);
				frontCabin[1][j][0] = frontCabin[0][j][0];
				frontCabin[0][j][1] = frontCabin[0][j][0];
				frontCabin[1][j][1] = frontCabin[0][j][0];
			}else{
				frontCabin[0][j][0] = addBPoint(-0.5*xx, yy, zz0, f0);
				frontCabin[1][j][0] = addBPoint(0.5*xx, yy, zz0, f0);
				frontCabin[0][j][1] = addBPoint(-0.5*xx, yy, zz1, f0);
				frontCabin[1][j][1] = addBPoint(0.5*xx, yy, zz1, f0);
			}

		}

	}

	@Override
	protected int buildCabinfaces(int counter, int numy) {

		counter = super.buildCabinfaces(counter, numy);
		counter=buildFrontCabinFaces(counter);
		return counter;
	}

	private int buildFrontCabinFaces(int counter) {

		faces[counter++] = buildFace(Renderer3D.CAR_BACK, frontCabin[0][0][0], frontCabin[1][0][0], frontCabin[1][0][1],
				frontCabin[0][0][1], tBo[0]);

		for (int k = 0; k < frontNY - 1; k++) {
			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, frontCabin[0][k][0], frontCabin[0][k][1], frontCabin[0][k + 1][1],
					frontCabin[0][k + 1][0], tLeftFront[k]);
			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, frontCabin[0][k][0], frontCabin[0][k + 1][0], frontCabin[1][k + 1][0],
					frontCabin[1][k][0], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, frontCabin[1][k][0], frontCabin[1][k + 1][0], frontCabin[1][k + 1][1],
					frontCabin[1][k][1], tRightFront[k]);
			faces[counter++] = buildFace(Renderer3D.CAR_TOP, frontCabin[0][k][1], frontCabin[1][k][1], frontCabin[1][k + 1][1],
					frontCabin[0][k + 1][1], tTopFront[k]);
		}

		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, frontCabin[0][frontNY - 1][0],frontCabin[0][frontNY - 1][1], frontCabin[1][frontNY - 1][1],
				frontCabin[1][frontNY - 1][0],
				tBo[0]);
		return counter;
	}

	@Override
	protected void buildTail() {

		/////// tail

		double back_width = dxRear;
		double back_height = dzRear;

		double twDX = 65;

		tailRightWing = new BPoint[tailWingNX][tailWingNY][tailWingNZ];

		Segments trWing0 = new Segments(back_width * 0.5, twDX, 0, dyRear * 0.125, dz - back_height, back_height);

		tailRightWing[0][0][0] = body[1][0][0];
		tailRightWing[1][0][0] = addBPoint(1.0, 0.0, 0.0, trWing0);
		tailRightWing[0][0][1] = body[1][0][1];
		tailRightWing[1][0][1] = addBPoint(1.0, 0.0, 1.0, trWing0);

		tailRightWing[0][1][0] = body[1][1][0];
		tailRightWing[1][1][0] = addBPoint(1.0, 1.0, 0, trWing0);
		tailRightWing[0][1][1] = body[1][1][1];
		tailRightWing[1][1][1] = addBPoint(1.0, 1.0, 1.0, trWing0);

		tailLeftWing = new BPoint[tailWingNX][tailWingNY][tailWingNZ];

		Segments tlWing0 = new Segments(-back_width * 0.5, twDX, 0, dyRear * 0.125, dz - back_height, back_height);

		tailLeftWing[0][0][0] = addBPoint(-1.0, 0, 0, tlWing0);
		tailLeftWing[1][0][0] = body[0][0][0];
		tailLeftWing[0][0][1] = addBPoint(-1.0, 0.0, 1.0, tlWing0);
		tailLeftWing[1][0][1] = body[0][0][1];

		tailLeftWing[0][1][0] = addBPoint(-1.0, 1.0, 0, tlWing0);
		tailLeftWing[1][1][0] = body[0][1][0];
		tailLeftWing[0][1][1] = addBPoint(-1.0, 1.0, 1.0, tlWing0);
		tailLeftWing[1][1][1] = body[0][1][1];

		tailRudder = new BPoint[2][tailRudderNX][tailRudderNY][tailRudderNZ];

		Segments rudder0 = new Segments(0, back_width, 0, dyRudder, dz - back_height, dzRudder);

		tailRudder[0][0][0][0] = addBPoint(body[0][0][1].x, body[0][0][1].y, body[0][0][1].z);
		tailRudder[0][0][1][0] = addBPoint(body[0][0][1].x, body[0][1][1].y, body[0][1][1].z);
		tailRudder[0][0][0][1] = addBPoint(-0.5, 0.0, 1.0, rudder0);
		tailRudder[0][0][1][1] = addBPoint(-0.5, 1.0, 1.0, rudder0);

		tailRudder[1][0][0][0] = addBPoint(body[1][0][1].x, body[0][0][1].y, body[0][0][1].z);
		tailRudder[1][0][1][0] = addBPoint(body[1][0][1].x, body[0][1][1].y, body[0][1][1].z);
		tailRudder[1][0][0][1] = addBPoint(+0.5, 0.0, 1.0, rudder0);
		tailRudder[1][0][1][1] = addBPoint(+0.5, 1.0, 1.0, rudder0);

	}

}
