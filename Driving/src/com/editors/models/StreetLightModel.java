package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.BPoint;
import com.CustomData;
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
public class StreetLightModel extends MeshModel{

    private int bx=10;
    private int by=10;

    private double dx = 0;
    private double dy = 0;
    private double dz = 0;

    private double dxRoof;
    private double dyRoof;
    private double dzRoof;

    double x0=0;
    double y0=0;
    double z0=0;

    //body textures
    protected int[][] bo= {{0,1,2,3}};

    private int[][][] faces;

    private double dxTexture=200;
    private double dyTexture=200;

    int trunk_meridians=10;
    int trunk_parallels=10;

    int lampNumx=2;
    int lampNumy=2;
    int lampNumz=10;

    private BPoint[][] trunkpoints;
    private BPoint[][][] lamp;

    public StreetLightModel(
            double dx, double dy, double dz,
            double dxRoof,double dyRoof,double dzRoof) {
        super();
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;

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

        int lampFaces=2*((lampNumx-1)*(lampNumy-1)+(lampNumx-1)*(lampNumz-1)+(lampNumy-1)*(lampNumz-1));

        //faces
        int NF=2*trunk_meridians+trunk_meridians*trunk_parallels;

        faces=new int[NF+lampFaces][3][4];

        int counter=0;
        counter=buildFaces(counter);

    }


    private int buildFaces(int counter) {


        for (int i = 0; i < trunk_meridians; i++) {

            faces[counter++]=buildFace(Renderer3D.CAR_TOP,
                    trunkpoints[trunk_parallels-1][0],
                    trunkpoints[trunk_parallels-1][(i+1)%trunk_meridians],
                    trunkpoints[trunk_parallels-1][i],
                    bo[0]);

        }


        for (int i = trunk_meridians-1; i >=0; i--) {

            faces[counter++]=buildFace(Renderer3D.CAR_TOP,
                    trunkpoints[0][0],
                    trunkpoints[0][i],
                    trunkpoints[0][(i+1)%trunk_meridians],
                    bo[0]);

        }

        for (int k = 0; k < trunk_parallels-1; k++) {

            for (int i = 0; i < trunk_meridians; i++) {

                faces[counter++]=buildFace(Renderer3D.CAR_TOP,
                        trunkpoints[k][i],
                        trunkpoints[k][(i+1)%trunk_meridians],
                        trunkpoints[k+1][(i+1)%trunk_meridians],
                        trunkpoints[k+1][i],
                        bo[0]);

            }

        }

        for (int i = 0; i < lampNumx-1; i++) {


            for (int j = 0; j < lampNumy-1; j++) {

                for (int k = 0; k < lampNumz-1; k++) {


                    if(i==0){

                        faces[counter++]=buildFace(Renderer3D.CAR_BACK,
                                lamp[i][j][k],lamp[i][j][k+1],lamp[i][j+1][k+1],lamp[i][j+1][k],
                                bo[0]);
                    }

                    if(k+1==lampNumz-1){

                        faces[counter++]=buildFace(Renderer3D.CAR_TOP,
                                lamp[i][j][k+1],lamp[i+1][j][k+1],lamp[i+1][j+1][k+1],lamp[i][j+1][k+1],
                                bo[0]);
                    }
                    if(k==0){

                        faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM,
                                lamp[i][j][k],lamp[i][j+1][k],lamp[i+1][j+1][k],lamp[i+1][j][k],
                                bo[0]);
                    }

                    if(j==0){
                        faces[counter++]=buildFace(Renderer3D.CAR_LEFT,
                                lamp[i][j][k],lamp[i+1][j][k],lamp[i+1][j][k+1],lamp[i][j][k+1],
                                bo[0]);
                    }
                    if(j+1==lampNumy-1){

                        faces[counter++]=buildFace(Renderer3D.CAR_RIGHT,
                                lamp[i][j+1][k],lamp[i][j+1][k+1],lamp[i+1][j+1][k+1],lamp[i+1][j+1][k],
                                bo[0]);
                    }


                    if(i+1==lampNumx-1){

                        faces[counter++]=buildFace(Renderer3D.CAR_FRONT,
                                lamp[i+1][j][k],lamp[i+1][j+1][k],lamp[i+1][j+1][k+1],lamp[i+1][j][k+1],
                                bo[0]);

                    }

                }

            }

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

        double trunk_radius=dx*0.5;
        double trunk_lenght=dz;

        //trunk:

        trunkpoints=new BPoint[trunk_parallels][trunk_meridians];

        for (int k = 0; k < trunk_parallels; k++) {


            for (int i = 0; i < trunk_meridians; i++) {

                double x=trunk_radius*Math.cos(2*Math.PI/trunk_meridians*i);
                double y=trunk_radius*Math.sin(2*Math.PI/trunk_meridians*i);

                double z=0;

                double dz0=trunk_lenght*2.0/3.0;
                double dz1=(trunk_lenght-dz0);

                if(k<2){

                    z=dz0*k;
                }
                else{

                    z=dz0+dz1/(trunk_parallels-2.0)*(k-1);
                }

                trunkpoints[k][i]=addBPoint(x,y,z);

            }

        }

        //lamp

        Segments l0=new Segments(0,dxRoof,-0.457*dzRoof,dzRoof,dz,dzRoof);


        lamp=new BPoint[lampNumx][lampNumy][lampNumz];


        lamp[0][0][0]=addBPoint(-0.116,0.215,0,l0);
        lamp[1][0][0]=addBPoint(0.116,0.215,0,l0);
        lamp[0][1][0]=addBPoint(-0.116,0.699,0,l0);
        lamp[1][1][0]=addBPoint(0.116,0.699,0,l0);


        lamp[0][0][1]=addBPoint(-0.113,0.221,0.156,l0);
        lamp[1][0][1]=addBPoint(0.113,0.221,0.156,l0);
        lamp[0][1][1]=addBPoint(-0.113,0.699,0.156,l0);
        lamp[1][1][1]=addBPoint(0.113,0.699,0.156,l0);

        lamp[0][0][2]=addBPoint(-0.175,0.202,0.192,l0);
        lamp[1][0][2]=addBPoint(0.175,0.202,0.192,l0);
        lamp[0][1][2]=addBPoint(-0.175,1.0,0.192,l0);
        lamp[1][1][2]=addBPoint(0.175,1.0,0.192,l0);


        lamp[0][0][3]=addBPoint(-0.322,0.16,0.259,l0);
        lamp[1][0][3]=addBPoint(0.322,0.16,0.259,l0);
        lamp[0][1][3]=addBPoint(-0.322,1.0,0.259,l0);
        lamp[1][1][3]=addBPoint(0.322,1.0,0.259,l0);


        lamp[0][0][4]=addBPoint(-0.472,0.049,0.44,l0);
        lamp[1][0][4]=addBPoint(0.472,0.049,0.44,l0);
        lamp[0][1][4]=addBPoint(-0.472,1.0,0.44,l0);
        lamp[1][1][4]=addBPoint(0.472,1.0,0.44,l0);


        lamp[0][0][5]=addBPoint(-0.5,0.0,0.578,l0);
        lamp[1][0][5]=addBPoint(0.5,0.0,0.578,l0);
        lamp[0][1][5]=addBPoint(-0.5,1.0,0.578,l0);
        lamp[1][1][5]=addBPoint(0.5,1.0,0.578,l0);


        lamp[0][0][6]=addBPoint(-0.463,0.074,0.748,l0);
        lamp[1][0][6]=addBPoint(0.463,0.074,0.748,l0);
        lamp[0][1][6]=addBPoint(-0.463,1.0,0.748,l0);
        lamp[1][1][6]=addBPoint(0.463,1.0,0.748,l0);


        lamp[0][0][7]=addBPoint(-0.391,0.221,0.846,l0);
        lamp[1][0][7]=addBPoint(0.391,0.221,0.846,l0);
        lamp[0][1][7]=addBPoint(-0.391,1.0,0.846,l0);
        lamp[1][1][7]=addBPoint(0.391,1.0,0.846,l0);


        lamp[0][0][8]=addBPoint(-0.307,0.417,0.918,l0);
        lamp[1][0][8]=addBPoint(0.307,0.417,0.918,l0);
        lamp[0][1][8]=addBPoint(-0.307,1.0,0.918,l0);
        lamp[1][1][8]=addBPoint(0.307,1.0,0.918,l0);


        lamp[0][0][9]=addBPoint(-0.163,0.706,0.98,l0);
        lamp[1][0][9]=addBPoint(0.163,0.706,0.98,l0);
        lamp[0][1][9]=addBPoint(-0.163,1.0,0.98,l0);
        lamp[1][1][9]=addBPoint(0.163,1.0,0.98,l0);

        //bending of the streetlight

        double[] q=new double[trunk_parallels];
        q[1]=0.2;
        q[2]=0.2;
        q[3]=0.2;
        q[4]=0.2;
        q[5]=0.2;
        q[6]=0.2;
        q[7]=0.2;
        q[8]=0.2;

        for (int i = 0; i < q.length; i++) {

            if(q[i]==0) {
                continue;
            }

            BPoint[] refSlice = trunkpoints[i];
            BPoint p0= refSlice[0];

            CustomData.rotateYZ(lamp,  p0.y,  p0.z,  q[i]);

            for (int k = 0; k < trunk_parallels; k++) {

                if(k<=i) {
                    continue;
                }

                BPoint[] slice = trunkpoints[k];

                CustomData.rotateYZ(slice,  p0.y,  p0.z,  q[i]);


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
