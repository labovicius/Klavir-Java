package projekat;
 
public class Trajanje {
	
	private int brojilac;
	private int imenilac;

	public Trajanje(int b, int i){ brojilac = b; imenilac = i; }
	
	public int dohvB() { return brojilac; }
	public int dohvI() { return imenilac; }
	

	public boolean istoTrajanje(Trajanje tr) {
		if (brojilac == tr.brojilac && imenilac == tr.imenilac) return true;
		return false;
	}
	public String toString() {
		return brojilac + "/" + imenilac;
	}
}
