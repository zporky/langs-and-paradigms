package bence.prognyelvek.transition.conditions;

import bence.prognyelvek.contexts.ContextView;

import java.util.ArrayList;
import java.util.List;

public class DisjunctCondition<T, O> extends AbstractCondition<T, O>  {

    private final List<Condition<T, O>> conditions = new ArrayList<>();

    public DisjunctCondition(final String name, final Condition<T, O>[] conditions) {
        super(name);

        for (final Condition<T, O> condition : conditions) {
            this.conditions.add(condition);
        }
    }

    @Override
    public boolean checkAgainst(final ContextView<T, O> context) {
        boolean result = false;

        for (final Condition<T, O> condition : conditions) {
            result = result || condition.checkAgainst(context);
        }

        return result;
    }
}
