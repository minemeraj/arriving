package com.editors.autocars;


public class AutocarData {

	public int car_width = 0;
	public int car_length = 0;
	public int car_type = 0;
	public double x = 0;
	public double y = 0;
	public double u = 0;
	public double nu = 0;
	public double fi = 0;
	public double steering = 0;
	
	public static final int AUTOLINE_PARKED=-1;
	
	public int autoline_index=AUTOLINE_PARKED;
	
	@Override
	public AutocarData clone(){
		
		AutocarData ac=new AutocarData();
		
		ac.car_width=car_width;
		ac.car_length=car_length;
		ac.car_type=car_type;
		ac.x=x;
		ac.y=y;
		ac.u=u;
		ac.nu=nu;
		ac.fi=fi;
		ac.steering=steering;
		
		ac.autoline_index=autoline_index;
		
		return ac;
		
	}

}
