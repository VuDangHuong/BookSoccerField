// Hàm xử lý login
async function handleLogin(event) {
  event.preventDefault();

  const email = document.getElementById('email').value;
  const password = document.getElementById('password').value;

  const requestBody = { email, password };

  try {
    const response = await fetch('http://localhost:8080/api/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(requestBody)
    });

    if (response.ok) {
      const data = await response.json();
      localStorage.setItem('token', data.token);

      // ✅ Gọi API /me để lấy thông tin người dùng
      const userInfoRes = await fetch('http://localhost:8080/api/users/me', {
        headers: {
          'Authorization': `Bearer ${data.token}`
        }
      });

      if (!userInfoRes.ok) {
        alert('Không lấy được thông tin người dùng!');
        return;
      }

      const user = await userInfoRes.json();

      alert(`Đăng nhập thành công! Xin chào ${user.name}`);

      // ✅ Điều hướng theo vai trò
      if (user.role === 'ADMIN') {
        window.location.href = '/admin/dashboard.html';
      } else {
        window.location.href = 'index.html'; // Trang chính của người dùng thường
      }

    } else {
      const errorData = await response.json();
      alert('Đăng nhập thất bại: ' + (errorData.message || 'Sai thông tin'));
    }
  } catch (error) {
    console.error('Login Error:', error);
    alert('Lỗi hệ thống. Vui lòng thử lại.');
  }
}


// Hàm xử lý register (nếu cần dùng sau này)
async function handleRegister(event) {
  event.preventDefault();

  const name = document.getElementById('name').value.trim();
  const email = document.getElementById('email').value.trim();
  const phone = document.getElementById('phone').value.trim();
  const password = document.getElementById('password').value;
  const repeatPassword = document.getElementById('repeatPassword').value;

  if (password !== repeatPassword) {
    alert('Passwords do not match!');
    return;
  }

  const requestBody = { name, email, phone, password };

  try {
    const response = await fetch('http://localhost:8080/api/auth/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(requestBody)
    });

    if (response.ok) {
      const data = await response.json();
      alert('Register success! Now you can login.');
    } else {
      try {
        const errorData = await response.json();
        alert('Register failed: ' + (errorData.message || 'Something went wrong.'));
      } catch (e) {
        alert('Register failed: Server returned no content');
      }
    }
  } catch (error) {
    console.error('Register Error:', error);
    alert('Something went wrong. Please try again.');
  }
}

// Gắn sự kiện cho form khi DOM đã load
document.addEventListener("DOMContentLoaded", function () {
  const loginForm = document.getElementById('loginForm');
  if (loginForm) {
    loginForm.addEventListener('submit', handleLogin);
  }

  const registerForm = document.getElementById('registerForm');
  if (registerForm) {
    registerForm.addEventListener('submit', handleRegister);
  }
});
