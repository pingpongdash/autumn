package jp.kernelpanic.autumn.dbgateway.repository;

import jp.kernelpanic.autumn.dbgateway.entity.UserEntity;
import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> findByUsername(String username);
}
