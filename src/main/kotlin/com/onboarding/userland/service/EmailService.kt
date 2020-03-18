package com.onboarding.userland.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import java.io.IOException
import javax.mail.MessagingException

@Component
class EmailService @Autowired constructor(private val mailSender: JavaMailSender) {
    @Throws(MessagingException::class, IOException::class)
    fun sendMail(mailTo: String, subject: String, body: String) {
        val message = SimpleMailMessage().apply {
            setTo(mailTo)
            setSubject(subject)
            setText(body)
        }
        try {
            mailSender.send(message)
        } catch (e: MessagingException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        const val SUBJECT_FORGOT_PASSWORD = "Reset Your Password"
        const val RESET_PASSWORD_MAIL_TEMPLATE = "Use the token below to reset your password.\n\n    %s\n" +
                "Please ignore this email if you didn't request this."
    }
}