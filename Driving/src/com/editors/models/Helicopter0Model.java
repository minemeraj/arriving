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
public class Helicopter0Model extends VehicleModel{

    //body textures
    protected int[][] tBo= null;

    private BPoint[][][] body;
    private BPoint[][][] turret;
    private BPoint[][] cannon_barrel;
    private BPoint[][][][] propellers;
    private BPoint[][][][] shoes;

    private double dxTexture=200;
    private double dyTexture=200;

    public static String NAME="Helicopter";

    public Helicopter0Model(
            double dx, double dy, double dz,
            double dxf, double dyf, double dzf,
            double dxr, double dyr,	double dzr,
            double dxRoof,double dyRoof,double dzRoof,
            double rearOverhang, double frontOverhang,
            double rearOverhang1, double frontOverhang1
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

        this.rearOverhang1 = rearOverhang1;
        this.frontOverhang1 = frontOverhang1;
    }


    @Override
    public void initMesh() {

        int c=0;
        c = initDoubleArrayValues(tBo = new int[1][4], c);

        points=new Vector<Point3D>();
        texturePoints=new Vector<Point3D>();

        buildBody();
        buildPropellers();
        buildShoes();
        buildTextures();

        //faces
        int NF=6*2;
        //propellers
        NF+=6*propellers.length;
        //shoes
        NF+=6*shoes.length;

        faces=new int[NF+cannon_barrel.length][3][4];

        int counter=0;
        counter=buildFaces(counter);
        counter=buildPropellersFaces(counter);
        counter=buildShoesFaces(counter);
    }



    private int buildFaces(int counter) {

        faces[counter++]=buildFace(Renderer3D.CAR_TOP, body[0][0][1],body[1][0][1],body[1][1][1],body[0][1][1],tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_LEFT, body[0][0][0],body[0][0][1],body[0][1][1],body[0][1][0], tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, body[1][0][0],body[1][1][0],body[1][1][1],body[1][0][1], tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_FRONT, body[0][1][0],body[0][1][1],body[1][1][1],body[1][1][0], tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_BACK, body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1], tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, body[0][0][0],body[0][1][0],body[1][1][0],body[1][0][0], tBo[0]);


        faces[counter++]=buildFace(Renderer3D.CAR_TOP, turret[0][0][1],turret[1][0][1],turret[1][1][1],turret[0][1][1],tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_LEFT, turret[0][0][0],turret[0][0][1],turret[0][1][1],turret[0][1][0],tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, turret[1][0][0],turret[1][1][0],turret[1][1][1],turret[1][0][1],tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_FRONT, turret[0][1][0],turret[0][1][1],turret[1][1][1],turret[1][1][0],tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_BACK, turret[0][0][0],turret[1][0][0],turret[1][0][1],turret[0][0][1],tBo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, turret[0][0][0],turret[0][1][0],turret[1][1][0],turret[1][0][0],tBo[0]);


        for (int i = 0; i < cannon_barrel.length; i++) {

            faces[counter++]=buildFace(Renderer3D.CAR_TOP,
                    cannon_barrel[i][0],
                    cannon_barrel[(i+1)%cannon_barrel.length][0],
                    cannon_barrel[(i+1)%cannon_barrel.length][1],
                    cannon_barrel[i][1],
                    0, 1, 2, 3);
        }

        return counter;
    }


    private void buildTextures() {


        //Texture points

        double y=by;
        double x=bx;

        addTPoint(x,y,0);
        addTPoint(x+dxTexture,y,0);
        addTPoint(x+dxTexture, y+dyTexture,0);
        addTPoint(x,y+dyTexture,0);

        IMG_WIDTH=(int) (2*bx+dxTexture);
        IMG_HEIGHT=(int) (2*by+dyTexture);

    }


    private void buildBody() {

        Segments s0=new Segments(0,dx*0.5,0,dy,0,dz);

        body=new BPoint[2][2][2];

        body[0][0][0]=addBPoint(-1.0,0.0,0,s0);
        body[1][0][0]=addBPoint(1.0,0.0,0,s0);
        body[0][1][0]=addBPoint(-1.0,1.0,0,s0);
        body[1][1][0]=addBPoint(1.0,1.0,0,s0);

        body[0][0][1]=addBPoint(-1.0,0.0,1.0,s0);
        body[1][0][1]=addBPoint(1.0,0.0,1.0,s0);
        body[0][1][1]=addBPoint(-1.0,1.0,1.0,s0);
        body[1][1][1]=addBPoint(1.0,1.0,1.0,s0);



        Segments s1=new Segments(0,dxRoof*0.5,dyRear,dyRoof,dz,dzRoof);

        turret=new BPoint[2][2][2];

        turret[0][0][0]=addBPoint(-1.0,0.0,0,s1);
        turret[1][0][0]=addBPoint(1.0,0.0,0,s1);
        turret[0][1][0]=addBPoint(-1.0,1.0,0,s1);
        turret[1][1][0]=addBPoint(1.0,1.0,0,s1);

        turret[0][0][1]=addBPoint(-1.0,0.0,1.0,s1);
        turret[1][0][1]=addBPoint(1.0,0.0,1.0,s1);
        turret[0][1][1]=addBPoint(-1.0,1.0,1.0,s1);
        turret[1][1][1]=addBPoint(1.0,1.0,1.0,s1);


        cannon_barrel = addYCylinder(0,dyRoof+dyRear,dz+dzRear*0.5,10,100,10);

    }



    private int buildPropellersFaces(int counter) {
        for (int i = 0; i < propellers.length; i++) {

            BPoint[][][] prop = propellers[i];
            faces[counter++]=buildFace(Renderer3D.CAR_TOP, prop[0][0][1],prop[1][0][1],prop[1][1][1],prop[0][1][1],tBo[0]);
            faces[counter++]=buildFace(Renderer3D.CAR_LEFT, prop[0][0][0],prop[0][0][1],prop[0][1][1],prop[0][1][0], tBo[0]);
            faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, prop[1][0][0],prop[1][1][0],prop[1][1][1],prop[1][0][1], tBo[0]);
            faces[counter++]=buildFace(Renderer3D.CAR_FRONT, prop[0][1][0],prop[0][1][1],prop[1][1][1],prop[1][1][0], tBo[0]);
            faces[counter++]=buildFace(Renderer3D.CAR_BACK, prop[0][0][0],prop[1][0][0],prop[1][0][1],prop[0][0][1], tBo[0]);
            faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, prop[0][0][0],prop[0][1][0],prop[1][1][0],prop[1][0][0], tBo[0]);

        }
        return counter;
    }


    private void buildPropellers() {

        propellers=new BPoint[2][2][2][2];

        Segments s0=new Segments(0,dx*0.5,0,dy,0,dz);

        propellers[0][0][0][0]=addBPoint(-1.0,0.0,0,s0);
        propellers[0][1][0][0]=addBPoint(1.0,0.0,0,s0);
        propellers[0][0][1][0]=addBPoint(-1.0,1.0,0,s0);
        propellers[0][1][1][0]=addBPoint(1.0,1.0,0,s0);

        propellers[0][0][0][1]=addBPoint(-1.0,0.0,1.0,s0);
        propellers[0][1][0][1]=addBPoint(1.0,0.0,1.0,s0);
        propellers[0][0][1][1]=addBPoint(-1.0,1.0,1.0,s0);
        propellers[0][1][1][1]=addBPoint(1.0,1.0,1.0,s0);

        propellers[1][0][0][0]=addBPoint(-1.0,0.0,0,s0);
        propellers[1][1][0][0]=addBPoint(1.0,0.0,0,s0);
        propellers[1][0][1][0]=addBPoint(-1.0,1.0,0,s0);
        propellers[1][1][1][0]=addBPoint(1.0,1.0,0,s0);

        propellers[1][0][0][1]=addBPoint(-1.0,0.0,1.0,s0);
        propellers[1][1][0][1]=addBPoint(1.0,0.0,1.0,s0);
        propellers[1][0][1][1]=addBPoint(-1.0,1.0,1.0,s0);
        propellers[1][1][1][1]=addBPoint(1.0,1.0,1.0,s0);

    }

    private int buildShoesFaces(int counter) {
        for (int i = 0; i < shoes.length; i++) {

            BPoint[][][] shoe = shoes[i];
            faces[counter++]=buildFace(Renderer3D.CAR_TOP, shoe[0][0][1],shoe[1][0][1],shoe[1][1][1],shoe[0][1][1],tBo[0]);
            faces[counter++]=buildFace(Renderer3D.CAR_LEFT, shoe[0][0][0],shoe[0][0][1],shoe[0][1][1],shoe[0][1][0], tBo[0]);
            faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, shoe[1][0][0],shoe[1][1][0],shoe[1][1][1],shoe[1][0][1], tBo[0]);
            faces[counter++]=buildFace(Renderer3D.CAR_FRONT, shoe[0][1][0],shoe[0][1][1],shoe[1][1][1],shoe[1][1][0], tBo[0]);
            faces[counter++]=buildFace(Renderer3D.CAR_BACK, shoe[0][0][0],shoe[1][0][0],shoe[1][0][1],shoe[0][0][1], tBo[0]);
            faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, shoe[0][0][0],shoe[0][1][0],shoe[1][1][0],shoe[1][0][0], tBo[0]);

        }
        return counter;
    }


    private void buildShoes() {
        shoes=new BPoint[2][2][2][2];

        Segments s0=new Segments(0,dx*0.5,0,dy,0,dz);

        shoes[0][0][0][0]=addBPoint(-1.0,0.0,0,s0);
        shoes[0][1][0][0]=addBPoint(1.0,0.0,0,s0);
        shoes[0][0][1][0]=addBPoint(-1.0,1.0,0,s0);
        shoes[0][1][1][0]=addBPoint(1.0,1.0,0,s0);

        shoes[0][0][0][1]=addBPoint(-1.0,0.0,1.0,s0);
        shoes[0][1][0][1]=addBPoint(1.0,0.0,1.0,s0);
        shoes[0][0][1][1]=addBPoint(-1.0,1.0,1.0,s0);
        shoes[0][1][1][1]=addBPoint(1.0,1.0,1.0,s0);

        shoes[1][0][0][0]=addBPoint(-1.0,0.0,0,s0);
        shoes[1][1][0][0]=addBPoint(1.0,0.0,0,s0);
        shoes[1][0][1][0]=addBPoint(-1.0,1.0,0,s0);
        shoes[1][1][1][0]=addBPoint(1.0,1.0,0,s0);

        shoes[1][0][0][1]=addBPoint(-1.0,0.0,1.0,s0);
        shoes[1][1][0][1]=addBPoint(1.0,0.0,1.0,s0);
        shoes[1][0][1][1]=addBPoint(-1.0,1.0,1.0,s0);
        shoes[1][1][1][1]=addBPoint(1.0,1.0,1.0,s0);

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
