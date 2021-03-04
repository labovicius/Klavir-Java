package projekat;
 
import java.io.File;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;

public class TxtKonv extends Konvertor {

	public TxtKonv(String putanja,Kompozicija komp) {
		super(putanja,komp);

		popuni();
	}
	 
	public void popuni() {
		try { 
			
		File f=new File(p);
 		if(!f.exists()) { f.createNewFile(); }


 		boolean Zaj=false,pZaj=true;
		boolean Osm=false,pOsm=true;
		
		ArrayList<MuzSimbol> simList = komp.getsimboli();
		BufferedWriter buff =new BufferedWriter(new FileWriter(f));
		
		for(int i=0; i<simList.size(); i++) {
		     try {
		    	 
		     if(simList.get(i).Zajedno()) {
		    	 if(Osm) {
		    		 buff.write("]");
		    	 }
		    	 if(simList.get(i).dohvTrajanje().istoTrajanje(new Trajanje(1,4)) && Zaj) {
		    		 Zaj=false;
		    		 pZaj=true;
		    		 buff.write("]");
		    	 }
		    	 pOsm=true;
		    	 Osm=false;
		    	 if(pZaj) {
		    		 buff.write("[");
		    		 Zaj=true;
				     pZaj=false;
		    	 }
		    	 buff.write(simList.get(i).getTxt());
	     }
		     else {
		    	 if(Zaj) {
		    		 pZaj=true;
		    		 buff.write("]");
		    		 Zaj=false;
		    	 }
		    	 if(simList.get(i).dohvTrajanje().istoTrajanje(new Trajanje(1,8))) {    		 
	    			 if(pOsm) {
	    				 pOsm=false;
	    				 Osm=true;
	    				 buff.write("[");
	    				 buff.write(simList.get(i).getTxt());
		    	 }
	    			 else {
	    			 buff.write(" ");
	    			 buff.write(simList.get(i).getTxt());
	    			 } 
		    	 }
		    	 else if(simList.get(i).getTxt()=="|" || simList.get(i).getTxt()==" ") {
		    		 if(Osm) {
		    			 buff.write("]");
		    		 }
		    		 pOsm=true;
		    		 Osm=false;		
		    		 buff.write(simList.get(i).getTxt());
		    		 
		    			 
		    		 
		    	 }else {
		    		 if(Osm) {
		    			 buff.write("]");
		    		 }
		    		pOsm=true;
		    		Osm=false; 
		    		buff.write(simList.get(i).getTxt());
		    	 }
		     }
		  }catch(IOException e1) { System.err.println("U TxtKonv greska!"); }
		}
		if(Osm || Zaj) {
			buff.write("]");
		}
		buff.close();
		} catch(IOException e2) { System.out.println("U TxtKonv greska!"); }
	}
}
