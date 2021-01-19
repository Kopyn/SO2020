import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;

public class Process implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LinkedList<Request> list;//lista odwolan
	public int ilBledow;
	
	
	public double ilStron() {
		HashSet<Request> set=new HashSet<>(list);
		
		return set.size();
	}
	
	public Process() {
		list=new LinkedList<>();
		ilBledow=0;
	}
	
	public Process(LinkedList<Request> l) {
		list=l;
		ilBledow=0;
	}

}
