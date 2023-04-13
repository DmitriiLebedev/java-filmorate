package ru.yandex.practicum.filmorate.storage.user;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final HashMap<Integer, User> users = new HashMap<>();
    @Getter
    private int id = 0;

    private int increaseId() {
        return ++id;
    }

    @Override
    public User addUser(User user) {
        user.setId(increaseId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User getUser(int id) {
        return users.get(id);
    }

    @Override
    public HashMap<Integer, User> getAllUsers() {
        return users;
    }

    @Override
    public User updateUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void addFriend(User user, User user2) {
        if (user.getId() != user2.getId()) {
            user.getFriends().add(user2.getId());
            user2.getFriends().add(user.getId());
        }
    }

    @Override
    public void removeFriend(User user, User user2) {
        if (user.getId() != user2.getId()) {
            user.getFriends().remove(user2.getId());
            user2.getFriends().remove(user.getId());
        }
    }

    @Override
    public ArrayList<User> getFriends(User user) {
        ArrayList<User> userFriends = new ArrayList<>();
        for (int id : user.getFriends()) {
            userFriends.add(users.get(id));
        }
        return userFriends;
    }

    @Override
    public List<User> getCommonFriends(User user, User user2) {
        List<User> commonFriends = new ArrayList<>();
        Set<Integer> friendsIds = new HashSet<>(user.getFriends());
        friendsIds.retainAll(user2.getFriends());
        for (int id : friendsIds) {
            commonFriends.add(users.get(id));
        }
        return commonFriends;
    }


}
