import java.util.ArrayList;

public class Statystyka {
	
	public int liczbaBledow;
	public Process[] procesy;
	public int wstrzymane;
	public int szamotania;
	public int suma;
	
	public Statystyka() {
		liczbaBledow=0;
		wstrzymane=0;
		procesy=new Process[10];
		szamotania=0;
		suma=0;
	}
	
	public Statystyka(int z) {
		liczbaBledow=0;
		wstrzymane=0;
		procesy=new Process[z];
		szamotania=0;
	}
	
	
	public String stats() {
		StringBuffer sb=new StringBuffer();
		for(int i=0; i<procesy.length; i++) {
			sb.append("Proces "+i+" - "+procesy[i].ilBledow+" bledow\n");
			suma+=procesy[i].ilBledow;
		}
		sb.append("\nIlosc bledow lacznie: "+suma);
		sb.append("\nIlosc wstrzymanych procesow: " + wstrzymane + "\nIlosc szamotan: " + szamotania);
		
		return sb.toString();
	}

}
