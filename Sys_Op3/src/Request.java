import java.io.Serializable;
import java.util.Random;

public class Request implements Serializable, Comparable<Request>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public int index;//index informuje z ktorego procesu jest to zgloszenie
	public int page;
	public boolean secondChance;
	public int timeArrived;
	
	public Request() {
		page=-1;
		secondChance=true;
	}
	
	public Request(int p, int i, int time) {
		page=p;
		secondChance=true;
		index=i;
		timeArrived=time;
	}

	@Override
	public String toString() {
		return  ""+page;
	}
	
	@Override
	public int hashCode() {
		return page;
	}

	@Override
	public boolean equals(Object r) {
		Request r2=(Request)r;
		return (page==r2.page)?true:false;
	}

	@Override
	public int compareTo(Request o) {
		Request r=(Request)o;
		if(timeArrived<r.timeArrived) {
			return 1;
		}else if(timeArrived>r.timeArrived) {
			return -1;
		}else
			return 0;
	}
	

}
