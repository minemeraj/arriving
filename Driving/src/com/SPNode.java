package com;

import com.editors.EditorData;

public class SPNode extends Point4D implements Cloneable{
	
	private Point3D tangent=null;

	private PolygonMesh ring=null;
	private PolygonMesh circle=null;	

	

	public SPNode(){
		

		
	}

	public SPNode(double x, double y, double z, String gREEN_HEX, int index) {
	
		super( x,  y,  z,  gREEN_HEX,  index);
		update();
		
	}
	
	@Override
	public SPNode clone() {
		
		SPNode sp=new SPNode(x,  y,  z,  "FFFFFF",  index);

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


}
