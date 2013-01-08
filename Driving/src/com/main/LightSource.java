package com.main;

import com.Point3D;

public class LightSource {
	
	public Point3D position;
	
	public Point3D xAxis=null;
	public Point3D yAxis=null;
	public Point3D zAxis=null;
	
	//light point axes
	
    double cos[][]=null;
	
	
	public LightSource(Point3D position, Point3D yAxis) {
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
	public void calculateLightAxes(){

		
		xAxis=new Point3D(yAxis.y,-yAxis.x,0).calculateVersor();
		zAxis=new Point3D(-yAxis.z*yAxis.x,
								  -yAxis.z*yAxis.y,
								  yAxis.y*yAxis.y+yAxis.x*yAxis.x).calculateVersor();
		
		/*System.out.println("x:"+xAxis);
		System.out.println("y:"+yAxis);
		System.out.println("z:"+zAxis);*/
		
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
