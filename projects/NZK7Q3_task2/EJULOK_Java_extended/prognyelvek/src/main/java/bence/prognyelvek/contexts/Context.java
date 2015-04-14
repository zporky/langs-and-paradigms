package bence.prognyelvek.contexts;

/**
 * @param <T> Input token type.
 * @param <O> Output token type.
 */
public interface Context<T, O> extends ContextView<T, O> {

    T popToken();

    void writeOutput(O output);
}
