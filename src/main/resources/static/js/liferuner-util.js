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

function userLogout() {
	if(confirm('정말 로그아웃 하시겠습니까?')) {
    	localStorage.removeItem('liferuner_jwt_token');
    	alert('로그아웃 되었습니다.');
    window.location.href = '/';
} else {
	return;
	}
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

const LifeAPI = {
	postApiRequest(url, body, callback) {
		const token = localStorage.getItem('liferuner_jwt_token');
	
	fetch(url, {
		method: 'post',
		headers: {
	    	'Content-Type':'application/json',
	    	'Authorization':`Bearer ${token}`
		},
		body: JSON.stringify(body)
	})
	.then(response => response.json())
	.then(data => {
		callback(data);
	})
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

function headerComponentChange() {
	const token = localStorage.getItem('liferuner_jwt_token');
	const header = gettersClass('main-header');
	header.innerHTML = '';

	let tokenValid = tokenTimeValid(token);

	if(tokenValid) {
		localStorage.removeItem('liferuner_jwt_token');	
		header.appendChild(componentFactory.getComponent('로그인-이전-헤더', {}));
	} else {
		header.appendChild(componentFactory.getComponent('로그인-이후-헤더', {}));
	}
}