package com.editors.models;

import java.awt.Color;
import java.awt.Graphics2D;
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
public class OceanLiner0Model extends Ship0Model{


    public static String NAME="Ocean liner";

    double dxTexture=200;
    double dyTexture=200;

    BPoint[][][] funnels=null;
    private int funnel_number=4;
    private int funnel_meridians=10;
    private int funnel_parallels=3;
    private double funnel_height=60;
    private double funnel_radius=15;

    public OceanLiner0Model(
            double dx, double dy, double dz,
            double dxFront, double dyFront, double dzFront,
            double dxRear, double dyRear, double dzRear,
            double dxRoof, double dyRoof, double dzRoof
            ) {

        super(dx, dy, dz,
                dxFront, dyFront, dzFront,
                dxRear, dyRear, dzRear,
                dxRoof, dyRoof, dzRoof);

    }


    @Override
    public void initMesh() {
        points=new Vector<Point3D>();
        texturePoints=new Vector<Point3D>();

        buildHull();
        buildAfterCastle();
        buildForeCastle();
        buildMainBridge();
        buildFunnels();

        buildTextures();


        //faces
        //hull
        int NF=(nxHull-1)*(nyHull-1)+2;
        //hull back closures
        NF+=(nxHull-1)/2;
        //deck
        NF+=nyHull-1;
        //main bridge
        NF+=6;
        //castles
        NF+=6+(4*(nyCastle-1)+1);
        //funnels
        NF+=funnel_number*(funnel_meridians*(funnel_parallels-1)+funnel_meridians);

        faces=new int[NF][3][4];

        //build the hull
        int counter=0;
        counter=buildFaces(counter,nxHull,nyHull,nyCastle,hull,mainBridge,afterCastle,foreCastle);

    }

    @Override
    protected void buildMainBridge() {
        Segments s3=new Segments(x0,dxRoof,y0+dyRear,dyRoof,z0+dz,dzRoof);

        mainBridge=new BPoint[2][4];

        mainBridge[0][0]=addBPoint(0.0,0.0,0,s3);
        mainBridge[0][1]=addBPoint(1.0,0.0,0,s3);
        mainBridge[0][2]=addBPoint(1.0,1.0,0,s3);
        mainBridge[0][3]=addBPoint(0.0,1.0,0,s3);

        mainBridge[1][0]=addBPoint(0.0,0.0,1.0,s3);
        mainBridge[1][1]=addBPoint(1.0,0.0,1.0,s3);
        mainBridge[1][2]=addBPoint(1.0,1.0,1.0,s3);
        mainBridge[1][3]=addBPoint(0.0,1.0,1.0,s3);

    }

    private void buildFunnels() {


        funnels=new BPoint[funnel_number][funnel_parallels][funnel_meridians];

        double fx=dxRoof*0.5;
        for (int i = 0; i < funnels.length; i++) {

            double fy=y0+dyRear+(i+1)*dyRoof/(funnel_number+1);
            double fz=z0+dz+dzRoof;
            funnels[i]=buildFunnel(fx,fy,fz,funnel_height,funnel_radius,funnel_parallels,funnel_meridians);
        }

    }

    private BPoint[][] buildFunnel(double x0, double y0, double z0,double funnel_height, double funnel_radius, int funnel_parallels2, int funnel_meridians2) {

        BPoint[][] funnel=new BPoint[funnel_parallels2][funnel_meridians2];
        double dTeta=Math.PI*2.0/(funnel_meridians2);
        double dz=funnel_height/(funnel_parallels2-1);

        for (int k = 0; k < funnel_parallels2; k++) {
            double zz=z0+dz*k;

            for (int i = 0; i < funnel_meridians2; i++) {
                double teta=dTeta*i;
                double xx=x0+funnel_radius*Math.cos(teta);
                double yy=y0+funnel_radius*Math.sin(teta);
                funnel[k][i]=addBPoint(xx, yy, zz);
            }
        }

        return funnel;
    }

    @Override
    protected void buildTextures() {

        int shift=1;

        //Texture points

        double y=by;
        double x=bx;

        //hull
        addTRect(x, y, dxTexture, dyTexture);

        x+=shift+dxTexture;
        //main deck
        addTRect(x, y, dxTexture, dyTexture);

        IMG_WIDTH=(int) (2*bx+dxTexture+dxTexture+shift);
        IMG_HEIGHT=(int) (2*by+dyTexture);

    }

    @Override
    protected int buildFaces(int counter, int nx, int ny,int nyc, BPoint[][] hull, BPoint[][] mainBridge, BPoint[][] afterCastle, BPoint[][][] foreCastle) {

        counter=super.buildFaces(counter, nx, ny, nyc, hull, mainBridge, afterCastle, foreCastle);

        for (int i = 0; i < funnels.length; i++) {
            for (int k = 0; k < funnel_parallels-1; k++) {
                for (int j = 0; j < funnel_meridians; j++) {
                    faces[counter++]=buildFace(Renderer3D.CAR_LEFT,funnels[i][k][j],funnels[i][k][(j+1)%funnel_meridians],funnels[i][k+1][(j+1)%funnel_meridians],funnels[i][k+1][j],d[0]);
                }
            }

            for (int j = 0; j < funnel_meridians; j++) {
                int k=funnel_parallels-1;
                faces[counter++]=buildFace(Renderer3D.CAR_TOP,funnels[i][k][0],funnels[i][k][j],funnels[i][k][(j+1)%funnel_meridians],d[0]);
            }
        }

        return counter;
    }


    @Override
    public void printTexture(Graphics2D bufGraphics) {


        bufGraphics.setColor(Color.RED);
        printTexturePolygon(bufGraphics, h[0]);

        bufGraphics.setColor(Color.BLACK);
        printTexturePolygon(bufGraphics, d[0]);
    }

}
