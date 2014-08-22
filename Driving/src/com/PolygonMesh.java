package com;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import com.main.Renderer3D;

public class PolygonMesh implements Cloneable{
	
	


	public Point3D[] points=null;
	public Vector <LineData>polygonData=null;
	public Vector <Point3D>normals=null;
	public int[] boxFaces=null; 



	public PolygonMesh() {
		points= null;
		polygonData= new Vector <LineData>();
		normals= new Vector <Point3D>();		
	}
	
	public PolygonMesh(Point3D[] points, Vector<LineData> polygonData) {
		this.points=points;
		this.polygonData=polygonData;
		calculateNormals();
	}
	
	public PolygonMesh(Vector <Point3D> points, Vector <LineData> polygonData) {
		
		if(points!=null){
			this.points=new Point3D[points.size()];
			for(int i=0;i<points.size();i++)
				this.points[i] = points.elementAt(i);
		}	
		else
			this.points= null;
		
		if(polygonData!=null)
			this.polygonData = polygonData;
		else
			this.polygonData= new Vector <LineData>();
		
		calculateNormals();
	}



	private void calculateNormals() {
		
				
		normals=new Vector<Point3D>();
		boxFaces=new int[polygonData.size()];
		for(int l=0;l<polygonData.size();l++){

			LineData ld=(LineData) polygonData.elementAt(l);

			Point3D normal = getNormal(0,ld,points);
			normals.add(normal);
			if(ld.getData()!=null){
				
				boxFaces[l]=Integer.parseInt(ld.getData());
			}
			/*else
				boxFaces[l]=Renderer3D.findBoxFace(normal);*/
			
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

	public void addPoints(Point3D[] addPoints){
		
		int newSize=points.length+addPoints.length;	
		Point3D[] newPoints=new Point3D[newSize];
		
		
		for(int i=0;i<points.length;i++)
			newPoints[i]=points[i];
		for(int i=0;i<addPoints.length;i++)
			newPoints[i+points.length]=addPoints[i];
		
	}
	
	public void addPolygonData(LineData polygon){
		polygonData.add(polygon);		
	}
	
	public PolygonMesh clone() {
		
		PolygonMesh pm=new PolygonMesh();
		pm.points=new Point3D[this.points.length];
		
		for(int i=0;i<this.points.length;i++){
	
			pm.points[i]=points[i].clone();
			
		}
		
		for(int i=0;i<this.polygonData.size();i++){

			pm.addPolygonData(polygonData.elementAt(i).clone());
		}
		for(int i=0;i<this.normals.size();i++){

			pm.normals.add(normals.elementAt(i).clone());
		}
	
		return pm;
	}
	
	public static Polygon3D getBodyPolygon(Point3D[] points,LineData ld) {
		 
		int size=ld.size();
		Polygon3D pol=new Polygon3D(size);
		
		for(int i=0;i<size;i++){
			int index=ld.getIndex(i);
			
			pol.xpoints[i]=(int) points[index].x;
			pol.ypoints[i]=(int) points[index].y;
			pol.zpoints[i]=(int) points[index].z;
		} 
		
		return pol;
		
	}
	
	public static Vector getBodyPolygons(PolygonMesh pm) {

		Vector pols=new Vector();
        int polsSize=pm.polygonData.size();

		for(int j=0;j<polsSize;j++){

			LineData ld=pm.polygonData.elementAt(j);
			int size=ld.size();

			Polygon3D pol=new Polygon3D(ld.size());

			for(int i=0;i<size;i++){
				int index=ld.getIndex(i);

				pol.xpoints[i]=(int) pm.points[index].x;
				pol.ypoints[i]=(int) pm.points[index].y;
				pol.zpoints[i]=(int) pm.points[index].z;
			} 
			pols.add(pol);
		}
		return pols;

	}
	
	public static void buildPoints(Vector points, String str) {

		StringTokenizer sttoken=new StringTokenizer(str,"_");

		while(sttoken.hasMoreElements()){

			String[] vals = sttoken.nextToken().split(",");

			Point3D p=new Point3D();

			p.x=Double.parseDouble(vals[0]);
			p.y=Double.parseDouble(vals[1]);
			p.z=Double.parseDouble(vals[2]);

			points.add(p);
		}




	}

	public static void buildLines(Vector lines, String str) {

		StringTokenizer sttoken=new StringTokenizer(str,"_");

		while(sttoken.hasMoreElements()){
			
			String token=sttoken.nextToken();
			
			LineData ld=new LineData();

			if(token.indexOf("]")>0){
				
				String extraData=token.substring(token.indexOf("[")+1,token.indexOf("]"));
				token=token.substring(token.indexOf("]")+1);
				ld.setData(extraData);
				
			}
			
			String[] vals = token.split(",");

			

			for(int i=0;i<vals.length;i++)
				ld.addIndex(Integer.parseInt(vals[i]));


			lines.add(ld);
		}
	}

	public void translate(double i, double j, double k) {
		
		for(int p=0;p<points.length;p++){
			points[p].translate(i,j,k);
	
		}
	
	}

	public static PolygonMesh loadMeshFromFile(File file) {
		Vector points=new Vector();
		Vector lines=new Vector();
		
		PolygonMesh pm=null;

		try {
			BufferedReader br=new BufferedReader(new FileReader(file));


			String str=null;
			int rows=0;
			while((str=br.readLine())!=null){
				if(str.indexOf("#")>=0 || str.length()==0)
					continue;

				if(str.startsWith("P="))
					buildPoints(points,str.substring(2));
				else if(str.startsWith("L="))
					buildLines(lines,str.substring(2));


			}

			br.close();
			//checkNormals();
		} catch (Exception e) {

			e.printStackTrace();
		}
		pm=new PolygonMesh(points,lines);
		
	
		return pm;
	}
	
    public static Point3D[] fromVectorToArray(Vector points){
    	
    	Point3D[] pts=new Point3D[points.size()];
    	
    	for (int i = 0; i < points.size(); i++) {
    		pts[i] = (Point3D) points.elementAt(i);
		}
    	
    	return pts;
    	
    }

    public static Vector  fromArrayToVector(Point3D[] points){
    	
    	Vector pts=new Vector();
    	
    	for (int i = 0; i < points.length; i++) {
    		pts.add(points[i]);
		}
    	
    	return pts;
    	
    }
    
	public static PolygonMesh simplifyMesh(PolygonMesh pm){
		
		
		PolygonMesh newPolygonMesh=new PolygonMesh();
		
		Point3D[] arrPoints = pm.points; 
		Vector<LineData> polygonData = pm.polygonData;
		
		//which points are really used ?
		
		Hashtable readIndexes=new Hashtable();
		
		Vector orderedIndexes=new Vector();
		
		for (int i = 0; i < polygonData.size(); i++) {
			LineData ld= (LineData) polygonData.elementAt(i);
			
					
			for (int j = 0; j < ld.lineDatas.size(); j++) {
				
				 Integer index=(Integer) ld.lineDatas.elementAt(j);
				 if(readIndexes.get(index)!=null)
					 continue;
				 readIndexes.put(index,index);
				 orderedIndexes.add(index);
			}
			
		}
		
		//order points 
		Collections.sort(orderedIndexes);
		
		//build transforming function
		Hashtable transformFunction=new Hashtable();
		
		for (int i = 0; i < orderedIndexes.size(); i++) {
			Integer index = (Integer) orderedIndexes.elementAt(i);
			transformFunction.put(index,new Integer(i));	
		}
		
		//create new points
		Point3D[] newArrPoints = new Point3D[orderedIndexes.size()];
		
		for (int i = 0; i < newArrPoints.length; i++) {
			newArrPoints[i]=arrPoints[ (Integer)orderedIndexes.elementAt(i)];
		}
		
		//create new line data
		
		Vector<LineData> newPolygonData =new Vector();
			
		for (int i = 0; i < polygonData.size(); i++) {
			
			LineData ld= (LineData) polygonData.elementAt(i);
			LineData newLd=new LineData(); 
					
			for (int j = 0; j < ld.lineDatas.size(); j++) {
				
				 Integer index=(Integer) ld.lineDatas.elementAt(j);
				 Integer newIndex=(Integer)transformFunction.get(index);
				 newLd.addIndex(newIndex.intValue());
				 
			}
			newLd.setData(ld.getData());
			newLd.setTexture_index(ld.getTexture_index());
			newPolygonData.add(newLd);
			
		}	
		
		newPolygonMesh.points=newArrPoints;
		newPolygonMesh.polygonData=newPolygonData;
		return newPolygonMesh;
	}

	public Vector getPointsAsVector() {
		
		Vector vpoints=new Vector(); 
		
		for (int i = 0; i < points.length; i++) {
			vpoints.add(points[i]);
		}
		
		return vpoints;
	}

	public Vector<LineData> getPolygonData() {
		return polygonData;
	}

	public void setPolygonData(Vector<LineData> polygonData) {
		this.polygonData = polygonData;
	}

	public void setPoints(Vector points) {
		
		if(points!=null){
			this.points=new Point3D[points.size()];
			for(int i=0;i<points.size();i++)
				this.points[i] = (Point3D) points.elementAt(i);
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


}
