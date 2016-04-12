package com.editors.forniture;

import java.awt.Color;

import com.CustomData;
import com.PolygonMesh;
import com.editors.CustomJPanel;
import com.editors.forniture.data.Forniture;

class FornitureJPanel extends CustomJPanel{


	
	@Override
	public void draw(CustomData data ) {
		 
		Forniture forniture=(Forniture) data;
		
		if(buffGraph==null)
			return;
		
		drawBasic();
		
		if(forniture!=null){
			
			
			buffGraph.setColor(Color.WHITE);
			drawFornitureData(forniture);
			
		}
		
		drawAxes();
		
		graph.drawImage(buf,0,0,null);
	}


	private void drawFornitureData(Forniture forniture) {


		
		PolygonMesh mesh = forniture.buildMesh();	
		draw(mesh);
	
		
	}
	


}
