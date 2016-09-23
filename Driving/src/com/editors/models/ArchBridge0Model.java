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
/**
 * One texture model
 * Summing up the best creation logic so far
 *
 * @author Administrator
 *
 */
public class ArchBridge0Model extends MeshModel{

    private int bx=10;
    private int by=10;

    private double dx = 0;
    private double dy = 0;
    private double dz = 0;

    private double dxFront = 0;
    private double dyFront = 0;
    private double dzFront = 0;

    private double dxRear = 0;
    private double dyRear = 0;
    private double dzRear = 0;

    private double dxRoof;
    private double dyRoof;
    private double dzRoof;

    double x0=0;
    double y0=0;
    double z0=0;

    //body textures
    protected int[][] bo= {{0,1,2,3}};
    //deck textures
    protected int[][] de= {{4,5,6,7}};

    private int[][][] faces;

    private BPoint[][][] body;

    private double dxTexture=100;
    private double dyTexture=100;
    private int archNY=10;

    public static final String NAME="Arch bridge";

    public ArchBridge0Model(
            double dx, double dy, double dz,
            double dxf, double dyf, double dzf,
            double dxr, double dyr,	double dzr,
            double dxRoof,double dyRoof,double dzRoof) {
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
    }


    @Override
    public void initMesh() {
        points=new Vector<Point3D>();
        texturePoints=new Vector<Point3D>();

        buildBody();

        buildTextures();

        //faces
        int NF=4*(archNY+1)+2;

        faces=new int[NF][3][4];

        int counter=0;
        counter=buildFaces(counter);

    }


    private int buildFaces(int counter) {

        for (int i = 0; i < archNY+1; i++) {

            faces[counter++]=buildFace(Renderer3D.CAR_TOP, body[0][i][1],body[1][i][1],body[1][i+1][1],body[0][i+1][1],de[0]);
            faces[counter++]=buildFace(Renderer3D.CAR_LEFT, body[0][i][0],body[0][i][1],body[0][i+1][1],body[0][i+1][0], bo[0]);
            faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, body[1][i][0],body[1][i+1][0],body[1][i+1][1],body[1][i][1], bo[0]);
            faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, body[0][i][0],body[0][i+1][0],body[1][i+1][0],body[1][i][0], bo[0]);

            if(i==0){

                faces[counter++]=buildFace(Renderer3D.CAR_BACK, body[0][i][0],body[1][i][0],body[1][i][1],body[0][i][1], bo[0]);

            }else if(i==archNY){

                faces[counter++]=buildFace(Renderer3D.CAR_FRONT, body[0][i+1][0],body[0][i+1][1],body[1][i+1][1],body[1][i+1][0], bo[0]);
            }

        }

        return counter;
    }


    private void buildTextures() {


        //Texture points
        int shift=1;

        double y=by;
        double x=bx;

        //body texture
        addTRect(x, y, dxTexture, dyTexture);

        x=bx+dx+shift;
        y=by;

        //deck texture
        addTRect(x, y, dxTexture, dyTexture);

        IMG_WIDTH=(int) (2*bx+2*dxTexture+shift);
        IMG_HEIGHT=(int) (2*by+dyTexture);

    }


    private void buildBody() {

        Segments s0=new Segments(0,dx*0.5,0,dyFront,0,dz);
        Segments s1=new Segments(0,dx*0.5,dyFront,dy,0,dz);
        Segments s2=new Segments(0,dx*0.5,dyFront+dy,dyFront,0,dz);

        body=new BPoint[2][archNY+2][2];
        double fMax=dzFront/dz;
        double a=-4*fMax;

        for (int i = 0; i < archNY+2; i++) {

            double yy=i*1.0/(archNY+1);

            double fz0=0;
            if(i>0 && i<archNY+1){
                double yr=(i-1)*1.0/(archNY-1);
                fz0=a*yr*(yr-1.0);
            }
            double fz1=1.0;

            Segments s=s1;
            if(i==0) {
                s=s0;
            }else if(i==archNY+1) {
                s=s2;
            }

            body[0][i][0]=addBPoint(-1.0,yy,fz0,s);
            body[1][i][0]=addBPoint(1.0,yy,fz0,s);
            body[0][i][1]=addBPoint(-1.0,yy,fz1,s);
            body[1][i][1]=addBPoint(1.0,yy,fz1,s);
        }





    }


    @Override
    public void printMeshData(PrintWriter pw) {

        super.printMeshData(pw);
        super.printFaces(pw, faces);

    }


    @Override
    public void printTexture(Graphics2D bufGraphics) {

        bufGraphics.setColor(Color.BLACK);
        bufGraphics.setStroke(new BasicStroke(0.1f));

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
