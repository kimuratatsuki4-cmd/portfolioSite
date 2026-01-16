document.addEventListener("DOMContentLoaded", function () {
  const commentForm = document.getElementById("commentForm");
  const commentList = document.getElementById("commentList");
  // Finds the first heading in commentList (Recent Comments) so we can insert after it.
  // However, looking at the HTML structure:
  // <div id="commentList">
  //     <h5 ...>Recent Comments</h5>
  //     <div ... class="comment-item">...</div>
  // </div>
  // We want to insert after the h5.

  if (commentForm) {
    commentForm.addEventListener("submit", function (event) {
      event.preventDefault();

      const nameInput = document.getElementById("commentName");
      const contentInput = document.getElementById("commentContent");

      const name = nameInput.value;
      const content = contentInput.value;

      if (!content) {
        alert("コメントを入力してください。");
        return;
      }

      const payload = {
        name: name,
        content: content,
      };

      fetch("/api/comments", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      })
        .then((response) => {
          if (response.ok) {
            return response.json();
          } else {
            throw new Error("Something went wrong");
          }
        })
        .then((data) => {
          // Clear inputs
          nameInput.value = "";
          contentInput.value = "";

          // Add new comment to DOM
          addCommentToDom(data);
        })
        .catch((error) => {
          console.error("Error:", error);
          alert("コメントの送信に失敗しました。");
        });
    });
  }

  function addCommentToDom(comment) {
    const commentDiv = document.createElement("div");
    commentDiv.className = "d-flex gap-3 mb-4 comment_item";
    // Add minimal animation
    commentDiv.style.animation = "fadeIn 0.5s ease-out";

    // Format date: simple implementation to match server format roughly
    // We assume local time for simplicity, or parse the server time string if returned.
    // Server returns "createdAt" as "2026-01-06T19:30:00" etc.
    const dateObj = new Date(comment.createdAt);
    const formattedDate =
      dateObj.getFullYear() +
      "-" +
      String(dateObj.getMonth() + 1).padStart(2, "0") +
      "-" +
      String(dateObj.getDate()).padStart(2, "0") +
      " " +
      String(dateObj.getHours()).padStart(2, "0") +
      ":" +
      String(dateObj.getMinutes()).padStart(2, "0");

    commentDiv.innerHTML = `
            <div class="flex-shrink-0">
                <div class="rounded-circle bg-secondary bg-opacity-10 d-flex align-items-center justify-content-center"
                    style="width: 40px; height: 40px;">
                    <i class="fas fa-user text-secondary"></i>
                </div>
            </div>
            <div>
                <h6 class="fw-bold mb-1">
                    <span>${escapeHtml(comment.name)}</span>
                    <small class="text-muted ms-2 fw-normal" style="font-size: 0.8rem;">${formattedDate}</small>
                </h6>
                <p class="text-muted small mb-0">${escapeHtml(
                  comment.content
                )}</p>
            </div>
        `;

    // Insert after the header.
    // The structure is #commentList > h5, then comments.
    // We want to insert after the last h5, or as the first 'div.comment_item'.
    const header = commentList.querySelector("h5");
    if (header && header.nextSibling) {
      commentList.insertBefore(commentDiv, header.nextSibling);
    } else {
      commentList.appendChild(commentDiv);
    }
  }

  function escapeHtml(text) {
    if (!text) return text;
    return text
      .replace(/&/g, "&amp;")
      .replace(/</g, "&lt;")
      .replace(/>/g, "&gt;")
      .replace(/"/g, "&quot;")
      .replace(/'/g, "&#039;");
  }
});

// Add keyframes for fadeIn if not exists (programmatically) or just rely on CSS if available
// I will add a simple style block to document head just in case
const style = document.createElement("style");
style.innerHTML = `
@keyframes fadeIn {
    from { opacity: 0; transform: translateY(-10px); }
    to { opacity: 1; transform: translateY(0); }
}
`;
document.head.appendChild(style);
