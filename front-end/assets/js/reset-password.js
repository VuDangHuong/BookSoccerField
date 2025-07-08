document.getElementById("resetForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const token = new URLSearchParams(window.location.search).get("token");
  const newPassword = document.getElementById("newPassword").value;
  const messageEl = document.getElementById("message");

  if (!token) {
    messageEl.textContent = "Token không hợp lệ hoặc đã hết hạn.";
    return;
  }

  try {
    const response = await fetch("http://localhost:8080/api/auth/reset-password", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ token, newPassword })
    });

    const text = await response.text();
    if (response.ok) {
      messageEl.textContent = text || "Đặt lại mật khẩu thành công!";
    } else {
      messageEl.textContent = text || "Có lỗi xảy ra!";
    }
  } catch (error) {
    messageEl.textContent = "Không thể kết nối tới server!";
  }
});
