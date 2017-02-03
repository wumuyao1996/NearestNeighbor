import java.io.*;
import java.util.ArrayList;



public class Reader {

    private  static ArrayList <DataNode> dnal;
    private static int kValue;
    private static ArrayList <DataNode> testData;
    
    public static void main(String [] args) {

        // The name of the file to open.
        String fileName = "hw2train.txt";
        String testDataFile = "hw2validate.txt";

        // This will reference one line at a time
        String line = null;

        kValue = 1;
        ArrayList
        
        dnal = new ArrayList <DataNode> ();

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


///*********************   TEST DATA**************************************)
                fileReader = 
                new FileReader(testDataFile);

                bufferedReader = 
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

                testData.add(node);
            }  



            for(int i=0; i<testData.size(); i++){
                for(int j=0; j<dnal.size(); j++){
                    DataNode dnode = dnal.get(j);
                    double distance = getDistance(dnode.getKey(), testData.get(i).getKey());
                    NDPair newPair = new NDPair(dnode, distance);
                    testData.get(i).addNeighbor(newPair);
                }

            }








            // Always close files.
            bufferedReader.close();         
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

    private static double getDistance(int[] a, int[] b){

        double total = 0;
        for(int i=0; i<a.length; i++){
            double sum = a[i] + b[i];
            double square = sum * sum;
            total = total + square; 
        }

        return Math.sqrt(total);
    }

}