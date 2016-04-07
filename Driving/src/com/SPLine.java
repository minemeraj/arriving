package com;

import java.util.ArrayList;

import com.editors.EditorData;
import com.main.Road;

public class SPLine implements Cloneable{
	
	public SPNode root=null;
	public ArrayList<ArrayList<Rib>> ribs=null;
	private ArrayList<Point3D> vTexturePoints=null;
	
	private ArrayList<PolygonMesh> meshes3D=new ArrayList<PolygonMesh>();
	
	private int level=Road.ROAD_LEVEL;

	
	public SPLine(ArrayList<Point3D> vTexturePoints){
		
		ribs=new ArrayList<ArrayList<Rib>>();
		this.vTexturePoints=vTexturePoints;
	}
	
	public void addSPNode(SPNode nextNode){

		if(root==null){
			root=nextNode;
			return;
		}
		else
			root.addChildren(nextNode);
		
        calculateRibs();

	}

	public void calculateRibs() {
		
		ribs=new ArrayList<ArrayList<Rib>>();
		
		calculateTangents(root);
		
		calculateRibs(root);
	}

	private void calculateRibs(SPNode root) {
	
		       
		ArrayList<Rib> nodeRibs=new ArrayList<Rib>();
		ribs.add(nodeRibs);

		int sz=root.getChildCount();
		
		for (int i = -1; i < sz-1; i++) {

			SPNode previousNode = (SPNode) root.getChildAt(i);
			SPNode nextNode = (SPNode) root.getChildAt(i+1);

			double prevX=previousNode.x;
			double prevY=previousNode.y;
			double prevZ=previousNode.z;

			double nextX=nextNode.x;
			double nextY=nextNode.y;
			double nextZ=nextNode.z;
			
			
			Point3D NextTangent=nextNode.getTangent();

			Point3D vDistance=new Point3D(nextX-prevX,nextY-prevY,0);
			double nodeDistance=Point3D.calculateNorm(vDistance);

			Point3D lnextd=new Point3D(-NextTangent.y,NextTangent.x,0);
			Point3D rnextd=new Point3D(NextTangent.y,-NextTangent.x,0);
			
			
			
			Point3D preTangent=previousNode.getTangent();
			
			Point3D lprevd=null;
			Point3D rprevd=null;

			if(i==0 && preTangent!=null ){


				lprevd=new Point3D(-preTangent.y,preTangent.x,0);
				rprevd=new Point3D(preTangent.y,-preTangent.x,0);
				
			}else{				
				
				lprevd=new Point3D(-NextTangent.y,NextTangent.x,0);
				rprevd=new Point3D(NextTangent.y,-NextTangent.x,0);
				
			}
			
			int index=previousNode.getIndex();
			

			double wid=0.5*(EditorData.splinesMeshes[index].getDeltaX2()-
					EditorData.splinesMeshes[index].getDeltaX());

			double len=EditorData.splinesMeshes[index].getDeltaY2()-
					EditorData.splinesMeshes[index].getDeltaY();

			double dz=EditorData.splinesMeshes[index].getDeltaY();
			int n=(int) (nodeDistance/len)+1;

			if(n==1)
				n=2;

			for(int k=0;k<n;k++){

				double l=k*len/nodeDistance;
				//stretching last texture
				if(k==n-1){

					//do not repeat the last rib two time!
					if(i<sz-2){

						continue;
					}	

					l=1.0;

				}	

				double x=(1.0-l)*prevX+l*nextX;
				double y=(1.0-l)*prevY+l*nextY;
				double z=(1.0-l)*prevZ+l*nextZ;

				Rib rib=new Rib(4);
				rib.getPoints()[0]=new Point4D(x+lprevd.x*wid,y+lprevd.y*wid,z,LineData.GREEN_HEX,0);
				rib.getPoints()[1]=new Point4D(x+rprevd.x*wid,y+rprevd.y*wid,z,LineData.GREEN_HEX,0);		
				rib.getPoints()[2]=new Point4D(x+rnextd.x*wid,y+rnextd.y*wid,z+dz,LineData.GREEN_HEX,0);	
				rib.getPoints()[3]=new Point4D(x+lnextd.x*wid,y+lnextd.y*wid,z+dz,LineData.GREEN_HEX,0);
				rib.setIndex(previousNode.getIndex());
				nodeRibs.add(rib);	
				//System.out.println(rib[0]+","+rib[1]+","+rib[2]+","+rib[3]+",");  

				if(rib.getIndex()==Road.ROAD_INDEX0 || 
				   rib.getIndex()==Road.ROAD_INDEX1 )
						rib.translate(0,0,-dz);
			}

			if(nextNode.getChildCount()>0)
				calculateRibs(nextNode);
			
		}

	}

	private void calculateTangents(SPNode root) {
		
		int sz=root.getChildCount();
		
		for (int i = -1; i < sz-1; i++) {
			
			SPNode previousNode = (SPNode) root.getChildAt(i);
			SPNode nextNode = (SPNode) root.getChildAt(i+1);

			double prevX=previousNode.x;
			double prevY=previousNode.y;
			double prevZ=previousNode.z;

			double nextX=nextNode.x;
			double nextY=nextNode.y;
			double nextZ=nextNode.z;

			//Point3D preTangent=previousNode.getTangent();

			Point3D NextTangent=new Point3D(nextX-prevX,nextY-prevY,0);
			
			
					
			NextTangent=NextTangent.calculateVersor();		
			nextNode.setTangent(NextTangent);

			if(nextNode.getChildCount()>0)
				calculateTangents(nextNode);
		}
		
	}

	public ArrayList<PolygonMesh> getMeshes() {

		ArrayList<PolygonMesh> meshes=new ArrayList<PolygonMesh>();

		for (int j = 0; j < ribs.size(); j++) {

			ArrayList<Rib> nodeRibs=(ArrayList<Rib>)ribs.get(j);

			for (int i = 0;i < nodeRibs.size()-1; i++) {

				Rib prevRib= (Rib) nodeRibs.get(i);
				Rib nextRib= (Rib) nodeRibs.get(i+1);

				Point4D p0=prevRib.getPoints()[3];
				Point4D p1=prevRib.getPoints()[2];
				Point4D p2=nextRib.getPoints()[2];
				Point4D p3=nextRib.getPoints()[3];


				ArrayList<Point3D> points=new ArrayList<Point3D>();
				points.add(p0);
				points.add(p1);
				points.add(p2);
				points.add(p3);

				Point3D pt0=(Point3D) vTexturePoints.get(0);
				Point3D pt1=(Point3D) vTexturePoints.get(1);
				Point3D pt2=(Point3D) vTexturePoints.get(2);
				Point3D pt3=(Point3D) vTexturePoints.get(3);

				ArrayList<LineData> polygonData=new ArrayList<LineData>();
				LineData ld=new LineData();
				ld.addIndex(0,0,pt0.x,pt0.y);
				ld.addIndex(1,1,pt1.x,pt1.y);
				ld.addIndex(2,2,pt2.x,pt2.y);
				ld.addIndex(3,3,pt3.x,pt3.y);
				ld.setTexture_index(prevRib.getIndex());
				polygonData.add(ld);

				PolygonMesh mesh=new PolygonMesh(points,polygonData);
				mesh.setTexturePoints(vTexturePoints);
				mesh.setLevel(getLevel());
				meshes.add(mesh);
			}
		}
		return meshes;

	}
	
	public void calculate3DMeshes() {

		meshes3D=new ArrayList<PolygonMesh>();


		for (int j = 0;j < ribs.size(); j++) {

			ArrayList<Rib> nodeRibs=(ArrayList<Rib>)ribs.get(j);

			for (int i = 0;i < nodeRibs.size()-1; i++) {

				Rib prevRib= (Rib) nodeRibs.get(i);
				Rib nextRib= (Rib) nodeRibs.get(i+1);

				Point4D p0=prevRib.getPoints()[0];
				Point4D p1=prevRib.getPoints()[1];
				Point4D p2=nextRib.getPoints()[1];
				Point4D p3=nextRib.getPoints()[0];


				Point4D p4=prevRib.getPoints()[3];
				Point4D p5=prevRib.getPoints()[2];
				Point4D p6=nextRib.getPoints()[2];
				Point4D p7=nextRib.getPoints()[3];


				ArrayList<Point3D> points=new ArrayList<Point3D>();
				points.add(p0);
				points.add(p1);
				points.add(p2);
				points.add(p3);		


				points.add(p4);
				points.add(p5);
				points.add(p6);
				points.add(p7);


				ArrayList<LineData> polygonData= EditorData.splinesMeshes[prevRib.getIndex()].polygonData;
				ArrayList<LineData> nPolygonData=new ArrayList<LineData>();


				for (int kj = 0; kj < polygonData.size(); kj++) {

					LineData ld = ((LineData) polygonData.get(kj)).clone();

					ld.setTexture_index(prevRib.getIndex());
					nPolygonData.add(ld);
				}

				PolygonMesh mesh=new PolygonMesh(points,nPolygonData);
				mesh.setTexturePoints(vTexturePoints);
				mesh.setLevel(getLevel());
				meshes3D.add(mesh);
			}
		}
	}

	public ArrayList<Point3D> getvTexturePoints() {
		return vTexturePoints;
	}

	public SPLine clone() throws CloneNotSupportedException {
		
		SPLine line=new SPLine(vTexturePoints);
		
		line.setRoot(root.clone());
		line.calculateRibs();
		return  line;
	}


	public ArrayList<PolygonMesh> getMeshes3D() {
		return meshes3D;
	}

	public void setMeshes3D(ArrayList<PolygonMesh> meshes3d) {
		this.meshes3D = meshes3d;
	}

	public SPNode getRoot() {
		return root;
	}

	public void setRoot(SPNode root) {
		this.root = root;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}



}
