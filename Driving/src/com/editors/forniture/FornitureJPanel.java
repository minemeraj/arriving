package com.editors.forniture;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.Polygon3D;
import com.editors.CustomJPanel;
import com.editors.forniture.data.Forniture;

public class FornitureJPanel extends CustomJPanel{


	

	public void draw(Forniture forniture) {
		
		if(graph==null)
			return;
		
		graph.setColor(BACKGROUND);
		graph.fillRect(0,0,getWidth(),getHeight());
		
		if(forniture!=null){
			
			
			graph.setColor(Color.WHITE);
			drawFornitureData(forniture);
			
		}
		graph.setColor(Color.GREEN);
		graph.drawLine((int)calcX(0,0,0),(int)calcY(0,0,0),(int)calcX(100,0,0),(int)calcY(100,0,0));
		graph.setColor(Color.YELLOW);
		graph.drawLine((int)calcX(0,0,0),(int)calcY(0,0,0),(int)calcX(0,100,0),(int)calcY(0,100,0));
		
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
	
			
		graph.draw(pol);
	
		
	}
	


}
