package projekat;
 
public class Help <A,B> {

	private A prvi;
	private B drugi;
	
	public Help(A a, B b) {prvi = a; drugi = b;}
		
	public void setPrvi(A a) { prvi = a; }

	public A getPrvi() { return prvi; }
	
	public void setDrugi(B b) { drugi = b; }
	
	public B getDrugi() { return drugi; }

	public String toString() { return prvi + "," + drugi; } 

}
