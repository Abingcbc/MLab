DELIMITER //
CREATE TRIGGER trg_Post_au 
AFTER UPDATE ON post
FOR EACH ROW
BEGIN
  UPDATE `comment` SET `comment`.`status`=1
    WHERE `comment`.post_id=new.post_id;
  UPDATE `like` SET `like`.`status`=0
    WHERE `like`.`type`=0 AND `like`.type_id=new.post_id;
END;
    