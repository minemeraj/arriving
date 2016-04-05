package com.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.CubicMesh;
import com.DrawObject;
import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.SPLine;
import com.SPNode;
import com.SquareMesh;
import com.editors.EditorData;

public abstract class DrivingFrame extends JFrame{
	
	protected PolygonMesh[] meshes=new PolygonMesh[2];	
	protected JFileChooser fc= new JFileChooser();
	
	protected static final String TAG[]={"terrain","road"};
	
	public static final int TERRAIN_INDEX=0;
	public static final int ROAD_INDEX=1;
	
	protected ArrayList loadSPLinesFromFile(File file,boolean forceReading){
		
		ArrayList splines=new ArrayList();
		
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));


			String str=null;
			
			boolean read=forceReading;
		
			ArrayList aTexturePoints=new ArrayList();
			
			while((str=br.readLine())!=null){
				
				
				
				if(str.indexOf("#")>=0 || str.length()==0)
					continue;
				
				if(str.indexOf(TAG[ROAD_INDEX])>=0){
					read=!read;
				    continue;
				}	
				
				if(!read)
					continue;
				
				if(str.startsWith("vt=")){
					PolygonMesh.buildTexturePoint(aTexturePoints,str.substring(3));
					continue;
				}else if(str.equals("<spline>")){			
				
					
					
					SPLine sp=new SPLine(aTexturePoints);
					splines.add(sp);
					continue;
				}else if(str.equals("</spline>")){
					continue;
				}
				
				
				SPLine sp=(SPLine) splines.get(splines.size()-1);
				
			
								
				buildSPLine(sp,str);
				

			}
			
			br.close();

		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return splines;
		
	}

	private void buildSPLine(SPLine sp,String str) {
		
		if(str.startsWith("v=")){
			
			str=str.substring(2);
		
			String[] vals =str.split(" ");
	
			SPNode p=new SPNode();
			
			String texture_index=vals[0];
			texture_index=texture_index.substring(1);
			p.setIndex(Integer.parseInt(texture_index));
			
			p.x=Double.parseDouble(vals[1]);
			p.y=Double.parseDouble(vals[2]);
			p.z=Double.parseDouble(vals[3]);
			p.update();
			
	        sp.addSPNode(p);
        
		}
	}
	
	
	protected ArrayList loadObjectsFromFile(File file,CubicMesh[] objectMeshes ){

		
		ArrayList drawObjects=new ArrayList();

		try {
			BufferedReader br=new BufferedReader(new FileReader(file));

			boolean read=false;

			String str=null;

			while((str=br.readLine())!=null){
				if(str.indexOf("#")>=0 || str.length()==0)
					continue;
				
				if(str.indexOf("objects")>=0){
					read=!read;
				    continue;
				}	
				
				if(!read)
					continue;
				
				DrawObject dro=buildDrawObject(str,objectMeshes);
				drawObjects.add(dro);
				
				buildRectanglePolygons(dro.getPolygons(),dro.getX(),dro.getY(),dro.getZ(),dro.getDx(),dro.getDy(),dro.getDz());

				
				CubicMesh cm=EditorData.objectMeshes[dro.getIndex()].clone();
				Point3D point = cm.point000;
				
				
				double dx=-point.x+dro.getX();
				double dy=-point.y+dro.getY();
				double dz=-point.z+dro.getZ();
				
				cm.translate(dx,dy,dz);
				
				Point3D center=cm.findCentroid();
				
				if(dro.getRotation_angle()!=0)
					cm.rotate(center.x,center.y,Math.cos(dro.getRotation_angle()),Math.sin(dro.getRotation_angle()));
			
				
				dro.setMesh(cm);
				
			}

			br.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return drawObjects;
	}

	protected void buildRectanglePolygons(ArrayList polygons, double x, double y, double z, double dx, double dy,
			double dz) {
		//TO IMPLEMENT, WHEN NECESSARY
		
	}

	protected DrawObject buildDrawObject(String str, CubicMesh[] objectMeshes) {
		DrawObject dro=new DrawObject();
		
		String properties0=str.substring(0,str.indexOf("["));
		String properties1=str.substring(str.indexOf("[")+1,str.indexOf("]"));

		StringTokenizer tok0=new StringTokenizer(properties0," "); 
		
		dro.setX(Double.parseDouble(tok0.nextToken()));
		dro.setY(Double.parseDouble(tok0.nextToken()));
		dro.setZ(Double.parseDouble(tok0.nextToken()));
		dro.setIndex(Integer.parseInt(tok0.nextToken()));
		
		dro.setDx(objectMeshes[dro.getIndex()].getDeltaX2()-objectMeshes[dro.getIndex()].getDeltaX());
		dro.setDy(objectMeshes[dro.getIndex()].getDeltaY2()-objectMeshes[dro.getIndex()].getDeltaY());
		dro.setDz(objectMeshes[dro.getIndex()].getDeltaX());
		
	
		
		StringTokenizer tok1=new StringTokenizer(properties1," "); 
		dro.setRotation_angle(Double.parseDouble(tok1.nextToken()));
		dro.setHexColor(tok1.nextToken());
		return dro;
	}
	
	protected void loadPointsFromFile(File file,int ACTIVE_PANEL,boolean forceReading){

		if(ACTIVE_PANEL==0)
			meshes[ACTIVE_PANEL]=new  SquareMesh();
		else	
			meshes[ACTIVE_PANEL]=new  PolygonMesh();
		
		
	

		try {
			BufferedReader br=new BufferedReader(new FileReader(file));


			String str=null;

			boolean read=forceReading;
			
			int nx=0;
			int ny=0;
			int dx=0;
			int dy=0;
			double x0=0;
			double y0=0;
			
			ArrayList aPoints=new ArrayList();
			ArrayList vTexturePoints=new ArrayList();
			
			while((str=br.readLine())!=null){
				
				
				
				if(str.indexOf("#")>=0 || str.length()==0)
					continue;
				
				if(str.indexOf(TAG[ACTIVE_PANEL])>=0){
					read=!read;
				    continue;
				}	
				
				if(!read)
					continue;
				
				

				if(str.startsWith("v="))
					buildPoint(aPoints,str.substring(2));
				else if(str.startsWith("vt="))
					PolygonMesh.buildTexturePoint(vTexturePoints,str.substring(3));
				else if(str.startsWith("f="))
					buildLine(meshes[ACTIVE_PANEL].polygonData,str.substring(2),vTexturePoints);
				else if(str.startsWith("NX="))					
					nx=Integer.parseInt(str.substring(3)); 
				else if(str.startsWith("NY="))					
					ny=Integer.parseInt(str.substring(3)); 
				else if(str.startsWith("DX="))					
					dx=Integer.parseInt(str.substring(3)); 
				else if(str.startsWith("DY="))					
					dy=Integer.parseInt(str.substring(3)); 
				else if(str.startsWith("X0="))					
					x0=Double.parseDouble(str.substring(3)); 
				else if(str.startsWith("Y0="))					
					y0=Double.parseDouble(str.substring(3)); 


			}
			
			if(meshes[ACTIVE_PANEL] instanceof SquareMesh){
				
				((SquareMesh)meshes[ACTIVE_PANEL]).setNumx(nx); 
				((SquareMesh)meshes[ACTIVE_PANEL]).setNumy(ny); 
				((SquareMesh)meshes[ACTIVE_PANEL]).setDx(dx);
				((SquareMesh)meshes[ACTIVE_PANEL]).setDy(dy);
				((SquareMesh)meshes[ACTIVE_PANEL]).setX0(x0); 
				((SquareMesh)meshes[ACTIVE_PANEL]).setY0(y0); 
				
			}

			br.close();
			
			meshes[ACTIVE_PANEL].setPoints(aPoints);
			meshes[ACTIVE_PANEL].setTexturePoints(vTexturePoints);
			meshes[ACTIVE_PANEL].setLevel(Road.GROUND_LEVEL);
			
		
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		repaint();
	}

	protected void buildLine(ArrayList<LineData> polygonData, String substring, ArrayList vTexturePoints) {
			
	}

	protected void buildPoint(ArrayList aPoints, String substring) {
			
	}


}
