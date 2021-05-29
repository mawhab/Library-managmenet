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
import java.util.Scanner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author knega
 */
public class Admin extends User {
    private static LibrarianWrapper[] librarians; // librarians array
    private static int i;
    static{
        librarians = new LibrarianWrapper[100]; // initializing librarians
        i=0;
    }
    
    public Admin(String name, String password){
        super(name,password); // constructior
    }
    
    static public void addLibrarian(String libData[], Boolean save){
        // given required data, we create new librarian object and set data given
        librarians[getI()] = new LibrarianWrapper(new Librarian(libData[0], libData[1]));
        getLibrarians()[getI()].lib.setEmail(libData[2]);
        getLibrarians()[getI()].lib.setAddress(libData[3]);
        getLibrarians()[getI()].lib.setCity(libData[4]);
        getLibrarians()[getI()].lib.setContactNumber(libData[5]);
        i++;
        if(save)
            saveToFile();
    }
    
    static public void viewLibrarian(JTable t){
        DefaultTableModel d = (DefaultTableModel)t.getModel();
        String[] temp;
        for(int j=0;j<getI();j++){
            temp = getLibrarians()[j].lib.getData(false);
            temp[0] = Integer.toString(j+1);
            d.addRow((Object[])temp);
        }
        
    }
    
    static public void deleteLibrarian(int index){
        // we delete an object by swapping it to the end of array then assigning it to null and calling garbage collector
        while(index<getI()-1){
            swap(getLibrarians()[index], getLibrarians()[index+1]);
            index++;
        }
        librarians[index] = null;
        i--;
        System.gc();
        saveToFile();
    }
    
    static private void swap(LibrarianWrapper a, LibrarianWrapper b){
        // swap function that utilizes wrapping class to easily swap objects in array
        LibrarianWrapper temp = new LibrarianWrapper(a.lib);
        a.lib = b.lib;
        b.lib = temp.lib;
        
    }
    static public void readLibrarians(){
        // read from file function that will throw exception in case of no file found
        try{
            File f = new File("Librarians.txt");
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String x = s.nextLine();
                String tokens[] = x.split(",");
                addLibrarian(tokens, false);
            }
        }catch(FileNotFoundException e){
            
        }
    }
    
    static private void saveToFile(){
        try{
            FileWriter f = new FileWriter("Librarians.txt");
            for(int j=0;j<i;j++){
                f.append(String.join(",", librarians[j].lib.getData(true)));
                f.append("\n");
            }
            f.close();
        }catch(IOException e){
        }
    }

    /**
     * @return the librarians
     */
    public static LibrarianWrapper[] getLibrarians() {
        return librarians;
    }

    /**
     * @return the i
     */
    public static int getI() {
        return i;
    }
}
