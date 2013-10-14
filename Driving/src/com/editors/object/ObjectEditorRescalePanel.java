package com.editors.object;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.Point3D;
import com.PolygonMesh;
import com.editors.DoubleTextField;

public class ObjectEditorRescalePanel extends JDialog implements ActionListener{
	
	private JPanel center;
	private JButton confirm;
	private JButton cancel;

	private JTextField xFactor;
	private JTextField yFactor;
	private JTextField zFactor;

	private JCheckBox checkRescaleX;
	private JCheckBox checkRescaleY;
	private JCheckBox checkRescaleZ;
	
	
	ObjectEditor editor=null;

	
	public ObjectEditorRescalePanel(ObjectEditor editor){
		
		
		this.editor=editor;
	
	    setTitle("Rescale Selected");
		setSize(280,200);
		setLocation(20,20);
		setModal(true);

		center=new JPanel();
		center.setLayout(null);
		add(center);
		
		int r=10;
		
		JLabel jlb=new JLabel("X Factor:");
		jlb.setBounds(10,r,80,20);
		center.add(jlb);
		
        xFactor=new JTextField("1");
        xFactor.setBounds(100,r,100,20);
		center.add(xFactor);

        checkRescaleX=new JCheckBox();
        checkRescaleX.setBounds(220,r,100,20);
		center.add(checkRescaleX);
		checkRescaleX.setSelected(true);
		
		r+=30;
		
		jlb=new JLabel("Y Factor:");
		jlb.setBounds(10,r,80,20);
		center.add(jlb);
		
        yFactor=new JTextField("1");
        yFactor.setBounds(100,r,100,20);
		center.add(yFactor);
		
        checkRescaleY=new JCheckBox();
        checkRescaleY.setBounds(220,r,100,20);
        checkRescaleY.setSelected(true);
		center.add(checkRescaleY);
		
		r+=30;
		
		jlb=new JLabel("Z Factor:");
		jlb.setBounds(10,r,80,20);
		center.add(jlb);
		
        zFactor=new JTextField("1");
        zFactor.setBounds(100,r,100,20);
		center.add(zFactor);
		
        checkRescaleZ=new JCheckBox();
        checkRescaleZ.setBounds(220,r,100,20);
        checkRescaleZ.setSelected(true);
		center.add(checkRescaleZ);
		
		r+=50;

		confirm=new JButton("Confirm");
		confirm.setBounds(30,r,100,20);
		confirm.addActionListener(this);
		center.add(confirm);


		cancel=new JButton("Cancel");
		cancel.setBounds(140,r,100,20);
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

		if(
				xFactor.getText()==null || xFactor.getText().equals("")				
				||yFactor.getText()==null || yFactor.getText().equals("")
				||zFactor.getText()==null || zFactor.getText().equals("")
		
		){

			JOptionPane.showMessageDialog(this,"There are missing value!","Error",JOptionPane.ERROR_MESSAGE);
			return;

		}

		if(
				xFactor.getText().indexOf(",")>=0				
				||yFactor.getText().indexOf(",")>=0
				||zFactor.getText().indexOf(",")>=0
		
		){

			JOptionPane.showMessageDialog(this,"Use the point . for decimals","Error",JOptionPane.ERROR_MESSAGE);
			return;

		}

		editor.prepareUndo();

		double alfax=1.0;
		double alfay=1.0;
		double alfaz=1.0;
		
		if(checkRescaleX.isSelected())
			alfax=Double.parseDouble(xFactor.getText());
		if(checkRescaleY.isSelected())
			alfay=Double.parseDouble(yFactor.getText());
		if(checkRescaleZ.isSelected())
			alfaz=Double.parseDouble(zFactor.getText());
		
		

		PolygonMesh mesh=editor.meshes[editor.ACTIVE_PANEL];


		for(int i=0;i<mesh.points.length;i++){
			
			Point3D p=mesh.points[i];
			
			if(!p.isSelected())
				continue;
			
			p.x=p.x*alfax;
			p.y=p.y*alfay;
			p.z=p.z*alfaz;
		}

	
		editor.repaint();

		dispose();

	}

}
