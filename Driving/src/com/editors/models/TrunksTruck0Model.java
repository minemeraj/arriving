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
public class TrunksTruck0Model extends Truck0Model {

    public static final String NAME = "Trunks truck";

    BPoint[][][] trunks = null;

    public TrunksTruck0Model(double dxFront, double dyfront, double dzFront, double dxRoof, double dyRoof,
            double dzRoof, double dxRear, double dyRear, double dzRear, double dxWagon, double dyWagon, double dzWagon,
            double rearOverhang, double frontOverhang, double rearOverhang1, double frontOverhang1, double wheelRadius,
            double wheelWidth, int wheel_rays) {
        super(dxRoof, dyRoof, dzRoof,dxFront, dyfront, dzFront,  dxRear, dyRear, dzRear, dxWagon, dyWagon, dzWagon,
                rearOverhang, frontOverhang, rearOverhang1, frontOverhang1, wheelRadius, wheelWidth, wheel_rays);
    }

    @Override
    public void initMesh() {

        initTexturesArrays();

        points = new Vector<Point3D>();
        texturePoints = new Vector();

        x0 = dxWagon * 0.5;
        nWagonSides=10;

        buildCabin();
        buildRear();
        buildWagon();
        buildWheels();

        buildTextures();

        int totWheelPolygon = wheel_rays + 2 * (wheel_rays - 2);
        int NUM_WHEEL_FACES = 4 * totWheelPolygon;

        // faces
        int NF =2 *(nzCab* nYcab - 1) ;
        // cabin roof
        NF += 8;
        NF += 2 + (nzBody - 1) * 4;
        NF += trunks.length * (nWagonSides + 2 * (nWagonSides-2));

        faces = new int[NF + NUM_WHEEL_FACES][3][4];

        int counter = 0;
        counter = buildBodyFaces(counter, nzBody);
        counter = buildWheelFaces(counter, totWheelPolygon);

    }

    @Override
    protected void buildWagon() {

        int nWagongMeridians=nWagonSides;

        trunks = new BPoint[3][nWagongMeridians][2];

        for (int i = 0; i < trunks.length; i++) {

            double radius = dxWagon / trunks.length * 0.5;
            double xx = i * 2 * radius + radius;
            trunks[i] = addYCylinder(xx, 0, dzRear + dxWagon * 0.5, radius, dyWagon, nWagongMeridians);
        }

    }

    @Override
    protected int buildWagonFaces(int counter) {

        int nWagonMeridians=nWagonSides;

        for (int m = 0; m < trunks.length; m++) {

            for (int i = 0; i < nWagonMeridians; i++) {
                faces[counter++] = buildFace(Renderer3D.CAR_TOP, trunks[m][i][0],
                        trunks[m][(i + 1) % trunks[m].length][0], trunks[m][(i + 1) % trunks[0].length][1],
                        trunks[m][i][1], tRe[0]);
            }

            for (int j = 1; j < nWagonMeridians - 1; j++) {

                BPoint p0 = trunks[m][0][1];
                BPoint p1 = trunks[m][(j + 1) % nWagonMeridians][1];
                BPoint p2 = trunks[m][j][1];

                faces[counter++] = buildFace(0, p0, p1, p2, tRe[0]);
            }

            for (int j = 1; j < nWagonMeridians - 1; j++) {

                BPoint p0 = trunks[m][0][0];
                BPoint p1 = trunks[m][j][0];
                BPoint p2 = trunks[m][(j + 1) % nWagonMeridians][0];

                faces[counter++] = buildFace(0, p0, p1, p2, tRe[0]);
            }
        }

        return counter;
    }

}
