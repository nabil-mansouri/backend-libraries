(function(ctx) {
	ObjectUtils = {
		clear : function(myObject) {
			for ( var member in myObject)
				delete myObject[member];
		},
		clearAll : function(myObject, exclude) {
			for ( var member in myObject) {
				if (exclude.indexOf(member) == -1) {
					delete myObject[member];
				}
			}
		}
	};
	UUID = {
		generate : function() {
			var d = new Date().getTime();
			var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
				var r = (d + Math.random() * 16) % 16 | 0;
				d = Math.floor(d / 16);
				return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
			});
			return uuid;
		}
	};
	ArrayUtils = {
		objectToArray : function(object) {
			if (object) {
				var res = []
				for ( var i in object) {
					res.push(object[i])
				}
				return res;
			} else {
				return []
			}
		},
		concat : function(arr1, arr2) {
			arr1 = (arr1 && arr1 instanceof Array) ? arr1 : []
			arr2 = (arr2 && arr2 instanceof Array) ? arr2 : []
			return arr1.concat(arr2)
		}
	};
	Array.prototype.clear = function() {
		while (this.length > 0) {
			this.pop();
		}
	};
	Array.prototype.clone = function() {
		return this.slice(0);
	};
	if (!Array.prototype.forEach) {
		Array.prototype.forEach = function(fun /* , thisp */) {
			var len = this.length >>> 0;
			if (typeof fun != "function") {
				throw new TypeError();
			}

			var thisp = arguments[1];
			for ( var i = 0; i < len; i++) {
				if (i in this) {
					fun.call(thisp, this[i], i, this);
				}
			}
		};
	}
	String.prototype.capitalize = function() {
		return this.charAt(0).toUpperCase() + this.slice(1);
	}
	if (!Array.prototype.foreach) {
		Array.prototype.foreach = function(fun /* , thisp */) {
			var len = this.length >>> 0;
			if (typeof fun != "function") {
				throw new TypeError();
			}

			var thisp = arguments[1];
			for ( var i = 0; i < len; i++) {
				if (i in this) {
					fun.call(thisp, this[i], i, this);
				}
			}
		};
	}
	if (!Array.prototype.filter) {
		Array.prototype.filter = function(fun /* , thisp */) {
			var len = this.length >>> 0;
			if (typeof fun != "function")
				throw new TypeError();

			var res = [];
			var thisp = arguments[1];
			for ( var i = 0; i < len; i++) {
				if (i in this) {
					var val = this[i]; // in case fun mutates this
					if (fun.call(thisp, val, i, this))
						res.push(val);
				}
			}
			return res;
		};
	}
	Object.size = function(obj) {
		var size = 0, key;
		for (key in obj) {
			if (obj.hasOwnProperty(key))
				size++;
		}
		return size;
	};
})(typeof exports != "undefined" ? exports : window);
