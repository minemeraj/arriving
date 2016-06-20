package com;

import com.editors.EditorData;

public class SPNode extends Point4D implements Cloneable{
	
	private Point3D tangent=null;

	private PolygonMesh ring=null;
	private PolygonMesh circle=null;	

	private double banking_angle=0;

	public SPNode(){
		

		
	}

	public SPNode(double x, double y, double z, double banking_angle,String gREEN_HEX, int index) {
	
		super( x,  y,  z,  gREEN_HEX,  index);
		setBanking_angle(banking_angle);
		update();
		
	}
	
	@Override
	public SPNode clone() {
		
		SPNode sp=new SPNode(x,  y,  z, getBanking_angle(), "FFFFFF",  index);

		sp.update();
		
		return sp;
	}
	
	public void update(){
		
		ring=EditorData.getRing(x,y,z+25);	
		circle=EditorData.getCircle(x,y,z+25);	
		
	}
	
	
	public Point3D getTangent() {
		return tangent;
	}

	public void setTangent(Point3D tangent) {
		this.tangent = tangent;
	}

	public PolygonMesh getRing() {
		return ring;
	}

	public void setRing(PolygonMesh circle) {
		this.ring = circle;
	}

	public PolygonMesh getCircle() {
		return circle;
	}

	public void setCircle(PolygonMesh circle) {
		this.circle = circle;
	}

	public double getBanking_angle() {
		return banking_angle;
	}

	public void setBanking_angle(double banking_angle) {
		this.banking_angle = banking_angle;
	}


}
