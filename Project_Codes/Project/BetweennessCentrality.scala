import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object BetweennessCentrality {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Betweenness Centrality")
      .config("spark.master", "local")
      .getOrCreate()
    val sc = spark.sparkContext

    // Loading  the dataset and constructing  edges RDD
    val lines = sc.textFile("src/main/resources/graph.txt")
    val edges: RDD[Edge[Double]] = lines.map { line =>
      val parts = line.split("\\s+")
      Edge(parts(0).toLong, parts(1).toLong, parts(2).toDouble) // Assuming the weight is the third element in each line
    }

    // Constructing a graph from edges
    val graph = Graph.fromEdges(edges, defaultValue = Double.PositiveInfinity)

    // Computing betweenness centrality for each vertex
    val betweenness = graph.aggregateMessages[Double](
      triplet => {

        triplet.sendToDst(1.0)
      },
      (a, b) => a + b, // Merging  messages by summing them
      TripletFields.None
    )

    // Normalizing betweenness centrality values
    val maxCentrality = betweenness.map(_._2).max()
    val normalizedBetweenness = betweenness.mapValues(_ / maxCentrality)

    // Finding the top 5 vertices with the highest centrality
    val top5Vertices = normalizedBetweenness.top(5)(Ordering[Double].on(_._2))

    // Printing the top 5 vertices with the highest centrality
    println("Top 5 vertices with the highest centrality:")
    top5Vertices.foreach { case (vertexId, centrality) =>
      println(s"Vertex $vertexId has centrality: $centrality")
    }

    spark.stop()
  }
}
