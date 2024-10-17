package ca.bcit.comp2522.lab6.bam;

public abstract class Literature {

    public abstract String getTitle();

    @Override
    public String toString() {

        return getTitle();
    }
}
