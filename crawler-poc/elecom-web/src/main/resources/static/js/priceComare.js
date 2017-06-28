$(document).ready(function() {


	var pno = $("#pno").val();
	var url = $("#ctx").val() + "/details?pno=" + pno;
	$.ajax({
		type : "GET",
		url : url,
		dataType : "json",
		success : function (data) {
			loadTable(data);
			console.log(data.product_no);
			loadBarChart(data);
		},
		error : function (XMLHttpRequest, textStatus, errorThrown) {
			console.log(errorThrown);
		}
	});
});

function loadTable(datas) {

	var table = '<table class="table table-bordered table-hover table-striped">';
	table += '<thead>';
	table += '<tr>';
	table += '<th>型番</th>';
	table += '<th>店舗</th>';
	table += '<th>販売価格</th';
	table += '</tr>';
	table += '</thead>';
	table += '<tbody>';
	var cnt = datas.length;
	for(var i = 0; i < cnt; i++) {
		var data = datas[i];
		table += '  <tr>';
		if (i == 0) {
			table += '    <td rowspan="' + cnt +'">' + data.product_no + '</td>';
		} 
		table += '    <td>' + data.shop_name + '</td>';
		if (data.product_link_url != null) {
			table += '    <td><a target="_blank" href="'+ data.product_link_url +'"><span style="color:red">$' + $.trim(data.product_price) + '</span></a></td>';
		} else {
			table += '    <td><a href="#"><span style="color:red">$' + $.trim(data.product_price) + '</span></a></td>';
		}
		table += '  </tr>';
	}
	table += '</tbody>';
	table += '</table>';
	
	$(".table-responsive").html(table);
}

function loadBarChart(datas) {
	var title = "型番：";
	var categories = new Array();
	var prices = new Array();
	var cnt = datas.length;
	for(var i = 0; i < cnt; i++) {
		var data = datas[i];

		if (i == 0) {
			title += data.product_no;
		} 
		categories.push(data.shop_name);
		prices.push(parseFloat(data.product_price));
	}
	
	   var chart = {
	      type: 'column'
	   };
	   var title = {
	      text: title
	   };
	   var subtitle = {
	      text: ''
	   };
	   var xAxis = {
	      categories: categories,
	      crosshair: true
	   };
	   var yAxis = {
	      min: 0,
	      title: {
	         text: 'Price'
	      },
	      stackLabels: {
	        enabled: true
	      }
	   };

	   var plotOptions = {
	      column: {
	         pointPadding: 0.3,
	         borderWidth: 0
	      },
	        series: {
	            borderWidth: 0,
	            dataLabels: {
	                enabled: true,
	                format: '<span style="color:red">{point.y:.2f}</span>'
	            }
	        }
	   };

	   var credits = {
	      enabled: false
	   };

	   var series = [{
	      name: $.format.date(new Date, "実施日： yyyy/MM/dd"),
	      data: prices
	   }];

	   var json = {};
	   json.chart = chart;
	   json.title = title;
	   json.xAxis = xAxis;
	   json.yAxis = yAxis;
	   json.series = series;
	   json.plotOptions = plotOptions;
	   json.credits = credits;
	   $('#morris-bar-chart').highcharts(json);
}

function download() {
	var pno = $("#pno").val();
	var url = $("#ctx").val() + "/download?pno=" + pno;
	window.open(url);
}