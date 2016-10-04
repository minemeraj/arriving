package com.editors.models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.main.Renderer3D;
/**
 *
 * TWO TEXTURES
 *
 * @author Administrator
 *
 */
public class Sailer0Model extends Ship0Model{


    public static String NAME="Sailer";

    double dxTexture=200;
    double dyTexture=200;

    double rearOverhang;
    double frontOverhang;

    BPoint[][][] masts=null;
    private int mast_number=3;
    private int mast_meridians=0;
    private int mast_parallels=3;
    private double mast_height=0;
    private double mast_radius=0;

    protected int[][] fun={{8,9,10,11}};

    public Sailer0Model(
            double dx, double dy, double dz,
            double dxFront, double dyFront, double dzFront,
            double dxRear, double dyRear, double dzRear,
            double dxRoof, double dyRoof, double dzRoof,
            double rearOverhang, double frontOverhang,
            double funnel_radius,
            double funnel_height,
            int funnel_meridians
            ) {

        super(dx, dy, dz,
                dxFront, dyFront, dzFront,
                dxRear, dyRear, dzRear,
                dxRoof, dyRoof, dzRoof);

        this.rearOverhang=rearOverhang;
        this.frontOverhang=frontOverhang;

        this.mast_radius=funnel_radius;
        this.mast_height=funnel_height;
        this.mast_meridians=funnel_meridians;
    }


    @Override
    public void initMesh() {
        points=new Vector<Point3D>();
        texturePoints=new Vector<Point3D>();

        buildHull();
        buildAfterCastle();
        buildForeCastle();
        buildMasts();

        buildTextures();


        //faces
        //hull
        int NF=(nxHull-1)*(nyHull-1)+2;
        //hull back closures
        NF+=(nxHull-1)/2;
        //deck
        NF+=nyHull-1;
        //castles
        NF+=6+(4*(nyCastle-1)+1);
        //funnels
        NF+=mast_number*(mast_meridians*(mast_parallels-1)+mast_meridians);

        faces=new int[NF][3][4];

        //build the hull
        int counter=0;
        counter=buildFaces(counter,nxHull,nyHull,nyCastle);

    }


    private void buildMasts() {

        masts=new BPoint[mast_number][mast_parallels][mast_meridians];

        double fx=dx*0.5;
        double dyMasts=dy;
        for (int i = 0; i < masts.length; i++) {

            double fy=y0+(i+1)*dyMasts/(mast_number+1);
            double fz=z0+dz;
            masts[i]=buildFunnel(fx,fy,fz,mast_height,mast_radius,mast_parallels,mast_meridians);
        }

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

        x+=shift+dxTexture;
        //main deck
        addTRect(x, y, dxTexture, dyTexture);

        IMG_WIDTH=(int) (2*bx+3*dxTexture+2*shift);
        IMG_HEIGHT=(int) (2*by+dyTexture);

    }

    @Override
    protected int buildFaces(int counter, int nx, int ny,int nyc) {

        counter=super.buildFaces(counter, nx, ny, nyc);

        for (int i = 0; i < masts.length; i++) {
            for (int k = 0; k < mast_parallels-1; k++) {
                for (int j = 0; j < mast_meridians; j++) {
                    faces[counter++]=buildFace(Renderer3D.CAR_LEFT,masts[i][k][j],masts[i][k][(j+1)%mast_meridians],masts[i][k+1][(j+1)%mast_meridians],masts[i][k+1][j],fun[0]);
                }
            }

            for (int j = 0; j < mast_meridians; j++) {
                int k=mast_parallels-1;
                faces[counter++]=buildFace(Renderer3D.CAR_TOP,masts[i][k][0],masts[i][k][j],masts[i][k][(j+1)%mast_meridians],fun[0]);
            }
        }

        return counter;
    }



    @Override
    public void printTexture(Graphics2D bufGraphics) {


        bufGraphics.setColor(Color.BLACK);
        printTexturePolygon(bufGraphics, h[0]);

        bufGraphics.setColor(Color.WHITE);
        printTexturePolygon(bufGraphics, d[0]);

        bufGraphics.setColor(new Color(255,168,16));
        printTexturePolygon(bufGraphics, fun[0]);
    }

}
