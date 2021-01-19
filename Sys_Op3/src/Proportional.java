
public class Proportional extends Assignment{
	
	
	
	public Proportional() {
		super();
		assign();
	}

	@Override
	public void assign() {
		
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
			//System.out.println("Ilosc przydzielonych ramek: "+assigned[i]);
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
			//System.out.println("Ilosc ramek dla frames["+i+"]: "+frames[i].length);
		}
	}

	@Override
	public void nazwa() {
		System.out.println("PRZYDZIAL PROPORCJONALNY");
		
	}

	@Override
	public void simulate() {
		
		
		LRU a=new LRU(processes);
		
		int time=0;
			
		
		while(!a.isDone()) {
			
			if(time!=0) {
				if(time%timeSlit==0) {
					for(int i=0; i<counter.length; i++) {
						if((counter[i]/c[i])>top) {
							s.szamotania++;
						}
					}
					restartCounter();
				}
			}
			
//			if(timeSlit%time==0 && time!=0) {
//				restartCounter();
//			}
			int proc=a.actualRequest().index;
			int var=s.procesy[proc].ilBledow;
			//System.out.println("Odwolanie do strony - "+a.actualRequest());
			a.execute(frames[proc], s);
			//System.out.println("Liczba b³êdow - "+a.s.liczbaBledow);
			
			if(s.procesy[proc].ilBledow>var) {
				counter[proc]++;
			}
			c[proc]++;
			
			time++;
			//System.out.println(time);
		}
		
		
		System.out.println(s.stats());
		System.out.println("---------------------------------");
		
	}

	
	
}
