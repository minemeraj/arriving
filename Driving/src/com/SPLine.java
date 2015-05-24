package com;

import java.util.Vector;

public class SPLine {
	
	public Vector nodes=null;
	public Vector meshes=null;

	
	public SPLine(){
		
		nodes=new Vector();
		meshes=new Vector();
	}
	
	public void addPoint(Point4D p1,Vector vTexturePoints){


		
		Point4D p0=null;
		
		if(nodes.size()>0)
		{
			p0=((Point4D) nodes.lastElement()).clone();
			nodes.add(p1);
		}
		else{
			
			nodes.add(p1);
			return;
		}
		

		int index=0;

		double x0=p0.x;
		double y0=p0.y;
		
		double x1=p1.x;
		double y1=p1.y;
		
		Point3D d=new Point3D(x1-x0,y1-y0,0);
		d=d.calculateVersor();
		
		Point3D lpd=new Point3D(-d.y,d.x,0);
		Point3D rpd=new Point3D(d.y,-d.x,0);

		Point4D p2=new Point4D(x1+lpd.x*200,y1+lpd.y*200,0,LineData.GREEN_HEX,index);
		Point4D p3=new Point4D(x0+lpd.x*200,y0+lpd.y*200,0,LineData.GREEN_HEX,index);
		
		Vector points=new Vector();
		points.add(p0);
		points.add(p1);
		points.add(p2);
		points.add(p3);
		
		Point3D pt0=(Point3D) vTexturePoints.elementAt(0);
		Point3D pt1=(Point3D) vTexturePoints.elementAt(1);
		Point3D pt2=(Point3D) vTexturePoints.elementAt(2);
		Point3D pt3=(Point3D) vTexturePoints.elementAt(3);

		Vector polygonData=new Vector();
		LineData ld=new LineData();
		ld.addIndex(0,0,pt0.x,pt0.y);
		ld.addIndex(1,1,pt1.x,pt1.y);
		ld.addIndex(2,2,pt2.x,pt2.y);
		ld.addIndex(3,3,pt3.x,pt3.y);
		polygonData.add(ld);

		PolygonMesh mesh=new PolygonMesh(points,polygonData);
		mesh.setTexturePoints(vTexturePoints);
		meshes.add(mesh);



	}



	public Vector getMeshes() {
		return meshes;
	}

	public void setMeshes(Vector meshes) {
		this.meshes = meshes;
	}

}
