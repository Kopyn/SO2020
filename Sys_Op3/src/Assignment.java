
public abstract class Assignment {
	
	Request[][] frames;
	Statystyka s;
	public static int nrOfProcesses;
	public static int nrOfFrames;
	Process[] processes;
	
	public static int timeSlit;
	
	public static double top;
	public static double bottom;
	
	public double[] counter;
	public double[] c;
	
	int incremented;
	
	public Assignment() {
		s=new Statystyka(nrOfProcesses);
		//processes=new Process[nrOfProcesses];
		processes=Generator.g(nrOfProcesses);
		s.procesy=new Process[nrOfProcesses];
		counter=new double[nrOfProcesses];
		c=new double[nrOfProcesses];
		restartCounter();
		for(int i=0; i<s.procesy.length; i++) {
			s.procesy[i]=new Process();
		}
		
		
		
//		for(int i=0; i<processes.length; i++) {
//			processes[i]=Generator.generateProcesses(i);
//		}
	}
	
	public Assignment(Process[] p) {
		processes=p;
	}
	
	protected void restartCounter() {
		for(int i=0; i<counter.length; i++) {
			counter[i]=0;
			c[i]=0;
		}
	}
	
	public abstract void assign();
	
	public abstract void simulate();
	
	public abstract void nazwa();
	

}
