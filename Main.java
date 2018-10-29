
/**
 * Write a description of Main here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.Random;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {
    String baseDeck = "AAAA22223333444455556666777788889999TTTTJJJJQQQQKKKK";
    String currentDeck = "";
    int[][] advancedResultsWins = new int[12][22]; // [Dealer Shows][Stratagy]
    int[][] advancedResultsLosses = new int[12][22]; // [Dealer Shows][Stratagy]
    public String dealCard(int cardSelected){
        if (currentDeck.length() < 1){return "Deck Empty";}
        if (cardSelected == -1){
            Random rand = new Random();
            cardSelected = rand.nextInt(currentDeck.length());
        }   
        String card = currentDeck.substring(cardSelected, cardSelected + 1);
        if (cardSelected == 0){
            currentDeck = currentDeck.substring(1,currentDeck.length());
        }
        else {
            currentDeck = currentDeck.substring(0,cardSelected) + currentDeck.substring(cardSelected + 1, currentDeck.length());
        }
        return card;
    }
    public int playHand(int stratagy, String startingCardA, String startingCardB){
        int handTotal = getCardValue(startingCardA) + getCardValue(startingCardB);
        //System.out.println("Starting cards: " + startingCardA + "," + startingCardB);
        //System.out.println("Starting hand value: " + handTotal);
        String cardsInHand = startingCardA + startingCardB;
        while(handTotal < stratagy){
            String newCard = dealCard(-1);
            cardsInHand = cardsInHand + newCard;
            handTotal = handTotal + getCardValue(newCard);
            int acePresent = cardsInHand.indexOf("A");
            if(acePresent != -1 && handTotal > 21){
                handTotal = handTotal - 10;
                cardsInHand = cardsInHand.substring(0,acePresent) + "1" + cardsInHand.substring(acePresent + 1, cardsInHand.length());
            }
            //System.out.println("Player Hit. Card Dealt: " + newCard);
            //System.out.println("New hand total: " + handTotal);
        }
        if (handTotal > 21){/*System.out.println("Player Bust!");*/}
        else if(handTotal == 21){/*System.out.println("Blackjack!");*/}
        else {/*System.out.println("Player Sticks.");*/}
        return handTotal;
    }
    public int playDealersHand(String startingCardA, String startingCardB){
        int stratagy = 17;
        int handTotal = getCardValue(startingCardA) + getCardValue(startingCardB);
        //System.out.println("Dealer Shows: " + startingCardA + " Dealer Hides: "+ startingCardB);
        //System.out.println("Dealer hand value: " + handTotal);
        String cardsInHand = startingCardA + startingCardB;
        while(handTotal < stratagy){
            String newCard = dealCard(-1);
            cardsInHand = cardsInHand + newCard;
            handTotal = handTotal + getCardValue(newCard);
            int acePresent = cardsInHand.indexOf("A");
            if(acePresent != -1 && handTotal > 21){
                handTotal = handTotal - 10;
                cardsInHand = cardsInHand.substring(0,acePresent) + "1" + cardsInHand.substring(acePresent + 1, cardsInHand.length());
            }
            //System.out.println("Dealer Hit. Card Dealt: " + newCard);
            //System.out.println("New hand total: " + handTotal);
        }
        if (handTotal > 21){/*System.out.println("Dealer Bust!");*/}
        else if(handTotal == 21){/*System.out.println("Blackjack!");*/}
        else {/*System.out.println("Dealer Sticks.");*/}
        return handTotal;
    }
    public int getCardValue(String card){
        int cardValue = 0;
        if (card.equals("K") || card.equals("Q") || card.equals("J") || card.equals("T")){cardValue = 10;}
        else if (card.equals("A")){cardValue = 11;}
        else if (card.equals("Deck Empty")){}
        else{ cardValue = Integer.parseInt(card);}
        return cardValue;
    }
    public String getResultMessage(int playersTotal, int dealersTotal){
        String resultMessage = "Error";
        if (playersTotal + dealersTotal > 42){resultMessage = "Everyone Bust! It's a Draw";}
        else if (playersTotal > 21){resultMessage = "Player Bust. Dealer wins.";}
        else if (dealersTotal > 21){resultMessage = "Dealer Bust. Player wins.";}
        else if (playersTotal == dealersTotal){resultMessage = "Scores Tied. It's a Draw.";}
        else if (playersTotal < dealersTotal){resultMessage = "Dealer Wins.";}
        else if (playersTotal > dealersTotal){resultMessage = "Player Wins.";}
        return resultMessage;
    }
    public int getResult(int playersTotal, int dealersTotal){
        int result = -1;
        if (playersTotal + dealersTotal > 42){result = 2;}
        else if (playersTotal > 21){result = 2;}
        else if (dealersTotal > 21){result = 1;}
        else if (playersTotal == dealersTotal){result = 0;}
        else if (playersTotal < dealersTotal){result = 2;}
        else if (playersTotal > dealersTotal){result = 1;}
        return result;
    }
    public void testDeal(){
        currentDeck = baseDeck;
        for (int i = 0; i<60; i++){
           String dealtCard = dealCard(-1);
           //System.out.println("Card: " + dealtCard + " Card Value: " + getCardValue(dealtCard));
        }
    }
    public int testPlayHand(int stratagy){
        //currentDeck = baseDeck + baseDeck + baseDeck + baseDeck;
        if (currentDeck.length() < 292){
             currentDeck = baseDeck + baseDeck + baseDeck + baseDeck + baseDeck + baseDeck;   
            }
            
        String startingCardA = dealCard(-1);
        String startingCardB = dealCard(-1);
        String startingCardC = dealCard(-1);
        String startingCardD = dealCard(-1);
        int playersTotal = playHand(stratagy, startingCardA, startingCardB);
        //System.out.println(" ");
        int dealersTotal = playDealersHand(startingCardC, startingCardD);
        //System.out.println(" ");
        //System.out.println(getResultMessage(playersTotal, dealersTotal));
        int result = getResult(playersTotal, dealersTotal);
        if (result == 1){advancedResultsWins[getCardValue(startingCardC)][stratagy]++;}
        if (result == 2){advancedResultsLosses[getCardValue(startingCardC)][stratagy]++;}
        return result;
    }
    public void testPlayManyHands(){
        for (int i = 0; i < 10; i++){
            testPlayHand(17);
            //System.out.println(" ");
        }
    }
    public double playManyHandsResults(int stratagy){
        
        double draws = 0; double wins = 0; double losses = 0;
        for (int i = 0; i < 1000000; i++){
            //System.out.println("Playing Game: " + i + " using stratagy " + stratagy);
            int gameResult = testPlayHand(stratagy);
            switch (gameResult) {
                case 0: draws++; break;
                case 1: wins++; break;
                case 2: losses++; break;
                default: break;
            }      
        }
        double totalGames = draws + wins + losses;
        //System.out.println("Total Games: " + totalGames);
        //System.out.println("Wins: " + wins);
        //System.out.println("Losses: " + losses);
        //System.out.println("Draws: " + draws);
        double winChance = (wins*100)/(wins + losses);
        //System.out.println("Vs Percentage: " + winChance + "%");
        return winChance;
    }
    public void evaluateBasicStratagy(){
        double currentBestWinPercentage = 0;
        double currentWinPercentage = 0;
        int currentBestWinStratagy = 0;
        String results = "";
        String iString = "";
        String percentageString = "";
        
        for (int i = 3; i <= 21; i++){
            
            currentWinPercentage = playManyHandsResults(i);
            if (currentWinPercentage > currentBestWinPercentage){
                currentBestWinPercentage = currentWinPercentage;
                currentBestWinStratagy = i;
            }
            int percentage = (int) currentWinPercentage;
            if (i<10){iString = "0" + i;}
            else {iString = Integer.toString(i);}
            results = iString + "-" + percentage + "% ";
            System.out.println(results);
        }
        System.out.println("The best basic stratagy is " + currentBestWinStratagy + " with a winrate of " + currentBestWinPercentage);
        System.out.println(" ");
        String printLine = "";
        int advancedPercentage = 0;
        System.out.println("Strat:     03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20 21");
        for (int i = 2; i < 12; i++){
            if (i<10){iString = "0" + i;}
            else {iString = Integer.toString(i);}
           printLine = "Dealer: " + iString;
           for (int j = 3; j <= 21; j++){
               if(advancedResultsLosses[i][j] != 0 && advancedResultsLosses[i][j] != 0){
                   advancedPercentage = (advancedResultsWins[i][j] * 100) / (advancedResultsLosses[i][j] + advancedResultsLosses[i][j]);
               }
               if (advancedPercentage < 10){percentageString = "0" + advancedPercentage;}
               else {percentageString = Integer.toString(advancedPercentage);}
               printLine = printLine + " " + percentageString;
               advancedResultsWins[i][j] = 0;
               advancedResultsLosses[i][j] = 0;
            }
           System.out.println(printLine);
        }
    }
}
