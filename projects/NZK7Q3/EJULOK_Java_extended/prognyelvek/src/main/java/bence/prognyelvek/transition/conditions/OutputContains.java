package bence.prognyelvek.transition.conditions;

import bence.prognyelvek.contexts.ContextView;

public class OutputContains<T, O> extends AbstractCondition<T, O> {

    private final O token;

    public OutputContains(final String name, final O token) {
        super(name);
        this.token = token;
    }

    @Override
    public boolean checkAgainst(final ContextView<T, O> context) {
        return context.getOutput().contains(token);
    }
}
