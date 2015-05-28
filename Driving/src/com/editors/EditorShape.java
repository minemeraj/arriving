package com.editors;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Vector;

import com.LineData;
import com.Point3D;
import com.Point4D;
import com.PolygonMesh;
import com.Texture;
import com.editors.road.RoadEditor;

public class EditorShape {
	

	
	public static PolygonMesh circleShape;
	public static Texture whiteTexture=null;

	public static void buildShape() {
		
		BufferedImage bf=new BufferedImage(100,100,BufferedImage.TYPE_BYTE_INDEXED);
		bf.getGraphics().setColor(Color.WHITE);
		bf.getGraphics().fillRect(0,0,100,100);
		whiteTexture=new Texture(bf);
		
		
		double r=100;
		int n=10;
		
		double dteta=2*Math.PI/n;
		
		//Vector vTexturePoints=RoadEditor.buildTemplateTexturePoints(100);
		//circleShape.setTexturePoints(vTexturePoints);
		
		Point4D[] points=new Point4D[n];
		LineData ld=new LineData();
		
		for (int i = 0; i < n; i++) {
			
			double teta=i*dteta;
			double x=r*Math.cos(teta);
			double y=r*Math.sin(teta);
			
			points[i]=new Point4D(x,y,0);
			
			ld.addIndex(i,i,x,y);
		}
		
		Vector polygonData=new Vector();
		
		polygonData.add(ld);
		
		circleShape=new PolygonMesh(points,polygonData);
		
		
		
			
	}
	
	public static PolygonMesh getCircle(double x,double y,double z){
		
		PolygonMesh circle=circleShape.clone();
		
		circle.translate(x,y,z);
		
		return circle;
	}

}
