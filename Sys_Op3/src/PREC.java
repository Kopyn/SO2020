import java.util.Iterator;
import java.util.Random;

public class PREC extends Assignment{
	
	public boolean[] paused;
	Request[] free;
	int licznik;
	boolean[] done;
	
	int ilOddanych;
	int ilWznowionych;
	int[] kto=new int[nrOfProcesses];
	
	public PREC() {
		super();
		paused=new boolean[nrOfProcesses];
		for(int i=0; i<paused.length; i++) {
			paused[i]=false;
		}
		free=new Request[0];
		assignFirst();
		ilWznowionych=0;
		licznik=0;
		done=new boolean[nrOfProcesses];
		for(int i=0; i<done.length; i++) {
			done[i]=false;
		}
	}

	@Override
	public void assign() {
		for(int i=0; i<done.length; i++) {
			if(done[i]) {
				free=new Request[free.length+frames[i].length];
			}
		}
		boolean changed=false;
		boolean ch=true;
		
		for(int i=0; i<counter.length; i++) {
			if(done[i]) {
				continue;
			}else {
				ch=false;
			}
			if(!paused[i]) {
				changed=true;
			}
			
			double x=counter[i]/c[i];
			//x*=nrOfProcesses;
			if(Double.isNaN(x)) {
				//x=bottom;
				x=0;
			}
			
			
			if(x>top){
				stop(i);
			}else if(x<bottom) {
				if(paused[i]) {
					save(i);
				}
				
			}
			
			
		}
		
		if(ch) {
			System.out.println("KONIEC");
			System.out.println(s.stats());
		}
		
		if(!changed) {
			unpause();
		}
		

		restartCounter();
	}
	
	private void ratunek() {
		int z=free.length;
		if(z==0) {
			free=new Request[0];
			return;
		}
		for(int i=0; i<paused.length; i++) {
			if(!paused[i] && frames[i]!=null) {
				int a=frames[i].length;
				Request[] tab=new Request[a+z];
				for(int j=0; j<frames[i].length; j++) {
					if(frames[i][j]==null) {
						break;
					}
					tab[j]=new Request(frames[i][j].page, frames[i][j].index, frames[i][j].timeArrived);
				}
				for(int j=frames[i].length; j<tab.length; j++) {
					tab[j]=new Request();
				}
				
				frames[i]=tab;
				z=0;
			}
			
		}
		free=new Request[0];
	}
	
	public void save(int i) {
		int sumka=0;
		for(int j=0; j<paused.length; j++) {
			if(paused[i] && frames[i].length>0) {
				sumka+=frames[i].length;
			}
		}
		if(free.length>1) {
			int z=free.length;
			free=new Request[z+frames[i].length+sumka];
			frames[i]=new Request[free.length];
			paused[i]=false;
		}
		
//		int suma=0;
//		if(frames[i].length>0) {
//			suma+=frames[i].length;
//		}
//		
//		if(free.length>0) {
//			int z=free.length;
//			free=new Request[0];
//			paused[i]=false;
//			frames[i]=new Request[z+suma];
//		}else {
//		for(int j=0; j<done.length; j++) {
//			if(done[i]) {
//				suma+=frames[j].length;
//				frames[j]=null;
//			}
//		}
//		
//		frames[i]=new Request[suma];
//		
//		}
	}
	
	private void unpause() {
		System.out.println("hehz");
		int sum=0;
		for(int i=0; i<done.length; i++) {
			if(done[i]) {
				sum+=frames[i].length;
				paused[i]=true;
			}
		}
		sum+=free.length;
		free=new Request[sum];
		
		int ilosc=0;
		for(int i=0; i<paused.length; i++) {
			if(paused[i] && !done[i]) {
				ilosc++;
			}
		}
		
		if(ilosc==0) {
			return;
		}else {
		int przydzial=(int)free.length/ilosc;
		
		int o=free.length;
		for(int i=0; i<paused.length; i++) {
			if(paused[i] && !done[i]) {
				int a=frames[i].length;
				frames[i]=new Request[a+przydzial];
			}
		}
			if(o-(przydzial*ilosc)>=0)
				free=new Request[o-(przydzial*ilosc)];
		else
			free=new Request[0];
		}
	}
	
	private void resume(int i) { 
		if(frames[i].length>3) {
		Request[] arr=new Request[frames[i].length-1];
		for(int j=0; j<arr.length; j++) {
			arr[j]=frames[i][j];
		}
		frames[i]=arr;
		ilOddanych++;
		int z=free.length;
		free=new Request[z+1];
		}
	}
	
	private void stop(int i) {
		if(paused[i]) {
			return;
		}
		if(done[i]) {
			return;
		}
		s.szamotania++;
		if(free.length>0) {
			Request[] arr=new Request[frames[i].length+1];
			for(int j=0; j<frames[i].length; j++) {
				arr[j]=frames[i][j];
			}
			arr[frames[i].length]=new Request();
			frames[i]=arr;
			int z=free.length-1;
			free=new Request[z];
		}else {
			s.wstrzymane++;
			paused[i]=true;
			free=new Request[free.length+frames[i].length];
			frames[i]=new Request[0];
			
		}
	}
	
	public void assignFirst() {
		double biggest=0;
		int a=0;
		int all=0;
		
		double allPages=0;
		for(int i=0; i<processes.length; i++) {
			allPages+=processes[i].ilStron();
		}
				
		int[] assigned=new int[nrOfProcesses];
		
		for(int i=0; i<assigned.length; i++) {
			double procent=processes[i].ilStron()/allPages;
			if(biggest<(procent*nrOfFrames)-(int)(procent*nrOfFrames)) {
				biggest=(procent*nrOfFrames)-(int)(procent*nrOfFrames);
				a=i;
			}
			assigned[i]=(int)(procent*nrOfFrames);
			all+=assigned[i];
		}
		
		frames=new Request[nrOfProcesses][];
		
		for(int i=0; i<frames.length; i++) {
			if(i!=a)
				frames[i]=new Request[assigned[i]];
			else {
				int sum=assigned[i];
				sum+=(nrOfFrames-all);
				frames[i]=new Request[sum];
			}
		}
	}

	@Override
	public void nazwa() {
		System.out.println("STEROWANIE CZESTOSCIA BLEDOW STRONY");
		
	}
	
	
	
	
	private void fix() {
		if(s.wstrzymane>s.szamotania) {
			int z=s.szamotania;
			s.szamotania=s.wstrzymane;
			s.wstrzymane=s.szamotania;
		}
		for(int i=0; i<s.procesy.length; i++) {
			s.procesy[i].ilBledow/=2;
		}
	}

	@Override
	
	public void simulate() {
		
		int time=0;
		
		
		LRU a=new LRU(processes);
		
		while(!a.isDone()) {
			
			if(time!=0) {
				if(time%timeSlit==0) {
					for(int i=0; i<frames.length; i++) {
						boolean changed=false;
						Iterator<Request> it=a.requests.iterator();
						Request current;
						while(it.hasNext()) {
							current=it.next();
							if(current.index==i) {
								changed=true;
								break;
							}
						}
						if(!changed) {
							done[i]=true;;
						}
						
					}
					assign();
				}
			}
			
			int proc;
			int var=0;
			if(paused[a.actualRequest().index]) {
				Iterator<Request> it=a.requests.iterator();
				Request current=a.actualRequest();
				proc=current.index;
				while(it.hasNext() && paused[proc]) {
					
					current=it.next();
					proc=current.index;
					if(!paused[proc]) {
						break;
					}
				}
				if(paused[proc]) {
					if(!done[proc])
						save(proc);
					else {
						int z=frames[proc].length;
						int q=free.length;
						free=new Request[q+z];
					}
				}else {
					if(frames[proc].length==0) {
						
						resume(proc);
						var=s.procesy[proc].ilBledow;
						a.execute(frames[proc], s, proc);
						c[proc]++;
						counter[proc]+=s.procesy[proc].ilBledow-var;
					}else {
						var=s.procesy[proc].ilBledow;
						
						a.execute(frames[proc], s, proc);
						c[proc]++;
						counter[proc]+=s.procesy[proc].ilBledow-var;
					}
				}
			}else {
				proc=a.actualRequest().index;
				
				var=s.procesy[proc].ilBledow;
				a.execute(frames[proc], s, proc);
				c[proc]++;
				counter[proc]+=s.procesy[proc].ilBledow-var;
			}
			
			
			
			time++;
			
			
			
		}
		
		//fix();
		System.out.println(s.stats());
		//System.out.println(ilOddanych);
		System.out.println("---------------------------------");
		
		
	}

}
