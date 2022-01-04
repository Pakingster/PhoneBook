/*
Student: Alexey Vartanov
ID: 321641086
Maman 14: Phonebook
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.io.*;
import java.util.Iterator;
import java.util.Map;

/***
 * Controller class,
 * For Edit or Remove phonebook entry, choose it in table view
 */
public class Controller {

    @FXML
    private TextField nameField;

    @FXML
    private TextField numField;

    @FXML
    private Button addBtn;

    @FXML
    private Button removeBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button loadBtn;

    @FXML
    private Label logField;

    @FXML
    private TableView<PhoneBookEntry> tableGUI;

    @FXML
    private TableColumn<PhoneBookEntry, String> nameColumn;

    @FXML
    private TableColumn<PhoneBookEntry, String> numberColumn;

    public Phonebook book;

    @FXML
    void initialize(){
        book = new Phonebook();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        updateTable();
    }

    /**
     * Button used to add new and to modify exist number
     * if name exists in the phone book the new number value
     * will be associated with this name
     * @param event: button click event
     */
    @FXML
    void addContact(ActionEvent event) {
        try{
            if(!nameField.getText().isEmpty()) {
                verifyNumberInput(numField.getText());
                if (book.addName(nameField.getText(), numField.getText())) {
                    logField.setText("Contact was added successfully");
                    updateTable();
                } else
                    logField.setText("Can't add contact");
            }
            else {
                logField.setText("No contact to add");
            }
        } catch (Exception e) {
            logField.setText(e.getMessage());
        }
    }

    @FXML
    void saveToFile(ActionEvent event) throws Exception {
        try {
            String phonebookFileSuffix = ".pb";
            JFileChooser fileChooser = new JFileChooser(  );
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showSaveDialog( null );
            if(result == JFileChooser.APPROVE_OPTION) {
                FileOutputStream fileOut = new FileOutputStream(fileChooser.getSelectedFile()+phonebookFileSuffix);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(book);
                objectOut.close();
                logField.setText("Phonebook was successfully saved");
            }
        } catch (Exception ex) {
            throw new Exception("Can't save the phonebook to file");
        }
    }

    @FXML
    void loadFromFile(ActionEvent event) throws Exception {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                FileInputStream input = new FileInputStream(selectedFile);
                ObjectInputStream bookObject = new ObjectInputStream(input);
                //Object obj = bookObject.readObject();
                book = (Phonebook) bookObject.readObject();
                bookObject.close();
                updateTable();
                logField.setText("Phonebook was successfully loaded\nfrom " +  selectedFile.getAbsolutePath());
            }
        } catch (Exception ex) {
            throw new Exception("Can't load the Phone book from file");
        }
    }

    @FXML
    void removeContact(ActionEvent event) {
        if(book.removeName(nameField.getText()))
        {
            logField.setText("Contact was removed successfully");
            updateTable();
        }
        else
            logField.setText("No contact found to remove");
    }

    @FXML
    void selectEntry(MouseEvent event){
        if(tableGUI.getSelectionModel().getSelectedItem() != null){
            PhoneBookEntry entry = tableGUI.getSelectionModel().getSelectedItem();
            nameField.setText(entry.getName());
            numField.setText(entry.getNumber());
        }
    }

    private void verifyNumberInput(String number) throws Exception {
        if (!number.matches("[0-9]+")){
            throw new Exception("Invalid phone number\nUse only numbers");
        }
        int numberLength = number.length();
        if(numberLength > 10 || numberLength < 7 )
            throw new Exception("Invalid phone number length");
    }

    private void updateTable() {
        ObservableList<PhoneBookEntry> data = FXCollections.observableArrayList();
        for (Iterator<Map.Entry<String, String>> it = book.iterator(); it.hasNext(); ) {
            Map.Entry<String, String> entry = it.next();
            data.add(new PhoneBookEntry(entry.getKey(), entry.getValue()));
        }
        tableGUI.setItems(data);
    }
}
