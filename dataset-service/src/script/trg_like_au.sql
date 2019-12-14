delimiter //
CREATE TRIGGER trg_Like_au AFTER UPDATE ON `like`
FOR EACH ROW
BEGIN
  IF old.`status`=0 and new.`status`=1 THEN # not like -> like
    IF old.`type`=0 THEN #post 
      UPDATE post SET post.like_num=post.like_num+1 WHERE post.post_id=old.type_id;
    ELSEIF old.`type`=1 THEN #comment 
      UPDATE `comment` SET `comment`.like_num=`comment`.like_num+1 WHERE `comment`.comment_id=old.type_id;
    END IF;
  ELSEIF old.`status`=1 and new.`status`=0 THEN # like -> not like
    IF old.`type`=0 THEN #post
      UPDATE post SET post.like_num=post.like_num-1 WHERE post.post_id=old.type_id;
	ELSEIF old.`type`=1 THEN #comment
      UPDATE `comment` SET `comment`.like_num=`comment`.like_num-1 WHERE `comment`.comment_id=old.type_id;
	END IF;
  END IF;
END;