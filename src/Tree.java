import java.io.Serializable; //importing java serializable package
import java.util.Random; //importing java random package


/**
 * Class representing a Tree.
 */
public class Tree implements Serializable {

    private static final int MIN_YEAR = 2000;
    private static final int MIN_HEIGHT = 10;

    private TreeSpecies species; // The species of the tree
    private int yearOfPlanting; // The year the tree was planted
    private double height; // The current height of the tree
    private double growthRate; // The growth rate of the tree

    /**
     * Enumeration for TreeSpecies.
     */
    public enum TreeSpecies {
        BIRCH, MAPLE, FIR,
    }

    /**
     * Constructor for the Tree class.
     *
     * @param species The species of the tree.
     * @param yearOfPlanting The year the tree was planted.
     * @param height The current height of the tree.
     * @param growthRate The growth rate of the tree.
     */
    public Tree (TreeSpecies species, int yearOfPlanting, double height, double growthRate){
        this.species = species;
        this.yearOfPlanting = yearOfPlanting;
        this.height = height;
        this.growthRate = growthRate;
    } //end of constructor

    /**
     * Returns a string representation of the Tree object.
     *
     * @return A string representation of the Tree object.
     */
    public String toString() {
        return this.species+ " " + this.yearOfPlanting + " " + this.height + "' " + this.growthRate + "%";
    } //end of toString

    /**
     * Returns the year the tree was planted.
     *
     * @return The year the tree was planted.
     */
    public int getYearOfPlanting() {

        return(yearOfPlanting);
    }//end of getYearofPlanting

    /**
     * Returns the species of the tree.
     *
     * @return The species of the tree.
     */
    public TreeSpecies getSpecies() {

        return(species);
    } //end of getSpecies

    /**
     * Returns the current height of the tree.
     *
     * @return The current height of the tree.
     */
    public double getHeight(){
        return(height);
    } //end of getHeight

    /**
     * Returns the growth rate of the tree.
     *
     * @return The growth rate of the tree.
     */
    public double getGrowthRate() {

        return(growthRate);
    } //end of getGrowthRate

    /**
     * Sets the height of the tree.
     *
     * @param height The new height of the tree.
     */
    public void setHeight(double height){

        this.height = Math.round(height);
    } //end of setHeight

    /**
     * Creates a random Tree object.
     *
     * @return A random Tree object.
     */
    public static Tree makeRandomTree() {
        Random random = new Random();
        TreeSpecies[] species = TreeSpecies.values();
        TreeSpecies randomSpecies = species[random.nextInt(species.length)];
        // Random year between 2000 and 2024
        int yearOfPlanting = 2000 + random.nextInt(25);
        // Random height between 10 and 20, rounded to nearest whole number
        double height = Math.round(10 + random.nextDouble() * 10);
        // Random growth rate between 10% and 20%, rounded to one decimal place
        double growthRate = Math.round((10 + random.nextDouble() * 10) * 10) / 10.0;
        return new Tree(randomSpecies, yearOfPlanting, height, growthRate);
    } //end of makeRandomTree

} //end of tree class

