public class BinarySearchTree <E extends Comparable<E>> {
    Node<E> root;

    public BinarySearchTree() {
        root = null;
    }

    class Node<E> {
        E value;
        Node<E> leftChild;
        Node<E> rightChild;

        public Node(E value) {
            this.value = value;
        }
    }

    boolean insert(E value) {
        Node<E> node = new Node<>(value);

        if (root == null) {
            root = node;
            return true;
        }

        Node<E> parent = null;
        Node<E> current = root;
        while (current != null) {
            parent = current;
            if (value.compareTo(current.value) < 0) {
                current = current.leftChild;

            } else if (value.compareTo(current.value) > 0) {
                current = current.rightChild;
            } else{
                return false;
            }
        }

        // Lost info about which child to insert on, therefore
        // have to do the left/right test again.
        if (value.compareTo(parent.value) < 0) {
            parent.leftChild = node;

        } else {
            parent.rightChild = node;

        }
        return true;
    }

    boolean remove(E value) {
        Node<E> parent = null;
        Node<E> node = root;

        boolean done = false;
        while (!done) {
            if (value.compareTo(node.value) < 0) {
                parent = node;
                node = node.leftChild;
                if(node == null){
                    return false;
                }
            }
            else if (value.compareTo(node.value) > 0) {
                parent = node;
                node = node.rightChild;
                if (node == null){
                    return false;
                }
            }
            else {
                done = true;
            }
        }

        // Case for no left child
        if (node.leftChild == null) {
            // Special case for root node
            if (parent == null) {
                root = node.rightChild;
            }
            else { // General case for no left child
                if (value.compareTo(parent.value) < 0) {
                    parent.leftChild = node.rightChild;
                }
                else {
                    parent.rightChild = node.rightChild;
                }
            }
        }
        else { // Case for there IS a left child
            Node<E> parentOfRightMost = node;
            Node<E> rightMost = node.leftChild;
            while (rightMost.rightChild != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.rightChild;
            }
            node.value = rightMost.value;
            if (parentOfRightMost.rightChild == rightMost) {
                parentOfRightMost.rightChild = rightMost.leftChild;
            }
            else {
                parentOfRightMost.leftChild = rightMost.leftChild;
            }
        }
        return true;
    }


    boolean search(E value) {
        Node<E> node = root;

        while (node != null) {
            if (value.compareTo(node.value) == 0) {
                return true;
            }
            if (value.compareTo(node.value) < 0) {
                node = node.leftChild;
            }
            else {
                node = node.rightChild;
            }
        }
        return false;
    }


    public void display(String message) {
        System.out.println(message);
        inOrderTraversal(root);
    }
    private void inOrderTraversal(Node<E> currentNode) {
        if (currentNode == null) {
            return;
        }
        inOrderTraversal(currentNode.leftChild);
        System.out.println(currentNode.value);
        inOrderTraversal(currentNode.rightChild);
    }

    int numberNodes() {
        Node node = root;
        int count = 0;
        if (node != null) {
            count = countNodes(root);
        }
        return count;
    }
    private int countNodes(Node<E> node) {
        int count = 1;
        if (node.leftChild != null) {
            count += countNodes(node.leftChild);
        }
        if (node.rightChild != null) {
            count += countNodes(node.rightChild);
        }
        return count;
    }

    int numberLeafNodes() {
        return countLeaf(root);
    }

    private int countLeaf(Node<E> node) {
        if (node == null)
            return 0;
        if (node.leftChild == null && node.rightChild == null)
            return 1;
        else
            return countLeaf(node.leftChild) + countLeaf(node.rightChild);
    }

    int height() {
        Node<E> node = root;
        return depth(node);
    }
    private int depth(Node node) {
        if (node == null) {
            return -1;
        }
        int leftDepth = depth(node.leftChild);
        int rigthDepth = depth(node.rightChild);
        if (leftDepth > rigthDepth) {
            return leftDepth + 1;
        }
        else {
            return rigthDepth + 1;
        }
    }
}
