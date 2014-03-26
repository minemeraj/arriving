package com.editors.weapons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.Polygon3D;
import com.PolygonMesh;
import com.editors.CustomJPanel;
import com.editors.weapons.data.Weapon;


public class WeaponsJPanel extends CustomJPanel{


	

	public void draw(Weapon weapon) {
		
		if(buffGraph==null)
			return;
		
		drawBasic();
		
		if(weapon!=null){ 
			
			
			buffGraph.setColor(Color.WHITE);
			drawWeaponData(weapon);
			
		}
		drawAxes();
		
		graph.drawImage(buf,0,0,null);
	}


	private void drawWeaponData(Weapon weapon) {
	
			
		PolygonMesh mesh = weapon.buildMesh();
				
		draw(mesh);
	
		
	}


}
