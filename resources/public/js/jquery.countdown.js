! function(t) {
	t.fn.countdown = function(e) {
		function n() {
			f = !1;
			j.attr("readonly", true);
			var t = setInterval(function() {
				u--;
				var e = r.text + "(" + u + ")";
				if(u == 0){
					j.attr("readonly", false);
				}
				o.val(e), 0 == u && (f = !0, u = r.time, o.text(c), clearInterval(t))
			}, 1e3);
		}
		var i = {
				time: 10,
				text: "重发",
				obj: "",
				before: function() {},
				after: function(t) {
					t()
				}
			},
			r = t.extend(i, e),
			f = !0,
			o = t(this),
			c = o.text(),
			u = r.time;
			j = r.obj;
		o.click(function() {
			return f && r.before() ? void r.after(n) : !1
		})
	}
}(jQuery);