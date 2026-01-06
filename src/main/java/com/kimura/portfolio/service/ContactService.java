package com.kimura.portfolio.service;

import com.kimura.portfolio.entity.Contact;
import com.kimura.portfolio.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final JavaMailSender javaMailSender;
    private final String adminEmail;

    // JavaMailSenderと管理者メールアドレスをコンストラクタインジェクションで受け取ります
    public ContactService(ContactRepository contactRepository,
            JavaMailSender javaMailSender,
            @Value("${portfolio.admin.email}") String adminEmail) {
        this.contactRepository = contactRepository;
        this.javaMailSender = javaMailSender;
        this.adminEmail = adminEmail;
    }

    @Transactional
    public void saveContact(Contact contact) {
        // 1. データベースにお問い合わせ内容を保存（履歴として残す）
        contactRepository.save(contact);

        // 2. 管理者へ通知メールを送信
        sendNotificationEmail(contact);

        // 3. お問い合わせしてくれたユーザーへ自動返信メールを送信
        sendAutoReplyEmail(contact);
    }

    // 管理者への通知メールを作成・送信するメソッド
    private void sendNotificationEmail(Contact contact) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hello@demomailtrap.com"); // Mailtrapのデフォルト送信元（必要に応じて変更可）
        message.setTo(adminEmail);
        message.setSubject("【Portfolio】お問い合わせがありました");
        message.setText("以下のお問い合わせを受け付けました。\n\n" +
                "お名前: " + contact.getName() + "\n" +
                "メールアドレス: " + contact.getEmail() + "\n" +
                "件名: " + contact.getSubject() + "\n" +
                "内容:\n" + contact.getMessage());

        javaMailSender.send(message);
    }

    // ユーザーへの自動返信メールを作成・送信するメソッド
    private void sendAutoReplyEmail(Contact contact) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hello@demomailtrap.com"); // Mailtrapのデフォルト送信元
        message.setTo(contact.getEmail()); // フォームに入力されたメールアドレス宛に送信
        message.setSubject("【自動返信】お問い合わせありがとうございます");
        message.setText(contact.getName() + " 様\n\n" +
                "お問い合わせありがとうございます。\n" +
                "以下の内容で受け付けました。\n" +
                "確認次第、ご連絡させていただきます。\n\n" +
                "--------------------------------------------------\n" +
                "件名: " + contact.getSubject() + "\n" +
                "内容:\n" + contact.getMessage() + "\n" +
                "--------------------------------------------------");

        javaMailSender.send(message);
    }
}
