package com.fx.user.adapter.security.dto

class GoogleOAuthDto(
    private val attribute: Map<String, Any>
) : OAuth2Dto {

    override fun getProvider(): String =
        "google"

    override fun getProviderId(): String =
        attribute["sub"].toString()

    override fun getEmail(): String =
        attribute["email"].toString()

    override fun getNickname(): String =
        attribute["name"].toString()

    override fun getMobile(): String? = null  // 기본 OAuth에서는 제공되지 않음

}