package bence.prognyelvek.transition.conditions;

import bence.prognyelvek.contexts.ContextView;

public class NoMoreToRead<T,O> extends AbstractCondition<T,O> {

    public NoMoreToRead(final String name) {
        super(name);
    }

    @Override
    public boolean checkAgainst(final ContextView<T, O> context) {
        return !context.hasNextToken();
    }
}
