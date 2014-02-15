package com.editors.plants.data;

import java.util.Vector;

import com.BPoint;
import com.CustomData;
import com.LineData;
import com.PolygonMesh;

public class Plant extends CustomData{

	double trunk_lenght=0; 
	double trunk_radius=0;
	double foliage_length=0;
	double foliage_radius=0;

	public Plant(){}

	public Plant(double trunk_lenght, double trunk_radius,
			double foliage_length, double foliage_radius) {
		super();
		this.trunk_lenght = trunk_lenght;
		this.trunk_radius = trunk_radius;
		this.foliage_length = foliage_length;
		this.foliage_radius = foliage_radius;
	}


	public Object clone(){

		Plant grid=new Plant(trunk_lenght,trunk_radius,foliage_length,foliage_radius);
		return grid;

	}




	public String toString() {

		return "F="+trunk_lenght+","+trunk_radius+","+foliage_length+","+foliage_radius;
	}

	public static Plant buildPlant(String str) {

		String[] vals = str.split(",");

	
		double trunk_lenght =Double.parseDouble(vals[0]);
		double trunk_radius = Double.parseDouble(vals[1]);
		double foliage_length = Double.parseDouble(vals[2]);  
		double foliage_radius = Double.parseDouble(vals[3]); 

		Plant grid=new Plant(trunk_lenght,trunk_radius,foliage_length,foliage_radius);

		return grid;
	}



	
	public PolygonMesh buildMesh(){



		Vector points=new Vector();
		points.setSize(200);

		Vector polyData=new Vector();
		
		int nBase=12;
		int levels_numer=10;
		
		int n=0;


		//trunk:
		
		BPoint[] uTrunkpoints=new BPoint[nBase];
		BPoint[] bTrunkpoints=new BPoint[nBase];
		
		
		for (int i = 0; i < nBase; i++) {
			
			double x=trunk_radius*Math.cos(2*Math.PI/nBase*i);
			double y=trunk_radius*Math.sin(2*Math.PI/nBase*i);
			
			uTrunkpoints[i]=new BPoint(x,y,trunk_lenght,n++);
			points.setElementAt(uTrunkpoints[i],uTrunkpoints[i].getIndex());
			
		}


		LineData topLD=new LineData();
		
		for (int i = 0; i < nBase; i++) {
			
			topLD.addIndex(uTrunkpoints[i].getIndex());
			
		}
		
		polyData.add(topLD);
		
		for (int i = 0; i < nBase; i++) {
			
			double x=trunk_radius*Math.cos(2*Math.PI/nBase*i);
			double y=trunk_radius*Math.sin(2*Math.PI/nBase*i);
			
			bTrunkpoints[i]=new BPoint(x,y,0,n++);
			points.setElementAt(bTrunkpoints[i],bTrunkpoints[i].getIndex());
			
		}


		LineData bottomLD=new LineData();
		
		for (int i = nBase-1; i >=0; i--) {
			
			bottomLD.addIndex(bTrunkpoints[i].getIndex());
			
		}
		
		polyData.add(bottomLD);
		
		
		
		for (int i = 0; i < nBase; i++) {
			
			LineData sideLD=new LineData();
			
			sideLD.addIndex(bTrunkpoints[i].getIndex());
			sideLD.addIndex(bTrunkpoints[(i+1)%nBase].getIndex());
			sideLD.addIndex(uTrunkpoints[(i+1)%nBase].getIndex());
			sideLD.addIndex(uTrunkpoints[i].getIndex());			
			polyData.add(sideLD);
			
		}
		
		//foliage:
		
	
		
		BPoint[][] foliagePoints=new BPoint[levels_numer][nBase];
		
		double dzf=foliage_length/(levels_numer-1);
		
		for (int k = 0; k < levels_numer; k++) {
			
			double zf=dzf*k;
			
			foliagePoints[k]=new BPoint[nBase];
			
			for (int i = 0; i < nBase; i++) {
				
				double r=ff(zf);
				
				double x=r*Math.cos(2*Math.PI/nBase*i);
				double y=r*Math.sin(2*Math.PI/nBase*i);
				
				foliagePoints[k][i]=new BPoint(x,y,trunk_lenght+zf,n++);
				points.setElementAt(foliagePoints[k][i],foliagePoints[k][i].getIndex());
				
			}
			
		}
			

		LineData topFoliage=new LineData();
		
		for (int i = 0; i < nBase; i++) {
			
			topFoliage.addIndex(foliagePoints[levels_numer-1][i].getIndex());
			
		}
		polyData.add(topFoliage);
		
		LineData bottomFoliage=new LineData();
		
		for (int i = nBase-1; i>=0; i--) {
			
			bottomFoliage.addIndex(foliagePoints[0][i].getIndex());
			
		}
		polyData.add(bottomFoliage);
		
		for (int k = 0; k < levels_numer-1; k++) {
		
		
			for (int i = 0; i < nBase; i++) {
				
				LineData sideLD=new LineData();
				
				sideLD.addIndex(foliagePoints[k][i].getIndex());
				sideLD.addIndex(foliagePoints[k][(i+1)%nBase].getIndex());
				sideLD.addIndex(foliagePoints[k+1][(i+1)%nBase].getIndex());
				sideLD.addIndex(foliagePoints[k+1][i].getIndex());			
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

	

	public double ff(double x){
		
		if(foliage_length==0)
			return 0;
		
		double a=-4*(foliage_radius-trunk_radius);
		double b=4*(foliage_radius-trunk_radius);
		double c=trunk_radius;
		
		double xr=x/foliage_length;
		
		return a*xr*xr+b*xr+c;
		
	}



}	