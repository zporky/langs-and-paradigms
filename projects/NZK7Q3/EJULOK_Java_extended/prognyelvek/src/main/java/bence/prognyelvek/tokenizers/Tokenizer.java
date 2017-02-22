package bence.prognyelvek.tokenizers;

import java.util.List;

/**
 * @param <I> Input type.
 * @param <T> Input token type.
 */
public interface Tokenizer<I, T> {

    List<T> tokenize(I input);
}
