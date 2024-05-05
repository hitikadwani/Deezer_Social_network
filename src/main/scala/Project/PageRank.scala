import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object PageRank {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("PageRank Example")
      .config("spark.master", "local")
      .getOrCreate()
    val sc = spark.sparkContext

    // Loading the dataset and construct edges RDD
    val lines = sc.textFile("src/main/resources/graph.txt")
    val edges: RDD[Edge[Boolean]] = lines.map { line =>
      val parts = line.split("\\s+")
      Edge(parts(0).toLong, parts(1).toLong, true)
    }

    // Constructing a graph from edges
    val graph = Graph.fromEdges(edges, defaultValue = false)

    // Computing PageRank
    val pageRank = graph.pageRank(tol = 0.0001).vertices

    // Finding the top 5 vertices with the highest PageRank
    val top5PageRankVertices = pageRank.takeOrdered(5)(Ordering[Double].reverse.on(_._2))

    // Printing the top 5 vertices with the highest PageRank
    println("Top 5 vertices with the highest PageRank:")
    top5PageRankVertices.foreach { case (vertexId, rank) =>
      println(s"Vertex $vertexId has PageRank $rank")
    }

    spark.stop()
  }
}
