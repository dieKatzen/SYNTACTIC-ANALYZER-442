import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ReservedSymbols {

    public static Character [] singles = new Character[]{':',',','.',';','[',
            ']' , '{' ,'}' ,'(',')','+' , '-' ,'*' ,'/'};
    public static String [] doubles = new String[]{ "==", "<>","<=",">=",
             "::"};

    public static HashSet <Character>  singlesHash = new HashSet<Character>(Arrays.asList(singles));
    public static HashSet <String>  doublesHash = new HashSet<String>(Arrays.asList(doubles));



}
