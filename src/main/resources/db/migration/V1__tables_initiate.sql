CREATE TABLE IF NOT EXISTS users (
       id INT AUTO_INCREMENT,
       name VARCHAR(60),
       password VARCHAR(100),
       PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS messages (
       id INT AUTO_INCREMENT,
       name VARCHAR(60),
       message VARCHAR(255),
       PRIMARY KEY (id)
);

INSERT INTO messages(name, message)
VALUES ('user1', '1.asdasdasda'),
       ('user2', '1.special_user2'),
       ('user3', '1.special_user3'),
       ('user1', '2.dsadalclxzlcz'),
       ('user1', '3.dasdqqqwzzzxxx'),
       ('user1', '4.oooooopadasda'),
       ('user1', '5.ggggggfsccvv'),
       ('user1', '6.sdiaooocccc'),
       ('user1', '7.asssssxxxzzzaa'),
       ('user1', '8.psdpada'),
       ('user1', '9.dsadasdaxxxxxzzz'),
       ('user1', '10.assssxxzzrrrrr'),
       ('user1', '11.dsssqqqwwweessaaxx'),
       ('user1', '12.mvmvmvmvmccncnnc'),
       ('user1', '13.ssspppaaavvv');
