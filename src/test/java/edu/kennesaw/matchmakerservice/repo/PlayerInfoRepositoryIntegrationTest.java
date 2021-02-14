package edu.kennesaw.matchmakerservice.repo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import edu.kennesaw.matchmakerservice.Application;
import edu.kennesaw.matchmakerservice.datamodel.PlayerInfo;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@ActiveProfiles("local")
@TestPropertySource(properties = {
        "amazon.dynamodb.endpoint=http://localhost:8000/",
        "amazon.aws.accesskey=test1",
        "amazon.aws.secretkey=test231" })
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerInfoRepositoryIntegrationTest {

    private static final Logger LOGGER = Logger.getLogger(PlayerInfoRepositoryIntegrationTest.class.getName());

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    PlayerInfoRepository repository;

    @Before
    public void setup() throws Exception {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        //If table has not been created, then uncomment this block before test execution
       /**
       CreateTableRequest tableRequest = dynamoDBMapper
                .generateCreateTableRequest(PlayerInfo.class);
        tableRequest.setProvisionedThroughput(
                new ProvisionedThroughput(1L, 1L));
        amazonDynamoDB.createTable(tableRequest);
        dynamoDBMapper.batchDelete((List<PlayerInfo>)repository.findAll());
        */
    }

    @Test
    public void givenPlayerInfo_whenRunFindAll_thenPlayerInfoIsFound() {
        PlayerInfo playerInfo = new PlayerInfo("test124", "Allen", "Smith", "beginner",
                "Southeast US", "English", "casual", "4", "Overwatch",
                "Quickplay");
        repository.save(playerInfo);

        List<PlayerInfo> result = (List<PlayerInfo>) repository.findAll();
        LOGGER.info("givenPlayerInfo_whenRunFindAll_thenPlayerInfoIsFound: " + result.size());

        int index = 1;
        for(PlayerInfo player : result ){
            LOGGER.info("player " + index + " " + player.toString());
            index++;
        }

        assertTrue(result.size() > 0);
    }

    @Test
    public void test_findPlayerInfoById(){
        String gamerId = "test124";

        PlayerInfo playerInfo = repository.findPlayerInfoByGamerId(gamerId);

        LOGGER.info("test_findPlayerInfoById: " + playerInfo.toString());

        assertNotNull(playerInfo);
    }

    @Test
    public void test_findById(){
        String id1 = "b6184e10-8f9d-411e-9edc-c34d5aa9da56";
        String id2 = "98d47f87-8315-4040-834e-7adf2258a5b7";

        Optional<PlayerInfo> player1 = repository.findById(id1);
        Optional<PlayerInfo> player2 = repository.findById(id2);

        LOGGER.info("test_findById player1: " + player1.get().toString());
        LOGGER.info("test_findById player2: " + player2.get().toString());

        assertNotNull(player1.get());
        assertNotNull(player2.get());
    }
}
