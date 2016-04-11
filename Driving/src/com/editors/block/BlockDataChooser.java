package com.editors.block;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.editors.DoubleTextField;
import com.editors.block.BlockEditor.BlockData;


public class BlockDataChooser extends JDialog implements ActionListener{
	
	private BlockData cc=null;
	private JButton exit=null;
	private JButton ok=null;
	private DoubleTextField NX;
	private DoubleTextField NY;
	private DoubleTextField NZ;
	
	private DoubleTextField LX;
	private DoubleTextField LY;
	private DoubleTextField LZ;
	
	
	public BlockDataChooser(){
		
		setLayout(null);
		setSize(180,260);
		setTitle("Start block");
		setLocation(10,10);
		
		int r=10;
		int c=10;
		
		JLabel label=new JLabel("P X:");
		label.setBounds(c,r,30,20);
		add(label);
		NX = new DoubleTextField();
		NX.setBounds(c+30,r,100,20);
		add(NX);
		
		r+=20;
		
		label=new JLabel("P Y:");
		label.setBounds(c,r,30,20);
		add(label);
		NY = new DoubleTextField();
		NY.setBounds(c+30,r,100,20);
		add(NY);
		
		r+=20;
		
		label=new JLabel("P Z:");
		label.setBounds(c,r,30,20);
		add(label);
		NZ = new DoubleTextField();
		NZ.setBounds(c+30,r,100,20);
		add(NZ);
		
		r+=30;
		
		label=new JLabel("LX:");
		label.setBounds(c,r,20,20);
		add(label);
		LX = new DoubleTextField();
		LX.setBounds(c+30,r,100,20);
		add(LX);
		
		r+=20;
		
		label=new JLabel("LY:");
		label.setBounds(c,r,20,20);
		add(label);
		LY = new DoubleTextField();
		LY.setBounds(c+30,r,100,20);
		add(LY);
		
		r+=20;
		label=new JLabel("LZ:");
		label.setBounds(c,r,20,20);
		add(label);
		LZ = new DoubleTextField();
		LZ.setBounds(c+30,r,100,20);
		add(LZ);
		
		
		r+=30;
		ok=new JButton("OK");
		ok.setBounds(c+30,r,100,20);
		add(ok);
		ok.addActionListener(this);
		r+=20;
		exit=new JButton("Exit");
		exit.setBounds(c+30,r,100,20);
		exit.addActionListener(this);
		add(exit);
		
		setModal(true);
		
		//DEBUG
		setData(2,2,2,25,25,25);
		
		setVisible(true);
		
	}

	private void setData(int NX, int NY, int NZ, int LX, int LY, int LZ) {
		
		this.NX.setText(""+NX);
		this.NY.setText(""+NY);
		this.NZ.setText(""+NZ);
		
		this.LX.setText(""+LX);
		this.LY.setText(""+LY);
		this.LZ.setText(""+LZ);
		
	}

	public BlockData getBlockData() {
	
		return cc;
	}
	
	private void exit(){
		
		cc=null;
		dispose();
		
	}

	
	public void actionPerformed(ActionEvent e) {
		
		Object o = e.getSource();
		if(o==exit)
			exit();
		else if(o==ok){
			
			readBlockData();
		}
	}

	private void readBlockData() {
		
		try{
			
			cc=new BlockData();
			
			
			cc.setNX(Integer.parseInt(NX.getText()));
			cc.setNY(Integer.parseInt(NY.getText()));
			cc.setNZ(Integer.parseInt(NZ.getText()));
			
			cc.setLX(Double.parseDouble(LX.getText())*cc.getNX());
			cc.setLY(Double.parseDouble(LY.getText())*cc.getNY());
			cc.setLZ(Double.parseDouble(LZ.getText())*cc.getNZ());
			
			cc.initSelectionMask();
			
		}
		catch (Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,"Some data are incorrect","Error",JOptionPane.ERROR_MESSAGE);
		    return;
		}
		
		dispose();
		
	}

}
