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


		double x0=forniture.getNw_x();
		double y0=forniture.getNw_y();		
		double xside=forniture.getX_side();
		double yside=forniture.getY_side();
		
		Polygon3D pol=new Polygon3D();
		pol.addPoint((int)calcX(x0,y0,0),(int)calcY(x0,y0,0),0);
		pol.addPoint((int)calcX(x0+xside,y0,0),(int)calcY(x0+xside,y0,0),0);
		pol.addPoint((int)calcX(x0+xside,y0+yside,0),(int)calcY(x0+xside,y0+yside,0),0);
		pol.addPoint((int)calcX(x0,y0+yside,0),(int)calcY(x0,y0+yside,0),0);
	
			
		PolygonMesh mesh = forniture.buildMesh();		
		draw(mesh);
	
		
	}
	


}
