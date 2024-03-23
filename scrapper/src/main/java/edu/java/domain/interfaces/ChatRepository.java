package edu.java.domain.interfaces;

import edu.java.api_exceptions.ChatAlreadyExistsException;
import edu.java.api_exceptions.DoesNotExistException;

public interface ChatRepository {

    void addChatRequest(long chatId) throws ChatAlreadyExistsException;

    void deleteChatRequest(long chatId) throws DoesNotExistException;

    void makeTrack(long chatId);

    void makeUntrack(long chatId);

    void deleteTrack(long chatId);

    void deleteUntrack(long chatId);

    boolean isWaitingTrack(long chatId);

    boolean isWaitingUntrack(long chatId);
}
