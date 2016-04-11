package com.editors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.LineData;
import com.Plain;
import com.Point3D;
import com.Point4D;
import com.Polygon3D;
import com.PolygonMesh;
import com.Rib;
import com.SPLine;
import com.SPNode;
import com.SquareMesh;
import com.main.DrivingFrame;

public abstract class Editor extends DrivingFrame implements MenuListener{
	
	protected int ACTIVE_PANEL=0; 
	protected int numPanels=2;
	
	protected Stack[] oldMeshes=null;
	
	protected transient LineData polygon=new LineData();
	
	public static final int MAX_STACK_SIZE=10;
	
	protected boolean redrawAfterMenu=false;
	
	
	protected File currentDirectory=null;
	protected File currentFile=null;
	

	
	
	
	protected ArrayList<SPLine> splines=null;
	
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
		
		splines=new ArrayList<SPLine>();
	
	}

	@Deprecated
	public static void levelSPLinesTerrain(PolygonMesh mesh, ArrayList splines) {



		int lsize=mesh.polygonData.size();

		for (int i = 0; splines!=null && i < splines.size(); i++) {


			SPLine sp = (SPLine) splines.get(i);

			for (int k = 0; k < sp.ribs.size();k++){
				ArrayList<Rib> nodeRibs =  sp.ribs.get(k);
				
				for (int s = 0; s < nodeRibs.size(); s++) {
					
					Rib rib = nodeRibs.get(s);
					Point4D[] points=rib.getPoints();

					double mx=(points[0].x+points[1].x)*0.5;
					double my=(points[0].y+points[1].y)*0.5;



					for(int j=0;j<lsize;j++){


						LineData ld=(LineData) mesh.polygonData.get(j);

						Polygon3D p3D=levelGetPolygon(ld,mesh.points);

						if(p3D.contains(mx,my)){

							double zz=interpolate(mx,my,p3D);

							for (int l = 0; l < points.length; l++) {
								points[l].z+=zz;
							}


							break;


						}


					} 
					
				}


			}
		}



	}


	private static Polygon3D levelGetPolygon(LineData ld, Point3D[] points) {
		
	

		int size=ld.size();

		int[] cx=new int[size];
		int[] cy=new int[size];
		int[] cz=new int[size];


		for(int i=0;i<size;i++){


			int num=ld.getIndex(i);

			Point4D p=(Point4D) points[num];

			cx[i]=(int)p.x;
			cy[i]=(int)p.y;
			cz[i]=(int)p.z;

	

		}

		Polygon3D p_in=new Polygon3D(ld.size(),cx,cy,cz);
		
		return p_in;
	}
	
	
	private static double interpolate(double px, double py, Polygon3D p3d) {
	       
		Polygon3D p1=Polygon3D.extractSubPolygon3D(p3d,3,0);

		if(p1.hasInsidePoint(px,py)){

			Plain plane1=Plain.calculatePlain(p1);

			return plane1.calculateZ(px,py);

		}

		Polygon3D p2=Polygon3D.extractSubPolygon3D(p3d,3,2);

		if(p2.hasInsidePoint(px,py)){

			Plain plane2=Plain.calculatePlain(p2);

			return plane2.calculateZ(px,py);

		}


		return 0;
	}
	
	
	protected boolean forceReading=false;
	
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
			
			loadPointsFromFile(file,ACTIVE_PANEL,forceReading);
			oldMeshes[ACTIVE_PANEL]=new Stack();
            setTitle(file.getName());

		}
	}
	
	
	
	public void loadSPLinesFromFile(File file){
		
		splines=loadSPLinesFromFile(file,forceReading);
		repaint();
	}

	
	
	
	public static Point3D loadStartPosition(File file) {
		
		Point3D startPosition=null;

		try {
			BufferedReader br=new BufferedReader(new FileReader(file));

			boolean read=false;

			String str=null;

			while((str=br.readLine())!=null){
				if(str.indexOf("#")>=0 || str.length()==0)
					continue;
				
				if(str.indexOf("startPosition")>=0){
					read=!read;
				    continue;
				}	
				
				if(!read)
					continue;
				

				String[] vals = str.split(" ");

				startPosition=new Point3D();
				
				startPosition.x=Double.parseDouble(vals[0]);
				startPosition.y=Double.parseDouble(vals[1]);
				startPosition.z=Double.parseDouble(vals[2]);
		
				
			}

			br.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		if(startPosition==null)
			startPosition=new Point3D(0,0,0);
		
		return startPosition;
	}

	
	@Override
	public void buildPoint(ArrayList<Point3D> vPoints, String str) {
		PolygonMesh.buildPoint(vPoints,str);
		
	}

	@Override
	public void buildLine(ArrayList<LineData> polygonData, String str,
			ArrayList<Point3D> vTexturePoints) {
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
	
	private String decomposeLineData(LineData ld,boolean isCustom) {

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
		
		String str="<spline>\n";

		SPNode root = sp.getRoot();
		int sz=root.getChildCount();
		
		for(int i=-1;i<sz;i++){
			
			if(i>-1)
				str+="\n";
			Point4D p0=(Point4D) root.getChildAt(i);
			str+="v=T"+p0.getIndex()+" "+p0.getX()+" "+p0.getY()+" "+p0.getZ();
		}
		str+="\n</spline>";
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
				pr.print("<"+TAG[ACTIVE_PANEL]+">\n");
			
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
				pr.println();
			}
			
			decomposeObjVertices(pr,mesh,isCustom);

	
			

			for(int i=0;i<mesh.polygonData.size();i++){

				LineData ld=(LineData) mesh.polygonData.get(i);
				
				////////////
				pr.print("f=");
				if(isCustom){
					
					pr.println(decomposeLineData(ld,true));
					
				}else
					pr.println(decomposeLineData(ld));
			
			}	
			if(!forceReading)
				pr.print("</"+TAG[ACTIVE_PANEL]+">\n");
				

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void saveSPLines(PrintWriter pr) {
		
		try {
			
			if(!forceReading)
				pr.println("<"+TAG[ACTIVE_PANEL]+">");
			
			decomposeObjVertices(pr,null,false);
			
			for (int i = 0; i < splines.size(); i++) {
				
				SPLine sp = (SPLine) splines.get(i);
				if(i>0)
					pr.println();
				
				pr.print(decomposeSPLine(sp));
				
			}
			
			
			if(!forceReading)
				pr.println("\n</"+TAG[ACTIVE_PANEL]+">");

		} catch (Exception e) {

			e.printStackTrace();
		}

		
	}


	public abstract void decomposeObjVertices(PrintWriter pr,PolygonMesh mesh,boolean isCustom);


		
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
	@Override
	public void menuCanceled(MenuEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void menuDeselected(MenuEvent arg0) {
		redrawAfterMenu=true;

	}
	@Override
	public void menuSelected(MenuEvent arg0) {
		redrawAfterMenu=false;

	}

	public Polygon3D buildPolygon(LineData ld, Point3D[] points, boolean b) {
		return null;
	}

	public int calculateShadowColor(double xi, double yi, double zi, double cosin, int argbs) {

		return argbs;
	
	}
	
	public double calculateCosin(Polygon3D polReal) {
		
		return 1.0;
	}
	
	public int getACTIVE_PANEL() {
		return ACTIVE_PANEL;
	}

	public void setACTIVE_PANEL(int aCTIVE_PANEL) {
		ACTIVE_PANEL = aCTIVE_PANEL;
	}


	public PolygonMesh[] getMeshes() {
		return meshes;
	}


	public LineData getPolygon() {
		return polygon;
	}


	public void setPolygon(LineData polygon) {
		this.polygon = polygon;
	}


	public boolean isForceReading() {
		return forceReading;
	}


	public void setForceReading(boolean forceReading) {
		this.forceReading = forceReading;
	}


}
