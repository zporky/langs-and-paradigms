package bence.prognyelvek.transition.conditions;

import bence.prognyelvek.contexts.ContextView;

public class OutputHasSuffix<T, O> extends AbstractCondition<T, O> {

    private final O token;

    public OutputHasSuffix(final String name, final O token) {
        super(name);
        this.token = token;
    }

    @Override
    public boolean checkAgainst(final ContextView<T, O> context) {
        if (context.getOutput().isEmpty()) {
            return false;
        }
        return context.getOutput().get(context.getOutput().size()-1).equals(token);
    }
}
