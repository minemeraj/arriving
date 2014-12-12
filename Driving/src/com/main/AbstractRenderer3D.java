package com.main;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.BarycentricCoordinates;
import com.Point3D;
import com.Polygon3D;
import com.Texture;
import com.ZBuffer;

public interface AbstractRenderer3D {

	 public void decomposeClippedPolygonIntoZBuffer(Polygon3D p3d,Color color,Texture texture,ZBuffer zbuffer);
	 
	    public void decomposeClippedPolygonIntoZBuffer(Polygon3D p3d,Color color,Texture texture,ZBuffer zbuffer,
	    		Point3D xDirection,Point3D yDirection,Point3D origin,int deltaX,int deltaY);
	    
	    public void decomposeTriangleIntoZBufferEdgeWalking(Polygon3D p3d,int rgbColor,Texture texture,ZBuffer zbuffer,
	    		Point3D xDirection, Point3D yDirection, Point3D origin,int deltaX,int deltaY,BarycentricCoordinates bc);
	    
	    
	    public void buildNewZBuffers();
	    
	    public void buildScreen(BufferedImage buf);
	
}
