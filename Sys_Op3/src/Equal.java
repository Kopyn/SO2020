
public class Equal extends Assignment{
	
	public Equal() {
		super();
		
		assign();
	}

	@Override
	public void assign() {
		int z=nrOfFrames/nrOfProcesses;
		frames=new Request[nrOfProcesses][z];
	}

	@Override
	public void nazwa() {
		System.out.println("PRZYDZIAL ROWNY");
	}

	@Override
	public void simulate() {
		
		
		
		LRU a=new LRU(processes);
		
//		System.out.println("Wielkosc tablicy frames-"+frames.length);
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
			
			int proc=a.actualRequest().index;
			int var=s.procesy[proc].ilBledow;
			//System.out.println("Odwolanie do strony - "+a.actualRequest());
			a.execute(frames[proc], s);
			if(s.procesy[proc].ilBledow>var) {
				counter[proc]++;
			}
			c[proc]++;
			//System.out.println("Liczba b³êdow - "+a.s.liczbaBledow);
			time++;
		}
		
		System.out.println(s.stats());
		System.out.println("---------------------------------");
		
	}

}
