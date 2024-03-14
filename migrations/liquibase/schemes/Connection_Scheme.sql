create table Chat_Link_Connection
(
    chat_id bigint references Chats(chat_id),
    link_id bigint references Links(id),

    constraint id primary key (chat_id, link_id)
)
