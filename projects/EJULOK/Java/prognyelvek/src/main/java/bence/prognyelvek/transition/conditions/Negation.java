package bence.prognyelvek.transition.conditions;

import bence.prognyelvek.contexts.ContextView;

public class Negation<T, O> extends AbstractCondition<T, O> {

    private final Condition<T, O> condition;

    public Negation(final Condition<T, O> condition) {
        super("negation of " + condition.getName());
        this.condition = condition;
    }

    @Override
    public boolean checkAgainst(final ContextView<T, O> context) {
        return !condition.checkAgainst(context);
    }
}
