import java.io.*;
import java.util.ArrayList;



public class Reader {

    private  static ArrayList <DataNode> dnal;
    private static int kValue;
    private static ArrayList <DataNode> testData;
    
    public static void main(String [] args) {

        // The name of the file to open.
        String fileName = "hw2train.txt";
        String testDataFile = "hw2train.txt";

        // This will reference one line at a time
        String line = null;

        kValue = 3;
        
        
        dnal = new ArrayList <DataNode> ();
         testData = new ArrayList <DataNode> ();

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                
                
                String[] splited = line.split(" ");
                int label = Integer.parseInt(splited[splited.length - 1]);
                int[] key = new int[784];

                //debug later check if its -1 or -2
                for(int i=0; i< splited.length - 1; i++){
                    key[i] = Integer.parseInt(splited[i]);
                }

                DataNode node = new DataNode(key, label, kValue);

                dnal.add(node);

            }  

                        // Always close files.
            bufferedReader.close(); 


///*********************   TEST DATA**************************************)
                FileReader fileReader2 = 
                new FileReader(testDataFile);

                BufferedReader bufferedReader2 = 
                new BufferedReader(fileReader2);

                 String line2 = null;

            while((line2 = bufferedReader2.readLine()) != null) {
                String[] splited = line2.split(" ");
                int label = Integer.parseInt(splited[splited.length - 1]);
                int[] key = new int[784];

                //debug later check if its -1 or -2
                for(int i=0; i< splited.length - 1; i++){
                    key[i] = Integer.parseInt(splited[i]);
                }

                DataNode node = new DataNode(key, label, kValue);

                testData.add(node);
            }  




            int numErrors= 0;
            for(int i=0; i<testData.size(); i++){
                for(int j=0; j<dnal.size(); j++){
                    //get nodes
                    DataNode dnode =  dnal.get(j);
                    DataNode tnode = testData.get(i);

                    //get distance
                    double distance = getDistance( dnode.getKey(), tnode.getKey());
                    NDPair newPair = new NDPair(dnode, distance);
                    testData.get(i).addNeighbor(newPair);
                }

                int label = majorityLabel(testData.get(i).getNeighbors());
                
                if(label != testData.get(i).getLabel()){

                    numErrors += 1;

                }
            }

            System.out.println(numErrors + "/" + testData.size() );


            // Always close files.
            bufferedReader2.close(); 




        
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
    }


    private static void setDistance(ArrayList <DataNode> list){
        

        for(int i=0; i<list.size(); i++){
             for(int j=0; j<i; j++){

                DataNode a = list.get(i);
                DataNode b = list.get(j);
                double distance = getDistance(a.getKey(), b.getKey()); 

                NDPair aPair = new NDPair(b,distance );
                a.addNeighbor(aPair);

                NDPair bPair = new NDPair(a,distance );
                b.addNeighbor(bPair);


             }




        }
    } 


    private static int majorityLabel(ArrayList <NDPair> neighbors){
        
        int maxCount = 0;
        int maxLabel = neighbors.get(0).getNeighbor().getLabel();;
        int count;
        int label;


        for(int i=0; i<neighbors.size(); i++){
            count = 0;
            label = neighbors.get(i).getNeighbor().getLabel();
            //System.out.println("actual label " + label);
            for(int j=0; j<neighbors.size(); j++){
          //      System.out.println("comparing to label " + neighbors.get(j).getNeighbor().getLabel());
                if(neighbors.get(j).getNeighbor().getLabel() == label){
                    count++;
                }
                

            }

            if(count >= maxCount){
                maxLabel = label;
                maxCount = count;
            }

        }

        //System.out.println(" maxLabel = "+ maxLabel);
        //System.out.println("");
        //System.out.println("");

        return maxLabel;
    }

    private static double getDistance(int[] a, int[] b){

        double total = 0;
        for(int i=0; i<a.length; i++){
            double sum = a[i] - b[i];
            double square = sum * sum;
            total = total + square; 
        }

        return Math.sqrt(total);
    }

}