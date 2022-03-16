
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
    nickename varchar(20) NOT NULL,
    deleted_flg boolean DEFAULT false NOT NULL,
    created_timestamp timestamp NOT NULL,
    created_user varchar(20) NOT NULL,
    updated_timestamp timestamp NOT NULL,
    updated_user varchar(20) NOT NULL,
    PRIMARY KEY (player_id)
);
CREATE UNIQUE INDEX player_name_unique
    ON player (nickename);

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



