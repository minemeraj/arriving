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

	private double[][][] mainRear = {
			{ { 0.00 }, { 1.372 }, { 0.0, 0.718} },
			{ { 0.25 }, { 1.2 }, { 0.0, 0.8 } },
			{ { 0.50 }, { 1.1 }, { 0.0, 0.9} },
			{ { 0.75 }, { 1.0 }, { 0.0, 1.0 } },
	};

	private double[][][] mainFuselage = {
			{ { 0.00 }, { 1.0 }, { 0.0, 1.000} },
			{ { 1.00 }, { 1.0 }, { 0.0, 1.000} },
	};

	double[][][] fighterFront = {
			{ { 0.00 }, { 1.0 }, { 0.0000, 0.42 }},
			{ { 0.18 }, { 1.0 }, { 0.0000, 0.55 }},
			{ { 0.50 }, { 1.0 }, { 0.0000, 0.81 }},
			{ { 0.62 }, { 1.0 }, { 0.0000, 0.97}},
			{ { 0.65 }, { 1.0 }, { 0.0000, 0.98 }},
			{ { 0.68 }, { 1.0 }, { 0.0000, 1.0 }},
			{ { 0.73 }, { 1.0 }, { 0.0000, 0.91 }},
			{ { 0.79 }, { 0.93 }, { 0.0000, 0.69 }},
			{ { 0.85 }, { 0.80 }, { 0.0000, 0.56 }},
			{ { 0.91 }, { 0.58 }, { 0.03, 0.44 }},
			{ { 0.95 }, { 0.38 }, { 0.08, 0.33 }},
			{ { 0.98 }, { 0.18 }, { 0.13, 0.27 }},
			{ { 1.00 }, { 0.0 }, { 0.17, 0.17 }},
	};




	public FighterAircraft0Model(double dx, double dy, double dz, double dxf, double dyf, double dzf, double dxr,
			double dyr, double dzr, double dxRoof, double dyRoof, double dzRoof, double dxBottom, double dyBottom,
			double dzBottom, double rearOverhang, double frontOverhang, double rearOverhang1, double frontOverhang1,
			double wheelRadius, double wheelWidth,int wheelRays) {
		super(dx, dy, dz, dxf, dyf, dzf, dxr, dyr, dzr, dxRoof, dyRoof, dzRoof, dxBottom, dyBottom, dzBottom,
				rearOverhang, frontOverhang, rearOverhang1, frontOverhang1,
				wheelRadius, wheelWidth,wheelRays);

	}

	@Override
	public void initMesh() {

		pRear = mainRear;
		pFront = fighterFront;
		pFuselage=mainFuselage;
		fuselageNY=pFuselage.length;
		backNY = pRear.length;
		frontNY = pFront.length;
		bodyNY = backNY + fuselageNY;

		this.dyRudder = dyRear *pRear[1][0][0];
		this.dzRudder = dzRear-dz*pRear[0][2][1];

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
		NF+=4*(frontNY-1)+1;
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
		x += maxDZ+dzBottom;
		buildRudderTextures(x, y);
		x += 2 * dyRudder;

		IMG_WIDTH = (int) (bx + x);
		IMG_HEIGHT = (int) (2 * by + maxDY);

	}

	@Override
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
		return counter;
	}

	@Override
	protected void buildTail() {

		/////// tail

		double back_width = dxRear;
		double back_height = dz*pRear[0][2][1];
		double zzBack0=dz*pRear[0][2][0];

		double twDX = 65;

		tailRightWing = new BPoint[tailWingNX][tailWingNY][tailWingNZ];

		Segments trWing0 = new Segments(back_width * 0.5, twDX, 0, dyRear * 0.125, zzBack0, back_height);

		tailRightWing[0][0][0] = body[1][0][0];
		tailRightWing[1][0][0] = addBPoint(1.0, 0.0, 0.0, trWing0);
		tailRightWing[0][0][1] = body[1][0][1];
		tailRightWing[1][0][1] = addBPoint(1.0, 0.0, 1.0, trWing0);

		tailRightWing[0][1][0] = body[1][1][0];
		tailRightWing[1][1][0] = addBPoint(1.0, 1.0, 0, trWing0);
		tailRightWing[0][1][1] = body[1][1][1];
		tailRightWing[1][1][1] = addBPoint(1.0, 1.0, 1.0, trWing0);

		tailLeftWing = new BPoint[tailWingNX][tailWingNY][tailWingNZ];

		Segments tlWing0 = new Segments(-back_width * 0.5, twDX, 0, dyRear * 0.125, zzBack0, back_height);

		tailLeftWing[0][0][0] = addBPoint(-1.0, 0, 0, tlWing0);
		tailLeftWing[1][0][0] = body[0][0][0];
		tailLeftWing[0][0][1] = addBPoint(-1.0, 0.0, 1.0, tlWing0);
		tailLeftWing[1][0][1] = body[0][0][1];

		tailLeftWing[0][1][0] = addBPoint(-1.0, 1.0, 0, tlWing0);
		tailLeftWing[1][1][0] = body[0][1][0];
		tailLeftWing[0][1][1] = addBPoint(-1.0, 1.0, 1.0, tlWing0);
		tailLeftWing[1][1][1] = body[0][1][1];

		tailRudder = new BPoint[2][tailRudderNX][tailRudderNY][tailRudderNZ];

		Segments rudder0 = new Segments(0, back_width, 0, dyRudder, back_height, dzRudder);

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
