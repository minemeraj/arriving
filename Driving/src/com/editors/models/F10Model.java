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

public class F10Model extends MeshModel {

	private int bx = 10;
	private int by = 10;

	private double dx = 0;
	private double dy = 0;
	private double dz = 0;

	private double dxFront = 0;
	private double dyFront = 0;
	private double dzFront = 0;

	private double dxRear = 0;
	private double dyRear = 0;
	private double dzRear = 0;

	private double dxRoof;
	private double dyRoof;
	private double dzRoof;

	protected double rearOverhang;
	protected double frontOverhang;

	protected double rearOverhang1;
	protected double frontOverhang1;

	private double wheelRadius;
	private double wheelWidth;
	private int wheel_rays;

	private double dyTexture = 200;
	private double dxTexture = 200;

	double x0 = 0;
	double y0 = 0;
	double z0 = 0;
	private BPoint[][][] body;
	private BPoint[][][] front;
	private BPoint[][][] roof;
	private BPoint[][][] frontSpoiler;
	private BPoint[][][] backSpoiler;
	private BPoint[][][] back;
	private BPoint[][] wheelLeftFront;
	private BPoint[][] wheelRightFront;
	private BPoint[][] wheelLeftRear;
	private BPoint[][] wheelRightRear;

	public static String NAME = "F1 Car";

	int[][] bo = { { 0, 1, 2, 3 } };
	// wheel texture
	protected int[][] wh = { { 4, 5, 6, 7 } };

	public F10Model(double dx, double dy, double dz, double dxf, double dyf, double dzf, double dxr, double dyr,
			double dzr, double dxRoof, double dyRoof, double dzRoof, double rearOverhang, double frontOverhang,
			double rearOverhang1, double frontOverhang1, double wheelRadius, double wheelWidth, int wheel_rays) {
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

		this.rearOverhang = rearOverhang;
		this.frontOverhang = frontOverhang;

		this.rearOverhang1 = rearOverhang1;
		this.frontOverhang1 = frontOverhang1;

		this.wheelRadius = wheelRadius;
		this.wheelWidth = wheelWidth;
		this.wheel_rays = wheel_rays;
	}

	@Override
	public void initMesh() {

		points = new Vector<Point3D>();
		texturePoints = new Vector<Point3D>();

		buildTextures();
		int totBlockTexturesPoints = 4;

		buildBody();

		buildWheels();

		int totWheelPolygon = wheel_rays + 2 * (wheel_rays - 2);
		int NUM_WHEEL_FACES = 4 * totWheelPolygon;

		// faces
		int NF = 6 * 6;

		int counter = 0;

		faces = new int[NF + NUM_WHEEL_FACES][3][4];

		counter = buildBodyFaces(counter);

		counter = buildWheelFaces(counter, totWheelPolygon);

	}

	private void buildWheels() {

		double wz = 0;
		double wx = wheelWidth;

		wheelLeftFront = buildWheel(-dx * 0.5 - wx * 0.5, dyRear + dy + dyFront * 0.5, wz, wheelRadius, wheelWidth,
				wheel_rays);
		wheelRightFront = buildWheel(dx * 0.5 - wx * 0.5, dyRear + dy + dyFront * 0.5, wz, wheelRadius, wheelWidth,
				wheel_rays);
		wheelLeftRear = buildWheel(-dx * 0.5 - wx * 0.5, dyRear * 0.5, wz, wheelRadius, wheelWidth, wheel_rays);
		wheelRightRear = buildWheel(dx * 0.5 - wx * 0.5, dyRear * 0.5, wz, wheelRadius, wheelWidth, wheel_rays);

	}

	private void buildBody() {

		Segments rs = new Segments(0, dxRear * 0.5, 0, 14, dzRear, dzRoof);

		backSpoiler = new BPoint[2][2][2];

		backSpoiler[0][0][0] = addBPoint(-1.0, 0.0, 0, rs);
		backSpoiler[1][0][0] = addBPoint(1.0, 0.0, 0, rs);
		backSpoiler[0][1][0] = addBPoint(-1.0, 1.0, 0, rs);
		backSpoiler[1][1][0] = addBPoint(1.0, 1.0, 0, rs);

		backSpoiler[0][0][1] = addBPoint(-1.0, 0.0, 1.0, rs);
		backSpoiler[1][0][1] = addBPoint(1.0, 0.0, 1.0, rs);
		backSpoiler[0][1][1] = addBPoint(-1.0, 1.0, 1.0, rs);
		backSpoiler[1][1][1] = addBPoint(1.0, 1.0, 1.0, rs);

		Segments s0 = new Segments(0, dxRear * 0.5, 0, dyRear, 0, dzRear);

		back = new BPoint[2][2][2];

		back[0][0][0] = addBPoint(-1.0, 0.0, 0, s0);
		back[1][0][0] = addBPoint(1.0, 0.0, 0, s0);
		back[0][1][0] = addBPoint(-1.0, 1.0, 0, s0);
		back[1][1][0] = addBPoint(1.0, 1.0, 0, s0);

		back[0][0][1] = addBPoint(-1.0, 0.0, 1.0, s0);
		back[1][0][1] = addBPoint(1.0, 0.0, 1.0, s0);
		back[0][1][1] = addBPoint(-1.0, 1.0, 1.0, s0);
		back[1][1][1] = addBPoint(1.0, 1.0, 1.0, s0);

		Segments s1 = new Segments(0, dx * 0.5, dyRear, dy, 0, dz);

		body = new BPoint[2][2][2];

		body[0][0][0] = addBPoint(-1.0, 0.0, 0, s1);
		body[1][0][0] = addBPoint(1.0, 0.0, 0, s1);
		body[0][1][0] = addBPoint(-1.0, 1.0, 0, s1);
		body[1][1][0] = addBPoint(1.0, 1.0, 0, s1);

		body[0][0][1] = addBPoint(-1.0, 0.0, 1.0, s1);
		body[1][0][1] = addBPoint(1.0, 0.0, 1.0, s1);
		body[0][1][1] = addBPoint(-1.0, 1.0, 1.0, s1);
		body[1][1][1] = addBPoint(1.0, 1.0, 1.0, s1);

		Segments s2 = new Segments(0, dxFront * 0.5, dyRear + dy, dyFront, 0, dzFront);

		front = new BPoint[2][2][2];

		front[0][0][0] = addBPoint(-1.0, 0.0, 0, s2);
		front[1][0][0] = addBPoint(1.0, 0.0, 0, s2);
		front[0][1][0] = addBPoint(-1.0, 1.0, 0, s2);
		front[1][1][0] = addBPoint(1.0, 1.0, 0, s2);

		front[0][0][1] = addBPoint(-1.0, 0.0, 1.0, s2);
		front[1][0][1] = addBPoint(1.0, 0.0, 1.0, s2);
		front[0][1][1] = addBPoint(-1.0, 1.0, 1.0, s2);
		front[1][1][1] = addBPoint(1.0, 1.0, 1.0, s2);

		Segments s3 = new Segments(0, dxRoof * 0.5, dyRear, dyRoof, dz, dzRoof);

		roof = new BPoint[2][2][2];

		roof[0][0][0] = addBPoint(-1.0, 0.0, 0, s3);
		roof[1][0][0] = addBPoint(1.0, 0.0, 0, s3);
		roof[0][1][0] = addBPoint(-1.0, 1.0, 0, s3);
		roof[1][1][0] = addBPoint(1.0, 1.0, 0, s3);

		roof[0][0][1] = addBPoint(-1.0, 0.0, 1.0, s3);
		roof[1][0][1] = addBPoint(1.0, 0.0, 1.0, s3);
		roof[0][1][1] = addBPoint(-1.0, 1.0, 1.0, s3);
		roof[1][1][1] = addBPoint(1.0, 1.0, 1.0, s3);

		Segments fs = new Segments(0, dx * 0.5, dyRear + dy + dyFront, 16, 0, dzFront);

		frontSpoiler = new BPoint[2][2][2];

		frontSpoiler[0][0][0] = addBPoint(-1.0, 0.0, 0, fs);
		frontSpoiler[1][0][0] = addBPoint(1.0, 0.0, 0, fs);
		frontSpoiler[0][1][0] = addBPoint(-1.0, 1.0, 0, fs);
		frontSpoiler[1][1][0] = addBPoint(1.0, 1.0, 0, fs);

		frontSpoiler[0][0][1] = addBPoint(-1.0, 0.0, 1.0, fs);
		frontSpoiler[1][0][1] = addBPoint(1.0, 0.0, 1.0, fs);
		frontSpoiler[0][1][1] = addBPoint(-1.0, 1.0, 1.0, fs);
		frontSpoiler[1][1][1] = addBPoint(1.0, 1.0, 1.0, fs);
	}

	private void buildTextures() {

		int shift = 1;

		// Texture points

		double y = by;
		double x = bx;

		addTRect(x, y, dxTexture, dyTexture);

		// wheel texture, a black square for simplicity:

		x = bx + dxTexture + shift;
		y = by;

		addTRect(x, y, wheelWidth, wheelWidth);

		IMG_WIDTH = (int) (2 * bx + dxTexture + wheelWidth + shift);
		IMG_HEIGHT = (int) (2 * by + dyTexture);

	}

	private int buildWheelFaces(int counter, int totWheelPolygon) {

		///// WHEELS

		int[][][] wFaces = buildWheelFaces(wheelLeftFront, wh[0]);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}

		wFaces = buildWheelFaces(wheelRightFront, wh[0]);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}

		wFaces = buildWheelFaces(wheelLeftRear, wh[0]);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}

		wFaces = buildWheelFaces(wheelRightRear, wh[0]);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}
		/////
		return 0;
	}

	private int buildBodyFaces(int counter) {

		faces[counter++] = buildFace(Renderer3D.CAR_TOP, backSpoiler[0][0][1], backSpoiler[1][0][1],
				backSpoiler[1][1][1], backSpoiler[0][1][1], bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, backSpoiler[0][0][0], backSpoiler[0][0][1],
				backSpoiler[0][1][1], backSpoiler[0][1][0], bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, backSpoiler[1][0][0], backSpoiler[1][1][0],
				backSpoiler[1][1][1], backSpoiler[1][0][1], bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, backSpoiler[0][1][0], backSpoiler[0][1][1],
				backSpoiler[1][1][1], backSpoiler[1][1][0], bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, backSpoiler[0][0][0], backSpoiler[1][0][0],
				backSpoiler[1][0][1], backSpoiler[0][0][1], bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, backSpoiler[0][0][0], backSpoiler[0][1][0],
				backSpoiler[1][1][0], backSpoiler[1][0][0], bo[0]);

		faces[counter++] = buildFace(Renderer3D.CAR_TOP, back[0][0][1], back[1][0][1], back[1][1][1], back[0][1][1],
				bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_TOP, back[0][0][0], back[0][0][1], back[0][1][1], back[0][1][0],
				bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_TOP, back[1][0][0], back[1][1][0], back[1][1][1], back[1][0][1],
				bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_TOP, back[0][1][0], back[0][1][1], back[1][1][1], back[1][1][0],
				bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_TOP, back[0][0][0], back[1][0][0], back[1][0][1], back[0][0][1],
				bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_TOP, back[0][0][0], back[0][1][0], back[1][1][0], back[1][0][0],
				bo[0]);

		faces[counter++] = buildFace(Renderer3D.CAR_TOP, body[0][0][1], body[1][0][1], body[1][1][1], body[0][1][1],
				bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, body[0][0][0], body[0][0][1], body[0][1][1], body[0][1][0],
				bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, body[1][0][0], body[1][1][0], body[1][1][1], body[1][0][1],
				bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, body[0][1][0], body[0][1][1], body[1][1][1], body[1][1][0],
				bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, body[0][0][0], body[1][0][0], body[1][0][1], body[0][0][1],
				bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, body[0][0][0], body[0][1][0], body[1][1][0], body[1][0][0],
				bo[0]);

		faces[counter++] = buildFace(Renderer3D.CAR_TOP, front[0][0][1], front[1][0][1], front[1][1][1], front[0][1][1],
				bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, front[0][0][0], front[0][0][1], front[0][1][1],
				front[0][1][0], bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, front[1][0][0], front[1][1][0], front[1][1][1],
				front[1][0][1], bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, front[0][1][0], front[0][1][1], front[1][1][1],
				front[1][1][0], bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, front[0][0][0], front[1][0][0], front[1][0][1],
				front[0][0][1], bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, front[0][0][0], front[0][1][0], front[1][1][0],
				front[1][0][0], bo[0]);

		faces[counter++] = buildFace(Renderer3D.CAR_TOP, frontSpoiler[0][0][1], frontSpoiler[1][0][1],
				frontSpoiler[1][1][1], frontSpoiler[0][1][1], bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, frontSpoiler[0][0][0], frontSpoiler[0][0][1],
				frontSpoiler[0][1][1], frontSpoiler[0][1][0], bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, frontSpoiler[1][0][0], frontSpoiler[1][1][0],
				frontSpoiler[1][1][1], frontSpoiler[1][0][1], bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, frontSpoiler[0][1][0], frontSpoiler[0][1][1],
				frontSpoiler[1][1][1], frontSpoiler[1][1][0], bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, frontSpoiler[0][0][0], frontSpoiler[1][0][0],
				frontSpoiler[1][0][1], frontSpoiler[0][0][1], bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, frontSpoiler[0][0][0], frontSpoiler[0][1][0],
				frontSpoiler[1][1][0], frontSpoiler[1][0][0], bo[0]);

		faces[counter++] = buildFace(Renderer3D.CAR_TOP, roof[0][0][1], roof[1][0][1], roof[1][1][1], roof[0][1][1],
				bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, roof[0][0][0], roof[0][0][1], roof[0][1][1], roof[0][1][0],
				bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, roof[1][0][0], roof[1][1][0], roof[1][1][1], roof[1][0][1],
				bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, roof[0][1][0], roof[0][1][1], roof[1][1][1], roof[1][1][0],
				bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, roof[0][0][0], roof[1][0][0], roof[1][0][1], roof[0][0][1],
				bo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, roof[0][0][0], roof[0][1][0], roof[1][1][0], roof[1][0][0],
				bo[0]);

		return counter;
	}

	@Override
	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);

	}

	@Override
	public void printTexture(Graphics2D bufGraphics) {

		bufGraphics.setStroke(new BasicStroke(0.1f));

		bufGraphics.setColor(new Color(255, 40, 1));
		printTexturePolygon(bufGraphics, bo[0]);

		bufGraphics.setColor(new Color(0, 0, 0));
		printTexturePolygon(bufGraphics, wh[0]);

	}

}
