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
public class Helicopter0Model extends VehicleModel {

	// body textures
	protected int[][] tBo = null;

	private double dxTexture = 50;
	private double dyTexture = 50;

	private BPoint[][][] body;
	private BPoint[][][][] propellers;
	private BPoint[][][][] shoes;

	protected int backNY = 4;
	protected int fuselageNY = 2;
	protected int frontNY = 4;
	protected int bodyNX = 2;
	protected int bodyNY = backNY + fuselageNY + frontNY;
	protected int bodyNZ = 2;

	public static String NAME = "Helicopter";

	public Helicopter0Model(double dx, double dy, double dz, double dxf, double dyf, double dzf, double dxr, double dyr,
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

	}

	@Override
	public void initMesh() {

		int c = 0;
		c = initDoubleArrayValues(tBo = new int[1][4], c);

		points = new Vector<Point3D>();
		texturePoints = new Vector<Point3D>();

		buildBody();
		buildPropellers();
		buildShoes();
		buildTextures();

		// faces
		int NF = (bodyNY - 1) * 4;// body
		// propellers
		NF += 6 * propellers.length;
		// shoes
		NF += 6 * shoes.length;

		faces = new int[NF][3][4];

		int counter = 0;
		counter = buildCabinFaces(counter, bodyNY);
		counter = buildPropellersFaces(counter);
		counter = buildShoesFaces(counter);
	}

	private int buildCabinFaces(int counter, int numy) {

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

		return counter;
	}

	private void buildTextures() {

		// Texture points

		double y = by;
		double x = bx;

		addTRect(x, y, dxTexture, dyTexture);

		IMG_WIDTH = (int) (2 * bx + dxTexture);
		IMG_HEIGHT = (int) (2 * by + dyTexture);

	}

	private void buildBody() {

		body = new BPoint[bodyNX][bodyNY][bodyNZ];

		double back_width = dxRear;
		double back_width1 = dxRear * (1 - 0.3) + dx * 0.3;
		double back_width2 = dxRear * (1 - 0.6) + dx * 0.6;
		double back_width3 = dxRear * (1 - 0.9) + dx * 0.9;

		double back_height = dzRear;
		double back_height1 = dzRear * (1 - 0.3) + dz * 0.3;
		double back_height2 = dzRear * (1 - 0.6) + dz * 0.6;
		double back_height3 = dzRear * (1 - 0.9) + dz * 0.9;

		Segments b0 = new Segments(0, back_width, 0, dyRear, dz - dzRear + dzBottom, back_height);
		Segments b1 = new Segments(0, back_width1, 0, dyRear, dz - back_height1 + dzBottom, back_height1);
		Segments b2 = new Segments(0, back_width2, 0, dyRear, dz - back_height2 + dzBottom, back_height2);
		Segments b3 = new Segments(0, back_width3, 0, dyRear, dz - back_height3 + dzBottom, back_height3);

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

		Segments p0 = new Segments(0, dx, dyRear, dy, dzBottom, dz);

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

		Segments f0 = new Segments(0, front_width0, dyRear + dy, dyFront, dzBottom, front_height0);
		Segments f1 = new Segments(0, front_width1, dyRear + dy, dyFront, dzBottom, front_height1);
		Segments f2 = new Segments(0, front_width2, dyRear + dy, dyFront, dzBottom, front_height2);
		Segments f3 = new Segments(0, front_width, dyRear + dy, dyFront, dzBottom, dzFront);

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

		Segments s0 = new Segments(0, dxRoof, dyRear + dy * 0.5, dyRoof * 0.5, dz + dzBottom, dzRoof);

		propellers[0][0][0][0] = addBPoint(-1.0, -1.0, 0, s0);
		propellers[0][1][0][0] = addBPoint(1.0, -1.0, 0, s0);
		propellers[0][0][1][0] = addBPoint(-1.0, 1.0, 0, s0);
		propellers[0][1][1][0] = addBPoint(1.0, 1.0, 0, s0);

		propellers[0][0][0][1] = addBPoint(-1.0, -1.0, 1.0, s0);
		propellers[0][1][0][1] = addBPoint(1.0, -1.0, 1.0, s0);
		propellers[0][0][1][1] = addBPoint(-1.0, 1.0, 1.0, s0);
		propellers[0][1][1][1] = addBPoint(1.0, 1.0, 1.0, s0);

		Segments s1 = new Segments(0, dyRoof * 0.5, dyRear + dy * 0.5, dxRoof, dz + dzBottom, dzRoof);

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
	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);

	}

	@Override
	public void printTexture(Graphics2D bufGraphics) {

		bufGraphics.setColor(Color.BLACK);
		bufGraphics.setStroke(new BasicStroke(0.1f));

		for (int i = 0; i < faces.length; i++) {

			int[][] face = faces[i];
			int[] tPoints = face[2];
			if (tPoints.length == 4) {
				printTexturePolygon(bufGraphics, tPoints[0], tPoints[1], tPoints[2], tPoints[3]);
			} else if (tPoints.length == 3) {
				printTexturePolygon(bufGraphics, tPoints[0], tPoints[1], tPoints[2]);
			}

		}

	}

}
