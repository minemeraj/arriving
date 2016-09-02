package com.editors.others;

public class SplineTracer extends ImageTracer {
	
	public static void main(String[] args) {

		SplineTracer st=new SplineTracer();

	}

	
	public SplineTracer(){
		super();
		setTitle("Spline tracer");
		
		CLOSING_TAG = "</spline>";
		OPENING_TAG = "<spline>";
	}
}
