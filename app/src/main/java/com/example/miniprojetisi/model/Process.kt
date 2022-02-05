package com.example.miniprojetisi.model

data class Process (
    val name : String,
    val displayDescription: String,
    val deploymentDate : String,
    val displayName: String,
    val description: String,
    val deployedBy: String,
    val id : String,
    val activationState: String,
    val version: String,
    val configurationState: String,
    val last_update_date: String,
    val actorinitiatorid: String
)