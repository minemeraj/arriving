package com.editors.models;

import java.util.Vector;

import com.Point3D;

/**
 *
 * TWO TEXTURES
 *
 * @author Administrator
 *
 */
public class Yacht0Model extends Ship0Model{

    public static String NAME="Yacht";

    double dxTexture=200;
    double dyTexture=200;

    public Yacht0Model(
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
        buildMainBridge();

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

        faces=new int[NF][3][4];


        //build the hull
        int counter=0;
        counter=buildHullfaces(counter,nxHull,nyHull,nyCastle);
        counter=buildMainBridgeFaces(counter);
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



}
