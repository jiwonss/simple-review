function sendAjax(url, clickedName) {
	var oReq = new XMLHttpRequest();
	oReq.addEventListener("load", function () {
	var content = document.querySelector(".content");
	content.removeChild(content.childNodes[0]);
	var div = document.createElement("div");
	div.innerHTML = oReq.responseText;
	content.appendChild(div);
	});
	oReq.open("GET", url);
	oReq.send();
}

var tabmenu = document.querySelector(".tab-menu");
tabmenu.addEventListener("click", function (evt) {
	var category = evt.target.innerText.split(" ")[0].toLowerCase();
	if (category == "user"){
		category += "info";
		sendAjax(`./${category}`, evt.target.innerText);
	} else {
		sendAjax(`./${category}/list`, evt.target.innerText);
	}
});
