package be.iminds.motifmapper.driver;

import be.iminds.motifmapper.alphabets.Alphabet;
import be.iminds.motifmapper.alphabets.CharacterIterator;
import be.iminds.motifmapper.alphabets.IUPACAlphabet;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ddewitte on 19.08.15.
 */
public class BaseFreq {

    private static final Alphabet alph = new IUPACAlphabet(IUPACAlphabet.IUPACType.TWOFOLDSANDN);
    private Map<Character,Integer> iupacFreqs;


    public BaseFreq(){
        iupacFreqs = new HashMap<Character, Integer>();

        CharacterIterator it = alph.getAllCharsIterator();

        while (it.hasNext()){
            iupacFreqs.put(it.next(),0);
        }
    }

    public void increment(Character c){
        iupacFreqs.put(c,iupacFreqs.get(c)+1);
    }

    public JsonObject toJson(){
        JsonObject js = new JsonObject();

        CharacterIterator it = alph.getAllCharsIterator();

        while (it.hasNext()){
            Character c = it.next();
            int freq = iupacFreqs.get(c);
            js.add("" + c, new JsonPrimitive(freq));

        }

        return js;
    }

    public void normalize() {
        CharacterIterator it = alph.getAllCharsIterator();

        while (it.hasNext()){
            Character c = it.next();
            int freq = iupacFreqs.get(c);

            if (freq > 1){
                iupacFreqs.put(c,1);
            }

        }

    }

    public void joinWith(BaseFreq baseFreq) {
        CharacterIterator it = alph.getAllCharsIterator();

        while (it.hasNext()){
            Character c = it.next();
            iupacFreqs.put(c, iupacFreqs.get(c) + baseFreq.iupacFreqs.get(c));
        }
    }
}
