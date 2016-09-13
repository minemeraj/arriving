package com.editors.road;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.CubicMesh;
import com.DrawObject;
import com.LineData;
import com.Point3D;
import com.Point4D;
import com.PolygonMesh;
import com.SPLine;
import com.SPNode;
import com.SquareMesh;
import com.editors.DoubleTextField;
import com.editors.IntegerTextField;
import com.main.Road;

class RoadEditorCityManager extends JDialog implements ActionListener{

    private JPanel center;

    private int NX=10;
    private int NY=10;

    private int B_TX=0;
    private int B_TY=0;

    private double DX=200;
    private double DY=200;

    private double X0=0;
    private double Y0=0;

    private int WIDTH=230;
    private int HEIGHT=280;
    private IntegerTextField NX_Field;
    private IntegerTextField NY_Field;

    private DoubleTextField X0_Field;
    private DoubleTextField Y0_Field;

    private IntegerTextField B_TX_Field;
    private IntegerTextField B_TY_Field;

    private Object returnValue=null;

    private JButton update=null;
    private JButton cancel=null;




    RoadEditorCityManager(){

        setTitle("Create new grid");
        setLayout(null);


        setSize(WIDTH,HEIGHT);
        setModal(true);
        center=new JPanel(null);
        center.setBounds(0,0,WIDTH,HEIGHT);
        add(center);

        int r=10;

        JLabel jlb=new JLabel("NX:");
        jlb.setBounds(10,r,30,20);
        center.add(jlb);

        NX_Field=new IntegerTextField();
        NX_Field.setBounds(50,r,100,20);
        NX_Field.setToolTipText("NX Blocks");
        center.add(NX_Field);


        r+=30;

        jlb=new JLabel("NY:");
        jlb.setBounds(10,r,30,20);
        center.add(jlb);

        NY_Field=new IntegerTextField();
        NY_Field.setBounds(50,r,100,20);
        NY_Field.setToolTipText("NY Blocks");
        center.add(NY_Field);

        NX_Field.setText(NX);
        NY_Field.setText(NY);


        r+=30;

        jlb=new JLabel("X0:");
        jlb.setBounds(10,r,30,20);
        center.add(jlb);

        X0_Field=new DoubleTextField();
        X0_Field.setBounds(50,r,100,20);
        center.add(X0_Field);

        r+=30;

        jlb=new JLabel("Y0:");
        jlb.setBounds(10,r,30,20);
        center.add(jlb);

        Y0_Field=new DoubleTextField();
        Y0_Field.setBounds(50,r,100,20);
        center.add(Y0_Field);

        X0_Field.setText(0);
        Y0_Field.setText(0);


        r+=30;

        jlb=new JLabel("B TX:");
        jlb.setBounds(10,r,30,20);
        center.add(jlb);

        B_TX_Field=new IntegerTextField();
        B_TX_Field.setBounds(50,r,100,20);
        B_TX_Field.setToolTipText("X tiles for Block");
        center.add(B_TX_Field);

        r+=30;

        jlb=new JLabel("B TY:");
        jlb.setBounds(10,r,30,20);
        center.add(jlb);

        B_TY_Field=new IntegerTextField();
        B_TY_Field.setBounds(50,r,100,20);
        B_TY_Field.setToolTipText("Y tiles for Block");
        center.add(B_TY_Field);


        B_TX_Field.setText(13);
        B_TY_Field.setText(8);

        r+=30;

        update=new JButton("Update");
        update.setBounds(10,r,80,20);
        center.add(update);
        update.addActionListener(this);

        cancel=new JButton("Cancel");
        cancel.setBounds(100,r,80,20);
        center.add(cancel);
        cancel.addActionListener(this);

        returnValue=null;

        setVisible(true);


    }


    @Override
    public void actionPerformed(ActionEvent arg0) {


        Object obj = arg0.getSource();

        if(obj==update){

            update();
            dispose();

        }
        else if(obj==cancel){
            returnValue=null;
            dispose();
        }


    }



    private void update() {
        try{

            NX=NX_Field.getvalue();
            NY=NY_Field.getvalue();

            X0=X0_Field.getvalue();
            Y0=Y0_Field.getvalue();

            B_TX=B_TX_Field.getvalue();
            B_TY=B_TY_Field.getvalue();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        returnValue=this;



    }



    public Object getReturnValue() {
        return returnValue;
    }



    public int getNX() {
        return NX;
    }



    public void setNX(int nX) {
        NX = nX;
    }



    public int getNY() {
        return NY;
    }



    public void setNY(int nY) {
        NY = nY;
    }



    public double getDX() {
        return DX;
    }



    public void setDX(double dX) {
        DX = dX;
    }



    public double getDY() {
        return DY;
    }



    public void setDY(double dY) {
        DY = dY;
    }



    public double getX0() {
        return X0;
    }



    public void setX0(double x0) {
        X0 = x0;
    }



    public double getY0() {
        return Y0;
    }



    public void setY0(double y0) {
        Y0 = y0;
    }


    /**
     * Tile=texture unity is a ground texture
     * N tiles require N points in each x,y direction
     * X direction: Each block has a road on the left, plus a final right road.
     * X direction: Road centers are separated by a space of a road and a X block
     * X direction: first road center is a position X=1
     * Y direction: Each block has a road below, plus a final road above.
     * Y direction: Road centers are separated by a space of a road and a Y block
     * Y direction: first road center is a position Y=1
     * Y direction: in the old version, X direction roads have X centers
     * only between two vertical road
     * Objects block are separated by a space of a road and a block,
     * first block angle is at road span
     * The ground is larger and translated to give an external border
     * @param terrainMesh
     * @param splines
     * @param roadECM
     * @param drawObjects
     * @param objectMeshes
     */
    static void buildCustomCity1(PolygonMesh terrainMesh, ArrayList splines,
            RoadEditorCityManager roadECM, ArrayList drawObjects, CubicMesh[] objectMeshes) {



        int nx_blocks=roadECM.NX;
        int ny_blocks=roadECM.NY;

        int road_textures=2;
        int block_xtextures=roadECM.B_TX;
        int block_ytextures=roadECM.B_TY;


        int numx=nx_blocks*block_xtextures+(nx_blocks+1)*road_textures+1;
        int numy=ny_blocks*block_ytextures+(ny_blocks+1)*road_textures+1;

        double dx=roadECM.DX;
        double dy=roadECM.DY;

        double x_0=roadECM.X0;
        double y_0=roadECM.Y0;

        //new road

        splines.clear();


        Point4D[] newPoints = new Point4D[numy*numx];

        for(int i=0;i<numx;i++){
            for(int j=0;j<numy;j++){

                int tot=i+j*numx;

                double x=x_0+dx*i;
                double y=y_0+dy*j;

                newPoints[tot]=new Point4D(x,y,0);

            }

        }



        for(int i=0;i<numx;i++){

            //y direction roads
            if(i%(block_xtextures+road_textures)==1){

                ArrayList vTexturePoints=RoadEditor.buildTemplateTexturePoints(200);

                SPLine sp=new SPLine(vTexturePoints);

                int tot=i+0*numx;
                Point4D p=newPoints[tot];
                SPNode spn0=new SPNode(p.x,p.y,p.z,0.0,"FFFFFF",0);
                sp.addSPNode(spn0);

                tot=i+(numy-1)*numx;
                p=newPoints[tot];
                SPNode spn1=new SPNode(p.x,p.y,p.z,0.0,"FFFFFF",0);
                sp.addSPNode(spn1);
                sp.setLevel(Road.ROAD_LEVEL);
                splines.add(sp);
            }





        }

        //x roads
        for(int j=0;j<numy;j++){

            if(j%(block_ytextures+road_textures)==1	){

                ArrayList vTexturePoints=RoadEditor.buildTemplateTexturePoints(200);

                SPLine sp=new SPLine(vTexturePoints);

                int tot=0+j*numx;
                Point4D p=newPoints[tot];
                SPNode spn0=new SPNode(p.x,p.y,p.z,0.0,"FFFFFF",0);
                sp.addSPNode(spn0);

                tot=numx-1+j*numx;
                p=newPoints[tot];
                SPNode spn1=new SPNode(p.x,p.y,p.z,0.0,"FFFFFF",0);
                sp.addSPNode(spn1);
                sp.setLevel(Road.ROAD_LEVEL);
                splines.add(sp);



            }
        }

        ///// new terrain

        double gapX=-dx;
        double gapY=-dy;

        int numTerrainx=numx+2;
        int numTerrainy=numy+2;

        double[] newXTerrainPoints = new double[numTerrainx*numTerrainy];
        double[] newYTerrainPoints = new double[numTerrainx*numTerrainy];
        double[] newZTerrainPoints = new double[numTerrainx*numTerrainy];

        for(int i=0;i<numTerrainx;i++) {
            for(int j=0;j<numTerrainy;j++)
            {


                newXTerrainPoints[i+j*numTerrainx]=i*dx+x_0+gapX;
                newYTerrainPoints[i+j*numTerrainx]=j*dy+y_0+gapY;
                newZTerrainPoints[i+j*numTerrainx]=0;

            }
        }

        terrainMesh.xpoints=newXTerrainPoints;
        terrainMesh.ypoints=newYTerrainPoints;
        terrainMesh.zpoints=newZTerrainPoints;

        if(terrainMesh instanceof SquareMesh){
            ((SquareMesh)terrainMesh).setNumx(numTerrainx);
            ((SquareMesh)terrainMesh).setNumy(numTerrainy);
            ((SquareMesh)terrainMesh).setDx((int) dx);
            ((SquareMesh)terrainMesh).setDy((int) dy);
            ((SquareMesh)terrainMesh).setX0(x_0+gapX);
            ((SquareMesh)terrainMesh).setY0(y_0+gapY);
        }
        terrainMesh.polygonData=new ArrayList();

        ArrayList vTexturePoints=RoadEditor.buildTemplateTexturePoints(200);

        for(int i=0;i<numTerrainx-1;i++) {
            for(int j=0;j<numTerrainy-1;j++){


                int pl1=pos(i,j,numTerrainx,numTerrainy);
                int pl2=pos(i+1,j,numTerrainx,numTerrainy);
                int pl3=pos(i+1,j+1,numTerrainx,numTerrainy);
                int pl4=pos(i,j+1,numTerrainx,numTerrainy);

                Point3D pt0=(Point3D) vTexturePoints.get(0);
                Point3D pt1=(Point3D) vTexturePoints.get(1);
                Point3D pt2=(Point3D) vTexturePoints.get(2);
                Point3D pt3=(Point3D) vTexturePoints.get(3);

                LineData ld=new LineData();
                ld.addIndex(pl1,0,pt0.x,pt0.y);
                ld.addIndex(pl2,1,pt1.x,pt1.y);
                ld.addIndex(pl3,2,pt2.x,pt2.y);
                ld.addIndex(pl4,3,pt3.x,pt3.y);

                ld.setTexture_index(0);

                terrainMesh.polygonData.add(ld);

                terrainMesh.setLevel(Road.GROUND_LEVEL);

            }
        }

        //add objects

        drawObjects.clear();

        //set object=4 w=200px,l=600px
        int buildingXPixels=200;
        int buildingYPixels=600;

        int nbx=(int) (block_xtextures*dx/buildingXPixels);
        int nby=(int) (block_ytextures*dy/buildingYPixels);

        //keep some space
        if((block_xtextures*dx-buildingXPixels*nbx)/(nbx+1)==0){
            nbx=nbx-1;
        }

        if((block_ytextures*dy-buildingYPixels*nby)/(nby+1)==0){
            nby=nby-1;
        }

        double intervalX=(block_xtextures*dx-buildingXPixels*nbx)/(nbx+1);
        double intervalY=(block_ytextures*dy-buildingYPixels*nby)/(nby+1);

        for(int i=0;i<numx-1;i++){
            for(int j=0;j<numy-1;j++){

                if(i%(block_xtextures+road_textures)==road_textures

                        && 	j%(block_ytextures+road_textures)==road_textures
                        )
                {

                    int tot=i+j*numx;
                    Point3D p = newPoints[tot];

                    for(int ii=0;ii<nbx;ii++){

                        for(int jj=0;jj<nby;jj++){

                            double dpx=intervalX+ii*(intervalX+buildingXPixels);
                            double dpy=intervalY+jj*(intervalY+buildingYPixels);

                            DrawObject dro=new DrawObject();
                            dro.setX(p.x+dpx);
                            dro.setY(p.y+dpy);
                            dro.setIndex(4);
                            dro.setDx(objectMeshes[dro.getIndex()].getDeltaX2()-objectMeshes[dro.getIndex()].getDeltaX());
                            dro.setDy(objectMeshes[dro.getIndex()].getDeltaY2()-objectMeshes[dro.getIndex()].getDeltaY());
                            dro.setDz(objectMeshes[dro.getIndex()].getDeltaX());
                            dro.setHexColor("FFFFFF");
                            drawObjects.add(dro);


                            CubicMesh mesh=objectMeshes[dro.getIndex()].clone();

                            Point3D point = mesh.point000;

                            double ddx=-point.x+dro.getX();
                            double ddy=-point.y+dro.getY();
                            double ddz=-point.z+dro.getZ();

                            mesh.translate(ddx,ddy,ddz);

                            Point3D center=mesh.findCentroid();

                            if(dro.getRotation_angle()!=0) {
                                mesh.rotate(center.x,center.y,Math.cos(dro.getRotation_angle()),Math.sin(dro.getRotation_angle()));
                            }

                            dro.setMesh(mesh);

                        }

                    }




                }
            }
        }
    }


    private static int pos(int i, int j,  int numx, int numy) {

        return (i+j*numx);
    }


}
