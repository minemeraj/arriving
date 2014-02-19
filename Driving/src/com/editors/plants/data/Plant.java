package com.editors.plants.data;

import java.util.Vector;

import com.BPoint;
import com.CustomData;
import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.main.Renderer3D;

public class Plant extends CustomData{

	double trunk_lenght=0; 
	double trunk_radius=0;
	double foliage_length=0;
	double foliage_radius=0;
		
	int foliage_meridians=0;
	int foliage_parallels=0;
	int foliage_lobes=0;

	public Plant(){}

	public Plant(double trunk_lenght, double trunk_radius,
			double foliage_length, double foliage_radius,
			int foliage_meridians,int foliage_parallels,int foliage_lobes
			) {
		super();
		this.trunk_lenght = trunk_lenght;
		this.trunk_radius = trunk_radius;
		this.foliage_length = foliage_length;
		this.foliage_radius = foliage_radius;
		
		this.foliage_meridians = foliage_meridians;
		this.foliage_parallels = foliage_parallels;
		this.foliage_lobes = foliage_lobes;
	}


	public Object clone(){

		Plant grid=new Plant(trunk_lenght,trunk_radius,foliage_length,foliage_radius,
				foliage_meridians,foliage_parallels,foliage_lobes);
		return grid;

	}




	public String toString() {

		return "F="+trunk_lenght+","+trunk_radius+","+foliage_length+","+foliage_radius+","+
				foliage_meridians+","+foliage_parallels+","+foliage_lobes;
	}

	public static Plant buildPlant(String str) {

		String[] vals = str.split(",");

	
		double trunk_lenght =Double.parseDouble(vals[0]);
		double trunk_radius = Double.parseDouble(vals[1]);
		double foliage_length = Double.parseDouble(vals[2]);  
		double foliage_radius = Double.parseDouble(vals[3]); 
		int foliageMeridians=Integer.parseInt(vals[4]);
		int foliageParallels=Integer.parseInt(vals[5]);
		int foliageLobes=Integer.parseInt(vals[6]);

		Plant grid=new Plant(trunk_lenght,trunk_radius,foliage_length,foliage_radius,
				foliageMeridians,foliageParallels,foliageLobes
				);

		return grid;
	}



	
	public PolygonMesh buildMesh(){



		Vector points=new Vector();
		points.setSize(200);

		Vector polyData=new Vector();
		
		
		int n=0;


		//trunk:
		
		BPoint[] uTrunkpoints=new BPoint[foliage_meridians];
		BPoint[] bTrunkpoints=new BPoint[foliage_meridians];
		
		
		for (int i = 0; i < foliage_meridians; i++) {
			
			double x=trunk_radius*Math.cos(2*Math.PI/foliage_meridians*i);
			double y=trunk_radius*Math.sin(2*Math.PI/foliage_meridians*i);
			
			uTrunkpoints[i]=new BPoint(x,y,trunk_lenght,n++);
			points.setElementAt(uTrunkpoints[i],uTrunkpoints[i].getIndex());
			
		}


		LineData topLD=new LineData();
		
		for (int i = 0; i < foliage_meridians; i++) {
			
			topLD.addIndex(uTrunkpoints[i].getIndex());
			
		}
		topLD.setData(""+Renderer3D.CAR_TOP);
		polyData.add(topLD);
		
		for (int i = 0; i < foliage_meridians; i++) {
			
			double x=trunk_radius*Math.cos(2*Math.PI/foliage_meridians*i);
			double y=trunk_radius*Math.sin(2*Math.PI/foliage_meridians*i);
			
			bTrunkpoints[i]=new BPoint(x,y,0,n++);
			points.setElementAt(bTrunkpoints[i],bTrunkpoints[i].getIndex());
			
		}


		LineData bottomLD=new LineData();
		
		for (int i = foliage_meridians-1; i >=0; i--) {
			
			bottomLD.addIndex(bTrunkpoints[i].getIndex());
			
		}
		bottomLD.setData(""+Renderer3D.CAR_BOTTOM);
		polyData.add(bottomLD);
		
		
		
		for (int i = 0; i < foliage_meridians; i++) {
			
			LineData sideLD=new LineData();
			
			sideLD.addIndex(bTrunkpoints[i].getIndex());
			sideLD.addIndex(bTrunkpoints[(i+1)%foliage_meridians].getIndex());
			sideLD.addIndex(uTrunkpoints[(i+1)%foliage_meridians].getIndex());
			sideLD.addIndex(uTrunkpoints[i].getIndex());	
			sideLD.setData(""+getFace(sideLD,points));
			polyData.add(sideLD);
			
		}
		
		//foliage:
		
	
		
		BPoint[][] foliagePoints=new BPoint[foliage_parallels][foliage_meridians];
		
		double dzf=foliage_length/(foliage_parallels-1);
		
		for (int k = 0; k < foliage_parallels; k++) {
			
			double zf=dzf*k;
			
			foliagePoints[k]=new BPoint[foliage_meridians];
			
			for (int i = 0; i < foliage_meridians; i++) {
				
				double teta=2*Math.PI/foliage_meridians*i;
				
				double r=ff(zf)*rr(teta);
				
				double x=r*Math.cos(teta);
				double y=r*Math.sin(teta);
				
				foliagePoints[k][i]=new BPoint(x,y,trunk_lenght+zf,n++);
				points.setElementAt(foliagePoints[k][i],foliagePoints[k][i].getIndex());
				
			}
			
		}
			

		LineData topFoliage=new LineData();
		
		for (int i = 0; i < foliage_meridians; i++) {
			
			topFoliage.addIndex(foliagePoints[foliage_parallels-1][i].getIndex());
			
		}
		topFoliage.setData(""+Renderer3D.CAR_TOP);
		polyData.add(topFoliage);
		
		LineData bottomFoliage=new LineData();
		
		for (int i = foliage_meridians-1; i>=0; i--) {
			
			bottomFoliage.addIndex(foliagePoints[0][i].getIndex());
			
			
		}
		bottomFoliage.setData(""+getFace(bottomFoliage,points));
		polyData.add(bottomFoliage);
		
		for (int k = 0; k < foliage_parallels-1; k++) {
		
		
			for (int i = 0; i < foliage_meridians; i++) {
				
				LineData sideLD=new LineData();
				
				sideLD.addIndex(foliagePoints[k][i].getIndex());
				sideLD.addIndex(foliagePoints[k][(i+1)%foliage_meridians].getIndex());
				sideLD.addIndex(foliagePoints[k+1][(i+1)%foliage_meridians].getIndex());
				sideLD.addIndex(foliagePoints[k+1][i].getIndex());	
				sideLD.setData(""+getFace(sideLD,points));
				polyData.add(sideLD);
				
			}
	
		}
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;


	}
	
	
	


	public double getTrunk_lenght() {
		return trunk_lenght;
	}

	public void setTrunk_lenght(double trunk_lenght) {
		this.trunk_lenght = trunk_lenght; 
	}

	public double getTrunk_radius() {
		return trunk_radius;
	}

	public void setTrunk_radius(double trunk_radius) {
		this.trunk_radius = trunk_radius;
	}

	public double getFoliage_length() {
		return foliage_length;
	}

	public void setFoliage_length(double foliage_length) {
		this.foliage_length = foliage_length;
	}

	public double getFoliage_radius() {
		return foliage_radius;
	}

	public void setFoliage_radius(double foliage_radius) {
		this.foliage_radius = foliage_radius;
	}


	public int getFoliage_meridians() {
		return foliage_meridians;
	}

	public void setFoliage_meridians(int foliage_meridians) {
		this.foliage_meridians = foliage_meridians;
	}

	public int getFoliage_parallels() {
		return foliage_parallels;
	}

	public void setFoliage_parallels(int foliage_parallels) {
		this.foliage_parallels = foliage_parallels;
	}

	public int getFoliage_lobes() {
		return foliage_lobes;
	}

	public void setFoliage_lobes(int foliage_lobes) {
		this.foliage_lobes = foliage_lobes;
	}

	public double ff(double x){
		
		if(foliage_length==0)
			return 0;
		
		double a=-4*(foliage_radius-trunk_radius);
		double b=4*(foliage_radius-trunk_radius);
		double c=trunk_radius;
		
		double xr=x/foliage_length;

		
		return a*xr*xr+b*xr+c;
		
	}
	
	public double rr(double teta){
		
		double alfa=0.8;
		
		double rad=(alfa+(1.0-alfa)*Math.abs(Math.cos(foliage_lobes*teta/2.0)));
		
		return rad;
		
	}




}	