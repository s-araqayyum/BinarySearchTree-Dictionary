public class BSTNode {

    //Each node in a Dictionary-Tree will contain a word, and its subsequent meaning
    String word;
    String definition;

    //To keep a BST balanced at the time of deletion, height must be stored as well for each node
    int height;

    BSTNode rightChild;
    BSTNode leftChild;

    public BSTNode(String word, String definition){
        this.word = word;
        this.definition = definition;
        this.height = 0;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BSTNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(BSTNode rightChild) {
        this.rightChild = rightChild;
    }

    public BSTNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(BSTNode leftChild) {
        this.leftChild = leftChild;
    }
}
