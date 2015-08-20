package motifmapper.driver;

import be.iminds.motifmapper.driver.BaseFreqList;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ddewitte on 19.08.15.
 */
public class TestBaseFreqList {

    @Test
    public void testAddOneMotif(){
        String motif = "AN";

        BaseFreqList bfl = new BaseFreqList();

        bfl.increment(-8,-7,motif);

        Assert.assertEquals("[{\"-8\":{\"A\":1,\"C\":0,\"G\":0,\"T\":0,\"N\":0,\"M\":0,\"R\":0,\"W\":0,\"S\":0,\"Y\":0,\"K\":0}},{\"-7\":{\"A\":0,\"C\":0,\"G\":0,\"T\":0,\"N\":1,\"M\":0,\"R\":0,\"W\":0,\"S\":0,\"Y\":0,\"K\":0}}]",
                bfl.toJsonArray().toString());



    }

    @Test
    public void testMultipleOverlappingMotifs(){
        String motif1 = "TAT";
        String motif2 = "TAN";
        String motif3 = "CGT";

        BaseFreqList bfl = new BaseFreqList();

        bfl.increment(-8,-6,motif1);
        bfl.increment(-8,-6,motif2);
        bfl.increment(-10,-8,motif3);


        Assert.assertEquals(  "[" +
               "{\"-10\":{\"A\":0,\"C\":1,\"G\":0,\"T\":0,\"N\":0,\"M\":0,\"R\":0,\"W\":0,\"S\":0,\"Y\":0,\"K\":0}}," +
                "{\"-9\":{\"A\":0,\"C\":0,\"G\":1,\"T\":0,\"N\":0,\"M\":0,\"R\":0,\"W\":0,\"S\":0,\"Y\":0,\"K\":0}}," +
                "{\"-8\":{\"A\":0,\"C\":0,\"G\":0,\"T\":3,\"N\":0,\"M\":0,\"R\":0,\"W\":0,\"S\":0,\"Y\":0,\"K\":0}}," +
                "{\"-7\":{\"A\":2,\"C\":0,\"G\":0,\"T\":0,\"N\":0,\"M\":0,\"R\":0,\"W\":0,\"S\":0,\"Y\":0,\"K\":0}}," +
                "{\"-6\":{\"A\":0,\"C\":0,\"G\":0,\"T\":1,\"N\":1,\"M\":0,\"R\":0,\"W\":0,\"S\":0,\"Y\":0,\"K\":0}}" +
                        "]",
                bfl.toJsonArray().toString());



    }


}
