package projekat;
 
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Objects;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;

public class MidiKonv extends Konvertor{
	
	private Trajanje cetvrtina = new Trajanje(1,4);
	private Trajanje osmina = new Trajanje(1,8);
	private ArrayList<Integer> nizMidi = new ArrayList<Integer>();
	private ArrayList<Integer> nizTr = new ArrayList<Integer>();
	
	public MidiKonv(String putanja,Kompozicija k) {
		super(putanja, k);
		try { 
			konvert();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	} 

	
	
	public void konvert() throws InvalidMidiDataException {
		try {
			popuni();
			long actionTime = 0, tpq = 48;
			
			Sequence s = new Sequence(javax.sound.midi.Sequence.PPQ,24);
			Track t = s.createTrack();
			
			byte[] b = {(byte)0xF0, 0x7E, 0x7F, 0x09, 0x01, (byte)0xF7};
			SysexMessage sm = new SysexMessage();
			sm.setMessage(b, 6);
			MidiEvent me = new MidiEvent(sm,(long)0);
			t.add(me);
			
			MetaMessage mt = new MetaMessage();
	        byte[] bt = {0x02, (byte)0x00, 0x00};
			mt.setMessage(0x51 ,bt, 3);
			me = new MidiEvent(mt,(long)0);
			t.add(me);
			
			ShortMessage mm = new ShortMessage();
			mm.setMessage(0xC0, 0x00, 0x00);
			me = new MidiEvent(mm,(long)0);
			t.add(me);
			
			actionTime = 1; long pom_noteon = 1; long pom_noteoff = 1;
			for (int i = 0; i < nizMidi.size(); i++) {
				int midibr = nizMidi.get(i);
				int rhythm= nizTr.get(i);
					
				if(rhythm == 3) 
					pom_noteon = actionTime;
				
				if (rhythm == 0) 
					actionTime = pom_noteon;
					
				if (midibr != 0) { 
					mm = new ShortMessage();
					mm.setMessage(ShortMessage.NOTE_ON, midibr, 100);
					me = new MidiEvent(mm, actionTime);
					t.add(me);
				}
				
				if (rhythm == 3) { 
					actionTime += tpq / 2 * 2;
					pom_noteoff = actionTime;
				}else if (rhythm == 0) { 
					actionTime = pom_noteoff;
				}else {
					actionTime += tpq / 2 * rhythm;
				}
				
				if (midibr != 0) { 
					mm = new ShortMessage();
					mm.setMessage(ShortMessage.NOTE_OFF, midibr, 100);
					me = new MidiEvent(mm, actionTime);
					t.add(me);
				}
				
			}	
			
			actionTime += tpq;
			mt = new MetaMessage();
	        byte[] bet = {};
			mt.setMessage(0x2F,bet,0);
			me = new MidiEvent(mt, actionTime);
			t.add(me);
			
			File f = new File(p);
			MidiSystem.write(s,1,f);
		
		} catch (IOException e) {
			System.err.println("U MidiKonv greska");
		}
		 
	}
	public void popuni() {
		komp.getsimboli().stream().forEach((elem)->{
			if (elem instanceof Pauza) {
				int br = 0;
				nizMidi.add(br);
			}else { 
				int br = dohvMidi((Nota)elem);
				nizMidi.add(br);
			}
			if (cetvrtina.istoTrajanje((elem.dohvTrajanje()))) {
				if (elem.Zajedno() == false)
					nizTr.add(2);
				else
					nizTr.add(3);
			}
			else if (osmina.istoTrajanje((elem.dohvTrajanje()))) {
				nizTr.add(1);
			}else { 
				nizTr.add(0);
			}
		});
	}

	public int dohvMidi(Nota n) {
		String c = n.getVisina();
		c += n.getOk();
	
		for (Entry<String, Help<String, Integer>> ulaz : komp.getMap().get().entrySet()) {
			if (Objects.equals(c, ulaz.getValue().getPrvi())) {
			return ulaz.getValue().getDrugi();
		}
	}
	
	return 0; 
}

}
