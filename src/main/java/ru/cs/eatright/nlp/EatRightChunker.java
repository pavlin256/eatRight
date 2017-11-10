package ru.cs.eatright.nlp;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.tokensregex.TokenSequenceMatcher;
import edu.stanford.nlp.ling.tokensregex.TokenSequencePattern;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.List;

public class EatRightChunker {

    private final TokenSequencePattern tokenPattern =
            TokenSequencePattern.compile("([{tag:/(JJ.*|NUM|NN.*|VB)/}] )*[{tag:/NN.*/}]");

    public List<Phrase> getNounPhrases(List<CoreLabel> annotatedText) {
        List<Phrase> phrases = new ArrayList<>();
        TokenSequenceMatcher tokenMatcher = tokenPattern.getMatcher(annotatedText);
        while (tokenMatcher.find()){
            List<CoreMap> matches = tokenMatcher.groupNodes();

            List<String> words = new ArrayList<>();
            List<String> postags = new ArrayList<>();
            for (CoreMap match : matches) {
                String token = match.get(CoreAnnotations.TextAnnotation.class);
                String pos = match.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                words.add(token);
                postags.add(pos);
            }

            phrases.add(new Phrase(words, postags));
        }
        return phrases;
    }
}