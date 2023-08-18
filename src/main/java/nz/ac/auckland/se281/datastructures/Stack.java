package nz.ac.auckland.se281.datastructures;

/**
 * A stack is a data structure that allows elements in a last in first out order.
 *
 * @param <T> The type of data to store in the queue.
 */
public class Stack<T> {
  private LinkedList<T> stackList = new LinkedList<T>();

  /**
   * Add an item to the top of the stack.
   *
   * @param data The data to add to the stack.
   */
  public void push(T data) {
    stackList.insert(0, data);
  }

  /**
   * Remove an item from the top of the stack and return it.
   *
   * @return The item at the top of the stack.
   */
  public T pop() {
    T head = stackList.get(0);
    stackList.remove(0);
    return head;
  }

  /**
   * Return the item at the top of the stack without removing it.
   *
   * @return The item at the top of the stack.
   */
  public T peek() {
    return stackList.get(0);
  }

  /**
   * Return the size of the stack.
   *
   * @return The size of the stack.
   */
  public int size() {
    return stackList.size();
  }

  /**
   * Checks if a stack is empty.
   *
   * @return <code>true</code> if the stack is empty, <code>false</code> otherwise.
   */
  public boolean isEmpty() {
    if (stackList.size() == 0) {
      return true;
    }
    return false;
  }

  /**
   * Override the toString method to print the stack in a specific format defined in
   * LinkedList.java.
   *
   * @return The stack in a specific format defined in LinkedList.java.
   */
  @Override
  public String toString() {
    return stackList.toString();
  }
}
