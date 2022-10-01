/*
This program should
>retrieve and print the meaning of a queried word
>delete any word from a tree and balance the tree if needed
>use file handling and save elements of tree in a file in pre-order/post-order/in-order
>have start () which inserts all elements from file to BST as you open the program
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("Welcome to Sara's Dictionary!");

        BSTNode root = null;
        root = start(null);

        UserDrivenMenu GM = new UserDrivenMenu();

        for(;;){
            System.out.println("");
            GM.GeneralMenu(root);
        }
    }

    public static BSTNode start(BSTNode root) {

        //Transforming file data to BST nodes through insertion
        DictionaryTree DT = new DictionaryTree();

        File dictionaryWordFile = new File("dictionaryWordFile.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(dictionaryWordFile));
            String line;
            while ((line = br.readLine()) != null) {
                String[] fileLineSplit = line.split(":");
                String word = fileLineSplit[0];
                String definition = fileLineSplit[1];
                root = DT.InsertWord(word, definition, root);
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error in populating dictionary due to:: "+e);
        }
        return root;
    }
}
