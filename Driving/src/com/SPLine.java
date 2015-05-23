package com;

import java.util.Vector;

public class SPLine {
	
	public Vector nodes=null;
	public Vector meshes=null;

	
	public SPLine(){
		
		nodes=new Vector();
		meshes=new Vector();
	}
	
	public void addPoint(Point4D p1){


		
		Point4D p0=null;
		
		if(nodes.size()>0)
		{
			p0=(Point4D) nodes.lastElement();
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

		Point4D p2=new Point4D(x1,y1+100,0,LineData.GREEN_HEX,index);
		Point4D p3=new Point4D(x0,y0+100,0,LineData.GREEN_HEX,index);


		Vector points=new Vector();
		points.add(p0);
		points.add(p1);
		points.add(p2);
		points.add(p3);

		Vector polygonData=new Vector();
		LineData ld=new LineData();
		ld.addIndex(0);
		ld.addIndex(1);
		ld.addIndex(2);
		ld.addIndex(3);
		polygonData.add(ld);

		PolygonMesh mesh=new PolygonMesh(points,polygonData);

		meshes.add(mesh);



	}

	public Vector getMeshes() {
		return meshes;
	}

	public void setMeshes(Vector meshes) {
		this.meshes = meshes;
	}

}
