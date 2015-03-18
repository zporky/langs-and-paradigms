package bence.prognyelvek.transition.conditions;

import bence.prognyelvek.contexts.ContextView;

/**
 * @param <T> Input token type.
 * @param <O> Output token type.
 */
public interface Condition<T, O> {

    String getName();

    boolean checkAgainst(ContextView<T, O> context);
}
