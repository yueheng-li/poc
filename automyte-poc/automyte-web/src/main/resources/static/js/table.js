$(document).ready(function() {

	var url = $("#ctx").val() + "/tables";
	$('#example').DataTable( {
        "ajax": url,
        "columns": [
            { "data": "id" },
            { "data": "category_name" },
            { "data": "date" },
            { "data": "amount" },
            { "data": "image_name" },
            { "data": "status" },
            { "data": "location" }
        ],
        "columnDefs": [
            {
                "targets": [ 0 ],
                "visible": false,
                "searchable": false
            }
        ]
    } );
    

	var compareUrl = $("#ctx").val() + "/detail?id=";
    $('#example tbody').on( 'click', 'tr', function () {
    	var table = $('#example').DataTable();
        if (table) {
        	var data = table.row(this._DT_RowIndex).data();
        	if (data) {
                window.location.href = compareUrl+data.id;
        	}
        }
        
    } );
} );


function download() {
	var pno = $("#pno").val();
	var url = $("#ctx").val() + "/download?pno=" + pno;
	window.open(url);
}