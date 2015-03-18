package bence.prognyelvek.transition.actions;

import bence.prognyelvek.contexts.Context;

public class NoAction<T, O> implements Action<T, O> {

    @Override
    public void perform(final Context<T, O> context) {
        // Do nothing
    }
}
