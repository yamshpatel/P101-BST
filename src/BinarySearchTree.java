/**
 * This class represents a binary search tree that stores objects of type T in sorted order using
 * the comparable interface. Supports insertion, searching, size, empty checks, and clearing.
 * Implements the SortedCollection interface
 */
public class BinarySearchTree<T extends Comparable<T>> implements SortedCollection<T> {

  /**
   * The root of this BinarySearchTree.
   */
  protected BinaryNode<T> root = null;

  /**
   * Inserts a new data value into the sorted collection.
   *
   * @param data the new value being inserted
   * @throws NullPointerException if data argument is null, we do not allow null values
   *                              to be stored within a SortedCollection, so an
   *                              exception is thrown
   */
  @Override
  public void insert(T data) throws NullPointerException {
    // Throw error if null data
    if (data == null) {
      throw new NullPointerException("Data is null");
    }
    // Make a new node with the data
    BinaryNode<T> newNode = new BinaryNode<>(data);
    // If the root is null, make the root a node with newNode
    if (root == null) {
      root = newNode;
      return;
    }
    // Add it to the tree
    insertHelper(newNode, root);
  }

  /**
   * Performs the naive binary search tree insert algorithm to recursively insert the provided
   * newNode (which has already been initialized with a data value) into the provided tree/subtree.
   * When the provided subtree is null, this method does nothing.
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
    else {
      // If the right subtree is null, we can simply set the new node there.
      if (subtree.getRight() == null) {
        subtree.setRight(newNode);
        // exit method after we set new node
        return;
      }
      // Recursively move to the right
      insertHelper(newNode, subtree.getRight());
    }
  }

  /**
   * Check whether data is stored in the tree.
   *
   * @param data the value to check for in the collection
   * @return true if the collection contains data one or more times, and false otherwise
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
   * Performs the binary search tree contains algorithm to recursively look for
   * the provided node. If the subtree is null, this method does not do anything.
   *
   * @param data    the data of the node to find
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
   * Counts the number of values in the collection, with each duplicate value being counted
   * separately within the value returned.
   *
   * @return the number of values in the collection, including duplicates
   */
  @Override
  public int size() {
    // Call helper method to recursively find size
    return sizeHelper(this.root);
  }

  /**
   * Performs the binary search tree size algorithm to recursively add
   * up the total number of nodes. If the subtree is null, this method
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

  /**
   * Tests the insert method when inserting a node as both left and right children in different
   * orders to create differently shaped trees
   *
   * @return true if the test passes, false otherwise
   */
  public boolean test1() {
    // Test Scenario 1: Inserting a null node into the tree
    {
      // Make null Integer
      Integer testNode = null;
      BinarySearchTree<Integer> testTree = new BinarySearchTree<>();
      // Try to insert it
      try {
        testTree.insert(testNode);
        // return false if succeeds
        return false;
      } catch (NullPointerException e) {
        // pass message
        System.out.println("Insert npe passed");
      } catch (Exception e) {
        // Catch any other exceptions
        System.out.println("Unexpected exception was caught");
        return false;
      }
      // Check the size of the tree and if it is still empty
      if (testTree.size() != 0) {
        System.out.println("The size of the tree did not stay the same");
        System.out.println("Expected size of the tree: " + 0);
        System.out.println("Actual size of the tree: " + testTree.size());
        return false;
      }
    }

    // Test Scenario 2: Inserting nodes into an empty tree
    {
      // Make test values
      String testNode = "D";
      BinarySearchTree<String> testTree = new BinarySearchTree<>();
      // Inserting into tree
      testTree.insert(testNode);
      // Test if the root was properly changed
      if (!testTree.root.getData().equals(testNode)) {
        System.out.println("The root was not set after adding a node");
        System.out.println("Expected: " + testNode);
        System.out.println("Actual: " + testTree.root);
        return false;
      }
      // Size should be updated
      if (testTree.size() != 1) {
        System.out.println("Size of the tree was not updated properly");
        return false;
      }
      // Add more nodes to make a perfect binary tree
      testTree.insert("B");
      testTree.insert("F");
      // Test if the structure is correct
      BinaryNode<String> rootNode = testTree.root;
      if (!rootNode.getLeft().getData().equals("B")) {
        System.out.println("The left child was not properly set");
        System.out.println("Expected: B");
        System.out.println("Actual: " + rootNode.getLeft().getData());
        return false;
      }
      // Test the right as well
      if (!rootNode.getRight().getData().equals("F")) {
        System.out.println("The right child was not properly set");
        System.out.println("Expected: F");
        System.out.println("Actual: " + rootNode.getRight().getData());
        return false;
      }
      // Test if the size was properly updated
      if (testTree.size() != 3) {
        System.out.println("Size of the tree was not updated properly");
        System.out.println("Expected: " + 3);
        System.out.println("Actual: " + testTree.size());
        return false;
      }
      // Test if isEmpty returns false now
      if (testTree.isEmpty()) {
        System.out.println("isEmpty returned true for a non-empty tree");
        return false;
      }
    }

    // Test Scenario 3: Inserting nodes to make a skewed BST
    {
      BinarySearchTree<Integer> testTree = new BinarySearchTree<>();
      // These values should result in a left heavy tree
      testTree.insert(10);
      testTree.insert(7);
      testTree.insert(1);
      // Test the root and other nodes
      if (!testTree.root.getData().equals(10)) {
        System.out.println("The root is not correct");
        System.out.println("Expected: " + 10);
        System.out.println("Actual: " + testTree.root);
        return false;
      }
      // Test left node for the correct value
      if (!testTree.root.getLeft().getData().equals(7)) {
        System.out.println("The first left child is not correct");
        System.out.println("Expected: " + 7);
        System.out.println("Actual: " + testTree.root.getLeft());
        return false;
      }
      // Test the left node for the correct value
      if (!testTree.root.getLeft().getLeft().getData().equals(1)) {
        System.out.println("The second left child is not correct");
        System.out.println("Expected: " + 1);
        System.out.println("Actual: " + testTree.root.getLeft().getLeft());
        return false;
      }
      // Test if the size was properly updated
      if (testTree.size() != 3) {
        System.out.println("Size of the tree was not updated properly");
        System.out.println("Expected: " + 3);
        System.out.println("Actual: " + testTree.size());
        return false;
      }
    }

    // Test Scenario 4: Inserting duplicates and interior nodes
    {
      BinarySearchTree<String> testTree = new BinarySearchTree<>();
      // Insert all nodes
      testTree.insert("D");
      testTree.insert("B");
      testTree.insert("F");
      testTree.insert("A");
      testTree.insert("D");
      // Test the nodes for correct structure
      BinaryNode<String> rootNode = testTree.root;
      // Test left interior node
      if (!rootNode.getLeft().getData().equals("B")) {
        System.out.println("The interior left node is not correct");
        System.out.println("Expected: B");
        System.out.println("Actual: " + rootNode.getLeft());
        return false;
      }
      // Test if duplicate node is in correct position
      if (!rootNode.getLeft().getRight().getData().equals("D")) {
        System.out.println("The duplicate node is not in the correct position");
        System.out.println("Expected: D");
        System.out.println("Actual: " + rootNode.getLeft().getRight());
        return false;
      }
      // Check if the level order is correct
      String expected = "[ D, B, F, A, D ]";
      String actual = rootNode.toLevelOrderString();
      if (!expected.equals(actual)) {
        System.out.println("The level order is not correct");
        System.out.println("Expected: " + expected);
        System.out.println("Actual: " + actual);
        return false;
      }
      // Test if the size was properly updated
      if (testTree.size() != 5) {
        System.out.println("Size of the tree was not updated properly");
        System.out.println("Expected: " + 5);
        System.out.println("Actual: " + testTree.size());
        return false;
      }
    }

    return true;
  }

  /**
   * Tests the contains method when finding nodes in both left and right
   * subtrees as well as the root and interior nodes
   *
   * @return true if the test passes, false otherwise
   */
  public boolean test2() {
    // Test Scenario 1: Finding a null value
    {
      // Create null data and tree
      Comparable<Integer> testData = null;
      BinarySearchTree<Integer> testTree = new BinarySearchTree<>();
      try {
        testTree.contains(testData);
        // Return false if succeeds
        return false;
      } catch (NullPointerException e) {
        // pass message
        System.out.println("Contains npe caught");
      } catch (Exception e) {
        // Catch other exceptions
        System.out.println("Unexpected exception was caught");
        return false;
      }
    }

    // Test Scenario 2: Find root node
    {
      // Make test values
      Comparable<Integer> testData = 10;
      BinarySearchTree<Integer> testTree = new BinarySearchTree<>();
      // Make tree
      testTree.insert(10);
      testTree.insert(1);
      testTree.insert(14);
      // Try to find the root node
      if (!testTree.contains(testData)) {
        System.out.println("The root was not found");
        return false;
      }
    }

    // Test Scenario 3: Finding a left leaf
    {
      // Make test values
      Comparable<String> testData = "C";
      BinarySearchTree<String> testTree = new BinarySearchTree<>();
      // Insert all nodes
      testTree.insert("D");
      testTree.insert("B");
      testTree.insert("F");
      testTree.insert("A");
      testTree.insert("C");
      // Try to find the left leaf node
      if (!testTree.contains(testData)) {
        System.out.println("The left leaf node was not found");
        return false;
      }
    }

    // Test Scenario 4: Finding a right leaf
    {
      // Make test values
      Comparable<String> testData = "Z";
      BinarySearchTree<String> testTree = new BinarySearchTree<>();
      // Insert all nodes
      testTree.insert("D");
      testTree.insert("B");
      testTree.insert("F");
      testTree.insert("A");
      testTree.insert("Z");
      // Try to find the right leaf node
      if (!testTree.contains(testData)) {
        System.out.println("The right leaf node was not found");
        return false;
      }
    }

    // Test Scenario 5: Finding an interior node
    {
      // Make test values
      Comparable<String> testData = "F";
      BinarySearchTree<String> testTree = new BinarySearchTree<>();
      // Insert all nodes
      testTree.insert("D");
      testTree.insert("B");
      testTree.insert("F");
      testTree.insert("A");
      testTree.insert("C");
      // Try to find the interior node
      if (!testTree.contains(testData)) {
        System.out.println("The interior node was not found");
        return false;
      }
    }

    // Test Scenario 6: Trying to find a node that does not exist
    {
      // Make test values
      Comparable<String> testData = "Z";
      BinarySearchTree<String> testTree = new BinarySearchTree<>();
      // Insert all nodes
      testTree.insert("D");
      testTree.insert("B");
      testTree.insert("F");
      testTree.insert("A");
      testTree.insert("C");
      // Try to find the non-existent node
      if (testTree.contains(testData)) {
        System.out.println("The non-existent node was found");
        return false;
      }
    }

    return true;
  }

  /**
   * Tests the size, clear, and isEmpty methods by building and clearing a few different trees
   *
   * @return true if the test passes, false otherwise
   */
  public boolean test3() {
    // Test Scenario 1: Size test and clear an empty tree
    {
      BinarySearchTree<Integer> testTree = new BinarySearchTree<>();
      // Test size
      if (testTree.size() != 0) {
        System.out.println("The size is incorrect");
        System.out.println("Expected: " + 0);
        System.out.println("Actual: " + testTree.size());
        return false;
      }
      // Test clearing
      testTree.clear();
      // Size should still be 0
      if (testTree.size() != 0) {
        System.out.println("The size is incorrect");
        System.out.println("Expected: " + 0);
        System.out.println("Actual: " + testTree.size());
        return false;
      }
    }

    // Test Scenario 2: Single node tree
    {
      BinarySearchTree<Integer> testTree = new BinarySearchTree<>();
      // Insert node
      testTree.insert(4);
      // Test size
      if (testTree.size() != 1) {
        System.out.println("The size is incorrect");
        System.out.println("Expected: " + 1);
        System.out.println("Actual: " + testTree.size());
        return false;
      }
      // isEmpty should return false
      if (testTree.isEmpty()) {
        System.out.println("isEmpty returned true, but should be false");
        return false;
      }
      // Test clearing
      testTree.clear();
      // Size should be updated
      if (testTree.size() != 0) {
        System.out.println("The size is incorrect");
        System.out.println("Expected: " + 0);
        System.out.println("Actual: " + testTree.size());
        return false;
      }
      // isEmpty should return true
      if (!testTree.isEmpty()) {
        System.out.println("isEmpty returned false, but should be true");
        return false;
      }
    }

    // Test Scenario 3: Multi-node tree
    {
      BinarySearchTree<Integer> testTree = new BinarySearchTree<>();
      // Insert all nodes
      testTree.insert(4);
      testTree.insert(2);
      testTree.insert(6);
      testTree.insert(1);
      testTree.insert(3);
      // Test size
      if (testTree.size() != 5) {
        System.out.println("The size is incorrect");
        System.out.println("Expected: " + 5);
        System.out.println("Actual: " + testTree.size());
        return false;
      }
      // isEmpty should return false
      if (testTree.isEmpty()) {
        System.out.println("isEmpty returned true, but should be false");
        return false;
      }
      // Test clearing
      testTree.clear();
      // Size should be updated
      if (testTree.size() != 0) {
        System.out.println("The size is incorrect");
        System.out.println("Expected: " + 0);
        System.out.println("Actual: " + testTree.size());
        return false;
      }
      // isEmpty should return true
      if (!testTree.isEmpty()) {
        System.out.println("isEmpty returned false, but should be true");
        return false;
      }
    }

    // Test Scenario 4: Tree with duplicates
    {
      BinarySearchTree<String> testTree = new BinarySearchTree<>();
      // Insert all nodes
      testTree.insert("D");
      testTree.insert("B");
      testTree.insert("F");
      testTree.insert("A");
      testTree.insert("D");
      // Test size
      if (testTree.size() != 5) {
        System.out.println("The size is incorrect");
        System.out.println("Expected: " + 5);
        System.out.println("Actual: " + testTree.size());
        return false;
      }
      // Test clearing
      testTree.clear();
      // Size should be updated
      if (testTree.size() != 0) {
        System.out.println("The size is incorrect");
        System.out.println("Expected: " + 0);
        System.out.println("Actual: " + testTree.size());
        return false;
      }
    }

    // Test Scenario 5: Rebuilding tree after clearing it
    {
      BinarySearchTree<String> testTree = new BinarySearchTree<>();
      // Insert all nodes
      testTree.insert("D");
      testTree.insert("B");
      testTree.insert("F");
      testTree.insert("A");
      testTree.insert("D");
      // Test clearing
      testTree.clear();
      // Size should be updated
      if (testTree.size() != 0) {
        System.out.println("The size is incorrect");
        System.out.println("Expected: " + 0);
        System.out.println("Actual: " + testTree.size());
        return false;
      }
      // Rebuild tree
      testTree.insert("D");
      testTree.insert("B");
      testTree.insert("F");
      testTree.insert("A");
      testTree.insert("C");
      // Size should be updated
      if (testTree.size() != 5) {
        System.out.println("The size is incorrect");
        System.out.println("Expected: " + 5);
        System.out.println("Actual: " + testTree.size());
        return false;
      }
    }

    return true;
  }

  /**
   * The main method that calls each test method using an instantiated BST
   *
   * @param args unused
   */
  public static void main(String[] args) {
    // Make a BST to use test methods
    BinarySearchTree<Integer> testTree = new BinarySearchTree<>();
    // Call each test method and provide feedback
    System.out.println("Testing BST implementation...");
    System.out.println("test1: " + testTree.test1());
    System.out.println("test2: " + testTree.test2());
    System.out.println("test3: " + testTree.test3());
  }
}
