package com;

import java.util.ArrayList;

import com.editors.EditorData;
import com.main.Road;

public class SPLine implements Cloneable{
	
	public ArrayList<SPNode> nodes=null;
	public ArrayList<ArrayList<Rib>> ribs=null;
	private ArrayList<Point3D> vTexturePoints=null;
	
	private ArrayList<PolygonMesh> meshes3D=new ArrayList<PolygonMesh>();
	
	private int level=Road.ROAD_LEVEL;
	
	public SPLine(ArrayList<Point3D> vTexturePoints){
		
		ribs=new ArrayList<ArrayList<Rib>>();
		this.vTexturePoints=vTexturePoints;
	}
	
	public void addSPNode(SPNode nextNode){

		if(nodes==null){
			nodes=new ArrayList<SPNode>();
			nodes.add(nextNode);
			return;
		}
		else
			nodes.add(nextNode);
		
        calculateRibs();

	}

	public void calculateRibs() {
		
		ribs=new ArrayList<ArrayList<Rib>>();
		
		calculateTangents(nodes);
		
		calculateRibs(nodes);
	}

	private void calculateRibs(ArrayList<SPNode> nodes) {
	
		       
		ArrayList<Rib> nodeRibs=new ArrayList<Rib>();
		ribs.add(nodeRibs);

		int sz=nodes.size();
		
		for (int i = 0; i < sz-1; i++) {

			SPNode previousNode = (SPNode) nodes.get(i);
			SPNode nextNode = (SPNode) nodes.get(i+1);
			
			int index=previousNode.getIndex();
			

			double wid=0.5*(EditorData.splinesMeshes[index].getDeltaX2()-
					EditorData.splinesMeshes[index].getDeltaX());

			double len=EditorData.splinesMeshes[index].getDeltaY2()-
					EditorData.splinesMeshes[index].getDeltaY();

			double dz=EditorData.splinesMeshes[index].getDeltaY();
			
			String description=EditorData.splinesMeshes[index].getDescription();

			double prevX=previousNode.x;
			double prevY=previousNode.y;
			double prevZ=previousNode.z;
			double prevBanking=previousNode.getBanking_angle();

			double nextX=nextNode.x;
			double nextY=nextNode.y;
			double nextZ=nextNode.z;
			double nextBanking=nextNode.getBanking_angle();
			
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
			
			calculateRingCenter(lnextd,rnextd,wid,dz,nextNode);
			calculateRingCenter(lprevd,rprevd,wid,dz,previousNode);
			
			int n=(int) (nodeDistance/len)+1;
			if(n==1)
				n=2;
			//stretching the normal length
			len=nodeDistance/(n-1);

			for(int k=0;k<n;k++){

				double l=k*len/nodeDistance;
				//stretching last texture
				if(k==n-1){

					//do not repeat the last rib two time!
					if(i<sz-2){

						continue;
					}	

					//l=1.0;

				}	

				double x=(1.0-l)*prevX+l*nextX;
				double y=(1.0-l)*prevY+l*nextY;
				double z=(1.0-l)*prevZ+l*nextZ;
				double banking=(1.0-l)*prevBanking+l*nextBanking;
				
				double dzl=dz;
				double dzr=dz;
				if(banking>0)
					dzr+=2.0*wid*Math.tan(banking);
				else if(banking<0)
					dzl+=2.0*wid*Math.tan(-banking);
				
				Rib rib=new Rib(4);
				rib.getPoints()[0]=new Point4D(x+lprevd.x*wid,y+lprevd.y*wid,z,LineData.GREEN_HEX,0);
				rib.getPoints()[1]=new Point4D(x+rprevd.x*wid,y+rprevd.y*wid,z,LineData.GREEN_HEX,0);				
				rib.getPoints()[2]=new Point4D(x+rnextd.x*wid,y+rnextd.y*wid,z+dzr,LineData.GREEN_HEX,0);	
				rib.getPoints()[3]=new Point4D(x+lnextd.x*wid,y+lnextd.y*wid,z+dzl,LineData.GREEN_HEX,0);
				rib.setIndex(previousNode.getIndex());
				rib.setLength(len);
				nodeRibs.add(rib);	
				//System.out.println(rib[0]+","+rib[1]+","+rib[2]+","+rib[3]+",");  

				//not all the splines are road spline, they' don't need to be translated!
				if(description!=null && description.toLowerCase().indexOf("road")>=0)
					rib.translate(0,0,-dz);
			}
			
		}

	}

	private void calculateRingCenter(Point3D lDirection, Point3D rDirection, double wid, double dz, SPNode node) {
		
		double x=node.getX();
		double y=node.getY();
		double z=node.getZ();
		
		double banking=node.getBanking_angle();
		
		double dzl=dz;
		double dzr=dz;
		if(banking>0)
			dzr+=wid*Math.tan(banking);
		else if(banking<0)
			dzl+=wid*Math.tan(-banking);		
		
		Point3D p0=new Point3D(x+rDirection.x*wid,y+rDirection.y*wid,z+dzr);	
		Point3D p1=new Point3D(x+lDirection.x*wid,y+lDirection.y*wid,z+dzl);
		
		Point3D ringP=new Point3D((p0.getX()+p1.getX())*0.5,(p0.getY()+p1.getY())*0.5,(p0.getZ()+p1.getZ())*0.5);
		
		node.setRingCenter(ringP);
		
		node.update();
	}


	private void calculateTangents(ArrayList<SPNode> nodes) {
		
		int sz=nodes.size();
		
		for (int i = 0; i < sz-1; i++) {
			
			SPNode previousNode = (SPNode) nodes.get(i);
			SPNode nextNode = (SPNode) nodes.get(i+1);

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


				ArrayList<Point3D> aPoints=new ArrayList<Point3D>();
				aPoints.add(p0);
				aPoints.add(p1);
				aPoints.add(p2);
				aPoints.add(p3);

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

				PolygonMesh mesh=new PolygonMesh(aPoints,polygonData);
				mesh.setTexturePoints(vTexturePoints);
				mesh.setLevel(getLevel());
				meshes.add(mesh);
			}
		}
		return meshes;

	}
	
	public void oldCalculate3DMeshes() {

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


				ArrayList<Point3D> aPoints=new ArrayList<Point3D>();
				aPoints.add(p0);
				aPoints.add(p1);
				aPoints.add(p2);
				aPoints.add(p3);		


				aPoints.add(p4);
				aPoints.add(p5);
				aPoints.add(p6);
				aPoints.add(p7);


				ArrayList<LineData> polygonData= EditorData.splinesMeshes[prevRib.getIndex()].polygonData;
				ArrayList<LineData> nPolygonData=new ArrayList<LineData>();


				for (int kj = 0; kj < polygonData.size(); kj++) {

					LineData ld = ((LineData) polygonData.get(kj)).clone();

					ld.setTexture_index(prevRib.getIndex());
					nPolygonData.add(ld);
				}

				PolygonMesh mesh=new PolygonMesh(aPoints,nPolygonData);
				mesh.setTexturePoints(vTexturePoints);
				mesh.setLevel(getLevel());
				meshes3D.add(mesh);
			}
		}
	}
	
	/**
	 * 
	 * Using FFD algorithm =Free Form Deformation
	 * 
	 */	
	public void calculate3DMeshes() {

		meshes3D=new ArrayList<PolygonMesh>();

		/*Array to reuse, so out of the cycle*/
		Point3D[][][] nonNormalizedControlPoints=new Point3D[2][2][2];
		
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

				//Box points=  reference points
					
				
				nonNormalizedControlPoints[0][0][0]=p0;
				nonNormalizedControlPoints[1][0][0]=p1;
				nonNormalizedControlPoints[1][1][0]=p2;
				nonNormalizedControlPoints[0][1][0]=p3;
				nonNormalizedControlPoints[0][0][1]=p4;
				nonNormalizedControlPoints[1][0][1]=p5;
				nonNormalizedControlPoints[1][1][1]=p6;
				nonNormalizedControlPoints[0][1][1]=p7;
				
				
				//original points, to deform
				CubicMesh clonedMesh = EditorData.splinesMeshes[prevRib.getIndex()].clone();
				double[] clonedXpoints = clonedMesh.xpoints;
				double[] clonedYpoints = clonedMesh.ypoints;
				double[] clonedZpoints = clonedMesh.zpoints;
				
				int l=1;
				int m=1;
				int n=1;
				double deltax=clonedMesh.getXLen();
				double deltay=clonedMesh.getYLen();
				double deltaz=clonedMesh.getZLen();
				
				Point3D origin=p0;
				
				FreeFormDeformation ffd=new FreeFormDeformation(
						l, m, n, 
						deltax, deltay, deltaz,
						nonNormalizedControlPoints, 
						origin);
				
				for (int k = 0; k < clonedXpoints.length; k++) {

					Point3D pOut=ffd.getDeformedPoint(clonedXpoints[k]+origin.x,clonedYpoints[k]+origin.y,clonedZpoints[k]+origin.z);		
					
					clonedXpoints[k]=pOut.getX();
					clonedYpoints[k]=pOut.getY();
					clonedZpoints[k]=pOut.getZ();
				}
				
				
				
				//Original polygon data
				ArrayList<LineData> polygonData= EditorData.splinesMeshes[prevRib.getIndex()].polygonData;
				ArrayList<LineData> nPolygonData=new ArrayList<LineData>();


				for (int kj = 0; kj < polygonData.size(); kj++) {

					LineData ld = ((LineData) polygonData.get(kj)).clone();

					ld.setTexture_index(prevRib.getIndex());
					nPolygonData.add(ld);
				}

				PolygonMesh mesh=new PolygonMesh(clonedXpoints,clonedYpoints,clonedZpoints,nPolygonData);
				mesh.setTexturePoints(vTexturePoints);
				mesh.setLevel(getLevel());
				meshes3D.add(mesh);
			}
		}
	}
	
	public void update(){
		
		calculateRibs();
		calculate3DMeshes();
	}

	public ArrayList<Point3D> getvTexturePoints() {
		return vTexturePoints;
	}

	public SPLine clone() throws CloneNotSupportedException {
		
		SPLine line=new SPLine(vTexturePoints);
		
		for (int i = 0; i < nodes.size(); i++) {

			SPNode node = (SPNode) nodes.get(i);
			line.addSPNode(node);
		}
		
		
		line.calculateRibs();
		return  line;
	}


	public ArrayList<PolygonMesh> getMeshes3D() {
		return meshes3D;
	}

	public void setMeshes3D(ArrayList<PolygonMesh> meshes3d) {
		this.meshes3D = meshes3d;
	}


	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public ArrayList<SPNode> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<SPNode> nodes) {
		this.nodes = nodes;
	}



}
