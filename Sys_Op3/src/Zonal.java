import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Zonal extends Assignment{
	
	public boolean[] paused;
	Request[] free;
	public static int f;
	private double[] wss;
	private LinkedList<Request>[] lastlyUsed;
	private boolean[] done;
	
	
	@SuppressWarnings("unchecked")
	public Zonal() {
		super();
		paused=new boolean[nrOfProcesses];
		lastlyUsed=new LinkedList[nrOfProcesses];
		for(int i=0; i<lastlyUsed.length; i++) {
			lastlyUsed[i]=new LinkedList<>();
		}
		free=new Request[0];
		assignFirst();
		wss=new double[nrOfProcesses];
		done=new boolean[nrOfProcesses];
		for(int i=0; i<done.length; i++) {
			done[i]=false;
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
	public void assign() {
		for(int i=0; i<done.length; i++) {
			if(done[i]) {
				free=new Request[free.length+frames[i].length];
			}
		}
		int sum=0;
		
		for(int i=0; i<lastlyUsed.length; i++) {
			
			int z=lastlyUsed[i].size();
			z=z-timeSlit;
			if(z<0) {
				z=0;
			}
			if(!paused[i]) {
				for(int j=0; j<z; j++) {
					lastlyUsed[i].removeFirst();
				}
			}
			HashSet<Request> set=new HashSet<>(lastlyUsed[i]);
			wss[i]=set.size();
			sum+=set.size();
			
		}
		
		
		if(sum>nrOfFrames) {
			pause();
		}else if(sum<=nrOfFrames) {
			resume(sum);
		}
		
	}
	
	private void pause() {
		HashSet<Request> set=new HashSet<>(lastlyUsed[0]);
		int max=set.size();
		int index=0;
		for(int i=0; i<lastlyUsed.length; i++) {
			set=new HashSet<>(lastlyUsed[i]);
			if(set.size()>max) {
				max=set.size();
				index=i;
			}
		}
		
		s.wstrzymane++;
		paused[index]=true;
		free=new Request[free.length+frames[index].length];
		frames[index]=new Request[0];
		
		
		//frames[i]
	}
	
	private void resume(int a) {
		for(int i=0; i<lastlyUsed.length; i++) {
			if(paused[i]) {
				HashSet<Request> set=new HashSet<>(lastlyUsed[i]);
				if(free.length>0 && set.size()+a<nrOfFrames) {
					paused[i]=false;
					frames[i]=new Request[free.length];
					free=new Request[0];
				}
				return;
			}
		}
	}
	
	private void unpause() {
		//System.out.println("ellp");
		for(int i=0; i<frames.length; i++) {
			if(paused[i]) {
				paused[i]=false;
			}
		}
	}

	@Override
	public void nazwa() {
		System.out.println("MODEL STREFOWY");
		
	}
	

	@Override
	public void simulate() {
		
		int time=0;
		
		LRU a=new LRU(processes);
		
		
		
		while(!a.isDone() && time<10000) {
			
			
			if(time!=0) {
				if(time%f==0) {
					assign();
				}
				if(time%timeSlit==0) {
					for(int i=0; i<frames.length; i++) {
						boolean changed=false;
						Iterator<Request> it=a.requests.iterator();
						while(it.hasNext()) {
							if(it.next().index==i) {
								changed=true;
								break;
							}
							
						}
						if(!changed) {
							done[i]=true;
							return;
						}
					}
					for(int i=0; i<counter.length; i++) {
						if((counter[i]/c[i])>top) {
							s.szamotania++;
						}
					}
					restartCounter();
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
					if(free.length==0) {
						int suma=0;
						for(int i=0; i<frames.length; i++) {
							suma+=frames[i].length;
						}
						unpause();
					}
				}else {
					lastlyUsed[proc].add(current);
					var=s.procesy[proc].ilBledow;
					a.execute(frames[proc], s, proc);
					if(s.procesy[proc].ilBledow>var)
						counter[proc]++;
				}
			}else {
				proc=a.actualRequest().index;
				//System.out.println(proc);
				lastlyUsed[proc].add(a.actualRequest());
				var=s.procesy[proc].ilBledow;
				a.execute(frames[proc], s);
				if(s.procesy[proc].ilBledow>var)
					counter[proc]++;
			}
			
			c[proc]++;
			
			time++;
		}
		
		//fix();
		System.out.println(s.stats());
		System.out.println("---------------------------------");
		
	}

}
