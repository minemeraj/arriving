package com.editors.models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.Segments;
import com.main.Renderer3D;

/**
 *
 * TWO TEXTURES
 *
 * @author Administrator
 *
 */
public class Ship0Model extends VehicleModel {

    protected int c = 0;
    protected int[][] tH = null;
    protected int[][] tD = null;

    public static final String NAME = "Ship";

    protected BPoint[][] hull;
    protected BPoint[][] afterCastle;
    protected BPoint[][][] mainDecks;
    protected BPoint[][][] foreCastle;

    protected int nxHull = 9;
    protected int nyHull = 5;
    protected int nyCastle = 3;

    public Ship0Model(double dx, double dy, double dz, double dxFront, double dyFront, double dzFront, double dxRear,
            double dyRear, double dzRear, double dxRoof, double dyRoof, double dzRoof, double rearOverhang,
            double frontOverhang, double rearOverhang1, double frontOverhang1) {
        super();
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;

        this.dxFront = dxFront;
        this.dyFront = dyFront;
        this.dzFront = dzFront;

        this.dxRear = dxRear;
        this.dyRear = dyRear;
        this.dzRear = dzRear;

        this.dxRoof = dxRoof;
        this.dyRoof = dyRoof;
        this.dzRoof = dzRoof;

        this.rearOverhang = rearOverhang;
        this.frontOverhang = frontOverhang;

        this.rearOverhang1 = rearOverhang1;
        this.frontOverhang1 = frontOverhang1;
    }

    @Override
    public void initMesh() {

        int c = 0;
        c = initDoubleArrayValues(tH = new int[1][4], c);
        c = initDoubleArrayValues(tD = new int[1][4], c);

        points = new Vector<Point3D>();
        texturePoints = new Vector<Point3D>();

        buildHull();
        buildAfterCastle();
        buildForeCastle();
        buildMainDecks();

        buildTextures();

        // faces
        // hull
        int NF = (nxHull - 1) * (nyHull - 1) + 2;
        // hull back closures
        NF += (nxHull - 1) / 2;
        // deck
        NF += nyHull - 1;
        // main bridge
        NF += 6;
        // castles
        NF += 6 + (4 * (nyCastle - 1) + 1);

        faces = new int[NF][3][4];

        // build the hull
        int counter = 0;
        counter = buildFaces(counter, nxHull, nyHull, nyCastle);
        counter = buildMainDecksFaces(counter);
    }

    protected void buildMainDecks() {
        Segments s3 = new Segments(x0, dxRoof, y0 + dyRear - dyRoof, dyRoof, z0 + dz + dzRear, dzRoof);

        mainDecks = new BPoint[1][2][4];

        mainDecks[0][0][0] = addBPoint(0.0, 0.0, 0, s3);
        mainDecks[0][0][1] = addBPoint(1.0, 0.0, 0, s3);
        mainDecks[0][0][2] = addBPoint(1.0, 1.0, 0, s3);
        mainDecks[0][0][3] = addBPoint(0.0, 1.0, 0, s3);

        mainDecks[0][1][0] = addBPoint(0.0, 0.0, 1.0, s3);
        mainDecks[0][1][1] = addBPoint(1.0, 0.0, 1.0, s3);
        mainDecks[0][1][2] = addBPoint(1.0, 1.0, 1.0, s3);
        mainDecks[0][1][3] = addBPoint(0.0, 1.0, 1.0, s3);

    }

    protected void buildForeCastle() {

        foreCastle = new BPoint[nyCastle][2][2];

        for (int i = 0; i < nyCastle; i++) {

            int e = nyCastle - i;

            foreCastle[i][0][0] = hull[nyHull - e][0];
            foreCastle[i][1][0] = hull[nyHull - e][nxHull - 1];
            foreCastle[i][1][1] = addBPoint(hull[nyHull - e][nxHull - 1].x, hull[nyHull - e][nxHull - 1].y,
                    hull[nyHull - e][nxHull - 1].z + dzFront);
            foreCastle[i][0][1] = addBPoint(hull[nyHull - e][0].x, hull[nyHull - e][0].y,
                    hull[nyHull - e][0].z + dzFront);
        }

    }

    protected void buildAfterCastle() {
        afterCastle = new BPoint[2][4];
        afterCastle[0][0] = hull[0][0];
        afterCastle[0][1] = hull[0][nxHull - 1];
        afterCastle[0][2] = hull[1][nxHull - 1];
        afterCastle[0][3] = hull[1][0];

        afterCastle[1][0] = addBPoint(hull[0][0].x, hull[0][0].y, hull[0][0].z + dzRear);
        afterCastle[1][1] = addBPoint(hull[0][nxHull - 1].x, hull[0][nxHull - 1].y, hull[0][nxHull - 1].z + dzRear);
        afterCastle[1][2] = addBPoint(hull[1][nxHull - 1].x, hull[1][nxHull - 1].y, hull[1][nxHull - 1].z + dzRear);
        afterCastle[1][3] = addBPoint(hull[1][0].x, hull[1][0].y, hull[1][0].z + dzRear);

    }

    protected void buildHull() {

        double y1 = dyRear / dy;
        double y2 = (dy - dyFront) / dy;
        double y3 = (1.0 + y2) * 0.5;

        Segments s0 = new Segments(x0, dx, y0, dy, z0, dz);

        hull = new BPoint[nyHull][nxHull];

        hull[0][0] = addBPoint(0, 0, 1.0, s0);
        hull[0][1] = addBPoint(0.125, 0, 0.35, s0);
        hull[0][2] = addBPoint(0.25, 0, 0.2, s0);
        hull[0][3] = addBPoint(0.375, 0, 0.05, s0);
        hull[0][4] = addBPoint(0.5, 0, 0, s0);
        hull[0][5] = addBPoint(0.625, 0, 0.05, s0);
        hull[0][6] = addBPoint(0.75, 0, 0.2, s0);
        hull[0][7] = addBPoint(0.875, 0, 0.35, s0);
        hull[0][8] = addBPoint(1.0, 0, 1.0, s0);

        hull[1][0] = addBPoint(0, y1, 1.0, s0);
        hull[1][1] = addBPoint(0.125, y1, 0.35, s0);
        hull[1][2] = addBPoint(0.25, y1, 0.2, s0);
        hull[1][3] = addBPoint(0.375, y1, 0.05, s0);
        hull[1][4] = addBPoint(0.5, y1, 0, s0);
        hull[1][5] = addBPoint(0.625, y1, 0.05, s0);
        hull[1][6] = addBPoint(0.75, y1, 0.2, s0);
        hull[1][7] = addBPoint(0.875, y1, 0.35, s0);
        hull[1][8] = addBPoint(1.0, y1, 1.0, s0);

        hull[2][0] = addBPoint(0, y2, 1.0, s0);
        hull[2][1] = addBPoint(0.125, y2, 0.35, s0);
        hull[2][2] = addBPoint(0.25, y2, 0.2, s0);
        hull[2][3] = addBPoint(0.375, y2, 0.05, s0);
        hull[2][4] = addBPoint(0.5, y2, 0, s0);
        hull[2][5] = addBPoint(0.625, y2, 0.05, s0);
        hull[2][6] = addBPoint(0.75, y2, 0.2, s0);
        hull[2][7] = addBPoint(0.875, y2, 0.35, s0);
        hull[2][8] = addBPoint(1.0, y2, 1.0, s0);

        hull[3][0] = addBPoint(0, y3, 1.0, s0);
        hull[3][1] = addBPoint(0.125, y3, 0.35, s0);
        hull[3][2] = addBPoint(0.25, y3, 0.2, s0);
        hull[3][3] = addBPoint(0.375, y3, 0.05, s0);
        hull[3][4] = addBPoint(0.5, y3, 0, s0);
        hull[3][5] = addBPoint(0.625, y3, 0.05, s0);
        hull[3][6] = addBPoint(0.75, y3, 0.2, s0);
        hull[3][7] = addBPoint(0.875, y3, 0.35, s0);
        hull[3][8] = addBPoint(1.0, y3, 1.0, s0);

        // hull converging at the bow
        hull[4][0] = addBPoint(0.5, 1.0, 1.0, s0);
        hull[4][1] = addBPoint(0.5, 1.0, 0.35, s0);
        hull[4][2] = addBPoint(0.5, 1.0, 0.2, s0);
        hull[4][3] = addBPoint(0.5, 1.0, 0.05, s0);
        hull[4][4] = addBPoint(0.5, 1.0, 0, s0);
        hull[4][5] = hull[4][3];
        hull[4][6] = hull[4][2];
        hull[4][7] = hull[4][1];
        hull[4][8] = hull[4][0];

    }

    protected void buildTextures() {

        double dxt = 200;
        double dyt = 200;

        int shift = 1;

        // Texture points

        double y = by;
        double x = bx;

        // hull
        addTRect(x, y, dxt, dyt);

        x += shift + dxt;
        // main deck
        addTRect(x, y, dxt, dyt);

        IMG_WIDTH = (int) (2 * bx + dxt + dxt + shift);
        IMG_HEIGHT = (int) (2 * by + dyt);

    }

    protected void buildHullTextures(int nx, int ny,double x,double y) {

        for (int i = 0; i < ny - 1; i++) {
            for (int j = 0; j < nx - 1; j++) {
                addTPoint(x + hull[j][i].x, y + hull[j][i].y, 0);
                addTPoint(x + hull[j][i].x, y + hull[j][i].y, 0);
                addTPoint(x + hull[j][i].x, y + hull[j][i].y, 0);
                addTPoint(x + hull[j][i].x, y + hull[j][i].y, 0);
            }
        }
    }

    protected int buildFaces(int counter, int nx, int ny, int nyc) {

        counter = buildHullfaces(counter, nx, ny, nyc);
        counter = buildForeCastlefaces(counter, nx, ny, nyc);
        counter = buildAfterCastlefaces(counter, nx, ny, nyc);
        return counter;
    }

    protected int buildAfterCastlefaces(int counter, int nx, int ny, int nyc) {
        //// after castle
        faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, afterCastle[0][0], afterCastle[0][3], afterCastle[0][2],
                afterCastle[0][1], tH[0]);

        for (int k = 0; k < 2 - 1; k++) {

            faces[counter++] = buildFace(Renderer3D.CAR_LEFT, afterCastle[k][0], afterCastle[k + 1][0],
                    afterCastle[k + 1][3], afterCastle[k][3], tH[0]);
            faces[counter++] = buildFace(Renderer3D.CAR_BACK, afterCastle[k][0], afterCastle[k][1],
                    afterCastle[k + 1][1], afterCastle[k + 1][0], tH[0]);
            faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, afterCastle[k][1], afterCastle[k][2],
                    afterCastle[k + 1][2], afterCastle[k + 1][1], tH[0]);
            faces[counter++] = buildFace(Renderer3D.CAR_FRONT, afterCastle[k][2], afterCastle[k][3],
                    afterCastle[k + 1][3], afterCastle[k + 1][2], tD[0]);

        }

        faces[counter++] = buildFace(Renderer3D.CAR_TOP, afterCastle[2 - 1][0], afterCastle[2 - 1][1],
                afterCastle[2 - 1][2], afterCastle[2 - 1][3], tD[0]);
        return counter;
    }

    protected int buildForeCastlefaces(int counter, int nx, int ny, int nyc) {

        /// fore castle, converging at the bow
        faces[counter++] = buildFace(Renderer3D.CAR_BACK, foreCastle[0][0][0], foreCastle[0][1][0], foreCastle[0][1][1],
                foreCastle[0][0][1], tD[0]);
        for (int k = 0; k < nyc - 1; k++) {

            if (foreCastle[k + 1][1][0].getIndex() != foreCastle[k][1][0].getIndex()) {
                faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, foreCastle[k][0][0], foreCastle[k + 1][0][0],
                        foreCastle[k + 1][1][0], foreCastle[k][1][0], tH[0]);
            } else {
                faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, foreCastle[k][0][0], foreCastle[k + 1][0][0],
                        foreCastle[k + 1][1][0], tH[0]);
            }

            faces[counter++] = buildFace(Renderer3D.CAR_LEFT, foreCastle[k][0][0], foreCastle[k][0][1],
                    foreCastle[k + 1][0][1], foreCastle[k + 1][0][0], tH[0]);
            faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, foreCastle[k][1][0], foreCastle[k + 1][1][0],
                    foreCastle[k + 1][1][1], foreCastle[k][1][1], tH[0]);

            if (foreCastle[k + 1][1][1].getIndex() != foreCastle[k + 1][0][1].getIndex()) {
                faces[counter++] = buildFace(Renderer3D.CAR_TOP, foreCastle[k][0][1], foreCastle[k][1][1],
                        foreCastle[k + 1][1][1], foreCastle[k + 1][0][1], tD[0]);
            } else {
                faces[counter++] = buildFace(Renderer3D.CAR_TOP, foreCastle[k][0][1], foreCastle[k][1][1],
                        foreCastle[k + 1][0][1], tD[0]);
            }
        }
        // faces[counter++]=buildFace(Renderer3D.CAR_FRONT,
        // foreCastle[0][2],foreCastle[0][3],foreCastle[0+1][3],foreCastle[0+1][2],
        // h0, h1, h2, h3);
        return counter;
    }

    protected int buildHullfaces(int counter, int nx, int ny, int nyc) {
        int middle = (nx - 1) / 2;

        for (int i = 0; i < ny - 1; i++) {
            for (int j = 0; j < nx - 1; j++) {
                if (i == ny - 2 && j == middle - 1) {

                    faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, hull[i][j], hull[i + 1][j], hull[i + 1][j + 1],
                            tH[0]);
                    faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, hull[i][j], hull[i + 1][j + 1], hull[i][j + 1],
                            tH[0]);

                } else if (i == ny - 2 && j == middle) {

                    faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, hull[i][j], hull[i + 1][j], hull[i][j + 1],
                            tH[0]);
                    faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, hull[i][j + 1], hull[i + 1][j],
                            hull[i + 1][j + 1], tH[0]);

                } else {
                    faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, hull[i][j], hull[i + 1][j], hull[i + 1][j + 1],
                            hull[i][j + 1], tH[0]);
                }
            }

        }

        // closing back hull
        faces[counter++] = buildFace(Renderer3D.CAR_BACK, hull[0][0], hull[0][1], hull[0][nx - 2], hull[0][nx - 1],
                tH[0]);
        faces[counter++] = buildFace(Renderer3D.CAR_BACK, hull[0][1], hull[0][2], hull[0][nx - 3], hull[0][nx - 2],
                tH[0]);
        faces[counter++] = buildFace(Renderer3D.CAR_BACK, hull[0][2], hull[0][3], hull[0][nx - 4], hull[0][nx - 3],
                tH[0]);
        faces[counter++] = buildFace(Renderer3D.CAR_BACK, hull[0][3], hull[0][4], hull[0][nx - 4], tH[0]);

        // build the main deck
        for (int i = 0; i < ny - 1; i++) {

            BPoint l0 = hull[i][0];
            BPoint l1 = hull[i][nx - 1];
            BPoint l2 = hull[i + 1][nx - 1];
            BPoint l3 = hull[i + 1][0];

            if (l2.getIndex() == l3.getIndex()) {
                faces[counter++] = buildFace(Renderer3D.CAR_TOP, l0, l1, l3, tD[0]);
            } else {
                faces[counter++] = buildFace(Renderer3D.CAR_TOP, l0, l1, l2, l3, tD[0]);
            }

        }
        return counter;
    }

    /**
     *
     * BUILDING THE BRIDGE BY Z SECTIONS
     *
     * @param counter
     * @return
     */
    protected int buildMainDecksFaces(int counter) {

        int numDecks = mainDecks.length;

        for (int i = 0; i < numDecks; i++) {

            faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, mainDecks[i][0][0], mainDecks[i][0][3],
                    mainDecks[i][0][2], mainDecks[i][0][1], tD[0]);

            int nz = mainDecks[i].length;

            for (int k = 0; k < nz - 1; k++) {

                faces[counter++] = buildFace(Renderer3D.CAR_LEFT, mainDecks[i][k][0], mainDecks[i][k + 1][0],
                        mainDecks[i][k + 1][3], mainDecks[i][k][3], tD[0]);
                faces[counter++] = buildFace(Renderer3D.CAR_BACK, mainDecks[i][k][0], mainDecks[i][k][1],
                        mainDecks[i][k + 1][1], mainDecks[i][k + 1][0], tD[0]);
                faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, mainDecks[i][k][1], mainDecks[i][k][2],
                        mainDecks[i][k + 1][2], mainDecks[i][k + 1][1], tD[0]);
                faces[counter++] = buildFace(Renderer3D.CAR_FRONT, mainDecks[i][k][2], mainDecks[i][k][3],
                        mainDecks[i][k + 1][3], mainDecks[i][k + 1][2], tD[0]);

            }

            faces[counter++] = buildFace(Renderer3D.CAR_TOP, mainDecks[i][nz - 1][0], mainDecks[i][nz - 1][1],
                    mainDecks[i][nz - 1][2], mainDecks[i][nz - 1][3], tD[0]);
        }

        return counter;
    }

    /**
     * BUILDING THE BRIDGE BY Y SECTIONS
     *
     * @param counter
     * @return
     */
    protected int buildMainDecksYFaces(int counter) {

        int numDecks = mainDecks.length;

        for (int i = 0; i < numDecks; i++) {

            int ny = mainDecks[i].length;

            faces[counter++] = buildFace(Renderer3D.CAR_BACK, mainDecks[i][0][0], mainDecks[i][0][1],
                    mainDecks[i][0][2], mainDecks[i][0][3], tD[0]);

            for (int j = 0; j < ny - 1; j++) {

                faces[counter++] = buildFace(Renderer3D.CAR_LEFT, mainDecks[i][j + 1][0], mainDecks[i][j][0],
                        mainDecks[i][j][3], mainDecks[i][j + 1][3], tD[0]);
                faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, mainDecks[i][j][0], mainDecks[i][j + 1][0],
                        mainDecks[i][j + 1][1], mainDecks[i][j][1], tD[0]);
                faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, mainDecks[i][j][1], mainDecks[i][j + 1][1],
                        mainDecks[i][j + 1][2], mainDecks[i][j][2], tD[0]);
                faces[counter++] = buildFace(Renderer3D.CAR_TOP, mainDecks[i][j][2], mainDecks[i][j + 1][2],
                        mainDecks[i][j + 1][3], mainDecks[i][j][3], tD[0]);
            }

            faces[counter++] = buildFace(Renderer3D.CAR_FRONT, mainDecks[i][ny - 1][0], mainDecks[i][ny - 1][3],
                    mainDecks[i][ny - 1][2], mainDecks[i][ny - 1][1], tD[0]);

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

        bufGraphics.setColor(Color.RED);
        printTexturePolygon(bufGraphics, tH[0]);

        bufGraphics.setColor(Color.BLACK);
        printTexturePolygon(bufGraphics, tD[0]);
    }

}
