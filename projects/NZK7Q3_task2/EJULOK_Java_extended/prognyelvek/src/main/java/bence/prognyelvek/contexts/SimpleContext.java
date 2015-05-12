package bence.prognyelvek.contexts;

import java.util.ArrayList;
import java.util.List;

public class SimpleContext<T, O> implements Context<T, O> {

    private final List<T> tokens = new ArrayList<T>();

    private final List<O> output = new ArrayList<O>();

    private int pointer = 0;

    public SimpleContext(final List<T> tokens) {
        this.tokens.addAll(tokens);
    }

    @Override
    public T popToken() {
        final T result = peakToken();
        pointer++;
        return result;
    }

    @Override
    public T peakToken() {
        return tokens.get(pointer);
    }

    @Override
    public boolean hasNextToken() {
        return tokens.size() > pointer;
    }

    @Override
    public void writeOutput(final O token) {
        output.add(token);
    }

    @Override
    public List<O> getOutput() {
        return new ArrayList<O>(output);
    }
}
