package com.main;

import java.text.DecimalFormat;


/**
 * 
 * Reference book:Dinamica del veicolo (Vehicle Dynamics),by Massimo Guiggiani  
 * 
 * @author Francesco Piazza
 *
 */

class CarDynamics {
	

	/** STRUCTURAL VARIABLES **/
	private double a1=0;
	private double a2=0;	
	private double tau=1;
	private double chi=0;
	/** wheelbase **/
	private double l=0;
	/** Front track **/
	private double t1=0;
	/** Rear track **/
	private double t2=0;

	/** moment of inertia  **/
	private double Jz=0;
	/** inverse moment of inertia  **/
	private double i_Jz=0;
	
	/** vehicle mass  **/
	private double m=0;
	/** vehicle inverse mass  **/
	private double i_m=0;

	//DYNAMIC VARIABLES  
	
	/** front steering angle**/
	private double delta1=0;
	/** rear steering angle**/
	private double delta2=0;
	
	private double delta=0;
	
	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		
		this.delta = delta;
		delta1=tau*delta;
		delta2=chi*tau*delta;
	}

	/** longitudinal  velocity**/
	double u=0;
	/**  yaw  velocity**/
	private double r=0;
	/** lateral  velocity**/
	private double nu=0;
	
	/** front tire slip angle**/
	private double alfa1=0;
	/** rear tire slip angle**/
	private double alfa2=0;
	
	/** front side slip angles **/
	private double beta1=0;
	/** rear side slip angle **/
	private double beta2=0;
	
	/** front cornering stiffness **/
	private double C1=0;
	/** rear cornering stiffness **/
	private double C2=0;
	
	/** relaxation lenght **/
	private double d=0.25;
	
	/** front tractive force **/
	private double Fx1=0;
	/** rear tractive force **/
	private double Fx2=0;
	/** front lateral force**/
	private double Fy1;
	/** rear lateral force**/
	private double Fy2;
	/** torque force **/
	private double torque_force=0;
	/** braking force **/
	private double brakingForce=0;
		
    //DRAG PROPERTIES
	/** air density **/
	private double ro=0;
	private double S=0;
	private double Cx=0;
	
	/** Centripetal Force  **/
	double ay=0;
	
	/** COORDINATES INCREMENTS**/
	double dx=0;
	double dy=0;
	double dpsi=0;
	private double psi=0;
	
    private static DecimalFormat df=null;
	
	private byte Fx2Signum=0;
	private boolean isBraking=false;
	
	//MATH CONSTANTS
	
	private double PI_2=Math.PI*0.5;

	public static void main(String[] args) {
		
	    double dt=0.01;
	    df=new DecimalFormat("##.###");	
	  
	    CarDynamics cd=new CarDynamics(1000,1.2,1.4,1,1,1,0,1680,100000,100000);
		cd.setAerodynamics(1.3, 1.8, 0.35);
		cd.setForces(3000, 3000);
		  //linear motion
		//cd.setInitvalues(0, 1, 0, 0);
		 //steering 
		cd.setInitvalues(0.30, 1, 0, 0,0);
		cd.setFx2Versus(1);
		
		//System.out.println("\t"+df.format(0)+"\t "+cd);
		
		long time=System.currentTimeMillis();
		for(double t=dt;t<=200000;t+=dt){
			
		
			cd.move(dt);
			//System.out.println("\t"+df.format(t)+"\t "+cd);
			//System.out.println("\t"+df.format(t)+"\t "+cd.printForces());
		
		}
		
		System.out.println(System.currentTimeMillis()-time);
	}
	
	void move(double dt) {
		
		int sgn=(int) Math.signum(u);
		
		//calculate lateral forces:
		calculateFy1(dt);
		calculateFy2(dt);
        calculateAutomaticTorque(); 	
       		
		//formula for constant speed:
		//Fx2=m*(0-nu*r)+Fy1*delta1+0.5*ro*S*Cx*u*u;
              
        
		double du=i_m*(Fx1+Fx2-sgn*(Fy1*delta1+Fy2*delta2+0.5*ro*S*Cx*u*u))+nu*r;
        double dnu=i_m*(sgn*(Fy1+Fy2)+Fx1*delta1+Fx2*delta2)-u*r;
		double dr=i_Jz*(sgn*(Fy1*a1-Fy2*a2)+Fx1*a1*delta1-Fx2*a2*delta2);
		
	
		//System.out.println(printForces());
		//System.out.println(toString());
		//System.out.println(du+" "+dnu+" "+dr);
		//System.out.println(du*dt+" "+dnu*dt+" "+dr*dt);
		
		u+=du*dt;
		nu+=dnu*dt;
		r+=dr*dt;
		
		calculateCoordinatesIncrements(dt);
		
		
		//ay=dnu/dt+u*r;
		
	
	}
	
	

              
       

	private void calculateAutomaticTorque() {
		
		
	   if(!isBraking)	
		    Fx2=Fx2Signum*torque_force*Math.exp(-0.15*Math.abs(u));
	   else 
		    Fx2=-brakingForce*Math.signum(u);
		
	}
	
	
	/**
	 * REAR LATERAL FORCE
	 * 
	 * @param dt
	 */

	private void calculateFy2(double dt) {
		
		if(Math.abs(u-r*t1*0.5)==0)
			alfa2=delta2-PI_2*Math.signum(nu-r*a2);
		else
			alfa2=delta2-Math.atan((nu-r*a2)/(u-r*t2*0.5));
		//double dFy2= (C2*alfa2-Fy2)*u/d;
		//Fy2+=dt*dFy2;
		Fy2= C2*alfa2*f(u,dt);
		
	}
	
	
	
	/**
	 * FRONT LATERAL FORCE
	 * 
	 * @param dt
	 */
	private void calculateFy1(double dt) {
	
		if(Math.abs(u-r*t1*0.5)==0)
			alfa1=delta1-PI_2*Math.signum(nu+r*a1);
		else
			alfa1=delta1-Math.atan((nu+r*a1)/(u-r*t1*0.5));	
		
		//double dFy1= (C1*alfa1-Fy1)*u/d;
		//Fy1+=dt*dFy1;
		Fy1= C1*alfa1*f(u,dt);
	}
	/**
	 * relaxation formula
	 * @param u2
	 * @param dt 
	 * @return
	 */
	private double f(double u, double dt) {
		return (1.0-Math.exp(-Math.abs(u)*dt/d));
	}

	@Override
	public String toString() {
		
		String str=" nu= "+nu+ " ,r= "+r+ " ,u= "+u+" ,alfa1= "+alfa1+" ,alfa2= "+alfa2;
		
		return str;
	}
	
	@Deprecated
	public String printForces() {
		
		String str=" Fy1= "+Fy1+ " ,Fy2= "+Fy2+ " ,Fx2= "+Fx2;
		
		return str;
	}
	
	public CarDynamics(){}
	
	CarDynamics(double m,double a1, double a2,double t1,double t2, double tau, double chi ,double jz, double c1,
			double c2) {
		
		this.m=m;
		i_m=1.0/m;
		
		Jz = jz;
		i_Jz=1.0/Jz;
		
		this.a1 = a1;
		this.a2 = a2;
		l=a1+a2;
		this.tau = tau;
		this.chi = chi;
		C1 = c1;
		C2 = c2;
		this.t1=t1;
		this.t2=t2;
	}

	
	void setAerodynamics(double ro,double S,double Cx){
		
		this.ro=ro;
		this.S=S;
		this.Cx=Cx;
	}
	
	private void calculateCoordinatesIncrements(double dt){
		
		dpsi=r*dt;
		psi+=dpsi;
		double cosPsi=Math.cos(psi);
		double sinPsi=Math.sin(psi);
		
		dy=dt*(u*cosPsi-nu*sinPsi);
		dx=dt*(u*sinPsi+nu*cosPsi);
	}
	
	
	void setInitvalues(double delta, double u, double r, double nu,double psi) {
		
		this.delta = delta;
		this.u = u;
		this.r = r;
		this.nu = nu;
		this.psi = psi;
		
		delta1=tau*delta;
		delta2=chi*tau*delta;
		
	}
	
	/**
	 * FRONT TRACTIVE FORCE
	 * 
	 * @return
	 */
	public double getFx2() {
		return Fx2;
	}

	/**
	 * REAR TRACTIVE FORCE
	 * 
	 * @return
	 */
	public void setFx2(double fx2) {
		Fx2 = fx2;
	}

	public void setFx2Versus(int i) {
		
		Fx2Signum=(byte) i;
		
	}
	
	public boolean isIsbreaking() {
		return isBraking;
	}

	public void setIsbraking(boolean isbreaking) {
		this.isBraking = isbreaking;
	}
	
	public double getTorque_force() {
		return torque_force;
	}
	
	void setForces(double torque_force,double brakingForce){
		
		
		this.torque_force=torque_force;
		this.brakingForce=brakingForce;
		
	}

	public void setTorque_force(double torqueForce) {
		torque_force = torqueForce;
	}

	public double getBrakingForce() {
		return brakingForce;
	}

	public void setBrakingForce(double brakingForce) {
		this.brakingForce = brakingForce;
	}

	public void stop() {
		u=0;
		r=0;
		nu=0;
		
	}


}
