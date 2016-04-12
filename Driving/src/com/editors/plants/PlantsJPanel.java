package com.editors.plants;

import java.awt.Color;

import com.CustomData;
import com.PolygonMesh;
import com.editors.CustomJPanel;
import com.editors.plants.data.Plant;

class PlantsJPanel extends CustomJPanel{


	

	public void draw(CustomData data ) {
		
		Plant plant=(Plant) data;
		
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
