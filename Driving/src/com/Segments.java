package com;

public class Segments {
	
	private double xc;
	private double yc;
	private double zc;
	
	private double dx;
	private double dy;
	private double dz;
	
	public Segments(double xc,double dx, double yc,double dy, double zc,  
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
