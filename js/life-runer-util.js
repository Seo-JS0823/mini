function gettersId(name) {
  nameValid(name);
  let tag = document.getElementById(name);
  tagValid(tag);
  return tag;
}
function gettersClassAll(name) {
  nameValid(name);
  let tag = document.getElementsByClassName(name);
  tagValid(tag);
  return tag;
}
function gettersClass(name) {
  nameValid(name);
  let tag = document.getElementsByClassName(name)[0];
  tagValid(tag);
  return tag;
}
function gettersClassChoice(name, number) {
  nameValid(name);
  let tag = document.getElementsByClassName(name)[number];
  tagValid(tag);
  return tag;
}

// 유효성 검사 함수들
function nameValid(name) {
  if(!name) {
    throw new Error('가져올 태그의 키값을 지정하세요.');
  }
}

function tagValid(tag) {
  if(!tag) {
    throw new Error('가져올 태그의 키값 또는 인덱스가 유효하지 않습니다.');
  }
}

// URL 보내는 함수들
const Urls = {
	url:{
		'home-href' : '/view/home',
		'acot-href' : '/view/acot',
		'life-href' : '/view/life',
    'search'    : '/search?search=',
    'user-profile-btn' : '/view/user'
	},
	listener(tag) {
		tagValid(tag);
		let id = tag.id;
		nameValid(tag);
		let url = this.url[id];
		
		tag.addEventListener('click', () => {
			let formEl = document.createElement('form');
			formEl.action = url;
			formEl.method = 'GET';
			
			document.body.appendChild(formEl);
			formEl.submit();
		})
	},
  search(tag) {
    tagValid(tag);
    let id = tag.id;
    nameValid(id);
    
    tag.addEventListener('keydown', (e) => {
      let search = tag.value;
      let url = this.url[id] + search;
      
      if(e.key === 'Enter') {
        console.log(url);
        /*
        fetch(url)
        .catch(error => console.log(error))
        .then(response => response.json())
        .then(data => {
          render(data);
        })
        */
      }
		})
  }
}