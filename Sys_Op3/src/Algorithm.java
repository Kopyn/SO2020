import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class Algorithm {
	
	Statystyka s;
	Request[] frames;
	Process[] proc;
	int position;//pomocne do FIFO
	public static int nrOfFrames;//do parametryzacji
	LinkedList<Request> requests;
	
	public Algorithm() {
		proc=new Process[nrOfFrames];
		s=new Statystyka();
		//proc.list=Generator.random();
		frames=new Request[nrOfFrames];
		position=0;
	}
	
	//zad4
	public Algorithm(Process[] p) {
		//s=new Statystyka(p);
		proc=p;
		frames=new Request[nrOfFrames];
		position=0;
		requests=new LinkedList<>();
		make();
	}
	
	//zad4
	private void make() {
		for(int i=0; i<proc.length; i++) {
			Iterator<Request> it=proc[i].list.iterator();
			while(it.hasNext()) {
				requests.add(it.next());
			}
		}
		Collections.sort(requests);
	}
	
	//metoda wykonujaca krok algorytmu
	public abstract void execute();
	
	
	
	//metoda do zapelnienie ramek gdy sa jeszcze puste
	protected void goFifo() {
		if(!occured()) {
			frames[position]=requests.remove();
			position++;
			s.liczbaBledow++;
		}else {
			requests.remove();
		}
	}
	
	protected boolean check(Request r) {
		for(int i=0; i<frames.length; i++) {
			if(frames[i].page==r.page)
				return false;
		}
		s.liczbaBledow++;
		return true;
	}
	
	//sprawdza czy strona do ktorej jest aktualnie odwolanie znajduje sie jzu w ramce
	protected boolean occured() {
		if(actualRequest()==null) return false;
		boolean ret=false;
		for(int i=0; i<frames.length; i++) {
			if(frames[i]==null)
				return false;
			if(actualRequest().page==frames[i].page)
				return true;
		}
		return ret;
	}
	
	public boolean isDone() {
		return (requests.size()==0)?true:false;
	}
	
	//wizualizacja ramek
	protected void show() {
		System.out.println("-------------------------");
		for(int i=0; i<frames.length; i++) {
			System.out.println(i+"- " + frames[i]);
		}
		System.out.println("-------------------------");
	}
	
	////////////////////////////
	//ZAD4
	protected boolean occured(Request[] f) {
		if(actualRequest()==null) return false;
		boolean ret=false;
		for(int i=0; i<f.length; i++) {
			if(f[i]==null)
				return false;
			if(actualRequest().page==f[i].page) {
				return true;
			}
		}
		return ret;
	}
	
	protected boolean occured(Request[] f, Request r) {
		if(actualRequest()==null) return false;
		boolean ret=false;
		for(int i=0; i<f.length; i++) {
			if(f[i]==null)
				return false;
			if(r.page==f[i].page) {
				return true;
			}
		}
		return ret;
	}
	
	//zad4
		public abstract void execute(Request[] f, Statystyka stat);
		
		//zad4
		protected Request actualRequest() {
			if(requests.size()>0)
				return requests.get(0);
			else return null;
		}
		
		protected void show(Request[] f) {
			System.out.println("-------------------------");
			for(int i=0; i<f.length; i++) {
				System.out.println(i+"- " + f[i]);
			}
			System.out.println("-------------------------");
		}

}
