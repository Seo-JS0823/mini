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