Language used: Java

Assumptions: 
The maximum # of clusters is taken 25.

External jars used:
json-simple-1.1.1.jar to read the json file.(included in the part 2 folder)

Steps to run the code:
> In Eclipse:
	> Add json-simple-1.1.1.jar into the library jars.(Project properties -> java build path -> libraries -> add jar -> give the path of this jar ->apply)
	> Run Main.java with arguments  <numberOfClusters> <initialSeedsFile> <TweetsDataFile> <outputFile>

> Through command promt,run the following command to run:
    > javac -cp "../json-simple-1.1.1.jar"  *.java
	> java  Main arg0 arg1 arg2
   where the arg0 number of clusters, arg1 is Absolute path of data set, arg2 is Absolute path of output file.
