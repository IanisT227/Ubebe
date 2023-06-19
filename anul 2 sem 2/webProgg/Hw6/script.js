let table_columns = [];
(() => {
	// table_columns = {column text, order}
	$("#mytable")
		.find("thead")
		.find("tr")
		.children()
		.each((_index, elem) => {
			$(elem).on("click", clickedHead);
			table_columns.push({ text: $(elem).html(), order: "" });
		});
		++
	$("#mytable")
		.find("tfoot")
		.find("tr")
		.children()
		.each((_index, elem) => {
			$(elem).on("click", clickedFooter);
		});
})();

function clickedHead(event) {
	column_index = table_columns.findIndex((elem) => elem.text == event.target.innerHTML);
	table_columns[column_index].order = table_columns[column_index].order === "asc" ? "desc" : "asc";
	sortTable(column_index, table_columns[column_index].order);
}

function clickedFooter(event) {
	column_index = $(event.target).index();
	switchColumns(column_index);
}

function sortTable(column_index, order) {
	let table, rows, switching, i, x, y;
	table = $("#mytable").find("tbody");
	switching = true;
	// bubble sort
	while (switching) {
		switching = false;
		rows = $(table).find("tr");
		for (i = 0; i < rows.length - 1; i++) {
			x = $(rows[i]).find("td")[column_index];
			y = $(rows[i + 1]).find("td")[column_index];
			if (
				(order === "asc" && $(x).html().toLowerCase() > $(y).html().toLowerCase()) ||
				(order === "desc" && $(x).html().toLowerCase() < $(y).html().toLowerCase())
			) {
				// switch rows
				$(rows[i + 1]).after($(rows[i]));
				switching = true;
			}
		}
	}
}

function switchColumns(column_index) {
	let desired_position = column_index === table_columns.length - 1 ? table_columns.length - 2 : column_index + 1;
	if (desired_position > column_index) {
		[desired_position, column_index] = [column_index, desired_position];
	}

	$("#mytable")
		.find("tr")
		.each((_index, elem) => {
			$(elem).children("th, td").eq(column_index).detach().insertBefore($(elem).children("th, td").eq(desired_position));
		});

	[table_columns[column_index], table_columns[desired_position]] = [table_columns[desired_position], table_columns[column_index]];
}
