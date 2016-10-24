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

public class F10Model extends VehicleModel {

    private double wheelRadius;
    private double wheelWidth;
    private int wheel_rays;

    private double dyTexture = 100;
    private double dxTexture = 100;

    private BPoint[][][] frontSpoiler;
    private BPoint[][][] backSpoiler;
    private BPoint[][] wheelLeftFront;
    private BPoint[][] wheelRightFront;
    private BPoint[][] wheelLeftRear;
    private BPoint[][] wheelRightRear;

    public static String NAME = "F1 Car";


    private int frontNY = 3;
    private double dyBackSpoiler;
    private double dyFrontSpoiler;

    private int[] tBackSpoiler=null;
    private int[] tBackRear=null;
    private int[] tTopRear=null;
    private int[] tTopRoof=null;
    private int[] tLeftBody=null;
    private int[] tTopBody=null;
    private int[] tRightBody=null;
    private int[][] tTopFront=null;
    private int[] tFrontSpoiler=null;
    private int[][] tBo=null;
    private int[] tWh=null;

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

        dyBackSpoiler = dyRear * 0.737;
        dyFrontSpoiler = dyFront * 0.282;
    }

    @Override
    public void initMesh() {


        int c = 0;
        c = initSingleArrayValues(tBackSpoiler = new int[4], c);
        c = initSingleArrayValues(tBackRear = new int[4], c);
        c = initSingleArrayValues(tTopRear = new int[4], c);
        c = initSingleArrayValues(tLeftBody = new int[4], c);
        c = initSingleArrayValues(tTopBody = new int[4], c);
        c = initSingleArrayValues(tRightBody = new int[4], c);
        c = initDoubleArrayValues(tTopFront = new int[2][4], c);
        c = initSingleArrayValues(tFrontSpoiler = new int[4], c);
        c = initDoubleArrayValues(tBo = new int[1][4], c);
        c = initSingleArrayValues(tWh = new int[4], c);

        points = new Vector<Point3D>();
        texturePoints = new Vector<Point3D>();

        buildTextures();
        int totBlockTexturesPoints = 4;

        buildBody();

        buildWheels();

        int totWheelPolygon = wheel_rays + 2 * (wheel_rays - 2);
        int NUM_WHEEL_FACES = 4 * totWheelPolygon;

        // faces
        int NF = 6 * 5;
        // front
        NF += 2 + (frontNY - 1) * 4;

        int counter = 0;

        faces = new int[NF + NUM_WHEEL_FACES][3][4];

        counter = buildBodyFaces(counter);

        counter = buildWheelFaces(counter, totWheelPolygon);

    }

    private void buildWheels() {

        wheelZ = 0.795 * wheelRadius;

        double wz = wheelZ;
        double wx = wheelWidth;

        wheelLeftFront = buildWheel(-dx * 0.5 - wx * 0.5, dyRear + dy + (dyFront - frontOverhang), wz, wheelRadius,
                wheelWidth, wheel_rays);
        wheelRightFront = buildWheel(dx * 0.5 - wx * 0.5, dyRear + dy + (dyFront - frontOverhang), wz, wheelRadius,
                wheelWidth, wheel_rays);
        wheelLeftRear = buildWheel(-dx * 0.5 - wx * 0.5, rearOverhang, wz, wheelRadius, wheelWidth, wheel_rays);
        wheelRightRear = buildWheel(dx * 0.5 - wx * 0.5, rearOverhang, wz, wheelRadius, wheelWidth, wheel_rays);

    }

    private void buildBody() {

        Segments rs = new Segments(0, dxRear * 0.5, 0, dyBackSpoiler, dzRear, dzRoof);

        backSpoiler = new BPoint[2][2][2];

        backSpoiler[0][0][0] = addBPoint(-1.0, 0.0, 0, rs);
        backSpoiler[1][0][0] = addBPoint(1.0, 0.0, 0, rs);
        backSpoiler[0][1][0] = addBPoint(-1.0, 1.0, 0, rs);
        backSpoiler[1][1][0] = addBPoint(1.0, 1.0, 0, rs);

        backSpoiler[0][0][1] = addBPoint(-1.0, 0.0, 1.0, rs);
        backSpoiler[1][0][1] = addBPoint(1.0, 0.0, 1.0, rs);
        backSpoiler[0][1][1] = addBPoint(-1.0, 1.0, 1.0, rs);
        backSpoiler[1][1][1] = addBPoint(1.0, 1.0, 1.0, rs);

        Segments s0 = new Segments(0, dxRear * 0.5, rearOverhang1, dyRear, 0, dzRear);

        back = new BPoint[2][2][2];

        back[0][0][0] = addBPoint(-1.0, 0.0, 0, s0);
        back[1][0][0] = addBPoint(1.0, 0.0, 0, s0);
        back[0][1][0] = addBPoint(-1.0, 1.0, 0, s0);
        back[1][1][0] = addBPoint(1.0, 1.0, 0, s0);

        back[0][0][1] = addBPoint(-1.0, 0.0, 1.0, s0);
        back[1][0][1] = addBPoint(1.0, 0.0, 1.0, s0);
        back[0][1][1] = addBPoint(-1.0, 1.0, 1.0, s0);
        back[1][1][1] = addBPoint(1.0, 1.0, 1.0, s0);

        Segments s1 = new Segments(0, dx * 0.5, rearOverhang1 + dyRear, dy, 0, dz);

        body = new BPoint[2][2][2];

        body[0][0][0] = addBPoint(-1.0, 0.0, 0, s1);
        body[1][0][0] = addBPoint(1.0, 0.0, 0, s1);
        body[0][1][0] = addBPoint(-1.0, 1.0, 0, s1);
        body[1][1][0] = addBPoint(1.0, 1.0, 0, s1);

        body[0][0][1] = addBPoint(-1.0, 0.0, 1.0, s1);
        body[1][0][1] = addBPoint(1.0, 0.0, 1.0, s1);
        body[0][1][1] = addBPoint(-1.0, 1.0, 1.0, s1);
        body[1][1][1] = addBPoint(1.0, 1.0, 1.0, s1);

        Segments s2 = new Segments(0, dxFront * 0.5, rearOverhang1 + dyRear + dy, dyFront, 0, dzFront);

        front = new BPoint[2][frontNY][2];

        front[0][0][0] = body[0][1][0];
        front[1][0][0] = body[1][1][0];
        front[0][0][1] = body[0][1][1];
        front[1][0][1] = body[1][1][1];

        double middleFrontDx = 0.5 * (dxFront + dx) * 0.5 / dxFront;

        front[0][1][0] = addBPoint(-middleFrontDx, 0.5, 0, s2);
        front[1][1][0] = addBPoint(middleFrontDx, 0.5, 0, s2);
        front[0][1][1] = addBPoint(-middleFrontDx, 0.5, 1.0, s2);
        front[1][1][1] = addBPoint(middleFrontDx, 0.5, 1.0, s2);

        front[0][2][0] = addBPoint(-1.0, 1.0, 0, s2);
        front[1][2][0] = addBPoint(1.0, 1.0, 0, s2);
        front[0][2][1] = addBPoint(-1.0, 1.0, 1.0, s2);
        front[1][2][1] = addBPoint(1.0, 1.0, 1.0, s2);

        Segments s3 = new Segments(0, dxRoof * 0.5, rearOverhang1 + dyRear, dyRoof, dz, dzRoof);

        roof = new BPoint[2][2][2];

        roof[0][0][0] = addBPoint(-1.0, 0.0, 0, s3);
        roof[1][0][0] = addBPoint(1.0, 0.0, 0, s3);
        roof[0][1][0] = addBPoint(-1.0, 1.0, 0, s3);
        roof[1][1][0] = addBPoint(1.0, 1.0, 0, s3);

        roof[0][0][1] = addBPoint(-1.0, 0.0, 1.0, s3);
        roof[1][0][1] = addBPoint(1.0, 0.0, 1.0, s3);
        roof[0][1][1] = addBPoint(-1.0, 1.0, 1.0, s3);
        roof[1][1][1] = addBPoint(1.0, 1.0, 1.0, s3);

        Segments fs = new Segments(0, dx * 0.5, rearOverhang1 + dyRear + dy + dyFront - dyFrontSpoiler, dyFrontSpoiler,
                0, dzFront);

        frontSpoiler = new BPoint[2][2][2];

        frontSpoiler[0][0][0] = addBPoint(-1.0, 0.0, 0, fs);
        frontSpoiler[1][0][0] = addBPoint(1.0, 0.0, 0, fs);
        frontSpoiler[0][1][0] = addBPoint(-1.0, 1.0, 0, fs);
        frontSpoiler[1][1][0] = addBPoint(1.0, 1.0, 0, fs);

        frontSpoiler[0][0][1] = addBPoint(-1.0, 0.0, 0.5, fs);
        frontSpoiler[1][0][1] = addBPoint(1.0, 0.0, 0.5, fs);
        frontSpoiler[0][1][1] = addBPoint(-1.0, 1.0, 0.25, fs);
        frontSpoiler[1][1][1] = addBPoint(1.0, 1.0, 0.25, fs);
    }

    private void buildTextures() {

        int shift = 1;

        // Texture points

        double y = by;
        double x = bx;

        double midX = bx + (dz + dx + dz) * 0.5;
        double deltaXRo = (midX - dxRoof * 0.5 - bx);
        double deltaXRe = (midX - dxRear * 0.5 - bx);
        double deltaXFr = (midX - dxFront * 0.5 - bx);

        // rear and top
        addTRect(x + deltaXRe, y, dxRear, dyBackSpoiler);
        y += dyBackSpoiler;
        addTRect(x + deltaXRe, y, dxRear, dzRear);
        y += dzRear;
        addTRect(x + deltaXRe, y, dxRear, dyRear);
        y += dyRear;
        //roof over the body
        addTRect(x+deltaXRo, y, dxRoof, dyRoof);
        addTRect(x, y, dz, dy);
        addTRect(x + dz, y, dx, dy);
        addTRect(x + dz + dx, y, dz, dy);
        y += dy;
        addTTrapezium(x + dz, y, dx, dxFront, dyFront * 0.5);
        addTRect(x + deltaXFr, y + dyFront * 0.5, dxFront, dyFront * 0.5);
        y += dyFront;
        addTRect(x + dz, y, dx, dyFrontSpoiler);

        // body texture
        x += dz + dx + dz + shift;
        y = by;
        addTRect(x, y, dxTexture, dyTexture);
        // wheel texture, a black square for simplicity:
        x += dxTexture + shift;
        y = by;
        addTRect(x, y, wheelWidth, wheelWidth);

        IMG_WIDTH = (int) (2 * bx + dx + 2 * dz + dxTexture + wheelWidth + 2 * shift);
        IMG_HEIGHT = (int) (2 * by + dyBackSpoiler + dzRear + dyRear + dy + dyFront + dyFrontSpoiler);

    }

    private int buildWheelFaces(int counter, int totWheelPolygon) {

        ///// WHEELS

        int[][][] wFaces = buildWheelFaces(wheelLeftFront, tWh);
        for (int i = 0; i < totWheelPolygon; i++) {
            faces[counter++] = wFaces[i];
        }

        wFaces = buildWheelFaces(wheelRightFront, tWh);
        for (int i = 0; i < totWheelPolygon; i++) {
            faces[counter++] = wFaces[i];
        }

        wFaces = buildWheelFaces(wheelLeftRear, tWh);
        for (int i = 0; i < totWheelPolygon; i++) {
            faces[counter++] = wFaces[i];
        }

        wFaces = buildWheelFaces(wheelRightRear, tWh);
        for (int i = 0; i < totWheelPolygon; i++) {
            faces[counter++] = wFaces[i];
        }
        /////
        return 0;
    }

    private int buildBodyFaces(int counter) {

        faces[counter++] = buildFace(Renderer3D.CAR_TOP, backSpoiler[0][0][1], backSpoiler[1][0][1],
                backSpoiler[1][1][1], backSpoiler[0][1][1], tBackSpoiler);
        faces[counter++] = buildFace(Renderer3D.CAR_LEFT, backSpoiler[0][0][0], backSpoiler[0][0][1],
                backSpoiler[0][1][1], backSpoiler[0][1][0], tBo[0]);
        faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, backSpoiler[1][0][0], backSpoiler[1][1][0],
                backSpoiler[1][1][1], backSpoiler[1][0][1], tBo[0]);
        faces[counter++] = buildFace(Renderer3D.CAR_BACK, backSpoiler[0][1][0], backSpoiler[0][1][1],
                backSpoiler[1][1][1], backSpoiler[1][1][0], tBo[0]);
        faces[counter++] = buildFace(Renderer3D.CAR_BACK, backSpoiler[0][0][0], backSpoiler[1][0][0],
                backSpoiler[1][0][1], backSpoiler[0][0][1], tBo[0]);
        faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, backSpoiler[0][0][0], backSpoiler[0][1][0],
                backSpoiler[1][1][0], backSpoiler[1][0][0], tBo[0]);

        faces[counter++] = buildFace(Renderer3D.CAR_TOP, back[0][0][1], back[1][0][1], back[1][1][1], back[0][1][1],
                tTopRear);
        faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, back[0][0][0], back[0][0][1], back[0][1][1], back[0][1][0],
                tBo[0]);
        faces[counter++] = buildFace(Renderer3D.CAR_LEFT, back[1][0][0], back[1][1][0], back[1][1][1], back[1][0][1],
                tBo[0]);
        faces[counter++] = buildFace(Renderer3D.CAR_FRONT, back[0][1][0], back[0][1][1], back[1][1][1], back[1][1][0],
                tBo[0]);
        faces[counter++] = buildFace(Renderer3D.CAR_BACK, back[0][0][0], back[1][0][0], back[1][0][1], back[0][0][1],
                tBackRear);
        faces[counter++] = buildFace(Renderer3D.CAR_TOP, back[0][0][0], back[0][1][0], back[1][1][0], back[1][0][0],
                tBo[0]);

        faces[counter++] = buildFace(Renderer3D.CAR_TOP, body[0][0][1], body[1][0][1], body[1][1][1], body[0][1][1],
                tTopBody);
        faces[counter++] = buildFace(Renderer3D.CAR_LEFT, body[0][0][0], body[0][0][1], body[0][1][1], body[0][1][0],
                tLeftBody);
        faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, body[1][0][0], body[1][1][0], body[1][1][1], body[1][0][1],
                tRightBody);
        faces[counter++] = buildFace(Renderer3D.CAR_FRONT, body[0][1][0], body[0][1][1], body[1][1][1], body[1][1][0],
                tBo[0]);
        faces[counter++] = buildFace(Renderer3D.CAR_BACK, body[0][0][0], body[1][0][0], body[1][0][1], body[0][0][1],
                tBo[0]);
        faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, body[0][0][0], body[0][1][0], body[1][1][0], body[1][0][0],
                tBo[0]);

        faces[counter++] = buildFace(Renderer3D.CAR_BACK, front[0][0][0], front[1][0][0], front[1][0][1],
                front[0][0][1], tBo[0]);
        for (int j = 0; j < frontNY - 1; j++) {
            faces[counter++] = buildFace(Renderer3D.CAR_TOP, front[0][j][1], front[1][j][1], front[1][j + 1][1],
                    front[0][j + 1][1], tTopFront[j]);
            faces[counter++] = buildFace(Renderer3D.CAR_LEFT, front[0][j][0], front[0][j][1], front[0][j + 1][1],
                    front[0][j + 1][0], tBo[0]);
            faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, front[1][j][0], front[1][j + 1][0], front[1][j + 1][1],
                    front[1][j][1], tBo[0]);
            faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, front[0][j][0], front[0][j + 1][0], front[1][j + 1][0],
                    front[1][j][0], tBo[0]);
        }
        faces[counter++] = buildFace(Renderer3D.CAR_FRONT, front[0][frontNY - 1][0], front[0][frontNY - 1][1],
                front[1][frontNY - 1][1], front[1][frontNY - 1][0], tBo[0]);

        faces[counter++] = buildFace(Renderer3D.CAR_TOP, frontSpoiler[0][0][1], frontSpoiler[1][0][1],
                frontSpoiler[1][1][1], frontSpoiler[0][1][1], tFrontSpoiler);
        faces[counter++] = buildFace(Renderer3D.CAR_LEFT, frontSpoiler[0][0][0], frontSpoiler[0][0][1],
                frontSpoiler[0][1][1], frontSpoiler[0][1][0], tBo[0]);
        faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, frontSpoiler[1][0][0], frontSpoiler[1][1][0],
                frontSpoiler[1][1][1], frontSpoiler[1][0][1], tBo[0]);
        faces[counter++] = buildFace(Renderer3D.CAR_BACK, frontSpoiler[0][1][0], frontSpoiler[0][1][1],
                frontSpoiler[1][1][1], frontSpoiler[1][1][0], tBo[0]);
        faces[counter++] = buildFace(Renderer3D.CAR_BACK, frontSpoiler[0][0][0], frontSpoiler[1][0][0],
                frontSpoiler[1][0][1], frontSpoiler[0][0][1], tBo[0]);
        faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, frontSpoiler[0][0][0], frontSpoiler[0][1][0],
                frontSpoiler[1][1][0], frontSpoiler[1][0][0], tBo[0]);

        faces[counter++] = buildFace(Renderer3D.CAR_TOP, roof[0][0][1], roof[1][0][1], roof[1][1][1], roof[0][1][1],
                tTopRoof);
        faces[counter++] = buildFace(Renderer3D.CAR_LEFT, roof[0][0][0], roof[0][0][1], roof[0][1][1], roof[0][1][0],
                tBo[0]);
        faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, roof[1][0][0], roof[1][1][0], roof[1][1][1], roof[1][0][1],
                tBo[0]);
        faces[counter++] = buildFace(Renderer3D.CAR_BACK, roof[0][1][0], roof[0][1][1], roof[1][1][1], roof[1][1][0],
                tBo[0]);
        faces[counter++] = buildFace(Renderer3D.CAR_BACK, roof[0][0][0], roof[1][0][0], roof[1][0][1], roof[0][0][1],
                tBo[0]);
        faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, roof[0][0][0], roof[0][1][0], roof[1][1][0], roof[1][0][0],
                tBo[0]);

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

        bufGraphics.setColor(new Color(0, 0, 0));
        printTexturePolygon(bufGraphics, tBackSpoiler);
        printTexturePolygon(bufGraphics, tBackRear);
        printTexturePolygon(bufGraphics, tTopRear);
        printTexturePolygon(bufGraphics, tTopRoof);
        printTexturePolygon(bufGraphics, tLeftBody);
        printTexturePolygon(bufGraphics, tTopBody);
        printTexturePolygon(bufGraphics, tRightBody);
        printTexturePolygon(bufGraphics, tTopFront[0]);
        printTexturePolygon(bufGraphics, tTopFront[1]);
        printTexturePolygon(bufGraphics, tFrontSpoiler);

        bufGraphics.setColor(new Color(255, 40, 1));
        printTexturePolygon(bufGraphics, tBo[0]);

        bufGraphics.setColor(new Color(0, 0, 0));
        printTexturePolygon(bufGraphics, tWh);

    }

}
