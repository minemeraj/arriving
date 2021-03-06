package com.main;

import com.Point3D;

class LightSource {
	
	Point3D position;
	
	private Point3D xAxis=null;
	private Point3D yAxis=null;
	private Point3D zAxis=null;
	
	//light point axes
	
    double cos[][]=null;
	
	
	LightSource(Point3D position, Point3D yAxis) {
		super();
		this.position = position;
		this.yAxis = yAxis;
		cos=new double[3][3];
		
		calculateLightAxes();
	}
	public Point3D getPosition() {
		return position;
	}
	public void setPosition(Point3D position) {
		this.position = position;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
	
		LightSource ls=new LightSource();
		
		ls.position=position.clone();
		
		ls.xAxis=xAxis.clone();
		ls.yAxis=yAxis.clone();
		ls.zAxis=zAxis.clone();
		
		return ls;
		
	}

	public LightSource() {
		super();
	}
	private void calculateLightAxes(){

		
		xAxis=new Point3D(yAxis.y,-yAxis.x,0).calculateVersor();
		zAxis=new Point3D(-yAxis.z*yAxis.x,
								  -yAxis.z*yAxis.y,
								  yAxis.y*yAxis.y+yAxis.x*yAxis.x).calculateVersor();
		
		cos[0][0]=xAxis.x;
		cos[0][1]=xAxis.y;
		cos[0][2]=xAxis.z;
		
		cos[1][0]=yAxis.x;
		cos[1][1]=yAxis.y;
		cos[1][2]=yAxis.z;
		
		cos[2][0]=zAxis.x;
		cos[2][1]=zAxis.y;
		cos[2][2]=zAxis.z;
		
	}

}
