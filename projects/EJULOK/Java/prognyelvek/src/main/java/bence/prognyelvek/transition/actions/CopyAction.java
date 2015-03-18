package bence.prognyelvek.transition.actions;

import bence.prognyelvek.contexts.Context;

public class CopyAction<O> implements Action<O, O> {

    @Override
    public void perform(final Context<O, O> context) {
        context.writeOutput(context.popToken());
    }
}
