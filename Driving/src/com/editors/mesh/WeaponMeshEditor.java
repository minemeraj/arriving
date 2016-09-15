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
import com.editors.models.SteeringWheelModel;


public class WeaponMeshEditor extends MeshModelEditor implements KeyListener, ItemListener{

    String title="Weapon model";
    private DoubleTextField dz_text;
    private DoubleTextField dy_text;
    private DoubleTextField dx_text;
    private DoubleTextField tilt_angle;
    private DoubleTextField dx1_text;
    private DoubleTextField dy1_text;
    private JComboBox chooseWeapon;
    private DoubleTextField dz1_text;
    private IntegerTextField num_meridians;
    private DoubleTextField dy2_text;
    private DoubleTextField dx2_text;
    private DoubleTextField dy3_text;

    private boolean skipItemChanged=false;
    private DoubleTextField dz2_text;
    private IntegerTextField num_parallels;

    public static int STEERING_WHEEL=0;

    public static void main(String[] args) {

        WeaponMeshEditor fm=new WeaponMeshEditor(790,350);
    }


    public WeaponMeshEditor(int W, int H) {
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


        JLabel lr=new JLabel("Tilt angle:");
        lr.setBounds(a0,r,80,20);
        center.add(lr);
        tilt_angle=new DoubleTextField(8);
        tilt_angle.setBounds(a1,r,120,20);
        center.add(tilt_angle);

        lr=new JLabel("N merid.");
        lr.setBounds(c0,r,80,20);
        center.add(lr);
        num_meridians=new IntegerTextField(8);
        num_meridians.setBounds(c1,r,120,20);
        center.add(num_meridians);

        lr=new JLabel("N par.");
        lr.setBounds(c2,r,80,20);
        center.add(lr);
        num_parallels=new IntegerTextField(8);
        num_parallels.setBounds(c3,r,120,20);
        num_parallels.setToolTipText("Num parallels");
        center.add(num_parallels);

        setRightData(100,200,100,50,0,150,0,0,0,0,0,0,0);


        r+=30;

        JLabel jlb=new JLabel("Weapon type:");
        jlb.setBounds(5, r, 100, 20);
        center.add(jlb);

        chooseWeapon=new JComboBox();
        chooseWeapon.setBounds(115, r, 130, 20);
        chooseWeapon.addKeyListener(this);
        chooseWeapon.addItem(new ValuePair("-1",""));
        chooseWeapon.addItem(new ValuePair(""+STEERING_WHEEL,SteeringWheelModel.NAME));


        chooseWeapon.addItemListener(this);


        chooseWeapon.setSelectedIndex(0);
        center.add(chooseWeapon);

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
        double tiltAngle = tilt_angle.getvalue();

        double dx1 = dx1_text.getvalue();
        double dy1 = dy1_text.getvalue();
        double dz1 = dz1_text.getvalue();
        double dz2 = dz2_text.getvalue();

        double dx2 = dx2_text.getvalue();
        double dy2 = dy2_text.getvalue();
        double dy3 = dy3_text.getvalue();

        int num_mer=num_meridians.getvalue();
        int num_par=num_parallels.getvalue();

        ValuePair vp= (ValuePair)chooseWeapon.getSelectedItem();

        int val=Integer.parseInt(vp.getId());
        if(val<0) {
            val=STEERING_WHEEL;
        }

        if(STEERING_WHEEL==val) {
            meshModel=new SteeringWheelModel(
                    dx,dx1,dx2,tiltAngle,num_mer,num_par
                    );
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

        if(obj==chooseWeapon){

            ValuePair vp= (ValuePair)chooseWeapon.getSelectedItem();

            int val=Integer.parseInt(vp.getId());
            if(val<0) {
                val=STEERING_WHEEL;
            }

            if(STEERING_WHEEL==val){
                setRightData(
                        100,0,0,//dx,dy,dz
                        -Math.PI*21.0/180.0,//tilt angle
                        30,0,0,//dx1,dy1,dz1
                        8,//num_meridians
                        10,//num parallel
                        30,0,0,0);//dx2,dy2,dz2,dy3
            }

        }

    }


    private void setRightData(
            double dx,
            double dy,
            double dz,
            double tiltAngle,
            double dx1,
            double dy1,
            double dz1,
            int num_merid,
            int num_para,
            double dx2,
            double dy2,
            double dz2,
            double dy3
            ) {


        dx_text.setText(dx);
        dy_text.setText(dy);
        dz_text.setText(dz);
        tilt_angle.setText(tiltAngle);
        dx1_text.setText(dx1);
        dy1_text.setText(dy1);
        dz1_text.setText(dz1);
        num_meridians.setText(num_merid);
        num_parallels.setText(num_para);
        dx2_text.setText(dx2);
        dy2_text.setText(dy2);
        dz2_text.setText(dz2);
        dy3_text.setText(dy3);
    }

    @Override
    public void saveData(PrintWriter pr) {

        ValuePair vp= (ValuePair)chooseWeapon.getSelectedItem();

        int val=Integer.parseInt(vp.getId());
        if(val<0) {
            val=STEERING_WHEEL;
        }

        pr.println("weapon="+val);
        pr.println("dx="+dx_text.getvalue());
        pr.println("dy="+dy_text.getvalue());
        pr.println("dz="+dz_text.getvalue());
        pr.println("roof_height="+tilt_angle.getvalue());
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

            if("weapon".equals(name)){

                skipItemChanged=true;

                int weapon=Integer.parseInt(value);

                for (int i = 0; i < chooseWeapon.getItemCount(); i++) {
                    ValuePair vp= (ValuePair) chooseWeapon.getItemAt(i);
                    if(vp.getId().equals(""+weapon))
                    {
                        chooseWeapon.setSelectedIndex(i);
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
                tilt_angle.setText(roofHeight);


            }
            else if("num_meridians".equals(name)){

                int nm=Integer.parseInt(value);
                num_meridians.setText(nm);


            }

        }
    }

}
