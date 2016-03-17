package com;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import com.editors.EditorData;

public class SPNode extends Point4D implements TreeNode{
	
	Point3D tangent=null;

	PolygonMesh ring=null;
	PolygonMesh circle=null;	

	Object object=null;
	Vector children=null;
	
	SPNode parent=null;

	public SPNode(){
		
		children=new Vector();
		
	}

	public SPNode(double x, double y, double z, String gREEN_HEX, int index) {
	
		super( x,  y,  z,  gREEN_HEX,  index);
		children=new Vector();
		update();
		
	}
	
	@Override
	public SPNode clone() {
		
		SPNode sp=new SPNode(x,  y,  z,  "FFFFFF",  index);

		for (int i = 0; i < getChildCount(); i++) {
			
			sp.addChildren(getChildAt(i).clone());
		}
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


	public SPNode(Object object) {
		this.object=object;
		children=new Vector();
	}

	public void addChildren(SPNode child0) {
		children.add(child0);
		child0.setParent(this);
		
	}


	public Enumeration children() {
		if(children==null)
			return null;
		return children.elements();
	}


	public boolean getAllowsChildren() {
		// TODO Auto-generated method stub
		return children!=null;
	}


	public SPNode getChildAt(int i) {
		if(children==null)
			return null;
		
		if(i==-1)
			return this;
		
		return (SPNode) children.elementAt(i);
	}

	
	public int getChildCount() {
		if(children==null)
			return 0;
		return children.size();
	}

	public SPNode getParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	
	public boolean isLeaf() {
		return children==null || children.size()==0;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	@Override
	public int getIndex(TreeNode node) {
		return 0;
	}

	public void setParent(SPNode parent) {
		this.parent = parent;
	}

	public void setChildAt(SPNode spnode, int k) {
		children.setElementAt(spnode, k);
		
	}


}
