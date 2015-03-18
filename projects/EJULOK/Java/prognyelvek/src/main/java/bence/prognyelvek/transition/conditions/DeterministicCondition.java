package bence.prognyelvek.transition.conditions;

import bence.prognyelvek.contexts.ContextView;

public class DeterministicCondition<T, O> extends AbstractCondition<T, O> {

    private final boolean result;

    public DeterministicCondition(final String name, final boolean result) {
        super(name);
        this.result = result;
    }

    @Override
    public boolean checkAgainst(final ContextView<T, O> context) {
        return result;
    }
}
