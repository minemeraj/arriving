package com.editors.models;

import java.util.Vector;

import com.Point3D;

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
		int NF = (bodyNY - 1) * 4 + 2;// body
		NF += 12;// wings
		NF += 12;// tail wings
		NF += 4;// rudder
		faces = new int[NF][3][4];

		int counter = 0;
		counter = buildFaces(counter, bodyNY);

	}

}
