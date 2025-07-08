document.getElementById('forgotPasswordForm').addEventListener('submit', async function (e) {
    e.preventDefault();
    const email = document.getElementById('email').value;

    try {
        const response = await fetch('http://localhost:8080/api/auth/forgot-password', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email: email })
        });

        const data = await response.text();

        if (response.ok) {
            document.getElementById('message').innerText = data;
        } else {
            document.getElementById('message').innerText = "Lỗi: " + data;
        }
    } catch (error) {
        console.error('Lỗi gửi yêu cầu:', error);
        document.getElementById('message').innerText = "Đã xảy ra lỗi kết nối.";
    }
});
