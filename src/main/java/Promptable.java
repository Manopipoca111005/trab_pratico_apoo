/**
 * Interface for entities that provide a series of prompts.
 * These prompts are typically used for gathering user input.
 */
public interface Promptable {
    /**
     * Retrieves the list of prompt messages.
     *
     * @return An array of Strings containing the prompts.
     */
    String[] prompts();
}
