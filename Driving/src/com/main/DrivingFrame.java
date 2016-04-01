package com.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.CubicMesh;
import com.DrawObject;
import com.PolygonMesh;
import com.SPLine;
import com.SPNode;

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
			int rows=0;
			
			boolean read=forceReading;

			double x0=0;
			double y0=0;
			
			
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

	protected void buildSPLine(SPLine sp,String str) {
		
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


}
