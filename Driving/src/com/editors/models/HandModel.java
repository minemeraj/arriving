package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.Vector;

import javax.imageio.ImageIO;

public class HandModel extends MeshModel {

    private int[][][] splacFaces;
    private int[][][] neuroFaces;

    private double dx = 0;
    private double dy = 0;
    private double dz = 0;

    private int bx=10;
    private int by=10;

    private int tetaNumSections=10;

    public static final String NAME="Hand";

    public HandModel(double dx, double dy, double dz) {

        super();
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    @Override
    public void initMesh() {

        points=new Vector();
        texturePoints=new Vector();

        int xNumSections=faceData[0].length;

        int zNumSections=skullSections.length;



        double deltax=dx/(xNumSections-1);
        double deltaz=dz/(zNumSections-1);


        int nSplacnoPoints=0;

        //splanchnocranium
        for (int k = 0; k < zNumSections; k++) {

            double[] d=skullSections[k];

            double z=dz*d[0];

            for (int i = 0; i < xNumSections; i++) {

                //center points in x=0 with -dx/2;
                double x=deltax*i-dx*0.5;
                //double y=-s[i]*dy*d[2];

                double y=-dy*faceData[k][i];

                addPoint(x, y, z);

                nSplacnoPoints++;
            }

        }

        //neurocranium

        double dTeta=Math.PI/(tetaNumSections-1);
        for (int k = 0; k < zNumSections; k++) {


            double rx=dx*0.5;
            double ry=dy;

            double[] d=skullSections[k];

            double z=dz*d[0];
            ry=ry*d[1];

            for (int i = 0; i < tetaNumSections; i++) {

                double teta=dTeta*i;


                double x=rx*Math.cos(teta);
                double y=ry*Math.sin(teta);

                addPoint(x, y, z);

            }

        }

        //splanchnocranium

        int nSplacnoTPoints=0;

        for (int k = 0; k < zNumSections; k++) {

            double[] d=skullSections[k];

            double z=by+dz*d[0];

            for (int i = 0; i < xNumSections; i++) {

                double x=bx+deltax*i;


                addTPoint(x, z, 0);

                nSplacnoTPoints++;
            }

        }

        //neurocranium
        double deltaTeta=(dy*Math.PI)/(tetaNumSections-1);

        for (int k = 0; k < zNumSections; k++) {

            double[] d=skullSections[k];

            double z=by+dz*d[0];

            for (int i = 0; i < tetaNumSections; i++) {

                double x=bx+dx+deltaTeta*i;

                addTPoint(x, z, 0);

            }

        }

        splacFaces=buildSinglePlaneFaces(xNumSections, zNumSections,
                0, 0);
        neuroFaces=buildSinglePlaneFaces(tetaNumSections, zNumSections,
                nSplacnoPoints, nSplacnoTPoints);

        IMG_WIDTH=(int) (2*bx+dx+dy*Math.PI);
        IMG_HEIGHT=(int) (2*by+dz);

    }


    @Override
    public void printTexture(Graphics2D bg) {

        bg.setColor(Color.BLACK);
        bg.setStroke(new BasicStroke(0.1f));
        printTextureFaces(bg,splacFaces);

        bg.setColor(Color.BLUE);
        bg.setStroke(new BasicStroke(0.1f));
        printTextureFaces(bg,neuroFaces);
    }

    @Override
    public void printMeshData(PrintWriter pw) {
        super.printMeshData(pw);
        super.printFaces(pw, splacFaces);
        super.printFaces(pw, neuroFaces);
    }

    private double[][] skullSections={
            {0.0,0.8837,0.2093},
            {0.0574,0.84885,0.22095},
            {0.1148,0.814,0.2326},
            {0.14755,0.814,0.2326},
            {0.1803,0.814,0.2326},
            {0.22949999999999998,0.8371999999999999,0.25585},
            {0.2787,0.8604,0.2791},
            {0.3033,0.87205,0.3256},
            {0.3279,0.8837,0.3721},
            {0.40165,0.9244,0.3256},
            {0.4754,0.9651,0.2791},
            {0.541,0.9709,0.26745},
            {0.6066,0.9767,0.2558},
            {0.6475500000000001,0.9883500000000001,0.23255000000000003},
            {0.6885,1.0,0.2093},
            {0.7705,0.9318500000000001,0.13955},
            {0.8525,0.8637,0.0698},
            {0.8934500000000001,0.80975,0.01745},
            {0.9344,0.7558,-0.0349},
            {0.9672000000000001,0.57555,-0.22094999999999998},
            {1.0,0.3953,-0.407},
    };


    /*double[][] splancnoSections={

			{0.0,0.6614,0.8660,0.9682,1.0,0.9682,0.8660,0.6614,0.0},
			{0.0,0.6614,0.8660,0.9682,1.0,0.9682,0.8660,0.6614,0.0},
			{0.0,0.6614,0.8660,0.9682,1.0,0.9682,0.8660,0.6614,0.0},
			{0.0,0.6614,0.8660,0.9682,1.0,0.9682,0.8660,0.6614,0.0},
			{0.0,0.6614,0.8660,0.9682,1.0,0.9682,0.8660,0.6614,0.0},
			{0.0,0.6614,0.8660,0.9682,1.0,0.9682,0.8660,0.6614,0.0},
			{0.0,0.6614,0.8660,0.9682,1.0,0.9682,0.8660,0.6614,0.0},
			{0.0,0.6614,0.8660,0.9682,1.0,0.9682,0.8660,0.6614,0.0},
			{0.0,0.6614,0.8660,0.9682,1.0,0.9682,0.8660,0.6614,0.0},
			{0.0,0.6614,0.8660,0.9682,1.0,0.9682,0.8660,0.6614,0.0},
			{0.0,0.6614,0.8660,0.9682,1.0,0.9682,0.8660,0.6614,0.0},


	};*/

    private double[][] faceData={

            {0.0,0.0692,0.1384,0.15985,0.1813,0.19195,0.2026,0.20595000000000002,0.2093,0.20595000000000002,0.2026,0.19195,0.1813,0.15985,0.1384,0.0692,0.0},
            {0.0,0.07305,0.1461,0.16872499999999999,0.19135,0.202625,0.2139,0.217425,0.22095,0.217425,0.2139,0.202625,0.19135,0.16872499999999999,0.1461,0.07305,0.0},
            {0.0,0.0769,0.1538,0.17759999999999998,0.2014,0.2133,0.2252,0.2289,0.2326,0.2289,0.2252,0.2133,0.2014,0.17759999999999998,0.1538,0.0769,0.0},
            {0.0,0.0769,0.1538,0.17759999999999998,0.2014,0.2133,0.2252,0.2289,0.2326,0.2289,0.2252,0.2133,0.2014,0.17759999999999998,0.1538,0.0769,0.0},
            {0.0,0.0769,0.1538,0.17759999999999998,0.2014,0.2133,0.2252,0.2289,0.2326,0.2289,0.2252,0.2133,0.2014,0.17759999999999998,0.1538,0.0769,0.0},
            {0.0,0.0846,0.1692,0.195375,0.22155,0.234625,0.2477,0.25177499999999997,0.25585,0.25177499999999997,0.2477,0.234625,0.22155,0.195375,0.1692,0.0846,0.0},
            {0.0,0.0923,0.1846,0.21315,0.2417,0.25595,0.2702,0.27465,0.2791,0.27465,0.2702,0.25595,0.2417,0.21315,0.1846,0.0923,0.0},
            {0.0,0.08845,0.1769,0.20425,0.2316,0.24527500000000002,0.25895,0.292275,0.3256,0.282275,0.25895,0.24527500000000002,0.2316,0.20425,0.1769,0.08845,0.0},
            {0.0,0.0846,0.1692,0.19535,0.2215,0.2346,0.2477,0.2899,0.3721,0.2899,0.2477,0.2346,0.2215,0.19535,0.1692,0.0846,0.0},//nose tip
            {0.0,0.08845,0.1769,0.17925,0.18159999999999998,0.20277499999999998,0.22395,0.254775,0.3256,0.254775,0.22394999999999998,0.20277499999999998,0.18159999999999998,0.17925,0.1769,0.08845,0.0},
            {0.0,0.0923,0.1446,0.15315,0.1617,0.17095,0.2002,0.23965,0.2791,0.23965,0.2002,0.17095,0.1617,0.15315,0.1446,0.0923,0.0},//10 eye line
            {0.0,0.08845,0.1769,0.17925,0.18159999999999998,0.20277499999999998,0.22394999999999998,0.24570000000000003,0.26745,0.24570000000000003,0.22394999999999998,0.20277499999999998,0.18159999999999998,0.17925,0.1769,0.08845,0.0},
            {0.0,0.0846,0.1692,0.19535,0.2215,0.2346,0.2477,0.25175000000000003,0.2558,0.25175000000000003,0.2477,0.2346,0.2215,0.19535,0.1692,0.0846,0.0},
            {0.0,0.0769,0.1538,0.17759999999999998,0.2014,0.213275,0.22515000000000002,0.22885000000000003,0.23255000000000003,0.22885000000000003,0.22515000000000002,0.213275,0.2014,0.17759999999999998,0.1538,0.0769,0.0},
            {0.0,0.0692,0.1384,0.15985,0.1813,0.19195,0.2026,0.20595000000000002,0.2093,0.20595000000000002,0.2026,0.19195,0.1813,0.15985,0.1384,0.0692,0.0},
            {0.0,0.04615,0.0923,0.106575,0.12085,0.127975,0.1351,0.137325,0.13955,0.137325,0.1351,0.127975,0.12085,0.106575,0.0923,0.04615,0.0},
            {0.0,0.0231,0.0462,0.0533,0.0604,0.064,0.0676,0.0687,0.0698,0.0687,0.0676,0.064,0.0604,0.0533,0.0462,0.0231,0.0},
            {0.0,0.005775,0.01155,0.013325,0.0151,0.016,0.0169,0.017175,0.01745,0.017175,0.0169,0.016,0.0151,0.013325,0.01155,0.005775,0.0},
            {0.0,-0.01155,-0.0231,-0.02665,-0.0302,-0.032,-0.0338,-0.03435,-0.0349,-0.03435,-0.0338,-0.032,-0.0302,-0.02665,-0.0231,-0.01155,0.0},
            {0.0,-0.073075,-0.14615,-0.16874999999999998,-0.19135,-0.20265,-0.21395,-0.21744999999999998,-0.22094999999999998,-0.21744999999999998,-0.21395,-0.20265,-0.19135,-0.16874999999999998,-0.14615,-0.073075,0.0},
            {0.0,-0.1346,-0.2692,-0.31084999999999996,-0.3525,-0.37329999999999997,-0.3941,-0.40054999999999996,-0.407,-0.40054999999999996,-0.3941,-0.37329999999999997,-0.3525,-0.31084999999999996,-0.2692,-0.1346,0.0},

    };


    public static void main(String[] args) {
        //new HeadModel(200,200,284).printNewCode();
        new HandModel(200,200,284).printXSections();
    }

    /**
     * DIAGNOSTICS UTILITY
     *
     */
    public void printSections(){

        int w=(int) (dx)+2*bx;
        int h=(int) (2*dy)+2*by;

        int xNumSections=faceData[0].length;
        double deltax=dx/(xNumSections-1);

        //double y=-s[i]*dy*d[2];



        BufferedImage buf=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_INDEXED);

        File file=new File("sections.jpg");

        try {



            Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();

            bufGraphics.setColor(Color.BLACK);
            bufGraphics.fillRect(0,0,w,h);

            bufGraphics.setColor(Color.WHITE);

            for (int k = 0; k < faceData.length; k++) {

                double[] pts = faceData[k];


                if(k==8){

                    bufGraphics.setColor(Color.WHITE);
                }else if(k==9){

                    bufGraphics.setColor(Color.RED);
                }else if(k==10){

                    bufGraphics.setColor(Color.GREEN);
                    continue;
                } else {
                    continue;
                }

                for (int i = 0; i < pts.length-1; i++) {

                    int x=(int)(deltax*i-dx*0.5)+bx;
                    int y=(int)(-dy*faceData[k][i])+by;

                    bufGraphics.fillRect(x-1, y-1, 2, 2);
                }


            }

            //////
            int zNumSections=skullSections.length;
            double dTeta=Math.PI/(tetaNumSections-1);
            for (int k = 0; k < zNumSections; k++) {


                double rx=dx*0.5;
                double ry=dy;

                double[] d=skullSections[k];

                double z=dz*d[0];
                ry=ry*d[1];

                for (int i = 0; i < tetaNumSections; i++) {

                    double teta=dTeta*i;


                    int x=(int)(rx*Math.cos(teta));
                    int y=(int)(ry*Math.sin(teta));


                    bufGraphics.fillRect(x-1, y-1, 2, 2);
                }


            }

            ImageIO.write(buf,"gif",file);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * DIAGNOSTICS UTILITY
     *
     */
    private void printXSections(){

        int w=(int) (dy*2)+2*bx;
        int h=(int) dz+2*by;

        int xNumSections=faceData[0].length;
        double deltax=dx/(xNumSections-1);

        BufferedImage buf=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_INDEXED);

        File file=new File("lsections.jpg");

        try {



            Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();

            bufGraphics.setColor(Color.BLACK);
            bufGraphics.fillRect(0,0,w,h);

            bufGraphics.setColor(Color.WHITE);

            Color[] cols={Color.RED,Color.GREEN,Color.CYAN,Color.YELLOW,
                    Color.MAGENTA,Color.PINK,Color.BLUE,Color.GRAY,Color.WHITE};

            for (int k = 0; k < faceData.length-1; k++) {

                double[] pts = faceData[k];

                for (int i = 0; i < pts.length; i++) {

                    if(i<8) {
                        continue;
                    }

                    bufGraphics.setColor(cols[i-8]);

                    int x=(int)(-dy*faceData[k][i])+(int)dy+bx;
                    double[] d=skullSections[k];
                    int y=(int) (dz*d[0])+by;

                    int x1=(int)(-dy*faceData[k+1][i])+(int)dy+bx;
                    double[] d1=skullSections[k+1];
                    int y1=(int) (dz*d1[0])+by;

                    bufGraphics.drawLine(x, h-(y), x1, h-y1);
                }


            }

            //////
            bufGraphics.setColor(Color.GREEN);


            int zNumSections=skullSections.length;
            double dTeta=Math.PI/(tetaNumSections-1);
            for (int k = 0; k < zNumSections-1; k++) {


                double rx=dx*0.5;
                double ry=dy;
                double ry1=dy;

                double[] d=skullSections[k];
                double[] d1=skullSections[k+1];

                ry=ry*d[1];
                ry1=ry1*d1[1];

                for (int i = 0; i < tetaNumSections; i++) {

                    double teta=dTeta*i;


                    int x=(int)(ry*Math.sin(teta))+(int)dy+bx;
                    int y=(int) (dz*d[0])+by;

                    int x1=(int)(ry1*Math.sin(teta))+(int)dy+bx;
                    int y1=(int) (dz*d1[0])+by;


                    bufGraphics.drawLine(x, h-(y), x1, h-y1);
                }


            }

            ImageIO.write(buf,"gif",file);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }



    private void printNewCode() {

        for (int i = 0; i < skullSections.length; i++) {

            double[] sk0 = skullSections[i];

            System.out.print("{");

            for (int j = 0; j < sk0.length; j++) {

                if(j>0) {
                    System.out.print(",");
                }
                System.out.print(sk0[j]);
            }

            System.out.println("},");

            if(i<skullSections.length-1){


                double[] sk1 = skullSections[i+1];

                System.out.print("{");

                for (int j = 0; j < sk0.length; j++) {

                    if(j>0) {
                        System.out.print(",");
                    }
                    System.out.print(m(sk0[j],sk1[j]));
                }

                System.out.println("},");

            }
        }

        System.out.println("--------------");

        for (int i = 0; i < faceData.length; i++) {

            double[] fd0 = faceData[i];

            System.out.print("{");

            for (int j = 0; j < fd0.length; j++) {
                if(j>0) {
                    System.out.print(",");
                }
                System.out.print(fd0[j]);

            }

            System.out.println("},");

            if(i<faceData.length-1){

                double[] fd1 = faceData[i+1];

                System.out.print("{");

                for (int j = 0; j < fd0.length; j++) {
                    if(j>0) {
                        System.out.print(",");
                    }
                    System.out.print(m(fd0[j],fd1[j]));

                }

                System.out.println("},");
            }
        }

        /*for (int i = 0; i < faceData.length; i++) {

			double[] fd0 = faceData[i];

			System.out.print("{");

			for (int j = 0; j < fd0.length; j++) {
				if(j>0)
					System.out.print(",");
				System.out.print(fd0[j]);
				if(j<fd0.length-1){

					System.out.print(",");
					System.out.print(m(fd0[j],fd0[j+1]));
				}

			}

			System.out.println("},");


		}*/

    }

    private double m(double d, double e) {
        return (d+e)*0.5;
    }



}
