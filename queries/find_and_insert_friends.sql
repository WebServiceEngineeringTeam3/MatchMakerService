SELECT @gamerId  := 'gpetrasdn';

INSERT INTO gamer_friends 
(gamer_id,
gamer_friend_id)
(SELECT g.gamer_id, fr. gamer_id 
FROM gamers fr
	inner join 
		(SELECT * FROM MatchMaker.gamers 
		where gamer_id = @gamerId) g 
			on g.skill_level = fr.skill_level
			 -- and g.region = fr.region
             and g.personality_type = fr.personality_type
             -- and g.minimum_wait_time = fr.minimum_wait_time
            and g.preferred_game = fr.preferred_game
WHERE fr.gamer_id != @gamerId);
            





