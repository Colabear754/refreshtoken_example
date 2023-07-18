package com.colabear754.refreshtoken_example.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
class MemberRefreshToken(
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "member_id")
    val member: Member,
    private var refreshToken: String
) {
    @Id
    val memberId: UUID? = null
    private var reissueCount = 0

    fun updateRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }

    fun validateRefreshToken(refreshToken: String) = this.refreshToken == refreshToken

    fun increaseReissueCount() {
        reissueCount++
    }
}