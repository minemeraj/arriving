package com.editors.models;

import com.BPoint;
import com.Segments;
import com.main.Renderer3D;

public class Jeep0Model extends PickupModel {

    public static final String NAME = "Jeep";

    public Jeep0Model(double dx, double dy, double dz,
            double dxFront, double dyfront, double dzFront,
            double dxRoof,double dyRoof, double dzRoof,
            double rearOverhang, double frontOverhang,
            double rearOverhang1,double frontOverhang1,
            double wheelRadius, double wheelWidth, int wheel_rays) {

        super(dx, dy, dz,
                dxFront, dyfront, dzFront,
                dxRoof, dyRoof, dzRoof,
                rearOverhang, frontOverhang,
                rearOverhang1,frontOverhang1,
                wheelRadius, wheelWidth, wheel_rays);

    }

    @Override
    protected void buildWagon(int nzWagon) {

        Segments s0 = new Segments(x0 - dxRoof * 0.5, dxRoof, y0, dyRoof, z0 + dzRear, dzRoof);

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
