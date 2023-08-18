package nz.ac.auckland.se281.datastructures;

/**
 * A linked list is a data structure that stores a list of items via nodes.
 *
 * @param <T> The type of data to store in the list.
 */
public class LinkedList<T> {
  private Node<T> head = null;
  private Node<T> tail = null;
  private int listcount = 0;

  /**
   * Add an item to the end of the list.
   *
   * @param data The data to add to the list.
   */
  public void add(T data) {
    Node<T> temp = new Node<T>(data);
    // If there is no current head, set the new node as the head.
    if (head == null) {
      head = temp;
    } else {
      // Otherwise, set the new node as the tail and link it to the previous node.
      tail.setNext(temp);
      temp.setPrev(tail);
    }
    // Set the new node as the tail.
    tail = temp;
    listcount++;
  }

  /**
   * Retrive a node from the list at the specified index.
   *
   * @param index The index of the node to get
   * @return The data at the specified index.
   */
  public T get(int index) {
    Node<T> current = head;
    // If the index is 0, return the head.
    if (index == 0) {
      return current.getData();
    }
    // Otherwise, iterate through the list until we reach the index.
    if (head != null) {
      for (int i = 0; i < index; i++) {
        if (current.getNext() == null) {
          return null;
        }
        current = current.getNext();
      }
    }
    // Return the data at the index.
    return current.getData();
  }

  /**
   * Insert an item at the specified index.
   *
   * @param index The index to insert the item at.
   * @param data The data to insert.
   */
  public void insert(int index, T data) {
    Node<T> tempNode = new Node<T>(data);
    Node<T> current = head;
    // If there is no head, set the new node as head and tail.
    if (current == null) {
      head = tempNode;
      tail = tempNode;
      listcount++;
      return;
    }
    // If the index is 0, set the new node as the head and link it to the previous head.
    if (index == 0) {
      tempNode.setNext(current);
      current.setPrev(tempNode);
      head = tempNode;
      listcount++;
      return;
    }
    // Otherwise, iterate through the list until we reach the index.
    if (current != null) {
      for (int i = 0; i < index - 1; i++) {
        current = current.getNext();
      }
    }
    // Set the new node as the next node and link it to the previous node.
    current.setNext(tempNode);
    tempNode.setPrev(current);
    tempNode.setNext(current.getNext());
    current.getNext().setPrev(tempNode);

    listcount++;
  }

  /**
   * Remove an item from the list.
   *
   * @param index The index of the item to remove.
   */
  public void remove(int index) {
    Node<T> current = head;
    // If the index is 0, set the next node as the head and remove the previous head.
    if (index == 0) {
      Node<T> newHead = head.getNext();
      head = newHead;
      listcount--;
      return;
    }
    // Otherwise, iterate through the list until we reach the index.
    for (int i = 0; i < index - 1; i++) {
      current = current.getNext();
    }
    // If the next node is not null, set the next node as the next next node.
    if (current.getNext() != null && current.getNext().getNext() != null) {
      Node<T> nextNext = current.getNext().getNext();
      current.setNext(nextNext);
      nextNext.setPrev(current);
    } else {
      current.setNext(null);
    }
    // Decrement the list count.
    listcount--;
  }

  /**
   * Return the size of the list.
   *
   * @return The size of the list.
   */
  public int size() {
    return listcount;
  }

  /**
   * Check if the list is empty.
   *
   * @return <code>true</code> if the list is empty, <code>false</code> otherwise.
   */
  public boolean isEmpty() {
    if (listcount == 0) {
      return true;
    }
    return false;
  }

  /**
   * Return the index of a node with the specified data.
   *
   * @param data The data to search for.
   * @return The index of the node with the specified data.
   */
  public int indexOf(T data) {
    Node<T> current = head;
    for (int i = 0; i < listcount; i++) {
      if (current.getData().equals(data)) {
        return i;
      }
      current = current.getNext();
    }
    return -1;
  }

  @Override
  public String toString() {
    // We will create a string builder the list of items.
    Node<T> current = head;
    StringBuilder string = new StringBuilder();
    string.append("[");
    // If the list is empty, return an empty string.
    if (listcount == 0) {
      return ("[]");
    }
    // Otherwise, iterate through the list and add each item to the string.
    for (int i = 0; i < listcount; i++) {
      string.append(current.getData());
      if (current.getNext() != null) {
        string.append(", ");
        current = current.getNext();
      }
    }
    string.append("]");
    return string.toString();
  }
}
