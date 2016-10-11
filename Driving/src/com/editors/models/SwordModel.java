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
 * One texture model
 * Summing up the best creation logic so far
 *
 * @author Administrator
 *
 */
public class SwordModel extends MeshModel{

    private int bx=10;
    private int by=10;

    private double dx = 0;
    private double dy = 0;
    private double dz = 0;

    double x0=0;
    double y0=0;
    double z0=0;

    //body textures
    protected int[][] bo= {{0,1,2,3}};

    private BPoint[][][] body;

    private BPoint[][][] core;

    public final static String NAME="Sword";
    private double butt_height=0;
    private double butt_width=0;
    private int barrel_meridians=0;

    private double breech_width=0;
    private double breech_length=0;
    private double breech_height=0;

    private double forearm_width=0;
    private double forearm_length=0;
    private double forearm_height=0;

    private double core_radius=0;
    private int sections_number=0;


    double columnAngle=0;

    private final int arm_number=3;
    private BPoint[][] grip;

    public SwordModel(
            double taurus_radius, double section_radius, double core_radius,
            double tiltAngle,int section_meridians,int sections_number) {
        super();
        this.butt_width = taurus_radius;
        this.butt_height = section_radius;
        this.core_radius = core_radius;
        this.barrel_meridians = section_meridians;
        this.sections_number = sections_number;
        columnAngle=tiltAngle;
    }


    @Override
    public void initMesh() {
        points=new Vector<Point3D>();
        texturePoints=new Vector<Point3D>();


        //the grip
        grip=addZCylinder(0,0,0,butt_width,butt_height,barrel_meridians);

        Segments s1=new Segments(0,breech_width,0,breech_length,butt_height,breech_height);

        BPoint[][][] guard=new BPoint[2][2][2];

        guard[0][0][0]=addBPoint(-0.5,-0.5,0,s1);
        guard[1][0][0]=addBPoint(0.5,-0.5,0,s1);
        guard[0][1][0]=addBPoint(-0.5,0.5,0,s1);
        guard[1][1][0]=addBPoint(0.5,0.5,0,s1);

        guard[0][0][1]=addBPoint(-0.5,-0.5,1.0,s1);
        guard[1][0][1]=addBPoint(0.5,-0.5,1.0,s1);
        guard[0][1][1]=addBPoint(-0.5,0.5,1.0,s1);
        guard[1][1][1]=addBPoint(0.5,0.5,1.0,s1);




        Segments s2=new Segments(0,forearm_width,0,forearm_length,butt_height+breech_height,forearm_height);

        BPoint[][][] blade=new BPoint[2][2][2];

        blade[0][0][0]=addBPoint(0,-0.5,0,s2);
        blade[1][0][0]=addBPoint(0.5,0.0,0,s2);
        blade[0][1][0]=addBPoint(-0.5,0.0,0,s2);
        blade[1][1][0]=addBPoint(0,0.5,0,s2);

        blade[0][0][1]=addBPoint(0,-0.125,1.0,s2);
        blade[1][0][1]=addBPoint(0.125,0.0,1.0,s2);
        blade[0][1][1]=addBPoint(-0.125,0.0,1.0,s2);
        blade[1][1][1]=addBPoint(0,0.125,1.0,s2);



        buildBody();

        buildTextures();

        //faces
        int NF=sections_number*barrel_meridians;
        int CORE_FACES=sections_number+sections_number*2;
        int ARM_FACES=arm_number*4;

        faces=new int[NF+CORE_FACES+ARM_FACES][3][4];

        int counter=0;
        counter=buildFaces(counter);

    }


    private int buildFaces(int counter) {




        //grip
        for (int i = 0; i < sections_number; i++) {

            for (int j = 0; j < barrel_meridians; j++) {

                faces[counter++]=buildFace(Renderer3D.CAR_TOP,
                        body[i][j][0],
                        body[i][(j+1)%barrel_meridians][0],
                        body[(i+1)%sections_number][(j+1)%barrel_meridians][0],
                        body[(i+1)%sections_number][j][0],
                        bo[0]);
            }

        }


        /*addLine(guard[0][0][1],guard[1][0][1],guard[1][1][1],guard[0][1][1],Renderer3D.CAR_TOP);

        addLine(guard[0][0][0],guard[0][0][1],guard[0][1][1],guard[0][1][0],Renderer3D.CAR_LEFT);

        addLine(guard[1][0][0],guard[1][1][0],guard[1][1][1],guard[1][0][1],Renderer3D.CAR_RIGHT);

        addLine(guard[0][1][0],guard[0][1][1],guard[1][1][1],guard[1][1][0],Renderer3D.CAR_FRONT);

        addLine(guard[0][0][0],guard[1][0][0],guard[1][0][1],guard[0][0][1],Renderer3D.CAR_BACK);

        addLine(guard[0][0][0],guard[0][1][0],guard[1][1][0],guard[1][0][0],Renderer3D.CAR_BOTTOM);*/


        /*addLine(blade[0][0][1],blade[1][0][1],blade[1][1][1],blade[0][1][1],Renderer3D.CAR_TOP);

        addLine(blade[0][0][0],blade[0][0][1],blade[0][1][1],blade[0][1][0],Renderer3D.CAR_LEFT);

        addLine(blade[1][0][0],blade[1][1][0],blade[1][1][1],blade[1][0][1],Renderer3D.CAR_RIGHT);

        addLine(blade[0][1][0],blade[0][1][1],blade[1][1][1],blade[1][1][0],Renderer3D.CAR_FRONT);

        addLine(blade[0][0][0],blade[1][0][0],blade[1][0][1],blade[0][0][1],Renderer3D.CAR_BACK);

        addLine(blade[0][0][0],blade[0][1][0],blade[1][1][0],blade[1][0][0],Renderer3D.CAR_BOTTOM);*/

        //core lateral faces:
        for (int i = 0; i < sections_number; i++) {

            faces[counter++]=buildFace(Renderer3D.CAR_LEFT,
                    core[i][0][0],
                    core[(i+1)%sections_number][0][0],
                    core[(i+1)%sections_number][1][0],
                    core[i][1][0],
                    bo[0]);
        }

        //core back and front faces:
        for (int i = 0; i < sections_number; i++) {

            faces[counter++]=buildFace(Renderer3D.CAR_TOP,
                    core[0][0][0],
                    core[i][0][0],
                    core[(i+1)%sections_number][0][0],
                    bo[0]);
        }

        for (int i = 0; i < sections_number; i++) {

            faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
                    core[0][1][0],
                    core[(i+1)%sections_number][1][0],
                    core[i][1][0],
                    bo[0]);
        }

        //arms:
        int frontMer=barrel_meridians/2-1;
        int backMer=barrel_meridians/2+1;
        for (int i = 0; i < sections_number; i++) {

            if(i!=5 && i!=7 && i!=9) {
                continue;
            }


            faces[counter++]=buildFace(Renderer3D.CAR_BACK,
                    core[i][0][0],
                    core[(i+1)%sections_number][0][0],
                    body[(i+1)%sections_number][backMer][0],
                    body[i][backMer][0],
                    bo[0]);

            faces[counter++]=buildFace(Renderer3D.CAR_FRONT,
                    core[i][1][0],
                    core[(i+1)%sections_number][1][0],
                    body[(i+1)%sections_number][frontMer][0],
                    body[i][frontMer][0],
                    bo[0]);

            faces[counter++]=buildFace(Renderer3D.CAR_LEFT,
                    core[(i+1)%sections_number][0][0],
                    core[(i+1)%sections_number][1][0],
                    body[(i+1)%sections_number][frontMer][0],
                    body[(i+1)%sections_number][backMer][0],
                    bo[0]);

            faces[counter++]=buildFace(Renderer3D.CAR_RIGHT,
                    core[i][0][0],
                    core[i][1][0],
                    body[i][frontMer][0],
                    body[i][backMer][0],
                    bo[0]);

        }

        return counter;
    }

    int dxTexture=50;
    int dyTexture=50;

    private void buildTextures() {


        //Texture points

        double y=by;
        double x=bx;

        addTPoint(x,y,0);
        addTPoint(x+dxTexture,y,0);
        addTPoint(x+dxTexture, y+dyTexture,0);
        addTPoint(x,y+dyTexture,0);

        IMG_WIDTH=2*bx+dxTexture;
        IMG_HEIGHT=2*by+dyTexture;

    }


    private void buildBody() {


        body=new BPoint[sections_number][barrel_meridians][1];

        double dteta=2.0*Math.PI/(sections_number);
        double dfi=2.0*Math.PI/(barrel_meridians);


        for (int i = 0; i < sections_number; i++) {

            double teta=dteta*i;


            for (int j = 0; j < barrel_meridians; j++) {

                double fi=dfi*j;

                double xx=(butt_width+butt_height*Math.cos(fi))*Math.cos(teta);
                double yy=butt_height*Math.sin(fi);
                double zz=(butt_width+butt_height*Math.cos(fi))*Math.sin(teta);

                double x=xx;
                double y=Math.cos(columnAngle)*yy-Math.sin(columnAngle)*zz;
                double z=Math.sin(columnAngle)*yy+Math.cos(columnAngle)*zz;

                body[i][j][0]=addBPoint(x,y,z);
            }

        }


        core=new BPoint[sections_number][2][1];


        for (int i = 0; i < sections_number; i++) {

            double teta=dteta*i;

            for(int k=0;k<2;k++){

                double xx=core_radius*Math.cos(teta);
                double yy=(2.0*k-1.0)*butt_height;
                double zz=core_radius*Math.sin(teta);

                double x=xx;
                double y=Math.cos(columnAngle)*yy-Math.sin(columnAngle)*zz;
                double z=Math.sin(columnAngle)*yy+Math.cos(columnAngle)*zz;

                core[i][k][0]=addBPoint(x,y,z);

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

        bufGraphics.setColor(Color.BLACK);

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
