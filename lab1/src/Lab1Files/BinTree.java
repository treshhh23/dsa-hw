package Lab1Files;

import java.util.ArrayList;

public class BinTree{
    private TNode root;
    private int num = 0;

    // constructor 1; creates a tree with only the root node
    // Time Complexity: O(1), creates a single node, constant time
    // Space Complexity: O(1), we create space for a single node
    public  BinTree(){
        root = new TNode(null, null, null);
    }

    // Time Complexity: O(N)
    // The length of the string is N, and we traverse the tree based on the length of the string
    // Space Complexity: O(N)
    // In the worst case, we create N new nodes for the codeword
    private void insertCodeword(String symbol, String binary){
        
	TNode node = this.root; //node is a reference variable for the current tree node;
    int n = binary.length(); // length of binary string
    char c; // to store the current character in the binary string
	
	//loop through the binary string
        for(int i = 0; i < n; i++){
	        c = binary.charAt(i);
            // Navigate or create the path
            if(c=='0'){
                // Check if either a left or right child exist, if they do, move the reference node to the
                // node corresponding to the direction, if not create a new node and move to it
                if(node.left == null) {
                    node.left = new TNode(null, null,null);
                }
                node = node.left;
            } else {
                if(node.right == null) {
                    node.right = new TNode(null, null,null);
                }
                node = node.right;
            }
            // Check if the current node already contains a codeword, if it does throw a violation
            if(node.data != null)
                 throw new IllegalArgumentException("Prefix condition violated!");
        }

        // Checks if the prefix condition for the current node is violated, 
        // if not insert symbol and increment the number of nodes variable
        if((node.left != null) || (node.right != null))
            throw new IllegalArgumentException("Prefix condition violated!");
        else 
            { 
             node.data = symbol;
             num++;
            }
    }
    
    //constructor 2; creates a tree with based on an input of binary strings
    // Time Complexity: O(N * K)
    // N is the average length of the strings, and K is the amount of strings in the array
    // We perform the insertCodeword (O(N)) function K amount of times
    // Space Complexity: O(M)
    // We create space for an M amount of nodes
    public BinTree(String[] a) throws IllegalArgumentException{
        int k = a.length; // array length
        char c; // to store the current character in the binary string

        for(int i = 0; i < k; i++){
            int n = a[i].length(); //length of current string
            //loop through the current string;
            //if current character is not '0' or '1', throw exception
            for(int j = 0; j < n; j++) {
                c = a[i].charAt(j);
                if (c != '0' && c != '1') {
                    throw new IllegalArgumentException("Invalid Argument!");
                }
            }
        }// end outer loop
      
        //Now , we know that all inputs are binary; 
	    //we start constructing the tree by creating the root node
        this.root = new TNode(null, null,null);
        
	    //loop through the array and insert each codeword by using insertCodeword()  
        for( int i = 0; i < k; i++){
            // insert the codeword a[i] in the tree with corresponding symbol "c" + i
            String symbol = "c" + i;
            insertCodeword(symbol, a[i]);
        }// end for
    }// end constructor 
    
    // Time Complexity: O(N)
    // N is the number of nodes in the tree, and we visit each node once
    // Space Complexity: O(H)
    // H is the height of the tree, our deepest recursion stack is the height of the tree
    // and once we reach the bottom, the memory is freed
    public void printTree(){printTree(root);}

    private void printTree(TNode t) {
    // Recursively iterates through tree and prints I if node data is null and the codeword otherwise
        if(t != null) {
            printTree(t.left);
            if(t.data == null)
                System.out.print("I ");
            else 
                System.out.print(t.data + " ");
            printTree(t.right);
        }
    }

    // Time and Space Complexity: O(1)
    public int getNumberOfCodewords() {
        return this.num;
    }

    // Time Complexity: O(M)
    // M is the amount of nodes, in the worst case we will have to visit all nodes
    // Space Complexity: O(H)
    // The recursion will at most equal the tree height
    public int height() {return height(root);}

    private int height(TNode t) {
        if(t == null) {
            return -1;
        }

        // Recursively iterate through the left and right subtrees
        int left_height = height(t.left);
        int right_height = height(t.right);

        // Return + 1 for every level 
        return Math.max(left_height, right_height) + 1;
    }

    // Time Complexity: O(M)
    // We need to visit every node to get all the codewords
    // Space Complexity: O(H + K)
    // O(H) is the recursion stack depth, which is the height of the tree
    // O(K) is the memory allocated for the Arraylist
    // Since these memory allocations are not related, we take the space complexity as the sum of both
    public ArrayList<String> getCodewords() {
        ArrayList<String> list = new ArrayList<String>();
        getCodewords(root, "", list);
        return list;
    }

    // Private Helper
    private void getCodewords(TNode t, String path, ArrayList<String> list) {
        if (t == null) {
            return;
        }

        // If we are on a leaf, append it to an arraylist
        if (t.data != null) {
            list.add(path); 
        }

        // Iterates through the tree, and concatenates the path code to the existing string
        getCodewords(t.left, path + "0", list);
        getCodewords(t.right, path + "1", list);
    }

    // Time Complexity: O(N)
    // N is the length of the binary stream
    // Space Complexity: O(S)
    // S is the total memory allocated for each of the decoded symbols in the ArrayList
    // Decodes a binary stream and returns the codes
    public ArrayList<String> decode(String s) {
        int n = s.length();
        TNode node = this.root;
        ArrayList<String> decodedString = new ArrayList<>();

        // Loop through each bit in binary stream
        for (int i = 0; i < n; i++) {
            // Move left if 0, right if 1
            if (s.charAt(i) == '0') {
                node = node.left;
            } else {
                node = node.right;
            }

            if (node.data != null) {
                // Append data to arraylist of codes
                decodedString.add(node.data);
                // Reset to root after finding code
                node = this.root;
            }
        }

        return decodedString;
    }

    // Time Complexity:
    // Public Class: O(M + K * L)
    // Public Class: O(K*L), concatenates K strings and StringBuilder.append is
    // based on the length of the string
    // Private Helper Class: O(M), has to visit M amount of nodes
    // Space Complexity: O(H + K * L)
    // We use space of O(H) for recursion, and O(K + L) for the temporary array "codes"
    // and the final array that is to be returned where K is the amount of strings, and L is 
    // the length of each string
    
    // Prints the string representation of the tree
    public String toString() {
        // Create an array to hold formatted strings for each codeword
        String[] codes = new String[this.getNumberOfCodewords()]; 
        
        // Fill the array using recursive traversal
        toString(root, codes, "");
        
        // Use StringBuilder for efficient string concatenation
        StringBuilder result = new StringBuilder();

        // Append each formatted codeword from the array
        for (int j = 0; j < codes.length; j++) {
            if (codes[j] != null) {
                result.append(codes[j]);
            }
        }
        
        return result.toString();
    }

    private void toString(TNode t, String[] codes, String path) {
        if (t == null) {
            return;
        }

        // If we are at a leaf, insert the data and path at the index corresponding to the code number
        if (t.data != null) {
            int i = Integer.parseInt(t.data.substring(1));
            codes[i] = "(" + t.data + "," + path + ") ";
        }

        // Traverse the tree recursively, while tracking the path
        toString(t.left, codes, path + "0");
        toString(t.right, codes, path + "1");
    }


    // Time Complexity: O(2^H)
    // We have to initialize and traverse the tree with 2^(H+1) many times 
    // for each possible element, even if it's null
    // Space Complexity: O(2^H)
    // We have to create an array that can contain all the possible nodes
    // of a tree given its height

    // Convert tree to array representation
    public String[] toArray() {
        String[] codesArray = new String[(int)Math.pow(2.0, this.height() + 1)];
        // Start index at 1, since 0 is dummy header
        int idx = 1;
        toArray(root, codesArray, idx, "");

        return codesArray;

    }

    private void toArray(TNode t, String[] codesArray, int idx, String path) {
        if (t == null) {
            return;
        }

        // Store path if leaf, I if internal node
        if(t.data != null) {
            codesArray[idx] = path;
        } else {
            codesArray[idx] = "I";
        }

        // Use array indexing, left child = 2 * i, right child = 2 * i + 1
        toArray(t.left, codesArray, 2*idx, path + "0");
        toArray(t.right, codesArray, 2*idx + 1, path + "1");
    }

    // Test class for constructors
    public static void main(String[] args){
            System.out.println("------------------Testing Constructor 1--------------");
            System.out.println("Expected Output: \"I num=0\"");
            BinTree myTree = new BinTree();
            myTree.printTree();
            System.out.println("num=" + myTree.num);


            System.out.println("\n------------------Testing method insertCodeword--------------");
            System.out.println("\n------------------Test 1. Valid Input--------------");
            myTree.insertCodeword("c0", "10");
            System.out.println("Expected Output: \"I c0 I num=1\""); 
            myTree.printTree();
            System.out.println("num=" + myTree.num);

            System.out.println("\n------------------Testing method insertCodeword--------------");
            System.out.println("\n------------------Test 2. Valid Input--------------");
            myTree.insertCodeword("c1", "001");
            System.out.println("Expected Output: \"I c1 I I c0 I num=2\""); 
            myTree.printTree();
            System.out.println("num=" + myTree.num);

            System.out.println("\n------------------Testing method insertCodeword--------------");
            System.out.println("\n------------------Test 3. Invalid Input. Duplicate codeword already in the tree--------------");
            System.out.println("Expected Output: \"Prefix condition violated!\""); 
            try{
                myTree.insertCodeword("c2", "001");
                myTree.printTree();
                System.out.println("num=" + myTree.num);
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }

            System.out.println("\n------------------Testing method insertCodeword--------------");
            System.out.println("\n------------------Test 4. Invalid Input. A strict sufffix already in the tree--------------");
            System.out.println("Expected Output: \"Prefix condition violated!\""); 
            try{
                myTree.insertCodeword("c2", "00");
                myTree.printTree();
                System.out.println("num=" + myTree.num);
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }

            System.out.println("\n------------------Testing method insertCodeword--------------");
            System.out.println("\n------------------Test 5. Invalid Input. A strict prefix already in the tree--------------");
            System.out.println("Expected Output: \"Prefix condition violated!\""); 
            try{
                myTree.insertCodeword("c2", "0011");
                myTree.printTree();
                System.out.println("num=" + myTree.num);
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }

            System.out.println("\n------------------Testing Constructor 2--------------");
            System.out.println("\n------------------Test 6. Valid Input--------------");
            String[] a = {"10", "0", "111", "110"};
            myTree = new BinTree(a);
            System.out.println("Expected Output: \"c1 I c0 I c3 I c2 num=4\""); 
            myTree.printTree();
            System.out.println("num=" + myTree.num);

            System.out.println("\n------------------Testing Constructor 2--------------");
            System.out.println("\n------------------Test 7. Invalid Input. Some inputs are not binary--------------");
            String[] b = {"10", "0", "1a1", "110"};
            System.out.println("Expected Output: \"Invalid Argument!\""); 
            try{
                myTree = new BinTree(b);
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }

            System.out.println("\n------------------Testing Constructor 2--------------");
            System.out.println("\n------------------Test 8. Invalid Input. Inputs are the same--------------");
            String[] c = {"10", "10"};
            System.out.println("Expected Output: \"Prefix condition violated!\""); 
            try{
                myTree = new BinTree(c);
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
            
        }//end main
       
}// end class
