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
		
		
		double maxR=100;
		double minR=80;
		int n=10;
		
		double dteta=2*Math.PI/n;
		
		//Vector vTexturePoints=RoadEditor.buildTemplateTexturePoints(100);
		//circleShape.setTexturePoints(vTexturePoints);
		
		Point4D[] points=new Point4D[2*n];
		
		
		for (int i = 0; i < n; i++) {
			
			double teta=i*dteta;
			
			double x=maxR*Math.cos(teta);
			double y=maxR*Math.sin(teta);
			
			double xm=minR*Math.cos(teta);
			double ym=minR*Math.sin(teta);
			
			points[i]=new Point4D(x,y,0);
			points[i+n]=new Point4D(xm,ym,0);
			
			
		}
		
		Vector polygonData=new Vector();
		for (int i = 0; i < n; i++) {
			
			int indx0=i;
			int indx1=(i+1)%n;
			int indx2=(i+1)%n+n;
			int indx3=i+n;
			
			Point4D p0= points[indx0];
			Point4D p1= points[indx1];
			Point4D p2= points[indx2];
			Point4D p3= points[indx3];
			
			LineData ld=new LineData();
			ld.addIndex(indx0,indx0,p0.x,p0.y);
			ld.addIndex(indx1,indx1,p1.x,p1.y);
			ld.addIndex(indx2,indx2,p2.x,p2.y);
			ld.addIndex(indx3,indx3,p3.x,p3.y);
			
			polygonData.add(ld);
		}
		
		
		
		
		
		circleShape=new PolygonMesh(points,polygonData);
		
		
		
			
	}
	
	public static PolygonMesh getCircle(double x,double y,double z){
		
		PolygonMesh circle=circleShape.clone();
		
		circle.translate(x,y,z);
		
		return circle;
	}

}
