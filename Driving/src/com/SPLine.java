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
		
        calculateRibs();
		
		


	}



	public void calculateRibs() {

		ribs=new Vector();
		
		double wid=200;
		double len=200;

		for (int i = 0; i < nodes.size()-1; i++) {
			
			SPNode previousNode = (SPNode) nodes.elementAt(i); 
			SPNode nextNode = (SPNode) nodes.elementAt(i+1); 

			int index=3;

			double prevX=previousNode.x;
			double prevY=previousNode.y;

			double nextX=nextNode.x;
			double nextY=nextNode.y;

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
			
			int n=(int) (nodeDistance/200.0)+1;
			
			
			double dj=1.0/n;
			
			for(double l=0;l<=1;){
				
				double x=(1-l)*prevX+l*nextX;
				double y=(1-l)*prevY+l*nextY;

				if(l==0 && i>0){
					
					l=l+dj;
					continue;
				}	
				
				Point4D[] rib=new Point4D[2];
				rib[0]=new Point4D(x+rnextd.x*wid,y+rnextd.y*len,0,LineData.GREEN_HEX,index);		
				rib[1]=new Point4D(x+lnextd.x*wid,y+lnextd.y*len,0,LineData.GREEN_HEX,index);
				ribs.add(rib);	
				
				
				
				l=l+dj;

			
			}

		}

	}

	/*public Vector getMeshes() {

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

	}*/
	
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
			
			
			Point4D p4=prevRib[0];p4.translate(0,0,-20);
			Point4D p5=nextRib[0];p5.translate(0,0,-20);
			Point4D p6=nextRib[1];p6.translate(0,0,-20);
			Point4D p7=prevRib[1];p7.translate(0,0,-20);
			
			points.add(p4);
			points.add(p5);
			points.add(p6);
			points.add(p7);

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

	public Vector getvTexturePoints() {
		return vTexturePoints;
	}

	public void setvTexturePoints(Vector vTexturePoints) {
		this.vTexturePoints = vTexturePoints;
	}



}
