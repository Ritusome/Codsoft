import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        int maxAttempts = 10;
        boolean playAgain = true;
        int totalRounds = 0;
        int totalScore = 0;

        while (playAgain) {
            int randomNumber = random.nextInt(100) + 1;
            int attempts = 0;
            boolean hasWon = false;

            System.out.println("Guess a number between 1 and 100:");

            while (attempts < maxAttempts) {
                System.out.print("Enter your guess: ");
                int userGuess = sc.nextInt();
                attempts++;

                if (userGuess == randomNumber) {
                    System.out.println("Congratulations! You guessed the correct number.");
                    hasWon = true;
                    break;
                } else if (userGuess > randomNumber) {
                    System.out.println("Your guess is too high.");
                } else {
                    System.out.println("Your guess is too low.");
                }

                System.out.println("Attempts remaining: " + (maxAttempts - attempts));
            }

            if (!hasWon) {
                System.out.println("Sorry, you've used all your attempts. The correct number was: " + randomNumber);
            }

            totalRounds++;
            totalScore += (maxAttempts - attempts);

            System.out.print("Do you want to play again? (yes/no): ");
            playAgain = sc.next().equalsIgnoreCase("yes");
        }

        System.out.println("Game over. You played " + totalRounds + " rounds with a total score of " + totalScore + " points.");
        sc.close();
    }
}
