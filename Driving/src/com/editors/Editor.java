package com.editors;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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
import javax.swing.filechooser.FileView;

import com.LineData;
import com.Point3D;
import com.Polygon3D;

public class Editor extends JFrame implements MenuListener{
	
	public int ACTIVE_PANEL=0; 
	public int numPanels=2;
	
	public Vector[] points=null;
	public Vector[] lines=null;
	
	public Stack[] oldPoints=null;
	public Stack[] oldLines=null;
	
	public LineData polygon=new LineData();
	
	public int MAX_STACK_SIZE=10;
	
	public boolean redrawAfterMenu=false;
	
	public JFileChooser fc= new JFileChooser();
	public File currentDirectory=null;
	public File currentFile=null;
	
	public static String TAG[]={"terrain","road"};
	
	public Editor(){
		
		
		points=new Vector[numPanels];
		lines=new  Vector[numPanels];
		
		
		oldPoints=new Stack[numPanels];
		oldLines=new Stack[numPanels];
		
		for (int i = 0; i < numPanels; i++) {
			
			
			points[i]=new Vector();
			lines[i]=new  Vector();
			
			
			oldPoints[i]=new Stack();
			oldLines[i]=new Stack();
		}
		
		
	
	}
	
	public void buildPoints(Vector points, String str) {

		StringTokenizer sttoken=new StringTokenizer(str,"_");

		while(sttoken.hasMoreElements()){

			String[] vals = sttoken.nextToken().split(",");

			Point3D p=new Point3D();

			p.x=Double.parseDouble(vals[0]);
			p.y=Double.parseDouble(vals[1]);
			p.z=Double.parseDouble(vals[2]);
			
			if(vals.length==4)
				p.data=vals[3];

			points.add(p);
		}




	}
	
	public void buildLines(Vector lines, String str) {

		StringTokenizer sttoken=new StringTokenizer(str,"_");

		while(sttoken.hasMoreElements()){

			String[] vals = sttoken.nextToken().split(",");

			LineData ld=new LineData();

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


		}
	}
	
	public void loadPointsFromFile(File file){

		points[ACTIVE_PANEL]=new  Vector();
		lines[ACTIVE_PANEL]=new  Vector();
		
		oldPoints[ACTIVE_PANEL]=new Stack();
		oldLines[ACTIVE_PANEL]=new Stack();

		try {
			BufferedReader br=new BufferedReader(new FileReader(file));


			String str=null;
			int rows=0;
			
			boolean read=forceReading;
			
			while((str=br.readLine())!=null){
				
				
				
				if(str.indexOf("#")>=0 || str.length()==0)
					continue;
				
				if(str.indexOf(TAG[ACTIVE_PANEL])>=0){
					read=!read;
				    continue;
				}	
				
				if(!read)
					continue;

				if(str.startsWith("P="))
					buildPoints(points[ACTIVE_PANEL],str.substring(2));
				else if(str.startsWith("L="))
					buildLines(lines[ACTIVE_PANEL],str.substring(2));


			}

			br.close();
			//checkNormals();
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		repaint();
	}
	
	public String decomposePoint(Point3D p) {
		String str="";

		str=p.x+","+p.y+","+p.z;
		
		if(p.getData()!=null)
			str=str+","+p.getData().toString();

		return str;
	}
	
	public String decomposeLineData(LineData ld) {

		String str="";

		for(int j=0;j<ld.size();j++){

			if(j>0)
				str+=",";
			str+=ld.getIndex(j);

		}

		return str;
	}
	
	public void saveLines() {

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
				saveLines(pr);
	            pr.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		} 


	}

	public void saveLines(PrintWriter pr) {

       

		
		try {
			
			pr.println("<"+TAG[ACTIVE_PANEL]+">");
			pr.print("P=");

			for(int i=0;i<points[ACTIVE_PANEL].size();i++){

				Point3D p=(Point3D) points[ACTIVE_PANEL].elementAt(i);

				pr.print(decomposePoint(p));
				if(i<points[ACTIVE_PANEL].size()-1)
					pr.print("_");
			}	

			pr.print("\nL=");

			for(int i=0;i<lines[ACTIVE_PANEL].size();i++){

				LineData ld=(LineData) lines[ACTIVE_PANEL].elementAt(i);

				pr.print(decomposeLineData(ld));
				if(i<lines[ACTIVE_PANEL].size()-1)
					pr.print("_");
			}	
			pr.println("\n</"+TAG[ACTIVE_PANEL]+">");
				

		} catch (Exception e) {

			e.printStackTrace();
		}
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
		
		if(oldPoints[ACTIVE_PANEL].size()==MAX_STACK_SIZE){
			
			oldPoints[ACTIVE_PANEL].removeElementAt(0);
			oldLines[ACTIVE_PANEL].removeElementAt(0);
		}
		oldPoints[ACTIVE_PANEL].push(clonePoints(points[ACTIVE_PANEL]));
		oldLines[ACTIVE_PANEL].push(cloneLineData(lines[ACTIVE_PANEL]));
	}
	
	public void undo() {
		
		if(oldPoints[ACTIVE_PANEL].size()>0)
			points[ACTIVE_PANEL]=(Vector) oldPoints[ACTIVE_PANEL].pop();
		if(oldLines[ACTIVE_PANEL].size()>0)
			lines[ACTIVE_PANEL]=(Vector) oldLines[ACTIVE_PANEL].pop();
		
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

	public Polygon3D buildPolygon(LineData ld, Vector points2, boolean b) {
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
