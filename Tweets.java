

import java.util.HashSet;

class Tweets {

        private String id;
        private String tweet;

        public Tweets(String id, String tweet){
            this.id =id;
            this.tweet=tweet;
        }
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTweet() {
            return tweet;
        }

        public double getDistance(Tweets centroid){
            HashSet<String> tweetWords = new HashSet<String>();
            HashSet<String> centroidWords = new HashSet<String>();
            int count = 0;
            int commonCount = 0;
            for(String words : this.tweet.split(" ")){
                if(!tweetWords.contains(words)){
                    tweetWords.add(words);
                    count++;
                }
            }
            for(String words : centroid.getTweet().split(" ")){
                if(centroidWords.contains(words)){
                    continue;
                }
                else{
                    centroidWords.add(words);
                }
                if(!tweetWords.contains(words)){
                    count++;
                }
                else{
                    commonCount++;
                }
            }
            double distance = (1.0-((double)commonCount/(double)count));
            return distance;
        }
    }

