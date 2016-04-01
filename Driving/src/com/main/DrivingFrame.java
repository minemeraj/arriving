package com.main;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.PolygonMesh;

public abstract class DrivingFrame extends JFrame{
	
	protected PolygonMesh[] meshes=new PolygonMesh[2];	
	protected JFileChooser fc= new JFileChooser();

}
