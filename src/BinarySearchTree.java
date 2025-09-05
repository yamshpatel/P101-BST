


/**
 * This class represents a binary search tree that stores objects of type T in
 * sorted order using the comparable interface. Supports insertion, searching,
 * size, empty checks, and clearing.
 */
public class BinarySearchTree<T extends Comparable<T>> implements SortedCollection<T> {

  private int hi;
  /**
   * The root of this BinarySearchTree.
   */
  protected BinaryNode<T> root = null;

  /**
   * Inserts a new data value into the sorted collection.
   * @param data the new value being inserted
   * @throws NullPointerException if data argument is null, we do not allow
   *         null values to be stored within a SortedCollection, so an
   *         exception is thrown
   */
  @Override
  public void insert(T data) throws NullPointerException {
    // Throw error if null data
    if (data == null) {
      throw new NullPointerException("Data is null");
    }
    // Make a new node with the data
    BinaryNode<T> newNode = new BinaryNode<T>(data);
    // Add it to the tree
    insertHelper(newNode, root);
  }

  /**
   * Performs the naive binary search tree insert algorithm to recursively
   * insert the provided newNode (which has already been initialized with a
   * data value) into the provided tree/subtree. When the provided subtree
   * is null, this method does nothing.
   *
   * @param newNode the node to insert to the BST
   * @param subtree the root node of the current subtree
   */
  protected void insertHelper(BinaryNode<T> newNode, BinaryNode<T> subtree) {
    // If subtree is null, do nothing
    if (subtree == null) {
      return;
    }

    // Compare subtree's data to the newNode. If less than or duplicate, add to the left subtree
    if (newNode.getData().compareTo(subtree.getData()) <= 0) {
      // If the left is null, we can simply set the new node there
      if (subtree.getLeft() == null) {
        subtree.setLeft(newNode);
        // exit method after setting new node
        return;
      }
      // Recursively call insertHelper and keep going left
      insertHelper(newNode, subtree.getLeft());
    }
    // Same idea but for the right. If the newNode is greater than the subtree's root
    // we know we have to go right.
    if (newNode.getData().compareTo(subtree.getData()) > 0) {
      // If the right subtree is null, we can simply set the new node there.
      if (subtree.getRight() == null) {
        subtree.setLeft(newNode);
        // exit method after we set new node
        return;
      }
      // Recursively move to the right
      insertHelper(newNode, subtree.getLeft());
    }
    // This will never be reached because the insert method makes sure this method
    // only gets called if a node can be added to the tree.
    return;
  }

  /**
   * Check whether data is stored in the tree.
   * @param data the value to check for in the collection
   * @return true if the collection contains data one or more times,
   *         and false otherwise
   * @throws NullPointerException if data argument is null
   */
  @Override
  public boolean contains(Comparable<T> data) throws NullPointerException {
    // Throw exception if data is null
    if (data == null) {
      throw new NullPointerException("Data is null");
    }
    // Look for the node containing the data using a private helper method
    return containsHelper(data, root);
  }

  /**
   * Performs the binary search tree contains algorithm to recursively
   * look for the provided node. If the subtree is null, this method
   * does not do anything.
   *
   * @param data the data of the node to find
   * @param subtree the root node of the current subtree we are traversing
   */
  private boolean containsHelper(Comparable<T> data, BinaryNode<T> subtree) {
    // Return false if not found
    if (subtree == null) {
      return false;
    }
    // If we find the node, return true
    if (subtree.getData().equals(data)) {
      return true;
    }
    // Check right node if the data is larger that our current root node
    if (data.compareTo(subtree.getData()) > 0) {
      return containsHelper(data, subtree.getRight());
    }
    // Check left node if the data is less than or equal to our current root node
    if (data.compareTo(subtree.getData()) <= 0) {
      return containsHelper(data, subtree.getLeft());
    }
    // Never reached
    return false;
  }

  /**
   * Counts the number of values in the collection, with each duplicate value
   * being counted separately within the value returned.
   *
   * @return the number of values in the collection, including duplicates
   */
  @Override
  public int size() {
    // Call helper method to recursively find size
    return sizeHelper(this.root);
  }

  /**
   * Performs the binary search tree size algorithm to recursively
   * add up the total number of nodes. If the subtree is null, this method
   * does not add to the size
   *
   * @param subtree the root node of the current subtree
   * @return the number of values in the collection, including duplicates
   */
  private int sizeHelper(BinaryNode<T> subtree) {
    // If the node is null, just return 0
    if (subtree == null) {
      return 0;
    }
    // If there is a node, add 1 to the total size and go to left and right subtrees
    return 1 + sizeHelper(subtree.getLeft()) + sizeHelper(subtree.getRight());
  }

  /**
   * Checks if the collection is empty.
   *
   * @return true if the collection contains 0 values, false otherwise
   */
  @Override
  public boolean isEmpty() {
    // If the root is null, there are no nodes in the tree.
    return root == null;
  }

  /**
   * Removes all values and duplicates from the collection.
   */
  @Override
  public void clear() {
    // Java garbage collection clears the tree
    this.root = null;
  }
}
