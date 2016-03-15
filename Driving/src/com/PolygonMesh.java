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
	public Vector<Point3D> texturePoints=null;
	public Vector <Point3D>normals=null;
	public int[] boxFaces=null; 
	public int level=0;


	public PolygonMesh() {
		points= null;
		polygonData= new Vector <LineData>();
		normals= new Vector <Point3D>();
		texturePoints=new Vector();
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
		
		for(int i=0;texturePoints!=null && i<this.texturePoints.size();i++){

			pm.texturePoints.add(((Point3D)texturePoints.elementAt(i)).clone());
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
			
			Point3D pt = ld.getVertexTexturePoint(i);
			
			pol.xtpoints[i]=(int) pt.x;
			pol.ytpoints[i]=(int) pt.y;
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

	
	
	public static void buildPoint(Vector vPoints,String str) {
		

			String[] vals =str.split(" ");

			Point3D p=new Point3D();

			p.x=Double.parseDouble(vals[0]);
			p.y=Double.parseDouble(vals[1]);
			p.z=Double.parseDouble(vals[2]);
			
			if(vals.length==4)
				p.data=vals[3];

			vPoints.add(p);
		

	}
	

	public static void buildLine(Vector polygonData, String token, Vector vTexturePoints) {



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
				Point3D pt=(Point3D) vTexturePoints.elementAt(indx1);					

				ld.addIndex(indx0,indx1,pt.x,pt.y);
			}
			else
				ld.addIndex(Integer.parseInt(val));
		}

		polygonData.add(ld);


	}

	
	
	public static void buildTexturePoint(Vector vTexturePoints,String str) {
		
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

	public static PolygonMesh loadMeshFromFile(File file) {
		Vector points=new Vector();
		Vector lines=new Vector();
		Vector vTexturePoints=new Vector();
		
		PolygonMesh pm=null;

		try {
			BufferedReader br=new BufferedReader(new FileReader(file));


			String str=null;
			int rows=0;
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
				
				LineDataVertex vertex=(LineDataVertex ) ld.lineDatas.elementAt(j);
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
				
			     LineDataVertex vertex=(LineDataVertex ) ld.lineDatas.elementAt(j);
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

	public Vector getTexturePoints() {
		return texturePoints;
	}

	public void setTexturePoints(Vector vTexturePoints) {
		this.texturePoints = vTexturePoints;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}


}
