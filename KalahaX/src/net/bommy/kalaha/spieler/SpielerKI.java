package net.bommy.kalaha.spieler;

import net.bommy.kalaha.feld.Spielfeld;

public class SpielerKI {
	public SpielerKI() {}
	
	public int bestenZugSuchen(Spielfeld model) {
		int start = model.getAktiverSpieler() * 7;
		int ende = start + Spielfeld.PROSPIELER - 1;
		int ret = 0;
		for(int i=start; i < ende; i++) {
			ret = model.zugAusfuehren( i );
			if(ret >= -1)
				return i;
		}
		return (-1)*ret; //sollte nie erreicht werden
	}
}
