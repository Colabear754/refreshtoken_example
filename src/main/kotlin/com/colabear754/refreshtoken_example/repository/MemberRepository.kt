package com.colabear754.refreshtoken_example.repository

import com.colabear754.refreshtoken_example.common.MemberType
import com.colabear754.refreshtoken_example.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, UUID> {
    fun findByAccount(account: String): Member?
    fun findAllByType(type: MemberType): List<Member>
}