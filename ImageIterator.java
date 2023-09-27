import java.util.Iterator;

/**
 * iterator classes used to traver through the 2D Doubly Linked LIst in image.
 * @param <T> Node definition defined in Node.java
 */
public class ImageIterator<T extends Comparable<T>> implements Iterator<Node<T>>
{
    /**
     * head for iterator.
     */
    private Node<T> head;
    /**
     * represents current working node that is being changed after each iteration.
     */
    private Node<T> current = null;
    /**
     * which direction to iterate in.
     */
    private Direction direction;

    /**
     * takes in a given node and sets it to current, default direction is horizontal.
     * @param given given node
     */
    public ImageIterator(Node<T> given){
        head = given;
        //current.setRight(head);
        this.direction = Direction.HORIZONTAL;
    }

    /**
     * Takes in direction (horizontal/vertical) depending on which way you want to iterate.
     * @param given given node
     * @param direction VERTICAL / HORIZONTAL depending on which way to iterate
     */
    public ImageIterator(Node<T> given, Direction direction){
        head = given;
        //current.setRight(head);
        this.direction = direction;
    }

    /**
     * checks if the current node is the last node in the linked list.
     * @return true if last node false otherwise
     */
    public boolean hasNext(){
        return current.getDown() != null || current.getRight() != null;
    }

    /**
     * finds next node.
     * @return returns next node in the linked list
     */
    public Node<T> next(){;
        if(!hasNext()){
            return null;
        }
        if(current == null){
            current = head;
            return head;
        }
        if(direction == Direction.HORIZONTAL){
            if(current.getRight() == null){
                current = current.getDown();
                while(current.getLeft() != null){
                    current = current.getLeft();
                }
                return current;
            }
            current = current.getRight();
            return current;
        }
        else if(direction == Direction.VERTICAL) {
            if (current.getDown() == null) {
                current = current.getRight();
                while (current.getUp() != null) {
                    current = current.getUp();
                }
                return current;
            }
            current = current.getDown();
            return current;
        }
        return null;
    }
}
