package com.editors.autocars;


class AutocarData {

	int car_width = 0;
	int car_length = 0;
	int car_type = 0;
	double x = 0;
	double y = 0;
	double u = 0;
	double nu = 0;
	double fi = 0;
	double steering = 0;
	
	static final int AUTOLINE_PARKED=-1;
	
	int autoline_index=AUTOLINE_PARKED;
	
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
