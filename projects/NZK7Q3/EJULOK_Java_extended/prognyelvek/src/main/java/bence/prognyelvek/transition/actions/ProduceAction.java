package bence.prognyelvek.transition.actions;

import bence.prognyelvek.contexts.Context;

public class ProduceAction<T, O> implements Action<T, O> {

    private final O product;

    public ProduceAction(final O product) {
        this.product = product;
    }

    @Override
    public void perform(final Context<T, O> context) {
        context.writeOutput(product);
    }
}
