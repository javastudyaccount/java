
drop table game cascade;
CREATE TABLE game (
    game_id bigint NOT NULL AUTO_INCREMENT,
    roome_id bigint NOT NULL,
    started_timestamp timestamp,
    ended_timestamp timestamp,
    shuffled_tiles json,
    from_direction varchar(20),
    from_column integer,
    deleted_flg boolean DEFAULT false NOT NULL,
    created_timestamp timestamp NOT NULL,
    created_user varchar(20) NOT NULL,
    updated_timestamp timestamp NOT NULL,
    updated_user varchar(20) NOT NULL,
    PRIMARY KEY (game_id)
);


drop table game_log cascade;
CREATE TABLE game_log (
    game_log_id bigint NOT NULL AUTO_INCREMENT,
    game_id bigint NOT NULL,
    player_id bigint NOT NULL,
    operation varchar(20) NOT NULL,
    tiles json NOT NULL,
    player_id_counterpart bigint,
    deleted_flg boolean DEFAULT false NOT NULL,
    created_timestamp timestamp NOT NULL,
    created_user varchar(20) NOT NULL,
    updated_timestamp timestamp NOT NULL,
    updated_user varchar(20) NOT NULL,
    PRIMARY KEY (game_log_id)
);

drop table game_player cascade;
CREATE TABLE game_player (
    game_id bigint NOT NULL,
    player_id bigint NOT NULL,
    mentsu json,
    last integer,
    direction varchar(20),
    is_east boolean,
    deleted_flg boolean DEFAULT false NOT NULL,
    created_timestamp timestamp NOT NULL,
    created_user varchar(20) NOT NULL,
    updated_timestamp timestamp NOT NULL,
    updated_user varchar(20) NOT NULL,
    PRIMARY KEY (game_id, player_id)
);

drop table player cascade;
CREATE TABLE player (
    player_id bigint NOT NULL AUTO_INCREMENT,
    login_id varchar(8) not null,
    nickname varchar(20) NOT NULL,
    deleted_flg boolean DEFAULT false NOT NULL,
    created_timestamp timestamp NOT NULL,
    created_user varchar(20) NOT NULL,
    updated_timestamp timestamp NOT NULL,
    updated_user varchar(20) NOT NULL,
    PRIMARY KEY (player_id)
);
CREATE UNIQUE INDEX player_login_unique
    ON player (login_id);
CREATE UNIQUE INDEX player_name_unique
    ON player (nickname);

drop table room cascade;
CREATE TABLE room (
    room_id bigint NOT NULL AUTO_INCREMENT,
    room_name varchar(50) NOT NULL,
    deleted_flg boolean DEFAULT false NOT NULL,
    created_timestamp timestamp NOT NULL,
    created_user varchar(20) NOT NULL,
    updated_timestamp timestamp NOT NULL,
    updated_user varchar(20) NOT NULL,
    PRIMARY KEY (room_id)
);
CREATE UNIQUE INDEX room_name_unique
    ON room (room_name);

drop table player_in_room cascade;
create table player_in_room(
    room_id bigint not null,
    player_id bigint not null,
    role_id integer not null, --0: creator, 1: player, 2: visitor
    deleted_flg boolean DEFAULT false NOT NULL,
    created_timestamp timestamp NOT NULL,
    created_user varchar(20) NOT NULL,
    updated_timestamp timestamp NOT NULL,
    updated_user varchar(20) NOT NULL,
    PRIMARY KEY (room_id, player_id)
);

drop table passwd cascade;
create table passwd(
    player_id bigint not null,
    password varchar(256) not null,
    deleted_flg boolean DEFAULT false NOT NULL,
    created_timestamp timestamp NOT NULL,
    created_user varchar(20) NOT NULL,
    updated_timestamp timestamp NOT NULL,
    updated_user varchar(20) NOT NULL,
    PRIMARY KEY (player_id)
);
-- alter table passwd modify password varchar(256) not null;

CREATE TABLE persistent_logins (
    login_id  VARCHAR(64) NOT NULL,
    series    VARCHAR(64) NOT NULL PRIMARY KEY,
    token     VARCHAR(64) NOT NULL,
    last_used TIMESTAMP   NOT NULL
);

drop table notice cascade;
create table notice(
    notice_id bigint not null AUTO_INCREMENT PRIMARY KEY,
    title varchar(200) not null,
    detail varchar(5000) not null,
    start_date date not null,
    end_date date,
    deleted_flg boolean DEFAULT false NOT NULL,
    created_timestamp timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_user varchar(20) NOT NULL,
    updated_timestamp timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    updated_user varchar(20) NOT NULL
);

insert into notice (title, detail, start_date, created_user, updated_user) values ('notice', 'notice detail', '2022-04-10', 'SE', 'SE');

drop table room_player cascade;
create table room_player(
    long room_id,
    long player_id,
    long role,
    deleted_flg boolean DEFAULT false NOT NULL,
    created_timestamp timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_user varchar(20) NOT NULL,
    updated_timestamp timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    updated_user varchar(20) NOT NULL,
    PRIMARY KEY (room_id, player_id)
);


