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
      alert('Login success!');
      localStorage.setItem('token', data.token);
      // window.location.href = "/dashboard.html"; // Chuyển hướng nếu muốn
    } else {
      const errorData = await response.json();
      alert('Login failed: ' + (errorData.message || 'Invalid credentials'));
    }
  } catch (error) {
    console.error('Login Error:', error);
    alert('Something went wrong. Please try again.');
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
