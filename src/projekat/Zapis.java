package projekat;
 
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class Zapis extends JPanel implements Runnable {
	
	private Kompozicija komp;
	private boolean radi=false;
	private boolean kraj=false;
	private Thread zapis=null;
	private ArrayList<MuzSimbol> simboli= new ArrayList<MuzSimbol>();
	private int max;
	private int tekNota = 0;
	private boolean nacin = true; //false tekst true note
	
	public Zapis(Kompozicija k) {
		zapis=new Thread(this);
		komp=k;
		simboli=komp.getsimboli();
		setVisible(true);
	}
	
	public void nacin(boolean n) {
		nacin = n;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int x=0;
		int y=0;
		boolean pomoc=true;
		boolean jestePrvi=true;
		int brAkorda=0;
		int pom=tekNota+1;
		int visinaNotaZajedno=0;
			
		if (nacin) {
			for(int i=0; i<max; i++) {
				if(tekNota==simboli.size()-1) {
					if(pomoc) {
						pomoc=false;
					}else {
						break;
					}
				}
				if(simboli.get(tekNota) instanceof Nota) {
					if(simboli.get(tekNota).Zajedno()){
						    
							brAkorda=0;
							y=150;
							JTextField tf = new JTextField(simboli.get(tekNota).getVisina()+Integer.toString(simboli.get(tekNota).getOk()));
							tf.setBounds(x, y, 50, 50);
							tf.setBackground(Color.red);
							add(tf);
							visinaNotaZajedno++;
							tekNota++;
						    brAkorda++;
						    
						if(tekNota>=simboli.size()) {
							break;
						}
						
							while(simboli.get(tekNota).dohvTrajanje().istoTrajanje(new Trajanje(0,0))) {
								y=y-50;
								tf=new JTextField(simboli.get(tekNota).getVisina()+Integer.toString(simboli.get(tekNota).getOk()));
								tf.setBounds(x, y, 50, 50);
								tf.setBackground(Color.red);
								add(tf);
								visinaNotaZajedno++;
								if(tekNota+1<simboli.size()) {
									tekNota++;
									max--;
									brAkorda++;
								}else {
								    if(tekNota+1==simboli.size()) {
								    	max--;
								    }
									break;
								}
							}
							y=y-50;
						if(visinaNotaZajedno==2) {
							JTextField tf1=new JTextField();
							tf1.setBounds(x,y,50,50);
							tf1.setBackground(Color.white);
							add(tf1);
							
						}
						if(jestePrvi) {
							pom=pom+brAkorda-1;
							jestePrvi=false;
						}
							
						
						x=x+50;
						
					}
					else {
						jestePrvi=false;
						JTextField tf1 = new JTextField();
						tf1.setBounds(x,0,50,150);
						tf1.setBackground(Color.white);
						add(tf1);
						y=150;
						
						brAkorda=0;
						JTextField tf=new JTextField(simboli.get(tekNota).getVisina()+Integer.toString(simboli.get(tekNota).getOk()));
						tf.setBounds(x, y, 50, 50);
						if(simboli.get(tekNota).dohvTrajanje().istoTrajanje(new Trajanje(1,4))) {
						tf.setBackground(Color.red);
						}else {
							tf.setBackground(Color.green);
						}
						add(tf);
						tekNota++;
						x=x+50;
					}
				
				
				}
				else {
					jestePrvi=false;
					y=150;
					JTextField tf1 = new JTextField();
					tf1.setBounds(x,0,50,150);
					tf1.setBackground(Color.white);
					add(tf1);
					brAkorda=0;
					JTextField tf=new JTextField(" ");
					tf.setBounds(x, y, 50, 50);
					if(simboli.get(tekNota).dohvTrajanje().istoTrajanje(new Trajanje(1,4))) {
					tf.setBackground(Color.red);
					}else {
						tf.setBackground(Color.green);
					}
					add(tf);
					tekNota++;
					x=x+50;
				}
				
			}
			tekNota=pom;
		}
		else {	// else grana od nacina
		for(int i=0; i<max; i++) {
			if(tekNota==simboli.size()-1) {
				if(pomoc) {
					pomoc=false;
				}else {
					break;
				}
			}
			if(simboli.get(tekNota) instanceof Nota) {
				if(simboli.get(tekNota).Zajedno()){
					    
						brAkorda=0;
						y=150;
						JTextField tf = new JTextField(simboli.get(tekNota).getTxt());
						tf.setBounds(x, y, 50, 50);
						tf.setBackground(Color.red);
						add(tf);
						visinaNotaZajedno++;
						tekNota++;
					    brAkorda++;
					    
					if(tekNota>=simboli.size()) {
						break;
					}
					
						while(simboli.get(tekNota).dohvTrajanje().istoTrajanje(new Trajanje(0,0))) {
							y=y-50;
							tf=new JTextField(simboli.get(tekNota).getTxt());
							tf.setBounds(x, y, 50, 50);
							tf.setBackground(Color.red);
							add(tf);
							visinaNotaZajedno++;
							if(tekNota+1<simboli.size()) {
								tekNota++;
								max--;
								brAkorda++;
							}else {
							    if(tekNota+1==simboli.size()) {
							    	max--;
							    }
								break;
							}
						}
						y=y-50;
					if(visinaNotaZajedno==2) {
						JTextField tf1=new JTextField();
						tf1.setBounds(x,y,50,50);
						tf1.setBackground(Color.white);
						add(tf1);
						
					}
					if(jestePrvi) {
						pom=pom+brAkorda-1;
						jestePrvi=false;
					}
						
					
					x=x+50;
					
				}else {
					jestePrvi=false;
					JTextField tf1 = new JTextField();
					tf1.setBounds(x,0,50,150);
					tf1.setBackground(Color.white);
					add(tf1);
					y=150;
					
					brAkorda=0;
					JTextField tf=new JTextField(simboli.get(tekNota).getTxt());
					tf.setBounds(x, y, 50, 50);
					if(simboli.get(tekNota).dohvTrajanje().istoTrajanje(new Trajanje(1,4))) {
					tf.setBackground(Color.red);
					}else {
						tf.setBackground(Color.green);
					}
					add(tf);
					tekNota++;
					x=x+50;
				}
			
			
			}
			else {
				jestePrvi=false;
				y=150;
				JTextField tf1 = new JTextField();
				tf1.setBounds(x,0,50,150);
				tf1.setBackground(Color.white);
				add(tf1);
				brAkorda=0;
				JTextField tf=new JTextField(" ");
				tf.setBounds(x, y, 50, 50);
				if(simboli.get(tekNota).dohvTrajanje().istoTrajanje(new Trajanje(1,4))) {
				tf.setBackground(Color.red);
				}else {
					tf.setBackground(Color.green);
				}
				add(tf);
				tekNota++;
				x=x+50;
			}
		}
		tekNota=pom;
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
	kraj=true;
}
	@Override
	public void run() {
		try {
			tekNota=0;
			boolean pom=true;
			while(!Thread.interrupted() && !kraj) {
				synchronized (this) {
					if (!radi)
						wait();
				}
				if(pom) {
					if(simboli.size()-tekNota < 10) {
						max=simboli.size()-tekNota - 1;
						pom=false;
					}else {
						max=10;
					}
					repaint();
					if(tekNota<simboli.size()) {
							if(simboli.get(tekNota).dohvTrajanje().istoTrajanje(new Trajanje(1,4))) { Thread.sleep(400);}
							else { Thread.sleep(200);}	
					}
				}
			}
		} catch (InterruptedException e) {}
	}
}