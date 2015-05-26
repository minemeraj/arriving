package com;

import java.util.Vector;

public class SPLine {
	
	public Vector nodes=null;
	public Vector ribs=null;
	Vector vTexturePoints=null;

	
	public SPLine(Vector vTexturePoints){
		
		nodes=new Vector();
		ribs=new Vector();
		
		this.vTexturePoints=vTexturePoints;
	}
	
	public void addPoint(SPNode nextNode){


		
		SPNode previousNode=null;
		
		if(nodes.size()>0)
		{
			previousNode=(SPNode) nodes.lastElement();
			nodes.add(nextNode);
		}
		else{
			
			nodes.add(nextNode);
			return;
		}
		

		int index=3;

		double prevX=previousNode.x;
		double prevY=previousNode.y;
		
		double nextX=nextNode.x;
		double nextY=nextNode.y;
		
		Point3D preTangent=previousNode.getTangent();
		
		Point3D NextTangent=new Point3D(nextX-prevX,nextY-prevY,0);
		NextTangent=NextTangent.calculateVersor();		
		nextNode.setTangent(NextTangent);
		
		Point3D lnextd=new Point3D(-NextTangent.y,NextTangent.x,0);
		Point3D rnextd=new Point3D(NextTangent.y,-NextTangent.x,0);
		
		Point3D lprevd=null;
		Point3D rprevd=null;
		
		if(preTangent==null){
			
			lprevd=new Point3D(-NextTangent.y,NextTangent.x,0);
			rprevd=new Point3D(NextTangent.y,-NextTangent.x,0);
			
		}else{
			
			
			lprevd=new Point3D(-preTangent.y,preTangent.x,0);
			rprevd=new Point3D(preTangent.y,-preTangent.x,0);
		}
		
		Point4D[] prevRib=null;
		Point4D[] nextRib=null;
	
		if(ribs.size()==0){
			
			
			prevRib=new Point4D[2];
			prevRib[0]=new Point4D(prevX+rprevd.x*100,prevY+rprevd.y*100,0,LineData.GREEN_HEX,index);
			prevRib[1]=new Point4D(prevX+lprevd.x*100,prevY+lprevd.y*100,0,LineData.GREEN_HEX,index);
			
			ribs.add(prevRib);
			
		}else{
			
			prevRib=(Point4D[]) ribs.lastElement();
		
		}

		nextRib=new Point4D[2];
		nextRib[0]=new Point4D(nextX+rnextd.x*100,nextY+rnextd.y*100,0,LineData.GREEN_HEX,index);		
		nextRib[1]=new Point4D(nextX+lnextd.x*100,nextY+lnextd.y*100,0,LineData.GREEN_HEX,index);
		
		ribs.add(nextRib);
		


	}



	public Vector getMeshes() {

		Vector meshes=new Vector();

		int index=3;

		for (int i = 0; i < ribs.size()-1; i++) {
			Point4D[] prevRib= (Point4D[]) ribs.elementAt(i);
			Point4D[] nextRib= (Point4D[]) ribs.elementAt(i+1);
			
			Point4D p0=prevRib[0];
			Point4D p1=nextRib[0];
			Point4D p2=nextRib[1];
			Point4D p3=prevRib[1];

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
			ld.setTexture_index(index);
			polygonData.add(ld);

			PolygonMesh mesh=new PolygonMesh(points,polygonData);
			mesh.setTexturePoints(vTexturePoints);
			meshes.add(mesh);
		}

		return meshes;

	}



}
