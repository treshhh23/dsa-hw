package Lab1Files;

import java.util.Arrays;
import java.util.List;

public class Test {

    private static void check(boolean condition) {
    if (condition) {
        System.out.println(">> Test Passed");
    } else {
        System.out.println(">> Test Failed");
    }
}
    public static void main(String[] args) {
        String[] codes1 = {"10", "0", "111", "110"};
        String[] codes2 = {"00", "01", "10", "110", "111"};
        BinTree tree1 = new BinTree(codes1);
        BinTree tree2 = new BinTree(codes2);

        System.out.println("\n------------------Testing getNumberOfCodewords--------------");
        
        System.out.println("Expected Output: 4");
        System.out.println("Actual Output:   " + tree1.getNumberOfCodewords());
        check(tree1.getNumberOfCodewords() == 4);

        System.out.println("Expected Output: 5");
        System.out.println("Actual Output:   " + tree2.getNumberOfCodewords());
        check(tree2.getNumberOfCodewords() == 5);

        System.out.println("\n------------------Testing height--------------");
        
        System.out.println("Expected Output: 3");
        System.out.println("Actual Output:   " + tree1.height());
        check(tree1.height() == 3);

        System.out.println("Expected Output: 3");
        System.out.println("Actual Output:   " + tree1.height());
        check(tree1.height() == 3);

        System.out.println("\n------------------Testing getCodewords--------------");

        System.out.println("Expected Output: [0, 10, 110, 111]"); 
        System.out.println("Actual Output:   " + tree1.getCodewords());
        List<String> expected1 = Arrays.asList("0", "10", "110", "111");
        check(tree1.getCodewords().equals(expected1));

        System.out.println("\nExpected Output: [00, 01, 10, 110, 111]");
        System.out.println("Actual Output:   " + tree2.getCodewords());
        List<String> expected2 = Arrays.asList("00", "01", "10", "110", "111");
        check(tree2.getCodewords().equals(expected2));

        System.out.println("\n------------------Testing decode()--------------");
        
        String inputBits = "0011001010111";
        System.out.println("Input: " + inputBits);

        List<String> expectedDecode = Arrays.asList("c1", "c1", "c3", "c1", "c0", "c0", "c2");
        List<String> actualDecode = tree1.decode(inputBits);

        System.out.println("Expected: " + expectedDecode);
        System.out.println("Actual:   " + actualDecode);
        check(actualDecode.equals(expectedDecode));

        System.out.println("\nInput: \"\" (Empty String)");
        List<String> emptyList = tree1.decode("");
        System.out.println("Actual: " + emptyList);
        check(emptyList.isEmpty());

        System.out.println("\n------------------Testing toString()--------------");

        String expectedString = "(c0,10) (c1,0) (c2,111) (c3,110) "; 
        String actualString = tree1.toString();

        System.out.println("Expected: \"" + expectedString + "\"");
        System.out.println("Actual:   \"" + actualString + "\"");
        check(actualString.equals(expectedString));

        String expectedString2 = "(c0,00) (c1,01) (c2,10) (c3,110) (c4,111) ";
        String actualString2 = tree2.toString();

        System.out.println("\nExpected: \"" + expectedString2 + "\"");
        System.out.println("Actual:   \"" + actualString2 + "\"");
        check(actualString2.equals(expectedString2));

        System.out.println("\n------------------Testing toArray()--------------");

        String[] actualArr = tree1.toArray();
        String actualArrayStr = Arrays.toString(actualArr);
        String expectedArrayStr = "[null, I, 0, I, null, null, 10, I, null, null, null, null, null, null, 110, 111]";

        System.out.println("Expected: " + expectedArrayStr);
        System.out.println("Actual:   " + actualArrayStr);
        check(actualArrayStr.equals(expectedArrayStr));

        int expectedSize = (int) Math.pow(2, tree1.height() + 1);
        System.out.println("\nChecking Array Size");
        System.out.println("Expected: " + expectedSize);
        System.out.println("Actual:   " + actualArr.length);
        check(actualArr.length == expectedSize);

        
    }

}
