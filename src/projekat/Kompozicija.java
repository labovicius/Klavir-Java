package projekat;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Kompozicija {
	
	private ArrayList<MuzSimbol> simboli = new ArrayList<MuzSimbol>();
	private Mapa mapa = new Mapa();
     
    
    public Kompozicija() {}

    public ArrayList<MuzSimbol> getsimboli() { return simboli; }
    
    public Mapa getMap() { return mapa; }
    
    public void ucitajKompoziciju(String str) throws FileNotFoundException {
    		
    		File file = new File(str);
    		
    		BufferedReader buff = new BufferedReader(new FileReader(file));
    		Stream<String> s = buff.lines();
    		
    		s.forEach((linija)->{
    			Pattern p = Pattern.compile("(([^\\[\\]]{1})|\\[([^\\]]+)\\])");
    			Matcher matcher = p.matcher(linija); 
    	    
    			while(matcher.find()) {
    	    	if(matcher.group(1).length()==1) {
    			  
    			  if(matcher.group(1).equals(" ")) {
    				  simboli.add(new Pauza(1,8));
    			  }
    			  
    			  else if(matcher.group(1).equals("|")) {
    				  simboli.add(new Pauza(1,4));
    				  
    			  }
    			  else {
    				  int ok;
    				  String visina;
    				  String c = mapa.get().get(matcher.group(1)).getPrvi();
    				  Pattern pat = Pattern.compile("([A-Z]{1}[#]?)([0-9]{1})");
    				  Matcher m = pat.matcher(c);
    				  if(m.matches()) {
    			      ok=Integer.parseInt(m.group(2));
    				  visina=m.group(1);
    				  simboli.add(new Nota(1,4,false, visina, ok, matcher.group(1)));
    				  }
    			  }
    		  }
    		  else {
    			  boolean zajedno=true;
    			  String provera=matcher.group(1);
    			  
    			  for(char c:provera.toCharArray()) {
    				  if(c==' ') {
    					  zajedno=false;
    					  break;
    				  }
    			  }
    			  
    			  if(zajedno) {
    				  boolean prvi=true;
    				  for(char ch:provera.toCharArray()) {
    					  if(ch=='[' || ch==']' || ch==' ') { continue; }
    					  int ok;
    					  String v;
    					  String c=mapa.get().get(Character.toString(ch)).getPrvi();
    					  Pattern pat1 = Pattern.compile("([A-Z]{1}[#]?)([0-9]{1})");
        				  Matcher mat1 = pat1.matcher(c);
        				  if(mat1.matches()) {
        					  ok=Integer.parseInt(mat1.group(2));
        					  v=mat1.group(1);
        					  if(prvi) {
        					  simboli.add(new Nota(1,4,true, v, ok, Character.toString(ch)));
        					  prvi=false;
        					  }else {
        						  simboli.add(new Nota(0,0,true, v, ok, Character.toString(ch)));
        					  }
        				  }
    				  }
    			  }
    			  else {
    				  for(char ch:provera.toCharArray()) {
    					  if(ch==' ' || ch=='[' || ch==']') {
    						  continue;
    					  }else {
    					  String v;
    	    			  int ok;    		 			  
    					  String c=mapa.get().get(Character.toString(ch)).getPrvi();
    					  
    					  Pattern pat2 = Pattern.compile("([A-Z]{1}[#]?)([0-9]{1})");
        				  Matcher mat2 = pat2.matcher(c);
        				  if(mat2.matches()) {
        					  ok=Integer.parseInt(mat2.group(2));
        					  v=mat2.group(1);
        					  simboli.add(new Nota(1,8,false, v, ok, Character.toString(ch)));
        				  }
    					  }
    				  }
    			  }
    		  }
    	   }
       }); 
    	//provera
    	for (int i = 0 ; i < simboli.size();i++) {
   		System.out.println(simboli.get(i).toString());
   		}
    }	  
}