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
	
			
		PolygonMesh mesh = plant.buildMesh();
				
		draw(mesh);
	
		
	}


}
