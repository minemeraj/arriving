package com.editors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.LineData;
import com.Point3D;
import com.Point4D;
import com.Polygon3D;
import com.PolygonMesh;
import com.SPLine;
import com.SquareMesh;
import com.main.Renderer3D;

public class Editor extends JFrame implements MenuListener{
	
	public int ACTIVE_PANEL=0; 
	public int numPanels=2;
	
	public PolygonMesh[] meshes=null;	
	public Stack[] oldMeshes=null;

	
	public LineData polygon=new LineData();
	
	public int MAX_STACK_SIZE=10;
	
	public boolean redrawAfterMenu=false;
	
	public JFileChooser fc= new JFileChooser();
	public File currentDirectory=null;
	public File currentFile=null;
	
	public int TERRAIN_INDEX=0;
	public int ROAD_INDEX=1;
	
	public static String TAG[]={"terrain","road"};
	
	public Vector splines=null;
	
	public Editor(){
		
		
		meshes=new PolygonMesh[numPanels];
		
		
		oldMeshes=new Stack[numPanels];
			
		for (int i = 0; i < numPanels; i++) {
			
			if(i==TERRAIN_INDEX)
				meshes[i]=new SquareMesh();
			else
				meshes[i]=new PolygonMesh();

			oldMeshes[i]=new Stack();
		}
		
		splines=new Vector();
	
	}


	

	
	public void buildLines(Vector lines, String str) {

		StringTokenizer sttoken=new StringTokenizer(str," ");

		while(sttoken.hasMoreElements()){
			
			String token=sttoken.nextToken();
			
			LineData ld=new LineData();
			
			if(token.indexOf("]")>0){
				
				String extraData=token.substring(token.indexOf("[")+1,token.indexOf("]"));
				token=token.substring(token.indexOf("]")+1);
				ld.setData(extraData);
				
			}

			String[] vals = token.split(" ");

			for(int i=0;i<vals.length;i++)
				ld.addIndex(Integer.parseInt(vals[i]));


			lines.add(ld);
		}




	}
	
	public boolean forceReading=false;
	
	public void loadPointsFromFile(){	
		
		forceReading=true;

		fc=new JFileChooser();
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setDialogTitle("Load lines ");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		if(currentFile!=null)
			fc.setSelectedFile(currentFile);

		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentDirectory=fc.getCurrentDirectory();
			currentFile=fc.getSelectedFile();
			File file = fc.getSelectedFile();
			loadPointsFromFile(file);
            setTitle(file.getName());

		}
	}
	
	public void loadPointsFromFile(File file){

		if(ACTIVE_PANEL==0)
			meshes[ACTIVE_PANEL]=new  SquareMesh();
		else	
			meshes[ACTIVE_PANEL]=new  PolygonMesh();
		
		oldMeshes[ACTIVE_PANEL]=new Stack();
	

		try {
			BufferedReader br=new BufferedReader(new FileReader(file));


			String str=null;
			int rows=0;
			
			boolean read=forceReading;
			
			int nx=0;
			int ny=0;
			int dx=0;
			int dy=0;
			double x0=0;
			double y0=0;
			
			Vector vPoints=new Vector();
			Vector vTexturePoints=new Vector();
			
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
					buildPoint(vPoints,str.substring(2));
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
			
			meshes[ACTIVE_PANEL].setPoints(vPoints);
			meshes[ACTIVE_PANEL].setTexturePoints(vTexturePoints);
			
			//checkNormals();
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		repaint();
	}
	
	public void loadSPLinesFromFile(File file){
		
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));


			String str=null;
			int rows=0;
			
			boolean read=forceReading;

			double x0=0;
			double y0=0;
			
			splines=new Vector();
			
			while((str=br.readLine())!=null){
				
				
				
				if(str.indexOf("#")>=0 || str.length()==0)
					continue;
				
				if(str.indexOf(TAG[ACTIVE_PANEL])>=0){
					read=!read;
				    continue;
				}	
				
				if(!read)
					continue;
				
				

				
				splines.add(buildSPLine(str));
		


			}
			
			br.close();

			
			//checkNormals();
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		repaint();
		
	}


	public SPLine buildSPLine(String str) {
		
		String[] vals =str.split(" ");

		Point4D p=new Point4D();

		p.x=Double.parseDouble(vals[0]);
		p.y=Double.parseDouble(vals[1]);
		
		SPLine sp=new SPLine(p);

		
		
		return sp;
	}





	public void buildPoint(Vector vPoints, String str) {
		PolygonMesh.buildPoint(vPoints,str);
		
	}





	public void buildLine(Vector<LineData> polygonData, String str,
			Vector vTexturePoints) {
		PolygonMesh.buildLine(polygonData,str,vTexturePoints);
		
	}





	public String decomposePoint(Point3D p) {
		String str="";

		str=p.x+" "+p.y+" "+p.z;
		
		if(p.getData()!=null)
			str=str+" "+p.getData().toString();

		return str;
	}
	
	public String decomposeLineData(LineData ld) {
		
		return decomposeLineData(ld,false);
	}
	
	public String decomposeLineData(LineData ld,boolean isCustom) {

		String str="";
		
		if(ld.data!=null){
			
			str+="["+ld.data+"]";
		    
		}
		for(int j=0;j<ld.size();j++){

			if(j>0)
				str+=" ";
			str+=ld.getIndex(j);
			
			if(isCustom){
				
				int indx=ld.getVertex_texture_index(j);
				str+="/"+indx;
				
			}else{
				
				if(ld.data!=null){
					
					int face=Integer.parseInt(ld.data);
		
					str+="/"+(ld.getIndex(j)*6+face);
				}
				
			}
			
		

		}

		return str;
	}
	
	


	private String decomposeSPLine(SPLine sp) {
		
		String str="";
		Point4D p0=(Point4D) sp.getStarPoint();
		str=p0.getX()+" "+p0.getY();
		return str;
	}
	
	public void saveLines() {
		
		saveLines(false);
		
	}
	
	public void saveLines(boolean isCustom) {

		fc = new JFileChooser();
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Save lines");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		if(currentFile!=null)
			fc.setSelectedFile(currentFile);
		
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			
			
			currentDirectory=fc.getCurrentDirectory();
			currentFile=fc.getSelectedFile();
			try{
				PrintWriter pr = new PrintWriter(new FileOutputStream(file));
				saveLines(pr,isCustom);
	            pr.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		} 


	}
	
	public void saveLines(PrintWriter pr) {
		
		saveLines(pr,false);
	}

	public void saveLines(PrintWriter pr,boolean isCustom) {

       

		
		try {
			
			PolygonMesh mesh=meshes[ACTIVE_PANEL];
			
			if(mesh.points==null)
				return; 
			
			if(!forceReading)
				pr.println("<"+TAG[ACTIVE_PANEL]+">");
			
			if(mesh instanceof SquareMesh){
				
				SquareMesh sm = (SquareMesh)mesh;
				pr.println("NX="+sm.getNumx());
				pr.println("NY="+sm.getNumy());
				pr.println("DX="+sm.getDx());
				pr.println("DY="+sm.getDy());
				pr.println("X0="+sm.getX0());
				pr.println("Y0="+sm.getY0());
			}
			

			for(int i=0;i<mesh.points.length;i++){

				Point3D p=mesh.points[i];

				pr.print("v=");
				pr.print(decomposePoint(p));
				if(i<mesh.points.length-1)
					pr.println();
			}
			
			decomposeObjVertices(pr,mesh,isCustom);

	
			

			for(int i=0;i<mesh.polygonData.size();i++){

				LineData ld=(LineData) mesh.polygonData.elementAt(i);
				
				//for the transaction phase:
				
				/*Point3D normal = PolygonMesh.getNormal(0,ld,mesh.points);	
				
				int boxFace=Renderer3D.findBoxFace(normal);
				
				ld.setData(""+boxFace);*/
				
				////////////
				pr.print("\nf=");
				if(isCustom){
					
					pr.print(decomposeLineData(ld,true));
					
				}else
					pr.print(decomposeLineData(ld));
			
			}	
			if(!forceReading)
				pr.println("\n</"+TAG[ACTIVE_PANEL]+">");
				

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void saveSPLines(PrintWriter pr) {
		
		try {
			
			if(!forceReading)
				pr.println("<"+TAG[ACTIVE_PANEL]+">");
			
			for (int i = 0; i < splines.size(); i++) {
				
				SPLine sp = (SPLine) splines.elementAt(i);
				pr.print(decomposeSPLine(sp));
				
			}	
			
			if(!forceReading)
				pr.println("\n</"+TAG[ACTIVE_PANEL]+">");

		} catch (Exception e) {

			e.printStackTrace();
		}

		
	}








	public void decomposeObjVertices(PrintWriter pr,PolygonMesh mesh,boolean isCustom) {
		
		
		
	}


	public Vector clonePoints(Vector oldPoints) {
		
		Vector newPoints=new Vector();
		
		for(int i=0;i<oldPoints.size();i++){
			
			Point3D p=(Point3D) oldPoints.elementAt(i);
			newPoints.add(p.clone());
		}
		
		return newPoints;
	}
	
	public Vector cloneLineData(Vector oldLines) {
		
		Vector newLineData=new Vector();
		
		for(int i=0;i<oldLines.size();i++){

			LineData ld=(LineData) oldLines.elementAt(i);
		
			
			newLineData.add(ld.clone());
		
			
		}
		return newLineData;
	}
	
	public void prepareUndo() {
		
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		if(mesh.points==null)
			return; 
		
		if(oldMeshes[ACTIVE_PANEL].size()==MAX_STACK_SIZE){
			
			oldMeshes[ACTIVE_PANEL].removeElementAt(0);
		}
		oldMeshes[ACTIVE_PANEL].push(meshes[ACTIVE_PANEL].clone());
	
	}
	
	public void undo() {
		
		if(oldMeshes[ACTIVE_PANEL].size()>0)
			meshes[ACTIVE_PANEL]=(PolygonMesh) oldMeshes[ACTIVE_PANEL].pop();

		
	}
	
	public void preview(){}
	
	public void menuCanceled(MenuEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void menuDeselected(MenuEvent arg0) {
		redrawAfterMenu=true;

	}

	public void menuSelected(MenuEvent arg0) {
		redrawAfterMenu=false;

	}

	public Polygon3D buildPolygon(LineData ld, Point3D[] points, boolean b) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getACTIVE_PANEL() {
		return ACTIVE_PANEL;
	}

	public void setACTIVE_PANEL(int aCTIVE_PANEL) {
		ACTIVE_PANEL = aCTIVE_PANEL;
	}


}
