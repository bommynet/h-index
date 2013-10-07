package model.interfaces;

/**
 * Dieses Interface stellt eine Methode bereit, die ein Objekt anwweisst,
 * seinen internen Zustand weiter zu simulieren. Die Schrittweise der Simulation
 * wird als float-Wert in Millisekunden angebeben.
 * @author niklas
 */
public interface IUpdatable
{
	public void Update(float eleapsedMillis);
}
