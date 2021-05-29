/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author knega
 */
public class Student extends User{
    private int id;
    private String contactNumber;
    private int borrowedBooksi;
    private IssuedBookWrapper[] borrowedBooks;
    private String[] callNos;
    private String[] datesIssued;
    
    public Student(String name, int id, String contactNumber){
        this.setName(name);
        this.id = id;
        this.contactNumber = contactNumber;
        borrowedBooksi = 0;
        borrowedBooks = new IssuedBookWrapper[3];
        callNos = new String[3];
        datesIssued = new String[3];
        
    }
    
    public void viewIssuedBooks(JTable t){
        DefaultTableModel d = (DefaultTableModel)t.getModel();
        String[] temp;
        for(int j=0;j<borrowedBooksi;j++){
            temp = borrowedBooks[j].b.getData(1);
            temp[0] = Integer.toString(j+1);
            d.addRow((Object[])temp);
        }
    }
    
    public String[] getData(){
        String[] data = new String[9];
        data[0] = this.getName();
        data[1] = Integer.toString(this.getId());
        data[2] = this.getContactNumber();
        switch(borrowedBooksi){
            case 1:
                data[3] = callNos[0];
                data[4] = datesIssued[0];
                break;
            case 2:
                data[3] = callNos[0];
                data[4] = datesIssued[0];
                data[5] = callNos[1];
                data[6] = datesIssued[1];
                break;
            case 3:
                data[3] = callNos[0];
                data[4] = datesIssued[0];
                data[5] = callNos[1];
                data[6] = datesIssued[1];
                data[7] = callNos[2];
                data[8] = datesIssued[2];
                break;
            default:
        }
        return data;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the contactNumber
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * @param contactNumber the contactNumber to set
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * @return the borrowedBooksi
     */
    public int getBorrowedBooksi() {
        return borrowedBooksi;
    }

    /**
     * @param borrowedBooksi the borrowedBooksi to set
     */
    public void setBorrowedBooksi(int borrowedBooksi) {
        this.borrowedBooksi = borrowedBooksi;
    }

    /**
     * @return the borrowedBooks
     */
    public IssuedBookWrapper[] getBorrowedBooks() {
        return borrowedBooks;
    }

    /**
     * @param borrowedBooks the borrowedBooks to set
     */
    public void setBorrowedBooks(IssuedBookWrapper[] borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    /**
     * @return the callNos
     */
    public String[] getCallNos() {
        return callNos;
    }

    /**
     * @param callNos the callNos to set
     */
    public void setCallNos(String[] callNos) {
        this.callNos = callNos;
    }

    /**
     * @return the datesIssued
     */
    public String[] getDatesIssued() {
        return datesIssued;
    }

    /**
     * @param datesIssued the datesIssued to set
     */
    public void setDatesIssued(String[] datesIssued) {
        this.datesIssued = datesIssued;
    }


}
