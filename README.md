## Spark-Based Exploratory Analysis On Deezer Social Network

Group Members: 

HITIK ADWANI - 22BDS029
LAKSHYA BABEL - 222BDS033
ABHIJIT SINGH - 22BDS054
SURYANSH AYUSH - 22BDS057

Repository structure:

The original Dataset graph.txt is uploaded in Repository under test_dataset folder. All The Project Codes are also updated in the Repository under Project_Codes section.The folders like target,project
contain important dependencies to make all these codes smoothly.

Configuration

We have used Apache Spark for this Project and our Primary Programming Language  is Scala. We have build.sbt which is used to run Scala Codes and it contains multiple dependencies like Graph-X,SQl,SPARK-CORE etc. We have also used Hadoop Distributed File System (HDFS) as Input Storage system on two nodes.HDFS is used for inputing data to the codes and then jar file is created.We are using built-in plugin in IntelliJ IDE to make it run.

Introduction:

This Dataset is acquired from Deezer in November 2017. The Dataset comprises information regarding Interactions of Users on Deezer Platform from Romania. This network helps us to understand how users interact,
by analyzing key metrics like Betweenness Centrality and Clustering Coefficient, Page Rank and Indegree. This will help us understand how people connect and share music on Deezer,a music streaming Platform.


Network Properties:

This dataset is collected from SNAP which contains 41,773 nodes representing Artists and Users,Interconnected by 1,25,826 Edges denoting Interactions between them.

Functionalities:

Indegree  
Betweenness Centrality  
Clustering Coefficient  
Page rank  


Link to Report: https://drive.google.com/file/d/1ybLiE2KjHBpns8EZPPzJNdJDMGcWYrCf/view?usp=drive_link
