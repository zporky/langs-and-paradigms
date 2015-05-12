package bence.prognyelvek.contexts;

import java.util.List;

public class SimpleContextFactory<T, O> implements ContextFactory<T, O> {

    @Override
    public Context<T, O> getInstance(final List<T> tokens) {
        return new SimpleContext<>(tokens);
    }
}
