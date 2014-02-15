package com.editors.forniture;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.Polygon3D;
import com.PolygonMesh;
import com.editors.CustomJPanel;
import com.editors.forniture.data.Forniture;

public class FornitureJPanel extends CustomJPanel{


	

	public void draw(Forniture forniture) {
		
		if(buffGraph==null)
			return;
		
		drawBasic();
		
		if(forniture!=null){
			
			
			buffGraph.setColor(Color.WHITE);
			drawFornitureData(forniture);
			
		}
		
		drawAxes();
		
		graph.drawImage(buf,0,0,null);
	}


	private void drawFornitureData(Forniture forniture) {


		
		PolygonMesh mesh = forniture.buildMesh();		
		draw(mesh);
	
		
	}
	


}
