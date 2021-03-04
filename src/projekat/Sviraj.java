package projekat;
  
import javax.sound.midi.MidiUnavailableException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Map.Entry;



public class Sviraj extends Thread {


	private Kompozicija k;

	private MidiPlayer mp;
	private boolean radi;

	private ArrayList<MuzSimbol> kompozicija=new ArrayList<>();
	
	public Sviraj(Kompozicija komp) {

		try {
			mp =new MidiPlayer();
		} catch (MidiUnavailableException e) { e.printStackTrace(); }
		
		radi=false;
		k=komp;
		kompozicija=komp.getsimboli();
		
	}
	public int dohvMidi(String s) {
		String c = s;
		Mapa m= new Mapa();
		
	
		for (Entry<String, Help<String, Integer>> tek : m.get().entrySet() ) {
			if (Objects.equals(c, tek.getValue().getPrvi())) {
				return tek.getValue().getDrugi();
			}
		}
		
		return 0;
	}
	public void run() {
		try {
			while(!interrupted()) {
				synchronized (this) {
					if (!radi)
						wait();
				}
				for(int i=0; i<kompozicija.size();i++) {
					synchronized (this) {
						if (!radi)
							wait();
					}
					if(kompozicija.get(i).Zajedno()) {
						
						int midi;
						ArrayList<Integer> zajedno = new ArrayList<Integer>();
						
						midi=dohvMidi(kompozicija.get(i).getVisina()+Integer.toString(kompozicija.get(i).getOk()));
						zajedno.add(midi);
						i++;
						if(i>=kompozicija.size()) {
							break;
						}
						while(kompozicija.get(i).dohvTrajanje().istoTrajanje(new Trajanje(0,0))) {

							midi=dohvMidi(kompozicija.get(i).getVisina()+Integer.toString(kompozicija.get(i).getOk()));
							zajedno.add(midi);
							if(i+1<kompozicija.size()) {
							i++;
							}else {
								break;
							}
						}
						mp.playZajedno(zajedno, 400);
						i--;
					}else {
						if(kompozicija.get(i) instanceof Nota) {
					    	int midi=dohvMidi(kompozicija.get(i).getVisina()+Integer.toString(kompozicija.get(i).getOk()));
						    if(kompozicija.get(i).dohvTrajanje().istoTrajanje(new Trajanje(1,4))) { mp.play(midi, 400); }
						    else { mp.play(midi,200); }
						}else {
							if(kompozicija.get(i).dohvTrajanje().istoTrajanje(new Trajanje(1,4))) { mp.playPause(400); }
							else { mp.playPause(200); 
						    }
						}
					}
				}
			stani();
			}
		}catch(InterruptedException e) {
			
		}
	}
	public synchronized void kreni() {
		radi=true;
		notifyAll();
	}
	public synchronized void stani() {
		radi=false;
		
	}
	public synchronized void zavrsi() {
		radi=false;
		interrupt();
	}
	
}