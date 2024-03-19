create table if not exists CHAT_LINK_CONNECTION
(
    chat_id bigint references CHATS(chat_id),
    link_id bigint references LINKS(id),

    constraint id primary key (chat_id, link_id)
)
