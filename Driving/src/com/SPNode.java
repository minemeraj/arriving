package com;

public class SPNode extends Point4D{
	
	Point3D tangent=null;
	
	public SPNode(){}

	public SPNode(int x, int y, int i, String gREEN_HEX, int index) {
	
		super( x,  y,  i,  gREEN_HEX,  index);
	}

	public Point3D getTangent() {
		return tangent;
	}

	public void setTangent(Point3D tangent) {
		this.tangent = tangent;
	}

}
