package name.generator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by rodrigo on 07/07/15.
 */
public class NameGenerator {

    private List<Syllable> prefixList;
    private List<Syllable> midTextList;
    private List<Syllable> suffixList;
    private Random random;

    private NameGenerator(){}

    public static NameGenerator getInstance(NameType type){
        NameGenerator nameGenerator = new NameGenerator();
        nameGenerator.initialize(type);
        return nameGenerator;
    }

    public void changeType(NameType type){
        initialize(type);
    }

    private void initialize(NameType type) {
        String fileName = type.toString().toLowerCase();

        prefixList = new ArrayList<Syllable>();
        midTextList = new ArrayList<Syllable>();
        suffixList = new ArrayList<Syllable>();
        random = new Random();

        InputStream stream = NameGenerator.class.getClassLoader().getResourceAsStream(fileName);
        Reader reader = null;
        BufferedReader bufferedReader = null;

        try {
            reader = new InputStreamReader(stream);
            bufferedReader = new BufferedReader(reader);
            String line;

            while((line = bufferedReader.readLine()) != null){

                boolean nextMustBeVowel = line.contains("+v");
                boolean nextMustBeConsonant = line.contains("+c");
                boolean addedAfterVowel = line.contains("-v");
                boolean addedAfterConsonant = line.contains("-c");

                Syllable syl = new Syllable(cleanText(line), nextMustBeVowel, nextMustBeConsonant, addedAfterVowel, addedAfterConsonant);

                if (line.startsWith("-")) {
                    syl.setType(Syllable.Type.PRE);
                    prefixList.add(syl);
                } else if (line.startsWith("+")) {
                    syl.setType(Syllable.Type.SUF);
                    suffixList.add(syl);
                } else {
                    syl.setType(Syllable.Type.MID);
                    midTextList.add(syl);
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bufferedReader != null)
                    bufferedReader.close();

                if(reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private String cleanText(String line) {
        return line.split("\\s")[0].replaceAll("\\W", "");
    }

    public String generate(){
        Syllable prefix = getRandomPrefix();
        Syllable midText = getRandomMidText(prefix);
        Syllable suffix = getRandomSuffix(midText);
        return prefix.toString() + midText.toString() + suffix.toString();
    }

    private Syllable getRandomPrefix() {
        return getRandomElement(prefixList);
    }

    private Syllable getRandomMidText(Syllable prefix) {
        boolean valid = false;
        Syllable text = null;
        while(!valid){
            text = getRandomElement(midTextList);
            if((prefix.nextMustBeVowel() && text.startsWithConsonant())
                    || (prefix.nextMustBeConsonant() && text.startsWithVowel())
                    || (prefix.endsWithConsonant() && text.addedAfterVowel())
                    || (prefix.endsWithVowel() && text.addedAfterConsonant())){
                continue;
            }
            valid = true;
        }
        return text;
    }

    private Syllable getRandomSuffix(Syllable midText) {
        boolean valid = false;
        Syllable text = null;
        while(!valid){
            text = getRandomElement(suffixList);
            if((midText.nextMustBeVowel() && text.startsWithConsonant())
                    || (midText.nextMustBeConsonant() && text.startsWithVowel())
                    || (midText.endsWithConsonant() && text.addedAfterVowel())
                    || (midText.endsWithVowel() && text.addedAfterConsonant())){
                continue;
            }
            valid = true;
        }
        return text;
    }

    private <E> E getRandomElement(List<E> list){
        return list.get(random.nextInt(list.size()));
    }

}
