
public class OPT extends Algorithm{
	
	public OPT() {
		super();
	}
	
	public OPT(Process[] p) {
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
			}else
				requests.remove();
		}
		//show();
	}
	
	//metoda zwracajaca index do ktorego najdluzej nie bedzie odwolania w celu podmiany odwolania w metodzie execute
	public int longest() {
		int pos=0;
		int ret=0;
		for(int i=0; i<frames.length; i++) {
			int pos1=requests.indexOf(frames[i]);
			if(pos1==-1) {
				ret=i;
				break;
			}
			if(pos<pos1) {
				pos=pos1;
				ret=i;
			}
		}
		
		return ret;
	}

	@Override
	public void execute(Request[] f, Statystyka s) {
		// TODO Auto-generated method stub
		
	}

}
