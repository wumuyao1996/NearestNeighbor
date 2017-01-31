import java.util.ArrayList;

public class DataNode{
	int[] key;
	int label;
	ArrayList<NDPair> neighbors;
	public DataNode(int [] key, int label){
	}
	public void addNeighbor(NDPair e){
		neighbors.add(e);
	}
	public int[] getKey(){
		return key;
	}
	public int getLabel(){
		return label;
	}
	public ArrayList<NDPair> getNeighbors(int k){
		return neighbors;
	}
	public ArrayList<NDPair> getNeighbors(){
		return neighbors;
	}
}