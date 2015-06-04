package com;

import java.util.Vector;

import com.editors.EditorData;
import com.editors.road.RoadEditor;
import com.main.CarFrame;
import com.main.Road;

public class SPLine {
	
	public Vector nodes=null;
	public Vector ribs=null;
	Vector vTexturePoints=null;

	
	public SPLine(Vector vTexturePoints){
		
		nodes=new Vector();
		ribs=new Vector();
		
		this.vTexturePoints=vTexturePoints;
	}
	
	public void addSPNode(SPNode nextNode){


		
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
		
        calculateRibs();
		
		


	}



	public void calculateRibs() {

		ribs=new Vector();
		


		for (int i = 0; i < nodes.size()-1; i++) {
			
			SPNode previousNode = (SPNode) nodes.elementAt(i); 
			SPNode nextNode = (SPNode) nodes.elementAt(i+1); 

			int index=0;

			double prevX=previousNode.x;
			double prevY=previousNode.y;
			double prevZ=previousNode.z;

			double nextX=nextNode.x;
			double nextY=nextNode.y;
			double nextZ=nextNode.z;

			//Point3D preTangent=previousNode.getTangent();

			Point3D NextTangent=new Point3D(nextX-prevX,nextY-prevY,0);
			
			double nodeDistance=Point3D.calculateNorm(NextTangent);
					
			NextTangent=NextTangent.calculateVersor();		
			nextNode.setTangent(NextTangent);

			Point3D lnextd=new Point3D(-NextTangent.y,NextTangent.x,0);
			Point3D rnextd=new Point3D(NextTangent.y,-NextTangent.x,0);

			/*
			Point3D lprevd=null;
			Point3D rprevd=null;

			if(preTangent==null){

				lprevd=new Point3D(-NextTangent.y,NextTangent.x,0);
				rprevd=new Point3D(NextTangent.y,-NextTangent.x,0);

			}else{


				lprevd=new Point3D(-preTangent.y,preTangent.x,0);
				rprevd=new Point3D(preTangent.y,-preTangent.x,0);
			}*/
			
			
			
			double wid=0.5*(EditorData.splinesMeshes[0].getDeltaX2()-
					EditorData.splinesMeshes[0].getDeltaX());
				
			double len=EditorData.splinesMeshes[0].getDeltaY2()-
					EditorData.splinesMeshes[0].getDeltaY();
			
			double dz=EditorData.splinesMeshes[0].getDeltaY();
			int n=(int) (nodeDistance/len)+1;
			
			for(int k=0;k<n;k++){
				
				double l=k*len/nodeDistance;
				//stretching last texture
				if(k==n-1)
					l=1.0;
				
				double x=(1.0-l)*prevX+l*nextX;
				double y=(1.0-l)*prevY+l*nextY;
				double z=(1.0-l)*prevZ+l*nextZ;

				if(l==0 && i>0){
	
					continue;
				}	
				
				Point4D[] rib=new Point4D[4];
				rib[0]=new Point4D(x+lnextd.x*wid,y+lnextd.y*wid,z,LineData.GREEN_HEX,index);
				rib[1]=new Point4D(x+rnextd.x*wid,y+rnextd.y*wid,z,LineData.GREEN_HEX,index);		
				rib[2]=new Point4D(x+rnextd.x*wid,y+rnextd.y*wid,z+dz,LineData.GREEN_HEX,index);	
				rib[3]=new Point4D(x+lnextd.x*wid,y+lnextd.y*wid,z+dz,LineData.GREEN_HEX,index);
				ribs.add(rib);	
	            //System.out.println(rib[0]+","+rib[1]+","+rib[2]+","+rib[3]+",");  

			
			}

		}

	}

	public Vector getMeshes() {

		Vector meshes=new Vector();

		int index=0;

		for (int i = 0; i < ribs.size()-1; i++) {
			Point4D[] prevRib= (Point4D[]) ribs.elementAt(i);
			Point4D[] nextRib= (Point4D[]) ribs.elementAt(i+1);
			
			Point4D p0=prevRib[3];
			Point4D p1=prevRib[2];
			Point4D p2=nextRib[2];
			Point4D p3=nextRib[3];
			

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
	
	public Vector get3DMeshes() {

		Vector meshes=new Vector();

		int index=0;

		for (int i = 0; i < ribs.size()-1; i++) {
			Point4D[] prevRib= (Point4D[]) ribs.elementAt(i);
			Point4D[] nextRib= (Point4D[]) ribs.elementAt(i+1);
			
			Point4D p0=prevRib[0];
			Point4D p1=prevRib[1];
			Point4D p2=nextRib[1];
			Point4D p3=nextRib[2];
			
			
			Point4D p4=prevRib[3];
			Point4D p5=prevRib[2];
			Point4D p6=nextRib[2];
			Point4D p7=nextRib[3];
			

			Vector points=new Vector();
			points.add(p0);
			points.add(p1);
			points.add(p2);
			points.add(p3);		
		
			
			points.add(p4);
			points.add(p5);
			points.add(p6);
			points.add(p7);


			Vector polygonData=EditorData.splinesMeshes[0].polygonData;
			
			PolygonMesh mesh=new PolygonMesh(points,polygonData);
			mesh.setTexturePoints(vTexturePoints);
			meshes.add(mesh);
		}

		return meshes;

	}

	public Vector getvTexturePoints() {
		return vTexturePoints;
	}

	public void setvTexturePoints(Vector vTexturePoints) {
		this.vTexturePoints = vTexturePoints;
	}
	
	
	public SPLine clone() throws CloneNotSupportedException {
		
		SPLine line=new SPLine(vTexturePoints);
		
		for (int i = 0; i < nodes.size(); i++) {
			SPNode sp = (SPNode) nodes.elementAt(i);
			line.addSPNode(sp.clone());
		}
		
		return  line;
	}



}
