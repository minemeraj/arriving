package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.main.Renderer3D;

public class Chimney0Model extends MeshModel{

    private double dx=100;
    private double dx1=200;
    private double dz=20;


    private int bx=10;
    private int by=10;

    private int num_meridians=10;

    public static String NAME="Chimney0";

    public Chimney0Model(double dx, double dx1, double dz,int num_meridians) {
        super();
        this.dx = dx;
        this.dx1 = dx1;
        this.dz = dz;
        this.num_meridians = num_meridians;
    }


    @Override
    public void printMeshData(PrintWriter pw) {

        super.printMeshData(pw);

        for (int i = 0; i < faces.length; i++) {

            int[][] face=faces[i];

            int[] fts=face[0];
            int[] pts=face[1];
            int[] tts=face[2];

            String line="f=["+fts[0]+"]";

            int len=pts.length;

            if(i>0 && i<faces.length-1) {
                len=4;
            }

            for (int j = 0; j < len; j++) {

                if(j>0) {
                    line+=" ";
                }
                line+=(pts[j]+"/"+tts[j]);
            }

            print(pw,line);

        }

    }


    @Override
    public void initMesh() {

        points=new Vector();


        //lower base


        double dteta=Math.PI*2.0/num_meridians;

        //lower base
        for(int i=0;i<num_meridians;i++){

            double teta=dteta*i;
            double xx=dx*0.5+dx*0.5*Math.cos(teta);
            double yy=dx*0.5+dx*0.5*Math.sin(teta);
            addPoint(xx,yy,0);
        }

        //upper base
        for(int i=0;i<num_meridians;i++){

            double teta=dteta*i;
            double xx=dx*0.5+dx1*0.5*Math.cos(teta);
            double yy=dx*0.5+dx1*0.5*Math.sin(teta);
            addPoint(xx,yy,dz);
        }


        texturePoints=new Vector();

        double y=by+dx*0.5;
        double x=bx+dx*0.5;

        //lower base
        for(int i=0;i<num_meridians;i++){

            double teta=dteta*i;
            double xx=dx*0.5*Math.cos(teta);
            double yy=dx*0.5*Math.sin(teta);
            addTPoint(x+xx,y+yy,0);
        }

        x=bx;
        y=by+dx;

        //faces
        double dl=Math.PI*dx/num_meridians;

        for(int i=0;i<=num_meridians;i++){


            double xx=dl*i;
            double yy=0;
            addTPoint(x+xx,y+yy,dz);
        }

        x=bx;
        y=by+dx+dz;

        for(int i=0;i<=num_meridians;i++){


            double xx=dl*i;
            double yy=0;
            addTPoint(x+xx,y+yy,dz);
        }

        x=bx+dx*0.5;
        y=by+dx+dz+dx1*0.5;

        for(int i=0;i<num_meridians;i++){

            double teta=dteta*i;
            double xx=dx1*0.5*Math.cos(teta);
            double yy=dx1*0.5*Math.sin(teta);
            addTPoint(x+xx,y+yy,dz);
        }




        IMG_WIDTH=(int) (2*bx+Math.PI*dx);
        IMG_HEIGHT=(int) (2*by+dx+dz+dx1);

        //2=bases
        int NF=2+num_meridians;
        faces=new int[NF][3][num_meridians];

        //bottom
        faces[0][0][0]=Renderer3D.CAR_BOTTOM;
        for(int i=0;i<num_meridians;i++){
            faces[0][1][i]=num_meridians-1-i;
            faces[0][2][i]=num_meridians-1-i;
        }

        //faces
        int start=num_meridians;
        for(int l=0;l<num_meridians;l++){

            faces[1+l][0][0]=Renderer3D.CAR_BACK;


            faces[1+l][1][0]=l;
            faces[1+l][1][1]=(l+1)%num_meridians;
            faces[1+l][1][2]=(l+1)%num_meridians+num_meridians;
            faces[1+l][1][3]=l+num_meridians;

            faces[1+l][2][0]=start+l;
            faces[1+l][2][1]=start+(l+1);
            faces[1+l][2][2]=start+(l+1)+num_meridians+1;
            faces[1+l][2][3]=start+l+num_meridians+1;

        }

        //top
        start=num_meridians+2*(num_meridians+1);
        faces[NF-1][0][0]=Renderer3D.CAR_TOP;
        for(int i=0;i<num_meridians;i++){
            faces[NF-1][1][i]=i+num_meridians;
            faces[NF-1][2][i]=i+start;
        }

    }





    @Override
    public void printTexture(Graphics2D bg) {


        //draw lines for reference

        bg.setColor(Color.RED);
        bg.setStroke(new BasicStroke(0.1f));

        //lower base
        for(int i=0;i<num_meridians;i++){

            printTextureLine(bg,i,(i+1)%num_meridians);
        }

        //faces
        int start=num_meridians;
        bg.setColor(Color.BLACK);
        for(int i=0;i<num_meridians;i++){

            printTextureLine(bg,start+i,start+i+1);
            printTextureLine(bg,start+i,start+i+(num_meridians+1));
            printTextureLine(bg,start+i+1,start+i+1+(num_meridians+1));
            printTextureLine(bg,start+i+(num_meridians+1),start+i+1+(num_meridians+1));
        }

        //upper base
        bg.setColor(Color.BLUE);
        start=num_meridians+2*(num_meridians+1);
        for(int i=0;i<num_meridians;i++){

            printTextureLine(bg,start+i,start+(i+1)%num_meridians);
        }

    }






}
