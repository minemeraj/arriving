package com.editors.weapons.data;

import java.util.Vector;

import com.BPoint;
import com.CustomData;
import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.main.Renderer3D;

public class Weapon extends CustomData{

	double barrel_lenght=0; 
	double barrel_radius=0;
		
	int barrel_meridians=0;
	int barrel_parallels=0;
	
	
	public static int WEAPON_TYPE_SHOTGUN=0;
	public int weapon_type=WEAPON_TYPE_SHOTGUN;
	
	double foliage_length=0;
	double foliage_radius=0;
	int foliage_lobes=0;

	public Weapon(){}

	public Weapon(double trunk_lenght, double trunk_radius,
			double foliage_length, double foliage_radius,
			int foliage_meridians,int foliage_parallels,int foliage_lobes
			) {
		super();
		this.barrel_lenght = trunk_lenght;
		this.barrel_radius = trunk_radius;
		this.barrel_meridians = foliage_meridians;
		this.barrel_parallels = foliage_parallels;
		
		this.foliage_length = foliage_length;
		this.foliage_radius = foliage_radius;
		this.foliage_lobes = foliage_lobes;
	}


	public Object clone(){

		Weapon grid=new Weapon(barrel_lenght,barrel_radius,foliage_length,foliage_radius,
				barrel_meridians,barrel_parallels,foliage_lobes);
		return grid;

	}




	public String toString() {

		return "F="+barrel_lenght+","+barrel_radius+","+foliage_length+","+foliage_radius+","+
				barrel_meridians+","+barrel_parallels+","+foliage_lobes;
	}

	public static Weapon buildWeapon(String str) {

		String[] vals = str.split(",");

	
		double trunk_lenght =Double.parseDouble(vals[0]);
		double trunk_radius = Double.parseDouble(vals[1]);
		double foliage_length = Double.parseDouble(vals[2]);  
		double foliage_radius = Double.parseDouble(vals[3]); 
		int foliageMeridians=Integer.parseInt(vals[4]);
		int foliageParallels=Integer.parseInt(vals[5]);
		int foliageLobes=Integer.parseInt(vals[6]);

		Weapon grid=new Weapon(trunk_lenght,trunk_radius,foliage_length,foliage_radius,
				foliageMeridians,foliageParallels,foliageLobes
				);

		return grid;
	}



	
	public PolygonMesh buildMesh(){



		points=new Vector();
		points.setSize(200);

		polyData=new Vector();
		
		
		n=0;

		//trunk:
		
		BPoint[] uTrunkpoints=new BPoint[barrel_meridians];
		BPoint[] bTrunkpoints=new BPoint[barrel_meridians];
		
		
		for (int i = 0; i < barrel_meridians; i++) {
			
			double x=barrel_radius*Math.cos(2*Math.PI/barrel_meridians*i);
			double y=barrel_radius*Math.sin(2*Math.PI/barrel_meridians*i);
			
			uTrunkpoints[i]=addBPoint(x,y,barrel_lenght);
			
			
		}


		LineData topLD=new LineData();
		
		for (int i = 0; i < barrel_meridians; i++) {
			
			topLD.addIndex(uTrunkpoints[i].getIndex());
			
		}
		topLD.setData(""+Renderer3D.CAR_TOP);
		polyData.add(topLD);
		
		for (int i = 0; i < barrel_meridians; i++) {
			
			double x=barrel_radius*Math.cos(2*Math.PI/barrel_meridians*i);
			double y=barrel_radius*Math.sin(2*Math.PI/barrel_meridians*i);
			
			bTrunkpoints[i]=addBPoint(x,y,0);
			
		}


		LineData bottomLD=new LineData();
		
		for (int i = barrel_meridians-1; i >=0; i--) {
			
			bottomLD.addIndex(bTrunkpoints[i].getIndex());
			
		}
		bottomLD.setData(""+Renderer3D.CAR_BOTTOM);
		polyData.add(bottomLD);
		
		
		
		for (int i = 0; i < barrel_meridians; i++) {
			
			LineData sideLD=new LineData();
			
			sideLD.addIndex(bTrunkpoints[i].getIndex());
			sideLD.addIndex(bTrunkpoints[(i+1)%barrel_meridians].getIndex());
			sideLD.addIndex(uTrunkpoints[(i+1)%barrel_meridians].getIndex());
			sideLD.addIndex(uTrunkpoints[i].getIndex());	
			sideLD.setData(""+getFace(sideLD,points));
			polyData.add(sideLD);
			
		}
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;


	}
	

	public double getBarrel_lenght() {
		return barrel_lenght;
	}

	public void setBarrel_lenght(double trunk_lenght) {
		this.barrel_lenght = trunk_lenght; 
	}

	public double getBarrel_radius() {
		return barrel_radius;
	}

	public void setBarrel_radius(double trunk_radius) {
		this.barrel_radius = trunk_radius;
	}

	public double getBarrel_length() {
		return foliage_length;
	}

	public void setBarrel_length(double foliage_length) {
		this.foliage_length = foliage_length;
	}

	public int getBarrel_meridians() {
		return barrel_meridians;
	}

	public void setBarrel_meridians(int foliage_meridians) {
		this.barrel_meridians = foliage_meridians;
	}

	public int getBarrel_parallels() {
		return barrel_parallels;
	}

	public void setBarrel_parallels(int foliage_parallels) {
		this.barrel_parallels = foliage_parallels;
	}
		

	public double getFoliage_radius() {
		return foliage_radius;
	}

	public void setFoliage_radius(double foliage_radius) {
		this.foliage_radius = foliage_radius;
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
		
		double a=-4*(foliage_radius-barrel_radius);
		double b=4*(foliage_radius-barrel_radius);
		double c=barrel_radius;
		
		double xr=x/foliage_length;

		
		return a*xr*xr+b*xr+c;
		
	}
	
	public double rr(double teta){
		
		double alfa=0.8;
		
		double rad=(alfa+(1.0-alfa)*Math.abs(Math.cos(foliage_lobes*teta/2.0)));
		
		return rad;
		
	}

	public int getWeapon_type() {
		return weapon_type;
	}

	public void setWeapon_type(int weapon_type) {
		this.weapon_type = weapon_type;
	}




}	