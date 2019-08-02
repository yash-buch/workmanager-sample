package com.binc.workermanager.beans;

public class Meaning {
    Definition[] noun;
    Definition[] pronoun;
    Definition[] verb;
    Definition[] adjective;
    Definition[] adverb;
    Definition[] conjunction;
    Definition[] preposition;
    Definition[] interjection;

    public Definition[] getNoun() {
        return noun;
    }

    public void setNoun(Definition[] noun) {
        this.noun = noun;
    }

    public Definition[] getPronoun() {
        return pronoun;
    }

    public void setPronoun(Definition[] pronoun) {
        this.pronoun = pronoun;
    }

    public Definition[] getVerb() {
        return verb;
    }

    public void setVerb(Definition[] verb) {
        this.verb = verb;
    }

    public Definition[] getAdjective() {
        return adjective;
    }

    public void setAdjective(Definition[] adjective) {
        this.adjective = adjective;
    }

    public Definition[] getAdverb() {
        return adverb;
    }

    public void setAdverb(Definition[] adverb) {
        this.adverb = adverb;
    }

    public Definition[] getConjunction() {
        return conjunction;
    }

    public void setConjunction(Definition[] conjunction) {
        this.conjunction = conjunction;
    }

    public Definition[] getPreposition() {
        return preposition;
    }

    public void setPreposition(Definition[] preposition) {
        this.preposition = preposition;
    }

    public Definition[] getInterjection() {
        return interjection;
    }

    public void setInterjection(Definition[] interjection) {
        this.interjection = interjection;
    }
}
