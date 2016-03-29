package com.editors.cars;

import java.awt.Color;

import com.CustomData;
import com.PolygonMesh;
import com.editors.CustomJPanel;
import com.editors.cars.data.Car;

public class CarsEditorJPanel extends CustomJPanel{


	public CarsEditorJPanel(){
		
		super();
		x0=500;
	}

	public void draw(CustomData data ) { 
		
		Car car=(Car) data;
		
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
