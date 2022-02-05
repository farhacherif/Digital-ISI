package com.example.miniprojetisi.model

data class UserIdResponse (
    val copyright:String,
    val  is_guest_user: Boolean,
    val branding_version:String,
    val user_id:Int,
    val user_name: String,
    val session_id: String,
    val is_technical_user: Boolean,
    val version: String
)