package com.editors.buildings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.editors.DoubleTextField;
import com.editors.IntegerTextField;
import com.editors.buildings.data.BuildingPlan;

public class PlanPanel extends JDialog implements ActionListener {
	
	
	int WIDTH=250;
	int HEIGHT=310;
	private JPanel center;
	private DoubleTextField x_side;
	private DoubleTextField y_side;
	private DoubleTextField z_side;
	
	private DoubleTextField nw_x;
	private DoubleTextField nw_y;
	
	JButton generate=null;
	JButton delete=null;
	
	BuildingPlan newPlan=null;
	
	boolean isExpand=false;
	
	
	public PlanPanel(BuildingPlan plan){
		
		if(plan!=null)
			isExpand=true;
		
		newPlan=plan;
		
		setTitle("Cell");
		setLocation(50,50);
		setSize(WIDTH,HEIGHT);
		setLayout(null);
		
		center=new JPanel(null);
		center.setBounds(0,0,WIDTH,HEIGHT);
		add(center);
		
		setModal(true);
		
		
		int r=10;
		
		int column=100;
		

		JLabel jlb=new JLabel("X side");
		jlb.setBounds(5, r, 100, 20);
		center.add(jlb);
		x_side=new DoubleTextField();
		x_side.setBounds(column, r, 100, 20);
		center.add(x_side);

		r+=30;
		
		jlb=new JLabel("Y side");
		jlb.setBounds(5, r, 100, 20);
		center.add(jlb);
		y_side=new DoubleTextField();
		y_side.setBounds(column, r, 100, 20);
		center.add(y_side);
		
		r+=30;
		
		jlb=new JLabel("Z side");
		jlb.setBounds(5, r, 100, 20);
		center.add(jlb);
		z_side=new DoubleTextField();
		z_side.setBounds(column, r, 100, 20);
		center.add(z_side);
		
		r+=30;
		
		jlb=new JLabel("Num X");
		jlb.setBounds(5, r, 100, 20);
		center.add(jlb);
		nw_x=new DoubleTextField();
		nw_x.setBounds(column, r, 100, 20);
		center.add(nw_x);

		r+=30;
		
		jlb=new JLabel("Num Y ");
		jlb.setBounds(5, r, 100, 20);
		center.add(jlb);
		nw_y=new DoubleTextField();
		nw_y.setBounds(column, r, 100, 20);
		center.add(nw_y);
		
		r+=30;
		
        generate=new JButton(isExpand?"expand":"add plan");
        generate.setBounds(10,r,100,20);
        generate.addActionListener(this);
        center.add(generate);
        delete=new JButton("Cancel");
        delete.setBounds(120,r,100,20);
        delete.addActionListener(this);
        center.add(delete);
				
		initData(newPlan);
        
		setVisible(true);
	}

	private void initData(BuildingPlan bp) {
		
		if(bp!=null){
			
			nw_x.setText(bp.getNw_x());
			nw_y.setText(bp.getNw_y());
			x_side.setText(bp.getX_side());
			y_side.setText(bp.getY_side());
			z_side.setText(bp.getZ_side());
			
		}else{
		
			nw_x.setText(100);
			nw_y.setText(100);
			x_side.setText(100);
			y_side.setText(200);
			z_side.setText(100);

		
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object o=arg0.getSource();

		if(o==generate){
			
			if(isExpand){
				
					
				double xside=x_side.getvalue();
				double yside=y_side.getvalue();
				double zside=z_side.getvalue();
				double nwx=nw_x.getvalue();
				double nwy=nw_y.getvalue();
				
			    BuildingPlan expPlan = new BuildingPlan(nwx,nwy,xside,yside,zside);
							
				newPlan=expPlan;
				
				dispose();	
				
				
			}else{
				
					
				double xside=x_side.getvalue();
				double yside=y_side.getvalue();
				double zside=z_side.getvalue();
				double nwx=nw_x.getvalue();
				double nwy=nw_y.getvalue();
				
			    newPlan = new BuildingPlan(xside,yside,zside,nwx,nwy);
				
				
				dispose();	
				
			}
			
		
		}
		else if(o==delete){

			newPlan=null;
			dispose();	
		}
		
	}

	public BuildingPlan getNewPlan() {
		return newPlan;
	}

}
