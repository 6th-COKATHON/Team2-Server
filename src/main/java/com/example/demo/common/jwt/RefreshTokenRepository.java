package com.example.demo.common.jwt;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	Optional<RefreshToken> findByUserId(Long userId);

	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("DELETE FROM RefreshToken r WHERE r.userId = :userId")
	void deleteAllByUserId(@Param("userId") Long userId);

	/**
	 * 사용자의 RefreshToken을 저장하거나 업데이트합니다.
	 * 이미 존재하는 경우 새로운 토큰으로 업데이트하고, 없으면 새로 생성합니다.
	 * 
	 * @param userId 사용자 ID
	 * @param refreshTokenValue 새로운 refresh token 값
	 * @return 저장된 RefreshToken 엔티티
	 */
	default RefreshToken saveOrUpdateByUserId(Long userId, String refreshTokenValue) {
		Optional<RefreshToken> existingToken = findByUserId(userId);
		
		if (existingToken.isPresent()) {
			RefreshToken token = existingToken.get();
			token.updateRefreshToken(refreshTokenValue);
			return save(token);
		} else {
			RefreshToken newToken = RefreshToken.builder()
				.userId(userId)
				.refreshToken(refreshTokenValue)
				.build();
			return save(newToken);
		}
	}
}
