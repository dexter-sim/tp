package taskbook.logic.commands.categoryless;

import static taskbook.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static taskbook.model.Model.PREDICATE_SHOW_ALL_TASKS;

import taskbook.logic.commands.Command;
import taskbook.logic.commands.CommandResult;
import taskbook.logic.commands.exceptions.CommandException;
import taskbook.model.Model;

/**
 * Reverts to the previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Reverted to the previous state.";
    public static final String MESSAGE_INVALID_ACTION = "There are no actions left to undo.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.canUndoTaskBook()) {
            throw new CommandException(MESSAGE_INVALID_ACTION);
        }

        model.undoTaskBook();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);

        return new CommandResult(MESSAGE_SUCCESS);
    }
}