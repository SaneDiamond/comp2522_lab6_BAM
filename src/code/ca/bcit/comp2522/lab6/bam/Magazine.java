package ca.bcit.comp2522.lab6.bam;

/**
 * Represents a Magazine with a title
 * Extends the literature abstract class.
 *
 * @author Andre Bernard Chang Dizon
 * @author Ben Nguyen
 * @author Marcus Vinicius Santos Lages
 *
 * @version 1.0
 */
public class Magazine extends Literature {

    private final String title;

    public Magazine(final String title) {

        this.title = title;
    }


    @Override
    public String getTitle() {

        return title;
    }
}