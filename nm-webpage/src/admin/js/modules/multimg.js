var app = angular.module('commons.directives');
app.requires.push("blueimp.fileupload")
// Table
app.directive('multimg', [ '$q', function($q) {
	return {
		restrict : "E",
		scope : {
			images : "=",
			mode : "@"
		},
		replace : true,
		templateUrl : function(element, attr) {
			return attr.template ? attr.template : "multimg.html";
		},
		link : function($scope, elem, attrs, controller) {
			var url = sprintf("%s/commons/images/%s", Wick.BASE_URL, $scope.mode);
			$scope.required = {
				img : $scope.$eval(attrs["requiredImg"])
			}
			$scope.show = {
				detail : ($scope.mode == "Multi")
			}
			$scope.removeMain = function() {
				$scope.images.img = {}
			}
			$scope.remove = function(img, index) {
				$scope.images.imgs.splice(index, 1)
			}
			$scope.select = function(img) {
				$scope.images.imgs = _.without($scope.images.imgs, img);
				$scope.images.imgs.push($scope.images.img);
				$scope.images.img = img;
			} 
			$scope.getURL = function() {
				return url;
			}
			$scope.getImagesBean = function() {
				return $scope.images;
			}
			$scope.setImagesBean = function(images) {
				return $scope.images = images;
			}
		}
	}
} ]);

app.controller('MultiImgSingleController', [ '$scope', '$rootScope', '$controller', function($scope, $rootScope, $controller) {
	$scope.init = function(img) {
		$scope.img = img;
	}
	// FILEUPLOAD
	$scope.$on('fileuploaddone', function(event, data) {
		$scope.setImagesBean(data.result)
	});
	$scope.$on('fileuploadfail', function(event, data) {
	});
	$scope.$on('fileuploadadd', function(e, data) {
		data.url = $scope.getURL();
		data.formData = {
			bean : angular.toJson($scope.getImagesBean())
		}
		data.process().done(function() {
			data.submit();
		});
	});
} ]);
