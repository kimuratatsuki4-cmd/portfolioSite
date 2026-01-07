document.addEventListener('DOMContentLoaded', function() {
    // ページロード時のアニメーション

    // 1. プログレスバーのアニメーション
    // width: 0% から data-width の値まで、CSS transition を利用してアニメーションさせる
    const progressBars = document.querySelectorAll('.progress-bar');
    progressBars.forEach(bar => {
        setTimeout(() => {
            const targetWidth = bar.getAttribute('data-width');
            if (targetWidth) {
                bar.style.width = targetWidth;
            }
        }, 300); // ページの描画が落ち着いた頃に開始 (0.3秒後)
    });

    // 2. 資格リストのアニメーション (各アイテムを順番に表示)
    // 左から右へスライドしながらフェードイン
    const certItems = document.querySelectorAll('.certification-item');
    certItems.forEach((item, index) => {
        setTimeout(() => {
            item.classList.add('visible');
        }, 500 + (index * 150)); // プログレスバーより少し遅れて開始し、150msずつずらす
    });
});
