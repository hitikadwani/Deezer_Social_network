import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object HighestIndegreeNode {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Highest Indegree Nodes")
      .config("spark.master", "local")
      .getOrCreate()
    val sc = spark.sparkContext

    // Loading the dataset and construct edges RDD
    val lines = sc.textFile("src/main/resources/graph.txt")
    val edges: RDD[Edge[Boolean]] = lines.map { line =>
      val parts = line.split("\\s+")
      Edge(parts(0).toLong, parts(1).toLong, true)
    }

    // Constructing a directed graph from edges
    val graph = Graph.fromEdges(edges, defaultValue = false)

    // Calculating indegree for each vertex
    val indegree = graph.inDegrees

    // Finding the top 5 vertices with the highest indegree
    val top5IndegreeVertices = indegree.takeOrdered(5)(Ordering[Int].reverse.on(_._2))

    // Printing the top 5 vertices with the highest indegree
    println("Top 5 vertices with the highest indegree:")
    top5IndegreeVertices.foreach { case (vertexId, indegree) =>
      println(s"Vertex $vertexId has an indegree of $indegree")
    }

    spark.stop()
  }
}
