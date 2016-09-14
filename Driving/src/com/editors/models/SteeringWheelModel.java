package com.editors.models;

import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.main.Renderer3D;
/**
 * One texture model
 * Summing up the best creation logic so far
 *
 * @author Administrator
 *
 */
public class SteeringWheelModel extends MeshModel{

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

    private int[][][] faces;

    private BPoint[][][] body;

    public final static String NAME="Steering wheel";
    private double section_radius=0;
    private double taurus_radius=0;

    private int sections_number=0;
    private int section_meridians=0;

    double columnAngle=0;

    public SteeringWheelModel(
            double taurus_radius, double dy, double section_radius,
            double tiltAngle,int section_meridians,int sections_number) {
        super();
        this.taurus_radius = taurus_radius;
        this.section_radius = section_radius;
        this.section_meridians = section_meridians;
        this.sections_number = sections_number;
        columnAngle=tiltAngle;
    }


    @Override
    public void initMesh() {
        points=new Vector<Point3D>();
        texturePoints=new Vector<Point3D>();

        buildBody();

        buildTextures();

        //faces
        int NF=sections_number*section_meridians;

        faces=new int[NF][3][4];

        int counter=0;
        counter=buildFaces(counter);

    }


    private int buildFaces(int counter) {

        for (int i = 0; i < sections_number; i++) {

            for (int j = 0; j < section_meridians; j++) {

                faces[counter++]=buildFace(Renderer3D.CAR_TOP,
                        body[i][j][0],
                        body[i][(j+1)%section_meridians][0],
                        body[(i+1)%sections_number][(j+1)%section_meridians][0],
                        body[(i+1)%sections_number][j][0],
                        bo[0]);
            }

        }

        return counter;
    }


    private void buildTextures() {


        //Texture points

        double y=by;
        double x=bx;

        addTPoint(x,y,0);
        addTPoint(x+dx,y,0);
        addTPoint(x+dx, y+dy,0);
        addTPoint(x,y+dy,0);

        IMG_WIDTH=(int) (2*bx+dx);
        IMG_HEIGHT=(int) (2*by+dy);

    }


    private void buildBody() {


        body=new BPoint[sections_number][section_meridians][1];

        double dteta=2.0*Math.PI/(sections_number);
        double dfi=2.0*Math.PI/(section_meridians);


        for (int i = 0; i < sections_number; i++) {

            double teta=dteta*i;


            for (int j = 0; j < section_meridians; j++) {

                double fi=dfi*j;

                double xx=(taurus_radius+section_radius*Math.cos(fi))*Math.cos(teta);
                double yy=section_radius*Math.sin(fi);
                double zz=(taurus_radius+section_radius*Math.cos(fi))*Math.sin(teta);

                double x=xx;
                double y=Math.cos(columnAngle)*yy-Math.sin(columnAngle)*zz;
                double z=Math.sin(columnAngle)*yy+Math.cos(columnAngle)*zz;

                body[i][j][0]=addBPoint(x,y,z);
            }

        }




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
