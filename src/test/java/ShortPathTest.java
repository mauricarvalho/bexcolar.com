import dijkstra.BexShortestPath;
import models.Airport;
import models.FlightNetwork;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class ShortPathTest {

    private File file;

    @Before
    public void readFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        file = new File(classLoader.getResource("input-file.csv").getFile());
    }

    @Test
    public void buildNetworkTest() {
        Boolean isNetworkBuilt = BexShortestPath.buildNetwork(file.getAbsolutePath());
        Assert.assertEquals(true, isNetworkBuilt);
    }


    @Test
    public void checkNetworkSizeTest() {
        BexShortestPath.calculateShortestPathFromSource(new Airport("GRU"));
        Assert.assertEquals(5, FlightNetwork.getAirports().size());
    }


}
