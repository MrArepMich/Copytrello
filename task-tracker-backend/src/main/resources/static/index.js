document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.querySelector('#login-form');
    const registerForm = document.querySelector('#register-form');

    loginForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const email = document.querySelector('#login-email').value;
        const password = document.querySelector('#login-password').value;

        fetch('http://localhost:8300/copytrello/api/v1/auth/authenticate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email, password })
        })
            .then(response => response.json())
            .then(data => {
                if (data.token) {
                    localStorage.setItem('token', data.token);
                    alert('Login successful!');
                } else {
                    alert('Login failed: ' + data.message);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert("Unexpected error occurred");
            });
    });

    registerForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const email = document.querySelector('#register-email').value;
        const password = document.querySelector('#register-password').value;
        const confirmPassword = document.querySelector('#register-confirm-password').value;

        if (password !== confirmPassword) {
            alert('Passwords do not match!');
            return;
        }

        fetch('http://localhost:8300/copytrello/api/v1/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email, password })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('Registration successful!');
                } else {
                    alert('Registration failed: ' + data.message);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert("Unexpected error occurred");
            });
    });
});
