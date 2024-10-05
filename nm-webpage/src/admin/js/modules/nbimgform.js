var app = angular.module('commons.directives');
// Table
// Img form
app.directive('nbimgform', [ function() {
	return {
		require : [ '^form', '^fileUpload' ],
		restrict : 'E',
		scope : {
			'mainImg' : '=',
			'imgs' : '=',
			'options' : '=',
			'single' : '@'
		},
		templateUrl : "imgform.html",
		replace : true,
		link : function($scope, element, attrs, ctrls) {
			$scope.form = ctrls[0];
			$scope.addImage = function(img) {
				if ($scope.mainImg && !$scope.single) {
					$scope.imgs.push(img);
				} else {
					$scope.mainImg = img;
				}
			};
			$scope.removeImage = function(index) {
				// console.debug("[ImgForm] delete img:",index)
				$scope.imgs.splice(index, 1);
			};
			$scope.removeMainImage = function() {
				// console.debug("[ImgForm] delete main img")
				if ($scope.imgs && $scope.imgs.length > 0) {
					$scope.mainImg = $scope.imgs.shift();
				} else {
					$scope.mainImg = null;
				}
			};
			$scope.setMainImage = function(index) {
				// console.debug("[ImgForm] setting main img",index)
				var img = $scope.mainImg;
				$scope.mainImg = $scope.imgs[index];
				$scope.imgs[index] = img;
			};
			$scope.options = {
				maxFileSize : 5000000,
				acceptFileTypes : /(\.|\/)(gif|jpe?g|png)$/i,
				url : Wick.BASE_URL + '/ws/upload/image/',
				add : function(e, data) {
					// console.debug("[ImgForm] On fileupload add event in
					// options",arguments);
					data.submit();
				},
				done : function(e, data) {
					// console.debug("[ImgForm] Fileupload done :
					// ",arguments);
					var imgs = data.result;
					for ( var i = 0; i < imgs.length; i++) {
						$scope.addImage(imgs[i]);
					}
				}
			};
			// console.debug("[ImgForm] Init img form...
			// ",$scope.form,$scope.upload);
		}
	};
} ]);