package Lab1Files;

import java.util.ArrayList;

public class BinTree{
    private TNode root;
    private int num = 0;

    // //constructor 1; creates a tree with only the root node
    public  BinTree(){
        root = new TNode(null, null, null);
    }

    private void insertCodeword(String symbol, String binary){
        
	TNode node = this.root; //node is a reference variable for the current tree node; 
        			//it first points to the root
        
        //read the characters in the binary string and take the appropriate action for each of them

        int n = binary.length(); // length of binary string
        char c; // to store the current character in the binary string
	
	//loop through the binary string
        for(int i = 0; i < n; i++){
            // read current character in the binary string
	        c = binary.charAt(i);
            //We can write the method in such a way to make sure that at this point in the code 
	        //the current node exists (it is not null);  
            //go to left child if c=='0' or to right child if c=='1', 
            // but if the corresponding child does not exist, create it
            if(c=='0'){
                //check if left child exists; if it doesn't exist, 
                //create it as an internal node with data == null; then go to the left child
                if(node.left == null) {
                    node.left = new TNode(null, null,null);
                }
                node = node.left;
            } else {
                //check if right child exists; if it doesn't exist, create it as an internal node
                //then go to the right child
                if(node.right == null) {
                    node.right = new TNode(null, null,null);
                }
                node = node.right;
            }// end if 
            //if current node is a codeword, this means that the prefix condition is violated
	        //and an exception is thrown
            if(node.data != null)
                 throw new IllegalArgumentException("Prefix condition violated!");
        }// end for

        // now the current node is at the end of the path corresponding to the input binary string
        // if the current node is not a leaf then there must a codeword in the tree 
	    // that is suffix of the input binary string, so the prefix condition is violated
        if((node.left != null) || (node.right != null))
            throw new IllegalArgumentException("Prefix condition violated!");
        else 
            { 
             //copy symbol in the node
             //increase by 1 the number of codewords
             node.data = symbol;
             num++;
            }
    }// end method
    
    //constructor 2
    public BinTree(String[] a) throws IllegalArgumentException{

        //check if all items in the array are binary strings
	    //if they are not, throw an exception
        int k = a.length; // array length
        char c; // to store the current character in the binary string

	//loop through array a
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
    
    public void printTree(){printTree(root);}

    private void printTree(TNode t) {
        if(t != null) {
            printTree(t.left);
            if(t.data == null)
                System.out.print("I ");
            else 
                System.out.print(t.data + " ");
            printTree(t.right);
        }
    }

    public int getNumberOfCodewords() {
        return this.num;
    }

    public int height() {return height(root);}

    private int height(TNode t) {
        if(t == null) {
            return -1;
        }

        int left_height = height(t.left);
        int right_height = height(t.right);

        return Math.max(left_height, right_height) + 1;
    }

    // Public Interface
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
        if (t.data != null) {
            list.add(path); 
        }

        getCodewords(t.left, path + "0", list);
        getCodewords(t.right, path + "1", list);
    }

    public ArrayList<String> decode(String s) {
        int n = s.length();
        TNode node = this.root;
        ArrayList<String> decodedString = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') {
                node = node.left;
            } else {
                node = node.right;
            }

            if (node.data != null) {
                decodedString.add(node.data);
                node = this.root;
            }
        }

        return decodedString;
    }

public String toString() {
    String[] codes = new String[this.getNumberOfCodewords()]; 
    toString(root, codes, "");
    String result = "";

    for (int j = 0; j < codes.length; j++) {
            result += codes[j];
    }
    
    return result;
}

private void toString(TNode t, String[] codes, String path) {
    if (t == null) {
        return;
    }

    if (t.data != null) {
        int i = Integer.parseInt(t.data.substring(1));
        codes[i] = "(" + t.data + "," + path + ") ";
    }

    toString(t.left, codes, path + "0");
    toString(t.right, codes, path + "1");
}

public String[] toArray() {
    String[] codesArray = new String[(int)Math.pow(2.0, this.height() + 1)];
    int idx = 1;
    toArray(root, codesArray, idx, "");

    return codesArray;

}

private void toArray(TNode t, String[] codesArray, int idx, String path) {
    if (t == null) {
        return;
    }

    if(t.data != null) {
        codesArray[idx] = path;
    } else {
        codesArray[idx] = "I";
    }

    toArray(t.left, codesArray, 2*idx, path + "0");
    toArray(t.right, codesArray, 2*idx + 1, path + "1");
}

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
        
    }//end main
       
}// end class
