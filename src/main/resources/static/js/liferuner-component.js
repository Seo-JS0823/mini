const componentFactory = new ComponentManager();

componentFactory.setComponent('l-d-header', () => {
  let header = document.createElement('div');
  header.classList.add('l-d-header');
  
  let text = document.createElement('div');
  text.textContent = 'Life Runer';
  
  let inputBox = document.createElement('div');
  let uid = document.createElement('input');
  uid.type = 'text';
  uid.id = 'UID';
  uid.setAttribute('placeholder', '아이디를 입력해 주세요.');
  
  let upw = document.createElement('input');
  upw.type = 'password';
  upw.id = 'UPW';
  upw.setAttribute('placeholder', '비밀번호를 입력해 주세요.');
  inputBox.appendChild(uid);
  inputBox.appendChild(upw);
  
  header.appendChild(text);
  header.appendChild(inputBox);
  return header;
});

componentFactory.setComponent('로그인-이후-헤더', () => {
  let frag = document.createDocumentFragment();
  let left = document.createElement('div');
  let lifeRuner = document.createElement('a');
  lifeRuner.href = '#';
  lifeRuner.textContent = 'Life Runer';
  
  let search = document.createElement('input');
  search.type = 'text';
  search.id = 'search';
  search.setAttribute('placeholder', 'search');
  
  left.appendChild(lifeRuner);
  left.appendChild(search);
  
  let right = document.createElement('div');
  let myPage = document.createElement('button');
  myPage.id = 'my-page';
  myPage.classList.add('life-btn');
  myPage.classList.add('-bg-lightgreen');
  myPage.textContent = '마이페이지';
  right.appendChild(myPage);
  
  let logout = document.createElement('button');
  logout.classList.add('life-btn');
  logout.classList.add('-bg-red');
  logout.textContent = '로그아웃';
  logout.addEventListener('click', () => { userLogout(); });
  right.appendChild(logout);
  
  frag.appendChild(left);
  frag.appendChild(right);
  return frag;
});

componentFactory.setComponent('로그인-이전-헤더', () => {
  let frag = document.createDocumentFragment();
  let left = document.createElement('div');
  let lifeRuner = document.createElement('a');
  lifeRuner.href = '#';
  lifeRuner.textContent = 'Life Runer';
  
  let search = document.createElement('input');
  search.type = 'text';
  search.id = 'search';
  search.setAttribute('placeholder', 'search');
  
  left.appendChild(lifeRuner);
  left.appendChild(search);
  
  let right = document.createElement('div');
  let login = document.createElement('button');
  login.id = 'login';
  login.classList.add('life-btn');
  login.classList.add('-bg-blue');
  login.textContent = '로그인';
  login.addEventListener('click', () => {
	let loginDialog = gettersClass('login-dialog');
	loginDialog.style.display = 'flex';
  });
  right.appendChild(login);
  
  frag.appendChild(left);
  frag.appendChild(right);
  return frag;
});