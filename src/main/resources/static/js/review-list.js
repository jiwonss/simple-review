function editBtnClick(id) {
	const list = document.getElementById(`list${id}`);
	const title = list.querySelector("#title").innerText;
	const content = list.querySelector("div.content");
	const contentInput = content.querySelector("input");
	const contentSpan = content.querySelector("span");

	contentSpan.style.display = 'none';
	contentInput.style.display = 'block';

	const button = list.querySelector(".button");
	button.style.display = 'none';

	const buttonDiv = document.createElement("div");
	const checkBtn = document.createElement("button");
	const cancelBtn = document.createElement("button");
	checkBtn.innerHTML = "확인";
	cancelBtn.innerHTML = "취소";
	buttonDiv.className = "my-1";
	checkBtn.className = "btn btn-primary btn-sm";
	cancelBtn.className = "btn btn-primary btn-sm";

	buttonDiv.appendChild(checkBtn);
	buttonDiv.appendChild(cancelBtn);
	list.appendChild(buttonDiv);
	checkBtn.style.marginRight = '3px';

	checkBtn.addEventListener("click", checkBtnClick);

	function checkBtnClick() {
		fetch(`http://localhost:8080/api/review/edit`, {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
				id : `${id}`,
	            content : contentInput.value,
	            title : `${title}`,
	        }),
		})
			.then((data) => console.log(data));
		contentSpan.innerHTML = contentInput.value;
		contentInput.style.display = 'none';
		contentSpan.style.display = 'block';
		buttonDiv.remove();
        button.style.display = 'block';
	}

	cancelBtn.addEventListener("click", cancelBtnClick);

	function cancelBtnClick() {
		contentSpan.style.display = 'block';
        contentInput.style.display = 'none';
        buttonDiv.remove();
		button.style.display = 'block';
	}
}


function delBtnClick(id) {
	fetch(`http://localhost:8080/api/review/delete/${id}`, {
		method: "DELETE",
    })
		.then((data) => console.log(data));
	const list = document.getElementById(`list${id}`);
    list.remove();
}
