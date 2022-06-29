function addRestaurantBtnClick(id) {
	fetch(`http://localhost:8080/api/restaurant/${id}`, {
		method: "PUT",
		headers: {
			"Content-Type": "application/json",
		},
	})
		.then((data) => console.log(data));
	const list = document.querySelector(`tr.list[id=list${id}]`);
	const span = list.querySelector(".add span");
	span.innerHTML = parseInt(span.innerHTML, 10) + 1;
	location.replace(location.href);
}

function delRestaurantBtnClick(id) {
	console.log(id);
	fetch(`http://localhost:8080/api/restaurant/delete/${id}`, {
		method: "DELETE",
    })
		.then((data) => console.log(data));
    const list = document.querySelector(`tr.list[id=${id}]`);
    list.remove();
	location.replace(location.href);
}


