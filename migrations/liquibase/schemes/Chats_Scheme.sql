create table Chats
(
    chat_id bigint,
    wait_track int,
    wait_untrack int,

    primary key (chat_id),
    created_at timestamp with time zone not null
)
