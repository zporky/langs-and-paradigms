package bence.prognyelvek.contexts;

import java.util.List;

/**
 * @param <T> Input token type.
 * @param <O> Output token type.
 */
public interface ContextView<T, O> {

    T peakToken();

    boolean hasNextToken();

    List<O> getOutput();
}
