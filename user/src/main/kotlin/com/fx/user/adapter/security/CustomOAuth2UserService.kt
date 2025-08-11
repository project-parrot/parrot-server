package com.fx.user.adapter.security

import com.fx.global.annotation.hexagonal.SecurityAdapter
import com.fx.user.adapter.security.dto.AuthenticatedUser
import com.fx.user.adapter.security.dto.GoogleOAuthDto
import com.fx.user.adapter.security.dto.NaverOAuthDto
import com.fx.user.adapter.security.dto.OAuth2Dto
import com.fx.user.application.`in`.UserCommandUseCase
import com.fx.user.application.`in`.dto.UserOAuthCommand
import com.fx.user.exception.UserException
import com.fx.user.exception.errorcode.UserErrorCode
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User

@SecurityAdapter
class CustomOAuth2UserService(
    private val userCommandUseCase: UserCommandUseCase
) : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User? {
        val loadUser = super.loadUser(userRequest)

        val registrationId = userRequest?.clientRegistration?.registrationId

        val oauth2Dto: OAuth2Dto = when (registrationId) {
            "naver" -> NaverOAuthDto(loadUser.attributes)
            "google" -> GoogleOAuthDto(loadUser.attributes)
            else -> throw UserException(UserErrorCode.UNSUPPORTED_OAUTH_PROVIDER)
        }

        val oauthId = oauth2Dto.getProvider() + ":" + oauth2Dto.getProviderId()
        val user = userCommandUseCase.findOrCreateOAuthUser(
            UserOAuthCommand(
                oauthId = oauthId,
                email = oauth2Dto.getEmail(),
                nickname = oauth2Dto.getNickname(),
                phone = oauth2Dto.getMobile()
            )
        )

        return AuthenticatedUser(
            userId = user.id!!,
            role = user.role!!
        )
    }

}