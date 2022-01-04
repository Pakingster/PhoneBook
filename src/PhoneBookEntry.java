/*
Student: Alexey Vartanov
ID: 321641086
Maman 14: Phonebook
 */
import javafx.beans.property.SimpleStringProperty;
import java.io.Serializable;

/**
 * Class for use with FX TableView object
 */
public class PhoneBookEntry implements Serializable
{
    private final SimpleStringProperty name;
    private final SimpleStringProperty number;

    public PhoneBookEntry(String name, String number){
        this.name = new SimpleStringProperty(name);
        this.number = new SimpleStringProperty(number);
    }

    public String getName() {
        return name.get();
    }

    public String getNumber() {
        return number.get();
    }
}
