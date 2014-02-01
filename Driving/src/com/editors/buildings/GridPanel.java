package com.editors.buildings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.peer.PanelPeer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.editors.DoubleTextField;
import com.editors.IntegerTextField;
import com.editors.buildings.data.BuildingCell;
import com.editors.buildings.data.BuildingGrid;

public class GridPanel extends JDialog implements ActionListener {
	
	
	int WIDTH=250;
	int HEIGHT=310;
	private JPanel center;
	private DoubleTextField nw_x;
	private DoubleTextField nw_y;
	private DoubleTextField x_side;
	private DoubleTextField y_side;
	
	private IntegerTextField xnumber;
	private IntegerTextField ynumber;
	
	JButton generate=null;
	JButton delete=null;
	
	BuildingGrid newGrid=null;
	
	public GridPanel(){
		
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
		
		JLabel jlb=new JLabel("NW X");
		jlb.setBounds(5, r, 100, 20);
		center.add(jlb);

		nw_x=new DoubleTextField();
		nw_x.setBounds(column, r, 100, 20);
		center.add(nw_x);

		r+=30;
		
		jlb=new JLabel("NW Y");
		jlb.setBounds(5, r, 100, 20);
		center.add(jlb);

		nw_y=new DoubleTextField();
		nw_y.setBounds(column, r, 100, 20);
		center.add(nw_y);
		
		r+=30;

		jlb=new JLabel("X side");
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
		
		jlb=new JLabel("Num X");
		jlb.setBounds(5, r, 100, 20);
		center.add(jlb);
		xnumber=new IntegerTextField();
		xnumber.setBounds(column, r, 100, 20);
		center.add(xnumber);

		r+=30;
		
		jlb=new JLabel("Num Y ");
		jlb.setBounds(5, r, 100, 20);
		center.add(jlb);
		ynumber=new IntegerTextField();
		ynumber.setBounds(column, r, 100, 20);
		center.add(ynumber);
		
		r+=30;
		
        generate=new JButton("add grid");
        generate.setBounds(10,r,100,20);
        generate.addActionListener(this);
        center.add(generate);
        delete=new JButton("Cancel");
        delete.setBounds(120,r,100,20);
        delete.addActionListener(this);
        center.add(delete);
				
		initData();
        
		setVisible(true);
	}

	private void initData() {
		nw_x.setText(100);
		nw_y.setText(100);
		x_side.setText(100);
		y_side.setText(200);
		xnumber.setText(5);
		ynumber.setText(4);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object o=arg0.getSource();

		if(o==generate){
			
			double nwx=nw_x.getvalue();
			double nwy=nw_y.getvalue();			
			double xside=x_side.getvalue();
			double yside=y_side.getvalue();
		    int xnum=xnumber.getvalue();
		    int ynum=ynumber.getvalue();
			
			newGrid=new BuildingGrid(nwx,nwy,xside,yside,xnum,ynum);
			
			newGrid.cells=new BuildingCell[xnum][ynum];
			
			for (int i = 0; i < newGrid.getXnum(); i++) {
				
				for (int j = 0; j <newGrid.getYnum(); j++) {
					newGrid.cells[i][j]=new BuildingCell(nwx+(i-1)*xside,nwy+(j-1)*yside,xside,yside,i,j);
				}
				
			}
			
			dispose();	
		}
		else if(o==delete){

			newGrid=null;
			dispose();	
		}
		
	}

	public BuildingGrid getNewGrid() {
		return newGrid;
	}

}
