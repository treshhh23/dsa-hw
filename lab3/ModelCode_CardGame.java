import java.util.*;

public class ModelCode_CardGame {

    public static final int POCKETSIZE = 25;
    public static Scanner myInputScanner;          
    
    public static void main(String args[]) throws Exception
    {            
        // Turn-base AI (Aggresive) vs Player

        // General Rules
        // AI and Player get 25 cards each, 5 turns.  Player and AI Score initialized to zero.
        // In each turn
        //      Sort the cards, print AI Cards, then My Cards, numbered in increasing order
        //      Player makes a choice - proceed when valid
        //      AI makes a move, compare Player's hand vs. AI's hand
        //      Update score
        //      Players and AI cannot make INVALID moves until they are out of valid hands
        // At the end of the game, report winner with score


        // You can upgrade your Lab 2 algorithm to Lab 3 to complete this game
        // You can also redesign the entire game loop logic

        // Step 1 - Initialization
        //  - Given the CardPool instance, get 25 cards (POCKETSIZE) for both AI and Player.
        //  - Sort their cards using sortCards(). Assign a serial number to Player's cards
        //  - Instantiate a HandsRBT for the player and invoke generateHandsIntoRBT() 
        //    to populate the player RBT with all possible hands from the pocket card.
        //  - Instantiate a HandsBST for AI and invoke generateHandsIntoBST() 
        //    to populate the AI BST with all possible hands from the pocket card.

        //  - If you have successfully completed Lab 2, you may replace HandsBST with your own HandsMaxHeap
        //    to improve the program performance.

        // Step 2 - Game Loop Logic
        //  - Given that POCKETSIZE = 25 and a 5-card hand is consumed at each round, our game loop should only 
        //    repeat 5 times.  You can optionally parameterize the iteration count for scalability.

            // Step 2-1 : Print Both AI and Player Pocket Cards for Strategy Analysis
            //            - Also check if RBT is empty.  If yes, notify player that he/she is out of moves.
            //            - When printing the Player pocket cards, you **MUST** print with serial number.

            // Step 2-2 : Use the provided getUserHand() method to allow player to pick the 5-card hand from
            //            the pocket cards.
            //              - After the hand is chosen, check if this hand can be found in the RBT
            //              -  If this hand is not in the RBT and the RBT is not empty
            //                 notify the player that there are still valid 5-card hands and cannot pass.
            //                 Wait for Player to input another hand

            // Step 2-3 : Save the chosen hand as "PLAYERHAND", and update pocket card and RBT
            //            - Delete the invalid hands from the RBT using deleteInvalidHands()
            //            - Remove the consumed 5 cards from the pocket cards. 
            //            - Remember to reduce the pocket size by 5.

            // Step 2-4 : Using the logic from Lab 2, construct the Aggressive AI Logic
            //            - If you have completed Lab 2, you may use HandsMaxHeap.  
            //              Otherwise, you can use the provided HandsBST (a binary search tree of hands - apply knowledge from 2SI3)
            //            - For every 5-card move made, remove the consumed 5 cards from AI pocket cards, reduce the pocket size
            //              then regenerate the MaxHeap/HandsBST 
            //            - Save the chosen move as the "AIHAND"
            //            - Remember, once out of valid hands, AI can pick any cards to form a 5-card pass move.

            // Step 2-5 : Determine the Win/Lose result for this round, and update the scores for AI or Player
            //            - Print both PLAYERHAND and AIHAND for visual confirmation
            //            - Compare hands, and increase the score for the respective party who wins the round
            //            - An unlikely Draw (no winner) condition will result in no score increase for either party


        // Step 3 - Report the Results
        //  - This part is easy.  Refer to the provided sample execution for printout format
        CardPool myCardPool;
        Card[] aiCards; 
        Card[] myCards;        
        Hands aiHand = null, myHand = null;
        
        HandsBST aiBST; // you may replace this with your own HandsMaxHeap for improved performance.
        HandsRBT myRBT;
                
        int aiPocketSize = POCKETSIZE, myPocketSize = POCKETSIZE;
        int aiScore = 0, playerScore = 0; 
        
        myCardPool = new CardPool();         
        myInputScanner = new Scanner(System.in);

        // Initialization, generate and sort cards into our data structures
        aiCards = myCardPool.getRandomCards(POCKETSIZE);
        myCards = myCardPool.getRandomCards(POCKETSIZE);

        sortCards(aiCards);
        sortCards(myCards);

        aiBST = new HandsBST();
        myRBT = new HandsRBT();
        
        generateHandsIntoBST(aiCards, aiBST);
        generateHandsIntoRBT(myCards, myRBT);

        // Game loop
        for (int round = 0; round < 5; round++) {
            
            // Print pocket cards for both players
            System.out.println("AI Pocket Cards:");
            for (int i = 0; i < aiPocketSize; i++) {
                aiCards[i].printCard();
                System.out.print(" ");
            }
            System.out.println();
            
            System.out.printf("My Pocket Cards (Count: %d)\n", myPocketSize);
            for (int i = 0; i < myPocketSize; i++) {
                System.out.printf("[%d]", i + 1);
                myCards[i].printCard();
                System.out.print(" ");
            }
            System.out.println();

            // Check if tree is empty to signify out of moves
            if (myRBT.isEmpty()) {
                System.out.println("OUT OF HANDS!");
            }

            // Player picks 5 cards
            boolean validChoice = false;
            while (!validChoice) {
                myHand = getUserHand(myCards);
                
                // Check if invalid hand
                if (!myRBT.isEmpty() && myRBT.findNode(myHand) == null) {
                    System.out.println("Cannot Pass! You still have valid 5-card hands to make a move.");
                } else {
                    validChoice = true;
                }
            }

            // Save the valid hand, and delete it from the RBT
            if (!myRBT.isEmpty()) {
                myRBT.deleteInvalidHands(myHand);
            }

            myCards = removeConsumedCards(myCards, myHand);
            myPocketSize -= 5;

            // AI moves
            if (aiBST.isEmpty()) {
                // If out of valid hands, just pick the first 5 cards
                aiHand = new Hands(aiCards[0], aiCards[1], aiCards[2], aiCards[3], aiCards[4]);
            } else {
                aiHand = aiBST.getMaxHand();
            }
            
            // Remove the consumed 5 cards from AI pocket cards
            aiCards = removeConsumedCards(aiCards, aiHand);
            aiPocketSize -= 5;
            
            aiBST = new HandsBST();
            generateHandsIntoBST(aiCards, aiBST);

            // Print the moves
            System.out.print("My Hand: ");
            myHand.printMyHand();
            System.out.println();
            
            System.out.print("AI Hand: ");
            aiHand.printMyHand();
            System.out.println();

            // Compare hands, and increment the score for whoever wins
            if (myHand.isMyHandLarger(aiHand)) {
                System.out.println("[RESULT] Player Wins This Round!\n");
                playerScore++;
            } else if (aiHand.isMyHandLarger(myHand)) {
                System.out.println("[RESULT] AI Wins This Round!\n");
                aiScore++;
            } else {
                System.out.println("[RESULT] Draw!\n");
            }
        }

        // Results
        System.out.println("==== Game Result ====");
        System.out.printf("Player Score: %d\n", playerScore);
        System.out.printf("AI Score: %d\n", aiScore);
        
        if (playerScore > aiScore) {
            System.out.println("<< Player Won >>");
        } else if (aiScore > playerScore) {
            System.out.println("<< AI Won >>");
        } else {
            System.out.println("<< It's a Tie! >>");
        }
        myInputScanner.close();
    }

    public static void generateHandsIntoBST(Card[] cards, HandsBST thisBST)
    {
        // Implement this if you are using the BST version for the Aggressive AI
        // Populate all valid hands into the BST
        int n = cards.length;
        for (int i = 0; i < n - 4; i++) {
            for (int j = i + 1; j < n - 3; j++) {
                for (int k = j + 1; k < n - 2; k++) {
                    for (int l = k + 1; l < n - 1; l++) {
                        for (int m = l + 1; m < n; m++) {
                            Hands h = new Hands(cards[i], cards[j], cards[k], cards[l], cards[m]);
                            if (h.isAValidHand()) {
                                thisBST.insert(h); 
                            }
                        }
                    }
                }
            }
        }
    
    }

    public static void generateHandsIntoRBT(Card[] cards, HandsRBT thisRBT)
    {
        // Populate all valid hands into the RBT
        int n = cards.length;
        for (int i = 0; i < n - 4; i++) {
            for (int j = i + 1; j < n - 3; j++) {
                for (int k = j + 1; k < n - 2; k++) {
                    for (int l = k + 1; l < n - 1; l++) {
                        for (int m = l + 1; m < n; m++) {
                            Hands h = new Hands(cards[i], cards[j], cards[k], cards[l], cards[m]);
                            if (h.isAValidHand()) {
                                thisRBT.insert(h);
                            }
                        }
                    }
                }
            }
        }   
    }

    public static void sortCards(Card[] cards)
    {
        // Sort the cards in increasing order of rank; for equal rank sort in increasing order of suite
        int j, size;
        Card temp; 
        size = cards.length;

        for(int i = 1; i < size; i++) 
        { 
            temp = cards[i];		
            for(j = i; j > 0 && cards[j-1].isMyCardLarger(temp); j--) 
                cards[j] = cards[j-1]; 
            cards[j] = temp;
        }  
    }

    public static Card[] removeConsumedCards(Card[] pocket, Hands consumedHand) {
        if (pocket.length < 5) return new Card[0];
        Card[] newPocket = new Card[pocket.length - 5];
        int newIdx = 0;
        boolean[] removed = new boolean[pocket.length];
        
        for (int i = 0; i < 5; i++) {
            Card c = consumedHand.getCard(i);
            for (int j = 0; j < pocket.length; j++) {
                if (!removed[j] && pocket[j].isMyCardEqual(c)) {
                    removed[j] = true;
                    break;
                }
            }
        }
        
        for (int j = 0; j < pocket.length; j++) {
            if (!removed[j]) {
                newPocket[newIdx++] = pocket[j];
            }
        }
        return newPocket;
    }


    // This method enables Player to use the numerical key entries to select
    // the 5 cards to form a hand as a tentative move.
    public static Hands getUserHand(Card[] myCards)
    {
        int size = myCards.length;
        int[] mySelection = new int[5];  
        myInputScanner = new Scanner(System.in);

        System.out.println();
        for(int i = 0; i < 5; i++)
        {            
            System.out.printf("Card Choice #%d: ", i + 1);
            mySelection[i] = myInputScanner.nextInt() - 1;
            if(mySelection[i] > size) mySelection[i] = size - 1;
            if(mySelection[i] < 0) mySelection[i] = 0;            
        }
        
        Hands newHand = new Hands(  myCards[mySelection[0]], 
                                    myCards[mySelection[1]], 
                                    myCards[mySelection[2]], 
                                    myCards[mySelection[3]], 
                                    myCards[mySelection[4]]);

        return newHand;
    }

}
