package nz.ac.auckland.se281.datastructures;

/**
 * A queue is a data structure that allows elements in a first in first out order.
 *
 * @param <T> The type of data to store in the queue.
 */
public class Queue<T> {
  private LinkedList<T> queueList = new LinkedList<T>();

  /**
   * Add an item to the end of the queue.
   *
   * @param data The data to add to the queue.
   */
  public void enqueue(T data) {
    queueList.add(data);
  }

  /**
   * Remove an item from the front of the queue and return it.
   *
   * @return The item at the front of the queue.
   */
  public T dequeue() {
    T head = queueList.get(0);
    queueList.remove(0);
    return head;
  }

  /**
   * Return the item at the front of the queue without removing it.
   *
   * @return The item at the front of the queue.
   */
  public T peek() {
    return queueList.get(0);
  }

  /**
   * Checks if a queue is empty.
   *
   * @return <code>true</code> if the queue is empty, <code>false</code> otherwise.
   */
  public boolean isEmpty() {
    if (queueList.size() == 0) {
      return true;
    }
    return false;
  }

  // When we print a queue, we will print the list in a specific format defined in LinkedList.java.
  @Override
  public String toString() {
    return queueList.toString();
  }
}
