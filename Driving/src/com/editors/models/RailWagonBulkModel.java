package com.editors.models;

import java.util.Vector;

import com.Point3D;
import com.Prism;
import com.Segments;
/**
 * One texture model
 * Summing up the best creation logic so far
 *
 * @author Administrator
 *
 */
public class RailWagonBulkModel extends RailWagon0Model{

    protected Prism prismRight;
    protected Prism prismLeft;
    protected Prism prismBack;
    protected Prism prismFront;

    public static String NAME="RailWagon";


    public RailWagonBulkModel(
            double dx, double dy, double dz,
            double dxFront, double dyFront, double dzFront,
            double dxRear, double dyRear,	double dzRear,
            double dxRoof,double dyRoof,double dzRoof,
            double rearOverhang, double frontOverhang,
            double wheelRadius, double wheelWidth, int wheelRays
            ) {
        super(dx, dy, dz,
                dxFront, dyFront, dzFront,
                dxRear, dyRear, dzRear,
                dxRoof, dyRoof, dzRoof,
                rearOverhang, frontOverhang,
                wheelRadius, wheelWidth, wheelRays);
    }


    @Override
    public void initMesh() {
        points=new Vector<Point3D>();
        texturePoints=new Vector<Point3D>();


        int pnx=2;
        int pny=2;
        int pnz=2;

        buildBody(pnx,pny,pnz);

        buildWagon();


        int firstWheelTexturePoint=4;
        buildTextures();


        //faces
        int NF=6*3+6*4;

        int totWheelPolygon=wheelRays+2*(wheelRays-2);
        int NUM_WHEEL_FACES=8*totWheelPolygon;

        faces=new int[NF+NUM_WHEEL_FACES][3][4];

        int counter=0;



        counter=buildBodyFaces(counter,firstWheelTexturePoint,totWheelPolygon);

        counter=buildWagonFaces(counter);

    }





    @Override
    protected void buildWagon() {


        double rdy=(dy-dyRoof)*0.5;

        Segments r0=new Segments(0,dxRoof,rdy,dyRoof,dzRear+dz,dzRoof);

        double dy=4.0/dyRoof;
        double dx=4.0/dxRoof;

        prismRight=new Prism(4);

        prismRight.lowerBase[0]=addBPoint(0.5-dx,0,0,r0);
        prismRight.lowerBase[1]=addBPoint(0.5,0,0,r0);
        prismRight.lowerBase[2]=addBPoint(0.5,1.0,0,r0);
        prismRight.lowerBase[3]=addBPoint(0.5-dx,1.0,0,r0);

        prismRight.upperBase[0]=addBPoint(0.5-dx,0,1.0,r0);
        prismRight.upperBase[1]=addBPoint(0.5,0,1.0,r0);
        prismRight.upperBase[2]=addBPoint(0.5,1.0,1.0,r0);
        prismRight.upperBase[3]=addBPoint(0.5-dx,1.0,1.0,r0);


        prismLeft=new Prism(4);

        prismLeft.lowerBase[0]=addBPoint(-0.5,0,0,r0);
        prismLeft.lowerBase[1]=addBPoint(-0.5+dx,0,0,r0);
        prismLeft.lowerBase[2]=addBPoint(-0.5+dx,1.0,0,r0);
        prismLeft.lowerBase[3]=addBPoint(-0.5,1.0,0,r0);

        prismLeft.upperBase[0]=addBPoint(-0.5,0,1.0,r0);
        prismLeft.upperBase[1]=addBPoint(-0.5+dx,0,1.0,r0);
        prismLeft.upperBase[2]=addBPoint(-0.5+dx,1.0,1.0,r0);
        prismLeft.upperBase[3]=addBPoint(-0.5,1.0,1.0,r0);


        prismBack=new Prism(4);

        prismBack.lowerBase[0]=addBPoint(-(0.5-dx),0,0,r0);
        prismBack.lowerBase[1]=addBPoint(0.5-dx,0,0,r0);
        prismBack.lowerBase[2]=addBPoint(0.5-dx,dy,0,r0);
        prismBack.lowerBase[3]=addBPoint(-(0.5-dx),dy,0,r0);

        prismBack.upperBase[0]=addBPoint(-(0.5-dx),0,1.0,r0);
        prismBack.upperBase[1]=addBPoint(0.5-dx,0,1.0,r0);
        prismBack.upperBase[2]=addBPoint(0.5-dx,dy,1.0,r0);
        prismBack.upperBase[3]=addBPoint(-(0.5-dx),dy,1.0,r0);


        prismFront=new Prism(4);

        prismFront.lowerBase[0]=addBPoint(-(0.5-dx),1-dy,0,r0);
        prismFront.lowerBase[1]=addBPoint(0.5-dx,1-dy,0,r0);
        prismFront.lowerBase[2]=addBPoint(0.5-dx,1.0,0,r0);
        prismFront.lowerBase[3]=addBPoint(-(0.5-dx),1.0,0,r0);

        prismFront.upperBase[0]=addBPoint(-(0.5-dx),1-dy,1.0,r0);
        prismFront.upperBase[1]=addBPoint(0.5-dx,1-dy,1.0,r0);
        prismFront.upperBase[2]=addBPoint(0.5-dx,1.0,1.0,r0);
        prismFront.upperBase[3]=addBPoint(-(0.5-dx),1.0,1.0,r0);


    }


    protected int buildWagonFaces(int counter) {

        counter=addPrism(prismRight,counter,tBo[0]);
        counter=addPrism(prismLeft,counter,tBo[0]);
        counter=addPrism(prismBack,counter,tBo[0]);
        counter=addPrism(prismFront,counter,tBo[0]);



        return counter;

    }



}
