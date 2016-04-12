package com.main;
import com.CubicMesh;

/*
 * Created on 20/giu/09
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

class CarData {
	
	CubicMesh carMesh=null;
	


	public CubicMesh getCarMesh() {
		return carMesh;
	}
	
	public int getDeltaX() {
		return carMesh.deltaX;
	}
	public void setDeltaX(int deltaX) {
		carMesh.deltaX = deltaX;
	}
	public int getDeltaY() {
		return carMesh.deltaY;
	}
	public void setDeltaY(int deltaY) {
		carMesh.deltaY = deltaY;
	}
	public int getDeltaX2() {
		return carMesh.deltaX2;
	}
	public void setDeltaX2(int deltaX2) {
		carMesh.deltaX2 = deltaX2;
	}

	public int getDeltaY2() {
		return carMesh.deltaY2;
	}
	public void setDeltaY2(int deltaY2) {
		carMesh.deltaY2 = deltaY2;
	}

	public void setCarMesh(CubicMesh carMesh) {
		this.carMesh = carMesh;
	}
	
	
}
