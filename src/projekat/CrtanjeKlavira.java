package projekat;
 
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Objects;

import javax.sound.midi.MidiUnavailableException;

import projekat.*;

public class CrtanjeKlavira extends Canvas implements KeyListener, Runnable{

	private MidiPlayer mp;
	private Kompozicija komp = null;
	private ArrayList<MuzSimbol> listaSim = new  ArrayList<MuzSimbol>();
	private ArrayList<String> stampajZaj = new ArrayList<String>();
	private boolean radi = false, kraj = false;
	private Mapa mapa = new Mapa();
	private String stampaj = "";
	private Trajanje cetvrtina = new Trajanje(1,4);
	private Trajanje nulta = new Trajanje(0,0);

	private String [] normalno = {"C", "D", "E", "F", "G", "A", "B"};
	private String [] povisilica = {"C#", "D#", " ", "F#", "G#", "A#", " "};
	
	
	
	public CrtanjeKlavira() {
		
		try { mp = new MidiPlayer(); } catch (MidiUnavailableException e) { System.err.println("U CrtanjeKlavira greska"); }
		
		repaint();
		setVisible(true);
	}
	
	public void dodeli(Kompozicija kk) {
		komp = kk;
		listaSim = kk.getsimboli();
	}
	public synchronized void kreni() {
		radi = true;
		notifyAll();
	}
	
	public synchronized void stani() {
		radi = false;
	}
	
	public synchronized void zavrsi() {
		kraj = true;
	}
	
	@Override
	public void paint(Graphics g) {
		final int bele_s = 50;
		final int bele_d = 300;
		final int crne_s = bele_s / 2;
		final int crne_d = bele_d / 2 - 50;
		
		final int broj_bele = 7;
		final int oktave = 5;
		
		int x = 0, y = 0;
		for(int t = 0; t < oktave; t++) {
			for (int i = 0; i < broj_bele; i++) {
				int oktava = t + 2;
				String nota = normalno[i] + oktava; 
				
				if (stampaj.equals(nota))
					g.setColor(Color.RED); 
				else  
					g.setColor(Color.WHITE);
				
				if (stampajZaj != null) {
					for (int j = 0; j < stampajZaj.size(); j++) {
						if (stampajZaj.get(j).equals(nota)) {
							g.setColor(Color.RED);
							break;
						}
					}
				}
				
				g.fillRect(x, y, bele_s, bele_d);
				g.setColor(Color.DARK_GRAY);
				g.drawRect(x, y, bele_s, bele_d);
				g.setColor(Color.BLACK);
				g.drawString(nota, x + 25, y + 130);
				g.drawString(mapa.slovoNota(nota), x + 25, y + 180);
				x += bele_s;
			}			
			 
		}
		
		x = 0; x += bele_s - crne_s / 2;
		for(int t = 0; t < oktave; t++) { 
			for(int i = 0; i < povisilica.length; i++) {
				if (povisilica[i] == " ") {
					x += bele_s;
					continue;
				}
				int oktava = t + 2;
				String nota = povisilica[i] + oktava;
				
				if (stampaj.equals(nota))
					g.setColor(Color.RED); 
				else 
					g.setColor(Color.BLACK);
		
				if (stampajZaj != null) {
					for (int j = 0; j < stampajZaj.size(); j++) {
						if (stampajZaj.get(j).equals(nota)) {
							g.setColor(Color.RED);
							break;
						}
					}
				}	
				g.fillRect(x, y, crne_s, crne_d);
				g.setColor(Color.BLACK);
				g.drawRect(x, y, crne_s, crne_d);
				g.setColor(Color.WHITE);
				g.drawString(nota, x, y + 60);
				g.drawString(mapa.slovoNota(nota), x + 11, y + 80);
				x += bele_s; stampajZaj = null;
			}
		}
		
		
	}


	@Override
	public void run() {
		try {
			while(!Thread.interrupted() && !kraj) {
				synchronized (this) {
					if (!radi)
						wait();
				}
				for (int i = 0; i < listaSim.size(); ) {
					synchronized (this) {
						if (!radi)
							wait();
					}
					MuzSimbol simbol = listaSim.get(i);
					long trajanje;

					if ((cetvrtina.istoTrajanje(listaSim.get(i).dohvTrajanje())))
						trajanje = 400;
					else 
						trajanje = 200;
					if (simbol instanceof Nota) {
						if ((cetvrtina.istoTrajanje(listaSim.get(i).dohvTrajanje()) && (!simbol.Zajedno()))) {

							stampaj = ((Nota) simbol).getVisina();
							stampaj += ((Nota) simbol).getOk(); 
							repaint(); 
							Thread.sleep(trajanje);	
							i++;
						}else if((cetvrtina.istoTrajanje(listaSim.get(i).dohvTrajanje()) && (simbol.Zajedno()))) {

							stampajZaj = null; stampajZaj = new ArrayList<String>();
							stampaj = ((Nota) simbol).getVisina();
							stampaj += ((Nota) simbol).getOk();
							stampajZaj.add(stampaj);
							stampaj = "";
							i++; 
							simbol = listaSim.get(i);

							while ((nulta.istoTrajanje(listaSim.get(i).dohvTrajanje()))) {
								stampaj = ((Nota) simbol).getVisina();
								stampaj += ((Nota) simbol).getOk();
								stampajZaj.add(stampaj);
								stampaj = "";
								if ((i + 1) < listaSim.size()) {
									i++;
									simbol = listaSim.get(i);
								}else {
									i++;
									break;
								}
							}
							repaint();
							Thread.sleep(trajanje);	
						}else{
							stampaj = ((Nota) simbol).getVisina();
							stampaj += ((Nota) simbol).getOk();
							repaint(); 
							Thread.sleep(trajanje);
							i++;
						}
						
					}else {
						repaint();
						Thread.sleep(trajanje); 
						i++;
					}
				}
				stampaj = "";
				repaint();
				zavrsi();
			}
		}catch(InterruptedException e) {}	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int midi = dohvMidi(String.valueOf(e.getKeyChar()));
		if (midi != -1) {
			mp.play(midi);
			stampaj = dohvNota(String.valueOf(e.getKeyChar())); 
			
			repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int midi = dohvMidi(String.valueOf(e.getKeyChar()));
		if (midi != -1) {
			mp.release(midi);
		}
		stampaj = "";
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {}	

	
	public int dohvMidi(String s) {
		if (mapa.get().containsKey(s)) {
			return mapa.get().get(s).getDrugi();
			}
		else
			return -1; 
	}
	
	public String dohvNota(String s) {
		if (mapa.get().containsKey(s))
			return mapa.get().get(s).getPrvi();
		else
			return null;
	}
	

}