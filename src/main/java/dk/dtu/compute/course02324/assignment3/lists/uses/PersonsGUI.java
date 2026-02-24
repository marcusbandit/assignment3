package dk.dtu.compute.course02324.assignment3.lists.uses;


import dk.dtu.compute.course02324.assignment3.lists.implementations.GenericComparator;
import dk.dtu.compute.course02324.assignment3.lists.types.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import jakarta.validation.constraints.NotNull;
import java.util.Comparator;

/**
 * A GUI element that is allows the user to interact and
 * change a list of persons.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class PersonsGUI extends GridPane {

    /**
     * The list of persons to be maintained in this GUI.
     */
    final private List<Person> persons;

    private GridPane personsPane;

    private TextArea exceptionArea;

    /**
     * Constructor which sets up the GUI attached a list of persons.
     *
     * @param persons the list of persons which is to be maintained in
     *                this GUI component; it must not be <code>null</code>
     */
    public PersonsGUI(@NotNull List<Person> persons) {
        this.persons = persons;

        this.setVgap(5.0);
        this.setHgap(5.0);

        // text field for user entering a name
        TextField nameField = new TextField();
        nameField.setPrefColumnCount(5);
        nameField.setText("name");

        // text field for user entering a weight
        TextField weightField = new TextField();
        weightField.setPrefColumnCount(5);
        weightField.setText("70.0");

        // text field for user entering an index
        TextField indexField = new TextField();
        indexField.setPrefColumnCount(5);
        indexField.setText("0");

        // text area for showing exceptions
        exceptionArea = new TextArea();
        exceptionArea.setEditable(false);
        exceptionArea.setPrefRowCount(3);
        exceptionArea.setPrefColumnCount(20);
        exceptionArea.setStyle("-fx-text-fill: red;");

        // button for adding a new person to the list
        Button addButton = new Button("Add");
        addButton.setOnAction(
                e -> {
                    try {
                        clearException();
                        double weight = Double.parseDouble(weightField.getText());
                        Person person = new Person(nameField.getText(), weight);
                        persons.add(person);
                        update();
                    } catch (Exception ex) {
                        showException(ex);
                    }
                });

        // button for adding a person at a specific index
        Button addAtButton = new Button("Add at index");
        addAtButton.setOnAction(
                e -> {
                    try {
                        clearException();
                        int index = Integer.parseInt(indexField.getText());
                        double weight = Double.parseDouble(weightField.getText());
                        Person person = new Person(nameField.getText(), weight);
                        persons.add(index, person);
                        update();
                    } catch (Exception ex) {
                        showException(ex);
                    }
                });

        // button for setting a person at a specific index
        Button setButton = new Button("Set at index");
        setButton.setOnAction(
                e -> {
                    try {
                        clearException();
                        int index = Integer.parseInt(indexField.getText());
                        double weight = Double.parseDouble(weightField.getText());
                        Person person = new Person(nameField.getText(), weight);
                        persons.set(index, person);
                        update();
                    } catch (Exception ex) {
                        showException(ex);
                    }
                });

        // button for removing a person at a specific index
        Button removeAtButton = new Button("Remove at index");
        removeAtButton.setOnAction(
                e -> {
                    try {
                        clearException();
                        int index = Integer.parseInt(indexField.getText());
                        persons.remove(index);
                        update();
                    } catch (Exception ex) {
                        showException(ex);
                    }
                });

        Comparator<Person> comparator = new GenericComparator<>();

        // button for sorting the list
        Button sortButton = new Button("Sort");
        sortButton.setOnAction(
                e -> {
                    try {
                        clearException();
                        persons.sort(comparator);
                        update();
                    } catch (Exception ex) {
                        showException(ex);
                    }
                });

        // button for clearing the list
        Button clearButton = new Button("Clear");
        clearButton.setOnAction(
                e -> {
                    try {
                        clearException();
                        persons.clear();
                        update();
                    } catch (Exception ex) {
                        showException(ex);
                    }
                });

        // combines the above elements into vertically arranged boxes
        // which are then added to the left column of the grid pane
        VBox actionBox = new VBox(
                new Label("Name:"), nameField,
                new Label("Weight:"), weightField,
                new Label("Index:"), indexField,
                addButton, addAtButton, setButton, removeAtButton,
                sortButton, clearButton);
        actionBox.setSpacing(5.0);
        this.add(actionBox, 0, 0);

        // create the elements of the right column of the GUI
        // (scrollable person list) ...
        Label labelPersonsList = new Label("Persons:");

        personsPane = new GridPane();
        personsPane.setPadding(new Insets(5));
        personsPane.setHgap(5);
        personsPane.setVgap(5);

        ScrollPane scrollPane = new ScrollPane(personsPane);
        scrollPane.setMinWidth(300);
        scrollPane.setMaxWidth(300);
        scrollPane.setMinHeight(300);
        scrollPane.setMaxHeight(300);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        Label labelExceptions = new Label("Exceptions:");

        // ... and adds these elements to the right-hand columns of
        // the grid pane
        VBox personsList = new VBox(labelPersonsList, scrollPane, labelExceptions, exceptionArea);
        personsList.setSpacing(5.0);
        this.add(personsList, 1, 0);

        // updates the values of the different components with the values from
        // the list
        update();
    }

    /**
     * Updates the values of the GUI elements with the current values
     * from the list.
     */
    private void update() {
        personsPane.getChildren().clear();
        // adds all persons to the list in the personsPane (with
        // a delete button in front of it)
        for (int i=0; i < persons.size(); i++) {
            Person person = persons.get(i);
            Label personLabel = new Label(i + ": " + person.toString());
            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(
                    e -> {
                        try {
                            clearException();
                            persons.remove(person);
                            update();
                        } catch (Exception ex) {
                            showException(ex);
                        }
                    }
            );
            HBox entry = new HBox(deleteButton, personLabel);
            entry.setSpacing(5.0);
            entry.setAlignment(Pos.BASELINE_LEFT);
            personsPane.add(entry, 0, i);
        }
    }

    private void showException(Exception ex) {
        exceptionArea.setText(ex.getClass().getSimpleName() + ": " + ex.getMessage());
    }

    private void clearException() {
        exceptionArea.setText("");
    }

}
