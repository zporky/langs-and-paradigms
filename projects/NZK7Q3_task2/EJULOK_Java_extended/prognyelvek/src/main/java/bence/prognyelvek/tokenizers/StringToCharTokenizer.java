package bence.prognyelvek.tokenizers;

import java.util.ArrayList;
import java.util.List;

public class StringToCharTokenizer implements Tokenizer<String, Character> {

    @Override
    public List<Character> tokenize(final String input) {
        final List<Character> characters = new ArrayList<>();

        for (final char character: input.toCharArray()) {
            characters.add(character);
        }

        return characters;
    }
}
