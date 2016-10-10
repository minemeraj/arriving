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
public class ATeamVanModel extends PickupModel {

    public static final String NAME = "A-Team van";

    public ATeamVanModel(double dx, double dy, double dz, double dxFront, double dyfront, double dzFront, double dxRoof,
            double dyRoof, double dzRoof, double wheelRadius, double wheelWidth, int wheel_rays) {
        super(dx, dy, dz, dxFront, dyfront, dzFront, dxRoof, dyRoof, dzRoof, wheelRadius, wheelWidth, wheel_rays);
    }

    @Override
    public void initMesh() {
        points = new Vector<Point3D>();
        texturePoints = new Vector<Point3D>();

        x0 = dxRoof * 0.5;

        buildCabin();

        buildBody();

        int nzWagon = 2;
        buildWagon(nzWagon);

        buildTextures();

        buildWheels();

        int totWheelPolygon = wheel_rays + 2 * (wheel_rays - 2);
        int NUM_WHEEL_FACES = 4 * totWheelPolygon;

        // faces
        int NF = 2 + (2 + (nzCab - 1)) * (nYcab - 1) * 2;
        //cabin roof
        NF += 7;
        NF += 2 + (nzBody - 1) * 4;
        NF += 2 + (nzWagon - 1) * 4;

        faces = new int[NF + NUM_WHEEL_FACES][3][4];

        int counter = 0;
        counter = buildBodyFaces(counter, nzBody, nzWagon);
        counter = buildWheelFaces(counter, totWheelPolygon);

    }

    @Override
    protected void buildCabin() {
        super.buildCabin();
    }

    @Override
    protected int buildCabinFaces(int counter, int nYcab, int nzCab) {
        return super.buildCabinFaces(counter, nYcab, nzCab);
    }

    @Override
    protected void buildWagon(int nzWagon) {

        Segments s0 = new Segments(x0 - dxRoof * 0.5, dxRoof, y0, dyRoof, z0 + dz, dzRoof);

        wagon = new BPoint[nzWagon][4];
        wagon[0][0] = addBPoint(0.0, 0.0, 0.0, s0);
        wagon[0][1] = addBPoint(1.0, 0.0, 0.0, s0);
        wagon[0][2] = addBPoint(1.0, 1.0, 0.0, s0);
        wagon[0][3] = addBPoint(0.0, 1.0, 0.0, s0);

        wagon[1][0] = addBPoint(0.0, 0.0, 1.0, s0);
        wagon[1][1] = addBPoint(1.0, 0.0, 1.0, s0);
        wagon[1][2] = addBPoint(1.0, 1.0, 1.0, s0);
        wagon[1][3] = addBPoint(0.0, 1.0, 1.0, s0);
    }

    /**
     *
     * BUILD WAGON BY Z SECTIONS
     *
     * @param nzBody
     * @return
     */
    @Override
    protected int buildWagonFaces(int counter, int nzWagon) {

        faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, wagon[0][0], wagon[0][3], wagon[0][2], wagon[0][1], wa[0]);

        for (int k = 0; k < nzWagon - 1; k++) {

            faces[counter++] = buildFace(Renderer3D.CAR_LEFT, wagon[k][0], wagon[k + 1][0], wagon[k + 1][3],
                    wagon[k][3], wa[0]);
            faces[counter++] = buildFace(Renderer3D.CAR_BACK, wagon[k][0], wagon[k][1], wagon[k + 1][1],
                    wagon[k + 1][0], wa[0]);
            faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, wagon[k][1], wagon[k][2], wagon[k + 1][2],
                    wagon[k + 1][1], wa[0]);
            faces[counter++] = buildFace(Renderer3D.CAR_FRONT, wagon[k][2], wagon[k][3], wagon[k + 1][3],
                    wagon[k + 1][2], wa[0]);

        }

        faces[counter++] = buildFace(Renderer3D.CAR_TOP, wagon[nzWagon - 1][0], wagon[nzWagon - 1][1],
                wagon[nzWagon - 1][2], wagon[nzWagon - 1][3], wa[0]);

        return counter;
    }


}
