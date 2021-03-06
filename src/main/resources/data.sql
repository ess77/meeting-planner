INSERT INTO `ROOM` (`ID`,`NAME`, `CAPACITY`, `FEATURES`) VALUES (1001,'E1001', 23, NULL);
INSERT INTO `ROOM` (`ID`,`NAME`, `CAPACITY`, `FEATURES`) VALUES (1002,'E1002', 10, 'SCREEN');
INSERT INTO `ROOM` (`ID`,`NAME`, `CAPACITY`, `FEATURES`) VALUES (1003,'E1003', 8, 'SPIDER_PHONE');
INSERT INTO `ROOM` (`ID`,`NAME`, `CAPACITY`, `FEATURES`) VALUES (1004,'E1004', 4, 'BOARD');
INSERT INTO `ROOM` (`ID`,`NAME`, `CAPACITY`, `FEATURES`) VALUES (2001,'E2001', 4, NULL);
INSERT INTO `ROOM` (`ID`,`NAME`, `CAPACITY`, `FEATURES`) VALUES (2002,'E2002', 15, 'SCREEN|WEBCAM');
INSERT INTO `ROOM` (`ID`,`NAME`, `CAPACITY`, `FEATURES`) VALUES (2003,'E2003', 7, NULL);
INSERT INTO `ROOM` (`ID`,`NAME`, `CAPACITY`, `FEATURES`) VALUES (2004,'E2004', 9, 'BOARD');
INSERT INTO `ROOM` (`ID`,`NAME`, `CAPACITY`, `FEATURES`) VALUES (3001,'E3001', 13, 'SCREEN|WEBCAM|SPIDER_PHONE');
INSERT INTO `ROOM` (`ID`,`NAME`, `CAPACITY`, `FEATURES`) VALUES (3002,'E3002', 8, NULL);
INSERT INTO `ROOM` (`ID`,`NAME`, `CAPACITY`, `FEATURES`) VALUES (3003,'E3003', 9, 'SCREEN|SPIDER_PHONE');
INSERT INTO `ROOM` (`ID`,`NAME`, `CAPACITY`, `FEATURES`) VALUES (3004,'E3004', 4, NULL);
INSERT INTO `ROOM` (`ID`,`NAME`, `CAPACITY`, `FEATURES`) VALUES (9999,'E9999', 0, NULL);


INSERT INTO `EXTRA_FEATURE` (`ID`, `FEATURE_NAME`, `QUANTITY`, `AVAILABLE_QUANTITY`) VALUES (0,'SCREEN', 5, 5);
INSERT INTO `EXTRA_FEATURE` (`ID`, `FEATURE_NAME`, `QUANTITY`, `AVAILABLE_QUANTITY`) VALUES (1,'BOARD', 2, 2);
INSERT INTO `EXTRA_FEATURE` (`ID`, `FEATURE_NAME`, `QUANTITY`, `AVAILABLE_QUANTITY`) VALUES (2,'SPIDER_PHONE', 4, 4);
INSERT INTO `EXTRA_FEATURE` (`ID`, `FEATURE_NAME`, `QUANTITY`, `AVAILABLE_QUANTITY`) VALUES (3,'WEBCAM', 4, 4);
