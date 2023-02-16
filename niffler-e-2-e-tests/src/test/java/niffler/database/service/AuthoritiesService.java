package niffler.database.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import niffler.database.dao.AuthoritiesRepository;
import niffler.database.dto.AuthoritiesCreateDto;
import niffler.database.dto.AuthoritiesReadDto;
import niffler.database.entity.authorities.Authorities;
import niffler.mapper.AuthoritiesCreateMapper;
import niffler.mapper.AuthoritiesReadMapper;
import niffler.mapper.Mapper;
import org.hibernate.graph.GraphSemantic;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class AuthoritiesService {
    private final AuthoritiesRepository authoritiesRepository;
    private final AuthoritiesReadMapper authoritiesReadMapper;
    private final AuthoritiesCreateMapper authoritiesCreateMapper;
//    private final UserRepository userRepository;

    @Transactional
    public UUID createUserWithAuthority(AuthoritiesCreateDto authoritiesCreateDto) {
        final Authorities authorities = authoritiesCreateMapper.mapFrom(authoritiesCreateDto);
//        userRepository.save(authoritiesCreateDto.user());
        return authoritiesRepository.save(authorities).getId();
    }

    @Transactional
    public <T> Optional<T> findById(UUID id, Mapper<Authorities, T> mapper) {
        Map<String, Object> properties = Map.of(GraphSemantic.LOAD.getJakartaHintName(), authoritiesRepository.getEntityManager().getEntityGraph("WithUser"));
        return authoritiesRepository.findById(id, properties)
                .map(mapper::mapFrom);
    }

    @Transactional
    public Optional<AuthoritiesReadDto> findById(UUID id) {
        return findById(id, authoritiesReadMapper);
    }

    @Transactional
    public boolean delete(UUID id) {
        final Optional<Authorities> authorities = authoritiesRepository.findById(id);
        authorities.ifPresent(authority -> authoritiesRepository.delete(authority.getId()));
        return authorities.isPresent();
    }
}
