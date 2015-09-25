package be.iminds.motifmapper.driver;

import be.iminds.motifmapper.indexing.*;
import be.iminds.motifmapper.input.Gene;
import be.iminds.motifmapper.input.GeneFamily;
import be.iminds.motifmapper.motifmodels.IUPACMotif;

import be.iminds.motifmapper.phylogenetics.BLS;
import be.iminds.motifmapper.phylogenetics.BLSCalculator;
import be.iminds.motifmapper.toolbox.LineIterator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

//TODO POC: BLS95, C90, F20 (lagere BLS maakt het moeilijker!)
//TODO only work

/**
 * Created by ddewitte on 19.08.15.
 */
public class StackedMapper {

    private GeneFamily gf;
    private PatternMatcher gst;
    private BLSCalculator calculator;

    private Map<String,BaseFreqList> frequencies;

    public StackedMapper(GeneFamily gf){
        this.gf = gf;
        this.gst = new GeneralizedSuffixTree(gf.getSequences(),true,12,new BitSetDecorationFactory());
        this.calculator = new BLSCalculator(gf);
        frequencies = new HashMap<String, BaseFreqList>();

        for (Gene g : gf.getGenes()){
            frequencies.put(g.getID(),new BaseFreqList());
        }
    }

    public String toString(){
        return gf.toString() + "\n" + frequencies;
    }

    public void mapBackMotifSet(LineIterator it, int bls){
        PatternBLSPair p = new PatternBLSPair("",bls);
        int c = 0;
        while (it.hasNext()){
            String line = it.next();
            String pattern = line.split("\t")[0];
            p.setPattern(pattern);
            mapBackMotif(p);

            c++;
            if (c%100000==0)
                pr(c/100000 + "/32");


        }

    }

    public void mapBackMotif(PatternBLSPair pat){

        IUPACMotif m = new IUPACMotif(pat.getPattern(),0);

        List<Suffix> matches = gst.matchExactPattern(m);

        if (matches==null) return;
        BLS cutoffScore = new BLS(pat.getBls());

        NodeDecoration nodeInfo = new SequenceIDSet(gf.getNumberOfGenes());
        nodeInfo.processSuffixes(matches);
        BLS score = (BLS) calculator.calculateScore(nodeInfo);

        if (score.compareTo(cutoffScore)<0){ //score >=patternBLS
            return;
        }

        SuffixInterpreter interpreter = new SuffixInterpreter(gf);

        //NOTE we only want to add +1 per motif -> renormalization is necessary since a motif can have
        //multiple overlapping binding sites!

        Map<String, BaseFreqList> pileup = new HashMap<String, BaseFreqList>();


        for (Suffix s : matches){

            FullMotifMatchWithPos match = interpreter.translateSuffixWithPos(pat.getPattern(), s, 0);

            String forwardMotif = match.getDirection() == '+' ? m.toString() : m.getComplement().toString();

            String gene = match.getGene();


            BaseFreqList bflist = pileup.get(gene);
            if (bflist == null){
                bflist = new BaseFreqList();
                pileup.put(gene,bflist);
            }

            bflist.increment(match.getMotifStartPosition(), match.getMotifStopPosition(), forwardMotif);

        }

        for (Map.Entry<String,BaseFreqList> e : pileup.entrySet()){
            BaseFreqList bfnormalized = e.getValue();
            bfnormalized.normalize();

            frequencies.get(e.getKey()).joinWith(bfnormalized);
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

        String datasetdir = "/home/ddewitte/FULL_PROFESSIONAL_CONTAINER/Research/Bioinformatics_ALLSOURCES/" +
                "Bioinformatics_NieuweFolderTerug2015/Datasets/Input/MonocotFamiliesAgg10PBL1em6/";

        int numFiles = 1764;

        String motifFilename = "AFRealFilteredC90F20BLS95_ALL.txt";


        String genefamilyFilename ="groupOrtho";
        String filesuffix = ".txt";

        int numGF = 10;
        List<String> famsTGA = Toolbox.getFamiliesWithBolducMatch();

        Map<String,String> sequences = new HashMap<String, String>();

        for (int i=1; i<=numFiles; i++) {

            BufferedReader in = new BufferedReader(new FileReader(new File(datasetdir+genefamilyFilename+i+filesuffix)));

            for (int j=0; j<numGF; j++) {
                GeneFamily gf = new GeneFamily(in);

                if (!gf.isInitialized())
                    break;

                if (gf.getNumberOfGenes()!=4){
                    continue;
                }
                if (!famsTGA.contains(gf.getFamilyName())){
                    continue;
                }

                pr("processing file "+i+"-"+j+" : " + gf.getFamilyName());

                for (int g=0; g<gf.getNumberOfGenes(); g++){
                    Gene gene = gf.getGenes().get(g);
                    sequences.put(gene.getID() , gf.getSequence(gene).toString());
                }

                LineIterator lines = new LineIterator(motifFilename);

                StackedMapper m = new StackedMapper(gf);

                m.mapBackMotifSet(lines, 95);

                m.write(new BufferedWriter(new OutputStreamWriter(System.out)));
                m.write(new BufferedWriter(new FileWriter(new File(gf.getFamilyName() + "_C90F20BLS95" + "_KN1.txt"))));
            }
        }

        BufferedWriter wr = new BufferedWriter(new FileWriter(new File("seqsKN1.json")));
        wr.write("{"); wr.newLine();
        for (Map.Entry<String,String> e : sequences.entrySet()){

            wr.write("\""+e.getKey()+"\":\""+e.getValue() + "\","); wr.newLine();

        }
        wr.write("}"); wr.newLine();
        wr.close();



    }






}
