package net.bommy.kalaha.feld;

/**
 * @author Bommy
 *
 */
public class Feld {
	protected int feldId;
	protected int feldIdSpieler;
	protected int spieler;
	protected int bohnen;
	
//### KONSTRUKTOR #########################################
	/**
	 * Legt ein Standard-Feld an.
	 */
	public Feld() {
		this.feldId = 0;
		this.feldIdSpieler = 0;
		this.spieler = 0;
		this.bohnen = 0;
	}
	
	/**
	 * Legt ein leeres Feld f�r einen Spieler an.
	 * @param spieler 0=Spieler 1 und 1=Spieler 2
	 * @param feldId anzulegendes Feld mit der Nummer 0-6
	 */
	public Feld(int spieler, int feldId) {
		this.feldId = feldId + (spieler * 7);
		this.feldIdSpieler = feldId;
		this.spieler = spieler;
		this.bohnen = 0;
	}
	
	/**
	 * Legt ein Feld an, wobei alle Daten explizit vorgegeben werden.
	 * @param feldId die ID des Feldes (0-13)
	 * @param feldIdSpieler die ID des Feldes aus Spielersicht (0-6)
	 * @param spieler der Spieler selbst (0/1)
	 * @param bohnen Anzahl der Bohnen auf dem Feld
	 */
	public Feld(int feldId, int feldIdSpieler, int spieler, int bohnen) {
		this.feldId = feldId;
		this.feldIdSpieler = feldIdSpieler;
		this.spieler = spieler;
		this.bohnen = bohnen;
	}

//### GETTER & SETTER #####################################
	/**
	 * Liest die ID des Feldes aus.
	 * @return die FeldID
	 */
	public int getFeldId() {
		return feldId;
	}

	/**
	 * Setzt eine neue ID auf das Feld.
	 * @param feldId die neue FeldID
	 */
	public void setFeldId(int feldId) {
		this.feldId = feldId;
	}

	/**
	 * Liest die relative FeldID des Spielers aus.
	 * @param spieler Spieler mit dem verglichen wird
	 * @return die relative FeldID zum Spieler (0-6 wenn es
	 *         ein Feld des Spielers ist oder -1 wenn das
	 *         Feld nicht zum Spieler geh�rt
	 */
	public int getFeldIdSpieler(int spieler) {
		if(this.spieler == spieler) return feldIdSpieler;
		return -1;
	}

	/**
	 * Setzt die relative FeldID eines Spielers fest.
	 * @param feldIdSpieler die relative FeldID des Spielers (0-6)
	 */
	public void setFeldIdSpieler(int feldIdSpieler) {
		this.feldIdSpieler = feldIdSpieler;
	}

	/**
	 * Gibt an welchem Spieler das Feld geh�rt.
	 * @return Nummer des Spielers
	 */
	public int getSpieler() {
		return spieler;
	}

	/**
	 * Setzt den Besitzer des Feldes auf einen Spieler fest.
	 * @param spieler Nummer des Spielers
	 */
	public void setSpieler(int spieler) {
		this.spieler = spieler;
	}

	/**
	 * Gibt an wieviele Bohnen zur Zeit auf dem Feld liegen
	 * @return Anzahl der Bohnen
	 */
	public int getBohnen() {
		return bohnen;
	}

	/**
	 * Setzt eine neue Anzahl an Bohnen f�r das Feld
	 * @param bohnen neue Anzahl
	 */
	public void setBohnen(int bohnen) {
		this.bohnen = bohnen;
	}

//### FUNKTIONEN ##########################################
	/**
	 * Addiert neue Bohnen zu den vorhandenen dazu.
	 * @param bohnen Anzahl der neuen Bohnen
	 */
	public void legeBohnenAb(int bohnen) {
		this.bohnen += bohnen;
	}

	/**
	 * Subtrahiert Bohnen von den vorhandenen.
	 * @param bohnen Anzahl der zu entfernenden Bohnen
	 */
	public void nimmBohnenWeg(int bohnen) {
		this.bohnen -= bohnen;
	}
	
//### OVERRIDES ###########################################
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Feld[ID:"+feldId+"|sID:"+feldIdSpieler+"|Sp:"+spieler+"|B:"+bohnen+"]-"+this.getClass().getName();
	}
}
