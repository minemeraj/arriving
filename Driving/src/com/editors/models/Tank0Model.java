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
public class Tank0Model extends VehicleModel {

	private double dxTrack = 0;
	private double dyTopTrack = 0;
	private double dyBottomTrack;
	private double dzTrack = 0;

	// body textures
	protected int[][] tBo = null;
	protected int[] tTr = null;

	private BPoint[][][] turret;
	private BPoint[][] cannon_barrel;
	private BPoint[][][][] tracks;

	private double dxTexture = 200;
	private double dyTexture = 200;
	private double wheelRadius = 0;
	private double wheelWidth = 0;
	private int wheel_rays = 0;

	public static String NAME = "Tank";

	public Tank0Model(double dx, double dy, double dz, double dxf, double dyf, double dzf, double dxr, double dyr,
			double dzr, double dxRoof, double dyRoof, double dzRoof, double rearOverhang, double frontOverhang,
			double rearOverhang1, double frontOverhang1, double wheelRadius, double wheelWidth, int wheel_rays) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;

		this.dxTrack = dxf;
		this.dyTopTrack = dyf;
		this.dyBottomTrack = dyTopTrack - rearOverhang - frontOverhang;
		this.dzTrack = dzf;

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

		int c = 0;
		c = initDoubleArrayValues(tBo = new int[1][4], c);
		c = initSingleArrayValues(tTr = new int[4], c);

		points = new Vector<Point3D>();
		texturePoints = new Vector<Point3D>();

		buildBody();
		buildTracks();
		buildTextures();

		// turret and body
		int NF = 6 * 2;
		// tracks
		NF += 6 * tracks.length;

		faces = new int[NF + cannon_barrel.length][3][4];

		int counter = 0;
		counter = buildFaces(counter);
		counter = buildTracksFaces(counter);
	}

	private int buildFaces(int counter) {

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

		faces[counter++] = buildFace(Renderer3D.CAR_TOP, turret[0][0][1], turret[1][0][1], turret[1][1][1],
				turret[0][1][1], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, turret[0][0][0], turret[0][0][1], turret[0][1][1],
				turret[0][1][0], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, turret[1][0][0], turret[1][1][0], turret[1][1][1],
				turret[1][0][1], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, turret[0][1][0], turret[0][1][1], turret[1][1][1],
				turret[1][1][0], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, turret[0][0][0], turret[1][0][0], turret[1][0][1],
				turret[0][0][1], tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, turret[0][0][0], turret[0][1][0], turret[1][1][0],
				turret[1][0][0], tBo[0]);

		for (int i = 0; i < cannon_barrel.length; i++) {

			faces[counter++] = buildFace(Renderer3D.CAR_TOP, cannon_barrel[i][0],
					cannon_barrel[(i + 1) % cannon_barrel.length][0], cannon_barrel[(i + 1) % cannon_barrel.length][1],
					cannon_barrel[i][1], 0, 1, 2, 3);
		}

		return counter;
	}

	private void buildTextures() {

		double y = by;
		double x = bx;

		int shift = 1;

		addTRect(x, y, dxTexture, dyTexture);
		x += dxTexture + shift;
		addTRect(x, y, dzTrack, dyTopTrack);

		IMG_WIDTH = (int) (2 * bx + dxTexture + dzTrack + shift);
		IMG_HEIGHT = (int) (2 * by + Math.max(dyTopTrack, dyTexture));

	}

	private void buildTracks() {

		Segments sRTop = new Segments(dx * 0.5, dxTrack, 0, dyTopTrack, 0, dzTrack);
		Segments sRBottom = new Segments(dx * 0.5, dxTrack, rearOverhang, dyBottomTrack, 0, dzTrack);

		tracks = new BPoint[2][2][2][2];

		tracks[0][0][0][0] = addBPoint(0.0, 0.0, 0, sRBottom);
		tracks[0][1][0][0] = addBPoint(1.0, 0.0, 0, sRBottom);
		tracks[0][0][1][0] = addBPoint(0.0, 1.0, 0, sRBottom);
		tracks[0][1][1][0] = addBPoint(1.0, 1.0, 0, sRBottom);

		tracks[0][0][0][1] = addBPoint(0.0, 0.0, 1.0, sRTop);
		tracks[0][1][0][1] = addBPoint(1.0, 0.0, 1.0, sRTop);
		tracks[0][0][1][1] = addBPoint(0.0, 1.0, 1.0, sRTop);
		tracks[0][1][1][1] = addBPoint(1.0, 1.0, 1.0, sRTop);

		Segments sLTop = new Segments(-dx * 0.5 - dxTrack, dxTrack, 0, dyTopTrack, 0, dzTrack);
		Segments sLBottom = new Segments(-dx * 0.5 - dxTrack, dxTrack, rearOverhang, dyBottomTrack, 0, dzTrack);

		tracks[1] = new BPoint[2][2][2];

		tracks[1][0][0][0] = addBPoint(0, 0.0, 0, sLBottom);
		tracks[1][1][0][0] = addBPoint(1.0, 0.0, 0, sLBottom);
		tracks[1][0][1][0] = addBPoint(0.0, 1.0, 0, sLBottom);
		tracks[1][1][1][0] = addBPoint(1.0, 1.0, 0, sLBottom);

		tracks[1][0][0][1] = addBPoint(0.0, 0.0, 1.0, sLTop);
		tracks[1][1][0][1] = addBPoint(1.0, 0.0, 1.0, sLTop);
		tracks[1][0][1][1] = addBPoint(0.0, 1.0, 1.0, sLTop);
		tracks[1][1][1][1] = addBPoint(1.0, 1.0, 1.0, sLTop);

	}

	private void buildBody() {

		double zz = dzTrack - dz;

		Segments s0 = new Segments(0, dx * 0.5, 0, dy, zz, dz);

		body = new BPoint[2][2][2];

		body[0][0][0] = addBPoint(-1.0, 0.0, 0, s0);
		body[1][0][0] = addBPoint(1.0, 0.0, 0, s0);
		body[0][1][0] = addBPoint(-1.0, 1.0, 0, s0);
		body[1][1][0] = addBPoint(1.0, 1.0, 0, s0);

		body[0][0][1] = addBPoint(-1.0, 0.0, 1.0, s0);
		body[1][0][1] = addBPoint(1.0, 0.0, 1.0, s0);
		body[0][1][1] = addBPoint(-1.0, 1.0, 1.0, s0);
		body[1][1][1] = addBPoint(1.0, 1.0, 1.0, s0);

		Segments s1 = new Segments(0, dxRoof * 0.5, dyRear, dyRoof, dzTrack, dzRoof);

		turret = new BPoint[2][2][2];

		turret[0][0][0] = addBPoint(-1.0, 0.0, 0, s1);
		turret[1][0][0] = addBPoint(1.0, 0.0, 0, s1);
		turret[0][1][0] = addBPoint(-1.0, 1.0, 0, s1);
		turret[1][1][0] = addBPoint(1.0, 1.0, 0, s1);

		turret[0][0][1] = addBPoint(-1.0, 0.0, 1.0, s1);
		turret[1][0][1] = addBPoint(1.0, 0.0, 1.0, s1);
		turret[0][1][1] = addBPoint(-1.0, 1.0, 1.0, s1);
		turret[1][1][1] = addBPoint(1.0, 1.0, 1.0, s1);

		cannon_barrel = addYCylinder(0, dyRoof + dyRear, dzTrack + dzRoof * 0.5, wheelRadius, wheelWidth, wheel_rays);

	}

	private int buildTracksFaces(int counter) {

		for (int i = 0; i < tracks.length; i++) {

			faces[counter++] = buildFace(Renderer3D.CAR_TOP, tracks[i][0][0][1], tracks[i][1][0][1], tracks[i][1][1][1],
					tracks[i][0][1][1], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, tracks[i][0][0][0], tracks[i][0][0][1],
					tracks[i][0][1][1], tracks[i][0][1][0], tTr);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, tracks[i][1][0][0], tracks[i][1][1][0],
					tracks[i][1][1][1], tracks[i][1][0][1], tTr);
			faces[counter++] = buildFace(Renderer3D.CAR_FRONT, tracks[i][0][1][0], tracks[i][0][1][1],
					tracks[i][1][1][1], tracks[i][1][1][0], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_BACK, tracks[i][0][0][0], tracks[i][1][0][0],
					tracks[i][1][0][1], tracks[i][0][0][1], tBo[0]);
			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, tracks[i][0][0][0], tracks[i][0][1][0],
					tracks[i][1][1][0], tracks[i][1][0][0], tBo[0]);

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

		bufGraphics.setColor(Color.BLACK);
		bufGraphics.setStroke(new BasicStroke(0.1f));

		printTexturePolygon(bufGraphics, tBo[0]);
		printTexturePolygon(bufGraphics, tTr);

	}

}
