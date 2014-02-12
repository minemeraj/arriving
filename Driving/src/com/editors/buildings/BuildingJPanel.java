package com.editors.buildings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.Polygon3D;
import com.PolygonMesh;
import com.editors.CustomJPanel;
import com.editors.buildings.data.BuildingPlan;

public class BuildingJPanel extends CustomJPanel{



	public void draw(BuildingPlan plan) {
		
	
		
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
		
		double x0=0;
		double y0=0;		
		double xside=plan.getX_side();
		double yside=plan.getY_side();
		
		Polygon3D pol=new Polygon3D();
		pol.addPoint((int)calcX(x0,y0,0),(int)calcY(x0,y0,0),0);
		pol.addPoint((int)calcX(x0+xside,y0,0),(int)calcY(x0+xside,y0,0),0);
		pol.addPoint((int)calcX(x0+xside,y0+yside,0),(int)calcY(x0+xside,y0+yside,0),0);
		pol.addPoint((int)calcX(x0,y0+yside,0),(int)calcY(x0,y0+yside,0),0);
		
		PolygonMesh mesh = plan.buildMesh();
		draw(mesh);
	


		
	}






}
