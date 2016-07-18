package com;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import com.main.Road;

public class PolygonMesh implements Cloneable{

	public double[] xpoints=null;
	public double[] ypoints=null;
	public double[] zpoints=null;
	public boolean[] selected=null;
	public Object[] data=null;
	
	public ArrayList <LineData>polygonData=null;
	public ArrayList<Point3D> texturePoints=null;
	ArrayList <Point3D>normals=null;
	public int[] boxFaces=null; 
	int level=Road.OBJECT_LEVEL;
	
	private String description="";


	public PolygonMesh() {
		xpoints=null;
		ypoints=null;
		zpoints=null;
		selected=null;
		polygonData= new ArrayList <LineData>();
		normals= new ArrayList <Point3D>();
		texturePoints=new ArrayList<Point3D>();
	}
	
	public PolygonMesh(double[] xpoints,double[] ypoints,double[] zpoints, ArrayList <LineData> polygonData) {
		
		this.xpoints=xpoints;
		this.ypoints=ypoints;
		this.zpoints=zpoints;
		this.selected=new boolean[xpoints.length];
		
		if(polygonData!=null)
			this.polygonData = polygonData;
		else
			this.polygonData= new ArrayList <LineData>();
		
		calculateNormals();
	}



	public PolygonMesh(ArrayList<Point3D> aPoints, ArrayList<LineData> lines) {
		
		this.xpoints=new double[aPoints.size()];
		this.ypoints=new double[aPoints.size()];
		this.zpoints=new double[aPoints.size()];
		this.selected=new boolean[aPoints.size()];
		
		for (int i = 0; i < aPoints.size(); i++) {
			Point3D p=aPoints.get(i);
			xpoints[i]=p.x;
			ypoints[i]=p.y;
			zpoints[i]=p.z;
		}
		
		if(lines!=null)
			this.polygonData = lines;
		else
			this.polygonData= new ArrayList <LineData>();
		
		calculateNormals();
	}
	
	public PolygonMesh(Point3D[] rPoints, ArrayList<LineData> lines) {
		
		this.xpoints=new double[rPoints.length];
		this.ypoints=new double[rPoints.length];
		this.zpoints=new double[rPoints.length];
		this.selected=new boolean[rPoints.length];
		
		for (int i = 0; i < rPoints.length; i++) {
			Point3D p=rPoints[i];
			xpoints[i]=p.x;
			ypoints[i]=p.y;
			zpoints[i]=p.z;
		}
		
		if(lines!=null)
			this.polygonData = lines;
		else
			this.polygonData= new ArrayList <LineData>();
		
		calculateNormals();
	}

	private void calculateNormals() {
		
				
		normals=new ArrayList<Point3D>();
		boxFaces=new int[polygonData.size()];
		for(int l=0;l<polygonData.size();l++){

			LineData ld=(LineData) polygonData.get(l);

			Point3D normal = getNormal(0,ld,xpoints,ypoints,zpoints);
			normals.add(normal);
			if(ld.getData()!=null){
				
				boxFaces[l]=Integer.parseInt(ld.getData());
			}
			
		}
		
	}
	
	public static Point3D getNormal(int position, LineData ld,
			double[] xpoints,double[] ypoints,double[] zpoints) {

		int n=ld.size();


		Point3D p0=new Point3D(
				xpoints[ld.getIndex((n+position-1)%n)],
				ypoints[ld.getIndex((n+position-1)%n)],
				zpoints[ld.getIndex((n+position-1)%n)]);
				
				
		Point3D p1=new Point3D(
				xpoints[ld.getIndex(position)],
				ypoints[ld.getIndex(position)],
				zpoints[ld.getIndex(position)]);
				
		Point3D p2=new Point3D(
				xpoints[ld.getIndex((1+position)%n)],
				ypoints[ld.getIndex((1+position)%n)],
				zpoints[ld.getIndex((1+position)%n)]);

		Point3D normal=Point3D.calculateCrossProduct(p1.substract(p0),p2.substract(p1));


		return normal.calculateVersor();
	}

	public void addPolygonData(LineData polygon){
		polygonData.add(polygon);		
	}
	@Override
	public PolygonMesh clone() {
		
		PolygonMesh pm=new PolygonMesh();
		
		pm.xpoints=new double[this.xpoints.length];
		pm.ypoints=new double[this.xpoints.length];
		pm.zpoints=new double[this.xpoints.length];
		pm.selected=new boolean[this.xpoints.length];
		
		for(int i=0;i<this.xpoints.length;i++){
	
			pm.xpoints[i]=xpoints[i];
			pm.ypoints[i]=ypoints[i];
			pm.zpoints[i]=zpoints[i];
			
		}
		
		
		for(int i=0;i<this.polygonData.size();i++){

			pm.addPolygonData(polygonData.get(i).clone());
		}
		for(int i=0;i<this.normals.size();i++){

			pm.normals.add(normals.get(i).clone());
		}
		
		for(int i=0;texturePoints!=null && i<this.texturePoints.size();i++){

			pm.texturePoints.add(((Point3D)texturePoints.get(i)).clone());
		}
	
		pm.setLevel(level);
		
		return pm;
	}
	/**
	 * 
	 * Calculate the body polygon in the real coordinates system
	 * 
	 * @param points
	 * @param ld
	 * @param level
	 * @return
	 */
	public static Polygon3D getBodyPolygon(double[] xpoints,double[] ypoints,double[] zpoints,LineData ld,int level) {
		 
		int size=ld.size();
		Polygon3D pol=new Polygon3D(size);
		pol.setIsFilledWithWater(ld.isFilledWithWater());
		pol.setWaterPolygon(ld.isWaterPolygon());
		for(int i=0;i<size;i++){
			int index=ld.getIndex(i);
			
			pol.xpoints[i]=(int) xpoints[index];
			pol.ypoints[i]=(int) ypoints[index];
			pol.zpoints[i]=(int) zpoints[index];
			
			Point3D pt = ld.getVertexTexturePoint(i);
			
			pol.xtpoints[i]=(int) pt.x;
			pol.ytpoints[i]=(int) pt.y;
			
			
		} 
		pol.setLevel(level);
		
		return pol;
		
	}
	
	/**
	 * 
	 * Calculate the body polygon in the real coordinates system
	 * 
	 * @param points
	 * @param ld
	 * @param level
	 * @return
	 */
	public static Polygon3D getBodyPolygonWithoutTextures(Point3D[] points,LineData ld,int level) {
		 
		int nPoints=ld.size();
		
		int[] xpoints = new int[nPoints];
		int[] ypoints = new int[nPoints];
		int[] zpoints = new int[nPoints];
	
		for(int i=0;i<nPoints;i++){
			int index=ld.getIndex(i);
			
			xpoints[i]=(int) points[index].x;
			ypoints[i]=(int) points[index].y;
			zpoints[i]=(int) points[index].z;
			
		} 
		
		Polygon3D pol=new Polygon3D(nPoints,xpoints,ypoints,zpoints,null,null);
		pol.setIsFilledWithWater(ld.isFilledWithWater());
		pol.setWaterPolygon(ld.isWaterPolygon());		
		pol.setLevel(level);
		
		return pol;
		
	}
	
	public static ArrayList<Polygon3D> getBodyPolygons(PolygonMesh pm) {

		ArrayList<Polygon3D> pols=new ArrayList<Polygon3D>();
        int polsSize=pm.polygonData.size();

		for(int j=0;j<polsSize;j++){

			LineData ld=pm.polygonData.get(j);
			int size=ld.size();

			Polygon3D pol=new Polygon3D(ld.size());

			for(int i=0;i<size;i++){
				int index=ld.getIndex(i);

				pol.xpoints[i]=(int) pm.xpoints[index];
				pol.ypoints[i]=(int) pm.ypoints[index];
				pol.zpoints[i]=(int) pm.zpoints[index];
			} 
			pol.setLevel(pm.getLevel());
			pol.setIsFilledWithWater(ld.isFilledWithWater());
			pol.setWaterPolygon(ld.isWaterPolygon());
			pols.add(pol);
		}
		return pols;

	}

	
	
	public static void buildPoint(List<Point3D> vPoints,String str) {
		

			String[] vals =str.split(" ");

			Point3D p=new Point3D();

			p.x=Double.parseDouble(vals[0]);
			p.y=Double.parseDouble(vals[1]);
			p.z=Double.parseDouble(vals[2]);
			
			if(vals.length==4)
				p.data=vals[3];

			vPoints.add(p);
		

	}
	

	public static void buildLine(ArrayList<LineData> polygonData, String token, ArrayList<Point3D> vTexturePoints) {



		LineData ld=new LineData();

		if(token.indexOf("]")>0){

			String extraData=token.substring(token.indexOf("[")+1,token.indexOf("]"));
			token=token.substring(token.indexOf("]")+1);
			ld.setData(extraData);

		}

		String[] vals = token.split(" ");



		for(int i=0;i<vals.length;i++){

			String val=vals[i];
			if(val.indexOf("/")>0){


				String val0=val.substring(0,val.indexOf("/"));
				String val1=val.substring(1+val.indexOf("/"));

				int indx0=Integer.parseInt(val0);	
				int indx1=Integer.parseInt(val1);					
				Point3D pt=(Point3D) vTexturePoints.get(indx1);					

				ld.addIndex(indx0,indx1,pt.x,pt.y);
			}
			else
				ld.addIndex(Integer.parseInt(val));
		}

		polygonData.add(ld);


	}

	
	
	public static void buildTexturePoint(ArrayList<Point3D> vTexturePoints,String str) {
		
		String[] vals=str.split(" ");
		
		double x=Double.parseDouble(vals[0]);
		double y=Double.parseDouble(vals[1]);
		Point3D p=new Point3D(x,y,0);
		
		vTexturePoints.add(p);
	}
	
	public void translate(double i, double j, double k) {
		
		for(int p=0;p<xpoints.length;p++){
			//points[p].translate(i,j,k);
			xpoints[p]+=i;
			ypoints[p]+=j;
			zpoints[p]+=k;
		}
	
	}

	static PolygonMesh loadMeshFromFile(File file) {
		ArrayList<Point3D> points=new ArrayList<Point3D>();
		ArrayList<LineData> lines=new ArrayList<LineData>();
		ArrayList<Point3D> vTexturePoints=new ArrayList<Point3D>();
		String description="";
		
		PolygonMesh pm=null;

		try {
			BufferedReader br=new BufferedReader(new FileReader(file));


			String str=null;

			while((str=br.readLine())!=null){
				if(str.indexOf("#")>=0 || str.length()==0)
					continue;

				if(str.startsWith("v="))
					buildPoint(points,str.substring(2));
				else if(str.startsWith("vt="))
					PolygonMesh.buildTexturePoint(vTexturePoints,str.substring(3));
				else if(str.startsWith("f="))
					PolygonMesh.buildLine(lines,str.substring(2),vTexturePoints);
				else if(str.startsWith("DESCRIPTION="))
					description=str.substring(str.indexOf("=")+1);



			}

			br.close();
			//checkNormals();
		} catch (Exception e) {

			e.printStackTrace();
		}
		pm=new PolygonMesh(points,lines);
		pm.setDescription(description);
	
		return pm;
	}
	
    public static Point3D[] fromVectorToArray(ArrayList<Point3D> points){
    	
    	Point3D[] pts=new Point3D[points.size()];
    	
    	for (int i = 0; i < points.size(); i++) {
    		pts[i] = (Point3D) points.get(i);
		}
    	
    	return pts;
    	
    }

    public static PolygonMesh simplifyMesh(PolygonMesh pm){
		
		
		PolygonMesh newPolygonMesh=new PolygonMesh();
		
		double[] arrXPoints = pm.xpoints; 
		double[] arrYPoints = pm.ypoints; 
		double[] arrZPoints = pm.zpoints; 
		ArrayList<LineData> polygonData = pm.polygonData;
		
		//which points are really used ?*/
		
		Hashtable readIndexes=new Hashtable();
		
		ArrayList<Integer> orderedIndexes=new ArrayList<Integer>();
		
		for (int i = 0; i < polygonData.size(); i++) {
			LineData ld= (LineData) polygonData.get(i);
			
					
			for (int j = 0; j < ld.lineDatas.size(); j++) {
				
				LineDataVertex vertex=(LineDataVertex ) ld.lineDatas.get(j);
				 if(readIndexes.get(vertex.getVertex_index())!=null)
					 continue;
				 readIndexes.put(vertex.getVertex_index(),vertex.getVertex_index());
				 orderedIndexes.add(vertex.getVertex_index());
			}
			
		}
		
		//order points 
		Collections.sort(orderedIndexes);
		
		//build transforming function
		Hashtable transformFunction=new Hashtable();
		
		for (int i = 0; i < orderedIndexes.size(); i++) {
			Integer index = (Integer) orderedIndexes.get(i);
			transformFunction.put(index,new Integer(i));	
		}
		
		//create new points
		double[] newArrXPoints = new double[orderedIndexes.size()];
		double[] newArrYPoints = new double[orderedIndexes.size()];
		double[] newArrZPoints = new double[orderedIndexes.size()];
		
		for (int i = 0; i < newArrXPoints.length; i++) {
			newArrXPoints[i]=arrXPoints[ (Integer)orderedIndexes.get(i)];
			newArrYPoints[i]=arrYPoints[ (Integer)orderedIndexes.get(i)];
			newArrZPoints[i]=arrZPoints[ (Integer)orderedIndexes.get(i)];
		}
		
		//create new line data
		
		ArrayList<LineData> newPolygonData =new ArrayList<LineData>();
			
		for (int i = 0; i < polygonData.size(); i++) {
			
			LineData ld= (LineData) polygonData.get(i);
			LineData newLd=new LineData(); 
					
			for (int j = 0; j < ld.lineDatas.size(); j++) {
				
			     LineDataVertex vertex=(LineDataVertex ) ld.lineDatas.get(j);
			     Integer newIndex=(Integer)transformFunction.get(vertex.getVertex_index());
				 newLd.addIndex(newIndex.intValue(),vertex.getVertex_texture_index(),0,0);
				 
			}
			newLd.setData(ld.getData());
			newLd.setTexture_index(ld.getTexture_index());
			newPolygonData.add(newLd);
			
		}	
		
		newPolygonMesh.xpoints=newArrXPoints;
		newPolygonMesh.ypoints=newArrYPoints;
		newPolygonMesh.zpoints=newArrZPoints;
		newPolygonMesh.polygonData=newPolygonData;
		newPolygonMesh.texturePoints=pm.getTexturePoints();
		return newPolygonMesh;
	}

	public ArrayList<Point3D> getPointsAsArrayList() {
		
		ArrayList<Point3D> vpoints=new ArrayList<Point3D>(); 
		
		for (int i = 0; i < vpoints.size(); i++) {
			vpoints.add(new Point3D(xpoints[i],ypoints[i],zpoints[i]));
		}
		
		return vpoints;
	}

	public ArrayList<LineData> getPolygonData() {
		return polygonData;
	}

	public void setPolygonData(ArrayList<LineData> polygonData) {
		this.polygonData = polygonData;
	}

	public void setPoints(double[] xpoints,double[] ypoints,double[] zpoints) {
		
		this.xpoints=xpoints;
		this.ypoints=ypoints;
		this.zpoints=zpoints;
	
	}
	
	
	public void setPoints(ArrayList<Point3D> aPoints) {
		
		this.xpoints=new double[aPoints.size()];
		this.ypoints=new double[aPoints.size()];
		this.zpoints=new double[aPoints.size()];
		
		for (int i = 0; i < aPoints.size(); i++) {
			Point3D p=aPoints.get(i);
			xpoints[i]=p.x;
			ypoints[i]=p.y;
			zpoints[i]=p.z;
		}
		
		this.selected=new boolean[aPoints.size()];
		
	}

	public void addPoint(Point3D point) {
		
		int len=0;
		if(xpoints!=null)
			len=xpoints.length;

		double[] newXPoints=new double[len+1];
		double[] newYPoints=new double[len+1];
		double[] newZPoints=new double[len+1];

		for (int i = 0; xpoints!=null && i < xpoints.length; i++) {
			newXPoints[i]=xpoints[i];
			newYPoints[i]=ypoints[i];
			newZPoints[i]=zpoints[i];
		}

		newXPoints[len]=point.x;
		newYPoints[len]=point.y;
		newZPoints[len]=point.z;
		
		xpoints=newXPoints;
		ypoints=newYPoints;
		zpoints=newZPoints;

	}

	public ArrayList<Point3D> getTexturePoints() {
		return texturePoints;
	}

	public void setTexturePoints(ArrayList<Point3D> vTexturePoints) {
		this.texturePoints = vTexturePoints;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}




}
