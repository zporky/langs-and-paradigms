package bence.prognyelvek.transition.actions;

import bence.prognyelvek.contexts.Context;
import bence.prognyelvek.transition.conditions.Condition;

public class ConditionalAction<T, O> implements Action<T, O> {

    private final Condition<T, O> condition;

    private final Action<T, O> action;

    public ConditionalAction(final Condition<T, O> condition, final Action<T, O> action) {
        this.condition = condition;
        this.action = action;
    }

    @Override
    public void perform(final Context<T, O> context) {
        if (condition.checkAgainst(context)) {
            action.perform(context);
        }
    }
}
