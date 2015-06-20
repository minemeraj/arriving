package com.editors.models;

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

public class MeshModel extends JFrame implements ActionListener{
	

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

	
	public MeshModel(int W,int H){		
		
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

	public void generateMesh(){
		
		initMesh();
		printMesh();
		
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
				printMeshData();
				pw.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			currentDirectory=new File(file.getParent());
		} 
		
	}


	public void printMeshData() {
		
		for(int i=0;i<points.size();i++){
			
			
			Point3D p=(Point3D) points.elementAt(i);
			print("v="+p.x+" "+p.y+" "+p.z);
			
		}
	
		
		for (int i = 0; i < texturePoints.size(); i++) {
			Point3D p = (Point3D) texturePoints.elementAt(i);
			print("vt="+p.x+" "+p.y);
		}
		
	}

	public void initMesh(){
		
		texturePoints=new Vector();
	}
	
	
	public void print(String string) {
		
		pw.println(string);
		
	}
	
	
	
	
	public void printTexture(Graphics2D bufGraphics) {
		// TODO Auto-generated method stub
		
	}

	public void printTextureLine(Graphics2D graphics, int indx0,int indx1){
		
		
		Point3D p0=(Point3D) texturePoints.elementAt(indx0);
		Point3D p1=(Point3D) texturePoints.elementAt(indx1);
		

		graphics.drawLine(cX(p0.x),cY(p0.y),cX(p1.x),cY(p1.y));	
		
	}

	private int cX(double x) {
		return (int) x;
	}

	private int cY(double y) {
		
		return (int) (IMG_HEIGHT-y);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = e.getSource();
		
		if(obj==meshButton){
			
			generateMesh();
			
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
	
	public void printTexture(File file){


		BufferedImage buf=new BufferedImage(IMG_WIDTH,IMG_HEIGHT,BufferedImage.TYPE_BYTE_INDEXED);

		try {


			Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();

			bufGraphics.setColor(backgroundColor);
			bufGraphics.fillRect(0,0,IMG_WIDTH,IMG_HEIGHT);

			printTexture(bufGraphics);   


			ImageIO.write(buf,"gif",file);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
