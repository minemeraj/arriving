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


	

	public void draw(Car car) { 
		
		if(buffGraph==null)
			return;
		
		drawBasic();
		
		if(car!=null){
			
			
			buffGraph.setColor(Color.WHITE);
			drawCarData(car);
			
		}
		drawAxes();
		
		graph.drawImage(buf,0,0,null);
	}


	private void drawCarData(Car car) {


		double x0=0;
		double y0=0;		
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
