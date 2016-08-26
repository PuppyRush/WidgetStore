var CORE_FILES = [
	"StoreEditor/js/scopeVariable.js",
	/** common library */
	"StoreEditor/js/lib/txlib.js",
	"StoreEditor/js/lib/hyperscript.js",
	"StoreEditor/js/lib/template.js",
	"StoreEditor/js/lib/dgetty.js",
	"StoreEditor/js/lib/xgetty.js",
	"StoreEditor/js/lib/rubber.js",
	/** trex engine & config */
	"StoreEditor/js/trex/trex.js",
	"StoreEditor/js/trex/config.js",
	"StoreEditor/js/trex/event.js",
	/** trex library */
	"StoreEditor/js/trex/lib/markup.js",
	"StoreEditor/js/trex/lib/domutil.js",
	"StoreEditor/js/trex/lib/utils.js",
	/** trex mixins */
	"StoreEditor/js/trex/mixins/ajax.js",
	"StoreEditor/js/trex/mixins/observable.js",
	/** trex common */
	"StoreEditor/js/popuputil.js"
];

try {
	var urlParams = {};
	(function () {
		var e,
			a = /\+/g,  // Regex for replacing addition symbol with a space
			r = /([^&=]+)=?([^&]*)/g,
			d = function (s) {
				return decodeURIComponent(s.replace(a, " "));
			},
			q = window.location.search.substring(1);

		while (e = r.exec(q))
			urlParams[d(e[1])] = d(e[2]);
	})();
	if (urlParams.xssDomain) {
		document.domain = urlParams.xssDomain;
	}
} catch (e) {
	// ignore error when loaded from build script
}

try {
	var basePath = opener.EditorJSLoader.getJSBasePath();
} catch (e) {
	// ignore error when loaded from build script
}

for (var i = 0; i < CORE_FILES.length; i++) {
	if (CORE_FILES[i]) {
		var src = basePath + CORE_FILES[i] + '?v=' + new Date().getTime();
		document.write('<script type="text/javascript" src="' + src + '" charset="utf-8"></script>');
	}
}