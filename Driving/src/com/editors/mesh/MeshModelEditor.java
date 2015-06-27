package com.editors.mesh;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.Point3D;
import com.editors.models.MeshModel;

public class MeshModelEditor extends JFrame implements ActionListener{
	

	Vector texturePoints=null;
	Vector points=null;
	
	Color backgroundColor=Color.green;
	
	int IMG_WIDTH=100;
	int IMG_HEIGHT=100;

	public JPanel center;

	public JButton meshButton;

	public JButton textureButton;
	
	public File currentDirectory=new File("lib");

	private PrintWriter pw;

	
	String title="Mesh model";
	
	public MeshModel meshModel=null;

	
	public MeshModelEditor(int W,int H){		
		
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(W,H);
		setLocation(100,100);
		
		center=new JPanel(null);
		center.setBounds(0,0,W,H);
		add(center);
		
		buildCenter();
		
		setVisible(true);
		
	}
	
	public void buildCenter() {
		
		int r=10;
		
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
	public void actionPerformed(ActionEvent e) {
		
		Object obj = e.getSource();
		
		if(obj==meshButton){
			
			printMesh();
			
		}else if(obj==textureButton){
			
			prinTexture();
		}
		
	}

	private void prinTexture() {
		
		initMesh();
		
		JFileChooser fc = new JFileChooser();
		
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Save texture");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		int returnVal = fc.showOpenDialog(this);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			printTexture(file);
			currentDirectory=new File(file.getParent());
		} 
		
	}

	public void initMesh() {
		
		
	}

	public void printTexture(File file){



	}
	
	
	public void printMesh(){
		
		JFileChooser fc = new JFileChooser();
		
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Save texture");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		int returnVal = fc.showOpenDialog(this);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			
	
			try {
				
				pw = new PrintWriter(file);
				printMeshData(pw);
				pw.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			currentDirectory=new File(file.getParent());
		} 
		
	}

	
	public void printMeshData(PrintWriter pw) { 

		
	}
	

}
