/*
Student: Alexey Vartanov
ID: 321641086
Maman 14: Phonebook
 */
import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Phonebook implemented with TreeMap class
 */
public class Phonebook implements Serializable {

    public TreeMap<String,String > book;

    public Phonebook(){
        book = new TreeMap<>();
    }

    public String getNumber(String name){
        return book.get(name);
    }

    public boolean addName(String name, String number){
        if(!name.isEmpty())
        {
            book.put(name, number);
        }
        return book.containsKey(name);
    }

    public boolean removeName(String name){
        return book.remove(name) != null;
    }

    public Iterator<Map.Entry<String, String>> iterator(){
        return book.entrySet().iterator();
    }
}
