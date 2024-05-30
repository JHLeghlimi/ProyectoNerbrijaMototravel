const filterInput = document.getElementById("filterInput");
const itemsList = document.getElementById("listRecommend");

filterInput.addEventListener("keyup", function () {
	const term = filterInput.value.toLowerCase();
	const items = itemsList.getElementsByTagName("li");

	Array.from(items).forEach(function (item) {
		if (item.textContent.toLowerCase().indexOf(term) !== -1) {
			item.style.display = "block";
		} else {
			item.style.display = "none";
		}
    });
});