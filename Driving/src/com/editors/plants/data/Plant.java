package com.editors.plants.data;

import com.CustomData;
import com.PolygonMesh;

public class Plant extends CustomData{

	private double trunk_lenght=0; 
	private double trunk_upper_radius=0;
	private double trunk_lower_radius=0;
	
	private int trunk_parallels=0;
	private int trunk_meridians=0;	
	
	private double foliage_length=0;
	private double foliage_radius=0;
	private double foliage_barycenter=0;
		
	private int foliage_meridians=0;
	private int foliage_parallels=0;
	private int foliage_lobes=0;
	
	private double lobe_percentage_depth=1.0;
	
	public static int PLANT_TYPE_0=0;
	
	private int plant_type=PLANT_TYPE_0;

	public Plant(){}

	public Plant(
			int plant_type,
			double trunk_lenght, double trunk_upper_radius,double trunk_lower_radius,
			int trunk_meridians,int trunk_parallels,
			double foliage_length, double foliage_radius,double foliage_barycenter,			
			int foliage_meridians,int foliage_parallels,
			int foliage_lobes,
			double lobe_percentage_depth
			
			) {
		
		super();
		
		this.plant_type = plant_type; 
		this.trunk_lenght = trunk_lenght; 
		this.trunk_upper_radius = trunk_upper_radius;
		this.trunk_lower_radius = trunk_lower_radius;
		this.trunk_meridians = trunk_meridians;
		this.trunk_parallels = trunk_parallels;
		
		this.foliage_length = foliage_length;
		this.foliage_radius = foliage_radius;
		this.foliage_barycenter = foliage_barycenter;
		
		this.foliage_meridians = foliage_meridians;
		this.foliage_parallels = foliage_parallels;
		this.foliage_lobes = foliage_lobes;
		this.lobe_percentage_depth = lobe_percentage_depth;
	}


	public Object clone(){

		Plant grid=new Plant(
				plant_type,
				trunk_lenght,trunk_upper_radius,trunk_lower_radius,
				trunk_meridians,trunk_parallels,
				foliage_length,foliage_radius,foliage_barycenter,
				foliage_meridians,foliage_parallels,
				foliage_lobes,lobe_percentage_depth);
		return grid;

	}




	public String toString() {

		return "F="+plant_type+","+trunk_lenght+","+trunk_upper_radius+","+trunk_lower_radius+","
				+trunk_meridians+","+trunk_parallels+","
				+foliage_length+","+foliage_radius+","+foliage_barycenter+","+
				foliage_meridians+","+foliage_parallels+","+foliage_lobes
				+","+lobe_percentage_depth;
	}

	public static Plant buildPlant(String str) {

		String[] vals = str.split(",");

		int plant_type=Integer.parseInt(vals[0]);
		double trunk_lenght =Double.parseDouble(vals[1]);
		double trunk_upper_radius = Double.parseDouble(vals[2]);
		double trunk_lower_radius = Double.parseDouble(vals[3]);
		int trunk_meridians=Integer.parseInt(vals[4]);
		int trunk_parallels=Integer.parseInt(vals[5]);
		
		double foliage_length = Double.parseDouble(vals[6]);  
		double foliage_radius = Double.parseDouble(vals[7]); 
		double foliage_barycenter = Double.parseDouble(vals[8]); 
		
		int foliageMeridians=Integer.parseInt(vals[9]);
		int foliageParallels=Integer.parseInt(vals[10]);
		int foliageLobes=Integer.parseInt(vals[11]);
		double lobe_percentage_depth = Double.parseDouble(vals[12]);

		Plant grid=new Plant(
				plant_type,
				trunk_lenght,trunk_upper_radius,trunk_lower_radius,	
				trunk_meridians,trunk_parallels,
				foliage_length,foliage_radius,foliage_barycenter,
				foliageMeridians,foliageParallels,foliageLobes,
				lobe_percentage_depth
				);

		return grid;
	}

	public PolygonMesh buildMesh(){
		
		if(plant_type==PLANT_TYPE_0)
			return buildMeshPlant0();
		else
			return buildMeshPlant0();
	}
	
	private PolygonMesh buildMeshPlant0(){

		Plant0 plant0=new Plant0(
				plant_type,
				trunk_lenght,trunk_upper_radius,trunk_lower_radius,	
				trunk_meridians,trunk_parallels,
				foliage_length,foliage_radius,foliage_barycenter,
				foliage_meridians,foliage_parallels,foliage_lobes,
				lobe_percentage_depth
				);
		
		specificData=plant0;
		
		return plant0.getMesh();

		
	}
	
	
	


	public double getTrunk_lenght() {
		return trunk_lenght;
	}

	public void setTrunk_lenght(double trunk_lenght) {
		this.trunk_lenght = trunk_lenght; 
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


	public double getTrunk_upper_radius() {
		return trunk_upper_radius;
	}

	public void setTrunk_upper_radius(double trunk_upper_radius) {
		this.trunk_upper_radius = trunk_upper_radius;
	}

	public double getTrunk_lower_radius() {
		return trunk_lower_radius;
	}

	public void setTrunk_lower_radius(double trunk_lower_radius) {
		this.trunk_lower_radius = trunk_lower_radius;
	}

	public int getTrunk_parallels() {
		return trunk_parallels;
	}

	public void setTrunk_parallels(int trunk_parallels) {
		this.trunk_parallels = trunk_parallels;
	}

	public int getTrunk_meridians() {
		return trunk_meridians;
	}

	public void setTrunk_meridians(int trunk_meridians) {
		this.trunk_meridians = trunk_meridians;
	}

	public double getLobe_percentage_depth() {
		return lobe_percentage_depth;
	}

	public void setLobe_percentage_depth(double lobe_percentage_depth) {
		this.lobe_percentage_depth = lobe_percentage_depth;
	}


	public int getPlant_type() {
		return plant_type;
	}

	public void setPlant_type(int plant_type) {
		this.plant_type = plant_type;
	}


}	