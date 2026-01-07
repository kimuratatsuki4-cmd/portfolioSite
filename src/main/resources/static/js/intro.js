// 初回アクセス時のみアニメーションを実行するための制御
document.addEventListener('DOMContentLoaded', () => {
    // 要素の取得
    const overlay = document.getElementById('intro-overlay');
    const serverIcon = document.querySelector('.server-icon');
    const loadingText = document.getElementById('loading-text');
    const packet = document.querySelector('.packet-dot');

    // オーバーレイが存在しない場合は処理を中断
    if (!overlay) return;

    // アニメーション完了フラグ（多重実行防止用）
    let isFinished = false;

    // ---------------------------------------------------------
    // 接続成功時の状態変化処理
    // ---------------------------------------------------------
    function showSuccessState() {
        if (isFinished) return;
        isFinished = true;

        // パケットアニメーションを隠す
        packet.style.opacity = '0';
        
        // サーバーアイコンを「接続成功」状態に変更
        serverIcon.classList.remove('fa-server');
        serverIcon.classList.add('fa-check-circle'); // チェックマーク
        serverIcon.style.color = '#28a745'; // 緑色
        serverIcon.style.transform = 'scale(1.2)'; // 強調
        
        // テキストの更新
        loadingText.innerHTML = '<strong>Connected!</strong>';
        loadingText.style.color = '#28a745';
        
        // 成功状態を少し見せてからフェードアウトへ
        // スキップ時でも「Connected!」を一瞬見せることで達成感を与える
        setTimeout(finishAnimation, 500);
    }

    // ---------------------------------------------------------
    // 終了処理（フェードアウト）
    // ---------------------------------------------------------
    function finishAnimation() {
        // フェードアウトクラスを追加（CSSで opacity: 0 を設定）
        overlay.classList.add('fade-out');
        
        // CSSのtransition完了後にDOMから非表示にする
        setTimeout(() => {
            overlay.style.display = 'none';
        }, 1000);
    }

    // ---------------------------------------------------------
    // タイマー設定 (通常フロー)
    // ---------------------------------------------------------
    // 2.5秒後に接続成功とみなす
    const timerId = setTimeout(showSuccessState, 2500);

    // ---------------------------------------------------------
    // スキップ機能 (クリックイベント)
    // ---------------------------------------------------------
    overlay.addEventListener('click', () => {
        if (!isFinished) {
            // 通常のタイマーをキャンセル
            clearTimeout(timerId);
            // 即座に成功状態へ移行
            showSuccessState();
        }
    });
});
