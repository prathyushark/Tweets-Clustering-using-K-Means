
import java.util.ArrayList;

public class Cluster {
    private int id;
    private ArrayList<Tweets> totalTweetsList = new ArrayList<Tweets>();
    private Tweets centroid;
    public Cluster(int id, Tweets centroid){
        this.id =id;
        this.centroid=centroid;
    }
    

    public boolean Recompute(){
        Tweets newCent=null;
        double dist=Double.MAX_VALUE;
        for(Tweets tweet: this.totalTweetsList)
        {
            double tmpDist=0;
            for(Tweets tweetTmp: this.totalTweetsList)
            {
                tmpDist += tweet.getDistance(tweetTmp);
            }
            if(tmpDist < dist)
            {
                dist = tmpDist;
                newCent = tweet;
            }
        }
        if(this.getCentroid() != newCent)
        {
            this.setCentroid(newCent);
            return true;
        }
        return false;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Tweets> getTweetList() {
        return totalTweetsList;
    }

    public void setTweetList(ArrayList<Tweets> pointList) {
        this.totalTweetsList = pointList;
    }

    public Tweets getCentroid() {
        return centroid;
    }

    public void setCentroid(Tweets centroid) {
        this.centroid = centroid;
    }

}
