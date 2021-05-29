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
public class IssuedBook extends Book {
    private String issueDate;
    private String dueDate;
    private Student borrower;

    
    public IssuedBook(Book original){
        this.setName(original.getName());
        this.setCallNumber(original.getCallNumber());
        this.setAuthor(original.getAuthor());
        this.setPublisher(original.getPublisher());
        this.setDateAdded(original.getDateAdded());
    }
    
    
    /**
     * @override method from book class 
     * @return string array with all data from class 
     */
    public String[] getData(){ 
        // overriding method to account for different data needed for issuedbooks
        String[] data = new String[6];
        data[1] = this.getCallNumber();
        data[2] = Integer.toString(this.getBorrower().getId());
        data[3] = this.getBorrower().getName();
        data[4] = this.getBorrower().getContactNumber();
        data[5] = this.getIssueDate();
        return data;
    }
    
//    @Override
//    public String[] getData(Boolean file){
//        // overriding method to account for different data needed for issuedbooks
//        String[] data = new String[9];
//        data[0] = this.getCallNumber();
//        data[1] = Integer.toString(this.getBorrower().getId());
//        data[2] = this.getBorrower().getName();
//        data[3] = this.getBorrower().getContactNumber();
//        data[4] = this.getIssueDate();
//        data[5] = this.getName();
//        data[6] = this.getAuthor();
//        data[7] = this.getPublisher();
//        data[8] = this.getDateAdded();
//        return data;
//    }
    
    public String[] getData(int x){
        // overriding method to account for different data needed for issuedbooks
        String[] data = new String[6];
        data[1] = this.getCallNumber();
        data[2] = this.getName();
        data[3] = this.getAuthor();
        data[4] = this.getPublisher();
        data[5] = this.getIssueDate();
        return data;
    }
    /**
     * @return the issueDate
     */
    public String getIssueDate() {
        return issueDate;
    }

    /**
     * @param issueDate the issueDate to set
     */
    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    /**
     * @return the dueDate
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return the borrower
     */
    public Student getBorrower() {
        return borrower;
    }

    /**
     * @param borrower the borrower to set
     */
    public void setBorrower(Student borrower) {
        this.borrower = borrower;
    }
}
