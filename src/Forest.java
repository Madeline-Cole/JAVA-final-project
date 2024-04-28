import java.io.*; // Importing the java input/output package
import java.util.ArrayList; // Importing the ArrayList class from java.util package

/**
 * Class representing a Forest.
 */
public class Forest implements Serializable { // Declaring a public class Forest which implements Serializable interface

    // Declaring an ArrayList of Tree objects
    ArrayList<Tree> trees;

    // Declaring a String variable to store the name of the forest
    String fileName;

    /**
     * Prints the details of the forest.
     */
    public void print() { // Declaring a public method print

        int index; // Declaring an integer variable index
        double totalHeight = 0.0; // Declaring a double variable totalHeight and initializing it to 0.0

        // Printing the name of the forest
        System.out.println("\nForest name: " + fileName);
        // Starting a for loop to iterate over the trees ArrayList
        for (index = 0; index < trees.size(); index++) {
            // Getting the tree at the current index
            Tree tree = trees.get(index);
            // Printing the details of the current tree
            System.out.printf("   %2d %-6s %4d %6.2f' %5.1f%%\n", index, tree.getSpecies(), tree.getYearOfPlanting(), tree.getHeight(), tree.getGrowthRate());
            // Adding the height of the current tree to totalHeight
            totalHeight += tree.getHeight();
        }
        // Printing the total number of trees and the average height of the trees
        System.out.printf("There are %d trees, with an average height of %.2f\n", trees.size(), totalHeight / trees.size());
    } //end of print method

    /**
     * Adds a random tree to the forest.
     */
    public void add() { // Declaring a public method add
        trees.add(Tree.makeRandomTree()); // Adding a random tree to the trees ArrayList
    } //end of add method

    /**
     * Cuts down a tree from the forest.
     *
     * @param treeNumber The index of the tree to cut down.
     */
    public void cut(int treeNumber) { // Declaring a public method cut with an integer parameter treeNumber
        // Checking if the treeNumber is valid
        if (treeNumber >= 0 && treeNumber < trees.size()) {
            // Removing the tree at the given index from the trees ArrayList
            trees.remove(treeNumber);
            // If the treeNumber is not valid
        } else {
            // Printing an error message
            System.out.println("Tree number " + treeNumber + " does not exist");
        } //end of if-else loop
    } //end of cut method

    /**
     * Grows all trees in the forest.
     */
    public void grow() { // Declaring a public method grow
        // Starting a for-each loop to iterate over the trees ArrayList
        for (Tree tree : trees) {
            // Calculating the new height of the current tree
            double newHeight = tree.getHeight() + tree.getGrowthRate();
            // Setting the new height of the current tree
            tree.setHeight(newHeight);
        } //end of for loop
    } //end of grow method

    /**
     * Saves the forest to a file.
     *
     * @param filename The name of the file to save to.
     * @param forest   The forest to save.
     * @return true if the save was successful, false otherwise.
     */
    public static boolean save(String filename, Forest forest) {
        // Check if the filename ends with ".db", if not, append it
        if (!filename.endsWith(".db")) {
            filename += ".db";
        } //end of if statement

        // Declare an ObjectOutputStream
        ObjectOutputStream toStream = null;

        try {
            // Initialize the ObjectOutputStream with a FileOutputStream
            toStream = new ObjectOutputStream(new FileOutputStream(filename));

            // Write the forest object to the file
            toStream.writeObject(forest);

            // If the write operation is successful, return true
            return true;
        } catch (IOException e) {
            // Print an error message if an IOException occurs
            System.out.println("ERROR saving " + e.getMessage());

            // Print the stack trace of the exception
            e.printStackTrace();

            // Return false as the save operation was unsuccessful
            return false;
        } finally {
            // Check if the ObjectOutputStream is not null
            if (toStream != null) {
                try {
                    // Close the ObjectOutputStream
                    toStream.close();
                } catch (IOException e) {
                    // Print an error message if an IOException occurs while closing the ObjectOutputStream
                    System.out.println("ERROR closing " + e.getMessage());

                    // Print the stack trace of the exception
                    e.printStackTrace();

                    // Return false as the save operation was unsuccessful
                    return false;
                } // end of catch statement
            } //end of if statement
        } // end of finally loop
    } //end of save method

    /**
     * Loads a forest from a file.
     *
     * @param filename The name of the file to load from.
     * @return The loaded forest, or null if the load was unsuccessful.
     */
    public static Forest load(String filename) {
        // Declare an ObjectInputStream
        ObjectInputStream fromStream = null;
        // Declare a Forest object
        Forest local;

        try {
            // Initialize the ObjectInputStream with a FileInputStream
            fromStream = new ObjectInputStream(new FileInputStream(filename));
            // Read the forest object from the file
            local = (Forest) fromStream.readObject();
        } catch (IOException e) {
            // Print an error message if an IOException occurs
            System.out.println("ERROR opening/reading " + e.getMessage());
            // Print a message indicating that the old forest is retained
            System.out.println("Old forest retained");
            // Return the existing forest (myForest) instead of null
            return null;

        } catch (ClassNotFoundException e) {
        // Print the message of the ClassNotFoundException
        System.out.println(e.getMessage());
        // Return null as the load operation was unsuccessful
        return null;
    } finally {
        // Check if the ObjectInputStream is not null
        if (fromStream != null) {
            try {
                // Close the ObjectInputStream
                fromStream.close();
            } catch (IOException e) {
                // Print an error message if an IOException occurs while closing the ObjectInputStream
                System.out.println("ERROR closing " + e.getMessage());
                // Return null as the load operation was unsuccessful
                return null;
            } //end of catch statement
          } //end of if statement
        } //end of finally loop
        // Successfully loaded forest
        return local;
    } //end of load method

    /**
     * Reads a forest from a CSV file.
     *
     * @param fileName The name of the CSV file (without the .csv extension).
     * @return The forest read from the file, or null if an error occurred.
     */
    public static Forest readForestFromCSV(String fileName) {
        // Create a new forest
        Forest forest = new Forest();
        // Initialize the list of trees
        forest.trees = new ArrayList<>();
        // Store the filename
        forest.fileName = fileName;

        // Append the .csv extension to the filename
        fileName += ".csv";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            // Read each line of the file
            while ((line = br.readLine()) != null) {
                // Split the line into values
                String[] values = line.split(",");
                // Get the tree species
                Tree.TreeSpecies species = Tree.TreeSpecies.valueOf(values[0].toUpperCase());
                // Get the year of planting
                int yearOfPlanting = Integer.parseInt(values[1]);
                // Get the height
                double height = Double.parseDouble(values[2]);
                // Get the growth rate
                double growthRate = Double.parseDouble(values[3]);
                // Create a new tree
                Tree tree = new Tree(species, yearOfPlanting, height, growthRate);
                // Add the tree to the forest
                forest.trees.add(tree);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error opening/reading " + fileName);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Return the forest
        return forest;
    } //end of readFromCSV method

    /**
     * Reaps trees taller than a given height and replaces them with new random trees.
     *
     * @param maxHeight The maximum height. Trees taller than this will be reaped.
     */
    public void reap(double maxHeight) {
        // Loop through each tree
        for (int i = 0; i < trees.size(); i++) {
            // Get the current tree
            Tree tree = trees.get(i);
            // Check if the tree is taller than the maximum height
            if (tree.getHeight() > maxHeight) {
                // Reap the tall tree
                String reapedTree = String.format("%-23s %-6s %4d %7.2f' %6.1f%%", "Reaping the tall tree", tree.getSpecies(), tree.getYearOfPlanting(), tree.getHeight(), tree.getGrowthRate());
                System.out.println(reapedTree);

                // Create a new random tree
                Tree newTree = Tree.makeRandomTree();
                // Replace the old tree with the new tree
                trees.set(i, newTree);
                //prints replaced tree
                String replacedTree = String.format("%-23s %-6s %4d %7.2f' %6.1f%%", "Replaced with new tree", newTree.getSpecies(), newTree.getYearOfPlanting(), newTree.getHeight(), newTree.getGrowthRate());
                System.out.println(replacedTree);
            } //end of if statement
        } //end of for loop
    } //end of reap method
} //end of forest class