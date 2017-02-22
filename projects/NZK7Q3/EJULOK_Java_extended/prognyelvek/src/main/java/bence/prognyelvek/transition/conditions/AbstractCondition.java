package bence.prognyelvek.transition.conditions;

abstract class AbstractCondition<T, O> implements Condition<T, O> {

    private final String name;

    protected AbstractCondition(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
