package com.fx.user.application.port.out.persistence

interface SignUpVerificationPort {

    /**
     * 이메일 인증 코드 저장
     * @param email 인증할 이메일
     * @param code 생성된 인증 코드
     * @param minutes 인증 코드 만료 시간
     */
    fun saveVerificationCode(email: String, code: String, minutes: Long)

    /**
     * 이메일 인증 코드 조회
     * @param email 조회할 이메일
     * @return 인증 코드 또는 null
     */
    fun getVerificationCode(email: String): String?

    /**
     * 이메일 인증 코드 삭제
     */
    fun deleteVerificationCode(email: String)

    /**
     * 임시 이메일 저장
     * @param email 이메일
     * @param code 인증 코드
     * @return 회원가입 완료용 토큰
     */
    fun saveTempToken(email: String, token: String): String

    /**
     * 토큰으로 이메일 조회
     */
    fun getEmailByToken(token: String): String?

    /**
     * 임시 이메일 삭제
     */
    fun deleteTempToken(token: String)

}