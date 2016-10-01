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
public class TunnelModel extends MeshModel{

    public static final String NAME = "Tunnel";
    private int bx=10;
    private int by=10;

    private double dx = 0;
    private double dy = 0;
    private double dz = 0;

    private double poleWidth = 0;
    private double poleLength = 0;
    private double poleHeight = 0;

    double x0=0;
    double y0=0;
    double z0=0;

    //body textures
    protected int[][] bo= {{0,1,2,3}};

    private int[][][] faces;

    private BPoint[][][] body;

    private int numPoles=0;
    private BPoint[][][][] poles;

    private double dxTexture=200;
    private double dyTexture=200;

    public TunnelModel(
            double dx, double dy, double dz,
            int numPoles,
            double poleWidth, double poleLength, double poleHeight
            ) {
        super();
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;

        this.poleWidth = poleWidth;
        this.poleLength = poleLength;
        this.poleHeight = poleHeight;

        this.numPoles = numPoles;
    }


    @Override
    public void initMesh() {
        points=new Vector<Point3D>();
        texturePoints=new Vector<Point3D>();

        buildBody();

        buildTextures();

        //faces
        int NF=6+6*poles.length;

        faces=new int[NF][3][4];

        int counter=0;
        counter=buildFaces(counter);

    }


    private int buildFaces(int counter) {

        for(int i=0;i<poles.length;i++){
            faces[counter++]=buildFace(Renderer3D.CAR_TOP, poles[i][0][0][1],poles[i][1][0][1],poles[i][1][1][1],poles[i][0][1][1],bo[0]);
            faces[counter++]=buildFace(Renderer3D.CAR_LEFT, poles[i][0][0][0],poles[i][0][0][1],poles[i][0][1][1],poles[i][0][1][0], bo[0]);
            faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, poles[i][1][0][0],poles[i][1][1][0],poles[i][1][1][1],poles[i][1][0][1], bo[0]);
            faces[counter++]=buildFace(Renderer3D.CAR_FRONT, poles[i][0][1][0],poles[i][0][1][1],poles[i][1][1][1],poles[i][1][1][0], bo[0]);
            faces[counter++]=buildFace(Renderer3D.CAR_BACK, poles[i][0][0][0],poles[i][1][0][0],poles[i][1][0][1],poles[i][0][0][1], bo[0]);
            faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, poles[i][0][0][0],poles[i][0][1][0],poles[i][1][1][0],poles[i][1][0][0], bo[0]);
        }

        faces[counter++]=buildFace(Renderer3D.CAR_TOP, body[0][0][1],body[1][0][1],body[1][1][1],body[0][1][1],bo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_LEFT, body[0][0][0],body[0][0][1],body[0][1][1],body[0][1][0], bo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, body[1][0][0],body[1][1][0],body[1][1][1],body[1][0][1], bo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_FRONT, body[0][1][0],body[0][1][1],body[1][1][1],body[1][1][0], bo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_BACK, body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1], bo[0]);
        faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, body[0][0][0],body[0][1][0],body[1][1][0],body[1][0][0], bo[0]);


        return counter;
    }


    private void buildTextures() {


        //Texture points

        double y=by;
        double x=bx;

        addTRect(x, y, dxTexture, dyTexture);

        IMG_WIDTH=(int) (2*bx+dxTexture);
        IMG_HEIGHT=(int) (2*by+dyTexture);

    }


    private void buildBody() {

        poles=new BPoint[numPoles+1][2][2][2];


        double interPole=(dy-numPoles*poleLength)/(numPoles);
        double yRef=0;

        for(int i=0;i<=numPoles;i++){

        	double poleSpan=poleLength;
        	if(i==0 || i==numPoles){
        		poleSpan=poleLength*0.5;
        	}

            Segments s0=new Segments(0,poleWidth*0.5,yRef,poleSpan,0,poleHeight);

            poles[i][0][0][0]=addBPoint(-1.0,0.0,0,s0);
            poles[i][1][0][0]=addBPoint(1.0,0.0,0,s0);
            poles[i][0][1][0]=addBPoint(-1.0,1.0,0,s0);
            poles[i][1][1][0]=addBPoint(1.0,1.0,0,s0);

            poles[i][0][0][1]=addBPoint(-1.0,0.0,1.0,s0);
            poles[i][1][0][1]=addBPoint(1.0,0.0,1.0,s0);
            poles[i][0][1][1]=addBPoint(-1.0,1.0,1.0,s0);
            poles[i][1][1][1]=addBPoint(1.0,1.0,1.0,s0);

            yRef+=(interPole+poleSpan);

        }

        Segments s1=new Segments(0,dx*0.5,0,dy,poleHeight,dz);

        body=new BPoint[2][2][2];

        body[0][0][0]=addBPoint(-1.0,0.0,0,s1);
        body[1][0][0]=addBPoint(1.0,0.0,0,s1);
        body[0][1][0]=addBPoint(-1.0,1.0,0,s1);
        body[1][1][0]=addBPoint(1.0,1.0,0,s1);

        body[0][0][1]=addBPoint(-1.0,0.0,1.0,s1);
        body[1][0][1]=addBPoint(1.0,0.0,1.0,s1);
        body[0][1][1]=addBPoint(-1.0,1.0,1.0,s1);
        body[1][1][1]=addBPoint(1.0,1.0,1.0,s1);
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
