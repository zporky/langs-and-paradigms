package bence.prognyelvek;

import bence.prognyelvek.contexts.Context;
import bence.prognyelvek.contexts.ContextFactory;
import bence.prognyelvek.transition.states.State;
import bence.prognyelvek.tokenizers.Tokenizer;

import java.util.List;

/**
 * @param <I> Input
 * @param <T> Input token
 * @param <O> Output
 */
public class StateMachine<I, T, O> {

    private final Tokenizer<I, T> tokenizer;

    private final ContextFactory<T, O> contextFactory;

    private final State<T, O> startingState;

    public StateMachine(final Tokenizer<I, T> tokenizer, final ContextFactory<T, O> contextFactory, final State<T, O> startingState) {
        this.tokenizer = tokenizer;
        this.contextFactory = contextFactory;
        this.startingState = startingState;
    }

    public List<O> process(final I input) {
        final List<T> tokens = tokenizer.tokenize(input);
        final Context<T, O> context = contextFactory.getInstance(tokens);
        State<T, O> actualState = startingState;

        while (context.hasNextToken()) {
            actualState = actualState.step(context);
        }

        if (!actualState.isAcceptedState()) {
            throw new IllegalStateException("State machine finished in non-accepted state!");
        }

        return context.getOutput();
    }
}
