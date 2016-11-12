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

	public FighterAircraft0Model(double dx, double dy, double dz, double dxf, double dyf, double dzf, double dxr,
			double dyr, double dzr, double dxRoof, double dyRoof, double dzRoof, double dxBottom, double dyBottom,
			double dzBottom, double rearOverhang, double frontOverhang, double rearOverhang1, double frontOverhang1) {
		super(dx, dy, dz, dxf, dyf, dzf, dxr, dyr, dzr, dxRoof, dyRoof, dzRoof, dxBottom, dyBottom, dzBottom,
				rearOverhang, frontOverhang, rearOverhang1, frontOverhang1);

	}

	@Override
	public void initMesh() {

		int c = 0;
		c = initDoubleArrayValues(tBo = new int[1][4], c);
		c = initSingleArrayValues(tLeftWing = new int[4], c);
		c = initDoubleArrayValues(tLeftBody = new int[bodyNY - 1][4], c);
		c = initDoubleArrayValues(tTopBody = new int[bodyNY - 1][4], c);
		c = initDoubleArrayValues(tRightBody = new int[bodyNY - 1][4], c);
		c = initSingleArrayValues(tRightWing = new int[4], c);

		points = new Vector<Point3D>();
		texturePoints = new Vector();

		buildBody();
		buildTextures();

		// faces
		int NF = (bodyNY - 1) * 4 + 1;// body
		NF += 12;// wings
		NF += 12;// tail wings
		NF += tailRudder.length * 2;// rudder
		faces = new int[NF][3][4];

		int counter = 0;
		counter = buildFaces(counter, bodyNY);

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

	@Override
	protected int buildCabinfaces(int counter, int numy) {

		for (int k = 0; k < numy - 1; k++) {
			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, body[0][k][0], body[0][k][1], body[0][k + 1][1],
					body[0][k + 1][0], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, body[0][k][0], body[0][k + 1][0], body[1][k + 1][0],
					body[1][k][0], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, body[1][k][0], body[1][k + 1][0], body[1][k + 1][1],
					body[1][k][1], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_TOP, body[0][k][1], body[1][k][1], body[1][k + 1][1],
					body[0][k + 1][1], tBo[0]);
		}

		faces[counter++] = buildFace(Renderer3D.CAR_BACK, body[0][0][0], body[1][0][0], body[1][0][1], body[0][0][1],
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

		Segments rudder0 = new Segments(0, back_width, 0, dyRear * 0.25, dz - back_height, back_height + 71);

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
