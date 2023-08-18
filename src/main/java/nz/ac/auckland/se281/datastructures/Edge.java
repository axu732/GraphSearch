package nz.ac.auckland.se281.datastructures;

import java.util.Objects;

/**
 * An edge in a graph that connects two verticies.
 *
 * <p>You must NOT change the signature of the constructor of this class.
 *
 * @param <T> The type of each vertex.
 */
public class Edge<T> {
  private T source;
  private T destination;

  public Edge(T source, T destination) {
    this.source = source;
    this.destination = destination;
  }

  /**
   * Getter for the source vertex of an edge.
   *
   * @return The source vertex of an edge.
   */
  public T getSource() {
    return source;
  }

  /**
   * Getter for the destination vertex of an edge.
   *
   * @return The destination vertex of an edge.
   */
  public T getDestination() {
    return destination;
  }

  // Override the equals method to check if two edges are equal based on their source and
  // destination
  @Override
  public boolean equals(Object o) {
    // If the object is null, return false
    if (o == null) {
      return false;
    }
    // If the object is the same as this object, return true
    if (o == this) {
      return true;
    }
    // Get the classes of both objects and then check if they are the same, if not return false.
    Class otherClass = o.getClass();
    Class thisClass = this.getClass();

    if (otherClass != thisClass) {
      return false;
    }
    // Cast the object to an edge and then check if the source and destination vertices are the same
    Edge<T> otherEdge = (Edge<T>) o;

    if (otherEdge.getSource().equals(this.source)
        && otherEdge.getDestination().equals(this.destination)) {
      return true;
    }
    return false;
  }

  // Override the hashCode method to generate a hash code for an edge based on its source and
  // destination vertices.
  @Override
  public int hashCode() {
    return Objects.hash(source, destination);
  }
}
