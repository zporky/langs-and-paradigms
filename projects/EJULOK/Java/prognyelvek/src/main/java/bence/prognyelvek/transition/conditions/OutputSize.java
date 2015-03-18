package bence.prognyelvek.transition.conditions;

import bence.prognyelvek.contexts.ContextView;

public class OutputSize<T, O> extends AbstractCondition<T, O> {

    private final int expectedSize;

    public OutputSize(final String name, final int expectedSize) {
        super(name);
        this.expectedSize = expectedSize;
    }

    @Override
    public boolean checkAgainst(final ContextView<T, O> context) {
        return context.getOutput().size() == expectedSize;
    }
}
