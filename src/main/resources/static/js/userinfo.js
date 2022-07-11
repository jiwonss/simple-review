function deleteUserClick() {
	let result = confirm("계정을 삭제하시겠습니까?");

	if (result == true) {
		let form = document.querySelector(".delete-form")
        form.submit();
	}
}