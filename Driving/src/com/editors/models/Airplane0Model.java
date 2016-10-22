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

	int basePoints = 4;
	private BPoint[][][] body;
	private BPoint[][][] tailLeftWing;
	private BPoint[][][] tailRightWing;
	private BPoint[][][] tailRudder;
	private BPoint[][][] rightWing;
	private BPoint[][][] leftWing;

	public static final String NAME = "Airplane";

	public Airplane0Model(double dx, double dy, double dz, double dxf, double dyf, double dzf, double dxr, double dyr,
			double dzr, double dxRoof, double dyRoof, double dzRoof) {
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
	}

	@Override
	public void initMesh() {

		points = new Vector<Point3D>();
		texturePoints = new Vector();

		int pnx = 2;
		int bny = 4;
		int pny = 2;
		int fny = 4;
		int pnz = 2;

		int numy = bny + pny + fny;

		buildBody(pnx, bny, pny, fny, pnz, numy);
		buildTextures();

		// faces
		int NF = (numy - 1) * 4 + 2;// body
		NF += 12;// wings
		NF += 12;// tail wings
		NF += 4;// rudder
		faces = new int[NF][3][4];

		int counter = 0;
		counter = buildFaces(counter, numy);

	}

	private int buildFaces(int counter, int numy) {

		faces[counter++] = buildFace(Renderer3D.CAR_BACK, body[0][0][0], body[1][0][0], body[1][0][1], body[0][0][1], 0,
				1, 2, 3);
		for (int k = 0; k < numy - 1; k++) {

			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, body[0][k][0], body[0][k][1], body[0][k + 1][1],
					body[0][k + 1][0], 0, 1, 2, 3);
			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, body[0][k][0], body[0][k + 1][0], body[1][k + 1][0],
					body[1][k][0], 0, 1, 2, 3);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, body[1][k][0], body[1][k + 1][0], body[1][k + 1][1],
					body[1][k][1], 0, 1, 2, 3);
			faces[counter++] = buildFace(Renderer3D.CAR_TOP, body[0][k][1], body[1][k][1], body[1][k + 1][1],
					body[0][k + 1][1], 0, 1, 2, 3);

		}
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, body[0][numy - 1][0], body[0][numy - 1][1],
				body[1][numy - 1][1], body[1][numy - 1][0], 0, 1, 2, 3);

		faces[counter++] = buildFace(Renderer3D.CAR_BACK, rightWing[0][0][0], rightWing[1][0][0], rightWing[1][0][1],
				rightWing[0][0][1], 0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, rightWing[0][0][0], rightWing[0][0][1],
				rightWing[0][0 + 1][1], rightWing[0][0 + 1][0], 0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, rightWing[0][0][0], rightWing[0][0 + 1][0],
				rightWing[1][0 + 1][0], rightWing[1][0][0], 0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, rightWing[1][0][0], rightWing[1][0 + 1][0],
				rightWing[1][0 + 1][1], rightWing[1][0][1], 0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_TOP, rightWing[0][0][1], rightWing[1][0][1], rightWing[1][0 + 1][1],
				rightWing[0][0 + 1][1], 0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, rightWing[0][1][0], rightWing[0][1][1], rightWing[1][1][1],
				rightWing[1][1][0], 0, 1, 2, 3);

		faces[counter++] = buildFace(Renderer3D.CAR_BACK, leftWing[0][0][0], leftWing[1][0][0], leftWing[1][0][1],
				leftWing[0][0][1], 0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, leftWing[0][0][0], leftWing[0][0][1], leftWing[0][0 + 1][1],
				leftWing[0][0 + 1][0], 0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, leftWing[0][0][0], leftWing[0][0 + 1][0],
				leftWing[1][0 + 1][0], leftWing[1][0][0], 0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, leftWing[1][0][0], leftWing[1][0 + 1][0],
				leftWing[1][0 + 1][1], leftWing[1][0][1], 0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_TOP, leftWing[0][0][1], leftWing[1][0][1], leftWing[1][0 + 1][1],
				leftWing[0][0 + 1][1], 0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, leftWing[0][1][0], leftWing[0][1][1], leftWing[1][1][1],
				leftWing[1][1][0], 0, 1, 2, 3);

		faces[counter++] = buildFace(Renderer3D.CAR_BACK, tailRightWing[0][0][0], tailRightWing[1][0][0],
				tailRightWing[1][0][1], tailRightWing[0][0][1], 0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, tailRightWing[0][0][0], tailRightWing[0][0][1],
				tailRightWing[0][0 + 1][1], tailRightWing[0][0 + 1][0], 0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, tailRightWing[0][0][0], tailRightWing[0][0 + 1][0],
				tailRightWing[1][0 + 1][0], tailRightWing[1][0][0], 0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, tailRightWing[1][0][0], tailRightWing[1][0 + 1][0],
				tailRightWing[1][0 + 1][1], tailRightWing[1][0][1], 0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_TOP, tailRightWing[0][0][1], tailRightWing[1][0][1],
				tailRightWing[1][0 + 1][1], tailRightWing[0][0 + 1][1], 0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, tailRightWing[0][1][0], tailRightWing[0][1][1],
				tailRightWing[1][1][1], tailRightWing[1][1][0], 0, 1, 2, 3);

		faces[counter++] = buildFace(Renderer3D.CAR_BACK, tailLeftWing[0][0][0], tailLeftWing[1][0][0],
				tailLeftWing[1][0][1], tailLeftWing[0][0][1], 0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, tailLeftWing[0][0][0], tailLeftWing[0][0][1],
				tailLeftWing[0][0 + 1][1], tailLeftWing[0][0 + 1][0], 0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, tailLeftWing[0][0][0], tailLeftWing[0][0 + 1][0],
				tailLeftWing[1][0 + 1][0], tailLeftWing[1][0][0], 0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, tailLeftWing[1][0][0], tailLeftWing[1][0 + 1][0],
				tailLeftWing[1][0 + 1][1], tailLeftWing[1][0][1], 0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_TOP, tailLeftWing[0][0][1], tailLeftWing[1][0][1],
				tailLeftWing[1][0 + 1][1], tailLeftWing[0][0 + 1][1], 0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, tailLeftWing[0][1][0], tailLeftWing[0][1][1],
				tailLeftWing[1][1][1], tailLeftWing[1][1][0], 0, 1, 2, 3);

		faces[counter++] = buildFace(Renderer3D.CAR_BACK, tailRudder[0][0][0], tailRudder[1][0][0], tailRudder[0][0][1],
				0, 1, 2);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, tailRudder[0][0][0], tailRudder[0][0][1],
				tailRudder[0][0 + 1][1], tailRudder[0][0 + 1][0], 0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, tailRudder[1][0][0], tailRudder[0][0][1],
				tailRudder[0][0 + 1][1], tailRudder[1][1][0], 0, 1, 2, 3);
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, tailRudder[0][1][0], tailRudder[0][1][1],
				tailRudder[1][1][0], 0, 1, 2);

		return counter;
	}

	private void buildBody(int pnx, int bny, int pny, int fny, int pnz, int numy) {

		body = new BPoint[pnx][numy][pnz];

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

		body[0][bny][0] = addBPoint(-0.5, 0.0, 0, p0);
		body[1][bny][0] = addBPoint(0.5, 0.0, 0, p0);
		body[0][bny][1] = addBPoint(-0.5, 0.0, 1.0, p0);
		body[1][bny][1] = addBPoint(0.5, 0.0, 1.0, p0);

		body[0][bny + 1][0] = addBPoint(-0.5, 1.0, 0, p0);
		body[1][bny + 1][0] = addBPoint(0.5, 1.0, 0, p0);
		body[0][bny + 1][1] = addBPoint(-0.5, 1.0, 1.0, p0);
		body[1][bny + 1][1] = addBPoint(0.5, 1.0, 1.0, p0);

		double front_width = dxFront;
		double front_width0 = front_width * (1 - 0.75) + dx * 0.75;
		double front_width1 = front_width * (1 - 0.7) + dx * 0.7;
		double front_width2 = front_width * (1 - 0.65) + dx * 0.65;

		double front_height0 = dzFront * (1 - 0.75) + dz * 0.75;
		double front_height1 = dzFront * (1 - 0.7) + dz * 0.7;
		double front_height2 = dzFront * (1 - 0.65) + dz * 0.65;

		Segments f0 = new Segments(0, front_width0, dyRear + dy, dyRear, 0, front_height0);
		Segments f1 = new Segments(0, front_width1, dyRear + dy, dyRear, 0, front_height1);
		Segments f2 = new Segments(0, front_width2, dyRear + dy, dyRear, 0, front_height2);
		Segments f3 = new Segments(0, front_width, dyRear + dy, dyRear, 0, dzFront);

		body[0][bny + pny][0] = addBPoint(-0.5, 0.25, 0, f0);
		body[1][bny + pny][0] = addBPoint(0.5, 0.25, 0, f0);
		body[0][bny + pny][1] = addBPoint(-0.5, 0.25, 1.0, f0);
		body[1][bny + pny][1] = addBPoint(0.5, 0.25, 1.0, f0);

		body[0][bny + pny + 1][0] = addBPoint(-0.5, 0.5, 0, f1);
		body[1][bny + pny + 1][0] = addBPoint(0.5, 0.5, 0, f1);
		body[0][bny + pny + 1][1] = addBPoint(-0.5, 0.5, 1.0, f1);
		body[1][bny + pny + 1][1] = addBPoint(0.5, 0.5, 1.0, f1);

		body[0][bny + pny + 2][0] = addBPoint(-0.5, 0.75, 0, f2);
		body[1][bny + pny + 2][0] = addBPoint(0.5, 0.75, 0, f2);
		body[0][bny + pny + 2][1] = addBPoint(-0.5, 0.75, 1.0, f2);
		body[1][bny + pny + 2][1] = addBPoint(0.5, 0.75, 1.0, f2);

		body[0][bny + pny + 3][0] = addBPoint(-0.5, 1.0, 0, f3);
		body[1][bny + pny + 3][0] = addBPoint(0.5, 1.0, 0, f3);
		body[0][bny + pny + 3][1] = addBPoint(-0.5, 1.0, 1.0, f3);
		body[1][bny + pny + 3][1] = addBPoint(0.5, 1.0, 1.0, f3);

		/////// tail

		int twnx = 2;
		int twny = 2;
		int twnz = 2;

		double twDX = 65;

		tailRightWing = new BPoint[twnx][twny][twnz];

		Segments trWing0 = new Segments(0, twDX, 0, dyRear * 0.125, dz - back_height, back_height);

		tailRightWing[0][0][0] = body[1][0][0];
		tailRightWing[1][0][0] = addBPoint(1.0, 0.0, 0.0, trWing0);
		tailRightWing[0][0][1] = body[1][0][1];
		tailRightWing[1][0][1] = addBPoint(1.0, 0.0, 1.0, trWing0);

		tailRightWing[0][1][0] = body[1][1][0];
		tailRightWing[1][1][0] = addBPoint(1.0, 1.0, 0, trWing0);
		tailRightWing[0][1][1] = body[1][1][1];
		tailRightWing[1][1][1] = addBPoint(1.0, 1.0, 1.0, trWing0);

		tailLeftWing = new BPoint[twnx][twny][twnz];

		Segments tlWing0 = new Segments(0, twDX, 0, dyRear * 0.125, dz - back_height, back_height);

		tailLeftWing[0][0][0] = addBPoint(-1.0, 0, 0, tlWing0);
		tailLeftWing[1][0][0] = body[0][0][0];
		tailLeftWing[0][0][1] = addBPoint(-1.0, 0.0, 1.0, tlWing0);
		tailLeftWing[1][0][1] = body[0][0][1];

		tailLeftWing[0][1][0] = addBPoint(-1.0, 1.0, 0, tlWing0);
		tailLeftWing[1][1][0] = body[0][1][0];
		tailLeftWing[0][1][1] = addBPoint(-1.0, 1.0, 1.0, tlWing0);
		tailLeftWing[1][1][1] = body[0][1][1];

		int trnx = 2;
		int trny = 2;
		int trnz = 2;

		tailRudder = new BPoint[trnx][trny][trnz];

		Segments rudder0 = new Segments(0, back_width, 0, dyRear * 0.25, dz - back_height, back_height + 71);

		tailRudder[0][0][0] = body[0][0][1];
		tailRudder[1][0][0] = body[1][0][1];
		tailRudder[0][1][0] = body[0][1][1];
		tailRudder[1][1][0] = body[1][1][1];

		tailRudder[0][0][1] = addBPoint(0, 0.0, 1.0, rudder0);
		tailRudder[0][1][1] = addBPoint(0.0, 1.0, 1.0, rudder0);

		////// wings

		int wnx = 2;
		int wny = 2;
		int wnz = 2;

		double q = Math.PI * 10.0 / 180.0;
		double sq = Math.sin(q);
		double cq = Math.cos(q);

		double sq1 = sq * dxRoof / dzRoof;
		double ry = dyRear + 0.2 * dy;

		rightWing = new BPoint[wnx][wny][wnz];
		Segments rWing0 = new Segments(dx * 0.5, dxRoof / cq, ry, dyRoof, 0, dzRoof);

		rightWing[0][0][0] = addBPoint(0, 0.0, 0, rWing0);
		rightWing[1][0][0] = addBPoint(cq, 0.0, sq1, rWing0);
		rightWing[0][0][1] = addBPoint(0.0, 0.0, 1.0, rWing0);
		rightWing[1][0][1] = addBPoint(cq, 0.0, 1.0 + sq1, rWing0);

		rightWing[0][1][0] = addBPoint(0.0, 1.0, 0, rWing0);
		rightWing[1][1][0] = addBPoint(cq, 0.5, sq1, rWing0);
		rightWing[0][1][1] = addBPoint(0.0, 1.0, 1.0, rWing0);
		rightWing[1][1][1] = addBPoint(cq, 0.5, 1.0 + sq1, rWing0);

		leftWing = new BPoint[wnx][wny][wnz];

		Segments lWing0 = new Segments(-dx * 0.5, dxRoof / cq, ry, dyRoof, 0, dzRoof);

		leftWing[0][0][0] = addBPoint(-cq, 0.0, sq1, lWing0);
		leftWing[1][0][0] = addBPoint(0.0, 0.0, 0, lWing0);
		leftWing[0][0][1] = addBPoint(-cq, 0.0, 1.0 + sq1, lWing0);
		leftWing[1][0][1] = addBPoint(0.0, 0.0, 1.0, lWing0);

		leftWing[0][1][0] = addBPoint(-cq, 0.5, sq1, lWing0);
		leftWing[1][1][0] = addBPoint(0.0, 1.0, 0, lWing0);
		leftWing[0][1][1] = addBPoint(-cq, 0.5, 1.0 + sq1, lWing0);
		leftWing[1][1][1] = addBPoint(0.0, 1.0, 1.0, lWing0);

	}

	private void buildTextures() {
		// Texture points

		double y = by;
		double x = bx;

		double dxt = 200;
		double dyt = 200;

		addTPoint(x, y, 0);
		addTPoint(x + dxt, y, 0);
		addTPoint(x + dxt, y + dyt, 0);
		addTPoint(x, y + dyt, 0);

		IMG_WIDTH = (int) (2 * bx + dxt);
		IMG_HEIGHT = (int) (2 * by + dyt);

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
