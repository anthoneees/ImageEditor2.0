import java.util.Iterator;

/**
 * Image class that represents the pixels of an image in the form of a 2D doubly linked list.
 * contains getters, iteration methods, and six image manipulation methods.
 * @param <T> represents the Node that holds pixel values.
 */
public class Image<T extends Comparable<T>> implements Iterable<Node<T>>
{
    /**
     * width of the image.
     */
    private int width;
    /**
     * height of the image.
     */
    private int height;
    /**
     * upper left element of the image.
     */
    private Node<T> head;

    /**
     * constructor that creates an empty 2d doubly linked list.
     * @param width makes 2d linked list of this width.
     * @param height makes 2d linked list of this height.
     */
    public Image(int width, int height){
        this.width = width;
        this.height = height;
        if(height < 1 || width < 1){ // 1x1 minimum
            throw new RuntimeException();
        }
        head = new Node<>();
        Node<T> current = head;
        Node<T> tmpNodeRow = head;
        for(int i =0; i< height; i++){
            if(i != 0){
                tmpNodeRow.setDown(new Node<>());
                tmpNodeRow.getDown().setUp(tmpNodeRow);
                tmpNodeRow = tmpNodeRow.getDown();
                current = tmpNodeRow;
            }
            for(int j =1; j<width; j++){
                Node<T> tmpNode = new Node<>();
                current.setRight(tmpNode);
                current.getRight().setLeft(current);
                current = current.getRight();
                if(i == 0){
                    continue;
                }
                current.getLeft().getUp().getRight().setDown(current);
                current.setUp(current.getLeft().getUp().getRight());
            }

        }

    }

    /**
     * getter for height because its private.
     * @return returns this height
     */
    public int getHeight(){
        return height;
    }

    /**
     * getter for width.
     * @return returns width
     */
    public int getWidth() {
        return width;
    }

    /**
     * getter for head.
     * @return returns node at the upper left of the linked list
     */
    public Node<T> getHead() {
        return head;
    }

    /**
     * iterator method that returns the ImageIterator constructor.
     * @return ImageIterator object
     */
    public Iterator<Node<T>> iterator(){
        return new ImageIterator<>(this.head);
    }

    /**
     * same as above method except it includes direction as well.
     * @param dir enum value that specifies which way to iterate
     * @return ImageIterator object
     */
    public Iterator<Node<T>> iterator(Direction dir){
        return new ImageIterator<>(this.head, dir);
    }

    /**
     * appends row all with the same value at given index.
     * @param index index to append
     * @param value value to initialize the row with
     */
    public void insertRow(int index, T value){
        if(index< 0 || index > height){
            throw new RuntimeException();
        }
        Node<T> fstNode = new Node<>();
        Node<T> rowTracker = head;
        Node<T> topTail;
        Node<T> current;
        fstNode.setValue(value);
        current = fstNode;
        for(int i = 1; i< width ; i++){
            Node<T> tmpNode = new Node<>();
            tmpNode.setValue(value);
            current.setRight(tmpNode);
            current.getRight().setLeft(current);
            current = current.getRight();
        }
        current = fstNode;
        if(index == 0){
            topTail = head;
            head = current;
            while(current.getRight() != null){
                current.setDown(topTail);
                topTail.setUp(current);
                current = current.getRight();
                topTail = topTail.getRight();
            }
            height++;
        }
        else if(index < height){
            for(int i =0; i<index-1; i++){
                rowTracker = rowTracker.getDown();
            }
            topTail = rowTracker;
            while(current.getRight() != null){
                current.setDown(topTail.getDown());
                topTail.setDown(current);
                current.setUp(topTail);
                current.getDown().setUp(current);
                current = current.getRight();
                topTail = topTail.getRight();
            }
            height++;
        }
        else{
            topTail = head;
            while(topTail.getDown() != null){
                topTail = topTail.getDown();
            }
            while(current.getRight() != null){
                current.setUp(topTail);
                topTail.setDown(current);
                current = current.getRight();
                topTail = topTail.getRight();
            }
            height++;
        }
    }

    /**
     * removes entire column at given index.
     * @param index which index to remove column at
     */
    public void removeColumn(int index){
        if(index< 0 || index > width || width < 2){
            throw new RuntimeException();
        }
        Node<T> current = head;
        if(index == 0){
            head = head.getRight();
            current = head.getRight();
            while(current.getDown() != null){
                current.setLeft(null);
                current = current.getDown();
            }
            width--;
        }
        else if(index < width){
            for(int i =0; i<index-1; i++){
                current = current.getRight();
            }
            while(current.getDown()!= null){
                current.setRight(current.getRight().getRight());
                current.getRight().setLeft(current);
                current = current.getDown();
            }
            width--;
        }
        else{
            while(current.getRight() != null){
                current = current.getRight();
            }
            current = current.getLeft();
            while(current.getDown() != null){
                current.setRight(null);
                current = current.getDown();
            }
            width--;
        }
    }

    /**
     * helper method remove row which removes the row at the given index.
     * @param index which index to remove row at
     */
    private void removeRow(int index){
        if(index< 0 || index > height){
            throw new RuntimeException();
        }
        Node<T> current = head;
        if(index == 0){
            head = head.getDown();
            current = head.getDown();
            while(current.getRight() != null){
                current.setUp(null);
                current = current.getRight();
            }
            height--;
        }
        else if(index < width){
            for(int i =0; i<index-1; i++){
                current = current.getDown();
            }
            while(current.getRight()!= null){
                current.setDown(current.getDown().getDown());
                current.getDown().setUp(current);
                current = current.getRight();
            }
            height--;
        }
        else{
            while(current.getDown() != null){
                current = current.getDown();
            }
            current = current.getUp();
            while(current.getRight() != null){
                current.setDown(null);
                current = current.getRight();
            }
            height--;
        }
    }

    /**
     * if any adjacent columns or rows are the same, then remove one and return the total number of columns/rows removed.
     * @return total number of columns/rows removed
     */
    public int compress(){
        Node<T> innerLp=head; //temp = head
        boolean flag = true; //default flag
        Node<T> outerLp = head; //set current to head
        int index = -1; //set index to -1
        int count = 0; //set count to 0
        while(outerLp.getRight() != null){ //loop from left to right
            index++; //increment index
            while(innerLp.getDown()!=null){ //loop from top to bottom
                if(innerLp.getValue() != innerLp.getRight().getValue()){ //if .getvalue == right.get.value
                    flag = false; //set flag to false
                    break; //break out the loop
                }
                innerLp = innerLp.getDown(); //if equal set temp to temp. get down to check the next two columns
            }
            if(flag){ //if they were all equal
                removeColumn(index);
                count++;
            }
            flag = true;
            outerLp = outerLp.getRight();
            innerLp = innerLp.getRight();
            while(innerLp.getUp()!=null){
                innerLp = innerLp.getUp();
            }
        }
        outerLp = head;
        innerLp = head;
        index = -1;
        while(outerLp.getDown() != null && innerLp.getDown()!=null){ //from top to bottom
            index++; //increment index
            while(innerLp.getRight() != null){ //from left to right
                if(innerLp.getValue() != innerLp.getDown().getValue()){ //if temp != node under temp
                    flag = false; // set flag to false
                    break; //break
                }
                innerLp = innerLp.getRight(); //if were equal, increment to the next temp
            }
            if(flag){
                removeRow(index); //if flag is false remove row and increment count
                count++;
            }
            flag = true; //set flag to true
            outerLp=outerLp.getDown(); //set current to current.down
            innerLp=innerLp.getDown(); // set temp to temp.down
            while(innerLp.getLeft()!=null){ //move temp all the way to the left
                innerLp = innerLp.getLeft();
            }

        }
        return count;
    }

    /**
     * adds a border to the outside of the image that mimics that values of the inner image.
     */
    public void addBorder(){
        Node<T> newHead = new Node<>();
        Node<T> newRowNode = newHead;
        Node<T> originalRowNode = head;
        newHead.setValue(head.getValue());
        for(int i = 0; i<width; i++){
            Node<T> insertNode = new Node<>(); // create new node
            insertNode.setValue(originalRowNode.getValue()); // set the value of insert Node to og node get value
            newRowNode.setRight(insertNode); // set new row node right to insert node
            insertNode.setLeft(newRowNode); // set left of insert node to newrownode
            originalRowNode.setUp(insertNode); //og row node up = insert node
            insertNode.setDown(originalRowNode); // insertnode set down og node row
            newRowNode = newRowNode.getRight();
            if(i == width-1){
                continue;
            }
            originalRowNode = originalRowNode.getRight();
        }

        Node<T> lastNode = new Node<>();
        lastNode.setValue(originalRowNode.getValue());
        newRowNode.setRight(lastNode);
        lastNode.setLeft(newRowNode);

        head = newHead;
        newHead = new Node<>();
        newRowNode = newHead;
        originalRowNode = head.getRight();
        while(originalRowNode.getDown()!=null){
            originalRowNode = originalRowNode.getDown();
        }
        for(int i =0; i<width; i++){
            Node<T> insertNode = new Node<>(); // create new node
            insertNode.setValue(originalRowNode.getValue()); // set the value of insert Node to og node get value
            newRowNode.setRight(insertNode); // set new row node right to insert node
            insertNode.setLeft(newRowNode); // set left of insert node to newrownode
            originalRowNode.setUp(insertNode); //og row node up = insert node
            insertNode.setDown(originalRowNode); // insertnode set down og node row
            newRowNode = newRowNode.getRight();
            if(i == width-1){
                continue;
            }
            originalRowNode = originalRowNode.getRight();
        }
        Node<T> lastNode2 = new Node<>();
        lastNode2.setValue(originalRowNode.getValue());
        newRowNode.setRight(lastNode2);
        lastNode2.setLeft(newRowNode);

        newHead = new Node<>();
        newRowNode = newHead;
        originalRowNode = head.getRight().getDown();
        newHead.setValue(head.getValue());
        newHead.setUp(head);
        head.setDown(newHead);
        newHead.setRight(originalRowNode);
        originalRowNode.setLeft(newHead);
        originalRowNode = originalRowNode.getDown();
        for(int i = 0; i< height-1; i++){
            Node<T> insertNode = new Node<>();
            insertNode.setValue(originalRowNode.getValue());
            insertNode.setRight(originalRowNode);
            originalRowNode.setLeft(insertNode);
            newRowNode.setDown(insertNode);
            insertNode.setUp(newRowNode);
            newRowNode =newRowNode.getDown();
            if(i==height-2){
                Node<T> botHead= head;
                while(botHead.getDown()!=null){
                    botHead = botHead.getDown();
                }
                newRowNode.setDown(botHead);
                botHead.setUp(newRowNode);
                continue;
            }
            originalRowNode = originalRowNode.getDown();
        }

        newHead = new Node<>();
        newRowNode = newHead;
        originalRowNode = lastNode.getLeft().getDown();
        newHead.setValue(originalRowNode.getValue());
        newHead.setUp(lastNode);
        lastNode.setDown(newHead);
        newHead.setLeft(originalRowNode);
        originalRowNode.setRight(newHead);
        originalRowNode = originalRowNode.getDown();
        for(int i = 0; i< height-1; i++){
            Node<T> insertNode = new Node<>();
            insertNode.setValue(originalRowNode.getValue());
            insertNode.setLeft(originalRowNode);
            originalRowNode.setRight(insertNode);
            newRowNode.setDown(insertNode);
            insertNode.setUp(newRowNode);
            newRowNode =newRowNode.getDown();
            if(i==height-2){
                newRowNode.setDown(lastNode2);
                lastNode2.setUp(newRowNode);
                continue;
            }
            originalRowNode = originalRowNode.getDown();
        }

        height += 2;
        width += 2;
    }

    /**
     * remove border which removes all the outside nodes from the image.
     */
    public void removeBorder(){
        if(width < 3 || height < 3){
            throw new RuntimeException();
        }
        Node<T> nodeIterator = head;
        nodeIterator = nodeIterator.getDown().getRight();
        Node<T> newHead = nodeIterator;
        nodeIterator.setUp(null);
        nodeIterator.setLeft(null);
        while(nodeIterator.getRight()!= null){
            nodeIterator.setUp(null);
            nodeIterator = nodeIterator.getRight();
        }
        nodeIterator = nodeIterator.getLeft();
        nodeIterator.setRight(null);
        while(nodeIterator.getDown() != null){
            nodeIterator.setRight(null);
            nodeIterator = nodeIterator.getDown();
        }
        nodeIterator = nodeIterator.getUp();
        nodeIterator.setDown(null);
        while(nodeIterator.getLeft() != null){
            nodeIterator.setDown(null);
            nodeIterator = nodeIterator.getLeft();
        }
        nodeIterator = nodeIterator.getRight();
        nodeIterator.setLeft(null);
        while(nodeIterator.getUp() != null){
            nodeIterator.setLeft(null);
            nodeIterator = nodeIterator.getUp();
        }
        head = newHead;
        width-=2;
        height-=2;
    }

    /**
     * Creates new image with same size as original but creates neighborhoods and sets them to the greatest value in said neighborhood.
     * @return new Image.
     */
    public Image<T> maxFilter(){
        T maxValue = head.getValue();
        Image<T> maxImage = new Image<>(width, height);
        Iterator<Node<T>> thisIter = this.iterator();
        Iterator<Node<T>> maxImageIter = maxImage.iterator();
        Node<T> tmpNode = head;
        if(height == 1 && width == 1){
            maxImage.head.setValue(maxValue);
            return maxImage;
        }
        if(tmpNode.getRight() != null && tmpNode.getRight().getValue().compareTo(maxValue) > 0){
            maxValue = tmpNode.getRight().getValue();
        }
        if(tmpNode.getDown() != null && tmpNode.getDown().getValue().compareTo(maxValue)>0){
            maxValue = tmpNode.getDown().getValue();
        }
        if(tmpNode.getRight().getDown() != null && tmpNode.getRight().getDown().getValue().compareTo(maxValue) > 0){
            maxValue = tmpNode.getRight().getDown().getValue();
        }
        maxImage.head.setValue(maxValue);
        while(thisIter.hasNext()){ //checks nodes in the neighborhood for the greatest value
            tmpNode = thisIter.next();
            if(tmpNode.getLeft() != null){
                if(tmpNode.getLeft().getValue().compareTo(maxValue) > 0){
                    maxValue = tmpNode.getLeft().getValue();
                }
                if(tmpNode.getLeft().getDown() != null && tmpNode.getLeft().getDown().getValue().compareTo(maxValue) > 0){
                    maxValue = tmpNode.getLeft().getDown().getValue();
                }
                if(tmpNode.getLeft().getUp() != null && tmpNode.getLeft().getUp().getValue().compareTo(maxValue) > 0){
                    maxValue = tmpNode.getLeft().getUp().getValue();
                }
            }
            if(tmpNode.getRight() != null){
                if(tmpNode.getRight().getValue().compareTo(maxValue) > 0){
                    maxValue = tmpNode.getRight().getValue();
                }
                if(tmpNode.getRight().getDown() != null && tmpNode.getRight().getDown().getValue().compareTo(maxValue) > 0){
                    maxValue = tmpNode.getRight().getDown().getValue();
                }
            }
            if(tmpNode.getDown() != null && tmpNode.getDown().getValue().compareTo(maxValue)>0){
                maxValue = tmpNode.getDown().getValue();
            }
            if(tmpNode.getUp() != null){
                if(tmpNode.getUp().getValue().compareTo(maxValue) > 0){
                    maxValue = tmpNode.getUp().getValue();
                }
                if(tmpNode.getUp().getRight() != null && tmpNode.getUp().getRight().getValue().compareTo(maxValue) > 0){
                    maxValue = tmpNode.getUp().getRight().getValue();
                }
            }
            maxImageIter.next().setValue(maxValue); //sets max value
        }
        return maxImage; //return image
    }
    /**
     * toString for image.
     * @return string from of the image object
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        Node<T> rowNode = head;

        // Traverse each row
        for (int i = 0; i < height; i++) {
            Node<T> currentNode = rowNode;

            // Traverse each column in the row
            for (int j = 0; j < width; j++) {
                if (currentNode.getValue() != null) {
                    result.append(currentNode.getValue());
                } else {
                    result.append("-");
                }

                // Move to the next node in the same row
                currentNode = currentNode.getRight();

                if (j < width - 1) {
                    result.append(" -> ");
                }
            }

            // Move to the next row
            result.append("\n");
            if (i < height - 1) {
                rowNode = rowNode.getDown();
            }
        }

        return result.toString();
    }
}
