package bence.prognyelvek.transition.states;

import bence.prognyelvek.contexts.Context;

/**
 * @param <T> Input token type
 * @param <O> Output token type
 */
public interface State<T,O> {

    String getName();

    boolean isAcceptedState();

    State<T, O> step(Context<T,O> context);
}
