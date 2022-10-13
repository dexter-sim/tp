package taskbook.logic.commands.tasks;

import static java.util.Objects.requireNonNull;
import static taskbook.logic.parser.CliSyntax.PREFIX_ASSIGN_FROM;
import static taskbook.logic.parser.CliSyntax.PREFIX_ASSIGN_TO;
import static taskbook.logic.parser.CliSyntax.PREFIX_DATE;
import static taskbook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.time.LocalDate;

import taskbook.logic.commands.CommandResult;
import taskbook.logic.commands.exceptions.CommandException;
import taskbook.logic.parser.tasks.TaskCategoryParser;
import taskbook.model.Model;
import taskbook.model.person.Name;
import taskbook.model.task.Description;
import taskbook.model.task.Task;
import taskbook.model.task.enums.Assignment;

/**
 * Adds an event to the task book.
 */
public class TaskEventCommand extends TaskAddCommand {

    public static final String COMMAND_WORD = "event";

    public static final String MESSAGE_USAGE =
            TaskCategoryParser.CATEGORY_WORD + " " + COMMAND_WORD
                    + ": Adds an event to the task book.\n"
                    + "Parameters:\n"
                    + PREFIX_ASSIGN_FROM + "NAME " + PREFIX_DESCRIPTION + "DESCRIPTION " + PREFIX_DATE + "DATE\n"
                    + PREFIX_ASSIGN_TO + "NAME " + PREFIX_DESCRIPTION + "DESCRIPTION " + PREFIX_DATE + "DATE";
    public static final String MESSAGE_SUCCESS = "New event added: %1$s";

    private final LocalDate date;

    /**
     * Creates a TaskEventCommand to add a task with the specified
     * {@code Name name}, {@code Description description},
     * {@code Task.Assignment assignment} and {@code LocalDate date}
     *
     * @param name Name of the Person in the task book.
     * @param description The description for the new event.
     * @param assignment Represents event assigned to user or others.
     * @param date Represents the date for the new event.
     */
    public TaskEventCommand(Name name, Description description, Assignment assignment, LocalDate date) {
        super(name, description, assignment);
        requireNonNull(date);
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        checkPersonNameExist(model);

        Task newTask = createEvent(date);
        model.addTask(newTask);
        return new CommandResult(String.format(MESSAGE_SUCCESS, newTask));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TaskEventCommand)) {
            return false;
        }

        TaskEventCommand otherCommand = (TaskEventCommand) other;
        return super.equals(other) && date.equals(otherCommand.date);
    }
}