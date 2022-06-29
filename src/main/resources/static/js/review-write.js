function saveBtn(title) {
	const review = document.querySelector(".content").value;
	fetch(`http://localhost:8080/api/review/add`, {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify({
			content : `${review}`,
			title : `${title}`,
        }),
	})
		.then((data) => console.log(data));
}