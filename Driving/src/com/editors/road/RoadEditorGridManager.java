package com.editors.road;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.Point4D;
import com.editors.DoubleTextField;

public class RoadEditorGridManager extends JDialog implements ActionListener{
	
	private JPanel center;
	private JPanel bottom;
	
	int NX=10;
	int NY=10;
	
	double DX=200;
	double DY=200;
	
	double X0=0;
	double Y0=0;
	
	int WIDTH=200;
	int HEIGHT=260;
	private DoubleTextField NX_Field;
	private DoubleTextField NY_Field;
	
	private DoubleTextField X0_Field; 
	private DoubleTextField Y0_Field;
	
	private Object returnValue=null;
	
	JButton update=null;
	JButton cancel=null;
	private DoubleTextField DX_Field;
	private DoubleTextField DY_Field;
			

	public RoadEditorGridManager(Point4D[][] roadData){
		
		setTitle("Create new grid");
		setLayout(null);

		if(roadData!=null){
		
			NY=roadData.length;
			NY=roadData[0].length;
			setTitle("Grid manager");
		}
		setSize(WIDTH,HEIGHT);
		setModal(true);
		center=new JPanel(null);
		center.setBounds(0,0,WIDTH,HEIGHT);
		add(center);
		
		int r=10;
		
		JLabel jlb=new JLabel("NX:");
		jlb.setBounds(10,r,30,20);
		center.add(jlb);
		
		NX_Field=new DoubleTextField();
		NX_Field.setBounds(50,r,100,20);
		center.add(NX_Field);
		
		r+=30;
		
		jlb=new JLabel("NY:");
		jlb.setBounds(10,r,30,20);
		center.add(jlb);
		
		NY_Field=new DoubleTextField();
		NY_Field.setBounds(50,r,100,20);
		center.add(NY_Field);
		
		NX_Field.setText(NX);
		NY_Field.setText(NY);

		
		r+=30;
		
		jlb=new JLabel("DX:");
		jlb.setBounds(10,r,30,20);
		center.add(jlb);
		
		DX_Field=new DoubleTextField();
		DX_Field.setBounds(50,r,100,20);
		center.add(DX_Field);
		
		r+=30;
		
		jlb=new JLabel("DY:");
		jlb.setBounds(10,r,30,20);
		center.add(jlb);
		
		DY_Field=new DoubleTextField();
		DY_Field.setBounds(50,r,100,20);
		center.add(DY_Field);
		
		DX_Field.setText(DX);
		DY_Field.setText(DY);
		
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
			
			NX=(int) NX_Field.getvalue();
			NY=(int) NY_Field.getvalue();
		
			DX= DX_Field.getvalue();
			DY= DY_Field.getvalue();
						
			X0= X0_Field.getvalue();
			Y0= Y0_Field.getvalue();
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
	
	
	
	
	
}
