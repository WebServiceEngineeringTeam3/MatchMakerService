SELECT @gamerId  := 'kim12345';
SELECT @gamerGroupId  := 'test group';
SELECT @gamerFriendId  := 'friendxyz';

INSERT INTO gamer_groups 
(gamer_id,
gamer_group_id,
gamer_friend_id)
values (@gamerId, @gamerGroupId, @gamerFriendId);