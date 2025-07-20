async function loadUsers() {
  const token = localStorage.getItem("token");
  if (!token) {
    alert("Bạn chưa đăng nhập!");
    return;
  }

  try {
    const res = await fetch("http://localhost:8080/api/admin/users", {
      headers: { Authorization: `Bearer ${token}` }
    });

    if (!res.ok) {
      const errorText = await res.text();
      alert("Lỗi khi gọi API: " + res.status + "\n" + errorText);
      return;
    }

    const users = await res.json();
    const tbody = document.getElementById("userTableBody");

    if (!Array.isArray(users)) {
      alert("Dữ liệu trả về không đúng định dạng!");
      return;
    }

    tbody.innerHTML = users.map(user => `
      <tr>
        <td>${user.id}</td>
        <td>${user.name}</td>
        <td>${user.email}</td>
        <td>${user.role}</td>
        <td>${user.enabled ? "✅" : "🚫"}</td>
        <td>
          <button class="btn btn-sm btn-warning" onclick='showEditUserModal(${JSON.stringify(user)})'>Sửa</button>
          <button class="btn btn-sm btn-danger">Xoá</button>
        </td>
      </tr>
    `).join("");
  } catch (err) {
    console.error(err);
    alert("Không thể kết nối đến server.");
  }
} // <-- Dấu } này rất quan trọng!

// Khai báo sau khi đã kết thúc hàm loadUsers
// const API_URL = "http://localhost:8080/api/admin/users";
// const token = localStorage.getItem("token");

  function showAddUserModal() {
    document.getElementById("userModalTitle").innerText = "Thêm người dùng";
    document.getElementById("userId").value = "";
    document.getElementById("name").value = "";
    document.getElementById("email").value = "";
    document.getElementById("role").value = "USER";
    document.getElementById("password").value = "";
    new bootstrap.Modal(document.getElementById("userModal")).show();
  }

  function showEditUserModal(user) {
    document.getElementById("userModalTitle").innerText = "Cập nhật người dùng";
    document.getElementById("userId").value = user.id;
    document.getElementById("name").value = user.name;
    document.getElementById("email").value = user.email;
    document.getElementById("role").value = user.role;
    document.getElementById("password").value = ""; // Mật khẩu mới nếu muốn cập nhật
    new bootstrap.Modal(document.getElementById("userModal")).show();
  }

  async function saveUser() {
    const token = localStorage.getItem("token");
    const id = document.getElementById("userId").value;
    const user = {
      name: document.getElementById("name").value,
      email: document.getElementById("email").value,
      role: document.getElementById("role").value,
      password: document.getElementById("password").value
    };

    const method = id ? "PUT" : "POST";
    const url = id ? `${API_URL}/${id}` : API_URL;

    try {
      const res = await fetch(url, {
        method,
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`
        },
        body: JSON.stringify(user)
      });

      if (!res.ok) {
        const err = await res.text();
        alert("Lỗi: " + res.status + "\n" + err);
        return;
      }

      bootstrap.Modal.getInstance(document.getElementById("userModal")).hide();
      await loadUsers(); // Tải lại danh sách
    } catch (err) {
      alert("Lỗi kết nối!");
    }
  }
loadUsers();
