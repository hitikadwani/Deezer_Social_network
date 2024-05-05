import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object ClusteringCoefficient {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Clustering Coefficient Example")
      .config("spark.master", "local")
      .getOrCreate()
    val sc = spark.sparkContext

    // Loading the dataset and construct edges RDD
    val lines = sc.textFile("src/main/resources/graph.txt")
    val edges: RDD[Edge[Double]] = lines.map { line =>
      val parts = line.split("\\s+").map(_.toLong)
      Edge(parts(0), parts(1), parts(2).toDouble)
    }

    // Constructing a graph from edges
    val graph = Graph.fromEdges(edges, defaultValue = true)

    // Computing triangle counts for each vertex
    val triCounts = graph.triangleCount().vertices.map { case (vertexId, count) =>
      (vertexId, count / 2) // Divide by 2 because each triangle is counted 3 times
    }

    // Computing degree for each vertex
    val degrees = graph.degrees

    // Computing clustering coefficient for each vertex
    val clusteringCoefficients = triCounts.join(degrees).map { case (vertexId, (triCount, degree)) =>
      val possiblePairs = degree * (degree - 1) / 2.0
      val clusteringCoefficient = if (possiblePairs == 0) 0.0 else triCount / possiblePairs
      (vertexId, clusteringCoefficient)
    }

    // Finding the vertex with the highest clustering coefficient
    val maxClusteringCoefficient = clusteringCoefficients.max()(Ordering[Double].on(_._2))

    // Printing the vertex with the highest clustering coefficient
    println(s"Vertex ${maxClusteringCoefficient._1} has the highest clustering coefficient: ${maxClusteringCoefficient._2}")

    spark.stop()
  }
}
