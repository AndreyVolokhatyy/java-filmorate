package ru.yandex.practicum.filmorate.storage.friend;

import ru.yandex.practicum.filmorate.model.Friend;

import java.util.List;

public interface FriendStorage {

    List<Friend> findAllFriends(Integer id);

    void deleteFriends(Integer followingId, Integer followedId);

    void addFriends(Integer followingId, Integer followedId);

    }