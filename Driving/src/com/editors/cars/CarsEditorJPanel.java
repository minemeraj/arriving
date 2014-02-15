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
		
		PolygonMesh mesh = car.buildMesh();		
		draw(mesh);
	
		
	}


}
