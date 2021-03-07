SELECT @gamerId  := 'ababbidge5x';

SELECT * 
FROM gamers g
	inner join gamer_friends gf
		on g.gamer_id = gf.gamer_id
WHERE gf.gamer_id = @gamerId;
        