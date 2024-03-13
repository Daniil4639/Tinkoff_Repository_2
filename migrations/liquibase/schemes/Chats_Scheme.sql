create table if not exists Chats
(
    chat_id bigint,

    primary key (chat_id),
    unique (chat_id)
)
