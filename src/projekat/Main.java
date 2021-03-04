package projekat;
 
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JLabel;

public class Main extends Frame implements ActionListener, ItemListener {
	
	private Button play, stop, pause, load, find, startR, stopR;
	private TextField compPath;
	private Checkbox letters, notes;
	private boolean closing;
	private Kraj kraj=new Kraj(this, "Virtual Piano Project", true);
	private Kompozicija k = new Kompozicija();
	private Thread nitZapis = null;
	private Thread nitKlavir = null;
	private Zapis text;
	private TxtKonv t;
	private MidiKonv midif;
	private Sviraj s = null;
	CrtanjeKlavira kl;
	
	
	
	private class Kraj extends Dialog implements ActionListener {
		private Label l;
		private Button izadji,eksp;
		private TextField midi, txt;
		
		public Kraj(Frame f, String s, boolean b) {
			super(f, s, b);
			setSize(500,180); 
			setLocationRelativeTo(null); 
			l=new Label("NAPUSTANJE PROGRAMA",Label.CENTER);
		 	izadji=new Button("IZADJI"); 
		 	eksp=new Button("EKSPORTUJ"); 
		 	izadji.addActionListener(this);
		 	eksp.addActionListener(this);
		 	Panel pan =new Panel(new GridLayout(5,1));
			pan.add(l);
			pan.add(izadji);
			pan.add(eksp);
			midi=new TextField("Mesto za midi putanju", 30); 
			txt=new TextField("Mesto za txt putanju", 30); 
			pan.add(midi);
			pan.add(txt);
			this.add(pan, "North");

			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					setVisible(false);
				}
			});
		}

		public void actionPerformed(ActionEvent e) {
		
			if (e.getActionCommand()=="IZADJI"){
				closing=true;
				s.zavrsi();
				text.zavrsi();
				nitZapis.interrupt();
				text = null;
				nitZapis=null;
				s=null;
				kl.zavrsi();
				
				this.setVisible(false);
			}
			else if (e.getActionCommand()=="EKSPORTUJ") {
				if(txt.getText() != "")						
				t = new TxtKonv(txt.getText(),k);
				
				if (midi.getText() != "")
				midif = new MidiKonv(midi.getText(),k);
			}
			
		}
	}
	
	public Main() {
		super("Virtual Piano Project");
		setSize(1420,660);
		this.setLocationRelativeTo(null);
		addComponents();
		kraj.setVisible(false);
		setVisible(true);
			addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				kraj.setVisible(true);
				if (closing) {
					dispose();
				}
			}
		}); 
	}
	
	public void addComponents() {
	
		
		text = new Zapis(k);
		kl = new CrtanjeKlavira();
		kl.addKeyListener(kl);

		Panel panel=new Panel(new GridLayout(2,1));		
		Panel center=new Panel(new BorderLayout());
		center.setBackground(Color.green);
		Panel panel1 = new Panel(new GridLayout(0,3));
		panel1.setBounds(10, 11, 1580, 231);
		add(panel1,"North");
		center.add(text, "Center");
		
		
		Panel panel_1 = new Panel(new GridLayout(3,1));
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(10, 11, 504, 209);
		panel1.add(panel_1);
		
		Panel panel_2 = new Panel(new GridLayout(2,0));
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBounds(524, 11, 563, 209);
		panel1.add(panel_2);
		
		Panel panel_3 = new Panel(new GridLayout(6,1)); 
		panel_3.setBackground(Color.LIGHT_GRAY);
		panel_3.setBounds(1097, 11, 473, 209);
		panel1.add(panel_3);
		
		JLabel lblNewLabel_3 = new JLabel("Bo\u017Eo Labovi\u0107 0563/17");
		lblNewLabel_3.setFont(new Font("Century725 Cn BT", Font.PLAIN, 21)); 
		lblNewLabel_3.setBounds(10, 170, 524, 244);
		panel_1.add(lblNewLabel_3);
		
		JLabel lblProjekat = new JLabel("OOP Project - JAVA");
		lblProjekat.setFont(new Font("Arial", Font.PLAIN, 16));
		lblProjekat.setBounds(10, 11, 511, 244);
		
		JLabel lblDotsBoxes = new JLabel("Virtual Piano");
		lblDotsBoxes.setFont(new Font("Tempus Sans ITC", Font.BOLD | Font.ITALIC, 36));
		lblDotsBoxes.setBounds(10, 86, 524, 253);
		panel_2.add(lblDotsBoxes);
		panel_2.add(lblProjekat);
		
		panel.add(center);
		panel.add(kl);

		add(panel, "Center");
		
		Panel[] podesi=new Panel[6];
		podesi[0]=new Panel();
		panel_3.add(podesi[0]);
		podesi[1]=new Panel();
		panel_3.add(podesi[1]);
		podesi[2]=new Panel();
		panel_3.add(podesi[2]);
		podesi[3]=new Panel();
		panel_3.add(podesi[3]);
		podesi[4]=new Panel();
		panel_3.add(podesi[4]);
		podesi[5]=new Panel();
		panel_3.add(podesi[5]);

		podesi[0].add(new Label("Composition :", Label.CENTER));
		compPath=new TextField("",30);
		podesi[0].add(compPath);
		load=new Button("LOAD");
		load.setPreferredSize(new Dimension(180,25));
		load.addActionListener(this); 
		podesi[2].add(load);
		CheckboxGroup izbor=new CheckboxGroup();
		

		find=new Button("FIND");
		find.addActionListener(this); 
		podesi[0].add(find);
		
		play=new Button("PLAY");  
		play.addActionListener(this); 
		podesi[1].add(play);
		
		pause=new Button("PAUSE"); 
		pause.addActionListener(this); 
		podesi[1].add(pause);
		
		stop=new Button("STOP"); 
		stop.addActionListener(this);  
		podesi[1].add(stop);
		
		startR = new Button("Start recording");
		startR.addActionListener(this); 
		podesi[4].add(startR);
		 
		stopR = new Button("Stop recording");
		stopR.addActionListener(this); 
		podesi[4].add(stopR);
		
		letters=new Checkbox("LETTERS", true, izbor); 
		letters.addItemListener(this);
		notes=new Checkbox("NOTES", false, izbor); 
		notes.addItemListener(this);
		podesi[3].add(letters); 
		podesi[3].add(notes);
		

} 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		File ff;
		if (e.getActionCommand() == "LOAD") {
			try {
				k.ucitajKompoziciju(compPath.getText());
				
			} catch (FileNotFoundException e1) { e1.printStackTrace(); }
		}
		else if (e.getActionCommand() == "FIND") {
			JFileChooser fileChooser = new JFileChooser();
			int aa = fileChooser.showOpenDialog(null);
			if (aa == JFileChooser.APPROVE_OPTION) {
				ff = fileChooser.getSelectedFile();
				compPath.setText(ff.getAbsolutePath());
			}
            
		}
		else if (e.getActionCommand()=="PLAY") {
					s = new Sviraj(k);
					nitZapis = new Thread(text);
					nitZapis.start();
					
					s.start();
					text.kreni();
					
					s.kreni();
					kl.dodeli(k);
					nitKlavir = new Thread(kl);
					nitKlavir.start();
					kl.kreni();
				}

		else if (e.getActionCommand()=="PAUSE") {
				s.stani();
				text.stani();
				kl.stani();
		}
		else if (e.getActionCommand()=="STOP") {
				
				s.zavrsi();
				text.zavrsi();
				nitZapis.interrupt();
				text = null;
				nitZapis=null;
				s=null;
				kl.zavrsi();
				
		}

		else if (e.getActionCommand()=="Start recording") {
			startR.setEnabled(false);
			stopR.setEnabled(true);
	
		}
		else if (e.getActionCommand()=="Stop recording") {
			startR.setEnabled(true);
			stopR.setEnabled(false);
	
		}
	}	 
	
	@Override
	public void itemStateChanged(ItemEvent e) {

		if (e.getSource()==letters) {
			if (e.getStateChange()==ItemEvent.SELECTED) text.nacin(false);	
			text.repaint();
		}
		else if (e.getSource()==notes) {
			if (e.getStateChange()==ItemEvent.SELECTED) text.nacin(true);
			text.repaint();
			}
		}
			
	public static void main(String[] args) {
		new Main();
	}
}