import java.io.*;
import java.util.ArrayList;



public class Reader {

    private  static ArrayList <DataNode> train;
    private static int kValue;
    private static ArrayList <DataNode> validation;


    private static ArrayList <DataNode> projectionTArrayList;
    private static ArrayList <DataNode> projectionVArrayList;
    private static ArrayList <DataNode> projectionTestArrayList;


    private static double[][] matrixP;
    private static double[][] matrixT;
    private static double[][] matrixV;
    
    public static void main(String [] args) {

        // The name of the file to open.
        String fileName = "hw2train.txt";
        String testDataFile = "hw2validate.txt";
        String projectionFile = "projection.txt";

        // This will reference one line at a time
        String line = null;

        kValue = 5;
        
        
        matrixT = new double[2000][784];

        train = new ArrayList <DataNode> ();
         validation = new ArrayList <DataNode> ();
         projectionTArrayList = new ArrayList <DataNode> ();
         projectionVArrayList = new ArrayList <DataNode> ();

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
                double[] key = new double[784];

                //debug later check if its -1 or -2
                for(int i=0; i< splited.length - 1; i++){

                    key[i] = Double.parseDouble(splited[i]);
                }


                for(int i =0; i<2000; i++){
                    for(int j=0; j<784;j++){
                        matrixT[i][j] =  Double.parseDouble(splited[j]);
                    }
                }
                




                DataNode node = new DataNode(key, label, kValue);

                train.add(node);

            }  

            // Always close files.
            bufferedReader.close(); 


            matrixV = new double[1000][784];

///*********************   TEST DATA**************************************)
                FileReader fileReader2 = 
                new FileReader(testDataFile);

                BufferedReader bufferedReader2 = 
                new BufferedReader(fileReader2);

                 String line2 = null;

            while((line2 = bufferedReader2.readLine()) != null) {
                String[] splited = line2.split(" ");
                int label = Integer.parseInt(splited[splited.length - 1]);
                double[] key = new double[784];

                //debug later check if its -1 or -2
                for(int i=0; i< splited.length - 1; i++){

                    key[i] = Double.parseDouble(splited[i]);
                }
                for(int i =0; i<1000; i++){
                    for(int j=0; j<784;j++){
                        matrixV[i][j] =  Double.parseDouble(splited[j]);
                    }
                }

                DataNode node = new DataNode(key, label, kValue);

                validation.add(node);
            }  




            int numErrors= 0;
            /*for(int i=0; i<validation.size(); i++){
                for(int j=0; j<train.size(); j++){
                    //get nodes
                    DataNode dnode =  train.get(j);
                    DataNode tnode = validation.get(i);

                    //get distance
                    double distance = getDistance( dnode.getKey(), tnode.getKey());
                    NDPair newPair = new NDPair(dnode, distance);
                    validation.get(i).addNeighbor(newPair);
                }

                int label = majorityLabel(validation.get(i).getNeighbors());
                
                if(label != validation.get(i).getLabel()){

                    numErrors += 1;

                }
            }**/

            //System.out.println(numErrors + "/" + testData.size() );


            // Always close files.
            bufferedReader2.close();


            matrixP = new double[784][20] ;
               FileReader fileReader3 = 
                new FileReader(projectionFile);

                BufferedReader bufferedReader3 = 
                new BufferedReader(fileReader3);

                 String line3 = null;

            while((line3 = bufferedReader3.readLine()) != null) {
                String[] splited = line3.split(" ");

                for(int i =0; i<784; i++){
                    for(int j=0; j<20;j++){
                        matrixP[i][j] =  Double.parseDouble(splited[j]);
                    }
                }
            }  

            double[][] projectionT = new double[matrixT.length][matrixP[0].length];

            for(int i=0; i<matrixT.length; i++){ //0-2000
                for(int j=0; j<matrixP[0].length; j++){ //0-20
                    for(int k=0; k<matrixT[0].length; k++){//0-784
                        projectionT[i][j] = matrixT[i][k] * matrixP[k][j];
                    }
                }
            }

            double[][] projectionV = new double[matrixV.length][matrixP[0].length];

            for(int i=0; i<matrixV.length; i++){
                for(int j=0; j<matrixP[0].length; j++){
                    for(int k=0; k<matrixV[0].length; k++){
                        projectionT[i][j] = matrixT[i][k] * matrixP[k][j];
                    }
                }
            }



            double[] keyT = new double[20];
            for(int i=0; i<2000; i++){
                for(int j=0; j<20;j++){
                    keyT[j] = projectionT[i][j];
                }
            
                int labelT = train.get(i).getLabel();
                DataNode node = new DataNode(keyT, labelT, kValue);
                projectionTArrayList.add(node);
            }

            double[] keyV = new double[20];
            for(int i=0; i<1000; i++){
                for(int j=0; j<20;j++){
                    keyV[j] = projectionV[i][j];
                }
            
                int labelV = validation.get(i).getLabel();
                DataNode node = new DataNode(keyV, labelV, kValue);
                projectionVArrayList.add(node);
            }


            numErrors= 0;
            for(int i=0; i<validation.size(); i++){
                for(int j=0; j<train.size(); j++){
                    //get nodes
                    DataNode dnode =  projectionTArrayList.get(j);
                    DataNode tnode = projectionVArrayList.get(i);

                    //get distance
                    double distance = getDistance( dnode.getKey(), tnode.getKey());
                    NDPair newPair = new NDPair(dnode, distance);
                    projectionVArrayList.get(i).addNeighbor(newPair);
                }

                int label = majorityLabel(projectionVArrayList.get(i).getNeighbors());
                
                if(label != projectionVArrayList.get(i).getLabel()){

                    numErrors += 1;

                }
            }

            System.out.println(numErrors + "/" + projectionVArrayList.size() );
            














            bufferedReader3.close();




        
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

    private static double getDistance(double[] a, double[] b){

        double total = 0;
        for(int i=0; i<a.length; i++){
            double sum = a[i] - b[i];
            double square = sum * sum;
            total = total + square; 
        }

        return Math.sqrt(total);
    }

}