package bence.prognyelvek.transition.actions;

import bence.prognyelvek.contexts.Context;

public class ShallowAction<T, O> implements Action<T, O> {

    @Override
    public void perform(final Context<T, O> context) {
        context.popToken();
    }
}
