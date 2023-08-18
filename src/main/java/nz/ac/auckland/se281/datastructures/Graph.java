package nz.ac.auckland.se281.datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * A graph that is composed of a set of verticies and edges.
 *
 * <p>You must NOT change the signature of the existing methods or constructor of this class.
 *
 * @param <T> The type of each vertex, that have a total ordering.
 */
public class Graph<T extends Comparable<T>> {
  // Intialise private fields for the Graph class
  private Set<T> verticies;
  private Set<Edge<T>> edges;

  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    this.verticies = verticies;
    this.edges = edges;
  }

  /**
   * Checks through a graph to find its roots. We define a root as a vertex that has no incoming
   * edges and is not a destination of any edge or is a vertex that is the minimum value of an
   * equivalence class.
   *
   * @return A set of all the roots in the graph.
   */
  public Set<T> getRoots() {
    Set<T> rootVertices = new LinkedHashSet<>(verticies);

    // Create a map to store the in-degree of each vertex
    Map<T, Integer> inDegreeMap = new HashMap<>();
    for (T vertex : verticies) {
      inDegreeMap.put(vertex, 0);
    }

    // Calculate the in-degree of each vertex
    for (Edge<T> edge : edges) {
      T destination = edge.getDestination();
      inDegreeMap.put(destination, inDegreeMap.get(destination) + 1);
    }

    // Remove vertices with non-zero in-degree or zero out-degree
    for (Edge<T> edge : edges) {
      T source = edge.getSource();
      T destination = edge.getDestination();

      if (inDegreeMap.get(destination) > 0 || !verticies.contains(source)) {
        rootVertices.remove(destination);
      }
    }

    // If the graph is an equivalence relation, we will need to find the minimum value of each
    // equivalence class
    if (isEquivalence()) {
      for (T vertex : verticies) {
        Set<T> classSet = getEquivalenceClass(vertex);
        rootVertices.add(getMinValue(classSet));
      }
    }

    // We will sort the root vertices in ascending order via type casting
    List<T> sortedRootVertices = new ArrayList<>(rootVertices);
    List<Integer> sortIntegers = new ArrayList<>();
    // Add our root vertices to an arraylist of integers
    for (T vertex : sortedRootVertices) {
      sortIntegers.add(Integer.parseInt(vertex.toString()));
    }
    // Call our helper function to sort our arraylist of integers into ascending order
    sortArrayList(sortIntegers);
    sortedRootVertices.clear();
    // Add our sorted integers back to our root vertices set
    for (Integer integer : sortIntegers) {
      sortedRootVertices.add((T) integer.toString());
    }
    // Return our sorted root vertices
    return new LinkedHashSet<>(sortedRootVertices);
  }

  /**
   * Checks if a graph is reflexive. We define a reflexive graph as a graph that has a self loop for
   * every vertex.
   *
   * @return <code> true </code> if the graph is reflexive, <code> false </code> otherwise.
   */
  public boolean isReflexive() {
    for (T vertex : verticies) {
      // We will check if the graph has a self loop for each vertex
      boolean hasSelfLoop = false;
      for (Edge<T> edge : edges) {
        if (edge.getSource().equals(vertex) && edge.getDestination().equals(vertex)) {
          hasSelfLoop = true;
          break;
        }
      }
      // If a vertex does not have a self loop, it is not reflexive.
      if (hasSelfLoop == false) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if a graph is symmetric. We define a symmetric graph as a graph that has an edge from A
   * to B if and only if it also has an edge from B to A.
   *
   * @return <code> true </code> if the graph is symmetric, <code> false </code> otherwise.
   */
  public boolean isSymmetric() {
    // We will loop through every edge, and check if the symmetric edge exists, if not return false
    for (Edge<T> edge : edges) {
      Edge<T> symmetricEdge = new Edge<>(edge.getDestination(), edge.getSource());
      if (!edges.contains(symmetricEdge) && !edge.getDestination().equals(edge.getSource())) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if a graph is transitive. We define a transitive graph as a graph that has an edge from
   * A to B and B to C, then it must also have an edge from A to C.
   *
   * @return <code> true </code> if the graph is transitive, <code> false </code> otherwise.
   */
  public boolean isTransitive() {
    // We will loop through every edge, and check if the transitive edge exists, if not return false
    for (Edge<T> edgeA : edges) {
      for (Edge<T> edgeB : edges) {
        // If it is transistive, then we know the destination of edgeA is the source of edgeB
        if (edgeA.getDestination().equals(edgeB.getSource())
            && !edges.contains(new Edge<>(edgeA.getSource(), edgeB.getDestination()))) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Checks if a graph is anti-symmetric. We know if a graph is anti-symmetric if for an edge there
   * exists a symmetric edge in the set of all edges and the two edges are not equal.
   *
   * @return <code> true </code> if the graph is anti-symmetric, <code> false </code> otherwise.
   */
  public boolean isAntiSymmetric() {
    // We will loop through every edge, checking if an edge and its symmetric edge exist, if they
    // are not equal and the symmetric edge exists, return false
    for (Edge<T> edge : edges) {
      Edge<T> symmetricEdge = new Edge<>(edge.getDestination(), edge.getSource());
      if (!edge.equals(symmetricEdge) && edges.contains(symmetricEdge)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if a graph is an equivalence relation. We define an equivalence relation as a graph that
   * is reflexive, symmetric and transitive.
   *
   * @return <code> true </code> if the graph is an equivalence relation, <code> false </code>
   *     otherwise.
   */
  public boolean isEquivalence() {
    // We will only have an equivalence relation if the graph is reflexive, symmetric and
    // transistive.
    return isReflexive() && isSymmetric() && isTransitive();
  }

  /**
   * Gets the equivalence class of a vertex. We define an equivalence class as a set of vertices in
   * a graph that are equivalent to each other.
   *
   * @param vertex The vertex we want to get the equivalence class of.
   * @return A set of vertices that are equivalent to the vertex passed in.
   */
  public Set<T> getEquivalenceClass(T vertex) {
    // Create a new tree set to store our equivalence class
    Set<T> equivalenceClass = new TreeSet<>();
    // We know there is no equivalence class if the graph is not an equivalence relation
    if (!isEquivalence()) {
      return equivalenceClass;
    }
    // Add our vertex to the equivalence class
    equivalenceClass.add(vertex);

    // We will loop through every edge, and add the source and destination of the edge to the set of
    // our equivalence class if the vertex is the source or destination of the edge
    for (Edge<T> edge : edges) {
      if (vertex.equals(edge.getSource())) {
        equivalenceClass.add(edge.getDestination());
      } else if (vertex.equals(edge.getDestination())) {
        equivalenceClass.add(edge.getSource());
      }
    }

    return equivalenceClass;
  }

  /**
   * Performs a breadth first search on the graph. We will start at the root vertices and then
   * proceed to queue all of the related child verticies We will then visit these child verticies
   * after the root verticies have been visited. We will visit subsequent children until the child
   * queue is empty.
   *
   * @return a list of vertices in the order they were visited.
   */
  public List<T> iterativeBreadthFirstSearch() {
    // Intialize our traversal order, visited set, and queues for our roots and child.
    List<T> traversalOrder = new ArrayList<>();
    Set<T> visited = new LinkedHashSet<>();
    Queue<T> rootQueue = new Queue<>();
    Queue<T> childQueue = new Queue<>();

    // We will enqueue all of our roots to the root queue to be visited
    Set<T> roots = getRoots();
    for (T root : roots) {
      if (!visited.contains(root)) {
        rootQueue.enqueue(root);
        visited.add(root);
      }
    }
    // We will loop through our root queue and child queue until they are both empty
    while (!rootQueue.isEmpty() || !childQueue.isEmpty()) {
      // We will loop through our root queue first, then our child queue
      if (!rootQueue.isEmpty()) {
        T currentVertex = rootQueue.dequeue();
        traversalOrder.add(currentVertex);

        // Enqueue the children of the current vertex for looping through later
        for (Edge<T> edge : edges) {
          if (edge.getSource().equals(currentVertex) && !visited.contains(edge.getDestination())) {
            childQueue.enqueue(edge.getDestination());
            sortQueue(childQueue);
            visited.add(edge.getDestination());
          }
        }
        // Looping for our child queue
      } else {
        T currentVertex = childQueue.dequeue();
        traversalOrder.add(currentVertex);

        // Enqueue the children of the current vertex
        for (Edge<T> edge : edges) {
          if (edge.getSource().equals(currentVertex) && !visited.contains(edge.getDestination())) {
            childQueue.enqueue(edge.getDestination());
            visited.add(edge.getDestination());
          }
        }
      }
    }

    return traversalOrder;
  }

  /**
   * Performs a depth first search on the graph. We will start at the root vertices and then proceed
   * down the tree until we reach a leaf. We will then backtrack to the next child of the root
   * vertex and repeat the process until all vertices have been visited.
   *
   * @return a list of vertices in the order they were visited.
   */
  public List<T> iterativeDepthFirstSearch() {

    // Intialize our traversal order, visited set, and stack for our roots and child.
    List<T> traversalOrder = new ArrayList<>();
    Set<T> visited = new TreeSet<>();
    Stack<T> stack = new Stack<>();

    // Get all of our roots and loop through them, if they are not visited, we will push them to the
    // top of the stack
    Set<T> roots = getRoots();

    for (T root : roots) {
      if (!visited.contains(root)) {
        stack.push(root);
        // Loop through our stack until it is empty.
        while (!stack.isEmpty()) {
          T currentVertex = stack.pop();
          // If the current vertex is not visited, we will add it to our visited set, and traversal
          // order
          if (!visited.contains(currentVertex)) {
            visited.add(currentVertex);
            traversalOrder.add(currentVertex);
            // Using a helper function, we will get the neighbors of the current vertex, and push
            // them to the top of the stack if they are not visited
            List<T> neighbors = getNeighbors(currentVertex);
            List<Integer> intNeighbors = new ArrayList<>();
            // Sort the neighbors in ascending order
            for (int i = 0; i < neighbors.size(); i++) {
              intNeighbors.add(Integer.parseInt(neighbors.get(i).toString()));
            }
            sortArrayList(intNeighbors);
            neighbors.clear();
            for (int i = 0; i < intNeighbors.size(); i++) {
              neighbors.add((T) intNeighbors.get(i).toString());
            }
            neighbors = reverseList(neighbors);
            for (T neighbor : neighbors) {
              if (!visited.contains(neighbor)) {
                stack.push(neighbor);
              }
            }
          }
        }
      }
    }

    return traversalOrder;
  }

  /**
   * Helper function to get all the neighbors of a vertex.
   *
   * @param vertex The vertex we want to get the neighbors of
   * @return A list of all the neighbors of the vertex
   */
  private List<T> getNeighbors(T vertex) {
    // Initialise an array list to store our neighbors
    List<T> neighbors = new ArrayList<>();
    // Loop through all of our edges, and if the source of the edge is equal to the vertex, we will
    // add the destination to our neighbors list.
    for (Edge<T> edge : edges) {
      if (edge.getSource().equals(vertex)) {
        neighbors.add(edge.getDestination());
      }
    }
    return neighbors;
  }

  /**
   * Performs a recursive breadth first search on the graph. We will start at the root vertices and
   * then proceed to queue all of the related child verticies We will then visit these child
   * verticies after the root verticies have been visited. We will visit subsequent children until
   * the child queue is empty.
   *
   * @return traversalOrder a list of vertices in the order they were visited.
   */
  public List<T> recursiveBreadthFirstSearch() {
    // Intialise our traversal order, visited set, and queues for our roots and child.
    List<T> traversalOrder = new ArrayList<>();
    Set<T> visited = new HashSet<>();
    Queue<T> rootQueue = new Queue<>();
    Queue<T> childQueue = new Queue<>();

    // Get the roots and then loop though them, if they are not visited, we will enqueue them to the
    // root queue.
    Set<T> roots = getRoots();
    for (T root : roots) {
      rootQueue.enqueue(root);
      visited.add(root);
    }
    // Call our helper function to recursively loop through our root queue and child queue.

    recursiveBreadthSearchHelper(traversalOrder, visited, rootQueue, childQueue);

    return traversalOrder;
  }

  /**
   * Helper function for recursive breadth first search. We will loop through our root queue and our
   * child queue until they are both empty.
   *
   * @param traversalOrder An array list of the nodes in the order they were visited
   * @param visited A set of the nodes that have been visited already
   * @param rootQueue A queue of the root nodes
   * @param childQueue A queue of the child nodes
   */
  private void recursiveBreadthSearchHelper(
      List<T> traversalOrder, Set<T> visited, Queue<T> rootQueue, Queue<T> childQueue) {
    // If both queues are empty, we will return.
    if (rootQueue.isEmpty() && childQueue.isEmpty()) {
      return;
    }
    // If the root queue is empty and there are still nodes in the child queue, we will loop through
    // and finish the child queue
    if (rootQueue.isEmpty()) {
      while (!childQueue.isEmpty()) {
        T currentVertex = childQueue.dequeue();
        traversalOrder.add(currentVertex);

        for (Edge<T> edge : edges) {
          if (edge.getSource().equals(currentVertex) && !visited.contains(edge.getDestination())) {
            childQueue.enqueue(edge.getDestination());
            visited.add(edge.getDestination());
          }
        }
      }
      // Otherwise we will dequeue the root queue and add it to the traversal order, and then loop
      // through all of the edges and adding the child nodes to the child queue.
    } else {
      T currentVertex = rootQueue.dequeue();
      traversalOrder.add(currentVertex);

      for (Edge<T> edge : edges) {
        if (edge.getSource().equals(currentVertex) && !visited.contains(edge.getDestination())) {
          childQueue.enqueue(edge.getDestination());
          sortQueue(childQueue);
          visited.add(edge.getDestination());
        }
      }
    }
    // Recursively call the function
    recursiveBreadthSearchHelper(traversalOrder, visited, rootQueue, childQueue);
  }

  /**
   * Performs a recursive depth first search on the graph. We will start at the root vertices and
   * then proceed down the tree until we reach a leaf. We will then backtrack to the next child of
   * the root vertex and repeat the process until all vertices have been visited.
   *
   * @return a list of vertices in the order they were visited.
   */
  public List<T> recursiveDepthFirstSearch() {
    // Intialize our traversal order and visited set
    List<T> traversalOrder = new ArrayList<>();
    Set<T> visited = new TreeSet<>();

    // Get the roots of the graph

    Set<T> roots = getRoots();

    // Visit each root vertex and perform a recursive DFS
    for (T root : roots) {
      recursiveDepthSearchHelper(root, visited, traversalOrder);
    }

    return traversalOrder;
  }

  /**
   * Helper function to perform the recursive DFS search.
   *
   * @param vertex the current vertex we are visiting
   * @param visited the total set of vertices that have been visited already
   * @param traversalOrder the order in which the vertices were visited
   */
  private void recursiveDepthSearchHelper(T vertex, Set<T> visited, List<T> traversalOrder) {
    // Add the current vertex to our visited set and traversal order
    visited.add(vertex);
    traversalOrder.add(vertex);

    List<T> neighbours = getNeighbors(vertex);
    // Sort the neighbors in ascending order
    List<Integer> intNeighbors = new ArrayList<>();
    for (int i = 0; i < neighbours.size(); i++) {
      intNeighbors.add(Integer.parseInt(neighbours.get(i).toString()));
    }
    sortArrayList(intNeighbors);
    neighbours.clear();
    for (int i = 0; i < intNeighbors.size(); i++) {
      neighbours.add((T) intNeighbors.get(i).toString());
    }

    // Visit all neighbors of the current vertex and perform a recursive DFS on them if they have
    // not been visited yet
    for (T neighbour : neighbours) {
      if (!visited.contains(neighbour)) {
        recursiveDepthSearchHelper(neighbour, visited, traversalOrder);
      }
    }
  }

  /**
   * A Helper function that that sorts an array list of type Integer in ascending order.
   *
   * @param list The list to be sorted
   * @return The sorted list
   */
  private List<Integer> sortArrayList(List<Integer> list) {
    // We will loop through the list and swap elements if the current element is greater than the
    // other element
    for (int i = 0; i < list.size(); i++) {
      for (int j = 0; j < list.size(); j++) {
        if (list.get(i) < list.get(j)) {
          int temp = list.get(i);
          list.set(i, list.get(j));
          list.set(j, temp);
        }
      }
    }
    // Return the sorted list
    return list;
  }

  /**
   * A helper function that sorts a queue in ascending order.
   *
   * @param queue The queue to be sorted
   * @return The sorted queue
   */
  private Queue<T> sortQueue(Queue<T> queue) {
    // We will dequeue all elements from the queue and add them to an array list
    List<Integer> list = new ArrayList<>();
    while (!queue.isEmpty()) {
      list.add(Integer.parseInt(queue.dequeue().toString()));
    }
    // Sort the array list and add the elements back into the queue in ascending order
    list = sortArrayList(list);
    for (Integer element : list) {
      queue.enqueue((T) element.toString());
    }
    return queue;
  }

  /**
   * A Helper function that finds the minimum value in a set.
   *
   * @param set The set to be searched
   * @return The minimum value in the set
   */
  private T getMinValue(Set<T> set) {
    T min = null;
    for (T element : set) {
      if (min == null || element.compareTo(min) < 0) {
        min = element;
      }
    }
    return min;
  }

  /**
   * A helper function that will reverse the order of a list.
   *
   * @param list List to be reversed
   * @return The reversed list
   */
  private List<T> reverseList(List<T> list) {
    List<T> reversedList = new ArrayList<>();
    for (int i = list.size() - 1; i >= 0; i--) {
      reversedList.add(list.get(i));
    }
    return reversedList;
  }
}
