SELECT @gamerId  := 'ungo1985';

SELECT distinct gg.gamer_id, gg.gamer_group_id ,gg.gamer_friend_id, g.skill_level, g.region, g.language, g.personality_type, 
	g.minimum_wait_time, g.preferred_game, g.preferred_game_mode, g.first_name, g.last_name, g.age
FROM MatchMaker.gamer_groups gg 
	 INNER JOIN MatchMaker.gamer_friends gf
		 ON gg.gamer_friend_id = gf.gamer_friend_id
	INNER JOIN MatchMaker.gamers g
		ON g.gamer_id = gf.gamer_friend_id
 WHERE gg.gamer_id = @gamerId
 ORDER BY gg.gamer_group_id;