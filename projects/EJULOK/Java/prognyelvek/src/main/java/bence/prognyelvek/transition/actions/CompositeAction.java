package bence.prognyelvek.transition.actions;

import bence.prognyelvek.contexts.Context;

import java.util.ArrayList;
import java.util.List;

public class CompositeAction<T, O> implements Action<T, O> {

    private final List<Action<T, O>> actions = new ArrayList<>();

    public CompositeAction(final Action<T, O>[] actions) {
        for (final Action action : actions) {
            this.actions.add(action);
        }
    }

    @Override
    public void perform(final Context<T, O> context) {
        for (final Action action : actions) {
            action.perform(context);
        }
    }
}
