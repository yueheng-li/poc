$(document).ready(function() {

	var url = $("#ctx").val() + "/tables";
	$('#example').DataTable({
		"ajax" : url,
		"columns" : [ {
			"data" : "id"
		}, {
			"data" : "category_name"
		}, {
			"data" : "date"
		}, {
			"data" : "amount"
		}, {
			"data" : "image_name"
		}, {
			"data" : "status"
		}, {
			"data" : "location"
		} ],
		"columnDefs" : [ {
			"targets" : [ 0 ],
			"visible" : false,
			"searchable" : false
		} ]
	});

	var compareUrl = $("#ctx").val() + "/detail?id=";
	$('#example tbody').on('click', 'tr', function() {
		var table = $('#example').DataTable();
		if (table) {
			var data = table.row(this._DT_RowIndex).data();
			if (data) {
				window.location.href = compareUrl + data.id;
			}
		}

	});

	$('#uploadmulti').on('click', function() {
		$("#messageE").text(""); 
		var uploadFlg = true;
		if (window.File && window.FileList) {
			var files = $('#multipleFiles')[0].files;
			console.log(files.length);
			var file;
			for (var i = 0; i < files.length; i++) {
				// file对象为用户选择的某一个文件
				file = files[i];
				// 此时取出这个文件进行处理，这里只是显示文件名
				console.log(file.name);
				if (checkImgType(file.name)) {
					uploadFlg = false;
				}
			}
		} else {
			console.log("抱歉，你的浏览器不支持FileAPI，请升级浏览器！");
		}
		return uploadFlg;

	});
});

function download() {
	var pno = $("#pno").val();
	var url = $("#ctx").val() + "/download?pno=" + pno;
	window.open(url);
}

/*
 * 判断图片类型
 * 
 * @param ths type="file"的javascript对象 @return true-符合要求,false-不符合
 */
function checkImgType(fileName) {
	if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(fileName)) {
		$("#messageE").text(fileName + " type must be one of Gif, JPEG, JPG, or png"); 
		return true;
	}
	return false;
}



// else {
// var img = new Image();
// img.src = filepath;
// while (true) {
// if (img.fileSize > 0) {
// if (img.fileSize > 10 * 1024) {
// alert("图片不大于10M。");
// return false;
// }
// break;
// }
//
// }
// }

// /*
// * 判断图片大小
// *
// * @param ths type="file"的javascript对象 @param width 需要符合的宽 @param height
// 需要符合的高
// * @return true-符合要求,false-不符合
// */
// function checkImgPX(ths, width, height) {
// var img = null;
// img = document.createElement("img");
// document.body.insertAdjacentElement("beforeEnd", img); // firefox不行
// img.style.visibility = "hidden";
// img.src = ths.value;
// var imgwidth = img.offsetWidth;
// var imgheight = img.offsetHeight;
//
// alert(imgwidth + "," + imgheight);
//
// if (imgwidth != width || imgheight != height) {
// alert("图的尺寸应该是" + width + "x" + height);
// ths.value = "";
// return false;
// }
// return true;
// }
