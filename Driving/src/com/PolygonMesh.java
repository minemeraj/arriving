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
	
	


	public Point3D[] points=null;
	public ArrayList <LineData>polygonData=null;
	public ArrayList<Point3D> texturePoints=null;
	ArrayList <Point3D>normals=null;
	public int[] boxFaces=null; 
	int level=Road.OBJECT_LEVEL;


	public PolygonMesh() {
		points= null;
		polygonData= new ArrayList <LineData>();
		normals= new ArrayList <Point3D>();
		texturePoints=new ArrayList<Point3D>();
	}
	
	public PolygonMesh(Point3D[] points, ArrayList<LineData> polygonData) {
		this.points=points;
		this.polygonData=polygonData;
		calculateNormals();
	}
	
	public PolygonMesh(ArrayList <Point3D> points, ArrayList <LineData> polygonData) {
		
		if(points!=null){
			this.points=new Point3D[points.size()];
			for(int i=0;i<points.size();i++)
				this.points[i] = points.get(i);
		}	
		else
			this.points= null;
		
		if(polygonData!=null)
			this.polygonData = polygonData;
		else
			this.polygonData= new ArrayList <LineData>();
		
		calculateNormals();
	}



	private void calculateNormals() {
		
				
		normals=new ArrayList<Point3D>();
		boxFaces=new int[polygonData.size()];
		for(int l=0;l<polygonData.size();l++){

			LineData ld=(LineData) polygonData.get(l);

			Point3D normal = getNormal(0,ld,points);
			normals.add(normal);
			if(ld.getData()!=null){
				
				boxFaces[l]=Integer.parseInt(ld.getData());
			}
			
		}
		
	}
	
	public static Point3D getNormal(int position, LineData ld,
			Point3D[] points) {

		int n=ld.size();


		Point3D p0=points[ld.getIndex((n+position-1)%n)];
		Point3D p1=points[ld.getIndex(position)];
		Point3D p2=points[ld.getIndex((1+position)%n)];

		Point3D normal=Point3D.calculateCrossProduct(p1.substract(p0),p2.substract(p1));

		return normal.calculateVersor();
	}

	public void addPolygonData(LineData polygon){
		polygonData.add(polygon);		
	}
	@Override
	public PolygonMesh clone() {
		
		PolygonMesh pm=new PolygonMesh();
		pm.points=new Point3D[this.points.length];
		
		for(int i=0;i<this.points.length;i++){
	
			pm.points[i]=points[i].clone();
			
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
	
	public static Polygon3D getBodyPolygon(Point3D[] points,LineData ld,int level) {
		 
		int size=ld.size();
		Polygon3D pol=new Polygon3D(size);
		pol.setIsFilledWithWater(ld.isFilledWithWater());
		
		for(int i=0;i<size;i++){
			int index=ld.getIndex(i);
			
			pol.xpoints[i]=(int) points[index].x;
			pol.ypoints[i]=(int) points[index].y;
			pol.zpoints[i]=(int) points[index].z;
			
			Point3D pt = ld.getVertexTexturePoint(i);
			
			pol.xtpoints[i]=(int) pt.x;
			pol.ytpoints[i]=(int) pt.y;
			
			
		} 
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

				pol.xpoints[i]=(int) pm.points[index].x;
				pol.ypoints[i]=(int) pm.points[index].y;
				pol.zpoints[i]=(int) pm.points[index].z;
			} 
			pol.setLevel(pm.getLevel());
			pol.setIsFilledWithWater(ld.isFilledWithWater());
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
		
		for(int p=0;p<points.length;p++){
			points[p].translate(i,j,k);
	
		}
	
	}

	static PolygonMesh loadMeshFromFile(File file) {
		ArrayList<Point3D> points=new ArrayList<Point3D>();
		ArrayList<LineData> lines=new ArrayList<LineData>();
		ArrayList<Point3D> vTexturePoints=new ArrayList<Point3D>();
		
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



			}

			br.close();
			//checkNormals();
		} catch (Exception e) {

			e.printStackTrace();
		}
		pm=new PolygonMesh(points,lines);
		
	
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
		
		Point3D[] arrPoints = pm.points; 
		ArrayList<LineData> polygonData = pm.polygonData;
		
		//which points are really used ?
		
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
		Point3D[] newArrPoints = new Point3D[orderedIndexes.size()];
		
		for (int i = 0; i < newArrPoints.length; i++) {
			newArrPoints[i]=arrPoints[ (Integer)orderedIndexes.get(i)];
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
		
		newPolygonMesh.points=newArrPoints;
		newPolygonMesh.polygonData=newPolygonData;
		newPolygonMesh.texturePoints=pm.getTexturePoints();
		return newPolygonMesh;
	}

	public ArrayList<Point3D> getPointsAsArrayList() {
		
		ArrayList<Point3D> vpoints=new ArrayList<Point3D>(); 
		
		for (int i = 0; i < points.length; i++) {
			vpoints.add(points[i]);
		}
		
		return vpoints;
	}

	public ArrayList<LineData> getPolygonData() {
		return polygonData;
	}

	public void setPolygonData(ArrayList<LineData> polygonData) {
		this.polygonData = polygonData;
	}

	public void setPoints(ArrayList<Point3D> points) {
		
		if(points!=null){
			this.points=new Point3D[points.size()];
			for(int i=0;i<points.size();i++)
				this.points[i] = (Point3D) points.get(i);
		}	
		else
			this.points= null;
		
	}

	public void addPoint(Point3D point) {
		
		int len=0;
		if(points!=null)
			len=points.length;

		Point3D[] newPoints=new Point3D[len+1];

		for (int i = 0; points!=null && i < points.length; i++) {
			newPoints[i]=points[i];
		}

		newPoints[len]=point;
		
		points=newPoints;

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


}
