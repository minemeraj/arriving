package com;

import com.editors.EditorShape;

public class SPNode extends Point4D{
	
	Point3D tangent=null;
	
	public SPNode(){}
	
	PolygonMesh circle=null;

	public SPNode(int x, int y, int i, String gREEN_HEX, int index) {
	
		super( x,  y,  i,  gREEN_HEX,  index);
		circle=EditorShape.getCircle(x,y,z+10);	
	}

	public Point3D getTangent() {
		return tangent;
	}

	public void setTangent(Point3D tangent) {
		this.tangent = tangent;
	}

	public PolygonMesh getCircle() {
		return circle;
	}

	public void setCircle(PolygonMesh circle) {
		this.circle = circle;
	}

}
