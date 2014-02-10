package com.editors.buildings;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.Polygon3D;
import com.PolygonMesh;
import com.editors.CustomJPanel;
import com.editors.buildings.data.BuildingPlan;

public class BuildingJPanel extends CustomJPanel{

	

	public void draw(BuildingPlan plan) {
		
	
		
		if(graph==null)
			return;
		
		graph.setColor(BACKGROUND);
		graph.fillRect(0,0,WIDTH,HEIGHT);
		
		

		
		if(plan!=null){
		
		
			graph.setColor(Color.WHITE);
			drawPlanData(plan);
			
		}
		graph.setColor(Color.GREEN);
		graph.drawLine((int)calcX(0,0,0),(int)calcY(0,0,0),(int)calcX(100,0,0),(int)calcY(100,0,0));
		graph.setColor(Color.YELLOW);
		graph.drawLine((int)calcX(0,0,0),(int)calcY(0,0,0),(int)calcX(0,100,0),(int)calcY(0,100,0));
			
	}
	
	
	private void drawPlanData(BuildingPlan plan) { 
		
		double x0=plan.getNw_x();
		double y0=plan.getNw_y();		
		double xside=plan.getX_side();
		double yside=plan.getY_side();
		
		Polygon3D pol=new Polygon3D();
		pol.addPoint((int)calcX(x0,y0,0),(int)calcY(x0,y0,0),0);
		pol.addPoint((int)calcX(x0+xside,y0,0),(int)calcY(x0+xside,y0,0),0);
		pol.addPoint((int)calcX(x0+xside,y0+yside,0),(int)calcY(x0+xside,y0+yside,0),0);
		pol.addPoint((int)calcX(x0,y0+yside,0),(int)calcY(x0,y0+yside,0),0);
		
		PolygonMesh mesh = plan.buildMesh();
		plan.translatePoints(mesh.points,plan.getNw_x(),plan.getNw_y());		
		draw(mesh);
	


		
	}






}
