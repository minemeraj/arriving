package com.editors.models;

import java.awt.Color;
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

    private int[][][] faces;

    private BPoint[][][] body;

    private BPoint[][][] core;

    public final static String NAME="Sword";
    private double section_radius=0;
    private double taurus_radius=0;
    private double core_radius=0;

    private int sections_number=0;
    private int section_meridians=0;

    double columnAngle=0;

    private final int arm_number=3;

    public SwordModel(
            double taurus_radius, double section_radius, double core_radius,
            double tiltAngle,int section_meridians,int sections_number) {
        super();
        this.taurus_radius = taurus_radius;
        this.section_radius = section_radius;
        this.core_radius = core_radius;
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
        int CORE_FACES=sections_number+sections_number*2;
        int ARM_FACES=arm_number*4;

        faces=new int[NF+CORE_FACES+ARM_FACES][3][4];

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
        int frontMer=section_meridians/2-1;
        int backMer=section_meridians/2+1;
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


        core=new BPoint[sections_number][2][1];


        for (int i = 0; i < sections_number; i++) {

            double teta=dteta*i;

            for(int k=0;k<2;k++){

                double xx=core_radius*Math.cos(teta);
                double yy=(2.0*k-1.0)*section_radius;
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
