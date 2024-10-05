/**
 * Nabil Mansouri
 */

if (typeof String.prototype.startsWith != 'function') {
	String.prototype.startsWith = function(str) {
		return typeof str == "string" && this.trim().toLowerCase().indexOf(str.toLowerCase()) == 0;
	};
}
function objectCLEAR(myObject){
	for (var member in myObject) delete myObject[member];
}
function objectIsEmpty(obj) {

	// null and undefined are "empty"
	if (obj == null)
		return true;

	// Assume if it has a length property with a non-zero value
	// that that property is correct.
	if (obj.length > 0)
		return false;
	if (obj.length === 0)
		return true;

	// Otherwise, does it have any properties of its own?
	// Note that this doesn't handle
	// toString and valueOf enumeration bugs in IE < 9
	for ( var key in obj) {
		if (hasOwnProperty.call(obj, key))
			return false;
	}

	return true;
}
if (!String.prototype.endsWith) {
	String.prototype.endsWith = function(searchString, position) {
		var subjectString = this.toString();
		searchString = StringUtils.toString(searchString);
		if (position === undefined || position > subjectString.length) {
			position = subjectString.length;
		}
		position -= searchString.length;
		var lastIndex = subjectString.indexOf(searchString, position);
		return lastIndex !== -1 && lastIndex === position;
	};
}

if (typeof String.prototype.endsWithOne != 'function') {
	String.prototype.endsWithOne = function(arr) {
		for (var i = 0; i < arr.length; i++) {
			if (this.endsWith(arr[i])) {
				return true;
			}
		}
		return false;
	};
}

if (typeof String.prototype.startsWithOne != 'function') {
	String.prototype.startsWithOne = function(arr) {
		for (var i = 0; i < arr.length; i++) {
			if (this.startsWith(arr[i])) {
				return true;
			}
		}
		return false;
	};
}

if (!String.prototype.trim) {
	String.prototype.trim = function() {
		return this.replace(/^\s+|\s+$/g, '');
	};
}
if (!String.prototype.clean) {
	String.prototype.clean = function() {
		return this.replace(/\\u([0-9]|[a-fA-F])([0-9]|[a-fA-F])([0-9]|[a-fA-F])([0-9]|[a-fA-F])/, '').replace(
				'\\\\u001a', "").replace('\\u001a', "").replace('\u001a', "");
	};
}

if (!String.prototype.substringPos0) {
	String.prototype.substringPos0 = function(begin, length) {
		return this.substring(begin, begin + length);
	};
}

if (!String.prototype.substringPosAndTrim0) {
	String.prototype.substringPosAndTrim0 = function(begin, length) {
		return this.substringPos0(begin, length).trim();
	};
}
if (!String.prototype.substringPos) {
	String.prototype.substringPos = function(begin, length) {
		return this.substring(begin - 1, begin - 1 + length);
	};
}

if (!String.prototype.substringPosAndTrim) {
	String.prototype.substringPosAndTrim = function(begin, length) {
		return this.substringPos(begin, length).trim();
	};
}
if (!String.prototype.substringPos0) {
	String.prototype.substringPos0 = function(begin, length) {
		return this.substring(begin, begin + length);
	};
}

if (!String.prototype.substringPosAndTrim0) {
	String.prototype.substringPosAndTrim0 = function(begin, length) {
		return this.substringPos0(begin, length).trim();
	};
}
if (!String.prototype.substringEndAndTrim0) {
	String.prototype.substringEndAndTrim0 = function(begin, end) {
		var length = end - begin + 1;
		return this.substringPos(begin, length).trim();
	};
}
if (!String.prototype.equalsIgnoreCase) {
	String.prototype.equalsIgnoreCase = function(string2) {
		return (typeof string2 == "string") && this.trim().toUpperCase() === string2.trim().toUpperCase();
	};
};
if (!String.prototype.replaceAt) {
	String.prototype.replaceAt = function(index, character) {
		return this.substr(0, index) + character + this.substr(index + character.length);
	}
};
if (!String.prototype.setLength) {
	String.prototype.setLength = function(size) {
		if (this.length < size) {
			// log.debug("Length is lower : "+this.length+"/"+size)
			var str = "";
			for (var i = this.length; i < size; i++) {
				str += " ";
			}
			return this + str;
		} else {
			// log.debug("Length is greater : "+this.length+"/"+size)
			return this.substring(0, size);
		}
	};
};
if (!String.prototype.addAt) {
	String.prototype.addAt = function(string, position, length) {
		if (typeof string == "number") {
			string += "";
		}
		if (typeof string != "string") {
			string = "".setLength(length);
		}
		if (length) {
			string = string.substring(0, length);
		}
		var end = string.length + position;
		var size = Math.max(this.length, end);
		var str = this.setLength(size);
		str = str.replaceAt(position, string);
		return str;
	};
};

if (!String.prototype.addAt1) {
	String.prototype.addAt1 = function(string, position, length) {
		position = position - 1;
		return this.addAt(string, position, length);
	};
};

if (!String.prototype.padStart) {
	String.prototype.padStart = function(width, z) {
		z = z || '0';
		var n = this + '';
		return n.length >= width ? n : new Array(width - n.length + 1).join(z) + n;
	};
};
if (!String.prototype.addAtFormat) {
	String.prototype.addAtFormat = function(string, position, length, formats) {
		if (typeof string == "number") {
			string += "";
		}
		if (typeof string != "string") {
			string = "".setLength(length);
		}
		string = StringUtils.formats(string, length, formats);
		return this.addAt1(string, position, length);
	};
};
if (!String.prototype.addToFormat) {
	String.prototype.addToFormat = function(string, begin, end, formats) {
		var position = begin;
		var length = end - begin + 1;
		return this.addAtFormat(string, position, length, formats);
	};
};
if (!String.prototype.appendFormat) {
	String.prototype.appendFormat = function(string, sep, formats) {
		string = StringUtils.toString(string);
		string = StringUtils.formats(string, length, formats);
		//
		var str = this + "";
		if (!str.isEmpty()) {
			str = str + sep;
		}
		str = str + string;
		return str;
	};
};
if (!String.prototype.appendDef) {
	String.prototype.appendDef = function(def) {
		// log.debug("Setting at: " + JSON.stringify(def));
		var start = 0;
		var str = "";
		for ( var prop in def) {
			if (prop == "toJSONString" || prop == "parseJSON") {
				continue;
			}
			if (typeof def[prop].length == "number") {
				var string = def[prop].val;
				var length = def[prop].length;
				string = StringUtils.toString(string).replace("\n", " ");
				str = str.addAt(string, start, length);
				// log.debug("Setting at: " + prop + "/" + string + "/" + start
				// + "/" + length);
				start += length;
			}
		}
		str = str.setLength(start)
		return this + str;
	};
};
if (!String.prototype.appendDefSep) {
	String.prototype.appendDefSep = function(def, sep, last) {
		var all = []
		var str = "";
		for ( var prop in def) {
			if (prop == "toJSONString" || prop == "parseJSON") {
				continue;
			}
			if (typeof def[prop].val != "undefined") {
				var string = def[prop].val;
				all.push(string)
			} else {
				all.push("")
			}
		}
		str = all.join(sep)
		if (last) {
			str += sep;
		}
		return this + str;
	};
};
if (!String.prototype.addSepFormat) {
	String.prototype.addSepFormat = function(string, sep, position, length, formats) {
		string = StringUtils.toString(string);
		string = StringUtils.formats(string, length, formats);
		if (length && typeof length == "number") {
			string = string.substring(0, length);
		}
		var arr = this.split(sep);
		arr[position] = string;
		for (var i = 0; i < arr.length; i++) {
			if (typeof arr[i] == "undefined") {
				arr[i] = "";
			}
		}
		var str = arr.join(sep);
		return str;
	};
};

if (!String.prototype.setSepLength) {
	String.prototype.setSepLength = function(sep, length) {
		var arr = this.split(sep);
		for (var i = 0; i < length; i++) {
			if (typeof arr[i] == "undefined") {
				arr[i] = "";
			}
		}
		var str = arr.join(sep);
		return str;
	};
};
String.prototype.isEmpty = function() {
	return (0 === this.length);
}

String.prototype.isBlank = function() {
	return /^\s*$/.test(this);
}

String.prototype.isEmpty = function() {
	return (this.length === 0 || !this.trim());
};
String.prototype.replaceAll = function(find, replace) {
	return this.replace(new RegExp(find, 'g'), replace);
};

var StringUtils = {
	concat : function() {
		var str = "";
		for (var i = 0; i < arguments.length; i++) {
			var arg = arguments[i]
			str += this.toString(arg)
		}
		return str;
	},
	replaceAll : function(string, find, replace) {
		return this.toString(string).replaceAll(find, replace);
	},
	getNullOrEmpty : function(array) {
		var empty = []
		for (var i = 0; i < array.length; i++) {
			if (this.isNullOrEmpty(array[i])) {
				empty.push(array[i])
			}
		}
		return empty;
	},
	compare : function(string1, string2, length) {
		string1 = this.truncate(string1, 0, length)
		string2 = this.truncate(string2, 0, length)
		return this.equalsIgnoreCase(string1, string2);
	},
	toUpperCase : function(string) {
		string = this.toString(string)
		return string.toUpperCase()
	},
	isWriteableDef : function(def) {
		var start = 0;
		var str = "";
		for ( var prop in def) {
			if (prop == "toJSONString" || prop == "parseJSON") {
				continue;
			}
			if (typeof def[prop].notEmpty == "boolean") {
				var string = def[prop].val;
				var notEmpty = def[prop].notEmpty;
				if (notEmpty && !StringUtils.isNullOrEmpty(string)) {
					return true;
				}
			} else if (typeof def[prop].notZero == "boolean") {
				var string = def[prop].val;
				var notZero = def[prop].notZero;
				if (notZero && !NumberUtils.isNullOrZero(string)) {
					return true;
				}
			}
		}
		return false;
	},
	trim : function(string) {
		return this.toString(string).trim()
	},
	startsWith : function(string, prefix) {
		return this.toString(string).startsWith(prefix);
	},
	getLength : function(string) {
		return this.toString(string).length;
	},
	getLengthTrim : function(string) {
		return this.toString(string).trim().length;
	},
	setLength : function(string, val) {
		return this.toString(string).setLength(val);
	},
	truncate : function(string, min, max) {
		string = StringUtils.toString(string);
		if (typeof min == "number" && typeof max == "number") {
			return string.substring(min, max);
		} else {
			return string;
		}
	},
	getEnd : function(string, length) {
		string = StringUtils.toString(string);
		if (string.length > length) {
			return string;
		} else {
			return this.truncate(string, (string.length - length), string.length)
		}
	},
	replace : function(string, before, after) {
		string = StringUtils.toString(string);
		return string.replace(before, after);
	},
	replaceAll : function(string, before, after) {
		string = StringUtils.toString(string);
		return string.replaceAll(before, after);
	},
	equalsIgnoreCase : function(string1, string2) {
		string1 = this.toString(string1);
		string2 = this.toString(string2);
		return (typeof string1 == "string") && (typeof string2 == "string") && string1.equalsIgnoreCase(string2);
	},
	equalsIgnoreCaseTrim : function(string1, string2) {
		string1 = this.toString(string1).trim();
		string2 = this.toString(string2).trim();
		return (typeof string1 == "string") && (typeof string2 == "string") && string1.equalsIgnoreCase(string2);
	},
	inIgnoreCase : function(string1, array) {
		for (var i = 0; i < array.length; i++) {
			if (this.equalsIgnoreCase(string1, array[i])) {
				return true;
			}
		}
		return false;
	},
	areNullOrEmpty : function(array) {
		for (var i = 0; i < array.length; i++) {
			if (!this.isNullOrEmpty(array[i])) {
				return false;
			}
		}
		return true;
	},
	compareIn : function(string1, array, length) {
		for (var i = 0; i < array.length; i++) {
			if (this.compare(string1, array[i], length)) {
				return true;
			}
		}
		return false;
	},
	notInIgnoreCase : function(string1, array) {
		for (var i = 0; i < array.length; i++) {
			if (this.equalsIgnoreCase(string1, array[i])) {
				return false;
			}
		}
		return true;
	},
	isNullOrEmpty : function(str) {
		var str1 = StringUtils.toString(str);
		str1 = str1.clean();
		return !str1 || str1.trim().isEmpty();
	},
	hasNonZero : function(str) {
		var str = this.replaceAll(str, "0", "");
		return !this.isNullOrEmpty(str);
	},
	areEquivalent : function(arr1, arr2) {
		// log.debug("Are equivalent?" + JSON.stringify(arr1) + "/" +
		// JSON.stringify(arr2))
		for (var i = 0; i < arr1.length; i++) {
			if (!StringUtils.equalsIgnoreCase(arr1[i], arr2[i])) {
				// log.debug("FALSE")
				return false;
			}
		}
		// log.debug("TRUE")
		return true;
	},
	areEquivalentTrim : function(arr1, arr2) {
		// log.debug("Are equivalent?" + JSON.stringify(arr1) + "/" +
		// JSON.stringify(arr2))
		for (var i = 0; i < arr1.length; i++) {
			var comp1 = StringUtils.trim(arr1[i]);
			var comp2 = StringUtils.trim(arr2[i]);
			if (!StringUtils.equalsIgnoreCase(comp1, comp2)) {
				// log.debug("FALSE")
				return false;
			}
		}
		// log.debug("TRUE")
		return true;
	},
	formats : function(string, length, formats) {
		string = this.toString(string);
		if (formats && formats.length) {
			for (var i = 0; i < formats.length; i++) {
				var format = formats[i];
				if (typeof format == "string" && typeof length == "number") {
					if (format == "pad0") {
						string = string.padStart(length, '0');
					} else if (format == "rep0" && this.isNullOrEmpty(string)) {
						string = "".padStart(length, '0');
					}
				} else if (typeof format == "object") {
					if (format.key && format.key == "default") {
						if (this.isNullOrEmpty(string)) {
							string = format.value;
						}
					} else if (format.key && format.key == "replace") {
						if (this.equalsIgnoreCase(string, format.before)) {
							string = format.after
						}
					} else if (format.key && format.key == "float") {
						if (this.isNullOrEmpty(string)) {
							string = "0";
						}
						string = NumberUtils.printFloat(string, format.ratio, format.formatF, format.formatI,
								format.sep);
						// log.debug("Float : " + string + "/" +
						// JSON.stringify(format))
					}
				}
			}
		}
		return string;
	},
	toString : function(string) {
		if (typeof string == "number") {
			string += "";
		}
		if (typeof string != "string") {
			string = "";
		}
		return string;
	},
	readFromDefinition : function(string, definiton) {
		var start = 0;
		for ( var prop in definiton) {
			var length = definiton[prop].length;
			var value = string.substringPosAndTrim(start + 1, length);
			// log.debug("Getting prop
			// :"+prop+"/"+(start+1)+"/"+(start+length)+"/"+length+"/"+value)
			definiton[prop]["val"] = value;
			start += length;
		}
	},
	readFromDefinitionSep : function(string, definiton, sep) {
		var start = 0;
		var index = 0;
		var splitted = StringUtils.toString(string).split(sep);
		for ( var prop in definiton) {
			if (typeof splitted[index] != "undefined") {
				var value = splitted[index];
				definiton[prop]["val"] = value;
			}
			index++;
		}
	}
};

var sp = {};
sp.SU = StringUtils;
(function(window) {
	var re = {
		not_string : /[^s]/,
		number : /[dief]/,
		text : /^[^\x25]+/,
		modulo : /^\x25{2}/,
		placeholder : /^\x25(?:([1-9]\d*)\$|\(([^\)]+)\))?(\+)?(0|'[^$])?(-)?(\d+)?(?:\.(\d+))?([b-fiosuxX])/,
		key : /^([a-z_][a-z_\d]*)/i,
		key_access : /^\.([a-z_][a-z_\d]*)/i,
		index_access : /^\[(\d+)\]/,
		sign : /^[\+\-]/
	}

	function sprintf() {
		var key = arguments[0], cache = sprintf.cache
		if (!(cache[key] && cache.hasOwnProperty(key))) {
			cache[key] = sprintf.parse(key)
		}
		return sprintf.format.call(null, cache[key], arguments)
	}

	sprintf.format = function(parse_tree, argv) {
		var cursor = 1, tree_length = parse_tree.length, node_type = "", arg, output = [], i, k, match, pad, pad_character, pad_length, is_positive = true, sign = ""
		for (i = 0; i < tree_length; i++) {
			node_type = get_type(parse_tree[i])
			if (node_type === "string") {
				output[output.length] = parse_tree[i]
			} else if (node_type === "array") {
				match = parse_tree[i] // convenience purposes only
				if (match[2]) { // keyword argument
					arg = argv[cursor]
					for (k = 0; k < match[2].length; k++) {
						if (!arg.hasOwnProperty(match[2][k])) {
							throw new Error(sprintf("[sprintf] property '%s' does not exist", match[2][k]))
						}
						arg = arg[match[2][k]]
					}
				} else if (match[1]) { // positional argument (explicit)
					arg = argv[match[1]]
				} else { // positional argument (implicit)
					arg = argv[cursor++]
				}

				if (get_type(arg) == "function") {
					arg = arg()
				}

				if (re.not_string.test(match[8]) && (get_type(arg) != "number" && isNaN(arg))) {
					throw new TypeError(sprintf("[sprintf] expecting number but found %s", get_type(arg)))
				}

				if (re.number.test(match[8])) {
					is_positive = arg >= 0
				}

				switch (match[8]) {
				case "b":
					arg = arg.toString(2)
					break
				case "c":
					arg = String.fromCharCode(arg)
					break
				case "d":
				case "i":
					arg = parseInt(arg, 10)
					break
				case "e":
					arg = match[7] ? arg.toExponential(match[7]) : arg.toExponential()
					break
				case "f":
					arg = match[7] ? parseFloat(arg).toFixed(match[7]) : parseFloat(arg)
					break
				case "o":
					arg = arg.toString(8)
					break
				case "s":
					arg = ((arg = String(arg)) && match[7] ? arg.substring(0, match[7]) : arg)
					break
				case "u":
					arg = arg >>> 0
					break
				case "x":
					arg = arg.toString(16)
					break
				case "X":
					arg = arg.toString(16).toUpperCase()
					break
				}
				if (re.number.test(match[8]) && (!is_positive || match[3])) {
					sign = is_positive ? "+" : "-"
					arg = arg.toString().replace(re.sign, "")
				} else {
					sign = ""
				}
				pad_character = match[4] ? match[4] === "0" ? "0" : match[4].charAt(1) : " "
				pad_length = match[6] - (sign + arg).length
				pad = match[6] ? (pad_length > 0 ? str_repeat(pad_character, pad_length) : "") : ""
				output[output.length] = match[5] ? sign + arg + pad : (pad_character === "0" ? sign + pad + arg : pad
						+ sign + arg)
			}
		}
		return output.join("")
	}

	sprintf.cache = {}

	sprintf.parse = function(fmt) {
		var _fmt = fmt, match = [], parse_tree = [], arg_names = 0
		while (_fmt) {
			if ((match = re.text.exec(_fmt)) !== null) {
				parse_tree[parse_tree.length] = match[0]
			} else if ((match = re.modulo.exec(_fmt)) !== null) {
				parse_tree[parse_tree.length] = "%"
			} else if ((match = re.placeholder.exec(_fmt)) !== null) {
				if (match[2]) {
					arg_names |= 1
					var field_list = [], replacement_field = match[2], field_match = []
					if ((field_match = re.key.exec(replacement_field)) !== null) {
						field_list[field_list.length] = field_match[1]
						while ((replacement_field = replacement_field.substring(field_match[0].length)) !== "") {
							if ((field_match = re.key_access.exec(replacement_field)) !== null) {
								field_list[field_list.length] = field_match[1]
							} else if ((field_match = re.index_access.exec(replacement_field)) !== null) {
								field_list[field_list.length] = field_match[1]
							} else {
								throw new SyntaxError("[sprintf] failed to parse named argument key")
							}
						}
					} else {
						throw new SyntaxError("[sprintf] failed to parse named argument key")
					}
					match[2] = field_list
				} else {
					arg_names |= 2
				}
				if (arg_names === 3) {
					throw new Error("[sprintf] mixing positional and named placeholders is not (yet) supported")
				}
				parse_tree[parse_tree.length] = match
			} else {
				throw new SyntaxError("[sprintf] unexpected placeholder")
			}
			_fmt = _fmt.substring(match[0].length)
		}
		return parse_tree
	}

	var vsprintf = function(fmt, argv, _argv) {
		_argv = (argv || []).slice(0)
		_argv.splice(0, 0, fmt)
		return sprintf.apply(null, _argv)
	}

	/**
	 * helpers
	 */
	function get_type(variable) {
		return Object.prototype.toString.call(variable).slice(8, -1).toLowerCase()
	}

	function str_repeat(input, multiplier) {
		return Array(multiplier + 1).join(input)
	}

	/**
	 * export to either browser or node.js
	 */
	if (typeof exports !== "undefined") {
		exports.sprintf = sprintf
		exports.vsprintf = vsprintf
	} else {
		window.sprintf = sprintf
		window.vsprintf = vsprintf

		if (typeof define === "function" && define.amd) {
			define(function() {
				return {
					sprintf : sprintf,
					vsprintf : vsprintf
				}
			})
		}
	}
})(sp);

var guid = (function() {
	function s4() {
		return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
	}
	return function() {
		return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4() + s4() + s4();
	};
})();