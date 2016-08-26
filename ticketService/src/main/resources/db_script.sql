# Create Database
create database theater;

use theater;

# Create all Tables

CREATE TABLE `Level` (
  `levelId` int(11) NOT NULL,
  `levelName` varchar(45) DEFAULT NULL,
  `price` decimal(6,2) DEFAULT NULL,
  `numberOfRow` int(11) DEFAULT NULL,
  `seatsinRow` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`levelId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `Seat` (
  `seatId` int(11) NOT NULL AUTO_INCREMENT,
  `levelId` int(11) DEFAULT NULL,
  `row` int(11) DEFAULT NULL,
  `seatNo` int(11) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`seatId`),
  KEY `level_idx` (`levelId`),
  CONSTRAINT `level` FOREIGN KEY (`levelId`) REFERENCES `Level` (`levelId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1;


CREATE TABLE `Seathold` (
  `seatholdId` varchar(200) NOT NULL,
  `customerEmail` varchar(45) DEFAULT NULL,
  `holdtime` datetime DEFAULT NULL,
  `confirmationCode` varchar(400) DEFAULT NULL,
  `reservationTime` datetime DEFAULT NULL,
  `noOfSeats` int(11) DEFAULT NULL,
  `isValidHold` int(11) DEFAULT '1',
  PRIMARY KEY (`seatholdId`),
  KEY `customer_idx` (`customerEmail`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `SeatholdMapping` (
  `smId` int(11) NOT NULL AUTO_INCREMENT,
  `seatholdId` varchar(200) DEFAULT NULL,
  `seatId` int(11) DEFAULT NULL,
  PRIMARY KEY (`smId`),
  KEY `seatid_idx` (`seatId`),
  KEY `seatholdid_idx` (`seatholdId`),
  CONSTRAINT `Seat` FOREIGN KEY (`seatId`) REFERENCES `Seat` (`seatId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `seatholdmap` FOREIGN KEY (`seatholdId`) REFERENCES `Seathold` (`seatholdId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1;



INSERT INTO Level (levelId, levelName, price, numberofRow, seatsinRow) VALUES (1, 'Orchestra', 100.00, 25, 50);
INSERT INTO Level (levelId, levelName, price, numberofRow, seatsinRow) VALUES (2, 'Main', 75.00, 20, 100);
INSERT INTO Level (levelId, levelName, price, numberofRow, seatsinRow) VALUES (3, 'Balcony 1', 50.00, 15, 100);
INSERT INTO Level (levelId, levelName, price, numberofRow, seatsinRow) VALUES (4, 'Balcony 2', 40.00, 15, 100);



# Create Procedure - Seat Rank

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Seatentries`(OUT RESULT INT)
BEGIN

	DECLARE TMP INT;
	DECLARE rowcounts INT;
    DECLARE startrows INT;
    
    DECLARE seatperrow INT;
    DECLARE startseatrow INT;
    DECLARE levels INT;
    DECLARE tmpscore INT;
    DECLARE lvlscore INT;
    
    
    SET levels = 1;
    WHILE levels < 5 DO
		SELECT numberOfRow,seatsinRow into rowcounts, seatperrow FROM theater.Level where levelId = levels;    
        SET startrows = 1;
        SET lvlscore = 100 *(levels-1);
		WHILE startrows <= rowcounts DO
			#row
			SET startseatrow = 1;
            #seat
			WHILE startseatrow <= seatperrow DO
				
                #SCORE prediction
				IF ((seatperrow /5) < startseatrow) and (((seatperrow/5)*4) > startseatrow) THEN
					SET tmpscore = (startrows * 2 -1)+lvlscore;
                    
                ELSE
					SET tmpscore = (startrows * 2)+lvlscore;
                END IF;
            
				INSERT INTO theater.Seat (levelId,row, seatNo,score,status) VALUES (levels,startrows,startseatrow,tmpscore,1);
				SET startseatrow = startseatrow + 1;
			END WHILE;
            
			SET startrows = startrows +1;
            
		END WHILE;
        SET levels = levels +1;
	END WHILE;
    COMMIT;
    SET RESULT = levels;
END$$
DELIMITER ;


# Initialize the Procedure 

set @RESULT = 0;
call theater.Seatentries(@RESULT);
select @RESULT;


Commit;

