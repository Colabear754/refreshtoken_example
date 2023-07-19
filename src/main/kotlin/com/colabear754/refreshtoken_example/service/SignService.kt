package com.colabear754.refreshtoken_example.service

import com.colabear754.refreshtoken_example.dto.SignInRequest
import com.colabear754.refreshtoken_example.dto.SignInResponse
import com.colabear754.refreshtoken_example.dto.SignUpRequest
import com.colabear754.refreshtoken_example.dto.SignUpResponse
import com.colabear754.refreshtoken_example.entity.Member
import com.colabear754.refreshtoken_example.entity.MemberRefreshToken
import com.colabear754.refreshtoken_example.repository.MemberRepository
import com.colabear754.refreshtoken_example.repository.MemberRefreshTokenRepository
import com.colabear754.refreshtoken_example.security.TokenProvider
import com.colabear754.refreshtoken_example.util.flushOrThrow
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignService(
    private val memberRepository: MemberRepository,
    private val memberRefreshTokenRepository: MemberRefreshTokenRepository,
    private val tokenProvider: TokenProvider,
    private val encoder: PasswordEncoder
) {
    @Transactional
    fun registMember(request: SignUpRequest) = SignUpResponse.from(
        memberRepository.flushOrThrow(IllegalArgumentException("이미 사용중인 아이디입니다.")) { save(Member.from(request, encoder)) }
    )

    @Transactional
    fun signIn(request: SignInRequest): SignInResponse {
        val member = memberRepository.findByAccount(request.account)
            ?.takeIf { encoder.matches(request.password, it.password) } ?: throw IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.")
        val accessToken = tokenProvider.createToken("${member.id}:${member.type}")
        val refreshToken = tokenProvider.createRefreshToken()
        memberRefreshTokenRepository.findByIdOrNull(member.id)?.updateRefreshToken(refreshToken)
            ?: memberRefreshTokenRepository.save(MemberRefreshToken(member, refreshToken))
        return SignInResponse(member.name, member.type, accessToken, refreshToken)
    }
}