package com.editors.plants.data;

public class Plant1 extends Plant0{
	
	public Plant1(){}

	Plant1(
			int plant_type,
			double trunk_lenght, double trunk_upper_radius,double trunk_lower_radius,
			int trunk_meridians,int trunk_parallels,
			double foliage_length, double foliage_radius,double foliage_barycenter,			
			int foliage_meridians,int foliage_parallels,
			int foliage_lobes,
			double lobe_percentage_depth

			) {

		super(plant_type,
				trunk_lenght,
				trunk_upper_radius,
				trunk_lower_radius,
				trunk_meridians,
				trunk_parallels,
				foliage_length,
				foliage_radius,
				foliage_barycenter,
				foliage_meridians,
				foliage_parallels,
				foliage_lobes,
				lobe_percentage_depth
				);

	}
	
	@Override
	protected double ff(double x) {

		if(foliage_length==0)
			return 0;

		double xr=x/foliage_length;
		double xf=foliage_barycenter/foliage_length;


		if(xr>xf){

			double b=3.0*(foliage_radius-trunk_upper_radius)*(xf+1)/(xf*xf*xf-3*xf*xf-3*xf+5);
			double a=-2*b/(3*(xf+1));
			double c=-(3*a+2*b);
			double d=trunk_upper_radius-a-b-c;
			return a*xr*xr*xr+b*xr*xr+c*xr+d;

		}else{


			double a=(trunk_upper_radius-foliage_radius)/(xf*xf);
			double b=-2*(trunk_upper_radius-foliage_radius)/xf;
			double c=trunk_upper_radius;

			return a*xr*xr+b*xr+c;

		}



	}
}
