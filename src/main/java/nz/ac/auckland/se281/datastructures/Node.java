package nz.ac.auckland.se281.datastructures;

/**
 * A node is a part of a linked list that stores data and a reference to the next node and the
 * previous node.
 *
 * @param <T> The type of data to store in the node.
 */
public class Node<T> {
  private T data;
  private Node<T> next;
  private Node<T> previous;

  public Node(T data) {
    this.data = data;
    next = null;
  }

  public Node() {}

  /**
   * Get the data stored in the node.
   *
   * @return The data stored in the node.
   */
  public T getData() {
    return data;
  }

  /**
   * Set the data stored in the node.
   *
   * @param data The data to store in the node.
   */
  public void setData(T data) {
    this.data = data;
  }

  /**
   * Get the next node in the list.
   *
   * @return The next node in the list.
   */
  public Node<T> getNext() {
    return next;
  }

  /**
   * Set the next node in the list.
   *
   * @param next The next node in the list.
   */
  public void setNext(Node<T> next) {
    this.next = next;
  }

  /**
   * Get the previous node in the list.
   *
   * @return The previous node in the list.
   */
  public Node<T> getPrev() {
    return previous;
  }

  /**
   * Set the previous node in the list.
   *
   * @param prev The previous node in the list.
   */
  public void setPrev(Node<T> prev) {
    this.previous = prev;
  }

  @Override
  public String toString() {
    return data.toString();
  }
}
