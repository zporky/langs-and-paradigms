package bence.prognyelvek.transition;

import bence.prognyelvek.transition.actions.Action;
import bence.prognyelvek.transition.conditions.Condition;
import bence.prognyelvek.transition.states.State;

public class Transition<T, O> {

    private final Condition<T, O> condition;

    private final Action<T, O> action;

    private final State<T, O> destination;

    public Transition(final Condition<T, O> condition, final Action<T, O> action, final State<T, O> destination) {
        this.condition = condition;
        this.action = action;
        this.destination = destination;
    }

    public Condition<T, O> getCondition() {
        return condition;
    }

    public Action<T, O> getAction() {
        return action;
    }

    public State<T, O> getDestination() {
        return destination;
    }
}
