import java.io.Serializable; //importing java serializable package
import java.util.Scanner; //importing java scanner

/**
 * Class representing a Forestry simulation.
 */
public class Forestry implements Serializable {
    // Scanner for user input
    private static final Scanner keyboard = new Scanner(System.in);

    /**
     * Main method for the Forestry simulation.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {

        Forest myForest; // The forest for the simulation
        int argIndex; // Index for command line arguments
        char choice; // User's menu choice

        // Welcome message
        System.out.println("Welcome to the Forestry Simulation");
        System.out.println("----------------------------------");

        // Loop through command line arguments
        for (argIndex = 0; argIndex < args.length; argIndex++) {
            System.out.println("Initializing from " + args[argIndex]);
            myForest = new Forest();
            if ((myForest = Forest.readForestFromCSV(args[argIndex])) != null) {
                // Run menu
                while (true) {
                    System.out.print("\n(P)rint, (A)dd, (C)ut, (G)row, (R)eap, (S)ave, (L)oad, (N)ext, e(X)it : ");
                    choice = keyboard.nextLine().toUpperCase().charAt(0);

                    switch (choice) {
                        case 'P':
                            // Code for print
                            myForest.print();
                            break;
                        case 'A':
                            // Code for add
                            myForest.add();
                            break;
                        case 'C':
                            // Code for cut
                            while (true) {
                                System.out.print("Tree number to cut down: ");
                                if (keyboard.hasNextInt()) {
                                    int index = keyboard.nextInt();
                                    // consume newline left-over
                                    keyboard.nextLine();
                                    if (index >= 0 && index < myForest.trees.size()) {
                                        myForest.cut(index);
                                        break;
                                    } else {
                                        System.out.println("Tree number " + index + " does not exist");
                                        // Exit the cut operation
                                        break;
                                    }
                                } else {
                                    System.out.println("That is not an integer.");
                                    // consume the invalid input
                                    keyboard.nextLine();
                                }
                            }
                            break;
                        case 'G':
                            // Code for grow
                            myForest.grow();
                            break;
                        case 'R':
                            // Code for reap
                            while (true) {
                                System.out.print("Height to reap from: ");
                                if (keyboard.hasNextDouble()) {
                                    double maxHeight = keyboard.nextDouble();
                                    // consume newline left-over
                                    keyboard.nextLine();
                                    myForest.reap(maxHeight);
                                    break;
                                } else {
                                    System.out.println("That is not an integer.");
                                    // consume the invalid input
                                    keyboard.nextLine();
                                }
                            }
                            break;
                        case 'S':
                            // Code for save
                            Forest.save(myForest.fileName + ".db", myForest);
                            break;

                        case 'L':
                            // Code for load
                            //creates a forest to hold myForest
                            Forest previousForest = myForest;
                            System.out.print("Enter the name of the forest to load: ");
                            // Get the filename from the user input and append ".db"
                            String filename = keyboard.nextLine() + ".db";
                            // Load the forest from the file
                            myForest = Forest.load(filename);
                            //if forest does not load it reverts back to the old forest
                            if (myForest == null) {
                                myForest = previousForest;
                            }
                            break;
                        case 'N':
                            // Print a message indicating the transition to the next forest
                            System.out.println("Moving to the next forest");
                            // Break out of the while loop to move to the next command line argument
                            break;
                        case 'X':
                            // Print a message indicating the exit of the program
                            System.out.println("Exiting the Forestry Simulation");
                            // Exit the program
                            return;
                        default:
                            // Print a message indicating an invalid menu option
                            System.out.print("Invalid menu option, try again");
                    } //end of switch statement

                    // If 'N' was entered, break out of the while loop
                    if (choice == 'N' || choice == 'X') {
                        break;
                    } //end of if statement
                } //end of while loop
            } //end of if statement
        } //end of for loop

// Print a farewell message
        System.out.println();
        System.out.println("Exiting the Forestry Simulation");
    } // End of main method

} // End of Forestry class
