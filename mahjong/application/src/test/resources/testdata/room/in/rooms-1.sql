delete from room;

insert into
    room
(
    room_id,
    room_name,
    deleted_flg,
    created_timestamp,
    created_user,
    updated_timestamp,
    updated_user
)
values
(
    1,
    'testRoom1',
    0,
    now(),
    'default',
    now(),
    'default'
);
    