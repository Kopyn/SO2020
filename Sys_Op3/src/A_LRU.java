import java.util.LinkedList;
import java.util.Queue;

public class A_LRU extends Algorithm{
	
	Queue<Request> q=new LinkedList<>();
	
	public A_LRU() {
		super();
	}
	
	public A_LRU(Process[] p) {
		super(p);
	}
	
	@Override
	protected void goFifo() {
		if(!occured()) {
			Request r=requests.remove();
			frames[position]=r;
			position++;
			s.liczbaBledow++;
			q.add(r);
		}else {
			requests.remove();
			Request r=q.poll();
			r.secondChance=true;
			q.add(r);
		}
	}

	@Override
	public void execute() {
		if(position<frames.length) {
			goFifo();
		}else {
			if(actualRequest()==null) return;
			if(!occured()) {
				while(q.peek().secondChance==true) {
					Request pulled=q.poll();
					pulled.secondChance=false;
					q.add(pulled);
				}
				Request found=q.poll();
				int i;
				for(i=0; i<frames.length; i++) {
					if(frames[i].page==found.page)
						break;
				}
				found=requests.remove();
				frames[i]=found;
				q.add(found);
				s.liczbaBledow++;
			}else{
				q.remove(actualRequest());
				q.add(requests.remove());
			}
		}
		//show();
	}

	@Override
	public void execute(Request[] f, Statystyka s) {
		// TODO Auto-generated method stub
		
	}

}
