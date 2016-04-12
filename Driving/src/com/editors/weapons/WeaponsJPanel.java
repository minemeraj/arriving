package com.editors.weapons;

import java.awt.Color;

import com.CustomData;
import com.PolygonMesh;
import com.editors.CustomJPanel;
import com.editors.weapons.data.Weapon;


class WeaponsJPanel extends CustomJPanel{


	
	@Override
	public void draw(CustomData data) {
		
		 Weapon weapon=(Weapon) data;
		
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
