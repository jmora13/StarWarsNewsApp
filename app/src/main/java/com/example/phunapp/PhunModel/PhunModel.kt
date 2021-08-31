package com.example.phunapp.PhunModel

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class PhunModel : ArrayList<PhunModelItem>()