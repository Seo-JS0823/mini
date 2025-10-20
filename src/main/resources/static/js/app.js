let isLoggedIn = false;

function checkLoginStatus() {
    const token = localStorage.getItem('liferuner_jwt_token');
    if (token && !tokenTimeValid(token)) {
        isLoggedIn = true;
    } else {
        isLoggedIn = false;
        localStorage.removeItem('liferuner_jwt_token');
    }
}

function tokenTimeValid(token) {
    if(!token) return true;
    
    try {
        const payloadBase64 = token.split('.')[1];
        const decodedPayload = JSON.parse(atob(payloadBase64));
        const expTime = decodedPayload.exp;
        const curTime = Math.floor(Date.now() / 1000);
        return expTime < curTime;
    } catch(e) {
        console.error('토큰 디코딩 실패');
        return true;
    }
}

function renderHeader() {
    const headerRight = document.getElementById('headerRight');
    if (!headerRight) return;
    
    headerRight.innerHTML = '';
    
    checkLoginStatus();
    
    if (isLoggedIn) {
        headerRight.innerHTML = `
            <button class="btn-icon-only">
                <svg class="btn-icon" viewBox="0 0 24 24" style="stroke: var(--gray-700);">
                    <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"></path>
                    <path d="M13.73 21a2 2 0 0 1-3.46 0"></path>
                </svg>
            </button>
            <button class="btn btn-primary">
                <svg class="btn-icon" viewBox="0 0 24 24" style="stroke: white;">
                    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                    <circle cx="12" cy="7" r="4"></circle>
                </svg>
                <span class="btn-text">마이페이지</span>
            </button>
            <button class="btn btn-danger" onclick="handleLogout()">
                <svg class="btn-icon" viewBox="0 0 24 24" style="stroke: white;">
                    <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
                    <polyline points="16 17 21 12 16 7"></polyline>
                    <line x1="21" y1="12" x2="9" y2="12"></line>
                </svg>
                <span class="btn-text">로그아웃</span>
            </button>
        `;
    } else {
        headerRight.innerHTML = `
            <button class="btn btn-primary" onclick="openLoginModal()">로그인</button>
            <button class="btn btn-secondary" onclick="openJoinModal()">회원가입</button>
        `;
    }
}

function renderMainContent() {
    const mainContent = document.getElementById('mainContent');
    if (!mainContent) return;
    
    mainContent.innerHTML = '';
    
    checkLoginStatus();
    
    if (isLoggedIn) {
        mainContent.innerHTML = `
            <div class="dashboard-header">
                <h1 class="dashboard-title">대시보드</h1>
                <p class="dashboard-subtitle">오늘도 목표를 향해 달려가세요!</p>
            </div>

            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-header">
                        <div class="stat-icon-wrapper blue">
                            <svg class="stat-icon blue" viewBox="0 0 24 24">
                                <line x1="12" y1="1" x2="12" y2="23"></line>
                                <path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"></path>
                            </svg>
                        </div>
                        <span class="stat-label">총 자산</span>
                    </div>
                    <p class="stat-value">₩2,450,000</p>
                    <p class="stat-change positive">+12.5% 이번 달</p>
                </div>

                <div class="stat-card">
                    <div class="stat-header">
                        <div class="stat-icon-wrapper green">
                            <svg class="stat-icon green" viewBox="0 0 24 24">
                                <polyline points="23 6 13.5 15.5 8.5 10.5 1 18"></polyline>
                                <polyline points="17 6 23 6 23 12"></polyline>
                            </svg>
                        </div>
                        <span class="stat-label">이번 달 수입</span>
                    </div>
                    <p class="stat-value">₩3,200,000</p>
                    <p class="stat-change neutral">목표 달성률 85%</p>
                </div>

                <div class="stat-card">
                    <div class="stat-header">
                        <div class="stat-icon-wrapper orange">
                            <svg class="stat-icon orange" viewBox="0 0 24 24">
                                <rect x="1" y="4" width="22" height="16" rx="2" ry="2"></rect>
                                <line x1="1" y1="10" x2="23" y2="10"></line>
                            </svg>
                        </div>
                        <span class="stat-label">이번 달 지출</span>
                    </div>
                    <p class="stat-value">₩1,850,000</p>
                    <p class="stat-change negative">예산 대비 92%</p>
                </div>

                <div class="stat-card">
                    <div class="stat-header">
                        <div class="stat-icon-wrapper purple">
                            <svg class="stat-icon purple" viewBox="0 0 24 24">
                                <circle cx="12" cy="12" r="10"></circle>
                                <path d="M16.2 7.8l-2 6.3-6.4 2.1 2-6.3z"></path>
                            </svg>
                        </div>
                        <span class="stat-label">목표 달성</span>
                    </div>
                    <p class="stat-value">7 / 10</p>
                    <p class="stat-change neutral">이번 달 목표</p>
                </div>
            </div>

            <div class="content-grid">
                <div class="card">
                    <h3 class="card-title">
                        <svg class="card-title-icon" viewBox="0 0 24 24">
                            <line x1="18" y1="20" x2="18" y2="10"></line>
                            <line x1="12" y1="20" x2="12" y2="4"></line>
                            <line x1="6" y1="20" x2="6" y2="14"></line>
                        </svg>
                        최근 지출 내역
                    </h3>
                    <div class="expense-list">
                        <div>
                            <div class="expense-item-header">
                                <span class="expense-name">식비</span>
                                <span class="expense-amount">₩450,000</span>
                            </div>
                            <div class="progress-bar">
                                <div class="progress-fill blue" style="width: 60%"></div>
                            </div>
                        </div>
                        <div>
                            <div class="expense-item-header">
                                <span class="expense-name">교통비</span>
                                <span class="expense-amount">₩180,000</span>
                            </div>
                            <div class="progress-bar">
                                <div class="progress-fill green" style="width: 40%"></div>
                            </div>
                        </div>
                        <div>
                            <div class="expense-item-header">
                                <span class="expense-name">쇼핑</span>
                                <span class="expense-amount">₩320,000</span>
                            </div>
                            <div class="progress-bar">
                                <div class="progress-fill purple" style="width: 50%"></div>
                            </div>
                        </div>
                        <div>
                            <div class="expense-item-header">
                                <span class="expense-name">기타</span>
                                <span class="expense-amount">₩120,000</span>
                            </div>
                            <div class="progress-bar">
                                <div class="progress-fill orange" style="width: 25%"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <h3 class="card-title">
                        <svg class="card-title-icon" viewBox="0 0 24 24">
                            <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
                            <line x1="16" y1="2" x2="16" y2="6"></line>
                            <line x1="8" y1="2" x2="8" y2="6"></line>
                            <line x1="3" y1="10" x2="21" y2="10"></line>
                        </svg>
                        다가오는 일정
                    </h3>
                    <div class="schedule-list">
                        <div class="schedule-item blue">
                            <div class="schedule-content">
                                <div>
                                    <p class="schedule-title">프로젝트 미팅</p>
                                    <p class="schedule-date">2025-10-21</p>
                                </div>
                                <span class="schedule-time">14:00</span>
                            </div>
                        </div>
                        <div class="schedule-item green">
                            <div class="schedule-content">
                                <div>
                                    <p class="schedule-title">건강검진</p>
                                    <p class="schedule-date">2025-10-22</p>
                                </div>
                                <span class="schedule-time">10:00</span>
                            </div>
                        </div>
                        <div class="schedule-item purple">
                            <div class="schedule-content">
                                <div>
                                    <p class="schedule-title">친구 약속</p>
                                    <p class="schedule-date">2025-10-23</p>
                                </div>
                                <span class="schedule-time">19:00</span>
                            </div>
                        </div>
                        <div class="schedule-item orange">
                            <div class="schedule-content">
                                <div>
                                    <p class="schedule-title">월급날</p>
                                    <p class="schedule-date">2025-10-25</p>
                                </div>
                                <span class="schedule-time">00:00</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card">
                <h3 class="card-title">
                    <svg class="card-title-icon" viewBox="0 0 24 24">
                        <circle cx="12" cy="12" r="10"></circle>
                        <path d="M16.2 7.8l-2 6.3-6.4 2.1 2-6.3z"></path>
                    </svg>
                    이번 달 목표
                </h3>
                <div class="goals-grid">
                    <div class="goal-card">
                        <p class="goal-title">저축 100만원</p>
                        <div class="goal-progress">
                            <div class="progress-bar">
                                <div class="progress-fill blue" style="width: 75%"></div>
                            </div>
                        </div>
                        <div class="goal-stats">
                            <span class="goal-current">75만원</span>
                            <span class="goal-percent">75%</span>
                        </div>
                    </div>
                    <div class="goal-card">
                        <p class="goal-title">운동 20회</p>
                        <div class="goal-progress">
                            <div class="progress-bar">
                                <div class="progress-fill green" style="width: 60%"></div>
                            </div>
                        </div>
                        <div class="goal-stats">
                            <span class="goal-current">12회</span>
                            <span class="goal-percent">60%</span>
                        </div>
                    </div>
                    <div class="goal-card">
                        <p class="goal-title">독서 5권</p>
                        <div class="goal-progress">
                            <div class="progress-bar">
                                <div class="progress-fill purple" style="width: 80%"></div>
                            </div>
                        </div>
                        <div class="goal-stats">
                            <span class="goal-current">4권</span>
                            <span class="goal-percent">80%</span>
                        </div>
                    </div>
                </div>
            </div>
        `;
    } else {
        mainContent.innerHTML = `
            <div class="welcome-section">
                <div class="welcome-icon">
                    <svg viewBox="0 0 24 24">
                        <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                        <circle cx="12" cy="7" r="4"></circle>
                    </svg>
                </div>
                <h2 class="welcome-title">Life Runer에 오신 것을 환영합니다</h2>
                <p class="welcome-subtitle">당신의 삶을 더 나은 방향으로 이끌어드립니다</p>
                <div class="welcome-buttons">
                    <button class="btn btn-primary" onclick="openLoginModal()">로그인</button>
                    <button class="btn btn-secondary" onclick="openJoinModal()">회원가입</button>
                </div>
            </div>
        `;
    }
}

function openLoginModal() {
    document.getElementById('loginModal').classList.add('active');
}

function closeLoginModal() {
    document.getElementById('loginModal').classList.remove('active');
}

function openJoinModal() {
    document.getElementById('joinModal').classList.add('active');
}

function closeJoinModal() {
    document.getElementById('joinModal').classList.remove('active');
}

function switchToJoin() {
    closeLoginModal();
    openJoinModal();
}

function switchToLogin() {
    closeJoinModal();
    openLoginModal();
}

function handleLogin(event) {
    if (event) event.preventDefault();
    
    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;
    
    if (!username || !password) {
        alert('아이디와 비밀번호를 입력하세요.');
        return;
    }
    
    fetch('/api/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: username,
            password: password
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('로그인 실패');
        }
        return response.json();
    })
    .then(data => {
        localStorage.setItem('liferuner_jwt_token', data.token);
        isLoggedIn = true;
        closeLoginModal();
        renderHeader();
        renderMainContent();
        document.getElementById('loginUsername').value = '';
        document.getElementById('loginPassword').value = '';
        alert('로그인 성공!');
    })
    .catch(error => {
        alert('로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.');
        console.error('Error:', error);
    });
}

function handleJoin(event) {
    if (event) event.preventDefault();
    
    const name = document.getElementById('joinName').value;
    const username = document.getElementById('joinUsername').value;
    const password = document.getElementById('joinPassword').value;
    const birthday = document.getElementById('joinBirthday').value;
    const email = document.getElementById('joinEmail').value;
    const gender = document.querySelector('input[name="gender"]:checked').value;
    const address = document.getElementById('joinAddress').value;
    
    if (!name || !username || !password || !birthday || !email || !address) {
        alert('모든 필드를 입력해주세요.');
        return;
    }
    
    fetch('/join', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: name,
            username: username,
            password: password,
            birthday: birthday,
            email: email,
            gender: gender,
            address: address
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('회원가입 실패');
        }
        return response.text();
    })
    .then(data => {
        alert('회원가입 성공!');
        closeJoinModal();
        openLoginModal();
        document.getElementById('joinName').value = '';
        document.getElementById('joinUsername').value = '';
        document.getElementById('joinPassword').value = '';
        document.getElementById('joinBirthday').value = '';
        document.getElementById('joinEmail').value = '';
        document.getElementById('joinAddress').value = '';
    })
    .catch(error => {
        alert('회원가입에 실패했습니다. 다시 시도해주세요.');
        console.error('Error:', error);
    });
}

function handleLogout() {
    if (confirm('로그아웃 하시겠습니까?')) {
        localStorage.removeItem('liferuner_jwt_token');
        isLoggedIn = false;
        renderHeader();
        renderMainContent();
        alert('로그아웃 되었습니다.');
    }
}

document.addEventListener('DOMContentLoaded', function() {
    const menuBtn = document.getElementById('menuBtn');
    const sidebar = document.getElementById('sidebar');
    const sidebarOverlay = document.getElementById('sidebarOverlay');
    const menuIcon = document.getElementById('menuIcon');
    const closeIcon = document.getElementById('closeIcon');

    if (menuBtn && sidebar && sidebarOverlay) {
        menuBtn.addEventListener('click', () => {
            sidebar.classList.toggle('active');
            sidebarOverlay.classList.toggle('active');
            
            if (sidebar.classList.contains('active')) {
                menuIcon.style.display = 'none';
                closeIcon.style.display = 'block';
            } else {
                menuIcon.style.display = 'block';
                closeIcon.style.display = 'none';
            }
        });

        sidebarOverlay.addEventListener('click', () => {
            sidebar.classList.remove('active');
            sidebarOverlay.classList.remove('active');
            menuIcon.style.display = 'block';
            closeIcon.style.display = 'none';
        });
    }

    renderHeader();
    renderMainContent();
});

function handleGoogleLogin() {
    alert('구글 OAuth 로그인 기능은 백엔드 설정이 필요합니다.\n\nSpring Security OAuth2 설정:\n1. Google Cloud Console에서 OAuth 클라이언트 ID 생성\n2. application.yml에 client-id, client-secret 추가\n3. /oauth2/authorization/google 엔드포인트 연결');
    window.location.href = '/oauth2/authorization/google';
}

function handleNaverLogin() {
    alert('네이버 OAuth 로그인 기능은 백엔드 설정이 필요합니다.\n\nSpring Security OAuth2 설정:\n1. 네이버 개발자센터에서 애플리케이션 등록\n2. application.yml에 client-id, client-secret 추가\n3. /oauth2/authorization/naver 엔드포인트 연결');
    window.location.href = '/oauth2/authorization/naver';
}

function handleKakaoLogin() {
    alert('카카오 OAuth 로그인 기능은 백엔드 설정이 필요합니다.\n\nSpring Security OAuth2 설정:\n1. 카카오 개발자센터에서 애플리케이션 등록\n2. application.yml에 client-id, client-secret 추가\n3. /oauth2/authorization/kakao 엔드포인트 연결');
    window.location.href = '/oauth2/authorization/kakao';
}
