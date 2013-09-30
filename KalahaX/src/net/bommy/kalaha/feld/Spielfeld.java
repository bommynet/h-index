package net.bommy.kalaha.feld;

import java.util.Observable;

public class Spielfeld extends Observable implements Cloneable {
	public static final int SPIELER = 2;
	public static final int FELDER = 14;
	public static final int PROSPIELER = 7;
	
	protected Feld[] felder = new Feld[FELDER];
	protected int aktiverSpieler = 0;
	protected int letztesFeld = -1;
	
	public Spielfeld() {
		//jeden Spieler initialisieren
		for(int i=0; i<SPIELER; i++) {
			//jedes Feld des Spielers initialisieren
			for(int j=0; j<PROSPIELER; j++) {
				int id = j+(PROSPIELER*i);
				felder[id] = (j==PROSPIELER-1) ? new FeldKalaha(i) : new FeldKuhle(i,j);
				felder[id].setBohnen( ((j<PROSPIELER-1)? 3 : 0) );
			}
			
		}
	}
	
//### GETTER & SETTER #####################################
	public int getAktiverSpieler() {
		return aktiverSpieler;
	}
	
	public void setAktiverSpieler(int spieler) {
		this.aktiverSpieler = spieler;
		this.setChanged();
		this.notifyObservers();
	}
	
	public Feld getFeld(int feldId) {
		return felder[feldId];
	}
	
	public int getBohnenAufFeld(int feldId) {
		return felder[feldId].getBohnen();
	}
	
	public void setBohnenAufFeld(int feldId, int bohnen) {
		felder[feldId].setBohnen(bohnen);
		this.setChanged();
		this.notifyObservers();
	}
	
	public int getLetztesFeld() {
		return letztesFeld;
	}
	
//### FUNKTIONEN: Züge ####################################
	/**
	 * Prueft ob der gewaehlte Zug gueltig ist und fuehrt ihn
	 * anschließend aus.
	 * @param feldId ID des Feldes von dem aus gezogen werden soll
	 * @return -1-Zug ausgefuehrt | -2-Spieler ist nicht dran | -4-Feld ist leer |
	 *         0-Spieler1 gewinnt | 1-Spieler2 gewinnt | 2-Unentschieden
	 */
	public int zugAusfuehren(int feldId) {
		if( net.bommy.kalaha.Main.DEBUG ) {
			System.out.println("Zug "+ feldId + " Spieler["+ this.aktiverSpieler +"]");
			System.out.println("   vorher :  " + debugFeld(feldId));
		}
		if( istGewinnerBekannt() == -1 ) {
			//ist der Spieler überhaupt an der Reihe?
			if( !istSpielerAmZug( feldId ) ) return -2; 
			//sind auf dem Feld noch Bohnen?
			if( istFeldLeer(feldId) ) return -4;
			
			//Zug ausfuehren
			ziehen(feldId);
			
			//Bohnen fangen?
			if( wirdGefangen() ) fangeBohnen();
			
			//keine Zuege mehr moeglich?
			if( nochZuegeMoeglich() != -1 ) {
				//restliche Bohnen einsammeln
				sammelBohnen();
				return istGewinnerBekannt();
			}
			
			//Gewinner bekannt?
			if( istGewinnerBekannt() != -1 ) return istGewinnerBekannt();
			
			//Spieler nocheinmal dran?
			if( !(aktiverSpielerNochmalDran()) ) wechselSpieler();
			return -1;
		} else {
			//wenn Gewinner feststeht, gebe nur diesen zurück
			return istGewinnerBekannt();
		}
	}
	
	/**
	 * Zieht die Bohnen aus dem angegebenen Feld heraus und
	 * verteilt sie auf die folgenden Felder. Dabei wir in
	 * jedes folgende Feld eine Bohne abgelegt.<br/>
	 * <i>Diese Funktion loest ein setChanged() aus.</i>
	 * @param startfeld die FeldID, von der die Bohnen genommen
	 * und verteilt werden
	 * @return die Anzahl der verteilten Bohnen
	 */
	protected int ziehen(int startfeld)  {
		int bohnen = this._ziehen(startfeld);
		this.setChanged();
		this.notifyObservers();
		return bohnen;
	}
	
	/**
	 * Zieht die Bohnen aus dem angegebenen Feld heraus und
	 * verteilt sie auf die folgenden Felder. Dabei wir in
	 * jedes folgende Feld eine Bohne abgelegt.<br/>
	 * <i>Diese Funktion loest <b>kein</b> setChanged() aus.</i>
	 * @param startfeld die FeldID, von der die Bohnen genommen
	 * und verteilt werden
	 * @return die Anzahl der verteilten Bohnen
	 */
	protected int _ziehen(int startfeld)  {
		//Anzahl der Bohnen auf Startfeld lesen
		int bohnen = felder[startfeld].getBohnen();
		//neue Anzahl der Bohnen auf 0 setzen
		felder[startfeld].setBohnen(0);
		//genommene Bohnen auf Folgefelder verteilen
		for(int i=1; i<=bohnen; i++) {
			letztesFeld = (startfeld+i) % FELDER;
			//FeldID = (startfeld + i) mod anzahlFelder
			felder[letztesFeld].legeBohnenAb(1);
		}
		return bohnen;
	}
	
	/**
	 * Diese Funktionen nimmt einen Zug zurueck, welcher durch die
	 * beiden Parameter repraesentiert wird.<br/>
	 * <i>Diese Funktion loest ein setChanged() aus.</i>
	 * @param startfeld die ID des Feldes, wo der Zug begonnen hatte
	 * @param bohnen Anzahl der Bohnen die verteilt wurden
	 */
	protected void zurueck(int startfeld, int bohnen) {
		this._zurueck(startfeld, bohnen);
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Diese Funktionen nimmt einen Zug zurueck, welcher durch die
	 * beiden Parameter repraesentiert wird.<br/>
	 * <i>Diese Funktion loest <b>kein</b> setChanged() aus.</i>
	 * @param startfeld die ID des Feldes, wo der Zug begonnen hatte
	 * @param bohnen Anzahl der Bohnen die verteilt wurden
	 */
	protected void _zurueck(int startfeld, int bohnen) {
		//Anzahl der Bohnen auf Startfeld = bohnen setzen
		felder[startfeld].setBohnen(bohnen);
		//Bohnen von Folgefeldern entfernen
		for(int i=1; i<=bohnen; i++) {
			//FeldID = (startfeld + i) mod anzahlFelder
			felder[(startfeld+i) % FELDER].nimmBohnenWeg(1);
		}
		letztesFeld = -1;
	}
	
	/**
	 * 
	 */
	protected void wechselSpieler() {
		if( getAktiverSpieler() == 0 ) {
			setAktiverSpieler( 1 );
		} else {
			setAktiverSpieler( 0 );
		}
	}
	
	/**
	 * 
	 */
	protected void randomSpieler() {
		setAktiverSpieler( (Math.random()<0.5) ? 0 : 1 );
	}
	
	/**
	 * @return
	 */
	protected boolean aktiverSpielerNochmalDran() {
		//wenn letztes Feld == -1 dann immer false
		if(getLetztesFeld() == -1) return false;
		
		//wenn letztesFeld == Kalaha.aktiverSpieler, dann noch einmal
		if(getLetztesFeld() == (PROSPIELER-1 + (getAktiverSpieler() * 7)))
			return true;
		else
			return false;
	}
	
	/**
	 * @param feldId
	 * @return
	 */
	protected boolean istSpielerAmZug(int feldId) {
		return (felder[feldId].getSpieler() == getAktiverSpieler());
	}
	
	/**
	 * @param feldId
	 * @return
	 */
	protected boolean istFeldLeer(int feldId) {
		return !(felder[feldId].getBohnen() > 0);
	}
	
	/**
	 * 
	 * @return 0-Spieler1 keine Zuege mehr |
	 *         1-Spieler2 keine Zuege mehr |
	 *         -1-sonst
	 */
	protected int nochZuegeMoeglich() {
		//hat Spieler1 keine Bohnen mehr in den Kuhlen
		int tmp = 0;
		for(int i=0; i<PROSPIELER-1; i++) tmp += felder[i].getBohnen();
		if( tmp == 0 ) return 0;
		//hat Spieler2 keine Bohnen mehr in den Kuhlen
		tmp = 0;
		for(int i=PROSPIELER; i<FELDER-1; i++) tmp += felder[i].getBohnen();
		if( tmp == 0 ) return 1;
		return -1;
	}
	
	public boolean spielBeendet() {
		return ((nochZuegeMoeglich() != -1) && (istGewinnerBekannt() != -1));
	}
	
	/**
	 * Ermittelt wer der Gewinner ist.
	 * @return 0-Spieler1 | 1-Spieler2 | 2-Unentschieden | -1-sonst
	 */
	protected int istGewinnerBekannt() {
		//liegt ein Unentschieden vor?
		if( (felder[PROSPIELER-1].getBohnen() == felder[FELDER-1].getBohnen()) &&
		      nochZuegeMoeglich() != -1 ) return 2;
		//hat Spieler1 mehr als oder genau 19 Bohnen, dann ist Ende
		if( felder[PROSPIELER-1].getBohnen() >= 19 ) return 0;
		//hat Spieler2 mehr als oder genau 19 Bohnen, dann ist Ende
		if( felder[FELDER-1].getBohnen() >= 19 ) return 1;
		return -1;
	}
	
	/**
	 * @return
	 */
	protected boolean wirdGefangen() {
		if( (felder[getLetztesFeld()].getBohnen() == 1 ) &&
		    (felder[getLetztesFeld()].getSpieler() == getAktiverSpieler()) &&
		    !(getLetztesFeld() == 6) && !(getLetztesFeld() == 13)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 */
	protected void fangeBohnen() {
		//ID des letzten Feldes
		int aktivI = getLetztesFeld();
		//ID des gegenüber liegenden Feldes
		int gegnerI = (2*(PROSPIELER-1)) - aktivI;
		//letztes Feld leeren und in eigene Kalaha
		setBohnenAufFeld( (aktivI < Spielfeld.PROSPIELER) ? 6 : 13,
			getBohnenAufFeld( (aktivI < Spielfeld.PROSPIELER) ? 6 : 13 ) +
			getFeld(aktivI).getBohnen()
		);
		setBohnenAufFeld(aktivI, 0);
		//gegner Feld leeren und in eigene Kalaha
		setBohnenAufFeld( (aktivI < Spielfeld.PROSPIELER) ? 6 : 13,
				getBohnenAufFeld( (aktivI < Spielfeld.PROSPIELER) ? 6 : 13 ) +
				getFeld(gegnerI).getBohnen()
			);
		setBohnenAufFeld(gegnerI, 0);
	}
	
	/**
	 * 
	 */
	protected void sammelBohnen() {
		//Spieler ermitteln, bei dem noch Bohnen liegen
		int sp = 1 - nochZuegeMoeglich();
		//Speicher für restliche Bohnen
		int sammeln = 0;
		//restliche Bohnen einsammeln
		for(int i=(sp*PROSPIELER); i<(PROSPIELER+(sp*PROSPIELER)); i++) {
			sammeln += felder[i].getBohnen();
			felder[i].setBohnen(0);
		}
		//sp auf Kalaha zeigen lassen
		sp = (sp*PROSPIELER) + (PROSPIELER-1);
		//Bohnen zur Kalaha hinzufuegen 
		felder[sp].legeBohnenAb(sammeln);
		setChanged();
		notifyObservers();
	}
	
//### OVERRIDES ###########################################
	@Override
	public String toString() {
		String tmp = "";
		tmp += "Spieler(aktiv): " + aktiverSpieler + "\n";
		for(int i=0; i<felder.length; i++) tmp += felder[i].toString() + "\n";
		return tmp;
	}
	
	@Override
	public Spielfeld clone() {
		Spielfeld neu = new Spielfeld();
		for(int i=0; i<neu.felder.length; i++) {
			neu.setFeld( i, this.felder[i] );
		}
		neu.aktiverSpieler = this.aktiverSpieler;
		neu.letztesFeld = this.letztesFeld;
		return neu;
	}
	
	private void setFeld(int feldId, Feld feld) {
		this.felder[feldId].setBohnen( feld.getBohnen() );
		this.felder[feldId].setFeldId( feld.getFeldId() );
		this.felder[feldId].setSpieler( feld.getSpieler() );
		this.felder[feldId].setFeldIdSpieler( feld.getFeldIdSpieler( feld.getSpieler() ) );
	}
	
//### FUNKTIONEN: DEBUG ###################################
	public String debugFeld(int feldId) {
		return felder[feldId].toString();
	}
}
