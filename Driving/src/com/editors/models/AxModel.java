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
public class AxModel extends MeshModel{

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

    public final static String NAME="Ax";
    private double barrel_length=0;
    private double barrel_radius=0;
    private int barrel_meridians=0;

    private double forearm_length=0;
    private double forearm_height=0;
    private double forearm_width=0;

    private int sections_number=0;


    double columnAngle=0;

    private final int arm_number=3;
    private BPoint[][] barrel;

    public AxModel(
            double barrel_radius, double barrel_length, double core_radius,
            double tiltAngle,int barrel_meridians,int sections_number) {
        super();
        this.barrel_radius = barrel_radius;
        this.barrel_length = barrel_length;
        this.forearm_length = core_radius;
        this.barrel_meridians = barrel_meridians;
        this.sections_number = sections_number;
        columnAngle=tiltAngle;
    }


    @Override
    public void initMesh() {
        points=new Vector<Point3D>();
        texturePoints=new Vector<Point3D>();


        barrel=addZCylinder(0,0,0,barrel_radius,barrel_length,barrel_meridians);

        Segments s0=new Segments(0,barrel_radius,0,forearm_length,barrel_length-forearm_height,forearm_height);
        Segments s1=new Segments(0,barrel_radius,0,forearm_length,barrel_length-(forearm_height+forearm_width)*0.5,forearm_width);

        BPoint[][][] blade=new BPoint[2][2][2];

        blade[0][0][0]=addBPoint(-1.0,0.0,0,s0);
        blade[1][0][0]=addBPoint(1.0,0.0,0,s0);
        blade[0][1][0]=addBPoint(0.0,1.0,0,s1);
        blade[1][1][0]=null;

        blade[0][0][1]=addBPoint(-1.0,0.0,1.0,s0);
        blade[1][0][1]=addBPoint(1.0,0.0,1.0,s0);
        blade[0][1][1]=addBPoint(0.0,1.0,1.0,s1);
        blade[1][1][1]=null;

        /*addLine(blade[0][0][1],blade[1][0][1],blade[0][1][1],null,Renderer3D.CAR_TOP);

        addLine(blade[0][0][0],blade[0][0][1],blade[0][1][1],blade[0][1][0],Renderer3D.CAR_LEFT);

        addLine(blade[1][0][0],blade[0][1][0],blade[0][1][1],blade[1][0][1],Renderer3D.CAR_RIGHT);

        addLine(blade[0][0][0],blade[1][0][0],blade[1][0][1],blade[0][0][1],Renderer3D.CAR_BACK);

        addLine(blade[0][0][0],blade[0][1][0],blade[1][0][0],null,Renderer3D.CAR_BOTTOM);*/

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

                double xx=(barrel_radius+barrel_length*Math.cos(fi))*Math.cos(teta);
                double yy=barrel_length*Math.sin(fi);
                double zz=(barrel_radius+barrel_length*Math.cos(fi))*Math.sin(teta);

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

                double xx=forearm_length*Math.cos(teta);
                double yy=(2.0*k-1.0)*barrel_length;
                double zz=forearm_length*Math.sin(teta);

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
