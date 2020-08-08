package com.example.postal_code

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartException
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ExceptionResolver : HandlerExceptionResolver {

    val log: Logger = LoggerFactory.getLogger(ExceptionResolver::class.java)

    override fun resolveException(
            request: HttpServletRequest,
            response: HttpServletResponse,
            handler: Any?,
            ex: Exception): ModelAndView? {
        log.error("ERROR!!", ex)
        if (ex is MultipartException) return ModelAndView("error")
                .addObject("status", "400")
                .addObject("error", ex)
                .addObject("message", "ファイルサイズ超過")
                .addObject("timestamp", Date())
        return ModelAndView("error")
                .addObject("status", "400")
                .addObject("error", ex)
                .addObject("message", "不明")
                .addObject("timestamp", Date())
    }

}