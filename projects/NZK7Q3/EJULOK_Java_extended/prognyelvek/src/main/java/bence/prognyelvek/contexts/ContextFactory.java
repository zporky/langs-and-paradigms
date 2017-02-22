package bence.prognyelvek.contexts;

import java.util.List;

/**
 * @param <T> Input token type.
 * @param <O> Output token type.
 */
public interface ContextFactory<T, O> {

    Context<T, O> getInstance(List<T> tokens);
}
