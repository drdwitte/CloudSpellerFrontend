package motifmapper.driver;



import static org.junit.Assert.*;

import be.iminds.motifmapper.driver.BaseFreq;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ddewitte on 19.08.15.
 */
public class TestBaseFreq  {


    @Test
    public void testIncrement(){
        BaseFreq bf = new BaseFreq();


        Assert.assertEquals("{\"A\":0,\"C\":0,\"G\":0,\"T\":0,\"N\":0,\"M\":0,\"R\":0,\"W\":0,\"S\":0,\"Y\":0,\"K\":0}",
                bf.toJson().toString());

        bf.increment('A');
        bf.increment('A');

        Assert.assertEquals("{\"A\":2,\"C\":0,\"G\":0,\"T\":0,\"N\":0,\"M\":0,\"R\":0,\"W\":0,\"S\":0,\"Y\":0,\"K\":0}",
                bf.toJson().toString());

        bf.increment('K');

        Assert.assertEquals("{\"A\":2,\"C\":0,\"G\":0,\"T\":0,\"N\":0,\"M\":0,\"R\":0,\"W\":0,\"S\":0,\"Y\":0,\"K\":1}",
                bf.toJson().toString());
    }




}
