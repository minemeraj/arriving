package com.editors.models;

import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.Segments;
import com.main.Renderer3D;
/**
 * One texture model
 * Summing up the best creation logic so far
 *
 * @author Administrator
 *
 */
public class RaillTenderModel extends VehicleModel{


    private double wheelRadius;
    private double wheelWidth;
    protected int wheelRays;

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

    public static String NAME="RailWagon";

    //body textures
    protected int[][] tBo=null;
    //wheel textures
    protected int[][] tWh=null;

    private double dyTexture=200;
    private double dxTexture=200;


    public RaillTenderModel(
            double dx, double dy, double dz,
            double dxf, double dyf, double dzf,
            double dxr, double dyr,	double dzr,
            double dxRoof,double dyRoof,double dzRoof,
            double rearOverhang, double frontOverhang,
            double wheelRadius, double wheelWidth, int wheelRays
            ) {
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

        points=new Vector<Point3D>();
        texturePoints=new Vector<Point3D>();

        int pnx=2;
        int pny=2;
        int pnz=2;

        buildBody(pnx,pny,pnz);

        buildWagon();

        buildTextures();


        //faces
        int NF=6*4;

        int totWheelPolygon=wheelRays+2*(wheelRays-2);
        int NUM_WHEEL_FACES=8*totWheelPolygon;

        faces=new int[NF+NUM_WHEEL_FACES][3][4];

        int counter=0;

        counter=buildBodyFaces(counter,tWh[0][0],totWheelPolygon);

        counter=buildWagonFaces(counter,wagon);

    }


    protected void buildBody(int pnx, int pny, int pnz) {

        body=new BPoint[pnx][pny][pnz];

        Segments p0=new Segments(0,dx,0,dy,dzRear,dz);

        body[0][0][0]=addBPoint(-0.5,0.0,0,p0);
        body[1][0][0]=addBPoint(0.5,0.0,0,p0);
        body[0][0][1]=addBPoint(-0.5,0.0,1.0,p0);
        body[1][0][1]=addBPoint(0.5,0.0,1.0,p0);

        body[0][1][0]=addBPoint(-0.5,1.0,0,p0);
        body[1][1][0]=addBPoint(0.5,1.0,0,p0);
        body[0][1][1]=addBPoint(-0.5,1.0,1.0,p0);
        body[1][1][1]=addBPoint(0.5,1.0,1.0,p0);

        int bnx=2;
        int bny=2;
        int bnz=2;

        back=new BPoint[bnx][bny][bnz];

        Segments b0=new Segments(0,dxRear,rearOverhang,dyRear,0,dzRear);

        back[0][0][0]=addBPoint(-0.5,0.0,0,b0);
        back[1][0][0]=addBPoint(0.5,0.0,0,b0);
        back[0][0][1]=addBPoint(-0.5,0.0,1.0,b0);
        back[1][0][1]=addBPoint(0.5,0.0,1.0,b0);

        back[0][1][0]=addBPoint(-0.5,1.0,0,b0);
        back[1][1][0]=addBPoint(0.5,1.0,0,b0);
        back[0][1][1]=addBPoint(-0.5,1.0,1.0,b0);
        back[1][1][1]=addBPoint(0.5,1.0,1.0,b0);

        fWheelLeftFront=buildWheel(-dxRear*0.5-wheelWidth,rearOverhang,0,wheelRadius,wheelWidth,wheelRays);
        fWheelRightFront=buildWheel(dxRear*0.5,rearOverhang,0,wheelRadius,wheelWidth,wheelRays);

        fWheelLeftRear=buildWheel(-dxRear*0.5-wheelWidth,rearOverhang+dyRear,0,wheelRadius,wheelWidth,wheelRays);
        fWheelRightRear=buildWheel(dxRear*0.5,rearOverhang+dyRear,0,wheelRadius,wheelWidth,wheelRays);


        int fnx=2;
        int fny=2;
        int fnz=2;

        front=new BPoint[fnx][fny][fnz];

        double fy=dy-dyFront-frontOverhang;

        Segments f0=new Segments(0,dxFront,fy,dyFront,0,dzFront);

        front[0][0][0]=addBPoint(-0.5,0.0,0,f0);
        front[1][0][0]=addBPoint(0.5,0.0,0,f0);
        front[0][0][1]=addBPoint(-0.5,0.0,1.0,f0);
        front[1][0][1]=addBPoint(0.5,0.0,1.0,f0);

        front[0][1][0]=addBPoint(-0.5,1.0,0,f0);
        front[1][1][0]=addBPoint(0.5,1.0,0,f0);
        front[0][1][1]=addBPoint(-0.5,1.0,1.0,f0);
        front[1][1][1]=addBPoint(0.5,1.0,1.0,f0);


        rWheelLeftFront=buildWheel(-dxFront*0.5-wheelWidth,fy,0,wheelRadius,wheelWidth,wheelRays);
        rWheelRightFront=buildWheel(dxFront*0.5,fy,0,wheelRadius,wheelWidth,wheelRays);

        rWheelLeftRear=buildWheel(-dxFront*0.5-wheelWidth,fy+dyFront,0,wheelRadius,wheelWidth,wheelRays);
        rWheelRightRear=buildWheel(dxFront*0.5,fy+dyFront,0,wheelRadius,wheelWidth,wheelRays);

    }


    protected int buildBodyFaces(int counter, int firstWheelTexturePoint, int totWheelPolygon) {

        faces[counter++]=buildFace(Renderer3D.CAR_TOP, body[0][0][1],body[1][0][1],body[1][1][1],body[0][1][1], tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_LEFT, body[0][0][0],body[0][0][1],body[0][1][1],body[0][1][0], tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, body[1][0][0],body[1][1][0],body[1][1][1],body[1][0][1], tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_FRONT, body[0][1][0],body[0][1][1],body[1][1][1],body[1][1][0], tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_BACK, body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1], tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, body[0][0][0],body[0][1][0],body[1][1][0],body[1][0][0], tBo[0]);

        faces[counter++]=buildFace(Renderer3D.CAR_TOP, back[0][0][1],back[1][0][1],back[1][1][1],back[0][1][1], tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_LEFT, back[0][0][0],back[0][0][1],back[0][1][1],back[0][1][0], tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, back[1][0][0],back[1][1][0],back[1][1][1],back[1][0][1], tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_FRONT, back[0][1][0],back[0][1][1],back[1][1][1],back[1][1][0], tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_BACK, back[0][0][0],back[1][0][0],back[1][0][1],back[0][0][1],tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, back[0][0][0],back[0][1][0],back[1][1][0],back[1][0][0],tBo[0]);

        faces[counter++]=buildFace(Renderer3D.CAR_TOP, front[0][0][1],front[1][0][1],front[1][1][1],front[0][1][1], tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_LEFT, front[0][0][0],front[0][0][1],front[0][1][1],front[0][1][0],tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, front[1][0][0],front[1][1][0],front[1][1][1],front[1][0][1], tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_FRONT, front[0][1][0],front[0][1][1],front[1][1][1],front[1][1][0], tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_BACK, front[0][0][0],front[1][0][0],front[1][0][1],front[0][0][1], tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, front[0][0][0],front[0][1][0],front[1][1][0],front[1][0][0],tBo[0]);

        //front bogie
        counter=buildWheelFaces(counter,
                firstWheelTexturePoint,
                totWheelPolygon,
                fWheelLeftFront,fWheelRightFront,fWheelLeftRear,fWheelRightRear);

        //rear bogie
        counter=buildWheelFaces(counter,
                firstWheelTexturePoint,
                totWheelPolygon,
                rWheelLeftFront,rWheelRightFront,rWheelLeftRear,rWheelRightRear);

        return counter;
    }


    protected void buildWagon() {

        int rnx=2;
        int rny=2;
        int rnz=2;

        wagon=new BPoint[rnx][rny][rnz];

        double rdy=(dy-dyRoof)*0.5;

        Segments r0=new Segments(0,dxRoof,rdy,dyRoof,dzRear+dz,dzRoof);

        wagon[0][0][0]=addBPoint(-0.5,0.0,0,r0);
        wagon[1][0][0]=addBPoint(0.5,0.0,0,r0);
        wagon[0][0][1]=addBPoint(-0.5,0.0,1.0,r0);
        wagon[1][0][1]=addBPoint(0.5,0.0,1.0,r0);

        wagon[0][1][0]=addBPoint(-0.5,1.0,0,r0);
        wagon[1][1][0]=addBPoint(0.5,1.0,0,r0);
        wagon[0][1][1]=addBPoint(-0.5,1.0,1.0,r0);
        wagon[1][1][1]=addBPoint(0.5,1.0,1.0,r0);


    }


    protected int buildWagonFaces(int counter, BPoint[][][] wagon) {

        faces[counter++]=buildFace(Renderer3D.CAR_TOP, wagon[0][0][1],wagon[1][0][1],wagon[1][1][1],wagon[0][1][1], 0, 1, 2, 3);
        faces[counter++]=buildFace(Renderer3D.CAR_LEFT, wagon[0][0][0],wagon[0][0][1],wagon[0][1][1],wagon[0][1][0], 0, 1, 2, 3);
        faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, wagon[1][0][0],wagon[1][1][0],wagon[1][1][1],wagon[1][0][1], 0, 1, 2, 3);
        faces[counter++]=buildFace(Renderer3D.CAR_FRONT, wagon[0][1][0],wagon[0][1][1],wagon[1][1][1],wagon[1][1][0], 0, 1, 2, 3);
        faces[counter++]=buildFace(Renderer3D.CAR_BACK, wagon[0][0][0],wagon[1][0][0],wagon[1][0][1],wagon[0][0][1], 0, 1, 2, 3);
        faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, wagon[0][0][0],wagon[0][1][0],wagon[1][1][0],wagon[1][0][0], 0, 1, 2, 3);

        return counter;

    }


    protected void buildTextures() {

        double x=bx;
        double y=by;

        addTRect(x,y,dxTexture,dyTexture);

        //wheel texture, a black square for simplicity:

        x+=dxTexture;
        y=by;

        addTRect(x,y,wheelWidth,wheelWidth);

        IMG_WIDTH=(int) (2*bx+dxTexture);
        IMG_HEIGHT=(int) (2*by+dyTexture);
    }

    private int buildWheelFaces(

            int counter,
            int firstWheelTexturePoint,
            int totWheelPolygon,
            BPoint[][] wheelLeftFront,
            BPoint[][] wheelRightFront,
            BPoint[][] wheelLeftRear,
            BPoint[][] wheelRightRear
            ) {

        int[][][] wFaces = buildWheelFaces(wheelLeftFront,firstWheelTexturePoint);
        for (int i = 0; i < totWheelPolygon; i++) {
            faces[counter++]=wFaces[i];
        }

        wFaces = buildWheelFaces(wheelRightFront,firstWheelTexturePoint);
        for (int i = 0; i < totWheelPolygon; i++) {
            faces[counter++]=wFaces[i];
        }

        wFaces = buildWheelFaces(wheelLeftRear,firstWheelTexturePoint);
        for (int i = 0; i <totWheelPolygon; i++) {
            faces[counter++]=wFaces[i];
        }

        wFaces = buildWheelFaces(wheelRightRear,firstWheelTexturePoint);
        for (int i = 0; i < totWheelPolygon; i++) {
            faces[counter++]=wFaces[i];
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
            if(tPoints.length==4) {
                printTexturePolygon(bufGraphics, tPoints[0],tPoints[1],tPoints[2],tPoints[3]);
            } else if(tPoints.length==3) {
                printTexturePolygon(bufGraphics, tPoints[0],tPoints[1],tPoints[2]);
            }

        }


    }


}
