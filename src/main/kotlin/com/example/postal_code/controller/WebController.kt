package com.example.postal_code.controller

import com.example.postal_code.db.PostalCodeEntry
import com.example.postal_code.db.PostalCodeMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.multipart.MultipartException
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.ModelAndView
import java.io.*

@Controller
class WebController {

    val log: Logger = LoggerFactory.getLogger(WebController::class.java)

    @GetMapping("/upload")
    fun viewUpload(@ModelAttribute("form") form: FileUploadForm): String {
        return "upload"
    }

    @PostMapping("/upload")
    fun upload(@ModelAttribute("form") form: FileUploadForm): String {
        log.trace("upload size=${form.uploadFile?.size}")
        val uploadFile = form.uploadFile ?: throw MultipartException("upload failed")
        if (uploadFile.isEmpty) throw MultipartException("upload empty")
        log.info("upload file type is ${uploadFile.contentType}")
        val br = BufferedReader(InputStreamReader(uploadFile.inputStream,"Windows-31J"))
        br.lineSequence()
                .map { it.split("\\s*\"?,\"?".toRegex()) }
                .map { Address(it[2], it[6], it[7], if (it[8] == "以下に掲載がない場合") "" else it[8]) } // 郵便番号、都道府県名、市区町村名、町域名を抽出
                .groupBy { it.zip }  // 郵便番号でグループ化
                .map { listOf(it.key, joinAddress(it.value)) }
                .filter { it[1].contains(sep) }
                .forEach { log.trace(it.toString()) }

        return "uploaded"
    }

    private val sep = "："
    private fun joinAddress(l: List<Address>): String {
        return if (l.size == 1) l[0].ad1 + l[0].ad2 +l[0].ad3
        else
            l.asSequence()
                    .groupBy { it.ad1 + it.ad2 }
                    .mapValues { it.value.asSequence().map { v -> v.ad3 }.joinToString(sep) }
                    .map { if (it.value != "") "${it.key}$sep${it.value}" else it.key }
                    .joinToString(sep)
    }

    data class Address(val zip: String, val ad1: String, val ad2: String, val ad3: String)
    data class FileUploadForm(var uploadFile: MultipartFile?, var action: String = "upload")

    @Autowired
    lateinit var pcMapper: PostalCodeMapper

    @GetMapping("/store")
    fun viewLoad(@ModelAttribute("form") form: FileUploadForm): String {
        form.action = "store"
        return "upload"
    }

    @PostMapping("/store")
    fun load(@ModelAttribute("form") form: FileUploadForm): ModelAndView {
        log.trace("store size=${form.uploadFile?.size}")
        val uploadFile = form.uploadFile ?: throw MultipartException("store failed")
        if (uploadFile.isEmpty) throw MultipartException("store empty")
        log.info("store file type is ${uploadFile.contentType}")
        val br = BufferedReader(InputStreamReader(uploadFile.inputStream,"Windows-31J"))
        var numOfStored = 0
        br.lineSequence()
                .map { it.split("\\s*\"?,\"?".toRegex()) }
                .map { PostalCodeEntry(it[0], it[1], it[2], it[3], it[4], it[5], it[6], it[7], it[8]) }
                .forEach { numOfStored += if (pcMapper.insert(it)) 1 else 0 }
        log.trace("numOfStored = $numOfStored")

        val result = ModelAndView("stored")
        result.addObject("numOfStored", numOfStored)
        return result
    }

    @GetMapping("/show/postal_code")
    fun showPostalCode(): String {
        return "show/postal_code"
    }

}