package com.editors.plants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.Polygon3D;
import com.PolygonMesh;
import com.editors.CustomJPanel;
import com.editors.plants.data.Plant;

public class PlantsJPanel extends CustomJPanel{


	

	public void draw(Plant plant) {
		
		if(buffGraph==null)
			return;
		
		drawBasic();
		
		if(plant!=null){
			
			
			buffGraph.setColor(Color.WHITE);
			drawPlantData(plant);
			
		}
		drawAxes();
		
		graph.drawImage(buf,0,0,null);
	}


	private void drawPlantData(Plant plant) {


		double x0=plant.getNw_x();
		double y0=plant.getNw_y();		
		double xside=plant.getX_side();
		double yside=plant.getY_side();
		
		Polygon3D pol=new Polygon3D();
		pol.addPoint((int)calcX(x0,y0,0),(int)calcY(x0,y0,0),0);
		pol.addPoint((int)calcX(x0+xside,y0,0),(int)calcY(x0+xside,y0,0),0);
		pol.addPoint((int)calcX(x0+xside,y0+yside,0),(int)calcY(x0+xside,y0+yside,0),0);
		pol.addPoint((int)calcX(x0,y0+yside,0),(int)calcY(x0,y0+yside,0),0);
	
			
		PolygonMesh mesh = plant.buildMesh();
				
		draw(mesh);
	
		
	}


}
