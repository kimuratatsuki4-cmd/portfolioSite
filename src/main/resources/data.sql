INSERT INTO articles (title, content, author_name, author_id, created_at, likes, retweets) VALUES 
('AIコーディングアシスタントの選定について', '開発効率を飛躍的に向上させるAIツール。GitHub Copilot, Cursorなど数多くのツールが登場していますが、選定の鍵となるのは「既存ワークフローとの親和性」です。自身のプロジェクトに最適なツールを見つけましょう。', 'Kate', '@kate_dev', NOW(), 120, 45),
('Spring Boot 3.xへの移行ガイド', 'Java 17以上が必須となったSpring Boot 3。移行の際の注意点や、新機能であるObservabilityの強化ポイントについて解説します。特にJakarta EEパッケージへの変更は影響範囲が大きいので注意が必要です。', 'Kate', '@kate_dev', DATE_SUB(NOW(), INTERVAL 2 DAY), 85, 20),
('モダンなWebデザインのトレンド2026', 'グラスモーフィズムは依然として人気ですが、より立体的で没入感のある「クレイモーフィズム」や、AI生成アートを活用した動的な背景がトレンドになっています。UIはシンプルに、体験はリッチに。', 'Kate', '@kate_dev', DATE_SUB(NOW(), INTERVAL 5 DAY), 210, 98),
('ポートフォリオサイトをリニューアルしました', '今回、Spring BootとThymeleafを使ってポートフォリオサイトを完全リニューアルしました！デザインにもこだわり、グラスモーフィズムを取り入れています。非同期コメント機能も実装済みです。', 'Kate', '@kate_dev', DATE_SUB(NOW(), INTERVAL 1 WEEK), 350, 150),
('Docker環境構築のベストプラクティス', '開発環境のDocker化はもはや常識。軽量なAlpine Linuxベースのイメージ活用や、マルチステージビルドによるイメージサイズ削減など、明日から使えるTipsを紹介します。', 'TechBot', '@tech_bot_01', DATE_SUB(NOW(), INTERVAL 2 WEEK), 55, 12);
