import java.util.ArrayList;

public class DataNode{
	int[] key;
	int label;
	ArrayList<NDPair> neighbors;
	int k;


	public DataNode(int [] _key, int _label, int _k){
		key = _key;
		label = _label;
		k = _k;
		neighbors = new ArrayList<NDPair> ();	
	}
	public void addNeighbor(NDPair e){
		
		if(neighbors.size() < k){
				neighbors.add(e);
				return;
		}

		boolean flag = false;
		NDPair largestNeighbor = neighbors.get(0);
		double newDistance = e.getDist();
		for(int i=0; i<neighbors.size(); i++){	
			if(newDistance <= neighbors.get(i).getDist() ){
				flag = true;
			}
			if(largestNeighbor.getDist() < neighbors.get(i).getDist()){
				largestNeighbor = neighbors.get(i);
			}
		}

		if(flag){
			neighbors.add(e);
			neighbors.remove(largestNeighbor);
		}

		
	}
	public int[] getKey(){
		return key;
	}
	public int getLabel(){
		return label;
	}

	public ArrayList<NDPair> getNeighbors(){
		return neighbors;
	}
}