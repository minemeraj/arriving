package com.editors.autocars;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.editors.ComboElement;
import com.editors.DoubleTextField;

public class AutocarBuildPanel extends JDialog implements ActionListener{
	
	AutocarData autocarData=null;
	private JComboBox car_type_index;
	private DoubleTextField centerX;
	private DoubleTextField centerY;
	private DoubleTextField fi_angle;
	private DoubleTextField steering_angle;
	private DoubleTextField longitudinal_velocity;
	private DoubleTextField lateral_velocity;
	private JButton addButton;
	private JButton cancelButton;
	
	int WIDTH=200;
	int HEIGHT=350;
	
	public AutocarBuildPanel(double x, double y, double u){
		
		autocarData=null;
		
		setModal(true);
		setTitle("Add autocar");
		setSize(WIDTH,HEIGHT);
		setLocation(50,50);
		
		JPanel autocarPanel = new JPanel();
		autocarPanel.setBounds(0, 0, WIDTH, HEIGHT);
		autocarPanel.setLayout(null);

		int l = 10;

		JLabel jlb = new JLabel("Autocar");
		jlb.setBounds(10, l, 60, 20);
		autocarPanel.add(jlb);
		
		l += 30;

		
		jlb = new JLabel("Type");
		jlb.setBounds(10, l, 40, 20);
		autocarPanel.add(jlb);

		car_type_index = new JComboBox();
		car_type_index.setBounds(50, l, 100, 20);
		car_type_index.setToolTipText("Autocar type");
		autocarPanel.add(car_type_index);
		
		
		File directoryImg=new File("lib");
		File[] files=directoryImg.listFiles();	

		int count=0;
		
		for(int f=0;f<files.length;f++){

			if(files[f].getName().startsWith("cardefault3D_")){
				
				car_type_index.addItem(new ComboElement(""+count,""+count));
				count++;
			}		
		}	

		

		l += 30;

		jlb = new JLabel("X:");
		jlb.setBounds(10, l, 30, 20);
		autocarPanel.add(jlb);

		centerX = new DoubleTextField();
		centerX.setBounds(50, l, 100, 20);
		centerX.setToolTipText("Center X");
		centerX.setText(x);
		autocarPanel.add(centerX);

		l += 30;

		jlb = new JLabel("Y:");
		jlb.setBounds(10, l, 30, 20);
		autocarPanel.add(jlb);

		centerY = new DoubleTextField();
		centerY.setBounds(50, l, 100, 20);
		centerY.setToolTipText("Center Y");
		centerY.setText(y);
		autocarPanel.add(centerY);

		l += 30;

		
		jlb = new JLabel("Fi:");
		jlb.setBounds(10, l, 30, 20);
		autocarPanel.add(jlb);

		fi_angle = new DoubleTextField();
		fi_angle.setBounds(50, l, 100, 20);
		fi_angle.setToolTipText("Orientation");
		autocarPanel.add(fi_angle);

		l += 30;

		jlb = new JLabel("St:");
		jlb.setBounds(10, l, 30, 20);
		autocarPanel.add(jlb);

		steering_angle = new DoubleTextField();
		steering_angle.setBounds(50, l, 100, 20);
		steering_angle.setToolTipText("Steering angle");
		autocarPanel.add(steering_angle);

		l += 30;

		jlb = new JLabel("U:");
		jlb.setBounds(10, l, 30, 20);
		autocarPanel.add(jlb);

		longitudinal_velocity = new DoubleTextField();
		longitudinal_velocity.setBounds(50, l, 100, 20);
		longitudinal_velocity.setToolTipText("Longitudinal vel");
		longitudinal_velocity.setText(u);
		autocarPanel.add(longitudinal_velocity);

		l += 30;

		jlb = new JLabel("Nu:");
		jlb.setBounds(10, l, 30, 20);
		autocarPanel.add(jlb);

		lateral_velocity = new DoubleTextField();
		lateral_velocity.setBounds(50, l, 100, 20);
		lateral_velocity.setToolTipText("Lateral vel");
		autocarPanel.add(lateral_velocity);
		
		l += 30;
		
		addButton = new JButton("Add");
		addButton.setBounds(10, l, 60, 20);
		addButton.addActionListener(this);
		autocarPanel.add(addButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(75, l, 80, 20);
		cancelButton.addActionListener(this);
		autocarPanel.add(cancelButton);
		
		add(autocarPanel);

		setVisible(true);
	}

	public AutocarData getAutocar() {
		return autocarData;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = e.getSource();
		
		if(obj==addButton){
			
			generateAutocar();
			dispose();
			
		}else if(obj==cancelButton){
			
			
			autocarData=null;
			dispose();
		}
		
	}

	private void generateAutocar() {
		
		autocarData=new AutocarData();
		
		autocarData.x = centerX.getvalue();
		autocarData.y = centerY.getvalue();

		autocarData.fi = fi_angle.getvalue();
		autocarData.steering = steering_angle.getvalue();

		// longitudinal velocity
		autocarData.u = longitudinal_velocity.getvalue();
		// lateral velocity
		autocarData.nu = lateral_velocity.getvalue();
		
		if(car_type_index.getSelectedIndex()>=0)
			autocarData.car_type= car_type_index.getSelectedIndex();
		
	}

}
