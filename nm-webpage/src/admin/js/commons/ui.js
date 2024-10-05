(function(){
	var app = angular.module('commons.ui', []);
	app.service('anchorSmoothScroll', function ($document, $window, $timeout) {

	    var document = $document[0];
	    var window = $window;
	    var self = this;
	    function getCurrentPagePosition(window, document) {
	        // Firefox, Chrome, Opera, Safari
	        if (window.pageYOffset) return window.pageYOffset;
	        // Internet Explorer 6 - standards mode
	        if (document.documentElement && document.documentElement.scrollTop)
	            return document.documentElement.scrollTop;
	        // Internet Explorer 6, 7 and 8
	        if (document.body.scrollTop) return document.body.scrollTop;
	        return 0;
	    }

	    function getElementY(document, element) {
	        var y = element.offsetTop;
	        var node = element;
	        while (node.offsetParent && node.offsetParent != document.body) {
	            node = node.offsetParent;
	            y += node.offsetTop;
	        }
	        return y;
	    }

	    this.scrollDown = function (startY, stopY, speed, distance) {

	        var timer = 0;

	        var step = Math.round(distance / 25);
	        var leapY = startY + step;

	        for (var i = startY; i < stopY; i += step) {
	            setTimeout("window.scrollTo(0, " + leapY + ")", timer * speed);
	            leapY += step;
	            if (leapY > stopY) leapY = stopY;
	            timer++;
	        }
	    };

	    this.scrollUp = function (startY, stopY, speed, distance) {

	        var timer = 0;

	        var step = Math.round(distance / 25);
	        var leapY = startY - step;

	        for (var i = startY; i > stopY; i -= step) {
	            setTimeout("window.scrollTo(0, " + leapY + ")", timer * speed);
	            leapY -= step;
	            if (leapY < stopY) leapY = stopY;
	            timer++;
	        }
	    };

	    this.scrollToTop = function (stopY) {
	        scrollTo(0, stopY);
	    };

	    this.scrollTo = function (elementId, speed) {
	    	$timeout(function(){
	    		var element = document.getElementById(elementId);

		        if (element) {
		            var startY = getCurrentPagePosition(window, document);
		            var stopY = getElementY(document, element);

		            var distance = stopY > startY ? stopY - startY : startY - stopY;

		            if (distance < 100) {
		                self.scrollToTop(stopY);

		            } else {

		                var defaultSpeed = Math.round(distance / 100);
		                speed = speed || (defaultSpeed > 20 ? 20 : defaultSpeed);

		                if (stopY > startY) {
		                    self.scrollDown(startY, stopY, speed, distance);
		                } else {
		                    self.scrollUp(startY, stopY, speed, distance);
		                }
		            }

		        }
	    		
	    	},50);
	        
	        

	    };
	    
	    this.scrollToElement = function (element, speed) {
	    	$timeout(function(){
	    		 if (element) {
	 	            var startY = getCurrentPagePosition(window, document);
	 	            var stopY = getElementY(document, element);

	 	            var distance = stopY > startY ? stopY - startY : startY - stopY;

	 	            if (distance < 100) {
	 	                self.scrollToTop(stopY);

	 	            } else {

	 	                var defaultSpeed = Math.round(distance / 100);
	 	                speed = speed || (defaultSpeed > 20 ? 20 : defaultSpeed);

	 	                if (stopY > startY) {
	 	                    self.scrollDown(startY, stopY, speed, distance);
	 	                } else {
	 	                    self.scrollUp(startY, stopY, speed, distance);
	 	                }
	 	            }

	 	        }
	    	},50);
	        

	    };

	});

})();

