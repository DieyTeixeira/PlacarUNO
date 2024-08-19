package br.com.dieyteixeira.placaruno.models

class User(
    val email: String?
)

data class DataUser(
    val user_id: String = "",
    val user_email: String = "",
    val user_emailverified: Boolean = false,
    val user_name: String = ""
)