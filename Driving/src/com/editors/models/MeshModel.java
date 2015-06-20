package com.editors.models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.Point3D;

public class MeshModel extends JFrame implements ActionListener{
	

	Vector texturePoints=null;
	
	Color backgroundColor=Color.green;
	
	int IMG_WIDTH=100;
	int IMG_HEIGHT=100;

	private JPanel center;

	private JButton meshButton;

	private JButton textureButton;
	
	public File currentDirectory=new File("lib");

	
	public MeshModel(){		
		
		setTitle("MeshModel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(300,300);
		
		center=new JPanel(null);
		center.setBounds(0,0,300,300);
		add(center);
		
		buildCenter();
		
		setVisible(true);
		
	}
	
	private void buildCenter() {
		
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
		
		initTexturePoints();
		printMesh();
		
	}
	
	public void printMesh(){}
	
	
	
	public void initTexturePoints(){
		
		texturePoints=new Vector();
	}
	
	
	public void print(String string) {
		
		System.out.println(string);
		
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
		
		return (int) (HEIGHT-y);
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
		
		initTexturePoints();
		
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
