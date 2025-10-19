const Validate = {
  StringValidate(name) {
	if(!name) throw new Error(`Only String... 유효 파라미터값이 아닙니다. -> name = ${typeof name}`);
	return true;
  },
  ArrayValidate(name) {
	if(!Array.isArray(name)) throw new Error(`Only Array... 유효 파라미터값이 아닙니다. -> name = ${typeof name}`);
	return true;
  },
  HTMLValidate(HTMLElement) {
	if(!HTMLElement instanceof HTMLElement) throw new TypeError(`Only HTMLElement... 유효 파라미터값이 아닙니다. -> parameter : ${HTMLElement}`);
	return true;
  },
  ObjectValidate(object) {
	if(typeof object !== 'object') throw new TypeError(`Only Object... 유효 파라미터값이 아닙니다. -> parameter : ${object}`);
	return true;
  }
  
}

function gettersId(name) {
  Validate.StringValidate(name);
  return document.getElementById(name);
}

function gettersClass(name, i = 0) {
  Validate.StringValidate(name);
  return document.getElementsByClassName(name)[i];
}

function gettersClassAll(name) {
  Validate.StringValidate(name);
  return document.querySelectorAll(`[class=${name}]`);
}

function valueReset(name) {
  Validate.StringValidate(name);
  if(Array.isArray(name)) {
	name.forEach(target => {
	  gettersId(target).value = '';
	});
  } else {
	gettersId(name).value = '';
  }
}

function postRequestToTEXT(url, body) {
  Validate.StringValidate(url);
  Validate.ObjectValidate(body);
  const token = localStorage.getItem('accessToken');
  fetch(url, {
	method: 'post',
	headers: {
	  'Autorization': `Bearer ${token}`,
	  'Content-Type': 'application/json'
	},
	body: JSON.stringify(body)
  })
  .catch(error => console.log(error))
  .then(response => response.text())
  .then(data => {
	console.log(data);
  });
}

function postRequestToJSON(url, body) {
  Validate.StringValidate(url);
  Validate.ObjectValidate(body);
  const token = localStorage.getItem('accessToken');
  console.log(token);
  fetch(url, {
	method: 'post',
	headers: {
	  'Content-Type': 'application/json',
	  'Authorization': `Bearer ${token}`
	},
	body: JSON.stringify(body)
  })
  .catch(error => console.log(error))
  .then(response => response.json())
  .then(data => {
	tokenRequest(data);
	window.location.href = '/finance';
  });
}

function tokenRequest(token) {
  const accessToken = token.token;
  localStorage.setItem('accessToken', accessToken);
  console.log('토큰 저장 완료', accessToken);
}

class ComponentManager {
  constructor() {
	this.components = {};
  }
  setComponent(name, component) {
	Validate.StringValidate(name);
	Validate.HTMLValidate(component);
	this.components[name] = component;
  }
  getComponent(name, data) {
	Validate.StringValidate(name);
	Validate.ObjectValidate(data);
	const component = this.components[name];
	Validate.HTMLValidate(component);
	return component(data);
  }
}