package com.editors.models;

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
public class SteamLocomotive0Model extends VehicleModel {

	private double wheelRadius;
	private double wheelWidth;
	protected int wheelRays;

	protected BPoint[][][] cabin;
	protected BPoint[][][] body;

	private BPoint[][] rWheelLeftFront;
	private BPoint[][] rWheelRightFront;
	private BPoint[][] rWheelLeftRear;
	private BPoint[][] rWheelRightRear;

	private BPoint[][] fWheelLeftFront;
	private BPoint[][] fWheelRightFront;
	private BPoint[][] fWheelLeftRear;
	private BPoint[][] fWheelRightRear;
	protected BPoint[][][] wagon;

	public static String NAME = "Steam locomotive";

	// body textures
	protected int[][] tBo = null;
	// wheel textures
	protected int[][] tWh = null;

	private double dyTexture = 200;
	private double dxTexture = 200;

	private int boilerRays = 20;

	private BPoint[][] funnel;
	private int funnel_parallels = 2;
	private int funnel_meridians = 10;
	private double funnel_radius = 10;
	private double funnel_height = 40;

	public SteamLocomotive0Model(double dx, double dy, double dz, double dxf, double dyf, double dzf, double dxr,
			double dyr, double dzr, double dxRoof, double dyRoof, double dzRoof, double rearOverhang,
			double frontOverhang, double wheelRadius, double wheelWidth, int wheelRays) {
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

		this.wheelRadius = wheelRadius;
		this.wheelWidth = wheelWidth;
		this.wheelRays = wheelRays;
	}

	@Override
	public void initMesh() {

		int c = 0;
		c = initDoubleArrayValues(tBo = new int[1][4], c);
		c = initDoubleArrayValues(tWh = new int[1][4], c);

		points = new Vector<Point3D>();
		texturePoints = new Vector<Point3D>();

		int pnx = 2;
		int pny = 2;
		int pnz = 2;

		buildBody(pnx, pny, pnz);
		buildCabin();
		buildBoiler();
		buildFunnel();

		int firstWheelTexturePoint = 4;
		buildTextures();

		// faces
		int NF = 6 * 3;
		// cabin
		NF += 6;
		// funnel
		NF += (funnel_meridians * (funnel_parallels - 1) + funnel_meridians);
		// boiler
		int totBoilerPolygons = boilerRays + 2 * (boilerRays - 2);

		int totWheelPolygon = wheelRays + 2 * (wheelRays - 2);
		int NUM_WHEEL_FACES = 8 * totWheelPolygon;

		faces = new int[NF + NUM_WHEEL_FACES + totBoilerPolygons][3][4];

		int counter = 0;

		counter = buildBodyFaces(counter, firstWheelTexturePoint, totWheelPolygon);
		counter = buildBoilerFaces(counter, wagon);
		counter = buildCabinYFaces(counter);
		counter = buildFunnelFaces(counter);

		IMG_WIDTH = (int) (2 * bx + dx);
		IMG_HEIGHT = (int) (2 * by + dy);

	}

	private void buildCabin() {

		cabin = new BPoint[2][2][2];

		Segments cb = new Segments(x0, dx, y0, dy - dyRoof, z0 + dzRear + dz, dzRoof);
		cabin[0][0][0] = addBPoint(-0.5, 0, 0, cb);
		cabin[1][0][0] = addBPoint(0.5, 0, 0, cb);
		cabin[1][1][0] = addBPoint(0.5, 1, 0, cb);
		cabin[0][1][0] = addBPoint(-0.5, 1, 0, cb);

		cabin[0][0][1] = addBPoint(-0.5, 0, 1, cb);
		cabin[1][0][1] = addBPoint(0.5, 0, 1, cb);
		cabin[1][1][1] = addBPoint(0.5, 1, 1, cb);
		cabin[0][1][1] = addBPoint(-0.5, 1, 1, cb);

	}

	private int buildCabinYFaces(int counter) {

		for (int i = 0; i < 2 - 1; i++) {

			faces[counter++] = buildFace(Renderer3D.CAR_TOP, cabin[0][i][1], cabin[1][i][1], cabin[1][i + 1][1],
					cabin[0][i + 1][1], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, cabin[0][i][0], cabin[0][i][1], cabin[0][i + 1][1],
					cabin[0][i + 1][0], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, cabin[1][i][0], cabin[1][i + 1][0], cabin[1][i + 1][1],
					cabin[1][i][1], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, cabin[0][i][0], cabin[0][i + 1][0], cabin[1][i + 1][0],
					cabin[1][i][0], tBo[0]);

			if (i == 0) {

				faces[counter++] = buildFace(Renderer3D.CAR_BACK, cabin[0][i][0], cabin[1][i][0], cabin[1][i][1],
						cabin[0][i][1], tBo[0]);

			}

			if (i == 2 - 2) {

				faces[counter++] = buildFace(Renderer3D.CAR_FRONT, cabin[0][i + 1][0], cabin[0][i + 1][1],
						cabin[1][i + 1][1], cabin[1][i + 1][0], tBo[0]);
			}

		}

		return counter;
	}

	protected void buildBody(int pnx, int pny, int pnz) {

		body = new BPoint[pnx][pny][pnz];

		Segments p0 = new Segments(0, dx, 0, dy, dzRear, dz);

		body[0][0][0] = addBPoint(-0.5, 0.0, 0, p0);
		body[1][0][0] = addBPoint(0.5, 0.0, 0, p0);
		body[0][0][1] = addBPoint(-0.5, 0.0, 1.0, p0);
		body[1][0][1] = addBPoint(0.5, 0.0, 1.0, p0);

		body[0][1][0] = addBPoint(-0.5, 1.0, 0, p0);
		body[1][1][0] = addBPoint(0.5, 1.0, 0, p0);
		body[0][1][1] = addBPoint(-0.5, 1.0, 1.0, p0);
		body[1][1][1] = addBPoint(0.5, 1.0, 1.0, p0);

		int bnx = 2;
		int bny = 2;
		int bnz = 2;

		back = new BPoint[bnx][bny][bnz];

		Segments b0 = new Segments(0, dxRear, rearOverhang, dyRear, 0, dzRear);

		back[0][0][0] = addBPoint(-0.5, 0.0, 0, b0);
		back[1][0][0] = addBPoint(0.5, 0.0, 0, b0);
		back[0][0][1] = addBPoint(-0.5, 0.0, 1.0, b0);
		back[1][0][1] = addBPoint(0.5, 0.0, 1.0, b0);

		back[0][1][0] = addBPoint(-0.5, 1.0, 0, b0);
		back[1][1][0] = addBPoint(0.5, 1.0, 0, b0);
		back[0][1][1] = addBPoint(-0.5, 1.0, 1.0, b0);
		back[1][1][1] = addBPoint(0.5, 1.0, 1.0, b0);

		fWheelLeftFront = buildWheel(-dxRear * 0.5 - wheelWidth, rearOverhang, 0, wheelRadius, wheelWidth, wheelRays);
		fWheelRightFront = buildWheel(dxRear * 0.5, rearOverhang, 0, wheelRadius, wheelWidth, wheelRays);

		fWheelLeftRear = buildWheel(-dxRear * 0.5 - wheelWidth, rearOverhang + dyRear, 0, wheelRadius, wheelWidth,
				wheelRays);
		fWheelRightRear = buildWheel(dxRear * 0.5, rearOverhang + dyRear, 0, wheelRadius, wheelWidth, wheelRays);

		int fnx = 2;
		int fny = 2;
		int fnz = 2;

		front = new BPoint[fnx][fny][fnz];

		double fy = dy - dyFront - frontOverhang;

		Segments f0 = new Segments(0, dxFront, fy, dyFront, 0, dzFront);

		front[0][0][0] = addBPoint(-0.5, 0.0, 0, f0);
		front[1][0][0] = addBPoint(0.5, 0.0, 0, f0);
		front[0][0][1] = addBPoint(-0.5, 0.0, 1.0, f0);
		front[1][0][1] = addBPoint(0.5, 0.0, 1.0, f0);

		front[0][1][0] = addBPoint(-0.5, 1.0, 0, f0);
		front[1][1][0] = addBPoint(0.5, 1.0, 0, f0);
		front[0][1][1] = addBPoint(-0.5, 1.0, 1.0, f0);
		front[1][1][1] = addBPoint(0.5, 1.0, 1.0, f0);

		rWheelLeftFront = buildWheel(-dxFront * 0.5 - wheelWidth, fy, 0, wheelRadius, wheelWidth, wheelRays);
		rWheelRightFront = buildWheel(dxFront * 0.5, fy, 0, wheelRadius, wheelWidth, wheelRays);

		rWheelLeftRear = buildWheel(-dxFront * 0.5 - wheelWidth, fy + dyFront, 0, wheelRadius, wheelWidth, wheelRays);
		rWheelRightRear = buildWheel(dxFront * 0.5, fy + dyFront, 0, wheelRadius, wheelWidth, wheelRays);

	}

	protected int buildBodyFaces(int counter, int firstWheelTexturePoint, int totWheelPolygon) {

		faces[counter++] = buildFace(Renderer3D.CAR_TOP, body[0][0][1], body[1][0][1], body[1][1][1], body[0][1][1],
				tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, body[0][0][0], body[0][0][1], body[0][1][1], body[0][1][0],
				tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, body[1][0][0], body[1][1][0], body[1][1][1], body[1][0][1],
				tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, body[0][1][0], body[0][1][1], body[1][1][1], body[1][1][0],
				tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, body[0][0][0], body[1][0][0], body[1][0][1], body[0][0][1],
				tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, body[0][0][0], body[0][1][0], body[1][1][0], body[1][0][0],
				tBo[0]);

		faces[counter++] = buildFace(Renderer3D.CAR_TOP, back[0][0][1], back[1][0][1], back[1][1][1], back[0][1][1],
				tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, back[0][0][0], back[0][0][1], back[0][1][1], back[0][1][0],
				tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, back[1][0][0], back[1][1][0], back[1][1][1], back[1][0][1],
				tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, back[0][1][0], back[0][1][1], back[1][1][1], back[1][1][0],
				tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, back[0][0][0], back[1][0][0], back[1][0][1], back[0][0][1],
				tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, back[0][0][0], back[0][1][0], back[1][1][0], back[1][0][0],
				tBo[0]);

		faces[counter++] = buildFace(Renderer3D.CAR_TOP, front[0][0][1], front[1][0][1], front[1][1][1], front[0][1][1],
				tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, front[0][0][0], front[0][0][1], front[0][1][1],
				front[0][1][0], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, front[1][0][0], front[1][1][0], front[1][1][1],
				front[1][0][1], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, front[0][1][0], front[0][1][1], front[1][1][1],
				front[1][1][0], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, front[0][0][0], front[1][0][0], front[1][0][1],
				front[0][0][1], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, front[0][0][0], front[0][1][0], front[1][1][0],
				front[1][0][0], tBo[0]);

		// front bogie
		counter = buildWheelFaces(counter, firstWheelTexturePoint, totWheelPolygon, fWheelLeftFront, fWheelRightFront,
				fWheelLeftRear, fWheelRightRear);

		// rear bogie
		counter = buildWheelFaces(counter, firstWheelTexturePoint, totWheelPolygon, rWheelLeftFront, rWheelRightFront,
				rWheelLeftRear, rWheelRightRear);

		return counter;
	}

	protected void buildBoiler() {

		double rdy = (dy - dyRoof);

		double radius = dxRoof * 0.5;

		BPoint[][] cylinder = addYCylinder(0, rdy, dzRear + dz + radius, radius, dyRoof, boilerRays);
		wagon = new BPoint[1][][];
		wagon[0] = cylinder;

	}

	protected int buildBoilerFaces(int counter, BPoint[][][] wagon) {

		BPoint[][] cylinder = wagon[0];
		int raysNumber = cylinder.length;

		for (int i = 0; i < raysNumber; i++) {

			faces[counter++] = buildFace(Renderer3D.CAR_TOP, cylinder[i][0], cylinder[(i + 1) % cylinder.length][0],
					cylinder[(i + 1) % cylinder.length][1], cylinder[i][1], tBo[0]);

		}

		// sides as triangles
		for (int i = 1; i < raysNumber - 1; i++) {

			BPoint p0 = cylinder[0][0];
			BPoint p1 = cylinder[i][0];
			BPoint p2 = cylinder[(i + 1) % raysNumber][0];

			faces[counter++] = buildFace(0, p0, p1, p2, tBo[0]);
		}

		for (int i = 1; i < raysNumber - 1; i++) {

			BPoint p0 = cylinder[0][1];
			BPoint p1 = cylinder[(i + 1) % raysNumber][1];
			BPoint p2 = cylinder[i][1];

			faces[counter++] = buildFace(0, p0, p1, p2, tBo[0]);
		}

		return counter;

	}

	private void buildFunnel() {

		double fx = 0;
		double fy = dy - funnel_radius;
		double fz = dzFront + dzRoof;

		funnel = new BPoint[funnel_parallels][funnel_meridians];
		funnel = buildFunnel(fx, fy, fz, funnel_height, funnel_radius, 2, 10);

	}

	private int buildFunnelFaces(int counter) {

		for (int k = 0; k < funnel_parallels - 1; k++) {
			for (int j = 0; j < funnel_meridians; j++) {
				faces[counter++] = buildFace(Renderer3D.CAR_LEFT, funnel[k][j], funnel[k][(j + 1) % funnel_meridians],
						funnel[k + 1][(j + 1) % funnel_meridians], funnel[k + 1][j], tBo[0]);
			}
		}

		for (int j = 0; j < funnel_meridians; j++) {
			int k = funnel_parallels - 1;
			faces[counter++] = buildFace(Renderer3D.CAR_TOP, funnel[k][0], funnel[k][j],
					funnel[k][(j + 1) % funnel_meridians], tBo[0]);
		}

		return counter;
	}

	protected void buildTextures() {

		double x = bx;
		double y = by;

		addTRect(x, y, dxTexture, dyTexture);

		// wheel texture, a black square for simplicity:

		x += dxTexture;
		y = by;

		addTRect(x, y, wheelWidth, wheelWidth);

		IMG_WIDTH = (int) (2 * bx + dxTexture);
		IMG_HEIGHT = (int) (2 * by + dyTexture);
	}

	private int buildWheelFaces(

			int counter, int firstWheelTexturePoint, int totWheelPolygon, BPoint[][] wheelLeftFront,
			BPoint[][] wheelRightFront, BPoint[][] wheelLeftRear, BPoint[][] wheelRightRear) {

		int[][][] wFaces = buildWheelFaces(wheelLeftFront, firstWheelTexturePoint);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}

		wFaces = buildWheelFaces(wheelRightFront, firstWheelTexturePoint);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}

		wFaces = buildWheelFaces(wheelLeftRear, firstWheelTexturePoint);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}

		wFaces = buildWheelFaces(wheelRightRear, firstWheelTexturePoint);
		for (int i = 0; i < totWheelPolygon; i++) {
			faces[counter++] = wFaces[i];
		}

		return counter;

	}

	@Override
	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);

	}

	@Override
	public void printTexture(Graphics2D bufGraphics) {

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
