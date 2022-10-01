import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class UserDrivenMenu {

    static ArrayList<String> inorderArray = new ArrayList<>();
    static ArrayList<String> preorderArray = new ArrayList<>();
    static ArrayList<String> postorderArray = new ArrayList<>();

    DictionaryTree DT = new DictionaryTree();
    public void GeneralMenu(BSTNode root){

        Scanner userDecision = new Scanner(System.in);
        System.out.println("What action would you like to perform today: ");
        System.out.println("1. Display root of the balanced AVL tree.");
        System.out.println("2. Search for a specific word from the dictionary.");
        System.out.println("3. Delete a word from the dictionary.");
        System.out.println("4. Save words from dictionary in file - pre-order.");
        System.out.println("5. Save words from dictionary in file - post-order.");
        System.out.println("6. Save words from dictionary in file - in-order.");

        int userChoice = userDecision.nextInt();

        switch (userChoice) {
            case 1 -> System.out.println(root.word);
            case 2 -> {
                Scanner userChecker = new Scanner(System.in);
                System.out.println("Please enter a word that you would like to search from our dictionary:: ");
                String queriedWord = userChecker.nextLine();
                PrintSpecificWord(queriedWord, root);
            }
            case 3 -> {
                Scanner userDeletion = new Scanner(System.in);
                System.out.println("Please enter a word that you would like to delete from our dictionary:: ");
                String deletingWord = userDeletion.nextLine();
                BSTNode flagNode = DT.WordDeletion(deletingWord, root);
                if(flagNode == null){
                    System.out.println("This word is not in our dictionary and therefore, can't be deleted!");
                }
                else{
                    System.out.println("Deleted successfully!");
                }
            }
            case 4 -> {
                SavePreOrder(root);

                try {
                    FileWriter writingInFilePreOrder = new FileWriter("dictionaryWordFile.txt");
                    for (String s : preorderArray) {
                        writingInFilePreOrder.write(s + "\n");
                    }
                    writingInFilePreOrder.close();
                } catch (Exception e) {
                    System.out.println("There seems to be a problem populating the file: "+e);
                }
                System.out.println("Saved to file pre-order successfully!");
            }
            case 5 -> {
                SavePostOrder(root);
                try {
                    FileWriter writingInFilePostOrder = new FileWriter("dictionaryWordFile.txt");
                    for (String s : postorderArray) {
                        writingInFilePostOrder.write(s + "\n");
                    }
                    writingInFilePostOrder.close();
                } catch (Exception e) {
                    System.out.println("There seems to be a problem populating the file: "+e);
                }
                System.out.println("Saved to file post-order successfully!");
            }
            case 6 -> {
                SaveInOrder(root);
                try {
                    FileWriter writingInFileInOrder = new FileWriter("dictionaryWordFile.txt");
                    for (String s : inorderArray) {
                        writingInFileInOrder.write(s + "\n");
                    }
                    writingInFileInOrder.close();
                } catch (Exception e) {
                    System.out.println("There seems to be a problem populating the file: "+e);
                }
                System.out.println("Saved to file in-order successfully!");
            }
            default -> System.out.println("You have chosen a choice that does not exist. Please try again.");
        }
    }

    /*
    (a) Inorder (Left, Root, Right) - Should end with root
    (b) Preorder (Root, Left, Right) - Should start with root
    (c) Postorder (Left, Right, Root)
     */

    private void SaveInOrder(BSTNode root) {
        if (root == null)
            return;
        SaveInOrder(root.leftChild);
        inorderArray.add(root.word + ": "+ root.definition);
        SaveInOrder(root.rightChild);
    }

    private void SavePostOrder(BSTNode root) {
        if (root == null)
            return;
        SavePostOrder(root.leftChild);
        SavePostOrder(root.rightChild);
        postorderArray.add(root.word + ": "+ root.definition);
    }

    private void SavePreOrder(BSTNode root){
        if (root == null)
            return;
        preorderArray.add(root.word + ": "+ root.definition);
        SavePreOrder(root.leftChild);
        SavePreOrder(root.rightChild);
    }

    private void PrintSpecificWord(String queriedWord, BSTNode root) {

        BSTNode findingWordInDictionary = DT.SearchForWord(queriedWord, root);
        if(findingWordInDictionary == null){
            System.out.println("Sorry, this word does not exist in our dictionary.");
        }
        else{
            System.out.println("Word found: ");
            System.out.println(findingWordInDictionary.word + ": "+findingWordInDictionary.definition);
        }
    }
}
