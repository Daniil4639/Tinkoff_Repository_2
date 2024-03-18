package edu.java.scrapper.db;

import edu.java.api_exceptions.ChatAlreadyExistsException;
import edu.java.api_exceptions.DoesNotExistException;
import edu.java.domain.JdbcChatRepository;
import edu.java.domain.JdbcLinkDao;
import edu.java.response.api.LinkResponse;
import edu.java.scrapper.IntegrationEnvironment;
import java.time.OffsetDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JdbcTest extends IntegrationEnvironment {

    private final static JdbcChatRepository repository;
    private final static JdbcLinkDao linkDao;

    static {
        repository = new JdbcChatRepository(template);
        linkDao = new JdbcLinkDao(template);
    }

    @After
    public void configAfter() {
        template.execute("DELETE FROM chat_link_connection");
        template.execute("DELETE FROM chats");
        template.execute("DELETE FROM links");
    }

    @Test
    public void addChatTest() throws ChatAlreadyExistsException {
        repository.addChatRequest(12);

        assertThat(template.queryForObject(
            "SELECT COUNT(*) FROM chats WHERE chat_id=?", Integer.class, 12))
            .isEqualTo(1);
    }

    @Test
    public void deleteChatTest() throws ChatAlreadyExistsException, DoesNotExistException {
        repository.addChatRequest(12);

        repository.deleteChatRequest(12);

        assertThat(template.queryForObject(
            "SELECT COUNT(*) FROM chats WHERE chat_id=?", Integer.class, 12))
            .isEqualTo(0);
    }

    @Test
    public void addLinkTest() throws ChatAlreadyExistsException {
        repository.addChatRequest(12);

        linkDao.addLinkRequest(12, "someLink", OffsetDateTime.now()
            , OffsetDateTime.now());

        assertThat(template.queryForObject(
            "SELECT COUNT(*) FROM links WHERE url='someLink'", Integer.class))
            .isEqualTo(1);
    }

    @Test
    public void deleteLinkTest() throws ChatAlreadyExistsException {
        repository.addChatRequest(12);

        linkDao.addLinkRequest(12, "someLink", OffsetDateTime.now()
            , OffsetDateTime.now());

        linkDao.deleteLinkRequest(12, linkDao.getLinkId("someLink"));

        assertThat(template.queryForObject(
            "SELECT COUNT(*) FROM links WHERE url='someLink'", Integer.class))
            .isEqualTo(0);
    }

    @Test
    public void getListLinksTest() throws ChatAlreadyExistsException {
        repository.addChatRequest(12);

        linkDao.addLinkRequest(12, "someLink", OffsetDateTime.now()
            , OffsetDateTime.now());

        LinkResponse[] list = linkDao.getLinksByChatRequest(12);

        assertThat(list.length).isEqualTo(1);
        assertThat(list[0].getUrl()).isEqualTo("someLink");
    }
}
