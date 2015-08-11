(function(){
	var app = angular.module('app-monitor', []);

	app.controller('ServerController', [ '$scope', '$http', function($scope, $http){
		var self = this;
		$scope.server = {};
		
		$scope.list = function(){
			$http.get('api/servers').then(function(serversList){
				$scope.servers = serversList.data;
			});
		};
		
		$scope.save = function(){
			$http.post('api/servers', $scope.server).then(function(){
				$scope.servers.unshift($scope.server);
				$scope.server = {};
			});
		};
		
		$scope.list();
		
	}]);

})();