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
        final Condition<Character, Character> isE = new ContainmentCondition("e", new Character[] {'e', 'E'});
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
        final SimpleState<Character, Character> t135 = new SimpleState<>("t135", true);
        final SimpleState<Character, Character> t2 = new SimpleState<>("t2", true);
        final SimpleState<Character, Character> t4 = new SimpleState<>("t4", true);
        final SimpleState<Character, Character> i1 = new SimpleState<>("i1");
        final SimpleState<Character, Character> t6 = new SimpleState<>("t6", true);
        final SimpleState<Character, Character> i2 = new SimpleState<>("i2");

        // Actions
        final Action<Character, Character> shallowAction = new ShallowAction<>();
        final Action<Character, Character> produceZeroAction = new ProduceAction<>('0');
        final Action<Character, Character> noAction = new NoAction<>();
        final Action<Character, Character> floatCloser = new ConditionalAction<>(isCloseNeeded, new CompositeAction<>(new Action[] {
                new ProduceAction('.'),  new ProduceAction('0')
        }));
        final Action<Character, Character> extendedCopyAction = new CompositeAction<>(new Action[] {new CopyAction<>(), floatCloser});
        
        
                    //extension
        final Condition<Character, Character> noDigitsInOutput = new Negation<>(new OutputContainsAny<Character, Character>("isThereAnyDigit", new Character[]{'1', '2', '3', '4', '5', '6', '7', '8', '9','0'}));
        final Action<Character, Character> pointProcessing = new CompositeAction<>(new Action[] {
                new ConditionalAction(noDigitsInOutput, produceZeroAction),
                new CopyAction(),
                new ConditionalAction(noMoreToRead, produceZeroAction)
        });
        
        final Action<Character, Character> copyAction = new CopyAction<>();
        
        final Condition<Character, Character> isMinus = new ContainmentCondition("s-", new Character[] {'-'});
        final Condition<Character, Character> isPlus = new ContainmentCondition("s+", new Character[] {'+'});
        final Action<Character, Character> keepMinusOnly = new CompositeAction<>(new Action[] {
            new ConditionalAction(isMinus, copyAction),
            new ConditionalAction(new Negation<>(noMoreToRead), new ConditionalAction<>(isPlus, shallowAction))  //necessary noMoreToRead to avoid possible array index out of bounds in SimpleContext
        });
        
        final Condition<Character, Character> lastIsPoint = new OutputHasSuffix("lastIsPoint", '.');
        final Action<Character, Character> producePointZero = new CompositeAction<>(new Action[] {
                new ProduceAction('.'),  new ProduceAction('0')
        });
        final Action<Character, Character> eProcessing = new CompositeAction<>(
            new Action[] {
                new ConditionalAction(new Negation<>(isThereAnyPoint), producePointZero),
                new ConditionalAction(lastIsPoint, produceZeroAction),
                new ProduceAction('e'),
                shallowAction
            }
        );
        
        final Action<Character, Character> addPlusSignAction = new CompositeAction<>(new Action[] {
                new ProduceAction('+'),
                new CopyAction()
        });
                    //

        // Transitions
        s.addTransition(new Transition<>(isS, keepMinusOnly, s2));
        s.addTransition(new Transition<>(alwaysTrue, noAction, s2));

        s2.addTransition(new Transition<>(isP, pointProcessing, i0));
        s2.addTransition(new Transition<>(isZ, extendedCopyAction, t2));
        s2.addTransition(new Transition<>(isD, extendedCopyAction, t4));

        i0.addTransition(new Transition<>(isD, copyAction, t135));

        t2.addTransition(new Transition<>(isP, pointProcessing, t135));
        t2.addTransition(new Transition<>(isE, eProcessing, i1));

        t4.addTransition(new Transition<>(isD, extendedCopyAction, t4));
        t4.addTransition(new Transition<>(isP, pointProcessing, t135));
        t4.addTransition(new Transition<>(isE, eProcessing, i1));
        
        t135.addTransition(new Transition<>(isD, extendedCopyAction, t135));
        t135.addTransition(new Transition<>(isE, eProcessing, i1));
        
        i1.addTransition(new Transition<>(isD, addPlusSignAction, t6));
        i1.addTransition(new Transition<>(isS, copyAction, i2));
        
        i2.addTransition(new Transition<>(isD, copyAction, t6));
        
        t6.addTransition(new Transition<>(isZorD, copyAction, t6));

        return new StateMachine<>(tokenizer, contextFactory, s);
    }
}
