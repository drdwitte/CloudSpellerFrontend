package be.iminds.motifmapper.driver;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

/**
 * Created by ddewitte on 19.08.15.
 */
public class BaseFreqList {

    private SortedMap<Integer, BaseFreq> bfList;

    public BaseFreqList(){
        bfList = new TreeMap<Integer, BaseFreq>();
    }


    public void increment(int motifStartPosition, int motifStopPosition, String forwardMotif) {

        int cnt=0;
        for (int i=motifStartPosition; i<=motifStopPosition; i++, cnt++){

            if (!bfList.containsKey(i)){
                bfList.put(i,new BaseFreq());
            }
            bfList.get(i).increment(forwardMotif.charAt(cnt));
        }
    }

    public JsonArray toJsonArray() {
        JsonArray a = new JsonArray();

        for (Map.Entry<Integer,BaseFreq> e : bfList.entrySet()){
            JsonObject jo = new JsonObject();
            jo.add(e.getKey().toString(),e.getValue().toJson());
            a.add(jo);
        }

        return a;
    }
}
