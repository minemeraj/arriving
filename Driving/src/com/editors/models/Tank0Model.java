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
	protected int[] tLeftSideTrack = null;
	protected int[] tBackLeftTrack = null;
	protected int[] tBackBody = null;
	protected int[] tBackRightTrack = null;
	protected int[] tTopLeftTrack = null;
	protected int[] tTopBody = null;
	protected int[] tTopRightTrack = null;
	protected int[] tRightSideTrack = null;
	protected int[] tTopTurret = null;
	protected int[][] tCannon = null;
	protected int[][] tBo = null;
	protected int[] tTu = null;
	protected int[] tTrackPath = null;
	protected int[] tFrontLeftTrack = null;
	protected int[] tFrontBody = null;
	protected int[] tFrontRightTrack = null;

	private BPoint[][][] turret;
	private BPoint[][] cannon_barrel;
	private BPoint[][][][] tracks;

	private double dxTexture = 100;
	private double dyTexture = 100;
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
		c = initSingleArrayValues(tLeftSideTrack = new int[4], c);
		c = initSingleArrayValues(tBackLeftTrack = new int[4], c);
		c = initSingleArrayValues(tBackBody = new int[4], c);
		c = initSingleArrayValues(tBackRightTrack = new int[4], c);
		c = initSingleArrayValues(tTopLeftTrack = new int[4], c);
		c = initSingleArrayValues(tTopBody = new int[4], c);
		c = initSingleArrayValues(tTopRightTrack = new int[4], c);
		c = initSingleArrayValues(tTopTurret = new int[4], c);
		c = initSingleArrayValues(tRightSideTrack = new int[4], c);
		c = initSingleArrayValues(tTrackPath = new int[4], c);
		c = initDoubleArrayValues(tCannon = new int[wheel_rays][4], c);
		c = initDoubleArrayValues(tBo = new int[1][4], c);
		c = initSingleArrayValues(tTu = new int[4], c);
		c = initSingleArrayValues(tFrontLeftTrack = new int[4], c);
		c = initSingleArrayValues(tFrontBody = new int[4], c);
		c = initSingleArrayValues(tFrontRightTrack = new int[4], c);

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
				tTopBody);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, body[0][0][0], body[0][0][1], body[0][1][1], body[0][1][0],
				tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, body[1][0][0], body[1][1][0], body[1][1][1], body[1][0][1],
				tBo[0]);
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, body[0][1][0], body[0][1][1], body[1][1][1], body[1][1][0],
				tFrontBody);
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, body[0][0][0], body[1][0][0], body[1][0][1], body[0][0][1],
				tBackBody);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, body[0][0][0], body[0][1][0], body[1][1][0], body[1][0][0],
				tBo[0]);

		faces[counter++] = buildFace(Renderer3D.CAR_TOP, turret[0][0][1], turret[1][0][1], turret[1][1][1],
				turret[0][1][1], tTopTurret);
		faces[counter++] = buildFace(Renderer3D.CAR_LEFT, turret[0][0][0], turret[0][0][1], turret[0][1][1],
				turret[0][1][0], tTu);
		faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, turret[1][0][0], turret[1][1][0], turret[1][1][1],
				turret[1][0][1], tTu);
		faces[counter++] = buildFace(Renderer3D.CAR_FRONT, turret[0][1][0], turret[0][1][1], turret[1][1][1],
				turret[1][1][0], tTu);
		faces[counter++] = buildFace(Renderer3D.CAR_BACK, turret[0][0][0], turret[1][0][0], turret[1][0][1],
				turret[0][0][1], tTu);
		faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, turret[0][0][0], turret[0][1][0], turret[1][1][0],
				turret[1][0][0], tTu);

		for (int i = 0; i < cannon_barrel.length; i++) {

			faces[counter++] = buildFace(Renderer3D.CAR_TOP, cannon_barrel[i][0],
					cannon_barrel[(i + 1) % cannon_barrel.length][0], cannon_barrel[(i + 1) % cannon_barrel.length][1],
					cannon_barrel[i][1], tCannon[i]);
		}

		return counter;
	}

	private void buildTextures() {

		int shift = 1;

		double y = by;
		double x = bx;

		double maxWidth = Math.max(dx + 2 * dxTrack, dxRoof);
		double midX = maxWidth * 0.5;
		double deltaXBo = midX - dx * 0.5;
		double deltaXTu = midX - dxRoof * 0.5;
		double maxHeight = Math.max(dy, Math.max(dyTopTrack, dyTexture));

		y += dzTrack;
		buildLeftSideTrackTexture(x, y);
		x += dzTrack + shift;
		y = by;
		buildBackTextures(x, y);
		y += dzTrack;
		buildTopTextures(x, y, deltaXBo, deltaXTu);
		x += maxWidth + shift;
		buildRightSideTrackTexture(x, y);
		x += dzTrack + shift;
		addTRect(x, y + rearOverhang, dxTrack, dyBottomTrack);
		x += dxTrack;
		buildCannonTextures(x, y);
		x += wheelRadius * wheel_rays;
		x += +shift;
		addTRect(x, y, dx, dy);
		x += dx + shift;
		addTRect(x, y, dxRoof, dyRoof);
		x += dxRoof + shift;

		x = bx + dzTrack + shift;
		y += maxHeight;
		buildFrontTextures(x, y);

		IMG_WIDTH = (int) (2 * bx + dzTrack + dx + dzTrack + maxWidth + wheelRadius * wheel_rays + dxRoof + dxTrack
				+ 6 * shift);
		IMG_HEIGHT = (int) (2 * by + maxHeight + dzTrack + dzTrack);

	}

	private void buildCannonTextures(double x, double y) {
		for (int i = 0; i < wheel_rays; i++) {
			addTRect(x, y, wheelRadius, wheelWidth);
			x += wheelRadius;
		}

	}

	private void buildRightSideTrackTexture(double x, double y) {
		addTPoint(x, y, 0);
		addTPoint(x + dzTrack, y + rearOverhang, 0);
		addTPoint(x + dzTrack, y + rearOverhang + dyBottomTrack, 0);
		addTPoint(x, y + dyTopTrack, 0);

	}

	private void buildLeftSideTrackTexture(double x, double y) {
		addTPoint(x, y + rearOverhang, 0);
		addTPoint(x + dzTrack, y, 0);
		addTPoint(x + dzTrack, y + dyTopTrack, 0);
		addTPoint(x, y + rearOverhang + dyBottomTrack, 0);

	}

	private void buildTopTextures(double x, double y, double deltaXBo, double deltaXTu) {
		addTRect(x, y, dxTrack, dyTopTrack);
		addTRect(x + deltaXBo, y, dx, dy);
		addTRect(x + dxTrack + dx, y, dxTrack, dyTopTrack);
		addTRect(x + deltaXTu, y + dyRear, dxRoof, dyRoof);

	}

	private void buildFrontTextures(double x, double y) {

		addTRect(x, y, dxTrack, dzTrack);
		addTRect(x + dxTrack, y, dx, dz);
		addTRect(x + dxTrack + dx, y, dxTrack, dzTrack);

	}

	private void buildBackTextures(double x, double y) {
		addTRect(x, y, dxTrack, dzTrack);
		addTRect(x + dxTrack, y + (dzTrack - dz), dx, dz);
		addTRect(x + dxTrack + dx, y, dxTrack, dzTrack);

	}

	private void buildTracks() {

		Segments sLTop = new Segments(-dx * 0.5 - dxTrack, dxTrack, 0, dyTopTrack, 0, dzTrack);
		Segments sLBottom = new Segments(-dx * 0.5 - dxTrack, dxTrack, rearOverhang, dyBottomTrack, 0, dzTrack);

		tracks = new BPoint[2][2][2][2];

		tracks[0][0][0][0] = addBPoint(0, 0.0, 0, sLBottom);
		tracks[0][1][0][0] = addBPoint(1.0, 0.0, 0, sLBottom);
		tracks[0][0][1][0] = addBPoint(0.0, 1.0, 0, sLBottom);
		tracks[0][1][1][0] = addBPoint(1.0, 1.0, 0, sLBottom);

		tracks[0][0][0][1] = addBPoint(0.0, 0.0, 1.0, sLTop);
		tracks[0][1][0][1] = addBPoint(1.0, 0.0, 1.0, sLTop);
		tracks[0][0][1][1] = addBPoint(0.0, 1.0, 1.0, sLTop);
		tracks[0][1][1][1] = addBPoint(1.0, 1.0, 1.0, sLTop);

		Segments sRTop = new Segments(dx * 0.5, dxTrack, 0, dyTopTrack, 0, dzTrack);
		Segments sRBottom = new Segments(dx * 0.5, dxTrack, rearOverhang, dyBottomTrack, 0, dzTrack);

		tracks[1][0][0][0] = addBPoint(0.0, 0.0, 0, sRBottom);
		tracks[1][1][0][0] = addBPoint(1.0, 0.0, 0, sRBottom);
		tracks[1][0][1][0] = addBPoint(0.0, 1.0, 0, sRBottom);
		tracks[1][1][1][0] = addBPoint(1.0, 1.0, 0, sRBottom);

		tracks[1][0][0][1] = addBPoint(0.0, 0.0, 1.0, sRTop);
		tracks[1][1][0][1] = addBPoint(1.0, 0.0, 1.0, sRTop);
		tracks[1][0][1][1] = addBPoint(0.0, 1.0, 1.0, sRTop);
		tracks[1][1][1][1] = addBPoint(1.0, 1.0, 1.0, sRTop);

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

			int[] tTopTrack = (i == 0 ? tTopLeftTrack : tTopRightTrack);
			int[] tFrontrack = (i == 0 ? tFrontLeftTrack : tFrontRightTrack);
			int[] tbackTrack = (i == 0 ? tBackLeftTrack : tBackRightTrack);

			faces[counter++] = buildFace(Renderer3D.CAR_TOP, tracks[i][0][0][1], tracks[i][1][0][1], tracks[i][1][1][1],
					tracks[i][0][1][1], tTopTrack);
			faces[counter++] = buildFace(Renderer3D.CAR_LEFT, tracks[i][0][0][0], tracks[i][0][0][1],
					tracks[i][0][1][1], tracks[i][0][1][0], tLeftSideTrack);
			faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, tracks[i][1][0][1], tracks[i][1][0][0],
					tracks[i][1][1][0], tracks[i][1][1][1], tRightSideTrack);
			faces[counter++] = buildFace(Renderer3D.CAR_FRONT, tracks[i][0][1][0], tracks[i][0][1][1],
					tracks[i][1][1][1], tracks[i][1][1][0], tFrontrack);
			faces[counter++] = buildFace(Renderer3D.CAR_BACK, tracks[i][0][0][0], tracks[i][1][0][0],
					tracks[i][1][0][1], tracks[i][0][0][1], tbackTrack);
			faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, tracks[i][0][0][0], tracks[i][0][1][0],
					tracks[i][1][1][0], tracks[i][1][0][0], tTrackPath);

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

		Color bodyColor0 = new Color(162, 142, 93);
		Color bodyColor1 = new Color(150, 120, 56);
		Color tracksColor = new Color(123, 121, 136);
		Color turretColor = new Color(65, 69, 48);

		bufGraphics.setStroke(new BasicStroke(0.1f));

		bufGraphics.setColor(tracksColor);
		printTexturePolygon(bufGraphics, tLeftSideTrack);
		bufGraphics.setColor(tracksColor);
		printTexturePolygon(bufGraphics, tBackLeftTrack);
		bufGraphics.setColor(bodyColor0);
		printTexturePolygon(bufGraphics, tBackBody);
		bufGraphics.setColor(tracksColor);
		printTexturePolygon(bufGraphics, tBackRightTrack);
		bufGraphics.setColor(bodyColor0);

		printTexturePolygon(bufGraphics, tTopLeftTrack);
		bufGraphics.setColor(bodyColor0);
		printTexturePolygon(bufGraphics, tTopBody);
		bufGraphics.setColor(bodyColor0);
		printTexturePolygon(bufGraphics, tTopRightTrack);
		bufGraphics.setColor(turretColor);
		printTexturePolygon(bufGraphics, tTopTurret);
		bufGraphics.setColor(bodyColor1);
		printTexturePolygon(bufGraphics, tRightSideTrack);
		bufGraphics.setColor(tracksColor);
		printTexturePolygon(bufGraphics, tTrackPath);
		bufGraphics.setColor(turretColor);
		for (int i = 0; i < tCannon.length; i++) {
			printTexturePolygon(bufGraphics, tCannon[i]);
		}
		bufGraphics.setColor(bodyColor1);
		printTexturePolygon(bufGraphics, tBo[0]);
		bufGraphics.setColor(turretColor);
		printTexturePolygon(bufGraphics, tTu);

		bufGraphics.setColor(tracksColor);
		printTexturePolygon(bufGraphics, tFrontLeftTrack);
		bufGraphics.setColor(bodyColor0);
		printTexturePolygon(bufGraphics, tFrontBody);
		bufGraphics.setColor(tracksColor);
		printTexturePolygon(bufGraphics, tFrontRightTrack);
		bufGraphics.setColor(bodyColor0);

	}

}
