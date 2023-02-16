package niffler.database.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import niffler.database.dao.UserRepository;
import niffler.database.dto.UserCreateDto;
import niffler.database.dto.UserReadDto;
import niffler.database.entity.user.User;
import niffler.mapper.Mapper;
import niffler.mapper.UserCreateMapper;
import niffler.mapper.UserReadMapper;
import org.hibernate.graph.GraphSemantic;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;

    @Transactional
    public User create(UserCreateDto userDto) {
        final User user = userCreateMapper.mapFrom(userDto);
        return userRepository.save(user);
    }

    @Transactional
    public <T> Optional<T> findById(UUID id, Mapper<User, T> mapper) {
        Map<String, Object> properties = Map.of(GraphSemantic.LOAD.getJakartaHintName(), userRepository.getEntityManager().getEntityGraph("WithAuthorities"));
        return userRepository.findById(id, properties)
                .map(mapper::mapFrom);
    }

    @Transactional
    public Optional<UserReadDto> findById(UUID id) {
        return findById(id, userReadMapper);
    }

    @Transactional
    public boolean delete(UUID id) {
        final Optional<User> optionalUser = userRepository.findById(id);
        optionalUser.ifPresent(user -> userRepository.delete(user.getId()));
        return optionalUser.isPresent();
    }
}
