package edu.kennesaw.matchmakerservice.repo;

import edu.kennesaw.matchmakerservice.datamodel.PlayerInfo;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableScan
@Repository
public interface PlayerInfoRepository extends CrudRepository<PlayerInfo, String> {

    @Query("SELECT playerInfo FROM PlayerInfo playerInfo WHERE playerInfo.gamerId = ?1")
    PlayerInfo findPlayerInfoByGamerId(String gamerId);

    Optional<PlayerInfo> findById(String id);

}
