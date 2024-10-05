CKEDITOR.plugins.add("imgembed", {
	lang : [ "fr" ],
	init : function(g) {
		var d = 0;
		var e;
		var a;
		if (typeof g.lang.imgembed.imgembed != "undefined") {
			g.lang.imgembed = g.lang.imgembed.imgembed;
		}
		var c = g.addCommand("imgembed", new CKEDITOR.dialogCommand("imgembed"));
		c.modes = {
			wysiwyg : 1,
			source : 0
		};
		c.canUndo = true;
		c.addParam = "image";
		if (CKEDITOR.version.charAt(0) == "4") {
			g.ui.addButton("imgembed", {
				label : g.lang.imgembed.button_label,
				command : "imgembed",
				icon : this.path + "imgembed_4.png"
			});
		} else {
			g.ui.addButton("imgembed", {
				label : g.lang.imgembed.button_label,
				command : "imgembed",
				icon : this.path + "imgembed.png"
			});
		}
		CKEDITOR.dialog.add("imgembed", this.path + "dlg.js");
	}
});