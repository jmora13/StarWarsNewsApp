package com.example.phunapp.PhunModel


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
data class PhunModelItem(
    @JsonProperty("date")
    val date: String?,
    @JsonProperty("description")
    val description: String?,
    @PrimaryKey
    @JsonProperty("id")
    val id: Int?,
    @JsonProperty("image")
    val image: String?,
    @JsonProperty("locationline1")
    val locationline1: String?,
    @JsonProperty("locationline2")
    val locationline2: String?,
    @JsonProperty("phone")
    val phone: String?,
    @JsonProperty("timestamp")
    val timestamp: String?,
    @JsonProperty("title")
    val title: String?
)