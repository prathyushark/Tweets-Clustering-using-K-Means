
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {
	
    public static HashMap<String,Tweets> totalTweetsList;
    public static ArrayList<Cluster> clusterList;

    public static void main(String[] args) {

        int k =Integer.parseInt(args[0]);
        String initialSeed =args[1];
        String fileName=args[2];
        String outputFile =args[3];        
        if(k>25) k=25;
        totalTweetsList =new HashMap<String,Tweets>();
        clusterList =new ArrayList<Cluster>();
        Main kmean =new Main();
        kmean.getData(fileName,initialSeed, k);
        kmean.populateCluster();
        kmean.computeCluster();
        kmean.writeIntoOutputFile(outputFile);
        System.out.println("Output written into : "+ outputFile);
    }


    public void getData(String fileName,String initialSeed, int k){
        String line =null;
        Tweets centroid=null;
        JSONObject jsonObject ;
        try{
            JSONParser parser = new JSONParser();
            BufferedReader bufferedReader1 = new BufferedReader(new FileReader(fileName));
            while((line = bufferedReader1.readLine()) != null) {
                jsonObject = (JSONObject)parser.parse(line);
                totalTweetsList.put(jsonObject.get("id").toString(),new Tweets(jsonObject.get("id").toString(), (String)jsonObject.get("text")));
            }

            BufferedReader bufferedReader2 = new BufferedReader(new FileReader(initialSeed));
            int count =1;
            while((line = bufferedReader2.readLine()) != null) {
                if(line.endsWith(",")){
                    line = line.substring(0, line.length()-1);
                }
                centroid=totalTweetsList.get(line);
                if(count <= k){
                    clusterList.add(new Cluster(count,centroid));
                    count++;
                }
                else{
                    break;
                }
            }
            bufferedReader1.close();
            bufferedReader2.close();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void populateCluster() {
        Iterator it = totalTweetsList.entrySet().iterator();
        Map.Entry pair = null;
        Tweets tweet;
        while (it.hasNext()) {
            pair = (Map.Entry) it.next();
            tweet = (Tweets) pair.getValue();
            Cluster bestCluster = null;
            double dist = 500;
            for (Cluster cluster : clusterList) {
                double tmpDist = tweet.getDistance(cluster.getCentroid());
                if (tmpDist < dist) {
                    dist = tmpDist;
                    bestCluster = cluster;
                }

            }
            bestCluster.getTweetList().add(tweet);
        }
    }

    public void computeCluster(){
        boolean change =false;
        do{
            change =false;
            for(Cluster cluster : clusterList){

                if(cluster.Recompute()){
                    change =true;
                }
            }
            if(change){
                for(Cluster cluster : clusterList){
                    cluster.getTweetList().clear();
                }
                populateCluster();
            }
        }while(change);
    }


    public void writeIntoOutputFile(String outputPath){
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            String content = "ClusterId \tTweets seperated by comma belonging to this cluster\n";
            fw = new FileWriter(outputPath);
            bw = new BufferedWriter(fw);
            for(Cluster cluster : clusterList){
                content += cluster.getId()+"\t\t";
                for(Tweets tweets : cluster.getTweetList()){
                    content += tweets.getId()+",";
                }
                content = content.substring(0,content.lastIndexOf(",")) + "\n";
            }
            content += "SSE : "+sse();
            System.out.println(content);
            bw.write(content);
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();

            }

        }
    }

    public double sse(){
        double distSq = 0;
        for(Cluster cluster: clusterList){
            Tweets centroid = cluster.getCentroid();
            for(Tweets tweet: cluster.getTweetList()){
                double dist = tweet.getDistance(centroid);
                distSq+= Math.pow(dist, 2);
            }
        }
        return distSq;
    }
}

