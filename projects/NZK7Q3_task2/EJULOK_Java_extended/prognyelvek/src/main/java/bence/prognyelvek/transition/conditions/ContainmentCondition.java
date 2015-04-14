package bence.prognyelvek.transition.conditions;

import bence.prognyelvek.contexts.ContextView;

import java.util.HashSet;
import java.util.Set;

public class ContainmentCondition<T, O> extends AbstractCondition<T, O> {

    private final Set<T> allowedTokens;

    public ContainmentCondition(final String name, final T[] allowedTokens) {
        super(name);
        this.allowedTokens = new HashSet<T>();

        for (final T token : allowedTokens) {
            this.allowedTokens.add(token);
        }
    }

    @Override
    public boolean checkAgainst(final ContextView<T, O> context) {
        return allowedTokens.contains(context.peakToken());
    }
}
