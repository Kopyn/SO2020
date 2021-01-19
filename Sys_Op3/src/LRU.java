import java.util.Iterator;

public class LRU extends Algorithm{
	
	int pos=0;
	
	public LRU() {
		super();
	}
	
	public LRU(Process[] p) {
		super(p);
	}

	@Override
	public void execute() {
		if(position<frames.length) {
			goFifo();
		}else {
			if(actualRequest()==null) return;
			if(!occured()) {
				frames[longest()]=requests.remove();
				s.liczbaBledow++;
			}else {
				Request insert=requests.remove();
				int i=0;
				while(frames[i].page!=insert.page) {
					i++;
				}
				frames[i]=insert;
			}
		}
		//show();
	}
	
	//metoda zwracajaca index do ktorego najdluzej nie bylo odwolania w celu podmiany odwolanai w metodzie execute
		public int longest() {
			int ret=0;
			int smallestIndex=actualRequest().index;
			for(int i=0; i<frames.length; i++) {
				if(smallestIndex>frames[i].timeArrived) {
					smallestIndex=frames[i].timeArrived;
					ret=i;
				}
			}
			
			return ret;
		}
	
		
	//ZAD4
	//wlasciwa metoda do zadania 4
	@Override
	public void execute(Request[] f, Statystyka stat) {
		
		//show(f);
		for(int i=0; i<f.length; i++) {
			if(f[i]!=null && f[i].page==actualRequest().page) {
				f[i]=requests.remove();
				return;
			}
			if(f[i]==null) {
				stat.procesy[requests.get(0).index].ilBledow++;
				f[i]=requests.remove();
				return;
			}
		}
			if(actualRequest()==null) return;
			if(!occured(f)) {
				stat.procesy[requests.get(0).index].ilBledow++;
//				System.out.println("ilosc ramek na ktorych pracujemy: "+f.length);
//				System.out.println("co zwraca longest? "+longest(f));
				if(f.length>0)
					f[longest(f)]=requests.remove();				
			}else {
				Request insert=requests.remove();
				int i=0;
				while(f[i].page!=insert.page) {
					i++;
				}
				f[i]=insert;
			}
			
		
		//show(f);
	}
	
	public void execute(Request[] f, Statystyka stat, int index) {
		Request r=requests.peekFirst();
		Iterator<Request> it=requests.iterator();
		while(it.hasNext() && r.index!=index) {
			r=it.next();
		}
		if(r!=null && r.index==index) {
			requests.remove(r);
		}else
			System.out.println("tu mamy problem niesamowity");
		//show(f);
		for(int i=0; i<f.length; i++) {
			if(f[i]!=null && f[i].page==r.page) {
				f[i]=r;
				return;
			}
			if(f[i]==null) {
				stat.procesy[r.index].ilBledow++;
				f[i]=r;
				return;
			}
		}
			if(actualRequest()==null) return;
			if(!occured(f, r)) {
				stat.procesy[requests.get(0).index].ilBledow++;
				f[longest(f, r)]=r;				
			}else {
				Request insert=r;
				int i=0;
				while(f[i].page!=insert.page) {
					i++;
				}
				f[i]=insert;
			}
			
		
		//show(f);
	}
	
	
	//wlasciwa metoda do zadania 4
	public int longest(Request[] f) {
		int ret=0;
		int smallestIndex=actualRequest().timeArrived;
		for(int i=0; i<f.length; i++) {
			if(smallestIndex>f[i].timeArrived) {
				smallestIndex=f[i].timeArrived;
				ret=i;
			}
		}
		
		//System.out.println("zwracana wartosc: "+ret);
		
		return ret;
	}
	
	public int longest(Request[] f, Request r) {
		int ret=0;
		int smallestIndex=r.timeArrived;
		for(int i=0; i<f.length; i++) {
			if(smallestIndex>f[i].timeArrived) {
				smallestIndex=f[i].timeArrived;
				ret=i;
			}
		}
		
		return ret;
	}
	
	public void remove(int index) {
		Request r=requests.peekFirst();
		Iterator<Request> it=requests.iterator();
		while(it.hasNext()) {
			r=it.next();
			if(r.index==index) {
				requests.remove(r);
			}
		}
	}

}
