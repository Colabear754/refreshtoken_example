package com.colabear754.refreshtoken_example.repository

import com.colabear754.refreshtoken_example.entity.MemberRefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MemberRefreshTokenRepository : JpaRepository<MemberRefreshToken, UUID> {
    fun findByMemberIdAndReissueCountLessThan(id: UUID, count: Long): MemberRefreshToken?
}