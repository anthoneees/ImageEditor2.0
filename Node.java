/**
 * implementation of node that sets up down left and right parameters.
 * @param <T> whatever the value of node will hold
 */
public final class Node<T extends Comparable<T>> implements Comparable<Node<T>>
{
    /**
     * data is what will be stored in the nodes.
     */
    private T data;
    /**
     * pointer to what above node.
     */
    private Node<T> up;
    /**
     * pointer to what's below node.
     */
    private Node<T> down;
    /**
     * pointer to what's to the right of node.
     */
    private Node<T> right;
    /**
     * pointer to what's to the left of node.
     */
    private Node<T> left;

    /**
     * constructor for Node that sets data and all its pointers to null.
     */
    public Node(){
        this.data = null;
        this.up = null;
        this.down = null;
        this.right = null;
        this.left = null;
    }

    /**
     * constructor for node that sets value to data and the rest to null.
     * @param value what value to set data to
     */
    public Node(T value){
        this.data = value;
        this.up = null;
        this.down = null;
        this.right = null;
        this.left = null;
    }

    /**
     * setter for value.
     * @param value what to set data to
     */
    public void setValue(T value){
        this.data = value;
    }

    /**
     * getter for value.
     * @return data of the node
     */
    public T getValue(){
        return this.data;
    }

    /**
     * getter for up.
     * @return returns the node above node.
     */
    public Node<T> getUp(){
        return this.up;
    }

    /**
     * getter for down.
     * @return returns the node below node
     */
    public Node<T> getDown(){
        return this.down;
    }

    /**
     * getter for right.
     * @return returns the node to the right of node
     */
    public Node<T> getRight(){
        return this.right;
    }

    /**
     * getter for left.
     * @return returns the node to the left of node.
     */
    public Node<T> getLeft(){
        return this.left;
    }

    /**
     * setter for up.
     * @param p sets the node above node.
     */
    public void setUp(Node<T> p){
        this.up = p;
    }

    /**
     * setter for down.
     * @param p sets the node below node.
     */
    public void setDown(Node<T> p){
        this.down = p;
    }

    /**
     * setter for right.
     * @param p sets the node the right of node.
     */
    public void setRight(Node<T> p){
        this.right = p;
    }

    /**
     * setter for left.
     * @param p sets the node to the left of node.
     */
    public void setLeft(Node<T> p){
        this.left = p;
    }

    /**
     * compare method for node.
     * @param compareNode the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Node<T> compareNode){
        return this.data.compareTo(compareNode.data);
    }
}
