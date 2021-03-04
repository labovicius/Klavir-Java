package projekat;
 
public abstract class MuzSimbol {
	
	protected Trajanje trajanje;
	
	public MuzSimbol(int b, int i) { trajanje = new Trajanje(b,i); }

	public Trajanje dohvTrajanje() { return trajanje; }
	public abstract boolean Zajedno();	
	public abstract int getOk();
	public abstract String getTxt();
	public abstract String getVisina();
	public abstract String toString();

}
