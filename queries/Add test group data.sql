SELECT @gamerId  := 'atatlowcn';
SELECT @gamer_group_id := 'Fortnite casual group';

INSERT INTO gamer_groups
(gamer_id, gamer_group_id, gamer_friend_id)
( SELECT fr.gamer_id, @gamer_group_id, gf.gamer_id
FROM MatchMaker.gamer_friends fr
	INNER JOIN MatchMaker.gamers gf
		ON gf.gamer_id = fr.gamer_friend_id
 WHERE fr.gamer_id = @gamerId
 and preferred_game = 'Fortnite'
 and skill_level = 'casual');