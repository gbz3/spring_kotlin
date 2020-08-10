package com.example.postal_code.db

import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Component

@Component
@Mapper
interface PostalCodeMapper {

    fun findAll(): List<PostalCodeEntry>

    @Insert("INSERT INTO postal_code VALUES( #{code}, #{oldZip}, #{zip}, #{kanaKen}, #{kanaShi}, #{kanaCho}, #{kanjiKen}, #{kanjiShi}, #{kanjiCho} )")
    fun insert(record: PostalCodeEntry): Boolean


}

data class PostalCodeEntry(
        val code: String,
        val oldZip: String,
        val zip: String,
        val kanaKen: String,
        val kanaShi: String,
        val kanaCho: String,
        val kanjiKen: String,
        val kanjiShi: String,
        val kanjiCho: String
)
