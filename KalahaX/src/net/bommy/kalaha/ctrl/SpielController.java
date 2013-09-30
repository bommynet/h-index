package net.bommy.kalaha.ctrl;

import net.bommy.kalaha.feld.Spielfeld;
import net.bommy.kalaha.gui.GUISpielfeld;
import net.bommy.kalaha.spieler.Spieler;
import net.bommy.kalaha.spieler.SpielerDialog;

public class SpielController implements Runnable {
	private GUISpielfeld view;
	private Spielfeld model;
	
	private Spieler spieler[] = new Spieler[2];
	
	public SpielController() {}
	
	
//### FUNKTIONEN: Spielsteuerung ##########################
	/**
	 * Das Feld feldId im Model ansteuern und testen ob ein Zug auf
	 * diesem valide waere. Wenn ja, soll dieser Zug durchgefuehrt
	 * werden.
	 * @param feldId ID des geklickten Feldes
	 */
	public void clickedFeld(int feldId) {
		int ret = model.zugAusfuehren(feldId);
		//ret=1;//#######################################################################
		if( ret >= 0 ) {
			spielEndeDialog(ret);
		}
	}
	
	/**
	 * 
	 */
	private void neuesSpielStarten() {
		//wenn model schon existiert hat, alle Observer löschen
		if(model != null) {
			if( model.countObservers() > 0 ) model.deleteObservers();
		}
		//neues Model anlegen
		model = new Spielfeld();
		//View vorbereiten
		view.aktiverSpieler( model.getAktiverSpieler() );
		//Observer einfuegen
		model.addObserver(view);
		//view => Werte neu lesen
		view.update(model, null);
	}
	
	public void spielBeenden() {
		System.exit(0);
	}
	
	public void spielEndeDialog(int ret) {
		int wahl = view.showSpielVorbei( ret );
		if(wahl == 0)
			neuesSpielStarten();
		else if(wahl == 1)
			;//neue Spieler einstellen
		else //Spiel beenden
			spielBeenden();
	}
	
	private Spieler erstelleSpieler(String name, boolean ki) {
		Spieler tmp = new Spieler();
		tmp.setName(name);
		tmp.setKI(ki);
		return tmp;
	}
	
//### INIT + RUN ##########################################
	public void init() {
		//View initialisieren
		view = new GUISpielfeld(this);
		//Dialog zur Spielerabfrage
		SpielerDialog dlg = new SpielerDialog( this.view );
		spieler = dlg.createSpieler();
		//Model initialisieren
		//(model erstellen und Observer registrieren)
		neuesSpielStarten();
	}
	
	public void run() {
		//synchronized( model ) {
			while( !(model.spielBeendet()) ) {
				if(spieler[model.getAktiverSpieler()].getKI()) {
					spieler[model.getAktiverSpieler()].zugAusfuehren( model, this );
				}
			}
		//}
	}
}
