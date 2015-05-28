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
		int n=4;
		
		double dteta=2*Math.PI/n;
		
		Point4D[] points=new Point4D[n];
		
		for (int i = 0; i < n; i++) {
			
			double teta=i*dteta;
			double x=r*Math.cos(teta);
			double y=r*Math.sin(teta);
			
			points[i]=new Point4D(x,y,0);
		}
		
		Vector polygonData=new Vector();
		
		
		
		Vector vTexturePoints=RoadEditor.buildTemplateTexturePoints(100);
		Point3D pt0=(Point3D) vTexturePoints.elementAt(0);
		Point3D pt1=(Point3D) vTexturePoints.elementAt(1);
		Point3D pt2=(Point3D) vTexturePoints.elementAt(2);
		Point3D pt3=(Point3D) vTexturePoints.elementAt(3);
		
		LineData ld=new LineData();
		
		ld.addIndex(0,0,pt0.x,pt0.y);
		ld.addIndex(1,1,pt1.x,pt1.y);
		ld.addIndex(2,2,pt2.x,pt2.y);
		ld.addIndex(3,3,pt3.x,pt3.y);
		
		polygonData.add(ld);
		
		circleShape=new PolygonMesh(points,polygonData);
		
		
		circleShape.setTexturePoints(vTexturePoints);
			
	}
	
	public static PolygonMesh getCircle(double x,double y,double z){
		
		PolygonMesh circle=circleShape.clone();
		
		circle.translate(x,y,z);
		
		return circle;
	}

}
