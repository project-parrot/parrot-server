package com.fx.user.adapter.security.dto

interface OAuth2Dto {

    fun getProvider(): String

    fun getProviderId(): String

    fun getEmail(): String

    fun getNickname(): String

    fun getMobile(): String?

}