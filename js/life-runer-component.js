class ComponentManager {
  constructor() {
    this._components = {};
  }
  setComponent(name, component) {
    nameValid(name);
    if(!component instanceof HTMLElement)
      throw new TypeError(`컴포넌트 조각이 아닙니다. Argument : ${component}`)
    this._components[name] = component;
  }
  getComponent(name, json) {
    nameValid(name);
    jsonValid(json);
    let component = this._components[name];
    return component(json);
  }
}
const render = new ComponentManager();

render.setComponent('user-smsbox-headers', (data) => {
		let fragment = document.createDocumentFragment();
		let ulBox = document.createElement('ul');
		ulBox.classList.add('-col');
		ulBox.id = 'user-smsbox';
		let li_1 = document.createElement('li');
		let li_1_div = document.createElement('div');
		li_1_div.classList.add('main-navbar-user-sms');
		li_1_div.classList.add('-row');
		li_1_div.classList.add('-center');
		li_1_div.classList.add('-sms-e');
		let p = document.createElement('p');
		p.textContent = `${data.user.messageCount} notification`;
		
		li_1_div.appendChild(p);
		li_1.appendChild(li_1_div);
		ulBox.appendChild(li_1);
		
		let li_2 = document.createElement('li');
		let li_2_div = document.createElement('div');
		li_2_div.classList.add('main-navbar-user-sms');
		li_2_div.classList.add('-row');
		li_2_div.classList.add('-center');
		li_2_div.classList.add('-sms-e');
		let li_2_a = document.createElement('a');
		li_2_a.classList.add('-row');
		li_2_a.classList.add('-g1');
		li_2_a.href = `${data.user.href}`;
		li_2_a.innerHTML = `
		<span class="material-symbols-outlined">mail</span>
		<p>New Message</p>
		`;
		let li_2_p = document.createElement('p');
		li_2_p.classList.add('ft-15');
		li_2_p.textContent = `${data.user.lastTakeTime} mins`;
		li_2_a.appendChild(li_2_p);
		li_2_div.appendChild(li_2_a);
		li_2.appendChild(li_2_div);
		ulBox.appendChild(li_2);
		fragment.appendChild(ulBox);
		return fragment;
	})