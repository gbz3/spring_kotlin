package com.example.postal_code.controller

import com.example.postal_code.db.PostalCodeEntry
import com.example.postal_code.db.PostalCodeMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class JsonController {

    @Autowired
    lateinit var pcMapper: PostalCodeMapper

    @GetMapping("/api/table/postal_code")
    fun showPostalCode(): List<PostalCodeEntry> {
        return pcMapper.findAll()
    }

}
