package com.kimura.portfolio.service;

import com.kimura.portfolio.entity.Contact;
import com.kimura.portfolio.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final JavaMailSender javaMailSender;

    // プロパティファイルから注入される値
    private final String adminEmail;
    private final String fromEmail;

    public ContactService(ContactRepository contactRepository,
            JavaMailSender javaMailSender,
            @Value("${portfolio.admin.email}") String adminEmail,
            @Value("${portfolio.mail.from}") String fromEmail) {
        this.contactRepository = contactRepository;
        this.javaMailSender = javaMailSender;
        this.adminEmail = adminEmail;
        this.fromEmail = fromEmail;
    }

    @Transactional
    public void saveContact(Contact contact) {
        // 1. データベースに保存
        contactRepository.save(contact);

        // 2. 管理者へ通知メール送信（これは必ず送りたい）
        sendNotificationEmail(contact);

        // 3. ユーザーへ自動返信メール送信
        // 【重要】Mailgun Sandbox環境では、未許可のアドレスへ送ると例外が発生します。
        // そのため、try-catchで囲み、自動返信の失敗がユーザー画面のエラー(500)にならないようにします。
        try {
            sendAutoReplyEmail(contact);
        } catch (MailException e) {
            // 本番環境(Sandbox)などで送信できない場合はログに出力し、処理は続行する
            System.err.println("自動返信メールの送信に失敗しました（Sandbox制限の可能性があります）: " + e.getMessage());
        }
    }

    // 管理者（あなた）への通知
    private void sendNotificationEmail(Contact contact) {
        SimpleMailMessage message = new SimpleMailMessage();

        // From: プロパティファイルで設定された値
        // (Localなら hello@demomailtrap.com, Herokuなら MailgunのログインID)
        message.setFrom(fromEmail);

        // To: 管理者
        message.setTo(adminEmail);

        // Reply-To: お問い合わせしたユーザー
        // これを設定すると、届いたメールに「返信」するだけでユーザーに返せます
        message.setReplyTo(contact.getEmail());

        message.setSubject("【Portfolio】お問い合わせがありました");
        message.setText("以下のお問い合わせを受け付けました。\n\n" +
                "お名前: " + contact.getName() + "\n" +
                "メールアドレス: " + contact.getEmail() + "\n" +
                "件名: " + contact.getSubject() + "\n" +
                "内容:\n" + contact.getMessage());

        javaMailSender.send(message);
    }

    // ユーザーへの自動返信
    private void sendAutoReplyEmail(Contact contact) {
        SimpleMailMessage message = new SimpleMailMessage();

        // From: プロパティファイルで設定された値
        message.setFrom(fromEmail);

        // To: フォームに入力されたアドレス
        message.setTo(contact.getEmail());

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