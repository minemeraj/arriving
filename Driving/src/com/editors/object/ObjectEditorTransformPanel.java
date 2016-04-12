package com.editors.object;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.editors.DoubleTextField;

class ObjectEditorTransformPanel extends JDialog implements ActionListener{
	
	private JPanel center;
	private JButton confirm;
	private JButton cancel;
	private JTextField referencex;
	private JTextField referencey;
	private JTextField referencez;
	private JRadioButton operationRotate;
	private JRadioButton operationReflect;
	private JRadioButton axisX;
	private JRadioButton axisY;
	private JRadioButton axisZ;
	
	private ObjectEditor editor=null;
	private DoubleTextField rotationAngle;
	
	ObjectEditorTransformPanel(ObjectEditor editor){
		
		
		this.editor=editor;
	
	    setTitle("Transform Panel");
		setSize(500,300);
		setLocation(20,20);
		setModal(true);

		center=new JPanel();
		center.setLayout(null);
		add(center);
		
		int r=10;
		
		JLabel jlb=new JLabel("Reference Point:");
		jlb.setBounds(10,r,100,20);
		center.add(jlb);
		
		r+=30;

		
		jlb=new JLabel("X");
		jlb.setBounds(10,r,30,20);
		center.add(jlb);

		referencex=new DoubleTextField();
		referencex.setBounds(50,r,100,20);
		referencex.setText("0");
		center.add(referencex);
		
		jlb=new JLabel("Y");
		jlb.setBounds(160,r,30,20);
		center.add(jlb);
		
		referencey=new DoubleTextField();
		referencey.setBounds(200,r,100,20);
		referencey.setText("0");
		center.add(referencey);
		
		
		jlb=new JLabel("Z");
		jlb.setBounds(310,r,30,20);
		center.add(jlb);
		
		referencez=new DoubleTextField();
		referencez.setBounds(350,r,100,20);
		referencez.setText("0");
		center.add(referencez);
		
		r+=30;
		
		jlb=new JLabel("Operation:");
		jlb.setBounds(10,r,100,20);
		center.add(jlb);
				
		r+=30;
				
		operationRotate=new JRadioButton("Rotate");
		operationRotate.setBounds(10,r,100,20);
		center.add(operationRotate);
		
		operationReflect=new JRadioButton("Reflect");
		operationReflect.setBounds(130,r,100,20);
		center.add(operationReflect);
		
		ButtonGroup operation=new ButtonGroup();
		operation.add(operationRotate);
		operation.add(operationReflect);
		
		r+=30;
		
		jlb=new JLabel("Rotation angle (°):");
		jlb.setBounds(10,r,100,20);
		center.add(jlb);
		
		rotationAngle=new DoubleTextField();
		rotationAngle.setBounds(120,r,100,20);
		center.add(rotationAngle);
		
		r+=30;
		
		jlb=new JLabel("Operation axis:");
		jlb.setBounds(10,r,200,20);
		center.add(jlb);
		
		r+=30;
		
		axisX=new JRadioButton("X");
		axisX.setBounds(10,r,50,20);
		center.add(axisX);
		
		axisY=new JRadioButton("Y");
		axisY.setBounds(90,r,50,20);
		center.add(axisY);
		
		axisZ=new JRadioButton("Z");
		axisZ.setBounds(170,r,50,20);
		center.add(axisZ);
		
		ButtonGroup direction=new ButtonGroup();
		direction.add(axisX);
		direction.add(axisY);
		direction.add(axisZ);
		
		r+=50;

		confirm=new JButton("Confirm");
		confirm.setBounds(50,r,100,20);
		confirm.addActionListener(this);
		center.add(confirm);


		cancel=new JButton("Cancel");
		cancel.setBounds(160,r,100,20);
		cancel.addActionListener(this);
		center.add(cancel);

		
	    setVisible(true);

	}
    
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object o = e.getSource();

		if(o==confirm){
			
			applyOperation();
			
		}
		else if(o==cancel){
			
			dispose();
			
		}
		
		
	}

	private void applyOperation() {

		if(!operationRotate.isSelected() && !operationReflect.isSelected()){

			JOptionPane.showMessageDialog(this,"Please select operation","Error",JOptionPane.ERROR_MESSAGE);
			return;

		}

		if(!axisX.isSelected() && !axisY.isSelected() && !axisZ.isSelected()){

			JOptionPane.showMessageDialog(this,"Please select axix ","Error",JOptionPane.ERROR_MESSAGE);
			return;

		}

		if(referencex.getText().equals("") || referencey.getText().equals("") || referencez.getText().equals("")){

			JOptionPane.showMessageDialog(this,"Please insert point data","Error",JOptionPane.ERROR_MESSAGE);
			return;

		}

		if(operationRotate.isSelected() && rotationAngle.getText().equals("")){

			JOptionPane.showMessageDialog(this,"Please insert roation angle","Error",JOptionPane.ERROR_MESSAGE);
			return;

		}

		editor.prepareUndo();

		double refX=Double.parseDouble(referencex.getText());
		double refY=Double.parseDouble(referencey.getText());
		double refZ=Double.parseDouble(referencez.getText());

		PolygonMesh mesh=editor.getMeshes()[editor.getACTIVE_PANEL()];



		if(operationRotate.isSelected() ){

			double rotation=Math.PI*Double.parseDouble(rotationAngle.getText())/180.0;

			Point3D versus=null;

			if(axisX.isSelected()){
				versus=new Point3D(1,0,0);	
			}
			else if(axisY.isSelected()){
				versus=new Point3D(0,1,0);	
			}
			else if(axisZ.isSelected()){
				versus=new Point3D(0,0,1);	
			}

			double[][] matrix = ObjectEditor.getRotationMatrix(versus,rotation);
			ObjectEditor.rotate(mesh.points,matrix,refX,refY,refZ);

		}
		else if(operationReflect.isSelected() ){


			for(int i=0;i<mesh.points.length;i++){

				Point3D p=mesh.points[i];

				if(axisX.isSelected()){

					p.x=2*refX-p.x;
				}
				else if(axisY.isSelected()){
					p.y=2*refY-p.y;
				}
				else if(axisZ.isSelected()){
					p.z=2*refZ-p.z;
				}
			}
			
			for (int i = 0; i < mesh.polygonData.size(); i++) {
				LineData ld = (LineData) mesh.polygonData.get(i);
				ld=invertLineData(ld);
				mesh.polygonData.set(i,ld);
			}
		}


		editor.repaint();
		
		dispose();

	}
	
	private LineData invertLineData(LineData modifiedLineData) {
		
		LineData ld=new LineData();
		
		for (int i = modifiedLineData.size()-1; i >=0; i--) {
			
		
			ld.addIndex(modifiedLineData.getIndex(i));
			
		}
		ld.setTexture_index(modifiedLineData.getTexture_index());
		return ld;
		
	}



}
