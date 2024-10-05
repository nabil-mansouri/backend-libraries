(function() {
	var b = function(a) {
		return {
			title : a.lang.imgembed.dlg_title,
			minWidth : 420,
			minHeight : 70,
			onShow : function() {
				var e = 0;
				var c;
				var d;
			},
			onOk : function() {
				if (window.File && window.FileReader && window.FileList && window.Blob) {
					var d = document.getElementsByName("imgembed_upload_" + CKEDITOR.currentInstance.name)[0].files;
					var c = new FileReader();
					var f = 0;
					var e = this;
					c.onload = function(h) {
						var g = '<img src="' + h.target.result + '"/>';
						e.getParentEditor().insertElement(CKEDITOR.dom.element.createFromHtml(g));
						f++;
						if (f < d.length) {
							c.readAsDataURL(d[f]);
						}
					};
					c.onerror = function(g) {
						console.log("error", g);
						console.log(g.getMessage());
					};
					if (d.length > 0) {
						c.readAsDataURL(d[0]);
					} else {
						alert(a.lang.imgembed.choose_files);
					}
				} else {
					alert(a.lang.imgembed.not_supported);
				}
			},
			contents : [ {
				id : "info",
				label : "",
				accessKey : "I",
				elements : [ {
					type : "vbox",
					padding : 0,
					children : [ {
						type : "vbox",
						id : "uploadbox",
						align : "right",
						children : [ {
							type : "html",
							id : "imgembed_upload_" + CKEDITOR.currentInstance.name,
							html : '<input name="imgembed_upload_' + CKEDITOR.currentInstance.name + '" type="file" multiple="true" class="add-border"/>' + '<!--[if IE]>\n<style type="text/css">.add-border {border: 1px solid gray !important}</style>\n<![endif]-->'
						} ]
					} ]
				} ]
			} ]
		};
	};
	CKEDITOR.dialog.add("imgembed", function(a) {
		console.debug("[imgembed] begin dialog")
		return b(a);
	});
})();