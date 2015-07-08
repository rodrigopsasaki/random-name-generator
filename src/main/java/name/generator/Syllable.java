package name.generator;

public class Syllable {

    public enum Type {PRE, MID, SUF}

    private String text;

    private boolean nextMustBeVowel;
    private boolean nextMustBeConsonant;
    private boolean addedAfterVowel;
    private boolean addedAfterConsonant;
    private Type type;

    private boolean startsWithVowel;
    private boolean endsWithVowel;

    public Syllable(String text) {
        this.text = text;
        verifyText();
    }

    public Syllable(String text, boolean nextMustBeVowel, boolean nextMustBeConsonant, boolean addedAfterVowel, boolean addedAfterConsonant) {
        this(text);
        this.nextMustBeVowel = nextMustBeVowel;
        this.nextMustBeConsonant = nextMustBeConsonant;
        this.addedAfterVowel = addedAfterVowel;
        this.addedAfterConsonant = addedAfterConsonant;
    }


    private void verifyText() {
        char[] letters = text.toCharArray();
        startsWithVowel = isVowel(letters[0]);
        endsWithVowel = isVowel(letters[text.length() - 1]);
    }

    private boolean isVowel(char c){
        return "AEIOUaeiou".indexOf(c) != -1;
    }

    public boolean startsWithVowel(){
        return startsWithVowel;
    }

    public boolean endsWithVowel(){
        return endsWithVowel;
    }

    public boolean startsWithConsonant(){
        return !startsWithVowel();
    }

    public boolean endsWithConsonant(){
        return !endsWithVowel();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean nextMustBeVowel() {
        return nextMustBeVowel;
    }

    public void setNextMustBeVowel(boolean nextMustBeVowel) {
        this.nextMustBeVowel = nextMustBeVowel;
    }

    public boolean nextMustBeConsonant() {
        return nextMustBeConsonant;
    }

    public void setNextMustBeConsonant(boolean nextMustBeConsonant) {
        this.nextMustBeConsonant = nextMustBeConsonant;
    }

    public boolean addedAfterVowel() {
        return addedAfterVowel;
    }

    public void setAddedAfterVowel(boolean addedAfterVowel) {
        this.addedAfterVowel = addedAfterVowel;
    }

    public boolean addedAfterConsonant() {
        return addedAfterConsonant;
    }

    public void setAddedAfterConsonant(boolean addedAfterConsonant) {
        this.addedAfterConsonant = addedAfterConsonant;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        if(type == Type.PRE){
            return text.substring(0, 1).toUpperCase() + text.substring(1);
        }
        return text;
    }
}
