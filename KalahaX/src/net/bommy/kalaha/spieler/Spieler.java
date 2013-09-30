package net.bommy.kalaha.spieler;

import net.bommy.kalaha.ctrl.SpielController;
import net.bommy.kalaha.feld.Spielfeld;

public class Spieler {
	private String name = "";
	private SpielerKI ki = null;
	
	public Spieler() {
		this.setName("Noname");
		this.setKI(false);
	}
	
	public Spieler(String name, boolean ki) {
		this.setName(name);
		this.setKI(ki);
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gibt an, ob der Spieler ein Computer ist.
	 * @return true-Computer | false-Mensch
	 */
	public boolean getKI() {
		if( ki == null ) return false;
		return true;
	}
	
	public void setKI(boolean istComputer) {
		if(istComputer)
			ki = new SpielerKI();
		else
			ki = null;
	}

	public void zugAusfuehren(Spielfeld model, SpielController ctrl) {
		if( getKI() ) {
			int res = ki.bestenZugSuchen(model.clone());//einfach den nächsten, möglichen Zug ausführen
			
			if(res < 0) {
				System.out.println("Fehler bei KI Zug Berechnung: Ergebnis = "+res);
				System.out.println("Spiel bereits beendet: "+model.spielBeendet());
			} else {
				int ret = model.zugAusfuehren(res);
				if( ret >= 0 ) {
					ctrl.spielEndeDialog(ret);
				}
			}
		}
	}
}
