package bence.prognyelvek;

import bence.prognyelvek.contexts.ContextFactory;
import bence.prognyelvek.contexts.SimpleContextFactory;
import bence.prognyelvek.tokenizers.StringToCharTokenizer;
import bence.prognyelvek.tokenizers.Tokenizer;
import bence.prognyelvek.transition.Transition;
import bence.prognyelvek.transition.actions.*;
import bence.prognyelvek.transition.conditions.*;
import bence.prognyelvek.transition.states.SimpleState;

public class StateMachineBuilder {

    public static StateMachine<String, Character, Character> build() {
        // Tokenizer and ContextFactory
        final Tokenizer<String, Character> tokenizer = new StringToCharTokenizer();
        final ContextFactory<Character, Character> contextFactory = new SimpleContextFactory<>();

        // Conditions
        final Condition<Character, Character> isS = new ContainmentCondition("s", new Character[] {'+', '-'});
        final Condition<Character, Character> isP = new ContainmentCondition("p", new Character[] {'.'});
        final Condition<Character, Character> isZ = new ContainmentCondition("z", new Character[] {'0'});
        final Condition<Character, Character> isD = new ContainmentCondition("d", new Character[] {'1', '2', '3', '4', '5', '6', '7', '8', '9'});
        final Condition<Character, Character> isZorD = new DisjunctCondition<>("z||d", new Condition[] {isZ, isD});
        final Condition<Character, Character> alwaysTrue = new DeterministicCondition<>("alwaysTrue", true);
        final Condition<Character, Character> outputIsEmpty = new OutputSize<>("outputIsEmpty", 0);
        final Condition<Character, Character> noMoreToRead = new NoMoreToRead<>("noMoreToRead");
        final Condition<Character, Character> isThereAnyPoint = new OutputContains<>("isThereAnyPoint", '.');
        final Condition<Character, Character> outputIsWholeNumber = new Negation<>(isThereAnyPoint);
        final Condition<Character, Character> outputIsZero = new ConjuctCondition<>("outputIsZero", new Condition[] {
                new OutputSize("outputContainsOne", 1), new OutputContains("containsZero", '0')
        });
        final Condition<Character, Character> isCloseNeeded = new ConjuctCondition<>("isCloseNeeded", new Condition[] {
                noMoreToRead,  outputIsWholeNumber,  new Negation(outputIsZero)
        });

        // States
        final SimpleState<Character, Character> s = new SimpleState<>("s");
        final SimpleState<Character, Character> s2 = new SimpleState<>("s'");
        final SimpleState<Character, Character> i0 = new SimpleState<>("i0");
        final SimpleState<Character, Character> t1 = new SimpleState<>("t1", true);
        final SimpleState<Character, Character> t2 = new SimpleState<>("t2", true);
        final SimpleState<Character, Character> t3 = new SimpleState<>("t3", true);
        final SimpleState<Character, Character> t4 = new SimpleState<>("t4", true);
        final SimpleState<Character, Character> t5 = new SimpleState<>("t5", true);

        // Actions
        final Action<Character, Character> shallowAction = new ShallowAction<>();
        final Action<Character, Character> produceZeroAction = new ProduceAction<>('0');
        final Action<Character, Character> noAction = new NoAction<>();
        final Action<Character, Character> floatCloser = new ConditionalAction<>(isCloseNeeded, new CompositeAction<>(new Action[] {
                new ProduceAction('.'),  new ProduceAction('0')
        }));
        final Action<Character, Character> extendedCopyAction = new CompositeAction<>(new Action[] {new CopyAction<>(), floatCloser});
        final Action<Character, Character> pointProcessing = new CompositeAction<>(new Action[] {
                new ConditionalAction(outputIsEmpty, produceZeroAction),
                new CopyAction(),
                new ConditionalAction(noMoreToRead, produceZeroAction)
        });

        // Transitions
        s.addTransition(new Transition<>(isS, shallowAction, s2));
        s.addTransition(new Transition<>(alwaysTrue, noAction, s2));

        s2.addTransition(new Transition<>(isP, pointProcessing, i0));
        s2.addTransition(new Transition<>(isZ, extendedCopyAction, t2));
        s2.addTransition(new Transition<>(isD, extendedCopyAction, t4));

        i0.addTransition(new Transition<>(isZorD, extendedCopyAction, t1));

        t1.addTransition(new Transition<>(isZorD, extendedCopyAction, t1));

        t2.addTransition(new Transition<>(isP, pointProcessing, t3));

        t3.addTransition(new Transition<>(isZorD, extendedCopyAction, t3));

        t4.addTransition(new Transition<>(isZorD, extendedCopyAction, t4));
        t4.addTransition(new Transition<>(isP, pointProcessing, t5));

        t5.addTransition(new Transition<>(isZorD, extendedCopyAction, t5));

        return new StateMachine<>(tokenizer, contextFactory, s);
    }
}
