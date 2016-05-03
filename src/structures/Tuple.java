package structures;

public class Tuple<First, Second> implements Pair<First, Second> {

	First first;
	Second second;
	
	public Tuple(){
		this.first = null;
		this.second = null;
	}
	
	public Tuple(First first, Second second) {
		// TODO Auto-generated constructor stub
		this.first = first;
		this.second = second;
	}

	@Override
	public First getFirst() {
		// TODO Auto-generated method stub
		return first;
	}

	@Override
	public Second getSecond() {
		// TODO Auto-generated method stub
		return second;
	}

	@Override
	public void setFirst(First first) {
		// TODO Auto-generated method stub
		this.first = first;
	}

	@Override
	public void setSecond(Second second) {
		// TODO Auto-generated method stub
		this.second = second;
	}

}
