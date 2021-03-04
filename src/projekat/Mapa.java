package projekat;
 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Mapa {
 
	private HashMap<String, Help<String, Integer>> mapa = new HashMap<>();
	private File file; 
	
	public Mapa() {
		ucitavanje();
	}
	
	
	public static Help<String, Help<String, Integer>> parsiranje(String s){
		Help rez = new Help("", new Help("", 0));
		
		Pattern p = Pattern.compile("(.),([A-Z]{1}[#]?[0-9]{1}),([0-9]{2})(\\n)?");
		Matcher m = p.matcher(s); 
		
		if (m.matches()) {
			String k = m.group(1);
			String midi = m.group(2);
			String b = m.group(3);
			int br = Integer.parseInt(b);
			rez.setPrvi(k); 
			rez.setDrugi(new Help(midi,br));
		}
		
		return rez; 
	}
	

	public void ucitavanje() {
		try {  
			file = new File("C:\\Users\\HP\\Desktop\\map.csv");
			BufferedReader br = new BufferedReader(new FileReader(file));
			Stream<String> s = br.lines();
			
			s.forEach((linija)->{
				Help rez = parsiranje(linija);				
				mapa.put((String)rez.getPrvi(), (Help<String, Integer>)rez.getDrugi());
			});
			
			br.close();
		} catch(IOException e) { System.err.println("Mapa nije ucitana"); }
	}
	public String slovoNota(String n){
		
		for (Entry<String, Help<String, Integer>> mapica : get().entrySet()) {
			if (Objects.equals(n, mapica.getValue().getPrvi())) {
				return mapica.getKey();
			}
		}
		return null;
	}
	
	public HashMap<String, Help<String, Integer>> get(){
		return mapa; 
	}
	
}