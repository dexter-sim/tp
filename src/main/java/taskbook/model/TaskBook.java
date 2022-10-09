package taskbook.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import taskbook.model.person.Name;
import taskbook.model.person.Person;
import taskbook.model.person.UniquePersonList;
import taskbook.model.task.Task;
import taskbook.model.task.TaskList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class TaskBook implements ReadOnlyTaskBook {

    private final UniquePersonList persons;
    private final TaskList tasks;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        tasks = new TaskList();
    }

    public TaskBook() {}

    /**
     * Creates an TaskBook using the Persons in the {@code toBeCopied}
     */
    public TaskBook(ReadOnlyTaskBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code TaskBook} with {@code newData}.
     */
    public void resetData(ReadOnlyTaskBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the task book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns the person with the same name as {@code name} if they exist in the task book.
     */
    public Person findPerson(Name name) {
        requireNonNull(name);
        return persons.find(name);
    }

    /**
     * Adds a person to the task book.
     * The person must not already exist in the task book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the task book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the task book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code TaskBook}.
     * {@code key} must exist in the task book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// task-level operations
    /**
     * Adds a task to the task book.
     * The task must not already exist in the task book.
     */
    public void addTask(Task t) {
        tasks.add(t);
    }

    /**
     * Deletes the given task.
     * Task must exist in the task book.
     */
    public void deleteTask(Task t) {
        tasks.remove(t);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return tasks.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskBook // instanceof handles nulls
                && persons.equals(((TaskBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}