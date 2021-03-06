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
public class Helicopter0Model extends Airplane0Model {

	private BPoint[][][][] propellers;
	private BPoint[][][][] shoes;
	private static final double PROPELLER_CENTER_Z_BODY = 1.43;
	private static final double PROPELLER_CENTER_Y_BODY = 0.56;

	public static String NAME = "Helicopter";

	private double[][][] heliRear = {
			{ { 0.00 }, { 0.07 }, { 1.18, 1.35} },
			{ { 0.17 }, { 0.11 }, { 0.65, 1.13} },
			{ { 0.28 }, { 0.13 }, { 0.55, 0.80} },
			{ { 0.5 }, { 0.18 }, { 0.43, 0.75} },
			{ { 0.73 }, { 0.24 }, { 0.33, 0.73} }
	};

	private double[][][] heliFuselage = {
			{ { 0.00 }, { 0.4 }, { 0.18, 0.65} },
			{ { 0.06 }, { 0.51 }, { 0.13, 0.75} },
			{ { 0.21 }, { 0.76 }, { 0.05, 1.000} },
			{ { 0.32 }, { 0.89 }, { 0.0, 1.000} },
			{ { 0.71 }, { 1.0 }, { 0.0, 1.000} },
			{ { 0.82 }, { 1.0 }, { 0.0, 0.83} },
			{ { 1.0 }, { 0.93 }, { 0.0, 0.83} },
	};

	private double[][][] heliFront = {
			{ { 0.36 }, { 0.89 }, { 0.0, 0.83 } },
			{ { 0.7 }, { 0.76 }, { 0.0, 0.50 } },
			{ { 0.88 }, { 0.58 }, { 0.05, 0.43 } },
			{ { 1.0 }, { 0.0 }, { 0.25, 0.25 } },
	};

	public Helicopter0Model(double dx, double dy, double dz, double dxf, double dyf, double dzf, double dxr, double dyr,
			double dzr, double dxRoof, double dyRoof, double dzRoof, double dxBottom, double dyBottom, double dzBottom,
			double rearOverhang, double frontOverhang, double rearOverhang1, double frontOverhang1,
			double wheelRadius, double wheelWidth,int wheelRays) {
		super( dx,  dy,  dz,  dxf,  dyf,  dzf,  dxr,  dyr,
				dzr,  dxRoof,  dyRoof,  dzRoof,  dxBottom,  dyBottom,  dzBottom,
				rearOverhang,  frontOverhang,  rearOverhang1,  frontOverhang1,
				wheelRadius, wheelWidth,wheelRays);


	}

	@Override
	public void initMesh() {

		pRear = heliRear;
		pFuselage=heliFuselage;
		pFront = heliFront;
		backNY = pRear.length;
		fuselageNY=pFuselage.length;
		frontNY = pFront.length;
		bodyNY = backNY + fuselageNY + frontNY;

		z0=dzBottom;

		int c = 0;
		c = initDoubleArrayValues(tBo = new int[1][4], c);
		c = initDoubleArrayValues(tLeftBody = new int[bodyNY - 1][4], c);
		c = initDoubleArrayValues(tTopBody = new int[bodyNY - 1][4], c);
		c = initDoubleArrayValues(tRightBody = new int[bodyNY - 1][4], c);

		points = new Vector<Point3D>();
		texturePoints = new Vector<Point3D>();

		buildBody();
		buildTextures();

		// faces
		int NF = (bodyNY - 1) * 4+1;// body
		// propellers
		NF += 6 * propellers.length;
		// shoes
		NF += 6 * shoes.length;

		faces = new int[NF][3][4];

		int counter = 0;
		counter = buildCabinfaces(counter, bodyNY);
		counter = buildPropellersFaces(counter);
		counter = buildShoesFaces(counter);
	}

	@Override
	protected void buildBody() {
		buildCabin();
		buildPropellers();
		buildShoes();

	}

	@Override
	protected void buildTextures() {
		// Texture points

		double maxDY = Math.max(dyTexture, dy + dyRear + dyFront);
		double maxDZ = Math.max(dz,dzFront);

		int shift = 1;

		double y = by;
		double x = bx;

		addTRect(x, y, dxTexture, dyTexture);
		x += dxTexture;
		buildLefTextures(x, y, shift);
		x += maxDZ + dzBottom;
		buildTopTextures(x + dx * 0.5, y, shift);
		x += dx;
		buildRightTextures(x, y, shift);
		x += maxDZ+dzBottom;

		IMG_WIDTH = (int) (bx + x);
		IMG_HEIGHT = (int) (2 * by + maxDY);

	}

	private int buildPropellersFaces(int counter) {
		for (int i = 0; i < propellers.length; i++) {

			BPoint[][][] prop = propellers[i];
			faces[counter++] = buildFace(Renderer3D.CAR_TOP, prop[0][0][1], prop[1][0][1], prop[1][1][1], prop[0][1][1],
					tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, prop[0][0][0], prop[0][0][1], prop[0][1][1],
					prop[0][1][0], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, prop[1][0][0], prop[1][1][0], prop[1][1][1],
					prop[1][0][1], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_FRONT, prop[0][1][0], prop[0][1][1], prop[1][1][1],
					prop[1][1][0], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_BACK, prop[0][0][0], prop[1][0][0], prop[1][0][1],
					prop[0][0][1], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, prop[0][0][0], prop[0][1][0], prop[1][1][0],
					prop[1][0][0], tBo[0]);

		}
		return counter;
	}

	private void buildPropellers() {

		propellers = new BPoint[2][2][2][2];

		Segments s0 = new Segments(0, dxRoof, dyRear + dy * PROPELLER_CENTER_Y_BODY, dyRoof * 0.5, dz*PROPELLER_CENTER_Z_BODY + dzBottom, dzRoof);

		propellers[0][0][0][0] = addBPoint(-1.0, -1.0, 0, s0);
		propellers[0][1][0][0] = addBPoint(1.0, -1.0, 0, s0);
		propellers[0][0][1][0] = addBPoint(-1.0, 1.0, 0, s0);
		propellers[0][1][1][0] = addBPoint(1.0, 1.0, 0, s0);

		propellers[0][0][0][1] = addBPoint(-1.0, -1.0, 1.0, s0);
		propellers[0][1][0][1] = addBPoint(1.0, -1.0, 1.0, s0);
		propellers[0][0][1][1] = addBPoint(-1.0, 1.0, 1.0, s0);
		propellers[0][1][1][1] = addBPoint(1.0, 1.0, 1.0, s0);

		Segments s1 = new Segments(0, dyRoof * 0.5, dyRear + dy * PROPELLER_CENTER_Y_BODY, dxRoof, dz*PROPELLER_CENTER_Z_BODY  + dzBottom, dzRoof);

		propellers[1][0][0][0] = addBPoint(-1.0, -1.0, 0, s1);
		propellers[1][1][0][0] = addBPoint(1.0, -1.0, 0, s1);
		propellers[1][0][1][0] = addBPoint(-1.0, 1.0, 0, s1);
		propellers[1][1][1][0] = addBPoint(1.0, 1.0, 0, s1);

		propellers[1][0][0][1] = addBPoint(-1.0, -1.0, 1.0, s1);
		propellers[1][1][0][1] = addBPoint(1.0, -1.0, 1.0, s1);
		propellers[1][0][1][1] = addBPoint(-1.0, 1.0, 1.0, s1);
		propellers[1][1][1][1] = addBPoint(1.0, 1.0, 1.0, s1);

	}

	private int buildShoesFaces(int counter) {
		for (int i = 0; i < shoes.length; i++) {

			BPoint[][][] shoe = shoes[i];
			faces[counter++] = buildFace(Renderer3D.CAR_TOP, shoe[0][0][1], shoe[1][0][1], shoe[1][1][1], shoe[0][1][1],
					tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, shoe[0][0][0], shoe[0][0][1], shoe[0][1][1],
					shoe[0][1][0], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, shoe[1][0][0], shoe[1][1][0], shoe[1][1][1],
					shoe[1][0][1], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_FRONT, shoe[0][1][0], shoe[0][1][1], shoe[1][1][1],
					shoe[1][1][0], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_BACK, shoe[0][0][0], shoe[1][0][0], shoe[1][0][1],
					shoe[0][0][1], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, shoe[0][0][0], shoe[0][1][0], shoe[1][1][0],
					shoe[1][0][0], tBo[0]);

		}
		return counter;
	}

	private void buildShoes() {
		shoes = new BPoint[2][2][2][2];

		Segments s0 = new Segments(-dx * 0.5 - dxBottom, dxBottom, dyRear, dyBottom, 0, dxBottom);

		shoes[0][0][0][0] = addBPoint(-1.0, 0.0, 0, s0);
		shoes[0][1][0][0] = addBPoint(1.0, 0.0, 0, s0);
		shoes[0][0][1][0] = addBPoint(-1.0, 1.0, 0, s0);
		shoes[0][1][1][0] = addBPoint(1.0, 1.0, 0, s0);

		shoes[0][0][0][1] = addBPoint(-1.0, 0.0, 1.0, s0);
		shoes[0][1][0][1] = addBPoint(1.0, 0.0, 1.0, s0);
		shoes[0][0][1][1] = addBPoint(-1.0, 1.0, 1.0, s0);
		shoes[0][1][1][1] = addBPoint(1.0, 1.0, 1.0, s0);

		Segments s1 = new Segments(dx * 0.5, dxBottom, dyRear, dyBottom, 0, dxBottom);

		shoes[1][0][0][0] = addBPoint(-1.0, 0.0, 0, s1);
		shoes[1][1][0][0] = addBPoint(1.0, 0.0, 0, s1);
		shoes[1][0][1][0] = addBPoint(-1.0, 1.0, 0, s1);
		shoes[1][1][1][0] = addBPoint(1.0, 1.0, 0, s1);

		shoes[1][0][0][1] = addBPoint(-1.0, 0.0, 1.0, s1);
		shoes[1][1][0][1] = addBPoint(1.0, 0.0, 1.0, s1);
		shoes[1][0][1][1] = addBPoint(-1.0, 1.0, 1.0, s1);
		shoes[1][1][1][1] = addBPoint(1.0, 1.0, 1.0, s1);

	}

	@Override
	protected void buildTopTextures(double x, double y, int shift) {

		for (int i = 0; i < bodyNY - 1; i++) {
			addTPoint(x + body[0][i][0].x, y + body[0][i][0].y, 0);
			addTPoint(x + body[1][i][0].x, y + body[1][i][0].y, 0);
			addTPoint(x + body[1][i + 1][0].x, y + body[1][i + 1][0].y, 0);
			addTPoint(x + body[0][i + 1][0].x, y + body[0][i + 1][0].y, 0);
		}
	}

	@Override
	public void printTexture(Graphics2D bufGraphics) {

		Color bodyColor = new Color(96, 132, 42);
		Color topBodyColor = new Color(96, 132, 42);

		bufGraphics.setStroke(new BasicStroke(0.1f));

		bufGraphics.setColor(bodyColor);
		printTexturePolygon(bufGraphics, tBo[0]);

		bufGraphics.setColor(bodyColor);
		for (int i = 0; i < tLeftBody.length; i++) {
			printTexturePolygon(bufGraphics, tLeftBody[i]);
		}

		bufGraphics.setColor(topBodyColor);
		for (int i = 0; i < tTopBody.length; i++) {
			printTexturePolygon(bufGraphics, tTopBody[i]);
		}

		bufGraphics.setColor(bodyColor);
		for (int i = 0; i < tRightBody.length; i++) {
			printTexturePolygon(bufGraphics, tRightBody[i]);
		}

	}

}
