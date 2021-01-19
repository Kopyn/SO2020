import java.util.LinkedList;
import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;

public class Generator {
	
	public static int nrOfRequests;
	public static int range;
	public static int coIle;
	public static double szansa;
	
	//nieuzywana metoda
	public static LinkedList<Request> random() {
		int r=3;
		LinkedList<Request> lista=new LinkedList<>();
		Random rand=new Random();
		int c=rand.nextInt(nrOfRequests-r)+r;
		int z=rand.nextInt(range)+1;
		
		for(int i=0; i<nrOfRequests; i++) {
			lista.add(new Request(z, i, i));
			z++;
			z=(z%range)+1;
		}
		
		shuffle(lista);
		
		return lista;
	}
	
	//metoda generujaca zgloszenia
	public static LinkedList<Request> generate(){
		LinkedList<Request> retList=new LinkedList<>();
		Random rand=new Random();
		int srodek=range/2;
		int r=range/4;
		boolean lokalnosc=false;
		for(int i=0; i<nrOfRequests; i++) {
			if(lokalnosc) {
				retList.add(new Request(rand(srodek-r, srodek+r), i, i));
			}else{
				retList.add(new Request(rand.nextInt(range)+1,i, i));
			}
			if(i%coIle==0) {
				if(Math.random()>1-szansa) {
					lokalnosc=false;
				}else {
					lokalnosc=true;
					srodek=rand.nextInt(range)+1;
					r=range/(rand.nextInt(3)+3);
				}
			}
		}
		return retList;
	}
	
	public static int rand(int from, int to) {
		if(from<=0)
			from=1;
		if(to>range)
			to=range;
		int dif=to-from+1;
		Random rand=new Random();
		return rand.nextInt(dif)+from;
	}
	
	public static Process generateProcesses() {
		Process p=new Process();
		p.list=generate();
		return p;
	}
	
	
	
	public static void show(LinkedList<Request> list) {
		Iterator<Request> it=list.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}
	

	//nieuzywana metoda
	public static void shuffle(LinkedList<Request> list) {
		Random rand=new Random();
		int gap=nrOfRequests/2;
		for(int i=0; i<3; i++) {
			for(int j=0; j<list.size(); j+=gap) {
				list.get(j).page=rand.nextInt(range)+1;
			}
			gap/=1.5;
		}
	}
	
	/////////////////////////////
	//ZAD4
	public static Process[] gen(int il) {
		Process[]p=new Process[il];
		
		for(int i=0; i<p.length; i++) {
			p[i]=new Process();
		}
		
		LinkedList<Request> retList=new LinkedList<>();
		Random rand=new Random();
		int srodek=range/2;
		int r=range/4;
		boolean lokalnosc=false;
		int zakres=range;
		for(int i=0; i<il; i++) {
			retList=new LinkedList<>();
			for(int j=0; j<(nrOfRequests*rand.nextInt(3)+1); j++) {
				if(lokalnosc) {
					retList.add(new Request(zakres+rand(srodek-r, srodek+r), i, j));
				}else{
					retList.add(new Request(zakres+rand.nextInt(range)+1, i, j));
				}
				if(i%coIle==0) {
					if(Math.random()>1-szansa) {
						lokalnosc=false;
					}else {
						lokalnosc=true;
						srodek=rand.nextInt(range)+1;
						r=range/(rand.nextInt(3)+3);
					}
				}
			}
			p[i].list=retList;
			zakres+=range;
		}
		
		//return retList;
		return p;
	}
	public static Process generateProcesses(int z) {
		Process p=new Process();
		p.list=generate(z);
		return p;
	}
	
	public static Process[] g(int il) {
		Process[]p=new Process[il];
		int zakres=range;
		for(int i=0; i<il; i++) {
			p[i]=new Process(generate(i, zakres));
			zakres+=range;
		}
		return p;
	}
	
	public static LinkedList<Request> generate(int z){
		LinkedList<Request> retList=new LinkedList<>();
		Random rand=new Random();
		int srodek=range/2;
		int r=range/4;
		boolean lokalnosc=false;
		for(int i=0; i<nrOfRequests; i++) {
			if(lokalnosc) {
				retList.add(new Request(rand(srodek-r, srodek+r), z, i));
			}else{
				retList.add(new Request(rand.nextInt(range)+1, z, i));
			}
			if(i%coIle==0) {
				if(Math.random()>1-szansa) {
					lokalnosc=false;
				}else {
					lokalnosc=true;
					srodek=rand.nextInt(range)+1;
					r=range/(rand.nextInt(3)+3);
				}
			}
		}
		return retList;
	}
	
	public static LinkedList<Request> generate(int z, int zakres){
		LinkedList<Request> retList=new LinkedList<>();
		Random rand=new Random();
		int srodek=range/2;
		int r=range/4;
		boolean lokalnosc=false;
		for(int i=0; i<nrOfRequests; i++) {
			if(lokalnosc) {
				retList.add(new Request(zakres+rand(srodek-r, srodek+r), z, i));
			}else{
				retList.add(new Request(zakres+rand.nextInt(range)+1, z, i));
			}
			if(i%coIle==0) {
				if(Math.random()>1-szansa) {
					lokalnosc=false;
				}else {
					lokalnosc=true;
					srodek=rand.nextInt(range)+1;
					r=range/(rand.nextInt(3)+3);
				}
			}
		}
		return retList;
	}

}
