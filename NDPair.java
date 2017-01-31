import java.util.ArrayList;

public class NDPair{

	double dist;
	DataNode neighbor;
	public NDPair(DataNode n, double distance){
		neighbor = n;
		dist = distance;
	}
	public double getDist(){
		return dist;
	}
	public DataNode getNeighbor(){
		return neighbor;
	}
}