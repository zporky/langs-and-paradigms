package bence.prognyelvek.transition.actions;

import bence.prognyelvek.contexts.Context;

/**
 * @param <T> Input token type.
 * @param <O> Output token type.
 */
public interface Action<T,O> {

    void perform(Context<T, O> context);
}
