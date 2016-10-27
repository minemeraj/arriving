package com.editors.models;

import com.BPoint;

public abstract class AnimalModel extends MeshModel {

    protected int bx=10;
    protected int by=10;

    protected double dx = 0;
    protected double dy = 0;
    protected double dz = 0;

    protected double dxFront = 0;
    protected double dyFront = 0;
    protected double dzFront = 0;

    protected double dxRear = 0;
    protected double dyRear = 0;
    protected double dzRear = 0;

    protected double dxRoof;
    protected double dyRoof;
    protected double dzRoof;

    protected double x0=0;
    protected double y0=0;
    protected double z0=0;

    protected BPoint[][] head;
    protected BPoint[][] body;
    protected BPoint[][] leftArm;
    protected BPoint[][] leftLeg;
    protected BPoint[][] rightArm;
    protected BPoint[][] rightLeg;

    protected abstract void buildHead();
    protected abstract void buildBody();
    protected abstract void buildArms();
    protected abstract void buildLegs();
}
