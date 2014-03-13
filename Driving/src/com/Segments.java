package com;

public class Segments {
	
	public double xc;
	public double yc;
	public double zc;
	
	public double dx;
	public double dy;
	public double dz;
	
	public Segments(double xc, double yc, double zc, double dx, double dy,
			double dz) {

		this.xc = xc;
		this.yc = yc;
		this.zc = zc;
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}

	
	public double x(double alfa){
		
		return xc+alfa*dx;
		
	}
	
	public double y(double alfa){
		
		return yc+alfa*dy;
		
	}
	
	public double z(double alfa){
		
		return zc+alfa*dz;
		
	}
}
