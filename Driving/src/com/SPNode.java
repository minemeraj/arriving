package com;

import com.editors.EditorData;

public class SPNode extends Point4D{
	
	Point3D tangent=null;
	
	public SPNode(){}
	
	PolygonMesh ring=null;
	PolygonMesh circle=null;

	public SPNode(int x, int y, int z, String gREEN_HEX, int index) {
	
		super( x,  y,  z,  gREEN_HEX,  index);
		update();
		
	}
	
	public void update(){
		
		ring=EditorData.getRing(x,y,z+10);	
		circle=EditorData.getCircle(x,y,z+10);	
		
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
