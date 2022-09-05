
public class SimilarSequence implements Comparable<SimilarSequence>{
    private String firstString; //строка N
    private String secondString; //строка M
    private int count; //количество совпадений

    public SimilarSequence(String firstString, String secondString, int count) {
        this.firstString = firstString;
        this.secondString = secondString;
        this.count = count;
    }

    public String getFirstString() {
        return firstString;
    }

    public String getSecondString() {
        return secondString;
    }

    @Override
    public int compareTo(SimilarSequence object) {
        return this.count - object.count;
    }
}
