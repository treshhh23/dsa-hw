
public class ModelCode_CardGame {

    public static final int POCKETSIZE = 25;

    public static CardPool myCardPool;
    public static HandsMaxHeap myMaxHeap;

    public static Card[] myCards, tempCards;
    public static int pocketSize = POCKETSIZE;

    // [Problem 2] Generate All Possible Valid Hands from the Pocket Cards and store them in myMaxHeap
    public static void generateHands(Card[] thisPocket)
    {
        // Pay attention that memory needs to be allocated for the heap!
        myMaxHeap = new HandsMaxHeap(60000); // Generate memory in case of all combinations valid
        
        int n = thisPocket.length;

        // If thisPocket has less than 5 cards, no hand can be generated, thus the heap will be empty
        if (n < 5) return;
        
        // Otherwise, generate all possible valid hands from thisPocket and store them in myMaxHeap
        for (int i = 0; i < n - 4; i++) {
            for (int j = i + 1; j < n - 3; j++) {
                for (int k = j + 1; k < n - 2; k++) {
                    for (int l = k + 1; l < n - 1; l++) {
                        for (int m = l + 1; m < n; m++) {
                            
                            Hands hand = new Hands(thisPocket[i], thisPocket[j], thisPocket[k], thisPocket[l], thisPocket[m]);
                            
                            if (hand.isAValidHand()) {
                                myMaxHeap.insert(hand);
                            }
                        }
                    }
                }
            }
        }
    }

    // Sorts the array of Cards in ascending order: ascending order of ranks; cards of equal ranks are sorted in ascending order of suits
    public static void sortCards(Card[] cards)
    {
        int j;
        Card temp;        
        int size = cards.length;
        
        for(int i = 1; i < size; i++) 
        { 
            temp = cards[i];		
            for(j = i; j > 0 && cards[j-1].isMyCardLarger(temp); j--) 
                cards[j] = cards[j-1]; 
            cards[j] = temp;
        }  
    }

    public static void main(String args[]) throws Exception
    {
        Hands myMove;        
        
        myCardPool = new CardPool();        
        myCardPool.printPool();

        myCards = new Card[pocketSize];
        myCards = myCardPool.getRandomCards(POCKETSIZE);  
        sortCards(myCards);

        // print cards
        System.out.println("My Pocket Cards:");
        for(int j = 0; j < pocketSize; j++)
        {            
            myCards[j].printCard();
        }
        System.out.println();
        
        generateHands(myCards); // generates all valid hands from myCards and stores them in myMaxHeap
        myMaxHeap.printHeap(); // prints the contents of the initial heap

        // Print the contents of myMaxHeap
        
        // [Problem 3] Implementing Game Logic Part 1 - Aggresive AI: Always Picks the Strongest Hand
        for(int i = 0; pocketSize > 4; i++)
        {            
                                   
            // Step 1:
            // - Check if the Max Heap contains any valid hands 
            //   - if yes, remove the Largest Hand from the heap as the current Move
            //   - otherwise, you are out of valid hands.  Just pick any 5 cards from the pocket as a "Pass Move"
            // - Once a move is chosen, print the Hand for confirmation. MUST PRINT FOR VALIDATION!!
            
            // Step 2:
            // - Remove the Cards used in the move from the pocket cards and update the Max Heap
            // - Print the remaining cards and the contents of the heap


            // picks largest hand, if none just select 5 cards arbritarily
            if (!myMaxHeap.isEmpty()) {
                myMove = myMaxHeap.removeMax();
            } else {
                myMove = new Hands(myCards[0], myCards[1], myCards[2], myCards[3], myCards[4]);
            }

            // print move
            System.out.print("Move " + (i + 1) + ": ");
            myMove.printMyHand();
            System.out.println();
            
            // remove cards
            Card[] remainingCards = new Card[pocketSize - 5];
            int remainingIndex = 0;
            
            // only add cards that were not used
            for (int c = 0; c < pocketSize; c++) {
                boolean isUsed = false;
                for (int h = 0; h < 5; h++) {
                    if (myCards[c].isMyCardEqual(myMove.getCard(h))) {
                        isUsed = true;
                        break;
                    }
                }
                
                if (!isUsed) {
                    if (remainingIndex < remainingCards.length) {
                        remainingCards[remainingIndex++] = myCards[c];
                    }
                }
            }
            
            // create new array of cards with 5 less size
            myCards = remainingCards;
            pocketSize -= 5;

            // regenerate heap with new set of cards
            if (pocketSize >= 5) {
                generateHands(myCards);
            } else {
                // clear heap after last hand is dealt
                myMaxHeap = new HandsMaxHeap(0);
            }

            // Print the remaining cards
            System.out.println("Remaining Cards:");
            for (int k = 0; k < pocketSize; k++) {
                myCards[k].printCard();
            }
            System.out.println("\n");
            
            // Print the contents of the heap
            System.out.println("Heap Content:");
            myMaxHeap.printHeap();
            System.out.println("--------------------------------------------------");

            
                      
        }
        
    }

}
