package com.editors.cars;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.Polygon3D;
import com.PolygonMesh;
import com.editors.CustomJPanel;
import com.editors.cars.data.Car;

public class CarsEditorJPanel extends CustomJPanel{


	public void initialize() {
		
		graph=(Graphics2D)getGraphics();
		WIDTH=getWidth();
		HEIGHT=getHeight();
	}
	

	public void draw(Car car) { 
		
		if(graph==null)
			return;
		
		graph.setColor(BACKGROUND);
		graph.fillRect(0,0,getWidth(),getHeight());
		
		if(car!=null){
			
			
			graph.setColor(Color.WHITE);
			drawCarData(car);
			
		}
		graph.setColor(Color.GREEN);
		graph.drawLine((int)calcX(0,0,0),(int)calcY(0,0,0),(int)calcX(100,0,0),(int)calcY(100,0,0));
		graph.setColor(Color.YELLOW);
		graph.drawLine((int)calcX(0,0,0),(int)calcY(0,0,0),(int)calcX(0,100,0),(int)calcY(0,100,0));
		
	}


	private void drawCarData(Car car) {


		double x0=car.getNw_x();
		double y0=car.getNw_y();		
		double xside=car.getX_side();
		double yside=car.getY_side();
		
		Polygon3D pol=new Polygon3D();
		pol.addPoint((int)calcX(x0,y0,0),(int)calcY(x0,y0,0),0);
		pol.addPoint((int)calcX(x0+xside,y0,0),(int)calcY(x0+xside,y0,0),0);
		pol.addPoint((int)calcX(x0+xside,y0+yside,0),(int)calcY(x0+xside,y0+yside,0),0);
		pol.addPoint((int)calcX(x0,y0+yside,0),(int)calcY(x0,y0+yside,0),0);
	
			
		PolygonMesh mesh = car.buildMesh();		
		draw(mesh);
	
		
	}


}
