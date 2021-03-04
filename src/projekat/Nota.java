package projekat;
 


public class Nota extends MuzSimbol{
	
	String visina;
	String txt;
	boolean zajedno;
	int oktava;
	
	public Nota(int b, int i, boolean z, String v, int ok, String t) {
		super(b,i);
		zajedno=z;
		visina=v;
		oktava=ok;
		txt=t;	
	}
	
	public boolean Zajedno() { return zajedno; }
	
	public int getOk() { return oktava; }

	public String getTxt() { return txt; }

	public String getVisina() {	return visina; }
	
	
	public String toString() { return visina + oktava; }	

}
