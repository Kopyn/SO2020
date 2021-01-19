import java.util.Random;

public class RAND extends Algorithm{
	

	public RAND() {
		super();
	}
	
	public RAND(Process[] p) {
		super(p);
	}
	
	@Override
	public void execute() {
		
		if(position<frames.length) {
			goFifo();
		}else {
		if(actualRequest()==null) return;
		if(!occured()) {
			Random rand=new Random();
			int change=rand.nextInt(frames.length);
			frames[change]=requests.remove();
			s.liczbaBledow++;
		}else
			requests.remove();
		}
		//show();
	}

	@Override
	public void execute(Request[] f, Statystyka s) {
		// TODO Auto-generated method stub
		
	}

}
