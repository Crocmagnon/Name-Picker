package info.augendre.name_picker;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by gaugendre on 18/04/2016 13:56.
 */
public class ComputeResults implements Runnable {
    private final int LENGTH = 6;
    private final String VOWELS = "aeiouy";
    private final MainPanel mainPanel;
    private File file;

    public ComputeResults(MainPanel mainPanel) {
        this.file = mainPanel.getNamesFile();
        this.mainPanel = mainPanel;
    }

    @Override
    public void run() {
        try {
            Path filePath = Paths.get(file.getAbsolutePath());
            List<String> validStrings = Files.lines(filePath).parallel().filter(this::isValid).collect(Collectors.toList());
            mainPanel.getValidNamesList().setListData(validStrings.toArray());
        }
        catch (FileNotFoundException e) {
            System.err.println("ERR: Le fichier source n'existe pas ou le fichier de destination ne peut être crée ou ouvert.");
        }
        catch (IOException e) {
            System.err.println("ERR: entrée/sortie.");
        }
    }

    private boolean isValid(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("\\p{M}", "");
        s = s.toLowerCase();
        return lengthValidator(s)
                && vowelsAlternanceValidator(s)
                && containsValidator(s)
                && touchesOnceValidator(s)
                && noLetterRepeatValidator(s);
    }

    private boolean lengthValidator(String s) {
        return s.length() == LENGTH;
    }

    private boolean vowelsAlternanceValidator(String s) {
        boolean previousIsVowel = isVowel(s.charAt(0));

        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean currentIsVowel = isVowel(c);

            if (currentIsVowel && previousIsVowel || !currentIsVowel && !previousIsVowel) {
                return false;
            }
            previousIsVowel = currentIsVowel;
        }
        return true;
    }

    private boolean isVowel(char o) {
        o = Character.toLowerCase(o);
        return VOWELS.indexOf(o) >= 0;
    }

    private boolean containsValidator(String s) {
        return s.charAt(s.length() - 1) != 'a' && s.indexOf('h') < 0;
    }

    private boolean touchesOnceValidator(String s) {
        return (s.contains("b") ? 1 : 0) +
                (s.contains("m") ? 1 : 0) +
                (s.contains("p") ? 1 : 0) == 1;
    }

    private boolean noLetterRepeatValidator(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char current = s.charAt(i);
            int count = map.getOrDefault(current, 0) + 1;
            if (count > 1) {
                return false;
            }
            map.put(current, count + 1);
        }
        return true;
    }

}
