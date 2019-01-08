INSERT INTO Song(id,title,artist,album,released) VALUES (10,'7 Years','Lukas Graham','Lukas Graham (Blue Album)',2015);
INSERT INTO Song(id,title,artist,album,released) VALUES (9,'Private Show','Britney Spears','Glory',2016);
INSERT INTO Song(id,title,artist,album,released) VALUES (8,'No','Meghan Trainor','Thank You',2016);
INSERT INTO Song(id,title,artist,album,released) VALUES (7,'i hate u, i love u','Gnash','Top Hits 2017',2017);
INSERT INTO Song(id,title,artist,album,released) VALUES (6,'I Took a Pill in Ibiza','Mike Posner','At Night, Alone.',2016);
INSERT INTO Song(id,title,artist,album,released) VALUES (5,'Bad Things','Camila Cabello, Machine Gun Kelly','Bloom',2017);
INSERT INTO Song(id,title,artist,album,released) VALUES (4,'Ghostbusters (I''m not a fraid)','Fall Out Boy, Missy Elliott','Ghostbusters',2016);
INSERT INTO Song(id,title,artist,album,released) VALUES (3,'Team','Iggy Azalea',NULL,2016);
INSERT INTO Song(id,title,artist,album,released) VALUES (2,'Mom','Meghan Trainor, Kelli Trainor','Thank You',2016);
INSERT INTO Song(id,title,artist,album,released) VALUES (1,'Can’t Stop the Feeling','Justin Timberlake','Trolls',2016);

INSERT INTO User(id,userId,lastName,firstName) VALUES (1,'mmuster','Muster','Maxime');
INSERT INTO User(id,userId,lastName,firstName) VALUES (2,'eschuler','Schuler','Elena');


INSERT INTO SongList (id, isPublic, Owner_id) VALUES (1, 1, 1);
INSERT INTO SongList_Songs (song_id, songList_id) VALUES (1, 1);
INSERT INTO SongList_Songs (song_id, songList_id) VALUES (2, 1);
INSERT INTO SongList_Songs (song_id, songList_id) VALUES (3, 1);

INSERT INTO SongList (id, isPublic, Owner_id) VALUES (2, 0, 1);
INSERT INTO SongList_Songs (song_id, songList_id) VALUES (4, 2);
INSERT INTO SongList_Songs (song_id, songList_id) VALUES (5, 2);
INSERT INTO SongList_Songs (song_id, songList_id) VALUES (6, 2);

INSERT INTO SongList (id, isPublic, Owner_id) VALUES (3, 1, 2);
INSERT INTO SongList_Songs (song_id, songList_id) VALUES (7, 3);
INSERT INTO SongList_Songs (song_id, songList_id) VALUES (8, 3);
INSERT INTO SongList_Songs (song_id, songList_id) VALUES (9, 3);

INSERT INTO SongList (id, isPublic, Owner_id) VALUES (4, 0, 2);
INSERT INTO SongList_Songs (song_id, songList_id) VALUES (1, 4);
INSERT INTO SongList_Songs (song_id, songList_id) VALUES (2, 4);
INSERT INTO SongList_Songs (song_id, songList_id) VALUES (3, 4);