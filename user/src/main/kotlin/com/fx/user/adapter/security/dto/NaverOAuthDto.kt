package com.fx.user.adapter.security.dto

class NaverOAuthDto(
    attribute: Map<String, Any>
) : OAuth2Dto {

    private val attribute: Map<String, Any>

    init {
        this.attribute = attribute["response"] as Map<String, Any>
    }

    override fun getProvider(): String =
        "naver"

    override fun getProviderId(): String =
        attribute["id"].toString()

    override fun getEmail(): String =
        attribute["email"].toString()

    override fun getNickname(): String =
        attribute["nickname"].toString()

    override fun getMobile(): String =
        attribute["mobile"].toString()

}