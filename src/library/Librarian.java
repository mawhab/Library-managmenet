/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package library;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author knega
 */
public class Librarian extends User {
    private String email; // email of librarian
    private String address; // address of librarian
    private String city; // city of librarian
    private String contactNumber; // contact number of librarian
    private static StudentWrapper[] students; // array to hold student objects that have borrowed books
    private static int studentsi; //index variable to keep track of how many students there are 
    private static Book[] books; // books array to store all books added in library
    private static int booksi; // index variable to keep track of how many books there are
    private static IssuedBookWrapper[] issuedBooks; // issued books array to store all books added in library
    private static int issuedBooksi; // index variable to keep track of how many issued books there are
    static{
        setBooks(new Book[100]); // initializing books array
        setBooksi(0); // initializing books index
        setIssuedBooks(new IssuedBookWrapper[100]); // initializing issued books array
        setIssuedBooksi(0); // intiailizing issued books index
        setStudents(new StudentWrapper[100]); // intializing students array
        setStudentsi(0); // intiaizling students index to 0
    }
    
    public Librarian(String name, String pass){ // constructor
        super(name,pass); // calling constructor of class User
    }
    
    static public void addBook(String[] data, Boolean save){ 
        // given required data, create new element in array and set appropriate values
        books[booksi] = new Book();
        books[booksi].setCallNumber(data[0]);
        books[booksi].setName(data[1]);
        books[booksi].setAuthor(data[2]);
        books[booksi].setPublisher(data[3]);
        books[booksi].setQuantity(Integer.parseInt(data[4]));
        books[booksi].setIssued(0);
        books[booksi].setDateAdded(getDate());
        booksi++; // incrementing index by on 
        if(save)
            saveToFile();
    }
    
    static public void viewBooks(JTable t){ 
        // given a table, we create model for it and add row for each book that shows all info for each book
        DefaultTableModel d = (DefaultTableModel)t.getModel();
        String[] temp;
        for(int j=0;j<booksi;j++){
            temp = books[j].getData(false);
            temp[0] = Integer.toString(j+1);
            d.addRow((Object[])temp);
        }
    }
    
    static public int issueBook(String[] data, Boolean dateGiven){
        Boolean studentFound = false;
        String date;
        Student s = new Student(data[2], Integer.parseInt(data[1]), data[3]);
        for(int j=0;j<booksi;j++){
            if(books[j].getCallNumber().equals(data[0])){
                if(books[j].getQuantity()<=0){
                    return 0;   
                }else{
                   
                    for(int l=0;l<studentsi;l++){
                        if(Integer.parseInt(data[1])==students[l].s.getId()){
                            if(!students[l].s.getName().equals(data[2]) || !students[l].s.getContactNumber().equals(data[3])){
                                return 3;
                            }
                            
                            if(students[l].s.getBorrowedBooksi()>=3)
                                return 0;
                            else{
                                s = students[l].s;
                                studentFound = true;
                                break;
                            }
                        }
                    }
                    if(!studentFound){
                        students[studentsi] = new StudentWrapper(new Student(data[2], Integer.parseInt(data[1]), data[3]));
                        s = students[studentsi].s;
                        studentsi++;
                    }
                    if(dateGiven)
                        date = data[4];
                    else
                        date = getDate();
                    books[j].setQuantity(books[j].getQuantity()-1);
                    books[j].setIssued(books[j].getIssued()+1);
                    issuedBooks[issuedBooksi] = new IssuedBookWrapper(new IssuedBook(books[j]));
                    issuedBooks[issuedBooksi].b.setIssueDate(date);
                    issuedBooks[issuedBooksi].b.setBorrower(s);
                    s.getBorrowedBooks()[s.getBorrowedBooksi()] = issuedBooks[issuedBooksi];
                    s.getCallNos()[s.getBorrowedBooksi()] = issuedBooks[issuedBooksi].b.getCallNumber();
                    s.getDatesIssued()[s.getBorrowedBooksi()] = date;
                    s.setBorrowedBooksi(s.getBorrowedBooksi()+1);
                    issuedBooksi++;
                    if(!dateGiven)
                        saveSToFile();
                    System.gc();
                    return 1;
                }
            }
        }
        return 0;
    }
    
    static public void viewIssuedBooks(JTable t){
        // given a table, we create model for it and add row for each book that shows all info for each issued book
        DefaultTableModel d = (DefaultTableModel)t.getModel();
        String[] temp;
        for(int j=0;j<issuedBooksi;j++){
            temp = issuedBooks[j].b.getData();
            temp[0] = Integer.toString(j+1);
            d.addRow((Object[])temp);
        }
    }
    
    static public int returnBook(String callNo, String id){
        int res = 0;
        for(int j=0;j<issuedBooksi;j++){
            if(callNo.equals(issuedBooks[j].b.getCallNumber()) && Integer.parseInt(id)==issuedBooks[j].b.getBorrower().getId()){
                if(getDay(issuedBooks[j].b.getIssueDate()) == getDay(getDate())){
                    res = 1;
                }else{
                    res = 2;
                }
                deleteIssuedBook(j);
                deletefromStudent(callNo, Integer.parseInt(id));
                increment(callNo);
                studentCleanup();
                System.gc();
                saveToFile();
                saveSToFile();
                return res;
            }
        }
        return res;
    }
    static public void studentCleanup(){
        for(int j=0;j<studentsi;j++){
            if(students[j].s.getBorrowedBooksi()<=0){
                deleteStudent(j);
            }
        }
    }
    
    static private void deleteStudent(int index){
        while(index<studentsi-1){
            swapS(students[index], students[index+1]);
            index++;
        }
        students[index] = null;
        studentsi--;
    }
    
    static private void swapS(StudentWrapper a, StudentWrapper b){
        StudentWrapper temp = new StudentWrapper(a.s);
        a.s = b.s;
        b.s = temp.s;
    }
    
    static private void increment(String callNumber){
        for(int j=0;j<booksi;j++){
            if(books[j].getCallNumber().equals(callNumber)){
                books[j].setQuantity(books[j].getQuantity()+1);
                books[j].setIssued(books[j].getIssued()-1);
            }
        }
    }
    
    static private void deletefromStudent(String callNumber, int id){
        for(int j=0;j<studentsi;j++){
            if(students[j].s.getId()==id){
                for(int k=0;k<students[j].s.getBorrowedBooksi();k++){
                    if(students[j].s.getCallNos()[k].equals(callNumber)){
                        int index = k;
                        while(index<students[j].s.getBorrowedBooksi()-1){
                            String temp = students[j].s.getCallNos()[index];
                            students[j].s.getCallNos()[index] = students[j].s.getCallNos()[index+1];
                            students[j].s.getCallNos()[index+1] = temp;
                            index++;
                        }
                        index = k;
                        while(index<students[j].s.getBorrowedBooksi()-1){
                            String temp = students[j].s.getDatesIssued()[index];
                            students[j].s.getDatesIssued()[index] = students[j].s.getDatesIssued()[index+1];
                            students[j].s.getDatesIssued()[index+1] = temp;
                            index++;
                        }

                        index = k;
                        while(index<students[j].s.getBorrowedBooksi()-1){
                            swap(students[j].s.getBorrowedBooks()[index], students[j].s.getBorrowedBooks()[index+1]);
                            index++;
                        }
                        students[j].s.setBorrowedBooksi(students[j].s.getBorrowedBooksi()-1);
                    }
                }
            }
        }
    }
    
    
    static private void deleteIssuedBook(int index){
        while(index<issuedBooksi-1){
            swap(issuedBooks[index], issuedBooks[index+1]);
            index++;
        }
        issuedBooks[index] = null;
        issuedBooksi--;
    }
    static private void swap(IssuedBookWrapper a, IssuedBookWrapper b){
        IssuedBookWrapper temp = new IssuedBookWrapper(a.b);
        a.b = b.b;
        b.b = temp.b;
    }
    static private int getDay(String date){
        String temp = date.substring(0, 2);
        return Integer.parseInt(temp);
    }
    
    static public void readBooks(){
        // read from file function that will throw exception in case of no file found
        try{
            File f = new File("Books.txt");
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String x = s.nextLine();
                String tokens[] = x.split(",");
                addBook(tokens, false);
            }
        }catch(FileNotFoundException e){
            
        }
    }
    
    static private void saveToFile(){
        try{
            FileWriter f = new FileWriter("Books.txt");
            for(int j=0;j<booksi;j++){
                f.append(String.join(",", books[j].getData(true)));
                f.append("\n");
            }
            f.close();
        }catch(IOException e){
        }
    }
    
    
    static public void saveSToFile(){
        try{
            FileWriter f = new FileWriter("Students.txt");
            for(int j=0;j<studentsi;j++){
                f.append(String.join(",", students[j].s.getData()));
                f.append("\n");
            }
            f.close();
        }catch(IOException e){
        }
    }
    
    static public void readStudents(){
        try{
            File f = new File("Students.txt");
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String x = s.nextLine();
                String tokens[] = x.split(",");
                parseSData(tokens);
            }
        }catch(FileNotFoundException e){
            
        }
    }
    
    static private void parseSData(String[] data){
        String[] temp = new String[5];
        if(data.length>=5){
            temp[0] = data[3];
            temp[1] = data[1];
            temp[2] = data[0];
            temp[3] = data[2];
            temp[4] = data[4];
            issueBook(temp, true);
        }
        if(data.length>=7){
            temp[0] = data[5];
            temp[4] = data[6];
            issueBook(temp, true);
        }
        if(data.length>=9){
            temp[0] = data[7];
            temp[4] = data[8];
            issueBook(temp, true);
        }
    }

    public String[] getData(Boolean file){ // method that returns an array containing all info on librarian
        int m=0;
        if(!file)
            m=1;
        String[] data = new String[7];
        data[0+m] = this.getName();
        data[1+m] = this.getPassword();
        data[2+m] = this.getEmail();
        data[3+m] = this.getAddress();
        data[4+m] = this.getCity();
        data[5+m] = this.getContactNumber();
        return data;
    }
    
    static private String getDate(){ // method that returns date as a string
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-YY HH:mm:ss ");
        Date date = new Date();
        String d = dateFormat.format(date);
        return d;
    }

    //getters and setters
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
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
     * @return the booksi
     */
    public static int getBooksi() {
        return booksi;
    }


    /**
     * @return the issuedBooksi
     */
    public static int getIssuedBooksi() {
        return issuedBooksi;
    }

    /**
     * @param aBooks the books to set
     */
    public static void setBooks(Book[] aBooks) {
        books = aBooks;
    }

    /**
     * @param aBooksi the booksi to set
     */
    public static void setBooksi(int aBooksi) {
        booksi = aBooksi;
    }

    /**
     * @param aIssuedBooks the issuedBooks to set
     */
    public static void setIssuedBooks(IssuedBookWrapper[] aIssuedBooks) {
        issuedBooks = aIssuedBooks;
    }

    /**
     * @param aIssuedBooksi the issuedBooksi to set
     */
    public static void setIssuedBooksi(int aIssuedBooksi) {
        issuedBooksi = aIssuedBooksi;
    }

    /**
     * @return the students
     */
    public static StudentWrapper[] getStudents() {
        return students;
    }

    /**
     * @param aStudents the students to set
     */
    public static void setStudents(StudentWrapper[] aStudents) {
        students = aStudents;
    }

    /**
     * @return the studentsi
     */
    public static int getStudentsi() {
        return studentsi;
    }

    /**
     * @param aStudentsi the studentsi to set
     */
    public static void setStudentsi(int aStudentsi) {
        studentsi = aStudentsi;
    }

    
}
