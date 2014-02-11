package com.editors.animals;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.Polygon3D;
import com.PolygonMesh;
import com.editors.CustomJPanel;
import com.editors.animals.data.Animal;

public class AnimalsJPanel extends CustomJPanel{


	

	public void draw(Animal animal) {
		
		if(buffGraph==null)
			return;
		
		drawBasic();
		
		if(animal!=null){
			
			
			buffGraph.setColor(Color.WHITE);
			drawAnimalData(animal);
			
		}
		drawAxes();
		
		graph.drawImage(buf,0,0,null);
	}


	private void drawAnimalData(Animal animal) {


		double x0=animal.getNw_x();
		double y0=animal.getNw_y();		
		double xside=animal.getX_side();
		double yside=animal.getY_side();
		
		Polygon3D pol=new Polygon3D();
		pol.addPoint((int)calcX(x0,y0,0),(int)calcY(x0,y0,0),0);
		pol.addPoint((int)calcX(x0+xside,y0,0),(int)calcY(x0+xside,y0,0),0);
		pol.addPoint((int)calcX(x0+xside,y0+yside,0),(int)calcY(x0+xside,y0+yside,0),0);
		pol.addPoint((int)calcX(x0,y0+yside,0),(int)calcY(x0,y0+yside,0),0);
	
			
		PolygonMesh mesh = animal.buildMesh();
				
		draw(mesh);
	
		
	}


}
