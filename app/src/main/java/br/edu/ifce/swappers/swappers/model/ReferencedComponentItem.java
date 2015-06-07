package br.edu.ifce.swappers.swappers.model;

/**
 * Created by francisco on 05/06/15.
 */
public class ReferencedComponentItem {
    private String libraryName;
    private String libraryLink;

    public ReferencedComponentItem() {

    }

    public ReferencedComponentItem(String libraryName, String libraryLink) {
        this.libraryName = libraryName;
        this.libraryLink = libraryLink;
    }

    public String getLibraryLink() {
        return libraryLink;
    }

    public void setLibraryLink(String libraryLink) {
        this.libraryLink = libraryLink;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }
}
