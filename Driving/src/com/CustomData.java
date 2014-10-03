package com;

import java.util.Vector;

import com.editors.CustomEditor;
import com.main.Renderer3D;

public class CustomData {
	
	public Vector points=null;
	public Vector polyData=null;
	public int n=0;
	double pi=Math.PI;
	
	public void buildBox(double x , double y, double z,double x_side,double y_side, double z_side) {
		
		BPoint pLeg000=addBPoint(x,y,z);
		BPoint pLeg100=addBPoint(x+x_side,y,z);
		BPoint pLeg110=addBPoint(x+x_side,y+y_side,z);
		BPoint pLeg010=addBPoint(x,y+y_side,z);
		
	
		LineData bottom=buildLine(pLeg000,pLeg010,pLeg110,pLeg100,Renderer3D.CAR_BOTTOM);
		polyData.add(bottom);
		
		
		BPoint pLeg001=addBPoint(x,y,z+z_side);
		BPoint pLeg101=addBPoint(x+x_side,y,z+z_side);
		BPoint pLeg111=addBPoint(x+x_side,y+y_side,z+z_side);
		BPoint pLeg011=addBPoint(x,y+y_side,z+z_side);
	
		
		addLine(pLeg001,pLeg101,pLeg111,pLeg011,Renderer3D.CAR_TOP);
		
		addLine(pLeg000,pLeg001,pLeg011,pLeg010,Renderer3D.CAR_LEFT);

		addLine(pLeg010,pLeg011,pLeg111,pLeg110,Renderer3D.CAR_FRONT);

		addLine(pLeg110,pLeg111,pLeg101,pLeg100,Renderer3D.CAR_RIGHT);

		addLine(pLeg100,pLeg101,pLeg001,pLeg000,Renderer3D.CAR_BACK);


	}
	

	public void translatePoints(Vector points, double dx, double dy) { 

		for (int i = 0; i < points.size(); i++) {
			BPoint point = (BPoint) points.elementAt(i);
			if(point==null)
				continue;
			point.translate(dx,dy,0);
		}

	}
	
	
	public void translatePoints(Point3D[] points, double dx, double dy) { 

		for (int i = 0; i < points.length; i++) {
			Point3D point = points[i];
			if(point==null)
				continue;
			point.translate(dx,dy,0);
		}

	}
	
	public void rotateYZ(BPoint[][][] element, double y0, double z0, double teta) {
		
		double st=Math.sin(teta);
		double ct=Math.cos(teta);
		
		for (int i = 0; i < element.length; i++) {
			
			BPoint[][] ele1 = element[i];
			 
			for (int j = 0; j < ele1.length; j++) {
				
				BPoint[] ele2 = ele1[j];
				
				for (int k = 0; k < ele2.length; k++) {
					
					BPoint bp=ele2[k];
					
					double xx=bp.x;
					double yy=bp.y;
					double zz=bp.z;
					
					bp.x=xx;
					bp.y=(zz-z0)*st+(yy-y0)*ct+y0;
					bp.z=-(yy-y0)*st+(zz-z0)*ct+z0;
					
					
				}
				
			}
			
		}
		
		
	}
	
	public void rotateYZ(BPoint[] element, double y0, double z0, double teta) {

		double st=Math.sin(teta);
		double ct=Math.cos(teta);

		for (int i = 0; i < element.length; i++) {

			BPoint bp = element[i];

			double xx=bp.x;
			double yy=bp.y;
			double zz=bp.z;

			bp.x=xx;
			bp.y=(zz-z0)*st+(yy-y0)*ct+y0;
			bp.z=-(yy-y0)*st+(zz-z0)*ct+z0;


		}


	}
	
	public BPoint addBPoint(double x, double y, double z){
		
		BPoint point=new BPoint(x, y, z, n++);
		points.setElementAt(point,point.getIndex());
		return point;
		
	}

	public BPoint addBPoint( double x, double y, double z,Segments s){
		
		BPoint point=new BPoint(s.x(x), s.y(y), s.z(z), n++);
		points.setElementAt(point,point.getIndex());
		return point;
		
	}
	
	public LineData  addLine(BPoint p0, BPoint p1, BPoint p2,
			BPoint p3, int face) {

		LineData ld=new LineData();

		ld.addIndex(p0.getIndex());
		ld.addIndex(p1.getIndex());					
		ld.addIndex(p2.getIndex());
		if(p3!=null)
			ld.addIndex(p3.getIndex());	
		ld.setData(""+face);

		polyData.add(ld);
		
		return ld;
	}

	public LineData buildLine(BPoint p0, BPoint p1, BPoint p2,
			BPoint p3, int face) {

		LineData ld=new LineData();

		ld.addIndex(p0.getIndex());
		ld.addIndex(p1.getIndex());					
		ld.addIndex(p2.getIndex());
		if(p3!=null)
			ld.addIndex(p3.getIndex());	
		ld.setData(""+face);

		return ld;
	}
	


	
	public void addCylinder(double cyx0, double cyy0,double cyz0,
			double cylinder_radius,double cylinder_lenght,int barrel_meridians){
	
			BPoint[] uTrunkpoints=new BPoint[barrel_meridians];
			BPoint[] bTrunkpoints=new BPoint[barrel_meridians];
			
			for (int i = 0; i < barrel_meridians; i++) {
			
				
				double x=cyx0+cylinder_radius*Math.cos(2*Math.PI/barrel_meridians*i);
				double z=cyz0+cylinder_radius*Math.sin(2*Math.PI/barrel_meridians*i);
				
				uTrunkpoints[i]=addBPoint(x,cyy0+cylinder_lenght,z);
				
				
			}


			LineData topLD=new LineData();
			
			
				
				for (int i = barrel_meridians-1; i >=0; i--) {
				
				topLD.addIndex(uTrunkpoints[i].getIndex());
				
			}
			topLD.setData(""+Renderer3D.CAR_TOP);
			polyData.add(topLD);
			
			for (int i = 0; i < barrel_meridians; i++) {
				
				double x=cyx0+cylinder_radius*Math.cos(2*Math.PI/barrel_meridians*i);
				double z=cyz0+cylinder_radius*Math.sin(2*Math.PI/barrel_meridians*i);
				
				bTrunkpoints[i]=addBPoint(x,cyy0,z);
				
			}


			LineData bottomLD=new LineData();
			
			for (int i = 0; i < barrel_meridians; i++) {
				
				bottomLD.addIndex(bTrunkpoints[i].getIndex());
				
			}
			bottomLD.setData(""+Renderer3D.CAR_BOTTOM);
			polyData.add(bottomLD);
			
			
			
			for (int i = 0; i < barrel_meridians; i++) {
				
				LineData sideLD=new LineData();
				
				
				sideLD.addIndex(bTrunkpoints[(i+1)%barrel_meridians].getIndex());
				sideLD.addIndex(bTrunkpoints[i].getIndex());
				sideLD.addIndex(uTrunkpoints[i].getIndex());
				sideLD.addIndex(uTrunkpoints[(i+1)%barrel_meridians].getIndex());
				sideLD.setData(""+Renderer3D.getFace(sideLD,points));
				polyData.add(sideLD);
				
			}
			
	}

	public void addPrism(Prism prism){
		
	
		int size=prism.getSize();
		


			LineData topLD=new LineData();
			
			
			for (int i = 0; i < size; i++) {	
			
				
				topLD.addIndex(((BPoint) prism.upperBase[i]).getIndex());
				
			}
			topLD.setData(""+Renderer3D.CAR_TOP);
			polyData.add(topLD);
			
			LineData bottomLD=new LineData();
			
			
				
			for (int i = size-1; i >=0; i--) {	
				
				bottomLD.addIndex(((BPoint) prism.lowerBase[i]).getIndex());
				
			}
			bottomLD.setData(""+Renderer3D.CAR_BOTTOM);
			polyData.add(bottomLD);
			
			
			
			for (int i = 0; i < size; i++) {
				
				LineData sideLD=new LineData();
				
				
				
				sideLD.addIndex(((BPoint) prism.lowerBase[i]).getIndex());
				sideLD.addIndex(((BPoint) prism.lowerBase[(i+1)%size]).getIndex());
				sideLD.addIndex(((BPoint) prism.upperBase[(i+1)%size]).getIndex());
				sideLD.addIndex(((BPoint) prism.upperBase[i]).getIndex());				
				sideLD.setData(""+Renderer3D.getFace(sideLD,points));
				polyData.add(sideLD);
				
			}
			
	}
	
	public PolygonMesh buildMesh(double scale){
		
		PolygonMesh pm=buildMesh();
		if(pm!=null)
			rescaleMesh(pm,scale);
		
		return pm;
	}


	public PolygonMesh buildMesh() {
		return null;
	}
	
	public static void rescaleMesh(PolygonMesh mesh, double scale) {
		
		if(scale==1.0)
			return;
		
		Point3D[] points = mesh.points;
		
		for (int i = 0; i < points.length; i++) {
			
			points[i].x=Math.round(points[i].x *scale);
			points[i].y=Math.round(points[i].y *scale);
			points[i].z=Math.round(points[i].z *scale);
		}
		
	}
	
	public PolygonMesh addZRotatedMesh(int N_MERIDIANS,Point3D[] profile ) {

		
		points=new Vector();
		points.setSize(200);
		polyData=new Vector();
		
		n=0;

		double teta=(2*pi)/(N_MERIDIANS);


		int	N_PARALLELS=profile.length;


		BPoint[] aPoints=new BPoint[N_PARALLELS*N_MERIDIANS];

			for(int i=0;i<N_PARALLELS;i++){

				double radius=profile[i].y;

				for (int j = 0; j <N_MERIDIANS; j++) {


					double x= (radius*Math.cos(j*teta));
					double y= (radius*Math.sin(j*teta));
					double z= profile[i].x;

					aPoints[f(i,j,N_PARALLELS,N_MERIDIANS)]=
							addBPoint(x,y,z);

				}


			}


		Vector texture_points=new Vector();
		
		for(int i=0;i<N_PARALLELS;i++){

			for (int j = 0; j <N_MERIDIANS; j++) {

			}
			
		}	

		LineData lowerBase=new LineData();
		LineData upperBase=new LineData();

		for (int j = 0; j <N_MERIDIANS; j++) {

			upperBase.addIndex(aPoints[f(N_PARALLELS-1,(j+1)%N_MERIDIANS,N_PARALLELS,N_MERIDIANS)].getIndex());

			upperBase.setData(""+Renderer3D.getFace(upperBase,aPoints));

		}	

		for (int j = N_MERIDIANS-1; j >=0; j--) {

			lowerBase.addIndex(aPoints[f(0,(j+1)%N_MERIDIANS,N_PARALLELS,N_MERIDIANS)].getIndex());

			lowerBase.setData(""+Renderer3D.getFace(lowerBase,aPoints));
		}

		polyData.add(upperBase);

		for(int i=0;i<N_PARALLELS-1;i++){




			for (int j = 0; j <N_MERIDIANS; j++) {


				LineData ld=new LineData();


				ld.addIndex(aPoints[f(i,j,N_PARALLELS,N_MERIDIANS)].getIndex());
				ld.addIndex(aPoints[f(i,(j+1)%N_MERIDIANS,N_PARALLELS,N_MERIDIANS)].getIndex());
				ld.addIndex(aPoints[f(i+1,(j+1)%N_MERIDIANS,N_PARALLELS,N_MERIDIANS)].getIndex());
				ld.addIndex(aPoints[f(i+1,j,N_PARALLELS,N_MERIDIANS)].getIndex());

				ld.setData(""+Renderer3D.getFace(ld,aPoints));

				polyData.add(ld);

			}


		}

		polyData.add(lowerBase);

		PolygonMesh pm=new PolygonMesh(points,polyData);
		pm.setTexturePoints(texture_points);
		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}
	
	public int f(int i,int j,int nx,int ny){
		
		return i+j*nx;
	}
}
