package be.iminds.motifmapper.driver;

import be.iminds.motifmapper.indexing.*;
import be.iminds.motifmapper.input.Gene;
import be.iminds.motifmapper.input.GeneFamily;
import be.iminds.motifmapper.motifmodels.IUPACMotif;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.*;
import java.util.*;

//TODO POC: BLS95, C90, F20 (lagere BLS maakt het moeilijker!)
//TODO only work

/**
 * Created by ddewitte on 19.08.15.
 */
public class StackedMapper {

    private GeneFamily gf;
    private PatternMatcher gst;

    private Map<String,BaseFreqList> frequencies;

    public StackedMapper(GeneFamily gf){
        this.gf = gf;
        this.gst = new GeneralizedSuffixTree(gf.getSequences(),true,12,new BitSetDecorationFactory());

        frequencies = new HashMap<String, BaseFreqList>();

        for (Gene g : gf.getGenes()){
            frequencies.put(g.getID(),new BaseFreqList());
        }
    }

    public String toString(){
        return gf.toString() + "\n" + frequencies;
    }

    public void mapBackMotif(String motif){

        IUPACMotif m = new IUPACMotif(motif,0);

        List<Suffix> matches = gst.matchExactPattern(m);

        if (matches==null) return;

        SuffixInterpreter interpreter = new SuffixInterpreter(gf);

        for (Suffix s : matches){
            FullMotifMatchWithPos match = interpreter.translateSuffixWithPos(motif, s, 0);

            pr(match);


            String forwardMotif = match.getDirection() == '+' ? m.toString() : m.getComplement().toString();

            //pr(forwardMotif);

            BaseFreqList bflist = frequencies.get(match.getGene());

            frequencies.get(match.getGene()).increment(match.getMotifStartPosition(),match.getMotifStopPosition(), forwardMotif);

        }


    }

    public JsonObject toJson(){
        JsonObject jo = new JsonObject();
        jo.add("family", new JsonPrimitive(gf.getFamilyName()));

        JsonArray seqArray = new JsonArray();

        for (Map.Entry<String,BaseFreqList> e : frequencies.entrySet()){

            JsonObject job = new JsonObject();
            job.addProperty("geneID", e.getKey());
            job.add("mapping", e.getValue().toJsonArray());

            seqArray.add(job);
        }

        jo.add("mappings",seqArray);

        return jo;
    }

    public void write(BufferedWriter wr) throws IOException{

        wr.write(toJson().toString());
        wr.newLine();
        wr.flush();
        return;
    }

    private static void pr(Object o){
        System.out.println(o);
    }


    public static void main(String [] args) throws IOException {

        //String motifFilename = "AFRealFilteredC90F20BLS95_ALL.txt";
        //LineIterator lines = new LineIterator(motifFilename);

        String genefamilyFilename ="groupOrtho1.txt";

        BufferedReader in = new BufferedReader(new FileReader(new File(genefamilyFilename)));
        GeneFamily gf = new GeneFamily(in);


        StackedMapper m = new StackedMapper(gf);
        m.write(new BufferedWriter(new OutputStreamWriter(System.out)));


        m.mapBackMotif("TATACGC");

        m.write(new BufferedWriter(new OutputStreamWriter(System.out)));
/*
        m.mapBackMotif("TCGTCC");
        m.mapBackMotif("TGANNGANNG");*/

        //TODO; er moet nog een BLS worden vooropgesteld dus motif, BLS95!!, er bestaat iets als patternblspair en
        //de pattern matcher van originele blsspeller1.0 kan dit, => even opzoeeken



    }



}
