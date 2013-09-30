package net.bommy.kalaha;

import net.bommy.kalaha.ctrl.SpielController;

public class Main {
	public static final boolean DEBUG = true; 
	
	public static void main(String[] args) {
		SpielController ctrl = new SpielController();
		ctrl.init();

		Thread t = new Thread( ctrl );
		t.start();
	}
}
