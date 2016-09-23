package com.editors.mesh;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.editors.DoubleTextField;
import com.editors.IntegerTextField;
import com.editors.ValuePair;
import com.editors.models.ArchBridge0Model;
import com.editors.models.BarrierModel;
import com.editors.models.BellTowerModel;
import com.editors.models.Chimney0Model;
import com.editors.models.Church0Model;
import com.editors.models.Church1Model;
import com.editors.models.Courtyard0Model;
import com.editors.models.Gambrel0Model;
import com.editors.models.HouseCShapedModel;
import com.editors.models.HouseCrossModel;
import com.editors.models.HouseGableModel;
import com.editors.models.HouseLShapedModel;
import com.editors.models.HouseThreeWingsModel;
import com.editors.models.Mansard0Model;
import com.editors.models.Shed0Model;
import com.editors.models.Stand0Model;

public class BulldingMeshEditor extends MeshModelEditor implements KeyListener, ItemListener{

    String title="Building model";
    private DoubleTextField dz_text;
    private DoubleTextField dy_text;
    private DoubleTextField dx_text;
    private DoubleTextField roof_height;
    private DoubleTextField dx1_text;
    private DoubleTextField dy1_text;
    private JComboBox chooseBuilding;
    private DoubleTextField dz1_text;
    private IntegerTextField num_meridians;
    private DoubleTextField dy2_text;
    private DoubleTextField dx2_text;
    private DoubleTextField dy3_text;

    private boolean skipItemChanged=false;
    private DoubleTextField dz2_text;

    public static int HOUSE0_GABLE=0;
    public static int HOUSE1_LSHAPED=1;
    public static int GAMBREL0=2;
    public static int MANSARD0=3;
    public static int BELLTOWER=4;
    public static int SHED0=5;
    public static int CHIMNEY0=6;
    public static int HOUSE2_THREEWINGS=7;
    public static int HOUSE3_CROSSHOUSE=8;
    public static int HOUSE4_C_SHAPED=9;
    public static int COURTYARD=10;
    public static int STAND0=11;
    public static int CHURCH0=12;
    public static int CHURCH1=13;
    public static int ARCHBRIDGE=14;
    public static int BARRIER0=15;

    public static void main(String[] args) {

        BulldingMeshEditor fm=new BulldingMeshEditor(790,350);
    }


    public BulldingMeshEditor(int W, int H) {
        super(W, H);
        setTitle(title);
    }


    @Override
    public void buildCenter() {

        int r=10;

        int a0=5;
        int a1=70;

        int c0=200;
        int c1=280;

        int c2=410;
        int c3=450;


        int c4=580;
        int c5=620;

        JLabel name=new JLabel("Description:");
        name.setBounds(5,r,120,20);
        center.add(name);

        r+=30;

        description=new JTextField();
        description.setBounds(30,r,380,20);
        description.setToolTipText("Description");
        description.setText("");
        center.add(description);

        r+=30;

        JLabel lx=new JLabel("dx:");
        lx.setBounds(a0,r,80,20);
        center.add(lx);
        dx_text=new DoubleTextField(8);
        dx_text.setBounds(a1,r,120,20);
        center.add(dx_text);

        lx=new JLabel("dx1:");
        lx.setBounds(c0,r,80,20);
        center.add(lx);
        dx1_text=new DoubleTextField(8);
        dx1_text.setBounds(c1,r,120,20);
        center.add(dx1_text);

        lx=new JLabel("dx2:");
        lx.setBounds(c2,r,80,20);
        center.add(lx);
        dx2_text=new DoubleTextField(8);
        dx2_text.setBounds(c3,r,120,20);
        center.add(dx2_text);

        r+=30;

        JLabel ly=new JLabel("dy:");
        ly.setBounds(a0,r,80,20);
        center.add(ly);
        dy_text=new DoubleTextField(8);
        dy_text.setBounds(a1,r,120,20);
        center.add(dy_text);

        ly=new JLabel("dy1:");
        ly.setBounds(c0,r,80,20);
        center.add(ly);
        dy1_text=new DoubleTextField(8);
        dy1_text.setBounds(c1,r,120,20);
        center.add(dy1_text);

        ly=new JLabel("dy2:");
        ly.setBounds(c2,r,80,20);
        center.add(ly);
        dy2_text=new DoubleTextField(8);
        dy2_text.setBounds(c3,r,120,20);
        center.add(dy2_text);

        ly=new JLabel("dy3:");
        ly.setBounds(c4,r,80,20);
        center.add(ly);
        dy3_text=new DoubleTextField(8);
        dy3_text.setBounds(c5,r,120,20);
        center.add(dy3_text);

        r+=30;

        JLabel lz=new JLabel("dz:");
        lz.setBounds(a0,r,80,20);
        center.add(lz);
        dz_text=new DoubleTextField(8);
        dz_text.setBounds(a1,r,120,20);
        center.add(dz_text);

        ly=new JLabel("dz1:");
        ly.setBounds(c0,r,80,20);
        center.add(ly);
        dz1_text=new DoubleTextField(8);
        dz1_text.setBounds(c1,r,120,20);
        center.add(dz1_text);

        ly=new JLabel("dz2:");
        ly.setBounds(c2,r,80,20);
        center.add(ly);
        dz2_text=new DoubleTextField(8);
        dz2_text.setBounds(c3,r,120,20);
        center.add(dz2_text);

        r+=30;


        JLabel lr=new JLabel("Roof h:");
        lr.setBounds(a0,r,80,20);
        center.add(lr);
        roof_height=new DoubleTextField(8);
        roof_height.setBounds(a1,r,120,20);
        center.add(roof_height);

        lr=new JLabel("N merid.");
        lr.setBounds(c0,r,80,20);
        center.add(lr);
        num_meridians=new IntegerTextField(8);
        num_meridians.setBounds(c1,r,120,20);
        center.add(num_meridians);

        setRightData(100,200,100,50,0,150,0,0,0,0,0,0);


        r+=30;

        JLabel jlb=new JLabel("Buiilding type:");
        jlb.setBounds(5, r, 100, 20);
        center.add(jlb);

        chooseBuilding=new JComboBox();
        chooseBuilding.setBounds(115, r, 130, 20);
        chooseBuilding.addKeyListener(this);
        chooseBuilding.addItem(new ValuePair("-1",""));
        chooseBuilding.addItem(new ValuePair(""+ARCHBRIDGE,ArchBridge0Model.NAME));
        chooseBuilding.addItem(new ValuePair(""+BARRIER0,BarrierModel.NAME));
        chooseBuilding.addItem(new ValuePair(""+BELLTOWER,BellTowerModel.NAME));
        chooseBuilding.addItem(new ValuePair(""+CHIMNEY0,Chimney0Model.NAME));
        chooseBuilding.addItem(new ValuePair(""+CHURCH0,Church0Model.NAME));
        chooseBuilding.addItem(new ValuePair(""+CHURCH1,Church1Model.NAME));
        chooseBuilding.addItem(new ValuePair(""+COURTYARD,Courtyard0Model.NAME));
        chooseBuilding.addItem(new ValuePair(""+HOUSE3_CROSSHOUSE,HouseCrossModel.NAME));
        chooseBuilding.addItem(new ValuePair(""+HOUSE4_C_SHAPED,HouseCShapedModel.NAME));
        chooseBuilding.addItem(new ValuePair(""+HOUSE0_GABLE,HouseGableModel.NAME));
        chooseBuilding.addItem(new ValuePair(""+GAMBREL0,Gambrel0Model.NAME));
        chooseBuilding.addItem(new ValuePair(""+HOUSE1_LSHAPED,HouseLShapedModel.NAME));
        chooseBuilding.addItem(new ValuePair(""+MANSARD0,Mansard0Model.NAME));
        chooseBuilding.addItem(new ValuePair(""+SHED0,Shed0Model.NAME));
        chooseBuilding.addItem(new ValuePair(""+STAND0,Stand0Model.NAME));
        chooseBuilding.addItem(new ValuePair(""+HOUSE2_THREEWINGS,HouseThreeWingsModel.NAME));

        chooseBuilding.addItemListener(this);


        chooseBuilding.setSelectedIndex(0);
        center.add(chooseBuilding);

        r+=30;

        meshButton=new JButton("Mesh");
        meshButton.setBounds(10,r,80,20);
        meshButton.addActionListener(this);
        center.add(meshButton);

        r+=30;

        textureButton=new JButton("Texture");
        textureButton.setBounds(10,r,90,20);
        textureButton.addActionListener(this);
        center.add(textureButton);

    }

    @Override
    public void initMesh() {


        double dx = dx_text.getvalue();
        double dy = dy_text.getvalue();
        double dz = dz_text.getvalue();
        double rh = roof_height.getvalue();

        double dx1 = dx1_text.getvalue();
        double dy1 = dy1_text.getvalue();
        double dz1 = dz1_text.getvalue();
        double dz2 = dz2_text.getvalue();

        double dx2 = dx2_text.getvalue();
        double dy2 = dy2_text.getvalue();
        double dy3 = dy3_text.getvalue();

        int num_mer=num_meridians.getvalue();

        ValuePair vp= (ValuePair)chooseBuilding.getSelectedItem();

        int val=Integer.parseInt(vp.getId());
        if(val<0) {
            val=HOUSE0_GABLE;
        }

        if(HOUSE0_GABLE==val) {
            meshModel=new HouseGableModel(dx,dy,dz,rh,dy1);
        } else if(HOUSE1_LSHAPED==val) {
            meshModel=new HouseLShapedModel(dx,dy,dz,rh,dx1,dy1);
        } else if(MANSARD0==val) {
            meshModel=new Mansard0Model(dx,dy,dz,rh,dx1,dy1);
        } else if(GAMBREL0==val) {
            meshModel=new Gambrel0Model(dx,dy,dz);
        } else if(BELLTOWER==val) {
            meshModel=new BellTowerModel(dx,dy,dz,rh);
        } else if(SHED0==val) {
            meshModel=new Shed0Model(dx,dy,dz,dz1);
        } else if(STAND0==val) {
            meshModel=new Stand0Model(dx,dy,dz,dz1);
        } else if(CHIMNEY0==val) {
            meshModel=new Chimney0Model(dx,dx1,dz,num_mer);
        } else if(HOUSE2_THREEWINGS==val) {
            meshModel=new HouseThreeWingsModel(dx,dy,dz,rh,dx1,dy1,dy2);
        } else if(HOUSE3_CROSSHOUSE==val) {
            meshModel=new HouseCrossModel(dx,dy,dz,rh,dx1,dy1,dy2,dy3);
        } else if(CHURCH0==val) {
            meshModel=new Church0Model(dx,dy,dz,rh,dx1,dy1,dy2,dy3,dz1,dz2);
        } else if(CHURCH1==val) {
            meshModel=new Church1Model(dx,dy,dz,rh,dx1,dy1,dy2,dy3,dz1,dz2);
        } else if(HOUSE4_C_SHAPED==val) {
            meshModel=new HouseCShapedModel(dx,dy,dz,rh,dx1,dy1,dx2);
        } else if(COURTYARD==val) {
            meshModel=new Courtyard0Model(dx,dy,dz,rh,dx1,dy1);
        } else if(ARCHBRIDGE==val) {
            meshModel=new ArchBridge0Model(dx,dy,dz,0,dy1,dz1,0,0,0,0,0,0);
        } else if(BARRIER0==val) {
            meshModel=new BarrierModel(dx,dy,dz,num_mer,dx1,dy1,dz1);
        }

        meshModel.setDescription(description.getText());

        meshModel.initMesh();
    }

    @Override
    public void printTexture(File file){

        meshModel.printTexture(file);

    }

    @Override
    public void printMeshData(PrintWriter pw) {

        meshModel.printMeshData(pw);
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }


    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

    }


    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }


    @Override
    public void itemStateChanged(ItemEvent arg0) {

        if(skipItemChanged) {
            return;
        }

        Object obj = arg0.getSource();

        if(obj==chooseBuilding){

            ValuePair vp= (ValuePair)chooseBuilding.getSelectedItem();

            int val=Integer.parseInt(vp.getId());
            if(val<0) {
                val=HOUSE0_GABLE;
            }

            if(HOUSE0_GABLE==val){
                setRightData(
                        100,200,100,//dx,dy,dz
                        50,//roof_height
                        0,150,0,//dx1,dy1,dz1
                        0,//num_meridians
                        0,0,0,0);//dx2,dy2,dz2,dy3
            }
            else if(HOUSE1_LSHAPED==val){
                setRightData(100,200,100,//dx,dy,dz
                        50,//roof_height
                        100,150,0,//dx1,dy1,dz1
                        0,//num_meridians
                        0,0,0,0);//dx2,dy2,dz2,dy3
            }
            else if(MANSARD0==val){
                setRightData(100,200,100,//dx,dy,dz
                        50,//roof_height
                        50,150,0,//dx1,dy1,dz1
                        0,//num_meridians
                        0,0,0,0);//dx2,dy2,dz2,dy3
            }
            else if(GAMBREL0==val){
                setRightData(100,200,100,//dx,dy,dz
                        0,//roof_height
                        0,0,0,//dx1,dy1,dz1
                        0,//num_meridians
                        0,0,0,0);//dx2,dy2,dz2,dy3
            }
            else if(BELLTOWER==val){
                setRightData(100,200,300,//dx,dy,dz
                        100,//roof_height
                        0,0,0,//dx1,dy1,dz1
                        0,//num_meridians
                        0,0,0,0);//dx2,dy2,dz2,dy3
            }
            else if(SHED0==val){
                setRightData(100,200,100,//dx,dy,dz
                        0,//roof_height
                        0,0,60,//dx1,dy1,dz1
                        0,//num_meridians
                        0,0,0,0);//dx2,dy2,dz2,dy3
            }
            else if(STAND0==val){
                setRightData(271,1328,26,//dx,dy,dz
                        0,//roof_height
                        0,0,26*13,//dx1,dy1,dz1
                        0,//num_meridians
                        0,0,0,0);//dx2,dy2,dz2,dy3
            }
            else if(CHIMNEY0==val){
                setRightData(100,0,300,//dx,dy,dz
                        0,//roof_height
                        80,0,0,//dx1,dy1,dz1
                        12,//num_meridians
                        0,0,0,0);//dx2,dy2,dz2,dy3
            }
            else if(HOUSE2_THREEWINGS==val){
                setRightData(100,200,100,//dx,dy,dz
                        50,//roof_height
                        100,150,0,//dx1,dy1,dz1
                        0,//num_meridians
                        0,200,0,0//dx2,dy2,dz2,dy3
                        );
            }
            else if(HOUSE3_CROSSHOUSE==val) {
                setRightData(100,200,100,//dx,dy,dz
                        50,//roof_height
                        100,150,0,//dx1,dy1,dz1
                        0,//num_meridians
                        0,100,0,150//dx2,dy2,dz2,dy3
                        );
            }
            else if(CHURCH0==val){
                setRightData(312,936,400,//dx,dy,dz
                        42,//roof_height
                        312,195,229,//dx1,dy1,dz1
                        0,//num_meridians
                        312,520,262,195//dx2,dy2,dz2,dy3
                        );
            }
            else if(CHURCH1==val){
                setRightData(100,200,100,//dx,dy,dz
                        50,//roof_height
                        100,100,80,//dx1,dy1,dz1
                        0,//num_meridians
                        0,100,0,100//dx2,dy2,dz2,dy3
                        );
            }
            else if(HOUSE4_C_SHAPED==val){
                setRightData(100,150,100,//dx,dy,dz
                        50,//roof_height
                        100,300,0,//dx1,dy1,dz1
                        0,//num_meridians
                        100,0,0,0//dx2,dy2,dz2,dy3
                        );
            }
            else if(COURTYARD==val){
                setRightData(200,200,100,//dx,dy,dz
                        50,//roof_height
                        100,100,0,//dx1,dy1,dz1
                        0,//num_meridians
                        0,0,0,0//dx2,dy2,dz2,dy3
                        );
            }
            else if(ARCHBRIDGE==val){
                setRightData(200,1000,500,//dx,dy,dz
                        0,//roof_height
                        0,100,350,//dx1,dy1,dz1
                        0,//num_meridians
                        0,0,0,0//dx2,dy2,dz2,dy3
                        );
            }
            else if(BARRIER0==val){
                setRightData(200,1000,200,//dx,dy,dz
                        0,//roof_height
                        200,100,300,//dx1,dy1,dz1
                        3,//num_meridians
                        0,0,0,0//dx2,dy2,dz2,dy3
                        );
            }
        }

    }


    private void setRightData(
            double dx,
            double dy,
            double dz,
            double roofHeight,
            double dx1,
            double dy1,
            double dz1,
            int num_merid,
            double dx2,
            double dy2,
            double dz2,
            double dy3
            ) {


        dx_text.setText(dx);
        dy_text.setText(dy);
        dz_text.setText(dz);
        roof_height.setText(roofHeight);
        dx1_text.setText(dx1);
        dy1_text.setText(dy1);
        dz1_text.setText(dz1);
        num_meridians.setText(num_merid);
        dx2_text.setText(dx2);
        dy2_text.setText(dy2);
        dz2_text.setText(dz2);
        dy3_text.setText(dy3);
    }

    @Override
    public void saveData(PrintWriter pr) {

        ValuePair vp= (ValuePair)chooseBuilding.getSelectedItem();

        int val=Integer.parseInt(vp.getId());
        if(val<0) {
            val=HOUSE0_GABLE;
        }

        pr.println("building="+val);
        pr.println("dx="+dx_text.getvalue());
        pr.println("dy="+dy_text.getvalue());
        pr.println("dz="+dz_text.getvalue());
        pr.println("roof_height="+roof_height.getvalue());
        pr.println("dx1="+dx1_text.getvalue());
        pr.println("dy1="+dy1_text.getvalue());
        pr.println("dz1="+dz1_text.getvalue());
        pr.println("num_meridians="+num_meridians.getvalue());
        pr.println("dx2="+dx2_text.getvalue());
        pr.println("dy2="+dy2_text.getvalue());
        pr.println("dy3="+dy3_text.getvalue());
    }

    @Override
    public void loadData(BufferedReader br) throws IOException {



        String str=null;
        while((str=br.readLine())!=null){

            if(str.indexOf("#")>=0 || str.length()==0) {
                continue;
            }

            int indx=str.indexOf("=");

            String name=str.substring(0,indx);
            String value=str.substring(1+indx);

            if("building".equals(name)){

                skipItemChanged=true;

                int building=Integer.parseInt(value);

                for (int i = 0; i < chooseBuilding.getItemCount(); i++) {
                    ValuePair vp= (ValuePair) chooseBuilding.getItemAt(i);
                    if(vp.getId().equals(""+building))
                    {
                        chooseBuilding.setSelectedIndex(i);
                        break;
                    }
                }
                skipItemChanged=false;

            }
            else if("dx".equals(name)){

                double dx=Double.parseDouble(value);
                dx_text.setText(dx);


            }
            else if("dx1".equals(name)){

                double dx1=Double.parseDouble(value);
                dx1_text.setText(dx1);


            }
            else if("dx2".equals(name)){

                double dx2=Double.parseDouble(value);
                dx2_text.setText(dx2);


            }
            else if("dy".equals(name)){

                double dy=Double.parseDouble(value);
                dy_text.setText(dy);


            }
            else if("dy1".equals(name)){

                double dy1=Double.parseDouble(value);
                dy1_text.setText(dy1);


            }
            else if("dy2".equals(name)){

                double dy2=Double.parseDouble(value);
                dy2_text.setText(dy2);


            }
            else if("dy3".equals(name)){

                double dy3=Double.parseDouble(value);
                dy3_text.setText(dy3);


            }
            else if("dz".equals(name)){

                double dz=Double.parseDouble(value);
                dz_text.setText(dz);


            }
            else if("dz1".equals(name)){

                double dz1=Double.parseDouble(value);
                dz1_text.setText(dz1);


            }
            else if("roof_height".equals(name)){

                double roofHeight=Double.parseDouble(value);
                roof_height.setText(roofHeight);


            }
            else if("num_meridians".equals(name)){

                int nm=Integer.parseInt(value);
                num_meridians.setText(nm);


            }

        }
    }

}
