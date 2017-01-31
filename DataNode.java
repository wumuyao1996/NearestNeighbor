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
}