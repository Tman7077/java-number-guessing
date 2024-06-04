package numberguessing;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Main {
    public static void main(String[] args)
            throws IOException {
        // allows the system to rerad the user input from the console
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // the number the user inputs, should be 1 or 2
        int response = 0;

        // display the menu to the user
        do {
            System.out.println("Welcome! Enter 1 or 2.");
            System.out.println("  1. Play guessing game");
            System.out.println("  2. Quit");

            // whether or not to [b]reak the loop
            boolean b;
            do {
                try {
                    response = Integer.parseInt(reader.readLine());
                    // if the user input 1 or 2, the valid choices, break the loop
                    if (response == 1 || response == 2) {
                        b = true;
                    }
                    // if the user input a number, but not 1 or 2, try again
                    else
                    {
                        System.out.println("Please enter 1 or 2 to continue.");
                        b = false;
                    }
                }
                // if the user input something that can't be parsed as an integer, try again
                catch (NumberFormatException e) {
                    System.out.println("Please enter an integer (must be 1 or 2).");
                    b = false;
                }
            } while (!b);

            // if the user selected to play the game
            if (response == 1) {
                Play();
            }
            // else { do nothing, which quits }
        } while (response != 2);
    }
    // actually play the game
    public static void Play()
            throws IOException {
        // allows the system to rerad the user input from the console
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Between 1 and what number would you like to guess?");
        // the number the user inputs, allowed to be between 3 and 1,000,000 (arbitrary values)
        int randCap = 0;
        // whether or not to [b]reak the loop
        boolean b;

        // collect teh max number from the user
        do { 
            try {
                randCap = Integer.parseInt(reader.readLine());
                // if the max value entered is valid, break the loop
                if (randCap > 2 && randCap < 1000000) {
                    b = true;
                }
                // if the user entered a number, but it isn't valid, try again
                else
                {
                    System.out.println("Please enter an integer greater than 3 and less than 1,000,000.");
                    b = false;
                }
            // if the user input something that can't be parsed as an integer, try again
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer.");
                b = false;
            }
        } while (!b);

        // generate a random number from 1 to whatever the user said
        int answer = new Random().nextInt(randCap) + 1;
        System.out.println("Random number from 1 to " + randCap + " generated.");
        // the nnumber of guesses that are guaranteed to get the right answer (with the right strategy),
            // plus roughly 20% (an arbitrary increase just to make it easier)
        int numAllowedGuesses = GetNumAllowedGuesses(randCap);
        System.out.println("You have " + numAllowedGuesses + " guesses left.");
        // number of times the user has made a guess
            // this is only incremented when they make a valid guess
        int numGuesses = 0;
        // the number the user guessed
        int guess = 0;

        // get the user's guess and determine if it's right
        do { 
            try {
                guess = Integer.parseInt(reader.readLine());
                // if the guess is outside the valid range, try again
                if (guess < 1 || guess > randCap)
                {
                    System.out.println(guess + " is outside the range of 1 to " + randCap + " and is therefore not a valid guess. Try again.");
                    b = false;
                }
                // if the user got it right, congratulate them
                else if (guess == answer)
                {
                    System.out.println("Great job! You guessed it in " + (numGuesses + 1) + " guesses.");
                    b = true;
                }
                // inform the user how to get closer, then try again
                else
                {
                    System.out.println("Not quite. " + guess + " is too " + (guess > answer ? "high." : "low."));
                    numGuesses++;
                    b = false;
                }
            // if the user input something that can't be parsed as an integer, try again
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer.");
                b = false;
            }
        } while (!b && numGuesses < numAllowedGuesses);

        // if the user did not successfully guess the number
        if (numGuesses == numAllowedGuesses && guess != answer) {
            System.out.println("Good try, but you ran out of guesses. The number was " + answer + ".");
        }
    }

    // get the number of guesses the user is allowed to make to win, based on the max number they chose
    public static int GetNumAllowedGuesses(int max) {
        int numAllowedGuesses = 0;

        // get the powers of two between which their max number lies,
            // and return that value plus 20% (as the lowest greater integer)
        for (int i = 1; i <= 20; i++) {
            if (max >= Math.pow(2, i) && max < Math.pow(2, i + 1)) {
                numAllowedGuesses = (int)Math.ceil(i * 1.2);
            }
        }
        return numAllowedGuesses;
    }
}