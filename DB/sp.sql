
DELIMITER $$

-- Common Transaction Function
DROP PROCEDURE IF EXISTS funcTransaction;
CREATE PROCEDURE funcTransaction(IN inText TEXT, OUT outResult TINYINT)
BEGIN
	-- for multiple query
	DECLARE txtQueries TEXT;
	DECLARE strQuery VARCHAR(2048);
	DECLARE nCurrentPos INT DEFAULT 1;
	
	-- for error
	DECLARE nError INT DEFAULT 0;
	
	-- for system
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET nError = -1;
	
	SET txtQueries = inText;
	START TRANSACTION;
	WHILE CHAR_LENGTH(txtQueries) > 0 AND nCurrentPos > 0 AND nError = 0 DO
		SET nCurrentPos = INSTR(txtQueries, ';\\;');
		IF nCurrentPos = 0 THEN
			SET strQuery = txtQueries;
		ELSE
			SET strQuery = LEFT(txtQueries, nCurrentPos - 1);
		END IF;
		
		IF TRIM(strQuery) != '' THEN
			SET @s := strQuery;
			PREPARE STMT FROM @s;
			EXECUTE STMT;
			
			IF ROW_COUNT() = 0 THEN 
				SET nError = -2;
			END IF;
		END IF;
		
		SET txtQueries = SUBSTRING(txtQueries, nCurrentPos + 3);
	END WHILE;
	
	-- commit or rollback
	SET outResult = nError;
	IF nError = 0 THEN
		COMMIT;	
	ELSE
		ROLLBACK;
	END IF;

END $$

DELIMITER ;
