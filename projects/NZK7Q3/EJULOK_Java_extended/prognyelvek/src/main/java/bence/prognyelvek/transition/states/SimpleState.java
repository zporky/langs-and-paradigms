package bence.prognyelvek.transition.states;

import bence.prognyelvek.transition.Transition;
import bence.prognyelvek.contexts.Context;

import java.util.ArrayList;
import java.util.List;

public class SimpleState<T, O> implements State<T, O> {

    private final String name;

    private final boolean acceptedState;

    private final List<Transition<T, O>> transitions = new ArrayList<>();

    public SimpleState(final String name) {
        this(name, false);
    }

    public SimpleState(final String name, final boolean acceptedState) {
        this.name = name;
        this.acceptedState = acceptedState;
    }

    public void addTransition(final Transition<T, O> transition) {
        transitions.add(transition);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isAcceptedState() {
        return acceptedState;
    }

    @Override
    public State<T, O> step(final Context<T,O> context) {
        for (final Transition<T, O> transition : transitions) {
            if (transition.getCondition().checkAgainst(context)) {
                transition.getAction().perform(context);
                return transition.getDestination();
            }
        }

        throw new IllegalStateException("Unexpected token arrived to state " + name + "!");
    }
}
