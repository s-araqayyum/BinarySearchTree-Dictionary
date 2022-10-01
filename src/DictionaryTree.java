import java.util.Objects;

public class DictionaryTree {

    BSTNode root;

    int height(BSTNode nodeInQuestion) {
        return (nodeInQuestion == null) ? (0) : (nodeInQuestion.height);
    }

    public int HeightDifferenceCalculator(BSTNode comparator1, BSTNode comparator2){
        return 1 + Math.max(height(comparator1), height(comparator2));
    }

    public BSTNode SearchForWord(String dictionaryWord, BSTNode supposedRoot){

        boolean returnFlag = supposedRoot == null;
        if (returnFlag){
            return null;
        }

        if(dictionaryWord.equalsIgnoreCase(supposedRoot.word)){
            return supposedRoot; //Return if root has this value
        }
        else if(dictionaryWord.compareTo(supposedRoot.word) < 0){
            return SearchForWord(dictionaryWord, supposedRoot.leftChild);
        }
        else if(dictionaryWord.compareTo(supposedRoot.word) > 0){
            return SearchForWord(dictionaryWord, supposedRoot.rightChild);
        }
        else{
            return null; //Could not find queried word
        }
    }


    public BSTNode InsertWord(String dictionaryWord, String definition, BSTNode supposedRoot) {

        if (supposedRoot == null) {
            supposedRoot = new BSTNode(dictionaryWord, definition);
            root = supposedRoot;
            return supposedRoot;
        }

        if(dictionaryWord.equalsIgnoreCase(supposedRoot.word)){
            return supposedRoot; //Binary Search Trees do not hold repeated words
        }
        else if (dictionaryWord.compareTo(supposedRoot.word) < 0){ //Recursively send the left child to function if word object is minimal in comparison
            supposedRoot.leftChild = InsertWord(dictionaryWord, definition, supposedRoot.leftChild);
        }
        else if (dictionaryWord.compareTo(supposedRoot.word) > 0){//Recursively send the right child to function if word object is maximal in comparison
            supposedRoot.rightChild = InsertWord(dictionaryWord, definition, supposedRoot.rightChild);
        }

        //We must change the existing heights
        if(supposedRoot.leftChild == null && supposedRoot.rightChild == null){
            supposedRoot.height = 0;
        }
        else if(supposedRoot.leftChild == null){
            supposedRoot.height = 1 + Math.max(-1, supposedRoot.rightChild.height);
        }
        else if(supposedRoot.rightChild == null){
            supposedRoot.height = 1 + Math.max(supposedRoot.leftChild.height, -1);
        }
        else{
            supposedRoot.height = HeightDifferenceCalculator(supposedRoot.leftChild, supposedRoot.rightChild);
        }

        //Since this is an AVL tree, we must check balances of the tree
        int needOfBalance = 0;
        if ( BalanceFactorCalc(supposedRoot) < -1 && (dictionaryWord.compareTo(supposedRoot.rightChild.word) < 0)){
            needOfBalance = 4;
        }
        else if ( BalanceFactorCalc(supposedRoot) < -1 &&(dictionaryWord.compareTo(supposedRoot.rightChild.word) > 0)){
            needOfBalance = 3;
        }
        else if ( BalanceFactorCalc(supposedRoot) > 1 && (dictionaryWord.compareTo(supposedRoot.leftChild.word) > 0)){
            needOfBalance = 2;
        }
        else if (( BalanceFactorCalc(supposedRoot) > 1) && (dictionaryWord.compareTo(supposedRoot.leftChild.word) < 0)) {
            needOfBalance = 1;
        }

        switch(needOfBalance){
            case 1:
                return LLHeavyRotation(supposedRoot);
            case 2:
                return LRHeavyRotation(supposedRoot);
            case 3:
                return RRHeavyRotation(supposedRoot);
            case 4:
                return RLHeavyRotation(supposedRoot);
            case 0:
                break;
        }
        return supposedRoot;
    }

    public int BalanceFactorCalc(BSTNode nodeInQuestion) {
        if(nodeInQuestion == null){
            return 0;
        }
        else{
            if(nodeInQuestion.leftChild == null && nodeInQuestion.rightChild == null){
                return 0;
            }
            else if(nodeInQuestion.leftChild == null){
                return (-1-(nodeInQuestion.rightChild.height));
            }
            else if(nodeInQuestion.rightChild == null){
                return (nodeInQuestion.leftChild.height + 1);
            }
            else{
                return nodeInQuestion.leftChild.height - nodeInQuestion.rightChild.height;
            }
        }
    }

    BSTNode LLHeavyRotation(BSTNode problematicNode) { //When the rotation needs to pull problematic node to the right
        BSTNode traversalNode = problematicNode.leftChild;
        BSTNode temporaryHolder = traversalNode.rightChild;
        traversalNode.rightChild = problematicNode;
        problematicNode.leftChild = temporaryHolder;

        problematicNode.height = HeightDifferenceCalculator(problematicNode.leftChild, problematicNode.rightChild);
        traversalNode.height = HeightDifferenceCalculator(traversalNode.leftChild, traversalNode.rightChild);

        return traversalNode;
    }

    BSTNode RRHeavyRotation(BSTNode problematicNode) { //When the rotation needs to pull problematic node to the right
        BSTNode traversalNode = problematicNode.rightChild;
        BSTNode temporaryHolder = traversalNode.leftChild;
        traversalNode.leftChild = problematicNode;
        problematicNode.rightChild = temporaryHolder;

        problematicNode.height = HeightDifferenceCalculator(problematicNode.leftChild, problematicNode.rightChild);
        traversalNode.height = HeightDifferenceCalculator(traversalNode.leftChild, traversalNode.rightChild);

        return traversalNode;
    }

    public BSTNode LRHeavyRotation(BSTNode supposedRoot){
        supposedRoot.leftChild = RRHeavyRotation(supposedRoot.leftChild);
        return LLHeavyRotation(supposedRoot);
    }

    public BSTNode RLHeavyRotation(BSTNode supposedRoot) {
        supposedRoot.rightChild = LLHeavyRotation(supposedRoot.rightChild);
        return RRHeavyRotation(supposedRoot);
    }

    BSTNode WordDeletion(String deletingWord, BSTNode supposedRoot)
    {
        boolean returnFlag = supposedRoot == null;
        if (returnFlag)
            return null;

        if (deletingWord.compareTo(supposedRoot.word) < 0) {
            supposedRoot.setLeftChild(WordDeletion(deletingWord, supposedRoot.leftChild));
        }
        else if (deletingWord.compareTo(supposedRoot.word) > 0) {
            supposedRoot.setRightChild(WordDeletion(deletingWord, supposedRoot.rightChild));
        }
        else {
            if (supposedRoot.leftChild!=null && supposedRoot.rightChild!=null) {
                BSTNode traversingNode = supposedRoot.rightChild; //For re-attachment at extreme left
                while ((traversingNode.leftChild != null)) {
                    traversingNode = traversingNode.leftChild;
                }

                supposedRoot.word = traversingNode.word;
                supposedRoot.definition = traversingNode.definition;

                supposedRoot.rightChild = WordDeletion(traversingNode.word, supposedRoot.rightChild);
            }
            else {
                BSTNode newNode =  (supposedRoot.leftChild == null)? supposedRoot.rightChild:supposedRoot.leftChild;
                boolean flag = newNode == null;

                if(flag){
                    supposedRoot = null;
                }
                else{
                    supposedRoot = newNode;
                }
            }
        }

        boolean returnFlagCheck2 = supposedRoot == null;
        if (returnFlagCheck2)
            return null;

        supposedRoot.height = HeightDifferenceCalculator(supposedRoot.leftChild, supposedRoot.rightChild);

        int balancingOption = 0;

        if (BalanceFactorCalc(supposedRoot) > 1 && BalanceFactorCalc(supposedRoot.leftChild) >= 0) {
            balancingOption = 1;
        }
        else if (BalanceFactorCalc(supposedRoot) > 1 && BalanceFactorCalc(supposedRoot.leftChild) < 0) {
            balancingOption = 2;
        }
        else if (BalanceFactorCalc(supposedRoot) < -1 && BalanceFactorCalc(supposedRoot.rightChild) <= 0) {
            balancingOption = 3;
        }
        else if (BalanceFactorCalc(supposedRoot) < -1 && BalanceFactorCalc(supposedRoot.rightChild) > 0) {
            balancingOption = 4;
        }

        switch(balancingOption){
            case 1:
                return LLHeavyRotation(supposedRoot);
            case 2:
                return LRHeavyRotation(supposedRoot);
            case 3:
                return RRHeavyRotation(supposedRoot);
            case 4:
                return RLHeavyRotation(supposedRoot);
            case 0:
                break;
        }
        return supposedRoot;
    }
}
