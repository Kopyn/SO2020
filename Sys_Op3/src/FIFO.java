
public class FIFO extends Algorithm{
	
	
	public FIFO() {
		super();
		
	}
	
	public FIFO(Process[] p) {
		super(p);
	}
	
	@Override
	public void execute() {
		if(actualRequest()==null) return;
		if(!occured()) {
			frames[position]=requests.remove();
			position++;
			position=position%frames.length;
			s.liczbaBledow++;
		}else{
			requests.remove();
		}
		//show();
	}

	@Override
	public void execute(Request[] f, Statystyka s) {
		// TODO Auto-generated method stub
		
	}

}
