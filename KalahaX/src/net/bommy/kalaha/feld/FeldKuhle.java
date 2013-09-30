package net.bommy.kalaha.feld;

public class FeldKuhle extends Feld {

	public FeldKuhle() {
		super();
	}

	public FeldKuhle(int feldId, int feldIdSpieler, int spieler, int bohnen) {
		super(feldId, feldIdSpieler, spieler, bohnen);
	}

	public FeldKuhle(int spieler, int feldId) {
		super(spieler, feldId);
	}

}
