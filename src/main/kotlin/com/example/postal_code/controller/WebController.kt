package com.example.postal_code.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartException
import org.springframework.web.multipart.MultipartFile
import java.io.Serializable

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
        if (form.uploadFile != null && form.uploadFile!!.size >= 1024) throw MultipartException("over 1024")
        return "uploaded"
    }

    data class FileUploadForm(var uploadFile: MultipartFile?)

}