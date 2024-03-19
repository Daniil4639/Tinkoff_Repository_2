package edu.jooq;

import edu.jooq.tables.ChatLinkConnection;
import edu.jooq.tables.Chats;
import edu.jooq.tables.Links;
import javax.annotation.processing.Generated;


/**
 * Convenience access to all tables in the default schema.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Tables {

    /**
     * The table <code>CHAT_LINK_CONNECTION</code>.
     */
    public static final ChatLinkConnection CHAT_LINK_CONNECTION = ChatLinkConnection.CHAT_LINK_CONNECTION;

    /**
     * The table <code>CHATS</code>.
     */
    public static final Chats CHATS = Chats.CHATS;

    /**
     * The table <code>LINKS</code>.
     */
    public static final Links LINKS = Links.LINKS;
}