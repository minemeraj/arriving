package com.editors.models;

import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.main.Renderer3D;

/**
 * One texture model Summing up the best creation logic so far
 *
 * @author Administrator
 *
 */
public class Camper0Model extends Truck0Model {

	public static String NAME = "Camper";

	public Camper0Model(double dx, double dy, double dz, double dxFront, double dyfront, double dzFront, double dxRoof,
			double dyRoof, double dzRoof, double wheelRadius, double wheelWidth, int wheel_rays) {
		super(dx, dy, dz, dxFront, dyfront, dzFront, dxRoof, dyRoof, dzRoof, wheelRadius, wheelWidth, wheel_rays);
	}

	@Override
	public void initMesh() {
		points = new Vector<Point3D>();
		texturePoints = new Vector();

		x0 = dxRoof * 0.5;

		buildCabin();

		buildBody();

		int nWagonMeridians = 10;
		buildWagon(nWagonMeridians);

		buildTextures();

		buildWheels();

		int totWheelPolygon = wheel_rays + 2 * (wheel_rays - 2);
		int NUM_WHEEL_FACES = 4 * totWheelPolygon;

		// faces
		int NF = 2 + (2 + (nzCab - 1)) * (nYcab - 1) * 2;
		NF += 2 + (nzBody - 1) * 4;
		NF += 2 + (nWagonMeridians);

		faces = new int[NF + NUM_WHEEL_FACES][3][4];

		int counter = 0;
		counter = buildBodyFaces(counter, nzBody, nWagonMeridians);
		counter = buildWheelFaces(counter, totWheelPolygon);

		IMG_WIDTH = (int) (2 * bx + dx + wheelWidth);
		IMG_HEIGHT = (int) (2 * by + dy);

	}

	@Override
	protected void buildWagon(int nWagongMeridians) {

		wagon = addYCylinder(x0, 0, dz + dxRoof * 0.5, dxRoof * 0.5, dyRoof, nWagongMeridians);

	}

	@Override
	protected int buildWagonFaces(int counter, int nWagonMeridians, BPoint[][] wagon) {

		for (int i = 0; i < wagon.length; i++) {

			faces[counter++] = buildFace(Renderer3D.CAR_TOP, wagon[i][0], wagon[(i + 1) % wagon.length][0],
					wagon[(i + 1) % wagon.length][1], wagon[i][1], bo[0]);
		}

		return counter;
	}

}
