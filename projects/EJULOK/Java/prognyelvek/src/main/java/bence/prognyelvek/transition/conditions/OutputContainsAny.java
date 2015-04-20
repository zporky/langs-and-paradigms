package bence.prognyelvek.transition.conditions;

import bence.prognyelvek.contexts.ContextView;

public class OutputContainsAny<T, O> extends AbstractCondition<T, O> {

    private final O[] tokens;

    public OutputContainsAny(final String name, final O[] tokens) {
        super(name);
        this.tokens = tokens;
    }

    @Override
    public boolean checkAgainst(final ContextView<T, O> context) {
        for (O token: tokens) {
            if (context.getOutput().contains(token)) {
                return true;
            }
        }
        return false;
    }
}
