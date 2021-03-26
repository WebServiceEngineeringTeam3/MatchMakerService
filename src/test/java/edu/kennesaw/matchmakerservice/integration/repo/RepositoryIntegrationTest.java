package edu.kennesaw.matchmakerservice.integration.repo;

import edu.kennesaw.matchmakerservice.Application;
import edu.kennesaw.matchmakerservice.repo.Repository;
import edu.kennesaw.matchmakerservice.to.PlayerInfo;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RepositoryIntegrationTest {

    private static final Logger LOGGER = Logger.getLogger(RepositoryIntegrationTest.class.getName());

    @Autowired
    Repository repo;

    @Test
    @Ignore
    public void test01_addNewPlayer_Success() throws SQLException {
        PlayerInfo player = new PlayerInfo("cisco94", "Eric", "Acevedo", 35,"Advanced", "Southeast US", "English", "casual", 4, "Overwatch", "Competitive");
        boolean result = repo.addNewPlayer(player);
        assertTrue(result);
    }

    @Test
    public void test02_getPlayer_Success() throws SQLException{
        PlayerInfo player = repo.getPlayerInformation("ungo1985");
        LOGGER.info("test02_getPlayer_Success: " + player.toString());
        assertNotNull(player);
        assertTrue(player.getGamerId().equalsIgnoreCase("ungo1985"));
    }

    @Test
    public void test03_getCompleteInfoForFriendsList_Success() throws SQLException{
        List<String> gamerIds = new ArrayList<>();
        gamerIds.add("aguillermanp4");
        gamerIds.add("cisco94");
        gamerIds.add("feaclebe");
        gamerIds.add("jdacombelj");
        gamerIds.add("apullingerjw");
        gamerIds.add("eexleyqe");
        gamerIds.add("dklawih");
        gamerIds.add("afullickshp");
        gamerIds.add("ihammertonrg");
        gamerIds.add("scocklie6l");

        List<PlayerInfo> friendsList = repo.getCompleteInfoForFriendsList(gamerIds);
        LOGGER.info("test03_getCompleteInfoForFriendsList_Success: " + friendsList.toString());
        assertNotNull(friendsList);
        assertTrue(friendsList.size()>0);
    }

    @Test
    public void test04_getArrayString() throws SQLException{
        List<String> gamerIds = new ArrayList<>();
        gamerIds.add("aguillermanp4");
        gamerIds.add("cisco94");
        gamerIds.add("feaclebe");
        gamerIds.add("jdacombelj");
        gamerIds.add("apullingerjw");
        gamerIds.add("eexleyqe");
        gamerIds.add("dklawih");
        gamerIds.add("afullickshp");
        gamerIds.add("ihammertonrg");
        gamerIds.add("scocklie6l");

        String result = repo.getArrayString(gamerIds);
        LOGGER.info("test04_getArrayString: " + result);
        assertNotNull(result);
    }
}
