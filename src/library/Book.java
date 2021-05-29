/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

/**
 *
 * @author knega
 */
public class Book {
    private String callNumber;
    private String name;
    private String author;
    private String publisher;
    private int quantity;
    private int issued;
    private String dateAdded;
    
    

    public String[] getData(Boolean file){
        int m = 0;
        if(!file)
            m=1;
        String[] data = new String[8];
        data[0+m] = this.getCallNumber();
        data[1+m] = this.getName();
        data[2+m] = this.getAuthor();
        data[3+m] = this.getPublisher();
        if(file){
            data[4] = Integer.toString(this.getQuantity()+this.getIssued());
            data[5] = this.getDateAdded();
        }else{
            data[4+m] = Integer.toString(this.getQuantity());
            data[5+m] = Integer.toString(this.getIssued());
            data[6+m] = this.getDateAdded();
        }
        return data;
    }
    
    /**
     * @return the callNumber
     */
    public String getCallNumber() {
        return callNumber;
    }

    /**
     * @param callNumber the callNumber to set
     */
    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * @param publisher the publisher to set
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the issued
     */
    public int getIssued() {
        return issued;
    }

    /**
     * @param issued the issued to set
     */
    public void setIssued(int issued) {
        this.issued = issued;
    }

    /**
     * @return the dateAdded
     */
    public String getDateAdded() {
        return dateAdded;
    }

    /**
     * @param dateAdded the dateAdded to set
     */
    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
    
}
