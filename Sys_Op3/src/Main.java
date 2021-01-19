
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		Scanner input=new Scanner(System.in);
		
		
		
		System.out.println("Wprowadz ilosc procesow: ");
		Assignment.nrOfProcesses=input.nextInt();
		
		System.out.println("Wprowadz ilosc ramek: ");
		Algorithm.nrOfFrames=input.nextInt();
		Assignment.nrOfFrames=Algorithm.nrOfFrames;
		
		System.out.println("Wprowadz ilosc odwolan: ");
		Generator.nrOfRequests=input.nextInt();
		
		System.out.println("Wprowadz poczatkowy zakres dla stron: ");
		Generator.range=input.nextInt();
		
		System.out.println("Co ile wygenerowanych odwolan ma byc sprawdzana szansa na zakonczenie "
				+ "lub pojawienie sie fragmentu z lokalnoscia odwolan? ");
		Generator.coIle=input.nextInt();
		
		System.out.println("Jaka bedzie szansa, ze po tej ilosci odwolan rozpocznie sie lokalnosc(0,?): ");
		Generator.szansa=input.nextDouble();
		
		System.out.println("Wprowadz dolny prog: ");
		Assignment.bottom=input.nextDouble();
		
		System.out.println("Wprowadz gorny prog: ");
		Assignment.top=input.nextDouble();
		
		System.out.println("Okno czasowe: ");
		Assignment.timeSlit=input.nextInt();
		
		System.out.println("Co ile ma byc mierzony rozmiar zbioru roboczego?: ");
		Zonal.f=input.nextInt();
		
		Assignment[] a=new Assignment[4];
		a[0]=new Equal();
		a[1]=new Proportional();
		a[2]=new Zonal();
		a[3]=new PREC();
		
		
		
		
		for(int i=0; i<a.length; i++) {
			a[i].nazwa();
			a[i].simulate();
		}
		
		
	}

}
