package com.editors.buildings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.CustomData;
import com.Polygon3D;
import com.PolygonMesh;
import com.editors.CustomJPanel;
import com.editors.buildings.data.BuildingPlan;

public class BuildingJPanel extends CustomJPanel{



	public void draw(CustomData data ) {
		
		BuildingPlan plan=(BuildingPlan) data;
		
		if(buffGraph==null)
			return;

		drawBasic();

		if(plan!=null){
		
		
			buffGraph.setColor(Color.WHITE);
			drawPlanData(plan);
			
		}
		drawAxes();
		
		graph.drawImage(buf,0,0,null);
	}
	
	
	private void drawPlanData(BuildingPlan plan) { 
		
		
		PolygonMesh mesh = plan.buildMesh();
		draw(mesh);
	


		
	}






}
