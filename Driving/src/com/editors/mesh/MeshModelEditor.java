package com.editors.mesh;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.editors.models.MeshModel;

public class MeshModelEditor extends JFrame implements ActionListener, MenuListener{
	
	
	Color backgroundColor=Color.green;
	
	int IMG_WIDTH=100;
	int IMG_HEIGHT=100;

	public JPanel center;

	protected JButton meshButton;

	protected JButton textureButton;
	
	protected File currentDirectory=new File("lib");

	protected PrintWriter pw;

	
	String title="Mesh model";
	
	protected MeshModel meshModel=null;
	private JMenuBar jmb;
	private JMenu jm_file;
	private JMenuItem jm_file_load;
	private JMenuItem jm_file_save;
	private File currentFile;

	
	public MeshModelEditor(int W,int H){		
		
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(W,H);
		setLocation(100,100);
		
		center=new JPanel(null);
		center.setBounds(0,0,W,H);
		add(center);
		
		buildMenuBar();
		buildCenter();
		
		setVisible(true);
		
	}
	
	private void buildMenuBar() {
	
		jmb=new JMenuBar();
		jm_file=new JMenu("File");
		jm_file.addMenuListener(this);
		jmb.add(jm_file);

		/*jmt11 = new JMenuItem("Load road");
		jmt11.addActionListener(this);
		jm.add(jmt11);*/

		jm_file_load = new JMenuItem("Load data");
		jm_file_load.addActionListener(this);
		jm_file.add(jm_file_load);
		
		jm_file_save = new JMenuItem("Save data");
		jm_file_save.addActionListener(this);
		jm_file.add(jm_file_save);
		
		setJMenuBar(jmb);
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
		}else if(obj==jm_file_load){
			loadFile();
		}else if(obj==jm_file_save){
			saveFile();
		}
		
	}

	private void saveFile() {
		
		JFileChooser fc = new JFileChooser();
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setDialogTitle("Save file");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		if(currentFile!=null)
			fc.setSelectedFile(currentFile);

		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentDirectory=fc.getCurrentDirectory();
			currentFile=fc.getSelectedFile();
			File file = fc.getSelectedFile();
			saveFile(file);
            setTitle(file.getName());

		}
		
	}

	private void saveFile(File file) {
		
		try{
			PrintWriter pr = new PrintWriter(new FileOutputStream(file));
            saveData(pr);  
            pr.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

		
	}

	public void saveData(PrintWriter pr) { 
		// TODO Auto-generated method stub
		
	}

	private void loadFile() {
		
		JFileChooser fc = new JFileChooser();
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setDialogTitle("Load file");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		if(currentFile!=null)
			fc.setSelectedFile(currentFile);

		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentDirectory=fc.getCurrentDirectory();
			currentFile=fc.getSelectedFile();
			File file = fc.getSelectedFile();
			loadFile(file);
            setTitle(file.getName());

		}
		
	}

	private void loadFile(File file) {
		
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
			
			loadData(br); 
			
			br.close();
			
			//checkNormals();
		} catch (Exception e) {

			e.printStackTrace();
		}	
		
	}

	public void loadData(BufferedReader br) throws IOException { 
		// TODO Auto-generated method stub
		
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
		
		initMesh();
		
		JFileChooser fc = new JFileChooser();
		
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Save mesh");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		if(currentFile!=null)
			fc.setSelectedFile(currentFile);
		
		int returnVal = fc.showOpenDialog(this);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			currentFile=fc.getSelectedFile();
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

	@Override
	public void menuSelected(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuDeselected(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuCanceled(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
